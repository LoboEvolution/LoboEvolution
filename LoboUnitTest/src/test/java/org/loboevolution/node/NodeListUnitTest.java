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
 * Tests for {@link org.loboevolution.html.node.NodeList}.
 */

public class NodeListUnitTest extends LoboUnitTest {


    @Test
    public void defaultValue() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId').firstChild.childNodes);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div id='myId'>abcd</div>\n"
                + "</body></html>";

        final String[] messages = {"[object NodeList]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void has() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert(0 in nodeList);\n"
                + "    alert(4 in nodeList);\n"
                + "    alert(5 in nodeList);\n"
                + "    alert('entries' in nodeList);\n"
                + "    alert('forEach' in nodeList);\n"
                + "    alert('item' in nodeList);\n"
                + "    alert('keys' in nodeList);\n"
                + "    alert('length' in nodeList);\n"
                + "    alert('values' in nodeList);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"true", "true", "false", "true", "true", "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void length() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert(nodeList.length);\n"
                + "    alert('length' in nodeList);\n"
                + "    alert(Object.getOwnPropertyDescriptor(nodeList, 'length'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"5", "true", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void item() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert(nodeList.item(0));\n"
                + "    alert(nodeList.item(2));\n"
                + "    alert(nodeList.item(17));\n"
                + "    alert(nodeList.item(-1));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div></div>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLHtmlElement]", "[object HTMLTitleElement]", null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void itemBracketed() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert(nodeList[0]);\n"
                + "    alert(nodeList[2]);\n"
                + "    alert(nodeList[17]);\n"
                + "    alert(nodeList[-1]);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div></div>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLHtmlElement]", "[object HTMLTitleElement]", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void forIn() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var all = [];\n"
                + "    for (var i in document.querySelectorAll('*')) {\n"
                + "      all.push(i);\n"
                + "    }\n"
                + "    all.sort(sortFunction);\n"
                + "    alert(all);\n"
                + "  }\n"
                + "  function sortFunction(s1, s2) {\n"
                + "    return s1.toLowerCase() > s2.toLowerCase() ? 1 : -1;\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div></div>\n"
                + "</body></html>";

        final String[] messages = {"0,1,2,3,4,5,entries,forEach,item,keys,length,values"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void forInEmptyList() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var all = [];\n"
                + "    for (var i in document.querySelectorAll('.notThere')) {\n"
                + "      all.push(i);\n"
                + "    }\n"
                + "    all.sort(sortFunction);\n"
                + "    alert(all);\n"
                + "  }\n"
                + "  function sortFunction(s1, s2) {\n"
                + "    return s1.toLowerCase() > s2.toLowerCase() ? 1 : -1;\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div></div>\n"
                + "</body></html>";

        final String[] messages = {"entries,forEach,item,keys,length,values"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void iterator() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    if (!nodeList.forEach) {\n"
                + "      alert('no for..of');\n"
                + "      return;\n"
                + "    }\n"
                + "    for (var i of nodeList) {\n"
                + "      alert(i);\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div></div>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLHtmlElement]", "[object HTMLHeadElement]",
                "[object HTMLTitleElement]", "[object HTMLScriptElement]",
                "[object HTMLBodyElement]", "[object HTMLDivElement]"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void forEach() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    if (nodeList.forEach) {\n"
                + "      nodeList.forEach(myFunction, 'something');\n"
                + "    } else {\n"
                + "      alert('no forEach');\n"
                + "    }\n"
                + "  }\n"
                + "  function myFunction(value, index, list, arg) {\n"
                + "    alert(value + ' ' + index + ' ' + list + ' ' + arg);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div></div>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLHtmlElement] 0 [object NodeList] undefined",
                "[object HTMLHeadElement] 1 [object NodeList] undefined",
                "[object HTMLTitleElement] 2 [object NodeList] undefined",
                "[object HTMLScriptElement] 3 [object NodeList] undefined",
                "[object HTMLBodyElement] 4 [object NodeList] undefined",
                "[object HTMLDivElement] 5 [object NodeList] undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void entries() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    if (!nodeList.entries) {\n"
                + "      alert('not defined');\n"
                + "      return;\n"
                + "    }\n"
                + "    var i = nodeList.entries().next();\n"
                + "    for (var x in i) {\n"
                + "      alert(x);\n"
                + "    }\n"
                + "    var v = i.value;\n"
                + "    alert(typeof v);\n"
                + "    alert(v[0]);\n"
                + "    alert(v[1]);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"value", "done", "object", "0", "[object HTMLHtmlElement]"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void entriesPropertyDescriptor() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert('entries' in nodeList);\n"
                + "    alert(Object.getOwnPropertyDescriptor(nodeList, 'entries'));\n"
                + "    var desc = Object.getOwnPropertyDescriptor(Object.getPrototypeOf(nodeList), 'entries');\n"
                + "    if (desc === undefined) { alert('no entries'); return; }\n"
                + "    alert(typeof desc.value);\n"
                + "    alert(desc.get);\n"
                + "    alert(desc.set);\n"
                + "    alert(desc.writable);\n"
                + "    alert(desc.enumerable);\n"
                + "    alert(desc.configurable);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"true", "undefined", "function", "undefined", "undefined", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void entriesForOf() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    if (!nodeList.entries) {\n"
                + "      alert('not defined');\n"
                + "      return;\n"
                + "    }\n"
                + "    for (var i of nodeList.entries()) {\n"
                + "      alert(i);\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"0,[object HTMLHtmlElement]", "1,[object HTMLHeadElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void keys() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    if (!nodeList.keys) {\n"
                + "      alert('not defined');\n"
                + "      return;\n"
                + "    }\n"
                + "    var i = nodeList.keys().next();\n"
                + "    for (var x in i) {\n"
                + "      alert(x);\n"
                + "    }\n"
                + "    var v = i.value;\n"
                + "    alert(typeof v);\n"
                + "    alert(v);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"value", "done", "number", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void keysPropertyDescriptor() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert('keys' in nodeList);\n"
                + "    alert(Object.getOwnPropertyDescriptor(nodeList, 'keys'));\n"
                + "    var desc = Object.getOwnPropertyDescriptor(Object.getPrototypeOf(nodeList), 'keys');\n"
                + "    if (desc === undefined) { alert('no keys'); return; }\n"
                + "    alert(typeof desc.value);\n"
                + "    alert(desc.get);\n"
                + "    alert(desc.set);\n"
                + "    alert(desc.writable);\n"
                + "    alert(desc.enumerable);\n"
                + "    alert(desc.configurable);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"true", "undefined", "function", "undefined", "undefined", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void keysForOf() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    if (!nodeList.keys) {\n"
                + "      alert('not defined');\n"
                + "      return;\n"
                + "    }\n"
                + "    for (var i of nodeList.keys()) {\n"
                + "      alert(i);\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"0", "1", "2", "3", "4"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void objectKeys() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert(Object.keys(nodeList));\n"
                + "    nodeList = document.querySelectorAll('.notThere');\n"
                + "    alert(Object.keys(nodeList));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"0,1,2,3,4", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void values() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    if (!nodeList.values) {\n"
                + "      alert('not defined');\n"
                + "      return;\n"
                + "    }\n"
                + "    var i = nodeList.values().next();\n"
                + "    for (var x in i) {\n"
                + "      alert(x);\n"
                + "    }\n"
                + "    var v = i.value;\n"
                + "    alert(typeof v);\n"
                + "    alert(v);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"value", "done", "object", "[object HTMLHtmlElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void valuesPropertyDescriptor() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert('values' in nodeList);\n"
                + "    alert(Object.getOwnPropertyDescriptor(nodeList, 'values'));\n"
                + "    var desc = Object.getOwnPropertyDescriptor(Object.getPrototypeOf(nodeList), 'values');\n"
                + "    if (desc === undefined) { alert('no values'); return; }\n"
                + "    alert(typeof desc.value);\n"
                + "    alert(desc.get);\n"
                + "    alert(desc.set);\n"
                + "    alert(desc.writable);\n"
                + "    alert(desc.enumerable);\n"
                + "    alert(desc.configurable);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"true", "undefined", "function", "undefined", "undefined", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void valuesForOf() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    if (!nodeList.values) {\n"
                + "      alert('not defined');\n"
                + "      return;\n"
                + "    }\n"
                + "    for (var i of nodeList.values()) {\n"
                + "      alert(i);\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"[object HTMLHtmlElement]", "[object HTMLHeadElement]",
                "[object HTMLTitleElement]", "[object HTMLScriptElement]",
                "[object HTMLBodyElement]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getOwnPropertySymbols() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  if (Object.getOwnPropertySymbols) {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    var objectSymbols = Object.getOwnPropertySymbols(nodeList);\n"
                + "    alert(objectSymbols.length);\n"
                + "    var objectNames = Object.getOwnPropertyNames(nodeList);\n"
                + "    alert(objectNames.length);\n"
                + "    alert(objectNames[0]);\n"
                + "    alert(objectNames[1]);\n"
                + "    alert(objectNames[2]);\n"
                + "    alert(objectNames[3]);\n"
                + "  } else { alert('not defined'); }\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>\n";

        final String[] messages = {"0", "4", "0", "1", "2", "3"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void getOwnPropertySymbolsEmptyList() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  if (Object.getOwnPropertySymbols) {\n"
                + "    var nodeList = document.querySelectorAll('.notThere');\n"
                + "    var objectSymbols = Object.getOwnPropertySymbols(nodeList);\n"
                + "    alert(objectSymbols.length);\n"
                + "    var objectNames = Object.getOwnPropertyNames(nodeList);\n"
                + "    alert(objectNames.length);\n"
                + "  } else { alert('not defined'); }\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>\n";

        final String[] messages = {"0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setItem() {
        final String html = "<html><head><title>test</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var nodeList = document.querySelectorAll('*');\n"
                + "    alert(nodeList.item(1));\n"
                + "    nodeList[1] = nodeList.item(0);\n"
                + "    alert(nodeList.item(1));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>\n";

        final String[] messages = {"[object HTMLHeadElement]", "[object HTMLHeadElement]"};
        checkHtmlAlert(html, messages);
    }
}
