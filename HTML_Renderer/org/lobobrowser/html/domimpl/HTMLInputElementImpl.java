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
import org.lobobrowser.html.HtmlAttributeProperties;
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
			return this.getAttributeAsBoolean(HtmlAttributeProperties.CHECKED);
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
		return this.getAttribute(HtmlAttributeProperties.SRC);
	}

	public void setSrc(String src) {
		this.setAttribute(HtmlAttributeProperties.SRC, src);
	}

	/**
	 * Gets input type in lowercase.
	 */
	public String getType() {
		String type = this.getAttribute(HtmlAttributeProperties.TYPE);
		return type == null ? null : type.toLowerCase();
	}

	public void setType(String type) {
		this.setAttribute(HtmlAttributeProperties.TYPE, type);
	}

	public String getUseMap() {
		return this.getAttribute(HtmlAttributeProperties.USEMAP);
	}

	public void setUseMap(String useMap) {
		this.setAttribute(HtmlAttributeProperties.USEMAP, useMap);
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
		String autocomplete = this.getAttribute(HtmlAttributeProperties.AUTOCOMPLETE);
		return HtmlAttributeProperties.MUTED.equalsIgnoreCase(autocomplete);
	}

	@Override
	public void setAutocomplete(boolean autocomplete) {
		this.setAttribute(HtmlAttributeProperties.AUTOCOMPLETE, autocomplete ? HtmlAttributeProperties.AUTOCOMPLETE : null);

		
	}

	@Override
	public boolean getAutofocus() {
		String focus = this.getAttribute(HtmlAttributeProperties.AUTOFOCUS);
		return HtmlAttributeProperties.AUTOFOCUS.equalsIgnoreCase(focus);
	}

	@Override
	public void setAutofocus(boolean autofocus) {
		this.setAttribute(HtmlAttributeProperties.AUTOFOCUS, autofocus ? HtmlAttributeProperties.AUTOFOCUS : null);

		
	}

	@Override
	public File[] getFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFormAction() {
		return this.getAttribute(HtmlAttributeProperties.FORMACTION);
	}

	@Override
	public void setFormAction(String formAction) {
		this.setAttribute(HtmlAttributeProperties.FORMACTION, formAction);
		
	}

	@Override
	public String getFormEnctype() {
		return this.getAttribute(HtmlAttributeProperties.FORMENCTYPE);
	}

	@Override
	public void setFormEnctype(String formEnctype) {
		this.setAttribute(HtmlAttributeProperties.FORMENCTYPE, formEnctype);
		
	}

	@Override
	public String getFormMethod() {
		return this.getAttribute(HtmlAttributeProperties.FORMMETHOD);
	}

	@Override
	public void setFormMethod(String formMethod) {
		this.setAttribute(HtmlAttributeProperties.FORMMETHOD, formMethod);
	}

	@Override
	public boolean getFormNoValidate() {
		String formNoValidate = this.getAttribute(HtmlAttributeProperties.FORMNOVALIDATE);
		return HtmlAttributeProperties.FORMNOVALIDATE.equalsIgnoreCase(formNoValidate);
	}

	@Override
	public void setFormNoValidate(boolean formNoValidate) {
		this.setAttribute(HtmlAttributeProperties.FORMNOVALIDATE, formNoValidate ? HtmlAttributeProperties.FORMNOVALIDATE : null);
	}

	@Override
	public String getFormTarget() {
		return this.getAttribute(HtmlAttributeProperties.FORMTARGET);
	}

	@Override
	public void setFormTarget(String formTarget) {
		this.setAttribute(HtmlAttributeProperties.FORMTARGET, formTarget);
		
	}

	@Override
	public String getHeight() {
		return this.getAttribute(HtmlAttributeProperties.HEIGHT);
	}

	@Override
	public void setHeight(String height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, height);
		
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
		return this.getAttribute(HtmlAttributeProperties.MAX);
	}

	@Override
	public void setMax(String max) {
		this.setAttribute(HtmlAttributeProperties.MAX, max);
	}

	@Override
	public String getMin() {
		return this.getAttribute(HtmlAttributeProperties.MIN);
	}

	@Override
	public void setMin(String min) {
		this.setAttribute(HtmlAttributeProperties.MIN, min);
		
	}

	@Override
	public boolean getMultiple() {
		String multiple = this.getAttribute(HtmlAttributeProperties.MULTIPLE);
		return HtmlAttributeProperties.MULTIPLE.equalsIgnoreCase(multiple);
	}

	@Override
	public void setMultiple(boolean multiple) {
		this.setAttribute(HtmlAttributeProperties.MULTIPLE, multiple ? HtmlAttributeProperties.MULTIPLE : null);
		
	}

	@Override
	public String getPattern() {
		return this.getAttribute(HtmlAttributeProperties.PATTERN);
	}

	@Override
	public void setPattern(String pattern) {
		this.setAttribute(HtmlAttributeProperties.PATTERN, pattern);
		
	}

	@Override
	public String getPlaceholder() {
		return this.getAttribute(HtmlAttributeProperties.PLACEHOLDER);
	}

	@Override
	public void setPlaceholder(String placeholder) {
		this.setAttribute(HtmlAttributeProperties.PLACEHOLDER, placeholder);
		
	}

	@Override
	public boolean getRequired() {
		String required = this.getAttribute(HtmlAttributeProperties.REQUIRED);
		return HtmlAttributeProperties.REQUIRED.equalsIgnoreCase(required);
	}

	@Override
	public void setRequired(boolean required) {
		this.setAttribute(HtmlAttributeProperties.REQUIRED, required ? HtmlAttributeProperties.REQUIRED : null);
		
	}

	@Override
	public String getStep() {
		return this.getAttribute(HtmlAttributeProperties.STEP);
	}

	@Override
	public void setStep(String step) {
		this.setAttribute(HtmlAttributeProperties.STEP, step);
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
