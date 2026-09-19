import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
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
                Tile newTile = new Tile();
                newTile.x = x;
                newTile.y = y;
                column.add(y, newTile);
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
                    if (getTile(otherX,otherY).isMine) continue;
                    getTile(otherX, otherY).nearbyMines += 1;
                }
            }
        }
    }

    public void dig(int x, int y) {
        Queue<Tile> bfs = new LinkedList<>();
        bfs.offer(getTile(x, y));
        while (!bfs.isEmpty()) {
            Tile currentTile = bfs.poll();
            if (currentTile.flagged) continue;
            if (currentTile.isMine) {gameOver(); return;}
            if (currentTile.nearbyMines <= 0) {
                for (int otherX = Math.max(currentTile.x - 1, 0); otherX < Math.min(currentTile.x + 2, width); otherX++) {
                    for (int otherY = Math.max(currentTile.y - 1, 0); otherY < Math.min(currentTile.y + 2, height); otherY++) {
                        if (otherX == currentTile.x && otherY == currentTile.y) continue;
                        if (getTile(otherX, otherY).dug) continue;
                        bfs.offer(getTile(otherX, otherY));
                    }
                }
            }
            currentTile.dug = true;
        }
    }

    public void gameOver() {
        for (ArrayList<Tile> column : board) {
            for (Tile tile : column) {
                tile.dug = true;
            }
        }
    }

    public Tile getTile(int x, int y) {
        return board.get(x).get(y);
    }

    public ArrayList<ArrayList<Tile>> getBoard() {
        return board;
    }

    public Dimension getGridDimensions() {
        return new Dimension(width, height);
    }
}
