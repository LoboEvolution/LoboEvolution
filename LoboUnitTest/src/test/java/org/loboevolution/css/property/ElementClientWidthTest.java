/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.css.property;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Unit tests for {@code offsetWidth} of an element.
 */
@ExtendWith(AlertsExtension.class)
public class ElementClientWidthTest extends LoboUnitTest {

    private static final String VALUE_ = "e == null ? e : (e.clientWidth <= 700 ? e.clientWidth :"
            + "e.clientWidth - document.documentElement.clientWidth)";


    @Test
    @Alerts("0")
    public void abbr() {
        checkHtmlAlert(test("abbr"));
    }


    @Test
    @Alerts("0")
    public void acronym() {
        checkHtmlAlert(test("acronym"));
    }


    @Test
    @Alerts("0")
    public void a() {
        checkHtmlAlert(test("a"));
    }


    @Test
    @Alerts("-16")
    public void address() {
        checkHtmlAlert(test("address"));
    }


    @Test
    @Alerts("0")
    public void applet() {
        checkHtmlAlert(test("applet"));
    }


    @Test
    @Alerts("0")
    public void area() {
        checkHtmlAlert(test("area"));
    }


    @Test
    @Alerts("-16")
    public void article() {
        checkHtmlAlert(test("article"));
    }


    @Test
    @Alerts("-16")
    public void aside() {
        checkHtmlAlert(test("aside"));
    }


    @Test
    @Alerts("0")
    public void audio() {
        checkHtmlAlert(test("audio"));
    }


    @Test
    @Alerts("0")
    public void bgsound() {
        checkHtmlAlert(test("bgsound"));
    }


    @Test
    @Alerts("0")
    public void base() {
        checkHtmlAlert(test("base"));
    }

    @Test
    @Alerts("0")
    public void basefont() {
        checkHtmlAlert(test("basefont"));
    }

    @Test
    @Alerts("0")
    public void bdi() {
        checkHtmlAlert(test("bdi"));
    }

    @Test
    @Alerts("0")
    public void bdo() {
        checkHtmlAlert(test("bdo"));
    }

    @Test
    @Alerts("0")
    public void big() {
        checkHtmlAlert(test("big"));
    }


    @Test
    @Alerts("0")
    public void blink() {
        checkHtmlAlert(test("blink"));
    }


    @Test
    @Alerts("-96")
    public void blockquote() {
        checkHtmlAlert(test("blockquote"));
    }

    @Test
    @Alerts("-16")
    public void body() {
        checkHtmlAlert(test("body"));
    }


    @Test
    @Alerts("0")
    public void b() {
        checkHtmlAlert(test("b"));
    }


    @Test
    @Alerts("0")
    public void br() {
        checkHtmlAlert(test("br"));
    }


    @Test
    @Alerts("12")
    public void button() {
        checkHtmlAlert(test("button"));
    }


    @Test
    @Alerts("300")
    public void canvas() {
        checkHtmlAlert(test("canvas"));
    }


    @Test
    @Alerts("null")
    public void caption() {
        checkHtmlAlert(test("caption"));
    }

    @Test
    @Alerts("-16")
    public void center() {
        checkHtmlAlert(test("center"));
    }

    @Test
    @Alerts("0")
    public void cite() {
        checkHtmlAlert(test("cite"));
    }

    @Test
    @Alerts("0")
    public void code() {
        checkHtmlAlert(test("code"));
    }

    @Test
    @Alerts("0")
    public void command() {
        checkHtmlAlert(test("command"));
    }


    @Test
    @Alerts("0")
    public void datalist() {
        checkHtmlAlert(test("datalist"));
    }


    @Test
    @Alerts("-16")
    public void details() {
        checkHtmlAlert(test("details"));
    }


    @Test
    @Alerts("0")
    public void dfn() {
        checkHtmlAlert(test("dfn"));
    }


    @Test
    @Alerts("-55")
    public void dd() {
        checkHtmlAlert(test("dd"));
    }


    @Test
    @Alerts("0")
    public void del() {
        checkHtmlAlert(test("del"));
    }


    @Test
    @Alerts("0")
    public void diaalert() {
        checkHtmlAlert(test("dialog"));
    }


    @Test
    @Alerts("-16")
    public void dir() {
        checkHtmlAlert(test("dir"));
    }


    @Test
    @Alerts("-16")
    public void div() {
        checkHtmlAlert(test("div"));
    }


    @Test
    @Alerts("-16")
    public void dl() {
        checkHtmlAlert(test("dl"));
    }


    @Test
    @Alerts("-16")
    public void dt() {
        checkHtmlAlert(test("dt"));
    }


    @Test
    @Alerts("0")
    public void embed() {
        checkHtmlAlert(test("embed"));
    }


