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
package org.lobobrowser.cobra_testing;

import javax.swing.JFrame;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.lobobrowser.http.UserAgentContext;

/**
 * The Class BrowserFrameCallback.
 */
public class BrowserFrameCallback {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		JFrame window = new JFrame();
		HtmlPanel panel = new HtmlPanel();
		window.getContentPane().add(panel);
		window.setSize(600, 400);
		window.setVisible(true);
		new LocalHtmlRendererContext(panel, new SimpleUserAgentContext())
				.navigate("file:c:\\temp\\html\\usertest9.html");
	}

	/**
	 * The Class LocalHtmlRendererContext.
	 */
	private static class LocalHtmlRendererContext extends SimpleHtmlRendererContext {
		public LocalHtmlRendererContext(HtmlPanel contextComponent, UserAgentContext ucontext) {
			super(contextComponent, ucontext);
		}

		@Override
		public BrowserFrame createBrowserFrame() {
			System.out.println("## createBrowserFrame() called.");
			return super.createBrowserFrame();
		}
	}
}
