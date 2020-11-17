package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>HTMLTag class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public enum HTMLTag {
	
	A("A"),
	ANCHOR("ANCHOR"),
	ANIMATE("ANIMATE"),
	ANIMATE_TRASFORM("ANIMATETRANSFORM"),
	APPLET("APPLET"),
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
	TT("TT"),
	U("U"),
	UL("UL"),
	USE("USE");
	
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
