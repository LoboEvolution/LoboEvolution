/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html;

import java.awt.event.MouseEvent;
import java.net.URL;

import org.lobobrowser.html.w3c.HTMLCollection;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.HTMLLinkElement;


/**
 * Abstract implementation of the {@link HtmlRendererContext} interface with
 * blank methods, provided for developer convenience.
 */
public abstract class AbstractHtmlRendererContext implements
		HtmlRendererContext {

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#alert(java.lang.String)
	 */
	public void alert(String message) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#back()
	 */
	public void back() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#blur()
	 */
	public void blur() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#close()
	 */
	public void close() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#confirm(java.lang.String)
	 */
	public boolean confirm(String message) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#createBrowserFrame()
	 */
	public BrowserFrame createBrowserFrame() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#focus()
	 */
	public void focus() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getDefaultStatus()
	 */
	public String getDefaultStatus() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getFrames()
	 */
	public HTMLCollection getFrames() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getHtmlObject(org.lobobrowser.html.w3c.HTMLElement)
	 */
	public HtmlObject getHtmlObject(HTMLElement element) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getName()
	 */
	public String getName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getOpener()
	 */
	public HtmlRendererContext getOpener() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getParent()
	 */
	public HtmlRendererContext getParent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getStatus()
	 */
	public String getStatus() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getTop()
	 */
	public HtmlRendererContext getTop() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getUserAgentContext()
	 */
	public UserAgentContext getUserAgentContext() {
		return null;
	}

	/**
	 * Returns false unless overridden.
	 *
	 * @return true, if is closed
	 */
	public boolean isClosed() {
		return false;
	}

	/**
	 * Returns true unless overridden.
	 *
	 * @return true, if is image loading enabled
	 */
	public boolean isImageLoadingEnabled() {
		return true;
	}

	/**
	 * Returns false unless overridden.
	 *
	 * @param link the link
	 * @return true, if is visited link
	 */
	public boolean isVisitedLink(HTMLLinkElement link) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#linkClicked(org.lobobrowser.html.w3c.HTMLElement, java.net.URL, java.lang.String)
	 */
	public void linkClicked(HTMLElement linkNode, URL url, String target) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#navigate(java.net.URL, java.lang.String)
	 */
	public void navigate(URL url, String target) {
	}

	/**
	 * Returns true unless overridden.
	 *
	 * @param element the element
	 * @param event the event
	 * @return true, if successful
	 */
	public boolean onContextMenu(HTMLElement element, MouseEvent event) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#onMouseOut(org.lobobrowser.html.w3c.HTMLElement, java.awt.event.MouseEvent)
	 */
	public void onMouseOut(HTMLElement element, MouseEvent event) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#onMouseOver(org.lobobrowser.html.w3c.HTMLElement, java.awt.event.MouseEvent)
	 */
	public void onMouseOver(HTMLElement element, MouseEvent event) {
	}

	/**
	 * Open.
	 *
	 * @param absoluteUrl the absolute url
	 * @param windowName the window name
	 * @param windowFeatures the window features
	 * @param replace the replace
	 * @return the html renderer context
	 */
	public HtmlRendererContext open(String absoluteUrl, String windowName,
			String windowFeatures, boolean replace) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#open(java.net.URL, java.lang.String, java.lang.String, boolean)
	 */
	public HtmlRendererContext open(URL url, String windowName,
			String windowFeatures, boolean replace) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#prompt(java.lang.String, java.lang.String)
	 */
	public String prompt(String message, String inputDefault) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#reload()
	 */
	public void reload() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#scroll(int, int)
	 */
	public void scroll(int x, int y) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#setDefaultStatus(java.lang.String)
	 */
	public void setDefaultStatus(String value) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#setOpener(org.lobobrowser.html.HtmlRendererContext)
	 */
	public void setOpener(HtmlRendererContext opener) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#setStatus(java.lang.String)
	 */
	public void setStatus(String message) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#submitForm(java.lang.String, java.net.URL, java.lang.String, java.lang.String, org.lobobrowser.html.FormInput[])
	 */
	public void submitForm(String method, URL action, String target,
			String enctype, FormInput[] formInputs) {
	}

	/**
	 * Returns true unless overridden.
	 *
	 * @param element the element
	 * @param event the event
	 * @return true, if successful
	 */
	public boolean onDoubleClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/**
	 * Returns true unless overridden.
	 *
	 * @param element the element
	 * @param event the event
	 * @return true, if successful
	 */
	public boolean onMouseClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#scrollBy(int, int)
	 */
	public void scrollBy(int x, int y) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#resizeBy(int, int)
	 */
	public void resizeBy(int byWidth, int byHeight) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#resizeTo(int, int)
	 */
	public void resizeTo(int width, int height) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#forward()
	 */
	public void forward() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getCurrentURL()
	 */
	public String getCurrentURL() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getHistoryLength()
	 */
	public int getHistoryLength() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getNextURL()
	 */
	public String getNextURL() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#getPreviousURL()
	 */
	public String getPreviousURL() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#goToHistoryURL(java.lang.String)
	 */
	public void goToHistoryURL(String url) {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlRendererContext#moveInHistory(int)
	 */
	public void moveInHistory(int offset) {
	}
}
