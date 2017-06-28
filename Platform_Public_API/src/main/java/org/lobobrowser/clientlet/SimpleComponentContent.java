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
package org.lobobrowser.clientlet;

import java.awt.Component;

/**
 * The Class SimpleComponentContent.
 */
public class SimpleComponentContent extends AbstractComponentContent {

	/** The component. */
	private final Component component;

	/** The title. */
	private final String title;

	/** The source code. */
	private final String sourceCode;

	/**
	 * Instantiates a new simple component content.
	 *
	 * @param component
	 *            the component
	 * @param title
	 *            the title
	 * @param sourceCode
	 *            the source code
	 */
	public SimpleComponentContent(Component component, String title, String sourceCode) {
		this.component = component;
		this.title = title;
		this.sourceCode = sourceCode;
	}

	/**
	 * Instantiates a new simple component content.
	 *
	 * @param component
	 *            the component
	 */
	public SimpleComponentContent(Component component) {
		this.component = component;
		this.title = component.toString();
		this.sourceCode = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#canCopy()
	 */
	@Override
	public boolean canCopy() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#copy()
	 */
	@Override
	public boolean copy() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this.component;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#getSourceCode()
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.clientlet.AbstractComponentContent#getTitle()
	 */
	@Override
	public String getTitle() {
		return this.title;
	}
}
