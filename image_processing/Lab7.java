import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Lab7 {

    // Exercise 1: Add Salt-and-Pepper Noise
    public static BufferedImage addSaltAndPepperNoise(BufferedImage image, double noiseProbability) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage noisyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                if (Math.random() < noiseProbability / 2) {
                    // Add salt noise (white pixel)
                    noisyImage.setRGB(x, y, new Color(255, 255, 255).getRGB());
                } else if (Math.random() < noiseProbability / 2) {
                    // Add pepper noise (black pixel)
                    noisyImage.setRGB(x, y, new Color(0, 0, 0).getRGB());
                } else {
                    // Keep the original pixel
                    noisyImage.setRGB(x, y, pixel);
                }
            }
        }

        return noisyImage;
    }

    // Exercise 2: Min Filtering
    public static BufferedImage applyMinFilter(BufferedImage image, int kernelSize) {
        return applyFilter(image, kernelSize, "min");
    }

    // Exercise 3: Max Filtering
    public static BufferedImage applyMaxFilter(BufferedImage image, int kernelSize) {
        return applyFilter(image, kernelSize, "max");
    }

    // Exercise 4: Midpoint Filtering
    public static BufferedImage applyMidpointFilter(BufferedImage image, int kernelSize) {
        return applyFilter(image, kernelSize, "midpoint");
    }

    // Exercise 5: Median Filtering
    public static BufferedImage applyMedianFilter(BufferedImage image, int kernelSize) {
        return applyFilter(image, kernelSize, "median");
    }

    // Helper method to apply a filter based on the type
    private static BufferedImage applyFilter(BufferedImage image, int kernelSize, String filterType) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int margin = kernelSize / 2;

        for (int y = margin; y < height - margin; y++) {
            for (int x = margin; x < width - margin; x++) {
                int[] values = new int[kernelSize * kernelSize];
                int index = 0;

                // Collect pixel values in the kernel
                for (int ky = -margin; ky <= margin; ky++) {
                    for (int kx = -margin; kx <= margin; kx++) {
                        int pixel = image.getRGB(x + kx, y + ky);
                        Color color = new Color(pixel);
                        int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                        values[index++] = gray;
                    }
                }

                // Apply the filter
                int newGray = 0;
                switch (filterType) {
                    case "min":
                        newGray = Arrays.stream(values).min().getAsInt();
                        break;
                    case "max":
                        newGray = Arrays.stream(values).max().getAsInt();
                        break;
                    case "midpoint":
                        int min = Arrays.stream(values).min().getAsInt();
                        int max = Arrays.stream(values).max().getAsInt();
                        newGray = (min + max) / 2;
                        break;
                    case "median":
                        Arrays.sort(values);
                        newGray = values[values.length / 2];
                        break;
                }

                result.setRGB(x, y, new Color(newGray, newGray, newGray).getRGB());
            }
        }

        return result;
    }
}