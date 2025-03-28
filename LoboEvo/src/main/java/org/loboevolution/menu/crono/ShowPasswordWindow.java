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

package org.loboevolution.menu.crono;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.Serial;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.jtattoo.plaf.lobo.*;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.store.InputStore;

/**
 * <p>ShowPasswordWindow class.</p>
 */
@Slf4j
public class ShowPasswordWindow extends JFrame {

	/** The Constant serialVersionUID. */
	@Serial
    private static final long serialVersionUID = 1L;
		
	/** The type. */
	private LoboTextField type;
	
	/** The title Edit Txt Fld. */
	private LoboTextField valueEditTxtFld;
	
	/** The url Edit Txt Fld. */
	private LoboTextField urlEditTxtFld;
	
	/** The tmp Url. */
	private String tmpUrl;
	
	/** The tmp value. */
	private String tmpValue;

	/**
	 * <p>Constructor for ShowPasswordWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 * @param num a {@link java.lang.Integer} object.
	 */
	public ShowPasswordWindow(final BrowserFrame frame, final Integer num) {
		createAndShowGUI(frame, num);
	}
	
	private void createAndShowGUI(final BrowserFrame frame, final Integer num) {
		final Container contentPane = getContentPane();
		final LoboBackground lb = new LoboBackground();
		contentPane.setLayout(null);
		contentPane.setBackground(lb.getBackground());
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 410);
		final ImageIcon ico = new ImageIcon(DesktopConfig.getResourceFile("bookmark.png",DesktopConfig.PATH_IMAGE));
		setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		final LoboLabel label_6 = new LoboLabel("");
		label_6.setOpaque(true);
		label_6.setBounds(0, 11, 792, 8);
		contentPane.add(label_6);

		final LoboLabel label_1 = new LoboLabel("");
		label_1.setOpaque(true);
		label_1.setBounds(797, 11, 403, 9);
		contentPane.add(label_1);
		
