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

public class CSSImportRuleTest extends LoboUnitTest {

    @Test
    public void scriptableToString() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css';\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(Object.prototype.toString.call(rule));\n"
                + " alert(rule);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"[object CSSStyleRuleImpl], [object CSSStyleRule]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssText() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css';\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"@import url(\"../css/imp.css\");"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssTextSet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css';\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  try {"
                + "    rule.cssText = '@import \"imp2.css\";';\n"
                + "   alert(rule.cssText);\n"
                + "  } catch(e) {\n"
                + "   alert('exception');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"@import url(\"../css/imp.css\");"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentRule() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css';\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.parentRule);\n"
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
                + "  @import '../css/imp.css';\n"
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

        final String[] messages = {"null"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentStyleSheet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css';\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.parentStyleSheet);\n"
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
                + "  @media screen { p { background-color:#FFFFFF; }};\n"
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

        final String[] messages = {"[object CSSStyleSheet]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void hrefSimpleRelative() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import  'imp.css';\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.href);\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"imp.css", "@import url(\"../css/imp.css/imp.css\");"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void hrefSimpleAbsolute() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import  imp.css;\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.href);\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"§§URL§§imp.css", "@import url('§§URL§§imp.css')"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void hrefUrlRelative() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import url( 'imp.css' );\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.href);\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"imp.css", "@import url(../css/imp.css/imp.css);"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void hrefUrlAbsolute() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import  url(imp.css);\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.href);\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"§§URL§§imp.css", "@import url(§§URL§§imp.css)"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void mediaNone() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css';\n"
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
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object MediaList]", "", "0", "", "@import url(\"../css/imp.css/imp.css\");"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void mediaAll() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css' all;\n"
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
                + " alert(rule.cssText);\n"
                + "</script>\n"

                + "</body></html>";

        final String[] messages = {"[object MediaList]", "all", "1", "all", "all", "@import url(\"imp.css\") all"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void media() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css' screen;\n"
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
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object MediaList]", "screen", "1", "screen", "screen", "@import url(imp.css) screen"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void mediaQuery() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css' only screen and  (color ),print and ( max-width:12cm) and (min-width: 30em);\n"
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
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"2", "only screen and (color)", "print and (max-width: 12cm) and (min-width: 30em)",
                "only screen and (color), print and (max-width: 12cm) and (min-width: 30em)",
                "@import url(\"imp.css\") only screen and (color), "
                        + "print and (max-width: 12cm) and (min-width: 30em)"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleSheet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css';\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + " alert(styleSheet.ownerNode);\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.styleSheet);\n"
                + " alert(rule.styleSheet.href);\n"
                + " alert(rule.styleSheet.ownerNode);\n"
                + " alert(rule.styleSheet.cssRules[0].cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLStyleElement]", "[object CSSStyleSheet]", "§§URL§§imp.css",
                "null", "div { color: green; }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleSheetNotAvailable() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css';\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + " alert(styleSheet.ownerNode);\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.styleSheet);\n"
                + " alert(rule.styleSheet.href);\n"
                + " alert(rule.styleSheet.cssRules);\n"
                + " alert(rule.styleSheet.ownerNode);\n"
                + " alert(rule.styleSheet.cssRules.length);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLStyleElement]", "[object CSSStyleSheet]", "§§URL§§imp.css",
                "[object CSSRuleList]", "null", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleSheetMediaNotMatching() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css' print;\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.styleSheet);\n"
                + " alert(rule.styleSheet.href);\n"
                + " alert(rule.styleSheet.cssRules[0].cssText);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"[object CSSStyleSheet]", "§§URL§§imp.css", "div { color: green; }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleSheetSameObject() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import '../css/imp.css' print;\n"
                + "</style>\n"
                + "<script>\n"
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var sheet = rule.styleSheet;\n"
                + " alert(rule.styleSheet === sheet);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getImportFromCssRulesCollectionRelative() {
        final String html
                = "<html><body>\n"
                + "<style>@import url('../css/foo.css');</style><div id='d'>foo</div>\n"
                + "<script>\n"
                + "  var item = document.styleSheets.item(0);\n"
                + "  if (item.cssRules) {\n"
                + "    var r = item.cssRules[0];\n"
                + "   alert(r);\n"
                + "   alert(r.href);\n"
                + "   alert(r.media);\n"
                + "   alert(r.media.length);\n"
                + "   alert(r.styleSheet);\n"
                + "  } else {\n"
                + "   alert('cssRules undefined');\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"[object CSSImportRule]", "foo.css", "", "0", "[object CSSStyleSheet]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void importedStylesheetsLoaded() {
        final String html
                = "<html><body>\n"
                + "<style>@import url('../css/file1.css');</style>\n"
                + "<div id='d'>foo</div>\n"
                + "<script>\n"
                + "var d = document.getElementById('d');\n"
                + "var s = window.getComputedStyle(d, null);\n"
                + "alert(s.color.indexOf('128') > 0);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void circularImportedStylesheets() {
        final String html = "<html><head>\n"
                + "<link rel='stylesheet' type='text/css' href='../css/file1.css'></link>\n"
                + "<body>\n"
                + "<div id='d'>foo</div>\n"
                + "<script>\n"
                + "  var d = document.getElementById('d');\n"
                + "  var s = window.getComputedStyle(d, null);\n"
                + " alert(s.color.indexOf('128') > 0);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }
}
