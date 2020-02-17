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

import org.loboevolution.common.Strings;
import org.loboevolution.html.FormInput;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.html.dom.input.InputButton;
import org.loboevolution.html.dom.input.InputCheckbox;
import org.loboevolution.html.dom.input.InputColorPicker;
import org.loboevolution.html.dom.input.InputDataTime;
import org.loboevolution.html.dom.input.InputFile;
import org.loboevolution.html.dom.input.InputHidden;
import org.loboevolution.html.dom.input.InputImage;
import org.loboevolution.html.dom.input.InputNumber;
import org.loboevolution.html.dom.input.InputPassword;
import org.loboevolution.html.dom.input.InputRadio;
import org.loboevolution.html.dom.input.InputRange;
import org.loboevolution.html.dom.input.InputText;
import org.loboevolution.html.js.Executor;
import org.mozilla.javascript.Function;
import org.w3c.dom.Node;

public class HTMLInputElementImpl extends HTMLAbstractUIElement implements HTMLInputElement {
	
	private InputText text;
	
	private InputRadio radio;
	
	private InputCheckbox checkbox;
	
	private InputNumber number;
	
	private InputPassword password;
	
	private InputColorPicker color;

	public HTMLInputElementImpl(String name) {
		super(name);
	}

	@Override
	public String getAccept() {
		return getAttribute("accept");
	}

	@Override
	public String getAccessKey() {
		return getAttribute("accessKey");
	}

	@Override
	public String getAlt() {
		return getAttribute("alit");
	}

	@Override
	public boolean getChecked() {
		final String checked = getAttribute("checked");
		return checked == null ? false : true;
	}

	@Override
	public boolean getDisabled() {
		final String disabled = getAttribute("disabled");
		return disabled == null ? false : true;
	}

	@Override
	public HTMLFormElement getForm() {
		Node parent = getParentNode();
		while (parent != null && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	@Override
	public int getMaxLength() {
		try {
			final String maxLength = getAttribute("maxLength");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return Integer.MAX_VALUE;
		}
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}

	@Override
	public boolean getReadOnly() {
		final String readonly = getAttribute("readonly");
		return readonly == null ? false : true;
	}

	@Override
	public int getSize() {
		try {
			final String maxLength = getAttribute("size");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return 20;
		}
	}

	@Override
	public String getSrc() {
		return this.getAttribute("src");
	}

	@Override
	public String getType() {
		final String type = getAttribute("type");
		return type == null ? null : type.toLowerCase();
	}

	@Override
	public String getValue() {
		final String val = getAttribute("value");
		return val == null ? "" : val;
	}

	@Override
	public boolean getAutocomplete() {
		String autocomplete = this.getAttribute("autocomplete");
		return "on".equalsIgnoreCase(autocomplete);
	}

	public String getPlaceholder() {
		return this.getAttribute("placeholder");
	}
	
	@Override
	public void select() {
		if(text!= null) text.selectAll();
	}
	
	@Override
	public void click() {
		Function onclick = getOnclick();		
		if(onclick!= null) {
			Executor.executeFunction(this, onclick, null, new Object[] {});
		}
	}
	
	@Override
	public void blur() {
		if(text!= null) text.blur();
	}
	
	@Override
	public void focus() {
		if(text!= null) text.focus();
	}
	
	@Override
	public void setSelectionRange(int start, int end) {
		if(text!= null) text.setSelectionRange(start, end);
	} 
	
	@Override
	public void setRangeText(String select, int start, int end, String preserve) {
		text.setRangeText(start, end, select);
	}
	
	@Override
	public void setAccept(String accept) {
		setAttribute("accept", accept);
	}

	@Override
	public void setAccessKey(String accessKey) {
		setAttribute("accessKey", accessKey);
	}

	@Override
	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	@Override
	public void setChecked(boolean checked) {
		setAttribute("checked", String.valueOf(checked));
	}

	@Override
	public void setDisabled(boolean disabled) {
		setAttribute("disabled", String.valueOf(disabled));
	}

	@Override
	public void setMaxLength(int maxLength) {
		setAttribute("maxLength", String.valueOf(maxLength));
	}

	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		setAttribute("readonly", String.valueOf(readOnly));
	}

	@Override
	public void setSize(int size) {
		setAttribute("size", String.valueOf(size));
	}

	@Override
	public void setSrc(String src) {
		this.setAttribute("src", src);
	}

	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	@Override
	public void setValue(String value) {
		setAttribute("value", value);
	}
	
	public void setPlaceholder(String placeholder) {
		this.setAttribute("placeholder", placeholder);

	}

	public void draw(InputControl ic) {
		final String type = getType();
		
		if (Strings.isBlank(type)) {
			text = new InputText(this, ic);
		}

		switch (type.toLowerCase()) {
		case "text":
			text = new InputText(this, ic);
			break;
		case "hidden":
			new InputHidden(this, ic);
			break;
		case "submit":
			new InputButton(this, ic);
			break;
		case "password":
			password = new InputPassword(this, ic);
			break;
		case "file":
			new InputFile(this, ic);
			break;
		case "number":
			number = new InputNumber(this, ic);
			break;
		case "color":
			color = new InputColorPicker(this, ic);
			break;
		case "radio":
			radio = new InputRadio(this, ic);
			break;
		case "checkbox":
			checkbox = new InputCheckbox(this, ic);
			break;
		case "button":
			new InputButton(this, ic);
			break;
		case "image":
			new InputImage(this, ic);
			break;
		case "reset":
			new InputButton(this, ic);
			break;
		case "range":
			new InputRange(this, ic);
			break;
		case "date":
		case "datetime-local":
		case "month":
		case "time":
			new InputDataTime(this, ic);
			break;
		default:
			text = new InputText(this, ic);
			break;
		}
	}
	
	public void submit() {
		FormInput[] formInputs;
		final String name = getName();
		if (name == null) {
			formInputs = null;
		} else {
			formInputs = new FormInput[] { new FormInput(name, getValue()) };
		}

		final HTMLFormElementImpl form = (HTMLFormElementImpl) getForm();
		if (form != null) {
			form.submit(formInputs);
		}
	}

	public void reset() {
		final HTMLFormElementImpl form = (HTMLFormElementImpl) getForm();
		if (form != null) {
			form.reset();
		}
	}

	public void submitImage(int x, int y) {
		final String name = getName();
		final String prefix = name == null ? "" : name + ".";
		final FormInput[] extraFormInputs = new FormInput[] { new FormInput(prefix + "x", String.valueOf(x)), new FormInput(prefix + "y", String.valueOf(y)) };
		final HTMLFormElementImpl form = (HTMLFormElementImpl) getForm();
		if (form != null) {
			form.submit(extraFormInputs);
		}
	}

	public void submitForm(final FormInput[] extraFormInputs) {
		final HTMLFormElementImpl form = (HTMLFormElementImpl) this.getForm();
		if (form != null) {
			form.submit(extraFormInputs);
		}
	}

	public void resetInput() {
		if(text != null) text.reset();
		if(radio != null) radio.reset();
		if(checkbox != null) checkbox.reset();
		if(color != null) color.reset();
		if(number != null) number.reset();
		if(password != null) password.reset();
	}
}