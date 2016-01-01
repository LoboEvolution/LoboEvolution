/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.primary.gui.bookmarks;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lobobrowser.primary.gui.FieldType;
import org.lobobrowser.primary.gui.FormField;
import org.lobobrowser.primary.gui.FormPanel;
import org.lobobrowser.primary.info.BookmarkInfo;
import org.lobobrowser.util.Strings;

/**
 * The Class AddBookmarkDialog.
 */
public class AddBookmarkDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The url field. */
	private final FormField urlField = new FormField(FieldType.TEXT, "URL:");

	/** The title field. */
	private final FormField titleField = new FormField(FieldType.TEXT, "Title:");

	/** The description field. */
	private final FormField descriptionField = new FormField(FieldType.TEXT, "Description:");

	/** The tags field. */
	private final FormField tagsField = new FormField(FieldType.TEXT, "Tags:");

	/** The url. */
	private final URL url;

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
		this.tagsField.setToolTip("List of keywords separated by blanks.");
		this.urlField.setValue(existingInfo.getUrl().toExternalForm());
		this.titleField.setValue(existingInfo.getTitle());
		this.descriptionField.setValue(existingInfo.getDescription());
		this.tagsField.setValue(existingInfo.getTagsText());
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		FormPanel fieldsPanel = new FormPanel();
		fieldsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		fieldsPanel.addField(this.urlField);
		fieldsPanel.addField(this.titleField);
		fieldsPanel.addField(this.descriptionField);
		fieldsPanel.addField(this.tagsField);

		Dimension fpps = fieldsPanel.getPreferredSize();
		fieldsPanel.setPreferredSize(new Dimension(400, fpps.height));

		contentPane.add(fieldsPanel);
		JComponent buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		JButton okButton = new JButton();
		okButton.setAction(new OkAction());
		okButton.setText("Save");
		JButton cancelButton = new JButton();
		cancelButton.setAction(new CancelAction());
		cancelButton.setText("Cancel");
		buttonsPanel.add(Box.createHorizontalGlue());
		buttonsPanel.add(okButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(4, 1)));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createHorizontalGlue());
		contentPane.add(buttonsPanel);
		contentPane.add(Box.createRigidArea(new Dimension(1, 4)));
	}

	/** The bookmark info. */
	private BookmarkInfo bookmarkInfo;

	/**
	 * Gets the bookmark info.
	 *
	 * @return the bookmark info
	 */
	public BookmarkInfo getBookmarkInfo() {
		return this.bookmarkInfo;
	}

	/**
	 * The Class OkAction.
	 */
	private class OkAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			BookmarkInfo binfo = new BookmarkInfo();
			binfo.setUrl(url);
			binfo.setTitle(titleField.getValue());
			binfo.setDescription(descriptionField.getValue());
			binfo.setTags(Strings.split(tagsField.getValue()));
			bookmarkInfo = binfo;
			AddBookmarkDialog.this.dispose();
		}
	}

	/**
	 * The Class CancelAction.
	 */
	private class CancelAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			bookmarkInfo = null;
			AddBookmarkDialog.this.dispose();
		}
	}
}
