/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>HTMLTag class.</p>
 */
@Getter
public enum HTMLTag {

    HTML("HTML"),
    BODY("BODY"),
    HEAD("HEAD"),

    // DOCUMENT METADATA
    BASE("BASE"),
    BASEFONT("BASEFONT"),
    LINK("LINK"),
    META("META"),
    STYLE("STYLE"),
    TITLE("TITLE"),

    // CONTENT SECTIONING
    ADDRESS("ADDRESS"),
    ARTICLE("ARTICLE"),
    ASIDE("ASIDE"),
    FOOTER("FOOTER"),
    HEADER("HEADER"),
    H1("H1"),
    H2("H2"),
    H3("H3"),
    H4("H4"),
    H5("H5"),
    H6("H6"),
    HGROUP("HGROUP"),
    MAIN("MAIN"),
    NAV("NAV"),
    SECTION("SECTION"),

    // TEXT CONTENT
    BLOCKQUOTE("BLOCKQUOTE"),
    DD("DD"),
    DIV("DIV"),
    DL("DL"),
    DT("DT"),
    FIGCAPTION("FIGCAPTION"),
    FIGURE("FIGURE"),
    HR("HR"),
    LI("LI"),
    MENU("MENU"),
    OL("OL"),
    P("P"),
    PRE("PRE"),
    UL("UL"),

    // INLINE TEXT SEMANTICS
    A("A"),
    ANCHOR("ANCHOR"),
    ABBR("ABBR"),
    B("B"),
    BDI("BDI"),
    BDO("BDO"),
    BR("BR"),
    CITE("CITE"),
    CODE("CODE"),
    DATA("DATA"),
    DFN("DFN"),
    EM("EM"),
    I("I"),
    KBD("KBD"),
    MARK("MARK"),
    Q("Q"),
    RB("RB"),
    RP("RP"),
    RT("RT"),
    RTC("RTC"),
    RUBY("RUBY"),
    S("S"),
    SAMP("SAMP"),
    SMALL("SMALL"),
    SPAN("SPAN"),
    STRONG("STRONG"),
    SUB("SUB"),
    SUP("SUP"),
    TIME("TIME"),
    U("U"),
    VAR("VAR"),
    WBR("WBR"),

    // IMAGE AND MULTIMEDIA
    AREA("AREA"),
    AUDIO("AUDIO"),
    IMG("IMG"),
    MAP("MAP"),
    TRACK("TRACK"),
    VIDEO("VIDEO"),

    // EMBEDDED CONTENT
    EMBED("EMBED"),
    IFRAME("IFRAME"),
    OBJECT("OBJECT"),
    PARAM("PARAM"),
    PICTURE("PICTURE"),
    SOURCE("SOURCE"),

    // MATHML
    MATH("MATH"),

    // SCRIPTING
    CANVAS("CANVAS"),
    NOSCRIPT("NOSCRIPT"),
    SCRIPT("SCRIPT"),

    // DEMARCATING EDITS
    DEL("DEL"),
    INS("INS"),

    // TABLE CONTENT
    CAPTION("CAPTION"),
    COL("COL"),
    COLGROUP("COLGROUP"),
    TABLE("TABLE"),
    TBODY("TBODY"),
    TD("TD"),
    TFOOT("TFOOT"),
    TH("TH"),
    THEAD("THEAD"),
    TR("TR"),

    // FORMS
    BUTTON("BUTTON"),
    DATALIST("DATALIST"),
    FIELDSET("FIELDSET"),
    FORM("FORM"),
    INPUT("INPUT"),
    LABEL("LABEL"),
    LEGEND("LEGEND"),
    METER("METER"),
    OPTGROUP("OPTGROUP"),
    OPTION("OPTION"),
    OUTPUT("OUTPUT"),
    PROGRESS("PROGRESS"),
    SELECT("SELECT"),
    TEXTAREA("TEXTAREA"),

    // INTERACTIVE ELEMENTS
    DETAILS("DETAILS"),
    DIALOG("DIALOG"),
    SUMMARY("SUMMARY"),

    // WEB COMPONENTS
    SLOT("SLOT"),
    TEMPLATE("TEMPLATE"),

    // OBSOLETE/DEPRECATED
    ACRONYM("ACRONYM"),
    APPLET("APPLET"),
    BGSOUND("BGSOUND"),
    BIG("BIG"),
    CENTER("CENTER"),
    COMMAND("COMMAND"),
    CONTENT("CONTENT"),
    DIR("DIR"),
    FONT("FONT"),
    FRAME("FRAME"),
    FRAMESET("FRAMESET"),
    KEYGEN("KEYGEN"),
    MARQUEE("MARQUEE"),
    MENUITEM("MENUITEM"),
    NOBR("NOBR"),
    NOEMBED("NOEMBED"),
    NOFRAMES("NOFRAMES"),
    PLAINTEXT("PLAINTEXT"),
    SHADOW("SHADOW"),
    SPACER("SPACER"),
    STRIKE("STRIKE"),
    TT("TT"),
    XMP("XMP"),

