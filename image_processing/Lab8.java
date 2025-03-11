import java.awt.image.BufferedImage;

public class Lab8 {

    // Exercise 1: Mean and Standard Deviation
    public static double[] calculateMeanAndStdDev(int[] histogram, int totalPixels) {
        double mean = 0;
        double stdDev = 0;

        // Calculate mean
        for (int i = 0; i < histogram.length; i++) {
            mean += i * ((double) histogram[i] / totalPixels);
        }

        // Calculate standard deviation
        for (int i = 0; i < histogram.length; i++) {
            stdDev += Math.pow(i - mean, 2) * ((double) histogram[i] / totalPixels);
        }
        stdDev = Math.sqrt(stdDev);

        return new double[]{mean, stdDev};
    }

    // Exercise 2: Simple Thresholding
    public static BufferedImage applySimpleThreshold(BufferedImage image, int threshold) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int gray = (pixel >> 16) & 0xFF; // Extract red channel (grayscale)
                int newGray = (gray >= threshold) ? 255 : 0; // Apply threshold
                int newPixel = (newGray << 16) | (newGray << 8) | newGray; // Create grayscale pixel
                result.setRGB(x, y, newPixel);
            }
        }

        return result;
    }

    // Exercise 3: Automated Thresholding (Iterative Algorithm)
    public static int findOptimalThreshold(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] histogram = new int[256];

        // Calculate histogram
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int gray = (pixel >> 16) & 0xFF; // Extract red channel (grayscale)
                histogram[gray]++;
            }
        }

        // Initialize threshold
        int threshold = 128;
        int newThreshold;
        do {
            newThreshold = threshold;

            // Calculate mean for pixels below and above the threshold
            double meanBelow = 0, meanAbove = 0;
            int countBelow = 0, countAbove = 0;
            for (int i = 0; i < 256; i++) {
                if (i < threshold) {
                    meanBelow += i * histogram[i];
                    countBelow += histogram[i];
                } else {
                    meanAbove += i * histogram[i];
                    countAbove += histogram[i];
                }
            }
            meanBelow /= (countBelow == 0) ? 1 : countBelow;
            meanAbove /= (countAbove == 0) ? 1 : countAbove;

            // Update threshold
            threshold = (int) ((meanBelow + meanAbove) / 2);
        } while (threshold != newThreshold);

        return threshold;
    }

    public static BufferedImage applyAutomatedThreshold(BufferedImage image) {
        int threshold = findOptimalThreshold(image);
        return applySimpleThreshold(image, threshold);
    }
}