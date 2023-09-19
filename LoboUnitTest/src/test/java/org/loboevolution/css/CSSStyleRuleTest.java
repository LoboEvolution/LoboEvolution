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

import org.htmlunit.cssparser.dom.CSSStyleRuleImpl;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link CSSStyleRuleImpl}.
 */

public class CSSStyleRuleTest extends LoboUnitTest {


    @Test
    public void test() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { background-color: white; color: black; }\n"
                + "  H1 { font: 8pt Arial bold; }\n"
                + "  P  { font: 10pt Arial; text-indent: 0.5in; }\n"
                + "  A  { text-decoration: none; color: blue; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules;\n"
                + "    if (document.styleSheets[0].cssRules)\n"
                + "      rules = document.styleSheets[0].cssRules;\n"
                + "    else\n"
                + "      rules = document.styleSheets[0].rules;\n"
                + "    var r = rules[1];\n"
                + "    alert(r);\n"
                + "    if (r.type) {\n"
                + "      alert(r.type);\n"
                + "      alert(r.parentStyleSheet);\n"
                + "      alert(r.parentRule);\n"
                + "      alert(r.selectorText);\n"
                + "    } else {\n"
                + "      alert(r.selectorText);\n"
                + "    }\n"
                + "    alert(r.style.marginTop);\n"
                + "    r.style.marginTop = '10px';\n"
                + "    alert(r.style.marginTop);\n"
                + "    alert(r.style.backgroundColor);\n"
                + "    r.style.backgroundColor = 'red';\n"
                + "    alert(r.style.backgroundColor);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object CSSStyleRule]", "1", "[object CSSStyleSheet]", null, "H1", null, "10px", null, "red"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void styleSheet() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { margin: 4px; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules;\n"
                + "    if (document.styleSheets[0].cssRules)\n"
                + "      rules = document.styleSheets[0].cssRules;\n"
                + "    else\n"
                + "      rules = document.styleSheets[0].rules;\n"
                + "    var r = rules[0];\n"
                + "    alert(r.style.marginTop);\n"
                + "    alert(r.style.marginRight);\n"
                + "    alert(r.style.marginBottom);\n"
                + "    alert(r.style.marginLeft);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"4px", "4px", "4px", "4px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void readOnly() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { background-color: white; color: black; }\n"
                + "  H1 { font: 8pt Arial bold; }\n"
                + "  P  { font: 10pt Arial; text-indent: 0.5in; }\n"
                + "  A  { text-decoration: none; color: blue; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules;\n"
                + "    if (document.styleSheets[0].cssRules)\n"
                + "      rules = document.styleSheets[0].cssRules;\n"
                + "    else\n"
                + "      rules = document.styleSheets[0].rules;\n"
                + "    var r = rules[1];\n"
                + "    alert(r.readOnly);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void type() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { background-color: white; color: black; }\n"
                + "  H1 { font: 8pt Arial bold; }\n"
                + "  P  { font: 10pt Arial; text-indent: 0.5in; }\n"
                + "  A  { text-decoration: none; color: blue; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var rules;\n"
                + "    if (document.styleSheets[0].cssRules)\n"
                + "      rules = document.styleSheets[0].cssRules;\n"
                + "    else\n"
                + "      rules = document.styleSheets[0].rules;\n"
                + "    var r = rules[1];\n"
                + "    alert(r.type);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void selectorText() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BoDY { background-color: white; color: black; }\n"
                + "  H1 { font: 8pt Arial bold; }\n"
                + "  A.foo  { color: blue; }\n"
                + "  .foo  { color: blue; }\n"
                + "  .foo .foo2 { font: 8pt; }\n"
                + "  .myFoo { font: 10pt; }\n"
                + "  #byId { font: 8pt; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var sheet = document.styleSheets[0];\n"
                + "    var rules = sheet.cssRules || sheet.rules;\n"
                + "    for (var i = 0; i < rules.length; i++)\n"
                + "      alert(rules[i].selectorText);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"body", "h1", "a.foo", ".foo", ".foo .foo2", ".myFoo", "#byId"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void oldIEStyleFilter() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { filter: progid:DXImageTransform.Microsoft.AlphaImageLoader"
                + "(src='rightCorner.gif', sizingMethod='crop'); }\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var sheet = document.styleSheets[0];\n"
                + "    var rules = sheet.cssRules || sheet.rules;\n"
                + "    alert(rules.length);\n"
                + "    alert(rules[0].style.filter);\n"
                + "  } catch(e) { alert('exception'); }\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"1", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void filter() {
        final String html = "<html><head><title>First</title>\n"
                + "<style>\n"
                + "  BODY { filter: none; }\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var sheet = document.styleSheets[0];\n"
                + "    var rules = sheet.cssRules || sheet.rules;\n"
                + "    alert(rules.length);\n"
                + "    alert(rules[0].style.filter);\n"
                + "  } catch(e) { alert('exception'); }\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"1", "none"};
        checkHtmlAlert(html, messages);
    }
}
