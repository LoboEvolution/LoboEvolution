/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.menu.tools.screen;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboLookAndFeel;
import com.jtattoo.plaf.lobo.LoboPanel;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreviewFrame extends JFrame implements LoboLookAndFeel {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(PreviewFrame.class.getName());

    private static final String TITLE = "ScreenShot";

    private final CapturePane pane;

    private final BrowserFrame frame;

    private ImageIcon image;

    private LoboLabel imageDisplay;

    public PreviewFrame(CapturePane pane, BrowserFrame frame) {
        this.pane = pane;
        this.frame = frame;
        initComponents();
        initLayout();
    }

    /**
     * intialize frame and sets button listeners
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        imageDisplay = new LoboLabel("");
        imageDisplay.setHorizontalAlignment(JLabel.CENTER);
        LoboPanel buttonPanel = new LoboPanel();

        LoboButton redoButton = new LoboButton();
        redoButton.setText("Redo");
        redoButton.addActionListener(e -> redoPicture());

        LoboButton saveButton = new LoboButton();
        saveButton.setText("Save");
        saveButton.addActionListener(e -> saveImage());

        buttonPanel.add(redoButton);
        buttonPanel.add(saveButton);

        add(imageDisplay, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void initLayout() {
        setTitle(TITLE);
        setBackground(background());
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(pane);
        setVisible(true);
    }

    /**
     * reopens captureFrame and closes preview frame
     */
    private void redoPicture() {
        setVisible(false);
        pane.setVisible(true);
    }

    /**
     * Converts the image to a buffered image to save to a file
     */
    public void saveImage() {
        try {
            final ToolBar toolbar = this.frame.getToolbar();
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            final FileNameExtensionFilter png = new FileNameExtensionFilter("Portable Network Graphics", ".png");
            fileChooser.setFileFilter(png);
            final int returnValue = fileChooser.showSaveDialog(toolbar);
            File selectedFile = null;
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) {
                    final int response = JOptionPane.showConfirmDialog(null, "Overwrite existing file?",
                            "Confirm Overwrite", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
            }
            final File sFile = new File(selectedFile+".png");
            final BufferedImage buffered = (BufferedImage) image.getImage();
            ImageIO.write(buffered, "png", new File(sFile.getAbsolutePath()));

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            setVisible(false);
        }
    }

    /**
     * sets image to preview and packs the components to fit
     */
    public void setImage(ImageIcon image) {
        this.image = image;
        imageDisplay.setIcon(image);
        pack();
    }
}