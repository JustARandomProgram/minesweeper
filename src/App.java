import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {
    public static Controller controller;
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {
            controller = new Controller()
                        .setModel(new Model())
                        .setWindow(new Window(750, 850));
            controller.init();
        });
    }
}
