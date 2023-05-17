/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.laf;

import java.util.Locale;
import java.util.Set;
import java.util.Objects;

import lombok.*;

/**
 * The Class FontKey.
 */
@Data
@Builder
public class FontKey {

	/** The cached hash. */
	@Builder.Default
	private int cachedHash = -1;

	/** The font family. */
	@Builder.Default
	private String fontFamily = "Times New Roman";

	/** The font size. */
	private float fontSize;

	/** The font style. */
	@Builder.Default
	private String fontStyle = null;

	/** The font variant. */
	@Builder.Default
	private String fontVariant = null;

	/** The font weight. */
	@Builder.Default
	private String fontWeight = null;

	/** The letterSpacing. */
	@Builder.Default
	private Integer letterSpacing = 0;

	/** The locales. */
	@Builder.Default
	private Set<Locale> locales = null;

	/** The strikethrough. */
	@Builder.Default
	private Boolean strikethrough = null;

	/** The superscript. */
	@Builder.Default
	private Integer superscript = null;

	/** The underline. */
	@Builder.Default
	private Integer underline = null;

	/** The font. */
	private String font;

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object other) {
		FontKey ors;
		if (other instanceof FontKey) {
			ors = (FontKey) other;
		} else {
			return false;
		}

		return this.fontSize == ors.fontSize &&
				Objects.equals(this.fontFamily, ors.fontFamily) &&
				Objects.equals(this.fontStyle, ors.fontStyle) &&
				Objects.equals(this.fontWeight, ors.fontWeight) &&
				Objects.equals(this.fontVariant, ors.fontVariant) &&
				Objects.equals(this.superscript, ors.superscript) &&
				Objects.equals(this.locales, ors.locales);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		int ch = this.cachedHash;
		if (ch != -1) {
			// Object is immutable - caching is ok.
			return ch;
		}
		String ff = this.fontFamily;
		if (ff == null) {
			ff = "";
		}
		String fw = this.fontWeight;
		if (fw == null) {
			fw = "";
		}
		String fs = this.fontStyle;
		if (fs == null) {
			fs = "";
		}
		final Integer ss = this.superscript;
		ch = ff.hashCode() ^ fw.hashCode() ^ fs.hashCode() ^ (int) this.fontSize ^ (ss == null ? 0 : ss);
		this.cachedHash = ch;
		return ch;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "FontKey[family=" + this.fontFamily + ",size=" + this.fontSize + ",style=" + this.fontStyle + ",weight="
				+ this.fontWeight + ",variant=" + this.fontVariant + ",superscript=" + this.superscript
				+ ",letterSpacing=" + this.letterSpacing + ",strikethrough=" + this.strikethrough + ",underline="
				+ this.underline + "]";
	}
}
