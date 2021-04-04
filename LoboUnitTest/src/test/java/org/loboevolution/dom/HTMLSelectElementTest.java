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
import org.loboevolution.html.dom.HTMLSelectElement;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLSelectElement}.
 */
public class HTMLSelectElementTest extends LoboUnitTest {

    /**
     * <p>getSelectedIndex.</p>
     */
    @Test
    public void getSelectedIndex() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      alert(document.form1.select1.length);\n"
                        + "      alert(document.form1.select1.selectedIndex);\n"

                        + "      alert(document.form1.selectMulti.length);\n"
                        + "      alert(document.form1.selectMulti.selectedIndex);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1'>One</option>\n"
                        + "      <option name='option2' selected>Two</option>\n"
                        + "      <option name='option3'>Three</option>\n"
                        + "    </select>\n"
                        + "    <select name='selectMulti' multiple>\n"
                        + "      <option name='option1' selected>One</option>\n"
                        + "      <option name='option2'>Two</option>\n"
                        + "      <option name='option3' selected>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"3", "1", "3", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSelectedIndexNothingSelected.</p>
     */
    @Test
    public void getSelectedIndexNothingSelected() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      alert(document.form1.select1.length);\n"
                        + "      alert(document.form1.select1.selectedIndex);\n"
                        + "      alert(document.form1.selectMulti.length);\n"
                        + "      alert(document.form1.selectMulti.selectedIndex);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1'>One</option>\n"
                        + "      <option name='option2'>Two</option>\n"
                        + "      <option name='option3'>Three</option>\n"
                        + "    </select>\n"
                        + "    <select name='selectMulti' multiple>\n"
                        + "      <option name='option1'>One</option>\n"
                        + "      <option name='option2'>Two</option>\n"
                        + "      <option name='option3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"3", "0", "3", "-1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getSelectedIndexNoOption.</p>
     */
    @Test
    public void getSelectedIndexNoOption() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      alert(document.form1.select1.length);\n"
                        + "      alert(document.form1.select1.selectedIndex);\n"

                        + "      alert(document.form1.selectMulti.length);\n"
                        + "      alert(document.form1.selectMulti.selectedIndex);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "    </select>\n"
                        + "    <select name='selectMulti' multiple>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"0", "-1", "0", "-1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>selectedIndex2.</p>
     */
    @Test
    public void selectedIndex2() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var oSelect = document.getElementById('main');\n"
                        + "      var oOption = new Option('bla', 1);\n"
                        + "      oSelect.add(oOption);\n"
                        + "      oOption.selected = false;\n"
                        + "      alert(oSelect.selectedIndex);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form action=''>\n"
                        + "    <select id='main'/>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setSelectedIndexInvalidValue.</p>
     */
    @Test
    public void setSelectedIndexInvalidValue() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var s = document.form1.select1;\n"
                        + "      s.selectedIndex = -1;\n"
                        + "      alert(s.selectedIndex);\n"
                        + "      s.selectedIndex = 2;\n"
                        + "      alert(s.selectedIndex);\n"
                        + "      try { s.selectedIndex = 25; } catch (e) { alert('exception') }\n"
                        + "      alert(s.selectedIndex);\n"
                        + "      try { s.selectedIndex = -14; } catch (e) { alert('exception') }\n"
                        + "      alert(s.selectedIndex);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1' action='http://test' method='get'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option value='option1' name='option1'>One</option>\n"
                        + "      <option value='option2' name='option2' selected>Two</option>\n"
                        + "      <option value='option3' name='option3'>Three</option>\n"
                        + "    </select>\n"
                        + "    <input type='submit' id='clickMe' name='submit' value='button'>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"-1", "2", "-1", "-1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOptions.</p>
     */
    @Test
    public void getOptions() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var options = document.form1.select1.options;\n"
                        + "      alert(options.length);\n"
                        + "      for (var i = 0; i < options.length; i++) {\n"
                        + "        alert(options[i].value);\n"
                        + "        alert(options[i].text);\n"
                        + "      }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1'>One</option>\n"
                        + "      <option name='option2' value='value2' selected>Two</option>\n"
                        + "      <option name='option3' value='value3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"3", "value1", "One", "value2", "Two", "value3", "Three"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOptionLabel.</p>
     */
    @Test
    public void getOptionLabel() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var options = document.form1.select1.options;\n"
                        + "      alert(options.length);\n"
                        + "      for (var i = 0; i < options.length; i++) {\n"
                        + "        alert(options[i].value);\n"
                        + "        alert(options[i].text);\n"
                        + "      }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1' label='OneLabel'>One</option>\n"
                        + "      <option name='option2' value='value2' label='TwoLabel' selected>Two</option>\n"
                        + "      <option name='option3' value='value3' label='ThreeLabel'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"3", "value1", "One", "value2", "Two", "value3", "Three"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOptionSelected.</p>
     */
    @Test
    public void getOptionSelected() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var options = document.form1.select1.options;\n"
                        + "      alert(options[0].selected);\n"
                        + "      alert(options[1].selected);\n"
                        + "      options[0].selected = true;\n"
                        + "      alert(options[0].selected);\n"
                        + "      alert(options[1].selected);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1'>One</option>\n"
                        + "      <option name='option2' value='value2' selected>Two</option>\n"
                        + "      <option name='option3' value='value3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"false", "true", "true", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOptionByIndex.</p>
     */
    @Test
    public void getOptionByIndex() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var option1 = document.f1.elements['select'][0];\n"
                        + "      alert(option1 != null);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='f1' action='xxx.html'>\n"
                        + "    <SELECT name='select'>\n"
                        + "      <OPTION value='A'>111</OPTION>\n"
                        + "      <OPTION value='B'>222</OPTION>\n"
                        + "    </SELECT>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getOptionByOptionIndex.</p>
     */
    @Test
    public void getOptionByOptionIndex() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var option1 = document.form1.select1.options[0];\n"
                        + "      alert(option1.text);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1'>One</option>\n"
                        + "      <option name='option2' value='value2' selected>Two</option>\n"
                        + "      <option name='option3' value='value3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"One"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addOption.</p>
     */
    @Test
    public void addOption() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var options = document.form1.select1.options;\n"
                        + "      var index = options.length;\n"
                        + "      options[index] = new Option('Four','value4');\n"
                        + "      alert(options.length);\n"
                        + "      alert(options[index].text);\n"
                        + "      alert(options[index].value);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1'>One</option>\n"
                        + "      <option name='option2' value='value2' selected>Two</option>\n"
                        + "      <option name='option3' value='value3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"4", "Four", "value4"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addOptionSelected.</p>
     */
    @Test
    public void addOptionSelected() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var oSelect = document.form1.select1;\n"
                        + "      var options = oSelect.options;\n"
                        + "      var firstSelectedIndex = oSelect.selectedIndex;\n"
                        + "      alert(firstSelectedIndex);\n"
                        + "      alert(options[firstSelectedIndex].selected);\n"
                        + "      var index = options.length;\n"
                        + "      var oOption = new Option('Four','value4');\n"
                        + "      oOption.selected = true;\n"
                        + "      options[index] = oOption;\n"
                        + "      alert(options.length);\n"
                        + "      alert(options[index].text);\n"
                        + "      alert(options[index].value);\n"
                        + "      alert(options[index].selected);\n"
                        + "      alert(oSelect.selectedIndex);\n"
                        + "      alert(options[firstSelectedIndex].selected);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1'>One</option>\n"
                        + "      <option name='option2' value='value2' selected>Two</option>\n"
                        + "      <option name='option3' value='value3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"1", "true", "4", "Four", "value4", "true", "3", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addOptionWithAddMethodIndexNull.</p>
     */
    @Test
    public void addOptionWithAddMethodIndexNull() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var options = document.form1.select1;\n"
                        + "      try {\n"
                        + "        options.add(new Option('Four','value4'), null);\n"
                        + "      } catch(e) { alert('exception'); }\n"
                        + "      alert(options.length);\n"
                        + "      var index = options.length - 1;\n"
                        + "      alert(options[index].text);\n"
                        + "      alert(options[index].value);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1'>One</option>\n"
                        + "      <option name='option2' value='value2' selected>Two</option>\n"
                        + "      <option name='option3' value='value3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"4", "Four", "value4"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addOptionWithAddMethodNoSecondParameter.</p>
     */
    @Test
    public void addOptionWithAddMethodNoSecondParameter() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var oSelect = document.form1.select1;\n"
                        + "      try {\n"
                        + "        var opt = document.createElement('option');\n"
                        + "        opt.text = 'Four';\n"
                        + "        opt.value = 'value4';\n"
                        + "        oSelect.add(opt);\n"
                        + "        alert(oSelect.length);\n"
                        + "        alert(oSelect[oSelect.length-1].text);\n"
                        + "        alert(oSelect[oSelect.length-1].value);\n"
                        + "        var opt1 = document.createElement('option');\n"
                        + "        opt1.text = 'Three b';\n"
                        + "        opt1.value = 'value3b';\n"
                        + "        oSelect.add(opt1, 3);\n"
                        + "        alert(oSelect[3].text);\n"
                        + "        alert(oSelect[3].value);\n"
                        + "      } catch(e) { alert('exception'); }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1'>One</option>\n"
                        + "      <option name='option2' value='value2' selected>Two</option>\n"
                        + "      <option name='option3' value='value3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages =  {"4", "Four", "value4", "Three b", "value3b"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addOptionTooEmptySelectWithAddMethodIndexNull.</p>
     */
    @Test
    public void addOptionTooEmptySelectWithAddMethodIndexNull() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      var oSelect = document.form1.select1;\n"
                + "      try {\n"
                + "        alert(oSelect.length);\n"
                + "        var opt = document.createElement('option');\n"
                + "        opt.text = 'test';\n"
                + "        opt.value = 'testValue';\n"
                + "        oSelect.add(opt, null);\n"
                + "        alert(oSelect[oSelect.length-1].text);\n"
                + "        alert(oSelect[oSelect.length-1].value);\n"
                + "      } catch(e) { alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='form1'>\n"
                + "    <select name='select1'>\n"
                + "    </select>\n"
                + "  </form>\n"
                + "</body></html>";

