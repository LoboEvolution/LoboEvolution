/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.html;

import java.awt.event.MouseEvent;
import java.net.URL;

import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.w3c.html.HTMLAnchorElement;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLElement;

/**
 * Abstract implementation of the {@link HtmlRendererContext} interface with
 * blank methods, provided for developer convenience.
 */
public abstract class AbstractHtmlRendererContext implements HtmlRendererContext {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#alert(java.lang.String)
	 */
	@Override
	public void alert(String message) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#back()
	 */
	@Override
	public void back() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#blur()
	 */
	@Override
	public void blur() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#close()
	 */
	@Override
	public void close() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#confirm(java.lang.String)
	 */
	@Override
	public boolean confirm(String message) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#createBrowserFrame()
	 */
	@Override
	public BrowserFrame createBrowserFrame() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#focus()
	 */
	@Override
	public void focus() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getDefaultStatus()
	 */
	@Override
	public String getDefaultStatus() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getFrames()
	 */
	@Override
	public HTMLCollection getFrames() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.HtmlRendererContext#getHtmlObject(org.lobobrowser.
	 * html .w3c.HTMLElement)
	 */
	@Override
	public HtmlObject getHtmlObject(HTMLElement element) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getName()
	 */
	@Override
	public String getName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getOpener()
	 */
	@Override
	public HtmlRendererContext getOpener() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getParent()
	 */
	@Override
	public HtmlRendererContext getParent() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getStatus()
	 */
	@Override
	public String getStatus() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getTop()
	 */
	@Override
	public HtmlRendererContext getTop() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getUserAgentContext()
	 */
	@Override
	public UserAgentContext getUserAgentContext() {
		return null;
	}

	/**
	 * Returns false unless overridden.
	 *
	 * @return true, if is closed
	 */
	@Override
	public boolean isClosed() {
		return false;
	}

	/**
	 * Returns true unless overridden.
	 *
	 * @return true, if is image loading enabled
	 */
	@Override
	public boolean isImageLoadingEnabled() {
		return true;
	}

	/**
	 * Returns false unless overridden.
	 *
	 * @param link
	 *            the link
	 * @return true, if is visited link
	 */
	@Override
	public boolean isVisitedLink(HTMLAnchorElement link) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.HtmlRendererContext#linkClicked(org.lobobrowser.html
	 * .w3c.HTMLElement, java.net.URL, java.lang.String)
	 */
	@Override
	public void linkClicked(HTMLElement linkNode, URL url, String target) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#navigate(java.net.URL,
	 * java.lang.String)
	 */
	@Override
	public void navigate(URL url, String target) {
	}

	/**
	 * Returns true unless overridden.
	 *
	 * @param element
	 *            the element
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	@Override
	public boolean onContextMenu(HTMLElement element, MouseEvent event) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.HtmlRendererContext#onMouseOut(org.lobobrowser.html.
	 * w3c.HTMLElement, java.awt.event.MouseEvent)
	 */
	@Override
	public void onMouseOut(HTMLElement element, MouseEvent event) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.HtmlRendererContext#onMouseOver(org.lobobrowser.html
	 * .w3c.HTMLElement, java.awt.event.MouseEvent)
	 */
	@Override
	public void onMouseOver(HTMLElement element, MouseEvent event) {
	}

	/**
	 * Open.
	 *
	 * @param absoluteUrl
	 *            the absolute url
	 * @param windowName
	 *            the window name
	 * @param windowFeatures
	 *            the window features
	 * @param replace
	 *            the replace
	 * @return the html renderer context
	 */
	public HtmlRendererContext open(String absoluteUrl, String windowName, String windowFeatures, boolean replace) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#open(java.net.URL,
	 * java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#prompt(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String prompt(String message, String inputDefault) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#reload()
	 */
	@Override
	public void reload() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#scroll(int, int)
	 */
	@Override
	public void scroll(int x, int y) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#setDefaultStatus(java.lang.
	 * String)
	 */
	@Override
	public void setDefaultStatus(String value) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.HtmlRendererContext#setOpener(org.lobobrowser.html.
	 * HtmlRendererContext)
	 */
	@Override
	public void setOpener(HtmlRendererContext opener) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#setStatus(java.lang.String)
	 */
	@Override
	public void setStatus(String message) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.HtmlRendererContext#submitForm(java.lang.String,
	 * java.net.URL, java.lang.String, java.lang.String,
	 * org.lobobrowser.html.FormInput[])
	 */
	@Override
	public void submitForm(String method, URL action, String target, String enctype, FormInput[] formInputs) {
	}

	/**
	 * Returns true unless overridden.
	 *
	 * @param element
	 *            the element
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	@Override
	public boolean onDoubleClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/**
	 * Returns true unless overridden.
	 *
	 * @param element
	 *            the element
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	@Override
	public boolean onMouseClick(HTMLElement element, MouseEvent event) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#scrollBy(int, int)
	 */
	@Override
	public void scrollBy(int x, int y) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#resizeBy(int, int)
	 */
	@Override
	public void resizeBy(int byWidth, int byHeight) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#resizeTo(int, int)
	 */
	@Override
	public void resizeTo(int width, int height) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#forward()
	 */
	@Override
	public void forward() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getCurrentURL()
	 */
	@Override
	public String getCurrentURL() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getHistoryLength()
	 */
	@Override
	public int getHistoryLength() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getNextURL()
	 */
	@Override
	public String getNextURL() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#getPreviousURL()
	 */
	@Override
	public String getPreviousURL() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.HtmlRendererContext#goToHistoryURL(java.lang.String)
	 */
	@Override
	public void goToHistoryURL(String url) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.HtmlRendererContext#moveInHistory(int)
	 */
	@Override
	public void moveInHistory(int offset) {
	}
}
