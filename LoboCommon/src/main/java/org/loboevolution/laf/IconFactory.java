/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
package org.loboevolution.laf;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.loboevolution.common.IORoutines;

/**
 * A factory for creating Icon objects.
 *
 * @author utente
 * @version $Id: $Id
 */
public class IconFactory {

    private final static Logger logger = Logger.getLogger(IconFactory.class.getName());

	/** The Constant instance. */
	private static final IconFactory instance = new IconFactory();
	
	/** The icon map. */
	private Map<String, ImageIcon> iconMap = new HashMap<String, ImageIcon>();

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
						logger.info("getIcon(): Resource path " + resourcePath + " not found.");
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
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}
}
