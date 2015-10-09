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
