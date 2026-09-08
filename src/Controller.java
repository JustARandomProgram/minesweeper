import java.awt.Dimension;
import java.util.ArrayList;

import classes.Tile;

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
        setUpListeners();
        model.setGridDimensions(10, 10);
        model.generateBoard();

        window.setVisible(true);
        window.getTimer().start();
    }

    public void setUpListeners() {
        window.startButton.addActionListener(e -> {
            System.out.println("start");
        });
    }

    public ArrayList<ArrayList<Tile>> getBoard() {
        return model.getBoard();
    }

    public Dimension getGridDimensions() {
        return model.getGridDimensions();
    }
}
