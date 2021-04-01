package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLTableRowElement;
import org.loboevolution.html.dom.HTMLTableSectionElement;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.w3c.dom.DOMException;

public class HTMLTableSectionElementImpl extends HTMLElementImpl implements HTMLTableSectionElement {

	public HTMLTableSectionElementImpl(String name) {
		super(name);
	}

	@Override
	public void deleteRow(int index) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

	@Override
	public HTMLCollection getRows() {
		 return new HTMLCollectionImpl(this, new ElementFilter("TR"));
	}

	@Override
	public String getvAlign() {
		return getAttribute("valign");
	}

	@Override
	public HTMLTableRowElement insertRow(int index) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HTMLTableRowElement insertRow() throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
		
	}

	@Override
	public void setCh(String ch) {
		setAttribute("ch", ch);
		
	}

	@Override
	public void setChOff(String chOff) {
		setAttribute("choff", chOff);
		
	}

	@Override
	public void setvAlign(String vAlign) {
		setAttribute("valign", vAlign);
		
	}
	
	@Override
	public String toString() {
		return "[object HTMLTableSectionElement]";
	}

}
