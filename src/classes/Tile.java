package classes;

import javax.swing.ImageIcon;

public class Tile {
    public int nearbyMines = 0;
    public boolean flagged = false;
    public boolean isMine = false;
    public boolean dug = false;

    public ImageIcon getImage() {
        if (isMine) return ImageCacher.mine;
        if (flagged) return ImageCacher.flagged;
        if (!dug) {
            switch (nearbyMines) {
                case 1:
                    return ImageCacher.one;
                case 2:
                    return ImageCacher.two;
                case 3:
                    return ImageCacher.three;
                case 4:
                    return ImageCacher.four;
                case 5:
                    return ImageCacher.five;
                case 6:
                    return ImageCacher.six;
                case 7:
                    return ImageCacher.seven;
                case 8:
                    return ImageCacher.eight;
                case 0:
                    return ImageCacher.none; 
            }
        }
        return ImageCacher.base;
    }
}
