package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLBRElement;

/**
 * <p>HTMLBRElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
	/**
	 * <p>Constructor for HTMLBRElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLBRElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		buffer.append("\r\n");
		super.appendInnerTextImpl(buffer);
	}

	/** {@inheritDoc} */
	@Override
	public String getClear() {
		return getAttribute("clear");
	}

	/** {@inheritDoc} */
	@Override
	public void setClear(String clear) {
		setAttribute("clear", clear);
	}
}
