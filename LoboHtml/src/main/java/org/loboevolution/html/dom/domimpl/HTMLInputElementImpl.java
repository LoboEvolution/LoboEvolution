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
import org.w3c.dom.Node;

public class HTMLInputElementImpl extends HTMLAbstractUIElement implements HTMLInputElement {

	public HTMLInputElementImpl(String name) {
		super(name);
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
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
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public String getAlt() {
		return getAttribute("alit");
	}

	@Override
	public boolean getChecked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getDefaultChecked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDefaultValue() {
		return getAttribute("defaultValue");
	}

	@Override
	public boolean getDisabled() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}

	@Override
	public boolean getReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSrc() {
		return this.getAttribute("src");
	}

	@Override
	public int getTabIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		final String type = getAttribute("type");
		return type == null ? null : type.toLowerCase();
	}

	@Override
	public String getUseMap() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
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
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	@Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaultChecked(boolean defaultChecked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaultValue(String defaultValue) {
		setAttribute("defaultValue", defaultValue);
	}

	@Override
	public void setDisabled(boolean disabled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxLength(int maxLength) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSize(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSrc(String src) {
		this.setAttribute("src", src);
		
	}

	@Override
	public void setTabIndex(int tabIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	@Override
	public void setUseMap(String useMap) {
		// TODO Auto-generated method stub
		
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
			new InputText(this, ic);
		}

		switch (type.toLowerCase()) {
		case "text":
			new InputText(this, ic);
			break;
		case "hidden":
			new InputHidden(this, ic);
			break;
		case "submit":
			new InputButton(this, ic);
			break;
		case "password":
			new InputPassword(this, ic);
			break;
		case "file":
			new InputFile(this, ic);
			break;
		case "number":
			new InputNumber(this, ic);
			break;
		case "color":
			new InputColorPicker(this, ic);
			break;
		case "radio":
			new InputRadio(this, ic);
			break;
		case "checkbox":
			new InputCheckbox(this, ic);
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
			new InputText(this, ic);
			break;
		}
	}
}