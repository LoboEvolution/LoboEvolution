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
import org.loboevolution.html.renderer.HtmlController;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * <p>InputCheckbox class.</p>
 */
public class InputCheckbox {
	
	final JCheckBox checkBox = new JCheckBox();

	/**
	 * <p>Constructor for InputCheckbox.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputCheckbox(HTMLInputElementImpl modelNode, InputControl ic) {
		checkBox.setOpaque(false);
		if (modelNode.getTitle() != null) checkBox.setToolTipText(modelNode.getTitle());
		checkBox.setVisible(!modelNode.isHidden());
		checkBox.applyComponentOrientation(ic.direction(modelNode.getDir()));
		checkBox.setSelected(modelNode.getAttributeAsBoolean("checked"));
		checkBox.setEnabled(!modelNode.isDisabled());
		checkBox.setSelected(modelNode.isChecked());
		checkBox.setPreferredSize(new Dimension(modelNode.getClientWidth(), modelNode.getClientHeight()));
		checkBox.addActionListener(event -> HtmlController.getInstance().onPressed(modelNode, null, 0, 0));
		MouseInputAdapter mouseHandler = new MouseInputAdapter() {

			@Override
			public void mouseEntered(final MouseEvent e) {
				if (modelNode.getOnmouseover() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnmouseover(), null, new Object[] {});
				}
			}
		};
		checkBox.addMouseListener(mouseHandler);
		ic.add(checkBox);
	}

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		checkBox.setSelected(false);
	}
}
