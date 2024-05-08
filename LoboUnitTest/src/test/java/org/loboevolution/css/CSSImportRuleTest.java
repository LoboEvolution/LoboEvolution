/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
 * Tests for {@link CSSImportRule}.
 */
@ExtendWith(AlertsExtension.class)
public class CSSImportRuleTest extends LoboUnitTest {


    @Test
    @Alerts({"[object CSSImportRule]", "[object CSSImportRule]"})
    public void scriptableToString() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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
    @Alerts("@import url(\"imp.css\");")
    public void cssText() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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
    @Alerts("@import url(\"imp.css\");")
    public void cssTextSet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("null")
    public void parentRule() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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
    @Alerts("null")
    public void parentRuleSet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"imp.css", "@import url(\"imp.css\");"})
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
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"imp.css", "@import url(\"imp.css\");"})
    public void hrefSimpleAbsolute() {
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
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"imp.css", "@import url(\"imp.css\");"})
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"imp.css", "@import url(\"imp.css\");"})
    public void hrefUrlAbsolute() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import url('imp.css');\n"
                + "</style>\n"
                + "<script>\n"
                + " var styleSheet = document.styleSheets[0];\n"
                + " var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.href);\n"
                + " alert(rule.cssText);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object MediaList]", "", "0", "", "@import url(\"imp.css\");"})
    public void mediaNone() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object MediaList]", "all", "1", "all", "all", "@import url(\"imp.css\") all;"})
    public void mediaAll() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css' all;\n"
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object MediaList]", "screen", "1", "screen", "screen", "@import url(\"imp.css\") screen;"})
    public void media() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css' screen;\n"
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"2", "only screen and (color)", "print and (max-width: 12cm) and (min-width: 30em)",
            "only screen and (color), print and (max-width: 12cm) and (min-width: 30em)",
            "@import url(\"imp.css\") only screen and (color), "
                    + "print and (max-width: 12cm) and (min-width: 30em);"})
    public void mediaQuery() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css' only screen and  (color ),print and ( max-width:12cm) and (min-width: 30em);\n"
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object HTMLStyleElement]", "[object CSSStyleSheet]", "imp.css",
            "null", "div { color: green; }"})
    public void styleSheet() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object HTMLStyleElement]", "[object CSSStyleSheet]", "imp.css",
            "[object CSSRuleList]", "null", "0"})
    public void styleSheetNotAvailable() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css';\n"
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
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object CSSStyleSheet]", "imp.css", "div { color: green; }"})
    public void styleSheetMediaNotMatching() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css' print;\n"
                + "</style>\n"
                + "<script>\n" 
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + " alert(rule.styleSheet);\n"
                + " alert(rule.styleSheet.href);\n"
                + " alert(rule.styleSheet.cssRules[0].cssText);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("true")
    public void styleSheetSameObject() {
        final String html
                = "<html><body>\n"
                + "<style>\n"
                + "  @import 'imp.css' print;\n"
                + "</style>\n"
                + "<script>\n" 
                + "  var styleSheet = document.styleSheets[0];\n"
                + "  var rule = styleSheet.cssRules[0];\n"
                + "  var sheet = rule.styleSheet;\n"
                + " alert(rule.styleSheet === sheet);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CSSImportRule]", "second/", "", "0", "[object CSSStyleSheet]"})
    public void getImportFromCssRulesCollectionAbsolute() {
        getImportFromCssRulesCollection("second/");
    }

    @Test
    @Alerts({"[object CSSImportRule]", "foo.css", "", "0", "[object CSSStyleSheet]"})
    public void getImportFromCssRulesCollectionRelative() {
        getImportFromCssRulesCollection("foo.css");
    }

    private void getImportFromCssRulesCollection(final String cssRef) {
        final String html
                = "<html><body>\n"
                + "<style>@import url('" + cssRef + "');</style><div id='d'>foo</div>\n"
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void importedStylesheetsLoaded() {
        final String html
                = "<html><body>\n"
                + "<style> @import  url('" + URL_CSS + "imp.css" + "');</style>\n"
                + "<div id='d'>foo</div>\n"
                + "<script>\n" 
                + "var d = document.getElementById('d');\n"
                + "var s = window.getComputedStyle(d, null);\n"
                + "alert(s.color.indexOf('128') > 0);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("true")
    public void importedStylesheetsURLResolution() {
        final String html = "<html><head>\n"
                + "<link rel='stylesheet' type='text/css' href='" + URL_CSS + "file1.css'></link>\n"
                + "<body>\n"
                + "<div id='d'>foo</div>\n"
                + "<script>\n" 
                + "var d = document.getElementById('d');\n"
                + "var s = window.getComputedStyle(d, null);\n"
                + "alert(s.color.indexOf('128') > 0);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("true")
    public void circularImportedStylesheets() {
        final String html = "<html><head>\n"
                + "<link rel='stylesheet' type='text/css' href='" + URL_CSS + "file1.css'></link>\n"
                + "<body>\n"
                + "<div id='d'>foo</div>\n"
                + "<script>\n" 
                + "  var d = document.getElementById('d');\n"
                + "  var s = window.getComputedStyle(d, null);\n"
                + " alert(s.color.indexOf('128') > 0);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"true", "true", "true"})
    public void circularImportedStylesheetsComplexCase() {
        final String html = "<html><head>\n"
                + "<link rel='stylesheet' type='text/css' href='" + URL_CSS + "file1.css'></link>\n"
                + "<body>\n"
                + "<div id='d'>foo</div>\n"
                + "<div id='e'>foo</div>\n"
                + "<div id='f'>foo</div>\n"
                + "<script>\n" 
                + "var d = document.getElementById('d');\n"
                + "var s = window.getComputedStyle(d, null);\n"
                + "alert(s.color.indexOf('128') > 0);\n"
                + "var e = document.getElementById('e');\n"
                + "s = window.getComputedStyle(e, null);\n"
                + "alert(s.color.indexOf('127') > 0);\n"
                + "var f = document.getElementById('f');\n"
                + "s = window.getComputedStyle(f, null);\n"
                + "alert(s.color.indexOf('126') > 0);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("42px")
    public void importedStylesheetsLoadedAccordingToMediaType() {
        final String html
                = "<html><head>\n"
                + "  <style>\n"
                + "    @import  url('" + URL_CSS + "imp.css" + "');\n"
                + "    @import  url('" + URL_CSS + "imp.css" + "') print;\n"
                + "  </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='d'>foo</div>\n"
                + "  <script>\n" 
                + "    var d = document.getElementById('d');\n"
                + "    var s = window.getComputedStyle(d, null);\n"
                + "   alert(s.fontSize);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }
}
