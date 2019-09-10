package org.lobo.welcome;

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

import org.lobo.common.Strings;
import org.lobo.component.IBrowserPanel;
import org.lobo.http.NavigationManager;
import org.lobo.store.TabStore;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.gui.HtmlPanel;

public class LoginButton extends JLabel {
	private static final long serialVersionUID = 1L;

	private final String BUTTON_TEXT_LOGIN = "Surf!";

	private final Color COLOR_BACKGROUND = new Color(37, 51, 61);

	private final Color COLOR_INTERACTIVE = new Color(108, 216, 158);

	private final Color COLOR_INTERACTIVE_DARKER = new Color(87, 171, 127);

	private final Font FONT_GENERAL_UI = new Font("Segoe UI", Font.PLAIN, 20);

	private final Color[] loginButtonColors = { this.COLOR_INTERACTIVE, Color.white };

	private final Color OFFWHITE = new Color(229, 229, 229);

	private final int ROUNDNESS = 8;

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
				LoginButton.this.loginButtonColors[1] = Color.white;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent me) {
				final int indexPanel = panel.getTabbedPane().getIndex();
				panel.getTabbedPane().remove(indexPanel);
				final DnDTabbedPane tabbedPane = panel.getTabbedPane();
				tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
				HtmlPanel hpanel = NavigationManager.getHtmlPanelSearch(text.getText(), indexPanel);
				final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
				final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";	
				tabbedPane.insertTab(title, null, hpanel, title, indexPanel);
				tabbedPane.setSelectedIndex(indexPanel);
				final IBrowserPanel bpanel = hpanel.getBrowserPanel();
				bpanel.getScroll().getViewport().add(tabbedPane);
				TabStore.deleteTab(indexPanel);
				TabStore.insertTab(indexPanel, text.getText(), title);
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