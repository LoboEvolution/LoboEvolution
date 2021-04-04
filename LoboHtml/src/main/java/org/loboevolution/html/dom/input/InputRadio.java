/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.input;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

/**
 * <p>InputRadio class.</p>
 *
 *
 *
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
		if(modelNode.getTitle() != null) radio.setToolTipText(modelNode.getTitle());
		radio.setVisible(!modelNode.getHidden());
		radio.applyComponentOrientation(ic.direction(modelNode.getDir()));
		radio.setEnabled(!modelNode.isDisabled());
		radio.setSelected(modelNode.isChecked());
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
