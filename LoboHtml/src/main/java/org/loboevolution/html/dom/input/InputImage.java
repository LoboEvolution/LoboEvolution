package org.loboevolution.html.dom.input;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>InputImage class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputImage {

	private BufferedImage image;
	
	private HTMLInputElementImpl modelNode;

	/**
	 * <p>Constructor for InputImage.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputImage(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		image = toBufferedImage(HttpNetwork.getImage(modelNode.getSrc(), modelNode.getOwnerDocument().getBaseURI()));
		JLabel wIcon = new JLabel(new ImageIcon(image));
		ic.add(wIcon);
	}

	private BufferedImage toBufferedImage(Image image) {
		image = new ImageIcon(image).getImage();
		boolean hasAlpha = hasAlpha(image);
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			final int dw = HtmlValues.getPixelSize(modelNode.getAttribute("width"), null, image.getWidth(null), 0);
			final int dh = HtmlValues.getPixelSize(modelNode.getAttribute("height"), null, image.getHeight(null), 0);

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
			e.printStackTrace();
		}

		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
}
