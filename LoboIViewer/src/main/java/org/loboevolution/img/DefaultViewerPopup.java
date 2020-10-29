package org.loboevolution.img;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The default popup menu for image viewers. The contents of the menu are
 * unspecified and may change between library versions.
 *
 * @author Kazo Csaba
 * @version $Id: $Id
 */
public class DefaultViewerPopup extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	private final ImageViewer viewer;
	/*
	 * These will only be accessed from the event dispatch thread so using a static
	 * instance to share the current directory across components is fine.
	 */
	private static JFileChooser saveChooser;
	private static JButton saveChooserHelpButton;
	private static JLabel saveChooserHelpLabel;
	
	/**
	 * Creates a popup menu for use with the specified viewer.
	 *
	 * @param imageViewer
	 *            the viewer this popup menu belongs to
	 */
	public DefaultViewerPopup(ImageViewer imageViewer) {
		viewer = imageViewer;
		createAndShowGUI(); 
	}

	private void createAndShowGUI() {
		
		/** Status bar toggle **/

		final JCheckBoxMenuItem toggleStatusBarItem = new JCheckBoxMenuItem("Status bar");
		toggleStatusBarItem.setState(viewer.isStatusBarVisible());
		viewer.addPropertyChangeListener("statusBarVisible", evt -> toggleStatusBarItem.setState(viewer.isStatusBarVisible()));
		toggleStatusBarItem.addActionListener(e -> viewer.setStatusBarVisible(!viewer.isStatusBarVisible()));

		/** Zoom menu **/

		JMenu zoomMenu = new JMenu("Zoom");
		final JRadioButtonMenuItem zoomOriginalSize = new JRadioButtonMenuItem("Original size",
				viewer.getResizeStrategy() == ResizeStrategy.NO_RESIZE);
		zoomOriginalSize.addActionListener(e -> viewer.setResizeStrategy(ResizeStrategy.NO_RESIZE));
		final JRadioButtonMenuItem zoomShrinkToFit = new JRadioButtonMenuItem("Shrink to fit",
				viewer.getResizeStrategy() == ResizeStrategy.SHRINK_TO_FIT);
		zoomShrinkToFit.addActionListener(e -> viewer.setResizeStrategy(ResizeStrategy.SHRINK_TO_FIT));
		final JRadioButtonMenuItem zoomResizeToFit = new JRadioButtonMenuItem("Resize to fit",
				viewer.getResizeStrategy() == ResizeStrategy.RESIZE_TO_FIT);
		zoomResizeToFit.addActionListener(e -> viewer.setResizeStrategy(ResizeStrategy.RESIZE_TO_FIT));

		class CustomZoomEntry {
			private double value;
			private JRadioButtonMenuItem menuItem;

			private CustomZoomEntry(String label, double value) {
				this.value = value;
				menuItem = new JRadioButtonMenuItem(label,
						viewer.getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM && viewer.getZoomFactor() == value);
				menuItem.addActionListener(e -> {
					viewer.setResizeStrategy(ResizeStrategy.CUSTOM_ZOOM);
					viewer.setZoomFactor(CustomZoomEntry.this.value);
				});
			}

		}
		final CustomZoomEntry[] customZoomEntries = { new CustomZoomEntry("25%", .25), new CustomZoomEntry("50%", .50),
				new CustomZoomEntry("75%", .75), new CustomZoomEntry("100%", 1), new CustomZoomEntry("150%", 1.5),
				new CustomZoomEntry("200%", 2), new CustomZoomEntry("300%", 3), new CustomZoomEntry("500%", 5),
				new CustomZoomEntry("1000%", 10), new CustomZoomEntry("2000%", 20), new CustomZoomEntry("5000%", 50) };
		final ButtonGroup group = new ButtonGroup();
		group.add(zoomOriginalSize);
		group.add(zoomShrinkToFit);
		group.add(zoomResizeToFit);

		zoomMenu.add(zoomOriginalSize);
		zoomMenu.add(zoomShrinkToFit);
		zoomMenu.add(zoomResizeToFit);
		zoomMenu.add(new JSeparator());
		for (CustomZoomEntry cze : customZoomEntries) {
			zoomMenu.add(cze.menuItem);
			group.add(cze.menuItem);
		}

		viewer.addPropertyChangeListener("resizeStrategy", evt -> {
			switch ((ResizeStrategy) evt.getNewValue()) {
			case NO_RESIZE:
				zoomOriginalSize.setSelected(true);
				break;
			case RESIZE_TO_FIT:
				zoomResizeToFit.setSelected(true);
				break;
			case SHRINK_TO_FIT:
				zoomShrinkToFit.setSelected(true);
				break;
			case CUSTOM_ZOOM:
				group.clearSelection();
				for (CustomZoomEntry cze : customZoomEntries) {
					if (cze.value == viewer.getZoomFactor()) {
						cze.menuItem.setSelected(true);
						break;
					}
				}
				break;
			default:
				throw new AssertionError("Unknown resize strategy: " + evt.getNewValue());
			}
		});
		viewer.addPropertyChangeListener("zoomFactor", evt -> {
			if (viewer.getResizeStrategy() == ResizeStrategy.CUSTOM_ZOOM) {
				group.clearSelection();
				for (CustomZoomEntry cze : customZoomEntries) {
					if (cze.value == viewer.getZoomFactor()) {
						cze.menuItem.setSelected(true);
						break;
					}
				}
			}
		});

		/** Save command **/

		JMenuItem saveImageMenuItem = new JMenuItem("Save image...");
		saveImageMenuItem.addActionListener(e -> saveImageAction());

		/** Pixelated zoom toggle **/
		final JCheckBoxMenuItem togglePixelatedZoomItem = new JCheckBoxMenuItem("Pixelated zoom");
		togglePixelatedZoomItem.setState(viewer.isPixelatedZoom());
		viewer.addPropertyChangeListener("pixelatedZoom", evt -> togglePixelatedZoomItem.setState(viewer.isPixelatedZoom()));
		togglePixelatedZoomItem.addActionListener(e -> viewer.setPixelatedZoom(!viewer.isPixelatedZoom()));

		add(toggleStatusBarItem);
		add(zoomMenu);
		add(togglePixelatedZoomItem);
		add(saveImageMenuItem);
	}
	
	private synchronized void saveImageAction() {
		if (saveChooser == null) {
			saveChooser = new JFileChooser();
			saveChooserHelpLabel = new JLabel();
			saveChooserHelpLabel.setText( "<html>If the file name ends<br>with '.png' or '.jpg',<br>then the appropriate<br>format is used.<br>"
										 + "Otherwise '.png' is<br>appended to the name.");
			saveChooserHelpLabel.setFont(saveChooserHelpLabel.getFont().deriveFont(10f));
			saveChooserHelpButton = new JButton("?");
			saveChooserHelpButton.setMargin(new Insets(0, 2, 0, 2));
			saveChooserHelpButton.addActionListener(e -> {
				saveChooser.getAccessory().removeAll();
				saveChooser.getAccessory().add(saveChooserHelpLabel);
				saveChooser.revalidate();
				saveChooser.repaint();
			});
			saveChooserHelpLabel.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					saveChooser.getAccessory().removeAll();
					saveChooser.getAccessory().add(saveChooserHelpButton);
					saveChooser.revalidate();
					saveChooser.repaint();
				}

			});
			saveChooser.setAccessory(new JPanel());
			saveChooser.setDialogTitle("Save image...");

			saveChooser.setFileFilter(new FileNameExtensionFilter("JPG and PNG images", "jpg", "png"));
		}
		// reset to show the help button with every new dialog
		saveChooser.getAccessory().removeAll();
		saveChooser.getAccessory().add(saveChooserHelpButton);
		if (JFileChooser.APPROVE_OPTION == saveChooser.showSaveDialog(viewer.getComponent())) {
			File f = saveChooser.getSelectedFile();
			BufferedImage image = viewer.getImage();
			if (image == null) {
				JOptionPane.showMessageDialog(viewer.getComponent(), "No image", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String name = f.getName().toLowerCase();
				try {
					if (name.endsWith(".jpg")) {
						ImageIO.write(image, "jpg", f);
					} else if (name.endsWith(".png")) {
						ImageIO.write(image, "png", f);
					} else {
						f = new File(f.getPath() + ".png");
						ImageIO.write(image, "png", f);
					}
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(viewer.getComponent(),
							"<html>Cannot write image to " + f.getAbsolutePath() + ":<br>" + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

}
