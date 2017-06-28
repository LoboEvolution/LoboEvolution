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

package com.steadystate.css.format;

import java.util.Arrays;

/**
 * Format object that controls the output produced by toString(CssFormat).
 *
 * @author rbri
 */
public class CSSFormat {
	private static final String NEW_LINE = System.getProperty("line.separator");

	private boolean rgbAsHex_;
	private boolean propertiesInSeparateLines_;
	private String propertiesIndent_ = "";

	public boolean isRgbAsHex() {
		return rgbAsHex_;
	}

	public CSSFormat setRgbAsHex(final boolean rgbAsHex) {
		rgbAsHex_ = rgbAsHex;
		return this;
	}

	public boolean getPropertiesInSeparateLines() {
		return propertiesInSeparateLines_;
	}

	public String getPropertiesIndent() {
		return propertiesIndent_;
	}

	/**
	 * If this value is larger than -1 the individual properties from a rule are
	 * rendered in separate lines; the parameter defines the indentation level.
	 * Set this to -1 to disable the feature (default)
	 *
	 * @param anIndent
	 *            the number of blanks used for indentation
	 * @return the format itself
	 */
	public CSSFormat setPropertiesInSeparateLines(final int anIndent) {
		propertiesInSeparateLines_ = anIndent > -1;

		if (anIndent > 0) {
			final char[] chars = new char[anIndent];
			Arrays.fill(chars, ' ');
			propertiesIndent_ = new String(chars);
		} else {
			propertiesIndent_ = "";
		}
		return this;
	}

	public String getNewLine() {
		return NEW_LINE;
	}
}
