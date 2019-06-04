package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dom.HTMLOptionElement;
import org.lobobrowser.html.dom.HTMLOptionsCollection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class HTMLOptionsCollectionImpl extends HTMLCollectionImpl implements HTMLOptionsCollection {
	
	public static final NodeFilter OPTION_FILTER = new OptionFilter();

	public HTMLOptionsCollectionImpl(HTMLElementImpl selectElement) {
		super(selectElement, OPTION_FILTER);
	}

	@Override
	public void setLength(int length) throws DOMException {
		throw new UnsupportedOperationException();
	}
	
	private static class OptionFilter implements NodeFilter {
		@Override
		public boolean accept(Node node) {
			return node instanceof HTMLOptionElement;
		}
	}
}
