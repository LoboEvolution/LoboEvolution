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
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.node.DOMStringList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>DOMStringListImpl class.</p>
 */
public class DOMStringListImpl implements DOMStringList {
	private final List<String> sourceList;

	/**
	 * <p>Constructor for DOMStringListImpl.</p>
	 *
	 * @param sourceList a {@link java.util.Set} object.
	 */
	public DOMStringListImpl(Set<String> sourceList) {
		final List<String> list = new ArrayList<>(sourceList);
		this.sourceList = list;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(String str) {
		return this.sourceList.contains(str);
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.sourceList.size();
	}

	/** {@inheritDoc} */
	@Override
	public String item(int index) {
        int size = this.sourceList.size();
        if (size > index && index > -1) {
            return this.sourceList.get(index);
        } else {
            return null;
        }
	}
}
