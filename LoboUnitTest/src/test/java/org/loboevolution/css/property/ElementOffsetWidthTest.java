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
public class ElementOffsetWidthTest extends LoboUnitTest {

    private static final String VALUE_ = "e == null ? e : (e.offsetWidth < 700 ? e.offsetWidth :"
            + "e.offsetWidth - document.documentElement.offsetWidth)";

    @Test
    @Alerts("0")
    public void abbr() {
        test("abbr");
    }


    @Test
    @Alerts("0")
    public void acronym() {
        test("acronym");
    }


    @Test
    @Alerts("0")
    public void a() {
        test("a");
    }


    @Test
    @Alerts("-16")
    public void address() {
        test("address");
    }


    @Test
    @Alerts("0")
    public void applet() {
        test("applet");
    }


    @Test
    @Alerts("0")
    public void area() {
        test("area");
    }


    @Test
    @Alerts("-16")
    public void article() {
        test("article");
    }


    @Test
    @Alerts("-16")
    public void aside() {
        test("aside");
    }


    @Test
    @Alerts("0")
    public void audio() {
        test("audio");
    }

    @Test
    @Alerts("0")
    public void bgsound() {
        test("bgsound");
    }


    @Test
    @Alerts("0")
    public void base() {
        test("base");
    }


    @Test
    @Alerts("0")
    public void basefont() {
        test("basefont");
    }


    @Test
    @Alerts("0")
    public void bdi() {
        test("bdi");
    }


    @Test
    @Alerts("0")
    public void bdo() {
        test("bdo");
    }


    @Test
    @Alerts("0")
    public void big() {
        test("big");
    }


    @Test
    @Alerts("0")
    public void blink() {
        test("blink");
    }


    @Test
    @Alerts("-96")
    public void blockquote() {
        test("blockquote");
    }


    @Test
    @Alerts("-16")
    public void body() {
        test("body");
    }

    @Test
    @Alerts("0")
    public void b() {
        test("b");
    }


    @Test
    @Alerts("0")
    public void br() {
        test("br");
    }

    @Test
    @Alerts("22")
    public void button() {
        test("button");
    }

    @Test
    @Alerts("300")
    public void canvas() {
        test("canvas");
    }

    @Test
    @Alerts("null")
    public void caption() {
        test("caption");
    }


    @Test
    @Alerts("-16")
    public void center() {
        test("center");
    }


    @Test
    @Alerts("0")
    public void cite() {
        test("cite");
    }

    @Test
    @Alerts("0")
    public void code() {
        test("code");
    }


    @Test
    @Alerts("0")
    public void command() {
        test("command");
    }


    @Test
    @Alerts("0")
    public void datalist() {
        test("datalist");
    }


    @Test
    @Alerts("-16")
    public void details() {
        test("details");
    }


    @Test
    @Alerts("0")
    public void dfn() {
        test("dfn");
    }


    @Test
    @Alerts("-55")
    public void dd() {
        test("dd");
    }


    @Test
    @Alerts("0")
    public void del() {
        test("del");
    }


    @Test
    @Alerts("0")
    public void diaalert() {
        test("dialog");
    }


    @Test
    @Alerts("-16")
    public void dir() {
        test("dir");
    }


    @Test
    @Alerts("-16")
    public void div() {
        test("div");
    }


    @Test
    @Alerts("-16")
    public void dl() {
        test("dl");
    }


    @Test
    @Alerts("-16")
    public void dt() {
        test("dt");
    }


    @Test
    @Alerts("0")
    public void embed() {
        test("embed");
    }


    @Test
    @Alerts("0")
    public void em() {
        test("em");
    }


    @Test
    @Alerts("-24")
    public void fieldset() {
        test("fieldset");
    }


    @Test
    @Alerts("-16")
    public void figcaption() {
        test("figcaption");
    }


    @Test
    @Alerts("-96")
    public void figure() {
        test("figure");
    }


    @Test
    @Alerts("0")
    public void font() {
        test("font");
    }


