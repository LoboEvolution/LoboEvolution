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

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.loboevolution.html.HtmlObject;
import org.loboevolution.w3c.html.HTMLElement;

/**
 * Simple implementation of {@link org.loboevolution.html.HtmlObject}.
 */
public class SimpleHtmlObject extends JComponent implements HtmlObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The element. */
	protected final HTMLElement element;

	/**
	 * Instantiates a new simple html object.
	 *
	 * @param element
	 *            the element
	 */
	public SimpleHtmlObject(HTMLElement element) {
		this.element = element;
		this.setLayout(new FlowLayout());
		this.add(new JLabel("[" + element.getTagName() + "]"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlObject#reset(int, int)
	 */
	@Override
	public void reset(int availWidth, int availHeight) {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlObject#destroy()
	 */
	@Override
	public void destroy() {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlObject#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlObject#resume()
	 */
	@Override
	public void resume() {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.HtmlObject#suspend()
	 */
	@Override
	public void suspend() {
		return;
	}
}
