/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 4, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.nodeimpl.TextImpl;
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;

/**
 * <p>CDataSectionImpl class.</p>
 *
 *
 *
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
	public NodeType getNodeType() {
		return NodeType.CDATA_SECTION_NODE;
	}

}
