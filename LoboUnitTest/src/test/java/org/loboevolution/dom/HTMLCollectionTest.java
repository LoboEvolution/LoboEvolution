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
 * * Tests for {@link org.loboevolution.html.dom.HTMLCollection}.
 */
public class HTMLCollectionTest extends LoboUnitTest {

   /**
    * <p>implicitToStringConversion.</p>
    */
   @Test
    public void implicitToStringConversion() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  alert(document.links != 'foo')\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='bla.html'>link</a>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>toStringFunction.</p>
     */
    @Test
    public void toStringFunction() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  alert(typeof document.links.toString);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<a href='bla.html'>link</a>\n"
                + "</body></html>";

        final String[] messages = {"function"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElements.</p>
     */
    @Test
    public void getElements() {
        final String html
                = "<html><head><title>foo</title><script>\n"
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

        final String[] messages = {"5", "exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>for_in.</p>
     */
    @Test
    public void for_in() {
        final String html = "<html><head><title>foo</title><script>\n"
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

        final String[] messages = {"string 0", "string item", "string length", "string namedItem"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>for_in2.</p>
     */
    @Test
    public void for_in2() {
        final String html = "<html><head><title>foo</title><script>\n"
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
                + "  <input type='submit' name='first_submit' value='Commit it!'>\n"
                + "  <input type='submit' id='second_submit' value='Delete it!'>\n"
                + "  <input type='text' name='action' value='blah'>\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"string 0", "string 1", "string 2", "string 3", "string 4", "string 5",
                "string item", "string length", "string namedItem"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>tags.</p>
     */
    @Test
    public void tags() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    alert(document.all.tags != undefined);\n"
                + "    alert(document.forms.tags != undefined);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "</body></html>";

        final String[] messages = {"false", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>outOfBoundAccess.</p>
     */
    @Test
    public void outOfBoundAccess() {
        final String html = "<html><head><title>foo</title><script>\n"
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

        final String[] messages = {null, null, "undefined", "exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>inexistentProperties.</p>
     */
    @Test
    public void inexistentProperties() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var x = document.documentElement.childNodes;\n"
                + "    alert(x.split);\n"
                + "    alert(x.setInterval);\n"
                + "    alert(x.bogusNonExistentProperty);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>childNodes.</p>
     */
    @Test
    public void childNodes() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.body.childNodes.length);\n"
                + "    alert(document.body.firstChild.nodeName);\n"
                + "    alert(document.getElementById('myDiv').childNodes.length);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'> <div id='myDiv'> <div> </div> <div> </div> </div> </body>\n"
                + "</html>";

        final String[] messages = {"3", "#text", "5"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>typeof.</p>
     */
    @Test
    public void typeof() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(typeof document.getElementsByTagName('a'));\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        final String[] messages = {"object"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>getElementWithDollarSign.</p>
     */
    @Test
    public void getElementWithDollarSign() {
        final String html
                = "<h3 id='$h'>h</h3><script>\n"
                + "var hs = document.getElementsByTagName('h3');\n"
                + "alert(hs['$h']);\n"
                + "alert(hs['$n']);\n"
                + "</script>";
        final String[] messages = {"[object HTMLHeadingElement]", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>array_prototype.</p>
     */
    @Test
    public void array_prototype() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(typeof Object.prototype.__defineGetter__);\n"
                + "    alert(typeof Object.prototype.__lookupGetter__);\n"
                + "    alert(typeof Array.prototype.indexOf);\n"
                + "    alert(typeof Array.prototype.map);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        final String[] messages = {"function", "function", "function", "function"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>array_prototype_standards.</p>
     */
    @Test
    public void array_prototype_standards() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(typeof Object.prototype.__defineGetter__);\n"
                + "    alert(typeof Object.prototype.__lookupGetter__);\n"
                + "    alert(typeof Array.prototype.indexOf);\n"
                + "    alert(typeof Array.prototype.map);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        final String[] messages = {"function", "function", "function", "function"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>has.</p>
     */
    @Test
    public void has() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    alert(0 in document.forms);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>item_Unknown.</p>
     */
    @Test
    public void item_Unknown() {
        item("'foo'", "b1-button1");
    }

    /**
     * <p>item_ById.</p>
     */
    @Test
    public void item_ById() {
        item("'b2'", "b1-button1");
    }

    /**
     * <p>item_ByName.</p>
     */
    @Test
    public void item_ByName() {
        item("'button2'", "b1-button1");
    }

    /**
     * <p>item_NegativIndex.</p>
     */
    @Test
    public void item_NegativIndex() {
        item("-1", null);
    }

   /**
    * <p>item_ZeroIndex.</p>
    */
   @Test
   public void item_ZeroIndex() {
        item("0", "b1-button1");
    }

    /**
     * <p>item_ValidIndex.</p>
     */
    @Test
    public void item_ValidIndex() {
        item("1", "b2-button2");
    }

    /**
     * <p>item_DoubleIndex.</p>
     */
    @Test
            ()
    public void item_DoubleIndex() {
        item("1.1", "b2-button2");
    }

    /**
     * <p>item_InvalidIndex.</p>
     */
    @Test
    public void item_InvalidIndex() {
        item("2", null);
    }

    /**
     * <p>item_IndexAsString.</p>
     */
    @Test
    public void item_IndexAsString() {
        item("'1'", "b2-button2");
    }

    /**
     * <p>item_IndexDoubleAsString.</p>
     */
    @Test
    public void item_IndexDoubleAsString() {
        item("'1.1'", "b2-button2");
    }

    private void item(final String name, String message) {
        final String html = "<!doctype html>\n"+
                "<html><head><title>First</title><script>\n"
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

        final String[] messages = {message};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>arrayIndex_Unknown.</p>
     */
    @Test
    public void arrayIndex_Unknown() {
        arrayIndex("'foo'", "b2-button2");
    }

    /**
     * <p>arrayIndex_ById.</p>
     */
    @Test
    public void arrayIndex_ById() {
        arrayIndex("'b2'", "b2-button2");
    }

    /**
     * <p>arrayIndex_ByName.</p>
     */
    @Test
    public void arrayIndex_ByName() {
        arrayIndex("'button2'", "b2-button2");
    }

    /**
     * <p>arrayIndex_NegativIndex.</p>
     */
    @Test
    public void arrayIndex_NegativIndex() {
        arrayIndex("-1", "undefined");
    }

    /**
     * <p>arrayIndex_ZeroIndex.</p>
     */
    @Test
    public void arrayIndex_ZeroIndex() {
        arrayIndex("0", "b1-button1");
    }

    /**
     * <p>arrayIndex_ValidIndex.</p>
     */
    @Test
    public void arrayIndex_ValidIndex() {
        arrayIndex("1", "b2-button2");
    }

    /**
     * <p>arrayIndex_DoubleIndex.</p>
     */
    @Test
    public void arrayIndex_DoubleIndex() {
        arrayIndex("1.1",  "undefined");
    }

    /**
     * <p>arrayIndex_InvalidIndex.</p>
     */
    @Test
    public void arrayIndex_InvalidIndex() {
        arrayIndex("2", "undefined");
    }

    /**
     * <p>arrayIndex_IndexAsString.</p>
     */
    @Test
    public void arrayIndex_IndexAsString() {
        arrayIndex("'2'", "undefined");
    }

    private void arrayIndex(final String name, String message) {
        final String html = "<!doctype html>\n"+
                "<html><head><title>First</title><script>\n"
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

        final String[] messages = {message};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>functionIndex_Unknown.</p>
     */
    @Test
    public void functionIndex_Unknown() {
        functionIndex("'foo'","exception");
    }


    /**
     * <p>functionIndex_ById.</p>
     */
    @Test
    public void functionIndex_ById() {
        functionIndex("'b2'","exception");
    }

    /**
     * <p>functionIndex_ByName.</p>
     */
    @Test
    public void functionIndex_ByName() {
        functionIndex("'button2'","exception");
    }

    /**
     * <p>functionIndex_NegativIndex.</p>
     */
    @Test
    public void functionIndex_NegativIndex() {
        functionIndex("-1","exception");
    }

    /**
     * <p>functionIndex_ZeroIndex.</p>
     */
    @Test
    public void functionIndex_ZeroIndex() {
        functionIndex("0","exception");
    }

    /**
     * <p>functionIndex_ValidIndex.</p>
     */
    @Test
    public void functionIndex_ValidIndex() {
        functionIndex("1","exception");
    }

    /**
     * <p>functionIndex_DoubleIndex.</p>
     */
    @Test
    public void functionIndex_DoubleIndex() {
        functionIndex("1.1","exception");
    }

    /**
     * <p>functionIndex_InvalidIndex.</p>
     */
    @Test
    public void functionIndex_InvalidIndex() {
        functionIndex("2","exception");
    }

    /**
     * <p>functionIndex_IndexAsString.</p>
     */
    @Test
    public void functionIndex_IndexAsString() {
        functionIndex("'2'","exception");
    }

    private void functionIndex(final String name, String message) {
        final String html  = "<!doctype html>\n" +
                "<html><head><title>First</title><script>\n"
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

        final String[] messages = {message};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>namedItem_Unknown.</p>
     */
    @Test
    public void namedItem_Unknown() {
        namedItem("foo", null);
    }

    /**
     * <p>namedItem_ById.</p>
     */
    @Test
    public void namedItem_ById() {
        namedItem("button1", "button1-");
    }

    /**
     * <p>namedItem_ByName_formWithoutId.</p>
     */
    @Test
    public void namedItem_ByName_formWithoutId() {
        namedItem("button2", "-button2");
    }

    /**
     * <p>namedItem_ByName.</p>
     */
    @Test
    public void namedItem_ByName() {
        namedItem("button3", "b3-button3");
    }

    /**
     * <p>namedItem_DuplicateId.</p>
     */
    @Test
    public void namedItem_DuplicateId() {
        namedItem("b4", "b4-button4_1");
    }

    /**
     * <p>namedItem_DuplicateName.</p>
     */
    @Test
    public void namedItem_DuplicateName() {
        namedItem("button5", "b5_1-button5");
    }

    /**
     * <p>namedItem_DuplicateIdName.</p>
     */
    @Test
    public void namedItem_DuplicateIdName() {
        namedItem("button6", "b6-button6");
    }

    /**
     * <p>namedItem_ZeroIndex.</p>
     */
    @Test
    public void namedItem_ZeroIndex() {
        item("0", "b1-button1");
    }

    /**
     * <p>namedItem_ValidIndex.</p>
     */
    @Test
    public void namedItem_ValidIndex() {
        item("1", "b2-button2");
    }

    /**
     * <p>namedItem_DoubleIndex.</p>
     */
    @Test
    public void namedItem_DoubleIndex() {
        item("1.1", "b2-button2");
    }

    /**
     * <p>namedItem_InvalidIndex.</p>
     */
    @Test
    public void namedItem_InvalidIndex() {
        item("200", null);
    }

    /**
     * <p>namedItem_IndexAsString.</p>
     */
    @Test
    public void namedItem_IndexAsString() {
        item("'1'", "b2-button2");
    }

    /**
     * <p>namedItem_IndexDoubleAsString.</p>
     */
    @Test
    public void namedItem_IndexDoubleAsString() {
        item("'1.1'", "b2-button2");
    }

    private void namedItem(final String name, String message) {
        final String html = "<!doctype html>\n" +
                "<html><head><title>First</title><script>\n"
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

        final String[] messages = {message};
        checkHtmlAlert(html, messages);
    }
}
