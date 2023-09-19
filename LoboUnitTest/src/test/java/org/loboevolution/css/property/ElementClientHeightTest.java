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
package org.loboevolution.css.property;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Unit tests for {@code offsetHeight} of an element.
 */
public class ElementClientHeightTest extends LoboUnitTest {
    private static final String VALUE_ = "e == null ? e : (e.clientHeight < 1000 ? e.clientHeight :" + "e.clientHeight - document.documentElement.clientHeight)";

    @Test
    public void abbr() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("abbr", ""), messages);
    }

    @Test
    public void acronym() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("acronym", ""), messages);
    }

    @Test
    public void a() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("a", ""), messages);
    }

    @Test
    public void address() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("address", ""), messages);
    }

    @Test
    public void applet() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("applet", ""), messages);
    }

    @Test
    public void area() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("area", ""), messages);
    }

    @Test
    public void article() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("article", ""), messages);
    }

    @Test
    public void aside() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("aside", ""), messages);
    }

    @Test
    public void base() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("base", ""), messages);
    }

    @Test
    public void basefont() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("basefont", ""), messages);
    }

    @Test
    public void bdi() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("bdi", ""), messages);
    }

    @Test
    public void blink() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("blink", "text"), messages);
    }

    @Test
    public void blockquote() {
        final String[] messages = {"17"};
        checkHtmlAlert(test("blockquote", ""), messages);
    }

    @Test
    public void body() {
        final String[] messages = {"500"};
        checkHtmlAlert(test("body", ""), messages);
    }

    @Test
    public void b() {
        final String[] messages = {"17"};
        checkHtmlAlert(test("b", ""), messages);
    }

    @Test
    public void br() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("br", ""), messages);
    }

    @Test
    public void button() {
        final String[] messages = {"2"};
        checkHtmlAlert(test("button", ""), messages);
    }

    @Test
    public void canvas() {
        final String[] messages = {"150"};
        checkHtmlAlert(test("canvas", ""), messages);
    }

    @Test
    public void caption() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("caption", ""), messages);
    }

    @Test
    public void center() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("center", "text"), messages);
    }

    @Test
    public void cite() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("cite", "text"), messages);
    }

    @Test
    public void code() {
        final String[] messages = {"15"};
        checkHtmlAlert(test("code", ""), messages);
    }

    @Test
    public void command() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("command", ""), messages);
    }

    @Test
    public void datalist() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("datalist", ""), messages);
    }

    @Test
    public void details() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("details", "text"), messages);
    }

    @Test
    public void dfn() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("dfn", "text"), messages);
    }

    @Test
    public void dd() {
        final String[] messages = {"17"};
        checkHtmlAlert(test("dd", "text"), messages);
    }

    @Test

    public void del() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("del", "text"), messages);
    }

    @Test
    public void diaalert() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("dialog", ""), messages);
    }

    @Test

    public void dir() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("dir", "text"), messages);
    }

    @Test
    public void div() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("div", ""), messages);
    }

    @Test

    public void dl() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("dl", "text"), messages);
    }

    @Test
    public void dt() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("dt", ""), messages);
    }

    @Test
    public void embed() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("embed", ""), messages);
    }

    @Test
    public void em() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("em", "text"), messages);
    }

    @Test
    public void fieldset() {
        final String[] messages = {"35"};
        checkHtmlAlert(test("fieldset", "text"), messages);
    }

    @Test
    public void figcaption() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("figcaption", ""), messages);
    }

    @Test
    public void figure() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("figure", ""), messages);
    }

    @Test

    public void font() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("font", "text"), messages);
    }

    @Test
    public void footer() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("footer", ""), messages);
    }

    @Test
    public void form() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("form", ""), messages);
    }

    @Test
    public void h1() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("h1", ""), messages);
    }

    @Test
    public void h2() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("h2", ""), messages);
    }

    @Test
    public void h3() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("h3", ""), messages);
    }

    @Test
    public void h4() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("h4", ""), messages);
    }

    @Test
    public void h5() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("h5", ""), messages);
    }

    @Test
    public void h6() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("h6", ""), messages);
    }

    @Test
    public void head() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("head", ""), messages);
    }

    @Test
    public void header() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("header", ""), messages);
    }

    @Test
    public void hr() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("hr", ""), messages);
    }

    @Test
    public void iframe() {
        final String[] messages = {"150"};
        checkHtmlAlert(test("iframe", ""), messages);
    }

    @Test
    public void q() {
        final String[] messages = {"17"};
        checkHtmlAlert(test("q", ""), messages);
    }

    @Test
    public void image() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("image", ""), messages);
    }

    @Test
    public void img() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("img", ""), messages);
    }

    @Test
    public void i() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("i", "text"), messages);
    }

    @Test
    public void label() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("label", "text"), messages);
    }

    @Test
    public void layer() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("layer", ""), messages);
    }

    @Test
    public void legend() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("legend", ""), messages);
    }

    @Test
    public void listing() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("listing", "text"), messages);
    }

    @Test
    public void li() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("li", "text"), messages);
    }


    @Test
    public void link() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("link", ""), messages);
    }

    @Test
    public void mainTag() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("main", ""), messages);
    }

    @Test
    public void map() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("map", "text"), messages);
    }

    @Test
    public void marquee() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("marquee", "text"), messages);
    }

    @Test
    public void mark() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("mark", ""), messages);
    }

    @Test
    public void menu() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("menu", "text"), messages);
    }

    @Test
    public void menuitem() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("menuitem", ""), messages);
    }


    @Test
    public void meta() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("meta", ""), messages);
    }

    @Test
    public void meter() {
        final String[] messages = {"16"};
        checkHtmlAlert(test("meter", ""), messages);
    }

    @Test
    public void multicol() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("multicol", ""), messages);
    }

    @Test
    public void nobr() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("nobr", ""), messages);
    }

    @Test
    public void nav() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("nav", ""), messages);
    }

    @Test
    public void nextid() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("nextid", "text"), messages);
    }

    @Test
    public void noembed() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("noembed", ""), messages);
    }

    @Test
    public void noframes() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("noframes", ""), messages);
    }

    @Test
    public void nolayer() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("nolayer", ""), messages);
    }

    @Test
    public void noscript() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("noscript", ""), messages);
    }

    @Test
    public void object() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("object", "text"), messages);
    }

    @Test
    public void ol() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("ol", "text"), messages);
    }

    @Test
    public void optgroup() {
        final String[] messages = {"20"};
        checkHtmlAlert(test("optgroup", ""), messages);
    }

    @Test
    public void option() {
        final String[] messages = {"20"};
        checkHtmlAlert(test("option", ""), messages);
    }


    @Test
    public void output() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("output", ""), messages);
    }


    @Test
    public void p() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("p", ""), messages);
    }

    @Test
    public void param() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("param", ""), messages);
    }

    @Test
    public void plaintext() {
        final String[] messages = {"30"};
        checkHtmlAlert(test("plaintext", ""), messages);
    }

    @Test
    public void pre() {
        final String[] messages = {"15"};
        checkHtmlAlert(test("pre", ""), messages);
    }

    @Test
    public void progress() {
        final String[] messages = {"16"};
        checkHtmlAlert(test("progress", ""), messages);
    }

    @Test
    public void s() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("s", "text"), messages);
    }


    @Test
    public void samp() {
        final String[] messages = {"15"};
        checkHtmlAlert(test("samp", ""), messages);
    }

    @Test
    public void script() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("script", ""), messages);
    }


    @Test
    public void section() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("section", ""), messages);
    }

    @Test
    public void select() {
        final String[] messages = {"17"};
        checkHtmlAlert(test("select", ""), messages);
    }

    @Test
    public void small() {
        final String[] messages = {"15"};
        checkHtmlAlert(test("small", ""), messages);
    }

    @Test
    public void source() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("source", ""), messages);
    }

    @Test
    public void spacer() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("spacer", ""), messages);
    }

    @Test
    public void span() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("span", ""), messages);
    }


    @Test
    public void strike() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("strike", "text"), messages);
    }

    @Test
    public void strong() {
        final String[] messages = {"17"};
        checkHtmlAlert(test("strong", ""), messages);
    }

    @Test
    public void style() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("style", ""), messages);
    }

    @Test
    public void sub() {
        final String[] messages = {"15"};
        checkHtmlAlert(test("sub", "text"), messages);
    }


    @Test
    public void summary() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("summary", ""), messages);
    }

    @Test
    public void sup() {
        final String[] messages = {"15"};
        checkHtmlAlert(test("sup", "text"), messages);
    }

    @Test
    public void svg() {
        final String[] messages = {"150"};
        checkHtmlAlert(test("svg", ""), messages);
    }


    @Test
    public void table() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("table", ""), messages);
    }

    @Test
    public void col() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("col", ""), messages);
    }

    @Test
    public void colgroup() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("colgroup", ""), messages);
    }

    @Test
    public void tbody() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("tbody", ""), messages);
    }

    @Test
    public void td() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("td", ""), messages);
    }

    @Test
    public void th() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("th", ""), messages);
    }

    @Test
    public void tr() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("tr", ""), messages);
    }

    @Test
    public void track() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("track", ""), messages);
    }

    @Test
    public void textarea() {
        final String[] messages = {"34"};
        checkHtmlAlert(test("textarea", ""), messages);
    }

    @Test
    public void tfoot() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("tfoot", ""), messages);
    }

    @Test
    public void thead() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("thead", ""), messages);
    }

    @Test
    public void tt() {
        final String[] messages = {"15"};
        checkHtmlAlert(test("tt", ""), messages);
    }

    @Test
    public void time() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("time", ""), messages);
    }

    @Test
    public void u() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("u", "text"), messages);
    }

    @Test
    public void ul() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("ul", "text"), messages);
    }

    @Test
    public void var() {
        final String[] messages = {"18"};
        checkHtmlAlert(test("var", "text"), messages);
    }

    @Test
    public void video() {
        final String[] messages = {"150"};
        checkHtmlAlert(test("video", ""), messages);
    }

    @Test
    public void input() {
        final String[] messages = {"17"};
        checkHtmlAlert(test("input", ""), messages);
    }

    @Test
    public void inputButton() {
        final String[] messages = {"17"};
        checkHtmlAlert(testInput("button"), messages);
    }

    @Test
    public void inputCheckbox() {
        final String[] messages = {"13"};
        checkHtmlAlert(testInput("checkbox"), messages);
    }

    @Test
    public void inputFile() {
        final String[] messages = {"20"};
        checkHtmlAlert(testInput("file"), messages);
    }

    @Test
    public void inputHidden() {
        final String[] messages = {"0"};
        checkHtmlAlert(testInput("hidden"), messages);
    }

    @Test
    public void inputPassword() {
        final String[] messages = {"17"};
        checkHtmlAlert(testInput("password"), messages);
    }

    @Test
    public void inputRadio() {
        final String[] messages = {"13"};
        checkHtmlAlert(testInput("radio"), messages);
    }

    @Test
    public void inputReset() {
        final String[] messages = {"17"};
        checkHtmlAlert(testInput("reset"), messages);
    }

    @Test
    public void inputSelect() {
        final String[] messages = {"17"};
        checkHtmlAlert(test("select", ""), messages);
    }

    @Test
    public void inputSubmit() {
        final String[] messages = {"17"};
        checkHtmlAlert(testInput("submit"), messages);
    }

    @Test
    public void inputText() {
        final String[] messages = {"17"};
        checkHtmlAlert(testInput("text"), messages);
    }

    @Test
    public void data() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("data", ""), messages);
    }

    @Test
    public void content() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("content", ""), messages);
    }

    @Test
    public void picture() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("picture", ""), messages);
    }

    @Test
    public void template() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("template", ""), messages);
    }

    @Test
    public void slot() {
        final String[] messages = {"0"};
        checkHtmlAlert(test("slot", ""), messages);
    }

    private static String test(final String tagName, final String text) {
        switch (tagName) {
            case "basefont":
                return headElementClosesItself(tagName);
            case "script":
                return "<html><head>\n" + "<script>\n" + "function test() {\n" + "  var e = document.getElementById('outer');\n" + "  alert(" + VALUE_ + ");\n" + "}\n" + "</script>\n" + "</head><body onload='test()'>\n" + "<script id='outer'>//<script>\n" + "</script>\n" + "</body></html>";
            default:
                return "<html><head>\n" + "<script>\n" + "function test() {\n" + "  var e = document.getElementById('outer');\n" + "  alert(" + VALUE_ + ");\n" + "}\n" + "</script>\n" + "</head><body onload='test()'>\n" + "<" + tagName + " id='outer'><" + tagName + ">" + text + "</" + tagName + "></" + tagName + ">\n" + "</body></html>";
        }
    }

    private static String testInput(final String type) {
        return "<html><head>\n" + "<script>\n" + "function test() {\n" + "  var e = document.getElementById('outer');\n" + "  alert(" + VALUE_ + ");\n" + "}\n" + "</script>\n" + "</head><body onload='test()'>\n" + "<input type='" + type + "' id='outer'>\n" + "</body></html>";
    }

    private static String headElementClosesItself(final String tagName) {
        return "<html><head>\n" + "<" + tagName + " id='outer'><" + tagName + ">\n" + "<script>\n" + "function test() {\n" + "  var e = document.getElementById('outer');\n" + "  alert(" + VALUE_ + ");\n" + "}\n" + "</script>\n" + "</head><body onload='test()'>\n" + "</body></html>";
    }
}