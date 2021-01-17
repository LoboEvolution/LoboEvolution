/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Jan 15, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.html.dom.input.FormInput;
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

/**
 * <p>HTMLInputElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLInputElementImpl extends HTMLAbstractUIElement implements HTMLInputElement {
	
	private InputText text;
	
	private InputRadio radio;
	
	private InputCheckbox checkbox;
	
	private InputNumber number;
	
	private InputPassword password;
	
	private InputColorPicker color;

	/**
	 * <p>Constructor for HTMLInputElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLInputElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAccept() {
		return getAttribute("accept");
	}

	/** {@inheritDoc} */
	@Override
	public String getAccessKey() {
		return getAttribute("accessKey");
	}

	/** {@inheritDoc} */
	@Override
	public String getAlt() {
		return getAttribute("alit");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getChecked() {
		final String checked = getAttribute("checked");
		return checked != null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getDisabled() {
		final String disabled = getAttribute("disabled");
		return disabled != null;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLFormElement getForm() {
		Node parent = getParentNode();
		while (parent != null && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	/** {@inheritDoc} */
	@Override
	public int getMaxLength() {
		try {
			final String maxLength = getAttribute("maxLength");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return Integer.MAX_VALUE;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getReadOnly() {
		final String readonly = getAttribute("readonly");
		return readonly != null;
	}

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		try {
			final String maxLength = getAttribute("size");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return 20;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getSrc() {
		return this.getAttribute("src");
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		final String type = getAttribute("type");
		return type == null ? null : type.toLowerCase();
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		final String val = getAttribute("value");
		return val == null ? "" : val;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getAutocomplete() {
		String autocomplete = this.getAttribute("autocomplete");
		return "on".equalsIgnoreCase(autocomplete);
	}

	/**
	 * <p>getPlaceholder.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPlaceholder() {
		return this.getAttribute("placeholder");
	}
	
	/** {@inheritDoc} */
	@Override
	public void select() {
		if(text!= null) text.selectAll();
	}
	
	/** {@inheritDoc} */
	@Override
	public void click() {
		Function onclick = getOnclick();		
		if(onclick!= null) {
			Executor.executeFunction(this, onclick, null, new Object[] {});
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public void blur() {
		if(text!= null) text.blur();
	}
	
	/** {@inheritDoc} */
	@Override
	public void focus() {
		if(text!= null) text.focus();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setSelectionRange(int start, int end) {
		if(text!= null) text.setSelectionRange(start, end);
	} 
	
	/** {@inheritDoc} */
	@Override
	public void setRangeText(String select, int start, int end, String preserve) {
		text.setRangeText(start, end, select);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setAccept(String accept) {
		setAttribute("accept", accept);
	}

	/** {@inheritDoc} */
	@Override
	public void setAccessKey(String accessKey) {
		setAttribute("accessKey", accessKey);
	}

	/** {@inheritDoc} */
	@Override
	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	/** {@inheritDoc} */
	@Override
	public void setChecked(boolean checked) {
		setAttribute("checked", String.valueOf(checked));
	}

	/** {@inheritDoc} */
	@Override
	public void setDisabled(boolean disabled) {
		setAttribute("disabled", String.valueOf(disabled));
	}

	/** {@inheritDoc} */
	@Override
	public void setMaxLength(int maxLength) {
		setAttribute("maxLength", String.valueOf(maxLength));
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	/** {@inheritDoc} */
	@Override
	public void setReadOnly(boolean readOnly) {
		setAttribute("readonly", String.valueOf(readOnly));
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(int size) {
		setAttribute("size", String.valueOf(size));
	}

	/** {@inheritDoc} */
	@Override
	public void setSrc(String src) {
		this.setAttribute("src", src);
	}

	/** {@inheritDoc} */
	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		setAttribute("value", value);
	}
	
	/**
	 * <p>setPlaceholder.</p>
	 *
	 * @param placeholder a {@link java.lang.String} object.
	 */
	public void setPlaceholder(String placeholder) {
		this.setAttribute("placeholder", placeholder);

	}

	/**
	 * <p>draw.</p>
	 *
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public void draw(InputControl ic) {
		String type = getType();
		
		if (Strings.isBlank(type)) {
			type = "text";
		}

		switch (type.toLowerCase()) {
		case "text":
			text = new InputText(this, ic);
			break;
		case "hidden":
			new InputHidden(this, ic);
			break;
		case "submit":
			case "reset":
			case "button":
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
			case "image":
			new InputImage(this, ic);
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
	
	/**
	 * <p>submit.</p>
	 */
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

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		final HTMLFormElementImpl form = (HTMLFormElementImpl) getForm();
		if (form != null) {
			form.reset();
		}
	}

	/**
	 * <p>submitImage.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public void submitImage(int x, int y) {
		final String name = getName();
		final String prefix = name == null ? "" : name + ".";
		final FormInput[] extraFormInputs = new FormInput[] { new FormInput(prefix + "x", String.valueOf(x)), new FormInput(prefix + "y", String.valueOf(y)) };
		final HTMLFormElementImpl form = (HTMLFormElementImpl) getForm();
		if (form != null) {
			form.submit(extraFormInputs);
		}
	}

	/**
	 * <p>submitForm.</p>
	 *
	 * @param extraFormInputs an array of {@link org.loboevolution.html.dom.input.FormInput} objects.
	 */
	public void submitForm(final FormInput[] extraFormInputs) {
		final HTMLFormElementImpl form = (HTMLFormElementImpl) this.getForm();
		if (form != null) {
			form.submit(extraFormInputs);
		}
	}

	/**
	 * <p>resetInput.</p>
	 */
	public void resetInput() {
		if(text != null) text.reset();
		if(radio != null) radio.reset();
		if(checkbox != null) checkbox.reset();
		if(color != null) color.reset();
		if(number != null) number.reset();
		if(password != null) password.reset();
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLInputElement]";
	}
}
