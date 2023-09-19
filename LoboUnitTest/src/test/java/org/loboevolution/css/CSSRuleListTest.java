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
package org.loboevolution.css;

import org.htmlunit.cssparser.dom.CSSRuleListImpl;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSRuleListImpl}.
 */

public class CSSRuleListTest extends LoboUnitTest {
    
    @Test
    public void ruleList() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { font-size: 1234px; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules = document.styleSheets[0].cssRules;\n"
                + "    alert(rules.length);\n"
                + "    alert(rules[0]);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"1", "[object CSSStyleRule]"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void wrongRuleListAccess() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { font-size: 1234px; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules = document.styleSheets[0].cssRules;\n"
                + "    var r = rules[1];\n"
                + "    alert(r);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void has() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { font-size: 1234px; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules = document.styleSheets[0].cssRules;\n"
                + "    alert(0 in rules);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void ruleListUnknownAtRule() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  @UnknownAtRule valo-animate-in-fade {0 {opacity: 0;}}\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules = document.styleSheets[0].cssRules;\n"
                + "    alert(rules.length);\n"
                + "    alert(rules[0]);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"0", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void ruleListKeyframes() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  @keyframes mymove {from {top: 0px;} to {top: 200px;}}\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules = document.styleSheets[0].cssRules;\n"
                + "    alert(rules.length);\n"
                + "    alert(rules[0]);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"1", "[object CSSKeyframesRule]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void in() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { font-size: 1234px; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules = document.styleSheets[0].cssRules;\n"
                + "    alert(rules.length);\n"
                + "    alert(-1 in rules);\n"
                + "    alert(0 in rules);\n"
                + "    alert(1 in rules);\n"
                + "    alert(42 in rules);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"1", "false", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void item() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { font-size: 1234px; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules = document.styleSheets[0].cssRules;\n"
                + "    alert(rules.item(0));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object CSSStyleRule]"};
        checkHtmlAlert(html, messages);
    }
}
