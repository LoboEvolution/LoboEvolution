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
package org.lobobrowser.clientlet;

/**
 * Exception thrown by clientlets.
 */
public class ClientletException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The source code. */
    private final String sourceCode;

    /**
     * Constructs a ClientletException.
     *
     * @param message
     *            The exception message.
     */
    public ClientletException(String message) {
        super(message);
        this.sourceCode = null;
    }

    /**
     * Constructs a ClientletException.
     *
     * @param message
     *            The exception message.
     * @param sourceCode
     *            If the source code of the document generating the error is
     *            known, it should be passed in this parameter.
     */
    public ClientletException(String message, String sourceCode) {
        super(message);
        this.sourceCode = sourceCode;
    }

    /**
     * Constructs a ClientletException.
     *
     * @param message
     *            The exception message.
     * @param rootCause
     *            The root cause exception.
     */
    public ClientletException(String message, Throwable rootCause) {
        super(message, rootCause);
        this.sourceCode = null;
    }

    /**
     * Constructs a ClientletException.
     *
     * @param rootCause
     *            The root cause exception.
     */
    public ClientletException(Throwable rootCause) {
        super(rootCause);
        this.sourceCode = null;
    }

    /** Gets the source code.
	 *
	 * @return the source code
	 */
    public String getSourceCode() {
        return sourceCode;
    }
}
