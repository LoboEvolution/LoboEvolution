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
package org.loboevolution.info;

import java.awt.Color;
import java.net.URL;

/**
 * <p>BackgroundInfo class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class BackgroundInfo {
	
	/** Constant BR_NO_REPEAT=1 */
	public static final int BR_NO_REPEAT = 1;
	
	/** Constant BR_REPEAT=0 */
	public static final int BR_REPEAT = 0;
	
	/** Constant BR_REPEAT_X=2 */
	public static final int BR_REPEAT_X = 2;
	
	/** Constant BR_REPEAT_Y=3 */
	public static final int BR_REPEAT_Y = 3;
	
	private Color backgroundColor;
	
	private URL backgroundImage;
	
	private int backgroundRepeat = BR_REPEAT;

	private int backgroundXPosition;
	
	private boolean backgroundXPositionAbsolute;
	
	private int backgroundYPosition;
	
	private boolean backgroundYPositionAbsolute;

	/**
	 * <p>Getter for the field backgroundColor.</p>
	 *
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * <p>Getter for the field backgroundImage.</p>
	 *
	 * @return the backgroundImage
	 */
	public URL getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * <p>Getter for the field backgroundRepeat.</p>
	 *
	 * @return the backgroundRepeat
	 */
	public int getBackgroundRepeat() {
		return backgroundRepeat;
	}

	/**
	 * <p>Getter for the field backgroundXPosition.</p>
	 *
	 * @return the backgroundXPosition
	 */
	public int getBackgroundXPosition() {
		return backgroundXPosition;
	}

	/**
	 * <p>isBackgroundXPositionAbsolute.</p>
	 *
	 * @return the backgroundXPositionAbsolute
	 */
	public boolean isBackgroundXPositionAbsolute() {
		return backgroundXPositionAbsolute;
	}

	/**
	 * <p>Getter for the field backgroundYPosition.</p>
	 *
	 * @return the backgroundYPosition
	 */
	public int getBackgroundYPosition() {
		return backgroundYPosition;
	}

	/**
	 * <p>isBackgroundYPositionAbsolute.</p>
	 *
	 * @return the backgroundYPositionAbsolute
	 */
	public boolean isBackgroundYPositionAbsolute() {
		return backgroundYPositionAbsolute;
	}

	/**
	 * <p>Setter for the field backgroundColor.</p>
	 *
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * <p>Setter for the field backgroundImage.</p>
	 *
	 * @param backgroundImage the backgroundImage to set
	 */
	public void setBackgroundImage(URL backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * <p>Setter for the field backgroundRepeat.</p>
	 *
	 * @param backgroundRepeat the backgroundRepeat to set
	 */
	public void setBackgroundRepeat(int backgroundRepeat) {
		this.backgroundRepeat = backgroundRepeat;
	}

	/**
	 * <p>Setter for the field backgroundXPosition.</p>
	 *
	 * @param backgroundXPosition the backgroundXPosition to set
	 */
	public void setBackgroundXPosition(int backgroundXPosition) {
		this.backgroundXPosition = backgroundXPosition;
	}

	/**
	 * <p>Setter for the field backgroundXPositionAbsolute.</p>
	 *
	 * @param backgroundXPositionAbsolute the backgroundXPositionAbsolute to set
	 */
	public void setBackgroundXPositionAbsolute(boolean backgroundXPositionAbsolute) {
		this.backgroundXPositionAbsolute = backgroundXPositionAbsolute;
	}

	/**
	 * <p>Setter for the field backgroundYPosition.</p>
	 *
	 * @param backgroundYPosition the backgroundYPosition to set
	 */
	public void setBackgroundYPosition(int backgroundYPosition) {
		this.backgroundYPosition = backgroundYPosition;
	}

	/**
	 * <p>Setter for the field backgroundYPositionAbsolute.</p>
	 *
	 * @param backgroundYPositionAbsolute the backgroundYPositionAbsolute to set
	 */
	public void setBackgroundYPositionAbsolute(boolean backgroundYPositionAbsolute) {
		this.backgroundYPositionAbsolute = backgroundYPositionAbsolute;
	}
}
