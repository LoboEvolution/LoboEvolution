package org.loboevolution.menu.crono;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import org.loboevolution.common.Strings;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.BrowserPanel;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.store.NavigationStore;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;

/**
 * <p>ShowRecentWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ShowRecentWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The color background. */
	private final Color COLOR_BACKGROUND = new Color(37, 51, 61);
	
	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);

	/** The url Edit Txt Fld. */
	private JTextField urlEditTxtFld;
	
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
		contentPane.setBackground(COLOR_BACKGROUND);
		contentPane.setLayout(null);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1250, 450);
		setBackground(COLOR_BACKGROUND);
		final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/host.png"));
		setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		JLabel label_6 = new JLabel("");
		label_6.setOpaque(true);
		label_6.setBackground(COLOR_TEXT);
		label_6.setBounds(0, 11, 792, 8);
		contentPane.add(label_6);

		JLabel label_1 = new JLabel("");
		label_1.setOpaque(true);
		label_1.setBackground(COLOR_TEXT);
		label_1.setBounds(797, 11, 403, 9);
		contentPane.add(label_1);
		
		listHost(frame, contentPane);

		saveHost(frame, contentPane);
	}
	
	private void saveHost(BrowserFrame frame, Container contentPane) {
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBackground(COLOR_BACKGROUND);
		panel2.setBounds(870, 40, 403, 436);
		contentPane.add(panel2);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 58, 792, 12);
		panel2.add(separator_2);
		
		JLabel url_1 = new JLabel("Url");
		url_1.setForeground(COLOR_TEXT);
		url_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		url_1.setBounds(12, 70, 282, 16);
		panel2.add(url_1);
		
		urlEditTxtFld = new JTextField();
		urlEditTxtFld.setForeground(COLOR_TEXT);
		urlEditTxtFld.setFont(new Font("Tahoma", Font.BOLD, 12));
		urlEditTxtFld.setColumns(10);
		urlEditTxtFld.setCaretColor(COLOR_TEXT);
		urlEditTxtFld.setBorder(null);
		urlEditTxtFld.setBackground(COLOR_BACKGROUND);
		urlEditTxtFld.setBounds(12, 98, 500, 16);
		panel2.add(urlEditTxtFld);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setForeground(COLOR_TEXT);
		separator_11.setBackground(COLOR_BACKGROUND);
		separator_11.setBounds(12, 115, 350, 12);
		panel2.add(separator_11);

		JButton okButton = new JButton("Save");
		okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		okButton.setForeground(COLOR_TEXT);
		okButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		okButton.setFocusPainted(false);
		okButton.setContentAreaFilled(false);
		okButton.setBorder(new LineBorder(COLOR_TEXT));
		okButton.setActionCommand("okButton");
		okButton.setBounds(12, 150, 200, 40);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final NavigationStore nav = new NavigationStore();
				nav.deleteHost(tmpUrl);
				nav.addAsRecent(tmpUrl, -1);
				JOptionPane.showMessageDialog(contentPane, "Edit Ok!");
				setVisible(false);
				dispose();
				new ShowRecentWindow(frame).setVisible(true);
			}
		});
		panel2.add(okButton);

		JButton closeButton = new JButton("Close");
		closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		closeButton.setForeground(COLOR_TEXT);
		closeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeButton.setFocusPainted(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setBorder(new LineBorder(COLOR_TEXT));
		closeButton.setActionCommand("closeButton");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		closeButton.setBounds(224, 150, 138, 40);
		panel2.add(closeButton);
		
	}
	
	private void listHost(BrowserFrame frame, Container contentPane) {
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(0, 98, 900, 12);
		contentPane.add(separator_7);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(COLOR_BACKGROUND);
		panel.setBounds(0, 40, 800, 50);
		contentPane.add(panel);
		
		JLabel url = new JLabel("URL");
		url.setForeground(COLOR_TEXT);
		url.setFont(new Font("Tahoma", Font.BOLD, 14));
		url.setBounds(30, 18, 211, 16);
		panel.add(url);
		
		JLabel action = new JLabel("Action");
		action.setForeground(COLOR_TEXT);
		action.setFont(new Font("Tahoma", Font.BOLD, 14));
		action.setBounds(750, 18, 160, 17);
		panel.add(action);
		
		JPanel panelGeneralViewAllItems = new JPanel();
		panelGeneralViewAllItems.setBackground(COLOR_TEXT);
		panelGeneralViewAllItems.setBounds(10, 100, 850, 313);
		JScrollPane spViewallItems = new JScrollPane();
		spViewallItems.setBorder(null);
		spViewallItems.setBackground(COLOR_TEXT);
		spViewallItems.setViewportView(createItemPanel(frame));

		panelGeneralViewAllItems.setLayout(new BorderLayout());
		panelGeneralViewAllItems.add(spViewallItems, BorderLayout.CENTER);

		contentPane.add(panelGeneralViewAllItems);
		
	}
	
	private JPanel createItemPanel(BrowserFrame frame) {

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(COLOR_BACKGROUND);
		panel_3.setBounds(0, 191, 850, 70);

		int debutCpDesc = 15;
		int debutCpTitle = 15;
		int debutCpUrl = 15;
		int debutCpSeparator = 58;
		int incrementNouvelleLigne = 67;

		try {
			final NavigationStore history = new NavigationStore();
			final List<String[]> hostEntries = history.getRecentHostEntries(1000);
			for (final String[] hosts : hostEntries) {
				final String host = hosts[0]; 
				JTextField url = new JTextField();
				url.setText(host);
				url.setForeground(COLOR_TEXT);
				url.setFont(new Font("Tahoma", Font.BOLD, 12));
				url.setEditable(false);
				url.setColumns(10);
				url.setBorder(null);
				url.setBackground(COLOR_BACKGROUND);
				url.setBounds(12, debutCpDesc, 650, 22);
				url.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				url.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						final BrowserPanel panel = frame.getPanel();
						final int indexPanel = panel.getTabbedPane().getIndex() + 1;
						final DnDTabbedPane tabbedPane = panel.getTabbedPane();
						HtmlPanel hpanel = NavigationManager.getHtmlPanel(url.getText(), indexPanel);
						final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
						final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
						tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
						tabbedPane.insertTab(title, null, hpanel, title, indexPanel);
						tabbedPane.setSelectedIndex(indexPanel);
						TabStore.insertTab(indexPanel, url.getText(), title);

					}
				});
				panel_3.add(url);
				
				JSeparator separatorItem = new JSeparator();
				separatorItem.setBounds(0, debutCpSeparator, 900, 7);
				panel_3.add(separatorItem);

				JButton edit = new JButton("Edit");
				edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				edit.setForeground(COLOR_TEXT);
				edit.setFont(new Font("Tahoma", Font.BOLD, 12));
				edit.setFocusPainted(false);
				edit.setContentAreaFilled(false);
				edit.setBorder(new LineBorder(COLOR_TEXT));
				edit.setActionCommand("okButton");
				edit.setBounds(720, debutCpUrl, 40, 40);
				edit.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						urlEditTxtFld.setText(host);
						tmpUrl = host;

					}
				});
				panel_3.add(edit);
				
				
				JButton delete = new JButton("Delete");
				delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				delete.setForeground(COLOR_TEXT);
				delete.setFont(new Font("Tahoma", Font.BOLD, 12));
				delete.setFocusPainted(false);
				delete.setContentAreaFilled(false);
				delete.setBorder(new LineBorder(COLOR_TEXT));
				delete.setActionCommand("okButton");
				delete.setBounds(770, debutCpUrl, 50, 40);
				delete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						final NavigationStore nav = new NavigationStore();
						nav.deleteHost(url.getText());
						JOptionPane.showMessageDialog(panel_3, "Delete Ok!");
						setVisible(false);
						dispose();
						new ShowRecentWindow(frame).setVisible(true);

					}
				});
				panel_3.add(delete);
				
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
