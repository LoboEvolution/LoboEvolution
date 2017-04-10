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
package org.lobobrowser.html.domimpl;

import java.awt.Color;
import java.util.ArrayList;

import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.w3c.html.CanvasGradient;

/**
 * The Class DOMCanvasGradientImpl.
 */
public class DOMCanvasGradientImpl implements CanvasGradient {

	/** The fractions. */
	private ArrayList<Float> fractions;

	/** The colors. */
	private ArrayList<Color> colors;

	/** The linear x. */
	private Double linearX;

	/** The linear x1. */
	private Double linearX1;

	/** The linear y. */
	private Double linearY;

	/** The linear y1. */
	private Double linearY1;
	
	/** The r1. */
	private Double r1;
	
	/** The r2. */
	private Double r2;

	public DOMCanvasGradientImpl(Object x0, Object y0, Object x1, Object y1) {
		fractions = new ArrayList<Float>();
		colors = new ArrayList<Color>();
		setLinearX(new Double(x0.toString()));
		setLinearX1(new Double(y0.toString()));
		setLinearY(new Double(x1.toString()));
		setLinearY1(new Double(y1.toString()));
	}
	
	public DOMCanvasGradientImpl(Object x0, Object y0, Object x1, Object y1, Object r1, Object r2) {
		fractions = new ArrayList<Float>();
		colors = new ArrayList<Color>();
		setLinearX(new Double(x0.toString()));
		setLinearX1(new Double(y0.toString()));
		setLinearY(new Double(x1.toString()));
		setLinearY1(new Double(y1.toString()));
		setR1(new Double(r1.toString()));
		setR2(new Double(r2.toString()));
	}
	
	@Override
	public void addColorStop(String offset, String color) {
		fractions.add(new Float(offset));
		colors.add(ColorFactory.getInstance().getColor(color));

	}

	/**
	 * Gets the fractions.
	 *
	 * @return the fractions
	 */
	public ArrayList<Float> getFractions() {
		return fractions;
	}

	/**
	 * Sets the fractions.
	 *
	 * @param fractions
	 *            the new fractions
	 */
	public void setFractions(ArrayList<Float> fractions) {
		this.fractions = fractions;
	}

	/**
	 * Gets the colors.
	 *
	 * @return the colors
	 */
	public ArrayList<Color> getColors() {
		return colors;
	}

	/**
	 * Sets the colors.
	 *
	 * @param colors
	 *            the new colors
	 */
	public void setColors(ArrayList<Color> colors) {
		this.colors = colors;
	}

	/**
	 * Gets the linear x.
	 *
	 * @return the linear x
	 */
	public Double getLinearX() {
		return linearX;
	}

	/**
	 * Sets the linear x.
	 *
	 * @param linearX
	 *            the new linear x
	 */
	public void setLinearX(Double linearX) {
		this.linearX = linearX;
	}

	/**
	 * Gets the linear x1.
	 *
	 * @return the linear x1
	 */
	public Double getLinearX1() {
		return linearX1;
	}

	/**
	 * Sets the linear x1.
	 *
	 * @param linearX1
	 *            the new linear x1
	 */
	public void setLinearX1(Double linearX1) {
		this.linearX1 = linearX1;
	}

	/**
	 * Gets the linear y.
	 *
	 * @return the linear y
	 */
	public Double getLinearY() {
		return linearY;
	}

	/**
	 * Sets the linear y.
	 *
	 * @param linearY
	 *            the new linear y
	 */
	public void setLinearY(Double linearY) {
		this.linearY = linearY;
	}

	/**
	 * Gets the linear y1.
	 *
	 * @return the linear y1
	 */
	public Double getLinearY1() {
		return linearY1;
	}

	/**
	 * Sets the linear y1.
	 *
	 * @param linearY1
	 *            the new linear y1
	 */
	public void setLinearY1(Double linearY1) {
		this.linearY1 = linearY1;
	}

	/**
	 * @return the r1
	 */
	public Double getR1() {
		return r1;
	}

	/**
	 * @param r1 the r1 to set
	 */
	public void setR1(Double r1) {
		this.r1 = r1;
	}
	
	/**
	 * @return the r2
	 */
	public Double getR2() {
		return r2;
	}

	/**
	 * @param r2 the r2 to set
	 */
	public void setR2(Double r2) {
		this.r2 = r2;
	}
}