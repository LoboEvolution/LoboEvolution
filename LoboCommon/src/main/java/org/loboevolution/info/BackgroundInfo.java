/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.Color;
import java.net.URL;

/**
 * <p>BackgroundInfo class.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundInfo {
	
	/** Constant BR_NO_REPEAT=1 */
	public static final int BR_NO_REPEAT = 1;
	
	/** Constant BR_REPEAT=0 */
	public static final int BR_REPEAT = 0;
	
	/** Constant BR_REPEAT_X=2 */
	public static final int BR_REPEAT_X = 2;
	
	/** Constant BR_REPEAT_Y=3 */
	public static final int BR_REPEAT_Y = 3;
	
	/** The backgroundColor*/
	private Color backgroundColor;

	/** The backgroundColor*/
	private URL backgroundImage;

	/** The backgroundColor*/
	@Builder.Default
	private int backgroundRepeat = BR_REPEAT;

	/** The backgroundColor*/
	private int backgroundXPosition;

	/** The backgroundColor*/
	private boolean backgroundXPositionAbsolute;

	/** The backgroundColor*/
	private int backgroundYPosition;

	/** The backgroundColor*/
	private boolean backgroundYPositionAbsolute;
}
