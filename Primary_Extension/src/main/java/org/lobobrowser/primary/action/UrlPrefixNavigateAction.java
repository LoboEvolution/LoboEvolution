/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.swing.AbstractAction;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorWindow;
import org.lobobrowser.ua.RequestType;

/**
 * The Class UrlPrefixNavigateAction.
 */
public class UrlPrefixNavigateAction extends AbstractAction implements EnableableAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The url prefix. */
	private String urlPrefix;

	/** The url encode. */
	private boolean urlEncode;

	/** The component source. */
	private ComponentSource componentSource;

	/** The window. */
	private NavigatorWindow window;

	/** The action. */
	private ActionPool action;

	/**
	 * Instantiates a new url prefix navigate action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 * @param action
	 *            the action
	 */
	public UrlPrefixNavigateAction(ComponentSource componentSource, NavigatorWindow window, ActionPool action) {
		this.action = action;
		this.window = window;
		this.componentSource = componentSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.primary.ext.ActionPool.EnableableAction#updateEnabling()
	 */
	@Override
	public void updateEnabling() {
		NavigationEntry entry = window.getCurrentNavigationEntry();
		action.setEnabled(entry != null && !entry.getUrl().toExternalForm().startsWith(this.urlPrefix));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		NavigationEntry entry = window.getCurrentNavigationEntry();
		if (entry == null) {
			return;
		}
		try {
			String roughLocation = this.urlPrefix + (this.urlEncode
					? URLEncoder.encode(entry.getUrl().toExternalForm(), "UTF-8") : entry.getUrl().toExternalForm());
			componentSource.navigate(roughLocation, RequestType.PROGRAMMATIC);
		} catch (UnsupportedEncodingException uee) {
			// not expected - ignore
		}
	}

	/**
	 * Gets the url prefix.
	 *
	 * @return the url prefix
	 */
	public String getUrlPrefix() {
		return urlPrefix;
	}

	/**
	 * Sets the url prefix.
	 *
	 * @param urlPrefix
	 *            the new url prefix
	 */
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	/**
	 * Checks if is url encode.
	 *
	 * @return the url encode
	 */
	public boolean isUrlEncode() {
		return urlEncode;
	}

	/**
	 * Sets the url encode.
	 *
	 * @param urlEncode
	 *            the new url encode
	 */
	public void setUrlEncode(boolean urlEncode) {
		this.urlEncode = urlEncode;
	}
}