    @Test
    @Alerts("-16")
    public void footer() {
        test("footer");
    }


    @Test
    @Alerts("-16")
    public void form() {
        test("form");
    }


    @Test
    @Alerts("-16")
    public void h1() {
        test("h1");
    }


    @Test
    @Alerts("-16")
    public void h2() {
        test("h2");
    }


    @Test
    @Alerts("-16")
    public void h3() {
        test("h3");
    }


    @Test
    @Alerts("-16")
    public void h4() {
        test("h4");
    }


    @Test
    @Alerts("-16")
    public void h5() {
        test("h5");
    }


    @Test
    @Alerts("-16")
    public void h6() {
        test("h6");
    }


    @Test
    @Alerts("0")
    public void head() {
        test("head");
    }


    @Test
    @Alerts("-16")
    public void header() {
        test("header");
    }


    @Test
    @Alerts("-16")
    public void hr() {
        test("hr");
    }


    @Test
    @Alerts("300")
    public void iframe() {
        test("iframe");
    }


    @Test
    @Alerts("25")
    public void q() {
        test("q");
    }


    @Test
    @Alerts("0")
    public void image() {
        test("image");
    }


    @Test
    @Alerts("0")
    public void img() {
        test("img");
    }


    @Test
    @Alerts("0")
    public void ins() {
        test("ins");
    }


    @Test
    @Alerts("0")
    public void isindex() {
        test("isindex");
    }


    @Test
    @Alerts("0")
    public void i() {
        test("i");
    }


    @Test
    @Alerts("0")
    public void kbd() {
        test("kbd");
    }

    @Test
    @Alerts("0")
    public void keygen() {
        test("keygen");
    }


    @Test
    @Alerts("0")
    public void label() {
        test("label");
    }


    @Test
    @Alerts("0")
    public void layer() {
        test("layer");
    }


    @Test
    @Alerts("-16")
    public void legend() {
        test("legend");
    }


    @Test
    @Alerts("-16")
    public void listing() {
        test("listing");
    }


    @Test
    @Alerts("-16")
    public void li() {
        test("li");
    }


    @Test
    @Alerts("0")
    public void link() {
        test("link");
    }


    @Test
    @Alerts("-16")
    public void mainTsg() {
        test("main");
    }


    @Test
    @Alerts("0")
    public void map() {
        test("map");
    }


    @Test
    @Alerts("-16")
    public void marquee() {
        test("marquee");
    }


    @Test
    @Alerts("0")
    public void mark() {
        test("mark");
    }


    @Test
    @Alerts("-16")
    public void menu() {
        test("menu");
    }


    @Test
    @Alerts("0")
    public void menuitem() {
        test("menuitem");
    }

    @Test
    @Alerts("0")
    public void meta() {
        test("meta");
    }

    @Test
    @Alerts("80")
    public void meter() {
        test("meter");
    }


    @Test
    @Alerts("0")
    public void multicol() {
        test("multicol");
    }


    @Test
    @Alerts("0")
    public void nobr() {
        test("nobr");
    }


    @Test
    @Alerts("-16")
    public void nav() {
        test("nav");
    }


    @Test
    @Alerts("0")
    public void nextid() {
        test("nextid");
    }


    @Test
    @Alerts("0")
    public void noembed() {
        test("noembed");
    }


    @Test
    @Alerts("0")
    public void noframes() {
        test("noframes");
    }


    @Test
    @Alerts("0")
    public void nolayer() {
        test("nolayer");
    }


    @Test
    @Alerts("0")
    public void noscript() {
        test("noscript");
    }


    @Test
    @Alerts("0")
    public void object() {
        test("object");
    }


    @Test
    @Alerts("-16")
    public void ol() {
        test("ol");
    }


    @Test
    @Alerts("-16")
    public void optgroup() {
        test("optgroup");
    }


    @Test
    @Alerts("-16")
    public void option() {
        test("option");
    }


    @Test
    @Alerts("0")
    public void output() {
        test("output");
    }


