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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.Document;

/**
 * Tests for {@link Document}.
 */
@ExtendWith(AlertsExtension.class)
public class Document2Test extends LoboUnitTest {


    @Test
    @Alerts("exception")
    public void createElementWithAngleBrackets() {
        final String html = "<html><head>\n"
                + "<script>"
                +  " function test() {\n"
                + "    try {\n"
                + "      var select = document.createElement('<select>');\n"
                + "     alert(select.add == undefined);\n"
                + "    }\n"
                + "    catch (e) {alert('exception') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createElementWithHtml() {
        final String html = "<html><head>\n"
                + "<script>"
                +  " function test() {\n"
                + "    try {\n"
                + "     alert(document.createElement('<div>').tagName);\n"
                + "      var select = document.createElement(\"<select id='mySelect'><option>hello</option>\");\n"
                + "     alert(select.add == undefined);\n"
                + "     alert(select.id);\n"
                + "     alert(select.childNodes.length);\n"
                + "      var option = document.createElement(\"<option id='myOption'>\");\n"
                + "     alert(option.tagName);\n"
                + "     alert(option.id);\n"
                + "     alert(option.childNodes.length);\n"
                + "    }\n"
                + "    catch (e) {alert('exception') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("false")
    public void createElementPrototype() {
        final String html = "<html><head>\n"
                + "<script>\n" 
                + "  var HAS_EXTENDED_CREATE_ELEMENT_SYNTAX = (function() {\n"
                + "    try {\n"
                + "      var el = document.createElement('<input name=\"x\">');\n"
                + "      return el.tagName.toLowerCase() === 'input' && el.name === 'x';\n"
                + "    } catch (err) {\n"
                + "      return false;\n"
                + "    }\n"
                + "  })();\n"
                + " alert(HAS_EXTENDED_CREATE_ELEMENT_SYNTAX);\n"
                + "</script></head><body>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void appendChild() {
        final String html
                = "<html><head>\n"
                + "<script>\n" 
                + "function test() {\n"
                + "  var span = document.createElement('SPAN');\n"
                + "  var div = document.getElementById('d');\n"
                + "  div.appendChild(span);\n"
                + " alert(span === div.childNodes[0]);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='d'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void getElementByTagNameNS_includesHtml() {
        final String html
                = "<html><head>\n"
                + "<script>"
                +  " function doTest() {\n"
                + "   alert(document.getElementsByTagNameNS('*', 'html').length);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <p>hello world</p>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"div1", "null", "2", "1"})
    public void importNodeDeep() {
        importNode(true);
    }

    @Test
    @Alerts({"div1", "null", "0"})
    public void importNodeNotDeep() {
        importNode(false);
    }

    private void importNode(final boolean deep) {
        final String html = "<html><head>\n"
                + "<script>"
                +  " function test() {\n"
                + "    var node = document.importNode(document.getElementById('div1'), " + deep + ");\n"
                + "   alert(node.id);\n"
                + "   alert(node.parentNode);\n"
                + "   alert(node.childNodes.length);\n"
                + "    if (node.childNodes.length != 0)\n"
                + "     alert(node.childNodes[0].childNodes.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'><div id='div1_1'><div id='div1_1_1'></div></div><div id='div1_2'></div></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"parent", "child"})
    public void importNodeWithNamespace() {
        final String html = "<html xmlns='http://www.w3.org/1999/xhtml'>\n"
                + "<head><script>\n" 
                + "function test() {\n"
                + "  if (!document.evaluate) {alert('evaluate not available'); return; }\n"
                + "  var xmlhttp = new XMLHttpRequest();\n"
                +  " xmlhttp.open('GET', '" + URL_HTML + "content.xhtml" + "', true);\n"
                + "  xmlhttp.send();\n"
                + "  xmlhttp.onreadystatechange = function() {\n"
                + "    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n"
                + "      var child = document.importNode(xmlhttp.responseXML.getElementById(\"child\"), true);\n"
                + "      document.getElementById(\"parent\").appendChild(child);\n"
                + "      var found = document.evaluate(\"//div[@id='parent']\", document, null,"
                + "XPathResult.FIRST_ORDERED_NODE_TYPE, null);\n"
                + "     alert(found.singleNodeValue.id);\n"
                + "      found = document.evaluate(\"//div[@id='child']\", document, null,"
                + "XPathResult.FIRST_ORDERED_NODE_TYPE, null);\n"
                + "     alert(found.singleNodeValue.id);\n"
                + "    }\n"
                + " }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='parent'></div>\n"
                + "</body></html>\n";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"parent", "child", "child3"})
    public void importNodesWithNamespace() {
        final String html = "<html xmlns='http://www.w3.org/1999/xhtml'>\n"
                + "<head><script>\n" 
                + "function test() {\n"
                + "  if (!document.evaluate) {alert('evaluate not available'); return; }\n"
                + "  var xmlhttp = new XMLHttpRequest();\n"
                +  " xmlhttp.open('GET', '" + URL_HTML + "content.xhtml" + "', true);\n"
                + "  xmlhttp.send();\n"
                + "  xmlhttp.onreadystatechange = function() {\n"
                + "    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n"
                + "      var child = document.importNode(xmlhttp.responseXML.getElementById(\"child\"), true);\n"
                + "      document.getElementById(\"parent\").appendChild(child);\n"
                + "      var found = document.evaluate(\"//div[@id='parent']\", document, null,"
                + "XPathResult.FIRST_ORDERED_NODE_TYPE, null);\n"
                + "     alert(found.singleNodeValue.id);\n"
                + "      found = document.evaluate(\"//div[@id='child']\", document, null,"
                + "XPathResult.FIRST_ORDERED_NODE_TYPE, null);\n"
                + "     alert(found.singleNodeValue.id);\n"
                + "      found = document.evaluate(\"//div[@id='child3']\", document, null,"
                + "XPathResult.FIRST_ORDERED_NODE_TYPE, null);\n"
                + "     alert(found.singleNodeValue.id);\n"
                + "    }\n"
                + "  }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='parent'></div>\n"
                + "</body></html>\n";

       checkHtmlAlert(html);
    }

    @Test
    @Alerts({"div1", "null", "null"})
    public void adoptNode() {
        final String html = "<html><head><script>"
                +  " function test() {\n"
                + "    var newDoc = document.implementation.createHTMLDocument('something');\n"
                + "    var node = newDoc.adoptNode(document.getElementById('div1'));\n"
                + "   alert(node.id);\n"
                + "   alert(node.parentNode);\n"
                + "   alert(document.getElementById('div1'));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'><div id='div1_1'></div></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "text1"})
    public void activeElement() {
        final String html = "<html><head><script>\n"
                + "  alert(document.activeElement);\n"
                + "  function test() {\n"
                + "    alert(document.activeElement.id);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body>\n"
                + "  <input id='text1' onclick='test()'>\n"
                + "</body></html>";
        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("text1");
        elem.getOnclick();
    }

    @Test
    @Alerts({"<p>a & b</p> &amp; Ţ \" '",
            "<p>a & b</p> &amp; Ţ \" '",
            "<div id=\"div\">&lt;p&gt;a &amp; b&lt;/p&gt; &amp;amp; Ţ \" '</div>",
            "&lt;p&gt;a &amp; b&lt;/p&gt; &amp;amp; Ţ \" '",
            "<p>a & b</p> &amp; Ţ \" '"})
    public void createTextNodeWithHtml() {
        final String html = "<html>\n"
                + "<body onload='test()'>\n"
                + "<script>"
                +  " function test() {\n"
                + "    var node = document.createTextNode('<p>a & b</p> &amp; \\u0162 \" \\'');\n"
                + "   alert(node.data);\n"
                + "   alert(node.nodeValue);\n"
                + "    var div = document.getElementById('div');\n"
                + "    div.appendChild(node);\n"
                + "   alert(div.outerHTML);\n"
                + "   alert(div.innerHTML);\n"
                + "   alert(div.innerText);\n"
                + "  }\n"
                + "</script>\n"
                + "<div id='div'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void queryCommandEnabled() {
        final String html = "<html><body onload='x()'><iframe name='f' id='f'></iframe>\n"
                + "<script>\n" 
                + "function x() {\n"
                + "  var d = window.frames['f'].document;\n"
                + "  try {alert(d.queryCommandEnabled('SelectAll')); } catch(e) {alert('error'); }\n"
                + "  try {alert(d.queryCommandEnabled('sElectaLL')); } catch(e) {alert('error'); }\n"
                + "}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"true", "true", "true"})
    public void queryCommandEnabledDesignMode() {
        final String html = "<html><body onload='x()'><iframe name='f' id='f'></iframe>\n"
                + "<script>\n" 
                + "function x() {\n"
                + "  var d = window.frames['f'].document;\n"
                + "  d.designMode = 'on';\n"
                + " alert(d.queryCommandEnabled('SelectAll'));\n"
                + " alert(d.queryCommandEnabled('selectall'));\n"
                + " alert(d.queryCommandEnabled('SeLeCtALL'));\n"
                + "}\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"bar", "null", "null"})
    public void getElementById() {
        final String html
                = "<html><head>\n"
                + "<script>\n" 
                + "function doTest() {\n"
                + " alert(top.document.getElementById('input1').value);\n"
                + " alert(document.getElementById(''));\n"
                + " alert(document.getElementById('non existing'));\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form id='form1'>\n"
                + "<input id='input1' name='foo' type='text' value='bar' />\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"bar", "null"})
    public void getElementByIdResetId() {
        final String html
                = "<html><head>\n"
                + "<script>\n" 
                + "function doTest() {\n"
                + "  var input1 = top.document.getElementById('input1');\n"
                + "  input1.id = 'newId';\n"
                + " alert(top.document.getElementById('newId').value);\n"
                + " alert(top.document.getElementById('input1'));\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form id='form1'>\n"
                + "<input id='input1' name='foo' type='text' value='bar' />\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("bar")
    public void getElementById_setNewId() {
        final String html
                = "<html><head>\n"
                + "<script>\n" 
                + "function doTest() {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  div1.firstChild.id = 'newId';\n"
                + " alert(document.getElementById('newId').value);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form id='form1'>\n"
                + "<div id='div1'><input name='foo' type='text' value='bar'></div>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id1")
    public void getElementByIdDivId() {
        final String html
                = "<html><head>\n"
                + "<script>\n" 
                + "function doTest() {\n"
                + "  var element = document.getElementById('id1');\n"
                + " alert(element.id);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='id1'></div></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("script1")
    public void getElementById_scriptId() {
        final String html
                = "<html><head>\n"
                + "<script id='script1'>\n" 
                + "function doTest() {\n"
                + " alert(top.document.getElementById('script1').id);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"first", "newest"})
    public void getElementByIdAlwaysFirstOneInDocumentOrder() {
        final String html = "<html><body>\n"
                + "<span id='it' class='first'></span>\n"
                + "<span id='it' class='second'></span>\n"
                + "<script>\n" 
                + "alert(document.getElementById('it').className);\n"
                + "var s = document.createElement('span');\n"
                + "s.id = 'it';\n"
                + "s.className = 'newest';\n"
                + "document.body.insertBefore(s, document.body.firstChild);\n"
                + "alert(document.getElementById('it').className);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void createStyleSheet() {
        final String html
                = "<html><head>\n"
                + "<script>"
                +  " function doTest() {\n"
                + "    if (document.createStyleSheet) {\n"
                + "      document.createStyleSheet('style.css');\n"
                + "      for (var si = 0; si < document.styleSheets.length; si++) {\n"
                + "        var sheet = document.styleSheets[si];\n"
                + "       alert(sheet.href);\n"
                + "       alert(sheet.owningElement.tagName);\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='id1'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void createStyleSheetEmptyUrl() {
        final String html
                = "<html><head>\n" 
                + "<script>\n"
                + "  function doTest() {\n"
                + "    if (document.createStyleSheet) {\n"
                + "      document.createStyleSheet(null);\n"
                + "      document.createStyleSheet('');\n"
                + "      for (var si = 0; si < document.styleSheets.length; si++) {\n"
                + "        var sheet = document.styleSheets[si];\n"
                + "       alert(sheet.href);\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='id1'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void createStyleSheet_insertAt() {
        final String html
                = "<html><head>\n"
                + "<script>"
                +  " function doTest() {\n"
                + "    if (document.createStyleSheet) {\n"
                + "      document.createStyleSheet('minus1.css', -1);\n"
                + "      document.createStyleSheet('zero.css', 0);\n"
                + "      document.createStyleSheet('dotseven.css', 0.7);\n"
                + "      document.createStyleSheet('seven.css', 7);\n"
                + "      document.createStyleSheet('none.css');\n"
                + "      for (var si = 0; si < document.styleSheets.length; si++) {\n"
                + "        var sheet = document.styleSheets[si];\n"
                + "       alert(sheet.href);\n"
                + "      }\n"
                + "    }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='id1'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Initial State:loading", "Changed:interactive", "Changed:complete"})
    public void readyStateEventListener() {
        final String html
                = "<html><head>\n"
                + "<script>\n" 
                + "</script></head>\n"
                + "<body>\n"
                + "<script>\n"
                + "   alert('Initial State:' + document.readyState);\n"
                + "    document.addEventListener('readystatechange', function() {\n"
                + "       alert('Changed:' + document.readyState);\n"
                + "    });\r\n"
                + "</script>"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
