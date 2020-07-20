package org.loboevolution.menu.tools.pref.startup;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboPanel;
/**
 * The Class TextEdit.
 *
 * @author utente
 * @version $Id: $Id
 */
public class TextEdit extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The color background. */
	private final Color COLOR_BACKGROUND = new Color(37, 51, 61);

	/** The cancel button. */
	private final LoboButton cancelButton = new LoboButton();

	/** The caption label. */
	private final LoboLabel captionLabel = new LoboLabel("");

	/** The ok button. */
	private final LoboButton okButton = new LoboButton();

	/** The resulting text. */
	private String resultingText;

	/** The text area. */
	private final JTextArea textArea = new JTextArea();

	/**
	 * Instantiates a new simple text edit dialog.
	 */
	public TextEdit() {
		createAndShowGUI();
	}

	/**
	 * Inits the.
	 */
	private void createAndShowGUI() {
		this.captionLabel.setPreferredSize(new Dimension(Short.MAX_VALUE, 32));
		this.captionLabel.setAlignmentX(0.0f);
		this.captionLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
		this.textArea.setPreferredSize(new Dimension(1, Short.MAX_VALUE));
		final Container contentPane = getContentPane();
		contentPane.setBackground(COLOR_BACKGROUND);
		contentPane.setLayout(null);
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
	 * Creates the button panel.
	 *
	 * @return the component
	 */
	private Component createButtonPanel() {
		final LoboPanel panel = new LoboPanel("");
		panel.setBorder(new EmptyBorder(4, 4, 4, 4));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(Box.createHorizontalStrut(5));
		panel.add(this.okButton);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(this.cancelButton);
		panel.add(Box.createHorizontalStrut(5));
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
