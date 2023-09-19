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

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.renderer.HtmlController;

import javax.swing.*;
import java.awt.*;

/**
 * <p>InputButton class.</p>
 */
public class InputButton {

	/**
	 * <p>Constructor for InputButton.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputButton(HTMLInputElementImpl modelNode, InputControl ic) {
		final JButton button = new JButton();
		button.setContentAreaFilled(false);
		
		if (modelNode.getTitle() != null) {
			button.setToolTipText(modelNode.getTitle());
		}
		
		button.setVisible(!modelNode.isHidden());
		button.applyComponentOrientation(ic.direction(modelNode.getDir()));
		button.setEnabled(!modelNode.isDisabled());
		button.setText(getText(modelNode));
		button.setPreferredSize(new Dimension(modelNode.getClientWidth(), modelNode.getClientHeight()));
		button.addActionListener(event -> HtmlController.getInstance().onPressed(modelNode, null, 0, 0));
		
		button.setContentAreaFilled(!ic.getRUIControl().hasBackground());
		final Color foregroundColor = ic.getRUIControl().getForegroundColor();
		if (foregroundColor != null) {
			button.setForeground(foregroundColor);
		}

		ic.add(button);
	}

	private String getText(HTMLInputElementImpl element) {
		String text = element.getAttribute("value");
		if (Strings.isBlank(text)) {
			final String type = element.getType();
			if ("submit".equalsIgnoreCase(type)) {
				text = "Submit Query";
			} else if ("reset".equalsIgnoreCase(type)) {
				text = "Reset";
			} else {
				text = "";
			}
		}
		return text;
	}
}
