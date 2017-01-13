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

package com.steadystate.css.parser.selectors;

import java.io.Serializable;

import org.w3c.css.sac.Condition;
import org.w3c.css.sac.LangCondition;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class LangConditionImpl extends LocatableImpl implements LangCondition, CSSFormatable, Serializable {

    private static final long serialVersionUID = 1701599531953055387L;

    private String lang_;

    public void setLang(final String lang) {
        lang_ = lang;
    }

    public LangConditionImpl(final String lang) {
        setLang(lang);
    }

    public short getConditionType() {
        return Condition.SAC_LANG_CONDITION;
    }

    public String getLang() {
        return lang_;
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final StringBuilder result = new StringBuilder();
        result.append(":lang(");

        final String lang = getLang();
        if (null != lang) {
            result.append(lang);
        }

        result.append(")");
        return result.toString();
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
