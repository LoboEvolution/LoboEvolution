/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.menu.tools.screen;

import com.jtattoo.plaf.lobo.LoboBackground;
import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboPanel;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * <p>PreviewFrame class.</p>
 */
@Slf4j
public class PreviewFrame extends JFrame {

    private static final String TITLE = "ScreenShot";

    private final CapturePane pane;

    private final BrowserFrame frame;

    private ImageIcon image;

    private LoboLabel imageDisplay;

    public PreviewFrame(final CapturePane pane, final BrowserFrame frame) {
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
        final LoboPanel buttonPanel = new LoboPanel();

        final LoboButton redoButton = new LoboButton();
        redoButton.setText("Redo");
        redoButton.addActionListener(e -> redoPicture());

        final LoboButton saveButton = new LoboButton();
        saveButton.setText("Save");
        saveButton.addActionListener(e -> saveImage());

        buttonPanel.add(redoButton);
        buttonPanel.add(saveButton);

        add(imageDisplay, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void initLayout() {
        final LoboBackground lb = new LoboBackground();
        setTitle(TITLE);
        setBackground(lb.getBackground());
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            setVisible(false);
        }
    }

    /**
     * sets image to preview and packs the components to fit
     */
    public void setImage(final ImageIcon image) {
        this.image = image;
        imageDisplay.setIcon(image);
        pack();
    }
}