/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import org.lobobrowser.html.dombl.ModelNode;

/**
 * The Class LineBreak.
 */
public class LineBreak {

	/** The Constant NONE. */
	public static final int NONE = 0;

	/** The Constant LEFT. */
	public static final int LEFT = 1;

	/** The Constant RIGHT. */
	public static final int RIGHT = 2;

	/** The Constant ALL. */
	public static final int ALL = 3;

	/** The break type. */
	private final int breakType;

	/** The new line node. */
	private final ModelNode newLineNode;

	/**
	 * Instantiates a new line break.
	 *
	 * @param breakType
	 *            the break type
	 * @param newLineNode
	 *            the new line node
	 */
	public LineBreak(final int breakType, ModelNode newLineNode) {
		super();
		this.breakType = breakType;
		this.newLineNode = newLineNode;
	}

	/**
	 * Gets the break type.
	 *
	 * @return the break type
	 */
	public int getBreakType() {
		return this.breakType;
	}

	/**
	 * Gets the model node.
	 *
	 * @return the model node
	 */
	public ModelNode getModelNode() {
		return this.newLineNode;
	}

	/**
	 * Gets the break type.
	 *
	 * @param clearAttr
	 *            the clear attr
	 * @return the break type
	 */
	public static int getBreakType(String clearAttr) {
		if (clearAttr == null) {
			return NONE;
		} else if ("all".equalsIgnoreCase(clearAttr)) {
			return ALL;
		} else if ("left".equalsIgnoreCase(clearAttr)) {
			return LEFT;
		} else if ("right".equalsIgnoreCase(clearAttr)) {
			return RIGHT;
		} else {
			return NONE;
		}
	}
}
