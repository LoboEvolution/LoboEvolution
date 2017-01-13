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

package com.steadystate.css.parser;

import java.util.ListResourceBundle;

/**
 * ExceptionResource.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class ExceptionResource extends ListResourceBundle {

    @Override
    public Object[][] getContents() {
        return contents;
    }

    static final Object[][] contents = {
        {"s0", "Syntax error"},
        {"s1", "Index out of bounds error"},
        {"s2", "This style sheet is read only"},
        {"s3", "The text does not represent an unknown rule"},
        {"s4", "The text does not represent a style rule"},
        {"s5", "The text does not represent a charset rule"},
        {"s6", "The text does not represent an import rule"},
        {"s7", "The text does not represent a media rule"},
        {"s8", "The text does not represent a font face rule"},
        {"s9", "The text does not represent a page rule"},
        {"s10", "This isn't a Float type"},
        {"s11", "This isn't a String type"},
        {"s12", "This isn't a Counter type"},
        {"s13", "This isn't a Rect type"},
        {"s14", "This isn't an RGBColor type"},
        {"s15", "A charset rule must be the first rule"},
        {"s16", "A charset rule already exists"},
        {"s17", "An import rule must preceed all other rules"},
        {"s18", "The specified type was not found"},
        {"s20", "Can't insert a rule before the last charset or import rule"}
    };
}
