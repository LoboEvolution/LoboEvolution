/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>HTMLTag class.</p>
 */
public enum HTMLTag {
	
	A("A"),
	ANCHOR("ANCHOR"),
	ANIMATE("ANIMATE"),
	ANIMATE_TRASFORM("ANIMATETRANSFORM"),
	APPLET("APPLET"),
	ARTICLE("ARTICLE"),
	B("B"),
	BASE("BASE"),
	BLOCKQUOTE("BLOCKQUOTE"),
	BODY("BODY"),
	BR("BR"),
	BUTTON("BUTTON"),
	CANVAS("CANVAS"),
	CAPTION("CAPTION"),
	CENTER("CENTER"),
	CHANNEL("CHANNEL"),
	CIRCLE("CIRCLE"),
	CITE("CITE"),
	CLIPPATH("CLIPPATH"),
	CODE("CODE"),
	COL("COL"),
	DD("DD"),
	DEFS("DEFS"),
	DESCRIPTION("DESCRIPTION"),
	DIV("DIV"),
	DL("DL"),
	DT("DT"),
	ELLIPSE("ELLIPSE"),
	EM("EM"),
	EMBED("EMBED"),
	FORM("FORM"),
	G("G"),
	H1("H1"),
	H2("H2"),
	H3("H3"),
	H4("H4"),
	H5("H5"),
	H6("H6"),
	HEAD("HEAD"),
	HR("HR"),
	HTML("HTML"),
	I("I"),
	IFRAME("IFRAME"),
	IMAGE("IMAGE"),
	IMG("IMG"),
	INPUT("INPUT"),
	ITEM("ITEM"),
	LI("LI"),
	LINE("LINE"),
	LINEAR_GRADIENT("LINEARGRADIENT"),
	LINK("LINK"),
	MAIN("MAIN"),
	META("META"),
	NOSCRIPT("NOSCRIPT"),
	OBJECT("OBJECT"),
	OL("OL"),
	OPTION("OPTION"),
	P("P"),
	PATH("PATH"),
	POLYGON("POLYGON"),
	POLYLINE("POLYLINE"),
	PRE("PRE"),
	RADIAL_GRADIENT("RADIALGRADIENT"),
	RECT("RECT"),
	RSS("RSS"),
	SAMP("SAMP"),
	SCRIPT("SCRIPT"),
	SELECT("SELECT"),
	SMALL("SMALL"),
	SPACER("SPACER"),
	SPAN("SPAN"),
	STOP("STOP"),
	STRIKE("STRIKE"),
	STRONG("STRONG"),
	STYLE("STYLE"),
	SUB("SUB"),
	SUP("SUP"),
	SVG("SVG"),
	SYMBOL("SYMBOL"),
	TABLE("TABLE"),
	TD("TD"),
	TEXT("TEXT"),
	TEXTAREA("TEXTAREA"),
	TH("TH"),
	TITLE("TITLE"),
	TR("TR"),
	TFOOT("TFOOT"),
	THEAD("THEAD"),
	TBODY("TBODY"),
	TT("TT"),
	U("U"),
	UL("UL"),
	USE("USE"),
	KBD("KBD"),
	VAR("VAR");
	
	private final String value;
	private static final Map<String, HTMLTag> ENUM_MAP;

	static {
		Map<String, HTMLTag> map = new HashMap<>();
		for (HTMLTag instance : HTMLTag.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	HTMLTag(String value) {
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
	 * @return a {@link org.loboevolution.html.HTMLTag} object.
	 */
	public static HTMLTag get(String actionName) {
		return ENUM_MAP.get(actionName);
	}

}
