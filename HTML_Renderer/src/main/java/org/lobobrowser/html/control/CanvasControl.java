/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

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
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.domimpl.HTMLCanvasElementImpl;
import org.lobobrowser.html.info.CanvasInfo;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.w3c.html.HTMLCanvasElement;

/**
 * The Class CanvasControl.
 */
public class CanvasControl extends BaseControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CanvasControl.class.getName());

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/** The list canvas info. */
	private ArrayList<CanvasInfo> listCanvasInfo;

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
		g2d.drawRect(0, 0, new Integer(width), new Integer(height));
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
		g.setTransform(g.getTransform());
		g.setPaint(ci.getFillPaint());
		g.fill(ci.getPath());
		g.setTransform(new AffineTransform());
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
		Point2D.Float pos = calcTextPos(ci,g);
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
	private void image(Graphics2D g, CanvasInfo ci) {
		URL u;
		try {

			u = new URL(ci.getImage().getSrc());
			URLConnection con = u.openConnection();
            con.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);
			Image img = ImageIO.read(con.getInputStream());
			g.drawImage(img, ci.getX(), ci.getY(), ci.getWidth(), ci.getHeight(), this);
			g.finalize();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
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
			u = new URL(ci.getImage().getSrc());
			URLConnection con = u.openConnection();
            con.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);
			Image img = ImageIO.read(con.getInputStream());
			
			img = createImage(new FilteredImageSource(img.getSource(),
					new CropImageFilter(ci.getSx(), ci.getSy(), ci.getSw(), ci.getSh())));

			g.drawImage(img, ci.getDx(), ci.getDy(), ci.getDw(), ci.getDh(), this);

			g.finalize();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
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

		if ("baseline".equals(bs)) {
			y = y - metrics.getLeading() + metrics.getAscent();
		} else if ("top".equals(bs)) {
			y = y - metrics.getLeading();
		} else if ("middle".equals(bs)) {
			y = y - metrics.getLeading() - metrics.getAscent() / 2;
		} else if ("bottom".equals(bs) || "text-bottom".equals(bs)) {
			y = y - metrics.getHeight();
		} else {
			y = y + metrics.getLeading() + metrics.getAscent();
		}

		return new Point2D.Float(x, y);
	}
}