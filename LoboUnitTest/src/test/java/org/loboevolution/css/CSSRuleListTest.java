/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
