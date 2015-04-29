/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.util;

import java.io.Serializable;

import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

/**
 * Helper implementation of {@link ErrorHandler}, which throws CssException in case of problems.
 *
 * @version $Revision: $
 * @author rbri
 * @see ErrorHandler
 */
public class ThrowCssExceptionErrorHandler implements ErrorHandler, Serializable {
    private static final long serialVersionUID = -3933638774901855095L;

    /**
     * Singleton.
     */
    public static final ThrowCssExceptionErrorHandler INSTANCE = new ThrowCssExceptionErrorHandler();

    /**
     * {@inheritDoc}
     */
    public void error(final CSSParseException exception) {
        throw exception;
    }

    /**
     * {@inheritDoc}
     */
    public void fatalError(final CSSParseException exception) {
        throw exception;
    }

    /**
     * {@inheritDoc}
     */
    public void warning(final CSSParseException exception) {
        // ignore warnings
    }
}
