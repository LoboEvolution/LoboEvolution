package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dom.HTMLOptionElement;
import org.lobobrowser.html.dom.HTMLOptionsCollection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class HTMLOptionsCollectionImpl extends DescendentHTMLCollection implements HTMLOptionsCollection {
	private static class OptionFilter implements NodeFilter {
		@Override
		public boolean accept(Node node) {
			return node instanceof HTMLOptionElement;
		}
	}

	public static final NodeFilter OPTION_FILTER = new OptionFilter();

	public HTMLOptionsCollectionImpl(HTMLElementImpl selectElement) {
		super(selectElement, OPTION_FILTER, selectElement.treeLock, false);
	}

	@Override
	public void setLength(int length) throws DOMException {
		throw new UnsupportedOperationException();
	}
}
