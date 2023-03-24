/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.welcome;

import com.jtattoo.plaf.lobo.LoboLookAndFeel;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.IWelcomePanel;
import org.loboevolution.config.DesktopConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 * <p>WelcomePanel class.</p>
 */
public class WelcomePanel extends JPanel implements IWelcomePanel, LoboLookAndFeel {

    private static final long serialVersionUID = 1L;

    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(WelcomePanel.class.getName());

    private final LoginButton button;

    private final TextFieldUsername text;

    private Image img = null;

    /**
     * <p>Constructor for WelcomePanel.</p>
     *
     * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
     */
    public WelcomePanel(IBrowserPanel panel) {
        this.text = new TextFieldUsername();
        this.button = new LoginButton(panel, this.text);
        setBackground(background());
        setLayout(null);
        add(this.text);
        add(this.button);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        setOpaque(false);
        if (img == null) {
            File[] files = DesktopConfig.getResourceFolderFiles();
            Random rand = new Random();
            File file = files[rand.nextInt(files.length)];
            try (InputStream is = Files.newInputStream(file.toPath())) {
                img = resize(null, is, (int) getSize().getWidth(), (int) getSize().getHeight());
                g.drawImage(img, 0, 0, null);
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
            super.paintComponent(g);
        } else {
            img = resize(img, null, (int) getSize().getWidth(), (int) getSize().getHeight());
            g.drawImage(img, 0, 0, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void repaint() {
        if (this.text != null) {
            final int x = (int) (getSize().getWidth() * 0.07);
            final int x1 = (int) (getSize().getWidth() * 0.27);
            final int y = 109;
            final int width = (int) (getSize().getWidth() * 0.80);
            final int height = 44;
            this.text.setBounds(x, y, width, height);
            this.button.setBounds(x1, 170, width / 2, height);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getWelocome() {
        return this;
    }


    private BufferedImage resize(Image image, InputStream input, int width, int height) {
        try {
            BufferedImage originalImage = input != null ? ImageIO.read(input) : (BufferedImage) image;
            BufferedImage newResizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newResizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.fillRect(0, 0, width, height);
            Map<RenderingHints.Key, Object> hints = new HashMap<>();
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.addRenderingHints(hints);
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();
            return newResizedImage;

        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
        return null;
    }
}
