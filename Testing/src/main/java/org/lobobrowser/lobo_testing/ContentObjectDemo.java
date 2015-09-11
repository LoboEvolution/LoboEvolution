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

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import org.lobobrowser.gui.*;
import org.lobobrowser.ua.*;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLElement;
import org.lobobrowser.main.PlatformInit;
import org.lobobrowser.html.domimpl.*;

/**
 * The Class ContentObjectDemo.
 */
public class ContentObjectDemo extends JFrame {
    
	private static final long serialVersionUID = 1L;

	/** The links combo box. */
    private final JComboBox<LocationItem> linksComboBox;
    
    /** The browser panel. */
    private final BrowserPanel browserPanel;

    public static void main(String[] args) throws Exception {
        // We'll disable all logging but WARNING.
        Logger.getLogger("org.lobobrowser").setLevel(Level.WARNING);

        // This step is necessary for extensions (including HTML) to work:
        PlatformInit.getInstance().init(false, false);

        // Create window with a specific size.
        JFrame frame = new ContentObjectDemo();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    public ContentObjectDemo() throws Exception {
        this.setTitle("Lobo Content Object Demo");

        // Create a BrowserPanel and set a default home page.
        final BrowserPanel bp = new BrowserPanel();
        this.browserPanel = bp;
        // The response listener will tell us when a page
        // has finished loading.
        bp.addResponseListener(new LocalResponseListener());
        bp.navigate("http://www.google.com");

        // Create a links panel containing a combo box and a button.
        JPanel linksPanel = new JPanel();
        linksPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        linksPanel.setLayout(new BoxLayout(linksPanel, BoxLayout.X_AXIS));
        JComboBox<LocationItem> linksComboBox = new JComboBox<LocationItem>();
        this.linksComboBox = linksComboBox;
        JButton linksButton = new JButton();
        linksButton.setAction(new LinksAction());
        linksButton.setText("Go");
        linksPanel.add(linksComboBox);
        linksPanel.add(linksButton);

        // Add top-level components to window.
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(linksPanel);
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

    private void updateLinks() {
        JComboBox<LocationItem> comboBox = this.linksComboBox;
        comboBox.removeAllItems();
        // The content object should be of type HTMLDocumentImpl
        // when we're rendering a HTML page.
        Object contentObject = this.browserPanel.getContentObject();
        // We'll also need the base URL in order to resolve hrefs.
        NavigationEntry navEntry = this.browserPanel
                .getCurrentNavigationEntry();
        java.net.URL baseURL = navEntry == null ? null : navEntry.getUrl();
        if (baseURL == null) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                    "updateLinks(): No URL in current entry.");
            return;
        }
        if (contentObject instanceof HTMLDocumentImpl) {
            HTMLDocumentImpl document = (HTMLDocumentImpl) contentObject;
            // Now we'll look through all the anchors in the page
            // and populate the combo box accordingly.
            HTMLCollection anchors = document.getAnchors();
            int length = anchors.getLength();
            for (int i = 0; i < length; i++) {
                HTMLElement anchor = (HTMLElement) anchors.item(i);
                String href = anchor.getAttribute("href");
                if (href != null) {
                    java.net.URL itemURL;
                    try {
                        itemURL = new java.net.URL(baseURL, href);
                    } catch (java.net.MalformedURLException mfu) {
                        Logger.getLogger(this.getClass().getName()).log(
                                Level.WARNING, "updateLinks()", mfu);
                        continue;
                    }
                    String textContent = anchor.getTextContent();
                    if (textContent == null) {
                        textContent = "(no link text)";
                    } else if (textContent.length() > 60) {
                        textContent = textContent.substring(0, 60) + "...";
                    }
                    LocationItem item = new LocationItem(itemURL, textContent);
                    comboBox.addItem(item);
                }
            }
        }
    }

    /**
     * The Class LinksAction.
     */
    private class LinksAction extends AbstractAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
            LocationItem item = (LocationItem) linksComboBox.getSelectedItem();
            if (item != null) {
                try {
                    browserPanel.navigate(item.url);
                } catch (java.net.MalformedURLException mfu) {
                    Logger.getLogger(this.getClass().getName()).log(
                            Level.WARNING, "actionPerformed()", mfu);
                }
            }
        }
    }

    /**
     * The Class LocationItem.
     */
    private class LocationItem {
        
        /** The url. */
        public final java.net.URL url;
        
        /** The text. */
        public final String text;

        public LocationItem(java.net.URL url, String text) {
            super();
            this.url = url;
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

    /**
     * The listener interface for receiving localResponse events.
     * The class that is interested in processing a localResponse
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addLocalResponseListener<code> method. When
     * the localResponse event occurs, that object's appropriate
     * method is invoked.
     *
     * @see LocalResponseEvent
     */
    private class LocalResponseListener implements ResponseListener {
        public void responseProcessed(ResponseEvent event) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    updateLinks();
                }
            });
        }
    }
}
