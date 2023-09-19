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

/**
 * <p>InputRadio class.</p>
 */
public class InputRadio {
	
	private ButtonGroup buttonGroup;
	
	final JRadioButton radio = new JRadioButton();

	/**
	 * <p>Constructor for InputRadio.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputRadio(HTMLInputElementImpl modelNode, InputControl ic) {
		radio.setOpaque(false);
		if (modelNode.getTitle() != null) radio.setToolTipText(modelNode.getTitle());
		radio.setVisible(!modelNode.isHidden());
		radio.applyComponentOrientation(ic.direction(modelNode.getDir()));
		radio.setEnabled(!modelNode.isDisabled());
		radio.setSelected(modelNode.isChecked());
		radio.setPreferredSize(new Dimension(modelNode.getClientWidth(), modelNode.getClientHeight()));
		final String name = modelNode.getAttribute("name");
		final ButtonGroup prevGroup = this.buttonGroup;
		if (prevGroup != null) {
			prevGroup.remove(radio);
		}
		if (name != null) {
			final String key = "cobra.radio.group." + name;
			ButtonGroup group = (ButtonGroup) modelNode.getDocumentItem(key);
			if (group == null) {
				group = new ButtonGroup();
				modelNode.setDocumentItem(key, group);
			}
			group.add(radio);
			this.buttonGroup = group;
		} else {
			this.buttonGroup = null;
		}

		ic.add(radio);
	}

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		radio.setSelected(false);
	}
}
