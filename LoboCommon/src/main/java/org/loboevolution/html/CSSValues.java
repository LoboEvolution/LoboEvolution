/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>CSSValues class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public enum CSSValues {

	ABSOLUTE("absolute"),

	AUTO("auto"),

	BLINK("blink"),

	BLOCK("block"),

	BOLD("bold"),

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

	DASHED("dashed"),

	DECIMAL("decimal"),

	DECIMAL_LEADING_ZERO("decimal-leading-zero"),

	DEFAULT("default"),

	DISC("disc"),

	DOTTED("dotted"),

	DOUBLE("double"),
	
	E_RESIZE("e-resize"),

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

	MEDIUM("medium"),

	MENU("menu"),

	MESSAGE_BOX("message-box"),

	MOVE("move"),

	N_RESIZE("n-resize"),
	
	NE_RESIZE("ne-resize"),

	NONE("none"),

	NORMAL("normal"),

	NOWRAP("nowrap"),

	NW_RESIZE("nw-resize"),

	OBLIQUE("oblique"),

	OUTSET("outset"),

	OUTSIDE("outside"),

	OVERLINE("overline"),

	POINTER("pointer"),

	PRE("pre"),

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

	XX_SMALL("xx-small");
	
	private String value;
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
		CSSValues value = ENUM_MAP.get(actionName);
		return value == null ? CSSValues.DEFAULT : value;
	}
}
