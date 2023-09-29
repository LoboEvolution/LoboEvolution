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

package org.loboevolution.install;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Files;
import org.loboevolution.common.Strings;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.init.GuiInit;
import org.loboevolution.store.DatabseSQLite;

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

/**
 * <p>StorageManager class.</p>
 */
@Slf4j
public class StorageManager extends SwingWorker<Void, Void> {

	private final JFrame frame;

	/**
	 * <p>Constructor for StorageManager.</p>
	 *
	 * @param frame a {@link javax.swing.JFrame} object.
	 */
	public StorageManager(final JFrame frame) {
		this.frame = frame;
	}


	/** {@inheritDoc} */
	@Override
	public Void doInBackground() {
		setProgress(0);

		try {
			DatabseSQLite.createDatabaseDirectory();
			DesktopConfig.createWallpapersDirectory(DesktopConfig.PATH_IMAGE);
			DesktopConfig.createWallpapersDirectory(DesktopConfig.PATH_WELCOME);
			ClassLoader.getSystemClassLoader();
			int count = 1;
			for (final String name : getList()) {
				try (final InputStream input = ClassLoader.getSystemResourceAsStream(name)) {
					if (name.contains("storage")) {
						populateDatabase(Files.getResourceAsFile(input));
					} else if (name.contains(DesktopConfig.PATH_WELCOME)) {
						DesktopConfig.createWallpapersFile(input, DesktopConfig.PATH_WELCOME, name);
					} else {
						DesktopConfig.createWallpapersFile(input, DesktopConfig.PATH_IMAGE, name);
					}
				}

				setProgress(Math.min(count * 10, 100));
				Thread.sleep(500);
				count++;
			}

			setProgress(Math.min(100, 100));
			Thread.sleep(1000);

		} catch (final Exception e) {
			log.error(e.getMessage(), e);
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
			log.error(e.getMessage(), e);
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

	private void populateDatabase(final File fl) {
		try (final Connection conn = DriverManager.getConnection(DatabseSQLite.getDatabaseDirectory());
             final Statement stmt = conn.createStatement()) {
			String s;
			final StringBuilder sb = new StringBuilder();
			final boolean isTable = "table.sql".equals(fl.getName());
			try (final FileReader fr = new FileReader(fl); final BufferedReader br = new BufferedReader(fr)) {
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
			log.error(e.getMessage(), e);
		}
	}
}
