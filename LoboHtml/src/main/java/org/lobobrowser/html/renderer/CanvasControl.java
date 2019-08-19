/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/

package org.lobobrowser.html.renderer;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.lobo.common.IORoutines;
import org.lobo.info.CanvasInfo;
import org.lobobrowser.html.dom.HTMLCanvasElement;
import org.lobobrowser.html.dom.HTMLImageElement;
import org.lobobrowser.html.domimpl.HTMLCanvasElementImpl;
import org.lobobrowser.http.UserAgentContext;

/**
 * The Class CanvasControl.
 */
public class CanvasControl extends BaseControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(CanvasControl.class.getName());

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/** The list canvas info. */
	private List<CanvasInfo> listCanvasInfo;

	/**
	 * Instantiates a new canvas control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public CanvasControl(HTMLCanvasElementImpl modelNode) {
		super(modelNode);
		width = modelNode.getWidth();
		height = modelNode.getHeight();
		listCanvasInfo = modelNode.getListCanvasInfo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(0, 0, Integer.valueOf(width), Integer.valueOf(height));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < listCanvasInfo.size(); i++) {
			CanvasInfo ci = listCanvasInfo.get(i);

			switch (ci.getMethod()) {
			case HTMLCanvasElement.FILL:
				fill(g2d, ci);
				break;
			case HTMLCanvasElement.FILL_RECT:
				fillRect(g2d, ci);
				break;
			case HTMLCanvasElement.FILL_TEXT:
				fillText(g2d, ci);
				break;
			case HTMLCanvasElement.STROKE:
				stroke(g2d, ci);
				break;
			case HTMLCanvasElement.STROKE_RECT:
				strokeRect(g2d, ci);
				break;
			case HTMLCanvasElement.STROKE_TEXT:
				strokeText(g2d, ci);
				break;
			case HTMLCanvasElement.IMAGE:
				image(g2d, ci);
				break;
			case HTMLCanvasElement.IMAGE_CLIP:
				imageClip(g2d, ci);
				break;
			case HTMLCanvasElement.CLEAR_RECT:
				clearRect(g2d, ci);
				break;
			default:
				break;
			}

		}
	}

	/**
	 * Fill.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	private void fill(Graphics2D g, CanvasInfo ci) {
		g.setTransform(new AffineTransform());
		g.setPaint(ci.getFillPaint());
		g.setTransform(g.getTransform());
		g.fill(ci.getPath());
	}

	/**
	 * Fill rect.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	private void fillRect(Graphics2D g, CanvasInfo ci) {

		g.setComposite(ci.getGlobalAlpha());
		g.setPaint(ci.getFillPaint());
		g.rotate(ci.getRotate());
		g.scale(ci.getScaleX(), ci.getScaleY());
		g.translate(ci.getTranslateX(), ci.getTranslateY());
		g.transform(ci.getAffineTransform());
		g.fillRect(ci.getX(), ci.getY(), ci.getWidth(), ci.getHeight());
	}

	/**
	 * Fill text.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	private void fillText(Graphics2D g, CanvasInfo ci) {
		g.setPaint(ci.getFillPaint());
		g.setFont(ci.getFont());
		g.rotate(ci.getRotate());
		g.scale(ci.getScaleX(), ci.getScaleY());
		g.translate(ci.getTranslateX(), ci.getTranslateY());
		Point2D.Float f = calcTextPos(ci, g);
		g.drawString(ci.getText(), f.x, f.y);
	}

	/**
	 * Stroke.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	/**
	 * Stroke.
	 *
	 * @param g
	 *            the g
	 */
	private void stroke(Graphics2D g, CanvasInfo ci) {

		int intLineCap = BasicStroke.CAP_BUTT;
		int intlineJoin = BasicStroke.JOIN_BEVEL;

		if ("round".equals(ci.getLineCap())) {
			intLineCap = BasicStroke.CAP_ROUND;

		} else if ("square".equals(ci.getLineCap())) {
			intLineCap = BasicStroke.CAP_SQUARE;
		}
		if ("round".equals(ci.getLineJoin())) {
			intlineJoin = BasicStroke.JOIN_ROUND;

		} else if ("miter".equals(ci.getLineJoin())) {
			intlineJoin = BasicStroke.JOIN_MITER;
		}
		g.setStroke(new BasicStroke(ci.getLineWidth(), intLineCap, intlineJoin, ci.getMiterLimit()));
		g.setPaint(ci.getStrokePaint());
		g.draw(ci.getPath());
	}

	/**
	 * Stroke rect.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	private void strokeRect(Graphics2D g, CanvasInfo ci) {
		g.setComposite(ci.getGlobalAlpha());
		g.setPaint(ci.getStrokePaint());
		g.rotate(ci.getRotate());
		g.setStroke(new BasicStroke(ci.getLineWidth()));
		g.scale(ci.getScaleX(), ci.getScaleY());
		g.translate(ci.getTranslateX(), ci.getTranslateY());
		g.transform(ci.getAffineTransform());
		g.drawRect(ci.getX(), ci.getY(), ci.getWidth(), ci.getHeight());
	}

	/**
	 * Stroke text.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	private void strokeText(Graphics2D g, CanvasInfo ci) {
		FontRenderContext frc = new FontRenderContext(null, false, false);
		TextLayout tl = new TextLayout(ci.getText(), ci.getFont(), frc);
		Point2D.Float pos = calcTextPos(ci, g);
		AffineTransform textAt = AffineTransform.getTranslateInstance(pos.x, pos.y);
		textAt.translate(ci.getX(), ci.getY());
		Shape outline = tl.getOutline(textAt);
		g.setPaint(ci.getStrokePaint());
		g.rotate(ci.getRotate());
		g.setStroke(new BasicStroke(2));
		g.scale(ci.getScaleX(), ci.getScaleY());
		g.translate(ci.getTranslateX(), ci.getTranslateY());
		g.draw(outline);

	}

	/**
	 * Image.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	private void image(Graphics2D graphics, CanvasInfo ci) {
		URL u;
		try {
			HTMLImageElement image = (HTMLImageElement)ci.getImage();
			u = new URL(image.getSrc());
			URLConnection con = u.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
			con.setRequestProperty("Accept-Encoding", "gzip");
			Image img = ImageIO.read(IORoutines.getInputStream(con));
			float width = ci.getWidth() / image.getWidth();
			float height = ci.getHeight() / image.getHeight();
			AffineTransform at = new AffineTransform(width, 0, 0, height, ci.getX(), ci.getY());
			graphics.drawImage(img, at, (ImageObserver) null);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Image clip.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	private void imageClip(Graphics2D g, CanvasInfo ci) {
		URL u;
		try {
			HTMLImageElement image = (HTMLImageElement)ci.getImage();
			u = new URL(image.getSrc());
			URLConnection con = u.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
			con.setRequestProperty("Accept-Encoding", "gzip");
			Image img = ImageIO.read(IORoutines.getInputStream(con));
			img = createImage(new FilteredImageSource(img.getSource(),
					new CropImageFilter(ci.getSx(), ci.getSy(), ci.getSw(), ci.getSh())));
			g.clip(new Rectangle2D.Float(ci.getDx(), ci.getDy(), ci.getDw(), ci.getDh()));
			g.drawImage(img, ci.getDx(), ci.getDy(), ci.getDw(), ci.getDh(), this);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Clear rect.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 */
	private void clearRect(Graphics2D g, CanvasInfo ci) {
		g.clearRect(ci.getX(), ci.getY(), ci.getWidth(), ci.getHeight());
	}

	private Point2D.Float calcTextPos(CanvasInfo ci, Graphics2D graphics) {

		FontMetrics metrics = graphics.getFontMetrics();

		int x = ci.getX();
		int y = ci.getY();
		String bs = ci.getBaseline();
		String ta = ci.getTextAlign();

		if ("center".equals(ta)) {
			x = x - metrics.stringWidth(ci.getText()) / 2;
		} else if ("right".equals(ta)) {
			x = x - metrics.stringWidth(ci.getText());
		}
		
		switch (bs) {
		case "baseline":
			y = y - metrics.getLeading() + metrics.getAscent();
			break;
		case "top":
			y = y - metrics.getLeading();
			break;
		case "middle":
			y = y - metrics.getLeading() - metrics.getAscent() / 2;
			break;
		case "bottom":
		case "text-bottom":
			y = y - metrics.getHeight();
			break;
		default:
			y = y + metrics.getLeading() + metrics.getAscent();
			break;
		}		

		return new Point2D.Float(x, y);
	}
}