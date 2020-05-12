package org.loboevolution.menu.bookmarks;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.loboevolution.gui.FieldType;
import org.loboevolution.gui.FormField;
import org.loboevolution.gui.FormPanel;

/**
 * <p>AddBookmarkWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class AddBookmarkWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The description field. */
	private final transient FormField descriptionField = new FormField(FieldType.TEXT, "Description:");

	/** The tags field. */
	private final transient FormField tagsField = new FormField(FieldType.TEXT, "Tags:");

	/** The title field. */
	private final transient FormField titleField = new FormField(FieldType.TEXT, "Title:");

	/** The url field. */
	private transient FormField urlField = new FormField(FieldType.TEXT, "URL:");

	/**
	 * <p>Constructor for AddBookmarkWindow.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	public AddBookmarkWindow(String uri) {
		createAndShowGUI(uri);
	}

	private void createAndShowGUI(String uri) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/bookmark.png"));
		setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		this.urlField.setEditable(false);
		this.tagsField.setToolTip("List of keywords separated by blanks.");
		this.urlField.setValue(uri);
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		final FormPanel fieldsPanel = new FormPanel();
		fieldsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		fieldsPanel.addField(this.urlField);
		fieldsPanel.addField(this.tagsField);
		fieldsPanel.addField(this.descriptionField);
		fieldsPanel.addField(this.tagsField);

		final Dimension fpps = fieldsPanel.getPreferredSize();
		fieldsPanel.setPreferredSize(new Dimension(400, fpps.height));

		contentPane.add(fieldsPanel);
		final JComponent buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		final JButton okButton = new JButton();
		okButton.setAction(new OkCancelAddBookAction(this, true));
		okButton.setText("Save");
		final JButton cancelButton = new JButton();
		cancelButton.setAction(new OkCancelAddBookAction(this, false));
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
	 * <p>Getter for the field descriptionField.</p>
	 *
	 * @return the descriptionField
	 */
	public FormField getDescriptionField() {
		return this.descriptionField;
	}

	/**
	 * <p>Getter for the field tagsField.</p>
	 *
	 * @return the tagsField
	 */
	public FormField getTagsField() {
		return this.tagsField;
	}

	/**
	 * <p>Getter for the field titleField.</p>
	 *
	 * @return the titleField
	 */
	public FormField getTitleField() {
		return this.titleField;
	}

	/**
	 * <p>Getter for the field urlField.</p>
	 *
	 * @return the urlField
	 */
	public FormField getUrlField() {
		return this.urlField;
	}

	/**
	 * <p>Setter for the field urlField.</p>
	 *
	 * @param urlField the urlField to set
	 */
	public void setUrlField(FormField urlField) {
		this.urlField = urlField;
	}
}
