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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.htmlunit.cssparser.dom.CSSPageRuleImpl}.
 */
public class CSSPageRuleTest extends LoboUnitTest {

    @Test
    public void scriptableToString() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; };\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(Object.prototype.toString.call(rule));\n"
                + "  alert(rule);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object CSSPageRule]", "[object CSSPageRule]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssText() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"@page { margin: 1cm; }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssTextEmpty() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"@page { }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssTextMultipleRules() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page {   margin-left:4cm;margin-right:  3cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"@page { margin-left: 4cm; margin-right: 3cm; }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssTextSet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    rule.cssText = '@page { margin: 2cm; }';\n"
                + "    alert(rule.cssText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"@page { margin: 1cm; }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentRule() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.parentRule);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentRuleSet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    rule.parentRule = rule;\n"
                + "    alert(rule.parentRule);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentStyleSheet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  alert(rule.parentStyleSheet);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object CSSStyleSheet]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentStyleSheetSet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    rule.parentStyleSheet = null;\n"
                + "    alert(rule.parentStyleSheet);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object CSSStyleSheet]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void selectorTextEmpty() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    alert(rule.selectorText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void selectorText() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page :first { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    alert(rule.selectorText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {":first"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void selectorTextCaseInsensitive() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page :FiRsT { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    alert(rule.selectorText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {":first"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void selectorTextSet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page :first { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    alert(rule.selectorText);\n"
                + "    rule.selectorText = ':left';\n"
                + "    alert(rule.selectorText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {":first", ":left"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void selectorTextSetNull() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page :first { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    alert(rule.selectorText);\n"
                + "    rule.selectorText = null;\n"
                + "    alert(rule.selectorText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {":first", null};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void selectorTextSetEmpty() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page :first { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    alert(rule.selectorText);\n"
                + "    rule.selectorText = '';\n"
                + "    alert(rule.selectorText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {":first", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void selectorTextSetInvalid() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page :first { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    alert(rule.selectorText);\n"
                + "    rule.selectorText = ':grey';\n"
                + "    alert(rule.selectorText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {":first", ":first"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void selectorTextSetCaseInsensitive() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page :first { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    alert(rule.selectorText);\n"
                + "    rule.selectorText = ':LeFt';\n"
                + "    alert(rule.selectorText);\n"
                + "  } catch(e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {":first", ":left"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void style() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { margin: 1cm; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var style = rule.style;\n"
                + "  alert(Object.prototype.toString.call(style));\n"
                + "  alert(style);\n"
                + "  alert(style.length);\n"
                + "  alert(style.parentRule);\n"
                + "  alert(style.cssText);\n"
                + "  for (var i = 0; i < style.length; i++) {\n"
                + "    alert(style.item(i));\n"
                + "  }\n"
                + "</script>\n"

                + "</body></html>";

        final String[] messages = {"[object CSSStyleDeclaration]", "[object CSSStyleDeclaration]", "4", "[object CSSPageRule]",
                "margin: 1cm;", "margin-top", "margin-right", "margin-bottom", "margin-left"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleEmpty() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @page { }\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var style = rule.style;\n"
                + "  alert(Object.prototype.toString.call(style));\n"
                + "  alert(style);\n"
                + "  alert(style.length);\n"
                + "  alert(style.parentRule);\n"
                + "  alert(style.cssText);\n"
                + "  for (var i = 0; i < style.length; i++) {\n"
                + "    alert(style.item(i));\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object CSSStyleDeclaration]", "[object CSSStyleDeclaration]", "0", "[object CSSPageRule]", ""};
        checkHtmlAlert(html, messages);
    }
}