/*
 * Copyright (C) 1999-2017 David Schweinsberg.
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

package com.steadystate.css.format;

/**
 * Common interface for all classes supporting formated output.
 *
 * @author rbri
 */
public interface CSSFormatable {

    /**
     * Returns a string representation of the rule based on the given format.
     * If provided format is null, the result is the same as getCssText()
     *
     * @param format the formatting rules
     * @return the formated string
     */
    String getCssText(CSSFormat format);
}
