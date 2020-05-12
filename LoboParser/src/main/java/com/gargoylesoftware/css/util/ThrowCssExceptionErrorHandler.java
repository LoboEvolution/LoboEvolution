/*
 * Copyright (c) 2019 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.css.util;

import java.io.Serializable;

import com.gargoylesoftware.css.parser.CSSErrorHandler;
import com.gargoylesoftware.css.parser.CSSParseException;

/**
 * Helper implementation of {@link com.gargoylesoftware.css.parser.CSSErrorHandler}, which throws CssException in case of problems.
 *
 * @author Ronadl Brill
 * @version $Id: $Id
 */
public class ThrowCssExceptionErrorHandler implements CSSErrorHandler, Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
     * Singleton.
     */
    public static final ThrowCssExceptionErrorHandler INSTANCE = new ThrowCssExceptionErrorHandler();

    /** {@inheritDoc} */
    @Override
    public void error(final CSSParseException exception) {
        throw exception;
    }

    /** {@inheritDoc} */
    @Override
    public void fatalError(final CSSParseException exception) {
        throw exception;
    }

    /** {@inheritDoc} */
    @Override
    public void warning(final CSSParseException exception) {
        // ignore warnings
    }
}
