/*
 * $Id: Parameter.java 282 2008-03-19 21:13:55Z rbair $
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

import org.lobobrowser.util.NameValuePair;


/**
 * A NameValuePair used as a Parameter to http {@link Request}s.
 * 
 * @author rbair
 */
public class Parameter extends NameValuePair {
   
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 
     * Creates a new instance of Parameter with a null name and value.
     */
    public Parameter() {
        super();
    }
    
    /**
     * Creates a new instance of Parameter with the given name and value.
     * 
     * @param name The name. May be null.
     * @param value The value. May be null.
     */
    public Parameter(String name, String value) {
        super(name, value);
    }
    
    /* (non-Javadoc)
     * @see org.lobobrowser.util.NameValuePair#clone()
     */
    @Override public Parameter clone() {
        return new Parameter(getName(), getValue());
    }
}