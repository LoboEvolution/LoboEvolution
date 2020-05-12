/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */


package org.mozilla.javascript;

/**
 * The class of exceptions thrown by the JavaScript engine.
 *
 * @author utente
 * @version $Id: $Id
 */
public class EvaluatorException extends RhinoException
{
    private static final long serialVersionUID = -8743165779676009808L;

    /**
     * <p>Constructor for EvaluatorException.</p>
     *
     * @param detail a {@link java.lang.String} object.
     */
    public EvaluatorException(String detail)
    {
        super(detail);
    }

    /**
     * Create an exception with the specified detail message.
     *
     * Errors internal to the JavaScript engine will simply throw a
     * RuntimeException.
     *
     * @param detail the error message
     * @param sourceName the name of the source reponsible for the error
     * @param lineNumber the line number of the source
     */
    public EvaluatorException(String detail, String sourceName,
                              int lineNumber)
    {
        this(detail, sourceName, lineNumber, null, 0);
    }

    /**
     * Create an exception with the specified detail message.
     *
     * Errors internal to the JavaScript engine will simply throw a
     * RuntimeException.
     *
     * @param detail the error message
     * @param sourceName the name of the source responsible for the error
     * @param lineNumber the line number of the source
     * @param columnNumber the columnNumber of the source (may be zero if
     *                     unknown)
     * @param lineSource the source of the line containing the error (may be
     *                   null if unknown)
     */
    public EvaluatorException(String detail, String sourceName, int lineNumber,
                              String lineSource, int columnNumber)
    {
        super(detail);
        recordErrorOrigin(sourceName, lineNumber, lineSource, columnNumber);
    }

    /**
     * <p>getSourceName.</p>
     *
     * @deprecated Use {@link org.mozilla.javascript.RhinoException#sourceName()} from the super class.
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    public String getSourceName()
    {
        return sourceName();
    }

    /**
     * <p>getLineNumber.</p>
     *
     * @deprecated Use {@link org.mozilla.javascript.RhinoException#lineNumber()} from the super class.
     * @return a int.
     */
    @Deprecated
    public int getLineNumber()
    {
        return lineNumber();
    }

    /**
     * <p>getColumnNumber.</p>
     *
     * @deprecated Use {@link org.mozilla.javascript.RhinoException#columnNumber()} from the super class.
     * @return a int.
     */
    @Deprecated
    public int getColumnNumber()
    {
        return columnNumber();
    }

    /**
     * <p>getLineSource.</p>
     *
     * @deprecated Use {@link org.mozilla.javascript.RhinoException#lineSource()} from the super class.
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    public String getLineSource()
    {
        return lineSource();
    }

}
