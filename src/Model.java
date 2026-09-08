import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

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
        int totalNumOfMines = (int)Math.floor((width * height) * mineDensity);
        Random random = new Random();
        int randMineX = random.nextInt(0, width);
        int randMineY = random.nextInt(0, height);
        Tile tile = board.get(randMineX).get(randMineY);
        for (int mine = 0; mine < totalNumOfMines; mine++) {
            while (tile.isMine) {
                randMineX = random.nextInt(0, width);
                randMineY = random.nextInt(0, height);
                tile = board.get(randMineX).get(randMineY);
            }
            tile.isMine = true;
            for (int otherX = Math.max(0, randMineX-1); otherX < Math.min(width, randMineX+2); otherX++) {
                for (int otherY = Math.max(0, randMineY-1); otherY < Math.min(height, randMineY+2); otherY++) {
                    if (board.get(otherX).get(otherY).isMine) continue;
                    board.get(otherX).get(otherY).nearbyMines += 1;
                }
            }
        }
    }

    public ArrayList<ArrayList<Tile>> getBoard() {
        return board;
    }

    public Dimension getGridDimensions() {
        return new Dimension(width, height);
    }
}
