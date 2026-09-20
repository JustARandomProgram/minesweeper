package classes;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

public class FileCacher {
    public static void init() {
        try {
            Fonts.sevenSegFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(Fonts.sevenSegFontFile));
        } catch (FontFormatException | IOException e) {
            System.out.println("Font files failed to load");
        }
    }

    public static final class Images {
        public static ImageIcon none = new ImageIcon("src/assets/images/empty tile.png");
        public static ImageIcon one = new ImageIcon("src/assets/images/1.png");
        public static ImageIcon two = new ImageIcon("src/assets/images/2.png");
        public static ImageIcon three = new ImageIcon("src/assets/images/3.png");
        public static ImageIcon four = new ImageIcon("src/assets/images/4.png");
        public static ImageIcon five = new ImageIcon("src/assets/images/5.png");
        public static ImageIcon six = new ImageIcon("src/assets/images/6.png");
        public static ImageIcon seven = new ImageIcon("src/assets/images/7.png");
        public static ImageIcon eight = new ImageIcon("src/assets/images/8.png");
        public static ImageIcon mine = new ImageIcon("src/assets/images/mine.png");
        public static ImageIcon base = new ImageIcon("src/assets/images/base tile.png");
        public static ImageIcon flagged = new ImageIcon("src/assets/images/flagged tile.png");
        public static ImageIcon happy = new ImageIcon("src/assets/images/happy.png");
        public static ImageIcon sad = new ImageIcon("src/assets/images/sad.png");
        public static ImageIcon cool = new ImageIcon("src/assets/images/cool.png");
    }
    public static final class Fonts {
        private static File sevenSegFontFile = new File("src/assets/fonts/Seven Segment.ttf");
        public static Font sevenSegFont;
    }
}
