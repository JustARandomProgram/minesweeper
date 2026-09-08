import java.awt.Dimension;
import java.util.ArrayList;

import classes.Tile;

public class Model {
    private ArrayList<ArrayList<Tile>> board = new ArrayList<>();
    private int width, height;
    private double mineDensity = 0.1;

    public Model setGridDimensions(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
        return this;
    }

    public Model setMineDensity(double newDensity) {
        mineDensity = newDensity;
        return this;
    }

    public void generateBoard() {
        for (ArrayList<Tile> column : board) {
            column.clear();
        }
        board.clear();
        for (int x = 0; x < width; x++) {
            ArrayList<Tile> column = new ArrayList<>();
            for (int y = 0; y < height; y++) {
                column.add(y, new Tile());
            }
            board.add(x, column);
        }
    }

    public ArrayList<ArrayList<Tile>> getBoard() {
        return board;
    }

    public Dimension getGridDimensions() {
        return new Dimension(width, height);
    }
}