    // RSS FEED ELEMENTS (SEZIONE AGGIUNTIVA)
    RSS("RSS"),
    CHANNEL("CHANNEL"),
    DESCRIPTION("DESCRIPTION"),
    LANGUAGE("LANGUAGE"),
    COPYRIGHT("COPYRIGHT"),
    MANAGING_EDITOR("MANAGINGEDITOR"),
    WEB_MASTER("WEBMASTER"),
    PUB_DATE("PUBDATE"),
    LAST_BUILD_DATE("LASTBUILDDATE"),
    CATEGORY("CATEGORY"),
    GENERATOR("GENERATOR"),
    DOCS("DOCS"),
    CLOUD("CLOUD"),
    TTL("TTL"),
    RATING("RATING"),
    TEXT_INPUT("TEXTINPUT"),
    SKIP_HOURS("SKIPHOURS"),
    SKIP_DAYS("SKIPDAYS"),

    // ITEM-SPECIFIC TAGS
    ITEM("ITEM"),
    AUTHOR("AUTHOR"),
    COMMENTS("COMMENTS"),
    ENCLOSURE("ENCLOSURE"),
    GUID("GUID"),

    // SVG ELEMENTS (SEZIONE AGGIUNTIVA)
    SVG("SVG"),
    CIRCLE("CIRCLE"),
    CLIPPATH("CLIPPATH"),
    DEFS("DEFS"),
    ELLIPSE("ELLIPSE"),
    FEBLEND("FEBLEND"),
    FECOLORMATRIX("FECOLORMATRIX"),
    FECOMPONENTTRANSFER("FECOMPONENTTRANSFER"),
    FECOMPOSITE("FECOMPOSITE"),
    FECONVOLVEMATRIX("FECONVOLVEMATRIX"),
    FEDIFFUSELIGHTING("FEDIFFUSELIGHTING"),
    FEDISPLACEMENTMAP("FEDISPLACEMENTMAP"),
    FEDISTANTLIGHT("FEDISTANTLIGHT"),
    FEFLOOD("FEFLOOD"),
    FEFUNCA("FEFUNCA"),
    FEFUNCB("FEFUNCB"),
    FEFUNCG("FEFUNCG"),
    FEFUNCR("FEFUNCR"),
    FEGAUSSIANBLUR("FEGAUSSIANBLUR"),
    FEIMAGE("FEIMAGE"),
    FEMERGE("FEMERGE"),
    FEMERGENODE("FEMERGENODE"),
    FEMORPHOLOGY("FEMORPHOLOGY"),
    FEOFFSET("FEOFFSET"),
    FEPOINTLIGHT("FEPOINTLIGHT"),
    FESPECULARLIGHTING("FESPECULARLIGHTING"),
    FESPOTLIGHT("FESPOTLIGHT"),
    FETILE("FETILE"),
    FETURBULENCE("FETURBULENCE"),
    FILTER("FILTER"),
    FOREIGNOBJECT("FOREIGNOBJECT"),
    G("G"),
    IMAGE("IMAGE"),
    LINE("LINE"),
    LINEARRGRADIENT("LINEARGRADIENT"),
    MARKER("MARKER"),
    MASK("MASK"),
    PATH("PATH"),
    PATTERN("PATTERN"),
    POLYGON("POLYGON"),
    POLYLINE("POLYLINE"),
    RADIALGRADIENT("RADIALGRADIENT"),
    RECT("RECT"),
    STOP("STOP"),
    SYMBOL("SYMBOL"),
    TEXT("TEXT"),
    TSPAN("TSPAN"),
    USE("USE"),
    VIEW("VIEW"),

    // SVG ANIMATION ELEMENTS
    ANIMATE("ANIMATE"),
    ANIMATE_MOTION("ANIMATEMOTION"),
    ANIMATETRANSFORM("ANIMATETRANSFORM"),
    ANIMATETRASFORM("ANIMATETRANSFORM"),
    DISCARDELEMENT("DISCARDELEMENT"),
    MPATHELEMENT("MPATHELEMENT"),
    SET("SET");

    private final String value;
    private static final Map<String, HTMLTag> ENUM_MAP;

    static {
        final Map<String, HTMLTag> map = new HashMap<>();
        for (final HTMLTag instance : HTMLTag.values()) {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    HTMLTag(final String value) {
        this.value = value;
    }

    /**
     * <p>isEqual.</p>
     *
     * @param value a {@link String} object.
     * @return a boolean.
     */
    public boolean isEqual(final String value) {
        return value.equals(getValue());
    }

    /**
     * <p>isHtml.</p>
     *
     * @param actionName a {@link String} object.
     * @return a boolean.
     */
    public static boolean isHtml(final String actionName) {
        return ENUM_MAP.containsKey(actionName);
    }

    /**
     * <p>get.</p>
     *
     * @param actionName a {@link String} object.
     * @return a {@link HTMLTag} object.
     */
    public static HTMLTag get(final String actionName) {
        String trimmed = actionName.startsWith("/") ? actionName.substring(1) : actionName;
        return ENUM_MAP.get(trimmed);
    }

}
