/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

	private static final String PATH_IMAGE = "org/lobo/image/";

	private static final String PATH_PDF_IMAGE = "org/lobo/pdfview/";

	private static final String PATH_WELCOME = "org/lobo/welcome/";

	private static final String PATH_STORE = "org/lobo/storage/";

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
			final DesktopConfig desktopConfig = new DesktopConfig();
			desktopConfig.createDatabaseDirectory();
			desktopConfig.createDirectory(DesktopConfig.PATH_IMAGE);
			desktopConfig.createDirectory(DesktopConfig.PATH_PDF_IMAGE);
			desktopConfig.createDirectory(DesktopConfig.PATH_WELCOME);
			ClassLoader.getSystemClassLoader();
			int count = 1;
			for (final String name : getList()) {
				try (final InputStream input = ClassLoader.getSystemResourceAsStream(name)) {
					if (name.contains("storage")) {
						populateDatabase(Files.getResourceAsFile(input));
					} else {
						final String path = name.contains(DesktopConfig.PATH_WELCOME) ? DesktopConfig.PATH_WELCOME
								: name.contains(DesktopConfig.PATH_IMAGE) ? DesktopConfig.PATH_IMAGE
								: DesktopConfig.PATH_PDF_IMAGE;
						desktopConfig.createFile(input, path, name);
					}
				}

				setProgress(Math.min(count * 10, 100));
				Thread.sleep(500);
				count++;
			}

			setProgress(100);
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

		files.addFirst("org/lobo/storage/table.sql");
		files.add(PATH_STORE + "char.sql");
		files.add(PATH_STORE + "font_size.sql");
		files.add(PATH_STORE + "font.sql");
		files.add(PATH_STORE + "network.sql");
		files.add(PATH_STORE + "searchEngine.sql");
		files.add(PATH_STORE + "size.sql");
		files.add(PATH_STORE + "lookAndFeel.sql");

		files.add(PATH_WELCOME + "galaxy-g8ac4ab980_640.jpg");
		files.add(PATH_WELCOME + "wolf-g9d8e30cbc_640.jpg");
		files.add(PATH_WELCOME + "wolf-g5692c130a_640.jpg");
		files.add(PATH_WELCOME + "wolf-g26964a871_640.jpg");
		files.add(PATH_WELCOME + "wolf-gcc0cb3fd8_640.jpg");
		files.add(PATH_WELCOME + "wolf-gd9365180a_640.jpg");
		files.add(PATH_WELCOME + "wolf-gecf98a8c9_640.jpg");
		files.add(PATH_WELCOME + "wolf-gf3835d81e_640.png");
		files.add(PATH_WELCOME + "wolves-gccc236798_640.jpg");

		files.add(PATH_IMAGE + "back.png");
		files.add(PATH_IMAGE + "bookmark.png");
		files.add(PATH_IMAGE + "copy.png");
		files.add(PATH_IMAGE + "download.png");
		files.add(PATH_IMAGE + "forward.png");
		files.add(PATH_IMAGE + "go.png");
		files.add(PATH_IMAGE + "home.png");
		files.add(PATH_IMAGE + "host.png");
		files.add(PATH_IMAGE + "icon.png");
		files.add(PATH_IMAGE + "lobo.png");
		files.add(PATH_IMAGE + "print.png");
		files.add(PATH_IMAGE + "reload.png");
		files.add(PATH_IMAGE + "save.png");
		files.add(PATH_IMAGE + "search.png");
		files.add(PATH_IMAGE + "zoomin.png");
		files.add(PATH_IMAGE + "zoomout.png");

		files.add(PATH_PDF_IMAGE + "zoomin.png");
		files.add(PATH_PDF_IMAGE + "zoomout.png");
		files.add(PATH_PDF_IMAGE + "fit.png");
		files.add(PATH_PDF_IMAGE + "fit-height.png");
		files.add(PATH_PDF_IMAGE + "print.png");
		files.add(PATH_PDF_IMAGE + "print-setup.png");
		files.add(PATH_PDF_IMAGE + "fback.gif");
		files.add(PATH_PDF_IMAGE + "back.gif");
		files.add(PATH_PDF_IMAGE + "forward.gif");
		files.add(PATH_PDF_IMAGE + "fforward.gif");
		files.add(PATH_PDF_IMAGE + "end.gif");
		files.add(PATH_PDF_IMAGE + "start.gif");


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
