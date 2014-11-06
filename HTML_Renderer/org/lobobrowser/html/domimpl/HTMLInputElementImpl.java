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

import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.dombl.InputContext;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.HTMLInputElement;
import org.lobobrowser.html.w3c.HTMLOptionElement;
import org.lobobrowser.html.w3c.ValidityState;
import org.w3c.dom.NodeList;

public class HTMLInputElementImpl extends HTMLBaseInputElement implements
		HTMLInputElement {
	public HTMLInputElementImpl(String name) {
		super(name);
	}

	private boolean defaultChecked;

	public boolean getDefaultChecked() {
		return this.defaultChecked;
	}

	public void setDefaultChecked(boolean defaultChecked) {
		this.defaultChecked = defaultChecked;
	}

	public boolean getChecked() {
		InputContext ic = this.inputContext;
		if (ic == null) {
			return this.getAttributeAsBoolean("checked");
		} else {
			return ic.getChecked();
		}
	}

	public void setChecked(boolean checked) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setChecked(checked);
		}
	}

	public int getMaxLength() {
		InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getMaxLength();
	}

	public void setMaxLength(int maxLength) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setMaxLength(maxLength);
		}
	}

	public int getSize() {
		InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getControlSize();
	}

	public void setSize(int size) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setControlSize(size);
		}
	}

	public String getSrc() {
		return this.getAttribute("src");
	}

	public void setSrc(String src) {
		this.setAttribute("src", src);
	}

	/**
	 * Gets input type in lowercase.
	 */
	public String getType() {
		String type = this.getAttribute("type");
		return type == null ? null : type.toLowerCase();
	}

	public void setType(String type) {
		this.setAttribute("type", type);
	}

	public String getUseMap() {
		return this.getAttribute("usemap");
	}

	public void setUseMap(String useMap) {
		this.setAttribute("usemap", useMap);
	}

	public void click() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.click();
		}
	}

	public boolean isSubmittableWithEnterKey() {
		String type = this.getType();
		return (type == null || "".equals(type) || "text".equals(type) || "password"
				.equals(type));
	}

	public boolean isSubmittableWithPress() {
		String type = this.getType();
		return "submit".equals(type) || "image".equals(type);
	}

	public boolean isSubmitInput() {
		String type = this.getType();
		return "submit".equals(type);
	}

	public boolean isImageInput() {
		String type = this.getType();
		return "image".equals(type);
	}

	public boolean isResetInput() {
		String type = this.getType();
		return "reset".equals(type);
	}

	void resetInput() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.resetInput();
		}
	}

	protected FormInput[] getFormInputs() {
		String type = this.getType();
		String name = this.getName();
		if (name == null) {
			return null;
		}
		if (type == null) {
			return new FormInput[] { new FormInput(name, this.getValue()) };
		} else {
			if ("text".equals(type) || "password".equals(type)
					|| "hidden".equals(type) || "".equals(type)) {
				return new FormInput[] { new FormInput(name, this.getValue()) };
			} else if ("submit".equals(type)) {
				// It's done as an "extra" form input
				return null;
			} else if ("radio".equals(type) || "checkbox".equals(type)) {
				if (this.getChecked()) {
					String value = this.getValue();
					if (value == null || value.length() == 0) {
						value = "on";
					}
					return new FormInput[] { new FormInput(name, value) };
				} else {
					return null;
				}
			} else if ("image".equals(type)) {
				// It's done as an "extra" form input
				return null;
			} else if ("file".equals(type)) {
				java.io.File file = this.getFileValue();
				if (file == null) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("getFormInputs(): File input named " + name
								+ " has null file.");
					}
					return null;
				} else {
					return new FormInput[] { new FormInput(name, file) };
				}
			} else {
				return null;
			}
		}
	}

	@Override
	public boolean getAutocomplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutocomplete(boolean autocomplete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getAutofocus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutofocus(boolean autofocus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public File[] getFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFormAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFormAction(String formAction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFormEnctype() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFormEnctype(String formEnctype) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFormMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFormMethod(String formMethod) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getFormNoValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFormNoValidate(boolean formNoValidate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFormTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFormTarget(String formTarget) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHeight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHeight(String height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getIndeterminate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIndeterminate(boolean indeterminate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HTMLElement getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMax(String max) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMin(String min) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMultiple(boolean multiple) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPattern(String pattern) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPlaceholder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlaceholder(String placeholder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRequired(boolean required) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStep(String step) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getValueAsDate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setValueAsDate(long valueAsDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getValueAsNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setValueAsNumber(float valueAsNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HTMLOptionElement getSelectedOption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWidth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWidth(String width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepUp(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepDown(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getWillValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSelectionStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelectionStart(int selectionStart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSelectionEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelectionEnd(int selectionEnd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectionRange(int start, int end) {
		// TODO Auto-generated method stub
		
	}
}
