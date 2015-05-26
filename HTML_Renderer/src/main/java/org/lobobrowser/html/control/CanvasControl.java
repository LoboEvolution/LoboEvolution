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
import java.awt.LinearGradientPaint;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Arrays;

import org.lobobrowser.html.domimpl.HTMLCanvasElementImpl;
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
	private ArrayList<int[]> listRectValues;
	
	/** The list stroke rect values. */
	private ArrayList<int[]> listStrokeRectValues;
	
	/** The list text values. */
	private ArrayList<Object[]> listTextValues;
	
	private float[] fractions;
	private Color[] colors;
	private Double[] linearValues;

	/** The method. */
	private int method;
	
	private GeneralPath path;
	
	/** The method. */
	private int lineWidth;
	
	private Color color;

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
		listStrokeRectValues = modelNode.getListStrokeRectValues();
		method = modelNode.getMethod();
		path = modelNode.getPath();
		lineWidth = modelNode.getLineWidth();
		color = modelNode.getColor();
		linearValues = modelNode.getLinearValues();
		colors = modelNode.getColors();
		fractions = modelNode.getFractions();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(0, 0, new Integer(width), new Integer(height));

		switch (method) {
		case HTMLCanvasElement.FILL_RECT:
			fillRect(g2d);
			break;
		case HTMLCanvasElement.FILL_TEXT:
			fillText(g2d);
			break;
		case HTMLCanvasElement.STROKE_RECT:
			strokeRect(g2d);
			break;
		case HTMLCanvasElement.STROKE:
			stroke(g2d);
			break;
		default:
			break;
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
			paint(g);
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
			paint(g);
			g.drawString((String)val[0], (Integer)val[1], (Integer)val[2]);
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
			g.setStroke(new BasicStroke(val[4]));
			g.setColor(color);
			paint(g);
			g.drawRect(val[0], val[1], val[2],val[3]);
		}
	}
	
	private void stroke(Graphics2D g) {
		if (path != null) {
			g.setStroke(new BasicStroke(lineWidth));
			g.setColor(color);
			paint(g);
			g.draw(path);
		}
	}
	
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
	
}