/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
package org.loboevolution.html.dom.domimpl;

import java.net.MalformedURLException;

import org.loboevolution.html.renderstate.RenderState;

/**
 * A generic node interface. The idea is that implementors could be W3C nodes or
 * not.
 */
public interface ModelNode {
	// There shouldn't be any references to GUI components here.
	// Events are processed by controller in renderer package.

	Object getDocumentItem(String name);

	java.net.URL getFullURL(String spec) throws MalformedURLException;

	ModelNode getParentModelNode();

	RenderState getRenderState();

	boolean isEqualOrDescendentOf(ModelNode otherNode);

	/**
	 * Sets a document item. A radio button, for example, can use this to set button
	 * group state.
	 * 
	 * @param name
	 * @param value
	 */
	void setDocumentItem(String name, Object value);

	void warn(String message, Throwable err);
}
