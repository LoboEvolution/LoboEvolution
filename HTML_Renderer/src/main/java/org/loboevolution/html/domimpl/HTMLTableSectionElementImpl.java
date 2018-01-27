package org.loboevolution.html.domimpl;

import org.loboevolution.html.dombl.DescendentHTMLCollection;
import org.loboevolution.html.domfilter.ElementTableAttributeFilter;
import org.loboevolution.w3c.html.HTMLCollection;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLTableSectionElement;
import org.w3c.dom.DOMException;

/**
 * The Class HTMLTableSectionElementImpl.
 */
public class HTMLTableSectionElementImpl extends HTMLAbstractUIElement implements HTMLTableSectionElement {

	public HTMLTableSectionElementImpl(String name) {
		super(name);
	}

	@Override
	public HTMLCollection getRows() {
		return new DescendentHTMLCollection(this, new ElementTableAttributeFilter(TR),
				this.getTreeLock(), false);
	}

	@Override
	public HTMLElement insertRow() {
		return insertRow(-1);
	}

	@Override
	public HTMLElement insertRow(int index) throws DOMException {
		HTMLTableElementImpl table = new HTMLTableElementImpl();
		return table.insertRow(index, this.document);
	}

	@Override
	public void deleteRow(int index) throws DOMException {
		HTMLTableElementImpl table = new HTMLTableElementImpl();
		table.deleteRow(index, this.nodeList);
	}

	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);

	}

	@Override
	public String getCh() {
		return this.getAttribute(CH);
	}

	@Override
	public void setCh(String ch) {
		this.setAttribute(CH, ch);

	}

	@Override
	public String getChOff() {
		return this.getAttribute(CHOFF);
	}

	@Override
	public void setChOff(String chOff) {
		this.setAttribute(CHOFF, chOff);

	}

	@Override
	public String getVAlign() {
		return this.getAttribute(VALIGN);
	}

	@Override
	public void setVAlign(String vAlign) {
		this.setAttribute(VALIGN, vAlign);

	}

}
