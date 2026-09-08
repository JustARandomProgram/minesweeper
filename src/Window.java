import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Window extends JFrame {
    protected final int width, height;
    private Canvas canvas;
    private Timer timer;

    public JButton startButton;

    public class Canvas extends JPanel {
        private Rectangle2D background = new Rectangle2D.Double(0, 0, width, height);
        private Rectangle2D panel = new Rectangle2D.Double(0, 0, width, 100);

        public void paintPanel(Graphics2D g2d) {
            g2d.fill(panel);
            g2d.drawImage(classes.ImageCacher.start.getImage(), 337, 12, 75, 75, null);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(this.getBackground());
            g2d.fill(background);
            g2d.setColor(Color.GRAY);
            paintPanel(g2d);

        }
    }

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setLayout(null);

        startButton = new JButton();
        startButton.setBounds(337, 12, 75, 75);
        startButton.setOpaque(true);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        canvas.add(startButton);

        add(canvas);

        pack();
        setLocationRelativeTo(null);
        timer = new Timer(1000/60, e -> {
            canvas.repaint();
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Timer getTimer() {
        return timer;
    }
}
