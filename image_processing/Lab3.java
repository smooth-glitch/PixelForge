
import java.awt.image.BufferedImage;

public class Lab3 {
    public static BufferedImage addImages(BufferedImage img1, BufferedImage img2) {
        return arithmeticOperation(img1, img2, "add");
    }

    public static BufferedImage subtractImages(BufferedImage img1, BufferedImage img2) {
        return arithmeticOperation(img1, img2, "subtract");
    }

    public static BufferedImage multiplyImages(BufferedImage img1, BufferedImage img2) {
        return arithmeticOperation(img1, img2, "multiply");
    }

    public static BufferedImage divideImages(BufferedImage img1, BufferedImage img2) {
        return arithmeticOperation(img1, img2, "divide");
    }

    public static BufferedImage arithmeticOperation(BufferedImage img1, BufferedImage img2, String operation) {
        int width = Math.min(img1.getWidth(), img2.getWidth());
        int height = Math.min(img1.getHeight(), img2.getHeight());
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                int r1 = (rgb1 >> 16) & 0xFF;
                int g1 = (rgb1 >> 8) & 0xFF;
                int b1 = rgb1 & 0xFF;
                
                int r2 = (rgb2 >> 16) & 0xFF;
                int g2 = (rgb2 >> 8) & 0xFF;
                int b2 = rgb2 & 0xFF;

                int r = 0, g = 0, b = 0;

                switch (operation) {
                    case "add":
                        r = Math.min(255, r1 + r2);
                        g = Math.min(255, g1 + g2);
                        b = Math.min(255, b1 + b2);
                        break;
                    case "subtract":
                        r = Math.max(0, r1 - r2);
                        g = Math.max(0, g1 - g2);
                        b = Math.max(0, b1 - b2);
                        break;
                    case "multiply":
                        r = Math.min(255, (r1 * r2) / 255);
                        g = Math.min(255, (g1 * g2) / 255);
                        b = Math.min(255, (b1 * b2) / 255);
                        break;
                    case "divide":
                        r = (r2 != 0) ? Math.min(255, r1 / r2) : r1;
                        g = (g2 != 0) ? Math.min(255, g1 / g2) : g1;
                        b = (b2 != 0) ? Math.min(255, b1 / b2) : b1;
                        break;
                }
                output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return output;
    }
}