/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.html.control;

import java.awt.Dimension;

import javax.swing.text.JTextComponent;

import org.lobobrowser.html.dombl.JTextFieldImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

/**
 * The Class InputHiddenControl.
 */
public class InputHiddenControl extends BaseInputTextControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public InputHiddenControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
	}

	@Override
	protected JTextComponent createTextField() {
		JTextFieldImpl hidden = new JTextFieldImpl();
		hidden.setVisible(false);
		return hidden;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(0, 0);
	}
}
