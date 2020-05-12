package org.loboevolution.menu.tools.pref.startup;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * The Class SimpleTextEditDialog.
 *
 * @author utente
 * @version $Id: $Id
 */
public class SimpleTextEditDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cancel button. */
	private final JButton cancelButton = new JButton();

	/** The caption label. */
	private final JLabel captionLabel = new JLabel();

	/** The ok button. */
	private final JButton okButton = new JButton();

	/** The resulting text. */
	private String resultingText;

	/** The text area. */
	private final JTextArea textArea = new JTextArea();

	/**
	 * Instantiates a new simple text edit dialog.
	 *
	 * @param parent the parent
	 */
	public SimpleTextEditDialog(Dialog parent) {
		super(parent);
		init();
	}

	/**
	 * Instantiates a new simple text edit dialog.
	 *
	 * @param parent the parent
	 */
	public SimpleTextEditDialog(Frame parent) {
		super(parent);
		init();
	}

	/**
	 * Creates the button panel.
	 *
	 * @return the component
	 */
	private Component createButtonPanel() {
		final Box panel = new Box(BoxLayout.X_AXIS);
		panel.setPreferredSize(new Dimension(Short.MAX_VALUE, 0));
		panel.setBorder(new EmptyBorder(4, 4, 4, 4));
		panel.add(Box.createGlue());
		panel.add(this.okButton);
		panel.add(Box.createRigidArea(new Dimension(4, 1)));
		panel.add(this.cancelButton);
		panel.add(Box.createGlue());
		return panel;
	}

	/**
	 * Gets the resulting text.
	 *
	 * @return the resulting text
	 */
	public String getResultingText() {
		return this.resultingText;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return this.textArea.getText();
	}

	/**
	 * Inits the.
	 */
	private void init() {
		this.captionLabel.setPreferredSize(new Dimension(Short.MAX_VALUE, 32));
		this.captionLabel.setAlignmentX(0.0f);
		this.captionLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
		this.textArea.setPreferredSize(new Dimension(1, Short.MAX_VALUE));
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(this.captionLabel);
		contentPane.add(new JScrollPane(this.textArea));
		contentPane.add(createButtonPanel());
		this.textArea.setEditable(true);
		this.okButton.setAction(new TextEditOkAction(this.textArea, this));
		this.okButton.setText("OK");
		this.cancelButton.setAction(new TextEditCancelAction(this));
		this.cancelButton.setText("Cancel");
	}

	/**
	 * Sets the caption.
	 *
	 * @param text the new caption
	 */
	public void setCaption(String text) {
		this.captionLabel.setText(text);
	}

	/**
	 * <p>Setter for the field resultingText.</p>
	 *
	 * @param resultingText a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String setResultingText(String resultingText) {
		return this.resultingText = resultingText;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.textArea.setText(text);
	}
}
