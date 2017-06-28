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

import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.Condition;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class AttributeConditionImpl extends LocatableImpl implements AttributeCondition, CSSFormatable, Serializable {

	private static final long serialVersionUID = 9035418830958954213L;

	private String localName_;
	private String value_;
	private boolean specified_;

	public void setLocaleName(final String localName) {
		localName_ = localName;
	}

	public void setValue(final String value) {
		value_ = value;
	}

	public void setSpecified(final boolean specified) {
		specified_ = specified;
	}

	public AttributeConditionImpl(final String localName, final String value, final boolean specified) {
		setLocaleName(localName);
		setValue(value);
		setSpecified(specified);
	}

	@Override
	public short getConditionType() {
		return Condition.SAC_ATTRIBUTE_CONDITION;
	}

	@Override
	public String getNamespaceURI() {
		return null;
	}

	@Override
	public String getLocalName() {
		return localName_;
	}

	@Override
	public boolean getSpecified() {
		return specified_;
	}

	@Override
	public String getValue() {
		return value_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCssText(final CSSFormat format) {
		final String value = getValue();
		if (value != null) {
			return "[" + getLocalName() + "=\"" + value + "\"]";
		}
		return "[" + getLocalName() + "]";
	}

	@Override
	public String toString() {
		return getCssText(null);
	}
}
