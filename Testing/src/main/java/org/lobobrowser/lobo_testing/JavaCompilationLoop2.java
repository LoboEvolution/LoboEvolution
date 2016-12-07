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

import org.lobobrowser.gui.BrowserPanel;
import org.lobobrowser.main.PlatformInit;
import org.lobobrowser.store.CacheInfo;
import org.lobobrowser.store.CacheManager;

/**
 * The Class JavaCompilationLoop2.
 */
public class JavaCompilationLoop2 {
	public static void main(String[] args) throws Exception {
		PlatformInit.getInstance().init(false);
		PlatformInit.getInstance().initLogging();
		BrowserPanel panel = TestWindow.newWindow();
		for (int i = 0; i < 100; i++) {
			newTest(panel);
		}
	}

	public static void newTest(BrowserPanel panel) throws Exception {
		panel.navigate(getNewURL());
		Runtime.getRuntime().gc();
		Thread.sleep(5000);
		System.out.println("### Free memory: " + Runtime.getRuntime().freeMemory());
		CacheInfo cacheInfo = CacheManager.getInstance().getTransientCacheInfo();
		System.out.println("### RAM cache entries: " + cacheInfo.getNumEntries());
		System.out.println("### RAM cache size: " + cacheInfo.getApproximateSize());
	}

	/**
	 * Gets the new url.
	 *
	 * @return the new url
	 * @throws Exception
	 *             the exception
	 */
	public static String getNewURL() throws Exception {
		return "http://lobobrowser.org/ext/jweb/ChartDemo.java?" + System.nanoTime();
	}
}
