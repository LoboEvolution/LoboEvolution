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
import org.loboevolution.html.node.Attr;

/**
 * Tests for {@link Attr}.
 */
@ExtendWith(AlertsExtension.class)
public class AttrTest extends LoboUnitTest {


    @Test
    @Alerts({"true", "exception thrown"})
    public void specified() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  try {\n"
                + "    var s = document.getElementById('testSelect');\n"
                + "    var o1 = s.options[0];\n"
                + "   alert(o1.getAttributeNode('value').specified);\n"
                + "    var o2 = s.options[1];\n"
                + "   alert(o2.getAttributeNode('value').specified);\n"
                + "  } catch(e) {\n"
                + "   alert('exception thrown');\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'>\n"
                + "  <select name='select1' id='testSelect'>\n"
                + "    <option name='option1' value='foo'>One</option>\n"
                + "    <option>Two</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void specified2() {
        final String html
                = "<html><body onload='test()'><div id='div' class='test'></div>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('div');\n"
                + "   alert(div.attributes.id.specified);\n"
                + "   alert(div.attributes.class.specified);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLOptionElement]")
    public void ownerElement() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var s = document.getElementById('testSelect');\n"
                + "  var o1 = s.options[0];\n"
                + " alert(o1.getAttributeNode('value').ownerElement);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'>\n"
                + "  <select name='select1' id='testSelect'>\n"
                + "    <option name='option1' value='foo'>One</option>\n"
                + "    <option>Two</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined"})
    public void isId() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var d = document.getElementById('d');\n"
                + " alert(d.getAttributeNode('id').isId);\n"
                + " alert(d.getAttributeNode('name').isId);\n"
                + " alert(d.getAttributeNode('width').isId);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<div iD='d' name='d' width='40'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined", "undefined"})
    public void expando() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var d = document.getElementById('d');\n"
                + " alert(d.attributes['id'].expando);\n"
                + " alert(d.attributes['name'].expando);\n"
                + " alert(d.attributes['style'].expando);\n"
                + " alert(d.attributes['custom'].expando);\n"
                + " alert(d.attributes['other'].expando);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d' name='d' style='display: block' custom='value' other></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("undefined")
    public void expandoEvent() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var d = document.getElementById('d');\n"
                + "  d.setAttribute('onfocusin', 't');\n"
                + " alert(d.attributes['onfocusin'].expando);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("test()")
    public void textContent() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var a = document.body.getAttributeNode('onload');\n"
                + " alert(a.textContent);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "null", "null"})
    public void getAttributeNodeUndefinedAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var elem = document.getElementById('myDiv');\n"
                + " alert(elem.getAttributeNode('class'));\n"
                + " alert(elem.getAttributeNode('style'));\n"
                + " alert(elem.getAttributeNode('unknown'));\n"
                + " alert(elem.getAttributeNode('name'));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='myDiv'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "null", "null"})
    public void getAttributesUndefinedAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var elem = document.getElementById('myDiv');\n"
                + " alert(elem.attributes.getNamedItem('class'));\n"
                + " alert(elem.attributes.getNamedItem('style'));\n"
                + " alert(elem.attributes.getNamedItem('unknown'));\n"
                + " alert(elem.attributes.getNamedItem('name'));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='myDiv'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Attr]", "", "[object Attr]", ""})
    public void value() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var attr = document.createAttribute('hi');\n"
                + "   alert(attr);\n"
                + "   alert(attr.value);\n"
                + "    attr = document.implementation.createDocument('', '', null).createAttribute('hi');\n"
                + "   alert(attr);\n"
                + "   alert(attr.value);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Attr]", "undefined"})
    public void html_baseName() {
        html("baseName");
    }

    @Test
    @Alerts({"[object Attr]", ""})
    public void html_baseURI() {
        html("baseURI");
    }

    @Test
    @Alerts({"[object Attr]", "null"})
    public void htmlNamespaceURI() {
        html("namespaceURI");
    }

    @Test
    @Alerts({"[object Attr]", "testattr"})
    public void htmlLocalName() {
        html("localName");
    }

    @Test
    @Alerts({"[object Attr]", "null"})
    public void htmlPrefix() {
        html("prefix");
    }

    private void html(final String methodName) {
        final String html
                = "<html>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    debug(document.getElementById('tester').attributes.getNamedItem('testAttr'));\n"
                + "  }\n"
                + "  function debug(e) {\n"
                + "   alert(e);\n"
                + "   alert(e." + methodName + ");\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='tester' testAttr='test'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Attr]", "undefined"})
    public void xml_baseName() {
        xml("baseName");
    }

    @Test
    @Alerts({"[object Attr]", "foo.xml"})
    public void xml_baseURI() {
        xml("baseURI");
    }

    @Test
    @Alerts({"[object Attr]", "null"})
    public void xmlNamespaceURI() {
        xml("namespaceURI");
    }

    @Test
    @Alerts({"[object Attr]", "testAttr"})
    public void xmlLocalName() {
        xml("localName");
    }

    @Test
    @Alerts({"[object Attr]", "null"})
    public void xmlPrefix() {
        xml("prefix");
    }

    private void xml(final String methodName) {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var request;\n"
                        + "        request = new XMLHttpRequest();\n"
                        + "        request.open('GET', 'foo.xml', false);\n"
                        + "        request.send('');\n"
                        + "        var doc = request.responseXML;\n"
                        + "        debug(doc.documentElement.childNodes[0].attributes.item(0));\n"
                        + "      }\n"
                        + "      function debug(e) {\n"
                        + "        try {\n"
                        + "         alert(e);\n"
                        + "        } catch(ex) {alert(ex)}\n"
                        + "       alert(e." + methodName + ");\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }
}
