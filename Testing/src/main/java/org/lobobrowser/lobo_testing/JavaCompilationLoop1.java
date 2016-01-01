/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.lobo_testing;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.lobobrowser.context.VolatileContentImpl;
import org.lobobrowser.gui.BrowserPanel;
import org.lobobrowser.main.PlatformInit;
import org.lobobrowser.store.CacheInfo;
import org.lobobrowser.store.CacheManager;

/**
 * The Class JavaCompilationLoop1.
 */
public class JavaCompilationLoop1 {
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
		byte[] content = getNewContent();
		VolatileContentImpl vc = new VolatileContentImpl("text/x-java-source; charset=UTF-8", content);
		return vc.getURL().toExternalForm();
	}

	/**
	 * Gets the new content.
	 *
	 * @return the new content
	 * @throws Exception
	 *             the exception
	 */
	private static byte[] getNewContent() throws Exception {
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		out.println("package org.lobobrowser.www;");
		out.println("import javax.swing.*;");
		out.println("import java.awt.*;");
		out.println("public class MyTestClass extends JPanel {");
		out.println("  public MyTestClass() {");
		out.println("     setLayout(new BorderLayout());");
		out.println("     JPanel subPanel = new JPanel();");
		for (int i = 0; i < 1000; i++) {
			out.println("    subPanel.add(new JButton(\"This is button #" + i + "\"));");
		}
		out.println("     JScrollPane scrollPane = new JScrollPane(subPanel);");
		out.println("     this.add(scrollPane, BorderLayout.CENTER);");
		out.println("  }");
		out.println("}");
		out.flush();
		return writer.toString().getBytes("UTF-8");
	}
}
