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
package org.loboevolution.primary.action;

import java.awt.event.ActionEvent;
import java.net.URL;

import org.loboevolution.primary.ext.ActionPool;
import org.loboevolution.primary.ext.ComponentSource;
import org.loboevolution.ua.NavigatorWindow;

/**
 * The Class NavigateAction.
 */
public class NavigateAction extends ActionPool {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The component source. */
	private ComponentSource componentSource;

	/** The url. */
	private URL url;

	/**
	 * Instantiates a new navigate action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public NavigateAction(ComponentSource componentSource, NavigatorWindow window) {
		super(componentSource, window);
		this.componentSource = componentSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		componentSource.navigate(this.url);
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

}
