/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.filter;

import java.util.regex.Pattern;

import org.loboevolution.html.dom.NodeFilter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <p>ClassNameFilter class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ClassNameFilter implements NodeFilter {
	
	private static final Pattern CLASS_NAMES_SPLIT_PATTERN = Pattern.compile("\\s");
	 
	private final String className;

	/**
	 * <p>Constructor for ClassNameFilter.</p>
	 *
	 * @param _class a {@link java.lang.String} object.
	 */
	public ClassNameFilter(String className) {
		this.className = className;
	}

	/** {@inheritDoc} */
	public boolean accept(Node node) {
		if(node instanceof Element) {
			String classAttribute = ((Element) node).getAttribute("class");
			if(classAttribute != null) {
				final String[] classNames = CLASS_NAMES_SPLIT_PATTERN.split(className, 0);
				for (String aClassName : classNames) {
					if (!classAttribute.contains(aClassName)) {
						return false;
					}
				}
				return true;
			}
		}
		return false; 
	}
}
