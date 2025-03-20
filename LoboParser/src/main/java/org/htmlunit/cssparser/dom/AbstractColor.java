/*
 * Copyright (c) 2019-2024 Ronald Brill.
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
package org.htmlunit.cssparser.dom;

import java.io.Serializable;
import java.util.Locale;

import org.htmlunit.cssparser.parser.LexicalUnit;

/**
 * Color base class.
 *
 * @author Ronald Brill
 */
public class AbstractColor implements Serializable {

    private String function_;
    private CSSValueImpl cssValue_;

    /**
     * Constructor that reads the values from the given
     * chain of LexicalUnits.
     * @param function the name of the function; rgb or rgba
     * @param lu the values
     * @throws DOMException in case of error
     */
    public AbstractColor(final String function, final LexicalUnit lu) throws DOMException {
        function_ = function.toLowerCase(Locale.ROOT);
        cssValue_ = new CSSValueImpl(lu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb
            .append(function_)
            .append("(")
            .append(cssValue_)
            .append(")");

        return sb.toString();
    }
}
