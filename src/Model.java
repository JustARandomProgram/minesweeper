import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import classes.Tile;

public class Model {
    private ArrayList<ArrayList<Tile>> board = new ArrayList<>();
    private ArrayList<Tile> mines = new ArrayList<>();
    private int width, height;
    private double mineDensity = 0.1;
    private int gameState = 0;

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
        gameState = 0;
        for (ArrayList<Tile> column : board) {
            column.clear();
        }
        board.clear();
        mines.clear();
        for (int x = 0; x < width;x++) {
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
            mines.add(tile);
            for (int otherX = Math.max(0, randMineX-1); otherX < Math.min(width, randMineX+2); otherX++) {
                for (int otherY = Math.max(0, randMineY-1); otherY < Math.min(height, randMineY+2); otherY++) {
                    if (getTile(otherX,otherY).isMine) continue;
                    getTile(otherX, otherY).nearbyMines += 1;
                }
            }
        }
    }

    private void offerSuroundingTiles(Queue<Tile> bfs, Tile currentTile) {
        for (int otherX = Math.max(currentTile.x - 1, 0); otherX < Math.min(currentTile.x + 2, width); otherX++) {
            for (int otherY = Math.max(currentTile.y - 1, 0); otherY < Math.min(currentTile.y + 2, height); otherY++) {
                if (otherX == currentTile.x && otherY == currentTile.y) continue;
                if (getTile(otherX, otherY).dug) continue;
                bfs.offer(getTile(otherX, otherY));
            }
        }
    }

    public void dig(int x, int y) {
        Queue<Tile> bfs = new LinkedList<>();
        Tile currentTile = getTile(x, y);
        if (currentTile.nearbyMines == currentTile.nearbyFlags && currentTile.nearbyMines > 0 && currentTile.dug) {
            offerSuroundingTiles(bfs, currentTile);
        } else {
            bfs.offer(getTile(x, y));
        }
        while (!bfs.isEmpty()) {
            currentTile = bfs.poll();
            if (currentTile.flagged) continue;
            if (currentTile.isMine) {gameOver(); return;}
            if (currentTile.nearbyMines <= 0) {
                offerSuroundingTiles(bfs, currentTile);
            }
            currentTile.dug = true;
        }
        if (gameState != -1) {
            winCondition();
        }
    }

    private void winCondition() {
        for (Tile mine : mines) {
            for (int otherX = Math.max(mine.x - 1, 0); otherX < Math.min(mine.x + 2, width); otherX++) {
                for (int otherY = Math.max(mine.y - 1, 0); otherY < Math.min(mine.y + 2, height); otherY++) {
                    if (getTile(otherX, otherY).isMine) continue;
                    if (!getTile(otherX, otherY).dug) return;
                }
            }
        }
        gameState = 1;
    }

    private void gameOver() {
        for (ArrayList<Tile> column : board) {
            for (Tile tile : column) {
                tile.dug = true;
            }
        }
        gameState = -1;
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

    public int getGameState() {
        return gameState;
    }
}
