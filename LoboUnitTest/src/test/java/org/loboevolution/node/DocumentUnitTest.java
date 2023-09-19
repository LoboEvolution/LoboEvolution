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
 * Tests for {@link org.loboevolution.html.node.Document}.
 */

public class DocumentUnitTest extends LoboUnitTest {

    @Test
    public void formsAccessor_TwoForms() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.forms.length);\n"
                + "  for(var i = 0; i < document.forms.length; i++) {\n"
                + "    alert(document.forms[i].name);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='text' name='textfield1' value='foo' />\n"
                + "</form>\n"
                + "<form name='form2'>\n"
                + "  <input type='text' name='textfield2' value='foo' />\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"2", "form1", "form2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void formsAccessor_FormWithNoName() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.forms.length);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form>\n"
                + "  <input type='text' name='textfield1' value='foo' />\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void formsAccessor_NoForms() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  alert(document.forms.length);\n"
                + "  for(var i = 0; i < document.forms.length; i++) {\n"
                + "    alert(document.forms[i].name);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "</body></html>";

        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void formsLive() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.forms;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.forms.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<form id='myForm' action='foo.html'>\n"
                        + "</form>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "1", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void anchors() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.anchors;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.anchors.length);\n"
                        + "  alert(document.anchors == oCol);\n"
                        + "  if (document.anchors[0].name)\n"
                        + "    alert('name: ' + document.anchors[0].name);\n"
                        + "  else\n"
                        + "    alert('id: ' + document.anchors[0].id);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<a href='foo.html' id='firstLink'>foo</a>\n"
                        + "<a href='foo2.html'>foo2</a>\n"
                        + "<a name='end'/>\n"
                        + "<a href=''>null2</a>\n"
                        + "<a id='endId'/>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "1", "1", "true", "name: end"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void anchorsEmpty() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.anchors;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.anchors.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void applets() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.applets;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.applets.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<applet id='firstApplet'></applet>\n"
                        + "<applet name='end'></applet>\n"
                        + "<applet id='endId'></applet>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void appletsEmpty() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.applets;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.applets.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void embeds() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.embeds;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.embeds.length);\n"
                        + "  alert(document.embeds[0].id);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<embed id='firstEmbed' />\n"
                        + "<embed name='end' />\n"
                        + "<embed id='endId'/>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "3", "3", "firstEmbed"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void embedsEmpty() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.embeds;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.embeds.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void plugins() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.plugins;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.plugins.length);\n"
                        + "  alert(document.plugins == oCol);\n"
                        + "  alert(document.embeds[0].id);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<embed id='firstEmbed' />\n"
                        + "<embed name='end' />\n"
                        + "<embed id='endId'/>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "3", "3", "true", "firstEmbed"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void pluginsEmpty() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.plugins;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.plugins.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void links() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.links;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.links.length);\n"
                        + "  alert(document.links == oCol);\n"
                        + "  alert(document.links[0].id);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<a href='foo.html' id='firstLink'>foo</a>\n"
                        + "<a href='foo2.html'>foo2</a>\n"
                        + "<a name='end'/>\n"
                        + "<a href=''>null2</a>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "3", "3", "true", "firstLink"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void linksEmpty() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.links;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.links.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void createElement() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function doTest() {\n"
                + "        // Create a DIV element.\n"
                + "        var div1 = document.createElement('div');\n"
                + "        alert('parentNode: ' + div1.parentNode);\n"
                + "        div1.id = 'div1';\n"
                + "        document.body.appendChild(div1);\n"
                + "        alert(div1.tagName);\n"
                + "        alert(div1.nodeType);\n"
                + "        alert(div1.nodeValue);\n"
                + "        alert(div1.nodeName);\n"
                + "        // Create an INPUT element.\n"
                + "        var input = document.createElement('input');\n"
                + "        input.id = 'text1id';\n"
                + "        input.name = 'text1name';\n"
                + "        input.value = 'text1value';\n"
                + "        var form = document.getElementById('form1');\n"
                + "        form.appendChild(input);\n"
                + "        alert(document.getElementById('button1id').value);\n"
                + "        alert(document.getElementById('text1id').value);\n"
                + "        // The default type of an INPUT element is 'text'.\n"
                + "        alert(document.getElementById('text1id').type);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "    <form name='form1' id='form1'>\n"
                + "      <input type='button' id='button1id' name='button1name' value='button1value'/>\n"
                + "      This is form1.\n"
                + "    </form>\n"
                + "  </body>\n"
                + "</html>";

        final String[] messages = {"parentNode: null", "div", "1", null, "div", "button1value", "text1value", "text"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void createTextNode() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var text1=document.createTextNode('Some Text');\n"
                + "  var body1=document.getElementById('body');\n"
                + "  body1.appendChild(text1);\n"
                + "  alert(text1.data);\n"
                + "  alert(text1.length);\n"
                + "  alert(text1.nodeType);\n"
                + "  alert(text1.nodeValue);\n"
                + "  alert(text1.nodeName);\n"
                + "}\n"
                + "</script></head><body onload='doTest()' id='body'>\n"
                + "</body></html>";

        final String[] messages = {"Some Text", "9", "3", "Some Text", "#text"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void appendChild() {
        final String html
                = "<html><head><script>\n"
                + "  function doTest() {\n"
                + "    var form = document.forms['form1'];\n"
                + "    var div = document.createElement('DIV');\n"
                + "    form.appendChild(div);\n"
                + "    var elements = document.getElementsByTagName('DIV');\n"
                + "    alert(elements.length);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void appendChildAtDocumentLevel() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var div = document.createElement('div');\n"
                        + "      div.innerHTML = 'test';\n"
                        + "      try {\n"
                        + "        alert(document.childNodes.length);\n"
                        + "        document.appendChild(div); // Error\n"
                        + "        alert(document.childNodes.length);\n"
                        + "        alert(document.childNodes[0].tagName);\n"
                        + "        alert(document.childNodes[1].tagName);\n"
                        + "        alert(document.getElementsByTagName('div').length);\n"
                        + "      } catch(ex) {\n"
                        + "        alert('exception');\n"
                        + "      }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";

        final String[] messages = {"1", "exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void appendChild_textNode() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var form = document.forms['form1'];\n"
                + "    var child = document.createTextNode('Some Text');\n"
                + "    form.appendChild(child);\n"
                + "    alert(form.lastChild.data);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"Some Text"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cloneNode() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var form = document.forms['form1'];\n"
                + "    var cloneShallow = form.cloneNode(false);\n"
                + "    alert(cloneShallow != null);\n"
                + "    alert(cloneShallow.firstChild == null);\n"
                + "    var cloneDeep = form.cloneNode(true);\n"
                + "    alert(cloneDeep != null);\n"
                + "    alert(cloneDeep.firstChild != null);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'>\n"
                + "<p>hello world</p>\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void insertBefore() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var form = document.forms['form1'];\n"
                + "    var oldChild = document.getElementById('oldChild');\n"
                + "    var div = document.createElement('DIV');\n"
                + "    form.insertBefore(div, oldChild);\n"
                + "    alert(form.firstChild == div);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'><div id='oldChild'/></form>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getElementById_scriptType() {
        final String html
                = "<html><head>\n"
                + "<script id='script1' type='text/javascript'>\n"

                + "  doTest=function() {\n"
                + "  alert(top.document.getElementById('script1').type);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"text/javascript"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentNode_Nested() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var div1=document.getElementById('childDiv');\n"
                + "    alert(div1.parentNode.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'><div id='childDiv'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"parentDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentNode_Document() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    alert(document.parentNode == null);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void parentNode_CreateElement() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var div1=document.createElement('div');\n"
                + "    alert(div1.parentNode == null);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void parentNode_AppendChild() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var childDiv=document.getElementById('childDiv');\n"
                + "    var parentDiv=document.getElementById('parentDiv');\n"
                + "    parentDiv.appendChild(childDiv);\n"
                + "    alert(childDiv.parentNode.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'></div><div id='childDiv'></div>\n"
                + "</body></html>";

        final String[] messages = {"parentDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void documentElement() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    alert(document.documentElement != null);\n"
                + "    alert(document.documentElement.tagName);\n"
                + "    alert(document.documentElement.parentNode == document);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"true", "HTML", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void firstChild_Nested() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var div1=document.getElementById('parentDiv');\n"
                + "    alert(div1.firstChild.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'><div id='childDiv'/><div id='childDiv2'/></div>\n"
                + "</body></html>";

        final String[] messages = {"childDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void firstChild_AppendChild() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var childDiv=document.getElementById('childDiv');\n"
                + "    var parentDiv=document.getElementById('parentDiv');\n"
                + "    parentDiv.appendChild(childDiv);\n"
                + "    var childDiv2=document.getElementById('childDiv2');\n"
                + "    parentDiv.appendChild(childDiv2);\n"
                + "    alert(parentDiv.firstChild.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'/><div id='childDiv'/><div id='childDiv2'/>\n"
                + "</body></html>";

        final String[] messages = {"childDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void lastChild_Nested() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var div1=document.getElementById('parentDiv');\n"
                + "    alert(div1.lastChild.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'><div id='childDiv1'></div><div id='childDiv'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"childDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void lastChild_AppendChild() {
        final String html
                = "<html><head><script>\n"
                + "  function doTest() {\n"
                + "    var childDiv1=document.getElementById('childDiv1');\n"
                + "    var parentDiv=document.getElementById('parentDiv');\n"
                + "    parentDiv.appendChild(childDiv1);\n"
                + "    var childDiv=document.getElementById('childDiv');\n"
                + "    parentDiv.appendChild(childDiv);\n"
                + "    alert(parentDiv.lastChild.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'/><div id='childDiv1'/><div id='childDiv'/>\n"
                + "</body></html>";

        final String[] messages = {"childDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void nextSibling_Nested() {
        final String html
                = "<html><head><script>\n"
                + "  function doTest() {\n"
                + "    var div1 = document.getElementById('previousDiv');\n"
                + "    alert(div1.nextSibling.id);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='parentDiv'><div id='previousDiv'></div><div id='nextDiv'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"nextDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void nextSibling_AppendChild() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var previousDiv=document.getElementById('previousDiv');\n"
                + "    var parentDiv=document.getElementById('parentDiv');\n"
                + "    parentDiv.appendChild(previousDiv);\n"
                + "    var nextDiv=document.getElementById('nextDiv');\n"
                + "    parentDiv.appendChild(nextDiv);\n"
                + "    alert(previousDiv.nextSibling.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'/><div id='junk1'/><div id='previousDiv'/><div id='junk2'/><div id='nextDiv'/>\n"
                + "</body></html>";

        final String[] messages = {"nextDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void previousSibling_Nested() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var div1 = document.getElementById('nextDiv');\n"
                + "    alert(div1.previousSibling.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'><div id='previousDiv'></div><div id='nextDiv'></div></div>\n"
                + "</body></html>";

        final String[] messages = {"previousDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void previousSibling_AppendChild() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var previousDiv=document.getElementById('previousDiv');\n"
                + "    var parentDiv=document.getElementById('parentDiv');\n"
                + "    parentDiv.appendChild(previousDiv);\n"
                + "    var nextDiv=document.getElementById('nextDiv');\n"
                + "    parentDiv.appendChild(nextDiv);\n"
                + "    alert(nextDiv.previousSibling.id);\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='parentDiv'/><div id='junk1'/><div id='previousDiv'/><div id='junk2'/><div id='nextDiv'/>\n"
                + "</body></html>";

        final String[] messages = {"previousDiv"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void allProperty_KeyByName() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      alert(document.all['input1'].value);\n"
                + "      alert(document.all['foo2'].value);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form id='form1'>\n"
                + "    <input id='input1' name='foo1' type='text' value='tangerine' />\n"
                + "    <input id='input2' name='foo2' type='text' value='ginger' />\n"
                + "  </form>\n"
                + "</body></html>";

        final String[] messages = {"tangerine", "ginger"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void allProperty_CalledDuringPageLoad() {
        final String html
                = "<html><body>\n"
                + "<div id='ARSMenuDiv1' style='VISIBILITY: hidden; POSITION: absolute; z-index: 1000000'></div>\n"
                + "<script language='Javascript'>\n"

                + "  var divObj = document.all['ARSMenuDiv1'];\n"
                + "  alert(divObj.tagName);\n"
                + "</script></body></html>";

        final String[] messages = {"DIV"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void referrer_NoneSpecified() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='alert(document.referrer);'>\n"
                + "</form></body></html>";

        final String[] messages = {null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getElementsByTagName() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var elements = document.getElementsByTagName('input');\n"
                + "    for (var i = 0; i < elements.length; i++) {\n"
                + "      alert(elements[i].type);\n"
                + "      alert(elements.item(i).type);\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form><input type='button' name='button1' value='pushme'></form>\n"
                + "</body></html>";

        final String[] messages = {"button", "button"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getElementsByTagNameXml() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "</head><body>\n"
                + "<script>\n"
                + "  var xmlString = [\n"
                + "                 '<ResultSet>',\n"
                + "                 '<Result>One</Result>',\n"
                + "                 '<RESULT>Two</RESULT>',\n"
                + "                 '<result><nested>Three</nested></result>',\n"
                + "                 '<result>Four</result>',\n"
                + "                 '</ResultSet>'\n"
                + "                ].join('');\n"
                + "  if (window.DOMParser) {\n"
                + "    var parser = new DOMParser();\n"
                + "    xml = parser.parseFromString(xmlString, 'text/xml');\n"
                + "  } else { // IE\n"
                + "    var parser = new ActiveXObject('Microsoft.XMLDOM');\n"
                + "    parser.async = 'false';\n"
                + "    parser.loadXML(xmlString);\n"
                + "  }\n"
                + "  var xmlDoc = parser.parseFromString(xmlString, 'text/xml');\n"
                + "  try {\n"
                + "    var res = xmlDoc.getElementsByTagName('result');\n"
                + "    alert(res.length);\n"
                + "    alert(res[0].innerHTML);\n"
                + "    alert(res[1].innerHTML);\n"
                + "    res = xmlDoc.getElementsByTagName('RESULT');\n"
                + "    alert(res.length);\n"
                + "    alert(res[0].innerHTML);\n"
                + "    res = xmlDoc.getElementsByTagName('resulT');\n"
                + "    alert(res.length);\n"
                + "    res = xmlDoc.getElementsByTagName('rEsulT');\n"
                + "    alert(res.length);\n"
                + "  } catch(e) {alert('exception ' + e)}\n"
                + "</script></body></html>";

        final String[] messages = {"2", "<nested>Three</nested>", "Four", "1", "Two", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void all_WithParentheses() {
        final String html
                = "<html><head><title></title>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var length = document.all.length;\n"
                + "  for(i = 0; i < length; i++) {\n"
                + "    try {\n"
                + "      var all = document.all[i];\n"
                + "      if (all == null) {\n"
                + "        alert('all == null');\n"
                + "      } else {\n"
                + "        alert(all.tagName);\n"
                + "      }\n"
                + "    } catch(e) { alert(e); }\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"HTML", "HEAD", "TITLE", "SCRIPT", "BODY"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void all_IndexByInt() {
        final String html
                = "<html><head><title></title>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var length = document.all.length;\n"
                + "  for(i = 0; i < length; i++) {\n"
                + "    alert(document.all[i].tagName);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"HTML", "HEAD", "TITLE", "SCRIPT", "BODY"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void all_Item() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  alert(document.all.item(0).tagName);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"HTML"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void all_NamedItem_Unknown() {
        final String[] messages = {null};
        namedItem("foo", messages);
    }

    @Test
    public void all_NamedItem_ById() {
        final String[] messages = {"form1<->"};
        namedItem("form1", messages);
    }

    @Test
    public void all_NamedItem_ByName_formWithoutId() {
        final String[] messages = {"<->form2"};
        namedItem("form2", messages);
    }

    @Test
    public void all_NamedItem_ByName() {
        final String[] messages = {"f3<->form3"};
        namedItem("form3", messages);
    }

    @Test
    public void all_NamedItem_DuplicateId() {
        final String[] messages = {"coll 2", "f4<->form4_1", "f4<->form4_2"};
        namedItem("f4", messages);
    }

    @Test
    public void all_NamedItem_DuplicateName() {
        final String[] messages = {"coll 2", "f5_1<->form5", "f5_2<->form5"};
        namedItem("form5", messages);
    }

    @Test
    public void all_NamedItem_DuplicateIdName() {
        final String[] messages = {"coll 2", "f6<->form6", "form6<->form6_2"};
        namedItem("form6", messages);
    }

    private void namedItem(final String name, final String[] messages) {
        final String html
                = "<!doctype html>\n"
                + "<html><head>\n"
                + "<script>\n"
                + "  var res = '';"
                + "  function alert(msg) { res += msg + '§';}\n"
                + "  function doTest() {\n"
                + "    var result = document.all.namedItem('" + name + "');\n"
                + "    if (result == null) {\n"
                + "      alert(result);\n"
                + "    } else if (result.id || result.name) {\n"
                + "      alert(result.id + '<->' + result.name);\n"
                + "    } else {\n"
                + "      alert('coll ' + result.length);\n"
                + "      for(i = 0; i < result.length; i++) {\n"
                + "        alert(result.item(i).id + '<->' + result.item(i).name);\n"
                + "      }\n"
                + "    }\n"
                + "    window.document.title = res;"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form id='form1'></form>\n"
                + "  <form name='form2'></form>\n"
                + "  <form id='f3' name='form3'></form>\n"
                + "  <form id='f4' name='form4_1'></form>\n"
                + "  <form id='f4' name='form4_2'></form>\n"
                + "  <form id='f5_1' name='form5'></form>\n"
                + "  <form id='f5_2' name='form5'></form>\n"
                + "  <form id='f6' name='form6'></form>\n"
                + "  <form id='form6' name='form6_2'></form>\n"
                + "</body></html>";

        checkHtmlAlert(html, messages);
    }

    @Test
    public void all_tags() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  try {\n"
                + "    var inputs = document.all.tags('input');\n"
                + "    var inputCount = inputs.length;\n"
                + "    for(i = 0; i < inputCount; i++) {\n"
                + "      alert(inputs[i].name);\n"
                + "    }\n"
                + "    // Make sure tags() returns an element array that you can call item() on.\n"
                + "    alert(document.all.tags('input').item(0).name);\n"
                + "    alert(document.all.tags('input').item(1).name);\n"
                + "    // Make sure tags() returns an empty element array if there are no matches.\n"
                + "    alert(document.all.tags('xxx').length);\n"
                + "  } catch (e) { alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<input type='text' name='a' value='1'>\n"
                + "<input type='text' name='b' value='1'>\n"
                + "</body></html>";

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }
    @Test
    public void all_Caching() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='alert(document.all.b.value)'>\n"
                + "<input type='text' name='a' value='1'>\n"
                + "<script>alert(document.all.a.value)</script>\n"
                + "<input type='text' name='b' value='2'>\n"
                + "</body></html>";

        final String[] messages = {"1", "2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getElementsByName() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var elements = document.getElementsByName('name1');\n"
                + "  for (var i = 0; i < elements.length; i++) {\n"
                + "    alert(elements[i].value);\n"
                + "    alert(elements.item(i).value);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form>\n"
                + "<input type='radio' name='name1' value='value1'>\n"
                + "<input type='radio' name='name1' value='value2'>\n"
                + "<input type='button' name='name2' value='value3'>\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"value1", "value1", "value2", "value2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void body_read() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body id='IAmTheBody' onload='alert(document.body.id)'>\n"
                + "</body></html>";

        final String[] messages = {"IAmTheBody"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void images() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.images;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.images.length);\n"
                        + "  alert(document.images == oCol);\n"
                        + "  alert(document.images[0].id);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<img id='firstImage' />\n"
                        + "<img name='end' />\n"
                        + "<img id='endId'/>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "3", "3", "true", "firstImage"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void imagesEmpty() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "var oCol = document.images;\n"
                        + "alert(oCol.length);\n"
                        + "function test() {\n"
                        + "  alert(oCol.length);\n"
                        + "  alert(document.images.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        final String[] messages = {"0", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void readyState() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function testIt() {\n"
                + "  alert(document.readyState);\n"
                + "}\n"
                + "alert(document.readyState);\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onLoad='testIt()'></body></html>";

        final String[] messages = {"loading", "complete"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void documentWithNoBody() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  alert(document.body);\n"
                + "</script></head><body></body></html>";
        final String[] messages = {null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getElementById_findByName() {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "<input type='text' name='findMe'>\n"
                + "<input type='text' id='findMe2' name='byId'>\n"
                + "<script>\n"
                + "  var o = document.getElementById('findMe');\n"
                + "  alert(o ? o.name : 'null');\n"
                + "  alert(document.getElementById('findMe2').name);\n"
                + "</script></body></html>";

        final String[] messages = {"null", "byId"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void scriptsArray() {
        final String html = "<html><head>\n"
                + "<script lang='JavaScript'>\n"
                + "  function doTest() {\n"
                + "    alert(document.scripts);\n"
                + "    try {\n"
                + "      alert(document.scripts.length);\n" // This line used to blow up
                + "    }\n"
                + "    catch (e) { alert('exception occured') }\n"
                + "}\n"
                + "</script></head><body onload='doTest();'>\n"
                + "<script>var scriptTwo = 1;</script>\n"
                + "</body></html> ";

        final String[] messages = {"[object HTMLCollection]", "2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void precedence() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <form name='writeln'>foo</form>\n"
                + "  <script>alert(typeof document.writeln);</script>\n"
                + "  <script>alert(document.writeln.tagName);</script>\n"
                + "</body></html>";

        final String[] messages = {"object", "FORM"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultViewAndParentWindow() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  alert(document.defaultView == window);\n"
                + "  alert(document.parentWindow == window);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html> ";

        final String[] messages = {"true", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void put() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  alert(document.foo);\n"
                + "  if (!document.foo) document.foo = 123;\n"
                + "  alert(document.foo);\n"
                + "</script>\n"
                + "</form>\n" + "</body>\n" + "</html>";

        final String[] messages = {"undefined", "123"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void documentCloneNode() {
        final String html = "<html><body id='hello' onload='doTest()'>\n"
                + "  <script id='jscript'>\n"
                + "    function doTest() {\n"
                + "      var clone = document.cloneNode(true);\n"
                + "      alert(clone);\n"
                + "      if (clone != null) {\n"
                + "        alert(clone.body);\n"
                + "        alert(clone.body !== document.body);\n"
                + "        alert(clone.getElementById(\"id1\") !== document.getElementById(\"id1\"));\n"
                + "        alert(document.ownerDocument == null);\n"
                + "        alert(clone.ownerDocument == document);\n"
                + "        alert(document.getElementById(\"id1\").ownerDocument === document);\n"
                + "        alert(clone.getElementById(\"id1\").ownerDocument === document);\n"
                + "      }\n"
                + "    }\n"
                + "  </script>\n"
                + "  <div id='id1'>hello</div>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLDocument]", "[object HTMLBodyElement]", "true", "true", "true", "false", "true", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void createStyleSheet() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "try {\n"
                + "  var s = document.createStyleSheet('foo.css', 1);\n"
                + "  alert(s);\n"
                + "}\n"
                + "catch(ex) {\n"
                + "  alert('exception');\n"
                + "}\n"
                + "</script></head><body>\n"
                + "</body></html>";

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void createEventEvent() {
        final String[] messages = {"true", "object", "[object Event]", "false"};
        createEvent("Event", messages);
    }

    @Test
    public void createEventEvents() {
        final String[] messages = {"true", "object", "[object Event]", "false"};
        createEvent("Events", messages);
    }

    private void createEvent(final String eventType, final String[] messages) {
        final String html =
                "<html><head>\n"
                        + "<script>\n"

                        + "try {\n"
                        + "  var e = document.createEvent('" + eventType + "');\n"
                        + "  alert(e != null);\n"
                        + "  alert(typeof e);\n"
                        + "  alert(e);\n"
                        + "  alert(e.cancelable);\n"
                        + "}\n"
                        + "catch (e) { alert('exception') }\n"
                        + "</script></head><body>\n"
                        + "</body></html>";

        checkHtmlAlert(html, messages);
    }

    @Test
    public void createEvent_target() {
        final String html =
                "<html>\n"
                        + "  <body onload='test()'>\n"
                        + "    <div id='d' onclick='alert(event.target)'>abc</div>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        try {\n"
                        + "          var event = document.createEvent('MouseEvents');\n"
                        + "          alert(event.target);\n"
                        + "          event.initMouseEvent('click', true, true, window,\n"
                        + "               1, 0, 0, 0, 0, false, false, false, false, 0, null);\n"
                        + "          alert(event.target);\n"
                        + "          document.getElementById('d').dispatchEvent(event);\n"
                        + "        } catch (e) { alert('exception') }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {null, null, "[object HTMLDivElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void elementFromPoint() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var e = document.elementFromPoint(-1,-1);\n"
                + "    alert(e != null ? e.nodeName : null);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleSheets() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.styleSheets);\n"
                + "    alert(document.styleSheets.length);\n"
                + "    alert(document.styleSheets == document.styleSheets);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"[object StyleSheetList]", "0", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void execCommand() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    document.designMode = 'On';\n"
                + "    alert(document.execCommand('Bold', false, null));\n"
                + "    try {\n"
                + "      alert(document.execCommand('foo', false, null));\n"
                + "    }\n"
                + "    catch (e) {\n"
                + "      alert('command foo not supported');\n"
                + "    }\n"
                + "    document.designMode = 'Off';\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"true", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void evaluate_caseInsensitiveAttribute() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  if(document.evaluate) {\n"
                + "    var expr = './/*[@CLASS]';\n"
                + "    var result = document.evaluate(expr, document.documentElement, null, XPathResult.ANY_TYPE, null);\n"
                + "    alert(result.iterateNext());\n"
                + "  } else { alert('not available'); }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <h1 class='title'>Some text</h1>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLHeadingElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void evaluate_caseInsensitiveTagName() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if(document.evaluate) {\n"
                + "      var expr = '/hTmL';\n"
                + "      var result = document.evaluate(expr, "
                + "document.documentElement, null, XPathResult.ANY_TYPE, null);\n"
                + "      alert(result.iterateNext());\n"
                + "    } else { alert('not available'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <h1 class='title'>Some text</h1>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLHtmlElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void noBodyTag() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    </script>\n"
                        + "    <script>alert('1: ' + document.body);</script>\n"
                        + "    <script defer=''>alert('2: ' + document.body);</script>\n"
                        + "    <script>window.onload = function() { alert('3: ' + document.body); }</script>\n"
                        + "  </head>\n"
                        + "</html>";

        final String[] messages = {"1: null", "2: null", "3: [object HTMLBodyElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void noBodyTag_IFrame() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "<script>\n"
                        + "</script>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <iframe id='i'></iframe>\n"
                        + "    <script>\n"
                        + "      alert('1: ' + document.getElementById('i').contentWindow.document.body);\n"
                        + "      window.onload = function() {\n"
                        + "        alert('2: ' + document.getElementById('i').contentWindow.document.body);\n"
                        + "      };\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {"1: [object HTMLBodyElement]", "2: [object HTMLBodyElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void contains() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var testnode = document.getElementById('myNode');\n"
                + "    alert(document.contains ? document.contains(testnode) : '-');\n"
                + "    var newnode = document.createComment('some comment');\n"
                + "    alert(document.contains ? document.contains(newnode) : '-');\n"
                + "    alert(document.contains ? document.contains(document.documentElement) : '-');\n"
                + "    alert(document.contains ? document.contains(document.body) : '-');\n"
                + "    alert(document.contains ? document.contains(document.firstElementChild) : '-');\n"
                + "    alert(document.contains ? document.contains(null) : '-');\n"
                + "    alert(document.contains ? document.contains(undefined) : '-');\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myNode'></div>\n"
                + "</body></html>";
        final String[] messages = {"true", "false", "true", "true", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void createComment() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var elt = document.createComment('some comment');\n"
                + "  alert(elt);\n"
                + "  alert(document.contains ? document.contains(elt) : '-');\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"[object Comment]", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void oninput() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert('oninput' in document);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void documentDefineProperty() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.testProp);\n"
                + "      Object.defineProperty(document, 'testProp', {\n"
                + "        value: 42,\n"
                + "        writable: true,\n"
                + "        enumerable: true,\n"
                + "        configurable: true\n"
                + "      });\n"
                + "      alert(document.testProp);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"undefined", "42"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void rootElement() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var xmlDocument = document.implementation.createDocument('', '', null);\n"
                + "      alert(xmlDocument.rootElement);\n"
                + "      alert(document.rootElement);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        final String[] messages = {null, null};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void firstElementChild() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.childElementCount);\n"
                + "      alert(document.firstElementChild);\n"
                + "      alert(document.lastElementChild);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        final String[] messages = {"1", "[object HTMLHtmlElement]", "[object HTMLHtmlElement]"};
        checkHtmlAlert(html, messages);
    }
}
