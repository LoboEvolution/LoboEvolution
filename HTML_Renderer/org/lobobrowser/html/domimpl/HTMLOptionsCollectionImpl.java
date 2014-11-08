package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.dombl.NodeFilter;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.HTMLOptionElement;
import org.lobobrowser.html.w3c.HTMLOptionsCollection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class HTMLOptionsCollectionImpl extends DescendentHTMLCollection
		implements HTMLOptionsCollection {
	public static final NodeFilter OPTION_FILTER = new OptionFilter();

	public HTMLOptionsCollectionImpl(HTMLElementImpl selectElement) {
		super(selectElement, OPTION_FILTER, selectElement.treeLock, false);
	}

	public void setLength(int length) throws DOMException {
		throw new UnsupportedOperationException();
	}

	private static class OptionFilter implements NodeFilter {
		public boolean accept(Node node) {
			return node instanceof HTMLOptionElement;
		}
	}

	@Override
	public void add(HTMLElement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLElement element, HTMLElement before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLElement element, int before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int index) {
		// TODO Auto-generated method stub
		
	}
}
