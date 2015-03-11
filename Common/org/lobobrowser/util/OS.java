/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on May 14, 2005
 */
package org.lobobrowser.util;

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
	 * @param url the url
	 * @throws IOException Signals that an I/O exception has occurred.
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
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void launchPath(String path) throws IOException {
		if (isWindows()) {
			Runtime.getRuntime()
					.exec(new String[] { "cmd.exe", "/c", "start", "\"title\"",
							path });
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
