/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.welcome;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JLabel;

import org.loboevolution.common.Strings;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.ITabbedPane;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.SearchEngineStore;
import org.loboevolution.store.TabStore;

import com.jtattoo.plaf.lobo.LoboLookAndFeel;
import org.loboevolution.store.ToolsStore;

/**
 * <p>LoginButton class.</p>
 */
public class LoginButton extends JLabel implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;

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
	public LoginButton(final IBrowserPanel panel, final TextFieldUsername text) {

        Color COLOR_BACKGROUND = background();
        setBackground(COLOR_BACKGROUND);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(final MouseEvent e) {
				LoginButton.this.loginButtonColors[0] = LoginButton.this.COLOR_INTERACTIVE_DARKER;
				LoginButton.this.loginButtonColors[1] = LoginButton.this.OFFWHITE;
				repaint();
			}

			@Override
			public void mouseExited(final MouseEvent e) {
				LoginButton.this.loginButtonColors[0] = LoginButton.this.COLOR_INTERACTIVE;
				LoginButton.this.loginButtonColors[1] = LoginButton.this.foreground();
				repaint();
			}

			@Override
			public void mousePressed(final MouseEvent me) {
				final int indexPanel = panel.getTabbedPane().getIndex();
				panel.getTabbedPane().remove(indexPanel);
				final ITabbedPane tabbedPane = panel.getTabbedPane();
				tabbedPane.setComponentPopupMenu(panel);
				final HtmlPanel hpanel = NavigationManager.getHtmlPanelSearch(panel, text.getText());
				final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
				final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
				tabbedPane.insertTab(title, null, hpanel, title, indexPanel);
				tabbedPane.setSelectedIndex(indexPanel);
				final IBrowserPanel bpanel = hpanel.getBrowserPanel();
				bpanel.getScroll().getViewport().add((Component)tabbedPane);
				TabStore.deleteTab(indexPanel);
				final SearchEngineStore searchEngine = new ToolsStore().getSelectedSearchEngine();
				final String url = searchEngine.getBaseUrl() + text.getText();
				TabStore.insertTab(indexPanel, url, title);
				NavigationManager.insertHistory(url, title, indexPanel);
			}
		});
	}

	private Graphics2D get2dGraphics(final Graphics g) {
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
	protected void paintBorder(final Graphics g) {
		final Graphics2D g2 = get2dGraphics(g);
		g2.setColor(foreground());
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(final Graphics g) {
		final Graphics2D g2 = get2dGraphics(g);
		super.paintComponent(g2);

		final Insets insets = getInsets();
		final int w = getWidth() - insets.left - insets.right;
		final int h = getHeight() - insets.top - insets.bottom;
		g2.setColor(this.loginButtonColors[0]);
		g2.fillRoundRect(insets.left, insets.top, w, h, this.ROUNDNESS, this.ROUNDNESS);

		final FontMetrics metrics = g2.getFontMetrics(this.FONT_GENERAL_UI);
        String BUTTON_TEXT_LOGIN = "Surf!";
        final int x2 = (getWidth() - metrics.stringWidth(BUTTON_TEXT_LOGIN)) / 2;
		final int y2 = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
		g2.setFont(this.FONT_GENERAL_UI);
		g2.setColor(this.loginButtonColors[1]);
		g2.drawString(BUTTON_TEXT_LOGIN, x2, y2);
	}
}
