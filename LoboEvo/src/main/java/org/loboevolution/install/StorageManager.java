package org.loboevolution.install;

import java.awt.Toolkit;
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

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import org.loboevolution.common.Files;
import org.loboevolution.common.Strings;
import org.loboevolution.init.GuiInit;
import org.loboevolution.store.SQLiteCommon;

/**
 * <p>StorageManager class.</p>
 *
 * @author utente
 * @version $Id: $Id
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

	/*
	 * Executed in background thread.
	 */
	/** {@inheritDoc} */
	@Override
	public Void doInBackground() {
		setProgress(0);

		try {

			SQLiteCommon.createDatabaseDirectory();
			ClassLoader.getSystemClassLoader();
			int count = 1;
			for (final String name : getList()) {
				try (InputStream input = ClassLoader.getSystemResourceAsStream("org/lobo/storage/" + name)) {
					populateDatabse(Files.getResourceAsFile(input));
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

	/*
	 * Executed in event dispatching thread
	 */
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
		files.add(0, "table.sql");
		files.add("char.sql");
		files.add("color.sql");
		files.add("font_size.sql");
		files.add("font.sql");
		files.add("network.sql");
		files.add("searchEngine.sql");
		files.add("size.sql");
		files.add("userAgent.sql");
		files.add("lookAndFeel.sql");
		return files;
	}

	private void populateDatabse(File fl) {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				Statement stmt = conn.createStatement()) {
			String s = new String();
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
