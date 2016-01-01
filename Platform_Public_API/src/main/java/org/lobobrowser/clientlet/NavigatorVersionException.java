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
package org.lobobrowser.clientlet;

/**
 * Thrown when a clientlet requires a newer navigator version than the one
 * running.
 */
public class NavigatorVersionException extends ClientletException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The expecting version. */
    private final String expectingVersion;

    /**
     * Instantiates a new navigator version exception.
     *
     * @param message
     *            the message
     * @param expectingVersion
     *            the expecting version
     */
    public NavigatorVersionException(String message, String expectingVersion) {
        super(message);
        this.expectingVersion = expectingVersion;
    }

    /** Gets the expecting version.
	 *
	 * @return the expecting version
	 */
    public String getExpectingVersion() {
        return expectingVersion;
    }

    /**
     * Instantiates a new navigator version exception.
     *
     * @param message
     *            the message
     * @param expectingVersion
     *            the expecting version
     * @param rootCause
     *            the root cause
     */
    public NavigatorVersionException(String message, String expectingVersion,
            Throwable rootCause) {
        super(message, rootCause);
        this.expectingVersion = expectingVersion;
    }

    /**
     * Instantiates a new navigator version exception.
     *
     * @param expectingVersion
     *            the expecting version
     * @param rootCause
     *            the root cause
     */
    public NavigatorVersionException(String expectingVersion,
            Throwable rootCause) {
        super(rootCause);
        this.expectingVersion = expectingVersion;
    }
}
