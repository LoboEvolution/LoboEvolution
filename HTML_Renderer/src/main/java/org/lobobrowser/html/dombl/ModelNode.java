/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

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
     * @param spec
     *            the spec
     * @return the full url
     * @throws MalformedURLException
     *             the malformed url exception
     */
    URL getFullURL(String spec) throws MalformedURLException;

    /**
     * Checks if is equal or descendent of.
     *
     * @param otherNode
     *            the other node
     * @return true, if is equal or descendent of
     */
    boolean isEqualOrDescendentOf(ModelNode otherNode);

    /** Gets the parent model node.
	 *
	 * @return the parent model node
	 */
    ModelNode getParentModelNode();

    /** Gets the render state.
	 *
	 * @return the render state
	 */
    RenderState getRenderState();

    /**
     * Sets a document item. A radio button, for example, can use this to set
     * button group state.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    void setDocumentItem(String name, Object value);

    /**
     * Gets the document item.
     *
     * @param name
     *            the name
     * @return the document item
     */
    Object getDocumentItem(String name);
}
