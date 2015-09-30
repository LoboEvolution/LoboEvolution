/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;


/**
 * The Interface HTMLScriptElement.
 */
public interface HTMLScriptElement extends HTMLElement {
	
	/**
	 * Gets the src.
	 *
	 * @return the src
	 */
	// HTMLScriptElement
	public String getSrc();

	/**
	 * Sets the src.
	 *
	 * @param src
	 *            the new src
	 */
	public void setSrc(String src);

	/**
	 * Gets the async.
	 *
	 * @return the async
	 */
	public boolean getAsync();

	/**
	 * Sets the async.
	 *
	 * @param async
	 *            the new async
	 */
	public void setAsync(boolean async);

	/**
	 * Gets the defer.
	 *
	 * @return the defer
	 */
	public boolean getDefer();

	/**
	 * Sets the defer.
	 *
	 * @param defer
	 *            the new defer
	 */
	public void setDefer(boolean defer);

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type);

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	public String getCharset();

	/**
	 * Sets the charset.
	 *
	 * @param charset
	 *            the new charset
	 */
	public void setCharset(String charset);

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText();

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text);

	/**
	 * Gets the event.
	 *
	 * @return the event
	 */
	// HTMLScriptElement-28
	public String getEvent();

	/**
	 * Sets the event.
	 *
	 * @param event
	 *            the new event
	 */
	public void setEvent(String event);

	/**
	 * Gets the html for.
	 *
	 * @return the html for
	 */
	public String getHtmlFor();

	/**
	 * Sets the html for.
	 *
	 * @param htmlFor
	 *            the new html for
	 */
	public void setHtmlFor(String htmlFor);
}
