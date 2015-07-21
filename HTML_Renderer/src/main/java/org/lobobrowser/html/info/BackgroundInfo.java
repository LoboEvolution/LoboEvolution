/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.info;

import java.awt.Color;
import java.io.Serializable;
import java.net.URL;

/**
 * The Class BackgroundInfo.
 */
public class BackgroundInfo implements Serializable {

	private static final long serialVersionUID = -930859930039262249L;

	/** The background color. */
	private Color backgroundColor;

	/** The background image. */
	private URL backgroundImage;

	/** The background x position absolute. */
	private boolean backgroundXPositionAbsolute;

	/** The background x position. */
	private int backgroundXPosition;

	/** The background y position absolute. */
	private boolean backgroundYPositionAbsolute;

	/** The background y position. */
	private int backgroundYPosition;

	/** The background repeat. */
	public int backgroundRepeat = BR_REPEAT;

	/** The Constant BR_REPEAT. */
	public static final int BR_REPEAT = 0;

	/** The Constant BR_NO_REPEAT. */
	public static final int BR_NO_REPEAT = 1;

	/** The Constant BR_REPEAT_X. */
	public static final int BR_REPEAT_X = 2;

	/** The Constant BR_REPEAT_Y. */
	public static final int BR_REPEAT_Y = 3;

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the backgroundImage
	 */
	public URL getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * @param backgroundImage
	 *            the backgroundImage to set
	 */
	public void setBackgroundImage(URL backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * @return the backgroundXPositionAbsolute
	 */
	public boolean isBackgroundXPositionAbsolute() {
		return backgroundXPositionAbsolute;
	}

	/**
	 * @param backgroundXPositionAbsolute
	 *            the backgroundXPositionAbsolute to set
	 */
	public void setBackgroundXPositionAbsolute(boolean backgroundXPositionAbsolute) {
		this.backgroundXPositionAbsolute = backgroundXPositionAbsolute;
	}

	/**
	 * @return the backgroundXPosition
	 */
	public int getBackgroundXPosition() {
		return backgroundXPosition;
	}

	/**
	 * @param backgroundXPosition
	 *            the backgroundXPosition to set
	 */
	public void setBackgroundXPosition(int backgroundXPosition) {
		this.backgroundXPosition = backgroundXPosition;
	}

	/**
	 * @return the backgroundYPositionAbsolute
	 */
	public boolean isBackgroundYPositionAbsolute() {
		return backgroundYPositionAbsolute;
	}

	/**
	 * @param backgroundYPositionAbsolute
	 *            the backgroundYPositionAbsolute to set
	 */
	public void setBackgroundYPositionAbsolute(boolean backgroundYPositionAbsolute) {
		this.backgroundYPositionAbsolute = backgroundYPositionAbsolute;
	}

	/**
	 * @return the backgroundYPosition
	 */
	public int getBackgroundYPosition() {
		return backgroundYPosition;
	}

	/**
	 * @param backgroundYPosition
	 *            the backgroundYPosition to set
	 */
	public void setBackgroundYPosition(int backgroundYPosition) {
		this.backgroundYPosition = backgroundYPosition;
	}

	/**
	 * @return the backgroundRepeat
	 */
	public int getBackgroundRepeat() {
		return backgroundRepeat;
	}

	/**
	 * @param backgroundRepeat
	 *            the backgroundRepeat to set
	 */
	public void setBackgroundRepeat(int backgroundRepeat) {
		this.backgroundRepeat = backgroundRepeat;
	}

}
