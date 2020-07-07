package org.loboevolution.menu.tools.clear;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.gui.CheckBoxPanel;
import org.loboevolution.gui.FormPanel;

/**
 * <p>ClearHistoryWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ClearHistoryWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/** The bookmark panel. */
	private CheckBoxPanel bookmarkPanel;

	/** The cache anel. */
	private CheckBoxPanel cachePanel;

	/** The cookie panel. */
	private CheckBoxPanel cookiePanel;

	/** The history button. */
	private JButton historyButton;

	/** The history panel. */
	private FormPanel historyPanel;

	/** The navigation panel. */
	private CheckBoxPanel navigationPanel;
	
	/** The color background. */
	private final Color COLOR_BACKGROUND = new Color(37, 51, 61);
	
	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);
	

	/**
	 * <p>Constructor for ClearHistoryWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public ClearHistoryWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		this.historyPanel = new FormPanel();
		this.cachePanel = new CheckBoxPanel("Cache", this.historyPanel);
		this.cookiePanel = new CheckBoxPanel("Cookies", this.historyPanel);
		this.navigationPanel = new CheckBoxPanel("Navigation", this.historyPanel);
		this.bookmarkPanel = new CheckBoxPanel("Bookmarks", this.historyPanel);

		final JButton historyButton = new JButton();
		historyButton.setAction(new ClearDataAction(this.cachePanel, this.cookiePanel, this.navigationPanel, this.bookmarkPanel));
		historyButton.setText("Delete Now");
		this.historyButton = historyButton;
		this.historyButton.setForeground(COLOR_TEXT);
		this.historyButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.historyButton.setFocusPainted(false);
		this.historyButton.setContentAreaFilled(false);
		this.historyButton.setBorder(new LineBorder(COLOR_TEXT));

		add(getHistoryBox());

	}
	
	/**
	 * Gets the history box.
	 *
	 * @return the history box
	 */
	private Component getHistoryBox() {
		final JPanel groupBox = new JPanel();
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		TitledBorder titledBorder = new TitledBorder(new EtchedBorder(), "Clear History");
		titledBorder.setTitleColor(COLOR_TEXT);
		groupBox.setBorder(new TitledBorder(titledBorder));
		groupBox.setBackground(COLOR_BACKGROUND);
		groupBox.add(getCachePanel());
		groupBox.add(getCookiePanel());
		groupBox.add(getNavigationPanel());
		groupBox.add(getBookmarkPanel());
		groupBox.add(getHistoryButton());
		return groupBox;
	}

	/**
	 * <p>Getter for the field bookmarkPanel.</p>
	 *
	 * @return the bookmarkPanel
	 */
	public CheckBoxPanel getBookmarkPanel() {
		return this.bookmarkPanel;
	}

	/**
	 * <p>Getter for the field cachePanel.</p>
	 *
	 * @return the cachePanel
	 */
	public CheckBoxPanel getCachePanel() {
		return this.cachePanel;
	}

	/**
	 * <p>Getter for the field cookiePanel.</p>
	 *
	 * @return the cookiePanel
	 */
	public CheckBoxPanel getCookiePanel() {
		return this.cookiePanel;
	}

	/**
	 * <p>Getter for the field historyButton.</p>
	 *
	 * @return the historyButton
	 */
	public JButton getHistoryButton() {
		return this.historyButton;
	}

	/**
	 * <p>Getter for the field historyPanel.</p>
	 *
	 * @return the historyPanel
	 */
	public FormPanel getHistoryPanel() {
		return this.historyPanel;
	}

	/**
	 * <p>Getter for the field navigationPanel.</p>
	 *
	 * @return the navigationPanel
	 */
	public CheckBoxPanel getNavigationPanel() {
		return this.navigationPanel;
	}

	/**
	 * <p>Setter for the field bookmarkPanel.</p>
	 *
	 * @param bookmarkPanel the bookmarkPanel to set
	 */
	public void setBookmarkPanel(CheckBoxPanel bookmarkPanel) {
		this.bookmarkPanel = bookmarkPanel;
	}

	/**
	 * <p>Setter for the field cachePanel.</p>
	 *
	 * @param cachePanel the cachePanel to set
	 */
	public void setCachePanel(CheckBoxPanel cachePanel) {
		this.cachePanel = cachePanel;
	}

	/**
	 * <p>Setter for the field cookiePanel.</p>
	 *
	 * @param cookiePanel the cookiePanel to set
	 */
	public void setCookiePanel(CheckBoxPanel cookiePanel) {
		this.cookiePanel = cookiePanel;
	}

	/**
	 * <p>Setter for the field historyButton.</p>
	 *
	 * @param historyButton the historyButton to set
	 */
	public void setHistoryButton(JButton historyButton) {
		this.historyButton = historyButton;
	}

	/**
	 * <p>Setter for the field historyPanel.</p>
	 *
	 * @param historyPanel the historyPanel to set
	 */
	public void setHistoryPanel(FormPanel historyPanel) {
		this.historyPanel = historyPanel;
	}

	/**
	 * <p>Setter for the field navigationPanel.</p>
	 *
	 * @param navigationPanel the navigationPanel to set
	 */
	public void setNavigationPanel(CheckBoxPanel navigationPanel) {
		this.navigationPanel = navigationPanel;
	}
}
