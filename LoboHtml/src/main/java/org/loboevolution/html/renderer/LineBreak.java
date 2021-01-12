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
package org.loboevolution.html.renderer;

import org.loboevolution.html.dom.domimpl.ModelNode;

/**
 * <p>LineBreak class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class LineBreak {
	
	/** Constant LEFT=1 */
	public static final int LEFT = 1;
	/** Constant NONE=0 */
	public static final int NONE = 0;
	/** Constant RIGHT=2 */
	public static final int RIGHT = 2;
	/** Constant BOTH=3 */
	public static final int BOTH = 3;

	/**
	 * <p>Getter for the field breakType.</p>
	 *
	 * @param clearAttr a {@link java.lang.String} object.
	 * @return a int.
	 */
	public static int getBreakType(String clearAttr) {
		if (clearAttr == null) {
			return NONE;
		} else {
			switch (clearAttr) {
			case "right":
				return RIGHT;
			case "left":
				return LEFT;
			case "both":
				return BOTH;
			default:
				return NONE;
			}
		}
	}

	private final int breakType;

	private final ModelNode newLineNode;

	/**
	 * <p>Constructor for LineBreak.</p>
	 *
	 * @param breakType a int.
	 * @param newLineNode a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 */
	public LineBreak(final int breakType, ModelNode newLineNode) {
		super();
		this.breakType = breakType;
		this.newLineNode = newLineNode;
	}

	/**
	 * <p>Getter for the field breakType.</p>
	 *
	 * @return a int.
	 */
	public int getBreakType() {
		return this.breakType;
	}

	/**
	 * <p>getModelNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 */
	public ModelNode getModelNode() {
		return this.newLineNode;
	}
}
