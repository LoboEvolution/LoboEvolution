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
 * Unit tests for {@link com.gargoylesoftware.css.dom.CSSStyleSheetImpl}.
 */

public class CSSStyleSheetTest extends LoboUnitTest {

    @Test
    public void owningNodeOwningElement() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var myStyle = document.getElementById('myStyle');\n"
                + "  var stylesheet = document.styleSheets[0];\n"
                + "  alert(stylesheet);\n"
                + "  alert(stylesheet.ownerNode);\n"
                + "  alert(stylesheet.ownerNode == myStyle);\n"
                + "  alert(stylesheet.owningElement);\n"
                + "  alert(stylesheet.owningElement == myStyle);\n"
                + "}\n"
                + "</script>\n"
                + "<style id='myStyle' type='text/css'></style>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object CSSStyleSheet]", "[object HTMLStyleElement]", "true", "undefined", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void rules() {
        final String html = "<html><head>\n"
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
                + "    alert(rules.length);\n"
                + "    for (var i in rules)\n"
                + "      alert(i);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"4", "0", "1", "2", "3", "length", "item"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void addRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var f = document.getElementById('myStyle');\n"
                + "    var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "    var rules = s.cssRules || s.rules;\n"
                + "    alert(rules.length);\n"
                + "    alert(s.addRule == undefined);\n"
                + "    if (s.addRule) {\n"
                + "      alert(s.addRule('div', 'color: red;'));\n"
                + "      alert(rules[rules.length - 1].selectorText);\n"
                + "      alert(rules[rules.length - 1].style.cssText);\n"
                + "    }\n"
                + "    alert(rules.length);\n"
                + "  }\n"
                + "</script>\n"
                + "<style id='myStyle'>p { vertical-align:top }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"1", "false", "-1", "div", "color: red;", "2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void addRuleInvalidRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var f = document.getElementById('myStyle');\n"
                + "    var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "    var rules = s.cssRules || s.rules;\n"
                + "    alert(rules.length);\n"
                + "    if (s.addRule) {\n"
                + "      alert(s.addRule('div', 'invalid'));\n"
                + "      alert(rules[rules.length - 1].selectorText);\n"
                + "      alert(rules[rules.length - 1].style.cssText);\n"
                + "    }\n"
                + "    alert(rules.length);\n"
                + "  }\n"
                + "  </script>\n"
                + "  <style id='myStyle'>p { vertical-align: top } h1 { color: blue; }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"2", "-1", "div", "", "3"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void addInvalidRule() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  try {\n"
                + "    if (s.addRule)\n"
                + "      s.addRule('.testStyle1;', '', 1);\n"
                + "    alert('added');\n"
                + "  } catch(err) { alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void insertRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var f = document.getElementById('myStyle');\n"
                + "    var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "    var rules = s.cssRules || s.rules;\n"
                + "    alert(rules.length);\n"
                + "    alert(s.insertRule == undefined);\n"
                + "    if (s.insertRule) {\n"
                + "      alert(s.insertRule('div { color: red; }', 0));\n"
                + "      alert(rules[0].selectorText);\n"
                + "      alert(rules[0].style.cssText);\n"
                + "    }\n"
                + "    alert(rules.length);\n"
                + "  }\n"
                + "</script>\n"
                + "<style id='myStyle'>p { vertical-align:top }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"1", "false", "0", "div", "color: red;", "2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void insertRuleInvalidRule() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var f = document.getElementById('myStyle');\n"
                + "    var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "    var rules = s.cssRules || s.rules;\n"
                + "    alert(rules.length);\n"
                + "    alert(s.insertRule == undefined);\n"
                + "    if (s.insertRule) {\n"
                + "      alert(s.insertRule('div {invalid}', 0));\n"
                + "      alert(rules[0].selectorText);\n"
                + "      alert(rules[0].style.cssText);\n"
                + "    }\n"
                + "    alert(rules.length);\n"
                + "  }\n"
                + "</script>\n"
                + "<style id='myStyle'>p { vertical-align:top }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"1", "false", "0", "div", "", "2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void insertInvalidRule() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  try {\n"
                + "    if (s.insertRule)\n"
                + "      s.insertRule('.testStyle1', 0);\n"
                + "    alert('inserted');\n"
                + "  } catch(err) { alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void removeRule_deleteRule() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  alert(rules.length);\n"
                + "  alert(s.deleteRule == undefined);\n"
                + "  alert(s.removeRule == undefined);\n"
                + "  if (s.deleteRule)\n"
                + "    alert(s.deleteRule(0));\n"
                + "  else\n"
                + "    alert(s.removeRule(0));\n"
                + "  alert(rules.length);\n"
                + "  alert(rules[0].selectorText);\n"
                + "  alert(rules[0].style.cssText);\n"
                + "}</script>\n"
                + "<style id='myStyle'>p { vertical-align:top } div { color: red; }</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"2", "false", "false", "undefined", "1", "div", "color: red;"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void deleteRuleInvalidParam() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  try {\n"
                + "    if (s.deleteRule)\n"
                + "      s.deleteRule(19);\n"
                + "    else\n"
                + "      s.removeRule(19);\n"
                + "    alert('deleted');\n"
                + "  } catch(err) { alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void deleteRuleIgnored() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  alert(rules.length);\n"
                + "  try {\n"
                + "    if (s.deleteRule)\n"
                + "      s.deleteRule(0);\n"
                + "    else\n"
                + "      s.removeRule(0);\n"
                + "    alert(rules.length);\n"
                + "    alert(rules[0].selectorText);\n"
                + "    alert(rules[0].style.cssText);\n"
                + "  } catch(err) { alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'>\n"
                + "  p { vertical-align:top }\n"
                + "  @unknown div { color: red; }\n"
                + "  div { color: red; }\n"
                + "</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"2", "1", "div", "color: red;"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void deleteRuleIgnoredLast() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  alert(rules.length);\n"
                + "  try {\n"
                + "    if (s.deleteRule)\n"
                + "      s.deleteRule(1);\n"
                + "    else\n"
                + "      s.removeRule(1);\n"
                + "    alert(rules.length);\n"
                + "    alert(rules[0].selectorText);\n"
                + "    alert(rules[0].style.cssText);\n"
                + "  } catch(err) { alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'>\n"
                + "  p { vertical-align:top }\n"
                + "  @unknown div { color: red; }\n"
                + "  div { color: red; }\n"
                + "</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"2", "1", "p", "vertical-align: top;"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void insertRuleLeadingWhitespace() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  var rules = s.cssRules || s.rules;\n"
                + "  if (s.insertRule) {\n"
                + "    s.insertRule('.testStyle { width: 24px; }', 0);\n"
                + "    s.insertRule(' .testStyleDef { height: 42px; }', 0);\n"
                + "    alert(rules.length);\n"
                + "    alert(rules[0].selectorText);\n"
                + "    alert(rules[0].style.cssText);\n"
                + "    alert(rules[1].selectorText);\n"
                + "    alert(rules[1].style.cssText);\n"
                + "  }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"2", ".testStyleDef", "height: 42px;", ".testStyle", "width: 24px;"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void langCondition() {
        final String htmlSnippet = "<div id='elt2' lang='en'></div>\n"
                + "  <div id='elt3' lang='en-GB'></div>\n"
                + "  <div id='elt4' lang='english'></div>\n";
        final String[] messages = {"false", "false", "true", "true", "false"};
        doTest(":lang(en)", htmlSnippet, messages);
    }


    @Test
    public void langConditionParent() {
        final String htmlSnippet =
                "<div id='elt2' lang='en'>\n"
                        + "  <div id='elt3' lang='de'></div>\n"
                        + "  <div id='elt4' ></div>\n"
                        + "</div>\n";
        final String[] messages = {"false", "false", "true", "false", "true"};
        doTest(":lang(en)", htmlSnippet, messages);
    }

    @Test
    public void css2_root() {
        final String[] messages = {"true", "false"};
        doTest(":root", "", messages);
    }

    @Test
    public void css3_not() {
        final String[] messages = {"true", "true", "false"};
        doTest(":not(span)", "<span id='elt2'></span>", messages);
    }


    @Test
    public void css3_enabled() {
        final String htmlSnippet = "<input id='elt2'>\n"
                + "<input id='elt3' disabled>\n"
                + "<input id='elt4' type='checkbox'>\n"
                + "<button id='elt5' ></button>\n"
                + "<select id='elt6' ></select>\n"
                + "<textarea id='elt7' ></textarea>\n";
        final String[] messages = {"false", "false", "true", "false", "true", "true", "true", "true"};
        doTest(":enabled", htmlSnippet, messages);
    }

    @Test
    public void css3_disabled() {
        final String htmlSnippet = "<input id='elt2' disabled>\n"
                + "<input id='elt3'>\n"
                + "<input id='elt4' type='checkbox' disabled>\n"
                + "<button id='elt5' disabled></button>\n"
                + "<select id='elt6' disabled></select>\n"
                + "<textarea id='elt7' disabled></textarea>\n";
        final String[] messages = {"false", "false", "true", "false", "true", "true", "true", "true"};
        doTest(":disabled", htmlSnippet, messages);
    }


    @Test
    public void css3_checked() {
        final String htmlSnippet = "  <input id='elt2'>\n"
                + "  <input id='elt3' checked>\n"
                + "  <input id='elt4' type='checkbox' checked>\n"
                + "  <input id='elt5' type='checkbox'>\n"
                + "  <input id='elt6' type='radio' checked>\n"
                + "  <input id='elt7' type='radio'>\n";
        final String[] messages = {"false", "false", "false", "false", "true", "false", "true", "false"};
        doTest(":checked", htmlSnippet, messages);
    }


    @Test
    public void css3_required() {
        final String htmlSnippet =
                "  <input id='elt2' required>\n"
                        + "  <input id='elt3' type='text'>\n"
                        + "  <select id='elt4'></select>\n"
                        + "  <textarea id='elt5'></textarea>\n"
                        + "  <textarea id='elt6' required=false></textarea>\n"
                        + "  <textarea id='elt7' required=true></textarea>\n";
        final String[] messages = {"false", "false", "true", "false", "false", "false", "true", "true"};
        doTest(":required", htmlSnippet, messages);
    }


    @Test
    public void css3_optional() {
        final String htmlSnippet =
                "  <input id='elt2' required>\n"
                        + "  <input id='elt3' type='text'>\n"
                        + "  <select id='elt4'></select>\n"
                        + "  <textarea id='elt5'></textarea>\n"
                        + "  <textarea id='elt6' required=false></textarea>\n"
                        + "  <textarea id='elt7' required=true></textarea>\n";
        final String[] messages = {"false", "false", "false", "true", "true", "true", "false", "false"};
        doTest(":optional", htmlSnippet, messages);
    }

    private void doTest(final String cssSelector, final String htmlSnippet, final String[] messages) {
        final String html = "<html id='elt0'><head>\n"
                + "<style>\n"
                + cssSelector + " { z-index: 10 }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var getStyle = function(e) {\n"
                + "    return window.getComputedStyle(e, '');\n"
                + "  } \n"
                + "    var i = 0;\n"
                + "    while (true) {\n"
                + "      var elt = document.getElementById('elt' + i++);\n"
                + "      if (!elt) return;\n"
                + "      alert(getStyle(elt).zIndex == 10);\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div id='elt1'></div>\n"
                + htmlSnippet
                + "</body></html>";
        checkHtmlAlert(html, messages);
    }

    @Test
    public void important() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var e = document.getElementById('style1');\n"
                + "  alert('width=' + e.clientWidth);\n"
                + "}\n"
                + "</script>\n"
                + "<style>\n"
                + "#style1 {left: 25px; width: 100px !important;}\n"
                + "#style1 {position: absolute; left: 100px; width: 50px; height: 50px;}\n"
                + "</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "<div id='style1'>Hello</div>\n"
                + "</body></html>";
        final String[] messages = {"width=100"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void fontFace() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var e = document.getElementById('div1');\n"
                + "  var s = window.getComputedStyle(e, '');\n"
                + "  alert(s.display);\n"
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

        final String[] messages = {"none"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void rulePriority_specificity() {
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
                + "  }\n"
                + "  alert(getStyle(document.getElementById('it')).zIndex);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"60"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void rulePriority_specificity2() {
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
                + "  }\n"
                + "  alert(getStyle(document.getElementById('it')).zIndex);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"60"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void rulePriority_position() {
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
                + "  alert(getStyle(document.getElementById('it1')).zIndex);\n"
                + "  alert(getStyle(document.getElementById('it2')).zIndex);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"10", "10"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void mediaOnStyleTag_noMedia() {
        mediaOnStyleTag("");
    }

    @Test
    public void mediaOnStyleTag_whitespace() {
        mediaOnStyleTag(" ");
    }

    @Test
    public void mediaOnStyleTag_all() {
        mediaOnStyleTag("all");
    }

    @Test
    public void mediaOnStyleTag_screen() {
        mediaOnStyleTag("screen");
    }

    @Test
    public void mediaOnStyleTag_print() {
        mediaOnStyleTag("print");
    }

    @Test
    public void mediaOnStyleTag_print_not() {
        mediaOnStyleTag("not print");
    }

    @Test
    public void mediaOnStyleTag_multipleWithScreen() {
        mediaOnStyleTag("print, screen, tv");
    }

    @Test
    public void mediaOnStyleTag_multipleWithoutScreen() {
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
                + "  }\n"
                + "  alert(getStyle(document.getElementById('d')).display);\n"
                + "  alert(document.styleSheets.length);\n"
                + "</script></body></html>";
        final String[] messages = {"none", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void mediaOnLinkTag_noMedia() {
        final String[] messages = {"none", "1"};
        mediaOnLinkTag("", messages);
    }

    @Test
    public void mediaOnLinkTag_whitespace() {
        final String[] messages = {"none", "1"};
        mediaOnLinkTag(" ", messages);
    }

    @Test
    public void mediaOnLinkTag_all() {
        final String[] messages = {"none", "1"};
        mediaOnLinkTag("all", messages);
    }

    @Test
    public void mediaOnLinkTag_screen() {
        final String[] messages = {"none", "1"};
        mediaOnLinkTag("screen", messages);
    }

    @Test
    public void mediaOnLinkTag_notScreen() {
        final String[] messages = {"block", "1"};
        mediaOnLinkTag("print", messages);
    }

    @Test
    public void mediaOnLinkTag_multipleWithScreen() {
        final String[] messages = {"none", "1"};
        mediaOnLinkTag("print, screen, tv", messages);
    }

    @Test
    public void mediaOnLinkTag_multipleWithoutScreen() {
        final String[] messages = {"block", "1"};
        mediaOnLinkTag("print, projection, tv", messages);
    }

    private void mediaOnLinkTag(final String media, final String[] messages) {
        final String html
                = "<html><head>\n"
                + "<link rel='stylesheet' media='" + media + "' href='" + URL_SECOND + "'></link>\n"
                + "</head><body>\n"
                + "<div id='d'>hello</div>\n"
                + "<script>\n"
                + "  var getStyle = function(e) {\n"
                + "    return window.getComputedStyle(e, '');\n"
                + "  }\n"
                + "  alert(getStyle(document.getElementById('d')).display);\n"
                + "  alert(document.styleSheets.length);\n"
                + "</script></body></html>";

        checkHtmlAlert(html, messages);
    }


    @Test
    public void mediaRule_screen() {
        final String[] messages = {"none", "1"};
        mediaRule("screen", messages);
    }

    @Test
    public void mediaRule_notScreen() {
        final String[] messages = {"block", "1"};
        mediaRule("print", messages);
    }

    @Test
    public void mediaRule_multipleWithScreen() {
        final String[] messages = {"none", "1"};
        mediaRule("print, screen, tv", messages);
    }


    @Test
    public void mediaRule_multipleWithoutScreen() {
        final String[] messages = {"block", "1"};
        mediaRule("print, projection, tv", messages);
    }


    @Test
    public void mediaRule_max_width() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-width: 123px)", messages);
    }

    @Test
    public void mediaRule_max_width_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (max-width: 10000px)", messages);
    }


    @Test
    public void mediaRule_max_width_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-width: 5kilo)", messages);
    }

