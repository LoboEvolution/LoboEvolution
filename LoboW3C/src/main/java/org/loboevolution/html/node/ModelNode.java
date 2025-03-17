/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
 * Created on Oct 23, 2005
 */
package org.loboevolution.html.node;

import org.loboevolution.js.IgnoreJs;

import java.net.URL;

/**
 * A generic node interface. The idea is that implementors could be W3C nodes or not.
 */
public interface ModelNode {

	/**
	 * <p>getDocumentItem.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.lang.Object} object.
	 */
	@IgnoreJs
	Object getDocumentItem(final String name);

	/**
	 * <p>getFullURL.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @return a {@link java.net.URL} object.
	 * @throws java.lang.Exception if any.
	 */
	@IgnoreJs
	URL getFullURL(final String spec) throws Exception;

	/**
	 * <p>getParentModelNode.</p>
	 *
	 * @return a {@link ModelNode} object.
	 */
	@IgnoreJs
	ModelNode getParentModelNode();

	/**
	 * <p>isEqualOrDescendentOf.</p>
	 *
	 * @param otherNode a {@link ModelNode} object.
	 * @return a boolean.
	 */
	@IgnoreJs
	boolean isEqualOrDescendentOf(ModelNode otherNode);

	/**
	 * <p>getRenderState.</p>
	 */
	@IgnoreJs
	Object getRenderState();

	/**
	 * Sets a document item. A radio button, for example, can use this to set button
	 * group state.
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.Object} object.
	 */
	@IgnoreJs
	void setDocumentItem(final String name, Object value);

	/**
	 * <p>warn.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param err a {@link java.lang.Throwable} object.
	 */
	@IgnoreJs
	void warn(final String message, Throwable err);
}
