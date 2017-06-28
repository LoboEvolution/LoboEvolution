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

import com.steadystate.css.format.CSSFormat;

/**
 * Special ElementSelectorImpl used by the parser at all the places where the
 * parser inserts a '*' selector. The selector will be ignored when generating
 * output. This is done to be backward compatible.
 *
 * @author rbri
 */
public class SyntheticElementSelectorImpl extends ElementSelectorImpl {

	private static final long serialVersionUID = 3426191759125755798L;

	public SyntheticElementSelectorImpl() {
		super(null);
	}

	@Override
	public void setLocalName(final String localName) {
		throw new RuntimeException("Method setLocalName is not supported for SyntheticElementSelectorImpl.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCssText(final CSSFormat format) {
		return "";
	}
}
