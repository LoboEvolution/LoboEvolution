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

package org.loboevolution.welcome;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JLabel;

import org.loboevolution.common.Strings;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;

import com.jtattoo.plaf.lobo.LoboLookAndFeel;

/**
 * <p>LoginButton class.</p>
 *
 *
 *
 */
public class LoginButton extends JLabel implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

	private final String BUTTON_TEXT_LOGIN = "Surf!";

	private final Color COLOR_BACKGROUND = background();

	private final Color COLOR_INTERACTIVE = background();

	private final Color COLOR_INTERACTIVE_DARKER = interactive();

	private final Font FONT_GENERAL_UI = new Font("Segoe UI", Font.PLAIN, 20);

	private final Color[] loginButtonColors = { this.COLOR_INTERACTIVE, foreground() };

	private final Color OFFWHITE = new Color(229, 229, 229);

	private final int ROUNDNESS = 8;

	/**
	 * <p>Constructor for LoginButton.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
	 * @param text a {@link org.loboevolution.welcome.TextFieldUsername} object.
	 */
	public LoginButton(IBrowserPanel panel, TextFieldUsername text) {

		setBackground(this.COLOR_BACKGROUND);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				LoginButton.this.loginButtonColors[0] = LoginButton.this.COLOR_INTERACTIVE_DARKER;
				LoginButton.this.loginButtonColors[1] = LoginButton.this.OFFWHITE;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				LoginButton.this.loginButtonColors[0] = LoginButton.this.COLOR_INTERACTIVE;
				LoginButton.this.loginButtonColors[1] = LoginButton.this.foreground();
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent me) {
				final int indexPanel = panel.getTabbedPane().getIndex();
				panel.getTabbedPane().remove(indexPanel);
				final DnDTabbedPane tabbedPane = panel.getTabbedPane();
				tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
				HtmlPanel hpanel = NavigationManager.getHtmlPanelSearch(panel, text.getText());
				final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
				final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
				tabbedPane.insertTab(title, null, hpanel, title, indexPanel);
				tabbedPane.setSelectedIndex(indexPanel);
				final IBrowserPanel bpanel = hpanel.getBrowserPanel();
				bpanel.getScroll().getViewport().add(tabbedPane);
				TabStore.deleteTab(indexPanel);
				TabStore.insertTab(indexPanel, text.getText(), title);
				NavigationManager.insertHistory(text.getText(), title, indexPanel);
			}
		});
	}

	private Graphics2D get2dGraphics(Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		g2.addRenderingHints(new HashMap<RenderingHints.Key, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			}
		});
		return g2;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void paintBorder(Graphics g) {
		final Graphics2D g2 = get2dGraphics(g);
		g2.setColor(foreground());
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(Graphics g) {
		final Graphics2D g2 = get2dGraphics(g);
		super.paintComponent(g2);

		final Insets insets = getInsets();
		final int w = getWidth() - insets.left - insets.right;
		final int h = getHeight() - insets.top - insets.bottom;
		g2.setColor(this.loginButtonColors[0]);
		g2.fillRoundRect(insets.left, insets.top, w, h, this.ROUNDNESS, this.ROUNDNESS);

		final FontMetrics metrics = g2.getFontMetrics(this.FONT_GENERAL_UI);
		final int x2 = (getWidth() - metrics.stringWidth(this.BUTTON_TEXT_LOGIN)) / 2;
		final int y2 = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
		g2.setFont(this.FONT_GENERAL_UI);
		g2.setColor(this.loginButtonColors[1]);
		g2.drawString(this.BUTTON_TEXT_LOGIN, x2, y2);
	}
}
