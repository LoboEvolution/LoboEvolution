/*
 * Copyright (C) 1999-2016 David Schweinsberg.  All rights reserved.
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
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SimpleSelector;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class ConditionalSelectorImpl extends LocatableImpl implements ConditionalSelector, CSSFormatable, Serializable {

    private static final long serialVersionUID = 7217145899707580586L;

    private SimpleSelector simpleSelector_;
    private Condition condition_;

    public void setSimpleSelector(final SimpleSelector simpleSelector) {
        simpleSelector_ = simpleSelector;
        if (simpleSelector instanceof Locatable) {
            setLocator(((Locatable) simpleSelector).getLocator());
        }
        else if (simpleSelector == null) {
            setLocator(null);
        }
    }

    public void setCondition(final Condition condition) {
        condition_ = condition;
        if (getLocator() == null) {
            if (condition instanceof Locatable) {
                setLocator(((Locatable) condition).getLocator());
            }
            else if (condition == null) {
                setLocator(null);
            }
        }
    }

    public ConditionalSelectorImpl(final SimpleSelector simpleSelector, final Condition condition) {
        setSimpleSelector(simpleSelector);
        setCondition(condition);
    }

    public short getSelectorType() {
        return Selector.SAC_CONDITIONAL_SELECTOR;
    }

    public SimpleSelector getSimpleSelector() {
        return simpleSelector_;
    }

    public Condition getCondition() {
        return condition_;
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final StringBuilder sb = new StringBuilder();

        if (null != simpleSelector_) {
            sb.append(((CSSFormatable) simpleSelector_).getCssText(format));
        }

        if (null != condition_) {
            sb.append(((CSSFormatable) condition_).getCssText(format));
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return getCssText(null);
    }
}
