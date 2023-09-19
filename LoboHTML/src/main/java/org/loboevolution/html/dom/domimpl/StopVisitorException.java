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
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

class StopVisitorException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Object tag;

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 */
	public StopVisitorException() {
		super();
		this.tag = null;
	}

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 *
	 * @param tag a {@link java.lang.Object} object.
	 */
	public StopVisitorException(Object tag) {
		this.tag = tag;
	}

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public StopVisitorException(String message) {
		super(message);
		this.tag = null;
	}

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public StopVisitorException(String message, Throwable cause) {
		super(message, cause);
		this.tag = null;
	}

	/**
	 * <p>Constructor for StopVisitorException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public StopVisitorException(Throwable cause) {
		super(cause);
		this.tag = null;
	}

	/**
	 * <p>Getter for the field tag.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public final Object getTag() {
		return this.tag;
	}
}
