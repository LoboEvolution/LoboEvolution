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
package org.lobobrowser.primary.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lobobrowser.primary.action.SearchCancelAction;
import org.lobobrowser.primary.action.SearchOkAction;

/**
 * The Class SearchDialog.
 */
public class SearchDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The tags field. */
	private final FormField tagsField = new FormField(FieldType.TEXT, "Keywords:");

	/** The search keywords. */
	private String searchKeywords = null;

	/**
	 * Instantiates a new search dialog.
	 *
	 * @param owner
	 *            the owner
	 * @param modal
	 *            the modal
	 * @param keywordsTooltip
	 *            the keywords tooltip
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public SearchDialog(Frame owner, boolean modal, String keywordsTooltip) throws HeadlessException {
		super(owner, modal);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.tagsField.setToolTip(keywordsTooltip);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		FormPanel fieldsPanel = new FormPanel();
		fieldsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		fieldsPanel.addField(this.tagsField);
		contentPane.add(fieldsPanel);
		JComponent buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		JButton okButton = new JButton();
		okButton.setAction(new SearchOkAction(tagsField, this));
		okButton.setText("Search");
		JButton cancelButton = new JButton();
		cancelButton.setAction(new SearchCancelAction(this));
		cancelButton.setText("Cancel");
		buttonsPanel.add(Box.createHorizontalGlue());
		buttonsPanel.add(okButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(4, 1)));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createHorizontalGlue());
		contentPane.add(buttonsPanel);
		contentPane.add(Box.createRigidArea(new Dimension(1, 4)));
	}

	/**
	 * Gets the search keywords.
	 *
	 * @return the search keywords
	 */
	public String getSearchKeywords() {
		return this.searchKeywords;
	}

	public String setSearchKeywords(String searchKeywords) {
		return this.searchKeywords = searchKeywords;
	}
}
