/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Sep 4, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

/**
 * <p>CDataSectionImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CDataSectionImpl extends TextImpl implements CDATASection {

	/**
	 * <p>Constructor for CDataSectionImpl.</p>
	 */
	public CDataSectionImpl() {
		super();
	}

	/**
	 * <p>Constructor for CDataSectionImpl.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public CDataSectionImpl(String text) {
		super(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getnodeName()
	 */
	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#cdata-section";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getnodeType()
	 */
	/** {@inheritDoc} */
	@Override
	public short getNodeType() {
		return Node.CDATA_SECTION_NODE;
	}

}