    @Test
    @Alerts("-16")
    public void p() {
        test("p");
    }


    @Test
    @Alerts("0")
    public void param() {
        test("param");
    }


    @Test
    @Alerts("-16")
    public void plaintext() {
        test("plaintext");
    }


    @Test
    @Alerts("-16")
    public void pre() {
        test("pre");
    }


    @Test
    @Alerts("160")
    public void progress() {
        test("progress");
    }


    @Test
    @Alerts("0")
    public void ruby() {
        test("ruby");
    }


    @Test
    @Alerts("0")
    public void rb() {
        test("rb");
    }

    @Test
    @Alerts("0")
    public void rp() {
        test("rp");
    }


    @Test
    @Alerts("0")
    public void rt() {
        test("rt");
    }


    @Test
    @Alerts("0")
    public void rtc() {
        test("rtc");
    }


    @Test
    @Alerts("0")
    public void s() {
        test("s");
    }


    @Test
    @Alerts("0")
    public void samp() {
        test("samp");
    }


    @Test
    @Alerts("0")
    public void script() {
        test("script");
    }


    @Test
    @Alerts("-16")
    public void section() {
        test("section");
    }


    @Test
    @Alerts("24")
    public void select() {
        test("select");
    }

    @Test
    @Alerts("-16")
    public void small() {
        test("small");
    }

    @Test
    @Alerts("0")
    public void source() {
        test("source");
    }

    @Test
    @Alerts("0")
    public void spacer() {
        test("spacer");
    }


    @Test
    @Alerts("0")
    public void span() {
        test("span");
    }


    @Test
    @Alerts("0")
    public void strike() {
        test("strike");
    }


    @Test
    @Alerts("0")
    public void strong() {
        test("strong");
    }

    @Test
    @Alerts("0")
    public void style() {
        test("style");
    }

    @Test
    @Alerts("0")
    public void sub() {
        test("sub");
    }

    @Test
    @Alerts("-16")
    public void summary() {
        test("summary");
    }

    @Test
    @Alerts("0")
    public void sup() {
        test("sup");
    }

    @Test
    @Alerts("300")
    public void svg() {
        test("svg");
    }

    @Test
    @Alerts("0")
    public void table() {
        test("table");
    }

    @Test
    @Alerts("null")
    public void col() {
        test("col");
    }

    @Test
    @Alerts("null")
    public void colgroup() {
        test("colgroup");
    }

    @Test
    @Alerts("null")
    public void tbody() {
        test("tbody");
    }


    @Test
    @Alerts("null")
    public void td() {
        test("td");
    }

    @Test
    @Alerts("null")
    public void th() {
        test("th");
    }


    @Test
    @Alerts("null")
    public void tr() {
        test("tr");
    }

    @Test
    @Alerts("0")
    public void track() {
        test("track");
    }


    @Test
    @Alerts("183")
    public void textarea() {
        test("textarea");
    }


    @Test
    @Alerts("null")
    public void tfoot() {
        test("tfoot");
    }

    @Test
    @Alerts("null")
    public void thead() {
        test("thead");
    }

    @Test
    @Alerts("0")
    public void tt() {
        test("tt");
    }

    @Test
    @Alerts("0")
    public void time() {
        test("time");
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
        test("u");
    }


    @Test
    @Alerts("-16")
    public void ul() {
        test("ul");
    }


    @Test
    @Alerts("0")
    public void var() {
        test("var");
    }


    @Test
    @Alerts("300")
    public void video() {
        test("video");
    }


    @Test
    @Alerts("0")
    public void wbr() {
        test("wbr");
    }


    @Test
    @Alerts("-16")
    public void xmp() {
        test("xmp");
    }


    @Test
    @Alerts("177")
    public void input() {
        test("input");
    }


    @Test
    @Alerts("16")
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
    @Alerts("177")
    public void inputPassword() {
        checkHtmlAlert(testInput("password"));
    }


    @Test
    @Alerts("13")
    public void inputRadio() {
        checkHtmlAlert(testInput("radio"));
    }


