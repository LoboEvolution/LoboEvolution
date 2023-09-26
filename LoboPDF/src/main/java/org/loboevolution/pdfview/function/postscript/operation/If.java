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


final class If implements PostScriptOperation {
	@Override
	/**
	 * {@inheritDoc}
	 *
	 * <i>bool {proc}</i> <b>if</b> - <p>
	 *
	 * removes both operands from the stack, then executes proc
	 * if bool is true. The if operator pushes no results of
	 * its own on the operand stack, but proc may do so (see
	 * Section 3.5, "Execution"). <p>
	 *
	 * Examples <p>
	 * 3 4 lt {(3 is less than 4)} if <p>
	 *
	 * errors: stackunderflow, typecheck
	 */
	public void eval(final Stack<Object> environment) {
	    if ((Boolean)environment.pop()) {
	        environment.push(environment.pop());
	    } else {
	    	environment.pop();
	    }
	}
}
