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

import org.w3c.css.sac.CharacterDataSelector;
import org.w3c.css.sac.Selector;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.LocatableImpl;

/**
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class CharacterDataSelectorImpl extends LocatableImpl
		implements CharacterDataSelector, CSSFormatable, Serializable {

	private static final long serialVersionUID = 4635511567927852889L;

	private String data_;

	public void setData(final String data) {
		data_ = data;
	}

	public CharacterDataSelectorImpl(final String data) {
		setData(data);
	}

	@Override
	public short getSelectorType() {
		return Selector.SAC_CDATA_SECTION_NODE_SELECTOR;
	}

	@Override
	public String getData() {
		return data_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCssText(final CSSFormat format) {
		final String data = getData();
		if (data == null) {
			return "";
		}
		return data;
	}

	@Override
	public String toString() {
		return getCssText(null);
	}
}
