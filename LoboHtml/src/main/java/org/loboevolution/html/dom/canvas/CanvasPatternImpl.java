/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html.dom.canvas;

import org.loboevolution.html.dom.CanvasPattern;
import org.loboevolution.html.dom.HTMLCanvasElement;
import org.loboevolution.html.dom.HTMLImageElement;

/**
 * <p>CanvasPatternImpl class.</p>
 *
 *
 *
 */
public class CanvasPatternImpl implements CanvasPattern {
	
	private HTMLCanvasElement canvas;
	
	private HTMLImageElement image;
	
	private final String repetitionType;

	/**
	 * <p>Constructor for CanvasPatternImpl.</p>
	 *
	 * @param canvas a {@link org.loboevolution.html.dom.HTMLCanvasElement} object.
	 * @param repetitionType a {@link java.lang.String} object.
	 */
	public CanvasPatternImpl(HTMLCanvasElement canvas, String repetitionType) {
		this.canvas = canvas;
		this.repetitionType = repetitionType;
	}

	/**
	 * <p>Constructor for CanvasPatternImpl.</p>
	 *
	 * @param image a {@link org.loboevolution.html.dom.HTMLImageElement} object.
	 * @param repetitionType a {@link java.lang.String} object.
	 */
	public CanvasPatternImpl(HTMLImageElement image, String repetitionType) {
		this.image = image;
		this.repetitionType = repetitionType;
	}
}
