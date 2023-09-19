/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
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
 */
public class IconFactory {

    private static final  Logger logger = Logger.getLogger(IconFactory.class.getName());

	/** The Constant instance. */
	private static final IconFactory instance = new IconFactory();
	
	/** The icon map. */
	private final Map<String, ImageIcon> iconMap = new HashMap<>();

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
