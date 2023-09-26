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

package org.loboevolution.pdfview.function.postscript.operation;

import java.util.Stack;


final class Atan implements PostScriptOperation {
	@Override
	/**
	 * {@inheritDoc}
	 *
	 * <i>num den</i> <b>atan</b> <i>angle</i> <p>
	 *
	 * returns the angle (in degress between
	 * 0 and 360) whose tangent is num divided by den.
	 * Either num or den may be 0, but not both. The signs
	 * of num and den determine the quadrant in which the
	 * result will lie: positive num yeilds a result in the
	 * positive y plane, while a positive den yeilds a result in
	 * the positive x plane. The result is a real number.<p>
	 *
	 * errors: stackunderflow, typecheck, undefinedresult
	 */
	public void eval(final Stack<Object> environment) {
	    final double den = (Double)environment.pop();
	    final double num = (Double)environment.pop();
	    if (den == 0.0) {
	        environment.push(90.0);
	    } else {
	        environment.push(Math.toDegrees(Math.atan(num / den)));
	    }
	}
}
