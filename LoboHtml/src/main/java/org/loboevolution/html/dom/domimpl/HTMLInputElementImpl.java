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
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.jsenum.Direction;

/**
 * <p>HTMLInputElementImpl class.</p>
 *
 *
 *
 */
public class HTMLInputElementImpl extends HTMLElementImpl implements HTMLInputElement {
	
	private InputText text;
	
	private InputRadio radio;
	
	private InputCheckbox checkbox;
	
	private InputNumber number;
	
	private InputPassword password;
	
	private InputColorPicker color;
	
	private int selectionStart = 0;
	
	private int selectionEnd = 0;
	
	private boolean focusable = false;

	
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
	public boolean isDisabled() {
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
	public double getMaxLength() {
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
	public boolean isReadOnly() {
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
	
	/**
	 * <p>blur.</p>
	 */
	public void blur() {
		if(text!= null) text.blur();
		if(text!= null) {text.blur();} else {focusable = true;}
	}
	
	/**
	 * <p>focus.</p>
	 */
	public void focus() {
		if(text!= null) {text.focus();} else {focusable = true;}
	}
	
    /** {@inheritDoc} */
	@Override
    public int getSelectionStart() {
		final int textLenght = getTextLength();
        return (selectionStart > textLenght || selectionStart < 0) ? textLenght : selectionStart;
    }

    /** {@inheritDoc} */
	@Override
    public void setSelectionStart(int start) {
       this.selectionStart = start;
    }
	
    /** {@inheritDoc} */
	@Override
    public int getSelectionEnd() {
		final int textLenght = getTextLength();
        return (selectionEnd > textLenght || selectionEnd < 0) ? textLenght : selectionEnd;
    }

    /** {@inheritDoc} */
	@Override
    public void setSelectionEnd(int end) {
        this.selectionEnd = end;
    }

    /** {@inheritDoc} */
	@Override
    public void setSelectionRange(int start, int end) {
        setSelectionStart(start);
        setSelectionEnd(end);
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
	public void setMaxLength(double maxLength) {
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
		if(text!= null) text.setText(value);
		setAttribute("value", value);
		setSelectionStart(Strings.isBlank(value) ? 0 : value.length());
        setSelectionEnd(Strings.isBlank(value) ? 0 : value.length());
	}
		
	/**
	 * <p>getTextLength.</p>
	 *
	 * @return a int.
	 */
	public int getTextLength() {
		return getValue().length();
	}
	
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>setPlaceholder.</p>
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
	
	/**
	 * <p>isFocusable.</p>
	 *
	 * @return the focusable
	 */
	public boolean isFocusable() {
		return focusable;
	}

	/**
	 * <p>Setter for the field <code>focusable</code>.</p>
	 *
	 * @param focusable the focusable to set
	 */
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	/** {@inheritDoc} */
	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getAutocapitalize() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSpellcheck() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setAutocapitalize(String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setDraggable(boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setTranslate(boolean translate) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setAutocomplete(String autocomplete) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAutofocus() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setAutofocus(boolean autofocus) {
		// TODO Auto-generated method stub
		
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
	public String getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setDefaultValue(String defaultValue) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getDirName() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setDirName(String dirName) {
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
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
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
	public double getMinLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setMinLength(double minLength) {
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
	public boolean isRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setRequired(boolean required) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getSelectionDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setSelectionDirection(String selectionDirection) {
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
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
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
	public boolean isWillValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean reportValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setRangeText(String replacement) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setRangeText(String replacement, int start, int end) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setSelectionRange(int start, int end, Direction direction) {
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
