/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.input;

import java.awt.Color;

import javax.swing.JButton;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.renderer.HtmlController;

/**
 * <p>InputButton class.</p>
 *
 * @author utente
 * @version $Id: $Id
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
		
		button.setVisible(!modelNode.getHidden());
		button.applyComponentOrientation(ic.direction(modelNode.getDir()));
		button.setEnabled(!modelNode.getDisabled());
		button.setText(getText(modelNode));
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
