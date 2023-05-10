package misc;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import core.Constants;

public class ImageLoader {
    public static BufferedImage loadImage(String filename) {
        try {
            File imageFile = new File(Constants.TEXTURES_FOLDER_PATH + filename);
            return ImageIO.read(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}