		listPassword(frame, contentPane);
		savePassword(frame, contentPane);

	}

	private void savePassword(final BrowserFrame frame, final Container contentPane) {
		final LoboPanel panel2 = new LoboPanel();
		panel2.setLayout(null);
		panel2.setBounds(510, 40, 403, 436);
		contentPane.add(panel2);

		final LoboSeparator separator_2 = new LoboSeparator();
		separator_2.setBounds(0, 58, 403, 12);
		panel2.add(separator_2);
		
		final LoboLabel editPassword = new LoboLabel("Password");
		editPassword.setHorizontalAlignment(SwingConstants.CENTER);
		editPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		editPassword.setBounds(70, 18, 167, 22);
		panel2.add(editPassword);

		final LoboLabel lblType = new LoboLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblType.setBounds(12, 70, 282, 16);
		panel2.add(lblType);

		type = new LoboTextField();
		type.setFont(new Font("Tahoma", Font.BOLD, 12));
		type.setColumns(10);
		type.setBorder(null);
		type.setEditable(false);
		type.setBounds(12, 98, 350, 16);
		panel2.add(type);

		final LoboSeparator separator_9 = new LoboSeparator();
		separator_9.setBounds(12, 115, 350, 12);
		panel2.add(separator_9);

		final LoboLabel titleLbl = new LoboLabel("Value");
		titleLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		titleLbl.setBounds(12, 139, 282, 16);
		panel2.add(titleLbl);

		valueEditTxtFld = new LoboTextField();
		valueEditTxtFld.setFont(new Font("Tahoma", Font.BOLD, 12));
		valueEditTxtFld.setColumns(10);
		valueEditTxtFld.setBorder(null);
		valueEditTxtFld.setBounds(12, 167, 350, 16);
		panel2.add(valueEditTxtFld);

		final LoboSeparator separator_10 = new LoboSeparator();
		separator_10.setBounds(12, 184, 350, 12);
		panel2.add(separator_10);
		
		final LoboLabel url_1 = new LoboLabel("Url");
		url_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		url_1.setBounds(12, 200, 282, 16);
		panel2.add(url_1);

		urlEditTxtFld = new LoboTextField();
		urlEditTxtFld.setFont(new Font("Tahoma", Font.BOLD, 12));
		urlEditTxtFld.setColumns(10);
		urlEditTxtFld.setBorder(null);
		urlEditTxtFld.setEditable(false);
		urlEditTxtFld.setBounds(12, 230, 275, 16);
		panel2.add(urlEditTxtFld);
		
		final LoboSeparator separator_11 = new LoboSeparator();
		separator_11.setBounds(12, 250, 350, 12);
		panel2.add(separator_11);

		final LoboButton okButton = new LoboButton();
		okButton.setText("Save");
		okButton.setActionCommand("okButton");
		okButton.setBounds(12, 280, 200, 40);
		okButton.addActionListener(e -> {
			InputStore.deleteInput(tmpValue, tmpUrl);
			InputStore.insertLogin(type.getText(), valueEditTxtFld.getText(), urlEditTxtFld.getText(), true);
			JOptionPane.showMessageDialog(contentPane, "Edit Ok!");
			setVisible(false);
			dispose();
			new ShowPasswordWindow(frame, 100).setVisible(true);
		});
		panel2.add(okButton);

		final LoboButton closeButton = new LoboButton();
		closeButton.setText("Close");
		closeButton.setActionCommand("closeButton");
		closeButton.addActionListener(e -> {
			setVisible(false);
			dispose();
		});
		closeButton.setBounds(224, 280, 138, 40);
		panel2.add(closeButton);
		
	}

	private void listPassword(final BrowserFrame frame, final Container contentPane) {
		final LoboSeparator separator_7 = new LoboSeparator();
		separator_7.setBounds(0, 98, 510, 12);
		contentPane.add(separator_7);
		
		final LoboPanel panel = new LoboPanel();
		panel.setLayout(null);
		panel.setBounds(0, 40, 510, 50);
		contentPane.add(panel);
		
		final LoboLabel description = new LoboLabel("Type");
		description.setFont(new Font("Tahoma", Font.BOLD, 14));
		description.setBounds(30, 18, 211, 16);
		panel.add(description);

		final LoboLabel title = new LoboLabel("Value");
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		title.setBounds(200, 18, 150, 17);
		panel.add(title);
		
		final LoboLabel action = new LoboLabel("Action");
		action.setFont(new Font("Tahoma", Font.BOLD, 14));
		action.setBounds(400, 18, 160, 17);
		panel.add(action);
		
		final LoboPanel panelGeneralViewAllItems = new LoboPanel();
		panelGeneralViewAllItems.setBounds(10, 100, 500, 313);
		final JScrollPane spViewallItems = new JScrollPane();
		spViewallItems.setBorder(null);
		spViewallItems.setViewportView(createItemPanel(frame));
		panelGeneralViewAllItems.setLayout(new BorderLayout());
		panelGeneralViewAllItems.add(spViewallItems, BorderLayout.CENTER);
		contentPane.add(panelGeneralViewAllItems);
	}
	
	private LoboPanel createItemPanel(final BrowserFrame frame) {

		final LoboPanel panel_3 = new LoboPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(0, 191, 700, 70);

		int debutCpDesc = 15;
		int debutCpTitle = 15;
		int debutCpUrl = 15;
		int debutCpSeparator = 58;
		final int incrementNouvelleLigne = 67;

		try {
			final InputStore store = new InputStore();
			final List<BookmarkInfo> allEntries = store.getPassword(100);
			for (final BookmarkInfo binfo : allEntries) {

				final LoboTextField description = new LoboTextField();
				description.setText(binfo.getDescription());
				description.setToolTipText(binfo.getDescription());
				description.setFont(new Font("Tahoma", Font.BOLD, 12));
				description.setEditable(false);
				description.setColumns(10);
				description.setBorder(null);
				description.setBounds(12, debutCpDesc, 190, 22);
				panel_3.add(description);

				final LoboTextField value = new LoboTextField();
				value.setText(binfo.getTitle());
				value.setToolTipText(binfo.getTitle());
				value.setFont(new Font("Tahoma", Font.BOLD, 12));
				value.setEditable(false);
				value.setColumns(10);
				value.setBorder(null);
				value.setBounds(190, debutCpTitle, 180, 22);
				panel_3.add(value);
			
				final LoboSeparator separatorItem = new LoboSeparator();
				separatorItem.setBounds(0, debutCpSeparator, 500, 7);
				panel_3.add(separatorItem);

				final LoboButton edit = new LoboButton();
				edit.setText("Edit");
				edit.setActionCommand("okButton");
				edit.setBounds(370, debutCpUrl, 40, 40);
				edit.addActionListener(e -> {
					type.setText(description.getText());
					valueEditTxtFld.setText(value.getText());
					urlEditTxtFld.setText(binfo.getUrl());
					tmpValue = value.getText();

				});
				panel_3.add(edit);
				
				final LoboButton delete = new LoboButton();
				delete.setText("Delete");
				delete.setActionCommand("okButton");
				delete.setBounds(410, debutCpUrl, 50, 40);
				delete.addActionListener(e -> {
					InputStore.deleteInput(value.getText(), binfo.getUrl());
					JOptionPane.showMessageDialog(panel_3, "Delete Ok!");
					setVisible(false);
					dispose();
					new ShowPasswordWindow(frame, 100).setVisible(true);

				});
				panel_3.add(delete);
				
				debutCpDesc = debutCpDesc + incrementNouvelleLigne;
				debutCpTitle = debutCpTitle + incrementNouvelleLigne;
				debutCpUrl = debutCpUrl + incrementNouvelleLigne;
				debutCpSeparator = debutCpSeparator + incrementNouvelleLigne;
			}

		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}

		panel_3.setPreferredSize(new Dimension(0, 1000));
		panel_3.revalidate();
		panel_3.repaint();

		return panel_3;
	}
}
