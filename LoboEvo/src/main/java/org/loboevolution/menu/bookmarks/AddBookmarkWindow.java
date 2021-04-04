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

package org.loboevolution.menu.bookmarks;

import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import org.loboevolution.common.Strings;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.store.BookmarksStore;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboLookAndFeel;
import com.jtattoo.plaf.lobo.LoboPanel;
import com.jtattoo.plaf.lobo.LoboTextField;

/**
 * <p>AddBookmarkWindow class.</p>
 *
 *
 *
 */
public class AddBookmarkWindow extends JFrame implements LoboLookAndFeel, ActionListener{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The description field. */
	private LoboTextField descriptionField;
	
	/** The titleField field. */
	private LoboTextField titleField;
	
	/** The tags field. */
	private LoboTextField tagsField;
	
	/** The url field. */
	private LoboTextField urlField;

	/**
	 * <p>Constructor for AddBookmarkWindow.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	public AddBookmarkWindow(String uri) {
		createAndShowGUI(uri);
	}

	private void createAndShowGUI(String uri) {
		final MatteBorder border = new MatteBorder(0, 0, 1, 0, foreground());
		
		final Container contentPane = getContentPane();
		contentPane.setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 440);
		final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/bookmark.png"));
		setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		LoboPanel panel = new LoboPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 350, 400);
		contentPane.add(panel);
		
		LoboLabel lblEditionDeCompte = new LoboLabel("Add Bookmark");
		lblEditionDeCompte.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditionDeCompte.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEditionDeCompte.setBounds(0, 0, 167, 22);
		panel.add(lblEditionDeCompte);
		
		LoboLabel lbldescriptionField = new LoboLabel("Description");
		lbldescriptionField.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbldescriptionField.setBounds(12, 70, 282, 16);
		panel.add(lbldescriptionField);
		
		descriptionField = new LoboTextField();
		descriptionField.setFont(new Font("Tahoma", Font.BOLD, 12));
		descriptionField.setColumns(10);
		descriptionField.setBorder(border);
		descriptionField.setBounds(12, 98, 350, 16);
		panel.add(descriptionField);

		LoboLabel lbltitleField = new LoboLabel("Title");
		lbltitleField.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbltitleField.setBounds(12, 139, 282, 16);
		panel.add(lbltitleField);

		titleField = new LoboTextField();
		titleField.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleField.setColumns(10);
		titleField.setBorder(border);
		titleField.setBounds(12, 167, 350, 16);
		panel.add(titleField);

		LoboLabel lblMotDePasse_1 = new LoboLabel("Tags");
		lblMotDePasse_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMotDePasse_1.setBounds(12, 208, 282, 16);
		panel.add(lblMotDePasse_1);

		tagsField = new LoboTextField();
		tagsField.setFont(new Font("Tahoma", Font.BOLD, 12));
		tagsField.setColumns(10);
		tagsField.setBorder(border);
		tagsField.setBounds(12, 236, 350, 16);
		panel.add(tagsField);
		
		LoboLabel lblurlField_1 = new LoboLabel("Url");
		lblurlField_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblurlField_1.setBounds(12, 277, 282, 16);
		panel.add(lblurlField_1);
		
		urlField = new LoboTextField();
		urlField.setFont(new Font("Tahoma", Font.BOLD, 12));
		urlField.setColumns(10);
		urlField.setBorder(border);
		urlField.setText(uri);
		urlField.setBounds(12, 300, 350, 16);
		panel.add(urlField);
		
		LoboButton okButton = new LoboButton();
		okButton.setText("Save");
		okButton.setBounds(12, 356, 150, 40);
		okButton.setActionCommand("save");
		okButton.addActionListener(this);
		panel.add(okButton);

		LoboButton closeButton = new LoboButton();
		closeButton.setText("Close");
		closeButton.setActionCommand("close");
		closeButton.addActionListener(this);
		closeButton.setBounds(180, 356, 150, 40);
		panel.add(closeButton);
	}
	
	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();		
		switch (actionCommand) {
		case "save":
			final BookmarksStore book = new BookmarksStore();
			book.insertBookmark(getinfo());
			JOptionPane.showMessageDialog(this, "Bookmark Added!");
			setVisible(false);
			dispose();
			break;
		case "close":
			setVisible(false);
			dispose();
			break;
		default:
			break;
		}
	}
	
	private BookmarkInfo getinfo() {
		final BookmarkInfo binfo = new BookmarkInfo();
		binfo.setUrl(getUrlField().getText());
		binfo.setTitle(getTitleField().getText());
		binfo.setDescription(getDescriptionField().getText());
		binfo.setTags(Strings.split(getTagsField().getText()));
		return binfo;
	}

	/**
	 * <p>Getter for the field <code>descriptionField</code>.</p>
	 *
	 * @return the descriptionField
	 */
	public LoboTextField getDescriptionField() {
		return descriptionField;
	}

	/**
	 * <p>Setter for the field <code>descriptionField</code>.</p>
	 *
	 * @param descriptionField the descriptionField to set
	 */
	public void setDescriptionField(LoboTextField descriptionField) {
		this.descriptionField = descriptionField;
	}

	/**
	 * <p>Getter for the field <code>titleField</code>.</p>
	 *
	 * @return the titleField
	 */
	public LoboTextField getTitleField() {
		return titleField;
	}

	/**
	 * <p>Setter for the field <code>titleField</code>.</p>
	 *
	 * @param titleField the titleField to set
	 */
	public void setTitleField(LoboTextField titleField) {
		this.titleField = titleField;
	}

	/**
	 * <p>Getter for the field <code>tagsField</code>.</p>
	 *
	 * @return the tagsField
	 */
	public LoboTextField getTagsField() {
		return tagsField;
	}

	/**
	 * <p>Setter for the field <code>tagsField</code>.</p>
	 *
	 * @param tagsField the tagsField to set
	 */
	public void setTagsField(LoboTextField tagsField) {
		this.tagsField = tagsField;
	}

	/**
	 * <p>Getter for the field <code>urlField</code>.</p>
	 *
	 * @return the urlField
	 */
	public LoboTextField getUrlField() {
		return urlField;
	}

	/**
	 * <p>Setter for the field <code>urlField</code>.</p>
	 *
	 * @param urlField the urlField to set
	 */
	public void setUrlField(LoboTextField urlField) {
		this.urlField = urlField;
	}
}
