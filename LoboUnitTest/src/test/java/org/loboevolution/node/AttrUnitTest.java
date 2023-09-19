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
 * Tests for {@link org.loboevolution.html.node.Attr}.
 */
public class AttrUnitTest extends LoboUnitTest {

    @Test
    public void specified() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  try {\n"
                + "    var s = document.getElementById('testSelect');\n"
                + "    var o1 = s.options[0];\n"
                + "    alert(o1.getAttributeNode('value').specified);\n"
                + "    var o2 = s.options[1];\n"
                + "    alert(o2.getAttributeNode('value').specified);\n"
                + "  } catch(e) {\n"
                + "    alert('exception thrown');\n"
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
        final String[] messages = {"true", "exception thrown"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void specified2() {
        final String html
                = "<html><body onload='test()'><div id='div' class='test'></div>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('div');\n"
                + "    alert(div.attributes.id.specified);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void ownerElement() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var s = document.getElementById('testSelect');\n"
                + "  var o1 = s.options[0];\n"
                + "  alert(o1.getAttributeNode('value').ownerElement);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<form name='form1'>\n"
                + "  <select name='select1' id='testSelect'>\n"
                + "    <option name='option1' value='foo'>One</option>\n"
                + "    <option>Two</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body></html>";
        final String[] messages = {"[object HTMLOptionElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void textContent() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var a = document.body.getAttributeNode('onload');\n"
                + "  alert(a.textContent);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"test()"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getAttributeNodeUndefinedAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var elem = document.getElementById('myDiv');\n"
                + "  alert(elem.getAttributeNode('class'));\n"
                + "  alert(elem.getAttributeNode('style'));\n"
                + "  alert(elem.getAttributeNode('unknown'));\n"
                + "  alert(elem.getAttributeNode('name'));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='myDiv'></div>\n"
                + "</body></html>";
        final String[] messages = {null, null, null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getAttributesUndefinedAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var elem = document.getElementById('myDiv');\n"
                + "  alert(elem.attributes.getNamedItem('class'));\n"
                + "  alert(elem.attributes.getNamedItem('style'));\n"
                + "  alert(elem.attributes.getNamedItem('unknown'));\n"
                + "  alert(elem.attributes.getNamedItem('name'));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='myDiv'></div>\n"
                + "</body></html>";
        final String[] messages = {null, null, null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void value() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var attr = document.createAttribute('hi');\n"
                + "    alert(attr);\n"
                + "    alert(attr.value);\n"
                + "    attr = document.implementation.createDocument('', '', null).createAttribute('hi');\n"
                + "    alert(attr);\n"
                + "    alert(attr.value);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object Attr]", "", "[object Attr]", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void html_baseName() {
        final String[] messages = {"[object Attr]", "undefined"};
        html("baseName", messages);
    }

    @Test
    public void html_namespaceURI() {
        final String[] messages = {"[object Attr]", null};
        html("namespaceURI", messages);
    }

    @Test
    public void html_localName() {
        final String[] messages = {"[object Attr]", "testAttr"};
        html("localName", messages);
    }

    @Test
    public void htmlPrefix() {
        final String[] messages = {"[object Attr]", null};
        html("prefix", messages);
    }

    private void html(final String methodName, final String[] messages) {
        final String html
                = "<html>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    debug(document.getElementById('tester').attributes.getNamedItem('testAttr'));\n"
                + "  }\n"
                + "  function debug(e) {\n"
                + "    alert(e);\n"
                + "    alert(e." + methodName + ");\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='tester' testAttr='test'></div>\n"
                + "</body></html>";
        checkHtmlAlert(html, messages);
    }
}
