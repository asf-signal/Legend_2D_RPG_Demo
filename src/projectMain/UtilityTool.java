package projectMain;

import java.awt.*;
import java.awt.image.BufferedImage;


public class UtilityTool {
    public BufferedImage scaleImage(BufferedImage og, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, og.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(og, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaledImage;
    }
}