    @Test
    @Alerts("0")
    public void em() {
        checkHtmlAlert(test("em"));
    }


    @Test
    @Alerts("-24")
    public void fieldset() {
        checkHtmlAlert(test("fieldset"));
    }


    @Test
    @Alerts("-16")
    public void figcaption() {
        checkHtmlAlert(test("figcaption"));
    }


    @Test
    @Alerts("-96")
    public void figure() {
        checkHtmlAlert(test("figure"));
    }


    @Test
    @Alerts("0")
    public void font() {
        checkHtmlAlert(test("font"));
    }


    @Test
    @Alerts("-16")
    public void footer() {
        checkHtmlAlert(test("footer"));
    }


    @Test
    @Alerts("-16")
    public void form() {
        checkHtmlAlert(test("form"));
    }


    @Test
    @Alerts("0")
    public void frame() {
        checkHtmlAlert(test("frame"));
    }


    @Test
    @Alerts("0")
    public void frameset() {
        checkHtmlAlert(test("frameset"));
    }


    @Test
    @Alerts("-16")
    public void h1() {
        checkHtmlAlert(test("h1"));
    }


    @Test
    @Alerts("-16")
    public void h2() {
        checkHtmlAlert(test("h2"));
    }


    @Test
    @Alerts("-16")
    public void h3() {
        checkHtmlAlert(test("h3"));
    }


    @Test
    @Alerts("-16")
    public void h4() {
        checkHtmlAlert(test("h4"));
    }


    @Test
    @Alerts("-16")
    public void h5() {
        checkHtmlAlert(test("h5"));
    }


    @Test
    @Alerts("-16")
    public void h6() {
        checkHtmlAlert(test("h6"));
    }


    @Test
    @Alerts("0")
    public void head() {
        checkHtmlAlert(test("head"));
    }


    @Test
    @Alerts("-16")
    public void header() {
        checkHtmlAlert(test("header"));
    }


    @Test
    @Alerts("-16")
    public void hr() {
        checkHtmlAlert(test("hr"));
    }


    @Test
    @Alerts("300")
    public void iframe() {
        checkHtmlAlert(test("iframe"));
    }


    @Test
    @Alerts("-16")
    public void q() {
        checkHtmlAlert(test("q"));
    }


    @Test
    @Alerts("0")
    public void image() {
        checkHtmlAlert(test("image"));
    }


    @Test
    @Alerts("0")
    public void img() {
        checkHtmlAlert(test("img"));
    }


    @Test
    @Alerts("0")
    public void ins() {
        checkHtmlAlert(test("ins"));
    }


    @Test
    @Alerts("0")
    public void isindex() {
        checkHtmlAlert(test("isindex"));
    }


    @Test
    @Alerts("0")
    public void i() {
        checkHtmlAlert(test("i"));
    }


    @Test
    @Alerts("0")
    public void kbd() {
        checkHtmlAlert(test("kbd"));
    }


    @Test
    @Alerts("0")
    public void keygen() {
        checkHtmlAlert(test("keygen"));
    }


    @Test
    @Alerts("0")
    public void label() {
        checkHtmlAlert(test("label"));
    }


    @Test
    @Alerts("0")
    public void layer() {
        checkHtmlAlert(test("layer"));
    }


    @Test
    @Alerts("-16")
    public void legend() {
        checkHtmlAlert(test("legend"));
    }


    @Test
    @Alerts("-16")
    public void listing() {
        checkHtmlAlert(test("listing"));
    }

    @Test
    @Alerts("-16")
    public void li() {
        checkHtmlAlert(test("li"));
    }


    @Test
    @Alerts("0")
    public void link() {
        checkHtmlAlert(test("link"));
    }


    @Test
    @Alerts("-16")
    public void mainTag() {
        checkHtmlAlert(test("main"));
    }


    @Test
    @Alerts("0")
    public void map() {
        checkHtmlAlert(test("map"));
    }


    @Test
    @Alerts("-16")
    public void marquee() {
        checkHtmlAlert(test("marquee"));
    }


    @Test
    @Alerts("0")
    public void mark() {
        checkHtmlAlert(test("mark"));
    }


    @Test
    @Alerts("-16")
    public void menu() {
        checkHtmlAlert(test("menu"));
    }


    @Test
    @Alerts("0")
    public void menuitem() {
        checkHtmlAlert(test("menuitem"));
    }


    @Test
    @Alerts("0")
    public void meta() {
        checkHtmlAlert(test("meta"));
    }


    @Test
    @Alerts("80")
    public void meter() {
        checkHtmlAlert(test("meter"));
    }


    @Test
    @Alerts("0")
    public void multicol() {
        checkHtmlAlert(test("multicol"));
    }


