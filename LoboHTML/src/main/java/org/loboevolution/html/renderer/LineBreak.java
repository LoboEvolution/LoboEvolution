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
package org.loboevolution.html.renderer;

import org.loboevolution.html.dom.nodeimpl.ModelNode;

/**
 * <p>LineBreak class.</p>
 *
 *
 *
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
	 * @param newLineNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
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
	 * @return a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public ModelNode getModelNode() {
		return this.newLineNode;
	}
}