        final String[] messages = {"0", "test", "testValue"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addOptionMethodIndexMinusOneEmptySelect.</p>
     */
    @Test
    public void addOptionMethodIndexMinusOneEmptySelect() {
        final String[] messages = {"0", "1", "0", "foo*"};
        addOptionMethod(", -1", true, false,messages);
    }

    /**
     * <p>addOptionMethodIndexMinusOneEmptySelectMulti.</p>
     */
    @Test
    public void addOptionMethodIndexMinusOneEmptySelectMulti() {
        final String[] messages = {"0", "1", "-1", "foo"};
        addOptionMethod(", -1", true, true, messages);
    }

    /**
     * <p>addOptionMethodIndexZeroEmptySelect.</p>
     */
    @Test
    public void addOptionMethodIndexZeroEmptySelect() {
        final String[] messages =  {"0", "1", "0", "foo*"};
        addOptionMethod(", 0", true, false, messages);
    }

    /**
     * <p>addOptionMethodIndexZeroEmptySelectMulti.</p>
     */
    @Test
    public void addOptionMethodIndexZeroEmptySelectMulti() {
        final String[] messages = {"0", "1", "-1", "foo"};
        addOptionMethod(", 0", true, true, messages);
    }

    /**
     * <p>addOptionMethodIndexOneEmptySelect.</p>
     */
    @Test
    public void addOptionMethodIndexOneEmptySelect() {
        final String[] messages = {"0", "1", "0", "foo*"};
        addOptionMethod(", 1", true, false, messages);
    }

    /**
     * <p>addOptionMethodIndexOneEmptySelectMulti.</p>
     */
    @Test
    public void addOptionMethodIndexOneEmptySelectMulti() {
        final String[] messages = {"0", "1", "-1", "foo"};
        addOptionMethod(", 1", true, true, messages);
    }

    /**
     * <p>addOptionMethodIndexFourEmptySelect.</p>
     */
    @Test
    public void addOptionMethodIndexFourEmptySelect() {
        final String[] messages = {"0", "1", "0", "foo*"};
        addOptionMethod(", 4", true, false, messages);
    }

    /**
     * <p>addOptionMethodIndexFourEmptySelectMulti.</p>
     */
    @Test
    public void addOptionMethodIndexFourEmptySelectMulti() {
        final String[] messages = {"0", "1", "-1", "foo"};
        addOptionMethod(", 4", true, true, messages);
    }

    /**
     * <p>addOptionMethodIndexMinusOne.</p>
     */
    @Test
    public void addOptionMethodIndexMinusOne() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "Three", "foo"};
        addOptionMethod(", -1", false, false, messages);
    }

    /**
     * <p>addOptionMethodIndexMinusOneMulti.</p>
     */
    @Test
    public void addOptionMethodIndexMinusOneMulti() {
        final String[] messages =  {"3", "4", "1", "One", "Two*", "Three*", "foo"};
        addOptionMethod(", -1", false, true, messages);
    }

    /**
     * <p>addOptionMethodIndexZero.</p>
     */
    @Test
    public void addOptionMethodIndexZero() {
        final String[] messages = {"3", "4", "2", "foo", "One", "Two*", "Three"};
        addOptionMethod(", 0", false, false, messages);
    }

    /**
     * <p>addOptionMethodIndexZeroMulti.</p>
     */
    @Test
    public void addOptionMethodIndexZeroMulti() {
        final String[] messages = {"3", "4", "2", "foo", "One", "Two*", "Three*"};
        addOptionMethod(", 0", false, true, messages);
    }

    /**
     * <p>addOptionMethodIndexOne.</p>
     */
    @Test
    public void addOptionMethodIndexOne() {
        final String[] messages = {"3", "4", "2", "One", "foo", "Two*", "Three"};
        addOptionMethod(", 1", false, false, messages);
    }

    /**
     * <p>addOptionMethodIndexOneMulti.</p>
     */
    @Test
    public void addOptionMethodIndexOneMulti() {
        final String[] messages = {"3", "4", "2", "One", "foo", "Two*", "Three*"};
        addOptionMethod(", 1", false, true, messages);
    }

    /**
     * <p>addOptionMethodhIndexTwo.</p>
     */
    @Test
    public void addOptionMethodhIndexTwo() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "foo", "Three"};
        addOptionMethod(", 2", false, false, messages);
    }

    /**
     * <p>addOptionMethodhIndexTwoMulti.</p>
     */
    @Test
    public void addOptionMethodhIndexTwoMulti() {
        final String[] messages =  {"3", "4", "1", "One", "Two*", "foo", "Three*"};
        addOptionMethod(", 2", false, true, messages);
    }

    /**
     * <p>addOptionMethodIndexThree.</p>
     */
    @Test
    public void addOptionMethodIndexThree() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "Three", "foo"};
        addOptionMethod(", 3", false, false, messages);
    }

    /**
     * <p>addOptionMethodIndexThreeMulti.</p>
     */
    @Test
    public void addOptionMethodIndexThreeMulti() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "Three*", "foo"};
        addOptionMethod(", 3", false, true, messages);
    }

    /**
     * <p>addOptionMethodIndexFour.</p>
     */
    @Test
    public void addOptionMethodIndexFour() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "Three", "foo"};
        addOptionMethod(", 4", false, false, messages);
    }

    /**
     * <p>addOptionMethodIndexFourMulti.</p>
     */
    @Test
    public void addOptionMethodIndexFourMulti() {
        final String[] messages =  {"3", "4", "1", "One", "Two*", "Three*", "foo"};
        addOptionMethod(", 4", false, true, messages);
    }

    /**
     * <p>addOptionMethodOptionNullEmptySelect.</p>
     */
    @Test
    public void addOptionMethodOptionNullEmptySelect() {
        final String[] messages = {"0", "1", "0", "foo*"};
        addOptionMethod(", null", true, false, messages);
    }

    /**
     * <p>addOptionMethodOptionNullEmptySelectMulti.</p>
     */
    @Test
    public void addOptionMethodOptionNullEmptySelectMulti() {
        final String[] messages = {"0", "1", "-1", "foo"};
        addOptionMethod(", null", true, true, messages);
    }

    /**
     * <p>addOptionMethodNewOptionEmptySelect.</p>
     */
    @Test
    public void addOptionMethodNewOptionEmptySelect() {
        final String[] messages = {"0", "exception"};
        addOptionMethod(", new Option('foo', '123')", true, false, messages);
    }

    /**
     * <p>addOptionMethodNewOptionEmptySelectMulti.</p>
     */
    @Test
    public void addOptionMethodNewOptionEmptySelectMulti() {
        final String[] messages =  {"0", "exception"};
        addOptionMethod(", new Option('foo', '123')", true, true, messages);
    }

    /**
     * <p>addOptionMethodOptionNull.</p>
     */
    @Test
    public void addOptionMethodOptionNull() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "Three", "foo"};
        addOptionMethod(", null", false, false, messages);
    }

    /**
     * <p>addOptionMethodOptionNullMulti.</p>
     */
    @Test
    public void addOptionMethodOptionNullMulti() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "Three*", "foo"};
        addOptionMethod(", null", false, true, messages);
    }

    /**
     * <p>addOptionMethodNewOption.</p>
     */
    @Test
    public void addOptionMethodNewOption() {
        final String[] messages = {"3", "exception"};
        addOptionMethod(", new Option('foo', '123')", false, false, messages);
    }

    /**
     * <p>addOptionMethodNewOptionMulti.</p>
     */
    @Test
    public void addOptionMethodNewOptionMulti() {
        final String[] messages = {"3", "exception"};
        addOptionMethod(", new Option('foo', '123')", false, true, messages);
    }

    /**
     * <p>addOptionMethodOptionFirst.</p>
     */
    @Test
    public void addOptionMethodOptionFirst() {
        final String[] messages = {"3", "4", "2", "foo", "One", "Two*", "Three"};
        addOptionMethod(", oSelect.options[0]", false, false, messages);
    }

    /**
     * <p>addOptionMethodOptionFirstMulti.</p>
     */
    @Test
    public void addOptionMethodOptionFirstMulti() {
        final String[] messages = {"3", "4", "2", "foo", "One", "Two*", "Three*"};
        addOptionMethod(", oSelect.options[0]", false, true, messages);
    }

    /**
     * <p>addOptionMethodOptionSecond.</p>
     */
    @Test
    public void addOptionMethodOptionSecond() {
        final String[] messages = {"3", "4", "2", "One", "foo", "Two*", "Three"};
        addOptionMethod(", oSelect.options[1]", false, false, messages);
    }

    /**
     * <p>addOptionMethodOptionSecondMulti.</p>
     */
    @Test

    public void addOptionMethodOptionSecondMulti() {
        final String[] messages = {"3", "4", "2", "One", "foo", "Two*", "Three*"};
        addOptionMethod(", oSelect.options[1]", false, true, messages);
    }

    /**
     * <p>addOptionMethodOptionThird.</p>
     */
    @Test
    public void addOptionMethodOptionThird() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "foo", "Three"};
        addOptionMethod(", oSelect.options[2]", false, false, messages);
    }

    /**
     * <p>addOptionMethodOptionThirdMulti.</p>
     */
    @Test
    public void addOptionMethodOptionThirdMulti() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "foo", "Three*"};
        addOptionMethod(", oSelect.options[2]", false, true, messages);
    }

    /**
     * <p>addOptionMethodOptionLast.</p>
     */
    @Test
    public void addOptionMethodOptionLast() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "Three", "foo"};
        addOptionMethod(", oSelect.options[3]", false, false, messages);
    }

    /**
     * <p>addOptionMethodOptionLastMulti.</p>
     */
    @Test
    public void addOptionMethodOptionLastMulti() {
        final String[] messages = {"3", "4", "1", "One", "Two*", "Three*", "foo"};
        addOptionMethod(", oSelect.options[3]", false, true, messages);
    }

    private void addOptionMethod(final String param, final boolean empty, final boolean multi, String[] messages) {
        String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        var oSelect = document.forms.testForm.select1;\n"
                + "        alert(oSelect.length);\n"
                + "        var opt = document.createElement('option');\n"
                + "        opt.text = \"foo\";\n"
                + "        opt.value = '123';\n"
                + "        oSelect.add(opt " + param + ");\n"
                + "        alert(oSelect.length);\n"
                + "        alert(oSelect.selectedIndex);\n"
                + "        for (var i = 0; i < oSelect.options.length; i++) {\n"
                + "          alert(oSelect.options[i].text + (oSelect.options[i].selected ? '*' : ''));\n"
                + "        }\n"
                + "      } catch (e) { alert(e); }\n"
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

    /**
     * <p>addWithIndexEmptySelect.</p>
     */
    @Test
    public void addWithIndexEmptySelect() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      try {\n"
                        + "        var oSelect = document.forms.testForm.testSelect;\n"
                        + "        alert(oSelect.length);\n"
                        + "        var opt = new Option('foo', '123');\n"
                        + "        oSelect.add(opt, -1);\n"
                        + "        alert(oSelect.length);\n"
                        + "      } catch (e) { alert('exception'); }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='testForm'>\n"
                        + "    <select name='testSelect'></select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"0", "1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>removeOptionMethodIndexMinusOneEmptySelect.</p>
     */
    @Test
    public void removeOptionMethodIndexMinusOneEmptySelect() {
        final String[] messages =  {"0", "0", "-1"};
        removeOptionMethod("-1", true, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexMinusOneEmptySelectMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexMinusOneEmptySelectMulti() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod("-1", true, true, messages);
    }

    /**
     * <p>removeOptionMethodIndexZeroEmptySelect.</p>
     */
    @Test
    public void removeOptionMethodIndexZeroEmptySelect() {
        final String[] messages =  {"0", "0", "-1"};
        removeOptionMethod("0", true, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexZeroEmptySelectMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexZeroEmptySelectMulti() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod("0", true, true, messages);
    }

    /**
     * <p>removeOptionMethodIndexOneEmptySelect.</p>
     */
    @Test
    public void removeOptionMethodIndexOneEmptySelect() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod("1", true, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexOneEmptySelectMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexOneEmptySelectMulti() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod("1", true, true, messages);
    }

    /**
     * <p>removeOptionMethodIndexFourEmptySelect.</p>
     */
    @Test
    public void removeOptionMethodIndexFourEmptySelect() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod("4", true, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexFourEmptySelectMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexFourEmptySelectMulti() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod("4", true, true, messages);
    }

    /**
     * <p>removeOptionMethodIndexMinusOne.</p>
     */
    @Test
    public void removeOptionMethodIndexMinusOne() {
        final String[] messages = {"3", "3", "1", "One", "Two*", "Three"};
        removeOptionMethod("-1", false, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexMinusOneMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexMinusOneMulti() {
        final String[] messages = {"3", "3", "1", "One", "Two*", "Three*"};
        removeOptionMethod("-1", false, true, messages);
    }

    /**
     * <p>removeOptionMethodIndexZero.</p>
     */
    @Test
    public void removeOptionMethodIndexZero() {
        final String[] messages =  {"3", "2", "0", "Two*", "Three"};
        removeOptionMethod("0", false, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexZeroMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexZeroMulti() {
        final String[] messages = {"3", "2", "0", "Two*", "Three*"};
        removeOptionMethod("0", false, true, messages);
    }

    /**
     * <p>removeOptionMethodIndexOne.</p>
     */
    @Test
    public void removeOptionMethodIndexOne() {
        final String[] messages = {"3", "2", "0", "One*", "Three"};
        removeOptionMethod("1", false, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexOneMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexOneMulti() {
        final String[] messages =  {"3", "2", "1", "One", "Three*"};
        removeOptionMethod("1", false, true, messages);
    }

    /**
     * <p>removeOptionMethodhIndexTwo.</p>
     */
    @Test
    public void removeOptionMethodhIndexTwo() {
        final String[] messages = {"3", "2", "1", "One", "Two*"};
        removeOptionMethod("2", false, false, messages);
    }

    /**
     * <p>removeOptionMethodhIndexTwoMulti.</p>
     */
    @Test
    public void removeOptionMethodhIndexTwoMulti() {
        final String[] messages =  {"3", "2", "1", "One", "Two*"};
        removeOptionMethod("2", false, true, messages);
    }

    /**
     * <p>removeOptionMethodIndexThree.</p>
     */
    @Test
    public void removeOptionMethodIndexThree() {
        final String[] messages = {"3", "3", "1", "One", "Two*", "Three"};
        removeOptionMethod("3", false, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexThreeMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexThreeMulti() {
        final String[] messages = {"3", "3", "1", "One", "Two*", "Three*"};
        removeOptionMethod("3", false, true, messages);
    }

    /**
     * <p>removeOptionMethodIndexFour.</p>
     */
    @Test
    public void removeOptionMethodIndexFour() {
        final String[] messages = {"3", "3", "1", "One", "Two*", "Three"};
        removeOptionMethod("4", false, false, messages);
    }

    /**
     * <p>removeOptionMethodIndexFourMulti.</p>
     */
    @Test
    public void removeOptionMethodIndexFourMulti() {
        final String[] messages =  {"3", "3", "1", "One", "Two*", "Three*"};
        removeOptionMethod("4", false, true, messages);
    }

    /**
     * <p>removeOptionMethodOptionNullEmptySelect.</p>
     */
    @Test
    public void removeOptionMethodOptionNullEmptySelect() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod(null, true, false, messages);
    }

    /**
     * <p>removeOptionMethodOptionNullEmptySelectMulti.</p>
     */
    @Test
    public void removeOptionMethodOptionNullEmptySelectMulti() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod(null, true, true, messages);
    }

    /**
     * <p>removeOptionMethodNewOptionEmptySelect.</p>
     */
    @Test
    public void removeOptionMethodNewOptionEmptySelect() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod("new Option('foo', '123')", true, false, messages);
    }

    /**
     * <p>removeOptionMethodNewOptionEmptySelectMulti.</p>
     */
    @Test
    public void removeOptionMethodNewOptionEmptySelectMulti() {
        final String[] messages = {"0", "0", "-1"};
        removeOptionMethod("new Option('foo', '123')", true, true, messages);
    }

    /**
     * <p>removeOptionMethodOptionNull.</p>
     */
    @Test
    public void removeOptionMethodOptionNull() {
        final String[] messages = {"3", "2", "0", "Two*", "Three"};
        removeOptionMethod(null, false, false, messages);
    }

    /**
     * <p>removeOptionMethodOptionNullMulti.</p>
     */
    @Test
    public void removeOptionMethodOptionNullMulti() {
        final String[] messages = {"3", "2", "0", "Two*", "Three*"};
        removeOptionMethod(null, false, true, messages);
    }

    /**
     * <p>removeOptionMethodNewOption.</p>
     */
    @Test
    public void removeOptionMethodNewOption() {
        final String[] messages = {"3", "2", "0", "Two*", "Three"};
        removeOptionMethod("new Option('foo', '123')", false, false, messages);
    }

    /**
     * <p>removeOptionMethodNewOptionMulti.</p>
     */
    @Test
    public void removeOptionMethodNewOptionMulti() {
        final String[] messages = {"3", "2", "0", "Two*", "Three*"};
        removeOptionMethod("new Option('foo', '123')", false, true, messages);
    }

    /**
     * <p>removeOptionMethodOptionFirst.</p>
     */
    @Test
    public void removeOptionMethodOptionFirst() {
        final String[] messages = {"3", "2", "0", "Two*", "Three"};
        removeOptionMethod("oSelect.options[0]", false, false, messages);
    }

    /**
     * <p>removeOptionMethodOptionFirstMulti.</p>
     */
    @Test
    public void removeOptionMethodOptionFirstMulti() {
        final String[] messages = {"3", "2", "0", "Two*", "Three*"};
        removeOptionMethod("oSelect.options[0]", false, true, messages);
    }

    /**
     * <p>removeOptionMethodOptionSecond.</p>
     */
    @Test
    public void removeOptionMethodOptionSecond() {
        final String[] messages = {"3", "2", "0", "Two*", "Three"};
        removeOptionMethod("oSelect.options[1]", false, false, messages);
    }

    /**
     * <p>removeOptionMethodOptionSecondMulti.</p>
     */
    @Test
    public void removeOptionMethodOptionSecondMulti() {
        final String[] messages = {"3", "2", "0", "Two*", "Three*"};
        removeOptionMethod("oSelect.options[1]", false, true, messages);
    }

    /**
     * <p>removeOptionMethodOptionThird.</p>
     */
    @Test
    public void removeOptionMethodOptionThird() {
        final String[] messages = {"3", "2", "0", "Two*", "Three"};
        removeOptionMethod("oSelect.options[2]", false, false, messages);
    }

    /**
     * <p>removeOptionMethodOptionThirdMulti.</p>
     */
    @Test
    public void removeOptionMethodOptionThirdMulti() {
        final String[] messages = {"3", "2", "0", "Two*", "Three*"};
        removeOptionMethod("oSelect.options[2]", false, true, messages);
    }

    /**
     * <p>removeOptionMethodOptionLast.</p>
     */
    @Test
    public void removeOptionMethodOptionLast() {
        final String[] messages = {"3", "2", "0", "Two*", "Three"};
        removeOptionMethod("oSelect.options[3]", false, false, messages);
    }

    /**
     * <p>removeOptionMethodOptionLastMulti.</p>
     */
    @Test
    public void removeOptionMethodOptionLastMulti() {
        final String[] messages = {"3", "2", "0", "Two*", "Three*"};
        removeOptionMethod("oSelect.options[3]", false, true, messages);
    }

    private void removeOptionMethod(final String param, final boolean empty, final boolean multi, String[] messages) {
        String html = "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      try {\n"
                        + "        var oSelect = document.forms.testForm.select1;\n"
                        + "        alert(oSelect.length);\n"
                        + "        oSelect.remove(" + param + ");\n"
                        + "        alert(oSelect.length);\n"
                        + "        alert(oSelect.selectedIndex);\n"
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

    /**
     * <p>removeOption.</p>
     */
    @Test
    public void removeOption() {
        final String html = "<html>\n"
                        + "<head><title>foo</title><script>\n"
                        + "function doTest() {\n"
                        + "  var options = document.form1.select1.options;\n"
                        + "  options[1]=null;\n"
                        + "  alert(options.length);\n"
                        + "  alert(options[1].text);\n"
                        + "  alert(options[1].value);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option name='option1' value='value1'>One</option>\n"
                        + "    <option name='option2' value='value2' selected>Two</option>\n"
                        + "    <option name='option3' value='value3'>Three</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"2", "Three", "value3"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>removeOptionWithRemoveMethod.</p>
     */
    @Test
    public void removeOptionWithRemoveMethod() {
        final String html = "<html>\n"
                        + "<head><title>foo</title><script>\n"
                        + "function doTest() {\n"
                        + "  var select = document.form1.select1;\n"
                        + "  select.remove(1);\n"
                        + "  alert(select.length);\n"
                        + "  alert(select[1].text);\n"
                        + "  alert(select[1].value);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option name='option1' value='value1'>One</option>\n"
                        + "    <option name='option2' value='value2' selected>Two</option>\n"
                        + "    <option name='option3' value='value3'>Three</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"2", "Three", "value3"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>optionsRemoveMethod.</p>
     */
    @Test
    public void optionsRemoveMethod() {
        final String html =
                "<html>\n"
                        + "<head><title>foo</title><script>\n"
                        + "function doTest() {\n"
                        + "  var options = document.form1.select1.options;\n"
                        + "  try {\n"
                        + "    options.remove(1);\n"
                        + "    alert(options.length);\n"
                        + "    alert(options[1].text);\n"
                        + "    alert(options[1].value);\n"
                        + "  } catch(e) { alert('exception'); }\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option name='option1' value='value1'>One</option>\n"
                        + "    <option name='option2' value='value2' selected>Two</option>\n"
                        + "    <option name='option3' value='value3'>Three</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"2", "Three", "value3"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clearOptions.</p>
     */
    @Test
    public void clearOptions() {
        final String html =
                "<html>\n"
                        + "<head><title>foo</title><script>\n"
                        + "function doTest() {\n"
                        + "  var options = document.form1.select1.options;\n"
                        + "  options.length = 0;\n"
                        + "  alert(options.length);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option name='option1' value='value1'>One</option>\n"
                        + "    <option name='option2' value='value2' selected>Two</option>\n"
                        + "    <option name='option3' value='value3'>Three</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>increaseOptionsSettingLength.</p>
     */
    @Test
    public void increaseOptionsSettingLength() {
        final String html =

                "<html>\n"
                        + "<head><title>foo</title><script>\n"
                        + "function doTest() {\n"
                        + "  var options = document.form1.select1.options;\n"
                        + "  alert(options.length);\n"
                        + "  options.length = 2;\n"
                        + "  alert(options.length);\n"
                        + "  alert(options[1].text);\n"
                        + "  alert(options[1].value);\n"
                        + "  options.length = 50;\n"
                        + "  options[49].text = 'foo';\n"
                        + "  options[49].value = 'fooValue';\n"
                        + "  alert(options[49].text);\n"
                        + "  alert(options[49].value);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option name='option1' value='value1'>One</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"1", "2", "", "", "foo", "fooValue"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>optionArrayHasItemMethod.</p>
     */
    @Test
    public void optionArrayHasItemMethod() {
        final String html = "<html>\n"
                        + "<head><title>foo</title><script>\n"
                        + "function doTest() {\n"
                        + "  var options = document.form1.select1.options;\n"
                        + "  alert(options.item(0).text);\n"
                        + "  alert(options.item(0).value);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option name='option1' value='value1'>One</option>\n"
                        + "    <option name='option2' value='value2' selected>Two</option>\n"
                        + "    <option name='option3' value='value3'>Three</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"One", "value1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getValue.</p>
     */
    @Test
    public void getValue() {
        final String html =

                "<html>\n"
                        + "<head><title>foo</title><script>\n"
                        + "function doTest() {\n"
                        + "  for (var i = 1; i < 6; i++)\n"
                        + "  alert(document.form1['select' + i].value);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option name='option1'>One</option>\n"
                        + "    <option name='option2' selected is='test'>Two</option>\n"
                        + "    <option name='option3'>Three</option>\n"
                        + "  </select>\n"
                        + "  <select name='select2'>\n"
                        + "  </select>\n"
                        + "  <select name='select3' multiple>\n"
                        + "    <option name='option1'>One</option>\n"
                        + "    <option name='option2' selected>Two</option>\n"
                        + "    <option name='option3' selected>Three</option>\n"
                        + "  </select>\n"
                        + "  <select name='select4' multiple>\n"
                        + "    <option name='option1'>One</option>\n"
                        + "    <option name='option2'>Two</option>\n"
                        + "    <option name='option3'>Three</option>\n"
                        + "  </select>\n"
                        + "  <select name='select5' multiple>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"Two", "", "Two", "", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setValue.</p>
     */
    @Test
    public void setValue() {
        final String html =

                "<html>\n"
                        + "<head><title>foo</title><script>\n"
                        + "function doTest() {\n"
                        + "  alert(document.form1.select1.selectedIndex);\n"
                        + "  document.form1.select1.value = 'option2';\n"
                        + "  alert(document.form1.select1.selectedIndex);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "<form name='form1' action='http://test'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option value='option1' name='option1'>One</option>\n"
                        + "    <option value='option2' name='option2'>Two</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"0", "1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>optionsDelegateToSelect.</p>
     */
    @Test
    public void optionsDelegateToSelect() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "function doTest() {\n"
                        + "  try {\n"
                        + "    var s = document.getElementById('select1');\n"
                        + "    doAlerts(s);\n"
                        + "\n"
                        + "    s.selectedIndex = 0;\n"
                        + "    doAlerts(s);\n"
                        + "\n"
                        + "    s.options.selectedIndex = 1;\n"
                        + "    doAlerts(s);\n"
                        + "  } catch (e) { alert('exception'); }\n"
                        + "}\n"
                        + "function doAlerts(s) {\n"
                        + "  alert(s.childNodes.length + '-' + s.options.childNodes.length);\n"
                        + "  alert(s.selectedIndex + '-' + s.options.selectedIndex);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "<form name='test'>\n"
                        + "  <select id='select1'><option>a</option><option selected='selected'>b</option></select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>optionsArrayAdd.</p>
     */
    @Test
    public void optionsArrayAdd() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "function doTest() {\n"
                        + "  var s = document.getElementById('select1');\n"
                        + "  var lengthBefore = s.options.length;\n"
                        + "  alert(lengthBefore);\n"
                        + "  alert(s.options.item(lengthBefore - 1).text);\n"
                        + "  var opt = document.createElement(\"OPTION\");\n"
                        + "  opt.value = 'c';\n"
                        + "  opt.text = 'c';\n"
                        + "  s.options.add(opt);\n"
                        + "  var lengthAfterAdd = s.options.length;\n"
                        + "  alert(lengthAfterAdd);\n"
                        + "  alert(s.options.item(lengthAfterAdd - 1).text);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "<form name='test'>\n"
                        + "<select id='select1'>\n"
                        + "<option>a</option>\n"
                        + "<option selected='selected'>b</option>\n"
                        + "</select></form>\n"
                        + "</body></html>";

        final String[] messages =  {"2", "b", "3", "c"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>selectedIndex.</p>
     */
    @Test
    public void selectedIndex() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var s = document.getElementById('mySelect');\n"
                        + "    s.options.length = 0;\n"
                        + "    s.selectedIndex = 0;\n"
                        + "    alert(s.selectedIndex);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'><option>hello</option></select>\n"
                        + "</body></html>";

        final String[] messages = {"-1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeNegativeOne.</p>
     */
    @Test
    public void defaultSelectedValue_SizeNegativeOne() {
         final String[] messages ={"0", "true", "false", "false", "0"};
         defaultSelectedValue("-1", false, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeNegativeOne_Multi.</p>
     */
    @Test
    public void defaultSelectedValue_SizeNegativeOne_Multi() {
         final String[] messages =  {"0", "false", "false", "false", "-1"};
         defaultSelectedValue("-1", true, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeZero.</p>
     */
    @Test
    public void defaultSelectedValue_SizeZero() {
         final String[] messages = {"0", "true", "false", "false", "0"};
         defaultSelectedValue("0", false, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeZero_Multi.</p>
     */
    @Test
    public void defaultSelectedValue_SizeZero_Multi() {
         final String[] messages = {"0", "false", "false", "false", "-1"};
         defaultSelectedValue("0", true, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeOne.</p>
     */
    @Test
    public void defaultSelectedValue_SizeOne() {
         final String[] messages =  {"1", "true", "false", "false", "0"};
         defaultSelectedValue("1", false, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeOne_Multi.</p>
     */
    @Test
    public void defaultSelectedValue_SizeOne_Multi() {
         final String[] messages = {"1", "false", "false", "false", "-1"};
         defaultSelectedValue("1", true, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeTwo.</p>
     */
    @Test
    public void defaultSelectedValue_SizeTwo() {
         final String[] messages =  {"2", "false", "false", "false", "-1"};
         defaultSelectedValue("2", false, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeTwo_Multi.</p>
     */
    @Test
    public void defaultSelectedValue_SizeTwo_Multi() {
         final String[] messages =  {"2", "false", "false", "false", "-1"};
         defaultSelectedValue("2", true, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeInvalid.</p>
     */
    @Test
    public void defaultSelectedValue_SizeInvalid() {
         final String[] messages =  {"0", "true", "false", "false", "0"};
         defaultSelectedValue("x", false, messages);
    }

    /**
     * <p>defaultSelectedValue_SizeInvalid_Mulzi.</p>
     */
    @Test
    public void defaultSelectedValue_SizeInvalid_Mulzi() {
         final String[] messages =  {"0", "false", "false", "false", "-1"};
         defaultSelectedValue("x", true, messages);
    }

    private void defaultSelectedValue(final String size, final boolean multiple, String[] messages) {
        final String html =  "<html>\n"
                        + "<body onload='test()'>\n"
                        + "<script>\n"
                        + "   function test() {\n"
                        + "     alert(document.getElementById('s').size);\n"
                        + "     alert(document.getElementById('a').selected);\n"
                        + "     alert(document.getElementById('b').selected);\n"
                        + "     alert(document.getElementById('c').selected);\n"
                        + "     alert(document.getElementById('s').selectedIndex);\n"
                        + "   }\n"
                        + "</script>\n"
                        + "<form id='f'>\n"
                        + "  <select id='s' size='" + size + "'" + (multiple ? " multiple" : "") + ">\n"
                        + "    <option id='a' value='a'>a</option>\n"
                        + "    <option id='b' value='b'>b</option>\n"
                        + "    <option id='c' value='c'>c</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>size.</p>
     */
    @Test
    public void size() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.size + 5);//to test if int or string\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'/>\n"
                        + "</body></html>";

        final String[] messages = {"5"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>multiple.</p>
     */
    @Test
    public void multiple() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    alert(document.f['s1'].multiple);\n"
                        + "    alert(document.f['s2'].multiple);\n"
                        + "    document.f['s1'].multiple = false;\n"
                        + "    alert(document.f['s1'].multiple);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form name='f'>\n"
                        + "    <select name='s1' multiple>\n"
                        + "      <option name='option1'>One</option>\n"
                        + "      <option name='option2'>Two</option>\n"
                        + "      <option name='option3'>Three</option>\n"
                        + "    </select>\n"
                        + "    <select name='s2'>\n"
                        + "      <option name='option4'>Four</option>\n"
                        + "      <option name='option5'>Five</option>\n"
                        + "      <option name='option6'>Six</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"true", "false", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>selectedIndex_onfocus.</p>
     */
    @Test
    public void selectedIndex_onfocus() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var s = document.getElementById('mySelect');\n"
                        + "    alert(s.selectedIndex);\n"
                        + "    s.selectedIndex = 1;\n"
                        + "    alert(s.selectedIndex);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect' onfocus='alert(\"select-focus\")'>\n"
                        + "    <option value='o1'>hello</option>\n"
                        + "    <option value='o2'>there</option>\n"
                        + "  </select>\n"
                        + "</body></html>";

        final String[] messages =  {"0", "1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>value_onfocus.</p>
     */
    @Test
    public void value_onfocus() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var s = document.getElementById('mySelect');\n"
                        + "    alert(s.value);\n"
                        + "    s.value = 'o2';\n"
                        + "    alert(s.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect' onfocus='alert(\"select-focus\")'>\n"
                        + "    <option value='o1'>hello</option>\n"
                        + "    <option value='o2'>there</option>\n"
                        + "  </select>\n"
                        + "</body></html>";

        final String[] messages =  {"o1", "o2"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>selectedIndex_appendChild.</p>
     */
    @Test
    public void selectedIndex_appendChild() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var s = document.getElementById('mySelect');\n"
                        + "    var o = document.createElement('option');\n"
                        + "    alert(s.selectedIndex);\n"
                        + "    s.appendChild(o);\n"
                        + "    alert(s.selectedIndex);\n"
                        + "    s.removeChild(o);\n"
                        + "    alert(s.selectedIndex);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'></select>\n"
                        + "</body></html>";

        final String[] messages =  {"-1", "0", "-1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>selectedIndex_insertBefore.</p>
     */
    @Test

    public void selectedIndex_insertBefore() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var s = document.getElementById('mySelect');\n"
                        + "    var o = document.createElement('option');\n"
                        + "    alert(s.selectedIndex);\n"
                        + "    s.insertBefore(o, null);\n"
                        + "    alert(s.selectedIndex);\n"
                        + "    s.removeChild(o);\n"
                        + "    alert(s.selectedIndex);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'></select>\n"
                        + "</body></html>";

        final String[] messages = {"-1", "0", "-1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>selectedIndex_add.</p>
     */
    @Test
    public void selectedIndex_add() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var s = document.getElementById('mySelect');\n"
                        + "    var o = document.createElement('option');\n"
                        + "    alert(s.selectedIndex);\n"
                        + "    if (document.all)\n"
                        + "      s.add(o);\n"
                        + "    else\n"
                        + "      s.add(o, null);\n"
                        + "    alert(s.selectedIndex);\n"
                        + "    s.removeChild(o);\n"
                        + "    alert(s.selectedIndex);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'></select>\n"
                        + "</body></html>";

        final String[] messages = {"-1", "0", "-1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>item.</p>
     */
    @Test
    public void item() {
        final String html =

                "<html>\n"
                        + "<body>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option>first</option>\n"
                        + "    <option>second</option>\n"
                        + "  </select>\n"

                        + "  <script>\n"
                        + "    var s = document.getElementById('mySelect');\n"
                        + "    alert(s.item(0).text);\n"
                        + "    alert(s.item(300));\n"
                        + "    try { alert(s.item(-5)); } catch(e) { alert('exception'); }\n"
                        + "  </script>\n"
                        + "</body></html>";

        final String[] messages = {"first", null, null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>value.</p>
     */
    @Test
    public void value() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.value);\n"
                        + "    select.value = 'three';\n"
                        + "    alert(select.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option value='one'>One</option>\n"
                        + "    <option selected value='two'>Two</option>\n"
                        + "  </select>\n"
                        + "</body></html>";
        final String[] messages = {"two", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>valueByValue.</p>
     */
    @Test
    public void valueByValue() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.value);\n"
                        + "    select.value = 'one';\n"
                        + "    alert(select.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option value='one'>1</option>\n"
                        + "    <option selected value='two'>2</option>\n"
                        + "  </select>\n"
                        + "</body></html>";
        final String[] messages = {"two", "one"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>valueByValueCase.</p>
     */
    @Test
    public void valueByValueCase() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.value);\n"
                        + "    select.value = 'One';\n"
                        + "    alert(select.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option value='one'>1</option>\n"
                        + "    <option selected value='two'>2</option>\n"
                        + "  </select>\n"
                        + "</body></html>";
        final String[] messages =  {"two", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>valueByText.</p>
     */
    @Test
    public void valueByText() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.value);\n"
                        + "    select.value = 'One';\n"
                        + "    alert(select.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option>One</option>\n"
                        + "    <option selected value='two'>Two</option>\n"
                        + "  </select>\n"
                        + "</body></html>";
        final String[] messages =  {"two", "One"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>valueByTextTrim.</p>
     */
    @Test
    public void valueByTextTrim() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.value);\n"
                        + "    select.value = 'One';\n"
                        + "    alert(select.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option> One </option>\n"
                        + "    <option selected value='two'>Two</option>\n"
                        + "  </select>\n"
                        + "</body></html>";
        final String[] messages = {"two", "One"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>valueNull.</p>
     */
    @Test
    public void valueNull() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.value);\n"
                        + "    select.value = null;\n"
                        + "    alert(select.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option>One</option>\n"
                        + "    <option selected value='two'>Two</option>\n"
                        + "  </select>\n"
                        + "</body></html>";
        final String[] messages = {"two", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>valueOther.</p>
     */
    @Test
    public void valueOther() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.value);\n"
                        + "    select.value = 1234;\n"
                        + "    alert(select.value);\n"
                        + "    select.value = select;\n"
                        + "    alert(select.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option>One</option>\n"
                        + "    <option selected value='two'>Two</option>\n"
                        + "  </select>\n"
                        + "</body></html>";
        final String[] messages = {"two", "", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>valueAfterReset.</p>
     */
    @Test
    public void valueAfterReset() {
        final String html =

                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var form = document.getElementById('myForm');\n"
                        + "    var select = document.getElementById('mySelect');\n"
                        + "    alert(select.value);\n"
                        + "    select.options[1].selected = true;\n"
                        + "    alert(select.value);\n"
                        + "    form.reset();\n"
                        + "    alert(select.value);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<form id='myForm' name='myForm'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option value='One'>One</option>\n"
                        + "    <option value='Two'>Two</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        final String[] messages = {"One", "Two", "One"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>labels.</p>
     */
    @Test
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
                        + "  <select id='e1'>e 1</select><br>\n"
                        + "  <label>something <label> click here <select id='e2'>e 2</select></label></label><br>\n"
                        + "  <label for='e3'> and here</label>\n"
                        + "  <select id='e3'>e 3</select><br>\n"
                        + "  <label id='l4' for='e4'> what about</label>\n"
                        + "  <label> this<select id='e4'>e 4</select></label><br>\n"
                        + "</body></html>";

        final String[] messages = {"0", "2", "1", "2", "1", "1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>in.</p>
     */
    @Test
    public void in() {
        final String html =
                "<html>\n"
                        + "<head><title>foo</title>\n"
                        + "<script>\n"
                        + "  function doTest() {\n"
                        + "    var options = document.form1.select1.options;\n"
                        + "    alert(options.length);\n"
                        + "    alert(-1 in options);\n"
                        + "    alert(0 in options);\n"
                        + "    alert(1 in options);\n"
                        + "    alert(42 in options);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n2"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option name='option1' value='value1'>One</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final String[] messages = {"1", "false", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addOptionByAssigningViaIndex.</p>
     */
    @Test
    public void addOptionByAssigningViaIndex() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var select = document.getElementById('select1');\n"
                        + "      alert(select[1]);\n"
                        + "      select[1] = new Option('text','value');\n"
                        + "      alert(select[1]);\n"
                        + "      alert(select.options.length);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <select id='select1'>\n"
                        + "    <option name='option1' value='value1'>One</option>\n"
                        + "  </select>\n"
                        + "</body></html>";

        final String[] messages =  {"undefined", "[object HTMLOptionElement]", "2"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>addOptionByAssigningViaIndex2.</p>
     */
    @Test
    public void addOptionByAssigningViaIndex2() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function doTest() {\n"
                        + "      var select = document.getElementById('select1');\n"
                        + "      alert(select[7]);\n"
                        + "      select[7] = new Option('text','value');\n"
                        + "      alert(select[7]);\n"
                        + "      alert(select.options.length);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <select id='select1'>\n"
                        + "    <option name='option1' value='value1'>One</option>\n"
                        + "  </select>\n"
                        + "</body></html>";

        final String[] messages =  {"undefined", "[object HTMLOptionElement]", "8"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>form.</p>
     */
    @Test
    public void form() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <select id='a'>\n"
                + "      <option name='option1' value='value1'>One</option>\n"
                + "    </select>\n"
                + "  </form>"
                + "  <script>\n"
                + "    alert(document.getElementById('a').form);\n"
                + "  </script>"
                + "</body>"
                + "</html>";
        final String[] messages = {"[object HTMLFormElement]"};
        checkHtmlAlert(html, messages);
    }
}
