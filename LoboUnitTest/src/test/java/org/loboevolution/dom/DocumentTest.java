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
public class DocumentTest extends LoboUnitTest {

    @Test
    @Alerts({"2", "form1", "form2"})
    public void formsAccessor_TwoForms() {
        final String html
                = "<html><head><script>\n"

        +  "function doTest() {\n"
        +  " alert(document.forms.length);\n"
        +  "  for(var i = 0; i < document.forms.length; i++) {\n"
        +  "   alert(document.forms[i].name);\n"
        +  "  }\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<p>hello world</p>\n"
        +  "<form name='form1'>\n"
        +  "  <input type='text' name='textfield1' value='foo' />\n"
        +  "</form>\n"
        +  "<form name='form2'>\n"
        +  "  <input type='text' name='textfield2' value='foo' />\n"
        +  "</form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void formsAccessor_FormWithNoName() {
        final String html
                = "<html><head><script>\n"

        +  "function doTest() {\n"
        +  " alert(document.forms.length);\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<p>hello world</p>\n"
        +  "<form>\n"
        +  "  <input type='text' name='textfield1' value='foo' />\n"
        +  "</form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void formsAccessor_NoForms() {
        final String html
                = "<html><head><script>\n"

        +  "function doTest() {\n"
        +  " alert(document.forms.length);\n"
        +  "  for(var i = 0; i < document.forms.length; i++) {\n"
        +  "   alert(document.forms[i].name);\n"
        +  "  }\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<p>hello world</p>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "1", "1", "true"})
    public void formsLive() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.forms;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.forms.length);\n"
                +  " alert(document.forms == oCol);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "<form id='myForm' action='foo.html'>\n"
                +  "</form>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    
    @Test
    @Alerts({"0", "1", "1", "true", "name: end"})
    public void anchors() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.anchors;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.anchors.length);\n"
                +  " alert(document.anchors == oCol);\n"
                +  "  if (document.anchors[0].name)\n"
                +  "   alert('name: ' + document.anchors[0].name);\n"
                +  "  else\n"
                +  "   alert('id: ' + document.anchors[0].id);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "<a href='foo.html' id='firstLink'>foo</a>\n"
                +  "<a href='foo2.html'>foo2</a>\n"
                +  "<a name='end'/>\n"
                +  "<a href=''>null2</a>\n"
                +  "<a id='endId'/>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "true"})
    public void anchorsEmpty() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.anchors;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.anchors.length);\n"
                +  " alert(document.anchors == oCol);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "true"})
    public void applets() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.applets;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.applets.length);\n"
                +  " alert(document.applets == oCol);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "<applet id='firstApplet'></applet>\n"
                +  "<applet name='end'></applet>\n"
                +  "<applet id='endId'></applet>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "true"})
    public void appletsEmpty() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.applets;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.applets.length);\n"
                +  " alert(document.applets == oCol);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "3", "3", "true", "firstEmbed"})
    public void embeds() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.embeds;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.embeds.length);\n"
                +  " alert(document.embeds == oCol);\n"
                +  " alert(document.embeds[0].id);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "<embed id='firstEmbed' />\n"
                +  "<embed name='end' />\n"
                +  "<embed id='endId'/>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "true"})
    public void embedsEmpty() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.embeds;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.embeds.length);\n"
                +  " alert(document.embeds == oCol);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "3", "3", "true", "firstEmbed"})
    public void plugins() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.plugins;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.plugins.length);\n"
                +  " alert(document.plugins == oCol);\n"
                +  " alert(document.embeds[0].id);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "<embed id='firstEmbed' />\n"
                +  "<embed name='end' />\n"
                +  "<embed id='endId'/>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "true"})
    public void pluginsEmpty() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.plugins;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.plugins.length);\n"
                +  " alert(document.plugins == oCol);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "3", "3", "true", "firstLink"})
    public void links() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.links;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.links.length);\n"
                +  " alert(document.links == oCol);\n"
                +  " alert(document.links[0].id);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "<a href='foo.html' id='firstLink'>foo</a>\n"
                +  "<a href='foo2.html'>foo2</a>\n"
                +  "<a name='end'/>\n"
                +  "<a href=''>null2</a>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "0", "0", "true"})
    public void linksEmpty() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.links;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.links.length);\n"
                +  " alert(document.links == oCol);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"parentNode: null", "DIV", "1", "null", "DIV", "button1value", "text1value", "text"})
    public void createElement() {
        final String html
                = "<html>\n"
        +  "  <head>\n"
        +  "    <script>\n"        +  "      function doTest() {\n"
        +  "        // Create a DIV element.\n"
        +  "        var div1 = document.createElement('div');\n"
        +  "       alert('parentNode: ' + div1.parentNode);\n"
        +  "        div1.id = 'div1';\n"
        +  "        document.body.appendChild(div1);\n"
        +  "       alert(div1.tagName);\n"
        +  "       alert(div1.nodeType);\n"
        +  "       alert(div1.nodeValue);\n"
        +  "       alert(div1.nodeName);\n"
        +  "        // Create an INPUT element.\n"
        +  "        var input = document.createElement('input');\n"
        +  "        input.id = 'text1id';\n"
        +  "        input.name = 'text1name';\n"
        +  "        input.value = 'text1value';\n"
        +  "        var form = document.getElementById('form1');\n"
        +  "        form.appendChild(input);\n"
        +  "       alert(document.getElementById('button1id').value);\n"
        +  "       alert(document.getElementById('text1id').value);\n"
        +  "        // The default type of an INPUT element is 'text'.\n"
        +  "       alert(document.getElementById('text1id').type);\n"
        +  "      }\n"
        +  "    </script>\n"
        +  "  </head>\n"
        +  "  <body onload='doTest()'>\n"
        +  "    <form name='form1' id='form1'>\n"
        +  "      <input type='button' id='button1id' name='button1name' value='button1value'/>\n"
        +  "      This is form1.\n"
        +  "    </form>\n"
        +  "  </body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"DIV,DIV,http://www.w3.org/1999/xhtml,null,div",
            "HI:DIV,HI:DIV,http://www.w3.org/1999/xhtml,null,hi:div"})
    public void documentCreateElement2() {
        final String html
                = "<html>\n"
        +  "  <head>\n"
        +  "    <script>\n"
        +  "      function doTest() {\n"
        +  "        div = document.createElement('Div');\n"
        +  "       alert(div.nodeName + ',' + div.tagName + ',' + div.namespaceURI + ',' + "
        +  "div.prefix + ',' + div.localName);\n"
        +  "        div = document.createElement('Hi:Div');\n"
        +  "       alert(div.nodeName + ',' + div.tagName + ',' + div.namespaceURI + ',' + "
        +  "div.prefix + ',' + div.localName);\n"
        +  "      }\n"
        +  "    </script>\n"
        +  "  </head>\n"
        +  "  <body onload='doTest()'>\n"
        +  "  </body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Some:Div", "Some:Div", "myNS", "Some", "Div", "svg", "svg", "http://www.w3.org/2000/svg", "null", "svg"})
    public void createElementNS() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var div = document.createElementNS('myNS', 'Some:Div');\n"
        +  "   alert(div.nodeName);\n"
        +  "   alert(div.tagName);\n"
        +  "   alert(div.namespaceURI);\n"
        +  "   alert(div.prefix);\n"
        +  "   alert(div.localName);\n"

        +  "    var svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');\n"
        +  "   alert(svg.nodeName);\n"
        +  "   alert(svg.tagName);\n"
        +  "   alert(svg.namespaceURI);\n"
        +  "   alert(svg.prefix);\n"
        +  "   alert(svg.localName);\n"
        +  "  }\n"
        +  "</script></head>\n"
        +  "<body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Some Text", "9", "3", "Some Text", "#text"})
    public void createTextNode() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  "  var text1=document.createTextNode('Some Text');\n"
        +  "  var body1=document.getElementById('body');\n"
        +  "  body1.appendChild(text1);\n"
        +  " alert(text1.data);\n"
        +  " alert(text1.length);\n"
        +  " alert(text1.nodeType);\n"
        +  " alert(text1.nodeValue);\n"
        +  " alert(text1.nodeName);\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()' id='body'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void appendChild() {
        final String html
                = "<html><head><script>\n"

        +  "  function doTest() {\n"
        +  "    var form = document.forms['form1'];\n"
        +  "    var div = document.createElement('DIV');\n"
        +  "    form.appendChild(div);\n"
        +  "    var elements = document.getElementsByTagName('DIV');\n"
        +  "   alert(elements.length);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<p>hello world</p>\n"
        +  "<form name='form1'>\n"
        +  "</form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "exception"})
    public void appendChildAtDocumentLevel() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "  <script>\n"

                +  "    function test() {\n"
                +  "      var div = document.createElement('div');\n"
                +  "      div.innerHTML = 'test';\n"
                +  "      try {\n"
                +  "       alert(document.childNodes.length);\n"
                +  "        document.appendChild(div); // Error\n"
                +  "       alert(document.childNodes.length);\n"
                +  "       alert(document.childNodes[0].tagName);\n"
                +  "       alert(document.childNodes[1].tagName);\n"
                +  "       alert(document.getElementsByTagName('div').length);\n"
                +  "      } catch(ex) {\n"
                +  "       alert('exception');\n"
                +  "      }\n"
                +  "    }\n"
                +  "  </script>\n"
                +  "</head>\n"
                +  "<body onload='test()'></body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Some Text")
    public void appendChildTextNode() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var form = document.forms['form1'];\n"
        +  "    var child = document.createTextNode('Some Text');\n"
        +  "    form.appendChild(child);\n"
        +  "   alert(form.lastChild.data);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<p>hello world</p>\n"
        +  "<form name='form1'>\n"
        +  "</form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true", "true"})
    public void cloneNode() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var form = document.forms['form1'];\n"
        +  "    var cloneShallow = form.cloneNode(false);\n"
        +  "   alert(cloneShallow != null);\n"
        +  "   alert(cloneShallow.firstChild == null);\n"
        +  "    var cloneDeep = form.cloneNode(true);\n"
        +  "   alert(cloneDeep != null);\n"
        +  "   alert(cloneDeep.firstChild != null);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<form name='form1'>\n"
        +  "<p>hello world</p>\n"
        +  "</form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void insertBefore() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var form = document.forms['form1'];\n"
        +  "    var oldChild = document.getElementById('oldChild');\n"
        +  "    var div = document.createElement('DIV');\n"
        +  "    form.insertBefore(div, oldChild);\n"
        +  "   alert(form.firstChild == div);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<form name='form1'><div id='oldChild'/></form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("text/javascript")
    public void getElementById_scriptType() {
        final String html
                = "<html><head>\n"
        +  "<script id='script1' type='text/javascript'>\n"

        +  "  doTest=function() {\n"
        +  " alert(top.document.getElementById('script1').type);\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("script/")
    public void getElementByIdcriptSrc() {
        final String html
                = "<html><head>\n"
                + "<script>"
                + " function doTest() {\n"
                + " alert(top.document.getElementById('script1').src);\n"
                + "}"
                + "</script>\n"
                + "<script src=" + URL_JS + "script.js" + "></script>"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("parentDiv")
    public void parentNode_Nested() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"
        +  "  function doTest() {\n"
        +  "    var div1=document.getElementById('childDiv');\n"
        +  "   alert(div1.parentNode.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'><div id='childDiv'></div></div>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void parentNode_Document() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"
        +  "  function doTest() {\n"
        +  "   alert(document.parentNode == null);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("true")
    public void parentNode_CreateElement() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var div1=document.createElement('div');\n"
        +  "   alert(div1.parentNode == null);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("parentDiv")
    public void parentNode_AppendChild() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var childDiv=document.getElementById('childDiv');\n"
        +  "    var parentDiv=document.getElementById('parentDiv');\n"
        +  "    parentDiv.appendChild(childDiv);\n"
        +  "   alert(childDiv.parentNode.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'></div><div id='childDiv'></div>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "HTML", "true"})
    public void documentElement() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "   alert(document.documentElement != null);\n"
        +  "   alert(document.documentElement.tagName);\n"
        +  "   alert(document.documentElement.parentNode == document);\n"
        +  "  }\n"
        +  "</script></head>\n"
        +  "<body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("childDiv")
    public void firstChild_Nested() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"
        +  "  function doTest() {\n"
        +  "    var div1=document.getElementById('parentDiv');\n"
        +  "   alert(div1.firstChild.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'><div id='childDiv'/><div id='childDiv2'/></div>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("childDiv")
    public void firstChild_AppendChild() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var childDiv=document.getElementById('childDiv');\n"
        +  "    var parentDiv=document.getElementById('parentDiv');\n"
        +  "    parentDiv.appendChild(childDiv);\n"
        +  "    var childDiv2=document.getElementById('childDiv2');\n"
        +  "    parentDiv.appendChild(childDiv2);\n"
        +  "   alert(parentDiv.firstChild.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'/><div id='childDiv'/><div id='childDiv2'/>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("childDiv")
    public void lastChild_Nested() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var div1=document.getElementById('parentDiv');\n"
        +  "   alert(div1.lastChild.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'><div id='childDiv1'></div><div id='childDiv'></div></div>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("childDiv")
    public void lastChild_AppendChild() {
        final String html
                = "<html><head><script>\n"

        +  "  function doTest() {\n"
        +  "    var childDiv1=document.getElementById('childDiv1');\n"
        +  "    var parentDiv=document.getElementById('parentDiv');\n"
        +  "    parentDiv.appendChild(childDiv1);\n"
        +  "    var childDiv=document.getElementById('childDiv');\n"
        +  "    parentDiv.appendChild(childDiv);\n"
        +  "   alert(parentDiv.lastChild.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'/><div id='childDiv1'/><div id='childDiv'/>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("nextDiv")
    public void nextSibling_Nested() {
        final String html
                = "<html><head><script>\n"

        +  "  function doTest() {\n"
        +  "    var div1 = document.getElementById('previousDiv');\n"
        +  "   alert(div1.nextSibling.id);\n"
        +  "  }\n"
        +  "</script></head>\n"
        +  "<body onload='doTest()'>\n"
        +  "<div id='parentDiv'><div id='previousDiv'></div><div id='nextDiv'></div></div>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("nextDiv")
    public void nextSibling_AppendChild() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var previousDiv=document.getElementById('previousDiv');\n"
        +  "    var parentDiv=document.getElementById('parentDiv');\n"
        +  "    parentDiv.appendChild(previousDiv);\n"
        +  "    var nextDiv=document.getElementById('nextDiv');\n"
        +  "    parentDiv.appendChild(nextDiv);\n"
        +  "   alert(previousDiv.nextSibling.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'/><div id='junk1'/><div id='previousDiv'/><div id='junk2'/><div id='nextDiv'/>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("previousDiv")
    public void previousSibling_Nested() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var div1 = document.getElementById('nextDiv');\n"
        +  "   alert(div1.previousSibling.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'><div id='previousDiv'></div><div id='nextDiv'></div></div>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("previousDiv")
    public void previousSibling_AppendChild() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var previousDiv=document.getElementById('previousDiv');\n"
        +  "    var parentDiv=document.getElementById('parentDiv');\n"
        +  "    parentDiv.appendChild(previousDiv);\n"
        +  "    var nextDiv=document.getElementById('nextDiv');\n"
        +  "    parentDiv.appendChild(nextDiv);\n"
        +  "   alert(nextDiv.previousSibling.id);\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<div id='parentDiv'/><div id='junk1'/><div id='previousDiv'/><div id='junk2'/><div id='nextDiv'/>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"tangerine", "ginger"})
    public void allProperty_KeyByName() {
        final String html
                = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function doTest() {\n"
        +  "     alert(document.all['input1'].value);\n"
        +  "     alert(document.all['foo2'].value);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='doTest()'>\n"
        +  "  <form id='form1'>\n"
        +  "    <input id='input1' name='foo1' type='text' value='tangerine' />\n"
        +  "    <input id='input2' name='foo2' type='text' value='ginger' />\n"
        +  "  </form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("DIV")
    public void allProperty_CalledDuringPageLoad() {
        final String html
                = "<html><body>\n"
        +  "<div id='ARSMenuDiv1' style='VISIBILITY: hidden; POSITION: absolute; z-index: 1000000'></div>\n"
        +  "<script language='Javascript'>\n"

        +  "  var divObj = document.all['ARSMenuDiv1'];\n"
        +  " alert(divObj.tagName);\n"
        +  "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("")
    public void referrer_NoneSpecified() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='alert(document.referrer);'>\n"
        +  "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("")
    public void url() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='alert(document.URL);'>\n"
        +  "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"button", "button", "true"})
    public void getElementsByTagName() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var elements = document.getElementsByTagName('input');\n"
        +  "    for (var i = 0; i < elements.length; i++) {\n"
        +  "     alert(elements[i].type);\n"
        +  "     alert(elements.item(i).type);\n"
        +  "    }\n"
        +  "   alert(elements == document.getElementsByTagName('input'));\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<form><input type='button' name='button1' value='pushme'></form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("button")
    public void getElementsByTagName_CaseInsensitive() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "  function doTest() {\n"
        +  "    var elements = document.getElementsByTagName('InPuT');\n"
        +  "    for(i = 0; i < elements.length; i++) {\n"
        +  "     alert(elements[i].type);\n"
        +  "    }\n"
        +  "  }\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<form><input type='button' name='button1' value='pushme'></form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void getElementsByTagName_Inline() {
        final String html
                = "<html><body>\n"
        +  "<script type=\"text/javascript\">\n"

        +  "alert(document.getElementsByTagName('script').length);\n"
        +  "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void getElementsByTagNameLoadScript() {
        final String html = "<html><body><script src=" + URL_JS + "script.js" + ">alert(document.getElementsByTagName('script').length)</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "<nested>Three</nested>", "Four", "1", "Two", "0", "0"})
    public void getElementsByTagNameXml() {
        final String html = "<html><head>\n"
        +  "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
        +  "</head><body>\n"
        +  "<script>\n"
        +  "  var xmlString = [\n"
        +  "                 '<ResultSet>',\n"
        +  "                 '<Result>One</Result>',\n"
        +  "                 '<RESULT>Two</RESULT>',\n"
        +  "                 '<result><nested>Three</nested></result>',\n"
        +  "                 '<result>Four</result>',\n"
        +  "                 '</ResultSet>'\n"
        +  "                ].join('');\n"
        +  "  if (window.DOMParser) {\n"
        +  "    var parser = new DOMParser();\n"
        +  "    xml = parser.parseFromString(xmlString, 'text/xml');\n"
        +  "  } else { // IE\n"
        +  "    var parser = new ActiveXObject('Microsoft.XMLDOM');\n"
        +  "    parser.async = 'false';\n"
        +  "    parser.loadXML(xmlString);\n"
        +  "  }\n"
        +  "  var xmlDoc = parser.parseFromString(xmlString, 'text/xml');\n"
        +  "  try {\n"

        +  "    var res = xmlDoc.getElementsByTagName('result');\n"
        +  "   alert(res.length);\n"
        +  "   alert(res[0].innerHTML);\n"
        +  "   alert(res[1].innerHTML);\n"

        +  "    res = xmlDoc.getElementsByTagName('RESULT');\n"
        +  "   alert(res.length);\n"
        +  "   alert(res[0].innerHTML);\n"

        +  "    res = xmlDoc.getElementsByTagName('resulT');\n"
        +  "   alert(res.length);\n"

        +  "    res = xmlDoc.getElementsByTagName('rEsulT');\n"
        +  "   alert(res.length);\n"
        +  "  } catch(e) {alert('exception ' + e)}\n"
        +  "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"HTML", "HEAD", "TITLE", "SCRIPT", "BODY"})
    public void all_WithParentheses() {
        final String html
                = "<html><head><title></title>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  "  var length = document.all.length;\n"
        +  "  for(i = 0; i < length; i++) {\n"
        +  "    try {\n"
        +  "      var all = document.all(i);\n"
        +  "      if (all == null) {\n"
        +  "       alert('all == null');\n"
        +  "      } else {\n"
        +  "       alert(all.tagName);\n"
        +  "      }\n"
        +  "    } catch(e) {alert(e); }\n"
        +  "  }\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"HTML", "HEAD", "TITLE", "SCRIPT", "BODY"})
    public void all_IndexByInt() {
        final String html
                = "<html><head><title></title>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  "  var length = document.all.length;\n"
        +  "  for(i = 0; i < length; i++) {\n"
        +  "   alert(document.all[i].tagName);\n"
        +  "  }\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("HTML")
    public void all_Item() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  " alert(document.all.item(0).tagName);\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void allNamedItemUnknown() {
        namedItem("foo");
    }

    @Test
    @Alerts("form1<->")
    public void allNamedItemById() {
        namedItem("form1");
    }

    @Test
    @Alerts("<->form2")
    public void allNamedItemByName_formWithoutId() {
        namedItem("form2");
    }

    @Test
    @Alerts("f3<->form3")
    public void allNamedItemByName() {
        namedItem("form3");
    }

    @Test
    @Alerts({"coll 2", "f4<->form4_1", "f4<->form4_2"})
    public void allNamedItemDuplicateId() {
        namedItem("f4");
    }

    @Test
    @Alerts({"coll 2", "f5_1<->form5", "f5_2<->form5"})
    public void allNamedItemDuplicateName() {
        namedItem("form5");
    }

    @Test
    @Alerts({"coll 2", "f6<->form6", "form6<->form6_2"})
    public void allNamedItemDuplicateIdName() {
        namedItem("form6");
    }

    private void namedItem(final String name) {
        final String html
                = "<!doctype html>\n"
        +  "<html><head>\n"
        +  "<script>\n"
        +  "  var res = '';"
        +  "  functionalert(msg) { res += msg + '§';}\n"
        +  "  function doTest() {\n"
        +  "    var result = document.all.namedItem('" + name + "');\n"
        +  "    if (result == null) {\n"
        +  "     alert(result);\n"
        +  "    } else if (result.id || result.name) {\n"
        +  "     alert(result.id + '<->' + result.name);\n"
        +  "    } else {\n"
        +  "     alert('coll ' + result.length);\n"
        +  "      for(i = 0; i < result.length; i++) {\n"
        +  "       alert(result.item(i).id + '<->' + result.item(i).name);\n"
        +  "      }\n"
        +  "    }\n"
        +  "    window.document.title = res;"
        +  "  }\n"
        +  "</script></head>\n"
        +  "<body onload='doTest()'>\n"
        +  "  <form id='form1'></form>\n"
        +  "  <form name='form2'></form>\n"
        +  "  <form id='f3' name='form3'></form>\n"
        +  "  <form id='f4' name='form4_1'></form>\n"
        +  "  <form id='f4' name='form4_2'></form>\n"
        +  "  <form id='f5_1' name='form5'></form>\n"
        +  "  <form id='f5_2' name='form5'></form>\n"
        +  "  <form id='f6' name='form6'></form>\n"
        +  "  <form id='form6' name='form6_2'></form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void allTags() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  "  try {\n"
        +  "    var inputs = document.all.tags('input');\n"
        +  "    var inputCount = inputs.length;\n"
        +  "    for(i = 0; i < inputCount; i++) {\n"
        +  "     alert(inputs[i].name);\n"
        +  "    }\n"
        +  "    // Make sure tags() returns an element array that you can call item() on.\n"
        +  "   alert(document.all.tags('input').item(0).name);\n"
        +  "   alert(document.all.tags('input').item(1).name);\n"
        +  "    // Make sure tags() returns an empty element array if there are no matches.\n"
        +  "   alert(document.all.tags('xxx').length);\n"
        +  "  } catch (e) {alert('exception') }\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<input type='text' name='a' value='1'>\n"
        +  "<input type='text' name='b' value='1'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false", "undefined"})
    public void all() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  " alert(document.all ? true : false);\n"
        +  " alert(Boolean(document.all));\n"
        +  " alert(typeof document.all);\n"
        +  "}\n"
        +  "</script><body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2"})
    public void all_Caching() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='alert(document.all.b.value)'>\n"
        +  "<input type='text' name='a' value='1'>\n"
        +  "<script>alert(document.all.a.value)</script>\n"
        +  "<input type='text' name='b' value='2'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "null"})
    public void all_NotExisting() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  " alert(document.all('notExisting'));\n"
        +  " alert(document.all.item('notExisting'));\n"
        +  " alert(document.all.namedItem('notExisting'));\n"
        +  "}\n"
        +  "</script><body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"value1", "value1", "value2", "value2"})
    public void getElementsByName() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  "  var elements = document.getElementsByName('name1');\n"
        +  "  for (var i = 0; i < elements.length; i++) {\n"
        +  "   alert(elements[i].value);\n"
        +  "   alert(elements.item(i).value);\n"
        +  "  }\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<form>\n"
        +  "<input type='radio' name='name1' value='value1'>\n"
        +  "<input type='radio' name='name1' value='value2'>\n"
        +  "<input type='button' name='name2' value='value3'>\n"
        +  "</form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("IAmTheBody")
    public void bodyRead() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "</script>\n"
        +  "</head>\n"
        +  "<body id='IAmTheBody' onload='alert(document.body.id)'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("FRAMESET")
    public void bodyReadFrameset() {
        final String html = "<html>\n"
        +  "<frameset onload='alert(document.body.tagName)'>\n"
        +  "<frame src='about:blank' name='foo'>\n"
        +  "</frameset></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "3", "3", "true", "firstImage"})
    public void images() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.images;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.images.length);\n"
                +  " alert(document.images == oCol);\n"
                +  " alert(document.images[0].id);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "<img id='firstImage' />\n"
                +  "<img name='end' />\n"
                +  "<img id='endId'/>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "true"})
    public void imagesEmpty() {
        final String html =
                "<html>\n"
                +  "<head>\n"
                +  "<script>\n"

                +  "var oCol = document.images;\n"
                +  "alert(oCol.length);\n"
                +  "function test() {\n"
                +  " alert(oCol.length);\n"
                +  " alert(document.images.length);\n"
                +  " alert(document.images == oCol);\n"
                +  "}\n"
                +  "</script>\n"
                +  "</head>\n"
                +  "<body onload='test()'>\n"
                +  "</body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "2", "true"})
    public void allImages() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  " alert(document.images.length);\n"
        +  " alert(allImages.length);\n"
        +  " alert(document.images == allImages);\n"
        +  "}\n"
        +  "</script></head><body onload='doTest()'>\n"
        +  "<img src='firstImage'>\n"
        +  "<script>\n"
        +  "var allImages = document.images;\n"
        +  "alert(allImages.length);\n"
        +  "</script>\n"
        +  "<form>\n"
        +  "<img src='2ndImage'>\n"
        +  "</form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("correct title")
    public void settingTitle() {
        final String html
                = "<html><head><title>Bad Title</title></head>\n"
        +  "<body>\n"
        +  "<script>\n"
        +  "  document.title = 'correct title';\n"
        +  "  alert(document.title);\n"
        +  "</script>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("correct title")
    public void settingMissingTitle() {
        final String html = "<html><head></head>\n"
        +  "<body>\n"
        +  "<script>\n"
        +  "  document.title = 'correct title';\n"
        +  "  alert(document.title);\n"
        +  "</script>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("correct title")
    public void settingBlankTitle() {
        final String html = "<html><head><title></title></head>\n"
        +  "<body>\n"
        +  "<script>\n"
        +  "  document.title = 'correct title';\n"
        +  "  alert(document.title);\n"
        +  "</script>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("foo")
    public void title() {
        final String html = "<html><head><title>foo</title><script>\n"
        +  "function doTest() {\n"
        +  "  alert(document.title);\n"
        +  "}\n"
        +  "</script></head>\n"
        +  "<body onload='doTest()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"loading", "complete"})
    public void readyState() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "function testIt() {\n"
        +  " alert(document.readyState);\n"
        +  "}\n"
        +  "alert(document.readyState);\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onLoad='testIt()'></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("null")
    public void documentWithNoBody() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"
        +  " alert(document.body);\n"
        +  "</script></head><body></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "byId"})
    public void getElementById_findByName() {
        final String html
                = "<html><head></head>\n"
        +  "<body>\n"
        +  "<input type='text' name='findMe'>\n"
        +  "<input type='text' id='findMe2' name='byId'>\n"
        +  "<script>\n"

        +  "  var o = document.getElementById('findMe');\n"
        +  " alert(o ? o.name : 'null');\n"
        +  " alert(document.getElementById('findMe2').name);\n"
        +  "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myImageId", "2", "FORM", "undefined", "undefined", "undefined", "undefined"})
    public void directAccessByName() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "function doTest() {\n"
        +  " alert(document.myImage.id);\n"
        +  " alert(document.myImage2.length);\n"
        +  " alert(document.myForm.tagName);\n"
        +  " alert(document.myAnchor);\n"
        +  " alert(document.myInput);\n"
        +  " alert(document.myInputImage);\n"
        +  " alert(document.myButton);\n"
        +  "}\n"
        +  "</script></head>\n"
        +  "<body onload='doTest()'>\n"
        +  "  <img src='foo' name='myImage' id='myImageId'>\n"
        +  "  <img src='foo' name='myImage2'>\n"
        +  "  <img src='foo' name='myImage2'>\n"
        +  "  <a name='myAnchor'/>\n"
        +  "  <form name='myForm'>\n"
        +  "    <input name='myInput' type='text'>\n"
        +  "    <input name='myInputImage' type='image' src='foo'>\n"
        +  "    <button name='myButton'>foo</button>\n"
        +  "  </form>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object HTMLCollection]", "2"})
    public void scriptsArray() {
        final String html = "<html><head>\n"
        +  "<script lang='JavaScript'>\n"
        +  "  function doTest() {\n"
        +  "   alert(document.scripts);\n"
        +  "    try {\n"
        +  "     alert(document.scripts.length);\n" // This line used to blow up
        +  "    }\n"
        +  "    catch (e) {alert('exception occured') }\n"
        +  "}\n"
        +  "</script></head><body onload='doTest();'>\n"
        +  "<script>var scriptTwo = 1;</script>\n"
        +  "</body></html> ";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"object", "FORM"})
    public void precedence() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "</script>\n"
        +  "</head>\n"
        +  "<body>\n"
        +  "  <form name='writeln'>foo</form>\n"
        +  "  <script>alert(typeof document.writeln);</script>\n"
        +  "  <script>alert(document.writeln.tagName);</script>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "false"})
    public void defaultViewAndParentWindow() {
        final String html = "<html><head><script>\n"

        +  "function test() {\n"
        +  " alert(document.defaultView == window);\n"
        +  " alert(document.parentWindow == window);\n"
        +  "}\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html> ";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "123"})
    public void put() {
        final String html = "<html><body>\n"
        +  "<script>\n"

        +  " alert(document.foo);\n"
        +  "  if (!document.foo) document.foo = 123;\n"
        +  " alert(document.foo);\n"
        +  "</script>\n"
        +  "</form>\n" + "</body>\n" + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDocument]", "[object HTMLBodyElement]",
            "true", "true", "true", "false", "true", "false"})
    public void documentCloneNode() {
        final String html = "<html><body id='hello' onload='doTest()'>\n"
        +  "  <script id='jscript'>\n"

        +  "    function doTest() {\n"
        +  "      var clone = document.cloneNode(true);\n"
        +  "     alert(clone);\n"
        +  "      if (clone != null) {\n"
        +  "       alert(clone.body);\n"
        +  "       alert(clone.body !== document.body);\n"
        +  "       alert(clone.getElementById(\"id1\") !== document.getElementById(\"id1\"));\n"
        +  "       alert(document.ownerDocument == null);\n"
        +  "       alert(clone.ownerDocument == document);\n"
        +  "       alert(document.getElementById(\"id1\").ownerDocument === document);\n"
        +  "       alert(clone.getElementById(\"id1\").ownerDocument === document);\n"
        +  "      }\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "  <div id='id1'>hello</div>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createStyleSheet() {
        final String html
                = "<html><head>\n"
        +  "<script>\n"

        +  "try {\n"
        +  "  var s = document.createStyleSheet('foo.css', 1);\n"
        +  " alert(s);\n"
        +  "}\n"
        +  "catch(ex) {\n"
        +  " alert('exception');\n"
        +  "}\n"
        +  "</script></head><body>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("#document-fragment_null_11_null_0_")
    public void createDocumentFragment() {
        final String html = "<html><head><title>foo</title><script>\n"
        +  "  function test() {\n"
        +  "    var fragment = document.createDocumentFragment();\n"
        +  "    var textarea = document.getElementById('myTextarea');\n"
        +  "    textarea.value += fragment.nodeName + '_';\n"
        +  "    textarea.value += fragment.nodeValue + '_';\n"
        +  "    textarea.value += fragment.nodeType + '_';\n"
        +  "    textarea.value += fragment.parentNode + '_';\n"
        +  "    textarea.value += fragment.childNodes.length + '_';\n"
        +  "    alert(textarea.value);\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "<textarea id='myTextarea' cols='40'></textarea>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "object", "[object Event]", "false"})
    public void createEvent_Event() {
        createEvent("Event");
    }

    @Test
    @Alerts({"true", "object", "[object Event]", "false"})
    public void createEvent_Events() {
        createEvent("Events");
    }

    @Test
    @Alerts({"true", "object", "[object Event]", "false"})
    public void createEvent_HTMLEvents() {
        createEvent("HTMLEvents");
    }

    @Test
    @Alerts("exception")
    public void createEvent_Bogus() {
        createEvent("Bogus");
    }

    private void createEvent(final String eventType) {
        final String html =
                "<html><head>\n"
                +  "<script>\n"

                +  "try {\n"
                +  "  var e = document.createEvent('" + eventType + "');\n"
                +  " alert(e != null);\n"
                +  " alert(typeof e);\n"
                +  " alert(e);\n"
                +  " alert(e.cancelable);\n"
                +  "}\n"
                +  "catch (e) {alert('exception') }\n"
                +  "</script></head><body>\n"
                +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "[object HTMLDivElement]"})
    public void createEventTarget() {
        final String html =
                "<html>\n"
                +  "  <body onload='test()'>\n"
                +  "    <div id='d' onclick='alert(event.target)'>abc</div>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var event = document.createEvent('MouseEvents');\n"
                +  "         alert(event.target);\n"
                +  "          event.initMouseEvent('click', true, true, window,\n"
                +  "               1, 0, 0, 0, 0, false, false, false, false, 0, null);\n"
                +  "         alert(event.target);\n"
                +  "          document.getElementById('d').dispatchEvent(event);\n"
                +  "        } catch (e) {alert('exception') }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("function onload(event) {alert(\"hi\") }")
    public void createEventOverridden() {
        final String html =
                "<html>\n"
                +  "  <body onload='test()'>\n"
                +  "    <div id='d' onclick='alert(onload)' onload='alert(\"hi\")'>abc</div>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var event = document.createEvent('MouseEvents');\n"
                +  "          event.initMouseEvent('click', true, true, window,\n"
                +  "               1, 0, 0, 0, 0, false, false, false, false, 0, null);\n"
                +  "          document.getElementById('d').dispatchEvent(event);\n"
                +  "        } catch (e) {alert('exception') }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("test")
    public void createEventCaller() {
        final String html =
                "<html>\n"
                +  "  <body onload='test()'>\n"
                +  "    <div id='d' onclick='var c = arguments.callee.caller;alert(c ? c.name : c)'>abc</div>\n"
                +  "    <script>\n"                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var event = document.createEvent('MouseEvents');\n"
                +  "          event.initMouseEvent('click', true, true, window,\n"
                +  "               1, 0, 0, 0, 0, false, false, false, false, 0, null);\n"
                +  "          document.getElementById('d').dispatchEvent(event);\n"
                +  "        } catch (e) {alert('exception') }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void caller() {
        final String html =
                "<html>\n"
                +  "  <body>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        var c = arguments.callee.caller;\n"
                +  "       alert(c ? c.name : c);\n"
                +  "      }\n"
                +  "      test();\n"
                +  "    </script>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("onload")
    public void callerEvent() {
        final String html =
                "<html>\n"
                +  "  <body onload='test()'>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        var c = arguments.callee.caller;\n"
                +  "       alert(c ? c.name : c);\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createEventObject_IE() {
        final String html =
                "<html><head>\n"
                +  "<script>\n"

                +  "try {\n"
                +  "  var e = document.createEventObject();\n"
                +  " alert(e != null);\n"
                +  " alert(typeof e);\n"
                +  " alert(e);\n"
                +  "} catch(ex) {\n"
                +  " alert('exception');\n"
                +  "}\n"
                +  "</script></head><body>\n"
                +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void elementFromPoint() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "  function test() {\n"
        +  "    var e = document.elementFromPoint(-1,-1);\n"
        +  "   alert(e != null ? e.nodeName : null);\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object StyleSheetList]", "0", "true"})
    public void styleSheets() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "  function test() {\n"
        +  "   alert(document.styleSheets);\n"
        +  "   alert(document.styleSheets.length);\n"
        +  "   alert(document.styleSheets == document.styleSheets);\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"off", "off", "on", "on", "on", "off", "off", "off", "off"})
    public void designModeRoot() {
        designMode("document");
    }

    @Test
    @Alerts({"off", "off", "on", "on", "on", "off", "off", "off", "off"})
    public void designModeIframe() {
        designMode("window.frames['f'].document");
    }

    private void designMode(final String doc) {
        final String html = "<html><body><iframe name='f' id='f'></iframe><script>\n"

        +  "var d = " + doc + ";\n"
        +  "alert(d.designMode);\n"
        +  "try{d.designMode = 'abc';}catch(e){alert('!');}\n"
        +  "alert(d.designMode);\n"
        +  "try{d.designMode = 'on';}catch(e){alert('!');}\n"
        +  "alert(d.designMode);\n"
        +  "try{d.designMode = 'On';}catch(e){alert('!');}\n"
        +  "alert(d.designMode);\n"
        +  "try{d.designMode = 'abc';}catch(e){alert('!');}\n"
        +  "alert(d.designMode);\n"
        +  "try{d.designMode = 'Off';}catch(e){alert('!');}\n"
        +  "alert(d.designMode);\n"
        +  "try{d.designMode = 'off';}catch(e){alert('!');}\n"
        +  "alert(d.designMode);\n"
        +  "try{d.designMode = 'Inherit';}catch(e){alert('!');}\n"
        +  "alert(d.designMode);\n"
        +  "try{d.designMode = 'inherit';}catch(e){alert('!');}\n"
        +  "alert(d.designMode);\n"
        +  "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0"})
    public void designModeCreatesSelectionRange() {
        final String html = "<html><body onload='test()'>\n"
        +  "<script>\n"
        +  "  var selection = document.selection;\n"
        +  "  if(!selection) selection = window.getSelection();\n"
        +  "  function test() {\n"
        +  "    alert(selection.rangeCount);\n"
        +  "    document.designMode = 'on';\n"
        +  "    alert(selection.rangeCount);\n"
        +  "    document.designMode = 'off';\n"
        +  "    alert(selection.rangeCount);\n"
        +  "  }\n"
        +  "</script>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "false"})
    public void execCommand() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "  function test() {\n"
        +  "    document.designMode = 'On';\n"
        +  "   alert(document.execCommand('Bold', false, null));\n"
        +  "    try {\n"
        +  "     alert(document.execCommand('foo', false, null));\n"
        +  "    }\n"
        +  "    catch (e) {\n"
        +  "     alert('command foo not supported');\n"
        +  "    }\n"
        +  "    document.designMode = 'Off';\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLHeadingElement]")
    public void evaluateCaseInsensitiveAttribute() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "function test() {\n"
        +  "  if(document.evaluate) {\n"
        +  "    var expr = './/*[@CLASS]';\n"
        +  "    var result = document.evaluate(expr, document.documentElement, null, XPathResult.ANY_TYPE, null);\n"
        +  "   alert(result.iterateNext());\n"
        +  "  } else {alert('not available'); }\n"
        +  "}\n"
        +  "</script></head><body onload='test()'>\n"
        +  "  <h1 class='title'>Some text</h1>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLHtmlElement]")
    public void evaluateCaseInsensitiveTagName() {
        final String html = "<html><head>\n"
        +  "<script>\n"

        +  "  function test() {\n"
        +  "    if(document.evaluate) {\n"
        +  "      var expr = '/hTmL';\n"
        +  "      var result = document.evaluate(expr, "
        +  "document.documentElement, null, XPathResult.ANY_TYPE, null);\n"
        +  "     alert(result.iterateNext());\n"
        +  "    } else {alert('not available'); }\n"
        +  "  }\n"
        +  "</script></head>\n"
        +  "<body onload='test()'>\n"
        +  "  <h1 class='title'>Some text</h1>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1: null", "2: null", "3: [object HTMLBodyElement]"})
    public void noBodyTag() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "    </script>\n"
                +  "    <script>alert('1: ' + document.body);</script>\n"
                +  "    <script defer=''>alert('2: ' + document.body);</script>\n"
                +  "    <script>window.onload = function() {alert('3: ' + document.body); }</script>\n"
                +  "  </head>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1: [object HTMLBodyElement]", "2: [object HTMLBodyElement]"})
    public void noBodyTag_IFrame() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "<script>\n"

                +  "</script>\n"
                +  "  </head>\n"
                +  "  <body>\n"
                +  "    <iframe id='i'></iframe>\n"
                +  "    <script>\n"                +  "     alert('1: ' + document.getElementById('i').contentWindow.document.body);\n"
                +  "      window.onload = function() {\n"
                +  "       alert('2: ' + document.getElementById('i').contentWindow.document.body);\n"
                +  "      };\n"
                +  "    </script>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void ownerDocument() {
        final String html = "<html>\n"
        +  "<body id='hello' onload='doTest()'>\n"
        +  "  <script>\n"

        +  "    function doTest() {\n"
        +  "     alert(document.ownerDocument);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</body>\n" + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDocument]", "true"})
    public void getRootNode() {
        final String html = "<html>\n"
        +  "<body id='hello' onload='doTest()'>\n"
        +  "  <script>\n"

        +  "    function doTest() {\n"
        +  "      if (document.getRootNode) {\n"
        +  "       alert(document.getRootNode());\n"
        +  "       alert(document === document.getRootNode());\n"
        +  "      } elsealert('-');\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</body>\n" + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "text1", "not available"})
    public void setActive() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  " alert(document.activeElement);\n"
        +  "  function test() {\n"
        +  "   alert(document.activeElement.id);\n"
        +  "    var inp = document.getElementById('text2');\n"
        +  "    if (inp.setActive) {\n"
        +  "      inp.setActive();\n"
        +  "     alert(document.activeElement.id);\n"
        +  "    } else {alert('not available'); }\n"
        +  "  }\n"
        +  "</script></head>\n"
        +  "<body>\n"
        +  "  <input id='text1' onclick='test()'>\n"
        +  "  <input id='text2' onfocus='alert(\"onfocus text2\")'>\n"
        +  "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("text1");
        elem.getOnclick();
    }

    @Test
    @Alerts({"123", "captured"})
    public void captureEvents() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "  function t() {alert('captured'); }\n"
        +  "  if(document.captureEvents) {\n"
        +  "    document.captureEvents(Event.CLICK);\n"
        +  "    document.onclick = t;\n"
        +  "  } else {alert('not available'); }\n"
        +  "</script></head><body>\n"
        +  "<div id='theDiv' onclick='alert(123)'>foo</div>\n"
        +  "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("theDiv");
        elem.getOnclick();
    }

    @Test
    @Alerts({"true", "false", "true", "true", "true", "false", "false"})
    public void contains() {
        final String html = "<html><head><script>\n"

        +  "  function test() {\n"
        +  "    var testnode = document.getElementById('myNode');\n"
        +  "   alert(document.contains ? document.contains(testnode) : '-');\n"

        +  "    var newnode = document.createComment('some comment');\n"
        +  "   alert(document.contains ? document.contains(newnode) : '-');\n"

        +  "   alert(document.contains ? document.contains(document.documentElement) : '-');\n"
        +  "   alert(document.contains ? document.contains(document.body) : '-');\n"
        +  "   alert(document.contains ? document.contains(document.firstElementChild) : '-');\n"

        +  "   alert(document.contains ? document.contains(null) : '-');\n"
        +  "   alert(document.contains ? document.contains(undefined) : '-');\n"
        +  "  }\n"
        +  "</script></head>\n"
        +  "<body onload='test()'>\n"
        +  "  <div id='myNode'></div>\n"
        +  "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Comment]", "false"})
    public void createComment() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "<script>\n"

        +  "function test() {\n"
        +  "  var elt = document.createComment('some comment');\n"
        +  " alert(elt);\n"
        +  " alert(document.contains ? document.contains(elt) : '-');\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'>\n"
        +  "</body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void oninput() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "     alert('oninput' in document);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'>\n"
        +  "</body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "42"})
    public void documentDefineProperty() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "     alert(document.testProp);\n"

        +  "      Object.defineProperty(document, 'testProp', {\n"
        +  "        value: 42,\n"
        +  "        writable: true,\n"
        +  "        enumerable: true,\n"
        +  "        configurable: true\n"
        +  "      });\n"
        +  "     alert(document.testProp);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'>\n"
        +  "</body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "undefined"})
    public void urlUnencoded() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "     alert(document.URL);\n"
        +  "     alert(document.URLUnencoded);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'>\n"
        +  "</body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLHtmlElement]"})
    public void children() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      if (document.children) {\n"
        +  "       alert(document.children.length);\n"
        +  "       alert(document.children.item(0));\n"
        +  "      }\n"
        +  "      else {\n"
        +  "       alert('not found');\n"
        +  "      }\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"application/xml", "text/html"})
    public void contentType() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      var xmlDocument = document.implementation.createDocument('', '', null);\n"
        +  "     alert(xmlDocument.contentType);\n"
        +  "     alert(document.contentType);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null"})
    public void xmlEncoding() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      var xmlDocument = document.implementation.createDocument('', '', null);\n"
        +  "     alert(xmlDocument.xmlEncoding);\n"
        +  "     alert(document.xmlEncoding);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false"})
    public void xmlStandalone() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      var xmlDocument = document.implementation.createDocument('', '', null);\n"
        +  "     alert(xmlDocument.xmlStandalone);\n"
        +  "     alert(document.xmlStandalone);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1.0", "null"})
    public void xmlVersion() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      var xmlDocument = document.implementation.createDocument('', '', null);\n"
        +  "     alert(xmlDocument.xmlVersion);\n"
        +  "     alert(document.xmlVersion);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null"})
    public void rootElement() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      var xmlDocument = document.implementation.createDocument('', '', null);\n"
        +  "     alert(xmlDocument.rootElement);\n"
        +  "     alert(document.rootElement);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLHtmlElement]", "[object HTMLHtmlElement]"})
    public void firstElementChild() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "     alert(document.childElementCount);\n"
        +  "     alert(document.firstElementChild);\n"
        +  "     alert(document.lastElementChild);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLHtmlElement]", "[object HTMLHtmlElement]"})
    public void firstElementChildDoctype() {
        final String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
        +  "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
        +  "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "     alert(document.childElementCount);\n"
        +  "     alert(document.firstElementChild);\n"
        +  "     alert(document.lastElementChild);\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "test"})
    public void useInMap() {
        final String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
        +  "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
        +  "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      var map = new Map();\n"
        +  "      map.set(document, 'test');\n"
        +  "     alert(map.has(document));\n"
        +  "     alert(map.get(document));\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "test"})
    public void useInWeakMap() {
        final String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
        +  "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
        +  "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      var map = new WeakMap();\n"
        +  "      map.set(document, 'test');\n"
        +  "     alert(map.has(document));\n"
        +  "     alert(map.get(document));\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void useInSet() {
        final String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
        +  "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
        +  "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      var set = new Set();\n"
        +  "      set.add(document, 'test');\n"
        +  "     alert(set.has(document));\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void useInWeakSet() {
        final String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
        +  "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
        +  "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      if (window.WeakSet) {\n"
        +  "        var set = new WeakSet();\n"
        +  "        set.add(document, 'test');\n"
        +  "       alert(set.has(document));\n"
        +  "      } else {\n"
        +  "       alert('no WeakSet');\n"
        +  "      }\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"about:blank", "about:blank", "undefined", "null", "null"})
    public void newDoc() {
        final String html = "<html>\n"
        +  "<head>\n"
        +  "  <script>\n"

        +  "    function test() {\n"
        +  "      if (typeof Document === 'object') {alert('no'); return ; }\n"

        +  "      try {\n"
        +  "        var doc = new Document();"
        +  "       alert(doc.documentURI);\n"
        +  "       alert(doc.URL);\n"
        +  "       alert(doc.origin);\n"
        +  "       alert(doc.firstElementChild);\n"
        +  "       alert(doc.defaultView);\n"
        +  "      } catch(e) {alert('exception'); }\n"
        +  "    }\n"
        +  "  </script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }
}
