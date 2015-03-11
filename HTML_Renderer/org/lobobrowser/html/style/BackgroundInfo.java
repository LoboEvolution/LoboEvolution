/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.style;

import java.awt.Color;
import java.net.URL;


/**
 * The Class BackgroundInfo.
 */
public class BackgroundInfo {
	
	/** The background color. */
	public Color backgroundColor;
	
	/** The background image. */
	public URL backgroundImage;
	
	/** The background x position absolute. */
	public boolean backgroundXPositionAbsolute;
	
	/** The background x position. */
	public int backgroundXPosition;
	
	/** The background y position absolute. */
	public boolean backgroundYPositionAbsolute;
	
	/** The background y position. */
	public int backgroundYPosition;
	
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
}
