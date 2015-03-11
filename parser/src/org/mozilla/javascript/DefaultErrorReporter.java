/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;


/**
 * This is the default error reporter for JavaScript.
 *
 * @author Norris Boyd
 */
class DefaultErrorReporter implements ErrorReporter
{
    
    /** The Constant instance. */
    static final DefaultErrorReporter instance = new DefaultErrorReporter();

    /** The for eval. */
    private boolean forEval;
    
    /** The chained reporter. */
    private ErrorReporter chainedReporter;

    /**
     * Instantiates a new default error reporter.
     */
    private DefaultErrorReporter() { }

    /**
     * For eval.
     *
     * @param reporter the reporter
     * @return the error reporter
     */
    static ErrorReporter forEval(ErrorReporter reporter)
    {
        DefaultErrorReporter r = new DefaultErrorReporter();
        r.forEval = true;
        r.chainedReporter = reporter;
        return r;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ErrorReporter#warning(java.lang.String, java.lang.String, int, java.lang.String, int)
     */
    public void warning(String message, String sourceURI, int line,
                        String lineText, int lineOffset)
    {
        if (chainedReporter != null) {
            chainedReporter.warning(
                message, sourceURI, line, lineText, lineOffset);
        } else {
            // Do nothing
        }
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ErrorReporter#error(java.lang.String, java.lang.String, int, java.lang.String, int)
     */
    public void error(String message, String sourceURI, int line,
                      String lineText, int lineOffset)
    {
        if (forEval) {
            // Assume error message strings that start with "TypeError: "
            // should become TypeError exceptions. A bit of a hack, but we
            // don't want to change the ErrorReporter interface.
            String error = "SyntaxError";
            final String TYPE_ERROR_NAME = "TypeError";
            final String DELIMETER = ": ";
            final String prefix = TYPE_ERROR_NAME + DELIMETER;
            if (message.startsWith(prefix)) {
                error = TYPE_ERROR_NAME;
                message = message.substring(prefix.length());
            }
            throw ScriptRuntime.constructError(error, message, sourceURI,
                                               line, lineText, lineOffset);
        }
        if (chainedReporter != null) {
            chainedReporter.error(
                message, sourceURI, line, lineText, lineOffset);
        } else {
            throw runtimeError(
                message, sourceURI, line, lineText, lineOffset);
        }
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ErrorReporter#runtimeError(java.lang.String, java.lang.String, int, java.lang.String, int)
     */
    public EvaluatorException runtimeError(String message, String sourceURI,
                                           int line, String lineText,
                                           int lineOffset)
    {
        if (chainedReporter != null) {
            return chainedReporter.runtimeError(
                message, sourceURI, line, lineText, lineOffset);
        } else {
            return new EvaluatorException(
                message, sourceURI, line, lineText, lineOffset);
        }
    }
}
