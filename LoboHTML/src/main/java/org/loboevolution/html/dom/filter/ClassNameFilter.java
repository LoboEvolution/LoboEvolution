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
package org.loboevolution.html.dom.filter;

import org.loboevolution.common.Strings;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import java.util.regex.Pattern;

/**
 * <p>ClassNameFilter class.</p>
 */
public class ClassNameFilter implements NodeFilter {
	
	private static final Pattern CLASS_NAMES_SPLIT_PATTERN = Pattern.compile("\\s");
	 
	private final String className;

	/**
	 * <p>Constructor for ClassNameFilter.</p>
	 *
	 * @param className a {@link java.lang.String} object.
	 */
	public ClassNameFilter(String className) {
		this.className = className;
	}

	/** {@inheritDoc} */
	public short acceptNode(Node node) {
		if (node instanceof Element) {
			String classAttribute = ((Element) node).getAttribute("class");
			if (Strings.isNotBlank(classAttribute) && Strings.isNotBlank(className)) {
				final String[] classNames = CLASS_NAMES_SPLIT_PATTERN.split(className, 0);
				for (String aClassName : classNames) {
					if (!classAttribute.contains(aClassName)) {
						return NodeFilter.FILTER_REJECT;
					}
				}
				return NodeFilter.FILTER_ACCEPT;
			}
		}
		return NodeFilter.FILTER_REJECT;
	}
}
