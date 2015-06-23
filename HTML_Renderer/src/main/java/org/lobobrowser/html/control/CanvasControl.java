/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.lobobrowser.html.domimpl.HTMLCanvasElementImpl;
import org.lobobrowser.html.info.CanvasInfo;
import org.lobobrowser.html.w3c.HTMLCanvasElement;

/**
 * The Class CanvasControl.
 */
public class CanvasControl extends BaseControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(CanvasControl.class
			.getName());

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
		gradient(g, ci, ci.getFillStyle());
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
		gradient(g, ci, ci.getFillStyle());
		g.rotate(ci.getRotate());
		g.scale(ci.getScaleX(), ci.getScaleY());
		g.translate(ci.getTranslateX(), ci.getTranslateY());
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
		gradient(g, ci, ci.getFillStyle());
		g.setFont(ci.getFont());
		g.rotate(ci.getRotate());
		g.scale(ci.getScaleX(), ci.getScaleY());
		g.translate(ci.getTranslateX(), ci.getTranslateY());
		g.drawString(ci.getText(), ci.getX(), ci.getY());

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
		g.setStroke(new BasicStroke(ci.getLineWidth(), intLineCap, intlineJoin,
				ci.getMiterLimit()));
		gradient(g, ci, ci.getStrokeStyle());
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
		gradient(g, ci, ci.getStrokeStyle());
		g.rotate(ci.getRotate());
		g.setStroke(new BasicStroke(ci.getLineWidth()));
		g.scale(ci.getScaleX(), ci.getScaleY());
		g.translate(ci.getTranslateX(), ci.getTranslateY());
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
		AffineTransform textAt = new AffineTransform();
		textAt.translate(ci.getX(), ci.getY());
		Shape outline = tl.getOutline(textAt);
		gradient(g, ci, ci.getStrokeStyle());
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
			Image img = ImageIO.read(u);

			g.drawImage(img, ci.getX(), ci.getY(), ci.getWidth(),
					ci.getHeight(), this);
			g.finalize();
		} catch (Exception e) {
			logger.severe(e.getLocalizedMessage());
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
			Image img = ImageIO.read(u);
			img = createImage(new FilteredImageSource(img.getSource(),
					new CropImageFilter(ci.getSx(), ci.getSy(), ci.getSw(),
							ci.getSh())));

			g.drawImage(img, ci.getDx(), ci.getDy(), ci.getDw(), ci.getDh(),
					this);

			g.finalize();
		} catch (Exception e) {
			logger.severe(e.getLocalizedMessage());
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

	/**
	 * Gradient.
	 *
	 * @param g
	 *            the g
	 * @param ci
	 *            the ci
	 * @param color
	 *            the color
	 */
	private void gradient(Graphics2D g, CanvasInfo ci, Color color) {

		if (ci.getLinearGradient() != null) {
			g.setPaint(ci.getLinearGradient());
		} else {
			g.setPaint(color);
		}
	}
}
/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.lobobrowser.html.domimpl.HTMLCanvasElementImpl;
import org.lobobrowser.html.info.CanvasInfo;
import org.lobobrowser.html.w3c.HTMLCanvasElement;

/**
 * The Class CanvasControl.
 */
