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
        final String[] messages = {"[object Attr]", "testattr"};
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
