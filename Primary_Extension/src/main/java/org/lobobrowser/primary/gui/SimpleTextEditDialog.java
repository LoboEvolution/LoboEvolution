/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui;

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

import org.lobobrowser.primary.action.TextEditCancelAction;
import org.lobobrowser.primary.action.TextEditOkAction;

/**
 * The Class SimpleTextEditDialog.
 */
public class SimpleTextEditDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The caption label. */
	private final JLabel captionLabel = new JLabel();

	/** The text area. */
	private final JTextArea textArea = new JTextArea();

	/** The ok button. */
	private final JButton okButton = new JButton();

	/** The cancel button. */
	private final JButton cancelButton = new JButton();

	/** The resulting text. */
	private String resultingText;

	/**
	 * Instantiates a new simple text edit dialog.
	 *
	 * @param parent
	 *            the parent
	 */
	public SimpleTextEditDialog(Frame parent) {
		super(parent);
		this.init();
	}

	/**
	 * Instantiates a new simple text edit dialog.
	 *
	 * @param parent
	 *            the parent
	 */
	public SimpleTextEditDialog(Dialog parent) {
		super(parent);
		this.init();
	}

	/**
	 * Sets the caption.
	 *
	 * @param text
	 *            the new caption
	 */
	public void setCaption(String text) {
		this.captionLabel.setText(text);
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.textArea.setText(text);
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
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(this.captionLabel);
		contentPane.add(new JScrollPane(this.textArea));
		contentPane.add(this.createButtonPanel());
		this.textArea.setEditable(true);
		this.okButton.setAction(new TextEditOkAction(textArea, this));
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
		Box panel = new Box(BoxLayout.X_AXIS);
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

	public String setResultingText(String resultingText) {
		return this.resultingText = resultingText;
	}
}