public class CanvasControl extends BaseControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/** The list rect values. */
	private ArrayList<CanvasInfo> listRectValues;

	/** The list stroke rect values. */
	private ArrayList<CanvasInfo> listStrokeRectValues;

	/** The list text values. */
	private ArrayList<CanvasInfo> listTextValues;

	/** The list stroke text values. */
	private ArrayList<CanvasInfo> listStrokeTextValues;

	/** The fractions. */
	private ArrayList<Float> fractions;

	/** The colors. */
	private ArrayList<Color> colors;

	/** The linear values. */
	private CanvasInfo linearValues;

	/** The path. */
	private GeneralPath path;
	
	/** The canvas info. */
	private CanvasInfo canvasInfo;
	

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
		listRectValues = modelNode.getListRectValues();
		listTextValues = modelNode.getListTextValues();
		listStrokeTextValues = modelNode.getListStrokeTextValues();
		listStrokeRectValues = modelNode.getListStrokeRectValues();
		path = modelNode.getPath();
		linearValues = modelNode.getLinearValues();
		colors = modelNode.getColors();
		fractions = modelNode.getFractions();
		canvasInfo = modelNode.getCanvasInfo();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(0, 0, new Integer(width), new Integer(height));
		
		switch (canvasInfo.getMethod()) {
		case HTMLCanvasElement.FILL:
			fill(g2d);
			break;
		case HTMLCanvasElement.FILL_RECT:
			fillRect(g2d);
			break;
		case HTMLCanvasElement.FILL_TEXT:
			fillText(g2d);
			break;
		case HTMLCanvasElement.STROKE:
			stroke(g2d);
			break;
		case HTMLCanvasElement.STROKE_RECT:
			strokeRect(g2d);
			break;
		case HTMLCanvasElement.STROKE_TEXT:
			strokeText(g2d);
			break;
		case HTMLCanvasElement.IMAGE:
			image(g2d);
			break;
		case HTMLCanvasElement.IMAGE_CLIP:
			imageClip(g2d);
			break;
		default:
			break;
		}
	}

	/**
	 * Fill.
	 *
	 * @param g
	 *            the g
	 */
	private void fill(Graphics2D g) {
		if (path != null) {
			g.setColor(canvasInfo.getFillStyle());
			paint(g);
			g.fill(path);
		}
	}

	/**
	 * Fill rect.
	 *
	 * @param g
	 *            the g
	 */
	private void fillRect(Graphics2D g) {
		for (int i = 0; i < listRectValues.size(); i++) {
			CanvasInfo val = listRectValues.get(i);
			g.setColor(val.getFillStyle());
			g.setComposite(opacity(val.getGlobalAlpha()));
			paint(g);
			g.rotate(val.getRotate());
			g.scale(val.getScaleX(), val.getScaleY());
			g.translate(val.getTranslateX(), val.getTranslateY());
			g.fillRect(val.getX(), val.getY(), val.getWidth(), val.getHeight());
		}
	}

	/**
	 * Fill text.
	 *
	 * @param g
	 *            the g
	 */
	private void fillText(Graphics2D g) {
		for (int i = 0; i < listTextValues.size(); i++) {
			CanvasInfo val = listTextValues.get(i);
			g.setColor(val.getFillStyle());
			g.setFont(val.getFont());
			paint(g);
			g.rotate(val.getRotate());
			g.scale(val.getScaleX(), val.getScaleY());
			g.translate(val.getTranslateX(), val.getTranslateY());
			g.drawString(val.getText(), val.getX(), val.getY());
		}
	}

	/**
	 * Stroke.
	 *
	 * @param g
	 *            the g
	 */
	private void stroke(Graphics2D g) {
		if (path != null) {
			int intLineCap = BasicStroke.CAP_BUTT;
			int intlineJoin = BasicStroke.JOIN_BEVEL;

			if ("round".equals(canvasInfo.getLineCap())) {
				intLineCap = BasicStroke.CAP_ROUND;

			} else if ("square".equals(canvasInfo.getLineCap())) {
				intLineCap = BasicStroke.CAP_SQUARE;
			}
			if ("round".equals(canvasInfo.getLineJoin())) {
				intlineJoin = BasicStroke.JOIN_ROUND;

			} else if ("miter".equals(canvasInfo.getLineJoin())) {
				intlineJoin = BasicStroke.JOIN_MITER;
			}
			
			g.setStroke(new BasicStroke(canvasInfo.getLineWidth(), intLineCap, intlineJoin, canvasInfo.getMiterLimit()));
			g.setColor(canvasInfo.getStrokeStyle());
			paint(g);
			g.draw(path);
		}
	}

	/**
	 * Stroke rect.
	 *
	 * @param g
	 *            the g
	 */
	private void strokeRect(Graphics2D g) {
		for (int i = 0; i < listStrokeRectValues.size(); i++) {
			CanvasInfo val = listStrokeRectValues.get(i);
			g.setComposite(opacity(val.getGlobalAlpha()));
			g.setStroke(new BasicStroke(val.getLineWidth()));
			g.setColor(val.getStrokeStyle());
			paint(g);
			g.rotate(val.getRotate());
			g.scale(val.getScaleX(), val.getScaleY());
			g.translate(val.getTranslateX(), val.getTranslateY());
			g.drawRect(val.getX(), val.getY(), val.getWidth(), val.getHeight());
		}
	}

	/**
	 * Stroke text.
	 *
	 * @param g
	 *            the g
	 */
	private void strokeText(Graphics2D g) {
		for (int i = 0; i < listStrokeTextValues.size(); i++) {
			CanvasInfo val = listStrokeTextValues.get(i);
			FontRenderContext frc = new FontRenderContext(null, false, false);

			TextLayout tl = new TextLayout(val.getText(), val.getFont(), frc);
			AffineTransform textAt = new AffineTransform();
			textAt.translate(val.getX(), val.getY());
			Shape outline = tl.getOutline(textAt);
			g.setColor(val.getStrokeStyle());
			g.setFont(val.getFont());
			paint(g);
			g.rotate(val.getRotate());
			g.scale(val.getScaleX(), val.getScaleY());
			g.translate(val.getTranslateX(), val.getTranslateY());
			BasicStroke wideStroke = new BasicStroke(2);
			g.setStroke(wideStroke);
			g.draw(outline);
		}
	}
	
	
	/**
	 * Image.
	 *
	 * @param g the g
	 */
	private void image(Graphics2D g) {
		URL u;
		try {
			u = new URL(canvasInfo.getImage().getSrc());
			Image img = ImageIO.read(u);
			
			g.drawImage(img,canvasInfo.getX(),canvasInfo.getY(),canvasInfo.getWidth(), canvasInfo.getHeight(),this);		
			g.finalize();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Image clip.
	 *
	 * @param g the g
	 */
	private void imageClip(Graphics2D g) {
		URL u;
		try {
			u = new URL(canvasInfo.getImage().getSrc());
			Image img = ImageIO.read(u);
			img = createImage(new FilteredImageSource(img.getSource(),
					new CropImageFilter(canvasInfo.getSx(), canvasInfo.getSy(),
							canvasInfo.getSw(), canvasInfo.getSh())));

			g.drawImage(img, canvasInfo.getDx(), canvasInfo.getDy(),
					canvasInfo.getDw(), canvasInfo.getDh(), this);

			g.finalize();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Paint.
	 *
	 * @param g
	 *            the g
	 */
	private void paint(Graphics2D g) {

		if (colors != null && linearValues.getLinearX() != null) {

			float[] floatArray = new float[fractions.size()];
			int i = 0;

			for (Float f : fractions) {
				floatArray[i++] = (f != null ? f : Float.NaN);
			}

			Color[] colorArray = new Color[colors.size()];
			int a = 0;

			for (Color c : colors) {
				colorArray[a++] = c;
			}

			Arrays.sort(floatArray);

			LinearGradientPaint paint = new LinearGradientPaint(linearValues
					.getLinearX().floatValue(), linearValues.getLinearY()
					.floatValue(), linearValues.getLinearX1().floatValue(),
					linearValues.getLinearY1().floatValue(), floatArray,
					colorArray);
			g.setPaint(paint);
		}
	}

	/**
	 * Opacity.
	 *
	 * @return the alpha composite
	 */
	private AlphaComposite opacity(Double globalAlpha) {
		return AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				globalAlpha.floatValue());
	}
}