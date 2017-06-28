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

import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.Locatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class AndConditionImpl extends LocatableImpl implements CombinatorCondition, CSSFormatable, Serializable {

	private static final long serialVersionUID = -3180583860092672742L;

	private Condition firstCondition_;
	private Condition secondCondition_;

	public void setFirstCondition(final Condition c1) {
		firstCondition_ = c1;
		if (c1 instanceof Locatable) {
			setLocator(((Locatable) c1).getLocator());
		} else if (c1 == null) {
			setLocator(null);
		}
	}

	public void setSecondCondition(final Condition c2) {
		secondCondition_ = c2;
	}

	public AndConditionImpl(final Condition c1, final Condition c2) {
		setFirstCondition(c1);
		setSecondCondition(c2);
	}

	@Override
	public short getConditionType() {
		return Condition.SAC_AND_CONDITION;
	}

	@Override
	public Condition getFirstCondition() {
		return firstCondition_;
	}

	@Override
	public Condition getSecondCondition() {
		return secondCondition_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCssText(final CSSFormat format) {
		final StringBuilder sb = new StringBuilder();

		Condition cond = getFirstCondition();
		if (null != cond) {
			sb.append(((CSSFormatable) cond).getCssText(format));
		}

		cond = getSecondCondition();
		if (null != cond) {
			sb.append(((CSSFormatable) cond).getCssText(format));
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		return getCssText(null);
	}
}
