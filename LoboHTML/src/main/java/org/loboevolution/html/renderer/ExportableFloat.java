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

/**
 * <p>ExportableFloat class.</p>
 *
 *
 *
 */
public class ExportableFloat {
	/** Constant EMPTY_ARRAY */
	public static final ExportableFloat[] EMPTY_ARRAY = new ExportableFloat[0];
	public final BoundableRenderable element;
	public final boolean leftFloat;
	public final int origX;
	public final int origY;

	/**
	 * <p>Constructor for ExportableFloat.</p>
	 *
	 * @param element a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 * @param leftFloat a boolean.
	 * @param origX a int.
	 * @param origY a int.
	 */
	public ExportableFloat(BoundableRenderable element, boolean leftFloat, int origX, int origY) {
		super();
		this.element = element;
		this.leftFloat = leftFloat;
		this.origX = origX;
		this.origY = origY;
	}
}
