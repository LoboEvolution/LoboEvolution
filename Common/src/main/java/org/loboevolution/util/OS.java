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
/*
 * Created on May 14, 2005
 */
package org.loboevolution.util;

import java.io.IOException;

/**
 * The Class OS.
 *
 * @author J. H. S.
 */
public class OS {
	/**
	 * Instantiates a new os.
	 */
	private OS() {
		super();
	}

	/**
	 * Checks if is windows.
	 *
	 * @return true, if is windows
	 */
	public static boolean isWindows() {
		String osName = System.getProperty("os.name");
		return osName.indexOf("Windows") != -1;
	}

	/**
	 * Launch browser.
	 *
	 * @param url
	 *            the url
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void launchBrowser(String url) throws IOException {
		String cmdLine;
		if (isWindows()) {
			cmdLine = "rundll32 url.dll,FileProtocolHandler " + url;
		} else {
			cmdLine = "firefox " + url;
		}
		try {
			Runtime.getRuntime().exec(cmdLine);
		} catch (IOException ioe) {
			Runtime.getRuntime().exec("netscape " + url);
		}
	}

	/**
	 * Opens a file a directory with an appropriate program.
	 *
	 * @param path
	 *            the path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void launchPath(String path) throws IOException {
		if (isWindows()) {
			Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", "start", "\"title\"", path });
		} else {
			throw new UnsupportedOperationException("Unsupported");
		}
	}

	/**
	 * Supports launch path.
	 *
	 * @return true, if successful
	 */
	public static boolean supportsLaunchPath() {
		return isWindows();
	}
}
