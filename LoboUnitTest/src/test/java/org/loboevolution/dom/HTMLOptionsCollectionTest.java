/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLOptionsCollection;

/**
 * Tests for {@link HTMLOptionsCollection}.*/


public class HTMLOptionsCollectionTest extends LoboUnitTest {

    @Test
    public void addNoPosEmpty() {
        final String[] messages =  {"0", "1", "foo*"};
        add("", true, false, messages);
    }

    @Test
    public void addNoPosEmptyMulti() {
        final String[] messages = {"0", "1", "foo"};
        add("", true, true, messages);
    }

    @Test
    public void addNoPos() {
        final String[] messages = {"3", "4", "One", "Two*", "Three", "foo"};
        add("", false, false, messages);
    }

    @Test
    public void addNoPosMulti() {
        final String[] messages = {"3", "4", "One", "Two*", "Three*", "foo"};
        add("", false, true, messages);
    }

    @Test
    public void addPosMinusOneEmpty() {
        final String[] messages = {"0", "1", "foo*"};
        add(", -1", true, false, messages);
    }

    @Test
    public void addPosMinusOneEmptyMulti() {
        final String[] messages = {"0", "1", "foo"};
        add(", -1", true, true, messages);
    }

    @Test
    public void addPosMinusOne() {
        final String[] messages = {"3", "4", "One", "Two*", "Three", "foo"};
        add(", -1", false, false, messages);
    }

    @Test
    public void addPosMinusOneMulti() {
        final String[] messages = {"3", "4", "One", "Two*", "Three*", "foo"};
        add(", -1", false, true, messages);
    }

    @Test
    public void addPosZeroEmpty() {
        final String[] messages =  {"0", "1", "foo*"};
        add(", 0", true, false, messages);
    }

    @Test
    public void addPosZeroEmptyMulti() {
        final String[] messages = {"0", "1", "foo"};
        add(", 0", true, true, messages);
    }

    @Test
    public void addPosZero() {
        final String[] messages = {"3", "4", "foo", "One", "Two*", "Three"};
        add(", 0", false, false, messages);
    }

    @Test
    public void addPosZeroMulti() {
        final String[] messages = {"3", "4", "foo", "One", "Two*", "Three*"};
        add(", 0", false, true, messages);
    }

    @Test
    public void addPosOneEmpty() {
        final String[] messages = {"0", "1", "foo*"};
        add(", 1", true, false, messages);
    }

    @Test
    public void addPosOneEmptyMulti() {
        final String[] messages = {"0", "1", "foo"};
        add(", 1", true, true, messages);
    }

    @Test
    public void addPosOne() {
        final String[] messages = {"3", "4", "One", "foo", "Two*", "Three"};
        add(", 1", false, false, messages);
    }

    @Test
    public void addPosOneMulti() {
        final String[] messages = {"3", "4", "One", "foo", "Two*", "Three*"};
        add(", 1", false, true, messages);
    }

    @Test
    public void addPosThreeEmpty() {
        final String[] messages = {"0", "1", "foo*"};
        add(", 3", true, false, messages);
    }

    @Test
    public void addPosThreeEmptyMulti() {
        final String[] messages =  {"0", "1", "foo"};
        add(", 3", true, true, messages);
    }

    @Test
    public void addPosThree() {
        final String[] messages = {"3", "4", "One", "Two*", "Three", "foo"};
        add(", 3", false, false, messages);
    }

    @Test
    public void addPosThreeMulti() {
        final String[] messages = {"3", "4", "One", "Two*", "Three*", "foo"};
        add(", 3", false, true, messages);
    }

    @Test
    public void addPosTenEmpty() {
        final String[] messages = {"0", "1", "foo*"};
        add(", 10", true, false, messages);
    }

    @Test
    public void addPosTenEmptyMulti() {
        final String[] messages = {"0", "1", "foo"};
        add(", 10", true, true, messages);
    }

    @Test
    public void addPosTen() {
        final String[] messages = {"3", "4", "One", "Two*", "Three", "foo"};
        add(", 10", false, false, messages);
    }

