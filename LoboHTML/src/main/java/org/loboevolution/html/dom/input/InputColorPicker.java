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
import org.loboevolution.html.js.Executor;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * <p>InputColorPicker class.</p>
 */
public class InputColorPicker {
	
	private final HTMLInputElementImpl modelNode;
	
	final JButton widget = new JButton("Choose Color");

	/**
	 * <p>Constructor for InputColorPicker.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputColorPicker(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		if (modelNode.getTitle() != null) {
			widget.setToolTipText(modelNode.getTitle());
		}
		widget.setVisible(!modelNode.isHidden());
		widget.applyComponentOrientation(ic.direction(modelNode.getDir()));
		widget.setEnabled(!modelNode.isDisabled());

		widget.addActionListener(event -> {
			Color c = JColorChooser.showDialog(null, "Choose a Color", null);
			String value = "#" + Integer.toHexString(c.getRGB()).substring(2);
			modelNode.setValue(value);
			widget.setToolTipText(value);
			widget.setBackground(c);
		});

		MouseInputAdapter mouseHandler = new MouseInputAdapter() {

			@Override
			public void mouseEntered(final MouseEvent e) {
				if (modelNode.getOnmouseover() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnmouseover(), null, new Object[] {});
				}
			}
		};
		widget.addMouseListener(mouseHandler);

		ic.add(widget);
	}

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		Color c = Color.BLACK;
		String value = "#" + Integer.toHexString(c.getRGB()).substring(2);
		modelNode.setValue(value);
		widget.setToolTipText(value);
		widget.setBackground(c);
	}
}
