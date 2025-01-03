/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.menu.bookmarks;

import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.jtattoo.plaf.lobo.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.loboevolution.common.Strings;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.store.BookmarksStore;

/**
 * <p>AddBookmarkWindow class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddBookmarkWindow extends JFrame implements ActionListener {

	/** The Constant serialVersionUID. */
	@Serial
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
	public AddBookmarkWindow(final String uri) {
		createAndShowGUI(uri);
	}

	private void createAndShowGUI(final String uri) {
		final LoboBackground lb = new LoboBackground();
		final MatteBorder border = new MatteBorder(0, 0, 1, 0, lb.getForeground());
		
		final Container contentPane = getContentPane();
		contentPane.setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 440);
		final ImageIcon ico = new ImageIcon(DesktopConfig.getResourceFile("bookmark.png",DesktopConfig.PATH_IMAGE));
		setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		final LoboPanel panel = new LoboPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 350, 400);
		contentPane.add(panel);
		
		final LoboLabel lblEditionDeCompte = new LoboLabel("Add Bookmark");
		lblEditionDeCompte.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditionDeCompte.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEditionDeCompte.setBounds(0, 0, 167, 22);
		panel.add(lblEditionDeCompte);
		
		final LoboLabel lbldescriptionField = new LoboLabel("Description");
		lbldescriptionField.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbldescriptionField.setBounds(12, 70, 282, 16);
		panel.add(lbldescriptionField);
		
		descriptionField = new LoboTextField();
		descriptionField.setFont(new Font("Tahoma", Font.BOLD, 12));
		descriptionField.setColumns(10);
		descriptionField.setBorder(border);
		descriptionField.setBounds(12, 98, 350, 16);
		panel.add(descriptionField);

		final LoboLabel lbltitleField = new LoboLabel("Title");
		lbltitleField.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbltitleField.setBounds(12, 139, 282, 16);
		panel.add(lbltitleField);

		titleField = new LoboTextField();
		titleField.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleField.setColumns(10);
		titleField.setBorder(border);
		titleField.setBounds(12, 167, 350, 16);
		panel.add(titleField);

		final LoboLabel lblMotDePasse_1 = new LoboLabel("Tags");
		lblMotDePasse_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMotDePasse_1.setBounds(12, 208, 282, 16);
		panel.add(lblMotDePasse_1);

		tagsField = new LoboTextField();
		tagsField.setFont(new Font("Tahoma", Font.BOLD, 12));
		tagsField.setColumns(10);
		tagsField.setBorder(border);
		tagsField.setBounds(12, 236, 350, 16);
		panel.add(tagsField);
		
		final LoboLabel lblurlField_1 = new LoboLabel("Url");
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
		
		final LoboButton okButton = new LoboButton();
		okButton.setText("Save");
		okButton.setBounds(12, 356, 150, 40);
		okButton.setActionCommand("save");
		okButton.addActionListener(this);
		panel.add(okButton);

		final LoboButton closeButton = new LoboButton();
		closeButton.setText("Close");
		closeButton.setActionCommand("close");
		closeButton.addActionListener(this);
		closeButton.setBounds(180, 356, 150, 40);
		panel.add(closeButton);
	}
	
	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String actionCommand = e.getActionCommand();
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
}
