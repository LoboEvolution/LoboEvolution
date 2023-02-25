/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
