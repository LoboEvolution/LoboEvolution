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
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.FormInput;
import org.loboevolution.html.dom.HTMLTextAreaElement;

public class HTMLTextAreaElementImpl extends HTMLBaseInputElement implements HTMLTextAreaElement {
	public HTMLTextAreaElementImpl() {
		super("TEXTAREA");
	}

	public HTMLTextAreaElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.HTMLTextAreaElement#getCols()
	 */
	@Override
	public int getCols() {
		final InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getCols();
	}

	@Override
	protected FormInput[] getFormInputs() {
		final String name = getName();
		if (name == null) {
			return null;
		}
		return new FormInput[] { new FormInput(name, getValue()) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.HTMLTextAreaElement#getRows()
	 */
	@Override
	public int getRows() {
		final InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getRows();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.HTMLTextAreaElement#getType()
	 */
	@Override
	public String getType() {
		return "textarea";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.HTMLTextAreaElement#setCols(int)
	 */
	@Override
	public void setCols(int cols) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setCols(cols);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.HTMLTextAreaElement#setRows(int)
	 */
	@Override
	public void setRows(int rows) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setRows(rows);
		}
	}
}
