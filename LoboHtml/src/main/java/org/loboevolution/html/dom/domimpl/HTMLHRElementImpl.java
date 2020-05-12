package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLHRElement;

/**
 * <p>HTMLHRElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLHRElementImpl extends HTMLAbstractUIElement implements HTMLHRElement {
	/**
	 * <p>Constructor for HTMLHRElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLHRElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getNoShade() {
		return "noshade".equalsIgnoreCase(getAttribute("noshade"));
	}

	/** {@inheritDoc} */
	@Override
	public String getSize() {
		return getAttribute("size");
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setNoShade(boolean noShade) {
		setAttribute("noshade", noShade ? "noshade" : null);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(String size) {
		setAttribute("size", size);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
