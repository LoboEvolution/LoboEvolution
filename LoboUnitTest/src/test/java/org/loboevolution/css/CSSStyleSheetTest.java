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
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link CSSStyleSheet}.
 */
@ExtendWith(AlertsExtension.class)
public class CSSStyleSheetTest extends LoboUnitTest {


    @Test
    @Alerts({"[object CSSStyleSheet]", "[object HTMLStyleElement]", "true", "undefined", "false"})
    public void owningNodeOwningElement() {
        final String html = "<html><head>\n"
                + "<script>\n" 
                + "function test() {\n"
                + "  var myStyle = document.getElementById('myStyle');\n"
                + "  var stylesheet = document.styleSheets[0];\n"
                + " alert(stylesheet);\n"
                + " alert(stylesheet.ownerNode);\n"
                + " alert(stylesheet.ownerNode == myStyle);\n"
                + " alert(stylesheet.owningElement);\n"
                + " alert(stylesheet.owningElement == myStyle);\n"
                + "}\n"
                + "</script>\n"
                + "<style id='myStyle' type='text/css'></style>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"4", "0", "1", "2", "3", "length", "item"})
    public void rules() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  BODY { background-color: white; color: black; }\n"
                + "  H1 { font: 8pt Arial bold; }\n"
                + "  P  { font: 10pt Arial; text-indent: 0.5in; }\n"
                + "  A  { text-decoration: none; color: blue; }\n"
                + "</style>\n"
                + "<script>"
                +  " function test() {\n"
                + "    var rules;\n"
                + "    if (document.styleSheets[0].cssRules)\n"
                + "      rules = document.styleSheets[0].cssRules;\n"
                + "    else\n"
                + "      rules = document.styleSheets[0].rules;\n"
                + "   alert(rules.length);\n"
                + "    for (var i in rules)\n"
                + "     alert(i);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"4", "style2.css", "style4.css", "null", "null"})
    public void href() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link href='" + URL_CSS + "style1.css' type='text/css'></link>\n"
                        + "    <link href='" + URL_CSS + "style2.css' rel='stylesheet'></link>\n"
                        + "    <link href='" + URL_CSS + "style3.css'></link>\n"
                        + "    <link href='style4.css' rel='stylesheet'></link>\n"
                        + "    <style>div.x { color: red; }</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <style>div.y { color: green; }</style>\n"
                        + "    <script>\n"
                        + "     alert(document.styleSheets.length);\n"
                        + "      for (i = 0; i < document.styleSheets.length; i++) {\n"
                        + "       alert(document.styleSheets[i].href);\n"
                        + "      }\n"
                        + "    </script>\n" 
                + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"8", "style1.css 1", "style2.css 0",
            "style3.css 0", "style4.css 1",
            "style5.css 1", "style6.css 0",
            "style7.css 0", "style8.css 1"})
    public void hrefWrongContentType() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link href='" + URL_CSS + "style1.css' rel='stylesheet' type='text/css'></link>\n"
                        + "    <link href='" + URL_CSS + "style2.css' rel='stylesheet' type='text/css'></link>\n"
                        + "    <link href='" + URL_CSS + "style3.css' rel='stylesheet' type='text/css'></link>\n"
                        + "    <link href='" + URL_CSS + "style4.css' rel='stylesheet' type='text/css'></link>\n"
                        + "    <link href='" + URL_CSS + "style5.css' rel='stylesheet' ></link>\n"
                        + "    <link href='" + URL_CSS + "style6.css' rel='stylesheet' ></link>\n"
                        + "    <link href='" + URL_CSS + "style7.css' rel='stylesheet' ></link>\n"
                        + "    <link href='" + URL_CSS + "style8.css' rel='stylesheet' ></link>\n"
                        + "  </head>\n" 
                + "  <body>\n"
                        + "    <script>\n"

                        + "     alert(document.styleSheets.length);\n"
                        + "      for (i = 0; i < document.styleSheets.length; i++) {\n"
                        + "        var sheet = document.styleSheets[i];\n"
                        + "       alert(sheet.href + ' ' + sheet.cssRules.length);\n"
                        + "      }\n"
                        + "    </script>\n" 
                + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "false", "-1", "div", "color: red;", "2"})
    public void addRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var f = document.getElementById('myStyle');\n"
                + "    var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "    var rules = s.cssRules || s.rules;\n"
                + "   alert(rules.length);\n"
                + "   alert(s.addRule == undefined);\n"
                + "    if (s.addRule) {\n"
                + "     alert(s.addRule('div', 'color: red;'));\n"
                + "     alert(rules[rules.length - 1].selectorText);\n"
                + "     alert(rules[rules.length - 1].style.cssText);\n"
                + "    }\n"
                + "   alert(rules.length);\n"
                + "  }\n"
                + "</script>\n"
                + "<style id='myStyle'>p { vertical-align:top }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"2", "-1", "div", "", "3"})
    public void addRuleInvalidRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>"
                +  " function doTest() {\n"
                + "    var f = document.getElementById('myStyle');\n"
                + "    var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "    var rules = s.cssRules || s.rules;\n"
                + "   alert(rules.length);\n"
                + "    if (s.addRule) {\n"
                + "     alert(s.addRule('div', 'invalid'));\n"
                + "     alert(rules[rules.length - 1].selectorText);\n"
                + "     alert(rules[rules.length - 1].style.cssText);\n"
                + "    }\n"
                + "   alert(rules.length);\n"
                + "  }\n"
                + "  </script>\n"
                + "  <style id='myStyle'>p { vertical-align: top } h1 { color: blue; }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void addInvalidRule() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  try {\n"
                + "    if (s.addRule)\n"
                + "      s.addRule('.testStyle1;', '', 1);\n"
                + "   alert('added');\n"
                + "  } catch(err) {alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"1", "false", "0", "div", "color: red;", "2"})
    public void insertRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var f = document.getElementById('myStyle');\n"
                + "    var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "    var rules = s.cssRules || s.rules;\n"
                + "   alert(rules.length);\n"
                + "   alert(s.insertRule == undefined);\n"
                + "    if (s.insertRule) {\n"
                + "     alert(s.insertRule('div { color: red; }', 0));\n"
                + "     alert(rules[0].selectorText);\n"
                + "     alert(rules[0].style.cssText);\n"
                + "    }\n"
                + "   alert(rules.length);\n"
                + "  }\n"
                + "</script>\n"
                + "<style id='myStyle'>p { vertical-align:top }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"1", "false", "0", "div", "", "2"})
    public void insertRuleInvalidRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var f = document.getElementById('myStyle');\n"
                + "    var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "    var rules = s.cssRules || s.rules;\n"
                + "   alert(rules.length);\n"
                + "   alert(s.insertRule == undefined);\n"
                + "    if (s.insertRule) {\n"
                + "     alert(s.insertRule('div {invalid}', 0));\n"
                + "     alert(rules[0].selectorText);\n"
                + "     alert(rules[0].style.cssText);\n"
                + "    }\n"
                + "   alert(rules.length);\n"
                + "  }\n"
                + "</script>\n"
                + "<style id='myStyle'>p { vertical-align:top }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("exception")
    public void insertInvalidRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  try {\n"
                + "    if (s.insertRule)\n"
                + "      s.insertRule('.testStyle1', 0);\n"
                + "   alert('inserted');\n"
                + "  } catch(err) {alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "false", "false", "undefined", "1", "div", "color: red;"})
    public void removeRuleDeleteRule() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + " alert(rules.length);\n"
                + " alert(s.deleteRule == undefined);\n"
                + " alert(s.removeRule == undefined);\n"
                + "  if (s.deleteRule)\n"
                + "   alert(s.deleteRule(0));\n"
                + "  else\n"
                + "   alert(s.removeRule(0));\n"
                + " alert(rules.length);\n"
                + " alert(rules[0].selectorText);\n"
                + " alert(rules[0].style.cssText);\n"
                + "}</script>\n"
                + "<style id='myStyle'>p { vertical-align:top } div { color: red; }</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("exception")
    public void deleteRuleInvalidParam() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  try {\n"
                + "    if (s.deleteRule)\n"
                + "      s.deleteRule(19);\n"
                + "    else\n"
                + "      s.removeRule(19);\n"
                + "   alert('deleted');\n"
                + "  } catch(err) {alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"2", "1", "div", "color: red;"})
    public void deleteRuleIgnored() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    if (s.deleteRule)\n"
                + "      s.deleteRule(0);\n"
                + "    else\n"
                + "      s.removeRule(0);\n"
                + "   alert(rules.length);\n"
                + "   alert(rules[0].selectorText);\n"
                + "   alert(rules[0].style.cssText);\n"
                + "  } catch(err) {alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'>\n"
                + "  p { vertical-align:top }\n"
                + "  @unknown div { color: red; }\n"
                + "  div { color: red; }\n"
                + "</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"2", "1", "p", "vertical-align: top;"})
    public void deleteRuleIgnoredLast() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + " alert(rules.length);\n"
                + "  try {\n"
                + "    if (s.deleteRule)\n"
                + "      s.deleteRule(1);\n"
                + "    else\n"
                + "      s.removeRule(1);\n"
                + "   alert(rules.length);\n"
                + "   alert(rules[0].selectorText);\n"
                + "   alert(rules[0].style.cssText);\n"
                + "  } catch(err) {alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'>\n"
                + "  p { vertical-align:top }\n"
                + "  @unknown div { color: red; }\n"
                + "  div { color: red; }\n"
                + "</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", ".testStyleDef", "height: 42px;", ".testStyle", "width: 24px;"})
    public void insertRuleLeadingWhitespace() {
        final String html =
                "<html><head><script>\n"

                        + "function doTest() {\n"
                        + "  var f = document.getElementById('myStyle');\n"
                        + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                        + "  var rules = s.cssRules || s.rules;\n"
                        + "  if (s.insertRule) {\n"
                        + "    s.insertRule('.testStyle { width: 24px; }', 0);\n"
                        + "    s.insertRule(' .testStyleDef { height: 42px; }', 0);\n"
                        + "   alert(rules.length);\n"
                        + "   alert(rules[0].selectorText);\n"
                        + "   alert(rules[0].style.cssText);\n"
                        + "   alert(rules[1].selectorText);\n"
                        + "   alert(rules[1].style.cssText);\n"
                        + "  }\n"
                        + "}</script>\n"
                        + "<style id='myStyle'></style>\n"
                        + "</head><body onload='doTest()'>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"false", "false", "true", "true", "false"})
    public void langCondition() {
        final String htmlSnippet = "<div id='elt2' lang='en'></div>\n"
                + "  <div id='elt3' lang='en-GB'></div>\n"
                + "  <div id='elt4' lang='english'></div>\n";
        doTest(":lang(en)", htmlSnippet);
    }


    @Test
    @Alerts({"false", "false", "true", "false", "true"})
    public void langConditionParent() {
        final String htmlSnippet =
                "<div id='elt2' lang='en'>\n"
                        + "  <div id='elt3' lang='de'></div>\n"
                        + "  <div id='elt4' ></div>\n"
                        + "</div>\n";
        doTest(":lang(en)", htmlSnippet);
    }


    @Test
    @Alerts({"true", "false"})
    public void css2Root() {
        doTest(":root", "");
    }

    @Test
    @Alerts({"true", "true", "false"})
    public void css3Not() {
        doTest(":not(span)", "<span id='elt2'></span>");
    }


    @Test
    @Alerts({"false", "false", "true", "false", "true", "true", "true", "true"})
    public void css3Enabled() {
        final String htmlSnippet = "<input id='elt2'>\n"
                + "<input id='elt3' disabled>\n"
                + "<input id='elt4' type='checkbox'>\n"
                + "<button id='elt5' ></button>\n"
                + "<select id='elt6' ></select>\n"
                + "<textarea id='elt7' ></textarea>\n";
        doTest(":enabled", htmlSnippet);
    }


    @Test
    @Alerts({"false", "false", "true", "false", "true", "true", "true", "true"})
    public void css3Disabled() {
        final String htmlSnippet = "<input id='elt2' disabled>\n"
                + "<input id='elt3'>\n"
                + "<input id='elt4' type='checkbox' disabled>\n"
                + "<button id='elt5' disabled></button>\n"
                + "<select id='elt6' disabled></select>\n"
                + "<textarea id='elt7' disabled></textarea>\n";
        doTest(":disabled", htmlSnippet);
    }


    @Test
    @Alerts({"false", "false", "false", "false", "true", "false", "true", "false"})
    public void css3Checked() {
        final String htmlSnippet = "  <input id='elt2'>\n"
                + "  <input id='elt3' checked>\n"
                + "  <input id='elt4' type='checkbox' checked>\n"
                + "  <input id='elt5' type='checkbox'>\n"
                + "  <input id='elt6' type='radio' checked>\n"
                + "  <input id='elt7' type='radio'>\n";
        doTest(":checked", htmlSnippet);
    }


    @Test
    @Alerts({"false", "false", "true", "false", "false", "false", "true", "true", "false", "false"})
    public void css3Required() {
        final String htmlSnippet =
                "  <input id='elt2' required>\n"
                        + "  <input id='elt3' type='text'>\n"
                        + "  <select id='elt4'></select>\n"
                        + "  <textarea id='elt5'></textarea>\n"
                        + "  <textarea id='elt6' required=false></textarea>\n"
                        + "  <textarea id='elt7' required=true></textarea>\n"
                        + "  <div id='elt8'></div>\n"
                        + "  <div id='elt9' required=true></div>\n";
        doTest(":required", htmlSnippet);
    }


    @Test
    @Alerts({"false", "false", "false", "true", "true", "true", "false", "false", "false", "false"})
    public void css3Optional() {
        final String htmlSnippet =
                "  <input id='elt2' required>\n"
                        + "  <input id='elt3' type='text'>\n"
                        + "  <select id='elt4'></select>\n"
                        + "  <textarea id='elt5'></textarea>\n"
                        + "  <textarea id='elt6' required=false></textarea>\n"
                        + "  <textarea id='elt7' required=true></textarea>\n"
                        + "  <div id='elt8'></div>\n"
                        + "  <div id='elt9' required=true></div>\n";
        doTest(":optional", htmlSnippet);
    }

    private void doTest(final String cssSelector, final String htmlSnippet) {
        final String html = "<html id='elt0'><head>\n"
                + "<style>\n"
                + cssSelector + " { z-index: 10 }\n"
                + "</style>\n"
                + "<script>"
                +  " function test() {\n"
                + "    var getStyle = function(e) {\n"
                + "      return window.getComputedStyle(e, '');\n"
                + "    };\n"
                + "    var i = 0;\n"
                + "    while (true) {\n"
                + "      var elt = document.getElementById('elt' + i++);\n"
                + "      if (!elt) return;\n"
                + "     alert(getStyle(elt).zIndex == 10);\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div id='elt1'></div>\n"
                + htmlSnippet
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("width=100")
    public void important() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var e = document.getElementById('style1');\n"
                + " alert('width=' + e.clientWidth);\n"
                + "}\n"
                + "</script>\n"
                + "<style>\n"
                + "#style1 {left: 25px; width: 100px !important;}\n"
                + "#style1 {position: absolute; left: 100px; width: 50px; height: 50px;}\n"
                + "</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "<div id='style1'>Hello</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("none")
    public void fontFace() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var e = document.getElementById('div1');\n"
                + "  var s = window.getComputedStyle(e, '');\n"
                + " alert(s.display);\n"
                + "}\n"
                + "</script>\n"
                + "<style>\n"
                + "  @font-face { font-family: YanoneKaffeesatz; src: url(/YanoneKaffeesatz-Regular.otf); }\n"
                + "  body { font-family: YanoneKaffeesatz; }\n"
                + "  div { display: none; }\n"
                + "</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "<div id='div1'>invisible</div>\n"
                + "visible\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("60")
    public void rulePrioritySpecificity() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "div { z-index: 60 }\n"
                + "* { z-index: 10 }\n"
                + "</style></head>\n"
                + "<body>\n"
                + "<div id='it'>hello</div>\n"
                + "<script>\n"
                + "  var getStyle = function(e) {\n"
                + "    return window.getComputedStyle(e, '');\n"
                + "  };\n"
                + " alert(getStyle(document.getElementById('it')).zIndex);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("60")
    public void rulePrioritySpecificity2() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + ".classA .classB .classC { z-index: 60 }\n"
                + ".classA .classC { z-index: 10 }\n"
                + "</style></head>\n"
                + "<body>\n"
                + "<div class='classA'>\n"
                + "<div class='classB'>\n"
                + "<div id='it' class='classC'>hello</div>\n"
                + "</div>\n"
                + "</div>\n"
                + "<script>\n"
                + "  var getStyle = function(e) {\n"
                + "    return window.getComputedStyle(e, '');\n"
                + "  };\n"
                + " alert(getStyle(document.getElementById('it')).zIndex);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"10", "10"})
    public void rulePriorityPosition() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + ".classA { z-index: 60 }\n"
                + ".classB { z-index: 10 }\n"
                + "</style></head>\n"
                + "<body>\n"
                + "<div id='it1' class='classA classB'>hello</div>\n"
                + "<div id='it2' class='classA classB'>hello</div>\n"
                + "<script>\n"
                + "  var getStyle = function(e) {\n"
                + "    return window.getComputedStyle(e, '');\n"
                + "  };\n"
                + " alert(getStyle(document.getElementById('it1')).zIndex);\n"
                + " alert(getStyle(document.getElementById('it2')).zIndex);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnStyleTagNoMedia() {
        mediaOnStyleTag("");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnStyleTagWhitespace() {
        mediaOnStyleTag(" ");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnStyleTagAll() {
        mediaOnStyleTag("all");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnStyleTag_screen() {
        mediaOnStyleTag("screen");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaOnStyleTagPrint() {
        mediaOnStyleTag("print");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnStyleTagPrintNot() {
        mediaOnStyleTag("not print");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnStyleTagMultipleWithScreen() {
        mediaOnStyleTag("print, screen, tv");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaOnStyleTagMultipleWithoutScreen() {
        mediaOnStyleTag("print, projection, tv");
    }

    private void mediaOnStyleTag(final String media) {
        final String html
                = "<html><head>\n"
                + "<style media='" + media + "'> div { display: none }</style>\n"
                + "</head><body>\n"
                + "<div id='d'>hello</div>\n"
                + "<script>\n"
                + "  var getStyle = function(e) {\n"
                + "    return window.getComputedStyle(e, '');\n"
                + "  };\n"
                + " alert(getStyle(document.getElementById('d')).display);\n"
                + " alert(document.styleSheets.length);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnLinkTagNoMedia() {
        mediaOnLinkTag("");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnLinkTagWhitespace() {
        mediaOnLinkTag(" ");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnLinkTagAll() {
        mediaOnLinkTag("all");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnLinkTag_screen() {
        mediaOnLinkTag("screen");
    }


    @Test
    @Alerts({"block", "0"})
    public void mediaOnLinkTagNotScreen() {
        mediaOnLinkTag("print");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaOnLinkTagMultipleWithScreen() {
        mediaOnLinkTag("print, screen, tv");
    }


    @Test
    @Alerts({"block", "0"})
    public void mediaOnLinkTagMultipleWithoutScreen() {
        mediaOnLinkTag("print, projection, tv");
    }

    private void mediaOnLinkTag(final String media) {
        final String html
                = "<html><head>\n"
                + "<link rel='stylesheet' media='" + media + "' href='" + URL_SECOND + "'></link>\n"
                + "</head><body>\n"
                + "<div id='d'>hello</div>\n"
                + "<script>\n"
                + "  var getStyle = function(e) {\n"
                + "    return window.getComputedStyle(e, '');\n"
                + "  };\n"
                + " alert(getStyle(document.getElementById('d')).display);\n"
                + " alert(document.styleSheets.length);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRule_screen() {
        mediaRule("screen");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleNotScreen() {
        mediaRule("print");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMultipleWithScreen() {
        mediaRule("print, screen, tv");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMultipleWithoutScreen() {
        mediaRule("print, projection, tv");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxWidth() {
        mediaRule("screen and (max-width: 123px)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMaxWidthMatch() {
        mediaRule("screen and (max-width: 10000px)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxWidth_invalid() {
        mediaRule("screen and (max-width: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxWidthWithout_unit() {
        mediaRule("screen and (max-width: 10000)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxWidthWithout_value() {
        mediaRule("screen and (max-width)");
        mediaRule("screen and (max-width:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinWidth() {
        mediaRule("screen and (min-width: 10000px)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMinWidthMatch() {
        mediaRule("screen and (min-width: 123px)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinWidth_invalid() {
        mediaRule("screen and (min-width: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinWidthWithout_unit() {
        mediaRule("screen and (min-width: 123)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinWidthWithout_value() {
        mediaRule("screen and (min-width)");
        mediaRule("screen and (min-width:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxDeviceWidth() {
        mediaRule("screen and (max-device-width: 123px)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMaxDeviceWidthMatch() {
        mediaRule("screen and (max-device-width: 10000px)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxDeviceWidth_invalid() {
        mediaRule("screen and (max-device-width: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxDeviceWidthWithout_unit() {
        mediaRule("screen and (max-device-width: 10000)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxDeviceWidthWithout_value() {
        mediaRule("screen and (max-device-width)");
        mediaRule("screen and (max-device-width:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinDeviceWidth() {
        mediaRule("screen and (min-device-width: 10000px)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMinDeviceWidthMatch() {
        mediaRule("screen and (min-device-width: 123px)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinDeviceWidth_invalid() {
        mediaRule("screen and (min-device-width: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinDeviceWidthWithout_unit() {
        mediaRule("screen and (min-device-width: 123)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinDeviceWidthWithout_value() {
        mediaRule("screen and (min-device-width)");
        mediaRule("screen and (min-device-width:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxHeight() {
        mediaRule("screen and (max-height: 123px)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMaxHeightMatch() {
        mediaRule("screen and (max-height: 10000px)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxHeight_invalid() {
        mediaRule("screen and (max-height: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxHeightWithout_unit() {
        mediaRule("screen and (max-height: 10000)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxHeightWithout_value() {
        mediaRule("screen and (max-height)");
        mediaRule("screen and (max-height:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinHeight() {
        mediaRule("screen and (min-height: 10000px)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMinHeightMatch() {
        mediaRule("screen and (min-height: 123px)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinHeight_invalid() {
        mediaRule("screen and (min-height: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinHeightWithout_unit() {
        mediaRule("screen and (min-height: 123)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinHeightWithout_value() {
        mediaRule("screen and (min-height)");
        mediaRule("screen and (min-height:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxDeviceHeight() {
        mediaRule("screen and (max-device-height: 123px)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMaxDeviceHeightMatch() {
        mediaRule("screen and (max-device-height: 10000px)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxDeviceHeight_invalid() {
        mediaRule("screen and (max-device-height: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxDeviceHeightWithout_unit() {
        mediaRule("screen and (max-device-height: 10000)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxDeviceHeightWithout_value() {
        mediaRule("screen and (max-device-height)");
        mediaRule("screen and (max-device-height:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinDeviceHeight() {
        mediaRule("screen and (min-device-height: 10000px)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMinDeviceHeightMatch() {
        mediaRule("screen and (min-device-height: 123px)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinDeviceHeight_invalid() {
        mediaRule("screen and (min-device-height: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinDeviceHeightWithout_unit() {
        mediaRule("screen and (min-device-height: 123)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinDeviceHeightWithout_value() {
        mediaRule("screen and (min-device-height)");
        mediaRule("screen and (min-device-height:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleResolution() {
        mediaRule("screen and (resolution: 4dpi)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleResolutionMatch() {
        mediaRule("screen and (resolution: 96dpi)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleResolution_invalid() {
        mediaRule("screen and (resolution: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleResolutionWithout_unit() {
        mediaRule("screen and (resolution: 96)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleResolutionWithout_value() {
        mediaRule("screen and (resolution)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleResolutionWithout_valueEmpty() {
        mediaRule("screen and (resolution:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxResolution() {
        mediaRule("screen and (max-resolution: 90dpi)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMaxResolutionMatch() {
        mediaRule("screen and (max-resolution: 10000dpi)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxResolution_invalid() {
        mediaRule("screen and (max-resolution: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxResolutionWithout_unit() {
        mediaRule("screen and (max-resolution: 10000)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMaxResolutionWithout_value() {
        mediaRule("screen and (max-resolution)");
        mediaRule("screen and (max-resolution:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinResolution() {
        mediaRule("screen and (min-resolution: 10000dpi)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleMinResolutionMatch() {
        mediaRule("screen and (min-resolution: 10dpi)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinResolution_invalid() {
        mediaRule("screen and (min-resolution: 5kilo)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinResolutionWithout_unit() {
        mediaRule("screen and (min-resolution: 10)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleMinResolutionWithout_value() {
        mediaRule("screen and (min-resolution)");
        mediaRule("screen and (min-resolution:)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRulePortrait() {
        mediaRule("screen and (orientation: portrait)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRulePortraitNot() {
        mediaRule("not screen and (orientation: portrait)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleLandscape() {
        mediaRule("screen and (orientation: landscape)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleLandscapeNot() {
        mediaRule("not screen and (orientation: landscape)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRule_invalidOrientation() {
        mediaRule("screen and (orientation: unknown)");
    }


    @Test
    @Alerts({"none", "1"})
    public void mediaRuleOrientationWithout_value() {
        mediaRule("screen and (orientation)");
    }


    @Test
    @Alerts({"block", "1"})
    public void mediaRuleOrientationWithout_valueEmpty() {
        mediaRule("screen and (orientation:)");
    }

    private void mediaRule(final String media) {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <style> @media " + media + " { div { display: none } }</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='d'>hello</div>\n"
                + "  <script>\n"
                + "    var getStyle = function(e) {\n"
                + "      return window.getComputedStyle(e, '');\n"
                + "    };\n"
                + "   alert(getStyle(document.getElementById('d')).display);\n"
                + "   alert(document.styleSheets.length);\n"
                + "  </script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("rgb(255, 0, 0)")
    public void veryBig() {
        final String html = "<html>\n"
                + "  <head>\n"
                + "    <link href='" + URL_CSS + "style.css' rel='stylesheet'></link>\n"
                + "  </head>\n"
                + "  <body class='someRed'>\n"
                + "    <script>\n"
                + "      var getStyle = function(e) {\n"
                + "        return window.getComputedStyle(e, '');\n"
                + "      };\n"
                + "      alert(getStyle(document.body).color);\n"
                + "    </script>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("inserted")
    public void insertRuleWithoutGetRules() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n" 
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  try {\n"
                + "    if (s.insertRule) {\n"
                + "      s.insertRule('.testStyle1 { color: red; }', 0);\n"
                + "    } else {\n"
                + "      s.addRule('.testStyle1', 'color: red;', 0);\n"
                + "    }\n"
                + "   alert('inserted');\n"
                + "  } catch(err) {alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    public void isDisplayed() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  .tab div {\n"
                + "    display: none;\n"
                + "  }\n"
                + "\n"
                + "  .tab div:target {\n"
                + "    display: block;\n"
                + "  }\n"
                + "</style></head><body>\n"
                + "<div class='tab'>\n"
                + "  <div id='anchor'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("anchor");
        assertEquals("none", elem.getStyle().getDisplay());
        elem = (HTMLElementImpl) elem.querySelector("#anchor");
        assertEquals("block", elem.getStyle().getDisplay());
    }


    @Test
    public void indexLowercaseElement() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  div { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='di'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("none", elem.getStyle().getDisplay());
    }


    @Test
    public void indexUppercaseElement() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  div { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <DIV id='di'>\n"
                + "    <p>Content</p>\n"
                + "  </DIV>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("none", elem.getStyle().getDisplay());
    }


    @Test
    public void indexUppercaseRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  DIV { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='di'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("none", elem.getStyle().getDisplay());
    }


    @Test
    public void indexLowercaseClass() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  .cls { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='di' class='cls'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("none", elem.getStyle().getDisplay());
    }


    @Test
    public void indexUppercaseElementClass() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  .cls { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='di' class='CLS'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("block", elem.getStyle().getDisplay());
    }


    @Test
    public void indexUppercaseRuleClass() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  .CLS { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='di' class='cls'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("block", elem.getStyle().getDisplay());
    }


    @Test
    public void indexUppercaseClass() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  .CLS { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='di' class='CLS'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("none", elem.getStyle().getDisplay());
    }


    @Test
    public void indexLowercase2Class() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  div.cls { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='di' class='cls'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("none", elem.getStyle().getDisplay());
    }


    @Test
    public void indexUppercase2ElementClass() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  div.cls { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <DIV id='di' class='CLS'>\n"
                + "    <p>Content</p>\n"
                + "  </DIV>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("block", elem.getStyle().getDisplay());
    }


    @Test
    public void indexUppercase2RuleClass() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  div.CLS { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <diV id='di' class='cls'>\n"
                + "    <p>Content</p>\n"
                + "  </diV>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("block", elem.getStyle().getDisplay());
    }


    @Test
    public void indexUppercase2Class() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "  DiV.CLS { display: none; }\n"
                + "</style>"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='di' class='CLS'>\n"
                + "    <p>Content</p>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("di");
        assertEquals("none", elem.getStyle().getDisplay());
    }

    @Test
    @Alerts("undefined")
    public void brokenExternalCSS() {
        final String html = "<html><head>\n"
                + "<link rel='stylesheet' type='text/css' href='" + URL_SECOND + "'/>\n"
                + "</head>\n"
                + "<body>\n"
                + "<script>\n"
                + " alert(document.body.currentStyle);\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
