/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.cleardata;

import java.awt.Component;
import java.awt.Frame;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.loboevolution.primary.gui.CheckBoxPanel;
import org.loboevolution.primary.gui.FormPanel;

public class ClearHistoryDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The history panel. */
	private FormPanel historyPanel;
	
	/** The cache anel. */
	private CheckBoxPanel cachePanel;
	
	/** The cookie panel. */
	private CheckBoxPanel cookiePanel;
	
	/** The navigation panel. */
	private CheckBoxPanel navigationPanel;
	
	/** The bookmark panel. */
	private CheckBoxPanel bookmarkPanel;
	
	/** The history button. */
	private JButton historyButton;
	
	public ClearHistoryDialog(Frame parent) throws HeadlessException {
		super(parent);
		createAndShowGUI();
	}
	
	private void createAndShowGUI() throws HeadlessException {
		historyPanel = new FormPanel();
		historyPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.cachePanel = new CheckBoxPanel("Cache", historyPanel);
		this.cookiePanel = new CheckBoxPanel("Cookies", historyPanel);
		this.navigationPanel = new CheckBoxPanel("Navigation", historyPanel);
		this.bookmarkPanel = new CheckBoxPanel("Bookmarks", historyPanel);
		
		JButton historyButton = new JButton();
		historyButton.setAction(new ClearDataAction(this.cachePanel,this.cookiePanel, this.navigationPanel, this.bookmarkPanel));
		historyButton.setText("Delete Now");
		this.historyButton = historyButton;
		
		add(getHistoryBox());
		
	}
	
	/**
	 * Gets the history box.
	 *
	 * @return the history box
	 */
	private Component getHistoryBox() {
		JPanel groupBox = new JPanel();
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Clear History"));
		groupBox.add(this.getCachePanel());
		groupBox.add(this.getCookiePanel());
		groupBox.add(this.getNavigationPanel());
		groupBox.add(this.getBookmarkPanel());
		groupBox.add(this.getHistoryButton());
		return groupBox;
	}

	/**
	 * @return the historyPanel
	 */
	public FormPanel getHistoryPanel() {
		return historyPanel;
	}

	/**
	 * @param historyPanel the historyPanel to set
	 */
	public void setHistoryPanel(FormPanel historyPanel) {
		this.historyPanel = historyPanel;
	}

	/**
	 * @return the cachePanel
	 */
	public CheckBoxPanel getCachePanel() {
		return cachePanel;
	}

	/**
	 * @param cachePanel the cachePanel to set
	 */
	public void setCachePanel(CheckBoxPanel cachePanel) {
		this.cachePanel = cachePanel;
	}

	/**
	 * @return the cookiePanel
	 */
	public CheckBoxPanel getCookiePanel() {
		return cookiePanel;
	}

	/**
	 * @param cookiePanel the cookiePanel to set
	 */
	public void setCookiePanel(CheckBoxPanel cookiePanel) {
		this.cookiePanel = cookiePanel;
	}

	/**
	 * @return the navigationPanel
	 */
	public CheckBoxPanel getNavigationPanel() {
		return navigationPanel;
	}

	/**
	 * @param navigationPanel the navigationPanel to set
	 */
	public void setNavigationPanel(CheckBoxPanel navigationPanel) {
		this.navigationPanel = navigationPanel;
	}

	/**
	 * @return the bookmarkPanel
	 */
	public CheckBoxPanel getBookmarkPanel() {
		return bookmarkPanel;
	}

	/**
	 * @param bookmarkPanel the bookmarkPanel to set
	 */
	public void setBookmarkPanel(CheckBoxPanel bookmarkPanel) {
		this.bookmarkPanel = bookmarkPanel;
	}

	/**
	 * @return the historyButton
	 */
	public JButton getHistoryButton() {
		return historyButton;
	}

	/**
	 * @param historyButton the historyButton to set
	 */
	public void setHistoryButton(JButton historyButton) {
		this.historyButton = historyButton;
	}
}
