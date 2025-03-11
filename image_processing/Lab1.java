import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Stack;

public class Lab1 extends JFrame implements ActionListener {
    protected BufferedImage originalImage;
    protected BufferedImage currentImage;
    protected Stack<BufferedImage> imageHistory = new Stack<>();
    protected JTabbedPane tabbedPane;
    String descs[] = { "Original", "Negative" };
    int opIndex; // option index
    int lastOp;

    public Lab1() {
        setTitle("PixelForge");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load icons for all menu items
        ImageIcon openIcon = new ImageIcon("icons/open.png");
        ImageIcon saveIcon = new ImageIcon("icons/save.png");
        ImageIcon exitIcon = new ImageIcon("icons/exit.png");
        ImageIcon undoIcon = new ImageIcon("icons/undo.png");
        ImageIcon closeIcon = new ImageIcon("icons/close.png");
        ImageIcon rescaleIcon = new ImageIcon("icons/rescale.png");
        ImageIcon shiftIcon = new ImageIcon("icons/shift.png");
        ImageIcon shiftRescaleIcon = new ImageIcon("icons/shift_rescale.png");
        ImageIcon negativeIcon = new ImageIcon("icons/negative.png");
        ImageIcon grayscaleIcon = new ImageIcon("icons/grayscale.png");
        ImageIcon addIcon = new ImageIcon("icons/add.png");
        ImageIcon subtractIcon = new ImageIcon("icons/subtract.png");
        ImageIcon multiplyIcon = new ImageIcon("icons/multiply.png");
        ImageIcon divideIcon = new ImageIcon("icons/divide.png");
        ImageIcon powerLawIcon = new ImageIcon("icons/power.png");
        ImageIcon logTransformIcon = new ImageIcon("icons/log.png");
        ImageIcon bitPlaneSlicingIcon = new ImageIcon("icons/layers.png");
        ImageIcon randomLUTIcon = new ImageIcon("icons/random.png");
        ImageIcon averagingIcon = new ImageIcon("icons/average.png");
        ImageIcon weightedAveragingIcon = new ImageIcon("icons/weighted_average.png");
        ImageIcon laplacian4Icon = new ImageIcon("icons/laplacian4.png");
        ImageIcon laplacian8Icon = new ImageIcon("icons/laplacian8.png");
        ImageIcon laplacianEnhancement4Icon = new ImageIcon("icons/laplacian_enhancement4.png");
        ImageIcon laplacianEnhancement8Icon = new ImageIcon("icons/laplacian_enhancement8.png");
        ImageIcon robertsIcon = new ImageIcon("icons/roberts.png");
        ImageIcon sobelXIcon = new ImageIcon("icons/sobel_x.png");
        ImageIcon sobelYIcon = new ImageIcon("icons/sobel_y.png");
        ImageIcon gaussianIcon = new ImageIcon("icons/gaussian.png");
        ImageIcon laplacianOfGaussianIcon = new ImageIcon("icons/laplacian_of_gaussian.png");
        ImageIcon saltAndPepperIcon = new ImageIcon("icons/noise.png");
        ImageIcon minFilterIcon = new ImageIcon("icons/min.png");
        ImageIcon maxFilterIcon = new ImageIcon("icons/max.png");
        ImageIcon midpointFilterIcon = new ImageIcon("icons/midpoint.png");
        ImageIcon medianFilterIcon = new ImageIcon("icons/median.png");
        ImageIcon histogramIcon = new ImageIcon("icons/histogram.png");
        ImageIcon normalizeHistogramIcon = new ImageIcon("icons/normalize.png");
        ImageIcon equalizeHistogramIcon = new ImageIcon("icons/equalize.png");
        ImageIcon meanStdDevIcon = new ImageIcon("icons/mean_std_dev.png"); // New icon
        ImageIcon simpleThresholdIcon = new ImageIcon("icons/simple_threshold.png"); // New icon
        ImageIcon automatedThresholdIcon = new ImageIcon("icons/automated_threshold.png");

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu processMenu = new JMenu("Process");
        JMenu arithmeticMenu = new JMenu("Arithmetics");
        JMenu transformationsMenu = new JMenu("Transformations");
        JMenu histogramMenu = new JMenu("Histograms");
        JMenu convolutionMenu = new JMenu("Convolution");
        JMenu filteringMenu = new JMenu("Filtering");
        JMenu thresholdingMenu = new JMenu("Thresholding");

        // File menu items
        JMenuItem openItem = new JMenuItem("Open Image", openIcon);
        JMenuItem saveItem = new JMenuItem("Save Image", saveIcon);
        JMenuItem exitItem = new JMenuItem("Exit", exitIcon);

        // Edit menu items
        JMenuItem undoItem = new JMenuItem("Undo", undoIcon);
        JMenuItem closeTabItem = new JMenuItem("Close Tab", closeIcon);

        // Process menu items
        JMenuItem rescaleItem = new JMenuItem("Rescale Image", rescaleIcon);
        JMenuItem shiftItem = new JMenuItem("Shift Image", shiftIcon);
        JMenuItem shiftRescaleItem = new JMenuItem("Shift & Rescale Image", shiftRescaleIcon);
        JMenuItem negativeItem = new JMenuItem("Negative Image", negativeIcon);
        JMenuItem grayscaleItem = new JMenuItem("Grayscale Image", grayscaleIcon);

        // Arithmetics menu items
        JMenuItem addItem = new JMenuItem("Add Images", addIcon);
        JMenuItem subtractItem = new JMenuItem("Subtract Images", subtractIcon);
        JMenuItem multiplyItem = new JMenuItem("Multiply Images", multiplyIcon);
        JMenuItem divideItem = new JMenuItem("Divide Images", divideIcon);

        // Transformations menu items
        JMenuItem powerLawItem = new JMenuItem("Power-law Transform", powerLawIcon);
        JMenuItem logTransformItem = new JMenuItem("Log Transform", logTransformIcon);
        JMenuItem bitPlaneSlicingItem = new JMenuItem("Bit-plane Slicing", bitPlaneSlicingIcon);
        JMenuItem randomLUTItem = new JMenuItem("Random Lookup Table", randomLUTIcon);

        // Convolution menu items
        JMenuItem averaging3x3Item = new JMenuItem("Averaging 3x3", averagingIcon);
        JMenuItem weightedAveraging3x3Item = new JMenuItem("Weighted Averaging 3x3", weightedAveragingIcon);
        JMenuItem laplacian4Neighbor3x3Item = new JMenuItem("4-Neighbor Laplacian 3x3", laplacian4Icon);
        JMenuItem laplacian8Neighbor3x3Item = new JMenuItem("8-Neighbor Laplacian 3x3", laplacian8Icon);
        JMenuItem laplacianEnhancement4Neighbor3x3Item = new JMenuItem("4-Neighbor Laplacian Enhancement 3x3",
                laplacianEnhancement4Icon);
        JMenuItem laplacianEnhancement8Neighbor3x3Item = new JMenuItem("8-Neighbor Laplacian Enhancement 3x3",
                laplacianEnhancement8Icon);
        JMenuItem robertsItem = new JMenuItem("Roberts", robertsIcon);
        JMenuItem sobelXItem = new JMenuItem("Sobel X", sobelXIcon);
        JMenuItem sobelYItem = new JMenuItem("Sobel Y", sobelYIcon);
        JMenuItem gaussian5x5Item = new JMenuItem("Gaussian 5x5", gaussianIcon);
        JMenuItem laplacianOfGaussian5x5Item = new JMenuItem("Laplacian of Gaussian 5x5", laplacianOfGaussianIcon);

        // Filtering menu items
        JMenuItem saltAndPepperNoiseItem = new JMenuItem("Salt-and-Pepper Noise", saltAndPepperIcon);
        JMenuItem minFilterItem = new JMenuItem("Min Filter", minFilterIcon);
        JMenuItem maxFilterItem = new JMenuItem("Max Filter", maxFilterIcon);
        JMenuItem midpointFilterItem = new JMenuItem("Midpoint Filter", midpointFilterIcon);
        JMenuItem medianFilterItem = new JMenuItem("Median Filter", medianFilterIcon);

        // Histogram menu items
        JMenuItem calculateHistogramItem = new JMenuItem("Calculate Histogram", histogramIcon);
        JMenuItem normalizeHistogramItem = new JMenuItem("Normalize Histogram", normalizeHistogramIcon);
        JMenuItem equalizeHistogramItem = new JMenuItem("Equalize Histogram", equalizeHistogramIcon);

        // Thresholding menu items
        JMenuItem meanStdDevItem = new JMenuItem("Mean & Std Dev", meanStdDevIcon); // New menu item
        JMenuItem simpleThresholdItem = new JMenuItem("Simple Threshold", simpleThresholdIcon); // New menu item
        JMenuItem automatedThresholdItem = new JMenuItem("Automated Threshold", automatedThresholdIcon);

        // Add action listeners
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        undoItem.addActionListener(this);
        closeTabItem.addActionListener(this);
        rescaleItem.addActionListener(this);
        shiftItem.addActionListener(this);
        shiftRescaleItem.addActionListener(this);
        negativeItem.addActionListener(this);
        grayscaleItem.addActionListener(this);
        addItem.addActionListener(this);
        subtractItem.addActionListener(this);
        multiplyItem.addActionListener(this);
        divideItem.addActionListener(this);
        powerLawItem.addActionListener(this);
        logTransformItem.addActionListener(this);
        bitPlaneSlicingItem.addActionListener(this);
        randomLUTItem.addActionListener(this);
        averaging3x3Item.addActionListener(this);
        weightedAveraging3x3Item.addActionListener(this);
        laplacian4Neighbor3x3Item.addActionListener(this);
        laplacian8Neighbor3x3Item.addActionListener(this);
        laplacianEnhancement4Neighbor3x3Item.addActionListener(this);
        laplacianEnhancement8Neighbor3x3Item.addActionListener(this);
        robertsItem.addActionListener(this);
        sobelXItem.addActionListener(this);
        sobelYItem.addActionListener(this);
        gaussian5x5Item.addActionListener(this);
        laplacianOfGaussian5x5Item.addActionListener(this);
        saltAndPepperNoiseItem.addActionListener(this);
        minFilterItem.addActionListener(this);
        maxFilterItem.addActionListener(this);
        midpointFilterItem.addActionListener(this);
        medianFilterItem.addActionListener(this);
        calculateHistogramItem.addActionListener(this);
        normalizeHistogramItem.addActionListener(this);
        equalizeHistogramItem.addActionListener(this);
        meanStdDevItem.addActionListener(this);
        simpleThresholdItem.addActionListener(this);
        automatedThresholdItem.addActionListener(this);

        // Add items to menus
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        editMenu.add(undoItem);
        editMenu.add(closeTabItem);
        processMenu.add(rescaleItem);
        processMenu.add(shiftItem);
        processMenu.add(shiftRescaleItem);
        processMenu.add(negativeItem);
        processMenu.add(grayscaleItem);
        arithmeticMenu.add(addItem);
        arithmeticMenu.add(subtractItem);
        arithmeticMenu.add(multiplyItem);
        arithmeticMenu.add(divideItem);
        transformationsMenu.add(powerLawItem);
        transformationsMenu.add(logTransformItem);
        transformationsMenu.add(bitPlaneSlicingItem);
        transformationsMenu.add(randomLUTItem);
        convolutionMenu.add(averaging3x3Item);
        convolutionMenu.add(weightedAveraging3x3Item);
        convolutionMenu.add(laplacian4Neighbor3x3Item);
        convolutionMenu.add(laplacian8Neighbor3x3Item);
        convolutionMenu.add(laplacianEnhancement4Neighbor3x3Item);
        convolutionMenu.add(laplacianEnhancement8Neighbor3x3Item);
        convolutionMenu.add(robertsItem);
        convolutionMenu.add(sobelXItem);
        convolutionMenu.add(sobelYItem);
        convolutionMenu.add(gaussian5x5Item);
        convolutionMenu.add(laplacianOfGaussian5x5Item);
        filteringMenu.add(saltAndPepperNoiseItem);
        filteringMenu.add(minFilterItem);
        filteringMenu.add(maxFilterItem);
        filteringMenu.add(midpointFilterItem);
        filteringMenu.add(medianFilterItem);
        histogramMenu.add(calculateHistogramItem);
        histogramMenu.add(normalizeHistogramItem);
        histogramMenu.add(equalizeHistogramItem);
        thresholdingMenu.add(meanStdDevItem);
        thresholdingMenu.add(simpleThresholdItem);
        thresholdingMenu.add(automatedThresholdItem);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(processMenu);
        menuBar.add(arithmeticMenu);
        menuBar.add(transformationsMenu);
        menuBar.add(histogramMenu);
        menuBar.add(convolutionMenu);
        menuBar.add(filteringMenu);
        menuBar.add(thresholdingMenu);

        setJMenuBar(menuBar);

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        // Add MouseListener to tabs for close button
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex != -1) {
                JPanel selectedPanel = (JPanel) tabbedPane.getComponentAt(selectedIndex);
                if (selectedPanel != null && selectedPanel.getComponentCount() == 1) {
                    addCloseButtonToTab(selectedPanel);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open Image":
                openImage();
                break;
            case "Save Image":
                saveImage();
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Undo":
                undo();
                break;
            case "Close Tab":
                closeTab();
                break;
            case "Rescale Image":
                float scaleFactor = getUserInputForFloat("Enter scale factor (0 to 2):");
                imageHistory.push(copyImage(currentImage)); // Push current state to stack
                currentImage = Lab2.rescaleImage(currentImage, scaleFactor);
                addImageToTab(currentImage, "Rescaled Image");
                break;
            case "Shift Image":
                int shiftValue = getUserInputForInt("Enter shift value:");
                imageHistory.push(copyImage(currentImage)); // Push current state to stack
                currentImage = Lab2.shiftImage(currentImage, shiftValue);
                addImageToTab(currentImage, "Shifted Image");
                break;
            case "Shift & Rescale Image":
                imageHistory.push(copyImage(currentImage)); // Push current state to stack
                currentImage = Lab2.shiftRescaleImage(currentImage);
                addImageToTab(currentImage, "Shifted & Rescaled Image");
                break;
            case "Log Transform":
                double c = getUserInputForDouble("Enter constant value for log transform:");
                imageHistory.push(copyImage(currentImage)); // Push current state to stack
                currentImage = Lab4.applyLogTransform(currentImage, c);
                addImageToTab(currentImage, "Log Transform");
                break;
            case "Power Law Transform":
                double gamma = getUserInputForDouble("Enter gamma value for power-law transform:");
                double cPower = getUserInputForDouble("Enter constant value for power-law transform:");
                imageHistory.push(copyImage(currentImage)); // Push current state to stack
                currentImage = Lab4.applyPowerLawTransform(currentImage, cPower, gamma);
                addImageToTab(currentImage, "Power Law Transform");
                break;
            case "Negative Image":
                imageHistory.push(copyImage(currentImage)); // Push current state to stack
                currentImage = applyNegative(currentImage);
                addImageToTab(currentImage, "Negative Image");
                break;
            case "Grayscale Image":
                imageHistory.push(copyImage(currentImage)); // Push current state to stack
                currentImage = applyGrayscale(currentImage);
                addImageToTab(currentImage, "Grayscale Image");
                break;
            case "Add Images":
                performArithmeticOperation("add");
                break;
            case "Subtract Images":
                performArithmeticOperation("subtract");
                break;
            case "Multiply Images":
                performArithmeticOperation("multiply");
                break;
            case "Divide Images":
                performArithmeticOperation("divide");
                break;
            case "Random Look-up Table":
                imageHistory.push(copyImage(currentImage)); // Push current state to stack
                currentImage = Lab4.applyRandomLUT(currentImage);
                addImageToTab(currentImage, "Random LUT");
                break;
            case "Bit-plane Slicing":
                int bitPlane = getUserInputForInt("Enter bit plane (0-7):");
                if (bitPlane >= 0 && bitPlane <= 7) {
                    imageHistory.push(copyImage(currentImage)); // Push current state to stack
                    currentImage = Lab4.applyBitPlaneSlicing(currentImage, bitPlane);
                    addImageToTab(currentImage, "Bit-plane " + bitPlane);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid bit plane. Please enter a value between 0 and 7.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Calculate Histogram":
                int[] histogram = Lab5.calculateHistogram(currentImage);
                displayHistogram(histogram, "Histogram");
                break;
            case "Normalize Histogram":
                int[] histogramData = Lab5.calculateHistogram(currentImage);
                double[] normalizedHistogram = Lab5.normalizeHistogram(histogramData,
                        currentImage.getWidth() * currentImage.getHeight());
                displayNormalizedHistogram(normalizedHistogram, "Normalized Histogram");
                break;
            case "Equalize Histogram":
                imageHistory.push(copyImage(currentImage)); // Save current state
                currentImage = Lab5.equalizeHistogram(currentImage);
                addImageToTab(currentImage, "Equalized Image");
                break;
            case "Averaging 3x3":
                applyConvolution(Lab6.AVERAGING_3x3, "Averaging 3x3");
                break;
            case "Weighted Averaging 3x3":
                applyConvolution(Lab6.WEIGHTED_AVERAGING_3x3, "Weighted Averaging 3x3");
                break;
            case "4-Neighbor Laplacian 3x3":
                applyConvolution(Lab6.LAPLACIAN_4_NEIGHBOR_3x3, "4-Neighbor Laplacian 3x3");
                break;
            case "8-Neighbor Laplacian 3x3":
                applyConvolution(Lab6.LAPLACIAN_8_NEIGHBOR_3x3, "8-Neighbor Laplacian 3x3");
                break;
            case "4-Neighbor Laplacian Enhancement 3x3":
                applyConvolution(Lab6.LAPLACIAN_ENHANCEMENT_4_NEIGHBOR_3x3, "4-Neighbor Laplacian Enhancement 3x3");
                break;
            case "8-Neighbor Laplacian Enhancement 3x3":
                applyConvolution(Lab6.LAPLACIAN_ENHANCEMENT_8_NEIGHBOR_3x3, "8-Neighbor Laplacian Enhancement 3x3");
                break;
            case "Roberts":
                applyConvolution(Lab6.ROBERTS_X_3x3, "Roberts X");
                applyConvolution(Lab6.ROBERTS_Y_3x3, "Roberts Y");
                break;
            case "Sobel X":
                applyConvolution(Lab6.SOBEL_X_3x3, "Sobel X");
                break;
            case "Sobel Y":
                applyConvolution(Lab6.SOBEL_Y_3x3, "Sobel Y");
                break;
            case "Gaussian 5x5":
                applyConvolution(Lab6.GAUSSIAN_5x5, "Gaussian 5x5");
                break;
            case "Laplacian of Gaussian 5x5":
                applyConvolution(Lab6.LAPLACIAN_OF_GAUSSIAN_5x5, "Laplacian of Gaussian 5x5");
                break;
            case "Salt-and-Pepper Noise":
                double noiseProbability = getUserInputForDouble("Enter noise probability (0 to 1):");
                if (noiseProbability >= 0 && noiseProbability <= 1) {
                    imageHistory.push(copyImage(currentImage)); // Save current state
                    currentImage = Lab7.addSaltAndPepperNoise(currentImage, noiseProbability);
                    addImageToTab(currentImage, "Salt-and-Pepper Noise");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid noise probability. Please enter a value between 0 and 1.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Min Filter":
                imageHistory.push(copyImage(currentImage)); // Save current state
                currentImage = Lab7.applyMinFilter(currentImage, 3);
                addImageToTab(currentImage, "Min Filter");
                break;
            case "Max Filter":
                imageHistory.push(copyImage(currentImage)); // Save current state
                currentImage = Lab7.applyMaxFilter(currentImage, 3);
                addImageToTab(currentImage, "Max Filter");
                break;
            case "Midpoint Filter":
                imageHistory.push(copyImage(currentImage)); // Save current state
                currentImage = Lab7.applyMidpointFilter(currentImage, 3);
                addImageToTab(currentImage, "Midpoint Filter");
                break;
            case "Median Filter":
                imageHistory.push(copyImage(currentImage)); // Save current state
                currentImage = Lab7.applyMedianFilter(currentImage, 3);
                addImageToTab(currentImage, "Median Filter");
                break;
            case "Mean & Std Dev":
                int[] hist = Lab5.calculateHistogram(currentImage);
                int totalPixels = currentImage.getWidth() * currentImage.getHeight();
                double[] meanStdDev = Lab8.calculateMeanAndStdDev(hist, totalPixels);
                JOptionPane.showMessageDialog(this,
                        "Mean: " + meanStdDev[0] + "\nStandard Deviation: " + meanStdDev[1],
                        "Mean & Std Dev", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Simple Threshold":
                int threshold = getUserInputForInt("Enter threshold value (0-255):");
                if (threshold >= 0 && threshold <= 255) {
                    imageHistory.push(copyImage(currentImage)); // Save current state
                    currentImage = Lab8.applySimpleThreshold(currentImage, threshold);
                    addImageToTab(currentImage, "Simple Threshold");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid threshold. Please enter a value between 0 and 255.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Automated Threshold":
                imageHistory.push(copyImage(currentImage)); // Save current state
                currentImage = Lab8.applyAutomatedThreshold(currentImage);
                addImageToTab(currentImage, "Automated Threshold");
                break;
        }
    }

    protected void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                originalImage = ImageIO.read(file);
                currentImage = copyImage(originalImage); // Create a working copy
                imageHistory.clear(); // Clear the history
                imageHistory.push(copyImage(originalImage)); // Push the original image to the stack
                addImageToTab(currentImage, "Original Image");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Load default image if no image is selected
            try {
                originalImage = ImageIO.read(new File("icons/default.png")); // Path to your default image
                currentImage = copyImage(originalImage);
                imageHistory.clear();
                imageHistory.push(copyImage(originalImage));
                addImageToTab(currentImage, "Default Image");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading default image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void saveImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                ImageIO.write(currentImage, "png", file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void undo() {
        if (!imageHistory.isEmpty()) {
            // Restore the previous image state from the stack
            currentImage = imageHistory.pop();
            addImageToTab(currentImage, "Undo"); // Display the restored image
        } else {
            JOptionPane.showMessageDialog(this, "No more undo steps available.", "Undo Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void closeTab() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex != -1) {
            tabbedPane.remove(selectedIndex);
            imageHistory.clear(); // Clear history since tab is closed
        }
    }

    protected void addImageToTab(BufferedImage image, String title) {
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        tabbedPane.addTab(title, panel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

    protected BufferedImage copyImage(BufferedImage image) {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = copy.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return copy;
    }

    protected void addCloseButtonToTab(JPanel panel) {
        JButton closeButton = new JButton("X");
        closeButton.setPreferredSize(new Dimension(30, 30));
        closeButton.addActionListener(e -> closeTab());
        panel.add(closeButton, BorderLayout.NORTH);
    }

    private void displayHistogram(int[] histogram, String title) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < histogram.length; i++) {
            sb.append("Intensity ").append(i).append(": ").append(histogram[i]).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Helper method to display normalized histogram
    private void displayNormalizedHistogram(double[] normalizedHistogram, String title) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < normalizedHistogram.length; i++) {
            sb.append("Intensity ").append(i).append(": ").append(normalizedHistogram[i]).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Helper method to apply convolution and display the result
    private void applyConvolution(float[][] mask, String title) {
        if (currentImage != null) {
            imageHistory.push(copyImage(currentImage)); // Save current state
            currentImage = Lab6.applyConvolution(currentImage, mask);
            addImageToTab(currentImage, title);
        } else {
            JOptionPane.showMessageDialog(this, "No image loaded.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected BufferedImage applyNegative(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel);
                int r = 255 - color.getRed();
                int g = 255 - color.getGreen();
                int b = 255 - color.getBlue();
                result.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return result;
    }

    protected BufferedImage applyGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel);
                int gray = (int) (0.3 * color.getRed() + 0.59 * color.getGreen() + 0.11 * color.getBlue());
                result.setRGB(x, y, new Color(gray, gray, gray).getRGB());
            }
        }
        return result;
    }

    // Method for performing arithmetic operations
    private void performArithmeticOperation(String operation) {
        // Open file chooser to select another image
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage secondImage = ImageIO.read(file);

                // Check if the images are the same size
                if (currentImage.getWidth() != secondImage.getWidth()
                        || currentImage.getHeight() != secondImage.getHeight()) {
                    JOptionPane.showMessageDialog(this, "Images must have the same dimensions.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                BufferedImage resultImage = new BufferedImage(currentImage.getWidth(), currentImage.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);
                imageHistory.push(copyImage(currentImage)); // Save the current image state

                // Perform the selected arithmetic operation
                for (int y = 0; y < currentImage.getHeight(); y++) {
                    for (int x = 0; x < currentImage.getWidth(); x++) {
                        Color color1 = new Color(currentImage.getRGB(x, y));
                        Color color2 = new Color(secondImage.getRGB(x, y));

                        int r, g, b;

                        switch (operation) {
                            case "add":
                                r = Math.min(color1.getRed() + color2.getRed(), 255);
                                g = Math.min(color1.getGreen() + color2.getGreen(), 255);
                                b = Math.min(color1.getBlue() + color2.getBlue(), 255);
                                break;
                            case "subtract":
                                r = Math.max(color1.getRed() - color2.getRed(), 0);
                                g = Math.max(color1.getGreen() - color2.getGreen(), 0);
                                b = Math.max(color1.getBlue() - color2.getBlue(), 0);
                                break;
                            case "multiply":
                                r = Math.min(color1.getRed() * color2.getRed() / 255, 255);
                                g = Math.min(color1.getGreen() * color2.getGreen() / 255, 255);
                                b = Math.min(color1.getBlue() * color2.getBlue() / 255, 255);
                                break;
                            case "divide":
                                r = color2.getRed() == 0 ? 0 : Math.min(color1.getRed() / color2.getRed(), 255);
                                g = color2.getGreen() == 0 ? 0 : Math.min(color1.getGreen() / color2.getGreen(), 255);
                                b = color2.getBlue() == 0 ? 0 : Math.min(color1.getBlue() / color2.getBlue(), 255);
                                break;
                            default:
                                r = g = b = 0;
                                break;
                        }

                        resultImage.setRGB(x, y, new Color(r, g, b).getRGB());
                    }
                }

                addImageToTab(resultImage,
                        operation.substring(0, 1).toUpperCase() + operation.substring(1) + " Result");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading second image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Placeholder method for getting the second image
    protected BufferedImage getSecondImage() {
        // Implementation can be to load or choose a second image to perform the
        // operation with.
        return originalImage; // You can update the logic to select a second image
    }

    private int getUserInputForInt(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    private float getUserInputForFloat(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid float number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return 0f;
        }
    }

    // Helper method to get user input for a double value
    private double getUserInputForDouble(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return 0.0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Launch the main application
            Lab1 lab1 = new Lab1();
            lab1.setVisible(true);
        });
    }
}
