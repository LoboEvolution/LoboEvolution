package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dom.HTMLOptionsCollection;
import org.lobobrowser.html.dom.NodeFilter;
import org.lobobrowser.html.dom.filter.OptionFilter;
import org.w3c.dom.DOMException;

public class HTMLOptionsCollectionImpl extends HTMLCollectionImpl implements HTMLOptionsCollection {
	
	public static final NodeFilter OPTION_FILTER = new OptionFilter();

	public HTMLOptionsCollectionImpl(HTMLElementImpl selectElement) {
		super(selectElement, OPTION_FILTER);
	}

	@Override
	public void setLength(int length) throws DOMException {
		throw new UnsupportedOperationException();
	}
}
