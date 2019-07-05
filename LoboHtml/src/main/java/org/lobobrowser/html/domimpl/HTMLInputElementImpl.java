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
package org.lobobrowser.html.domimpl;

import java.io.File;
import java.util.logging.Level;

import org.lobo.common.Strings;
import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.dom.HTMLInputElement;

public class HTMLInputElementImpl extends HTMLBaseInputElement implements HTMLInputElement {
	
	private final String IMAGE = "image";
	
	private final String SUBMIT = "submit";
	
	private final String TEXT = "tetx";
	
	private final String PASSWORD = "password";
	
	private final String HIDDEN = "hidden";
	
	private final String RADIO = "radio";
	
	private final String CHECKBOX = "checkbox";
	
	private final String RESET = "reset";
	
	private final String FILE = "file";
	
	private boolean defaultChecked;

	public HTMLInputElementImpl(String name) {
		super(name);
	}

	@Override
	public void click() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.click();
		}
	}

	@Override
	public boolean getChecked() {
		final InputContext ic = this.inputContext;
		if (ic == null) {
			return getAttributeAsBoolean("checked");
		} else {
			return ic.getChecked();
		}
	}

	@Override
	public boolean getDefaultChecked() {
		return this.defaultChecked;
	}

	@Override
	protected FormInput[] getFormInputs() {
		final String type = Strings.isBlank(getType()) ? "" : getType();
		final String name = getName();
		if (name == null) {
			return null;
		}

		switch (type) {
		case TEXT:
		case PASSWORD:
		case HIDDEN:
		case "":
			return new FormInput[] { new FormInput(name, getValue()) };
		case RADIO:
		case CHECKBOX:
			if (getChecked()) {
				String value = getValue();
				if (value == null || value.length() == 0) {
					value = "on";
				}
				return new FormInput[] { new FormInput(name, value) };
			} else {
				return null;
			}
		case SUBMIT:
		case IMAGE:
			return null;
		case FILE:
			final File files = getFileValue();
			if (files == null) {
				return null;
			} else {
				return new FormInput[] { new FormInput(name, files) };
			}
		default:
			return null;
		}
	}

	@Override
	public int getMaxLength() {
		final InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getMaxLength();
	}

	@Override
	public int getSize() {
		final InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getControlSize();
	}

	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	/**
	 * Gets input type in lowercase.
	 */
	@Override
	public String getType() {
		final String type = getAttribute("type");
		return type == null ? null : type.toLowerCase();
	}

	@Override
	public String getUseMap() {
		return getAttribute("usemap");
	}

	public boolean isImageInput() {
		final String type = getType();
		return IMAGE.equals(type);
	}

	public boolean isResetInput() {
		final String type = getType();
		return RESET.equals(type);
	}

	public boolean isSubmitInput() {
		final String type = getType();
		return SUBMIT.equals(type);
	}

	public boolean isSubmittableWithEnterKey() {
		final String type = getType();
		return (Strings.isBlank(type) || TEXT.equals(type) || PASSWORD.equals(type));
	}

	public boolean isSubmittableWithPress() {
		final String type = getType();
		return SUBMIT.equals(type) || IMAGE.equals(type);
	}

	@Override
	void resetInput() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.resetInput();
		}
	}

	@Override
	public void setChecked(boolean checked) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setChecked(checked);
		}
	}

	@Override
	public void setDefaultChecked(boolean defaultChecked) {
		this.defaultChecked = defaultChecked;
	}

	@Override
	public void setMaxLength(int maxLength) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setMaxLength(maxLength);
		}
	}

	@Override
	public void setSize(int size) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setControlSize(size);
		}
	}

	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}

	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	@Override
	public void setUseMap(String useMap) {
		setAttribute("usemap", useMap);
	}
}
