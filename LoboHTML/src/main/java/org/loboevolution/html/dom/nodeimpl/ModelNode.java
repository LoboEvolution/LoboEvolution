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
 * Created on Oct 23, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.renderstate.RenderState;

import java.net.MalformedURLException;
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
	Object getDocumentItem(String name);

	/**
	 * <p>getFullURL.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @return a {@link java.net.URL} object.
	 * @throws java.net.MalformedURLException if any.
	 */
	URL getFullURL(String spec) throws MalformedURLException;

	/**
	 * <p>getParentModelNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	ModelNode getParentModelNode();

	/**
	 * <p>getRenderState.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	RenderState getRenderState();

	/**
	 * <p>isEqualOrDescendentOf.</p>
	 *
	 * @param otherNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @return a boolean.
	 */
	boolean isEqualOrDescendentOf(ModelNode otherNode);

	/**
	 * Sets a document item. A radio button, for example, can use this to set button
	 * group state.
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.Object} object.
	 */
	void setDocumentItem(String name, Object value);

	/**
	 * <p>warn.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param err a {@link java.lang.Throwable} object.
	 */
	void warn(String message, Throwable err);
}
