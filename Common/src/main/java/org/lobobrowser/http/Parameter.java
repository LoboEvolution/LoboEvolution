/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.http;

/**
 * A NameValuePair used as a Parameter to http {@link Request}s.
 *
 * @author rbair
 */
public class Parameter extends NameValuePair {
    /** The Constant serialVersionUID. */
    @SuppressWarnings("unused")
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
     * @param name
     *            The name. May be null.
     * @param value
     *            The value. May be null.
     */
    public Parameter(final String name, final String value) {
        super(name, value);
    }
    
    /*
     * (non-Javadoc)
     * @see org.lobobrowser.util.NameValuePair#clone()
     */
    @Override
    public Parameter clone() {
        return new Parameter(getName(), getValue());
    }
}
