/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import org.loboevolution.html.dom.HTMLAllCollection;

/**
 * Tests for {@link HTMLAllCollection}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLAllCollectionTest extends LoboUnitTest {

    @Test
    @Alerts("null")
    public void namedItemUnknown() {
        namedItem("'foo'");
    }

    @Test
    @Alerts("button1-")
    public void namedItemById() {
        namedItem("'button1'");
    }

    @Test
    @Alerts("-button2")
    public void namedItemByName_formWithoutId() {
        namedItem("'button2'");
    }

    @Test
    @Alerts("b3-button3")
    public void namedItemByName() {
        namedItem("'button3'");
    }

    @Test
    @Alerts({"coll 2", "b4-button4_1", "b4-button4_2"})
    public void namedItemDuplicateId() {
        namedItem("'b4'");
    }

    @Test
    @Alerts({"coll 2", "b5_1-button5", "b5_2-button5"})
    public void namedItemDuplicateName() {
        namedItem("'button5'");
    }

    @Test
    @Alerts({"coll 2", "b6-button6", "button6-button6_2"})
    public void namedItemDuplicateIdName() {
        namedItem("'button6'");
    }

    @Test
    @Alerts("null")
    public void namedItemZeroIndex() {
        namedItem("0");
    }

    @Test
    @Alerts("null")
    public void namedItemValidIndex() {
        namedItem("1");
    }

    @Test
    @Alerts("null")
    public void namedItem_DoubleIndex() {
        namedItem("1.1");
    }

    @Test
    @Alerts("null")
    public void namedItemInvalidIndex() {
        namedItem("200");
    }

    @Test
    @Alerts("null")
    public void namedItemIndexAsString() {
        namedItem("'1'");
    }

    @Test
    @Alerts("null")
    public void namedItemIndexDoubleAsString() {
        namedItem("'1.1'");
    }

    private void namedItem(final String name) {
        final String html
                = "<!doctype html>\n"
                + "<html id='myHtml'><head id='myHead'><title id='myTitle'>First</title><script>\n"
                + "  alerts = ''\n"
                + "  function alert(msg) { alerts += msg + '§';}\n"
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
                + "      var item = document.all.namedItem(" + name + ");\n"
                + "      report(item);\n"
                + "    } catch(e) { alert(e); }\n"
                + "    document.title = alerts;"
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
    @Alerts("null")
    public void itemUnknown() {
        item("'foo'");
    }

    @Test
    @Alerts("b2-button2")
    public void itemById() {
        item("'b2'");
    }

    @Test
    @Alerts("b2-button2")
    public void itemByName() {
        item("'button2'");
    }

    @Test
    @Alerts("null")
    public void item_NegativIndex() {
        item("-1");
    }

    @Test
    @Alerts("myHtml-undefined")
    public void item_ZeroIndex() {
        item("0");
    }

    @Test
    @Alerts("myHead-undefined")
    public void item_ValidIndex() {
        item("1");
    }

    @Test
    @Alerts("null")
    public void itemDoubleIndex() {
        item("1.1");
    }

    @Test
    @Alerts("null")
    public void itemInvalidIndex() {
        item("200");
    }

    @Test
    @Alerts("myHead-undefined")
    public void itemIndexAsString() {
        item("'1'");
    }

    @Test
    @Alerts("null")
    public void itemIndexDoubleAsString() {
        item("'1.1'");
    }

    private void item(final String name) {
        final String html
                = "<!doctype html>\n"
                + "<html id='myHtml'><head id='myHead'><title id='myTitle'>First</title><script>\n"
                + "  alerts = ''\n"
                + "  function alert(msg) { alerts += msg + '§';}\n"
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
                + "      var item = document.all.item(" + name + ");\n"
                + "      report(item);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "    document.title = alerts;"
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
    @Alerts("myHtml-undefined")
    public void arrayIndexZeroIndex() {
        arrayIndex("0");
    }

    @Test
    @Alerts("myHead-undefined")
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
        arrayIndex("200");
    }

    @Test
    @Alerts("myTitle-undefined")
    public void arrayIndexIndexAsString() {
        arrayIndex("'2'");
    }

    private void arrayIndex(final String name) {
        final String html
                = "<!doctype html>\n"
                + "<html id='myHtml'><head id='myHead'><title id='myTitle'>First</title><script>\n"
                + "  alerts = ''\n"
                + "  function alert(msg) { alerts += msg + '§';}\n"
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
                + "      var item = document.all[" + name + "];\n"
                + "      report(item);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "    document.title = alerts;"
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
    public void functionIndexUnknown() {
        functionIndex("'foo'");
    }

    @Test
    @Alerts("b2-button2")
    public void functionIndexById() {
        functionIndex("'b2'");
    }

    @Test
    @Alerts("b2-button2")
    public void functionIndexByName() {
        functionIndex("'button2'");
    }

    @Test
    @Alerts("null")
    public void functionIndexNegativIndex() {
        functionIndex("-1");
    }

    @Test
    @Alerts("myHtml-undefined")
    public void functionIndexZeroIndex() {
        functionIndex("0");
    }

    @Test
    @Alerts("myHead-undefined")
    public void functionIndexValidIndex() {
        functionIndex("1");
    }

    @Test
    @Alerts("null")
    public void functionIndexDoubleIndex() {
        functionIndex("1.1");
    }

    @Test
    @Alerts("null")
    public void functionIndexInvalidIndex() {
        functionIndex("200");
    }

    @Test
    @Alerts("myTitle-undefined")
    public void functionIndexIndexAsString() {
        functionIndex("'2'");
    }

    private void functionIndex(final String name) {
        final String html
                = "<!doctype html>\n"
                + "<html id='myHtml'><head id='myHead'><title id='myTitle'>First</title><script>\n"
                + "  alerts = ''\n"
                + "  function alert(msg) { alerts += msg + '§';}\n"
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
                + "      report(col.namedItem(" + name + "));\n"
                + "    } catch(e) { alert(exception); }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <button id='b1' name='button1'></button>\n"
                + "  <button id='b2' name='button2'></button>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLAllCollection]", "function HTMLAllCollection() { [native code] }"})
    public void type() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.all);\n"
                + "      alert(HTMLAllCollection);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("function () { [native code] }")
    public void proto() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(HTMLAllCollection.__proto__);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }
}
