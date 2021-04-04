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
package org.loboevolution.html.dom.svg;

import org.loboevolution.html.node.events.UIEvent;

/**
 * <p>SVGZoomEvent interface.</p>
 *
 *
 *
 */
public interface SVGZoomEvent extends UIEvent {
	/**
	 * <p>getZoomRectScreen.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 */
	SVGRect getZoomRectScreen();

	/**
	 * <p>getPreviousScale.</p>
	 *
	 * @return a float.
	 */
	float getPreviousScale();

	/**
	 * <p>getPreviousTranslate.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 */
	SVGPoint getPreviousTranslate();

	/**
	 * <p>getNewScale.</p>
	 *
	 * @return a float.
	 */
	float getNewScale();

	/**
	 * <p>getNewTranslate.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 */
	SVGPoint getNewTranslate();
}
