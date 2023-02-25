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

/**
 * <p>BorderInfo class.</p>
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorderInfo {

	/** The bottomColor. */
	private Color bottomColor;

	/** The leftColor. */
	private Color leftColor;

	/** The rightColor. */
	private Color rightColor;

	/** The topColor. */
	private Color topColor;

	/** The insets. */
	private Object insets;

	/** The leftStyle. */
	private int leftStyle;

	/** The bottomStyle. */
	private int bottomStyle;

	/** The rightStyle. */
	private int rightStyle;

	/** The topStyle. */
	private int topStyle;

	/**
	 * <p>htmlInsetsIsVoid.</p>
	 * @return a {@link java.lang.Boolean} object.
	 */
	public boolean borderInfoIsVoid() {
		return topStyle == 0 && bottomStyle == 0 && leftStyle == 0 && rightStyle == 0;
	}
}
