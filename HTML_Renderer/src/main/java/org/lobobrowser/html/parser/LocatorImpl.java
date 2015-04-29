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
 * Created on Oct 16, 2005
 */
package org.lobobrowser.html.parser;

import org.xml.sax.Locator;

/**
 * The Class LocatorImpl.
 */
class LocatorImpl implements Locator {

    /** The public id. */
    private final String publicId;

    /** The system id. */
    private final String systemId;

    /** The line number. */
    private final int lineNumber;

    /** The column number. */
    private final int columnNumber;

    /**
     * Instantiates a new locator impl.
     *
     * @param pid
     *            the pid
     * @param sid
     *            the sid
     * @param lnumber
     *            the lnumber
     * @param cnumber
     *            the cnumber
     */
    public LocatorImpl(String pid, String sid, int lnumber, int cnumber) {
        super();
        publicId = pid;
        systemId = sid;
        lineNumber = lnumber;
        columnNumber = cnumber;
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.Locator#getPublicId()
     */
    @Override
    public String getPublicId() {
        return this.publicId;
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.Locator#getSystemId()
     */
    @Override
    public String getSystemId() {
        return this.systemId;
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.Locator#getLineNumber()
     */
    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

    /*
     * (non-Javadoc)
     * @see org.xml.sax.Locator#getColumnNumber()
     */
    @Override
    public int getColumnNumber() {
        return this.columnNumber;
    }
}
