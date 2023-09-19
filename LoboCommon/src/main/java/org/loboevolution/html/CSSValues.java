/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>CSSValues class.</p>
 */
public enum CSSValues {

	ABSOLUTE("absolute"),

	AUTO("auto"),
	
	BASELINE("baseline"),

	BACKCOMPAT("BackCompat"),

	BLINK("blink"),

	BLOCK("block"),

	BOLD("bold"),

	BOLD100("100"),

	BOLD200("200"),

	BOLD300("300"),

	BOLD400("400"),

	BOLD500("500"),

	BOLD600("600"),

	BOLD700("700"),

	BOLD800("800"),

	BOLD900("900"),

	BOLDER("bolder"),

	BORDER_BOX("border-box"),

	BOTTOM("bottom"),

	CAPITALIZE("capitalize"),

	CAPTION("caption"),

	CENTER("center"),

	CIRCLE("circle"),
	
	COLUMN("column"),
	
	COLUMN_REVERSE("column-reverse"),

	COLLAPSE("collapse"),

	COLSPAN("colspan"),

	CONTENT_BOX("content-box"),

	CROSSHAIR("crosshair"),

	CSS1COMPAT("CSS1Compat"),

	DASHED("dashed"),

	DECIMAL("decimal"),

	DECIMAL_LEADING_ZERO("decimal-leading-zero"),

	DEFAULT("default"),

	DISC("disc"),

	DOTTED("dotted"),

	DOUBLE("double"),
	
	E_RESIZE("e-resize"),

	EW_RESIZE("ew-resize"),

	FIXED("fixed"),
	
	FLEX("flex"),
	
	FLEX_START("flex-start"),
	
	FLEX_END("flex-end"),
	
	GRAB("grab"),

	GROOVE("groove"),

	HIDDEN("hidden"),

	ICON("icon"),

	INHERIT("inherit"),

	INITIAL("initial"),

	INLINE("inline"),

	INLINE_BLOCK("inline-block"),

	INLINE_TABLE("inline-table"),

	INSET("inset"),

	INSIDE("inside"),

	ITALIC("italic"),

	LARGE("large"),

	LARGER("larger"),

	LEFT("left"),

	LIGHTER("lighter"),

	LINE_THROUGH("line-through"),

	LIST_ITEM("list-item"),

	LOWER_ALPHA("lower-alpha"),

	LOWER_LATIN("lower-latin"),

	LOWER_ROMAN("lower-roman"),

	LOWERCASE("lowercase"),

	MARGIN("margin"),

	MEDIUM("medium"),

	MENU("menu"),

	MESSAGE_BOX("message-box"),

	MOVE("move"),

	N_RESIZE("n-resize"),
	
	NE_RESIZE("ne-resize"),

	NESW_RESIZE("nesw-resize"),

	NONE("none"),

	NORMAL("normal"),

	NOWRAP("nowrap"),

	NW_RESIZE("nw-resize"),

	NWSE_RESIZE("nwse-resize"),

	OBLIQUE("oblique"),

	OUTSET("outset"),

	OUTSIDE("outside"),

	OVERLINE("overline"),

	POINTER("pointer"),

	PRE("pre"),

	PROGRESS("progress"),

	RELATIVE("relative"),

	REPEAT("repeat"),

	REPEAT_NO("no-repeat"),

	REPEAT_X("repeat-x"),

	REPEAT_Y("repeat-y"),

	RIDGE("ridge"),

	RIGHT("right"),
	
	ROW("row"),
	
	ROW_REVERSE("row-reverse"),

	ROWSPAN("rowspan"),

	S_RESIZE("s-resize"),

	SCROLL("scroll"),

	SE_RESIZE("se-resize"),

	SMALL("small"),

	SMALL_CAPS("small-caps"),

	SMALL_CAPTION("small-caption"),

	SMALLER("smaller"),

	SOLID("solid"),
	
	SQUARE("square"),
	
	SPACE_AROUND("space-around"),
	
	SPACE_BETWEEN("space-between"),

	STATIC("static"),

	STATUS_BAR("status-bar"),
	
	STRETCH("stretch"),

	SW_RESIZE("sw-resize"),

	TABLE("table"),

	TABLE_CELL("table-cell"),

	TABLE_ROW("table-row"),

	TABLE_ROW_GROUP("table-row-group"),

	TABLE_CAPTION("table-caption"),

	TABLE_COLUMN("table-column"),

	TABLE_COLUMN_GROUP("table-column-group"),

	TABLE_HEADER_GROUP("table-header-group"),

	TABLE_FOOTER_GROUP("table-footer-group"),

	TEXT_CSS("text"),

	TOP("top"),

	UNDERLINE("underline"),

	UPPER_ALPHA("upper-alpha"),

	UPPER_LATIN("upper-latin"),

	UPPER_ROMAN("upper-roman"),

	UPPERCASE("uppercase"),

	VISIBLE("visible"),

	W_RESIZE("w-resize"),

	WAIT("wait"),
	
	WRAP("wrap"),
	
	WRAP_REVERSE("wrap-reverse"),

	X_LARGE("x-large"),

	X_SMALL("x-small"),

	XX_LARGE("xx-large"),

	XX_SMALL("xx-small"),

	ZOOM_IN("zoom-in"),

	ZOOM_OUT("zoom-out");
	
	private final String value;
	private static final Map<String, CSSValues> ENUM_MAP;
	
	static {
		Map<String, CSSValues> map = new HashMap<>();
		for (CSSValues instance : CSSValues.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	CSSValues(String value) {
		this.value = value;
	}

	/**
	 * <p>Getter for the field value.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>isEqual.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean isEqual(String value) {
		return this.value.equals(value);
	}

	/**
	 * <p>get.</p>
	 *
	 * @param actionName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.CSSValues} object.
	 */
	public static CSSValues get(String actionName) {
		CSSValues value = ENUM_MAP.get(actionName != null ? actionName.toLowerCase() : "");
		return value == null ? CSSValues.DEFAULT : value;
	}
}
