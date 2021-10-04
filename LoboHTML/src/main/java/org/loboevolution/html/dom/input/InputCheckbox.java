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

import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.event.MouseInputAdapter;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.renderer.HtmlController;

/**
 * <p>InputCheckbox class.</p>
 *
 *
 *
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
