/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 4, 2005
 */
package org.loboevolution.html.domimpl;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

/**
 * The Class DOMCDataSectionImpl.
 */
public class DOMCDataSectionImpl extends DOMTextImpl implements CDATASection {

	/**
	 * Instantiates a new DOMC data section impl.
	 */
	public DOMCDataSectionImpl() {
		super();
	}

	/**
	 * Instantiates a new DOMC data section impl.
	 *
	 * @param text
	 *            the text
	 */
	public DOMCDataSectionImpl(String text) {
		super(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DOMNodeImpl#getnodeName()
	 */
	@Override
	public String getNodeName() {
		return "#cdata-section";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DOMNodeImpl#getnodeType()
	 */
	@Override
	public short getNodeType() {
		return Node.CDATA_SECTION_NODE;
	}

}
