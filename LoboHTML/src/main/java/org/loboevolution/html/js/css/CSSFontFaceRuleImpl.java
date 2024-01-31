/*
 * Copyright (c) 2002-2023 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.html.js.css;

import org.loboevolution.css.CSSFontFaceRule;

/**
 * <p>CSSFontFaceRuleImpl class.</p>
 */
public class CSSFontFaceRuleImpl extends AbstractCSSStyleRule implements CSSFontFaceRule {

    public CSSFontFaceRuleImpl(final org.htmlunit.cssparser.dom.CSSFontFaceRuleImpl abstractCSSRule) {
        super(abstractCSSRule);
    }

    @Override
    public String toString() {
        return "[object CSSFontFaceRule]";
    }
}
