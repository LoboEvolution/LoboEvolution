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
final class Cvi implements PostScriptOperation {
	@Override
	/**
	 * {@inheritDoc}
	 *
	 * <i>num</i> <b>cvi</b> <i>int</i> <u>or</u> <i>string</i> <b>cvi</b> <i>int</i> <p>
	 *
	 * takes an integer, real, or string and produces an
	 * integer result. If the operand is an integer, cvi
	 * simply returns it. If the operand is a real number,
	 * it truncates any fractional part (that is, rounds
	 * it toward 0) and converts it to an integer.
	 * If the operand is a string, cvi invokes the equivalent
	 * of the token operator to interpret the characters
	 * of the string as a number according to the PostScript
	 * syntax rules. If that number is a real number, cvi converts
	 * it to an integer.
	 * A rangecheck error occurs if a real number is too
	 * large to convert to an integer. <p>
	 *
	 * errors: invalidaccess, rangecheck, stackunderflow,
	 *         syntaxError, typecheck,
	 */
	public void eval(Stack<Object> environment) {
	    environment.push(environment.pop());
	}
}
