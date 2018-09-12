/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Feb 5, 2006
 */
package org.loboevolution.primary.clientlets.html;

import java.awt.Component;
import java.awt.Insets;
import java.net.URL;

import org.loboevolution.html.BrowserFrame;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.ua.NavigatorFrame;
import org.loboevolution.ua.RequestType;
import org.loboevolution.ua.TargetType;

/**
 * The Class BrowserFrameImpl.
 */
public class BrowserFrameImpl implements BrowserFrame {

	/** The frame. */
	private final NavigatorFrame frame;

	/** The rcontext. */
	private final HtmlRendererContextImpl rcontext;

	/**
	 * Instantiates a new browser frame impl.
	 *
	 * @param frame
	 *            the frame
	 */
	public BrowserFrameImpl(NavigatorFrame frame) {
		this.frame = frame;
		this.rcontext = HtmlRendererContextImpl.getHtmlRendererContext(frame);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.BrowserFrame#getHtmlRendererContext()
	 */
	@Override
	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.BrowserFrame#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this.frame.getComponent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.BrowserFrame#getContentDocument()
	 */
	@Override
	public org.w3c.dom.Document getContentDocument() {
		return this.rcontext.getContentDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.BrowserFrame#loadURL(java.net.URL)
	 */
	@Override
	public void loadURL(URL url) {
		this.frame.navigate(url, "GET", null, TargetType.SELF, RequestType.FRAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.BrowserFrame#setDefaultMarginInsets(java.awt.Insets)
	 */
	@Override
	public void setDefaultMarginInsets(Insets insets) {
		this.frame.setProperty("defaultMarginInsets", insets);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.BrowserFrame#setDefaultOverflowX(int)
	 */
	@Override
	public void setDefaultOverflowX(int overflowX) {
		this.frame.setProperty("defaultOverflowX", overflowX);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.BrowserFrame#setDefaultOverflowY(int)
	 */
	@Override
	public void setDefaultOverflowY(int overflowY) {
		this.frame.setProperty("defaultOverflowY", overflowY);
	}
}
