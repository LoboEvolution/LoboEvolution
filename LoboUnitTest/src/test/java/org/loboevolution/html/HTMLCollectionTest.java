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
package org.loboevolution.html;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;

/**
 * Tests for {@link HTMLCollection}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLCollectionTest extends LoboUnitTest {

    @Test
    @Alerts("true")
    public void implicitToStringConversion() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  alert(document.links != 'foo')\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='bla.html'>link</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Test that <tt>toString</tt> is accessible.
     */
    @Test
    @Alerts("function")
    public void toStringFunction() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  alert(typeof document.links.toString);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<a href='bla.html'>link</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "exception"})
    public void getElements() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  alert(document.all.length);\n"
                + "  try {\n"
                + "    document.appendChild(document.createElement('div'));\n"
                + "    alert(document.all.length);\n"
                + "  } catch (e) { alert('exception') }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"string 0", "string item", "string length", "string namedItem"})
    public void forIn() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var arr = new Array();\n"
                + "    \n"
                + "    for (i in document.forms) {\n"
                + "      arr[arr.length] = (typeof i) + ' ' + i;\n"
                + "    }\n"
                + "    arr.sort();\n"
                + "    for (i = 0; i < arr.length; i++) {\n"
                + "      alert(arr[i]);\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"string 0", "string 1", "string 2", "string 3", "string 4", "string 5",
            "string item", "string length", "string namedItem"})
    public void forIn2() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var form = document.getElementById('myForm');\n"
                + "    var x = form.getElementsByTagName('*');\n"
                + "    var arr = new Array();\n"
                + "    for (i in x){\n"
                + "      arr[arr.length] = (typeof i) + ' ' + i;\n"
                + "    }\n"
                + "    arr.sort();\n"
                + "    for (i = 0; i < arr.length; i++) {\n"
                + "      alert(arr[i]);\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<form id='myForm'>\n"
                + "  <input type='text' id='id1' name='val1' id='input_enabled' value='4'>\n"
                + "  <div>This is not a form element</div>\n"
                + "  <input type='text' name='val2' id='input_disabled' disabled='disabled' value='5'>\n"
                + "  <input type='submit' name='firstsubmit' value='Commit it!'>\n"
                + "  <input type='submit' id='secondsubmit' value='Delete it!'>\n"
                + "  <input type='text' name='action' value='blah'>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * <code>document.all.tags</code> is different from <code>document.forms.tags</code>!
     */
    @Test
    @Alerts({"false", "false"})
    public void tags() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.all.tags != undefined);\n"
                + "    alert(document.forms.tags != undefined);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Depending on the method used, out of bound access give different responses.
     */
    @Test
    @Alerts({"null", "null", "undefined", "exception"})
    public void outOfBoundAccess() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var col = document.getElementsByTagName('a');\n"
                + "    alert(col.item(1));\n"
                + "    alert(col.namedItem('foo'));\n"
                + "    alert(col[1]);\n"
                + "    try {\n"
                + "      alert(col(1));\n"
                + "    } catch (e) { alert('exception') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined"})
    public void inexistentProperties() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var x = document.documentElement.childNodes;\n"
                + "    alert(x.split);\n"
                + "    alert(x.setInterval);\n"
                + "    alert(x.bogusNonExistentProperty);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "#text", "5"})
    public void childNodes() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.body.childNodes.length);\n"
                + "    alert(document.body.firstChild.nodeName);\n"
                + "    alert(document.getElementById('myDiv').childNodes.length);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'> <div id='myDiv'> <div> </div> <div> </div> </div> </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("object")
    public void typeof() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(typeof document.getElementsByTagName('a'));\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    /**
     * Verifies that dollar signs don't cause exceptions in {@link HTMLCollection} (which uses Java
     * regex internally). Found via the MooTools unit tests.
     */
    @Test
    @Alerts({"[object HTMLHeadingElement]", "undefined"})
    public void getElementWithDollarSign() {
        final String html
                = "<h3 id='$h'>h</h3><script>\n"
                + "var hs = document.getElementsByTagName('h3');\n"
                + "alert(hs['$h']);\n"
                + "alert(hs['$n']);\n"
                + "</script>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function", "function", "function", "function"})
    public void arrayPrototype() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(typeof Object.prototype.__defineGetter__);\n"
                + "    alert(typeof Object.prototype.__lookupGetter__);\n"
                + "    alert(typeof Array.prototype.indexOf);\n"
                + "    alert(typeof Array.prototype.map);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function", "function", "function", "function"})
    public void arrayPrototypestandards() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(typeof Object.prototype.__defineGetter__);\n"
                + "    alert(typeof Object.prototype.__lookupGetter__);\n"
                + "    alert(typeof Array.prototype.indexOf);\n"
                + "    alert(typeof Array.prototype.map);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void has() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(0 in document.forms);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myForm", "mySecondForm"})
    public void forOf() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {"
                + "      for (f of document.forms) {\n"
                + "        alert(f.name);\n"
                + "      }\n"
                + "    } catch(e) { alert('exception') }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "<form name='mySecondForm'></form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myForm", "mySecondForm", "dynamicForm", "-", "myForm", "mySecondForm", "dynamicForm"})
    public void forOfDynamicAtEnd() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {"
                + "      var i = 0;\n"
                + "      for (f of document.forms) {\n"
                + "        i++;\n"
                + "        if (i == 1) {\n"
                + "          var frm = document.createElement('FORM');\n"
                + "          frm.name = 'dynamicForm';\n"
                + "          document.body.appendChild(frm);\n"
                + "        }\n"
                + "        alert(f.name);\n"
                + "      }\n"
                + "      alert('-');\n"
                + "      for (f of document.forms) {\n"
                + "        alert(f.name);\n"
                + "      }\n"
                + "    } catch(e) { alert('exception') }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "<form name='mySecondForm'></form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myForm", "myForm", "mySecondForm", "-", "dynamicForm", "myForm", "mySecondForm"})
    public void forOfDynamicAtStart() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try{\n"
                + "      var i = 0;\n"
                + "      for (f of document.forms) {\n"
                + "        i++;\n"
                + "        if (i == 1) {\n"
                + "          var frm = document.createElement('FORM');\n"
                + "          frm.name = 'dynamicForm';\n"
                + "          document.body.insertBefore(frm, document.getElementsByName('myForm')[0]);\n"
                + "        }\n"
                + "        alert(f.name);\n"
                + "      }\n"
                + "      alert('-');\n"
                + "      for (f of document.forms) {\n"
                + "        alert(f.name);\n"
                + "      }\n"
                + "    } catch(e) { alert('exception') }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "<form name='mySecondForm'></form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("b1-button1")
    public void itemUnknown() {
        item("'foo'");
    }

    @Test
    @Alerts("b1-button1")
    public void itemById() {
        item("'b2'");
    }

    @Test
    @Alerts("b1-button1")
    public void itemByName() {
        item("'button2'");
    }

    @Test
    @Alerts("null")
    public void item_NegativIndex() {
        item("-1");
    }

    @Test
    @Alerts("b1-button1")
    public void itemZeroIndex() {
        item("0");
    }

    @Test
    @Alerts("b2-button2")
    public void item_ValidIndex() {
        item("1");
    }

    @Test
    @Alerts("b2-button2")
    public void item_DoubleIndex() {
        item("1.1");
    }

    @Test
    @Alerts("null")
    public void itemInvalidIndex() {
        item("2");
    }

    @Test
    @Alerts("b2-button2")
    public void itemIndexAsString() {
        item("'1'");
    }

    @Test
    @Alerts("b2-button2")
    public void itemIndexDoubleAsString() {
        item("'1.1'");
    }

    private void item(final String name) {
        final String html
                = "<!doctype html>\n"
                + "<html><head>\n"
                + "    <script>\n"
                + "  function report(result) {\n"
                + "    if (result == null || result == undefined) {\n"
                + "      alert(result);\n"
                + "    } else if (('length' in result) && ('item' in result)) {\n"
                + "      alert('coll ' + result.length);\n"
                + "      for(var i = 0; i < result.length; i++) {\n"
                + "        alert(result.item(i).id + '-' + result.item(i).name);\n"
                + "      }\n"
                + "    } else if (result.id || result.name) {\n"
                + "      alert(result.id + '-' + result.name);\n"
                + "    } else {\n"
                + "      alert(result);\n"
                + "    }\n"
                + "  }\n"
                + "  function doTest() {\n"
                + "    try {\n"
                + "      var col = document.getElementsByTagName('button');\n"
                + "      report(col.item(" + name + "));\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <button id='b1' name='button1'></button>\n"
                + "  <button id='b2' name='button2'></button>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("undefined")
    public void arrayIndexUnknown() {
        arrayIndex("'foo'");
    }

    @Test
    @Alerts("b2-button2")
    public void arrayIndexById() {
        arrayIndex("'b2'");
    }

    @Test
    @Alerts("b2-button2")
    public void arrayIndexByName() {
        arrayIndex("'button2'");
    }

    @Test
    @Alerts("undefined")
    public void arrayIndexNegativIndex() {
        arrayIndex("-1");
    }

    @Test
    @Alerts("b1-button1")
    public void arrayIndexZeroIndex() {
        arrayIndex("0");
    }

    @Test
    @Alerts("b2-button2")
    public void arrayIndexValidIndex() {
        arrayIndex("1");
    }

    @Test
    @Alerts("undefined")
    public void arrayIndexDoubleIndex() {
        arrayIndex("1.1");
    }

    @Test
    @Alerts("undefined")
    public void arrayIndexInvalidIndex() {
        arrayIndex("2");
    }

    @Test
    @Alerts("undefined")
    public void arrayIndexIndexAsString() {
        arrayIndex("'2'");
    }

    private void arrayIndex(final String name) {
        final String html
                = "<!doctype html>\n"
                + "<html><head>\n"
                + "    <script>\n"
                + "  function report(result) {\n"
                + "    if (result == null || result == undefined) {\n"
                + "      alert(result);\n"
                + "    } else if (('length' in result) && ('item' in result)) {\n"
                + "      alert('coll ' + result.length);\n"
                + "      for(var i = 0; i < result.length; i++) {\n"
                + "        alert(result.item(i).id + '-' + result.item(i).name);\n"
                + "      }\n"
                + "    } else if (result.id || result.name) {\n"
                + "      alert(result.id + '-' + result.name);\n"
                + "    } else {\n"
                + "      alert(result);\n"
                + "    }\n"
                + "  }\n"
                + "  function doTest() {\n"
                + "    try {\n"
                + "      var col = document.getElementsByTagName('button');\n"
                + "      report(col[" + name + "]);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <button id='b1' name='button1'></button>\n"
                + "  <button id='b2' name='button2'></button>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void functionIndexUnknown() {
        functionIndex("'foo'");
    }

    @Test
    @Alerts("exception")
    public void functionIndexById() {
        functionIndex("'b2'");
    }

    @Test
    @Alerts("exception")
    public void functionIndexByName() {
        functionIndex("'button2'");
    }

    @Test
    @Alerts("exception")
    public void functionIndexNegativIndex() {
        functionIndex("-1");
    }

    @Test
    @Alerts("exception")
    public void functionIndexZeroIndex() {
        functionIndex("0");
    }

    @Test
    @Alerts("exception")
    public void functionIndexValidIndex() {
        functionIndex("1");
    }

    @Test
    @Alerts("exception")
    public void functionIndexDoubleIndex() {
        functionIndex("1.1");
    }

    @Test
    @Alerts("exception")
    public void functionIndexInvalidIndex() {
        functionIndex("2");
    }

    @Test
    @Alerts("exception")
    public void functionIndexIndexAsString() {
        functionIndex("'2'");
    }

    private void functionIndex(final String name) {
        final String html
                = "<!doctype html>\n"
                + "<html><head>\n"
                + "    <script>\n"
                + "  function report(result) {\n"
                + "    if (result == null || result == undefined) {\n"
                + "      alert(result);\n"
                + "    } else if (('length' in result) && ('item' in result)) {\n"
                + "      alert('coll ' + result.length);\n"
                + "      for(var i = 0; i < result.length; i++) {\n"
                + "        alert(result.item(i).id + '-' + result.item(i).name);\n"
                + "      }\n"
                + "    } else if (result.id || result.name) {\n"
                + "      alert(result.id + '-' + result.name);\n"
                + "    } else {\n"
                + "      alert(result);\n"
                + "    }\n"
                + "  }\n"
                + "  function doTest() {\n"
                + "    try {\n"
                + "      var col = document.getElementsByTagName('button');\n"
                + "      report(col(" + name + "));\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <button id='b1' name='button1'></button>\n"
                + "  <button id='b2' name='button2'></button>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void namedItem_Unknown() {
        namedItem("foo");
    }

    @Test
    @Alerts("button1-")
    public void namedItemById() {
        namedItem("button1");
    }

    @Test
    @Alerts("-button2")
    public void namedItemByName_formWithoutId() {
        namedItem("button2");
    }

    @Test
    @Alerts("b3-button3")
    public void namedItemByName() {
        namedItem("button3");
    }

    @Test
    @Alerts("b4-button4_1")
    public void namedItem_DuplicateId() {
        namedItem("b4");
    }

    @Test
    @Alerts("b5_1-button5")
    public void namedItem_DuplicateName() {
        namedItem("button5");
    }

    @Test
    @Alerts("b6-button6")
    public void namedItem_DuplicateIdName() {
        namedItem("button6");
    }

    @Test
    @Alerts("b1-button1")
    public void namedItemZeroIndex() {
        item("0");
    }

    @Test
    @Alerts("b2-button2")
    public void namedItemValidIndex() {
        item("1");
    }

    @Test
    @Alerts("b2-button2")
    public void namedItem_DoubleIndex() {
        item("1.1");
    }

    @Test
    @Alerts("null")
    public void namedItemInvalidIndex() {
        item("200");
    }

    @Test
    @Alerts("b2-button2")
    public void namedItemIndexAsString() {
        item("'1'");
    }

    @Test
    @Alerts("b2-button2")
    public void namedItemIndexDoubleAsString() {
        item("'1.1'");
    }

    private void namedItem(final String name) {
        final String html
                = "<!doctype html>\n"
                + "<html><head>\n"
                + "    <script>\n"
                + "  function report(result) {\n"
                + "    if (result == null || result == undefined) {\n"
                + "      alert(result);\n"
                + "    } else if (result.id || result.name) {\n"
                + "      alert(result.id + '-' + result.name);\n"
                + "    } else {\n"
                + "      alert('coll ' + result.length);\n"
                + "      for(var i = 0; i < result.length; i++) {\n"
                + "        alert(result.item(i).id + '-' + result.item(i).name);\n"
                + "      }\n"
                + "    }\n"
                + "   }\n"
                + "  function doTest() {\n"
                + "    var col = document.getElementsByTagName('button');\n"
                + "    report(col.namedItem('" + name + "'));\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <button id='button1'></button>\n"
                + "  <button name='button2'></button>\n"
                + "  <button id='b3' name='button3'></button>\n"
                + "  <button id='b4' name='button4_1'></button>\n"
                + "  <button id='b4' name='button4_2'></button>\n"
                + "  <button id='b5_1' name='button5'></button>\n"
                + "  <button id='b5_2' name='button5'></button>\n"
                + "  <button id='b6' name='button6'></button>\n"
                + "  <button id='button6' name='button6_2'></button>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "1"})
    public void setLength() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  var x = document.children;\n"
                + "  try {\n"
                + "    alert(x.length);\n"
                + "    x.length = 100;\n"
                + "    alert(x.length);\n"
                + "  } catch(e) { alert('Type error'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "Type error"})
    public void setLengthStrictMode() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  'use strict';\n"
                + "  var x = document.children;\n"
                + "  try {\n"
                + "    alert(x.length);\n"
                + "    x.length = 100;\n"
                + "    alert(x.length);\n"
                + "  } catch(e) { alert('Type error'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
