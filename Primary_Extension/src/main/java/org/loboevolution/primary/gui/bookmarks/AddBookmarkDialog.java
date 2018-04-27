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
package org.loboevolution.primary.gui.bookmarks;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.loboevolution.primary.gui.FieldType;
import org.loboevolution.primary.gui.FormField;
import org.loboevolution.primary.gui.FormPanel;
import org.loboevolution.primary.info.BookmarkInfo;

/**
 * The Class AddBookmarkDialog.
 */
public class AddBookmarkDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The url field. */
	private transient FormField urlField = new FormField(FieldType.TEXT, "URL:");

	/** The title field. */
	private final transient FormField titleField = new FormField(FieldType.TEXT, "Title:");

	/** The description field. */
	private final transient FormField descriptionField = new FormField(FieldType.TEXT, "Description:");

	/** The tags field. */
	private final transient FormField tagsField = new FormField(FieldType.TEXT, "Tags:");

	/** The url. */
	private final URL url;

	/** The bookmark info. */
	private transient BookmarkInfo bookmarkInfo;

	/**
	 * Instantiates a new adds the bookmark dialog.
	 *
	 * @param owner
	 *            the owner
	 * @param modal
	 *            the modal
	 * @param existingInfo
	 *            the existing info
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public AddBookmarkDialog(Frame owner, boolean modal, BookmarkInfo existingInfo) throws HeadlessException {
		super(owner, modal);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.url = existingInfo.getUrl();
		this.urlField.setEditable(false);
		this.getTagsField().setToolTip("List of keywords separated by blanks.");
		this.urlField.setValue(existingInfo.getUrl().toExternalForm());
		this.getTitleField().setValue(existingInfo.getTitle());
		this.getDescriptionField().setValue(existingInfo.getDescription());
		this.getTagsField().setValue(existingInfo.getTagsText());
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		FormPanel fieldsPanel = new FormPanel();
		fieldsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		fieldsPanel.addField(this.urlField);
		fieldsPanel.addField(this.getTitleField());
		fieldsPanel.addField(this.getDescriptionField());
		fieldsPanel.addField(this.getTagsField());

		Dimension fpps = fieldsPanel.getPreferredSize();
		fieldsPanel.setPreferredSize(new Dimension(400, fpps.height));

		contentPane.add(fieldsPanel);
		JComponent buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		JButton okButton = new JButton();
		okButton.setAction(new OkAction(this));
		okButton.setText("Save");
		JButton cancelButton = new JButton();
		cancelButton.setAction(new CancelAction(this));
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
	 * Gets the bookmark info.
	 *
	 * @return the bookmark info
	 */
	public BookmarkInfo getBookmarkInfo() {
		return this.bookmarkInfo;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the titleField
	 */
	public FormField getTitleField() {
		return titleField;
	}

	/**
	 * @return the descriptionField
	 */
	public FormField getDescriptionField() {
		return descriptionField;
	}

	/**
	 * @return the tagsField
	 */
	public FormField getTagsField() {
		return tagsField;
	}

	/**
	 * @param bookmarkInfo the bookmarkInfo to set
	 */
	public void setBookmarkInfo(BookmarkInfo bookmarkInfo) {
		this.bookmarkInfo = bookmarkInfo;
	}
}
