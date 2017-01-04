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
/*
 * Created on Jan 29, 2006
 */
package org.lobobrowser.html.test;

import java.awt.Component;
import java.awt.Insets;
import java.net.URL;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.gui.HtmlPanel;
import org.w3c.dom.Document;

/**
 * The <code>SimpleBrowserFrame</code> class implements the
 * {@link org.lobobrowser.html.BrowserFrame} interface. It represents a browser
 * frame component.
 *
 * @see HtmlRendererContext#createBrowserFrame()
 */
public class SimpleBrowserFrame extends HtmlPanel implements BrowserFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The {@link HtmlRendererContext} associated with the browser frame. */
	private final HtmlRendererContext rcontext;

	/** The parent rcontext. */
	private final HtmlRendererContext parentRcontext;

	/**
	 * Instantiates a new simple browser frame.
	 *
	 * @param parentRcontext
	 *            the parent rcontext
	 */
	public SimpleBrowserFrame(HtmlRendererContext parentRcontext) {
		this.parentRcontext = parentRcontext;
		this.rcontext = this.createHtmlRendererContext(parentRcontext);
	}

	/**
	 * Creates the {@link HtmlRendererContext} associated with this browser
	 * frame. Override to use a specialized instance.
	 *
	 * @param parentRcontext
	 *            The parent context.
	 * @return the html renderer context
	 */
	protected HtmlRendererContext createHtmlRendererContext(HtmlRendererContext parentRcontext) {
		return new SimpleHtmlRendererContext(this, parentRcontext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.BrowserFrame#getHtmlRendererContext()
	 */
	@Override
	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.BrowserFrame#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.BrowserFrame#loadURL(java.net.URL)
	 */
	@Override
	public void loadURL(URL url) {
		this.rcontext.navigate(url, "_this");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.BrowserFrame#getContentDocument()
	 */
	@Override
	public Document getContentDocument() {
		return (Document) this.getRootNode();
	}

	/**
	 * Gets the parent html renderer context.
	 *
	 * @return the parent html renderer context
	 */
	public HtmlRendererContext getParentHtmlRendererContext() {
		return this.parentRcontext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.gui.HtmlPanel#setDefaultMarginInsets(java.awt.
	 * Insets)
	 */
	@Override
	public void setDefaultMarginInsets(Insets insets) {
		// Current implementation is the frame HtmlPanel.
		super.setDefaultMarginInsets(insets);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.gui.HtmlPanel#setDefaultOverflowX(int)
	 */
	@Override
	public void setDefaultOverflowX(int overflowX) {
		super.setDefaultOverflowX(overflowX);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.gui.HtmlPanel#setDefaultOverflowY(int)
	 */
	@Override
	public void setDefaultOverflowY(int overflowY) {
		super.setDefaultOverflowY(overflowY);
	}
}
