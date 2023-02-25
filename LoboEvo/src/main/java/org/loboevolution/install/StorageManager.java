/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.install;

import org.loboevolution.common.Files;
import org.loboevolution.common.Strings;
import org.loboevolution.init.GuiInit;
import org.loboevolution.store.DatabseSQLite;
import org.loboevolution.store.DesktopStore;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>StorageManager class.</p>
 */
public class StorageManager extends SwingWorker<Void, Void> {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(StorageManager.class.getName());

	private final JFrame frame;

	/**
	 * <p>Constructor for StorageManager.</p>
	 *
	 * @param frame a {@link javax.swing.JFrame} object.
	 */
	public StorageManager(JFrame frame) {
		this.frame = frame;
	}


	/** {@inheritDoc} */
	@Override
	public Void doInBackground() {
		setProgress(0);

		try {
			DatabseSQLite.createDatabaseDirectory();
			DesktopStore.createWallpapersDirectory(DesktopStore.PATH_IMAGE);
			DesktopStore.createWallpapersDirectory(DesktopStore.PATH_WELCOME);
			ClassLoader.getSystemClassLoader();
			int count = 1;
			for (final String name : getList()) {
				try (InputStream input = ClassLoader.getSystemResourceAsStream(name)) {
					if (name.contains("storage")) {
						populateDatabase(Files.getResourceAsFile(input));
					} else if (name.contains(DesktopStore.PATH_WELCOME)) {
						DesktopStore.createWallpapersFile(input, DesktopStore.PATH_WELCOME, name);
					} else {
						DesktopStore.createWallpapersFile(input, DesktopStore.PATH_IMAGE, name);
					}
				}

				setProgress(Math.min(count * 10, 100));
				Thread.sleep(500);
				count++;
			}

			setProgress(Math.min(100, 100));
			Thread.sleep(1000);

		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void done() {
		try {
			Toolkit.getDefaultToolkit().beep();
			this.frame.dispose();
			this.frame.setVisible(false);
			GuiInit.createAndShowGui();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private List<String> getList() {
		final List<String> files = new ArrayList<>();
		files.add(0, "org/lobo/storage/table.sql");
		files.add("org/lobo/storage/char.sql");
		files.add("org/lobo/storage/font_size.sql");
		files.add("org/lobo/storage/font.sql");
		files.add("org/lobo/storage/network.sql");
		files.add("org/lobo/storage/searchEngine.sql");
		files.add("org/lobo/storage/size.sql");
		files.add("org/lobo/storage/lookAndFeel.sql");

		files.add("org/lobo/image/welcome/galaxy-g8ac4ab980_640.jpg");
		files.add("org/lobo/image/welcome/wolf-g9d8e30cbc_640.jpg");
		files.add("org/lobo/image/welcome/wolf-g5692c130a_640.jpg");
		files.add("org/lobo/image/welcome/wolf-g26964a871_640.jpg");
		files.add("org/lobo/image/welcome/wolf-gcc0cb3fd8_640.jpg");
		files.add("org/lobo/image/welcome/wolf-gd9365180a_640.jpg");
		files.add("org/lobo/image/welcome/wolf-gecf98a8c9_640.jpg");
		files.add("org/lobo/image/welcome/wolf-gf3835d81e_640.png");
		files.add("org/lobo/image/welcome/wolves-gccc236798_640.jpg");

		files.add("org/lobo/image/back.png");
		files.add("org/lobo/image/bookmark.png");
		files.add("org/lobo/image/copy.png");
		files.add("org/lobo/image/download.png");
		files.add("org/lobo/image/forward.png");
		files.add("org/lobo/image/go.png");
		files.add("org/lobo/image/home.png");
		files.add("org/lobo/image/host.png");
		files.add("org/lobo/image/icon.png");
		files.add("org/lobo/image/lobo.png");
		files.add("org/lobo/image/print.png");
		files.add("org/lobo/image/reload.png");
		files.add("org/lobo/image/save.png");
		files.add("org/lobo/image/search.png");
		files.add("org/lobo/image/zoomin.png");
		files.add("org/lobo/image/zoomout.png");

		return files;
	}

	private void populateDatabase(File fl) {
		try (Connection conn = DriverManager.getConnection(DatabseSQLite.getDatabaseDirectory());
				Statement stmt = conn.createStatement()) {
			String s;
			final StringBuilder sb = new StringBuilder();
			final boolean isTable = "table.sql".equals(fl.getName());
			try (FileReader fr = new FileReader(fl); BufferedReader br = new BufferedReader(fr)) {
				while ((s = br.readLine()) != null) {
					sb.append(s);
				}
			}

			for (final String sql : Strings.splitUsingTokenizer(sb.toString(), ";")) {
				if (Strings.isNotBlank(sql)) {
					if (isTable) {
						stmt.execute(sql);
					} else {
						stmt.executeUpdate(sql);
					}
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
