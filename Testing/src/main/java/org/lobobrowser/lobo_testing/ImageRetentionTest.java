/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.lobo_testing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.context.VolatileContentImpl;
import org.lobobrowser.gui.BrowserPanel;
import org.lobobrowser.main.PlatformInit;
import org.lobobrowser.store.CacheInfo;
import org.lobobrowser.store.CacheManager;
import org.lobobrowser.util.ID;

/**
 * The Class ImageRetentionTest.
 */
public class ImageRetentionTest {
	public static void main(String[] args) throws Exception {
		LogManager.getLogger("org.lobobrowser");
		PlatformInit.getInstance().init(false);
		BrowserPanel panel = TestWindow.newWindow();
		for (int i = 0; i < 100; i++) {
			newTest(panel);
		}
	}

	public static void newTest(BrowserPanel panel) throws Exception {
		panel.navigate(getNewURL());
		System.gc();
		Thread.sleep(5000);
		System.out.println("### Free memory: " + Runtime.getRuntime().freeMemory());
		System.out.println("### Total memory: " + Runtime.getRuntime().totalMemory());
		CacheInfo cacheInfo = CacheManager.getInstance().getTransientCacheInfo();
		System.out.println("### RAM cache entries: " + cacheInfo.getNumEntries());
		System.out.println("### RAM cache size: " + cacheInfo.getApproximateSize());
	}

	/** The Constant BIG_IMAGE_URL. */
	private static final String BIG_IMAGE_URL = "file:c:\\temp\\image\\google-news.jpg";

	/** The Constant ANIMATED_IMAGE_URL. */
	private static final String ANIMATED_IMAGE_URL = "file:c:\\temp\\image\\tease_stopwatch.gif";

	/** The Constant REMOTE_IMAGE_URL. */
	private static final String REMOTE_IMAGE_URL = "http://lobobrowser.org/images/LoboLogo32.png";

	private static void addImages(StringBuffer buffer, int count, String baseURL) {
		for (int i = 0; i < count; i++) {
			buffer.append("<img width=40 height=30 hspace=4 vspace=4 src=\"");
			buffer.append(baseURL + "?" + ID.generateInt());
			buffer.append("\">");
		}
	}

	/**
	 * Gets the new url.
	 *
	 * @return the new url
	 */
	public static String getNewURL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<body>");
		addImages(buffer, 20, ANIMATED_IMAGE_URL);
		addImages(buffer, 20, BIG_IMAGE_URL);
		addImages(buffer, 1, REMOTE_IMAGE_URL);
		buffer.append("</body>");
		byte[] content = buffer.toString().getBytes();
		VolatileContentImpl vc = new VolatileContentImpl("text/html", content);
		return vc.getURL().toExternalForm();
	}
}
