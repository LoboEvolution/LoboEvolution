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