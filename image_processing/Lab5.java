import java.awt.image.BufferedImage;

public class Lab5 {

    // Exercise 1: Finding Histogram
    public static int[] calculateHistogram(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] histogram = new int[256]; // 256 intensity levels for grayscale images

        // Iterate through each pixel to count intensity values
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int gray = (pixel >> 16) & 0xFF; // Extract red channel (grayscale)
                histogram[gray]++;
            }
        }

        return histogram;
    }

    // Exercise 2: Histogram Normalization
    public static double[] normalizeHistogram(int[] histogram, int totalPixels) {
        double[] normalizedHistogram = new double[256];

        for (int i = 0; i < 256; i++) {
            normalizedHistogram[i] = (double) histogram[i] / totalPixels;
        }

        return normalizedHistogram;
    }

    // Exercise 3: Histogram Equalization
    public static BufferedImage equalizeHistogram(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int totalPixels = width * height;

        // Step 1: Calculate the histogram
        int[] histogram = calculateHistogram(image);

        // Step 2: Calculate the cumulative distribution function (CDF)
        int[] cdf = new int[256];
        cdf[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i - 1] + histogram[i];
        }

        // Step 3: Normalize the CDF to get the transformation function
        int[] transform = new int[256];
        for (int i = 0; i < 256; i++) {
            transform[i] = (int) (255 * ((double) cdf[i] / totalPixels));
        }

        // Step 4: Apply the transformation to the image
        BufferedImage equalizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int gray = (pixel >> 16) & 0xFF; // Extract red channel (grayscale)
                int newGray = transform[gray];
                int newPixel = (newGray << 16) | (newGray << 8) | newGray; // Create grayscale pixel
                equalizedImage.setRGB(x, y, newPixel);
            }
        }

        return equalizedImage;
    }
}