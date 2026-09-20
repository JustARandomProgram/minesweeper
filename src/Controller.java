import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import classes.Tile;

public class Controller {
    private Model model;
    private Window window;
    
    private class MouseInput implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            Dimension gridTileDimension = getGridDimensions();
            int numOfColumns = gridTileDimension.width, numOfRows = gridTileDimension.height;
            int tileWidth = window.width/numOfColumns, tileHeight = (window.height-100)/numOfRows;
            Point mousePos = e.getPoint();
            int mouseGridX = -1, mouseGridY = -1;
            for (int currentX = numOfColumns-1; currentX >= 0; currentX--) {
                if (model.getTile(currentX, 0).x * tileWidth < mousePos.x) {
                    mouseGridX = currentX;
                    break;
                }
            }
            for (int currentY = numOfRows-1; currentY >= 0; currentY--) {
                if (model.getTile(0,currentY).y * tileHeight + 100 < mousePos.y) {
                    mouseGridY = currentY;
                    break;
                }
            }
            if (mouseGridX < 0 || mouseGridY < 0) return;
            if (model.getGameState() == 0) {
                model.getStopwatch().start();
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        model.dig(mouseGridX, mouseGridY);
                        break;
                    case MouseEvent.BUTTON3:
                        Tile tile = model.getTile(mouseGridX, mouseGridY);
                        if (tile.dug) return;
                        tile.flagged = !tile.flagged;
                        if (tile.flagged) {
                            model.incrementFlagsOnBoard();
                        } else {
                            model.decrementFlagsOnBoard();
                        }
                        for (int otherX = Math.max(0, mouseGridX-1); otherX < Math.min(numOfColumns, mouseGridX+2); otherX++) {
                            for (int otherY = Math.max(0, mouseGridY-1); otherY < Math.min(numOfRows, mouseGridY+2); otherY++) {
                                if (model.getTile(otherX,otherY).isMine) continue;
                                model.getTile(otherX, otherY).nearbyFlags += tile.flagged ? 1 : -1;
                            }
                        }
                        break;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

    }

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
        window.getUpdateLoop().start();
    }

    public ImageIcon getIconImage() {
        switch (model.getGameState()) {
            case -1:
                return classes.FileCacher.Images.sad;

            case 0:
                return classes.FileCacher.Images.happy;

            case 1:
                return classes.FileCacher.Images.cool;
        
            default:
                return null;
        }
    }

    public void setUpListeners() {
        window.startButton.addActionListener(e -> {
            model.generateBoard();
        });
        window.getCanvas().addMouseListener(new MouseInput());
    }

    public ArrayList<ArrayList<Tile>> getBoard() {
        return model.getBoard();
    }

    public Dimension getGridDimensions() {
        return model.getGridDimensions();
    }

    public int remainingFlags() {
        return model.remainingFlags();
    }

    public int getTotalTime() {
        return model.getTotalTime();
    }
}
