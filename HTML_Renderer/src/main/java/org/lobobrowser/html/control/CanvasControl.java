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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Arrays;

import org.lobobrowser.html.domimpl.HTMLCanvasElementImpl;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.w3c.HTMLCanvasElement;
import org.lobobrowser.util.gui.FontFactory;

/**
 * The Class CanvasControl.
 */
public class CanvasControl extends BaseControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant FONT_FACTORY. */
    private static final FontFactory FONT_FACTORY = FontFactory.getInstance();
	
	 /** The Constant DEFAULT_FONT. */
    private static final Font DEFAULT_FONT = FONT_FACTORY.getFont(
            Font.SANS_SERIF, null, null, null, HtmlValues.DEFAULT_FONT_SIZE,
            null, null);

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/** The list rect values. */
	private ArrayList<int[]> listRectValues;
	
	/** The list stroke rect values. */
	private ArrayList<int[]> listStrokeRectValues;
	
	/** The list text values. */
	private ArrayList<Object[]> listTextValues;
	
	/** The list stroke text values. */
	private ArrayList<Object[]> listStrokeTextValues;
	
	/** The fractions. */
	private float[] fractions;
	
	/** The colors. */
	private Color[] colors;
	
	/** The linear values. */
	private Double[] linearValues;

	/** The method. */
	private int method;
	
	/** The path. */
	private GeneralPath path;
	
	/** The method. */
	private int lineWidth;
	
	/** The color. */
	private Color color;
	
	/** The font. */
	private Font font;
	
	/** The global alpha. */
	private Double globalAlpha;
	
	/** The rotate. */
	private Double rotate;
	
	/** The Scale x. */
	private int scaleX;
	
	/** The Scale Y. */
	private int scaleY;
	
	/** The Translate x. */
	private int translateX;
		
	/** The Translate Y. */
	private int translateY;
	
	/** The Line Cap. */
	private String lineCap; 
	
	/** The Line Join. */
	private String lineJoin;
	
	/** The Miter Limit. */
	private int miterLimit;
	

	/**
	 * Instantiates a new canvas control.
	 *
	 * @param modelNode the model node
	 */
	public CanvasControl(HTMLCanvasElementImpl modelNode) {
		super(modelNode);
		width = modelNode.getWidth();
		height = modelNode.getHeight();
		listRectValues = modelNode.getListRectValues();
		listTextValues = modelNode.getListTextValues();
		listStrokeTextValues = modelNode.getListStrokeTextValues();
		listStrokeRectValues = modelNode.getListStrokeRectValues();
		method = modelNode.getMethod();
		path = modelNode.getPath();
		lineWidth = modelNode.getLineWidth();
		lineCap = modelNode.getLineCap();
		lineJoin = modelNode.getLineJoin();
		miterLimit = modelNode.getMiterLimit();
		color = modelNode.getColor();
		font = modelNode.getFont();
		linearValues = modelNode.getLinearValues();
		colors = modelNode.getColors();
		fractions = modelNode.getFractions();
		globalAlpha = modelNode.getGlobalAlpha();
		rotate = modelNode.getRotate();
		scaleX = modelNode.getScaleX();
		scaleY = modelNode.getScaleY();
		translateX = modelNode.getTranslateX();
		translateY = modelNode.getTranslateY();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(0, 0, new Integer(width), new Integer(height));

		switch (method) {
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
		default:
			break;
		}
	}
	
	
	/**
	 * Fill.
	 *
	 * @param g the g
	 */
	private void fill(Graphics2D g) {
		if (path != null) {
			g.setColor(color);
			paint(g);
			g.fill(path);
		}
	}

	/**
	 * Fill rect.
	 *
	 * @param g the g
	 */
	private void fillRect(Graphics2D g) {
		for (int i = 0; i < listRectValues.size(); i++) {
			int[] val = listRectValues.get(i);
			g.setColor(color);
			g.setComposite(opacity());
			paint(g);
			g.rotate(rotate);
			g.scale(scaleX, scaleY);
			g.translate(translateX, translateY);
			g.fillRect(val[0], val[1], val[2], val[3]);
		}
	}
	
	/**
	 * Fill text.
	 *
	 * @param g the g
	 */
	private void fillText(Graphics2D g) {
		for (int i = 0; i < listTextValues.size(); i++) {
			Object[] val = listTextValues.get(i);
			g.setColor(color);
			font(g);
			paint(g);
			g.rotate(rotate);
			g.scale(scaleX, scaleY);
			g.translate(translateX, translateY);
			g.drawString((String)val[0], (Integer)val[1], (Integer)val[2]);
		}
	}
	
	/**
	 * Stroke.
	 *
	 * @param g the g
	 */
	private void stroke(Graphics2D g) {
		if (path != null) {
			int intLineCap = BasicStroke.CAP_BUTT;
			int intlineJoin = BasicStroke.JOIN_BEVEL;
			
			if ("round".equals(lineCap)) {
				intLineCap = BasicStroke.CAP_ROUND;

			} else if ("square".equals(lineCap)) {
				intLineCap = BasicStroke.CAP_SQUARE;
			}
			if ("round".equals(lineJoin)) {
				intlineJoin = BasicStroke.JOIN_ROUND;

			} else if ("miter".equals(lineJoin)) {
				intlineJoin = BasicStroke.JOIN_MITER;
			}
			
			g.setStroke(new BasicStroke(lineWidth,intLineCap,intlineJoin,miterLimit));
			g.setColor(color);
			paint(g);
			g.draw(path);
		}
	}
	
	/**
	 * Stroke rect.
	 *
	 * @param g the g
	 */
	private void strokeRect(Graphics2D g) {
		for (int i = 0; i < listStrokeRectValues.size(); i++) {
			int[] val = listStrokeRectValues.get(i);
			g.setComposite(opacity());
			g.setStroke(new BasicStroke(val[4]));
			g.setColor(color);
			paint(g);
			
			g.rotate(rotate);
			g.scale(scaleX, scaleY);
			g.translate(translateX, translateY);
			g.drawRect(val[0], val[1], val[2],val[3]);
		}
	}
	
	/**
	 * Stroke text.
	 *
	 * @param g the g
	 */
	private void strokeText(Graphics2D g) {
		for (int i = 0; i < listStrokeTextValues.size(); i++) {
			Object[] val = listStrokeTextValues.get(i);
			FontRenderContext frc = new FontRenderContext(null, false, false);

			TextLayout tl = new TextLayout((String) val[0], font, frc);
			AffineTransform textAt = new AffineTransform();
			textAt.translate((Integer) val[1], (Integer) val[2]);
			Shape outline = tl.getOutline(textAt);
			g.setColor(color);
			font(g);
			paint(g);
			g.rotate(rotate);
			g.scale(scaleX, scaleY);
			g.translate(translateX, translateY);
			BasicStroke wideStroke = new BasicStroke(2);
			g.setStroke(wideStroke);
			g.draw(outline);
		}
	}
	
	
	/**
	 * Paint.
	 *
	 * @param g the g
	 */
	private void paint(Graphics2D g) {

		if (colors != null) {
			Arrays.sort(fractions);
			LinearGradientPaint paint = new LinearGradientPaint(
					linearValues[0].floatValue(), linearValues[1].floatValue(),
					linearValues[2].floatValue(), linearValues[3].floatValue(),
					fractions, colors);
			g.setPaint(paint);
		}
	}
	
	/**
	 * Opacity.
	 *
	 * @return the alpha composite
	 */
	private AlphaComposite opacity(){
		return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalAlpha.floatValue());
	} 
	
	/**
	 * Font.
	 *
	 * @param g the g
	 */
	private void font(Graphics2D g){
		if(font!= null){
			g.setFont(font);
		}else{
			g.setFont(DEFAULT_FONT);
		}
	}
}