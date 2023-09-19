/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.dom.input;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * <p>InputFile class.</p>
 */
public class InputFile extends BasicInput {
	
	private final JTextField textField = new JTextField();
	
	private final InputControl control;

	/**
	 * <p>Constructor for InputFile.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputFile(HTMLInputElementImpl modelNode, InputControl ic) {
		control = ic;
		setElement(modelNode);
		setjComponent(textField);
		final JButton browseButton = new JButton();
		browseButton.setContentAreaFilled(false);
		browseButton.setAction(new BrowseAction());
		browseButton.setText("Browse");
		this.textField.setEditable(false);
		if (modelNode.getTitle() != null)
			this.textField.setToolTipText(modelNode.getTitle());
		textField.setVisible(!modelNode.isHidden());
		textField.applyComponentOrientation(ic.direction(modelNode.getDir()));
		textField.setEditable(Boolean.parseBoolean(modelNode.getContentEditable()));
		textField.setEnabled(!modelNode.isDisabled());
		textField.setPreferredSize(new Dimension(modelNode.getClientWidth(), modelNode.getClientHeight()));
		textField.addFocusListener(this);
		textField.addKeyListener(this);
		textField.addCaretListener(this);
		textField.addMouseListener(this);

		Box buttonBar = Box.createHorizontalBox();
		buttonBar.add(Box.createHorizontalGlue());
		buttonBar.add(textField);
		buttonBar.add(browseButton);
		ic.add(buttonBar, BorderLayout.SOUTH);
	}
	
	private class BrowseAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(final ActionEvent e) {
			final JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(control) == JFileChooser.APPROVE_OPTION) {
				String paths = chooser.getSelectedFile().getAbsolutePath();
				textField.setText(paths);
			} else {
				textField.setText("");
			}
		}
	}
}
