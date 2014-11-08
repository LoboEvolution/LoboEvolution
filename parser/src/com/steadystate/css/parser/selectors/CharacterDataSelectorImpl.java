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

import org.w3c.css.sac.CharacterDataSelector;
import org.w3c.css.sac.Selector;

import com.steadystate.css.parser.LocatableImpl;

/**
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class CharacterDataSelectorImpl extends LocatableImpl implements
		CharacterDataSelector, Serializable {

	private static final long serialVersionUID = 4635511567927852889L;

	private String data_;

	public void setData(final String data) {
		data_ = data;
	}

	public CharacterDataSelectorImpl(final String data) {
		setData(data);
	}

	public short getSelectorType() {
		return Selector.SAC_CDATA_SECTION_NODE_SELECTOR;
	}

	public String getData() {
		return data_;
	}

	@Override
	public String toString() {
		return getData();
	}
}
