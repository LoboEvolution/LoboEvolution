/**
 * 
 */
package org.lobobrowser.html.domimpl;

import java.awt.Color;
import java.util.ArrayList;

import org.lobobrowser.html.w3c.CanvasGradient;
import org.lobobrowser.util.gui.ColorFactory;

public class CanvasGradientImpl implements CanvasGradient {

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

	public CanvasGradientImpl(Object x0, Object y0, Object x1, Object y1) {
		fractions = new ArrayList<Float>();
		colors = new ArrayList<Color>();
		setLinearX(new Double(x0.toString()));
		setLinearX1(new Double(y0.toString()));
		setLinearY(new Double(x1.toString()));
		setLinearY1(new Double(y1.toString()));
	}
	
	@Override
	public void addColorStop(String offset, String color) {
		fractions.add(new Float(offset));
		colors.add(ColorFactory.getInstance().getColor(color));

	}

	/**
	 * @return the fractions
	 */
	public ArrayList<Float> getFractions() {
		return fractions;
	}

	/**
	 * @param fractions
	 *            the fractions to set
	 */
	public void setFractions(ArrayList<Float> fractions) {
		this.fractions = fractions;
	}

	/**
	 * @return the colors
	 */
	public ArrayList<Color> getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(ArrayList<Color> colors) {
		this.colors = colors;
	}

	/**
	 * @return the linearX
	 */
	public Double getLinearX() {
		return linearX;
	}

	/**
	 * @param linearX
	 *            the linearX to set
	 */
	public void setLinearX(Double linearX) {
		this.linearX = linearX;
	}

	/**
	 * @return the linearX1
	 */
	public Double getLinearX1() {
		return linearX1;
	}

	/**
	 * @param linearX1
	 *            the linearX1 to set
	 */
	public void setLinearX1(Double linearX1) {
		this.linearX1 = linearX1;
	}

	/**
	 * @return the linearY
	 */
	public Double getLinearY() {
		return linearY;
	}

	/**
	 * @param linearY
	 *            the linearY to set
	 */
	public void setLinearY(Double linearY) {
		this.linearY = linearY;
	}

	/**
	 * @return the linearY1
	 */
	public Double getLinearY1() {
		return linearY1;
	}

	/**
	 * @param linearY1
	 *            the linearY1 to set
	 */
	public void setLinearY1(Double linearY1) {
		this.linearY1 = linearY1;
	}

}
