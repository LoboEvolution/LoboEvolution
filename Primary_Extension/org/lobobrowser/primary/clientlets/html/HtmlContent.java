/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.clientlets.html;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.util.io.BufferExceededException;
import org.lobobrowser.util.io.RecordedInputStream;
import org.lobobrowser.w3c.dom.html.HTMLDocument;
import org.lobobrowser.w3c.dom.html.HTMLElement;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HtmlContent implements ComponentContent {
	private static final Logger logger = Logger.getLogger(HtmlContent.class
			.getName());
	private final HTMLDocument document;
	private final HtmlPanel panel;
	private final RecordedInputStream ris;
	private final String charset;
	private final String sourceCode;

	public HtmlContent(final HTMLDocument document, final HtmlPanel panel,
			RecordedInputStream ris, String charset) {
		super();
		this.document = document;
		this.panel = panel;
		this.ris = ris;
		this.charset = charset;
		this.sourceCode = null;
	}

	public HtmlContent(final HTMLDocument document, final HtmlPanel panel,
			String sourceCode) {
		super();
		this.document = document;
		this.panel = panel;
		this.ris = null;
		this.charset = null;
		this.sourceCode = sourceCode;
	}

	public boolean canCopy() {
		return this.panel.hasSelection();
	}

	public boolean copy() {
		return this.panel.copy();
	}

	public Component getComponent() {
		return this.panel;
	}

	public String getSourceCode() {
		try {
			RecordedInputStream ris = this.ris;
			if (ris != null) {
				byte[] bytesSoFar = ris.getBytesRead();
				try {
					return new String(bytesSoFar, this.charset);
				} catch (java.io.UnsupportedEncodingException uee) {
					return "[Error: " + uee + "]";
				}
			} else {
				return this.sourceCode;
			}
		} catch (BufferExceededException bee) {
			return "[Error: Document content too large.]";
		}
	}

	public String getTitle() {
		return this.document.getTitle();
	}

	public String getDescription() {
		NodeList nodeList = this.document.getElementsByTagName("meta");
		if (nodeList == null) {
			return null;
		}
		int length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			if (node instanceof HTMLElement) {
				HTMLElement element = (HTMLElement) node;
				String name = element.getAttribute("name");
				if (name != null && name.equalsIgnoreCase("description")) {
					return element.getAttribute("description");
				}
			}
		}
		return null;
	}

	public void addNotify() {
	}

	public void removeNotify() {
	}

	public Object getContentObject() {
		return this.document;
	}

	public String getMimeType() {
		return "text/html";
	}

	public void setProperty(String name, Object value) {
		if ("defaultMarginInsets".equals(name)
				&& value instanceof java.awt.Insets) {
			this.panel.setDefaultMarginInsets((java.awt.Insets) value);
			;
		} else if ("defaultOverflowX".equals(name) && value instanceof Integer) {
			this.panel.setDefaultOverflowX((Integer) value);
		} else if ("defaultOverflowY".equals(name) && value instanceof Integer) {
			this.panel.setDefaultOverflowY((Integer) value);
		} else {
			if (logger.isLoggable(Level.INFO)) {
				logger.info("setProperty(): Unknown property: " + name);
			}
		}
	}
}