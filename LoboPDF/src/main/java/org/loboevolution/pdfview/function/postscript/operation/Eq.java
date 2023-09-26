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


final class Eq implements PostScriptOperation {
	@Override
	/**
	 * {@inheritDoc}
	 *
	 * <i>any1 <i>any2</i> <b>eq</b> <i>bool</i> <p>
	 *
	 * pops two objects from the operand stack and pushes\
	 * true if they are equal, or false if not.
	 * The definition of equality depends on the types of
	 * the objects being compared.
	 * Simple objects are equal if their types and values
	 * are the same. Strings are equal if their lengths and
	 * individual elements are equal.
	 *  Other composite objects
	 * (arrays and dictionaries) are equal only if they share
	 * the same value. Separate values are considered unequal,
	 * even if all the components of those values are the
	 * same.
	 * This operator performs some type conversions.
	 * Integers and real numbers can be compared freely:
	 * an integer and a real number representing the same
	 * mathematical value are considered equal by eq.
	 * Strings and names can likewise be compared freely:
	 * a name defined by some sequence of characters is equal
	 * to a string whose elements are the same sequence of
	 * characters.
	 * The literal/executable and access attributes of
	 * objects are not considered in comparisons
	 * between objects. <p>
	 *
	 * errors: invalidaccess, stackunderflow
	 */
	public void eval(final Stack<Object> environment) {
	    environment.push(environment.pop().equals(environment.pop()));
	}
}