    @Test
    @Alerts("51")
    public void inputReset() {
        checkHtmlAlert(testInput("reset"));
    }


    @Test
    @Alerts("177")
    public void inputSelect() {
        checkHtmlAlert(testInput("select"));
    }


    @Test
    @Alerts("58")
    public void inputSubmit() {
        checkHtmlAlert(testInput("submit"));
    }


    @Test
    @Alerts("177")
    public void inputText() {
        checkHtmlAlert(testInput("text"));
    }


    @Test
    @Alerts("0")
    public void data() {
        test("data");
    }


    @Test
    @Alerts("0")
    public void content() {
        test("content");
    }


    @Test
    @Alerts("0")
    public void picture() {
        test("picture");
    }


    @Test
    @Alerts("0")
    public void template() {
        test("template");
    }


    @Test
    @Alerts("0")
    public void slot() {
        test("slot");
    }

    private static String testInput(final String type) {
        return "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var e = document.getElementById('outer');\n"
                + " alert(" + VALUE_ + ");\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "<input type='" + type + "' id='outer'>\n"
                + "</body></html>";
    }

    private void test(final String tagName) {
        String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var e = document.getElementById('outer');\n"
                + " alert(" + VALUE_ + ");\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "<" + tagName + " id='outer'><" + tagName + "></" + tagName + "></" + tagName + ">\n"
                + "</body></html>";

        if ("basefont".equals(tagName) || "isindex".equals(tagName)) {
            html = "<html><head>\n"
                    + "<" + tagName + " id='outer'><" + tagName + ">\n"
                    + "<script>\n"                    + "function test() {\n"
                    + "  var e = document.getElementById('outer');\n"
                    + "  alert(" + VALUE_ + ");\n"
                    + "}\n"
                    + "</script>\n"
                    + "</head><body onload='test()'>\n"
                    + "</body></html>";
        }

        if ("title".equals(tagName)) {
            // title is a bit special, we have to provide at least
            // one closing tab otherwise title spans to the end of the file
            html = "<html><head>\n"
                    + "<script>\n"                    + "function test() {\n"
                    + "  var e = document.getElementById('outer');\n"
                    + "  alert(" + VALUE_ + ");\n"
                    + "}\n"
                    + "</script>\n"
                    + "<title id='outer'><title></title>\n"
                    + "</head><body onload='test()'>\n"
                    + "</body></html>";
        }

        if ("frame".equals(tagName)) {
            html = "<html><head>\n"
                    + "<script>\n"
                    + "function test() {\n"
                    + "  var e = document.getElementById('outer');\n"
                    + " alert(" + VALUE_ + ");\n"
                    + "}\n"
                    + "</script>\n"
                    + "</head>\n"
                    + "<frameset onload='test()'>\n"
                    + "<frame id='outer'><frame>\n"
                    + "</frameset></html>";
        }
        if ("script".equals(tagName)) {
            html = "<html><head>\n"
                    + "<script>\n"
                    + "function test() {\n"
                    + "  var e = document.getElementById('outer');\n"
                    + " alert(" + VALUE_ + ");\n"
                    + "}\n"
                    + "</script>\n"
                    + "</head><body onload='test()'>\n"
                    + "<script id='outer'>//<script>\n"
                    + "</script>\n"
                    + "</body></html>";
        }
        if ("frameset".equals(tagName)) {
            html = "<html><head>\n"
                    + "<script>\n"
                    + "function test() {\n"
                    + "  var e = document.getElementById('outer');\n"
                    + " alert(" + VALUE_ + ");\n"
                    + "}\n"
                    + "</script>\n"
                    + "</head>\n"
                    + "<frameset onload='test()' id='outer'>\n"
                    + "<frameset>\n"
                    + "</frameset></html>";
        }

        if ("basefont".equals(tagName)
                || "isindex".equals(tagName)) {
            checkHtmlAlert(html);
            return;
        }

        checkHtmlAlert(html);
    }

}
