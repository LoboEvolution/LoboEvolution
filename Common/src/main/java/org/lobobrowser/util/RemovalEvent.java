/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util;

import java.util.EventObject;

/**
 * The Class RemovalEvent.
 */
public class RemovalEvent extends EventObject {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5119617932860542348L;
    /** The value removed. */
    public final Object valueRemoved;
    
    /**
     * Instantiates a new removal event.
     *
     * @param source
     *            the source
     * @param valueRemoved
     *            the value removed
     */
    public RemovalEvent(Object source, Object valueRemoved) {
        super(source);
        this.valueRemoved = valueRemoved;
    }
}
