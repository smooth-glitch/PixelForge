
import java.awt.image.BufferedImage;

public class Lab4 {
    public static BufferedImage applyLogTransform(BufferedImage image, double c) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                r = (int) (c * Math.log(1 + r));
                g = (int) (c * Math.log(1 + g));
                b = (int) (c * Math.log(1 + b));

                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));

                output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return output;
    }

    public static BufferedImage applyPowerLawTransform(BufferedImage image, double c, double gamma) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                r = (int) (c * Math.pow(r, gamma));
                g = (int) (c * Math.pow(g, gamma));
                b = (int) (c * Math.pow(b, gamma));

                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));

                output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return output;
    }

    public static BufferedImage applyBitPlaneSlicing(BufferedImage image, int bitPlane) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Create a mask for the selected bit plane
        int mask = 1 << bitPlane;

        // Extract the bit plane
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Check if the bit is set
                r = (r & mask) != 0 ? 255 : 0;
                g = (g & mask) != 0 ? 255 : 0;
                b = (b & mask) != 0 ? 255 : 0;

                output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return output;
    }

    public static BufferedImage applyRandomLUT(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Create a random lookup table (LUT)
        int[] lut = new int[256];
        for (int i = 0; i < 256; i++) {
            lut[i] = (int) (Math.random() * 256); // Random value between 0 and 255
        }

        // Apply the LUT to the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Replace pixel values with LUT values
                r = lut[r];
                g = lut[g];
                b = lut[b];

                output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return output;
    }
}
