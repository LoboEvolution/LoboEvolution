/*
 * $Id: Header.java 197 2007-02-15 21:25:22Z rbair $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.lobobrowser.http;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.Response;

import org.lobobrowser.util.NameValuePair;


/**
 * Represents a header field in an http {@link Request} or {@link Response}.
 * 
 * @author rbair
 */
public class Header extends NameValuePair {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The pcs. */
	private transient PropertyChangeSupport pcs;
	
	/** The elements. */
	private List<Element> elements = new ArrayList<Element>();
    
    /**
     * Creates a new Header with a null name and value, and no elements.
     */
    public Header() {
    }
    
    /**
     * Creates a new Header with the given name and value, and no elements.
     * 
     * @param name The name. May be null.
     * @param value The value. May be null.
     */
    public Header(String name, String value) {
        super(name, value);
    }
    
    /**
     * Creates a new Header with the given name, value, and elements. If
     * <code>elements</code> is null, then an empty set of elements will be
     * used instead.
     * 
     * @param name The name. May be null.
     * @param value The value. May be null.
     * @param elements The elements. May be null. If null, then getElements()
     *        will return an empty array, rather than null.
     */
    public Header(String name, String value, Element... elements) {
        super(name, value);
        setElements(elements);
    }
    
    /**
     * Returns an array of Elements for this Header. This array returned will
     * never be null. A new array instance will be returned for every invocation
     * of this method.
     * 
     * @return array of Elements. This will never be null.
     */
    public Element[] getElements() {
        return elements.toArray(new Element[0]);
    }

    /**
     * Sets the elements. If the <code>elements</code> param is null, this will
     * set the empty set of elements. That is, getElements() will return an
     * empty array rather than null.
     * 
     * @param elements The Elements. May be null. Replaces the old array of elements.
     */
    public void setElements(Element... elements) {
        Element[] old = getElements();
        this.elements.clear();
        if (elements != null) {
            this.elements.addAll(Arrays.asList(elements));
        }
        firePropertyChange("elements", old, getElements());
    }
    
    /* (non-Javadoc)
     * @see org.lobobrowser.util.NameValuePair#toString()
     */
    @Override
    public String toString() {
        return getName() + ": " + getValue();
    }

    /**
     * A representation of an Element within a Header.
     */
    public static final class Element {
        
        /** The params. */
        private Parameter[] params = new Parameter[0];
        
        /**
         * Create a new instance of Element with the given params.
         * 
         * @param params the Parameters. May be null.
         */
        public Element(Parameter... params) {
            if (params == null) {
                this.params = new Parameter[0];
            } else {
                this.params = new Parameter[params.length];
                System.arraycopy(params, 0, this.params, 0, this.params.length);
            }
        }
        
        /**
         * Gets the parameters. This array will never be null.
         * 
         * @return the array of Parameters. This will never be null.
         */
        public Parameter[] getParameters() {
            Parameter[] dest = new Parameter[params.length];
            System.arraycopy(params, 0, dest, 0, params.length);
            return dest;
        }
    }
}
