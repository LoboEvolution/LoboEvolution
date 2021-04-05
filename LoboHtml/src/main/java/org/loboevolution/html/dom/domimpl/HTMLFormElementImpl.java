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
 * Created on Jan 14, 2006
 */
package org.loboevolution.html.dom.domimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.filter.InputFilter;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.html.dom.nodeimpl.NodeVisitor;
import org.loboevolution.html.js.Executor;
import org.loboevolution.http.HtmlRendererContext;
import org.mozilla.javascript.Function;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>HTMLFormElementImpl class.</p>
 *
 *
 *
 */
public class HTMLFormElementImpl extends HTMLElementImpl implements HTMLFormElement {

	/**
	 * <p>isInput.</p>
	 *
	 * @param node a {@link org.w3c.dom.Node} object.
	 * @return a boolean.
	 */
	public static boolean isInput(Node node) {
		final String name = node.getNodeName().toLowerCase();
		return name.equals("input") || name.equals("textarea") || name.equals("select");
	}

	private HTMLCollection elements;

	/**
	 * <p>Constructor for HTMLFormElementImpl.</p>
	 */
	public HTMLFormElementImpl() {
		super("FORM");
	}

	/**
	 * <p>Constructor for HTMLFormElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLFormElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAcceptCharset() {
		return getAttribute("acceptCharset");
	}

	/** {@inheritDoc} */
	@Override
	public String getAction() {
		return getAttribute("action");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getElements() {
		HTMLCollection elements = this.elements;
		if (elements == null) {
			elements = new HTMLCollectionImpl(this, new InputFilter());
			this.elements = elements;
		}
		return elements;
	}

	/** {@inheritDoc} */
	@Override
	public String getEnctype() {
		return getAttribute("enctype");
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return getElements().getLength();
	}

	/** {@inheritDoc} */
	@Override
	public String getMethod() {
		String method = getAttribute("method");
		if (method == null) {
			method = "GET";
		}
		return method;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getTarget() {
		return getAttribute("target");
	}

	/**
	 * <p>item.</p>
	 *
	 * @param index a int.
	 * @return a {@link java.lang.Object} object.
	 */
	public Object item(final int index) {
		try {
			visit(new NodeVisitor() {
				private int current = 0;

				@Override
				public void visit(Node node) {
					if (HTMLFormElementImpl.isInput(node)) {
						if (this.current == index) {
							throw new StopVisitorException(node);
						}
						this.current++;
					}
				}
			});
		} catch (final StopVisitorException sve) {
			return sve.getTag();
		}
		return null;
	}

	/**
	 * <p>namedItem.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.lang.Object} object.
	 */
	public Object namedItem(final String name) {
		try {
			// TODO: This could use document.namedItem.
			visit(node -> {
				if (HTMLFormElementImpl.isInput(node)) {
					if (name.equals(((Element) node).getAttribute("name"))) {
						throw new StopVisitorException(node);
					}
				}
			});
		} catch (final StopVisitorException sve) {
			return sve.getTag();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void reset() { 
		visit(node -> {
			if (node instanceof HTMLInputElementImpl) {
				final HTMLInputElementImpl hie = (HTMLInputElementImpl) node;
				hie.resetInput();
			}
			
			if (node instanceof HTMLButtonElementImpl) {
				final HTMLButtonElementImpl btn = (HTMLButtonElementImpl) node;
				btn.resetInput();
			}
			
			if (node instanceof HTMLSelectElementImpl) {
				final HTMLSelectElementImpl slct = (HTMLSelectElementImpl) node;
				slct.resetInput();
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void setAcceptCharset(String acceptCharset) {
		setAttribute("acceptCharset", acceptCharset);
	}

	/** {@inheritDoc} */
	@Override
	public void setAction(String action) {
		setAttribute("action", action);
	}

	/** {@inheritDoc} */
	@Override
	public void setEnctype(String enctype) {
		setAttribute("enctype", enctype);
	}

	/** {@inheritDoc} */
	@Override
	public void setMethod(String method) {
		setAttribute("method", method);
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	/** {@inheritDoc} */
	@Override
	public void setTarget(String target) {
		setAttribute("target", target);
	}

	/** {@inheritDoc} */
	@Override
	public void submit() {
		this.submit(null);
	}

	/**
	 * This method should be called when form submission is done by a submit button.
	 *
	 * @param extraFormInputs Any additional form inputs that need to be submitted,
	 *                        e.g. the submit button parameter.
	 */
	public final void submit(final FormInput[] extraFormInputs) {
		final Function onsubmit = getOnsubmit();
		if (onsubmit != null) {
			if (!Executor.executeFunction(this, onsubmit, null, new Object[0])) {
				return;
			}
		}
		final HtmlRendererContext context = getHtmlRendererContext();
		if (context != null) {
			final ArrayList<FormInput> formInputs = new ArrayList<>();
			if (extraFormInputs != null) {
				Collections.addAll(formInputs, extraFormInputs);
			}
			visit(node -> {
				if (node instanceof HTMLElementImpl) {
					final FormInput[] fis = ((HTMLElementImpl) node).getFormInputs();
					if (fis != null) {
						for (final FormInput fi : fis) {
							if (fi.getName() == null) {
								throw new IllegalStateException("Form input does not have a name: " + node);
							}
							formInputs.add(fi);
						}
					}
				}
			});
			final FormInput[] fia = formInputs.toArray(FormInput.EMPTY_ARRAY);
			String href = getAction();
			if (href == null) {
				href = getBaseURI();
			}
			try {
				final URL url = getFullURL(href);
				context.submitForm(getMethod(), url, getTarget(), getEnctype(), fia);
			} catch (final MalformedURLException mfu) {
				this.warn("submit()", mfu);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getAccessKey() {
		// TODO Auto-generated method stub
		return null;
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
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
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
	public void click() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getAutocomplete() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setAutocomplete(String autocomplete) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setEncoding(String encoding) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNoValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setNoValidate(boolean noValidate) {
		// TODO Auto-generated method stub
		
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
	public String toString() {
		return "[object HTMLFormElement]";
	}
}
