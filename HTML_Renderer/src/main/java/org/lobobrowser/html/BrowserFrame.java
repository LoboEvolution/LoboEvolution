/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jan 29, 2006
 */
package org.lobobrowser.html;

import java.awt.Component;
import java.awt.Insets;
import java.net.URL;

import org.w3c.dom.Document;

/**
 * The <code>BrowserFrame</code> interface represents a browser frame. A simple
 * implementation of this interface is provided in
 * {@link org.lobobrowser.html.test.SimpleBrowserFrame}.
 */
public interface BrowserFrame {

	/**
	 * Gets the component.
	 *
	 * @return the component
	 */
	Component getComponent();

	/**
	 * Loads a URL in the frame.
	 *
	 * @param url
	 *            the url
	 */
	void loadURL(URL url);

	/**
	 * Gets the content document.
	 *
	 * @return the content document
	 */
	Document getContentDocument();

	/**
	 * Gets the html renderer context.
	 *
	 * @return the html renderer context
	 */
	HtmlRendererContext getHtmlRendererContext();

	/**
	 * Sets the default margin insets.
	 *
	 * @param insets
	 *            the new default margin insets
	 */
	void setDefaultMarginInsets(Insets insets);

	/**
	 * Sets the default overflow x.
	 *
	 * @param overflowX
	 *            the new default overflow x
	 */
	void setDefaultOverflowX(int overflowX);

	/**
	 * Sets the default overflow y.
	 *
	 * @param overflowY
	 *            the new default overflow y
	 */
	void setDefaultOverflowY(int overflowY);
}
