import java.awt.image.BufferedImage;
import java.util.Random;

public class Lab2 {
    public static BufferedImage rescaleImage(BufferedImage image, float scaleFactor) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (int) (((rgb >> 16) & 0xFF) * scaleFactor);
                int g = (int) (((rgb >> 8) & 0xFF) * scaleFactor);
                int b = (int) ((rgb & 0xFF) * scaleFactor);
                
                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));
                
                output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return output;
    }

    public static BufferedImage shiftImage(BufferedImage image, int shiftValue) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = ((rgb >> 16) & 0xFF) + shiftValue;
                int g = ((rgb >> 8) & 0xFF) + shiftValue;
                int b = (rgb & 0xFF) + shiftValue;
                
                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));
                
                output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return output;
    }

    public static BufferedImage shiftRescaleImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Random rand = new Random();
        
        int minPixel = 255;
        int maxPixel = 0;
        
        int[][] temp = new int[width][height];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int pixel = ((rgb >> 16) & 0xFF) + rand.nextInt(256);
                temp[x][y] = pixel;
                
                minPixel = Math.min(minPixel, pixel);
                maxPixel = Math.max(maxPixel, pixel);
            }
        }
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = (temp[x][y] - minPixel) * 255 / (maxPixel - minPixel);
                output.setRGB(x, y, (0xFF << 24) | (pixel << 16) | (pixel << 8) | pixel);
            }
        }
        return output;
    }
}