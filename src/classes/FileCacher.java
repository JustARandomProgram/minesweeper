package classes;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

public class FileCacher {
    private static final String assetsDirectory = "./assets";

    public static void init() {
        try {
            Fonts.sevenSegFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(Fonts.sevenSegFontFile));
        } catch (FontFormatException | IOException e) {
            System.out.println("Font files failed to load");
        }
    }

    public static final class Images {
        public static ImageIcon none = new ImageIcon(assetsDirectory+"/images/empty tile.png");
        public static ImageIcon one = new ImageIcon(assetsDirectory+"/images/1.png");
        public static ImageIcon two = new ImageIcon(assetsDirectory+"/images/2.png");
        public static ImageIcon three = new ImageIcon(assetsDirectory+"/images/3.png");
        public static ImageIcon four = new ImageIcon(assetsDirectory+"/images/4.png");
        public static ImageIcon five = new ImageIcon(assetsDirectory+"/images/5.png");
        public static ImageIcon six = new ImageIcon(assetsDirectory+"/images/6.png");
        public static ImageIcon seven = new ImageIcon(assetsDirectory+"/images/7.png");
        public static ImageIcon eight = new ImageIcon(assetsDirectory+"/images/8.png");
        public static ImageIcon mine = new ImageIcon(assetsDirectory+"/images/mine.png");
        public static ImageIcon base = new ImageIcon(assetsDirectory+"/images/base tile.png");
        public static ImageIcon flagged = new ImageIcon(assetsDirectory+"/images/flagged tile.png");
        public static ImageIcon happy = new ImageIcon(assetsDirectory+"/images/happy.png");
        public static ImageIcon sad = new ImageIcon(assetsDirectory+"/images/sad.png");
        public static ImageIcon cool = new ImageIcon(assetsDirectory+"/images/cool.png");
    }
    public static final class Fonts {
        private static File sevenSegFontFile = new File(assetsDirectory+"/fonts/Seven Segment.ttf");
        public static Font sevenSegFont;
    }
}
