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

import com.steadystate.css.parser.LocatableImpl;


/**
 * The Class LangConditionImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class LangConditionImpl extends LocatableImpl implements LangCondition, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1701599531953055387L;

    /** The lang_. */
    private String lang_;

    /**
     * Sets the lang.
     *
     * @param lang the new lang
     */
    public void setLang(final String lang) {
        lang_ = lang;
    }

    /**
     * Instantiates a new lang condition impl.
     *
     * @param lang the lang
     */
    public LangConditionImpl(final String lang) {
        setLang(lang);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Condition#getConditionType()
     */
    public short getConditionType() {
        return Condition.SAC_LANG_CONDITION;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LangCondition#getLang()
     */
    public String getLang() {
        return lang_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ":lang(" + getLang() + ")";
    }
}
