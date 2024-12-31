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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Node;

import static org.loboevolution.xml.XMLDocumentTest.*;

/**
 * Tests for {@link Node}.
 */
@ExtendWith(AlertsExtension.class)
public class NodeTest extends LoboUnitTest {

    @Test
    @Alerts({"[object HTMLSpanElement]", "[object Text]", "null"})
    public void lastChild() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + " alert(document.getElementById('myNode').lastChild);\n"
                + " alert(document.getElementById('onlyTextNode').lastChild);\n"
                + " alert(document.getElementById('emptyNode').lastChild);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'>hello world<span>Child Node</span></div>\n"
                + "  <div id='onlyTextNode'>hello</div>\n"
                + "  <div id='emptyNode'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void hasChildNodesTrue() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + " alert(document.getElementById('myNode').hasChildNodes());\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<p id='myNode'>hello world<span>Child Node</span></p>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("false")
    public void hasChildNodes_false() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + " alert(document.getElementById('myNode').hasChildNodes());\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<p id='myNode'></p>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "function", "3"})
    public void remove() {
        final String html = "<html><body>\n"
                + "<div id='div1'></div>\n"
                + "<script>\n"
                + "var div1 = document.getElementById('div1');\n"
                + "try {\n"
                + " alert(document.body.childNodes.length);\n"
                + " alert(typeof div1.remove);\n"
                + "  div1.remove();\n"
                + " alert(document.body.childNodes.length);\n"
                + "}\n"
                + "catch (e) {alert('exception'); }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void removeChild() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var form = document.forms['form1'];\n"
                + "  var div = form.firstChild;\n"
                + "  var removedDiv = form.removeChild(div);\n"
                + " alert(div==removedDiv);\n"
                + " alert(form.firstChild == null);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'><div id='formChild'/></form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void removeChildSibling() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  var div2 = document.getElementById('div2');\n"
                + "  try {\n"
                + "    div1.removeChild(div2);\n"
                + "  } catch(e) {alert('exception') }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='div1'></div>\n"
                + "  <div id='div2'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true"})
    public void replaceChild_Normal() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var form = document.forms['form1'];\n"
                + "  var div1 = form.firstChild;\n"
                + "  var div2 = document.getElementById('newChild');\n"
                + "  var removedDiv = form.replaceChild(div2,div1);\n"
                + " alert(div1 == removedDiv);\n"
                + " alert(form.firstChild == div2);\n"
                + "  var newDiv = document.createElement('div');\n"
                + "  form.replaceChild(newDiv, div2);\n"
                + " alert(form.firstChild == newDiv);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'><div id='formChild'/></form>\n"
                + "</body><div id='newChild'/></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("DIV")
    public void nodeNameIsUppercase() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + " alert(document.getElementById('myNode').nodeName);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<div id='myNode'>hello world<span>Child Node</span></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "SPAN", "2", "#text", "H1", "H2"})
    public void getChildNodes() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var aNode = document.getElementById('myNode');\n"
                + " alert(aNode.childNodes.length);\n"
                + " alert(aNode.childNodes[0].nodeName);\n"
                + " alert(aNode.childNodes[0].childNodes.length);\n"
                + " alert(aNode.childNodes[0].childNodes[0].nodeName);\n"
                + " alert(aNode.childNodes[0].childNodes[1].nodeName);\n"
                + " alert(aNode.childNodes[1].nodeName);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<div id='myNode'><span>Child Node 1-A"
                + "<h1>Child Node 1-B</h1></span>"
                + "<h2>Child Node 2-A</h2></div>"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"nb nodes: 2", "8", "1"})
    public void childNodesComments() {
        final String html = "<html><head>\n"
                + "</head>\n"
                + "<body><!-- comment --><script>\n"
                + "var nodes = document.body.childNodes;\n"
                + "alert('nb nodes: ' + nodes.length);\n"
                + "for (var i = 0; i < nodes.length; i++)\n"
                + " alert(nodes[i].nodeType);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"length: 5",
            "tempNode.name: undefined", "tempNode.name: input1", "tempNode.name: undefined",
            "tempNode.name: input2", "tempNode.name: undefined"})
    public void getChildNodesProperties() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var testForm = document.getElementById('testForm');\n"
                + "  var childNodes = testForm.childNodes;\n"
                + "  var length = childNodes.length;\n"
                + " alert('length: ' + length);\n"
                + "  for (var i = 0; i < length; i++) {\n"
                + "    var tempNode = childNodes.item(i);\n"
                + "   alert('tempNode.name: ' + tempNode.name);\n"
                + "  }\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<form name='testForm' id='testForm'>foo\n" // some text, because IE doesn't see "\n" as a text node here
                + "<input type='hidden' name='input1' value='1'>\n"
                + "<input type='hidden' name='input2' value='2'>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"document: 9", "document.body: 1", "body child 1: 3", "body child 2: 8"})
    public void nodeType() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + " alert('document: ' + document.nodeType);\n"
                + " alert('document.body: ' + document.body.nodeType);\n"
                + " alert('body child 1: ' + document.body.childNodes[0].nodeType);\n"
                + " alert('body child 2: ' + document.body.childNodes[1].nodeType);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "some text<!-- some comment -->\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void attachEvent() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var oField = document.getElementById('div1');\n"
                + "  try {\n"
                + "    oField.attachEvent('onclick', foo1);\n"
                + "    oField.attachEvent('onclick', foo2);\n"
                + "  } catch(e) {alert('exception') }\n"
                + "}\n"
                + "function foo1() {alert('in foo1');}\n"
                + "function foo2() {alert('in foo2');}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='div1'>bla</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false", "true", "false", "false", "false"})
    public void isEqualNode() {
        final String html =
                "<html><head><script>\n"
                + "  function test() {\n"
                + "    var list1 = document.getElementById('list1');\n"
                + "    var list2 = document.getElementById('list2');\n"
                + "   alert(list1.isEqualNode(list2));\n"
                + "\n"
                + "    var nodes = document.getElementsByClassName('test');\n"
                + "   alert(nodes[0].isEqualNode(nodes[1]));\n"
                + "   alert(nodes[0].isEqualNode(nodes[2]));\n"
                + "   alert(nodes[0].isEqualNode(nodes[3]));\n"
                + "   alert(nodes[0].isEqualNode(nodes[4]));\n"
                + "   alert(nodes[0].isEqualNode(nodes[5]));"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <ul id='list1'>\n"
                + "    <li>foo</li>\n"
                + "    <li>bar</li>\n"
                + "  </ul>\n"
                + "  <ul id='list2'>\n"
                + "    <li>foo</li>\n"
                + "    <li>bar</li>\n"
                + "  </ul>\n"
                + "\n"
                + "  <ul class='test'>\n"
                + "    <li>foo</li>\n"
                + "    <li>bar</li>\n"
                + "  </ul>\n"
                + "  <ul class='test'>\n"
                + "    <li>foo</li>     \n"
                + "    <li>bar</li>\n"
                + "  </ul>\n"
                + "  <ul class='test'>\n"
                + "    <li>foo</li>\n"
                + "    <li>bar</li>\n"
                + "  </ul>\n"
                + "  <ul class='test'>\n"
                + "    <li>foo</li>\n"
                + "\n"
                + "    <li>bar</li>\n"
                + "  </ul>\n"
                + "  <ul class='test'>\n"
                + "    <li>foo</li>\n"
                + "    <li>bar</li>\n"
                + "    <li></li>\n"
                + "  </ul>\n"
                + "  <ul class='test'>\n"
                + "    <li>foobar</li>\n"
                + "    <li></li>\n"
                + "  </ul>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "false"})
    public void isSameNode() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var d1 = document.getElementById('div1');\n"
                + "    var d2 = document.getElementById('div2');\n"
                + "    try {\n"
                + "     alert(d1.isSameNode(d1));\n"
                + "     alert(d1.isSameNode(d2));\n"
                + "    } catch(e) {\n"
                + "     alert('isSameNode not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='div1'/>\n"
                + "<div id='div2'/>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"null", "null"})
    public void appendChildParentNode() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.createElement('div');\n"
                + "    var div2 = document.createElement('div');\n"
                + "   alert(div1.parentNode);\n"
                + "    div1.appendChild(div2);\n"
                + "    if(div1.parentNode)\n"
                + "     alert(div1.parentNode.nodeName);\n"
                + "    else\n"
                + "     alert(div1.parentNode);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

 
    @Test
    @Alerts({"1", "exception", "1", "exception", "1", "exception", "1"})
    public void appendInsertHtmlNode() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  var htmlNode = document.documentElement;\n"
                + "  var body = document.body;\n"
                + " alert(body.childNodes.length);\n"
                + "  try { body.appendChild(htmlNode); } catch(e) {alert('exception'); }\n"
                + " alert(body.childNodes.length);\n"
                + "  try { body.insertBefore(htmlNode, body.firstChild); } catch(e) {alert('exception'); }\n"
                + " alert(body.childNodes.length);\n"
                + "  try { body.replaceChild(htmlNode, body.firstChild); } catch(e) {alert('exception'); }\n"
                + " alert(body.childNodes.length);\n"
                + "}\n"
                + "</script></head><body onload='test()'><span>hi</span></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("2")
    public void appendChildOfDocumentFragment() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var fragment = document.createDocumentFragment();\n"
                + "    var div1 = document.createElement('div');\n"
                + "    div1.id = 'div1';\n"
                + "    var div2 = document.createElement('div');\n"
                + "    div2.id = 'div2';\n"
                + "    fragment.appendChild(div1);\n"
                + "    fragment.appendChild(div2);\n"
                + "    var div = document.getElementById('myDiv');\n"
                + "    div.appendChild(fragment);\n"
                + "   alert(div.childNodes.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='myDiv'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "3", "3", "3", "3", "3", "3", "3"})
    public void nodePrototype() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert(document.body.TEXT_NODE);\n"
                + "     alert(Node.TEXT_NODE);\n"
                + "      document.body.TEXT_NODE = 123;\n"
                + "     alert(document.body.TEXT_NODE);\n"
                + "     alert(Node.TEXT_NODE);\n"
                + "      Node.TEXT_NODE = 456;\n"
                + "     alert(document.body.TEXT_NODE);\n"
                + "     alert(Node.TEXT_NODE);\n"
                + "      delete Node.TEXT_NODE;\n"
                + "      delete document.body.TEXT_NODE;\n"
                + "     alert(document.body.TEXT_NODE);\n"
                + "     alert(Node.TEXT_NODE);\n"
                + "    } catch(e) {\n"
                + "     alert('not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<div id=\"myDiv2\"></div><div id=\"myDiv3\"></div>", "myDiv2",
            "<div>one</div><div>two</div><div id=\"myDiv3\"></div>"})
    public void replaceChild() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var element = document.getElementById('myDiv2');\n"
                + "      var range = element.ownerDocument.createRange();\n"
                + "      range.setStartAfter(element);\n"
                + "      var fragment = range.createContextualFragment('<div>one</div><div>two</div>');\n"
                + "      var parent = element.parentNode;\n"
                + "     alert(parent.innerHTML);\n"
                + "     alert(parent.replaceChild(fragment, parent.firstChild).id);\n"
                + "     alert(parent.innerHTML);\n"
                + "    } catch(e) {\n"
                + "     alert('exception thrown');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'><div id='myDiv2'></div><div id='myDiv3'></div></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<div id=\"myDiv2\"></div><div id=\"myDiv3\"></div>", "myDiv2",
            "<div id=\"myDiv3\"></div>"})
    public void replaceChild_EmptyDocumentFragment() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var element = document.getElementById('myDiv2');\n"
                + "    try {\n"
                + "      var range = element.ownerDocument.createRange();\n"
                + "      range.setStartAfter(element);\n"
                + "      var fragment = range.createContextualFragment('');\n"
                + "      var parent = element.parentNode;\n"
                + "     alert(parent.innerHTML);\n"
                + "     alert(parent.replaceChild(fragment, parent.firstChild).id);\n"
                + "     alert(parent.innerHTML);\n"
                + "    } catch(e) {\n"
                + "     alert('exception thrown');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'><div id='myDiv2'></div><div id='myDiv3'></div></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("in click")
    public void cloneNodeCopiesListenerOnlyForIE() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <script>\n"
                        + "    function go() {\n"
                        + "      var node = document.createElement('button');\n"
                        + "      var f = function() {alert('in click') };\n"
                        + "      if (node.attachEvent)\n"
                        + "        node.attachEvent('onclick', f);\n"
                        + "      else\n"
                        + "        node.addEventListener('click', f, true);\n"
                        + "      document.body.appendChild(node);\n"
                        + "      node.click();\n"
                        + "      var clone = node.cloneNode(true);\n"
                        + "      document.body.appendChild(clone);\n"
                        + "      clone.click();\n"
                        + "      var div = document.createElement('div');\n"
                        + "      div.appendChild(node);\n"
                        + "      var cloneDiv = div.cloneNode(true);\n"
                        + "      document.body.appendChild(cloneDiv);\n"
                        + "      cloneDiv.firstChild.click();\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='go()'>\n"
                        + "    <div id='foo'></div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "1", "2", "4", "8", "16", "32"})
    public void documentPositionConstants() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert(document.body.DOCUMENT_POSITION_DISCONNECTED);\n"
                + "     alert(Node.DOCUMENT_POSITION_DISCONNECTED);\n"
                + "     alert(Node.DOCUMENT_POSITION_PRECEDING);\n"
                + "     alert(Node.DOCUMENT_POSITION_FOLLOWING);\n"
                + "     alert(Node.DOCUMENT_POSITION_CONTAINS);\n"
                + "     alert(Node.DOCUMENT_POSITION_CONTAINED_BY);\n"
                + "     alert(Node.DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC);\n"
                + "    } catch(e) {\n"
                + "     alert('not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "20", "20", "4", "10", "10", "2", "20", "exception"})
    public void compareDocumentPosition() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  var div2 = document.getElementById('div2');\n"
                + "  var div3 = document.getElementById('div3');\n"
                + "  if (!div1.compareDocumentPosition) {alert('compareDocumentPosition not available'); return }\n"
                + " alert(div1.compareDocumentPosition(div1));\n"
                + " alert(div1.compareDocumentPosition(div2));\n"
                + " alert(div1.compareDocumentPosition(div3));\n"
                + " alert(div1.compareDocumentPosition(div4));\n"
                + " alert(div2.compareDocumentPosition(div1));\n"
                + " alert(div3.compareDocumentPosition(div1));\n"
                + " alert(div4.compareDocumentPosition(div1));\n"
                + " alert(div2.compareDocumentPosition(div3));\n"
                + "  try {\n"
                + "   alert(div2.compareDocumentPosition({}));\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='div1'>\n"
                + "  <div id='div2'>\n"
                + "    <div id='div3'>\n"
                + "    </div>\n"
                + "  </div>\n"
                + "</div>\n"
                + "<div id='div4'>\n"
                + "</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "16"})
    public void compareDocumentPosition2() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    var childDiv = document.createElement('div');\n"
                + "    try {\n"
                + "     alert(div.compareDocumentPosition(childDiv) & Node.DOCUMENT_POSITION_CONTAINED_BY);\n"
                + "      div.appendChild(childDiv);\n"
                + "     alert(div.compareDocumentPosition(childDiv) & Node.DOCUMENT_POSITION_CONTAINED_BY);\n"
                + "    } catch(e) {alert('exception');}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("bk")
    public void prefix() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  var text = \"<bk:book xmlns:bk='urn:loc.gov:books'></bk:book>\";\n"
                + "  var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + " alert(doc.documentElement.prefix);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<root><![CDATA[abc]]><![CDATA[def]]></root>")
    public void xml() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var root = doc.appendChild(doc.createElement('root'));\n"
                + "    var cdata = root.appendChild(doc.createCDATASection('abcdef'));\n"
                + "    cdata.splitText(3);\n"
                + "   alert(" + callSerializeXMLDocumentToString("doc") + ");\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "SPAN"})
    public void insertBeforenullRef() {
        insertBefore("aNode.insertBefore(newNode, null);");
    }

    @Test
    @Alerts("exception")
    public void insertBeforemyself() {
        insertBefore("aNode.insertBefore(newNode, newNode);");
    }

    @Test
    @Alerts("exception")
    public void insertBeforesibling() {
        insertBefore("aNode.insertBefore(newNode, siblingNode);");
    }

    @Test
    @Alerts("done")
    public void insertBeforeundefinedRef() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  try {\n"
                + "    var e = document.createElement('div');\n"
                + "    e.innerHTML = 'new element';\n"
                + "    document.body.insertBefore(e, undefined);\n"
                + "   alert('done');"
                + "  } catch(e) {alert('exception');}\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void insertBeforenoArgs() {
        insertBefore("aNode.insertBefore();");
    }

    @Test
    @Alerts("exception")
    public void insertBeforenoSecondArg() {
        insertBefore("aNode.insertBefore(newNode);");
    }


    private void insertBefore(final String insertJSLine) {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      var newNode = document.createElement('span');\n"
                + "      var siblingNode = document.getElementById('sibingNode');\n"
                + "      var aNode = document.getElementById('myNode');\n"
                + "      try {\n"
                + insertJSLine
                + "       alert(aNode.childNodes.length);\n"
                + "       alert(aNode.childNodes[2].nodeName);\n"
                + "      }\n"
                + "      catch (e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='myNode'><span>Child Node 1-A</span><h1>Child Node 2-A</h1></div>\n"
                + "<h2 id='sibingNode'>Sibling</h2>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "SPAN"})
    public void insertBeforeFragmentNullRef() {
        insertBeforeFragment("aNode.insertBefore(fragment, null);");
    }

    @Test
    @Alerts("exception")
    public void insertBeforeFragmentMyself() {
        insertBeforeFragment("aNode.insertBefore(fragment, fragment);");
    }

    @Test
    @Alerts("exception")
    public void insertBeforeFragmentSibling() {
        insertBeforeFragment("aNode.insertBefore(fragment, siblingNode);");
    }

    @Test
    @Alerts("exception")
    public void insertBeforeFragmentNoSecondArg() {
        insertBeforeFragment("aNode.insertBefore(fragment);");
    }


    private void insertBeforeFragment(final String insertJSLine) {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      var fragment = document.createDocumentFragment('span');\n"
                + "      fragment.appendChild(document.createElement('span'));\n"
                + "      var aNode = document.getElementById('myNode');\n"
                + "      try {\n"
                + insertJSLine
                + "       alert(aNode.childNodes.length);\n"
                + "       alert(aNode.childNodes[2].nodeName);\n"
                + "      }\n"
                + "      catch (e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='myNode'><h6>Child Node 1-A</h6><h1>Child Node 2-A</h1></div>\n"
                + "<h2 id='sibingNode'>Sibling</h2>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null"})
    public void insertBeforeparentNode() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div1 = document.createElement('div');\n"
                + "    var div2 = document.createElement('div');\n"
                + "   alert(div1.parentNode);\n"
                + "    div1.insertBefore(div2, null);\n"
                + "    if(div1.parentNode)\n"
                + "     alert(div1.parentNode.nodeName);\n"
                + "    else\n"
                + "     alert(div1.parentNode);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLTableColElement]")
    public void insertBeforeinTable() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var table = document.getElementById('myTable');\n"
                + "  var colGroup = table.insertBefore(document.createElement('colgroup'), null);\n"
                + " alert(colGroup);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <table id='myTable'></table>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void insertBeforenewElement() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  try {\n"
                + "    var e = document.createElement('div');\n"
                + "    e.innerHTML = 'new element';\n"
                + "    document.body.insertBefore(e);\n"
                + "  } catch(e) {alert('exception');}\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "3", "abc", "def", "123456", "true", "0", "2", "123", "456", "1", "true"})
    public void normalize() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var root = doc.appendChild(doc.createElement('root'));\n"
                + "    var cdata = root.appendChild(doc.createCDATASection('abcdef'));\n"
                + "    cdata.splitText(3);\n"
                + "    var text = root.appendChild(doc.createTextNode('123456'));\n"
                + "    text.splitText(3);\n"
                + "   alert(root.childNodes.length);\n"
                + "    root.normalize();\n"
                + "   alert(root.childNodes.length);\n"
                + "   alert(root.childNodes.item(0).data);\n"
                + "   alert(root.childNodes.item(1).data);\n"
                + "   alert(root.childNodes.item(2).data);\n"
                + "   alert(root.childNodes.item(2) == text);\n"
                + "\n"
                + "    var body = document.body;\n"
                + "   alert(body.childNodes.length);\n"
                + "    text = body.appendChild(document.createTextNode('123456'));\n"
                + "    text.splitText(3);\n"
                + "   alert(body.childNodes.length);\n"
                + "   alert(body.childNodes.item(0).nodeValue);\n"
                + "   alert(body.childNodes.item(1).nodeValue);\n"
                + "    body.normalize();\n"
                + "   alert(body.childNodes.length);\n"
                + "   alert(body.childNodes.item(0) == text);\n"
                + "  }\n"
                + "</script></head><body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Element]", "[object HTMLHtmlElement]"})
    public void parentElement() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  var text = '<hello>hi</hello>';\n"
                + "  var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + " alert(doc.documentElement.firstChild.parentElement);\n"
                + " alert(document.body.parentElement);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"hi", "undefined", "abcd", "undefined"})
    public void attributes() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  var text = '<hello>hi</hello>';\n"
                + "  var doc = " + callLoadXMLDocumentFromString("text") + ";\n"
                + "  var node = doc.documentElement.firstChild;\n"
                + " alert(node.nodeValue);\n"
                + " alert(node.attributes);\n"
                + "\n"
                + "  node = document.getElementById('myId').firstChild;\n"
                + " alert(node.nodeValue);\n"
                + " alert(node.attributes);\n"
                + "}\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myId'>abcd</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void addEventListener() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var node = document.createElement('button');\n"
                        + "     alert(node.addEventListener !== undefined);\n"
                        + "     alert(node.removeEventListener !== undefined);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void event() {
        final String html = "<html>\n"
                + "<head><title>First Page</title>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var iframe = document.createElement('iframe');\n"
                + "    document.body.appendChild(iframe);\n"
                + "    iframe.contentWindow.location.replace('" + URL_SECOND + "');\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <input type='button' id='myInput' value='Test me'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("id=bar")
    public void cloneAttributesAvailable() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <script>\n"

                        + "    function go() {\n"
                        + "      var node = document.getElementById('foo');\n"
                        + "      var clone = node.cloneNode(true);\n"
                        + "      clone.id = 'bar';\n"
                        + "      node.appendChild(clone);\n"
                        + "     alert(clone.attributes['id'].nodeName + '=' + clone.attributes['id'].nodeValue);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='go()'>\n"
                        + "    <div id='foo'></div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Hello")
    public void setTextContent() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var foo = document.getElementById('foo');\n"
                        + "      foo.textContent = 'Hello';\n"
                        + "      if (foo.firstChild) {\n"
                        + "       alert(foo.firstChild.wholeText);\n"
                        + "      }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <span id='foo'></span>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void cloneParent() {
        final String html =
                "<!DOCTYPE><html>\n"
                        + "  <head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var foo = document.getElementById('foo');\n"
                        + "      var clone = foo.cloneNode(true);\n"
                        + "     alert(clone.parentNode);\n"
                        + "      if (clone.parentNode) {\n"
                        + "       alert(clone.parentNode.URL);\n"
                        + "       alert(clone.parentNode.nodeType);\n"
                        + "      }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <span id='foo'></span>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<div><span></span>nullundefinedhello<p></p></div>")
    public void before() {
        final String html =
                "<html><head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var parent = document.createElement('div');\n"
                        + "      var child = document.createElement('p');\n"
                        + "      parent.appendChild(child);\n"
                        + "      var span = document.createElement('span');\n"
                        + "      if (child.before) {"
                        + "        child.before(span, null, undefined, 'hello');\n"
                        + "      }\n"
                        + "     alert(parent.outerHTML);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<div><p></p><span></span>nullundefinedhello</div>")
    public void after() {
        final String html =
                "<html><head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var parent = document.createElement('div');\n"
                        + "      var child = document.createElement('p');\n"
                        + "      parent.appendChild(child);\n"
                        + "      var span = document.createElement('span');\n"
                        + "      if (child.after) {"
                        + "        child.after(span, null, undefined, 'hello');\n"
                        + "      }\n"
                        + "     alert(parent.outerHTML);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<div><span></span>nullundefinedhello</div>")
    public void replaceWith() {
        final String html =
                "<html><head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var parent = document.createElement('div');\n"
                        + "      var child = document.createElement('p');\n"
                        + "      parent.appendChild(child);\n"
                        + "      var span = document.createElement('span');\n"
                        + "      if (child.replaceWith) {"
                        + "        child.replaceWith(span, null, undefined, 'hello');\n"
                        + "      }\n"
                        + "     alert(parent.outerHTML);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "second"})
    public void eventListener() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function clicking1() {\n"
                + "   alert(1);\n"
                + "  }\n"
                + "  function clicking2() {\n"
                + "   alert(2);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    var e = document.getElementById('myAnchor');\n"
                + "    e.addEventListener('click', clicking1, false);\n"
                + "    e.addEventListener('click', clicking2, false);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <a href='second' id='myAnchor'>Click me</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "second"})
    public void eventListenerReturn_false() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function clicking1() {\n"
                + "   alert(1);\n"
                + "  }\n"
                + "  function clicking2() {\n"
                + "   alert(2);\n"
                + "    return false;\n"
                + "  }\n"
                + "  function test() {\n"
                + "    var e = document.getElementById('myAnchor');\n"
                + "    e.addEventListener('click', clicking1, false);\n"
                + "    e.addEventListener('click', clicking2, false);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='second' id='myAnchor'>Click me</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", ""})
    public void eventListenerReturnValueFalse() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function clicking1() {\n"
                + "   alert(1);\n"
                + "  }\n"
                + "  function clicking2() {\n"
                + "   alert(2);\n"
                + "    if (window.event)\n"
                + "      window.event.returnValue = false;\n"
                + "  }\n"
                + "  function test() {\n"
                + "    var e = document.getElementById('myAnchor');\n"
                + "    e.addEventListener('click', clicking1, false);\n"
                + "    e.addEventListener('click', clicking2, false);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='second' id='myAnchor'>Click me</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
