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
package org.loboevolution.css;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSMediaRule}.
 */
@ExtendWith(AlertsExtension.class)
public class CSSMediaRuleTest extends LoboUnitTest {


    @Test
    @Alerts({"[object CSSMediaRuleImpl]", "[object CSSMediaRule]"})
    public void scriptableToString() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(Object.prototype.toString.call(rule));\n"
                + " alert(rule);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("@media screen {\n  p { background-color: rgb(255, 255, 255); }\n}")
    public void cssText() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("@media screen {\n}")
    public void cssTextEmpty() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen {};\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("@media screen {\n  p { }\n  div { }\n}")
    public void cssTextMultipleRules() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p {} div {}}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("@media print {\n  #navigation { display: none; }"
            + "\n  @media (max-width: 12cm) {\n  .note { float: none; }\n}\n}")
    public void cssTextNested() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @media print { #navigation { display: none }"
                + "@media (max-width: 12cm) { .note { float: none } } }"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("@media screen {\n  p { background-color: rgb(255, 255, 255); }\n}")
    public void cssTextSet() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    rule.cssText = '@media screen { span { color: rgb(0, 0, 0); }}';\n"
                + "   alert(rule.cssText);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void parentRule() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.parentRule);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CSSMediaRule]", "[object CSSMediaRule]"})
    public void parentRuleNested() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media print { #navigation { display: none; } "
                + "@media (max-width: 12cm) { .note { float: none; } } }"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var ruleOuter = styleSheet.cssRules[0];\n"
                + "  var ruleInner = ruleOuter.cssRules[1];\n"
                + " alert(ruleInner);\n"
                + " alert(ruleInner.parentRule);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void parentRuleSet() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    rule.parentRule = rule;\n"
                + "   alert(rule.parentRule);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object CSSStyleSheet]")
    public void parentStyleSheet() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.parentStyleSheet);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object CSSStyleSheet]")
    public void parentStyleSheetSet() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    rule.parentStyleSheet = null;\n"
                + "   alert(rule.parentStyleSheet);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MediaList]", "all", "1", "all", "all", "all"})
    public void mediaAll() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media all { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var mediaList = rule.media;\n"
                + " alert(Object.prototype.toString.call(mediaList));\n"
                + " alert(mediaList);\n"
                + " alert(mediaList.length);\n"
                + "  for (var i = 0; i < mediaList.length; i++) {\n"
                + "   alert(mediaList.item(i));\n"
                + "  }\n"
                + " alert(mediaList.mediaText);\n"
                + " alert(rule.conditionText);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MediaList]", "screen", "1", "screen", "screen", "screen"})
    public void media() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var mediaList = rule.media;\n"
                + " alert(Object.prototype.toString.call(mediaList));\n"
                + " alert(mediaList);\n"
                + " alert(mediaList.length);\n"
                + "  for (var i = 0; i < mediaList.length; i++) {\n"
                + "   alert(mediaList.item(i));\n"
                + "  }\n"
                + " alert(mediaList.mediaText);\n"
                + " alert(rule.conditionText);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "only screen and (color)", "print and (max-width: 12cm) and (min-width: 30em)",
            "only screen and (color), print and (max-width: 12cm) and (min-width: 30em)",
            "only screen and (color), print and (max-width: 12cm) and (min-width: 30em)"})
    public void mediaQuery() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media only screen and  (color ),print and ( max-width:12cm) and (min-width: 30em) { "
                + "p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var mediaList = rule.media;\n"
                + " alert(mediaList.length);\n"
                + "  for (var i = 0; i < mediaList.length; i++) {\n"
                + "   alert(mediaList.item(i));\n"
                + "  }\n"
                + " alert(mediaList.mediaText);\n"
                + " alert(rule.conditionText);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CSSRuleList]", "[object CSSRuleList]", "1", "[object CSSStyleRule]",
            "p { background-color: rgb(255, 255, 255); }", "[object CSSMediaRule]"})
    public void cssRules() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(Object.prototype.toString.call(rules));\n"
                + " alert(rules);\n"
                + " alert(rules.length);\n"
                + "  for (var i = 0; i < rules.length; i++) {\n"
                + "   alert(rules.item(i));\n"
                + "   alert(rules.item(i).cssText);\n"
                + "   alert(rules.item(i).parentRule);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CSSRuleList]", "[object CSSRuleList]", "1", "[object CSSStyleRule]",
            "p { background-color: rgb(255, 255, 255); }", "[object CSSMediaRule]"})
    public void cssRulesMediaNotMatching() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media print { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(Object.prototype.toString.call(rules));\n"
                + " alert(rules);\n"
                + " alert(rules.length);\n"
                + "  for (var i = 0; i < rules.length; i++) {\n"
                + "   alert(rules.item(i));\n"
                + "   alert(rules.item(i).cssText);\n"
                + "   alert(rules.item(i).parentRule);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "span { color: rgb(0, 0, 0); }", "[object CSSMediaRule]",
            "p { background-color: rgb(255, 255, 255); }", "[object CSSMediaRule]"})
    public void insertRule() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "   rule.insertRule('span { color:#000000; }')\n"
                + "   alert(rules.length);\n"
                + "    for (var i = 0; i < rules.length; i++) {\n"
                + "     alert(rules.item(i).cssText);\n"
                + "     alert(rules.item(i).parentRule);\n"
                + "    }\n"
                + "  } catch(e) {\n"
                + "   alert(e);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void insertRuleNull() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + "  try {\n"
                + "    rule.insertRule(null);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void insertRuleEmpty() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + "  try {\n"
                + "    rule.insertRule('');\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void insertRuleInvalid() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + "  try {\n"
                + "    rule.insertRule('%ab');\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "p { background-color: rgb(255, 255, 255); }", "[object CSSMediaRule]",
            "span { color: rgb(0, 0, 0); }", "[object CSSMediaRule]"})
    public void insertRuleWithIndex() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "  rule.insertRule('span { color:#000000; }', 1);\n"
                + "   alert(rules.length);\n"
                + "    for (var i = 0; i < rules.length; i++) {\n"
                + "     alert(rules.item(i).cssText);\n"
                + "     alert(rules.item(i).parentRule);\n"
                + "    }\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void insertRuleNullWithIndex() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + "  try {\n"
                + "    rule.insertRule(null, 1);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "exception"})
    public void insertRuleEmptyWithIndex() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "   alert(rule.insertRule('', 1));\n"
                + "   alert(rules.length);\n"
                + "    for (var i = 0; i < rules.length; i++) {\n"
                + "     alert(rules.item(i).cssText);\n"
                + "     alert(rules.item(i).parentRule);\n"
                + "    }\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void insertRuleInvalidWithIndex() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + "  try {\n"
                + "    rule.insertRule('%ab', 1);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "p { background-color: rgb(255, 255, 255); }", "[object CSSMediaRule]",
            "span { color: rgb(0, 0, 0); }", "[object CSSMediaRule]"})
    public void insertRuleWithIndexNull() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "   rule.insertRule('span { color:#000000; }', null);\n"
                + "   alert(rules.length);\n"
                + "    for (var i = 0; i < rules.length; i++) {\n"
                + "     alert(rules.item(i).cssText);\n"
                + "     alert(rules.item(i).parentRule);\n"
                + "    }\n"
                + "  } catch(e) {\n"
                + "   alert('exception'+e);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "p { background-color: rgb(255, 255, 255); }", "[object CSSMediaRule]",
            "span { color: rgb(0, 0, 0); }", "[object CSSMediaRule]"})
    public void insertRuleWithIndexNaN() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + "  alert(rules.length);\n"
                + "  try {\n"
                + "   rule.insertRule('span { color:#000000; }', 'abc');\n"
                + "   alert(rules.length);\n"
                + "    for (var i = 0; i < rules.length; i++) {\n"
                + "     alert(rules.item(i).cssText);\n"
                + "     alert(rules.item(i).parentRule);\n"
                + "    }\n"
                + "  } catch(e) {\n"
                + "   alert('exception'+e);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "exception"})
    public void insertRuleWithIndexNegative() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    rule.insertRule('span { color:#000000; }', 2);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "exception"})
    public void insertRuleWithIndexGreaterThanLength() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    rule.insertRule('span { color:#000000; }', 2);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "1", "p { background-color: rgb(255, 255, 255); }"})
    public void deleteRule() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; } span { color: rgb(0, 0, 0); }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    rule.deleteRule(1);\n"
                + "   alert(rules.length);\n"
                + "    for (var i = 0; i < rules.length; i++) {\n"
                + "     alert(rules.item(i).cssText);\n"
                + "    }\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "1", "span { color: rgb(0, 0, 0); }"})
    public void deleteRuleNull() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; } span { color: rgb(0, 0, 0); }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    rule.deleteRule(null);\n"
                + "   alert(rules.length);\n"
                + "    for (var i = 0; i < rules.length; i++) {\n"
                + "     alert(rules.item(i).cssText);\n"
                + "    }\n"
                + "  } catch(e) {\n"
                + "   alert(e);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "1", "span { color: rgb(0, 0, 0); }"})
    public void deleteRuleNaN() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; } span { color: rgb(0, 0, 0); }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    rule.deleteRule('abc');\n"
                + "   alert(rules.length);\n"
                + "    for (var i = 0; i < rules.length; i++) {\n"
                + "     alert(rules.item(i).cssText);\n"
                + "    }\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "exception"})
    public void deleteRuleNegative() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; } span { color: rgb(0, 0, 0); }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    rule.deleteRule(-1);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "exception"})
    public void deleteRuleGreaterThanLength() {
        final String html
                = "<html>"
                + "<body>\n"
                + "<style>\n"
                + "  @media screen { p { background-color:#FFFFFF; } span { color: rgb(0, 0, 0); }}\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var rules = rule.cssRules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    rule.deleteRule(2);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
