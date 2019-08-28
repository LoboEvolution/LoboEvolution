/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.control;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.dom.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.renderer.HtmlController;

public class InputTextControl extends BaseInputTextControl {

	private static final long serialVersionUID = 1L;

	public InputTextControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		JTextField text = (JTextField) this.widget;
		if (modelNode.getTitle() != null) text.setToolTipText(modelNode.getTitle());
		text.setVisible(!modelNode.getHidden());
		
		text.applyComponentOrientation(direction(modelNode.getDir()));
		text.setEditable(new Boolean(modelNode.getContentEditable()));
		text.setEnabled(!modelNode.getDisabled());
		text.addActionListener(event -> HtmlController.getInstance().onEnterPressed(modelNode, null));
	}

	@Override
	protected JTextComponent createTextField() {
		return new JTextField();
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
	}
}