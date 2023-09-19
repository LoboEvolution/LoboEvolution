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

package org.loboevolution.node;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.node.Node}.
 */
public class NodeUnitTest extends LoboUnitTest {

    @Test
    public void lastChild() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('myNode').lastChild);\n"
                + "  alert(document.getElementById('onlyTextNode').lastChild);\n"
                + "  alert(document.getElementById('emptyNode').lastChild);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'>hello world<span>Child Node</span></div>\n"
                + "  <div id='onlyTextNode'>hello</div>\n"
                + "  <div id='emptyNode'></div>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLSpanElement]", "[object Text]", null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void hasChildNodes_true() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('myNode').hasChildNodes());\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<p id='myNode'>hello world<span>Child Node</span></p>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void hasChildNodes_false() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('myNode').hasChildNodes());\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<p id='myNode'></p>\n"
                + "</body></html>";

        final String[] messages = {"false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void remove() {
        final String html = "<html><body>\n"
                + "<div id='div1'></div>\n"
                + "<script>\n"
                + "var div1 = document.getElementById('div1');\n"
                + "try {\n"
                + "  alert(document.body.childNodes.length);\n"
                + "  alert(typeof div1.remove);\n"
                + "  div1.remove();\n"
                + "  alert(document.body.childNodes.length);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script></body></html>";

        final String[] messages = {"2", "function", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void removeChildSibling() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  var div2 = document.getElementById('div2');\n"
                + "  try {\n"
                + "    div1.removeChild(div2);\n"
                + "  } catch(e) { alert('exception') }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='div1'></div>\n"
                + "  <div id='div2'></div>\n"
                + "</body></html>";

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void replaceChild_Normal() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var form = document.forms['form1'];\n"
                + "  var div1 = form.firstChild;\n"
                + "  var div2 = document.getElementById('newChild');\n"
                + "  var removedDiv = form.replaceChild(div2,div1);\n"
                + "  alert(div1 == removedDiv);\n"
                + "  alert(form.firstChild == div2);\n"
                + "  var newDiv = document.createElement('div');\n"
                + "  form.replaceChild(newDiv, div2);\n"
                + "  alert(form.firstChild == newDiv);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'><div id='formChild'/></form>\n"
                + "</body><div id='newChild'/></html>";

        final String[] messages = {"true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void nodeNameIsUppercase() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('myNode').nodeName);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<div id='myNode'>hello world<span>Child Node</span></div>\n"
                + "</body></html>";

        final String[] messages = {"DIV"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getChildNodes() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var aNode = document.getElementById('myNode');\n"
                + "  alert(aNode.childNodes.length);\n"
                + "  alert(aNode.childNodes[0].nodeName);\n"
                + "  alert(aNode.childNodes[0].childNodes.length);\n"
                + "  alert(aNode.childNodes[0].childNodes[0].nodeName);\n"
                + "  alert(aNode.childNodes[0].childNodes[1].nodeName);\n"
                + "  alert(aNode.childNodes[1].nodeName);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<div id='myNode'><span>Child Node 1-A"
                + "<h1>Child Node 1-B</h1></span>"
                + "<h2>Child Node 2-A</h2></div>"
                + "</body></html>";

        final String[] messages = {"2", "SPAN", "2", "#text", "H1", "H2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void childNodes_Comments() {
        final String html = "<html><head>\n"
                + "</head>\n"
                + "<body><!-- comment --><script>\n"
                + "var nodes = document.body.childNodes;\n"
                + "alert('nb nodes: ' + nodes.length);\n"
                + "for (var i = 0; i < nodes.length; i++)\n"
                + "  alert(nodes[i].nodeType);\n"
                + "</script></body></html>";

        final String[] messages = {"nb nodes: 2", "8", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getChildNodesProperties() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var testForm = document.getElementById('testForm');\n"
                + "  var childNodes = testForm.childNodes;\n"
                + "  var length = childNodes.length;\n"
                + "  alert('length: ' + length);\n"
                + "  for (var i = 0; i < length; i++) {\n"
                + "    var tempNode = childNodes.item(i);\n"
                + "    alert('tempNode.name: ' + tempNode.name);\n"
                + "  }\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "<form name='testForm' id='testForm'>foo\n"
                + "<input type='hidden' name='input1' value='1'>foo\n"
                + "<input type='hidden' name='input2' value='2'>foo\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"length: 5",
                "tempNode.name: undefined", "tempNode.name: input1", "tempNode.name: undefined",
                "tempNode.name: input2", "tempNode.name: undefined"};

        checkHtmlAlert(html, messages);
    }

    @Test
    public void nodeType() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  alert('document: ' + document.nodeType);\n"
                + "  alert('document.body: ' + document.body.nodeType);\n"
                + "  alert('body child 1: ' + document.body.childNodes[0].nodeType);\n"
                + "  alert('body child 2: ' + document.body.childNodes[1].nodeType);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "some text<!-- some comment -->\n"
                + "</body></html>";

        final String[] messages = {"document: 9", "document.body: 1", "body child 1: 3", "body child 2: 8"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void isSameNode() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    var d1 = document.getElementById('div1');\n"
                + "    var d2 = document.getElementById('div2');\n"
                + "    try {\n"
                + "      alert(d1.isSameNode(d1));\n"
                + "      alert(d1.isSameNode(d2));\n"
                + "    } catch(e) {\n"
                + "      alert('isSameNode not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='div1'/>\n"
                + "<div id='div2'/>\n"
                + "</body></html>";
        final String[] messages = {"true", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void appendChild_parentNode() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.createElement('div');\n"
                + "    var div2 = document.createElement('div');\n"
                + "    alert(div1.parentNode);\n"
                + "    div1.appendChild(div2);\n"
                + "    if(div1.parentNode)\n"
                + "      alert(div1.parentNode.nodeName);\n"
                + "    else\n"
                + "      alert(div1.parentNode);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void append_insert_html_node() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  var htmlNode = document.documentElement;\n"
                + "  var body = document.body;\n"
                + "  alert(body.childNodes.length);\n"
                + "  try { body.appendChild(htmlNode); } catch(e) { alert('exception'); }\n"
                + "  alert(body.childNodes.length);\n"
                + "  try { body.insertBefore(htmlNode, body.firstChild); } catch(e) { alert('exception'); }\n"
                + "  alert(body.childNodes.length);\n"
                + "  try { body.replaceChild(htmlNode, body.firstChild); } catch(e) { alert('exception'); }\n"
                + "  alert(body.childNodes.length);\n"
                + "}\n"
                + "</script></head><body onload='test()'><span>hi</span></body></html>";

        final String[] messages = {"1", "exception", "1", "exception", "1", "exception", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void appendChild_of_DocumentFragment() {
        final String html = "<html><head><script>\n"
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
                + "    alert(div.childNodes.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='myDiv'></div>\n"
                + "</body></html>";

        final String[] messages = {"2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void nodePrototype() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.body.TEXT_NODE);\n"
                + "      alert(Node.TEXT_NODE);\n"
                + "      document.body.TEXT_NODE = 123;\n"
                + "      alert(document.body.TEXT_NODE);\n"
                + "      alert(Node.TEXT_NODE);\n"
                + "      Node.TEXT_NODE = 456;\n"
                + "      alert(document.body.TEXT_NODE);\n"
                + "      alert(Node.TEXT_NODE);\n"
                + "      delete Node.TEXT_NODE;\n"
                + "      delete document.body.TEXT_NODE;\n"
                + "      alert(document.body.TEXT_NODE);\n"
                + "      alert(Node.TEXT_NODE);\n"
                + "    } catch(e) {\n"
                + "      alert('not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"3", "3", "3", "3", "3", "3", "3", "3"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void replaceChild() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var element = document.getElementById('myDiv2');\n"
                + "      var range = element.ownerDocument.createRange();\n"
                + "      range.setStartAfter(element);\n"
                + "      var fragment = range.createContextualFragment('<div>one</div><div>two</div>');\n"
                + "      var parent = element.parentNode;\n"
                + "      alert(parent.innerHTML);\n"
                + "      alert(parent.replaceChild(fragment, parent.firstChild).id);\n"
                + "      alert(parent.innerHTML);\n"
                + "    } catch(e) {\n"
                + "      alert('exception thrown');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'><div id='myDiv2'></div><div id='myDiv3'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"<div id=\"myDiv2\"></div><div id=\"myDiv3\"></div>", "myDiv2",
                "<div>one</div><div>two</div><div id=\"myDiv3\"></div>"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void replaceChild_EmptyDocumentFragment() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var element = document.getElementById('myDiv2');\n"
                + "    try {\n"
                + "      var range = element.ownerDocument.createRange();\n"
                + "      range.setStartAfter(element);\n"
                + "      var fragment = range.createContextualFragment('');\n"
                + "      var parent = element.parentNode;\n"
                + "      alert(parent.innerHTML);\n"
                + "      alert(parent.replaceChild(fragment, parent.firstChild).id);\n"
                + "      alert(parent.innerHTML);\n"
                + "    } catch(e) {\n"
                + "      alert('exception thrown');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'><div id='myDiv2'></div><div id='myDiv3'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"<div id=\"myDiv2\"></div><div id=\"myDiv3\"></div>", "myDiv2",
                "<div id=\"myDiv3\"></div>"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void documentPositionConstants() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.body.DOCUMENT_POSITION_DISCONNECTED);\n"
                + "      alert(Node.DOCUMENT_POSITION_DISCONNECTED);\n"
                + "      alert(Node.DOCUMENT_POSITION_PRECEDING);\n"
                + "      alert(Node.DOCUMENT_POSITION_FOLLOWING);\n"
                + "      alert(Node.DOCUMENT_POSITION_CONTAINS);\n"
                + "      alert(Node.DOCUMENT_POSITION_CONTAINED_BY);\n"
                + "      alert(Node.DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC);\n"
                + "    } catch(e) {\n"
                + "      alert('not supported');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"1", "1", "2", "4", "8", "16", "32"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void compareDocumentPosition() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  var div2 = document.getElementById('div2');\n"
                + "  var div3 = document.getElementById('div3');\n"
                + "  if (!div1.compareDocumentPosition) { alert('compareDocumentPosition not available'); return }\n"
                + "  alert(div1.compareDocumentPosition(div1));\n"
                + "  alert(div1.compareDocumentPosition(div2));\n"
                + "  alert(div1.compareDocumentPosition(div3));\n"
                + "  alert(div1.compareDocumentPosition(div4));\n"
                + "  alert(div2.compareDocumentPosition(div1));\n"
                + "  alert(div3.compareDocumentPosition(div1));\n"
                + "  alert(div4.compareDocumentPosition(div1));\n"
                + "  alert(div2.compareDocumentPosition(div3));\n"
                + "  try {\n"
                + "    alert(div2.compareDocumentPosition({}));\n"
                + "  } catch(e) { alert('exception'); }\n"
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

        final String[] messages = {"0", "20", "20", "4", "10", "10", "2", "20", "exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void compareDocumentPosition2() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    var childDiv = document.createElement('div');\n"
                + "    try {\n"
                + "      alert(div.compareDocumentPosition(childDiv) & Node.DOCUMENT_POSITION_CONTAINED_BY);\n"
                + "      div.appendChild(childDiv);\n"
                + "      alert(div.compareDocumentPosition(childDiv) & Node.DOCUMENT_POSITION_CONTAINED_BY);\n"
                + "    } catch(e) {alert('exception');}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"0", "16"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void insertBeforenullRef() {
        final String[] messages = {"3", "span"};
        insertBefore("aNode.insertBefore(newNode, null);", messages);
    }

    @Test
    public void insertBeforemyself() {
        final String[] messages = {"exception"};
        insertBefore("aNode.insertBefore(newNode, newNode);", messages);
    }

    @Test
    public void insertBeforesibling() {
        final String[] messages = {"exception"};
        insertBefore("aNode.insertBefore(newNode, siblingNode);", messages);
    }

    @Test
    public void insertBeforenoArgs() {
        final String[] messages = {"exception"};
        insertBefore("aNode.insertBefore();", messages);
    }

    @Test
    public void insertBeforenoSecondArg() {
        final String[] messages = {"exception"};
        insertBefore("aNode.insertBefore(newNode);", messages);
    }

    private void insertBefore(final String insertJSLine, final String[] messages) {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"

                + "    function doTest() {\n"
                + "      var newNode = document.createElement('span');\n"
                + "      var siblingNode = document.getElementById('sibingNode');\n"
                + "      var aNode = document.getElementById('myNode');\n"
                + "      try {\n"
                + insertJSLine
                + "        alert(aNode.childNodes.length);\n"
                + "        alert(aNode.childNodes[2].nodeName);\n"
                + "      }\n"
                + "      catch (e) { alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='myNode'><span>Child Node 1-A</span><h1>Child Node 2-A</h1></div>\n"
                + "<h2 id='sibingNode'>Sibling</h2>\n"
                + "</body></html>";

        checkHtmlAlert(html, messages);
    }

    @Test
    public void insertBeforeparentNode() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    var div1 = document.createElement('div');\n"
                + "    var div2 = document.createElement('div');\n"
                + "    alert(div1.parentNode);\n"
                + "    div1.insertBefore(div2, null);\n"
                + "    if(div1.parentNode)\n"
                + "      alert(div1.parentNode.nodeName);\n"
                + "    else\n"
                + "      alert(div1.parentNode);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {null, null};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void insertBeforeinTable() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var table = document.getElementById('myTable');\n"
                + "  var colGroup = table.insertBefore(document.createElement('colgroup'), null);\n"
                + "  alert(colGroup);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <table id='myTable'></table>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLTableColElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
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

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void normalize() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var root = doc.appendChild(doc.createElement('root'));\n"
                + "    var cdata = root.appendChild(doc.createCDATASection('abcdef'));\n"
                + "    cdata.splitText(3);\n"
                + "    var text = root.appendChild(doc.createTextNode('123456'));\n"
                + "    text.splitText(3);\n"
                + "    alert(root.childNodes.length);\n"
                + "    root.normalize();\n"
                + "    alert(root.childNodes.length);\n"
                + "    alert(root.childNodes.item(0).data);\n"
                + "    alert(root.childNodes.item(1).data);\n"
                + "    alert(root.childNodes.item(2).data);\n"
                + "    alert(root.childNodes.item(2) == text);\n"
                + "\n"
                + "    var body = document.body;\n"
                + "    alert(body.childNodes.length);\n"
                + "    text = body.appendChild(document.createTextNode('123456'));\n"
                + "    text.splitText(3);\n"
                + "    alert(body.childNodes.length);\n"
                + "    alert(body.childNodes.item(0).nodeValue);\n"
                + "    alert(body.childNodes.item(1).nodeValue);\n"
                + "    body.normalize();\n"
                + "    alert(body.childNodes.length);\n"
                + "    alert(body.childNodes.item(0) == text);\n"
                + "  }\n"
                + "</script></head><body onload='test()'></body></html>";
        final String[] messages = {"4", "3", "abc", "def", "123456", "true", "0", "2", "123", "456", "1", "true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void addEventListener() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var node = document.createElement('button');\n"
                        + "      alert(node.addEventListener !== undefined);\n"
                        + "      alert(node.removeEventListener !== undefined);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {"true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setTextContent() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var foo = document.getElementById('foo');\n"
                        + "      foo.textContent = 'Hello';\n"
                        + "      if (foo.firstChild) {\n"
                        + "        alert(foo.firstChild.wholeText);\n"
                        + "      }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <span id='foo'></span>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {"Hello"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void cloneParent() {
        final String html =
                "<!DOCTYPE><html>\n"
                        + "  <head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var foo = document.getElementById('foo');\n"
                        + "      var clone = foo.cloneNode(true);\n"
                        + "      alert(clone.parentNode);\n"
                        + "      if (clone.parentNode) {\n"
                        + "        alert(clone.parentNode.URL);\n"
                        + "        alert(clone.parentNode.nodeType);\n"
                        + "      }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <span id='foo'></span>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {"null"};
        checkHtmlAlert(html, messages);
    }


    @Test
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
                        + "      alert(parent.outerHTML);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";

        final String[] messages = {"<div><span></span>nullundefinedhello<p></p></div>"};
        checkHtmlAlert(html, messages);
    }


    @Test
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
                        + "      alert(parent.outerHTML);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";

        final String[] messages = {"<div><p></p><span></span>nullundefinedhello</div>"};
        checkHtmlAlert(html, messages);
    }


    @Test
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
                        + "      alert(parent.outerHTML);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";

        final String[] messages = {"<div><span></span>nullundefinedhello</div>"};
        checkHtmlAlert(html, messages);
    }
}
