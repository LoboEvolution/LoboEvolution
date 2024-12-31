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

package org.loboevolution.welcome;

import com.jtattoo.plaf.lobo.LoboBackground;
import lombok.extern.slf4j.Slf4j;
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
import java.io.Serial;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>WelcomePanel class.</p>
 */
@Slf4j
public class WelcomePanel extends JPanel implements IWelcomePanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private final LoginButton button;

    private final TextFieldUsername text;

    private Image img = null;

    /**
     * <p>Constructor for WelcomePanel.</p>
     *
     * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
     */
    public WelcomePanel(final IBrowserPanel panel) {
        final LoboBackground lb = new LoboBackground();
        this.text = new TextFieldUsername();
        this.button = new LoginButton(panel, this.text);
        setBackground(lb.getBackground());
        setLayout(null);
        add(this.text);
        add(this.button);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics g) {
        setOpaque(false);
        if (img == null) {
            final File[] files = DesktopConfig.getResourceFolderFiles();
            final Random rand = new Random();
            final File file = files[rand.nextInt(files.length)];
            try (final InputStream is = Files.newInputStream(file.toPath())) {
                img = resize(null, is, (int) getSize().getWidth(), (int) getSize().getHeight());
                g.drawImage(img, 0, 0, null);
            } catch (final IOException e) {
                log.error(e.getMessage(), e);
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


    private BufferedImage resize(final Image image, final InputStream input, final int width, final int height) {
        try {
            final BufferedImage originalImage = input != null ? ImageIO.read(input) : (BufferedImage) image;
            final BufferedImage newResizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g = newResizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.fillRect(0, 0, width, height);
            final Map<RenderingHints.Key, Object> hints = new HashMap<>();
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.addRenderingHints(hints);
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();
            return newResizedImage;

        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
