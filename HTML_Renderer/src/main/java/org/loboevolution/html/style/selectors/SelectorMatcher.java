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
package org.loboevolution.html.style.selectors;

import java.util.Set;

import org.loboevolution.html.domimpl.HTMLElementImpl;

/**
 * The Class SelectorMatcher.
 */
public class SelectorMatcher {
	
	/** The op equal. */
	public static final String OP_EQUAL = "=";

	/** The op tilde equal. */
	public static final String OP_TILDE_EQUAL = "~=";

	/** The op pipe equal. */
	public static final String OP_PIPE_EQUAL = "|=";

	/** The op dollar equal. */
	public static final String OP_DOLLAR_EQUAL = "$=";

	/** The op circumflex equal. */
	public static final String OP_CIRCUMFLEX_EQUAL = "^=";

	/** The op star equal. */
	public static final String OP_STAR_EQUAL = "*=";

	/** The op all. */
	public static final String OP_ALL = "ALL";

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

	public SelectorMatcher() {
	}

	/**
	 * Instantiates a new simple selector.
	 *
	 * @param simpleSelectorText
	 *            Simple selector text in lower case.
	 * @param pseudoElement
	 *            The pseudo-element if any.
	 */
	public SelectorMatcher(String simpleSelectorText, String pseudoElement) {
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
			return pe == null || names.contains(pe);
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

			if (pe != null && pe.contains("(")) {
				pe = pe.substring(0, pe.indexOf('('));
			}
			return pe == null || names.contains(pe);
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
			return pe == null || pseudoName.equals(pe);
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
	
	public String getOperator(String selectorList) {
		String[] keys = {OP_TILDE_EQUAL, OP_PIPE_EQUAL, OP_DOLLAR_EQUAL, OP_CIRCUMFLEX_EQUAL, OP_STAR_EQUAL, OP_EQUAL};
		for (String key : keys) {
            if (selectorList.contains(key)) {
                return key;
            }
        }
        return OP_ALL;
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
	 * Gets the simple selector text.
	 *
	 * @return the simple selector text
	 */
	public String getSimpleSelectorText() {
		return simpleSelectorText;
	}

	/**
	 * Sets the simple selector text.
	 *
	 * @param simpleSelectorText
	 *            the new simple selector text
	 */
	public void setSimpleSelectorText(String simpleSelectorText) {
		this.simpleSelectorText = simpleSelectorText;
	}

	/**
	 * Gets the pseudo element.
	 *
	 * @return the pseudo element
	 */
	public String getPseudoElement() {
		return pseudoElement;
	}

	/**
	 * Sets the pseudo element.
	 *
	 * @param pseudoElement
	 *            the new pseudo element
	 */
	public void setPseudoElement(String pseudoElement) {
		this.pseudoElement = pseudoElement;
	}
}
