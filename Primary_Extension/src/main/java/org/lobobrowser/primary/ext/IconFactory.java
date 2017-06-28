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
package org.lobobrowser.primary.ext;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.util.io.IORoutines;

/**
 * A factory for creating Icon objects.
 */
public class IconFactory {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(IconFactory.class);

	/** The Constant instance. */
	private static final IconFactory instance = new IconFactory();

	/**
	 * Instantiates a new icon factory.
	 */
	private IconFactory() {
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static IconFactory getInstance() {
		return instance;
	}

	/** The icon map. */
	private Map<String, ImageIcon> iconMap = new HashMap<String, ImageIcon>();

	/**
	 * Gets the icon.
	 *
	 * @param resourcePath
	 *            the resource path
	 * @return the icon
	 */
	public ImageIcon getIcon(String resourcePath) {
		try {
			synchronized (this) {
				ImageIcon icon = this.iconMap.get(resourcePath);
				if (icon == null) {
					InputStream in = this.getClass().getResourceAsStream(resourcePath);
					if (in == null) {
						logger.warn("getIcon(): Resource path " + resourcePath + " not found.");
						return null;
					}
					try {
						byte[] imageBytes = IORoutines.load(in, 4096);
						icon = new ImageIcon(imageBytes);
						this.iconMap.put(resourcePath, icon);
					} finally {
						in.close();
					}
				}
				return icon;
			}
		} catch (IOException ioe) {
			logger.error("getIcon(): Resource path " + resourcePath + " gave error.", ioe);
			return null;
		}
	}
}
