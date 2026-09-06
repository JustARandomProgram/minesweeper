package classes;

import javax.swing.ImageIcon;

public class Tile {
    public int nearbyMines = 0;
    public boolean flagged = false;
    public boolean isMine = false;
    public boolean dug = false;

    private static class Images {
        public static final ImageIcon none = new ImageIcon("src/assets/images/empty tile.png");
        public static final ImageIcon one = new ImageIcon("src/assets/images/1.png");
        public static final ImageIcon two = new ImageIcon("src/assets/images/2.png");
        public static final ImageIcon three = new ImageIcon("src/assets/images/3.png");
        public static final ImageIcon four = new ImageIcon("src/assets/images/4.png");
        public static final ImageIcon five = new ImageIcon("src/assets/images/5.png");
        public static final ImageIcon six = new ImageIcon("src/assets/images/6.png");
        public static final ImageIcon seven = new ImageIcon("src/assets/images/7.png");
        public static final ImageIcon eight = new ImageIcon("src/assets/images/8.png");
        public static final ImageIcon mine = new ImageIcon("src/assets/images/mine.png");
        public static final ImageIcon base = new ImageIcon("src/assets/images/base tile.png");
        public static final ImageIcon flagged = new ImageIcon("src/assets/images/flagged tile.png");
    }

    public ImageIcon getImage() {
        if (isMine) return Tile.Images.mine;
        if (flagged) return Tile.Images.flagged;
        if (dug) return Tile.Images.none; 
        switch (nearbyMines) {
            case 1:
                return Tile.Images.one;
            case 2:
                return Tile.Images.two;
            case 3:
                return Tile.Images.three;
            case 4:
                return Tile.Images.four;
            case 5:
                return Tile.Images.five;
            case 6:
                return Tile.Images.six;
            case 7:
                return Tile.Images.seven;
            case 8:
                return Tile.Images.eight;
        }
        return Tile.Images.base;
    }
}
