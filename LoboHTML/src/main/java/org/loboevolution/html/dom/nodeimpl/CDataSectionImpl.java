/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 4, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Node;

/**
 * <p>CDataSectionImpl class.</p>
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


	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#cdata-section";
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.CDATA_SECTION_NODE;
	}

}
