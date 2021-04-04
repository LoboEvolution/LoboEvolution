/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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


package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLElement}.
 */
public class HTMLElementTest extends LoboUnitTest {

    /**
     * <p>all_IndexByInt.</p>
     */
    @Test
    public void all_IndexByInt() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  dumpAll('body');\n"
                + "  dumpAll('testDiv');\n"
                + "  dumpAll('testA');\n"
                + "  dumpAll('testImg');\n"
                + "  dumpAll('testDiv2');\n"
                + "}\n"
                + "function dumpAll(_id) {\n"
                + "  var oNode = document.getElementById(_id);\n"
                + "  var col = oNode.all;\n"
                + "  if (col) {\n"
                + "    var str = 'all node for ' + _id + ': ';\n"
                + "    for (var i = 0; i < col.length; i++) {\n"
                + "      str += col[i].tagName + ' ';\n"
                + "    }\n"
                + "    alert(str);\n"
                + "  } else {\n"
                + "    alert('all is not supported');\n"
                + "  }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()' id='body'>\n"
                + "  <div id='testDiv'>foo<a href='foo.html' id='testA'><img src='foo.png' id='testImg'></a></div>\n"
                + "  <div id='testDiv2'>foo</div>\n"
                + "</body></html>";

        final String[] messages = {"all is not supported", "all is not supported",
                "all is not supported", "all is not supported", "all is not supported"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttribute.</p>
     */
    @Test
    public void getAttribute() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert(myNode.title);\n"
                + "    alert(myNode.getAttribute('title'));\n"
                + "    alert(myNode.Title);\n"
                + "    alert(myNode.getAttribute('class'));\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<p id='myNode' title='a'>\n"
                + "</p>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"a", "a", "undefined", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttribute_styleAttribute.</p>
     */
    @Test
    public void getAttribute_styleAttribute() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('tester');\n"
                + "    alert(elem.getAttribute('style'));\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "  <div id='tester'>tester</div>\n"
                + "</body></html>";

        final String[] messages = {null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttribute_styleAttributeWithFlag.</p>
     */
    @Test
    public void getAttribute_styleAttributeWithFlag() {
        final String html =
                "<html><body onload='test()'><div id='div' style='color: green;'>abc</div>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var div = document.getElementById('div');\n"
                        + "    alert(div.getAttribute('style', 2));\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";

        final String[] messages = {"color: green;"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>attributes.</p>
     */
    @Test
    public void attributes() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.body.firstChild;\n"
                + "    if (myNode.attributes.length == 0)\n"
                + "      alert('0 attribute');\n"
                + "    else\n"
                + "      alert('at least 1 attribute');\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>" // no \n here!
                + "<span>test span</span>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"0 attribute"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSetAttributeNS.</p>
     */
    @Test
    public void getSetAttributeNS() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<title>test</title>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var myNode = document.getElementById('myNode');\n"
                + "  alert(myNode.getAttributeNS('myNamespaceURI', 'my:foo'));\n"
                + "  myNode.setAttributeNS('myNamespaceURI', 'my:foo', 'bla');\n"
                + "  alert(myNode.getAttributeNS('myNamespaceURI', 'foo'));\n"
                + "  alert(myNode.getAttributeNodeNS('myNamespaceURI', 'foo').specified);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<p id='myNode' title='a'>\n"
                + "</p>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {null, "bla", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>attributesAccess.</p>
     */
    @Test
    public void attributesAccess() {
        final String html
                = "<html><head>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <input type='text' id='i' name='i' style='color:red' onclick='alert(1)' custom1='a' />\n"
                + "  <script>\n"
                + "    var i = document.getElementById('i');\n"
                + "    alert(i.type);\n"
                + "    alert(i.id);\n"
                + "    alert(i.name);\n"
                + "    alert(i.style);\n"
                + "    alert(typeof i.onclick);\n"
                + "    alert(i.custom1);\n"
                + "    alert(i.custom2);\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"text", "i", "i", "[object CSS2Properties]", "function", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAttribute.</p>
     */
    @Test
    public void setAttribute() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert(myNode.title);\n"
                + "    myNode.setAttribute('title', 'b');\n"
                + "    alert(myNode.title);\n"
                + "    alert(myNode.Title);\n"
                + "    myNode.Title = 'foo';\n"
                + "    alert(myNode.Title);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<p id='myNode' title='a'>\n"
                + "</p>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"a", "b", "undefined", "foo"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttributeNode.</p>
     */
    @Test
    public void getAttributeNode() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <title>test</title>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var div = document.getElementById('div2');\n"
                        + "      alert(div.getAttributeNode('notExisting'));\n"
                        + "      var customAtt = div.getAttributeNode('custom_attribute');\n"
                        + "      alertAttributeProperties(customAtt);\n"
                        + "    }\n"
                        + "    function alertAttributeProperties(att) {\n"
                        + "      alert('expando=' + att.expando);\n"
                        + "      alert('firstChild=' + att.firstChild);\n"
                        + "      alert('lastChild=' + att.lastChild);\n"
                        + "      alert('name=' + att.name);\n"
                        + "      alert('nextSibling=' + att.nextSibling);\n"
                        + "      alert('nodeName=' + att.nodeName);\n"
                        + "      alert('nodeType=' + att.nodeType);\n"
                        + "      alert('nodeValue=' + att.nodeValue);\n"
                        + "      alert('(ownerDocument == document) = ' + (att.ownerDocument == document));\n"
                        + "      alert('parentNode=' + att.parentNode);\n"
                        + "      alert('previousSibling=' + att.previousSibling);\n"
                        + "      alert('specified=' + att.specified);\n"
                        + "      alert('value=' + att.value);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='div1'></div>\n"
                        + "  <div id='div2' name='blah' custom_attribute='bleh'></div>\n"
                        + "  <div id='div3'></div>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {null, "expando=undefined", "firstChild=null", "lastChild=null", "name=custom_attribute",
                "nextSibling=null", "nodeName=custom_attribute", "nodeType=2", "nodeValue=bleh",
                "(ownerDocument == document) = true", "parentNode=null", "previousSibling=null",
                "specified=true", "value=bleh"};   checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAttributeNode.</p>
     */
    @Test
    public void setAttributeNode() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <title>test</title>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      // Get the old alignment.\n"
                        + "      var div1 = document.getElementById('div1');\n"
                        + "      var a1 = div1.getAttributeNode('align');\n"
                        + "      alert(a1.value);\n"
                        + "      // Set the new alignment.\n"
                        + "      var a2 = document.createAttribute('align');\n"
                        + "      a2.value = 'right';\n"
                        + "      a1 = div1.setAttributeNode(a2);\n"
                        + "      alert(a1.value);\n"
                        + "      alert(div1.getAttributeNode('align').value);\n"
                        + "      alert(div1.getAttribute('align'));\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='div1' align='left'></div>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"left", "left", "right", "right"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByTagName.</p>
     */
    @Test
    public void getElementsByTagName() {
        final String html
                = "<html><head><title>First</title><script>\n"
                + "function doTest() {\n"
                + "  var a1 = document.getElementsByTagName('td');\n"
                + "  alert('all = ' + a1.length);\n"
                + "  var firstRow = document.getElementById('r1');\n"
                + "  var rowOnly = firstRow.getElementsByTagName('td');\n"
                + "  alert('row = ' + rowOnly.length);\n"
                + "  alert('by wrong name: ' + firstRow.getElementsByTagName('>').length);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<table>\n"
                + "<tr id='r1'><td>1</td><td>2</td></tr>\n"
                + "<tr id='r2'><td>3</td><td>4</td></tr>\n"
                + "</table>\n"
                + "</body></html>";

        final String[] messages = {"all = 4", "row = 2", "by wrong name: 0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByTagName2.</p>
     */
    @Test
    public void getElementsByTagName2() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    for (var f = 0; (formnode = document.getElementsByTagName('form').item(f)); f++)\n"
                + "      for (var i = 0; (node = formnode.getElementsByTagName('div').item(i)); i++)\n"
                + "        alert(node.id);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <form>\n"
                + "    <div id='div1'/>\n"
                + "    <div id='div2'/>\n"
                + "  </form>\n"
                + "</body></html>";

        final String[] messages = {"div1", "div2"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByTagNameCollection.</p>
     */
    @Test
    public void getElementsByTagNameCollection() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var form1 = document.getElementById('form1');\n"
                + "  var elements = form1.getElementsByTagName('input');\n"
                + "  alert(elements['one'].name);\n"
                + "  alert(elements['two'].name);\n"
                + "  alert(elements['three'].name);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<form id='form1'>\n"
                + "<input id='one' name='first' type='text'>\n"
                + "<input id='two' name='second' type='text'>\n"
                + "<input id='three' name='third' type='text'>\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"first", "second", "third"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByTagNameAsterisk.</p>
     */
    @Test
    public void getElementsByTagNameAsterisk() {
        final String html = "<html><body onload='test()'><script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementsByTagName('*').length);\n"
                + "    alert(document.getElementById('div').getElementsByTagName('*').length);\n"
                + "  }\n"
                + "</script>\n"
                + "<div id='div'><p>a</p><p>b</p><p>c</p></div>\n"
                + "</body></html>";
        final String[] messages = {"8", "3"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByTagNameEquality.</p>
     */
    @Test
    public void getElementsByTagNameEquality() {
        final String html =
                "<html><body><div id='d'><script>\n"
                        + "var div = document.getElementById('d');\n"
                        + "alert(document.getElementsByTagName('*') == document.getElementsByTagName('*'));\n"
                        + "alert(document.getElementsByTagName('script') == document.getElementsByTagName('script'));\n"
                        + "alert(document.getElementsByTagName('foo') == document.getElementsByTagName('foo'));\n"
                        + "alert(document.getElementsByTagName('script') == document.getElementsByTagName('body'));\n"
                        + "alert(document.getElementsByTagName('script') == div.getElementsByTagName('script'));\n"
                        + "</script></div></body></html>";
        final String[] messages = {"true", "true", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getClassName.</p>
     */
    @Test
    public void getClassName() {
        final String html
                = "<html><head><style>.x { font: 8pt Arial bold; }</style>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var ele = document.getElementById('pid');\n"
                + "  var aClass = ele.className;\n"
                + "  alert('the class is ' + aClass);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p id='pid' class='x'>text</p>\n"
                + "</body></html>";

        final String[] messages = {"the class is x"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setClassName.</p>
     */
    @Test
    public void setClassName() {
        final String html
                = "<html><head><style>.x { font: 8pt Arial bold; }</style>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var ele = document.getElementById('pid');\n"
                + "  ele.className = 'z';\n"
                + "  var aClass = ele.className;\n"
                + "  alert('the class is ' + aClass);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p id='pid' class='x'>text</p>\n"
                + "</body></html>";

        final String[] messages = {"the class is z"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getInnerHTML.</p>
     */
    @Test
    public void getInnerHTML() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script id='theScript'>if (1 > 2 & 3 < 2) willNotHappen('yo');</script>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('theScript');\n"
                + "    alert(myNode.innerHTML);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<form id='myNode'></form>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"if (1 > 2 & 3 < 2) willNotHappen('yo');"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getInnerHTML_EmptyAttributes.</p>
     */
    @Test
    public void getInnerHTML_EmptyAttributes() {
        final String html = "<body onload='alert(document.body.innerHTML)'><div id='i' foo='' name=''></div></body>";
        final String[] messages = {"<div id='i' foo='' name=''>"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSetInnerHTMLSimple_FF.</p>
     */
    @Test
    public void getSetInnerHTMLSimple_FF() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    myNode.innerHTML = 'New  cell value';\n"
                + "    alert('New = ' + myNode.innerHTML);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<p id='myNode'><b>Old innerHTML</b></p>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"Old = <b>Old innerHTML</b>", "New = New  cell value"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSetInnerHTMLNewInput.</p>
     */
    @Test
    public void getSetInnerHTMLNewInput() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    myNode.innerHTML = '<input type=\"checkbox\" name=\"myCb\" checked>';\n"
                + "    alert(myNode.myCb.checked);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<form id='myNode'></form>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSetInnerHTMLChar_FF.</p>
     */
    @Test
    public void getSetInnerHTMLChar_FF() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    myNode.innerHTML = 'New  cell value &amp; \\u0110 &#272;';\n"
                + "    alert('New = ' + myNode.innerHTML);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<p id='myNode'><b>Old innerHTML</b></p>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"Old = <b>Old innerHTML</b>",
                "New = New  cell value &amp; \u0110 \u0110"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setInnerHTMLDeclareJavaScript.</p>
     */
    @Test
    public void setInnerHTMLDeclareJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = '<scr'+'ipt>function tester() { alerter(); }</scr'+'ipt>';\n"
                + "    var outernode = document.getElementById('myNode');\n"
                + "    outernode.innerHTML = newnode;\n"
                + "    try {\n"
                + "      tester();\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('declared');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>outerHTMLinNewDiv.</p>
     */
    @Test
    public void outerHTMLinNewDiv() {
        final String html = "<html><body onload='test()'><script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    alert('outerHTML' in div);\n"
                + "    alert('innerHTML' in div);\n"
                + "    alert('innerText' in div);\n"
                + "  }\n"
                + "</script>\n"
                + "<div id='div'><span class='a b'></span></div>\n"
                + "</body></html>";

        final String[] messages = {"true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSetInnerHtmlEmptyTag_FF.</p>
     */
    @Test
    public void getSetInnerHtmlEmptyTag_FF() {
        final String html = "<html><body onload='test()'><script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('div');\n"
                + "    alert(div.outerHTML);\n"
                + "    alert(div.innerHTML);\n"
                + "    alert(div.innerText);\n"
                + "  }\n"
                + "</script>\n"
                + "<div id='div'><ul/></div>"
                + "</body></html>";

        final String[] messages = {"<div id=\"div\"><ul></ul></div>", "<ul></ul>", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSetInnerHtmlAttributeWithWhitespace_FF.</p>
     */
    @Test
    public void getSetInnerHtmlAttributeWithWhitespace_FF() {
        final String html = "<html><body onload='test()'><script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('div');\n"
                + "    alert(div.outerHTML);\n"
                + "    alert(div.innerHTML);\n"
                + "    alert(div.innerText);\n"
                + "  }\n"
                + "</script>\n"
                + "<div id='div'><span class='a b'></span></div>\n"
                + "</body></html>";

        final String[] messages = {"<div id=\"div\"><span class=\"a b\"></span></div>", "<span class=\"a b\"></span>", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setInnerHTMLEmpty.</p>
     */
    @Test
    public void setInnerHTMLEmpty() {
        final String html = "<html><head></head><body>\n"
                + "<div id='testDiv'>foo</div>\n"
                + "<script language='javascript'>\n"
                + "    var node = document.getElementById('testDiv');\n"
                + "    node.innerHTML = '';\n"
                + "    alert('Empty ChildrenLength: ' + node.childNodes.length);\n"
                + "</script></body></html>";

        final String[] messages = {"Empty ChildrenLength: 0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setInnerHTMLNull.</p>
     */
    @Test
    public void setInnerHTMLNull() {
        final String html = "<html><head></head><body>\n"
                + "<div id='testDiv'>foo</div>\n"
                + "<script language='javascript'>\n"
                + "    var node = document.getElementById('testDiv');\n"
                + "    node.innerHTML = null;\n"
                + "    alert('Null ChildrenLength: ' + node.childNodes.length);\n"
                + "</script></body></html>";

        final String[] messages = {"Null ChildrenLength: 0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setInnerHTMLResetsStyle.</p>
     */
    @Test
    public void setInnerHTMLResetsStyle() {
        final String html = "<html><head></head>\n"
                + "<body>\n"
                + "<div id='testDiv'></div>\n"
                + "<script language='javascript'>\n"
                + "    var node = document.getElementById('testDiv');\n"
                + "    var height = node.offsetHeight;\n"
                + "    node.innerHTML = 'HtmlUnit';\n"
                + "    alert(height < node.offsetHeight);\n"
                + "</script></body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOuterHTMLFromBlock.</p>
     */
    @Test
    public void getOuterHTMLFromBlock() {
        final String html = createPageForGetOuterHTML("div", "New  cell value", false);
        final String[] messages = {"Outer = <DIV id=\"myNode\">New  cell value</DIV>"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOuterHTMLFromInline.</p>
     */
    @Test
    public void getOuterHTMLFromInline() {
        final String html = createPageForGetOuterHTML("span", "New  cell value", false);
        final String[] messages = {"Outer = <SPAN id=\"myNode\">New  cell value</SPAN>"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOuterHTMLFromEmpty.</p>
     */
    @Test
    public void getOuterHTMLFromEmpty() {
        final String html = createPageForGetOuterHTML("br", "", true);
        final String[] messages = {"Outer = <BR id=\"myNode\">"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOuterHTMLFromUnclosedParagraph.</p>
     */
    @Test
    public void getOuterHTMLFromUnclosedParagraph() {
        final String html = createPageForGetOuterHTML("p", "New  cell value", true);
        final String[] messages = {"Outer = <P id=\"myNode\">New  cell value\n\n</P>"};
        checkHtmlAlert(html, messages);
    }

    private static String createPageForGetOuterHTML(final String nodeTag, final String value, final boolean unclosed) {
        return "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert('Outer = ' + myNode.outerHTML);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <" + nodeTag + " id='myNode'>" + value + (unclosed ? "" : "</" + nodeTag + ">") + "\n"
                + "</body>\n"
                + "</html>";
    }

    /**
     * <p>setOuterHTMLNull.</p>
     */
    @Test
    public void setOuterHTMLNull() {
        final String html = createPageForSetOuterHTML("div", null);
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = ", "Children: 0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLEmpty.</p>
     */
    @Test
    public void setOuterHTMLEmpty() {
        final String html = createPageForSetOuterHTML("div", "");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = ", "Children: 0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLBlank.</p>
     */
    @Test
    public void setOuterHTMLBlank() {
        final String html = createPageForSetOuterHTML("div", "  ");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New =   ", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddTextToBlock.</p>
     */
    @Test
    public void setOuterHTMLAddTextToBlock() {
        final String html = createPageForSetOuterHTML("div", "New  cell value");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = New  cell value", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddTextToInline.</p>
     */
    @Test
    public void setOuterHTMLAddTextToInline() {
        final String html = createPageForSetOuterHTML("span", "New  cell value");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = New  cell value", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddBlockToBlock.</p>
     */
    @Test
    public void setOuterHTMLAddBlockToBlock() {
        final String html = createPageForSetOuterHTML("div", "<div>test</div>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <div>test</div>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddBlockToInline.</p>
     */
    @Test
    public void setOuterHTMLAddBlockToInline() {
        final String html = createPageForSetOuterHTML("span", "<div>test</div>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <div>test</div>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>setOuterHTMLAddInlineToInline.</p>
     */
    @Test
    public void setOuterHTMLAddInlineToInline() {
        final String html = createPageForSetOuterHTML("span", "<span>test</span>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <span>test</span>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddInlineToBlock.</p>
     */
    @Test
    public void setOuterHTMLAddInlineToBlock() {
        final String html = createPageForSetOuterHTML("div", "<span>test</span>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <span>test</span>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddEmpty.</p>
     */
    @Test
    public void setOuterHTMLAddEmpty() {
        final String html = createPageForSetOuterHTML("div", "<br>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <br>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLToReadOnly.</p>
     */
    @Test
    public void setOuterHTMLToReadOnly() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var nodeTypes = ['body', 'caption', 'col', 'colgroup', 'head', 'html',\n"
                + "                     'tbody', 'td', 'tfoot', 'th', 'thead', 'tr'];\n"
                + "    for (var i = 0; i < nodeTypes.length; i++) {\n"
                + "      var nodeType = nodeTypes[i];\n"
                + "      var myNode = document.getElementsByTagName(nodeType)[0];\n"
                + "      try {\n"
                + "        myNode.outerHTML = 'test';\n"
                + "        alert('-' + i);\n"
                + "      } catch(e) {alert(i); }\n"
                + "    }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <table>\n"
                + "    <caption></caption>\n"
                + "    <colgroup><col></colgroup>\n"
                + "    <thead><tr><td></td><th></th></tr></thead>\n"
                + "    <tbody></tbody>\n"
                + "    <tfoot></tfoot>\n"
                + "  </table>\n"
                + "  </table>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"-0", "1", "2", "3", "-4", "5", "6", "7", "8", "9", "10", "11"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddBlockToParagraph.</p>
     */
    @Test
    public void setOuterHTMLAddBlockToParagraph() {
        final String html = createPageForSetOuterHTML("p", "<div>test</div>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <div>test</div>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddParagraphToParagraph.</p>
     */
    @Test
    public void setOuterHTMLAddParagraphToParagraph() {
        final String html = createPageForSetOuterHTML("p", "<p>test</p>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <p>test</p>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddAnchorToAnchor.</p>
     */
    @Test
    public void setOuterHTMLAddAnchorToAnchor() {
        final String html = createPageForSetOuterHTML("a", "<a>test</a>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <a>test</a>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddSelfClosingBlock.</p>
     */
    @Test
    public void setOuterHTMLAddSelfClosingBlock() {
        final String html = createPageForSetOuterHTML("div", "<div/>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <div/>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddMultipleSelfClosingBlock.</p>
     */
    @Test
    public void setOuterHTMLAddMultipleSelfClosingBlock() {
        final String html = createPageForSetOuterHTML("div", "<div/><div>");
        final String[] messages = {"Old = <SPAN id=\\\"innerNode\\\">Old outerHTML</span>\",\n" +
                "                \"New = <div><div></div></div>\", \"Children: 1\""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddSelfClosingInline.</p>
     */
    @Test
    public void setOuterHTMLAddSelfClosingInline() {
        final String html = createPageForSetOuterHTML("div", "<span/>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <span/>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLAddSelfClosingEmpty.</p>
     */
    @Test
    public void setOuterHTMLAddSelfClosingEmpty() {
        final String html = createPageForSetOuterHTML("div", "<br/>");
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <br/>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    private static String createPageForSetOuterHTML(final String nodeTag, final String newValue) {
        String newVal = null;
        if ("undefined".equals(newValue)) {
            newVal = "undefined";
        } else if (newValue != null) {
            newVal = "'" + newValue + "'";
        }
        return "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    var innerNode = document.getElementById('innerNode');\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    innerNode.outerHTML = " + newVal + ";\n"
                + "    alert('New = ' + myNode.innerHTML);\n"
                + "    alert('Children: ' + myNode.childNodes.length);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <" + nodeTag + " id='myNode'><span id='innerNode'>Old outerHTML</span></" + nodeTag + ">\n"
                + "</body>\n"
                + "</html>";
    }

    /**
     * <p>setOuterHTMLDetachedElementNull.</p>
     */
    @Test
    public void setOuterHTMLDetachedElementNull() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = null;\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLDetachedElementUndefined.</p>
     */
    @Test
    public void setOuterHTMLDetachedElementUndefined() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = undefined;\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLDetachedElementEmpty.</p>
     */
    @Test
    public void setOuterHTMLDetachedElementEmpty() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = '';\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLDetachedElementBlank.</p>
     */
    @Test
    public void setOuterHTMLDetachedElementBlank() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = '';\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setOuterHTMLDetachedElement.</p>
     */
    @Test
    public void setOuterHTMLDetachedElement() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    document.body.removeChild(myNode);\n"
                + "    alert('Old = ' + myNode.innerHTML);\n"
                + "    try {\n"
                + "      myNode.outerHTML = '<p>test</p>';\n"
                + "      alert('New = ' + myNode.innerHTML);\n"
                + "      alert('Children: ' + myNode.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='myNode'><span id='innerNode'>Old outerHTML</span></div>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"Old = <SPAN id=\"innerNode\">Old outerHTML</SPAN>", "New = <span id=\"innerNode\">Old outerHTML</span>", "Children: 1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addBehaviorDefaultClientCaps.</p>
     */
    @Test
    public void addBehaviorDefaultClientCaps() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  var body = document.body;\n"
                + "  alert('body.cpuClass = ' + body.cpuClass);\n"
                + "  var id = body.addBehavior('#default#clientCaps');\n"
                + "  alert('body.cpuClass = ' + body.cpuClass);\n"
                + "  var id2 = body.addBehavior('#default#clientCaps');\n"
                + "  body.removeBehavior(id);\n"
                + "  alert('body.cpuClass = ' + body.cpuClass);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        final String[] messages = {"body.cpuClass = undefined", "exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>removeBehavior.</p>
     */
    @Test
    public void removeBehavior() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  var body = document.body;\n"
                + "  alert('body.isHomePage = ' + body.isHomePage);\n"

                + "  if(!body.addBehavior) { alert('!addBehavior'); }\n"
                + "  if(!body.removeBehavior) { alert('!removeBehavior'); }\n"

                + "  var id = body.addBehavior('#default#homePage');\n"
                + "  alert('body.isHomePage = ' + body.isHomePage('not the home page'));\n"
                + "  body.removeBehavior(id);\n"
                + "  alert('body.isHomePage = ' + body.isHomePage);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        final String[] messages = {"body.isHomePage = undefined", "!addBehavior", "!removeBehavior", "exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>children.</p>
     */
    @Test
    public void children() {
        final String html = "<html><body>\n"
                + "<div id='myDiv'><br/><div><span>test</span></div></div>\n"
                + "<script>\n"
                + "  var oDiv = document.getElementById('myDiv');\n"
                + "  for (var i = 0; i < oDiv.children.length; i++) {\n"
                + "    alert(oDiv.children[i].tagName);\n"
                + "  }\n"
                + "  var oCol = oDiv.children;\n"
                + "  alert(oCol.length);\n"
                + "  oDiv.insertAdjacentHTML('beforeEnd', '<br>');\n"
                + "  alert(oCol.length);\n"
                + "</script></body></html>";

        final String[] messages = {"BR", "DIV", "2", "3"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>childrenDoesNotCountTextNodes.</p>
     */
    @Test
    public void childrenDoesNotCountTextNodes() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  children = document.getElementById('myBody').children;\n"
                + "  alert(children.length);\n"

                + "  children = document.getElementById('myId').children;\n"
                + "  alert(children.length);\n"
                + "}\n"
                + "</script></head><body id='myBody' onload='test()'>\n"
                + "  <div id='myId'>abcd</div>\n"
                + "</body></html>";

        final String[] messages = {"1", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>childrenFunctionAccess.</p>
     */
    @Test
    public void childrenFunctionAccess() {
        final String html = "<html><body>\n"
                + "<div id='myDiv'><br/><div>\n"
                + "<script>\n"
                + "try {\n"
                + "  var oDiv = document.getElementById('myDiv');\n"
                + "  alert(oDiv.children.length);\n"
                + "  alert(oDiv.children(0).tagName);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        final String[] messages = {"2", "exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSetInnerTextSimple.</p>
     */
    @Test
    public void getSetInnerTextSimple() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myNode = document.getElementById('myNode');\n"
                + "    alert('Old = ' + myNode.innerText);\n"
                + "    myNode.innerText = 'New cell value';\n"
                + "    alert('New = ' + myNode.innerText);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='myNode'><b>Old <p>innerText</p></b></div>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"Old = Old\n\ninnerText", "New = New cell value"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>removeAttribute.</p>
     */
    @Test
    public void removeAttribute() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myDiv = document.getElementById('aDiv');\n"
                + "    alert(myDiv.getAttribute('name'));\n"
                + "    myDiv.removeAttribute('name');\n"
                + "    alert(myDiv.getAttribute('name'));\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'><div id='aDiv' name='removeMe'>\n"
                + "</div></body>\n"
                + "</html>";

        final String[] messages = {"removeMe", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>removeAttribute_property.</p>
     */
    @Test
    public void removeAttribute_property() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var myDiv = document.getElementById('aDiv');\n"
                + "    myDiv.foo = 'hello';\n"
                + "    alert(myDiv.foo);\n"
                + "    alert(myDiv.getAttribute('foo'));\n"
                + "    myDiv.removeAttribute('foo');\n"
                + "    alert(myDiv.foo);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'><div id='aDiv' name='removeMe'>\n"
                + "</div></body>\n"
                + "</html>";
        final String[] messages = {"hello", null, "hello"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scrolls.</p>
     */
    @Test
    public void scrolls() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "</div></body>\n"
                + "<div id='div1'>foo</div>\n"
                + "<script>\n"
                + "function messagescrolls(_oElt) {\n"
                + "  alert(typeof _oElt.scrollHeight);\n"
                + "  alert(typeof _oElt.scrollWidth);\n"
                + "  alert(typeof _oElt.scrollLeft);\n"
                + "  _oElt.scrollLeft = 123;\n"
                + "  alert(typeof _oElt.scrollTop);\n"
                + "  _oElt.scrollTop = 123;\n"
                + "}\n"
                + "messagescrolls(document.body);\n"
                + "messagescrolls(document.getElementById('div1'));\n"
                + "</script></body></html>";
        final String[] messages = {"number", "number", "number", "number", "number", "number", "number", "number"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scrollLeft_overflowScroll.</p>
     */
    @Test
    public void scrollLeft_overflowScroll() {
        scrollLeft("scroll");
    }

    /**
     * <p>scrollLeft_overflowAuto.</p>
     */
    @Test
    public void scrollLeft_overflowAuto() {
        scrollLeft("auto");
    }

    private void scrollLeft(final String overflow) {
        final String html
                = "<html><body onload='test()'>\n"
                + "<div id='d1' style='width:100px;height:100px;background-color:green;'>\n"
                + "  <div id='d2' style='width:50px;height:50px;background-color:blue;'></div>\n"
                + "</div>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var d1 = document.getElementById('d1'), d2 = document.getElementById('d2');\n"
                + "  alert(d1.scrollLeft);\n"
                + "  d1.scrollLeft = -1;\n"
                + "  alert(d1.scrollLeft);\n"
                + "  d1.scrollLeft = 5;\n"
                + "  alert(d1.scrollLeft);\n"
                + "  d2.style.width = '200px';\n"
                + "  d2.style.height = '200px';\n"
                + "  d1.scrollLeft = 7;\n"
                + "  alert(d1.scrollLeft);\n"
                + "  d1.style.overflow = '" + overflow + "';\n"
                + "  alert(d1.scrollLeft);\n"
                + "  d1.scrollLeft = 17;\n"
                + "  alert(d1.scrollLeft);\n"
                + "  d1.style.overflow = 'visible';\n"
                + "  alert(d1.scrollLeft);\n"
                + "  d1.scrollLeft = 19;\n"
                + "  alert(d1.scrollLeft);\n"
                + "}\n"
                + "</script></body></html>";

        final String[] messages = {"0", "0", "0", "0", "0", "17", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scrollLeft.</p>
     */
    @Test
    public void scrollLeft() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var outer = document.getElementById('outer');\n"
                + "    var inner = document.getElementById('inner');\n"
                + "    alert(outer.scrollLeft);\n"

                + "    outer.scrollLeft = 10;\n"
                + "    alert(outer.scrollLeft);\n"

                + "    outer.scrollLeft = -4;\n"
                + "    alert(outer.scrollLeft);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='outer' style='overflow: scroll; width: 100px'>\n"
                + "    <div id='inner' style='width: 250px'>abcdefg hijklmnop qrstuvw xyz</div>\n"
                + "  </div>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"0", "10", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scrollTop_overflowScroll.</p>
     */
    @Test
    public void scrollTop_overflowScroll() {
        scrollTop("scroll");
    }

    /**
     * <p>scrollTop_overflowAuto.</p>
     */
    @Test
    public void scrollTop_overflowAuto() {
        scrollTop("auto");
    }

    private void scrollTop(final String overflow) {
        final String html
                = "<html><body onload='test()'>\n"
                + "<div id='d1' style='width:100px;height:100px;background-color:green;'>\n"
                + "  <div id='d2' style='width:50px;height:50px;background-color:blue;'></div>\n"
                + "</div>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var d1 = document.getElementById('d1'), d2 = document.getElementById('d2');\n"
                + "  alert(d1.scrollTop);\n"
                + "  d1.scrollTop = -1;\n"
                + "  alert(d1.scrollTop);\n"
                + "  d1.scrollTop = 5;\n"
                + "  alert(d1.scrollTop);\n"
                + "  d2.style.width = '200px';\n"
                + "  d2.style.height = '200px';\n"
                + "  d1.scrollTop = 7;\n"
                + "  alert(d1.scrollTop);\n"
                + "  d1.style.overflow = '" + overflow + "';\n"
                + "  alert(d1.scrollTop);\n"
                + "  d1.scrollTop = 17;\n"
                + "  alert(d1.scrollTop);\n"
                + "  d1.style.overflow = 'visible';\n"
                + "  alert(d1.scrollTop);\n"
                + "  d1.scrollTop = 19;\n"
                + "  alert(d1.scrollTop);\n"
                + "}\n"
                + "</script></body></html>";

        final String[] messages = {"0", "0", "0", "0", "0", "17", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scrollIntoView.</p>
     */
    @Test
    public void scrollIntoView() {
        final String html = "<html>\n"
                + "<body>\n"
                + "<script id='me'>document.getElementById('me').scrollIntoView(); alert('ok');</script>\n"
                + "</body></html>";
        final String[] messages = {"ok"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>offsetParent_Basic.</p>
     */
    @Test
    public void offsetParent_Basic() {
        final String html = "<html><head>\n"
                + "<script type='text/javascript'>\n"
                + "function alertOffsetParent(id) {\n"
                + "  var element = document.getElementById(id);\n"
                + "  var offsetParent = element.offsetParent;\n"
                + "  var alertMessage = 'element: ' + element.id + ' offsetParent: ';\n"
                + "  if (offsetParent) {\n"
                + "    alertMessage += offsetParent.id;\n"
                + "  }\n"
                + "  else {\n"
                + "    alertMessage += offsetParent;\n"
                + "  }\n"
                + "  alert(alertMessage);\n"
                + "}\n"
                + "function test() {\n"
                + "  alertOffsetParent('span3');\n"
                + "  alertOffsetParent('td2');\n"
                + "  alertOffsetParent('tr2');\n"
                + "  alertOffsetParent('table2');\n"
                + "  alertOffsetParent('td1');\n"
                + "  alertOffsetParent('tr1');\n"
                + "  alertOffsetParent('table1');\n"
                + "  alertOffsetParent('span2');\n"
                + "  alertOffsetParent('span1');\n"
                + "  alertOffsetParent('div1');\n"
                + "  alertOffsetParent('body1');\n"
                + "}\n"
                + "</script></head>\n"
                + "<body id='body1' onload='test()'>\n"
                + "<div id='div1'>\n"
                + "  <span id='span1'>\n"
                + "    <span id='span2'>\n"
                + "      <table id='table1'>\n"
                + "        <tr id='tr1'>\n"
                + "          <td id='td1'>\n"
                + "            <table id='table2'>\n"
                + "              <tr id='tr2'>\n"
                + "                <td id='td2'>\n"
                + "                  <span id='span3'>some text</span>\n"
                + "                </td>\n"
                + "              </tr>\n"
                + "            </table>\n"
                + "          </td>\n"
                + "        </tr>\n"
                + "      </table>\n"
                + "    </span>\n"
                + "  </span>\n"
                + "</div>\n"
                + "</body></html>";
        final String[] messages = {"element: span3 offsetParent: td2", "element: td2 offsetParent: table2",
                "element: tr2 offsetParent: table2", "element: table2 offsetParent: td1",
                "element: td1 offsetParent: table1", "element: tr1 offsetParent: table1",
                "element: table1 offsetParent: body1", "element: span2 offsetParent: body1",
                "element: span1 offsetParent: body1", "element: div1 offsetParent: body1",
                "element: body1 offsetParent: null"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>offsetParent_newElement.</p>
     */
    @Test
    public void offsetParent_newElement() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  var oNew = document.createElement('span');\n"
                + "  alert(oNew.offsetParent);\n"
                + "  var fragment = document.createDocumentFragment();\n"
                + "  fragment.appendChild(oNew);\n"
                + "  alert(oNew.offsetParent);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {null, null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>offsetParent_WithCSS.</p>
     */
    @Test
    public void offsetParent_WithCSS() {
        final String html = "<html>\n"
                + "  <body id='body' onload='test()'>\n"
                + "    <div id='a1'><div id='a2'>x</div></div>\n"
                + "    <div id='b1'><div id='b2' style='position:fixed'>x</div></div>\n"
                + "    <div id='c1'><div id='c2' style='position:static'>x</div></div>\n"
                + "    <div id='d1'><div id='d2' style='position:absolute'>x</div></div>\n"
                + "    <div id='e1'><div id='e2' style='position:relative'>x</div></div>\n"
                + "    <div id='f1' style='position:fixed'><div id='f2'>x</div></div>\n"
                + "    <div id='g1' style='position:static'><div id='g2'>x</div></div>\n"
                + "    <div id='h1' style='position:absolute'><div id='h2'>x</div></div>\n"
                + "    <div id='i1' style='position:relative'><div id='i2'>x</div></div>\n"
                + "    <table id='table'>\n"
                + "      <tr id='tr'>\n"
                + "        <td id='td'>\n"
                + "          <div id='j1'><div id='j2'>x</div></div>\n"
                + "          <div id='k1'><div id='k2' style='position:fixed'>x</div></div>\n"
                + "          <div id='l1'><div id='l2' style='position:static'>x</div></div>\n"
                + "          <div id='m1'><div id='m2' style='position:absolute'>x</div></div>\n"
                + "          <div id='n1'><div id='n2' style='position:relative'>x</div></div>\n"
                + "        </td>\n"
                + "      </tr>\n"
                + "    </table>\n"
                + "    <script>\n"
                + "      function alertOffsetParentId(id) {\n"
                + "        try {\n"
                + "          alert(document.getElementById(id).offsetParent.id);\n"
                + "        } catch (e) { alert('exception'); }\n"
                + "      }\n"
                + "      function test() {\n"
                + "        alert(document.getElementById('body').offsetParent);  // null (FF) null (IE)\n"
                + "        alertOffsetParentId('a2'); // body body \n"
                + "        alertOffsetParentId('b2'); // body exception \n"
                + "        alertOffsetParentId('c2'); // body body \n"
                + "        alertOffsetParentId('d2'); // body body \n"
                + "        alertOffsetParentId('e2'); // body body \n"
                + "        alertOffsetParentId('f2'); // f1   f1 \n"
                + "        alertOffsetParentId('g2'); // body body \n"
                + "        alertOffsetParentId('h2'); // h1   h1   \n"
                + "        alertOffsetParentId('i2'); // i1   i1   \n"
                + "        alertOffsetParentId('j2'); // td   td   \n"
                + "        alertOffsetParentId('k2'); // body exception   \n"
                + "        alertOffsetParentId('l2'); // td   td   \n"
                + "        alertOffsetParentId('m2'); // body body \n"
                + "        alertOffsetParentId('n2'); // body body \n"
                + "      }\n"
                + "    </script>\n"
                + "  </body>\n"
                + "</html>";

        final String[] messages = {null, "body", "exception", "body", "body", "body",
                "f1", "body", "h1", "i1", "td", "exception", "td", "body", "body"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>prototype.</p>
     */
    @Test
    public void prototype() {
        final String html = "<html><head><title>Prototype test</title>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var d = document.getElementById('foo');\n"
                + "  alert(d.foo);\n"
                + "  alert(d.myFunction);\n"
                + "  var link = document.getElementById('testLink');\n"
                + "  alert(link.foo);\n"
                + "  alert(link.myFunction);\n"
                + "  HTMLElement.prototype.foo = 123;\n"
                + "  alert(HTMLElement.foo);\n"
                + "  HTMLElement.prototype.myFunction = function() { return 'from myFunction'; };\n"
                + "  alert(d.foo);\n"
                + "  alert(d.myFunction());\n"
                + "  alert(link.foo);\n"
                + "  alert(link.myFunction());\n"
                + "}\n"
                + "</script></head><body onload='test()''>\n"
                + "<div id='foo'>bla</div>\n"
                + "<a id='testLink' href='foo'>bla</a>\n"
                + "</body></html>";

        final String[] messages = {"undefined", "undefined", "undefined", "undefined",
                "undefined", "123", "from myFunction", "123", "from myFunction"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>prototype_Element.</p>
     */
    @Test
    public void prototype_Element() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  Element.prototype.selectNodes = function(sExpr){\n"
                + "    alert('in selectNodes');\n"
                + "  }\n"
                + "  document.getElementById('myDiv').selectNodes();\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";

        final String[] messages = {"in selectNodes"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>instanceOf.</p>
     */
    @Test
    public void instanceOf() {
        final String html = "<html><head><title>instanceof test</title>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var d = document.getElementById('foo');\n"
                + "  alert(d instanceof HTMLDivElement);\n"
                + "  var link = document.getElementById('testLink');\n"
                + "  alert(link instanceof HTMLAnchorElement);\n"
                + "}\n"
                + "</script></head><body onload='test()''>\n"
                + "<div id='foo'>bla</div>\n"
                + "<a id='testLink' href='foo'>bla</a>\n"
                + "</body></html>";
        final String[] messages = {"true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>parentElement.</p>
     */
    @Test
    public void parentElement() {
        final String html
                = "<html id='htmlID'>\n"
                + "<head>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div id='divID'/>\n"
                + "<script language=\"javascript\">\n"
                + "  alert(document.getElementById('htmlID').parentElement);\n"
                + "  alert(document.getElementById('divID' ).parentElement);\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {null, "[object HTMLBodyElement]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>currentStyle.</p>
     */
    @Test
    public void currentStyle() {
        style("currentStyle");
    }

    /**
     * <p>runtimeStyle.</p>
     */
    @Test
    public void runtimeStyle() {
        style("runtimeStyle");
    }

    private void style(final String styleProperty) {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('myDiv');\n"
                + "    var style = elem." + styleProperty + ";\n"
                + "    alert(style);\n"
                + "    if (style) { alert(style.color); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='myDiv'></div>\n"
                + "</body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientLeftTop.</p>
     */
    @Test
    public void clientLeftTop() {
        final String html = "<html><body>"
                + "<div id='div1'>hello</div><script>\n"
                + "  var d1 = document.getElementById('div1');\n"
                + "  alert(d1.clientLeft);\n"
                + "  alert(d1.clientTop);\n"
                + "</script></body></html>";

        final String[] messages = {"0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientLeftTop_documentElement.</p>
     */
    @Test
    public void clientLeftTop_documentElement() {
        final String html =
                "<!DOCTYPE HTML "
                        + "PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n"
                        +"<html>\n"
                        + "<body>"
                        + "<div id='div1'>hello</div><script>\n"
                        + "  var d1 = document.documentElement;\n"
                        + "  alert(d1.clientLeft);\n"
                        + "  alert(d1.clientTop);\n"
                        + "</script></body></html>";
        final String[] messages = {"0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientLeftTopWithBorder.</p>
     */
    @Test
    public void clientLeftTopWithBorder() {
        final String html = "<html><body>"
                + "<div id='div1' style='border: 4px solid black;'>hello</div><script>\n"
                + "  var d1 = document.getElementById('div1');\n"
                + "  alert(d1.clientLeft);\n"
                + "  alert(d1.clientTop);\n"
                + "</script></body></html>";

        final String[] messages = {"4", "4"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getBoundingClientRect.</p>
     */
    @Test
    public void getBoundingClientRect() {
        final String html = "<html><body>\n"
                + "<div id='div1'>hello</div><script>\n"
                + "  var d1 = document.getElementById('div1');\n"
                + "  var pos = d1.getBoundingClientRect();\n"
                + "  alert(pos);\n"
                + "</script></body></html>";

        final String[] messages = {"[object DOMRect]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getBoundingClientRect2.</p>
     */
    @Test
    public void getBoundingClientRect2() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var d1 = document.getElementById('div1');\n"
                + "    var pos = d1.getBoundingClientRect();\n"
                + "    alert(pos.left);\n"
                + "    alert(pos.top);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='outer' style='position: absolute; left: 400px; top: 100px; width: 50px; height: 80px;'>"
                + "<div id='div1'></div></div>"
                + "</body></html>";

        final String[] messages = {"400", "100"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getBoundingClientRect_Scroll.</p>
     */
    @Test
    public void getBoundingClientRect_Scroll() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var d1 = document.getElementById('outer');\n"
                + "    d1.scrollTop = 150;\n"
                + "    var pos = d1.getBoundingClientRect();\n"
                + "    alert(pos.left);\n"
                + "    alert(pos.top);\n"
                + "    d1 = document.getElementById('div1');\n"
                + "    pos = d1.getBoundingClientRect();\n"
                + "    alert(pos.left);\n"
                + "    alert(pos.top);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='outer' "
                + "style='position: absolute; height: 500px; width: 500px; top: 100px; left: 0px; overflow:auto'>\n"
                + "  <div id='div1' "
                + "style='position: absolute; height: 100px; width: 100px; top: 100px; left: 100px; z-index:99;'>"
                + "</div>\n"
                + "  <div id='div2' "
                + "style='position: absolute; height: 100px; width: 100px; top: 100px; left: 300px; z-index:99;'></div>\n"
                + "  <div style='position: absolute; top: 1000px;'>way down</div>\n"
                + "</div>"
                + "</body></html>";

        final String[] messages = {"0", "100", "100", "50"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getBoundingClientRectDisconnected.</p>
     */
    @Test
    public void getBoundingClientRectDisconnected() {
        final String html = "<html>\n"
                + "<head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var d1 = document.createElement('div');\n"
                + "    try {\n"
                + "      var pos = d1.getBoundingClientRect();\n"
                + "      alert(pos);\n"
                + "      alert(pos.left);\n"
                + "      alert(pos.top);\n"
                + "    } catch (e) { alert('exception');}\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"[object DOMRect]", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getClientRects.</p>
     */
    @Test
    public void getClientRects() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var d1 = document.getElementById('div1');\n"
                + "    var rects = d1.getClientRects();\n"
                + "    alert(rects);\n"
                + "    alert(rects.length);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "</body></html>";
        final String[] messages = {"[object DOMRectList]", "1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getClientRectsDisconnected.</p>
     */
    @Test
    public void getClientRectsDisconnected() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var d1 = document.createElement('div');\n"
                        + "    alert(d1.getClientRects());\n"
                        + "    alert(d1.getClientRects().length);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='test()'>\n"
                        + "</body></html>";

        final String[] messages = {"[object DOMRectList]", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getClientRectsDisplayNone.</p>
     */
    @Test
    public void getClientRectsDisplayNone() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var d1 = document.getElementById('div1');\n"
                        + "    display(d1);\n"
                        + "    var d2 = document.getElementById('div2');\n"
                        + "    display(d2);\n"
                        + "  }\n"
                        + "\n"
                        + "  function display(elem) {\n"
                        + "    alert(elem.getClientRects());\n"
                        + "    alert(elem.getClientRects().length);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='div1' style='display: none'>\n"
                        + "    <div id='div2' />\n"
                        + "  </div>\n"
                        + "</body></html>";

        final String[] messages = {"[object DOMRectList]", "0", "[object DOMRectList]", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>innerHTML_parentNode.</p>
     */
    @Test
    public void innerHTML_parentNode() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.createElement('div');\n"
                + "    alert(div1.parentNode);\n"
                + "    div1.innerHTML = '<p>hello</p>';\n"
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

    /**
     * <p>innerText_parentNode.</p>
     */
    @Test
    public void innerText_parentNode() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.createElement('div');\n"
                + "    alert(div1.parentNode);\n"
                + "    div1.innerText = '<p>hello</p>';\n"
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

    /**
     * <p>uniqueID.</p>
     */
    @Test
    public void uniqueID() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('div1');\n"
                + "    var div2 = document.getElementById('div2');\n"
                + "    alert(div1.uniqueID == undefined);\n"
                + "    alert(div1.uniqueID == div1.uniqueID);\n"
                + "    alert(div1.uniqueID == div2.uniqueID);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "  <div id='div2'/>\n"
                + "</body></html>";

        final String[] messages = {"true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>uniqueIDFormatIE.</p>
     */
    @Test
    public void uniqueIDFormatIE() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('div1');\n"
                + "    var div2 = document.getElementById('div2');\n"
                + "    var id2 = div2.uniqueID;\n"
                + "    //as id2 is retrieved before getting id1, id2 should be < id1;\n"
                + "    var id1 = div1.uniqueID;\n"
                + "    if (id1 === undefined) { alert('undefined'); return }\n"
                + "    alert(id1.substring(0, 6) == 'ms__id');\n"
                + "    var id1Int = parseInt(id1.substring(6, id1.length));\n"
                + "    var id2Int = parseInt(id2.substring(6, id2.length));\n"
                + "    alert(id2Int < id1Int);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "  <div id='div2'/>\n"
                + "</body></html>";

        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setExpression.</p>
     */
    @Test
    public void setExpression() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var div1 = document.getElementById('div1');\n"
                + "      div1.setExpression('title','id');\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "</body></html>";
        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>removeExpression.</p>
     */
    @Test
    public void removeExpression() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('div1');\n"

                + "    try {\n"
                + "      div1.setExpression('title','id');\n"
                + "    } catch(e) { alert('ex setExpression'); }\n"

                + "    try {\n"
                + "      div1.removeExpression('title');\n"
                + "    } catch(e) { alert('ex removeExpression'); }\n"

                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "</body></html>";
        final String[] messages = {"ex setExpression", "ex removeExpression"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>dispatchEvent.</p>
     */
    @Test
    public void dispatchEvent() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function foo() {\n"
                        + "  var e = document.createEvent('MouseEvents');\n"
                        + "  e.initMouseEvent('click', true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var canceled = !d.dispatchEvent(e);\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='foo()'><div id='d' onclick='alert(\"clicked\")'>foo</div></body>\n"
                        + "</html>";

        final String[] messages = {"clicked"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>hasAttribute.</p>
     */
    @Test
    public void hasAttribute() {
        final String html
                = "<!DOCTYPE html>\n"
                +"<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elt = document.body;\n"
                + "    alert(elt.hasAttribute('onload'));\n"
                + "    alert(elt.hasAttribute('onLoad'));\n"
                + "    alert(elt.hasAttribute('foo'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";
        final String[] messages = {"true", "true", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>hasAttributeTypeOf.</p>
     */
    @Test
    public void hasAttributeTypeOf() {
        final String html
                = "<!DOCTYPE html>\n"
               + "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elt = document.body;\n"
                + "    alert(typeof elt.hasAttribute);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";
        final String[] messages = {"function"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>hasAttributeQuirksMode.</p>
     */
    @Test
    public void hasAttributeQuirksMode() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var elt = document.body;\n"
                        + "    alert(typeof elt.hasAttribute);\n"
                        + "    alert(elt.hasAttribute('onload'));\n"
                        + "    alert(elt.hasAttribute('onLoad'));\n"
                        + "    alert(elt.hasAttribute('foo'));\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";
        final String[] messages = {"function", "true", "true", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getComponentVersion.</p>
     */
    @Test
    public void getComponentVersion() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  alert(document.body.cpuClass);\n"
                + "  document.body.style.behavior = 'url(#default#clientCaps)';\n"
                + "  alert(document.body.cpuClass);\n"
                + "  if (document.body.getComponentVersion) {\n"
                + "    var ver=document.body.getComponentVersion('{E5D12C4E-7B4F-11D3-B5C9-0050045C3C96}','ComponentID');\n"
                + "    alert(ver.length);\n"
                + "  }\n"
                + "  document.body.style.behavior = '';\n"
                + "  alert(document.body.cpuClass);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientWidthAndHeight.</p>
     */
    @Test
    public void clientWidthAndHeight() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var myDiv = document.getElementById('myDiv');\n"
                        + "    alert(myDiv.clientWidth);\n"
                        + "    alert(myDiv.clientHeight);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "<style>#myDiv { width:30px; height:40px; padding:3px; border:5px; margin:7px; }</style>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myDiv'/>\n"
                        + "</body></html>";

        final String[] messages = {"36", "46"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientWidthAndHeightPositionAbsolute.</p>
     */
    @Test
    public void clientWidthAndHeightPositionAbsolute() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var div = document.getElementById('myDiv');\n"
                        + "    var absDiv = document.getElementById('myAbsDiv');\n"

                        // + "    alert(div.clientWidth +', ' + absDiv.clientWidth);\n"
                        + "    alert(div.clientWidth > 100);\n"
                        + "    alert(absDiv.clientWidth > 10);\n"
                        + "    alert(absDiv.clientWidth < 100);\n"

                        // + "    alert(div.clientHeight +', ' + absDiv.clientHeight);\n"
                        + "    alert(div.clientHeight > 10);\n"
                        + "    alert(div.clientHeight == absDiv.clientHeight);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myDiv'>Test</div>\n"
                        + "  <div id='myAbsDiv' style='position: absolute'>Test</div>\n"
                        + "</body></html>";

        final String[] messages = {"true", "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientWidthAndHeightPositionAbsoluteEmpty.</p>
     */
    @Test
    public void clientWidthAndHeightPositionAbsoluteEmpty() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var absDiv = document.getElementById('myAbsDiv');\n"
                        + "    alert(absDiv.clientWidth);\n"
                        + "    alert(absDiv.clientHeight);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myAbsDiv' style='position: absolute'></div>\n"
                        + "</body></html>";

        final String[] messages = {"0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scrollWidthAndHeight.</p>
     */
    @Test
    public void scrollWidthAndHeight() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var myDiv = document.getElementById('myDiv');\n"
                        + "    alert(myDiv1.scrollWidth >= myDiv1.clientWidth);\n"
                        + "    alert(myDiv1.scrollHeight >= myDiv1.clientHeight);\n"

                        + "    alert(myDiv2.scrollWidth >= myDiv1.scrollWidth);\n"
                        + "    alert(myDiv2.scrollHeight >= myDiv1.scrollHeight);\n"
                        + "    alert(myDiv2.scrollWidth >= myDiv2.clientWidth);\n"
                        + "    alert(myDiv2.scrollHeight >= myDiv2.clientHeight);\n"

                        + "    alert(myDiv3.scrollWidth >= myDiv2.scrollWidth);\n"
                        + "    alert(myDiv3.scrollHeight >= myDiv2.scrollHeight);\n"
                        + "    alert(myDiv3.scrollWidth >= myDiv3.clientWidth);\n"
                        + "    alert(myDiv3.scrollHeight >= myDiv3.clientHeight);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myDiv1'/>\n"
                        + "  <div id='myDiv2' style='height: 42px; width: 42px' />\n"
                        + "  <div id='myDiv3' style='height: 7em; width: 7em' />\n"
                        + "</body></html>";

        final String[] messages = {"true", "true", "false", "true", "true", "true", "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scrollWidthAndHeightDisplayNone.</p>
     */
    @Test
    public void scrollWidthAndHeightDisplayNone() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var myDiv = document.getElementById('myDiv');\n"
                        + "    alert(myDiv.scrollWidth);\n"
                        + "    alert(myDiv.scrollHeight);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myDiv' style='display: none;' />\n"
                        + "</body></html>";
        final String[] messages = {"0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scrollWidthAndHeightDetached.</p>
     */
    @Test
    public void scrollWidthAndHeightDetached() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var myDiv = document.createElement('div');\n"
                        + "    alert(myDiv.scrollWidth);\n"
                        + "    alert(myDiv.scrollHeight);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body></html>";

        final String[] messages = {"0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>class_className_attribute.</p>
     */
    @Test
    public void class_className_attribute() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var e = document.getElementById('pid');\n"
                + "  alert(e.getAttribute('class'));\n"
                + "  alert(e.getAttribute('className'));\n"
                + "  alert(e.getAttributeNode('class'));\n"
                + "  alert(e.getAttributeNode('className'));\n"
                + "  e.setAttribute('className', 'byClassname');\n"
                + "  alert(e.getAttribute('class'));\n"
                + "  alert(e.getAttribute('className'));\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p id='pid' class='x'>text</p>\n"
                + "</body></html>";

        final String[] messages = {"x", null, "[object Attr]", null, "x", "byClassname"};
        checkHtmlAlert(html, messages);
    }




    /**
     * <p>class_className_attribute2.</p>
     */
    @Test
    public void class_className_attribute2() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var e = document.getElementById('pid');\n"
                + "  alert(e['lang'] + '-' + e['class'] + '-' + e['className']);\n"
                + "  alert(e.getAttribute('lang') + '-' + e.getAttribute('class') + '-' + e.getAttribute('className'));\n"
                + "  alert(e.getAttributeNode('lang') + '-' + e.getAttributeNode('class') + '-' + "
                + "e.getAttributeNode('className'));\n"
                + "  alert(e.attributes.getNamedItem('lang') + '-' + e.attributes.getNamedItem('class') + '-' + "
                + "e.attributes.getNamedItem('className'));\n"
                + "  e.setAttribute('className', 'byClassname');\n"
                + "  alert(e.getAttribute('class') + '-' + e.getAttribute('className'));\n"
                + "  alert(e.getAttributeNode('class') + '-' + e.getAttributeNode('className'));\n"
                + "  e.setAttribute('class', 'byClassname');\n"
                + "  alert(e.getAttribute('class') + '-' + e.getAttribute('className'));\n"
                + "  alert(e.getAttributeNode('class') + '-' + e.getAttributeNode('className'));\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p id='pid' class='x'>text</p>\n"
                + "</body></html>";

        final String[] messages = {"-undefined-x", "null-x-null", "null-[object Attr]-null", "null-[object Attr]-null",
                "x-byClassname", "[object Attr]-[object Attr]", "byClassname-byClassname", "[object Attr]-[object Attr]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>contains.</p>
     */
    @Test
    public void contains() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "try {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  var div2 = document.getElementById('div2');\n"
                + "  var text = div2.firstChild;\n"
                + "  var div3 = document.getElementById('div3');\n"
                + "  alert(div1.contains(div1));\n"
                + "  alert(div1.contains(div2));\n"
                + "  alert(div1.contains(div3));\n"
                + "  alert(div1.contains(div4));\n"
                + "  alert(div2.contains(div1));\n"
                + "  alert(div3.contains(div1));\n"
                + "  alert(div4.contains(div1));\n"
                + "  alert(div2.contains(div3));\n"
                + "  alert(div2.contains(text));\n"
                + "  alert(div3.contains(text));\n"
                + "  alert(text.contains(div3));\n"
                + "} catch(e) { alert('exception'); }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='div1'>\n"
                + "  <div id='div2'>hello\n"
                + "    <div id='div3'>\n"
                + "    </div>\n"
                + "  </div>\n"
                + "</div>\n"
                + "<div id='div4'>\n"
                + "</div>\n"
                + "</body></html>";

        final String[] messages = {"true", "true", "true", "false", "false", "false", "false", "true", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }




    /**
     * <p>contains_invalid_argument.</p>
     */
    @Test
    public void contains_invalid_argument() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  alert(document.body.contains([]));\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>filters.</p>
     */
    @Test
    public void filters() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  var defined = typeof(div1.filters) != 'undefined';\n"
                + "  alert(defined ? 'defined' : 'undefined');\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='div1'>\n"
                + "</div>\n"
                + "</body></html>";

        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>attributes_trimmed.</p>
     */
    @Test
    public void attributes_trimmed() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var div1 = document.body.firstChild;\n"
                + "  alert('>' + div1.className + '<');\n"
                + "  alert('>' + div1.id + '<');\n"
                + "}\n"
                + "</script></head><body onload='test()'>"
                + "<div id=' myId  ' class=' myClass '>\n"
                + "hello"
                + "</div>\n"
                + "</body></html>";

        final String[] messages = {"> myClass <", "> myId  <"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByClassName.</p>
     */
    @Test
    public void getElementsByClassName() {
        final String html
                = "<html><head><title>First</title><script>\n"
                + "function test(x) {\n"
                + "  var b = document.body;\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  var s = x + ' => body: ' + b.getElementsByClassName(x).length;\n"
                + "  s += ', div1: ' + div1.getElementsByClassName(x).length;\n"
                + "  alert(s);\n"
                + "}\n"
                + "function doTest() {\n"
                + "  var b = document.body;\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  alert(typeof document.body.getElementsByClassName);\n"
                + "  test('*');\n"
                + "  test('foo');\n"
                + "  test('foo red');\n"
                + "  test('red foo');\n"
                + "  test('blue foo');\n"
                + "  test(null);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div class='foo' id='div1'>\n"
                + "  <span class='c2'>hello</span>\n"
                + "  <span class='foo' id='span2'>World!</span>\n"
                + "</div>\n"
                + "<span class='foo red' id='span3'>again</span>\n"
                + "<span class='red' id='span4'>bye</span>\n"
                + "</body></html>";

        final String[] messages = {"function", "* => body: 0, div1: 0", "foo => body: 3, div1: 1", "foo red => body: 1, div1: 0",
                "red foo => body: 1, div1: 0", "blue foo => body: 0, div1: 0", "null => body: 0, div1: 0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>parentElement2.</p>
     */
    @Test
    public void parentElement2() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var fragment = document.createDocumentFragment();\n"
                + "  var div = document.createElement('div');\n"
                + "  var bold = document.createElement('b');\n"
                + "  fragment.appendChild(div);\n"
                + "  div.appendChild(bold);\n"
                + "  alert(div.parentElement);\n"
                + "  alert(bold.parentElement);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {null, "[object HTMLDivElement]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>doScroll.</p>
     */
    @Test
    public void doScroll() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    document.documentElement.doScroll('left');\n"
                + "    alert('success');\n"
                + "  } catch (e) {\n"
                + "    alert('exception');\n"
                + "  }\n"
                + "}\n"
                + "test();\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"exception", "exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>removeNode.</p>
     */
    @Test
    public void removeNode() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var div1 = document.getElementById('div1');\n"
                + "  var div2 = document.getElementById('div2');\n"
                + "  if (!div2.removeNode) { alert('removeNode not available'); return }\n"

                + "  alert(div1.firstChild.id);\n"
                + "  alert(div2.removeNode().firstChild);\n"
                + "  alert(div1.firstChild.id);\n"
                + "  alert(div1.firstChild.nextSibling.id);\n"
                + "\n"
                + "  var div5 = document.getElementById('div5');\n"
                + "  var div6 = document.getElementById('div6');\n"
                + "  alert(div5.firstChild.id);\n"
                + "  alert(div6.removeNode(true).firstChild.id);\n"
                + "  alert(div5.firstChild);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1'><div id='div2'><div id='div3'></div><div id='div4'></div></div></div>\n"
                + "  <div id='div5'><div id='div6'><div id='div7'></div><div id='div8'></div></div></div>\n"
                + "</body></html>";

        final String[] messages = {"div2", null, "div3", "div4", "div6", "div7", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>document.</p>
     */
    @Test
    public void document() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  alert(document.body.document === document);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>prototype_innerHTML.</p>
     */
    @Test
    public void prototype_innerHTML() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "try {\n"
                + "  alert(HTMLElement.prototype.innerHTML);\n"
                + "} catch (e) { alert('exception call') }\n"
                + "try {\n"
                + "  var myFunc = function() {};\n"
                + "  HTMLElement.prototype.innerHTML = myFunc;\n"
                + "  alert(HTMLElement.prototype.innerHTML == myFunc);\n"
                + "} catch (e) { alert('exception set') }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"exception call", "exception set"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setColorAttribute.</p>
     */
    @Test
    public void setColorAttribute() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(b.vLink);\n"
                        + "        document.vlinkColor = '#0000aa';\n"
                        + "        alert(b.vLink);\n"
                        + "        document.vlinkColor = 'x';\n"
                        + "        alert(b.vLink);\n"
                        + "        document.vlinkColor = 'BlanchedAlmond';\n"
                        + "        alert(b.vLink);\n"
                        + "        document.vlinkColor = 'aBlue';\n"
                        + "        alert(b.vLink);\n"
                        + "        document.vlinkColor = 'bluex';\n"
                        + "        alert(b.vLink);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        final String[] messages = {null, "#0000aa", "x", "BlanchedAlmond", "aBlue", "bluex"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>innerHTMLwithQuotes.</p>
     */
    @Test
    public void innerHTMLwithQuotes() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.getElementById('foo').innerHTML);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div id='foo'><span onclick=\"var f = &quot;hello&quot; + 'world'\">test span</span></div>\n"
                + "</body></html>";

        final String[] messages = {"<span onclick=\"var f = &quot;hello&quot; + 'world'\">test span</span>"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>attributeNS.</p>
     */
    @Test
    public void attributeNS() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var e = document.getElementById('foo');\n"
                + "    alert(e.getAttribute('type'));\n"
                + "    try {\n"
                + "      alert(e.getAttributeNS('bar', 'type'));\n"
                + "      alert(e.hasAttributeNS('bar', 'type'));\n"
                + "      e.removeAttributeNS('bar', 'type');\n"
                + "      alert(e.hasAttribute('type'));\n"
                + "    } catch (e) {alert('getAttributeNS() not supported')}\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='foo' type='button' value='someValue'>\n"
                + "</body></html>";

        final String[] messages = {"button", null, "false", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>dataset.</p>
     */
    @Test
    public void dataset() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.body.dataset);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"[object DOMStringMap]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAttribute_className.</p>
     */
    @Test
    public void setAttribute_className() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    div.setAttribute('className', 't');\n"
                + "    alert(div.className);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        final String[] messages = {""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAttribute_class.</p>
     */
    @Test
    public void setAttribute_class() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    div.setAttribute('class', 't');\n"
                + "    alert(div.className);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        final String[] messages = {"t"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAttribute_className_standards.</p>
     */
    @Test
    public void setAttribute_className_standards() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    div.setAttribute('className', 't');\n"
                + "    alert(div.className);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        final String[] messages = {""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAttribute_class_standards.</p>
     */
    @Test
    public void setAttribute_class_standards() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    div.setAttribute('class', 't');\n"
                + "    alert(div.className);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        final String[] messages = {"t"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttribute2.</p>
     */
    @Test
    public void getAttribute2() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var form = document.getElementById('testForm');\n"
                + "    alert(form.getAttribute('target'));\n"
                + "    alert(form.target);\n"
                + "    alert(form.getAttribute('target222'));\n"
                + "    alert(form.target222);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<form id='testForm' action='#' method='get'>\n"
                + "</form>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {null, "", null, "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttribute2_standards.</p>
     */
    @Test
    public void getAttribute2_standards() {
        final String html = "<head>\n"
                + "  <title>test</title>\n"
                + "  <script>\n"
                + "  function doTest() {\n"
                + "    var form = document.getElementById('testForm');\n"
                + "    alert(form.getAttribute('target'));\n"
                + "    alert(form.target);\n"
                + "    alert(form.getAttribute('target222'));\n"
                + "    alert(form.target222);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<form id='testForm' action='#' method='get'>\n"
                + "</form>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {null, "", null, "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>nodeNameVsOuterElement.</p>
     */
    @Test
    public void nodeNameVsOuterElement() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.createElement('div').tagName);\n"
                + "      alert(document.createElement('section').tagName);\n"
                + "      alert(document.createElement('div').cloneNode( true ).outerHTML);\n"
                + "      alert(document.createElement('section').cloneNode( true ).outerHTML);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"DIV", "SECTION", "<div></div>", "<section></section>"};
        checkHtmlAlert(html, messages);
    }




    /**
     * <p>getSetAttribute_in_xml.</p>
     */
    @Test
    public void getSetAttribute_in_xml() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
                + "    text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
                + "    text += '  <xsl:template match=\"/\">\\n';\n"
                + "    text += \"  <html xmlns='http://www.w3.org/1999/xhtml'>\\n\";\n"
                + "    text += '    <body>\\n';\n"
                + "    text += '    </body>\\n';\n"
                + "    text += '  </html>\\n';\n"
                + "    text += '  </xsl:template>\\n';\n"
                + "    text += '</xsl:stylesheet>';\n"
                + "    if (window.ActiveXObject) {\n"
                + "      var doc=new ActiveXObject('Microsoft.XMLDOM');\n"
                + "      doc.async = false;\n"
                + "      doc.loadXML(text);\n"
                + "    } else {\n"
                + "      var parser=new DOMParser();\n"
                + "      var doc=parser.parseFromString(text,'text/xml');\n"
                + "    }\n"
                + "    var elem = doc.documentElement.getElementsByTagName('html').item(0);\n"
                + "    alert(elem.getAttribute('hi'));\n"
                + "    elem.setAttribute('hi', 'ho');\n"
                + "    alert(elem.getAttribute('hi'));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {null, "ho"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>textContentShouldNotDetachNestedNode.</p>
     */
    @Test
    public void textContentShouldNotDetachNestedNode() {
        final String html = "<html><body><div><div id='it'>foo</div></div><script>\n"
                + "  var elt = document.getElementById('it');\n"
                + "  alert(elt.firstChild);\n"
                + "  elt.parentNode.textContent = '';\n"
                + "  alert(elt.firstChild);\n"
                + "</script></body></html>";

        final String[] messages = {"[object Text]", "[object Text]"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>innerHTML_svg.</p>
     */
    @Test
    public void innerHTML_svg() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var div = document.createElement('div');\n"
                + "      document.body.appendChild(div);\n"
                + "      var svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');\n"
                + "      svg.setAttribute('id', 'svgElem2');\n"
                + "      div.appendChild(svg);\n"
                + "      alert(div.innerHTML);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"<svg id=\"svgElem2\"></svg>"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>appendChildExecuteJavaScript.</p>
     */
    @Test
    public void appendChildExecuteJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('script');\n"
                + "    try {\n"
                + "      newnode.appendChild(document.createTextNode('alerter();'));\n"
                + "      var outernode = document.getElementById('myNode');\n"
                + "      outernode.appendChild(newnode);\n"
                + "    } catch(e) { alert('exception-append'); }\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {"executed"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>appendChildExecuteNestedJavaScript.</p>
     */
    @Test
    public void appendChildExecuteNestedJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('div');\n"
                + "    var newscript = document.createElement('script');\n"
                + "    newnode.appendChild(newscript);\n"
                + "    try {\n"
                + "      newscript.appendChild(document.createTextNode('alerter();'));\n"
                + "      var outernode = document.getElementById('myNode');\n"
                + "      outernode.appendChild(newnode);\n"
                + "    } catch(e) { alert('exception-append'); }\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {"executed"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>appendChildDeclareJavaScript.</p>
     */
    @Test
    public void appendChildDeclareJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('script');\n"
                + "    newnode.appendChild(document.createTextNode('function tester() { alerter(); }'));\n"
                + "    var outernode = document.getElementById('myNode');\n"
                + "    outernode.appendChild(newnode);\n"
                + "    tester();\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('declared');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {"declared"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>insertBeforeExecuteJavaScript.</p>
     */
    @Test
    public void insertBeforeExecuteJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('script');\n"
                + "    try {\n"
                + "      newnode.appendChild(document.createTextNode('alerter();'));\n"
                + "      var outernode = document.getElementById('myNode');\n"
                + "      outernode.insertBefore(newnode, null);\n"
                + "    } catch(e) { alert('exception-append'); }\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {"executed"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>insertBeforeExecuteNestedJavaScript.</p>
     */
    @Test
    public void insertBeforeExecuteNestedJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('div');\n"
                + "    var newscript = document.createElement('script');\n"
                + "    newnode.appendChild(newscript);\n"
                + "    try {\n"
                + "      newscript.appendChild(document.createTextNode('alerter();'));\n"
                + "      var outernode = document.getElementById('myNode');\n"
                + "      outernode.insertBefore(newnode, null);\n"
                + "    } catch(e) { alert('exception-append'); }\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {"executed"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>insertBeforeDeclareJavaScript.</p>
     */
    @Test
    public void insertBeforeDeclareJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('script');\n"
                + "    newnode.appendChild(document.createTextNode('function tester() { alerter(); }'));\n"
                + "    var outernode = document.getElementById('myNode');\n"
                + "    outernode.insertBefore(newnode, null);\n"
                + "    tester();\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('declared');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {"declared"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>replaceChildExecuteJavaScript.</p>
     */
    @Test
    public void replaceChildExecuteJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('script');\n"
                + "    try {\n"
                + "      newnode.appendChild(document.createTextNode('alerter();'));\n"
                + "      var outernode = document.getElementById('myNode');\n"
                + "      outernode.replaceChild(newnode, document.getElementById('inner'));\n"
                + "    } catch(e) { alert('exception-append'); }\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'><div id='inner'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"executed"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>replaceChildExecuteNestedJavaScript.</p>
     */
    @Test
    public void replaceChildExecuteNestedJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('div');\n"
                + "    var newscript = document.createElement('script');\n"
                + "    newnode.appendChild(newscript);\n"
                + "    try {\n"
                + "      newscript.appendChild(document.createTextNode('alerter();'));\n"
                + "      var outernode = document.getElementById('myNode');\n"
                + "      outernode.replaceChild(newnode, document.getElementById('inner'));\n"
                + "    } catch(e) { alert('exception-append'); }\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'><div id='inner'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"executed"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>replaceChildDeclareJavaScript.</p>
     */
    @Test
    public void replaceChildDeclareJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('script');\n"
                + "    newnode.appendChild(document.createTextNode('function tester() { alerter(); }'));\n"
                + "    var outernode = document.getElementById('myNode');\n"
                + "    outernode.replaceChild(newnode, document.getElementById('inner'));\n"
                + "    tester();\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('declared');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'><div id='inner'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"declared"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>insertAdjacentHTML.</p>
     */
    @Test
    public void insertAdjacentHTML() {
        insertAdjacentHTML("beforeend", "afterend", "beforebegin", "afterbegin");
        insertAdjacentHTML("beforeEnd", "afterEnd", "beforeBegin", "afterBegin");
        insertAdjacentHTML("BeforeEnd", "AfterEnd", "BeFoReBeGiN", "afterbegin");
    }

    private void insertAdjacentHTML(final String beforeEnd,
                                    final String afterEnd, final String beforeBegin, final String afterBegin) {
        final String html = "<html><head><title>First</title>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var oNode = document.getElementById('middle');\n"
                + "  oNode.insertAdjacentHTML('" + beforeEnd + "', ' <span id=3>before-end</span> ');\n"
                + "  oNode.insertAdjacentHTML('" + afterEnd + "', ' <span id=4>after-end</span> ');\n"
                + "  oNode.insertAdjacentHTML('" + beforeBegin + "', ' <span id=1>before-begin</span> ');\n"
                + "  oNode.insertAdjacentHTML('" + afterBegin + "', ' <span id=2>after-begin</span> ');\n"
                + "  var coll = document.getElementsByTagName('SPAN');\n"
                + "  for (var i = 0; i < coll.length; i++) {\n"
                + "    alert(coll[i].id);\n"
                + "  }\n"
                + "  var outside = document.getElementById('outside');\n"
                + "  var text = outside.textContent ? outside.textContent : outside.innerText;\n"
                + "  text = text.replace(/(\\r\\n|\\r|\\n)/gm, '');\n"
                + "  text = text.replace(/(\\s{2,}/g, ' ');\n"
                + "  text = text.replace(/^\\s+|\\s+$/g, '');\n"
                + "  alert(text);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <span id='outside' style='color: #00ff00'>\n"
                + "    <span id='middle' style='color: #ff0000'>\n"
                + "      inside\n"
                + "    </span>\n"
                + "  </span>\n"
                + "</body></html>";

        final String[] messages = {"outside", "1", "middle", "2", "3", "4",
                "before-begin after-begin inside before-end after-end"};
        checkHtmlAlert(html, messages);
    }




    /**
     * <p>insertAdjacentHTMLExecuteJavaScript.</p>
     */
    @Test
    public void insertAdjacentHTMLExecuteJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var outernode = document.getElementById('myNode');\n"
                + "    outernode.insertAdjacentHTML('afterend', '<scr'+'ipt>alerter();</scr'+'ipt>');\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {};
        checkHtmlAlert(html, messages);
    }




    /**
     * <p>insertAdjacentHTMLExecuteNestedJavaScript.</p>
     */
    @Test
    public void insertAdjacentHTMLExecuteNestedJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var outernode = document.getElementById('myNode');\n"
                + "    outernode.insertAdjacentHTML('afterend', '<div><scr'+'ipt>alerter();</scr'+'ipt></div>');\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>insertAdjacentHTMLDeclareJavaScript.</p>
     */
    @Test
    public void insertAdjacentHTMLDeclareJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var outernode = document.getElementById('myNode');\n"
                + "    outernode.insertAdjacentHTML('afterend', "
                + "'<scr'+'ipt>function tester() { alerter(); }</scr'+'ipt>');\n"
                + "    try {\n"
                + "      tester();\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('declared');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>insertAdjacentElement.</p>
     */
    @Test
    public void insertAdjacentElement() {
        insertAdjacentElement("beforeend", "afterend", "beforebegin", "afterbegin");
        insertAdjacentElement("beforeEnd", "afterEnd", "beforeBegin", "afterBegin");
        insertAdjacentElement("BeforeEnd", "AfterEnd", "BeFoReBeGiN", "afterbegin");
    }

    private void insertAdjacentElement(final String beforeEnd,
                                       final String afterEnd, final String beforeBegin, final String afterBegin) {
        final String html = "<html><head><title>First</title>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var oNode = document.getElementById('middle');\n"
                + "  if (!oNode.insertAdjacentElement) { alert('insertAdjacentElement not available'); return }\n"

                + "  oNode.insertAdjacentElement('" + beforeEnd + "', makeElement(3, 'before-end'));\n"
                + "  oNode.insertAdjacentElement('" + afterEnd + "', makeElement(4, ' after-end'));\n"
                + "  oNode.insertAdjacentElement('" + beforeBegin + "', makeElement(1, 'before-begin '));\n"
                + "  oNode.insertAdjacentElement('" + afterBegin + "', makeElement(2, ' after-begin'));\n"
                + "  var coll = document.getElementsByTagName('SPAN');\n"
                + "  for (var i = 0; i < coll.length; i++) {\n"
                + "    alert(coll[i].id);\n"
                + "  }\n"
                + "  var outside = document.getElementById('outside');\n"
                + "  var text = outside.textContent ? outside.textContent : outside.innerText;\n"
                + "  text = text.replace(/(\\r\\n|\\r|\\n)/gm, '');\n"
                + "  text = text.replace(/(\\s{2,}/g, ' ');\n"
                + "  text = text.replace(/^\\s+|\\s+$/g, '');\n"
                + "  alert(text);\n"
                + "}\n"
                + "function makeElement(id, value) {\n"
                + "  var span = document.createElement('span');\n"
                + "  span.appendChild(document.createTextNode(value));\n"
                + "  span.id = id;\n"
                + "  return span;\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <span id='outside' style='color: #00ff00'>\n"
                + "    <span id='middle' style='color: #ff0000'>\n"
                + "      inside\n"
                + "    </span>\n"
                + "  </span>\n"
                + "</body></html>";

        final String[] messages = {"outside", "1", "middle", "2", "3", "4", "before-begin after-begin inside before-end after-end"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>insertAdjacentElementExecuteJavaScript.</p>
     */
    @Test
    public void insertAdjacentElementExecuteJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('script');\n"
                + "    newnode.appendChild(document.createTextNode('alerter();'));\n"

                + "    var outernode = document.getElementById('myNode');\n"
                + "    if (!outernode.insertAdjacentElement) { alert('insertAdjacentElement not available'); return }\n"
                + "    outernode.insertAdjacentElement('afterend', newnode);\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";

        final String[] messages = {"executed"};
        checkHtmlAlert(html, messages);
    }




    /**
     * <p>insertAdjacentElementExecuteNestedJavaScript.</p>
     */
    @Test
    public void insertAdjacentElementExecuteNestedJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('div');\n"
                + "    var newscript = document.createElement('script');\n"
                + "    newnode.appendChild(newscript);\n"
                + "    newscript.appendChild(document.createTextNode('alerter();'));\n"

                + "    var outernode = document.getElementById('myNode');\n"
                + "    if (!outernode.insertAdjacentElement) { alert('insertAdjacentElement not available'); return }\n"
                + "    outernode.insertAdjacentElement('afterend', newnode);\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('executed');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";

        final String[] messages = {"executed"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>insertAdjacentElementDeclareJavaScript.</p>
     */
    @Test
    public void insertAdjacentElementDeclareJavaScript() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var newnode = document.createElement('script');\n"
                + "    newnode.appendChild(document.createTextNode('function tester() { alerter(); }'));\n"
                + "    var outernode = document.getElementById('myNode');\n"
                + "    if (!outernode.insertAdjacentElement) { alert('insertAdjacentElement not available'); return }\n"
                + "    outernode.insertAdjacentElement('afterend', newnode);\n"
                + "    tester();\n"
                + "  }\n"
                + "  function alerter() {\n"
                + "    alert('declared');\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";

        final String[] messages = {"declared"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>insertAdjacentText.</p>
     */
    @Test
    public void insertAdjacentText() {
        insertAdjacentText("beforeend", "afterend", "beforebegin", "afterbegin");
        insertAdjacentText("beforeEnd", "afterEnd", "beforeBegin", "afterBegin");
        insertAdjacentText("BeforeEnd", "AfterEnd", "BeFoReBeGiN", "afterbegin");
    }

    private void insertAdjacentText(final String beforeEnd,
                                    final String afterEnd, final String beforeBegin, final String afterBegin) {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  var oNode = document.getElementById('middle');\n"
                + "  if (!oNode.insertAdjacentText) { alert('insertAdjacentText not available'); return }\n"
                + "  oNode.insertAdjacentText('" + beforeEnd + "', 'before-end');\n"
                + "  oNode.insertAdjacentText('" + afterEnd + "', ' after-end');\n"
                + "  oNode.insertAdjacentText('" + beforeBegin + "', 'before-begin ');\n"
                + "  oNode.insertAdjacentText('" + afterBegin + "', ' after-begin');\n"
                + "  var coll = document.getElementsByTagName('SPAN');\n"
                + "  for (var i = 0; i < coll.length; i++) {\n"
                + "    alert(coll[i].id);\n"
                + "  }\n"
                + "  var outside = document.getElementById('outside');\n"
                + "  var text = outside.textContent ? outside.textContent : outside.innerText;\n"
                + "  text = text.replace(/(\\r\\n|\\r|\\n)/gm, '');\n"
                + "  text = text.replace(/(\\s{2,}/g, ' ');\n"
                + "  text = text.replace(/^\\s+|\\s+$/g, '');\n"
                + "  alert(text);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <span id='outside' style='color: #00ff00'>\n"
                + "    <span id='middle' style='color: #ff0000'>\n"
                + "      inside\n"
                + "    </span>\n"
                + "  </span>\n"
                + "</body></html>";

        final String[] messages = {"outside", "middle", "before-begin after-begin inside before-end after-end"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setCapture.</p>
     */
    @Test
    public void setCapture() {
        final String html = "<html><head><title>foo</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('myDiv');\n"
                + "    try {\n"
                + "      div.setCapture();\n"
                + "      div.setCapture(true);\n"
                + "      div.setCapture(false);\n"
                + "      alert('setCapture available');\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";

        final String[] messages = {"setCapture available"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>releaseCapture.</p>
     */
    @Test
    public void releaseCapture() {
        final String html = "<html><head><title>foo</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('myDiv');\n"
                + "    try {\n"
                + "      div.releaseCapture();\n"
                + "      alert('releaseCapture available');\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";
        final String[] messages = {"releaseCapture available"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>contentEditable.</p>
     */
    @Test
    public void contentEditable() {
        final String html =
                "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('myDiv');\n"
                + "    alert(div.contentEditable);\n"
                + "    alert(div.isContentEditable);\n"
                + "    alert(typeof div.contentEditable);\n"
                + "    alert(typeof div.isContentEditable);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";

        final String[] messages = {"inherit", "false", "string", "boolean"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>oninput.</p>
     */
    @Test
    public void oninput() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var testNode = document.createElement('div');\n"
                + "      alert('oninput' in testNode);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>focus.</p>
     */
    @Test
    public void focus() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.activeElement);\n"

                + "      var testNode = document.getElementById('myButton');\n"
                + "      testNode.focus();\n"
                + "      alert(document.activeElement);\n"

                + "      testNode = document.getElementById('myA');\n"
                + "      testNode.focus();\n"
                + "      alert(document.activeElement);\n"

                + "      testNode = document.getElementById('myDiv');\n"
                + "      testNode.focus();\n"
                + "      alert(document.activeElement);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>blah</div>\n"
                + "  <a id='myA' href='http://srv/htmlunit.org'>anchor</a>\n"
                + "  <button id='myButton'>Press</button>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"[object HTMLBodyElement]", "[object HTMLButtonElement]",
                "http://srv/htmlunit.org", "http://srv/htmlunit.org"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>innerHTML.</p>
     */
    @Test
    public void innerHTML() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var select = document.getElementById('myId');\n"
                + "      select.innerHTML = \"<select id='myId2'><option>Two</option></select>\";\n"
                + "      alert(document.body.innerHTML.trim());\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <select id='myId'><option>One</option></select>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"<select id=\"myId\"><option>Two</option></select>"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>innerHTMLGetElementsByTagName.</p>
     */
    @Test
    public void innerHTMLGetElementsByTagName() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var div = document.createElement('div');\n"
                + "      div.innerHTML = \"<table></table><a href='/a'>a</a>\";\n"
                + "      alert(div.getElementsByTagName('a').length);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>hidden.</p>
     */
    @Test
    public void hidden() {
        final String html =
                "<html><head><script>\n"
                        + "  function test() {\n"
                        + "    var d1 = document.getElementById('div1');\n"
                        + "    alert(d1.hidden);\n"
                        + "    var d2 = document.getElementById('div2');\n"
                        + "    alert(d2.hidden);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='div1' style='display: none'>\n"
                        + "  </div>\n"
                        + "  <div id='div2' />\n"
                        + "</body></html>";

        final String[] messages = {"false", "false"};
        checkHtmlAlert(html, messages);
    }
}
