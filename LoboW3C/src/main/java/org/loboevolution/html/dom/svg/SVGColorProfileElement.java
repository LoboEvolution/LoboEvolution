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



/**
 * <p>SVGColorProfileElement interface.</p>
 *
 *
 *
 */
public interface SVGColorProfileElement extends SVGElement, SVGURIReference, SVGRenderingIntent {
	/**
	 * <p>getLocal.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLocal();

	/**
	 * <p>setLocal.</p>
	 *
	 * @param local a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setLocal(String local);

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * <p>setName.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setName(String name);

	/**
	 * <p>getRenderingIntent.</p>
	 *
	 * @return a short.
	 */
	short getRenderingIntent();

	/**
	 * <p>setRenderingIntent.</p>
	 *
	 * @param renderingIntent a short.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setRenderingIntent(short renderingIntent);
}
