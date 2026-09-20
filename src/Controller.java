import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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

    private class KeyInput implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    model.generateBoard();
                    break;
                case KeyEvent.VK_X:
                    window.settings.setVisible(true);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

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
        model.setGridDimensions(10, 10);
        model.setMineDensity(0.1);
        model.generateBoard();
        
        window.init();
        setUpListeners();
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
        window.addKeyListener(new KeyInput());

        window.widthField.addActionListener(e -> {
            try {
                int val = Integer.valueOf(window.widthField.getText());
                if (val <= 1) {
                    throw new IllegalArgumentException();
                }
                model.setGridDimensions(val, model.getGridDimensions().height);
                model.generateBoard();
            } catch (NumberFormatException ex) {
                window.widthField.setText(String.valueOf(model.getGridDimensions().width));
                JOptionPane.showMessageDialog(window, "Invalid Argument: Must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                window.widthField.setText(String.valueOf(model.getGridDimensions().width));
                JOptionPane.showMessageDialog(window, "Invalid Argument: Number cannot be less than one", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        window.heightField.addActionListener(e -> {
            try {
                int val = Integer.valueOf(window.heightField.getText());
                if (val <= 1) {
                    throw new IllegalArgumentException();
                }
                model.setGridDimensions(model.getGridDimensions().width, val);
                model.generateBoard();
            } catch (NumberFormatException ex) {
                window.heightField.setText(String.valueOf(model.getGridDimensions().height));
                JOptionPane.showMessageDialog(window, "Invalid Argument: Must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                window.heightField.setText(String.valueOf(model.getGridDimensions().height));
                JOptionPane.showMessageDialog(window, "Invalid Argument: Number cannot be less than one", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        window.densityField.addActionListener(e -> {
            try {
                double val = Double.valueOf(window.densityField.getText());
                if (val > 1 || val < 0) {
                    throw new IllegalArgumentException();
                }
                model.setMineDensity(val);
                model.generateBoard();
            } catch (NumberFormatException ex) {
                window.densityField.setText(String.valueOf(model.getMineDensity()));
                JOptionPane.showMessageDialog(window, "Invalid Argument: Must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                window.densityField.setText(String.valueOf(model.getMineDensity()));
                JOptionPane.showMessageDialog(window, "Invalid Argument: Number must be between 0 and 1", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }



    public Controller setGridDimensions(int width, int height) {
        model.setGridDimensions(width, height);
        return this;
    }

    public Controller setMineDensity(double newDensity) {
        model.setMineDensity(newDensity);
        return this;
    }

    public double getMineDensity() {
        return model.getMineDensity();
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
