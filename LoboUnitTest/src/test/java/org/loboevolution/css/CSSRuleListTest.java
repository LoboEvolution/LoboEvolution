/*
 * Copyright (c) 2002-2021 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.css;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link com.gargoylesoftware.css.dom.CSSRuleListImpl}.
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
