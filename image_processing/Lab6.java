import java.awt.Color;
import java.awt.image.BufferedImage;

public class Lab6 {

    // Helper method to apply a convolution mask to an image
    public static BufferedImage applyConvolution(BufferedImage image, float[][] mask) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int maskSize = mask.length;
        int margin = maskSize / 2;

        for (int y = margin; y < height - margin; y++) {
            for (int x = margin; x < width - margin; x++) {
                float sumR = 0, sumG = 0, sumB = 0;

                // Apply the mask to the pixel and its neighbors
                for (int ky = 0; ky < maskSize; ky++) {
                    for (int kx = 0; kx < maskSize; kx++) {
                        int pixel = image.getRGB(x + kx - margin, y + ky - margin);
                        Color color = new Color(pixel);
                        sumR += color.getRed() * mask[ky][kx];
                        sumG += color.getGreen() * mask[ky][kx];
                        sumB += color.getBlue() * mask[ky][kx];
                    }
                }

                // Clamp the values to the range [0, 255]
                int r = (int) Math.min(Math.max(sumR, 0), 255);
                int g = (int) Math.min(Math.max(sumG, 0), 255);
                int b = (int) Math.min(Math.max(sumB, 0), 255);

                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return result;
    }

    // Predefined masks for convolution
    public static final float[][] AVERAGING_3x3 = {
        {1/9f, 1/9f, 1/9f},
        {1/9f, 1/9f, 1/9f},
        {1/9f, 1/9f, 1/9f}
    };

    public static final float[][] WEIGHTED_AVERAGING_3x3 = {
        {1/16f, 2/16f, 1/16f},
        {2/16f, 4/16f, 2/16f},
        {1/16f, 2/16f, 1/16f}
    };

    public static final float[][] LAPLACIAN_4_NEIGHBOR_3x3 = {
        {0, -1, 0},
        {-1, 4, -1},
        {0, -1, 0}
    };

    public static final float[][] LAPLACIAN_8_NEIGHBOR_3x3 = {
        {-1, -1, -1},
        {-1, 8, -1},
        {-1, -1, -1}
    };

    public static final float[][] LAPLACIAN_ENHANCEMENT_4_NEIGHBOR_3x3 = {
        {0, -1, 0},
        {-1, 5, -1},
        {0, -1, 0}
    };

    public static final float[][] LAPLACIAN_ENHANCEMENT_8_NEIGHBOR_3x3 = {
        {-1, -1, -1},
        {-1, 9, -1},
        {-1, -1, -1}
    };

    public static final float[][] ROBERTS_X_3x3 = {
        {0, 0, 0},
        {0, 0, 1},
        {0, -1, 0}
    };

    public static final float[][] ROBERTS_Y_3x3 = {
        {0, 0, 0},
        {0, 1, 0},
        {0, 0, -1}
    };

    public static final float[][] SOBEL_X_3x3 = {
        {-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}
    };

    public static final float[][] SOBEL_Y_3x3 = {
        {-1, -2, -1},
        {0, 0, 0},
        {1, 2, 1}
    };

    public static final float[][] GAUSSIAN_5x5 = {
        {1/273f, 4/273f, 7/273f, 4/273f, 1/273f},
        {4/273f, 16/273f, 26/273f, 16/273f, 4/273f},
        {7/273f, 26/273f, 41/273f, 26/273f, 7/273f},
        {4/273f, 16/273f, 26/273f, 16/273f, 4/273f},
        {1/273f, 4/273f, 7/273f, 4/273f, 1/273f}
    };

    public static final float[][] LAPLACIAN_OF_GAUSSIAN_5x5 = {
        {0, 0, 1, 0, 0},
        {0, 1, 2, 1, 0},
        {1, 2, -16, 2, 1},
        {0, 1, 2, 1, 0},
        {0, 0, 1, 0, 0}
    };
}