/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
/*
 * Created on Jan 15, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.TextAreaControl;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLTextAreaElement;
import org.loboevolution.html.dom.input.TextArea;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLTextAreaElementImpl class.</p>
 */
public class HTMLTextAreaElementImpl extends HTMLBasicInputElement implements HTMLTextAreaElement {
	
	private TextArea textArea;

	private String value;

	private boolean isSet = false;

	/**
	 * <p>Constructor for HTMLTextAreaElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTextAreaElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public int getCols() {	
		final String cols = this.getAttribute("cols");
		if (Strings.isNotBlank(cols)  && Strings.isNumeric(cols)) {
			final float rowsInt = Float.parseFloat(cols);
			if (rowsInt > -1) {
				return (int) rowsInt;
			} else {
				return 20;
			}
		}
		return 20;
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
	public int getRows() {
		final String rows = this.getAttribute("rows");
		if (Strings.isNotBlank(rows)  && Strings.isNumeric(rows)) {
			final float rowsInt = Float.parseFloat(rows);
			if (rowsInt > -1) {
				return (int) rowsInt;
			} else {
				return 2;
			}
		}
		return 2;
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		return isSet ? this.value : getText();
	}

	/** {@inheritDoc} */
	@Override
	public void select() {
		if (textArea!= null) textArea.selectAll();
		
	}

	/** {@inheritDoc} */
	@Override
	public void setCols(final Integer cols) {
		setAttribute("cols", String.valueOf(cols));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setName(final String name) {
		setAttribute("name", name);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setRows(final Integer rows) {
		setAttribute("rows", String.valueOf(rows));
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(final String value) {
		this.value = Strings.isBlank(value) ? "" : value;
		this.isSet = true;
		setSelectionStart(Strings.isBlank(value) ? 0 : value.length());
        setSelectionEnd(Strings.isBlank(value) ? 0 : value.length());
	}

	/**
	 * <p>blur.</p>
	 */
	public void blur() {
		if (textArea!= null) {textArea.blur();} else {setFocusable(false);}
	}
	
	/**
	 * <p>focus.</p>
	 */
	public void focus() {
		if (textArea!= null) {textArea.focus();} else {setFocusable(true);}
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param ic a {@link org.loboevolution.html.control.TextAreaControl} object.
	 */
	public void draw(final TextAreaControl ic) {
		textArea = new TextArea(this, ic);
	}
	
	
	private String getText() {
		final StringBuilder text = new StringBuilder();
		if (hasChildNodes()) {
			final NodeListImpl children = (NodeListImpl)getChildNodes();
			children.forEach(child -> {
				if (child.getNodeType() == Node.TEXT_NODE) {
					String nodeValue = child.getNodeValue();
					final String childText;
					nodeValue = nodeValue.replace('\n', ' ');
					nodeValue = nodeValue.replace('\r', ' ');
					nodeValue = nodeValue.replace('\t', ' ');
					childText = nodeValue;
					text.append(childText).append(" ");
				}
			});
		}

		if (!text.isEmpty()) {
			return text.substring(0, text.length() - 1);
		} else {
			return text.toString();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getWrap() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setWrap(final String wrap) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getClientHeight() {
		final int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 34 : clientHeight;
	}

	@Override
	public Integer getClientWidth() {
		final int clientWidth = super.getClientWidth();
		return clientWidth == 0 ? 168 : clientWidth;
	}

	@Override
	public Integer getOffsetWidth() {
		final int offsetWidth = super.getOffsetWidth();
		return offsetWidth == 0 ? 183 : offsetWidth;
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_INLINE_BLOCK);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTextAreaElement]";
	}
}
