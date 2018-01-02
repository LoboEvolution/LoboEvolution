/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.html.info;

import java.awt.Color;
import java.io.Serializable;
import java.net.URL;

/**
 * The Class BackgroundInfo.
 */
public class BackgroundInfo implements Serializable {

	/** The Constant serialVersionUID. */
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
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets the background color.
	 *
	 * @param backgroundColor
	 *            the new background color
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Gets the background image.
	 *
	 * @return the background image
	 */
	public URL getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * Sets the background image.
	 *
	 * @param backgroundImage
	 *            the new background image
	 */
	public void setBackgroundImage(URL backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * Checks if is background x position absolute.
	 *
	 * @return the background x position absolute
	 */
	public boolean isBackgroundXPositionAbsolute() {
		return backgroundXPositionAbsolute;
	}

	/**
	 * Sets the background x position absolute.
	 *
	 * @param backgroundXPositionAbsolute
	 *            the new background x position absolute
	 */
	public void setBackgroundXPositionAbsolute(boolean backgroundXPositionAbsolute) {
		this.backgroundXPositionAbsolute = backgroundXPositionAbsolute;
	}

	/**
	 * Gets the background x position.
	 *
	 * @return the background x position
	 */
	public int getBackgroundXPosition() {
		return backgroundXPosition;
	}

	/**
	 * Sets the background x position.
	 *
	 * @param backgroundXPosition
	 *            the new background x position
	 */
	public void setBackgroundXPosition(int backgroundXPosition) {
		this.backgroundXPosition = backgroundXPosition;
	}

	/**
	 * Checks if is background y position absolute.
	 *
	 * @return the background y position absolute
	 */
	public boolean isBackgroundYPositionAbsolute() {
		return backgroundYPositionAbsolute;
	}

	/**
	 * Sets the background y position absolute.
	 *
	 * @param backgroundYPositionAbsolute
	 *            the new background y position absolute
	 */
	public void setBackgroundYPositionAbsolute(boolean backgroundYPositionAbsolute) {
		this.backgroundYPositionAbsolute = backgroundYPositionAbsolute;
	}

	/**
	 * Gets the background y position.
	 *
	 * @return the background y position
	 */
	public int getBackgroundYPosition() {
		return backgroundYPosition;
	}

	/**
	 * Sets the background y position.
	 *
	 * @param backgroundYPosition
	 *            the new background y position
	 */
	public void setBackgroundYPosition(int backgroundYPosition) {
		this.backgroundYPosition = backgroundYPosition;
	}

	/**
	 * Gets the background repeat.
	 *
	 * @return the background repeat
	 */
	public int getBackgroundRepeat() {
		return backgroundRepeat;
	}

	/**
	 * Sets the background repeat.
	 *
	 * @param backgroundRepeat
	 *            the new background repeat
	 */
	public void setBackgroundRepeat(int backgroundRepeat) {
		this.backgroundRepeat = backgroundRepeat;
	}

}
