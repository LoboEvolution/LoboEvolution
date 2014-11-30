/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2011 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.dom;

import java.io.Serializable;

import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.Rect;

/**
 * Implementation of {@link Rect}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 */
public class RectImpl implements Rect, Serializable {

	private static final long serialVersionUID = -7031248513917920621L;

	private CSSPrimitiveValue top_;
	private CSSPrimitiveValue right_;
	private CSSPrimitiveValue bottom_;
	private CSSPrimitiveValue left_;

	/**
	 * Constructor that reads the values from the given chain of LexicalUnits.
	 * 
	 * @param lu
	 *            the values
	 * @throws DOMException
	 *             in case of error
	 */
	public RectImpl(final LexicalUnit lu) throws DOMException {
		LexicalUnit next = lu;
		top_ = new CSSValueImpl(next, true);
		next = next.getNextLexicalUnit(); // ,
		if (next != null) {
			if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
				// error
				throw new DOMException(DOMException.SYNTAX_ERR,
						"Rect parameters must be separated by ','.");
			}
			next = next.getNextLexicalUnit();
			if (next != null) {
				right_ = new CSSValueImpl(next, true);
				next = next.getNextLexicalUnit(); // ,
				if (next != null) {
					if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
						// error
						throw new DOMException(DOMException.SYNTAX_ERR,
								"Rect parameters must be separated by ','.");
					}
					next = next.getNextLexicalUnit();
					if (next != null) {
						bottom_ = new CSSValueImpl(next, true);
						next = next.getNextLexicalUnit(); // ,
						if (next != null) {
							if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
								// error
								throw new DOMException(DOMException.SYNTAX_ERR,
										"Rect parameters must be separated by ','.");
							}
							next = next.getNextLexicalUnit();
							if (next != null) {
								left_ = new CSSValueImpl(next, true);
								next = next.getNextLexicalUnit();
								if (next != null) {
									// error
									throw new DOMException(
											DOMException.SYNTAX_ERR,
											"Too many parameters for rect function.");
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Constructor. The values for the coordinates are null.
	 */
	public RectImpl() {
		super();
	}

	/**
	 * Returns the top part.
	 */
	public CSSPrimitiveValue getTop() {
		return top_;
	}

	/**
	 * Sets the top part to a new value.
	 * 
	 * @param top
	 *            the new CSSPrimitiveValue
	 */
	public void setTop(final CSSPrimitiveValue top) {
		top_ = top;
	}

	/**
	 * Returns the right part.
	 */
	public CSSPrimitiveValue getRight() {
		return right_;
	}

	/**
	 * Sets the right part to a new value.
	 * 
	 * @param right
	 *            the new CSSPrimitiveValue
	 */
	public void setRight(final CSSPrimitiveValue right) {
		right_ = right;
	}

	/**
	 * Returns the bottom part.
	 */
	public CSSPrimitiveValue getBottom() {
		return bottom_;
	}

	/**
	 * Sets the bottom part to a new value.
	 * 
	 * @param bottom
	 *            the new CSSPrimitiveValue
	 */
	public void setBottom(final CSSPrimitiveValue bottom) {
		bottom_ = bottom;
	}

	/**
	 * Returns the left part.
	 */
	public CSSPrimitiveValue getLeft() {
		return left_;
	}

	/**
	 * Sets the left part to a new value.
	 * 
	 * @param left
	 *            the new CSSPrimitiveValue
	 */
	public void setLeft(final CSSPrimitiveValue left) {
		left_ = left;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new StringBuilder("rect(").append(top_).append(", ")
				.append(right_).append(", ").append(bottom_).append(", ")
				.append(left_).append(")").toString();
	}
}