package classes;

import javax.swing.ImageIcon;

public class Tile {
    public int nearbyMines = 0;
    public int nearbyFlags = 0;
    public int x, y;
    public boolean flagged = false;
    public boolean isMine = false;
    public boolean dug = false;

    public ImageIcon getImage() {
        if (flagged) return FileCacher.Images.flagged;
        if (dug) {
            if (isMine) return FileCacher.Images.mine;
            switch (nearbyMines) {
                case 1:
                    return FileCacher.Images.one;
                case 2:
                    return FileCacher.Images.two;
                case 3:
                    return FileCacher.Images.three;
                case 4:
                    return FileCacher.Images.four;
                case 5:
                    return FileCacher.Images.five;
                case 6:
                    return FileCacher.Images.six;
                case 7:
                    return FileCacher.Images.seven;
                case 8:
                    return FileCacher.Images.eight;
                case 0:
                    return FileCacher.Images.none;
                }
            }
        return FileCacher.Images.base;
    }
}
