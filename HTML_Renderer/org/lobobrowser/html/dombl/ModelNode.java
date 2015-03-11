/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 23, 2005
 */
package org.lobobrowser.html.dombl;

import java.net.MalformedURLException;
import java.net.URL;

import org.lobobrowser.html.renderstate.RenderState;


/**
 * A generic node interface. The idea is that implementors could be W3C nodes or
 * not.
 */
public interface ModelNode {
	// There shouldn't be any references to GUI components here.
	// Events are processed by controller in renderer package.

	/**
	 * Gets the full url.
	 *
	 * @param spec the spec
	 * @return the full url
	 * @throws MalformedURLException the malformed url exception
	 */
	public URL getFullURL(String spec) throws MalformedURLException;

	/**
	 * Warn.
	 *
	 * @param message the message
	 * @param err the err
	 */
	public void warn(String message, Throwable err);

	/**
	 * Checks if is equal or descendent of.
	 *
	 * @param otherNode the other node
	 * @return true, if is equal or descendent of
	 */
	public boolean isEqualOrDescendentOf(ModelNode otherNode);

	/**
	 * Gets the parent model node.
	 *
	 * @return the parent model node
	 */
	public ModelNode getParentModelNode();

	/**
	 * Gets the render state.
	 *
	 * @return the render state
	 */
	public RenderState getRenderState();

	/**
	 * Sets a document item. A radio button, for example, can use this to set
	 * button group state.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void setDocumentItem(String name, Object value);

	/**
	 * Gets the document item.
	 *
	 * @param name the name
	 * @return the document item
	 */
	public Object getDocumentItem(String name);
}
