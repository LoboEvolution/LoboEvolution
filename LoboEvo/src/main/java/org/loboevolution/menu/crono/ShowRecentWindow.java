/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.menu.crono;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboLookAndFeel;
import com.jtattoo.plaf.lobo.LoboPanel;
import com.jtattoo.plaf.lobo.LoboSeparator;
import com.jtattoo.plaf.lobo.LoboTextField;

/**
 * <p>ShowRecentWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ShowRecentWindow extends JFrame implements LoboLookAndFeel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The url Edit Txt Fld. */
	private LoboTextField urlEditTxtFld;
	
	/** The title Edit Txt Fld. */
	private LoboTextField titleEditTxtFld;
	
	/** The tmp Url. */
	private String tmpUrl;

	/**
	 * <p>Constructor for ShowRecentWindow.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public ShowRecentWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(background());
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 920, 500);
		final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/host.png"));
		setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		LoboLabel label_6 = new LoboLabel("");
		label_6.setOpaque(true);
		label_6.setBounds(0, 11, 792, 8);
		contentPane.add(label_6);

		LoboLabel label_1 = new LoboLabel("");
		label_1.setOpaque(true);
		label_1.setBounds(650, 11, 403, 9);
		contentPane.add(label_1);
		
		listHost(frame, contentPane);
		saveHost(frame, contentPane);
	}
	
	private void saveHost(BrowserFrame frame, Container contentPane) {
		LoboPanel panel2 = new LoboPanel();
		panel2.setLayout(null);
		panel2.setBounds(550, 40, 403, 436);
		contentPane.add(panel2);

		LoboSeparator separator_2 = new LoboSeparator();
		separator_2.setBounds(0, 58, 350, 12);
		panel2.add(separator_2);
		
		LoboLabel editBookmark = new LoboLabel("Host");
		editBookmark.setHorizontalAlignment(SwingConstants.CENTER);
		editBookmark.setFont(new Font("Tahoma", Font.BOLD, 17));
		editBookmark.setBounds(70, 18, 167, 22);
		panel2.add(editBookmark);
		
		LoboLabel lblUrl = new LoboLabel("URL");
		lblUrl.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUrl.setBounds(12, 70, 282, 16);
		panel2.add(lblUrl);
		
		urlEditTxtFld = new LoboTextField();
		urlEditTxtFld.setFont(new Font("Tahoma", Font.BOLD, 12));
		urlEditTxtFld.setColumns(10);
		urlEditTxtFld.setBorder(null);
		urlEditTxtFld.setBounds(12, 98, 350, 16);
		panel2.add(urlEditTxtFld);
		
		LoboSeparator separator_8 = new LoboSeparator();
		separator_8.setBounds(12, 115, 380, 12);
		panel2.add(separator_8);
		
		LoboLabel lblTitle = new LoboLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(12, 139, 282, 16);
		panel2.add(lblTitle);
		
		titleEditTxtFld = new LoboTextField();
		titleEditTxtFld.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleEditTxtFld.setColumns(10);
		titleEditTxtFld.setBorder(null);
		titleEditTxtFld.setBounds(12, 167, 350, 16);
		panel2.add(titleEditTxtFld);
		
		LoboSeparator separator_9 = new LoboSeparator();
		separator_9.setBounds(12, 184, 380, 12);
		panel2.add(separator_9);
		
		LoboButton okButton = new LoboButton();
		okButton.setText("Save");
		okButton.setActionCommand("okButton");
		okButton.setBounds(25, 340, 150, 40);
		okButton.addActionListener(e -> {
			final NavigationStore nav = new NavigationStore();
			nav.deleteHost(tmpUrl);
			nav.addAsRecent(tmpUrl, titleEditTxtFld.getText(), -1);
			JOptionPane.showMessageDialog(contentPane, "Edit Ok!");
			setVisible(false);
			dispose();
			new ShowRecentWindow(frame).setVisible(true);
		});
		panel2.add(okButton);

		LoboButton closeButton = new LoboButton();
		closeButton.setText("Close");
		closeButton.setActionCommand("closeButton");
		closeButton.addActionListener(e -> {
			setVisible(false);
			dispose();
		});
		closeButton.setBounds(180, 340, 138, 40);
		panel2.add(closeButton);
	}
	
	private void listHost(BrowserFrame frame, Container contentPane) {
		LoboSeparator separator_7 = new LoboSeparator();
		separator_7.setBounds(0, 98, 550, 12);
		contentPane.add(separator_7);
		
		LoboPanel panel = new LoboPanel();
		panel.setLayout(null);
		panel.setBounds(0, 40, 650, 50);
		contentPane.add(panel);
		
		LoboLabel title = new LoboLabel("Title");
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		title.setBounds(30, 18, 211, 16);
		panel.add(title);

		LoboLabel action = new LoboLabel("Action");
		action.setFont(new Font("Tahoma", Font.BOLD, 14));
		action.setBounds(400, 18, 160, 17);
		panel.add(action);
		
		LoboPanel panelGeneralViewAllItems = new LoboPanel();
		panelGeneralViewAllItems.setBounds(10, 100, 530, 313);
		JScrollPane spViewallItems = new JScrollPane();
		spViewallItems.setBorder(null);
		spViewallItems.setViewportView(createItemPanel(frame));
		panelGeneralViewAllItems.setLayout(new BorderLayout());
		panelGeneralViewAllItems.add(spViewallItems, BorderLayout.CENTER);
		contentPane.add(panelGeneralViewAllItems);
		
	}
	
	private LoboPanel createItemPanel(BrowserFrame frame) {


		LoboPanel panel_3 = new LoboPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(0, 191, 750, 70);

		int debutCpDesc = 15;
		int debutCpTitle = 15;
		int debutCpUrl = 15;
		int debutCpSeparator = 58;
		int incrementNouvelleLigne = 67;

		try {
			final NavigationStore nav = new NavigationStore();
			final List<BookmarkInfo> allEntries = nav.getRecentHost(1000, false);
			for (final BookmarkInfo binfo : allEntries) {
				final String url = binfo.getUrl();

				LoboTextField title = new LoboTextField();
				title.setText(binfo.getTitle());
				title.setToolTipText(binfo.getTitle());
				title.setFont(new Font("Tahoma", Font.BOLD, 12));
				title.setEditable(false);
				title.setColumns(10);
				title.setBorder(null);
				title.setBounds(12, debutCpDesc, 250, 22);
				panel_3.add(title);

				LoboButton edit = new LoboButton();
				edit.setText("Edit");
				edit.setActionCommand("okButton");
				edit.setBounds(370, debutCpUrl, 40, 40);
				edit.addActionListener(e -> {
					titleEditTxtFld.setText(title.getText());
					urlEditTxtFld.setText(url);
					tmpUrl = url;

				});
				panel_3.add(edit);

				LoboButton delete = new LoboButton();
				delete.setText("Delete");
				delete.setActionCommand("okButton");
				delete.setBounds(410, debutCpUrl, 50, 40);
				delete.addActionListener(e -> {
					final NavigationStore nstore = new NavigationStore();
					nstore.deleteHost(binfo.getUrl());
					JOptionPane.showMessageDialog(panel_3, "Delete Ok!");
					setVisible(false);
					dispose();
                    new ShowRecentWindow(frame).setVisible(true);

				});
				panel_3.add(delete);
				
				LoboButton go = new LoboButton();
				go.setText("Go");
				go.setActionCommand("goButton");
				go.setBounds(460, debutCpUrl, 40, 40);
				go.addActionListener(e -> {
					final BrowserPanel panel = frame.getPanel();
					final int indexPanel = panel.getTabbedPane().getIndex() + 1;
					final DnDTabbedPane tabbedPane = panel.getTabbedPane();
					HtmlPanel hpanel = HtmlPanel.createHtmlPanel(panel, url);
					final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
					String htmlTitle = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
					tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
					tabbedPane.insertTab(htmlTitle, null, hpanel, htmlTitle, indexPanel);
					tabbedPane.setSelectedIndex(indexPanel);
					TabStore.insertTab(indexPanel, url, htmlTitle);
					NavigationManager.insertHistory(url, htmlTitle, indexPanel);
				});
				panel_3.add(go);
				
				debutCpDesc = debutCpDesc + incrementNouvelleLigne;
				debutCpTitle = debutCpTitle + incrementNouvelleLigne;
				debutCpUrl = debutCpUrl + incrementNouvelleLigne;
				debutCpSeparator = debutCpSeparator + incrementNouvelleLigne;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		panel_3.setPreferredSize(new Dimension(0, 1000));
		panel_3.revalidate();
		panel_3.repaint();

		return panel_3;
	}
}
