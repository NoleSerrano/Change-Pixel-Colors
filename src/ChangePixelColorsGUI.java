import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class ChangePixelColorsGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7079411857689753515L;
	private static Color selectedColor;
	private JPanel contentPane;
	private static List<Color> existingColors = new ArrayList<>();
	private static JPanel selectionPanel;
	private static JPanel svBoxesPanel;
	private static int selectedPanelIndex;
	private static JPanel currentSelectedPanelCell;
	private static JPanel colorPanel;
	private static List<Color> newColorsList = new ArrayList<>();
	private static String[] baseImageNames;
	private static List<Integer> colorIndexList;
	private static int pixelImageScalar = 20;
	private static BufferedImage currentImage;
	private static Image scaledImage;
	private static JFrame pixelImageFrame;
	private static String imagePrefixName;
	private static String outputFolderPath;
	private static List<JPanel> colorCells = new ArrayList<>();


	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		imagePrefixName = "bb_";
		outputFolderPath = "output";
		
		File folder = new File(outputFolderPath);
		if (!folder.exists() || !folder.isDirectory()) {
			System.out.println("Invalid folder path");
			return;
		}
		if (imagePrefixName.length() == 0) {
			System.out.println("Image prefix name must not be empty");
			return;
		}

		baseImageNames = new String[] { "move_left", "move_right", "move_up", "move_down" };
		colorIndexList = new ArrayList<>();
		existingColors = new ArrayList<>();

		// Process all images and extract color values
		for (String baseImageName : baseImageNames) {

			BufferedImage image = ImageIO.read(new File(baseImageName + ".png"));
			int width = image.getWidth();
			int height = image.getHeight();

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Color color = new Color(image.getRGB(x, y), true);
					int colorIndex = getColorIndex(color, existingColors);
					System.out.print(colorIndex);
					colorIndexList.add(colorIndex);
				}
				System.out.println();
			}
			System.out.println();

		}

		// Display existing colors
		for (Color color : existingColors) {
			newColorsList.add(color);
			System.out.println(color + ", alpha = " + color.getAlpha());
		}

		// Create a JFrame to hold the panels
		JFrame frame = new JFrame("Color Selection");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(2, 1));

		// create the panels
		colorPanel = createColorPanel();
		selectionPanel = createSelectionPanel();
		JPanel adjustmentPanel = createAdjustmentPanel();
		JButton applyChangesButton = createApplyChangesButton();
		JButton saveChangesButton = createSaveChangesButton();

		// Add the panels to the frame
		frame.add(colorPanel);
		frame.add(selectionPanel);
		frame.add(adjustmentPanel);
		frame.add(applyChangesButton);
		frame.add(saveChangesButton);

		BufferedImage firstImage = ImageIO.read(new File(baseImageNames[0] + ".png"));
		displayPixelImage(firstImage);

		// Pack and display the frame
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public ChangePixelColorsGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel existingColorsPanel = new JPanel();
		existingColorsPanel.setBounds(181, 101, 243, 36);
		contentPane.add(existingColorsPanel);

		JPanel selectionColorsPanel = new JPanel();
		selectionColorsPanel.setBounds(181, 156, 243, 36);
		contentPane.add(selectionColorsPanel);
	}

	// PANELS
	
	private static JPanel createColorPanel() {
		JPanel colorPanel = new JPanel(new GridLayout(1, existingColors.size()));

		// Create a cell for each color
		for (int i = 0; i < existingColors.size(); i++) {
			JPanel cell = new JPanel();
			Color color = existingColors.get(i);
			cell.setBackground(color);
			cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			colorCells.add(cell);

			// Add a MouseListener to handle color selection
			cell.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selectedColor = color;
					selectedPanelIndex = colorCells.indexOf(cell);
					System.out.println("New color selected: " + color + " at selectedPanelIndex " + selectedPanelIndex);

					// Update the first image with the selected color
					// Code for updating the image goes here...
				}
			});

			colorPanel.add(cell);
		}

		return colorPanel;
	}
	
	private static void updateColorsPanel()
	{
		for (int i = 0; i < colorCells.size(); i++)
		{
			JPanel colorCell = colorCells.get(i);
			colorCell.setBackground(newColorsList.get(i));
		}
	}

	private static JPanel createSelectionPanel() {
		JPanel selectionPanel = new JPanel(new GridLayout(1, existingColors.size()));

		// Create a cell for each color
		for (int i = 0; i < existingColors.size(); i++) {
			JPanel cell = new JPanel();
			Color color = existingColors.get(i);
			cell.setBackground(color);
			cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			int k = i;

			// Add a MouseListener to handle color selection
			cell.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selectedColor = color;
					selectedPanelIndex = k;
					currentSelectedPanelCell = cell;
					System.out.println("New color selected: " + color + " at selectedPanelIndex " + selectedPanelIndex);

					// Update the first image with the selected color
					// Code for updating the image goes here...
				}
			});

			selectionPanel.add(cell);
		}

		return selectionPanel;
	}

	private static JPanel createAdjustmentPanel() {
		JPanel adjustmentPanel = new JPanel();
		adjustmentPanel.setLayout(new BoxLayout(adjustmentPanel, BoxLayout.Y_AXIS));

		// Create the hue slider
		JSlider hueSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
		hueSlider.setMajorTickSpacing(60);
		hueSlider.setMinorTickSpacing(10);
		hueSlider.setPaintTicks(true);
		hueSlider.setPaintLabels(true);
		hueSlider.addChangeListener(e -> {
			// Update the hue value based on the slider position
			int hueValue = hueSlider.getValue();
			float[] hsbValues = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(),
					selectedColor.getBlue(), null);
			Color updatedColor = Color.getHSBColor(hueValue / 360f, hsbValues[1], hsbValues[2]);
			selectedColor = new Color(updatedColor.getRed(), updatedColor.getGreen(), updatedColor.getBlue(),
					selectedColor.getAlpha());
			// Update the selection panel with the selected color
			updateSelectionPanel(updatedColor);
		});

		// Add a change listener to the hue slider
		hueSlider.addChangeListener(e -> {
			// Get the updated hue value
			int hue = hueSlider.getValue();
			// Regenerate the SV boxes with the updated hue value
			regenerateSVBoxes(hue);
		});

		// Create the hue line and SV boxes
		JPanel colorPickerPanel = new JPanel();
		colorPickerPanel.setLayout(new BorderLayout());

		// Create the hue line
		JPanel hueLinePanel = new JPanel();
		hueLinePanel.setLayout(new BoxLayout(hueLinePanel, BoxLayout.X_AXIS));
		hueLinePanel.setPreferredSize(new Dimension(400, 20));
		for (int hue = 0; hue <= 360; hue++) {
			Color hueColor = Color.getHSBColor(hue / 360f, 1, 1);
			JPanel hueBox = new JPanel();
			hueBox.setBackground(hueColor);
			hueBox.setPreferredSize(new Dimension(1, 20));
			hueBox.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Get the selected hue from the position of the mouse click
					int x = e.getX();
					int selectedHue = (int) ((x / (double) hueLinePanel.getWidth()) * 360);
					hueSlider.setValue(selectedHue);
				}
			});
			hueLinePanel.add(hueBox);
		}

		// Create the SV boxes panel
		svBoxesPanel = new JPanel();
		svBoxesPanel.setLayout(new GridLayout(10, 36));
		svBoxesPanel.setPreferredSize(new Dimension(400, 200));
		for (int s = 0; s < 10; s++) {
			for (int v = 0; v < 36; v++) {
				float saturation = s / 10f;
				float value = v / 36f;
				Color svColor = Color.getHSBColor(hueSlider.getValue() / 360f, saturation, value);
				JPanel svBox = new JPanel();
				svBox.setBackground(svColor);
				svBox.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// Get the selected color from the SV box
						Color selectedSVColor = svBox.getBackground();
						selectedColor = new Color(selectedSVColor.getRed(), selectedSVColor.getGreen(),
								selectedSVColor.getBlue(), selectedColor.getAlpha());
						// Update the selection panel with the selected color
						updateSelectionPanel(selectedSVColor);
					}
				});
				svBoxesPanel.add(svBox);
			}
		}

		colorPickerPanel.add(hueLinePanel, BorderLayout.NORTH);
		colorPickerPanel.add(svBoxesPanel, BorderLayout.CENTER);

		// Add components to the adjustment panel
		adjustmentPanel.add(hueSlider);
		adjustmentPanel.add(colorPickerPanel);

		return adjustmentPanel;
	}

	private static void updateSelectionPanel(Color newColor) {
		newColorsList.set(selectedPanelIndex, newColor);
		currentSelectedPanelCell.setBackground(newColor);
		;
	}

	private static JButton createApplyChangesButton() {
		JButton applyChangesButton = new JButton("Apply Changes");
		applyChangesButton.addActionListener(e -> {
			// Apply the selected color changes to the first image
			// Code for applying changes goes here...
			updatePixelImageWithSelectedColors();
			updateColorsPanel();
		});
		return applyChangesButton;
	}

	private static JButton createSaveChangesButton() {
		JButton saveChangesButton = new JButton("Save Changes");
		saveChangesButton.addActionListener(e -> {
			// Apply the selected color changes to the first image
			// Code for applying changes goes here...
			// Process all images and extract color values
			for (String baseImageName : baseImageNames) {

				BufferedImage image = null;
				try {
					image = ImageIO.read(new File(baseImageName + ".png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int width = image.getWidth();
				int height = image.getHeight();

				int k = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int colorIndex = colorIndexList.get(k++);
						Color newColor = newColorsList.get(colorIndex);
						int newColorRGB = newColor.getRGB();
						image.setRGB(x, y, newColorRGB);
					}
				}
				// Save the combined image
				try {
					ImageIO.write(image, "PNG", new File(outputFolderPath + File.separator + imagePrefixName + baseImageName + ".png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		return saveChangesButton;
	}

	private static void updatePixelImageWithSelectedColors() {

		int width = currentImage.getWidth();
		int height = currentImage.getHeight();

		int k = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int colorIndex = colorIndexList.get(k++);
				Color color = newColorsList.get(colorIndex);
				int colorRGB = color.getRGB();
				currentImage.setRGB(x, y, colorRGB);
			}
		}

		displayPixelImage(currentImage);
	}

	private static void displayPixelImage(BufferedImage image) {
		// Scale the image to the desired width and height
		currentImage = image;
		scaledImage = image.getScaledInstance(image.getWidth() * pixelImageScalar, image.getHeight() * pixelImageScalar,
				Image.SCALE_DEFAULT);

		if (pixelImageFrame != null) {
			pixelImageFrame.dispose();
		}
		// Create a new JFrame to display the image
		pixelImageFrame = new JFrame("First Image");
		pixelImageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create a JLabel and set it as the content pane of the JFrame
		JLabel label = new JLabel(new ImageIcon(scaledImage));
		pixelImageFrame.setContentPane(label);

		// Pack the frame to accommodate the size of the image
		pixelImageFrame.pack();

		// Set the frame to be visible
		pixelImageFrame.setVisible(true);
	}

	// Method to regenerate the SV boxes with the updated hue value
	private static void regenerateSVBoxes(int hue) {
		svBoxesPanel.removeAll();

		for (int s = 0; s < 10; s++) {
			for (int v = 0; v < 36; v++) {
				float saturation = s / 10f;
				float value = v / 36f;
				Color svColor = Color.getHSBColor(hue / 360f, saturation, value);
				JPanel svBox = new JPanel();
				svBox.setBackground(svColor);
				svBox.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// Get the selected color from the SV box
						Color selectedSVColor = svBox.getBackground();
						selectedColor = new Color(selectedSVColor.getRed(), selectedSVColor.getGreen(),
								selectedSVColor.getBlue(), selectedColor.getAlpha());
						// Update the selection panel with the selected color
						updateSelectionPanel(selectedColor);
					}
				});
				svBoxesPanel.add(svBox);
			}
		}

		svBoxesPanel.revalidate();
		svBoxesPanel.repaint();
	}

	public static int getColorIndex(Color color, List<Color> existingColors) {
		int colorDifferenceThreshold = 5;
		for (int i = 0; i < existingColors.size(); i++) {
			Color existingColor = existingColors.get(i);
			if (calculateColorDifference(color, existingColor) <= colorDifferenceThreshold) {
				return i;
			}
		}
		existingColors.add(color);
		return existingColors.size() - 1;
	}

	public static double calculateColorDifference(Color color1, Color color2) {
		int rDiff = color1.getRed() - color2.getRed();
		int gDiff = color1.getGreen() - color2.getGreen();
		int bDiff = color1.getBlue() - color2.getBlue();
		int aDiff = color1.getAlpha() - color2.getAlpha();

		// Calculate the Euclidean distance
		double distance = Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff + aDiff * aDiff);

		return distance;
	}
}
