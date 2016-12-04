/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.parser.HtmlParser;
import org.w3c.dom.UserDataHandler;

/**
 * The Class HTMLBaseElementImpl.
 */
public class HTMLBaseElementImpl extends HTMLElementImpl {

	/**
	 * Instantiates a new HTML base element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLBaseElementImpl(String name) {
		super(name, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.DOMNodeImpl#setUserData(java.lang.String,
	 * java.lang.Object, org.w3c.dom.UserDataHandler)
	 */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		boolean dataBool = (boolean)data;
		if (HtmlParser.MODIFYING_KEY.equals(key) && !dataBool) {
			this.processBaseTag();
		}
		return super.setUserData(key, data, handler);
	}

	/**
	 * Process base tag.
	 */
	private final void processBaseTag() {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.setBaseURI(this.getAttribute(HtmlAttributeProperties.HREF));
			doc.setDefaultTarget(this.getAttribute(HtmlAttributeProperties.TARGET));
		}
	}
}
