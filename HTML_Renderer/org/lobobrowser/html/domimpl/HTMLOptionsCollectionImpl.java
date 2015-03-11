package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.domfilter.NodeFilter;
import org.lobobrowser.html.domfilter.OptionFilter;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.HTMLOptionsCollection;
import org.w3c.dom.DOMException;


/**
 * The Class HTMLOptionsCollectionImpl.
 */
public class HTMLOptionsCollectionImpl extends DescendentHTMLCollection
		implements HTMLOptionsCollection {
	
	/** The Constant OPTION_FILTER. */
	public static final NodeFilter OPTION_FILTER = new OptionFilter();

	/**
	 * Instantiates a new HTML options collection impl.
	 *
	 * @param selectElement the select element
	 */
	public HTMLOptionsCollectionImpl(HTMLElementImpl selectElement) {
		super(selectElement, OPTION_FILTER, selectElement.getTreeLock(), false);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionsCollection#setLength(int)
	 */
	public void setLength(int length) throws DOMException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionsCollection#add(org.lobobrowser.html.w3c.HTMLElement)
	 */
	@Override
	public void add(HTMLElement element) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionsCollection#add(org.lobobrowser.html.w3c.HTMLElement, org.lobobrowser.html.w3c.HTMLElement)
	 */
	@Override
	public void add(HTMLElement element, HTMLElement before) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionsCollection#add(org.lobobrowser.html.w3c.HTMLElement, int)
	 */
	@Override
	public void add(HTMLElement element, int before) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionsCollection#remove(int)
	 */
	@Override
	public void remove(int index) {
		// TODO Auto-generated method stub
		
	}
}
