import javax.swing.*;

import classes.Tile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Window extends JFrame {
    protected final int width, height;
    private Canvas canvas;
    private Timer updateLoop;

    public JButton startButton;
    public JDialog settings;

    public JLabel widthText;
    public JTextField widthField;
    public JLabel heightText;
    public JTextField heightField;
    public JLabel densityText;
    public JTextField densityField;

    public class Canvas extends JPanel {
        private Rectangle2D background = new Rectangle2D.Double(0, 0, width, height);
        private Rectangle2D panel = new Rectangle2D.Double(0, 0, width, 100);

        public void paintPanel(Graphics2D g2d) {
            g2d.fill(panel);
            g2d.drawImage(App.controller.getIconImage().getImage(), 337, 12, 75, 75, null);
            g2d.setColor(Color.black);
            g2d.fill(new Rectangle2D.Double(50,12,200,75));
            g2d.fill(new Rectangle2D.Double(500,12,200,75));
            g2d.setFont(classes.FileCacher.Fonts.sevenSegFont.deriveFont(100f));
            g2d.setColor(Color.red);
            g2d.drawString(String.valueOf(App.controller.remainingFlags()), 50, 85);
            g2d.drawString(String.valueOf(App.controller.getTotalTime()), 500, 85);
        }

        public void paintGrid(Graphics2D g2d) {
            Dimension gridTileDimension = App.controller.getGridDimensions();
            int numOfColumns = gridTileDimension.width, numOfRows = gridTileDimension.height;
            int tileWidth = width/numOfColumns, tileHeight = (height-100)/numOfRows;
            ArrayList<ArrayList<Tile>> board = App.controller.getBoard();
            for (int column = 0; column < numOfColumns; column++) {
                for (int row = 0; row < numOfRows; row++) {
                    g2d.drawImage(board.get(column).get(row).getImage().getImage(), tileWidth*column, 100+tileHeight*row, tileWidth, tileHeight, null);
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(Color.GRAY);
            g2d.fill(background);
            paintPanel(g2d);
            paintGrid(g2d);
        }
    }

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        updateLoop = new Timer(1000/60, e -> {
            canvas.repaint();
        });
    }

    public void init() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setLayout(null);

        startButton = new JButton();
        startButton.setBounds(337, 12, 75, 75);
        startButton.setOpaque(true);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.setFocusable(false);
        canvas.add(startButton);

        add(canvas);

        settings = new JDialog(this, "Settings Panel");
        settings.setResizable(false);
        settings.setLayout(new FlowLayout());
        settings.setLocationRelativeTo(this);

        widthText = new JLabel("Width: ");
        widthText.setFont(classes.FileCacher.Fonts.sevenSegFont.deriveFont(25f));
        widthText.setPreferredSize(new Dimension(100, 50));
        settings.add(widthText);

        widthField = new JTextField(String.valueOf(App.controller.getGridDimensions().width));
        widthField.setFont(classes.FileCacher.Fonts.sevenSegFont.deriveFont(25f));
        widthField.setPreferredSize(new Dimension(200, 50));
        settings.add(widthField);

        heightText = new JLabel("Height: ");
        heightText.setFont(classes.FileCacher.Fonts.sevenSegFont.deriveFont(25f));
        heightText.setPreferredSize(new Dimension(100, 50));
        settings.add(heightText);

        heightField = new JTextField(String.valueOf(App.controller.getGridDimensions().height));
        heightField.setFont(classes.FileCacher.Fonts.sevenSegFont.deriveFont(25f));
        heightField.setPreferredSize(new Dimension(200, 50));
        settings.add(heightField);
        
        densityText = new JLabel("Density: ");
        densityText.setFont(classes.FileCacher.Fonts.sevenSegFont.deriveFont(25f));
        densityText.setPreferredSize(new Dimension(100, 50));
        settings.add(densityText);

        densityField = new JTextField(String.valueOf(App.controller.getMineDensity()));
        densityField.setFont(classes.FileCacher.Fonts.sevenSegFont.deriveFont(25f));
        densityField.setPreferredSize(new Dimension(200, 50));
        settings.add(densityField);

        settings.pack();

        pack();
        setLocationRelativeTo(null);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Timer getUpdateLoop() {
        return updateLoop;
    }
}
