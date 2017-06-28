/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jan 28, 2006
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.w3c.html.HTMLFrameSetElement;
import org.mozilla.javascript.Function;

/**
 * The Class HTMLFrameSetElementImpl.
 */
public class HTMLFrameSetElementImpl extends HTMLElementImpl implements HTMLFrameSetElement {

	/**
	 * Instantiates a new HTML frame set element impl.
	 *
	 * @param name
	 *            the name
	 * @param noStyleSheet
	 *            the no style sheet
	 */
	public HTMLFrameSetElementImpl(String name, boolean noStyleSheet) {
		super(name, noStyleSheet);
	}

	/**
	 * Instantiates a new HTML frame set element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLFrameSetElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameSetElement#getCols()
	 */
	@Override
	public String getCols() {
		return this.getAttribute(HtmlAttributeProperties.COLS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLFrameSetElement#setCols(java.lang.String)
	 */
	@Override
	public void setCols(String cols) {
		this.setAttribute(HtmlAttributeProperties.COLS, cols);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFrameSetElement#getRows()
	 */
	@Override
	public String getRows() {
		return this.getAttribute(HtmlAttributeProperties.ROWS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLFrameSetElement#setRows(java.lang.String)
	 */
	@Override
	public void setRows(String rows) {
		this.setAttribute(HtmlAttributeProperties.ROWS, rows);
	}

	@Override
	public Function getOnafterprint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnafterprint(Function onafterprint) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnbeforeprint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnbeforeprint(Function onbeforeprint) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnbeforeunload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnbeforeunload(Function onbeforeunload) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnhashchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnhashchange(Function onhashchange) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnmessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmessage(Function onmessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnoffline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnoffline(Function onoffline) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnonline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnonline(Function ononline) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnpagehide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpagehide(Function onpagehide) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnpageshow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpageshow(Function onpageshow) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnpopstate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpopstate(Function onpopstate) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnredo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnredo(Function onredo) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnresize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnresize(Function onresize) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnstorage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnstorage(Function onstorage) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnundo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnundo(Function onundo) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnunload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnunload(Function onunload) {
		// TODO Auto-generated method stub

	}
}
