/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.init;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.tab.DnDTabbedPane;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

/**
 * <p>PlatformInit class.</p>
 *
 *
 *
 */
public class PlatformInit {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PlatformInit.class.getName());

	/**
	 * <p>main.</p>
	 *
	 * @param args a {@link java.lang.String} object.
	 */
	public static void main(String... args) {
		SwingUtilities.invokeLater(() -> {
			try {
				BrowserFrame frame = new GuiInit().install();
				// TODO if script provided
				// run script
				frame.getToolbar().getAddressBar().setText("https://netflix.com/login");

				new Thread(new Runnable() {
					@Override public void run() {
						try {
							Thread.sleep(20 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						HtmlPanel panel = (HtmlPanel) ((DnDTabbedPane)frame.getPanel().getScroll().getComponent(0).getComponentAt(0, 0)).getComponent(0);
						// panel.getRootNode().findUINode();
						PrintConsoleTree(panel.getRootNode());
						//String html = panel.getRootNode().getInnerHTML();
						//System.out.println(html);

					}
				}).start();
			} catch (final Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		});
	}

	private static void PrintConsoleTree(Node node) {
		PrintConsoleTree(node, 0);
	}

	private static void PrintConsoleTree(Node node, int level) {
		PrintNodeDetails(node, level);
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			PrintConsoleTree(children.item(i), level + 1);
		}
	}

	private static void PrintNodeDetails(Node node, int level) {
		System.out.print(spaces(level) + node.getNodeName());
		if (node instanceof Element) {
			Element element = (Element) node;

			String id = element.getAttribute("id");
			String cls = element.getAttribute("class");

			System.out.print(" " + (id == null ? "" : "#" + id) + (cls == null ? "" : " [" + cls + "])"));

			// TODO ... find inputs ... send keys ... find button send click ... 
		}

		System.out.println();
	}

	private static String spaces(int level) {
		String space = "";
		for (int i = 0; i < level; i++) {
			space += "  ";
		}
		return space;
	}
}
