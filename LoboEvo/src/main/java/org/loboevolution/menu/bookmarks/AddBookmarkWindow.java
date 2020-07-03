package org.loboevolution.menu.bookmarks;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import org.loboevolution.common.Strings;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.store.BookmarksStore;

/**
 * <p>AddBookmarkWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class AddBookmarkWindow extends JFrame implements ActionListener{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The color background. */
	private final Color COLOR_BACKGROUND = new Color(37, 51, 61);
	
	/** The color text. */
	private final Color COLOR_TEXT = new Color(108, 216, 158);
	
	/** The border. */
	private final MatteBorder border = new MatteBorder(0, 0, 1, 0, COLOR_TEXT);
	
	/** The description field. */
	private JTextField descriptionField;
	
	/** The titleField field. */
	private JTextField titleField;
	
	/** The tags field. */
	private JTextField tagsField;
	
	/** The url field. */
	private JTextField urlField;

	/**
	 * <p>Constructor for AddBookmarkWindow.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	public AddBookmarkWindow(String uri) {
		createAndShowGUI(uri);
	}

	private void createAndShowGUI(String uri) {
		final Container contentPane = getContentPane();
		contentPane.setBackground(COLOR_BACKGROUND);
		contentPane.setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 440);
		setBackground(COLOR_BACKGROUND);
		final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/bookmark.png"));
		setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(COLOR_BACKGROUND);
		panel.setBounds(0, 0, 350, 400);
		contentPane.add(panel);
		
		JLabel lblEditionDeCompte = new JLabel("Add Bookmark");
		lblEditionDeCompte.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditionDeCompte.setForeground(COLOR_TEXT);
		lblEditionDeCompte.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEditionDeCompte.setBounds(0, 0, 167, 22);
		panel.add(lblEditionDeCompte);
		
		JLabel lbldescriptionField = new JLabel("Description");
		lbldescriptionField.setForeground(COLOR_TEXT);
		lbldescriptionField.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbldescriptionField.setBounds(12, 70, 282, 16);
		panel.add(lbldescriptionField);
		
		descriptionField = new JTextField();
		descriptionField.setForeground(COLOR_TEXT);
		descriptionField.setFont(new Font("Tahoma", Font.BOLD, 12));
		descriptionField.setColumns(10);
		descriptionField.setCaretColor(COLOR_TEXT);
		descriptionField.setBorder(border);
		descriptionField.setBackground(COLOR_BACKGROUND);
		descriptionField.setBounds(12, 98, 350, 16);
		panel.add(descriptionField);

		JLabel lbltitleField = new JLabel("Title");
		lbltitleField.setForeground(COLOR_TEXT);
		lbltitleField.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbltitleField.setBounds(12, 139, 282, 16);
		panel.add(lbltitleField);

		titleField = new JTextField();
		titleField.setForeground(COLOR_TEXT);
		titleField.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleField.setColumns(10);
		titleField.setCaretColor(COLOR_TEXT);
		titleField.setBorder(border);
		titleField.setBackground(COLOR_BACKGROUND);
		titleField.setBounds(12, 167, 350, 16);
		panel.add(titleField);

		JLabel lblMotDePasse_1 = new JLabel("Tags");
		lblMotDePasse_1.setForeground(COLOR_TEXT);
		lblMotDePasse_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMotDePasse_1.setBounds(12, 208, 282, 16);
		panel.add(lblMotDePasse_1);

		tagsField = new JTextField();
		tagsField.setForeground(COLOR_TEXT);
		tagsField.setFont(new Font("Tahoma", Font.BOLD, 12));
		tagsField.setColumns(10);
		tagsField.setCaretColor(COLOR_TEXT);
		tagsField.setBorder(border);
		tagsField.setBackground(COLOR_BACKGROUND);
		tagsField.setBounds(12, 236, 350, 16);
		panel.add(tagsField);
		
		JLabel lblurlField_1 = new JLabel("Url");
		lblurlField_1.setForeground(COLOR_TEXT);
		lblurlField_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblurlField_1.setBounds(12, 277, 282, 16);
		panel.add(lblurlField_1);
		
		urlField = new JTextField();
		urlField.setForeground(COLOR_TEXT);
		urlField.setFont(new Font("Tahoma", Font.BOLD, 12));
		urlField.setColumns(10);
		urlField.setCaretColor(COLOR_TEXT);
		urlField.setBorder(border);
		urlField.setText(uri);
		urlField.setBackground(COLOR_BACKGROUND);
		urlField.setBounds(12, 300, 350, 16);
		panel.add(urlField);
		
		JButton okButton = new JButton("Save");
		okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		okButton.setForeground(COLOR_TEXT);
		okButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		okButton.setFocusPainted(false);
		okButton.setContentAreaFilled(false);
		okButton.setBorder(new LineBorder(COLOR_TEXT));
		okButton.setBounds(12, 356, 150, 40);
		okButton.setActionCommand("save");
		okButton.addActionListener(this);
		panel.add(okButton);

		JButton closeButton = new JButton("Close");
		closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		closeButton.setForeground(COLOR_TEXT);
		closeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeButton.setFocusPainted(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setBorder(new LineBorder(COLOR_TEXT));
		closeButton.setActionCommand("close");
		closeButton.addActionListener(this);
		closeButton.setBounds(180, 356, 150, 40);
		panel.add(closeButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String actionCommand = e.getActionCommand();
		System.out.println(actionCommand);
		
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
	 * @return the descriptionField
	 */
	public JTextField getDescriptionField() {
		return descriptionField;
	}

	/**
	 * @param descriptionField the descriptionField to set
	 */
	public void setDescriptionField(JTextField descriptionField) {
		this.descriptionField = descriptionField;
	}

	/**
	 * @return the titleField
	 */
	public JTextField getTitleField() {
		return titleField;
	}

	/**
	 * @param titleField the titleField to set
	 */
	public void setTitleField(JTextField titleField) {
		this.titleField = titleField;
	}

	/**
	 * @return the tagsField
	 */
	public JTextField getTagsField() {
		return tagsField;
	}

	/**
	 * @param tagsField the tagsField to set
	 */
	public void setTagsField(JTextField tagsField) {
		this.tagsField = tagsField;
	}

	/**
	 * @return the urlField
	 */
	public JTextField getUrlField() {
		return urlField;
	}

	/**
	 * @param urlField the urlField to set
	 */
	public void setUrlField(JTextField urlField) {
		this.urlField = urlField;
	}
}
