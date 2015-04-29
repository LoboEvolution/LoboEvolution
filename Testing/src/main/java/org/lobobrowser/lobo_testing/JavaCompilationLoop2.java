/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.lobo_testing;

import org.lobobrowser.gui.*;
import org.lobobrowser.main.*;
import org.lobobrowser.store.*;


/**
 * The Class JavaCompilationLoop2.
 */
public class JavaCompilationLoop2 {
    public static void main(String[] args) throws Exception {
        PlatformInit.getInstance().init(false, false);
        PlatformInit.getInstance().initLogging(false);
        BrowserPanel panel = TestWindow.newWindow();
        for (int i = 0; i < 100; i++) {
            newTest(panel);
        }
    }

    public static void newTest(BrowserPanel panel) throws Exception {
        panel.navigate(getNewURL());
        System.gc();
        Thread.sleep(5000);
        System.out.println("### Free memory: "
                + Runtime.getRuntime().freeMemory());
        CacheInfo cacheInfo = CacheManager.getInstance()
                .getTransientCacheInfo();
        System.out.println("### RAM cache entries: " + cacheInfo.numEntries);
        System.out.println("### RAM cache size: " + cacheInfo.approximateSize);
    }

    /**
     * Gets the new url.
     *
     * @return the new url
     * @throws Exception the exception
     */
    public static String getNewURL() throws Exception {
        return "http://lobobrowser.org/ext/jweb/ChartDemo.java?"
                + System.nanoTime();
    }
}
