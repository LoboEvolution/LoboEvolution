/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.style.selectors;

import java.util.Set;

import org.lobobrowser.html.domimpl.HTMLElementImpl;

/**
 * The Class SimpleSelector.
 */
public class SimpleSelector {

	/** The Constant ANCESTOR. */
	public static final int ANCESTOR = 0;

	/** The Constant PARENT. */
	public static final int PARENT = 1;

	/** The Constant PRECEEDING_SIBLING. */
	public static final int PRECEEDING_SIBLING = 2;

	/** The simple selector text. */
	private String simpleSelectorText;

	/** The pseudo element. */
	private String pseudoElement;

	/** The selector type. */
	private int selectorType;

	/**
	 * Instantiates a new simple selector.
	 *
	 * @param simpleSelectorText
	 *            Simple selector text in lower case.
	 * @param pseudoElement
	 *            The pseudo-element if any.
	 */
	public SimpleSelector(String simpleSelectorText, String pseudoElement) {
		super();
		this.simpleSelectorText = simpleSelectorText;
		this.pseudoElement = pseudoElement;
		this.selectorType = ANCESTOR;
	}

	/**
	 * Matches.
	 *
	 * @param element
	 *            the element
	 * @return true, if successful
	 */
	public final boolean matches(HTMLElementImpl element) {
		Set<String> names = element.getPseudoNames();
		if (names == null) {
			return this.pseudoElement == null;
		} else {
			String pe = this.pseudoElement;
			return (pe == null) || names.contains(pe);
		}
	}

	/**
	 * Matches.
	 *
	 * @param names
	 *            the names
	 * @return true, if successful
	 */
	public final boolean matches(Set names) {
		if (names == null) {
			return this.pseudoElement == null;
		} else {
			String pe = this.pseudoElement;
			return (pe == null) || names.contains(pe);
		}
	}

	/**
	 * Matches.
	 *
	 * @param pseudoName
	 *            the pseudo name
	 * @return true, if successful
	 */
	public final boolean matches(String pseudoName) {
		if (pseudoName == null) {
			return this.pseudoElement == null;
		} else {
			String pe = this.pseudoElement;
			return (pe == null) || pseudoName.equals(pe);
		}
	}

	/**
	 * Checks for pseudo name.
	 *
	 * @param pseudoName
	 *            the pseudo name
	 * @return true, if successful
	 */
	public final boolean hasPseudoName(String pseudoName) {
		return pseudoName.equals(this.pseudoElement);
	}

	/**
	 * Gets the selector type.
	 *
	 * @return the selector type
	 */
	public int getSelectorType() {
		return selectorType;
	}

	/**
	 * Sets the selector type.
	 *
	 * @param selectorType
	 *            the new selector type
	 */
	public void setSelectorType(int selectorType) {
		this.selectorType = selectorType;
	}

	/**
	 * @return the simpleSelectorText
	 */
	public String getSimpleSelectorText() {
		return simpleSelectorText;
	}

	/**
	 * @param simpleSelectorText the simpleSelectorText to set
	 */
	public void setSimpleSelectorText(String simpleSelectorText) {
		this.simpleSelectorText = simpleSelectorText;
	}

	/**
	 * @return the pseudoElement
	 */
	public String getPseudoElement() {
		return pseudoElement;
	}

	/**
	 * @param pseudoElement the pseudoElement to set
	 */
	public void setPseudoElement(String pseudoElement) {
		this.pseudoElement = pseudoElement;
	}
}