    @Test
    public void mediaRule_max_width_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-width: 10000)", messages);
    }


    @Test
    public void mediaRule_max_width_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-width)", messages);
        mediaRule("screen and (max-width:)", messages);
    }

    @Test
    public void mediaRule_min_width() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-width: 10000px)", messages);
    }

    @Test
    public void mediaRule_min_width_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (min-width: 123px)", messages);
    }

    @Test
    public void mediaRule_min_width_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-width: 5kilo)", messages);
    }

    @Test
    public void mediaRule_min_width_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-width: 123)", messages);
    }

    @Test
    public void mediaRule_min_width_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-width)", messages);
        mediaRule("screen and (min-width:)", messages);
    }

    @Test
    public void mediaRule_max_device_width() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-device-width: 123px)", messages);
    }

    @Test
    public void mediaRule_max_device_width_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (max-device-width: 10000px)", messages);
    }

    @Test
    public void mediaRule_max_device_width_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-device-width: 5kilo)", messages);
    }

    @Test
    public void mediaRule_max_device_width_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-device-width: 10000)", messages);
    }

    @Test
    public void mediaRule_max_device_width_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-device-width)", messages);
        mediaRule("screen and (max-device-width:)", messages);
    }

    @Test
    public void mediaRule_min_device_width() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-device-width: 10000px)", messages);
    }

    @Test
    public void mediaRule_min_device_width_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (min-device-width: 123px)", messages);
    }

    @Test
    public void mediaRule_min_device_width_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-device-width: 5kilo)", messages);
    }

    @Test
    public void mediaRule_min_device_width_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-device-width: 123)", messages);
    }

    @Test
    public void mediaRule_min_device_width_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-device-width)", messages);
        mediaRule("screen and (min-device-width:)", messages);
    }

    @Test
    public void mediaRule_max_height() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-height: 123px)", messages);
    }

    @Test
    public void mediaRule_max_height_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (max-height: 10000px)", messages);
    }

    @Test
    public void mediaRule_max_height_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-height: 5kilo)", messages);
    }

    @Test
    public void mediaRule_max_height_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-height: 10000)", messages);
    }

    @Test
    public void mediaRule_max_height_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-height)", messages);
        mediaRule("screen and (max-height:)", messages);
    }

    @Test
    public void mediaRule_min_height() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-height: 10000px)", messages);
    }

    @Test
    public void mediaRule_min_height_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (min-height: 123px)", messages);
    }

    @Test
    public void mediaRule_min_height_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-height: 5kilo)", messages);
    }

    @Test
    public void mediaRule_min_height_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-height: 123)", messages);
    }

    @Test
    public void mediaRule_min_height_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-height)", messages);
        mediaRule("screen and (min-height:)", messages);
    }

    @Test
    public void mediaRule_max_device_height() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-device-height: 123px)", messages);
    }

    @Test
    public void mediaRule_max_device_height_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (max-device-height: 10000px)", messages);
    }

    @Test
    public void mediaRule_max_device_height_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-device-height: 5kilo)", messages);
    }


    @Test
    public void mediaRule_max_device_height_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-device-height: 10000)", messages);
    }

    @Test
    public void mediaRule_max_device_height_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-device-height)", messages);
        mediaRule("screen and (max-device-height:)", messages);
    }


    @Test
    public void mediaRule_min_device_height() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-device-height: 10000px)", messages);
    }


    @Test
    public void mediaRule_min_device_height_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (min-device-height: 123px)", messages);
    }

    @Test
    public void mediaRule_min_device_height_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-device-height: 5kilo)", messages);
    }

    @Test
    public void mediaRule_min_device_height_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-device-height: 123)", messages);
    }

    @Test
    public void mediaRule_min_device_height_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-device-height)", messages);
        mediaRule("screen and (min-device-height:)", messages);
    }

    @Test
    public void mediaRule_resolution() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (resolution: 4dpi)", messages);
    }

    @Test
    public void mediaRule_resolution_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (resolution: 96dpi)", messages);
    }

    @Test
    public void mediaRule_resolution_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (resolution: 5kilo)", messages);
    }


    @Test
    public void mediaRule_resolution_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (resolution: 96)", messages);
    }

    @Test
    public void mediaRule_resolution_without_value() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (resolution)", messages);
    }

    @Test
    public void mediaRule_resolution_without_value_empty() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (resolution:)", messages);
    }

    @Test
    public void mediaRule_max_resolution() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-resolution: 90dpi)", messages);
    }

    @Test
    public void mediaRule_max_resolution_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (max-resolution: 10000dpi)", messages);
    }


    @Test
    public void mediaRule_max_resolution_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-resolution: 5kilo)", messages);
    }

    @Test
    public void mediaRule_max_resolution_without_unit() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-resolution: 10000)", messages);
    }

    @Test
    public void mediaRule_max_resolution_without_value() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (max-resolution)", messages);
        mediaRule("screen and (max-resolution:)", messages);
    }

    @Test
    public void mediaRule_min_resolution() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-resolution: 10000dpi)", messages);
    }

    @Test
    public void mediaRule_min_resolution_match() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (min-resolution: 10dpi)", messages);
    }

    @Test
    public void mediaRule_min_resolution_invalid() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (min-resolution: 5kilo)", messages);
    }

    @Test
    public void mediaRule_min_resolution_without_unit() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (min-resolution: 10)", messages);
    }

    @Test
    public void mediaRule_min_resolution_without_value() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (min-resolution)", messages);
        mediaRule("screen and (min-resolution:)", messages);
    }


    @Test
    public void mediaRule_portrait() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (orientation: portrait)", messages);
    }

    @Test
    public void mediaRule_portrait_not() {
        final String[] messages = {"none", "1"};
        mediaRule("not screen and (orientation: portrait)", messages);
    }

    @Test
    public void mediaRule_landscape() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (orientation: landscape)", messages);
    }


    @Test
    public void mediaRule_landscape_not() {
        final String[] messages = {"block", "1"};
        mediaRule("not screen and (orientation: landscape)", messages);
    }


    @Test
    public void mediaRule_invalidOrientation() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (orientation: unknown)", messages);
    }

    @Test
    public void mediaRule_orientation_without_value() {
        final String[] messages = {"none", "1"};
        mediaRule("screen and (orientation)", messages);
    }


    @Test
    public void mediaRule_orientation_without_value_empty() {
        final String[] messages = {"block", "1"};
        mediaRule("screen and (orientation:)", messages);
    }

    private void mediaRule(final String media, final String[] messages) {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <style> @media " + media + " { div { display: none } }</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <div id='d'>hello</div>\n"
                + "  <script>\n"
                + "    var getStyle = function(e) {\n"
                + "      return window.getComputedStyle(e, '');\n"
                + "    }\n"
                + "    alert(getStyle(document.getElementById('d')).display);\n"
                + "    alert(document.styleSheets.length);\n"
                + "  </script>\n"
                + "</body></html>";
        checkHtmlAlert(html, messages);
    }

    @Test
    public void insertRuleWithoutGetRules() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  var s = f.sheet ? f.sheet : f.styleSheet;\n"
                + "  try {\n"
                + "    if (s.insertRule) {\n"
                + "      s.insertRule('.testStyle1 { color: red; }', 0);\n"
                + "    } else {\n"
                + "      s.addRule('.testStyle1', 'color: red;', 0);\n"
                + "    }\n"
                + "    alert('inserted');\n"
                + "  } catch(err) { alert('exception'); }\n"
                + "}</script>\n"
                + "<style id='myStyle'></style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";
        final String[] messages = {"inserted"};
        checkHtmlAlert(html, messages);
    }
}
