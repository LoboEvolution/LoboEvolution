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
package org.loboevolution.html.control;

import java.awt.ComponentOrientation;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

/**
 * <p>InputControl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputControl extends BaseControl {

	private static final long serialVersionUID = 1L;
	
	private final HTMLInputElementImpl  modelNode;
	
	/**
	 * <p>Constructor for InputControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 */
	public InputControl(HTMLInputElementImpl modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		this.modelNode = modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		modelNode.draw(this);
	}
	
	/**
	 * <p>direction.</p>
	 *
	 * @param dir a {@link java.lang.String} object.
	 * @return a {@link java.awt.ComponentOrientation} object.
	 */
	public ComponentOrientation direction(String dir) {
		if ("ltr".equalsIgnoreCase(dir)) {
			return ComponentOrientation.LEFT_TO_RIGHT;
		} else if ("rtl".equalsIgnoreCase(dir)) {
			return ComponentOrientation.RIGHT_TO_LEFT;
		} else {
			return ComponentOrientation.UNKNOWN;
		}
	}
}
