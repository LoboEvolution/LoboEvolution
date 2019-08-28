package org.lobo.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum HTMLTag {
	
	HTML("HTML"),

	TITLE("TITLE"),

	BASE("BASE"),

	HEAD("HEAD"),

	DIV("DIV"),

	DL("DL"),

	BODY("BODY"),

	CENTER("CENTER"),

	CAPTION("CAPTION"),

	PRE("PRE"),

	P("P"),

	BLOCKQUOTE("BLOCKQUOTE"),

	DD("DD"),
	
	DT("DT"),

	SPAN("SPAN"),

	SCRIPT("SCRIPT"),

	IMG("IMG"),
	
	META("META"),
	
	SPACER("SPACER"),

	STYLE("STYLE"),

	LINK("LINK"),

	A("A"),

	ANCHOR("ANCHOR"),

	TABLE("TABLE"),

	TD("TD"),

	TH("TH"),

	TR("TR"),

	FORM("FORM"),

	INPUT("INPUT"),

	BUTTON("BUTTON"),

	TEXTAREA("TEXTAREA"),

	SELECT("SELECT"),

	OPTION("OPTION"),

	FRAMESET("FRAMESET"),

	FRAME("FRAME"),

	IFRAME("IFRAME"),

	UL("UL"),

	OL("OL"),

	LI("LI"),

	HR("HR"),

	BR("BR"),

	OBJECT("OBJECT"),

	EMBED("EMBED"),

	TT("TT"),

	CODE("CODE"),

	SMALL("SMALL"),

	B("B"),

	STRONG("STRONG"),

	U("U"),

	STRIKE("STRIKE"),

	SUP("SUP"),

	SUB("SUB"),

	I("I"),

	EM("EM"),

	CITE("CITE"),

	H1("H1"),

	H2("H2"),

	H3("H3"),

	H4("H4"),

	H5("H5"),

	H6("H6"),

	CANVAS("CANVAS"),

	SVG("SVG"),

	CIRCLE("CIRCLE"),

	RECT("RECT"),

	ELLIPSE("ELLIPSE"),

	LINE("LINE"),

	POLYGON("POLYGON"),

	POLYLINE("POLYLINE"),

	PATH("PATH"),

	G("G"),

	DEFS("DEFS"),

	USE("USE"),

	SYMBOL("SYMBOL"),

	TEXT_HTML("TEXT_HTML"),

	RADIAL_GRADIENT("RADIAL_GRADIENT"),

	LINEAR_GRADIENT("LINEAR_GRADIENT"),

	STOP("STOP"),

	CLIPPATH("CLIPPATH"),

	ANIMATE("ANIMATE"),

	ANIMATE_TRASFORM("ANIMATE_TRASFORM"),

	ANIMATE_COLOR("ANIMATE_COLOR"),
	
	NOSCRIPT("NOSCRIPT");
	
	private String value;
	private static final Map<String, HTMLTag> ENUM_MAP;

	static {
		Map<String, HTMLTag> map = new HashMap<String, HTMLTag>();
		for (HTMLTag instance : HTMLTag.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	HTMLTag(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public boolean isEqual(String value) {
		return this.value.equals(value);
	}

	public static HTMLTag get(String actionName) {
		return ENUM_MAP.get(actionName);
	}

}