    @Test
    public void addPosTenMulti() {
        final String[] messages = {"3", "4", "One", "Two*", "Three*", "foo"};
        add(", 10", false, true, messages);
    }

    @Test

    public void addBeforeNullEmpty() {
        final String[] messages =  {"0", "1", "foo*"};
        add(", null", true, false, messages);
    }

    @Test

    public void addBeforeNullEmptyMulti() {
        final String[] messages =  {"0", "1", "foo"};
        add(", null", true, true, messages);
    }

    @Test

    public void addBeforeNull() {
        final String[] messages =  {"3", "4", "One", "Two*", "Three", "foo"};
        add(", null", false, false, messages);
    }

    @Test
    public void addBeforeNullMulti() {
        final String[] messages = {"3", "4", "One", "Two*", "Three*", "foo"};
        add(", null", false, true, messages);
    }

    @Test
    public void addBeforeUnknownEmpty() {
        final String[] messages = {"0", "exception"};
        add(", new Option('foo', '123')", true, false, messages);
    }

    @Test
    public void addBeforeUnknownEmptyMulti() {
        final String[] messages = {"0", "exception"};
        add(", new Option('foo', '123')", true, true, messages);
    }

    @Test
    public void addBeforeUnknown() {
        final String[] messages = {"3", "exception"};
        add(", new Option('foo', '123')", false, false, messages);
    }

    @Test
    public void addBeforeUnknownMulti() {
        final String[] messages = {"3", "exception"};
        add(", new Option('foo', '123')", false, true, messages);
    }

    @Test
    public void addBeforeFirst() {
        final String[] messages = {"3", "4", "foo", "One", "Two*", "Three"};
        add(", oSelect.options[0]", false, false, messages);
    }

    @Test
    public void addBeforeFirstMulti() {
        final String[] messages = {"3", "4", "foo", "One", "Two*", "Three*"};
        add(", oSelect.options[0]", false, true, messages);
    }

    @Test
    public void addBeforeSecond() {
        final String[] messages = {"3", "4", "One", "foo", "Two*", "Three"};
        add(", oSelect.options[1]", false, false, messages);
    }

    @Test
    public void addBeforeSecondMulti() {
        final String[] messages = {"3", "4", "One", "foo", "Two*", "Three*"};
        add(", oSelect.options[1]", false, true, messages);
    }

    @Test
    public void addBeforeLast() {
        final String[] messages =  {"3", "4", "One", "Two*", "foo", "Three"};
        add(", oSelect.options[2]", false, false, messages);
    }

    @Test
    public void addBeforeLastMulti() {
        final String[] messages = {"3", "4", "One", "Two*", "foo", "Three*"};
        add(", oSelect.options[2]", false, true, messages);
    }

    private void add(final String param, final boolean empty, final boolean multi, String[] messages) {
        String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        var oSelect = document.forms.testForm.select1;\n"
                + "        alert(oSelect.length);\n"
                + "        var opt = new Option('foo', '123');\n"
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
        checkHtmlAlert(html, messages);
    }
    
    @Test
    public void getMinusOneEmpty() {
        final String[] messages = {"undefined"};
        get("-1", true, messages);
    }

    @Test
    public void getMinusOne() {
        final String[] messages = {"undefined"};
        get("-1", false, messages);
    }

    @Test
    public void getZero() {
        final String[] messages = {"One"};
        get("0", false, messages);
    }

    @Test
    public void getOne() {
        final String[] messages = {"Two*"};
        get("1", false, messages);
    }

    @Test
    public void getTwo() {
        final String[] messages = {"Three"};
        get("2", false, messages);
    }

    @Test
    public void getThree() {
        final String[] messages = {"undefined"};
        get("3", false, messages);
    }

    @Test
    public void getTenEmpty() {
        final String[] messages = {"undefined"};
        get("10", true, messages);
    }

    @Test
    public void getTen() {
        final String[] messages = {"undefined"};
        get("10", false, messages);
    }

