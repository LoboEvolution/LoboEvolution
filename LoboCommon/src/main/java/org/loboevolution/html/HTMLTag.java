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
 * <p>HTMLTag class.</p>
 */
public enum HTMLTag {

    A("A"),
    ADDRESS("ADDRESS"),
    ANCHOR("ANCHOR"),
    ANIMATE("ANIMATE"),
    ANIMATE_TRASFORM("ANIMATETRANSFORM"),
    APPLET("APPLET"),
    ARTICLE("ARTICLE"),
    ASIDE("ASIDE"),
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
    COLGROUP("COLGROUP"),
    DD("DD"),
    DEFS("DEFS"),
    DESCRIPTION("DESCRIPTION"),
    DIALOG("DIALOG"),
    DIV("DIV"),
    DIR("DIR"),
    DL("DL"),
    DT("DT"),
    DETAILS("DETAILS"),
    ELLIPSE("ELLIPSE"),
    EM("EM"),
    EMBED("EMBED"),
    FIELDSET("FIELDSET"),
    FIGCAPTION("FIGCAPTION"),
    FIGURE("FIGURE"),
    FORM("FORM"),
    FOOTER("FOOTER"),
    G("G"),
    H1("H1"),
    H2("H2"),
    H3("H3"),
    H4("H4"),
    H5("H5"),
    H6("H6"),
    HEAD("HEAD"),
    HEADER("HEADER"),
    HR("HR"),
    HTML("HTML"),
    I("I"),
    IFRAME("IFRAME"),
    IMAGE("IMAGE"),
    IMG("IMG"),
    INPUT("INPUT"),
    ITEM("ITEM"),
    LEGEND("LEGEND"),
    LI("LI"),
    LINE("LINE"),
    LINEAR_GRADIENT("LINEARGRADIENT"),
    LINK("LINK"),
    MAIN("MAIN"),
    MARQUEE("MARQUEE"),
    META("META"),
    MENU("MENU"),
    NAV("NAV"),
    NOSCRIPT("NOSCRIPT"),
    OBJECT("OBJECT"),
    OL("OL"),
    OPTION("OPTION"),
    OPTGROUP("OPTGROUP"),
    P("P"),
    PATH("PATH"),
    POLYGON("POLYGON"),
    POLYLINE("POLYLINE"),
    PRE("PRE"),
    PROGRESS("PROGRESS"),
    Q("Q"),
    RADIAL_GRADIENT("RADIALGRADIENT"),
    RECT("RECT"),
    RSS("RSS"),
    SAMP("SAMP"),
    SCRIPT("SCRIPT"),
    SELECT("SELECT"),
    SECTION("SECTION"),
    SMALL("SMALL"),
    SPACER("SPACER"),
    SPAN("SPAN"),
    STOP("STOP"),
    STRIKE("STRIKE"),
    STRONG("STRONG"),
    STYLE("STYLE"),
    SUB("SUB"),
    SUMMARY("SUMMARY"),
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
    VAR("VAR"),

    VIDEO("VIDEO");

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