    @Test
    @Alerts("0")
    public void nobr() {
        checkHtmlAlert(test("nobr"));
    }


    @Test
    @Alerts("-16")
    public void nav() {
        checkHtmlAlert(test("nav"));
    }


    @Test
    @Alerts("0")
    public void nextid() {
        checkHtmlAlert(test("nextid"));
    }


    @Test
    @Alerts("0")
    public void noembed() {
        checkHtmlAlert(test("noembed"));
    }


    @Test
    @Alerts("0")
    public void noframes() {
        checkHtmlAlert(test("noframes"));
    }


    @Test
    @Alerts("0")
    public void nolayer() {
        checkHtmlAlert(test("nolayer"));
    }


    @Test
    @Alerts("0")
    public void noscript() {
        checkHtmlAlert(test("noscript"));
    }


    @Test
    @Alerts("0")
    public void object() {
        checkHtmlAlert(test("object"));
    }


    @Test
    @Alerts("-16")
    public void ol() {
        checkHtmlAlert(test("ol"));
    }


    @Test
    @Alerts("-16")
    public void optgroup() {
        checkHtmlAlert(test("optgroup"));
    }


    @Test
    @Alerts("-16")
    public void option() {
        checkHtmlAlert(test("option"));
    }


    @Test
    @Alerts("0")
    public void output() {
        checkHtmlAlert(test("output"));
    }


    @Test
    @Alerts("-16")
    public void p() {
        checkHtmlAlert(test("p"));
    }


    @Test
    @Alerts("0")
    public void param() {
        checkHtmlAlert(test("param"));
    }


    @Test
    @Alerts("-16")
    public void plaintext() {
        checkHtmlAlert(test("plaintext"));
    }


    @Test
    @Alerts("-16")
    public void pre() {
        checkHtmlAlert(test("pre"));
    }


    @Test
    @Alerts("160")
    public void progress() {
        checkHtmlAlert(test("progress"));
    }


    @Test
    @Alerts("0")
    public void ruby() {
        checkHtmlAlert(test("ruby"));
    }


    @Test
    @Alerts("0")
    public void rb() {
        checkHtmlAlert(test("rb"));
    }


    @Test
    @Alerts("0")
    public void rp() {
        checkHtmlAlert(test("rp"));
    }


    @Test
    @Alerts("0")
    public void rt() {
        checkHtmlAlert(test("rt"));
    }


    @Test
    @Alerts("0")
    public void rtc() {
        checkHtmlAlert(test("rtc"));
    }


    @Test
    @Alerts("0")
    public void s() {
        checkHtmlAlert(test("s"));
    }


    @Test
    @Alerts("0")
    public void samp() {
        checkHtmlAlert(test("samp"));
    }


    @Test
    @Alerts("0")
    public void script() {
        checkHtmlAlert(test("script"));
    }


    @Test
    @Alerts("-16")
    public void section() {
        checkHtmlAlert(test("section"));
    }


    @Test
    @Alerts("22")
    public void select() {
        checkHtmlAlert(test("select"));
    }


    @Test
    @Alerts("-16")
    public void small() {
        checkHtmlAlert(test("small"));
    }


    @Test
    @Alerts("0")
    public void source() {
        checkHtmlAlert(test("source"));
    }


    @Test
    @Alerts("0")
    public void spacer() {
        checkHtmlAlert(test("spacer"));
    }


    @Test
    @Alerts("0")
    public void span() {
        checkHtmlAlert(test("span"));
    }


    @Test
    @Alerts("0")
    public void strike() {
        checkHtmlAlert(test("strike"));
    }


    @Test
    @Alerts("0")
    public void strong() {
        checkHtmlAlert(test("strong"));
    }


    @Test
    @Alerts("0")
    public void style() {
        checkHtmlAlert(test("style"));
    }


    @Test
    @Alerts("0")
    public void sub() {
        checkHtmlAlert(test("sub"));
    }


    @Test
    @Alerts("-16")
    public void summary() {
        checkHtmlAlert(test("summary"));
    }


    @Test
    @Alerts("0")
    public void sup() {
        checkHtmlAlert(test("sup"));
    }


    @Test
    @Alerts("300")
    public void svg() {
        checkHtmlAlert(test("svg"));
    }


    @Test
    @Alerts("0")
    public void table() {
        checkHtmlAlert(test("table"));
    }

    @Test
    @Alerts("null")
    public void col() {
        checkHtmlAlert(test("col"));
    }


    @Test
    @Alerts("null")
    public void colgroup() {
        checkHtmlAlert(test("colgroup"));
    }


    @Test
    @Alerts("null")
    public void tbody() {
        checkHtmlAlert(test("tbody"));
    }


