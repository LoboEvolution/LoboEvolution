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
     * <p>forin.</p>
     */
    @Test
    public void forin() {
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
     * <p>forin2.</p>
     */
    @Test
    public void forin2() {
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
                + "  <input type='text' id='id1' name='val1' id='inputenabled' value='4'>\n"
                + "  <div>This is not a form element</div>\n"
                + "  <input type='text' name='val2' id='inputdisabled' disabled='disabled' value='5'>\n"
                + "  <input type='submit' name='firstsubmit' value='Commit it!'>\n"
                + "  <input type='submit' id='secondsubmit' value='Delete it!'>\n"
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
     * <p>arrayprototype.</p>
     */
    @Test
    public void arrayprototype() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(typeof Object.prototype.defineGetter);\n"
                + "    alert(typeof Object.prototype.lookupGetter);\n"
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
     * <p>arrayprototypestandards.</p>
     */
    @Test
    public void arrayprototypestandards() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(typeof Object.prototype.defineGetter);\n"
                + "    alert(typeof Object.prototype.lookupGetter);\n"
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
     * <p>itemUnknown.</p>
     */
    @Test
    public void itemUnknown() {
        item("'foo'", "b1-button1");
    }

    /**
     * <p>itemById.</p>
     */
    @Test
    public void itemById() {
        item("'b2'", "b1-button1");
    }

    /**
     * <p>itemByName.</p>
     */
    @Test
    public void itemByName() {
        item("'button2'", "b1-button1");
    }

    /**
     * <p>itemNegativIndex.</p>
     */
    @Test
    public void itemNegativIndex() {
        item("-1", null);
    }

   /**
    * <p>itemZeroIndex.</p>
    */
   @Test
   public void itemZeroIndex() {
        item("0", "b1-button1");
    }

    /**
     * <p>itemValidIndex.</p>
     */
    @Test
    public void itemValidIndex() {
        item("1", "b2-button2");
    }

    /**
     * <p>itemDoubleIndex.</p>
     */
    @Test
            ()
    public void itemDoubleIndex() {
        item("1.1", "b2-button2");
    }

    /**
     * <p>itemInvalidIndex.</p>
     */
    @Test
    public void itemInvalidIndex() {
        item("2", null);
    }

    /**
     * <p>itemIndexAsString.</p>
     */
    @Test
    public void itemIndexAsString() {
        item("'1'", "b2-button2");
    }

    /**
     * <p>itemIndexDoubleAsString.</p>
     */
    @Test
    public void itemIndexDoubleAsString() {
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
     * <p>arrayIndexUnknown.</p>
     */
    @Test
    public void arrayIndexUnknown() {
        arrayIndex("'foo'", "b2-button2");
    }

    /**
     * <p>arrayIndexById.</p>
     */
    @Test
    public void arrayIndexById() {
        arrayIndex("'b2'", "b2-button2");
    }

    /**
     * <p>arrayIndexByName.</p>
     */
    @Test
    public void arrayIndexByName() {
        arrayIndex("'button2'", "b2-button2");
    }

    /**
     * <p>arrayIndexNegativIndex.</p>
     */
    @Test
    public void arrayIndexNegativIndex() {
        arrayIndex("-1", "undefined");
    }

    /**
     * <p>arrayIndexZeroIndex.</p>
     */
    @Test
    public void arrayIndexZeroIndex() {
        arrayIndex("0", "b1-button1");
    }

    /**
     * <p>arrayIndexValidIndex.</p>
     */
    @Test
    public void arrayIndexValidIndex() {
        arrayIndex("1", "b2-button2");
    }

    /**
     * <p>arrayIndexDoubleIndex.</p>
     */
    @Test
    public void arrayIndexDoubleIndex() {
        arrayIndex("1.1",  "undefined");
    }

    /**
     * <p>arrayIndexInvalidIndex.</p>
     */
    @Test
    public void arrayIndexInvalidIndex() {
        arrayIndex("2", "undefined");
    }

    /**
     * <p>arrayIndexIndexAsString.</p>
     */
    @Test
    public void arrayIndexIndexAsString() {
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
     * <p>functionIndexUnknown.</p>
     */
    @Test
    public void functionIndexUnknown() {
        functionIndex("'foo'","exception");
    }


    /**
     * <p>functionIndexById.</p>
     */
    @Test
    public void functionIndexById() {
        functionIndex("'b2'","exception");
    }

    /**
     * <p>functionIndexByName.</p>
     */
    @Test
    public void functionIndexByName() {
        functionIndex("'button2'","exception");
    }

    /**
     * <p>functionIndexNegativIndex.</p>
     */
    @Test
    public void functionIndexNegativIndex() {
        functionIndex("-1","exception");
    }

    /**
     * <p>functionIndexZeroIndex.</p>
     */
    @Test
    public void functionIndexZeroIndex() {
        functionIndex("0","exception");
    }

    /**
     * <p>functionIndexValidIndex.</p>
     */
    @Test
    public void functionIndexValidIndex() {
        functionIndex("1","exception");
    }

    /**
     * <p>functionIndexDoubleIndex.</p>
     */
    @Test
    public void functionIndexDoubleIndex() {
        functionIndex("1.1","exception");
    }

    /**
     * <p>functionIndexInvalidIndex.</p>
     */
    @Test
    public void functionIndexInvalidIndex() {
        functionIndex("2","exception");
    }

    /**
     * <p>functionIndexIndexAsString.</p>
     */
    @Test
    public void functionIndexIndexAsString() {
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
     * <p>namedItemUnknown.</p>
     */
    @Test
    public void namedItemUnknown() {
        namedItem("foo", null);
    }

    /**
     * <p>namedItemById.</p>
     */
    @Test
    public void namedItemById() {
        namedItem("button1", "button1-");
    }

    /**
     * <p>namedItemByNameformWithoutId.</p>
     */
    @Test
    public void namedItemByNameformWithoutId() {
        namedItem("button2", "-button2");
    }

    /**
     * <p>namedItemByName.</p>
     */
    @Test
    public void namedItemByName() {
        namedItem("button3", "b3-button3");
    }

    /**
     * <p>namedItemDuplicateId.</p>
     */
    @Test
    public void namedItemDuplicateId() {
        namedItem("b4", "b4-button41");
    }

    /**
     * <p>namedItemDuplicateName.</p>
     */
    @Test
    public void namedItemDuplicateName() {
        namedItem("button5", "b51-button5");
    }

    /**
     * <p>namedItemDuplicateIdName.</p>
     */
    @Test
    public void namedItemDuplicateIdName() {
        namedItem("button6", "b6-button6");
    }

    /**
     * <p>namedItemZeroIndex.</p>
     */
    @Test
    public void namedItemZeroIndex() {
        item("0", "b1-button1");
    }

    /**
     * <p>namedItemValidIndex.</p>
     */
    @Test
    public void namedItemValidIndex() {
        item("1", "b2-button2");
    }

    /**
     * <p>namedItemDoubleIndex.</p>
     */
    @Test
    public void namedItemDoubleIndex() {
        item("1.1", "b2-button2");
    }

    /**
     * <p>namedItemInvalidIndex.</p>
     */
    @Test
    public void namedItemInvalidIndex() {
        item("200", null);
    }

    /**
     * <p>namedItemIndexAsString.</p>
     */
    @Test
    public void namedItemIndexAsString() {
        item("'1'", "b2-button2");
    }

    /**
     * <p>namedItemIndexDoubleAsString.</p>
     */
    @Test
    public void namedItemIndexDoubleAsString() {
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
                + "  <button id='b4' name='button41'></button>\n"
                + "  <button id='b4' name='button42'></button>\n"
                + "  <button id='b51' name='button5'></button>\n"
                + "  <button id='b52' name='button5'></button>\n"
                + "  <button id='b6' name='button6'></button>\n"
                + "  <button id='button6' name='button62'></button>\n"
                + "</body></html>";

        final String[] messages = {message};
        checkHtmlAlert(html, messages);
    }
}