    private void get(final String pos, final boolean empty, String[] messages) {
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

        checkHtmlAlert(html, messages);
    }

    @Test
    public void putMinusOneNullEmpty() {
        final String[] messages = {"0", "0"};
        put("-1", null, true, false, messages);
    }

    @Test
    public void putMinusOneNullEmptyMulti() {
        final String[] messages = {"0", "0"};
        put("-1", null, true, true, messages);
    }

    @Test
    public void putMinusOneNull() {
        final String[] messages = {"3", "3", "One", "Two*", "Three"};
        put("-1", null, false, false, messages);
    }

    @Test
    public void putMinusOneNullMulti() {
        final String[] messages = {"3", "3", "One", "Two*", "Three*"};
        put("-1", null, false, true, messages);
    }

    @Test
    public void putMinusOneEmpty() {
        final String[] messages = {"3", "3", "One", "Two*", "Three"};
        put("-1", "opt", false, false, messages);
    }

    @Test
    public void putMinusOneEmptyMulti() {
        final String[] messages = {"3", "3", "One", "Two*", "Three*"};
        put("-1", "opt", false, true, messages);
    }

    @Test
    public void putMinusOne() {
        final String[] messages =  {"3", "3", "One", "Two*", "Three"};
        put("-1", "opt", false, false, messages);
    }

    @Test
    public void putMinusOneMulti() {
        final String[] messages =  {"3", "3", "One", "Two*", "Three*"};
        put("-1", "opt", false, true, messages);
    }

    @Test
    public void putZeroEmpty() {
        final String[] messages = {"0", "1", "foo*"};
        put("0", "opt", true, false, messages);
    }

    @Test

    public void putZeroEmptyMulti() {
        final String[] messages =  {"0", "1", "foo"};
        put("0", "opt", true, true, messages);
    }

    @Test
    public void putZero() {
        final String[] messages = {"3", "3", "foo", "Two*", "Three"};
        put("0", "opt", false, false, messages);
    }

    @Test
    public void putZeroMulti() {
        final String[] messages = {"3", "3", "foo", "Two*", "Three*"};
        put("0", "opt", false, true, messages);
    }

    @Test
    public void putOne() {
        final String[] messages = {"3", "3", "One*", "foo", "Three"};
        put("1", "opt", false, false, messages);
    }

    @Test
    public void putOneMulit() {
        final String[] messages = {"3", "3", "One", "foo", "Three*"};
        put("1", "opt", false, true, messages);
    }

    @Test
    public void putTwo() {
        final String[] messages = {"3", "3", "One", "Two*", "foo"};
        put("2", "opt", false, false, messages);
    }

    @Test
    public void putTwoMulti() {
        final String[] messages = {"3", "3", "One", "Two*", "foo"};
        put("2", "opt", false, true, messages);
    }

    @Test
    public void putThree() {
        final String[] messages = {"3", "4", "One", "Two*", "Three", "foo"};
        put("3", "opt", false, false, messages);
    }

    @Test
    public void putThreeMulti() {
        final String[] messages = {"3", "4", "One", "Two*", "Three*", "foo"};
        put("3", "opt", false, true, messages);
    }

    @Test
    public void putTen() {
        final String[] messages = {"3", "11", "One", "Two*", "Three", "", "", "", "", "", "", "", "foo"};
        put("10", "opt", false, false, messages);
    }

    @Test
    public void putTenMulti() {
        final String[] messages = {"3", "11", "One", "Two*", "Three*", "", "", "", "", "", "", "", "foo"};
        put("10", "opt", false, true, messages);
    }

    private void put(final String pos, final String param, final boolean empty, final boolean multi, String[] messages) {
        String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        var oSelect = document.forms.testForm.select1;\n"
                + "        alert(oSelect.length);\n"
                + "        var opt = new Option('foo', '123');\n"
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

        checkHtmlAlert(html, messages);
    }

    @Test
    public void removeMinusOneEmpty() {
        final String[] messages = {"0"};
        remove("-1", true, false, messages);
    }

