/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.dom.input;

import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.net.HttpNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>InputImage class.</p>
 */
public class InputImage {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(InputImage.class.getName());

	private final HTMLInputElementImpl modelNode;

	/**
	 * <p>Constructor for InputImage.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputImage(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		TimingInfo info = new TimingInfo();
		BufferedImage image = toBufferedImage(HttpNetwork.getImage(modelNode, info, true));
		final HtmlRendererContext htmlRendererContext = modelNode.getHtmlRendererContext();
		final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
		htmlPanel.getBrowserPanel().getTimingList.add(info);
		JLabel wIcon = new JLabel(new ImageIcon(image));
		ic.add(wIcon);
	}

	private BufferedImage toBufferedImage(Image image) {
		image = new ImageIcon(image).getImage();
		boolean hasAlpha = hasAlpha(image);
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			HTMLDocumentImpl doc = (HTMLDocumentImpl)modelNode.getDocumentNode();
			final int dw = HtmlValues.getPixelSize(modelNode.getAttribute("width"), null, doc.getDefaultView(), image.getWidth(null), 0);
			final int dh = HtmlValues.getPixelSize(modelNode.getAttribute("height"), null, doc.getDefaultView(), image.getHeight(null), 0);

			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(dw, dh, transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	private static boolean hasAlpha(Image image) {
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage) image;
			return bimage.getColorModel().hasAlpha();
		}

		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
}
