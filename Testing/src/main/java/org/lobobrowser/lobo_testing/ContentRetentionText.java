/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

import java.awt.Component;

import org.lobobrowser.clientlet.AbstractComponentContent;
import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ClientletSelector;
import org.lobobrowser.context.ClientletFactory;
import org.lobobrowser.context.VolatileContentImpl;
import org.lobobrowser.gui.BrowserPanel;
import org.lobobrowser.main.PlatformInit;
import org.lobobrowser.store.CacheInfo;
import org.lobobrowser.store.CacheManager;

/**
 * The Class ContentRetentionText.
 */
public class ContentRetentionText {
	public static void main(String[] args) throws Exception {
		PlatformInit.getInstance().init(false, false);
		ClientletFactory.getInstance().addClientletSelector(new LocalClientletSelector());
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
		System.out.println("### RAM cache entries: " + cacheInfo.numEntries);
		System.out.println("### RAM cache size: " + cacheInfo.approximateSize);
	}

	/**
	 * Gets the new url.
	 *
	 * @return the new url
	 */
	public static String getNewURL() {
		VolatileContentImpl vc = new VolatileContentImpl("text/html", new byte[0]);
		return vc.getURL().toExternalForm();
	}

	/**
	 * The Class LocalClientletSelector.
	 */
	private static class LocalClientletSelector implements ClientletSelector {
		@Override
		public Clientlet lastResortSelect(ClientletRequest request, ClientletResponse response) {
			return null;
		}

		@Override
		public Clientlet select(ClientletRequest request, ClientletResponse response) {
			if (request.getRequestURL().getProtocol().equals("vc")) {
				return new LocalClientlet();
			} else {
				return null;
			}
		}
	}

	/**
	 * The Class LocalClientlet.
	 */
	private static class LocalClientlet implements Clientlet {
		@Override
		public void process(ClientletContext context) throws ClientletException {
			System.out.println("### Setting component content");
			context.setResultingContent(new LocalComponentContent());
		}
	}

	/**
	 * The Class LocalComponentContent.
	 */
	private static class LocalComponentContent extends AbstractComponentContent {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.clientlet.AbstractComponentContent#getComponent()
		 */
		@Override
		public Component getComponent() {
			return new javax.swing.JLabel("The content");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.clientlet.AbstractComponentContent#getSourceCode()
		 */
		@Override
		public String getSourceCode() {
			return "";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.lobobrowser.clientlet.AbstractComponentContent#getTitle()
		 */
		@Override
		public String getTitle() {
			return "The title";
		}
	}
}
