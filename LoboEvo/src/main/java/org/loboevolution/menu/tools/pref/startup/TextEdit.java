package org.loboevolution.menu.tools.pref.startup;

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
import com.jtattoo.plaf.lobo.LoboLookAndFeel;
/**
 * The Class TextEdit.
 *
 * @author utente
 * @version $Id: $Id
 */
public class TextEdit extends JFrame implements LoboLookAndFeel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
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
		setResizable(false);
		this.captionLabel.setPreferredSize(new Dimension(Short.MAX_VALUE, 32));
		this.captionLabel.setAlignmentX(0.0f);
		this.captionLabel.setBorder(new EmptyBorder(8, 0, 8, 0));
		this.okButton.setAction(new TextEditOkAction(this.textArea, this));
		this.okButton.setText("OK");
		this.cancelButton.setAction(new TextEditCancelAction(this));
		this.cancelButton.setText("Cancel");
		
		final Box rootBox = new Box(BoxLayout.Y_AXIS);
		rootBox.setBorder(new EmptyBorder(4, 4, 4, 4));
		rootBox.add(this.captionLabel);
		rootBox.add(new JScrollPane(this.textArea));
		rootBox.add(this.createButtonPanel());
		
		final Container contentPane = getContentPane();
		contentPane.setBackground(background());
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(rootBox);
	}
	
	
	/**
	 * Creates the button panel.
	 *
	 * @return the component
	 */
	private Component createButtonPanel() {
		final Box panel = new Box(BoxLayout.X_AXIS);
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
