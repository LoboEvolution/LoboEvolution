/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
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
