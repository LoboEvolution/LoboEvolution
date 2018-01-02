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
package org.loboevolution.util.gui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class StorageManagerCommon.
 */
public class StorageManagerCommon {
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(StorageManagerCommon.class);
	/** The Constant SETTINGS_DIR. */
	private static final String SETTINGS_DIR = "settings";
	/** The Constant STORE_DIR_NAME. */
	private static final String STORE_DIR_NAME = ".lobo";
	/** The Constant instance. */
	private static final StorageManagerCommon instance = new StorageManagerCommon();

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static StorageManagerCommon getInstance() throws IOException {
		return instance;
	}

	/**
	 * Save settings.
	 *
	 * @param name
	 *            the name
	 * @param data
	 *            the data
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void saveSettings(String name, Serializable data) throws IOException {
		File dir = this.getSettingsDirectory();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, name);
		OutputStream out = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(out);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		try {
			oos.writeObject(data);
			oos.flush();
		} finally {
			oos.close();
			bos.close();
			out.close();
		}
	}

	/**
	 * Retrieve settings.
	 *
	 * @param name
	 *            the name
	 * @param classLoader
	 *            the class loader
	 * @return the serializable
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public Serializable retrieveSettings(String name, ClassLoader classLoader)
			throws IOException, ClassNotFoundException {
		File dir = this.getSettingsDirectory();
		if (!dir.exists()) {
			return null;
		}
		File file = new File(dir, name);
		if (!file.exists()) {
			return null;
		}
		InputStream in = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(in);
		ObjectInputStream ois = new ClassLoaderObjectInputStream(bin, classLoader);
		try {
			try {
				return (Serializable) ois.readObject();
			} catch (InvalidClassException ice) {
				logger.error(ice.getMessage());
				return null;
			}
		} finally {
			ois.close();
			bin.close();
			in.close();
		}
	}

	/**
	 * Gets the settings directory.
	 *
	 * @return the settings directory
	 */
	public File getSettingsDirectory() {
		File homeDir = new File(System.getProperty("user.home"));
		File settingsDir = new File(homeDir, STORE_DIR_NAME);
		return new File(settingsDir, SETTINGS_DIR);
	}
}
