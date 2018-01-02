/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
 * Created on Oct 15, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

/**
 * The Class DOMImplementationImpl.
 */
public class DOMImplementationImpl implements DOMImplementation {

	/** The context. */
	private final UserAgentContext context;

	/**
	 * Instantiates a new DOM implementation impl.
	 *
	 * @param context
	 *            the context
	 */
	public DOMImplementationImpl(UserAgentContext context) {
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMImplementation#hasFeature(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean hasFeature(String feature, String version) {
		return "HTML".equals(feature) && "2.0".compareTo(version) <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMImplementation#createDocumentType(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) throws DOMException {
		return new DOMDocumentTypeImpl(qualifiedName, publicId, systemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMImplementation#createDocument(java.lang.String,
	 * java.lang.String, org.w3c.dom.DocumentType)
	 */
	@Override
	public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype)
			throws DOMException {
		return new HTMLDocumentImpl(this.context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMImplementation#getFeature(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Object getFeature(String feature, String version) {
		if ("HTML".equals(feature) && "2.0".compareTo(version) <= 0) {
			return this;
		} else {
			return null;
		}
	}
}
