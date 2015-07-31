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

import java.awt.event.WindowEvent;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import org.lobobrowser.gui.*;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.ua.*;
import org.lobobrowser.main.PlatformInit;


/**
 * The Class VetoableNavigationDemo.
 */
public class VetoableNavigationDemo extends JFrame {
    
    private static final long serialVersionUID = 1L;
	/** The browser panel. */
    private final BrowserPanel browserPanel;

    public static void main(String[] args) throws Exception {
        // We'll disable all logging but WARNING.
        Logger.getLogger("org.lobobrowser").setLevel(Level.WARNING);

        // This step is necessary for extensions (including HTML) to work:
        PlatformInit.getInstance().init(false, false);

        // Create window with a specific size.
        JFrame frame = new VetoableNavigationDemo();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    public VetoableNavigationDemo() throws Exception {
        this.setTitle("Lobo Vetoable Navigation Demo");

        // Create a BrowserPanel and set a default home page.
        final BrowserPanel bp = new BrowserPanel();
        this.browserPanel = bp;
        bp.navigate("http://www.google.com");

        // Add a navigation listener.
        bp.addNavigationListener(new LocalNavigationListener());

        // Add top-level components to window.
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(bp);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // This needs to be called in order
                // to inform extensions about the
                // window closing.
                bp.windowClosing();
            }
        });
    }

    /**
     * The listener interface for receiving localNavigation events.
     * The class that is interested in processing a localNavigation
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addLocalNavigationListener<code> method. When
     * the localNavigation event occurs, that object's appropriate
     * method is invoked.
     *
     * @see LocalNavigationEvent
     */
    private class LocalNavigationListener extends NavigationAdapter {
        @Override
        public void beforeLocalNavigate(NavigationEvent event)
                throws NavigationVetoException {
            Object linkObject = event.getLinkObject();
            if (linkObject instanceof HTMLElement) {
                HTMLElement linkElement = (HTMLElement) linkObject;
                String rel = linkElement.getAttribute("rel");
                if ("nofollow".equalsIgnoreCase(rel)) {
                    Component dialogParent = event.getOriginatingFrame()
                            .getComponent();
                    int response = JOptionPane
                            .showConfirmDialog(
                                    dialogParent,
                                    "This is a rel='nofollow' link. Are you sure you want to continue?",
                                    "Please Confirm", JOptionPane.YES_NO_OPTION);
                    if (response != JOptionPane.YES_OPTION) {
                        throw new NavigationVetoException();
                    }
                }
            }
        }
    }
}
