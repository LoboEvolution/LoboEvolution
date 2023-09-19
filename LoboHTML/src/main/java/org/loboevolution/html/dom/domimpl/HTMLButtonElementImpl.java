/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.control.ButtonControl;
import org.loboevolution.html.dom.HTMLButtonElement;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.html.renderer.HtmlController;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;

import javax.swing.*;
import java.awt.*;

/**
 * <p>HTMLButtonElementImpl class.</p>
 */
public class HTMLButtonElementImpl extends HTMLElementImpl implements HTMLButtonElement {

	/**
	 * <p>Constructor for HTMLButtonElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLButtonElementImpl(final String name) {
		super(name);
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
		Node parent = this.getParentNode();
		while ((parent != null) && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		final String name = getAttribute("name");
		return name == null ? "" : name;
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
	final String val = getAttribute("type");
	return val == null ? "submit" : val;
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		return getAttribute("value");
	}

	/** {@inheritDoc} */
	@Override
	public void setDisabled(boolean disabled) {
		setAttribute("disabled", String.valueOf(disabled));
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		setAttribute("value", value);
		
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param buttonControl a {@link org.loboevolution.html.control.ButtonControl} object.
	 */
	public void draw(ButtonControl buttonControl) {
		final JButton button = new JButton();
		button.setContentAreaFilled(false);
		button.setText(getText());
		button.setEnabled(!isDisabled());
		button.setPreferredSize(new Dimension(getClientWidth(), getClientHeight()));
		button.addActionListener(event -> HtmlController.getInstance().onMouseClick(this, null, 0, 0));
		buttonControl.add(button);
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
	 * <p>resetInput.</p>
	 */
	public void resetInput() {
		final HTMLFormElementImpl form = (HTMLFormElementImpl) getForm();
		if (form != null && form.hasChildNodes()) {
			NodeListImpl childNodes = (NodeListImpl) form.getChildNodes();
			childNodes.forEach(node -> {
				if (node instanceof HTMLInputElementImpl) {
					final HTMLInputElementImpl hie = (HTMLInputElementImpl) node;
					hie.resetInput();
				}
			});
		}
	}

	private String getText() {
		StringBuilder text = new StringBuilder();
		if (hasChildNodes()) {
			NodeListImpl childNodes = (NodeListImpl) getChildNodes();
			childNodes.forEach(child -> {
				if (child.getNodeType() == Node.TEXT_NODE) {
					String nodeValue = child.getNodeValue();
					String childText;
					nodeValue = nodeValue.replace('\n', ' ');
					nodeValue = nodeValue.replace('\r', ' ');
					nodeValue = nodeValue.replace('\t', ' ');
					childText = nodeValue;
					text.append(childText).append(" ");
				}
			});
		}

		if (text.length() > 0) {
			return text.substring(0, text.length() - 1);
		} else {
			return text.toString();
		}
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_INLINE_BLOCK);
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
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setType(String type) {
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

	@Override
	public int getClientHeight() {
		int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 2 : clientHeight;
	}

	@Override
	public Integer getClientWidth() {
		int clientWidth = super.getClientWidth();
		return clientWidth == 0 ? 12 : clientWidth;
	}

	@Override
	public Integer getOffsetWidth() {
		int offsetWidth = super.getOffsetWidth();
		return offsetWidth == 0 ? 22 : offsetWidth;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLButtonElement]";
	}
}