    @Test
    public void removeMinusOneEmptyMulti() {
        final String[] messages = { "0"};
        remove("-1", true, true, messages);
    }

    @Test
    public void removeMinusOne() {
        final String[] messages = {"3", "One", "Two*", "Three"};
        remove("-1", false, false, messages);
    }

    @Test
    public void removeMinusOneMulti() {
        final String[] messages = {"3", "One", "Two*", "Three*"};
        remove("-1", false, true, messages);
    }

    @Test
    public void removeZero() {
        final String[] messages = {"2", "Two*", "Three"};
        remove("0", false, false, messages);
    }

    @Test
    public void removeZeroMulti() {
        final String[] messages = {"2", "Two*", "Three*"};
        remove("0", false, true, messages);
    }

    @Test
    public void removeOne() {
        final String[] messages = {"2", "One*", "Three"};
        remove("1", false, false, messages);
    }

    @Test
    public void removeOneMulti() {
        final String[] messages =  {"2", "One", "Three*"};
        remove("1", false, true, messages);
    }

    @Test
    public void removeTwo() {
        final String[] messages = {"2", "One", "Two*"};
        remove("2", false, false, messages);
    }

    @Test
    public void removeTwoMulti() {
        final String[] messages = {"2", "One", "Two*"};
        remove("2", false, true, messages);
    }

    @Test
    public void removeThree() {
        final String[] messages = {"3", "One", "Two*", "Three"};
        remove("3", false, false, messages);
    }

    @Test
    public void removeThreeMulti() {
        final String[] messages = {"3", "One", "Two*", "Three*"};
        remove("3", false, true, messages);
    }

    @Test
    public void removeTenEmpty() {
        final String[] messages = {"0"};
        remove("10", true, false, messages);
    }

    @Test
    public void removeTenEmptyMulti() {
        final String[] messages = {"0"};
        remove("10", true, true, messages);
    }

    @Test
    public void removeTen() {
        final String[] messages = {"3", "One", "Two*", "Three"};
        remove("10", false, false, messages);
    }

    @Test
    public void removeTenMuti() {
        final String[] messages = {"3", "One", "Two*", "Three*"};
        remove("10", false, true, messages);
    }

    private void remove(final String pos, final boolean empty, final boolean multi, String[] messages) {
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

        checkHtmlAlert(html, messages);
    }

    @Test
    public void length() {
        final String html
                = "<html><head><title>foo</title><script>\n"
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

        final String[] messages = {"0", "1", "3"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setLengthMinusOne() {
        final String[] messages = {"0", "1", "One", "3", "One", "Two", "Three"};
        setLength("-1", messages);
    }

    @Test
    public void setLengthZero() {
        final String[] messages = {"0", "0", "0"};
        setLength("0", messages);
    }

    @Test
    public void setLengthOne() {
        final String[] messages = {"1", "", "1", "One", "1", "One"};
        setLength("1", messages);
    }

    @Test
    public void setLengthTwo() {
        final String[] messages = {"2", "", "", "2", "One", "", "2", "One", "Two"};
        setLength("2", messages);
    }

    @Test

    public void setLengthThree() {
        final String[] messages = {"3", "", "", "", "3", "One", "", "", "3", "One", "Two", "Three"};
        setLength("3", messages);
    }

    @Test
    public void setLengthTen() {
        final String[] messages = {"10", "", "", "", "", "", "", "", "", "", "",
                "10", "One", "", "", "", "", "", "", "", "", "",
                "10", "One", "Two", "Three", "", "", "", "", "", "", ""};
        setLength("10", messages);
    }

    private void setLength(final String lenght, String[] messages) {
        final String html
                = "<html><head><title>foo</title><script>\n"
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
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setLength_increase() {
        final String html
                = "<html><head><title>foo</title><script>\n"
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

        final String[] messages = {"1", "", "4", "One", "1", "", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void in() {
        final String html
                = "<html><head><title>foo</title><script>\n"
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

        final String[] messages =  {"1", "false", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }
}
