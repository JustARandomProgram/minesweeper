public class Controller {
    private Model model;
    private Window window;

    public Controller setModel(Model newModel) {
        model = newModel;
        return this;
    }

    public Controller setWindow(Window newWindow) {
        window = newWindow;
        return this;
    }

    public void init() {
        window.setVisible(true);
        window.getTimer().start();
    }
}