    @Test
    @Alerts("null")
    public void td() {
        checkHtmlAlert(test("td"));
    }


    @Test
    @Alerts("null")
    public void th() {
        checkHtmlAlert(test("th"));
    }

    @Test
    @Alerts("null")
    public void tr() {
        checkHtmlAlert(test("tr"));
    }


    @Test
    @Alerts("0")
    public void track() {
        checkHtmlAlert(test("track"));
    }


    @Test
    @Alerts("168")
    public void textarea() {
        checkHtmlAlert(test("textarea"));
    }


    @Test
    @Alerts("null")
    public void tfoot() {
        checkHtmlAlert(test("tfoot"));
    }


    @Test
    @Alerts("null")
    public void thead() {
        checkHtmlAlert(test("thead"));
    }


    @Test
    @Alerts("0")
    public void tt() {
        checkHtmlAlert(test("tt"));
    }


    @Test
    @Alerts("0")
    public void time() {
        checkHtmlAlert(test("time"));
    }


    @Test
    @Alerts("0")
    public void title() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var e = document.getElementById('outer');\n"
                + "  alert(" + VALUE_ + ");\n"
                + "}\n"
                + "</script>\n"
                + "<title id='outer'><title></title>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("0")
    public void u() {
        checkHtmlAlert(test("u"));
    }


    @Test
    @Alerts("-16")
    public void ul() {
        checkHtmlAlert(test("ul"));
    }


    @Test
    @Alerts("0")
    public void var() {
        checkHtmlAlert(test("var"));
    }


    @Test
    @Alerts("300")
    public void video() {
        checkHtmlAlert(test("video"));
    }


    @Test
    @Alerts("173")
    public void input() {
        checkHtmlAlert(test("input"));
    }


    @Test
    @Alerts("12")
    public void inputButton() {
        checkHtmlAlert(testInput("button"));
    }


    @Test
    @Alerts("13")
    public void inputCheckbox() {
        checkHtmlAlert(testInput("checkbox"));
    }


    @Test
    @Alerts("253")
    public void inputFile() {
        checkHtmlAlert(testInput("file"));
    }


    @Test
    @Alerts("0")
    public void inputHidden() {
        checkHtmlAlert(testInput("hidden"));
    }


    @Test
    @Alerts("173")
    public void inputPassword() {
        checkHtmlAlert(testInput("password"));
    }


    @Test
    @Alerts("13")
    public void inputRadio() {
        checkHtmlAlert(testInput("radio"));
    }


    @Test
    @Alerts("47")
    public void inputReset() {
        checkHtmlAlert(testInput("reset"));
    }


    @Test
    @Alerts("173")
    public void inputSelect() {
        checkHtmlAlert(testInput("select"));
    }


    @Test
    @Alerts("54")
    public void inputSubmit() {
        checkHtmlAlert(testInput("submit"));
    }


    @Test
    @Alerts("173")
    public void inputText() {
        checkHtmlAlert(testInput("text"));
    }


    @Test
    @Alerts("0")
    public void data() {
        checkHtmlAlert(test("data"));
    }


    @Test
    @Alerts("0")
    public void content() {
        checkHtmlAlert(test("content"));
    }


    @Test
    @Alerts("0")
    public void picture() {
        checkHtmlAlert(test("picture"));
    }


    @Test
    @Alerts("0")
    public void template() {
        checkHtmlAlert(test("template"));
    }


    @Test
    @Alerts("0")
    public void slot() {
        checkHtmlAlert(test("slot"));
    }

    private static String test(final String tagName) {
        switch (tagName) {
            case "basefont":
                return headElementClosesItself(tagName);
            case "script":
                return "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  var e = document.getElementById('outer');\n"
                        + "  alert(" + VALUE_ + ");\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head><body onload='test()'>\n"
                        + "<script id='outer'>//<script>\n"
                        + "</script>\n"
                        + "</body></html>";
            default:
                return "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  var e = document.getElementById('outer');\n"
                        + "  alert(" + VALUE_ + ");\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head><body onload='test()'>\n"
                        + "<" + tagName + " id='outer'><" + tagName + "></" + tagName + "></" + tagName + ">\n"
                        + "</body></html>";
        }
    }

    private static String testInput(final String type) {
        return "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var e = document.getElementById('outer');\n"
                + "  alert(" + VALUE_ + ");\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "<input type='" + type + "' id='outer'>\n"
                + "</body></html>";
    }

    private static String headElementClosesItself(final String tagName) {
        return "<html><head>\n"
                + "<" + tagName + " id='outer'><" + tagName + ">\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var e = document.getElementById('outer');\n"
                + "  alert(" + VALUE_ + ");\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
    }
}
