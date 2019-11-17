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

public class BackgroundInfo {
	
	public static final int BR_NO_REPEAT = 1;
	
	public static final int BR_REPEAT = 0;
	
	public static final int BR_REPEAT_X = 2;
	
	public static final int BR_REPEAT_Y = 3;
	
	private Color backgroundColor;
	
	private URL backgroundImage;
	
	private int backgroundRepeat = BR_REPEAT;

	private int backgroundXPosition;
	
	private boolean backgroundXPositionAbsolute;
	
	private int backgroundYPosition;
	
	private boolean backgroundYPositionAbsolute;

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @return the backgroundImage
	 */
	public URL getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * @return the backgroundRepeat
	 */
	public int getBackgroundRepeat() {
		return backgroundRepeat;
	}

	/**
	 * @return the backgroundXPosition
	 */
	public int getBackgroundXPosition() {
		return backgroundXPosition;
	}

	/**
	 * @return the backgroundXPositionAbsolute
	 */
	public boolean isBackgroundXPositionAbsolute() {
		return backgroundXPositionAbsolute;
	}

	/**
	 * @return the backgroundYPosition
	 */
	public int getBackgroundYPosition() {
		return backgroundYPosition;
	}

	/**
	 * @return the backgroundYPositionAbsolute
	 */
	public boolean isBackgroundYPositionAbsolute() {
		return backgroundYPositionAbsolute;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @param backgroundImage the backgroundImage to set
	 */
	public void setBackgroundImage(URL backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * @param backgroundRepeat the backgroundRepeat to set
	 */
	public void setBackgroundRepeat(int backgroundRepeat) {
		this.backgroundRepeat = backgroundRepeat;
	}

	/**
	 * @param backgroundXPosition the backgroundXPosition to set
	 */
	public void setBackgroundXPosition(int backgroundXPosition) {
		this.backgroundXPosition = backgroundXPosition;
	}

	/**
	 * @param backgroundXPositionAbsolute the backgroundXPositionAbsolute to set
	 */
	public void setBackgroundXPositionAbsolute(boolean backgroundXPositionAbsolute) {
		this.backgroundXPositionAbsolute = backgroundXPositionAbsolute;
	}

	/**
	 * @param backgroundYPosition the backgroundYPosition to set
	 */
	public void setBackgroundYPosition(int backgroundYPosition) {
		this.backgroundYPosition = backgroundYPosition;
	}

	/**
	 * @param backgroundYPositionAbsolute the backgroundYPositionAbsolute to set
	 */
	public void setBackgroundYPositionAbsolute(boolean backgroundYPositionAbsolute) {
		this.backgroundYPositionAbsolute = backgroundYPositionAbsolute;
	}
}
