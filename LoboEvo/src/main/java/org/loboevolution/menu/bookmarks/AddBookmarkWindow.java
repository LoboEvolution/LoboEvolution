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
 * @author utente
 * @version $Id: $Id
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
	 * @return the descriptionField
	 */
	public LoboTextField getDescriptionField() {
		return descriptionField;
	}

	/**
	 * @param descriptionField the descriptionField to set
	 */
	public void setDescriptionField(LoboTextField descriptionField) {
		this.descriptionField = descriptionField;
	}

	/**
	 * @return the titleField
	 */
	public LoboTextField getTitleField() {
		return titleField;
	}

	/**
	 * @param titleField the titleField to set
	 */
	public void setTitleField(LoboTextField titleField) {
		this.titleField = titleField;
	}

	/**
	 * @return the tagsField
	 */
	public LoboTextField getTagsField() {
		return tagsField;
	}

	/**
	 * @param tagsField the tagsField to set
	 */
	public void setTagsField(LoboTextField tagsField) {
		this.tagsField = tagsField;
	}

	/**
	 * @return the urlField
	 */
	public LoboTextField getUrlField() {
		return urlField;
	}

	/**
	 * @param urlField the urlField to set
	 */
	public void setUrlField(LoboTextField urlField) {
		this.urlField = urlField;
	}
}
