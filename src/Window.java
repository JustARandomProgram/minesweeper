import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    protected final int width, height;
    private Canvas canvas;
    private Timer timer;

    public static class Canvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
        }
    }

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(getLayout());

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height-100));
        canvas.setVisible(true);
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
