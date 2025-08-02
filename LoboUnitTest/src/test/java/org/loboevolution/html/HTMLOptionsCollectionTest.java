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
import org.loboevolution.html.dom.HTMLOptionsCollection;

/**
 * Tests for {@link HTMLOptionsCollection}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLOptionsCollectionTest extends LoboUnitTest {

    @Test
    @Alerts({"0", "1", "foo*"})
    public void addNoPosEmpty() {
        add("", true, false);
    }

    @Test
    @Alerts({"0", "1", "foo"})
    public void addNoPosEmptyMulti() {
        add("", true, true);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three", "foo"})
    public void addNoPos() {
        add("", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three*", "foo"})
    public void addNoPosMulti() {
        add("", false, true);
    }

    @Test
    @Alerts({"0", "1", "foo*"})
    public void addPosMinusOneEmpty() {
        add(", -1", true, false);
    }

    @Test
    @Alerts({"0", "1", "foo"})
    public void addPosMinusOneEmptyMulti() {
        add(", -1", true, true);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three", "foo"})
    public void addPosMinusOne() {
        add(", -1", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three*", "foo"})
    public void addPosMinusOneMulti() {
        add(", -1", false, true);
    }

    @Test
    @Alerts({"0", "1", "foo*"})
    public void addPosZeroEmpty() {
        add(", 0", true, false);
    }

    @Test
    @Alerts({"0", "1", "foo"})
    public void addPosZeroEmptyMulti() {
        add(", 0", true, true);
    }

    @Test
    @Alerts({"3", "4", "foo", "One", "Two*", "Three"})
    public void addPosZero() {
        add(", 0", false, false);
    }

    @Test
    @Alerts({"3", "4", "foo", "One", "Two*", "Three*"})
    public void addPosZeroMulti() {
        add(", 0", false, true);
    }

    @Test
    @Alerts({"0", "1", "foo*"})
    public void addPosOneEmpty() {
        add(", 1", true, false);
    }

    @Test
    @Alerts({"0", "1", "foo"})
    public void addPosOneEmptyMulti() {
        add(", 1", true, true);
    }

    @Test
    @Alerts({"3", "4", "One", "foo", "Two*", "Three"})
    public void addPosOne() {
        add(", 1", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "foo", "Two*", "Three*"})
    public void addPosOneMulti() {
        add(", 1", false, true);
    }

    @Test
    @Alerts({"0", "1", "foo*"})
    public void addPosThreeEmpty() {
        add(", 3", true, false);
    }

    @Test
    @Alerts({"0", "1", "foo"})
    public void addPosThreeEmptyMulti() {
        add(", 3", true, true);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three", "foo"})
    public void addPosThree() {
        add(", 3", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three*", "foo"})
    public void addPosThreeMulti() {
        add(", 3", false, true);
    }

    @Test
    @Alerts({"0", "1", "foo*"})
    public void addPosTenEmpty() {
        add(", 10", true, false);
    }

    @Test
    @Alerts({"0", "1", "foo"})
    public void addPosTenEmptyMulti() {
        add(", 10", true, true);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three", "foo"})
    public void addPosTen() {
        add(", 10", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three*", "foo"})
    public void addPosTenMulti() {
        add(", 10", false, true);
    }

    @Test
    @Alerts({"0", "1", "foo*"})
    public void addBeforeNullEmpty() {
        add(", null", true, false);
    }

    @Test
    @Alerts({"0", "1", "foo"})
    public void addBeforeNullEmptyMulti() {
        add(", null", true, true);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three", "foo"})
    public void addBeforeNull() {
        add(", null", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three*", "foo"})
    public void addBeforeNullMulti() {
        add(", null", false, true);
    }

    @Test
    @Alerts({"0", "exception"})
    public void addBeforeUnknownEmpty() {
        add(", new Option('foo', '123')", true, false);
    }

    @Test
    @Alerts({"0", "exception"})
    public void addBeforeUnknownEmptyMulti() {
        add(", new Option('foo', '123')", true, true);
    }

    @Test
    @Alerts({"3", "exception"})
    public void addBeforeUnknown() {
        add(", new Option('foo', '123')", false, false);
    }

    @Test
    @Alerts({"3", "exception"})
    public void addBeforeUnknownMulti() {
        add(", new Option('foo', '123')", false, true);
    }

    @Test
    @Alerts({"3", "4", "foo", "One", "Two*", "Three"})
    public void addBeforeFirst() {
        add(", oSelect.options[0]", false, false);
    }

    @Test
    @Alerts({"3", "4", "foo", "One", "Two*", "Three*"})
    public void addBeforeFirstMulti() {
        add(", oSelect.options[0]", false, true);
    }

    @Test
    @Alerts({"3", "4", "One", "foo", "Two*", "Three"})
    public void addBeforeSecond() {
        add(", oSelect.options[1]", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "foo", "Two*", "Three*"})
    public void addBeforeSecondMulti() {
        add(", oSelect.options[1]", false, true);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "foo", "Three"})
    public void addBeforeLast() {
        add(", oSelect.options[2]", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "foo", "Three*"})
    public void addBeforeLastMulti() {
        add(", oSelect.options[2]", false, true);
    }

    private void add(final String param, final boolean empty, final boolean multi) {
        String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        var oSelect = document.forms.testForm.select1;\n"
                + "        alert(oSelect.length);\n"
                + "        var opt = new Option();\n"
                + "        opt.text = 'foo';\n"
                + "        opt.value = '123';\n"
                + "        oSelect.options.add(opt" + param + ");\n"
                + "        alert(oSelect.options.length);\n"
                + "        for (var i = 0; i < oSelect.options.length; i++) {\n"
                + "          alert(oSelect.options[i].text + (oSelect.options[i].selected ? '*' : ''));\n"
                + "        }\n"
                + "      } catch (e) { alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='testForm'>\n"
                + "    <select name='select1' " + (multi ? "multiple" : "") + ">\n";
        if (!empty) {
            html = html
                    + "      <option name='option1' value='value1'>One</option>\n"
                    + "      <option name='option2' value='value2' selected>Two</option>\n"
                    + "      <option name='option3' value='value3'" + (multi ? "selected" : "") + ">Three</option>\n";
        }
        html = html
                + "    </select>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("undefined")
    public void getMinusOneEmpty() {
        get("-1", true);
    }

    @Test
    @Alerts("undefined")
    public void getMinusOne() {
        get("-1", false);
    }

    @Test
    @Alerts("One")
    public void getZero() {
        get("0", false);
    }

    @Test
    @Alerts("Two*")
    public void getOne() {
        get("1", false);
    }

    @Test
    @Alerts("Three")
    public void getTwo() {
        get("2", false);
    }

    @Test
    @Alerts("undefined")
    public void getThree() {
        get("3", false);
    }

    @Test
    @Alerts("undefined")
    public void getTenEmpty() {
        get("10", true);
    }

    @Test
    @Alerts("undefined")
    public void getTen() {
        get("10", false);
    }

    private void get(final String pos, final boolean empty) {
        String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        var oSelect = document.forms.testForm.select1;\n"
                + "        var opt = oSelect.options[" + pos + "];\n"
                + "        alert(opt ? opt.text + (opt.selected ? '*' : '') : opt);\n"
                + "      } catch (e) { alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='testForm'>\n"
                + "    <select name='select1' >\n";
        if (!empty) {
            html = html
                    + "      <option name='option1' value='value1'>One</option>\n"
                    + "      <option name='option2' value='value2' selected>Two</option>\n"
                    + "      <option name='option3' value='value3'>Three</option>\n";
        }
        html = html
                + "    </select>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0"})
    public void putMinusOneNullEmpty() {
        put("-1", "null", true, false);
    }

    @Test
    @Alerts({"0", "0"})
    public void putMinusOneNullEmptyMulti() {
        put("-1", "null", true, true);
    }

    @Test
    @Alerts({"3", "3", "One", "Two*", "Three"})
    public void putMinusOneNull() {
        put("-1", "null", false, false);
    }

    @Test
    @Alerts({"3", "3", "One", "Two*", "Three*"})
    public void putMinusOneNullMulti() {
        put("-1", "null", false, true);
    }

    @Test
    @Alerts({"3", "3", "One", "Two*", "Three"})
    public void putMinusOneEmpty() {
        put("-1", "opt", false, false);
    }

    @Test
    @Alerts({"3", "3", "One", "Two*", "Three*"})
    public void putMinusOneEmptyMulti() {
        put("-1", "opt", false, true);
    }

    @Test
    @Alerts({"3", "3", "One", "Two*", "Three"})
    public void putMinusOne() {
        put("-1", "opt", false, false);
    }

    @Test
    @Alerts({"3", "3", "One", "Two*", "Three*"})
    public void putMinusOneMulti() {
        put("-1", "opt", false, true);
    }

    @Test
    @Alerts({"0", "1", "foo*"})
    public void putZeroEmpty() {
        put("0", "opt", true, false);
    }

    @Test
    @Alerts({"0", "1", "foo"})
    public void putZeroEmptyMulti() {
        put("0", "opt", true, true);
    }

    @Test
    @Alerts({"3", "3", "foo", "Two*", "Three"})
    public void putZero() {
        put("0", "opt", false, false);
    }

    @Test
    @Alerts({"3", "3", "foo", "Two*", "Three*"})
    public void putZeroMulti() {
        put("0", "opt", false, true);
    }

    @Test
    @Alerts({"3", "3", "One*", "foo", "Three"})
    public void putOne() {
        put("1", "opt", false, false);
    }

    @Test
    @Alerts({"3", "3", "One", "foo", "Three*"})
    public void putOneMulit() {
        put("1", "opt", false, true);
    }

    @Test
    @Alerts({"3", "3", "One", "Two*", "foo"})
    public void putTwo() {
        put("2", "opt", false, false);
    }

    @Test
    @Alerts({"3", "3", "One", "Two*", "foo"})
    public void putTwoMulti() {
        put("2", "opt", false, true);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three", "foo"})
    public void putThree() {
        put("3", "opt", false, false);
    }

    @Test
    @Alerts({"3", "4", "One", "Two*", "Three*", "foo"})
    public void putThreeMulti() {
        put("3", "opt", false, true);
    }

    @Test
    @Alerts({"3", "11", "One", "Two*", "Three", "", "", "", "", "", "", "", "foo"})
    public void putTen() {
        put("10", "opt", false, false);
    }

    @Test
    @Alerts({"3", "11", "One", "Two*", "Three*", "", "", "", "", "", "", "", "foo"})
    public void putTenMulti() {
        put("10", "opt", false, true);
    }

    private void put(final String pos, final String param, final boolean empty, final boolean multi) {
        String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        var oSelect = document.forms.testForm.select1;\n"
                + "        alert(oSelect.length);\n"
                + "        var opt = new Option();\n"
                + "        opt.text = 'foo';\n"
                + "        opt.value = '123';\n"
                + "        oSelect.options[" + pos + "] = " + param + ";\n"
                + "        alert(oSelect.options.length);\n"
                + "        for (var i = 0; i < oSelect.options.length; i++) {\n"
                + "          alert(oSelect.options[i].text + (oSelect.options[i].selected ? '*' : ''));\n"
                + "        }\n"
                + "      } catch (e) { alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='testForm'>\n"
                + "    <select name='select1' " + (multi ? "multiple" : "") + ">\n";
        if (!empty) {
            html = html
                    + "      <option name='option1' value='value1'>One</option>\n"
                    + "      <option name='option2' value='value2' selected>Two</option>\n"
                    + "      <option name='option3' value='value3'" + (multi ? "selected" : "") + ">Three</option>\n";
        }
        html = html
                + "    </select>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void removeMinusOneEmpty() {
        remove("-1", true, false);
    }

    @Test
    @Alerts("0")
    public void removeMinusOneEmptyMulti() {
        remove("-1", true, true);
    }

    @Test
    @Alerts({"3", "One", "Two*", "Three"})
    public void removeMinusOne() {
        remove("-1", false, false);
    }

    @Test
    @Alerts({"3", "One", "Two*", "Three*"})
    public void removeMinusOneMulti() {
        remove("-1", false, true);
    }

    @Test
    @Alerts({"2", "Two*", "Three"})
    public void removeZero() {
        remove("0", false, false);
    }

    @Test
    @Alerts({"2", "Two*", "Three*"})
    public void removeZeroMulti() {
        remove("0", false, true);
    }

    @Test
    @Alerts({"2", "One*", "Three"})
    public void removeOne() {
        remove("1", false, false);
    }

    @Test
    @Alerts({"2", "One", "Three*"})
    public void removeOneMulti() {
        remove("1", false, true);
    }

    @Test
    @Alerts({"2", "One", "Two*"})
    public void removeTwo() {
        remove("2", false, false);
    }

    @Test
    @Alerts({"2", "One", "Two*"})
    public void removeTwoMulti() {
        remove("2", false, true);
    }

    @Test
    @Alerts({"3", "One", "Two*", "Three"})
    public void removeThree() {
        remove("3", false, false);
    }

    @Test
    @Alerts({"3", "One", "Two*", "Three*"})
    public void removeThreeMulti() {
        remove("3", false, true);
    }

    @Test
    @Alerts("0")
    public void removeTenEmpty() {
        remove("10", true, false);
    }

    @Test
    @Alerts("0")
    public void removeTenEmptyMulti() {
        remove("10", true, true);
    }

    @Test
    @Alerts({"3", "One", "Two*", "Three"})
    public void removeTen() {
        remove("10", false, false);
    }

    @Test
    @Alerts({"3", "One", "Two*", "Three*"})
    public void removeTenMuti() {
        remove("10", false, true);
    }

    private void remove(final String pos, final boolean empty, final boolean multi) {
        String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        var oSelect = document.forms.testForm.select1;\n"
                + "        oSelect.options.remove(" + pos + ");\n"
                + "        alert(oSelect.options.length);\n"
                + "        for (var i = 0; i < oSelect.options.length; i++) {\n"
                + "          alert(oSelect.options[i].text + (oSelect.options[i].selected ? '*' : ''));\n"
                + "        }\n"
                + "      } catch (e) { alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='testForm'>\n"
                + "    <select name='select1' " + (multi ? "multiple" : "") + ">\n";
        if (!empty) {
            html = html
                    + "      <option name='option1' value='value1'>One</option>\n"
                    + "      <option name='option2' value='value2' selected>Two</option>\n"
                    + "      <option name='option3' value='value3'" + (multi ? "selected" : "") + ">Three</option>\n";
        }
        html = html
                + "    </select>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "3"})
    public void length() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var sel = document.form1.select0;\n"
                + "  alert(sel.options.length);\n"
                + "  sel = document.form1.select1;\n"
                + "  alert(sel.options.length);\n"
                + "  sel = document.form1.select3;\n"
                + "  alert(sel.options.length);\n"
                + "}</script></head>\n"
                + "<body onload='test()'>\n"
                + "<form name='form1'>\n"
                + "  <select name='select0'></select>\n"
                + "  <select name='select1'>\n"
                + "    <option>One</option>\n"
                + "  </select>\n"
                + "  <select name='select3'>\n"
                + "    <option>One</option>\n"
                + "    <option>Two</option>\n"
                + "    <option>Three</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "One", "3", "One", "Two", "Three"})
    public void setLengthMinusOne() {
        setLength("-1");
    }

    @Test
    @Alerts({"0", "0", "0"})
    public void setLengthZero() {
        setLength("0");
    }

    @Test
    @Alerts({"1", "", "1", "One", "1", "One"})
    public void setLengthOne() {
        setLength("1");
    }

    @Test
    @Alerts({"2", "", "", "2", "One", "", "2", "One", "Two"})
    public void setLengthTwo() {
        setLength("2");
    }

    @Test
    @Alerts({"3", "", "", "", "3", "One", "", "", "3", "One", "Two", "Three"})
    public void setLengthThree() {
        setLength("3");
    }

    @Test
    @Alerts({"10", "", "", "", "", "", "", "", "", "", "",
            "10", "One", "", "", "", "", "", "", "", "", "",
            "10", "One", "Two", "Three", "", "", "", "", "", "", ""})
    public void setLengthTen() {
        setLength("10");
    }

    private void setLength(final String lenght) {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var sel = document.form1.select0;\n"
                + "  try {\n"
                + "    sel.options.length = " + lenght + ";\n"
                + "    alert(sel.options.length);\n"
                + "    for (var i = 0; i < sel.options.length; i++) {\n"
                + "      alert(sel.options[i].text);\n"
                + "    }\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "  var sel = document.form1.select1;\n"
                + "  try {\n"
                + "    sel.options.length = " + lenght + ";\n"
                + "    alert(sel.options.length);\n"
                + "    for (var i = 0; i < sel.options.length; i++) {\n"
                + "      alert(sel.options[i].text);\n"
                + "    }\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "  var sel = document.form1.select3;\n"
                + "  try {\n"
                + "    sel.options.length = " + lenght + ";\n"
                + "    alert(sel.options.length);\n"
                + "    for (var i = 0; i < sel.options.length; i++) {\n"
                + "      alert(sel.options[i].text);\n"
                + "    }\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "}</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <form name='form1'>\n"
                + "    <select name='select0'></select>\n"
                + "    <select name='select1'>\n"
                + "      <option>One</option>\n"
                + "    </select>\n"
                + "    <select name='select3'>\n"
                + "      <option>One</option>\n"
                + "      <option>Two</option>\n"
                + "      <option>Three</option>\n"
                + "    </select>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "", "4", "One", "1", "", "0"})
    public void setLengthIncrease() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var sel = document.form1.select0;\n"
                + "  try {\n"
                + "    sel.options.length = 1;\n"
                + "    alert(sel.options.length);\n"
                + "    alert(sel.options[0].text);\n"
                + "  } catch (e) { alert(e); }\n"
                + "  sel = document.form1.select1;\n"
                + "  try {\n"
                + "    sel.options.length = 4;\n"
                + "    alert(sel.options.length);\n"
                + "    alert(sel.options[0].text);\n"
                + "    alert(sel.options[0].childNodes.length);\n"
                + "    alert(sel.options[1].text);\n"
                + "    alert(sel.options[1].childNodes.length);\n"
                + "  } catch (e) { alert(e); }\n"
                + "}</script></head>\n"
                + "<body onload='test()'>\n"
                + "<form name='form1'>\n"
                + "  <select name='select0'></select>\n"
                + "  <select name='select1'>\n"
                + "    <option>One</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "false", "true", "false", "false"})
    public void in() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var opts = document.form1.select.options;\n"
                + "  alert(opts.length);\n"
                + "  alert(-1 in opts);\n"
                + "  alert(0 in opts);\n"
                + "  alert(1 in opts);\n"
                + "  alert(42 in opts);\n"
                + "}</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <form name='form1'>\n"
                + "    <select name='select'>\n"
                + "      <option>One</option>\n"
                + "    </select>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
