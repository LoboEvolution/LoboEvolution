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

package org.loboevolution.menu.tools.pref.startup;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.Serial;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.jtattoo.plaf.lobo.LoboBackground;
import com.jtattoo.plaf.lobo.LoboButton;
import com.jtattoo.plaf.lobo.LoboLabel;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class TextEdit.
 */
public class TextEdit extends JFrame {

	/** The Constant serialVersionUID. */
	@Serial
    private static final long serialVersionUID = 1L;
	
	/** The cancel button. */
	private final LoboButton cancelButton = new LoboButton();

	/** The caption label. */
	private final LoboLabel captionLabel = new LoboLabel("");

	/** The ok button. */
	private final LoboButton okButton = new LoboButton();

	/** The resulting text. */
	@Getter
	@Setter
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
		final LoboBackground lb = new LoboBackground();
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
		contentPane.setBackground(lb.getBackground());
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
	public void setCaption(final String text) {
		this.captionLabel.setText(text);
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(final String text) {
		this.textArea.setText(text);
	}
}
