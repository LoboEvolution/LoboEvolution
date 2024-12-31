/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.menu.tools.pref.data;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>BrowserData class.</p>
 */
@Slf4j
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
	protected static List<String> getFiles(final String directoryPath, List<String> cookieFilePaths, final String fileName) {
		if (cookieFilePaths == null) {
			cookieFilePaths = new ArrayList<>();
		}

		try (final DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath))) {
			for (final Path filePath : stream) {
				if (filePath.getFileName().toString().equals(fileName)) {
					cookieFilePaths.add(filePath.toAbsolutePath().toString());
				} else if (new File(filePath.toAbsolutePath().toString()).isDirectory()) {
					getFiles(filePath.toAbsolutePath().toString(), cookieFilePaths, fileName);
				}
			}
		} catch (final Exception ex) {
			log.error(ex.getMessage(), ex);
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
