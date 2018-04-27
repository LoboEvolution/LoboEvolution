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
package org.loboevolution.html.test;

import java.awt.Frame;
import java.net.URL;

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.UserAgentContext;

/**
 * The Class LocalHtmlRendererContext.
 */
public class LocalHtmlRendererContext extends SimpleHtmlRendererContext {

	/**
	 * Instantiates a new local html renderer context.
	 *
	 * @param contextComponent
	 *            the context component
	 * @param ucontext
	 *            the ucontext
	 */
	public LocalHtmlRendererContext(HtmlPanel contextComponent, UserAgentContext ucontext) {
		super(contextComponent, ucontext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.test.SimpleHtmlRendererContext#open(java.net.
	 * URL, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace) {
		TestFrame frame = new TestFrame("Cobra Test Tool");
		frame.setSize(600, 400);
		frame.setExtendedState(Frame.NORMAL);
		frame.setVisible(true);
		HtmlRendererContext ctx = frame.getHtmlRendererContext();
		ctx.setOpener(this);
		frame.navigate(url.toExternalForm());
		return ctx;
	}
}
