/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.store.CacheManager;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class ClearCacheAction.
 */
public class ClearCacheAction extends ActionPool {

	/**
	 * Instantiates a new clear cache action.
	 *
	 * @param componentSource
	 *            the component source
	 * @param window
	 *            the window
	 */
	public ClearCacheAction(ComponentSource componentSource, NavigatorWindow window) {
		super(componentSource, window);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ClearCacheAction.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		CacheManager clearCache = CacheManager.getInstance();
		try {
			clearCache.clearCache();
		} catch (Throwable err) {
			logger.log(Level.SEVERE, "ClearCacheAction", err);
		}
	}
}
