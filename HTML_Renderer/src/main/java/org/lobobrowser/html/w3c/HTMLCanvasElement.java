/*
 * GNU GENERAL LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General License for more details. You should have received
 * a copy of the GNU General License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.w3c;

/**
 * The public interface HTMLCanvasElement.
 */
public interface HTMLCanvasElement extends HTMLElement {
	
	
	/** The fill. */
	int FILL = 0;
	
	/** The fill rect. */
	int FILL_RECT = 1;
	
	/** The fill text. */
	int FILL_TEXT = 2;
	
	/** The circle. */
	int CIRCLE = 3;
	
	/** The stroke. */
	int STROKE = 4;
	
	/** The stroke rect. */
	int STROKE_RECT = 5;
	
	/** The stroke text. */
	int STROKE_TEXT = 6;
	
	/** The rect. */
	int RECT = 7; 
	
	
    /**
     * Gets the width.
     *
     * @return the width
     */
    int getWidth();

    /**
     * Sets the width.
     *
     * @param width
     *            the new width
     */
    void setWidth(int width);

    /**
     * Gets the height.
     *
     * @return the height
     */
    int getHeight();

    /**
     * Sets the height.
     *
     * @param height
     *            the new height
     */
    void setHeight(int height);

    /**
     * To data url.
     *
     * @return the string
     */
    String toDataURL();

    /**
     * To data url.
     *
     * @param type
     *            the type
     * @param args
     *            the args
     * @return the string
     */
    String toDataURL(String type, Object... args);

    /**
     * Gets the context.
     *
     * @param contextId
     *            the context id
     * @return the context
     */
    CanvasRenderingContext getContext(String contextId);
}
