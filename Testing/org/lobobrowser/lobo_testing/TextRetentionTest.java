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
import org.lobobrowser.util.*;
import org.lobobrowser.context.*;
import org.lobobrowser.store.*;

// TODO: Auto-generated Javadoc
/**
 * The Class TextRetentionTest.
 */
public class TextRetentionTest {
    public static void main(String[] args) throws Exception {
        PlatformInit.getInstance().init(false, false);
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
        System.out.println("### Total memory: "
                + Runtime.getRuntime().totalMemory());
        CacheInfo cacheInfo = CacheManager.getInstance()
                .getTransientCacheInfo();
        System.out.println("### RAM cache entries: " + cacheInfo.numEntries);
        System.out.println("### RAM cache size: " + cacheInfo.approximateSize);
    }

    /** The retain vc. */
    private static Object retainVc;

    /**
     * Gets the new url.
     *
     * @return the new url
     */
    public static String getNewURL() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<body>");
        int total = 10000;
        for (int i = 0; i < total; i++) {
            buffer.append("<div>This is line # " + i + " of " + total
                    + " lines.</div>");
        }
        buffer.append("</body>");
        byte[] content = buffer.toString().getBytes();
        VolatileContentImpl vc = new VolatileContentImpl("text/html", content);
        retainVc = vc;
        return vc.getURL().toExternalForm();
    }
}
