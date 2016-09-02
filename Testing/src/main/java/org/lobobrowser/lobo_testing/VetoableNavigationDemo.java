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

import java.awt.Component;
import java.awt.event.WindowEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.lobobrowser.gui.BrowserPanel;
import org.lobobrowser.gui.NavigationAdapter;
import org.lobobrowser.main.PlatformInit;
import org.lobobrowser.ua.NavigationEvent;
import org.lobobrowser.ua.NavigationVetoException;
import org.lobobrowser.w3c.html.HTMLElement;

/**
 * The Class VetoableNavigationDemo.
 */
public class VetoableNavigationDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception {
		// We'll disable all logging but WARNING.
		LogManager.getLogger("org.lobobrowser");

		// This step is necessary for extensions (including HTML) to work:
		PlatformInit.getInstance().init(false);

		// Create window with a specific size.
		JFrame frame = new VetoableNavigationDemo();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public VetoableNavigationDemo() throws Exception {
		this.setTitle("Lobo Vetoable Navigation Demo");

		// Create a BrowserPanel and set a default home page.
		final BrowserPanel bp = new BrowserPanel();
		bp.navigate("http://www.google.com");

		// Add a navigation listener.
		bp.addNavigationListener(new LocalNavigationListener());

		// Add top-level components to window.
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(bp);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// This needs to be called in order
				// to inform extensions about the
				// window closing.
				bp.windowClosing();
			}
		});
	}

	/**
	 * The listener interface for receiving localNavigation events. The class
	 * that is interested in processing a localNavigation event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's <code>addLocalNavigationListener
	 * <code> method. When the localNavigation event occurs, that object's
	 * appropriate method is invoked.
	 *
	 * @see LocalNavigationEvent
	 */
	private class LocalNavigationListener extends NavigationAdapter {
		@Override
		public void beforeLocalNavigate(NavigationEvent event) throws NavigationVetoException {
			Object linkObject = event.getLinkObject();
			if (linkObject instanceof HTMLElement) {
				HTMLElement linkElement = (HTMLElement) linkObject;
				String rel = linkElement.getAttribute("rel");
				if ("nofollow".equalsIgnoreCase(rel)) {
					Component dialogParent = event.getOriginatingFrame().getComponent();
					int response = JOptionPane.showConfirmDialog(dialogParent,
							"This is a rel='nofollow' link. Are you sure you want to continue?", "Please Confirm",
							JOptionPane.YES_NO_OPTION);
					if (response != JOptionPane.YES_OPTION) {
						throw new NavigationVetoException();
					}
				}
			}
		}
	}
}
