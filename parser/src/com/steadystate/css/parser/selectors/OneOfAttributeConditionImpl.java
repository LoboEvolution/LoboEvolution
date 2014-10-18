/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2011 David Schweinsberg.  All rights reserved.
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

import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.Condition;

import com.steadystate.css.parser.LocatableImpl;

/**
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class OneOfAttributeConditionImpl extends LocatableImpl implements
		AttributeCondition, Serializable {

	private static final long serialVersionUID = -1371164446179830634L;

	private String localName_;
	private String value_;
	private boolean specified_;

	public void setLocalName(final String localName) {
		localName_ = localName;
	}

	public void setValue(final String value) {
		value_ = value;
	}

	public void setSpecified(final boolean specified) {
		specified_ = specified;
	}

	public OneOfAttributeConditionImpl(final String localName,
			final String value, final boolean specified) {
		setLocalName(localName);
		setValue(value);
		setSpecified(specified);
	}

	public short getConditionType() {
		return Condition.SAC_ONE_OF_ATTRIBUTE_CONDITION;
	}

	public String getNamespaceURI() {
		return null;
	}

	public String getLocalName() {
		return localName_;
	}

	public boolean getSpecified() {
		return specified_;
	}

	public String getValue() {
		return value_;
	}

	@Override
	public String toString() {
		final String value = getValue();
		if (value != null) {
			return "[" + getLocalName() + "~=\"" + value + "\"]";
		}
		return "[" + getLocalName() + "]";
	}
}
