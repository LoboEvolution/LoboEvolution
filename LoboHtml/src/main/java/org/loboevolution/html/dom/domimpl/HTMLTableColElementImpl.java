package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLTableColElement;

public class HTMLTableColElementImpl extends HTMLElementImpl implements HTMLTableColElement {

	public HTMLTableColElementImpl(String name) {
		super(name);
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
	public int getSpan() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getvAlign() {
		return getAttribute("valign");
	}

	@Override
	public String getWidth() {
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
	public void setSpan(int span) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setvAlign(String vAlign) {
		setAttribute("valign", vAlign);
		
	}

	@Override
	public void setWidth(String width) {
		// TODO Auto-generated method stub
		
	}


}
