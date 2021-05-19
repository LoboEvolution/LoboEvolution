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
/*
 * Created on Jan 15, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.html.dom.input.*;
import org.loboevolution.html.node.Node;

import java.awt.*;
import java.util.Map;

/**
 * <p>HTMLInputElementImpl class.</p>
 */
public class HTMLInputElementImpl extends HTMLBasicInputElement implements HTMLInputElement {

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
	public String getAlt() {
		return getAttribute("alit");
	}

	/** {@inheritDoc} */
	@Override
	public boolean isChecked() {
		final String checked = getAttribute("checked");
		return checked != null;
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
	public String getName() {
		return getAttribute("name");
	}

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		try {
			final String size = getAttribute("size");
			return Integer.parseInt(size.trim());
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
	public void select() {
		if(text!= null) text.selectAll();
	}

	/**
	 * <p>blur.</p>
	 */
	public void blur() {
		if(text!= null) {text.blur();} else {setFocusable(false);}
	}
	
	/**
	 * <p>focus.</p>
	 */
	public void focus() {
		if(text!= null) {text.focus();} else {setFocusable(true);}
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
	public void setName(String name) {
		setAttribute("name", name);
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
		setSelectionStart(Strings.isBlank(value) ? 0 : value.length());
        setSelectionEnd(Strings.isBlank(value) ? 0 : value.length());
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 * @param map a {@link java.util.Map} object.
	 */
	public void draw(InputControl ic, Map<String, Image> map) {
		String type = getType();
		
		if (Strings.isBlank(type)) {
			type = "text";
		}

		switch (type.toLowerCase()) {
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
			new InputImage(this, ic, map);
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
		case "text":
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
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefaultChecked() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setDefaultChecked(boolean defaultChecked) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getFormAction() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setFormAction(String formAction) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getFormEnctype() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setFormEnctype(String formEnctype) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getFormMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setFormMethod(String formMethod) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isFormNoValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setFormNoValidate(boolean formNoValidate) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getFormTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setFormTarget(String formTarget) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(double height) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isIndeterminate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setIndeterminate(boolean indeterminate) {
		// TODO Auto-generated method stub
		
	}
	/** {@inheritDoc} */
	@Override
	public HTMLElement getList() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getMax() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setMax(String max) {
		// TODO Auto-generated method stub
		
	}
	

	/** {@inheritDoc} */
	@Override
	public String getMin() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setMin(String min) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setMultiple(boolean multiple) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setPattern(String pattern) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getStep() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setStep(String step) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getUseMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setUseMap(String useMap) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public double getValueAsNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setValueAsNumber(double valueAsNumber) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(double width) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void stepDown(double n) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void stepDown() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void stepUp(double n) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void stepUp() {
		// TODO Auto-generated method stub
		
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLInputElement]";
	}
}
