package org.loboevolution.menu.tools.pref.data;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>BrowserData class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class BrowserData {

	/**
	 * Gets the chrome directory.
	 *
	 * @return the chrome directory
	 */
	protected static String getChromeDirectory() {

		// pre Win7
		String filePath = System.getProperty("user.home") + "\\Application Data\\Google\\Chrome\\User Data\\Default\\";
		boolean isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Win 7+
		filePath = System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Mac
		filePath = System.getProperty("user.home") + "/Library/Application Support/Google/Chrome/Default/";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Linux
		filePath = System.getProperty("user.home") + "/.config/chromium/Default/";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		return "";
	}

	/**
	 * <p>getFiles.</p>
	 *
	 * @param directoryPath a {@link java.lang.String} object.
	 * @param cookieFilePaths a {@link java.util.List} object.
	 * @param fileName a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	protected static List<String> getFiles(String directoryPath, List<String> cookieFilePaths, String fileName) {
		if (cookieFilePaths == null) {
			cookieFilePaths = new ArrayList<String>();
		}

		try {
			final DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath));
			for (final Path filePath : stream) {
				if (filePath.getFileName().toString().equals(fileName)) {
					cookieFilePaths.add(filePath.toAbsolutePath().toString());
				} else if (new File(filePath.toAbsolutePath().toString()).isDirectory()) {
					getFiles(filePath.toAbsolutePath().toString(), cookieFilePaths, fileName);
				}
			}
		} catch (final Exception x) {
			x.printStackTrace();
		}
		return cookieFilePaths;
	}

	/**
	 * Gets the mozilla directory.
	 *
	 * @return the mozilla directory
	 */
	protected static String getMozillaDirectory() {

		// pre Win7
		String filePath = System.getProperty("user.home") + "\\Application Data\\Roaming\\Mozilla\\Firefox\\Profiles\\";
		boolean isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Win 7+
		filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Mac
		filePath = System.getProperty("user.home") + "/Library/Application Support/Mozilla/Firefox/Profiles/";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		return "";
	}

}
