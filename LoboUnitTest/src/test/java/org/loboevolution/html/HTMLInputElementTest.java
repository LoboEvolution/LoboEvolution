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
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link HTMLInputElement} and buttons.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLInputElementTest extends LoboUnitTest {

    @Test
    @Alerts({"foo", "text", "textfield1", "form1", "cat"})
    public void standardProperties_Text() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  alert(document.form1.textfield1.value);\n"
                + "  alert(document.form1.textfield1.type);\n"
                + "  alert(document.form1.textfield1.name);\n"
                + "  alert(document.form1.textfield1.form.name);\n"
                + "  document.form1.textfield1.value = 'cat';\n"
                + "  alert(document.form1.textfield1.value);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='text' name='textfield1' value='foo' />\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Alerts({"error.fileupload1", "abc", "abc", "abc", "", "abc", "", "", "abc", "abc",
            "abc", "abc", "abc", "abc", "abc", "abc", "#000000", "", "abc", "", "", "",
            "", "", "50", "abc", "abc", "abc", "abc"})
    @Test
    public void setValueString() {
        testValue("'abc'");
    }


    @Alerts({"", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "#000000", "", "", "", "", "",
            "", "", "50", "", "", "", ""})
    @Test
    public void setValueEmptyString() {
        testValue("''");
    }


    @Alerts({"error.fileupload1", ". ", ". ", ". ", "", ". ", "", "", ". ", ". ",
            ". ", ". ", ". ", ". ", ". ", ". ", "#000000", "", ". ", "", "", "",
            "", "", "50", ". ", "", ". ", ""})
    @Test
    public void setValueBlankString() {
        testValue("'  '");
    }


    @Alerts({"error.fileupload1", "12", "12", "12", "", "12", "", "", "12", "12",
            "12", "12", "12", "12", "12", "12", "#000000", "", "12", "", "", "",
            "", "12", "12", "12", "12", "12", "12"})
    @Test
    public void setValueNumber() {
        testValue("12");
    }


    @Alerts({"", "null", "", "", "", "", "", "", "",
            "", "null", "", "null", "", "", "#000000", "", "", "", "", "",
            "", "", "50", "", "", "", ""})
    @Test
    public void setValueNull() {
        testValue("null");
    }

    private void testValue(final String value) {
        final String html = "<html><head><script>\n"
                + "function alert(msg) { window.document.title += msg.replace(' ', '.') + '§';}\n"
                + "function doTest() {\n"

                + "  document.form1.button1.value = " + value + ";\n"
                + "  document.form1.button2.value = " + value + ";\n"
                + "  document.form1.checkbox1.value = " + value + ";\n"
                + "  try { document.form1.fileupload1.value = " + value + " } catch(e) { alert('error fileupload1') }\n"
                + "  document.form1.hidden1.value = " + value + ";\n"
                + "  document.form1.select1.value = " + value + ";\n"
                + "  document.form1.select2.value = " + value + ";\n"
                + "  document.form1.password1.value = " + value + ";\n"
                + "  document.form1.radio1.value = " + value + ";\n"
                + "  document.form1.reset1.value = " + value + ";\n"
                + "  document.form1.reset2.value = " + value + ";\n"
                + "  document.form1.submit1.value = " + value + ";\n"
                + "  document.form1.submit2.value = " + value + ";\n"
                + "  document.form1.textInput1.value = " + value + ";\n"
                + "  document.form1.textarea1.value = " + value + ";\n"
                + "  document.form1.color1.value = " + value + ";\n"
                + "  document.form1.date1.value = " + value + ";\n"
                + "  document.form1.datetime1.value = " + value + ";\n"
                + "  document.form1.datetimeLocal1.value = " + value + ";\n"
                + "  document.form1.time1.value = " + value + ";\n"
                + "  document.form1.week1.value = " + value + ";\n"
                + "  document.form1.month1.value = " + value + ";\n"
                + "  document.form1.number1.value = " + value + ";\n"
                + "  document.form1.range1.value = " + value + ";\n"
                + "  document.form1.search1.value = " + value + ";\n"
                + "  document.form1.email1.value = " + value + ";\n"
                + "  document.form1.tel1.value = " + value + ";\n"
                + "  document.form1.url1.value = " + value + ";\n"

                + "  alert(document.form1.button1.value);\n"
                + "  alert(document.form1.button2.value);\n"
                + "  alert(document.form1.checkbox1.value);\n"
                + "  alert(document.form1.fileupload1.value);\n"
                + "  alert(document.form1.hidden1.value);\n"
                + "  alert(document.form1.select1.value);\n"
                + "  alert(document.form1.select2.value);\n"
                + "  alert(document.form1.password1.value);\n"
                + "  alert(document.form1.radio1.value);\n"
                + "  alert(document.form1.reset1.value);\n"
                + "  alert(document.form1.reset2.value);\n"
                + "  alert(document.form1.submit1.value);\n"
                + "  alert(document.form1.submit2.value);\n"
                + "  alert(document.form1.textInput1.value);\n"
                + "  alert(document.form1.textarea1.value);\n"
                + "  alert(document.form1.color1.value);\n"
                + "  alert(document.form1.date1.value);\n"
                + "  alert(document.form1.datetime1.value);\n"
                + "  alert(document.form1.datetimeLocal1.value);\n"
                + "  alert(document.form1.time1.value);\n"
                + "  alert(document.form1.week1.value);\n"
                + "  alert(document.form1.month1.value);\n"
                + "  alert(document.form1.number1.value);\n"
                + "  alert(document.form1.range1.value);\n"
                + "  alert(document.form1.search1.value);\n"
                + "  alert(document.form1.email1.value);\n"
                + "  alert(document.form1.tel1.value);\n"
                + "  alert(document.form1.url1.value);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='button' name='button1'></button>\n"
                + "  <button type='button' name='button2'></button>\n"
                + "  <input type='checkbox' name='checkbox1'/>\n"
                + "  <input type='file' name='fileupload1'/>\n"
                + "  <input type='hidden' name='hidden1'/>\n"
                + "  <select name='select1'>\n"
                + "    <option>foo</option>\n"
                + "  </select>\n"
                + "  <select multiple='multiple' name='select2'>\n"
                + "    <option>boo</option>\n"
                + "  </select>\n"
                + "  <input type='password' name='password1'/>\n"
                + "  <input type='radio' name='radio1'/>\n"
                + "  <input type='reset' name='reset1'/>\n"
                + "  <button type='reset' name='reset2'></button>\n"
                + "  <input type='submit' name='submit1'/>\n"
                + "  <button type='submit' name='submit2'></button>\n"
                + "  <input type='text' name='textInput1'/>\n"
                + "  <textarea name='textarea1'>foo</textarea>\n"
                + "  <input type='color' name='color1'/>\n"
                + "  <input type='date' name='date1'/>\n"
                + "  <input type='datetime' name='datetime1'/>\n"
                + "  <input type='datetime-local' name='datetimeLocal1'/>\n"
                + "  <input type='time' name='time1'/>\n"
                + "  <input type='week' name='week1'/>\n"
                + "  <input type='month' name='month1'/>\n"
                + "  <input type='number' name='number1'/>\n"
                + "  <input type='range' name='range1'/>\n"
                + "  <input type='search' name='search1'/>\n"
                + "  <input type='email' name='email1'/>\n"
                + "  <input type='tel' name='tel1'/>\n"
                + "  <input type='url' name='url1'/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"button", "button", "checkbox", "file", "hidden", "select-one", "select-multiple",
            "password", "radio", "reset", "reset",
            "submit", "submit", "text", "textarea", "color", "date", "text",
            "datetime-local", "time", "week", "month", "number",
            "range", "search", "email", "tel", "url"})
    public void type() {
        testAttribute("type", "", null);
    }


    @Alerts({"null", "undefined", "null", "[object FileList]", "null", "undefined", "undefined", "null",
            "null", "null", "undefined", "null", "undefined", "null", "undefined", "null", "null", "null",
            "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"})
    @Test
    public void files() {
        testAttribute("files", "", null);
    }


    @Alerts({"false", "undefined", "false", "false", "false", "undefined", "undefined",
            "false", "false", "false", "undefined", "false", "undefined", "false",
            "undefined", "false", "false", "false", "false", "false", "false",
            "false", "false", "false", "false", "false", "false", "false"})
    @Test
    public void checked() {
        testAttribute("checked", "", null);
    }


    @Alerts({"true", "undefined", "true", "true", "true", "undefined", "undefined",
            "true", "true", "true", "undefined", "true", "undefined", "true",
            "undefined", "true", "true", "true", "true", "true", "true",
            "true", "true", "true", "true", "true", "true", "true"})
    @Test
    public void checkedWithAttribute() {
        testAttribute("checked", "checked", null);
    }


    @Alerts({"true", "undefined", "true", "true", "true", "undefined", "undefined",
            "true", "true", "true", "undefined", "true", "undefined", "true",
            "undefined", "true", "true", "true", "true", "true", "true",
            "true", "true", "true", "true", "true", "true", "true"})
    @Test
    public void setCheckedTrue() {
        testAttribute("checked", "", "true");
    }


    @Alerts({"true", "undefined", "true", "true", "true", "undefined", "undefined",
            "true", "true", "true", "undefined", "true", "undefined", "true",
            "undefined", "true", "true", "true", "true", "true", "true",
            "true", "true", "true", "true", "true", "true", "true"})
    @Test
    public void setCheckedBlank() {
        testAttribute("checked", "", "");
    }

    private void testAttribute(final String property, final String attrib, final String value) {
        String html
                = "<html><head><script>\n"

                + "function doTest() {\n";

        if (value != null) {
            html = html
                    + "  document.form1.button1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.button2.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.checkbox1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.fileupload1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.hidden1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.select1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.select2.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.password1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.radio1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.reset1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.reset2.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.submit1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.submit2.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.textInput1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.textarea1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.color1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.date1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.datetime1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.datetimeLocal1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.time1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.week1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.month1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.number1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.range1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.search1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.email1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.tel1.setAttribute('" + property + "', '" + value + "');\n"
                    + "  document.form1.url1.setAttribute('" + property + "', '" + value + "');\n";
        }

        html = html
                + "  alert(document.form1.button1." + property + ");\n"
                + "  alert(document.form1.button2." + property + ");\n"
                + "  alert(document.form1.checkbox1." + property + ");\n"
                + "  alert(document.form1.fileupload1." + property + ");\n"
                + "  alert(document.form1.hidden1." + property + ");\n"
                + "  alert(document.form1.select1." + property + ");\n"
                + "  alert(document.form1.select2." + property + ");\n"
                + "  alert(document.form1.password1." + property + ");\n"
                + "  alert(document.form1.radio1." + property + ");\n"
                + "  alert(document.form1.reset1." + property + ");\n"
                + "  alert(document.form1.reset2." + property + ");\n"
                + "  alert(document.form1.submit1." + property + ");\n"
                + "  alert(document.form1.submit2." + property + ");\n"
                + "  alert(document.form1.textInput1." + property + ");\n"
                + "  alert(document.form1.textarea1." + property + ");\n"
                + "  alert(document.form1.color1." + property + ");\n"
                + "  alert(document.form1.date1." + property + ");\n"
                + "  alert(document.form1.datetime1." + property + ");\n"
                + "  alert(document.form1.datetimeLocal1." + property + ");\n"
                + "  alert(document.form1.time1." + property + ");\n"
                + "  alert(document.form1.week1." + property + ");\n"
                + "  alert(document.form1.month1." + property + ");\n"
                + "  alert(document.form1.number1." + property + ");\n"
                + "  alert(document.form1.range1." + property + ");\n"
                + "  alert(document.form1.search1." + property + ");\n"
                + "  alert(document.form1.email1." + property + ");\n"
                + "  alert(document.form1.tel1." + property + ");\n"
                + "  alert(document.form1.url1." + property + ");\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='button' name='button1' " + attrib + "></button>\n"
                + "  <button type='button' name='button2' " + attrib + "></button>\n"
                + "  <input type='checkbox' name='checkbox1' " + attrib + "/>\n"
                + "  <input type='file' name='fileupload1' " + attrib + "/>\n"
                + "  <input type='hidden' name='hidden1' " + attrib + "/>\n"
                + "  <select name='select1' " + attrib + ">\n"
                + "    <option>foo</option>\n"
                + "  </select>\n"
                + "  <select multiple='multiple' name='select2' " + attrib + ">\n"
                + "    <option>foo</option>\n"
                + "  </select>\n"
                + "  <input type='password' name='password1' " + attrib + "/>\n"
                + "  <input type='radio' name='radio1' " + attrib + "/>\n"
                + "  <input type='reset' name='reset1' " + attrib + "/>\n"
                + "  <button type='reset' name='reset2' " + attrib + "></button>\n"
                + "  <input type='submit' name='submit1' " + attrib + "/>\n"
                + "  <button type='submit' name='submit2' " + attrib + "></button>\n"
                + "  <input type='text' name='textInput1' " + attrib + "/>\n"
                + "  <textarea name='textarea1' " + attrib + ">foo</textarea>\n"
                + "  <input type='color' name='color1' " + attrib + "/>\n"
                + "  <input type='date' name='date1' " + attrib + "/>\n"
                + "  <input type='datetime' name='datetime1' " + attrib + "/>\n"
                + "  <input type='datetime-local' name='datetimeLocal1' " + attrib + "/>\n"
                + "  <input type='time' name='time1' " + attrib + "/>\n"
                + "  <input type='week' name='week1' " + attrib + "/>\n"
                + "  <input type='month' name='month1' " + attrib + "/>\n"
                + "  <input type='number' name='number1' " + attrib + "/>\n"
                + "  <input type='range' name='range1' " + attrib + "/>\n"
                + "  <input type='search' name='search1' " + attrib + "/>\n"
                + "  <input type='email' name='email1' " + attrib + "/>\n"
                + "  <input type='tel' name='tel1' " + attrib + "/>\n"
                + "  <input type='url' name='url1' " + attrib + "/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeInputButton() {
        testAttribute("button1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeButton() {
        testAttribute("button2", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeCheckbox() {
        testAttribute("checkbox1", "value", "", "abc");
    }


    @Alerts("")
    @Test
    public void setValueAttributeFile() {
        testAttribute("fileupload1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeHidden() {
        testAttribute("hidden1", "value", "", "abc");
    }


    @Alerts("foo")
    @Test
    public void setValueAttributeSelect() {
        testAttribute("select1", "value", "", "abc");
    }


    @Alerts("")
    @Test
    public void setValueAttributeSelectMultiple() {
        testAttribute("select2", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributePassword() {
        testAttribute("password1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeRadio() {
        testAttribute("radio1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeReset() {
        testAttribute("reset1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeResetButton() {
        testAttribute("reset2", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeSubmit() {
        testAttribute("submit1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeSubmitButton() {
        testAttribute("submit2", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeText() {
        testAttribute("textInput1", "value", "", "abc");
    }


    @Alerts("foo")
    @Test
    public void setValueAttributeTextArea() {
        testAttribute("textarea1", "value", "", "abc");
    }


    @Alerts("#000000")
    @Test
    public void setValueAttributeColor() {
        testAttribute("color1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeDate() {
        testAttribute("date1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeDateTime() {
        testAttribute("datetime1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeDateTimeLocal() {
        testAttribute("datetimeLocal1", "value", "", "abc");
    }


    @Alerts("")
    @Test
    public void setValueAttributeTime() {
        testAttribute("time1", "value", "", "abc");
    }


    @Alerts("")
    @Test
    public void setValueAttributeWeek() {
        testAttribute("week1", "value", "", "abc");
    }


    @Alerts("")
    @Test
    public void setValueAttributeMonth() {
        testAttribute("month1", "value", "", "abc");
    }


    @Alerts("")
    @Test
    public void setValueAttributeNumber() {
        testAttribute("number1", "value", "", "abc");
    }


    @Alerts("50")
    @Test
    public void setValueAttributeRange() {
        testAttribute("range1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeSearch() {
        testAttribute("search1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeEmail() {
        testAttribute("email1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeTel() {
        testAttribute("tel1", "value", "", "abc");
    }


    @Alerts("abc")
    @Test
    public void setValueAttributeUrl() {
        testAttribute("url1", "value", "", "abc");
    }

    private void testAttribute(final String itemId, final String property,
                               final String attrib, final String value) {
        String html
                = "<html><head><script>\n"

                + "function doTest() {\n";

        if (value != null) {
            html = html
                    + "  document.form1." + itemId + ".setAttribute('" + property + "', '" + value + "');\n";
        }

        html = html
                + "  alert(document.form1." + itemId + "." + property + ");\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='button' name='button1' " + attrib + "></button>\n"
                + "  <button type='button' name='button2' " + attrib + "></button>\n"
                + "  <input type='checkbox' name='checkbox1' " + attrib + "/>\n"
                + "  <input type='file' name='fileupload1' " + attrib + "/>\n"
                + "  <input type='hidden' name='hidden1' " + attrib + "/>\n"
                + "  <select name='select1' " + attrib + ">\n"
                + "    <option>foo</option>\n"
                + "  </select>\n"
                + "  <select multiple='multiple' name='select2' " + attrib + ">\n"
                + "    <option>foo</option>\n"
                + "  </select>\n"
                + "  <input type='password' name='password1' " + attrib + "/>\n"
                + "  <input type='radio' name='radio1' " + attrib + "/>\n"
                + "  <input type='reset' name='reset1' " + attrib + "/>\n"
                + "  <button type='reset' name='reset2' " + attrib + "></button>\n"
                + "  <input type='submit' name='submit1' " + attrib + "/>\n"
                + "  <button type='submit' name='submit2' " + attrib + "></button>\n"
                + "  <input type='text' name='textInput1' " + attrib + "/>\n"
                + "  <textarea name='textarea1' " + attrib + ">foo</textarea>\n"
                + "  <input type='color' name='color1' " + attrib + "/>\n"
                + "  <input type='date' name='date1' " + attrib + "/>\n"
                + "  <input type='datetime' name='datetime1' " + attrib + "/>\n"
                + "  <input type='datetime-local' name='datetimeLocal1' " + attrib + "/>\n"
                + "  <input type='time' name='time1' " + attrib + "/>\n"
                + "  <input type='week' name='week1' " + attrib + "/>\n"
                + "  <input type='month' name='month1' " + attrib + "/>\n"
                + "  <input type='number' name='number1' " + attrib + "/>\n"
                + "  <input type='range' name='range1' " + attrib + "/>\n"
                + "  <input type='search' name='search1' " + attrib + "/>\n"
                + "  <input type='email' name='email1' " + attrib + "/>\n"
                + "  <input type='tel' name='tel1' " + attrib + "/>\n"
                + "  <input type='url' name='url1' " + attrib + "/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Alerts({"abc-abc", "abc-abc", "jkl-abc"})
    @Test
    public void typeChangeNotDirty() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  var inpt = document.form1.textInput1;\n"
                + "  alert(inpt.value + '-' + inpt.defaultValue);\n"

                + "  inpt.type = 'password';\n"
                + "  alert(inpt.value + '-' + inpt.defaultValue);\n"

                + "  inpt.value = 'jkl';\n"
                + "  alert(inpt.value + '-' + inpt.defaultValue);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='text' name='textInput1' value='abc'/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Alerts({"abc-abc", "def-abc", "ghi-abc", "ghi-abc", "jkl-abc"})
    @Test
    public void typeChangeDirty() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  var inpt = document.form1.textInput1;\n"
                + "  alert(inpt.value + '-' + inpt.defaultValue);\n"

                + "  inpt.value = 'def';\n"
                + "  alert(inpt.value + '-' + inpt.defaultValue);\n"

                + "  inpt.value = 'ghi';\n"
                + "  alert(inpt.value + '-' + inpt.defaultValue);\n"

                + "  inpt.type = 'password';\n"
                + "  alert(inpt.value + '-' + inpt.defaultValue);\n"

                + "  inpt.value = 'jkl';\n"
                + "  alert(inpt.value + '-' + inpt.defaultValue);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='text' name='textInput1' value='abc'/>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true"})
    public void checkedAttribute_Checkbox() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  alert(document.form1.checkbox1.checked);\n"
                + "  document.form1.checkbox1.checked = true;\n"
                + "  alert(document.form1.checkbox1.checked);\n"
                + "}\n"
                + "</script></head><body>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='cheCKbox' name='checkbox1' id='checkbox1' value='foo' />\n"
                + "</form>\n"
                + "<a href='javascript:test()' id='clickme'>click me</a>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("checkbox1");
        elem.getOnclick();

        elem = (HTMLElementImpl) document.getElementById("clickme");
        elem.getOnclick();
    }

    @Test
    @Alerts({"true", "false", "false", "false", "true", "false"})
    public void checkedAttribute_Radio() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  alert(document.form1.radio1[0].checked);\n"
                + "  alert(document.form1.radio1[1].checked);\n"
                + "  alert(document.form1.radio1[2].checked);\n"
                + "  document.form1.radio1[1].checked = true;\n"
                + "  alert(document.form1.radio1[0].checked);\n"
                + "  alert(document.form1.radio1[1].checked);\n"
                + "  alert(document.form1.radio1[2].checked);\n"
                + "}\n"
                + "</script></head><body>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='radio' name='radio1' id='radioA' value='a' checked='checked'/>\n"
                + "  <input type='RADIO' name='radio1' id='radioB' value='b' />\n"
                + "  <input type='radio' name='radio1' id='radioC' value='c' />\n"
                + "</form>\n"
                + "<a href='javascript:test()' id='clickme'>click me</a>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickme");
        elem.getOnclick();
    }

    @Test
    @Alerts({"false", "true", "false", "true", "false", "true"})
    public void disabledAttribute() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  alert(document.form1.button1.disabled);\n"
                + "  alert(document.form1.button2.disabled);\n"
                + "  alert(document.form1.button3.disabled);\n"
                + "  document.form1.button1.disabled = true;\n"
                + "  document.form1.button2.disabled = false;\n"
                + "  document.form1.button3.disabled = true;\n"
                + "  alert(document.form1.button1.disabled);\n"
                + "  alert(document.form1.button2.disabled);\n"
                + "  alert(document.form1.button3.disabled);\n"
                + "}\n"
                + "</script></head><body>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1'>\n"
                + "  <input type='submit' name='button1' value='1'/>\n"
                + "  <input type='submit' name='button2' value='2' disabled/>\n"
                + "  <input type='submit' name='button3' value='3'/>\n"
                + "</form>\n"
                + "<a href='javascript:test()' id='clickme'>click me</a>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickme");
        elem.getOnclick();
    }

    @Test
    @Alerts({"blue"})
    public void inputValue() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  document.form1.textfield1.value = 'blue';\n"
                + " alert(document.form1.textfield1.value);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1' onsubmit='doTest()'>\n"
                + "  <input type='text' name='textfield1' id='textfield1' value='foo' />\n"
                + "  <input type='submit' id='clickMe'/>\n"
                + "</form>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();
    }

    @Test
    @Alerts({"true"})
    public void inputSelect_NotDefinedAsPropertyAndFunction() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.form1.textfield1.select()9;\n"
                + "}\n"
                + "</script></head>\n"
                + "<body>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1' onsubmit='doTest()'>\n"
                + "  <input type='text' name='textfield1' id='textfield1' value='foo' />\n"
                + "  <input type='submit' id='clickMe'/>\n"
                + "</form>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();
    }

    @Test
    @Alerts("true")
    public void thisDotFormInOnClick() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "<form name='form1'>\n"
                + "<input type='submit' id='clickMe' onClick=\"this.form.target='_blank'; return false;\">\n"
                + "</form>\n"
                + "    <script>\n" + "alert(document.forms[0].target == '');\n"
                + "</script>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();
    }

    @Test
    @Alerts({"true", "true", "true", "true", "true"})
    public void fieldDotForm() {
        final String html
                = "<html>\n"
                + "<head><script>\n"
                + "function test() {\n"
                + "  var f = document.form1;\n"
                + "  alert(f == f.mySubmit.form);\n"
                + "  alert(f == f.myText.form);\n"
                + "  alert(f == f.myPassword.form);\n"
                + "  alert(f == document.getElementById('myImage').form);\n"
                + "  alert(f == f.myButton.form);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<form name='form1'>\n"
                + "<input type='submit' name='mySubmit'>\n"
                + "<input type='text' name='myText'>\n"
                + "<input type='password' name='myPassword'>\n"
                + "<input type='button' name='myButton'>\n"
                + "<input type='image' src='foo' name='myImage' id='myImage'>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void inputNameChange() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  document.form1.textfield1.name = 'changed';\n"
                + "}\n"
                + "</script></head><body>\n"
                + "<p>hello world</p>\n"
                + "<form name='form1' onsubmit='doTest()'>\n"
                + "  <input type='text' name='textfield1' id='textfield1' value='foo' />\n"
                + "  <input type='submit' name='button1' id='clickMe' value='pushme' />\n"
                + "</form>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();
    }

    @Test
    @Alerts({"on", "on"})
    public void defautValue() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  alert(document.myForm.myRadio.value);\n"
                + "  alert(document.myForm.myCheckbox.value);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<form name='myForm' action='foo'>\n"
                + "<input type='radio' name='myRadio'/>\n"
                + "<input type='checkbox' name='myCheckbox'/>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text, checkbox, date, datetime-local, month, time, week, color, email, text, submit, "
            + "radio, hidden, password, image, reset, button, file, number,"
            + " range, search, tel, url, text, text",
            "text, checkbox, date, datetime-local, month, time, week, color, email, text, submit, radio, "
                    + "hidden, password, image, reset, button, file, number, range, search, tel, url, text, text"})
    public void changeType() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var input = document.myForm.myInput;\n"
                + "  var types = ['checkbox', 'date', 'datetime-local', 'month', 'time', 'week', 'color'"
                + ", 'email', 'text', 'submit', 'radio', 'hidden', 'password', 'image', 'reset'"
                + ", 'button', 'file', 'number', 'range', 'search', 'tel', 'url', 'unknown', 'text'];"
                + "  var result = input.type;\n"
                + "  for(i = 0; i < types.length; i++) {\n"
                + "    try {\n"
                + "      input.type = types[i];\n"
                + "      result = result + ', ' + input.type;\n"
                + "    } catch(e) { result = result + ', error';}\n"
                + "  }\n"
                + "  alert(result);\n"
                + "  result = input.type;\n"
                + "  for(i = 0; i < types.length; i++) {\n"
                + "    try {\n"
                + "      input.setAttribute('type', types[i]);\n"
                + "      result = result + ', ' + input.type;\n"
                + "    } catch(e) { result = result + ', error';}\n"
                + "  }\n"
                + "  alert(result);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='myForm' action='foo'>\n"
                + "    <input type='text' name='myInput'/>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"button: false, false, function, function, , ",
            "submit: false, false, function, function, submit it!, submit it!",
            "file: false, false, function, function, , ",
            "checkbox: true, true, function, function, , on",
            "radio: true, true, function, function, , on",
            "text: false, false, function, function, , ",
            "password: false, false, function, function, , "})
    public void defaultValues() {
        final String html
                = "<html><head></head><body>\n"
                + "<form name='myForm'>\n"
                + "<input type='button' name='myButton'/>\n"
                + "<input type='submit' name='mySubmit' value='submit it!'/>\n"
                + "<input type='file' name='myFile'/>\n"
                + "<input type='checkbox' name='myCheckbox' checked='true'/>\n"
                + "<input type='radio' name='myRadio' checked='true'/>\n"
                + "<input type='text' name='myText'/>\n"
                + "<input type='password' name='myPwd'/>\n"
                + "</form>\n"
                + "    <script>\n"
                + "function details(_oInput) {\n"
                + "  alert(_oInput.type + ': '\n"
                + "  + _oInput.checked + ', '\n"
                + "  + _oInput.defaultChecked + ', '\n"
                + "  + ((String(_oInput.click).indexOf('function') != -1) ? 'function' : 'unknown') + ', '\n"
                + "  + ((String(_oInput.select).indexOf('function') != -1) ? 'function' : 'unknown') + ', '\n"
                + "  + _oInput.defaultValue + ', '\n"
                + "  + _oInput.value\n"
                + " );\n"
                + "}\n"
                + "var oForm = document.myForm;\n"
                + "details(oForm.myButton);\n"
                + "details(oForm.mySubmit);\n"
                + "details(oForm.myFile);\n"
                + "details(oForm.myCheckbox);\n"
                + "details(oForm.myRadio);\n"
                + "details(oForm.myText);\n"
                + "details(oForm.myPwd);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text", "null", "hidden", "hidden"})
    public void createInputAndChangeType() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  var input = document.createElement('INPUT');\n"
                + "  alert(input.type);\n"
                + "  alert(input.getAttribute('type'));\n"
                + "  input.type = 'hidden';\n"
                + "  alert(input.type);\n"
                + "  alert(input.getAttribute('type'));\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<form name='myForm' action='foo'>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text", "null", "text", "text"})
    public void createInputAndChangeTypeToText() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  var input = document.createElement('INPUT');\n"
                + "  alert(input.type);\n"
                + "  alert(input.getAttribute('type'));\n"
                + "  input.type = 'text';\n"
                + "  alert(input.type);\n"
                + "  alert(input.getAttribute('type'));\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<form name='myForm' action='foo'>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("123")
    public void buttonOutsideForm() {
        final String html
                = "<html><head></head><body>\n"
                + "<button id='clickme' onclick='alert(123)'>click me</button>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickme");
        elem.getOnclick();
    }

    @Test
    @Alerts({"undefined", "30", "undefined", "30", "30", "30", "40", "50", "string", "number"})
    public void maxLength() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var input = document.getElementById('text1');\n"
                + "  alert(input.maxlength);\n"
                + "  alert(input.maxLength);\n"
                + "  alert(input.MaxLength);\n"
                + "  alert(input.getAttribute('maxlength'));\n"
                + "  alert(input.getAttribute('maxLength'));\n"
                + "  alert(input.getAttribute('MaxLength'));\n"
                + "  input.setAttribute('MaXlenGth', 40);\n"
                + "  alert(input.maxLength);\n"
                + "  input.maxLength = 50;\n"
                + "  alert(input.getAttribute('maxlength'));\n"
                + "  alert(typeof input.getAttribute('maxLength'));\n"
                + "  alert(typeof input.maxLength);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<form name='myForm' action='foo'>\n"
                + "<input type='text' id='text1' maxlength='30'/>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "30", "undefined", "30", "30", "30", "40", "50", "string", "number"})
    public void minLength() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  var input = document.getElementById('text1');\n"
                + "  alert(input.minlength);\n"
                + "  alert(input.minLength);\n"
                + "  alert(input.MinLength);\n"
                + "  alert(input.getAttribute('minlength'));\n"
                + "  alert(input.getAttribute('minLength'));\n"
                + "  alert(input.getAttribute('MinLength'));\n"
                + "  input.setAttribute('MiNlenGth', 40);\n"
                + "  alert(input.minLength);\n"
                + "  input.minLength = 50;\n"
                + "  alert(input.getAttribute('minlength'));\n"
                + "  alert(typeof input.getAttribute('minLength'));\n"
                + "  alert(typeof input.minLength);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<form name='myForm' action='foo'>\n"
                + "<input type='text' id='text1' minlength='30'/>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void typeMaxLength() {
        final String html
                = "<html><body>\n"
                + "<form>\n"
                + "<input type='text' id='text1' maxlength='5'/>\n"
                + "<input type='password' id='password1' maxlength='6'/>\n"
                + "</form></body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLInputElementImpl elem = (HTMLInputElementImpl) document.getElementById("text1");
        elem.setValue("123456789");
        assertEquals("12345", elem.getAttribute("value"));
    }

    @Test
    public void typeMaxLengthZero() {
        final String html
                = "<html><body>\n"
                + "<form>\n"
                + "<input type='text' id='text1' maxlength='0'/>\n"
                + "<input type='password' id='password1' maxlength='0'/>\n"
                + "</form></body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLInputElementImpl elem = (HTMLInputElementImpl) document.getElementById("text1");
        elem.setValue("123456789");
        assertEquals("", elem.getAttribute("value"));
    }

    @Test
    public void typeMaxLengthAndBlanks() {
        final String html
                = "<html><body>\n"
                + "<form>\n"
                + "<input type='text' id='text1' maxlength=' 2 '/>\n"
                + "<input type='password' id='password1' maxlength='    4  '/>\n"
                + "</form></body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLInputElementImpl elem = (HTMLInputElementImpl) document.getElementById("text1");
        elem.setValue("123456789");
        assertEquals("12", elem.getAttribute("value"));
    }

    @Test
    @Alerts({"text TeXt", "password PassWord", "hidden Hidden",
            "checkbox CheckBox", "radio rAdiO", "file FILE", "checkbox CHECKBOX"})
    public void typeCase() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  var t = document.getElementById('aText');\n"
                + "  alert(t.type + ' ' + t.getAttribute('type'));\n"
                + "  var p = document.getElementById('aPassword');\n"
                + "  alert(p.type + ' ' + p.getAttribute('type'));\n"
                + "  var h = document.getElementById('aHidden');\n"
                + "  alert(h.type + ' ' + h.getAttribute('type'));\n"
                + "  var cb = document.getElementById('aCb');\n"
                + "  alert(cb.type + ' ' + cb.getAttribute('type'));\n"
                + "  var r = document.getElementById('aRadio');\n"
                + "  alert(r.type + ' ' + r.getAttribute('type'));\n"
                + "  var f = document.getElementById('aFile');\n"
                + "  alert(f.type + ' ' + f.getAttribute('type'));\n"

                + "  try {\n"
                + "    f.type = 'CHECKBOX';\n"
                + "    alert(f.type + ' ' + f.getAttribute('type'));\n"
                + "  } catch(e) { alert('error');}\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <form action='foo'>\n"
                + "    <input Type='TeXt' id='aText' value='some test'>\n"
                + "    <input tYpe='PassWord' id='aPassword' value='some test'>\n"
                + "    <input tyPe='Hidden' id='aHidden' value='some test'>\n"
                + "    <input typE='CheckBox' id='aCb'>\n"
                + "    <input TYPE='rAdiO' id='aRadio'>\n"
                + "    <input type='FILE' id='aFile'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text-text", "text- password", "text-hidden ", "text-checkbox ", "text-\\tradio"})
    public void typeTrim() {
        final String html
                = "<html><head>\n"
                + "<script>\n"                + "function alert(msg) { window.document.title += msg.replace('\t', '\\\\t') + '§';}\n"
                + "function test() {\n"
                + "  var t = document.getElementById('aText');\n"
                + "  alert(t.type + '-' + t.getAttribute('type'));\n"
                + "  var p = document.getElementById('aPassword');\n"
                + "  alert(p.type + '-' + p.getAttribute('type'));\n"
                + "  var h = document.getElementById('aHidden');\n"
                + "  alert(h.type + '-' + h.getAttribute('type'));\n"
                + "  var cb = document.getElementById('aCb');\n"
                + "  alert(cb.type + '-' + cb.getAttribute('type'));\n"
                + "  var r = document.getElementById('aRadio');\n"
                + "  alert(r.type + '-' + r.getAttribute('type'));\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onLoad='test()'>\n"
                + "  <form action='foo'>\n"
                + "    <input type='text' id='aText' value='some test'>\n"
                + "    <input type=' password' id='aPassword' value='some test'>\n"
                + "    <input type='hidden ' id='aHidden' value='some test'>\n"
                + "    <input type='checkbox ' id='aCb'>\n"
                + "    <input type='\tradio' id='aRadio'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void readOnly() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var input = document.getElementById('myInput');\n"
                + "    alert(input.readOnly);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='myInput' value='some test' readonly='false'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true"})
    public void readOnlyInputFile() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var input = document.getElementById('myInput');\n"
                + "    alert(input.readOnly);\n"
                + "    input = document.getElementById('myReadonlyInput');\n"
                + "    alert(input.readOnly);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='myInput' type='file' value='some test'>\n"
                + "  <input id='myReadonlyInput' type='file' value='some test' readonly='false'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"left", "right", "bottom", "middle", "top", "wrong", ""})
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <form>\n"
                + "    <input id='i1' align='left' />\n"
                + "    <input id='i2' align='right' />\n"
                + "    <input id='i3' align='bottom' />\n"
                + "    <input id='i4' align='middle' />\n"
                + "    <input id='i5' align='top' />\n"
                + "    <input id='i6' align='wrong' />\n"
                + "    <input id='i7' />\n"
                + "  </form>\n"

                + "    <script>\n"
                + "  for (var i = 1; i <= 7; i++) {\n"
                + "    alert(document.getElementById('i' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"CenTer", "8", "foo", "left", "right", "bottom", "middle", "top"})
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <form>\n"
                + "    <input id='i1' type='text' align='left' value=''/>\n"
                + "  </form>\n"

                + "    <script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"

                + "  var elem = document.getElementById('i1');\n"
                + "  setAlign(elem, 'CenTer');\n"

                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"

                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'bottom');\n"
                + "  setAlign(elem, 'middle');\n"
                + "  setAlign(elem, 'top');\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "A", "a", "A", "a8", "8Afoo", "8", "@"})
    public void accessKey() {
        final String html
                = "<html><body>\n"
                + "  <input id='a1'>\n"
                + "  <input id='a2' accesskey='A'>\n"
                + "  <script>\n"

                + "    var a1 = document.getElementById('a1');\n"
                + "    var a2 = document.getElementById('a2');\n"
                + "    alert(a1.accessKey);\n"
                + "    alert(a2.accessKey);\n"

                + "    a1.accessKey = 'a';\n"
                + "    alert(a1.accessKey);\n"

                + "    a1.accessKey = 'A';\n"
                + "    alert(a1.accessKey);\n"

                + "    a1.accessKey = 'a8';\n"
                + "    alert(a1.accessKey);\n"

                + "    a1.accessKey = '8Afoo';\n"
                + "    alert(a1.accessKey);\n"

                + "    a1.accessKey = '8';\n"
                + "    alert(a1.accessKey);\n"

                + "    a1.accessKey = '@';\n"
                + "    alert(a1.accessKey);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"test", "4", "42", "2", "[object HTMLInputElement]", "25"})
    public void getAttributeAndSetValue() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"

                + "      function test() {\n"
                + "        var t = document.getElementById('t');\n"
                + "        t.value = 'test';\n"
                + "        alert(t.value);\n"
                + "        if (t.value != null)\n"
                + "          alert(t.value.length);\n"

                + "        t.value = 42;\n"
                + "        alert(t.value);\n"
                + "        if (t.value != null)\n"
                + "          alert(t.value.length);\n"

                + "        t.value = document.getElementById('t');\n"
                + "        alert(t.value);\n"
                + "        if (t.value != null)\n"
                + "          alert(t.value.length);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='test()'>\n"
                + "    <input id='t'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "4", "", "0"})
    public void getAttributeAndSetValueNull() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"

                + "      function test() {\n"
                + "        var t = document.getElementById('t');\n"
                + "        t.value = 'null';\n"
                + "        alert(t.value);\n"
                + "        if (t.value != null)\n"
                + "          alert(t.value.length);\n"

                + "        t.value = null;\n"
                + "        alert(t.value);\n"
                + "        if (t.value != null)\n"
                + "          alert(t.value.length);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='test()'>\n"
                + "    <input id='t'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "2", "7"})
    public void selectionRange() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  var input = document.getElementById('myInput');\n"
                + "  alert(input.selectionStart);\n"
                + "  alert(input.selectionEnd);\n"

                + "  input.setSelectionRange(2, 7);\n"
                + "  alert(input.selectionStart);\n"
                + "  alert(input.selectionEnd);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='myInput' value='some test'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("onsubmit")
    public void submitNonRequired() {
        final String html
                = "<html><head><script>\n"
                + "function submitMe() {\n"
                + "  alert('onsubmit');\n"
                + "}\n"
                + "</script></head>\n"
                + "<body>\n"
                + "  <form onsubmit='submitMe()'>\n"
                + "    <input id='myInput' name='myName' value=''>\n"
                + "    <input id='mySubmit' type='submit'>\n"
                + "  </form>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("mySubmit");
        elem.getOnclick();
    }

    @Test
    @Alerts({"1", ""})
    public void submitRequired() {
        final String html
                = "<html><head><script>\n"
                + "function submitMe() {\n"
                + "  alert('onsubmit');\n"
                + "}\n"
                + "</script></head>\n"
                + "<body>\n"
                + "  <form onsubmit='submitMe()'>\n"
                + "    <input id='myInput' name='myName' value='' required>\n"
                + "    <input id='mySubmit' type='submit'>\n"
                + "  </form>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("text1");
        elem.getOnclick();
    }

    @Test
    @Alerts({"false", "true"})
    public void checkValidity() {
        final String html
                = "<html><head><script>\n"
                + "function checkStatus() {\n"
                + "  var elem = document.getElementById('myInput');\n"
                + "  alert(elem.checkValidity());\n"
                + "}\n"
                + "</script></head>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <input id='myInput' name='myName' required>\n"
                + "    <input id='mySubmit' type='submit'>\n"
                + "  </form>\n"
                + "  <button id='myButton' onclick='checkStatus()'>Check Status</button>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("myButton");
        elem.getOnclick();
    }

    @Test
    @Alerts("?myName=abcdefg")
    public void maxLengthJavaScript() {
        final String html
                = "<html><head><script>\n"
                + "function updateValue() {\n"
                + "  document.getElementById('myInput').value = 'abcdefg';\n"
                + "}\n"
                + "</script></head>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <input id='myInput' name='myName' maxlength='2'>\n"
                + "    <input id='mySubmit' type='submit'>\n"
                + "  </form>\n"
                + "  <button id='myButton' onclick='updateValue()'>Update Value</button>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("myButton");
        elem.getOnclick();
    }

    @Test
    @Alerts("?myName=ab")
    public void maxLength2() {
        final String html
                = "<html><head><script>\n"
                + "</script></head>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <input id='myInput' name='myName' maxlength='2'>\n"
                + "    <input id='mySubmit' type='submit'>\n"
                + "  </form>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("mySubmit");
        elem.getOnclick();
    }

    @Test
    @Alerts({"30", "undefined", "30", "30", "40", "50", "string", "string"})
    public void min() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var input = document.getElementById('text1');\n"
                + "  alert(input.min);\n"
                + "  alert(input.Min);\n"
                + "  alert(input.getAttribute('min'));\n"
                + "  alert(input.getAttribute('Min'));\n"
                + "  input.setAttribute('MiN', 40);\n"
                + "  alert(input.min);\n"
                + "  input.min = 50;\n"
                + "  alert(input.getAttribute('min'));\n"
                + "  alert(typeof input.getAttribute('min'));\n"
                + "  alert(typeof input.min);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<form name='myForm' action='foo'>\n"
                + "<input type='text' id='text1' min='30'/>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"30", "undefined", "30", "30", "40", "50", "string", "string"})
    public void max() {
        final String html
                = "<html><head><script>\n"

                + "function doTest() {\n"
                + "  var input = document.getElementById('text1');\n"
                + "  alert(input.max);\n"
                + "  alert(input.Max);\n"
                + "  alert(input.getAttribute('max'));\n"
                + "  alert(input.getAttribute('Max'));\n"
                + "  input.setAttribute('MaX', 40);\n"
                + "  alert(input.max);\n"
                + "  input.max = 50;\n"
                + "  alert(input.getAttribute('max'));\n"
                + "  alert(typeof input.getAttribute('max'));\n"
                + "  alert(typeof input.max);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<form name='myForm' action='foo'>\n"
                + "<input type='text' id='text1' max='30'/>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "1", "2", "1", "1"})
    public void labels() {
        final String html =
                "<html><head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      debug(document.getElementById('e1'));\n"
                        + "      debug(document.getElementById('e2'));\n"
                        + "      debug(document.getElementById('e3'));\n"
                        + "      debug(document.getElementById('e4'));\n"
                        + "      var labels = document.getElementById('e4').labels;\n"
                        + "      document.body.removeChild(document.getElementById('l4'));\n"
                        + "      debug(document.getElementById('e4'));\n"
                        + "      alert(labels ? labels.length : labels);\n"
                        + "    }\n"
                        + "    function debug(e) {\n"
                        + "      alert(e.labels ? e.labels.length : e.labels);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <input id='e1'><br>\n"
                        + "  <label>something <label> click here <input id='e2'></label></label><br>\n"
                        + "  <label for='e3'> and here</label>\n"
                        + "  <input id='e3'><br>\n"
                        + "  <label id='l4' for='e4'> what about</label>\n"
                        + "  <label> this<input id='e4'></label><br>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"173", "17", "173", "17", "13", "13", "13", "13"})
    public void defaultClientWidthHeight() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('txt');\n"
                + "    alert(elem.clientWidth);\n"
                + "    alert(elem.clientHeight);\n"
                + "    elem = document.getElementById('pw');\n"
                + "    alert(elem.clientWidth);\n"
                + "    alert(elem.clientHeight);\n"
                + "    elem = document.getElementById('chkbx');\n"
                + "    alert(elem.clientWidth);\n"
                + "    alert(elem.clientHeight);\n"
                + "    elem = document.getElementById('radio');\n"
                + "    alert(elem.clientWidth);\n"
                + "    alert(elem.clientHeight);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "<form>\n"
                + "  <input type='text' id='txt'>\n"
                + "  <input type='password' id='pw'>\n"
                + "  <input type='checkbox' id='chkbx'/>\n"
                + "  <input type='radio' id='radio'/>\n"
                + "</form>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLFormElement]")
    public void form() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <input type='text' id='a'>\n"
                + "  </form>"
                + "  <script>\n"

                + "    alert(document.getElementById('a').form);\n"
                + "  </script>"
                + "</body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverButton() {
        mouseOver("<input id='tester' type='button' onmouseover='dumpEvent(event);' value='HtmlUnit'>");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverButtonDisabled() {
        mouseOver("<input id='tester' type='button' onmouseover='dumpEvent(event);' value='HtmlUnit' disabled >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverSubmit() {
        mouseOver("<input id='tester' type='submit' onmouseover='dumpEvent(event);' >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverSubmitDisabled() {
        mouseOver("<input id='tester' type='submit' onmouseover='dumpEvent(event);' disabled >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverReset() {
        mouseOver("<input id='tester' type='reset' onmouseover='dumpEvent(event);' >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverResetDisabled() {
        mouseOver("<input id='tester' type='reset' onmouseover='dumpEvent(event);' disabled >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverText() {
        mouseOver("<input id='tester' type='text' onmouseover='dumpEvent(event);' value='HtmlUnit'>");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverTextDisabled() {
        mouseOver("<input id='tester' type='text' onmouseover='dumpEvent(event);' value='HtmlUnit' disabled >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverPassword() {
        mouseOver("<input id='tester' type='password' onmouseover='dumpEvent(event);' value='HtmlUnit'>");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverPasswordDisabled() {
        mouseOver("<input id='tester' type='password' onmouseover='dumpEvent(event);' value='HtmlUnit' disabled >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverFile() {
        mouseOver("<input id='tester' type='file' onmouseover='dumpEvent(event);'>");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverFileDisabled() {
        mouseOver("<input id='tester' type='file' onmouseover='dumpEvent(event);' disabled >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverCheckbox() {
        mouseOver("<input id='tester' type='checkbox' onmouseover='dumpEvent(event);' value='HtmlUnit'>");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverCheckboxDisabled() {
        mouseOver("<input id='tester' type='checkbox' onmouseover='dumpEvent(event);' value='HtmlUnit' disabled >");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverRadio() {
        mouseOver("<input id='tester' type='radio' onmouseover='dumpEvent(event);' value='HtmlUnit'>");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverRadioDisabled() {
        mouseOver("<input id='tester' type='radio' onmouseover='dumpEvent(event);' value='HtmlUnit' disabled >");
    }

    private void mouseOver(final String element) {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    function dumpEvent(event) {\n"
                        + "      // target\n"
                        + "      var eTarget;\n"
                        + "      if (event.target) {\n"
                        + "        eTarget = event.target;\n"
                        + "      } else if (event.srcElement) {\n"
                        + "        eTarget = event.srcElement;\n"
                        + "      }\n"
                        + "      // defeat Safari bug\n"
                        + "      if (eTarget.nodeType == 3) {\n"
                        + "        eTarget = eTarget.parentNode;\n"
                        + "      }\n"
                        + "      var msg = 'mouse over';\n"
                        + "      if (eTarget.name) {\n"
                        + "        msg = msg + ' [' + eTarget.name + ']';\n"
                        + "      } else {\n"
                        + "        msg = msg + ' [' + eTarget.id + ']';\n"
                        + "      }\n"
                        + "      document.title+= msg;\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <form id='form1'>\n"
                        + "    " + element + "\n"
                        + "  </form>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("tester");
        elem.getOnmouseover();
    }

    @Test
    @Alerts({"Test-Test-Test", "text1-text1-text1"})
    public void getAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var input = document.getElementById('myInput');\n"
                + "  alert(input.value + '-' + input.defaultValue + '-' + input.getAttribute('value'));\n"
                + "  input.setAttribute('value', 'text1');\n"
                + "  alert(input.value + '-' + input.defaultValue + '-' + input.getAttribute('value'));\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='myInput' value='Test'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Test-Test-Test", "text1-text1-text1"})
    public void getAttributeCase() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  var input = document.getElementById('myInput');\n"
                + "  alert(input.value + '-' + input.defaultValue + '-' + input.getAttribute('value'));\n"
                + "  input.setAttribute('vALue', 'text1');\n"
                + "  alert(input.value + '-' + input.defaultValue + '-' + input.getAttribute('value'));\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='myInput' value='Test'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Test-Test-Test", "Test-Test-Test"})
    public void setAttribute() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  var input = document.getElementById('myInput');\n"
                + "  alert(input.value + '-' + input.defaultValue + '-' + input.getAttribute('value'));\n"
                + "  input.setAttribute('autocomplete', 'text1');\n"
                + "  alert(input.value + '-' + input.defaultValue + '-' + input.getAttribute('value'));\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='myInput' value='Test'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("finish")
    public void setAttributeFromJavaScript() {
        final String html
                = "<html><head><script>\n"

                + "function test() {\n"
                + "  var input = document.getElementById('myInput');\n"
                + "  input.setAttribute('value', 'text1');\n"
                + "  alert('finish');\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='myInput' value='Test' onchange=\"alert('changed')\">\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"changed"})
    public void clear() {
        final String html
                = "<html><head><script>\n"
                + "</script></head>\n"
                + "<body>\n"
                + "  <input id='myInput' value='Test' onchange=\"alert('changed')\">\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("myInput");
        elem.getOnchange();
    }

    @Test
    @Alerts({"true", "false", "true", "false", "true"})
    public void willValidate() {
        final String html =
                "<html><head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      alert(document.getElementById('i1').willValidate);\n"
                        + "      alert(document.getElementById('i2').willValidate);\n"
                        + "      alert(document.getElementById('i3').willValidate);\n"
                        + "      alert(document.getElementById('i4').willValidate);\n"
                        + "      alert(document.getElementById('i5').willValidate);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form>\n"
                        + "    <input id='i1'>\n"
                        + "    <input id='i2' disabled>>\n"
                        + "    <input id='i3' hidden>\n"
                        + "    <input id='i4' readonly>\n"
                        + "    <input id='i5' style='display: none'>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void willValidateFormNoValidate() {
        final String html =
                "<html><head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      alert(document.getElementById('i1').willValidate);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form novalidate>\n"
                        + "    <input id='i1'>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false", "true", "false"})
    public void formNoValidate() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var i = document.createElement('input');\n"
                + "    alert(i.formNoValidate);\n"

                + "    i.formNoValidate = '';\n"
                + "    alert(i.formNoValidate);\n"

                + "    i.formNoValidate = 'yes';\n"
                + "    alert(i.formNoValidate);\n"

                + "    i.removeAttribute('formNoValidate');\n"
                + "    alert(i.formNoValidate);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
