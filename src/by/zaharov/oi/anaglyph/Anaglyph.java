package by.zaharov.oi.anaglyph;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Anaglyph {
    private final double[][] leftMatrix = {{1.0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    private final double[][] rightMatrix = {{0, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    private BufferedImage imageLeft = null;
    private BufferedImage imageRight = null;

    BufferedImage buildImage(String leftName, String rightName) {
        try {

            imageLeft = ImageIO.read(new FileImageInputStream(new File(leftName)));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {

            imageRight = ImageIO.read(new FileImageInputStream(new File(rightName)));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (imageLeft.getHeight() != imageRight.getHeight() || imageLeft.getWidth() != imageRight.getWidth() )
            return null;
        BufferedImage anaglyph = new BufferedImage(imageLeft.getWidth(), imageLeft.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int h = 0; h < imageLeft.getHeight(); h++)
            for (int w = 0; w < imageLeft.getWidth(); w++) {
                int pixel = imageLeft.getRGB(w, h);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                int newRed = (int) (leftMatrix[0][0] * red + leftMatrix[0][1] * green + leftMatrix[0][2] * blue);
                int newGreen = (int) (leftMatrix[1][0] * red + leftMatrix[1][1] * green + leftMatrix[1][2] * blue);
                int newBlue = (int) (leftMatrix[2][0] * red + leftMatrix[2][1] * green + leftMatrix[2][2] * blue);
                pixel = imageRight.getRGB(w, h);
                red = (pixel >> 16) & 0xff;
                green = (pixel >> 8) & 0xff;
                blue = (pixel) & 0xff;
                newRed += (int) (rightMatrix[0][0] * red + rightMatrix[0][1] * green + rightMatrix[0][2] * blue);
                newGreen += (int) (rightMatrix[1][0] * red + rightMatrix[1][1] * green + rightMatrix[1][2] * blue);
                newBlue += (int) (rightMatrix[2][0] * red + rightMatrix[2][1] * green + rightMatrix[2][2] * blue);
                Color color = new Color(newRed, newGreen, newBlue);
                pixel = color.getRGB();
                anaglyph.setRGB(w, h, pixel);

            }

        return anaglyph;
    }
}
