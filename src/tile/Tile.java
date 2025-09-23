package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public Tile() { //final String filename
//        try {
//            //this.image = ImageIO.read(getClass().getResourceAsStream(filename));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
