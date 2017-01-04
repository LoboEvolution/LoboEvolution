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
 * Created on Aug 6, 2005
 */
package org.lobobrowser.util;

/**
 * The Class WrapperException.
 */
public class WrapperException extends RuntimeException {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1873416786560593951L;
    
    /**
     * Instantiates a new wrapper exception.
     */
    public WrapperException() {
        super();
    }
    
    /**
     * Instantiates a new wrapper exception.
     *
     * @param message
     *            the message
     */
    public WrapperException(String message) {
        super(message);
    }
    
    /**
     * Instantiates a new wrapper exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public WrapperException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Instantiates a new wrapper exception.
     *
     * @param cause
     *            the cause
     */
    public WrapperException(Throwable cause) {
        super(cause);
    }
}
