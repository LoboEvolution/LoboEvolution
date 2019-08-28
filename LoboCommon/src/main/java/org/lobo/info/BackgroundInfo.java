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
package org.lobo.info;

import java.awt.Color;
import java.net.URL;

public class BackgroundInfo {
	public static final int BR_NO_REPEAT = 1;
	public static final int BR_REPEAT = 0;
	public static final int BR_REPEAT_X = 2;
	public static final int BR_REPEAT_Y = 3;
	public Color backgroundColor;
	public URL backgroundImage;
	public int backgroundRepeat = BR_REPEAT;

	public int backgroundXPosition;
	public boolean backgroundXPositionAbsolute;
	public int backgroundYPosition;
	public boolean backgroundYPositionAbsolute;
}
