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
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.HTMLSelectElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLOptionElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link HTMLSelectElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLSelectElementTest extends LoboUnitTest {

    @Test
    @Alerts({"3", "1", "3", "0"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "0", "3", "-1"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "-1", "0", "-1"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "1", "3", "2"})
    public void setSelectedIndex() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function doTest() {\n"
                        + "      alert(document.form1.select1.length);\n"
                        + "      alert(document.form1.select1.selectedIndex);\n"

                        + "      document.form1.select1.selectedIndex = 2;\n"
                        + "      alert(document.form1.select1.length);\n"
                        + "      alert(document.form1.select1.selectedIndex);\n"
                        + "      document.form1.select1.selectedIndex = -1;\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1' action='/foo' method='get'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option value='option1' name='option1'>One</option>\n"
                        + "      <option value='option2' name='option2' selected>Two</option>\n"
                        + "      <option value='option3' name='option3'>Three</option>\n"
                        + "    </select>\n"
                        + "    <input type='submit' id='clickMe' name='submit' value='button'>\n"
                        + "</form>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();
    }

    @Test
    @Alerts("0")
    public void selectedIndex2() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function doTest() {\n"
                        + "      var oSelect = document.getElementById('main');\n"
                        + "      var oOption = new Option('bla', 1);\n"
                        + "      oSelect.options[oSelect.options.length] = oOption;\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"-1", "2", "-1", "-1"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "value1", "One", "value2", "Two", "value3", "Three"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "value1", "One", "value2", "Two", "value3", "Three"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true", "true", "false"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("One")
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "Four", "value4"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "true", "4", "Four", "value4", "true", "3", "false"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "Four", "value4"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "Four", "value4", "Three b", "value3b"})
    public void addOptionWithAddMethodNoSecondParameter() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function doTest() {\n"
                        + "      var oSelect = document.form1.select1;\n"
                        + "      try {\n"
                        + "        oSelect.add(new Option('Four', 'value4'));\n"
                        + "        alert(oSelect.length);\n"
                        + "        alert(oSelect[oSelect.length-1].text);\n"
                        + "        alert(oSelect[oSelect.length-1].value);\n"

                        + "        oSelect.add(new Option('Three b', 'value3b'), 3);\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "test", "testValue"})
    public void addOptionTooEmptySelectWithAddMethodIndexNull() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function doTest() {\n"
                        + "      var oSelect = document.form1.select1;\n"
                        + "      try {\n"
                        + "        alert(oSelect.length);\n"
                        + "        oSelect.add(new Option('test', 'testValue'), null);\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "0", "foo*"})
    public void addOptionMethodIndexMinusOneEmptySelect() {
        addOptionMethod(", -1", true, false);
    }

    @Test
    @Alerts({"0", "1", "-1", "foo"})
    public void addOptionMethodIndexMinusOneEmptySelectMulti() {
        addOptionMethod(", -1", true, true);
    }

    @Test
    @Alerts({"0", "1", "0", "foo*"})
    public void addOptionMethodIndexZeroEmptySelect() {
        addOptionMethod(", 0", true, false);
    }

    @Test
    @Alerts({"0", "1", "-1", "foo"})
    public void addOptionMethodIndexZeroEmptySelectMulti() {
        addOptionMethod(", 0", true, true);
    }

    @Test
    @Alerts({"0", "1", "0", "foo*"})
    public void addOptionMethodIndexOneEmptySelect() {
        addOptionMethod(", 1", true, false);
    }

    @Test
    @Alerts({"0", "1", "-1", "foo"})
    public void addOptionMethodIndexOneEmptySelectMulti() {
        addOptionMethod(", 1", true, true);
    }

    @Test
    @Alerts({"0", "1", "0", "foo*"})
    public void addOptionMethodIndexFourEmptySelect() {
        addOptionMethod(", 4", true, false);
    }

    @Test
    @Alerts({"0", "1", "-1", "foo"})
    public void addOptionMethodIndexFourEmptySelectMulti() {
        addOptionMethod(", 4", true, true);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three", "foo"})
    public void addOptionMethodIndexMinusOne() {
        addOptionMethod(", -1", false, false);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three*", "foo"})
    public void addOptionMethodIndexMinusOneMulti() {
        addOptionMethod(", -1", false, true);
    }

    @Test
    @Alerts({"3", "4", "2", "foo", "One", "Two*", "Three"})
    public void addOptionMethodIndexZero() {
        addOptionMethod(", 0", false, false);
    }

    @Test
    @Alerts({"3", "4", "2", "foo", "One", "Two*", "Three*"})
    public void addOptionMethodIndexZeroMulti() {
        addOptionMethod(", 0", false, true);
    }

    @Test
    @Alerts({"3", "4", "2", "One", "foo", "Two*", "Three"})
    public void addOptionMethodIndexOne() {
        addOptionMethod(", 1", false, false);
    }

    @Test
    @Alerts({"3", "4", "2", "One", "foo", "Two*", "Three*"})
    public void addOptionMethodIndexOneMulti() {
        addOptionMethod(", 1", false, true);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "foo", "Three"})
    public void addOptionMethodhIndexTwo() {
        addOptionMethod(", 2", false, false);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "foo", "Three*"})
    public void addOptionMethodhIndexTwoMulti() {
        addOptionMethod(", 2", false, true);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three", "foo"})
    public void addOptionMethodIndexThree() {
        addOptionMethod(", 3", false, false);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three*", "foo"})
    public void addOptionMethodIndexThreeMulti() {
        addOptionMethod(", 3", false, true);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three", "foo"})
    public void addOptionMethodIndexFour() {
        addOptionMethod(", 4", false, false);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three*", "foo"})
    public void addOptionMethodIndexFourMulti() {
        addOptionMethod(", 4", false, true);
    }

    @Test
    @Alerts({"0", "1", "0", "foo*"})
    public void addOptionMethodOptionNullEmptySelect() {
        addOptionMethod(", null", true, false);
    }

    @Test
    @Alerts({"0", "1", "-1", "foo"})
    public void addOptionMethodOptionNullEmptySelectMulti() {
        addOptionMethod(", null", true, true);
    }

    @Test
    @Alerts({"0", "exception"})
    public void addOptionMethodNewOptionEmptySelect() {
        addOptionMethod(", new Option('foo', '123')", true, false);
    }

    @Test
    @Alerts({"0", "exception"})
    public void addOptionMethodNewOptionEmptySelectMulti() {
        addOptionMethod(", new Option('foo', '123')", true, true);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three", "foo"})
    public void addOptionMethodOptionNull() {
        addOptionMethod(", null", false, false);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three*", "foo"})
    public void addOptionMethodOptionNullMulti() {
        addOptionMethod(", null", false, true);
    }

    @Test
    @Alerts({"3", "exception"})
    public void addOptionMethodNewOption() {
        addOptionMethod(", new Option('foo', '123')", false, false);
    }

    @Test
    @Alerts({"3", "exception"})
    public void addOptionMethodNewOptionMulti() {
        addOptionMethod(", new Option('foo', '123')", false, true);
    }

    @Test
    @Alerts({"3", "4", "2", "foo", "One", "Two*", "Three"})
    public void addOptionMethodOptionFirst() {
        addOptionMethod(", oSelect.options[0]", false, false);
    }

    @Test
    @Alerts({"3", "4", "2", "foo", "One", "Two*", "Three*"})
    public void addOptionMethodOptionFirstMulti() {
        addOptionMethod(", oSelect.options[0]", false, true);
    }

    @Test
    @Alerts({"3", "4", "2", "One", "foo", "Two*", "Three"})
    public void addOptionMethodOptionSecond() {
        addOptionMethod(", oSelect.options[1]", false, false);
    }

    @Test
    @Alerts({"3", "4", "2", "One", "foo", "Two*", "Three*"})
    public void addOptionMethodOptionSecondMulti() {
        addOptionMethod(", oSelect.options[1]", false, true);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "foo", "Three"})
    public void addOptionMethodOptionThird() {
        addOptionMethod(", oSelect.options[2]", false, false);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "foo", "Three*"})
    public void addOptionMethodOptionThirdMulti() {
        addOptionMethod(", oSelect.options[2]", false, true);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three", "foo"})
    public void addOptionMethodOptionLast() {
        addOptionMethod(", oSelect.options[3]", false, false);
    }

    @Test
    @Alerts({"3", "4", "1", "One", "Two*", "Three*", "foo"})
    public void addOptionMethodOptionLastMulti() {
        addOptionMethod(", oSelect.options[3]", false, true);
    }

    private void addOptionMethod(final String param, final boolean empty, final boolean multi) {
        String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function doTest() {\n"
                        + "      try {\n"
                        + "        var oSelect = document.forms.testForm.select1;\n"
                        + "        alert(oSelect.length);\n"
                        + "        var opt = new Option('foo', '123');\n"
                        + "        oSelect.add(opt" + param + ");\n"

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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodIndexMinusOneEmptySelect() {
        removeOptionMethod("-1", true, false);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodIndexMinusOneEmptySelectMulti() {
        removeOptionMethod("-1", true, true);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodIndexZeroEmptySelect() {
        removeOptionMethod("0", true, false);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodIndexZeroEmptySelectMulti() {
        removeOptionMethod("0", true, true);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodIndexOneEmptySelect() {
        removeOptionMethod("1", true, false);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodIndexOneEmptySelectMulti() {
        removeOptionMethod("1", true, true);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodIndexFourEmptySelect() {
        removeOptionMethod("4", true, false);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodIndexFourEmptySelectMulti() {
        removeOptionMethod("4", true, true);
    }

    @Test
    @Alerts({"3", "3", "1", "One", "Two*", "Three"})
    public void removeOptionMethodIndexMinusOne() {
        removeOptionMethod("-1", false, false);
    }

    @Test
    @Alerts({"3", "3", "1", "One", "Two*", "Three*"})
    public void removeOptionMethodIndexMinusOneMulti() {
        removeOptionMethod("-1", false, true);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three"})
    public void removeOptionMethodIndexZero() {
        removeOptionMethod("0", false, false);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three*"})
    public void removeOptionMethodIndexZeroMulti() {
        removeOptionMethod("0", false, true);
    }

    @Test
    @Alerts({"3", "2", "0", "One*", "Three"})
    public void removeOptionMethodIndexOne() {
        removeOptionMethod("1", false, false);
    }

    @Test
    @Alerts({"3", "2", "1", "One", "Three*"})
    public void removeOptionMethodIndexOneMulti() {
        removeOptionMethod("1", false, true);
    }

    @Test
    @Alerts({"3", "2", "1", "One", "Two*"})
    public void removeOptionMethodhIndexTwo() {
        removeOptionMethod("2", false, false);
    }

    @Test
    @Alerts({"3", "2", "1", "One", "Two*"})
    public void removeOptionMethodhIndexTwoMulti() {
        removeOptionMethod("2", false, true);
    }

    @Test
    @Alerts({"3", "3", "1", "One", "Two*", "Three"})
    public void removeOptionMethodIndexThree() {
        removeOptionMethod("3", false, false);
    }

    @Test
    @Alerts({"3", "3", "1", "One", "Two*", "Three*"})
    public void removeOptionMethodIndexThreeMulti() {
        removeOptionMethod("3", false, true);
    }

    @Test
    @Alerts({"3", "3", "1", "One", "Two*", "Three"})
    public void removeOptionMethodIndexFour() {
        removeOptionMethod("4", false, false);
    }

    @Test
    @Alerts({"3", "3", "1", "One", "Two*", "Three*"})
    public void removeOptionMethodIndexFourMulti() {
        removeOptionMethod("4", false, true);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodOptionNullEmptySelect() {
        removeOptionMethod("null", true, false);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodOptionNullEmptySelectMulti() {
        removeOptionMethod("null", true, true);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodNewOptionEmptySelect() {
        removeOptionMethod("new Option('foo', '123')", true, false);
    }

    @Test
    @Alerts({"0", "0", "-1"})
    public void removeOptionMethodNewOptionEmptySelectMulti() {
        removeOptionMethod("new Option('foo', '123')", true, true);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three"})
    public void removeOptionMethodOptionNull() {
        removeOptionMethod("null", false, false);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three*"})
    public void removeOptionMethodOptionNullMulti() {
        removeOptionMethod("null", false, true);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three"})
    public void removeOptionMethodNewOption() {
        removeOptionMethod("new Option('foo', '123')", false, false);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three*"})
    public void removeOptionMethodNewOptionMulti() {
        removeOptionMethod("new Option('foo', '123')", false, true);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three"})
    public void removeOptionMethodOptionFirst() {
        removeOptionMethod("oSelect.options[0]", false, false);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three*"})
    public void removeOptionMethodOptionFirstMulti() {
        removeOptionMethod("oSelect.options[0]", false, true);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three"})
    public void removeOptionMethodOptionSecond() {
        removeOptionMethod("oSelect.options[1]", false, false);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three*"})
    public void removeOptionMethodOptionSecondMulti() {
        removeOptionMethod("oSelect.options[1]", false, true);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three"})
    public void removeOptionMethodOptionThird() {
        removeOptionMethod("oSelect.options[2]", false, false);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three*"})
    public void removeOptionMethodOptionThirdMulti() {
        removeOptionMethod("oSelect.options[2]", false, true);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three"})
    public void removeOptionMethodOptionLast() {
        removeOptionMethod("oSelect.options[3]", false, false);
    }

    @Test
    @Alerts({"3", "2", "0", "Two*", "Three*"})
    public void removeOptionMethodOptionLastMulti() {
        removeOptionMethod("oSelect.options[3]", false, true);
    }

    private void removeOptionMethod(final String param, final boolean empty, final boolean multi) {
        String html =
                "<html>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "Three", "value3"})
    public void removeOption() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "Three", "value3"})
    public void removeOptionWithRemoveMethod() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "Three", "value3"})
    public void optionsRemoveMethod() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void clearOptions() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "", "", "foo", "fooValue"})
    public void increaseOptionsSettingLength() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"One", "value1"})
    public void optionArrayHasItemMethod() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Two", "", "Two", "", ""})
    public void getValue() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1"})
    public void setValue() {
        final String html =
                "<html>\n"
                        + "<head><script>\n"

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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void optionsDelegateToSelect() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "b", "3", "c"})
    public void optionsArrayAdd() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("-1")
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "true", "false", "false", "0"})
    public void defaultSelectedValue_SizeNegativeOne() {
        defaultSelectedValue("-1", false);
    }

    @Test
    @Alerts({"0", "false", "false", "false", "-1"})
    public void defaultSelectedValue_SizeNegativeOne_Multi() {
        defaultSelectedValue("-1", true);
    }

    @Test
    @Alerts({"0", "true", "false", "false", "0"})
    public void defaultSelectedValue_SizeZero() {
        defaultSelectedValue("0", false);
    }

    @Test
    @Alerts({"0", "false", "false", "false", "-1"})
    public void defaultSelectedValue_SizeZero_Multi() {
        defaultSelectedValue("0", true);
    }

    @Test
    @Alerts({"1", "true", "false", "false", "0"})
    public void defaultSelectedValue_SizeOne() {
        defaultSelectedValue("1", false);
    }

    @Test
    @Alerts({"1", "false", "false", "false", "-1"})
    public void defaultSelectedValue_SizeOne_Multi() {
        defaultSelectedValue("1", true);
    }

    @Test
    @Alerts({"2", "false", "false", "false", "-1"})
    public void defaultSelectedValue_SizeTwo() {
        defaultSelectedValue("2", false);
    }

    @Test
    @Alerts({"2", "false", "false", "false", "-1"})
    public void defaultSelectedValue_SizeTwo_Multi() {
        defaultSelectedValue("2", true);
    }

    @Test
    @Alerts({"0", "true", "false", "false", "0"})
    public void defaultSelectedValue_SizeInvalid() {
        defaultSelectedValue("x", false);
    }

    @Test
    @Alerts({"0", "false", "false", "false", "-1"})
    public void defaultSelectedValue_SizeInvalid_Mulzi() {
        defaultSelectedValue("x", true);
    }

    /**
     * Tests default selection status for options in a select of the specified size, optionally
     * allowing multiple selections.
     *
     * @param size     the select input's size attribute
     * @param multiple whether the select input should allow multiple selections
     */
    private void defaultSelectedValue(final String size, final boolean multiple) {

        final String html =
                "<html>\n"
                        + "<body onload='test()'>\n"
                        + "<script>\n"                        + "   function test() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("5")
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "false", "false"})
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

        checkHtmlAlert(html);
    }

    @Test
    public void deselectMultiple() {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "  <form name='f'>\n"
                        + "    <select name='s1' multiple>\n"
                        + "      <option name='option1'>One</option>\n"
                        + "      <option id='it' name='option2' selected='true'>Two</option>\n"
                        + "      <option name='option3'>Three</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";


        final HTMLDocument document = loadHtml(html);
        HTMLOptionElementImpl firstOption = (HTMLOptionElementImpl) document.getElementById("it");
        assertTrue(firstOption.isSelected());
        firstOption.click();
        assertFalse(firstOption.isSelected());
    }

    @Test
    @Alerts({"0", "1"})
    public void selectedIndexonfocus() {
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"o1", "o2"})
    public void valueOnfocus() {
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"-1", "0", "-1"})
    public void selectedIndexappendChild() {
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"-1", "0", "-1"})
    public void selectedIndexinsertBefore() {
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "0"})
    public void selectedIndexinsertBeforeExisting() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                		+ "  function test() {\n"
                        + "    var s = document.getElementById('mySelect');\n"
                        + "    var o1 = document.getElementById('option1');\n"
                        + "    var o = document.createElement('option');\n"
                        + "    alert(s.selectedIndex);\n"

                        + "    s.insertBefore(o, o1);\n"
                        + "    alert(s.selectedIndex);\n"

                        + "    s.removeChild(o1);\n"
                        + "    alert(s.selectedIndex);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <select id='mySelect'>\n"
                        + "    <option id='option1'>option1</option>"
                        + "  </select>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"-1", "0", "-1"})
    public void selectedIndexadd() {
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"first", "null", "null"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"two", ""})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"two", "one"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"two", ""})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"two", "One"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"two", "One"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"two", ""})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"two", "", ""})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"One", "Two", "One"})
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("mouse over")
    public void mouseOver() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    function doTest() {\n"
                        + "      document.title += 'mouse over';\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <form id='form1'>\n"
                        + "    <select name='select1' id='select1' size='4' onmouseover='doTest()'>\n"
                        + "      <option value='option1' id='option1' >Option1</option>\n"
                        + "      <option value='option2' id='option2'>Option2</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl element = (HTMLElementImpl) document.getElementById("select1");
        element.getOnmouseover();
    }

    @Test
    @Alerts("mouse over")
    public void mouseOverDisabledSelect() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    function doTest() {\n"
                        + "      document.title += 'mouse over';\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <form id='form1'>\n"
                        + "    <select name='select1' id='select1' size='4' onmouseover='doTest()' disabled='disabled'>\n"
                        + "      <option value='option1' id='option1'>Option1</option>\n"
                        + "      <option value='option2' id='option2'>Option2</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl element = (HTMLElementImpl) document.getElementById("select1");
        element.getOnmouseover();
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
                        + "  <select id='e1'>e 1</select><br>\n"
                        + "  <label>something <label> click here <select id='e2'>e 2</select></label></label><br>\n"
                        + "  <label for='e3'> and here</label>\n"
                        + "  <select id='e3'>e 3</select><br>\n"
                        + "  <label id='l4' for='e4'> what about</label>\n"
                        + "  <label> this<select id='e4'>e 4</select></label><br>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "false", "true", "false", "false"})
    public void in() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "  function doTest() {\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "[object HTMLOptionElement]", "2"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "[object HTMLOptionElement]", "8"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLFormElement]")
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

        checkHtmlAlert(html);
    }

    @Test
    public void deselectFromMultiple() {
        final String html
                = "<html><body>\n"
                + "<select id='s' multiple>\n"
                + "  <option selected value='one'>One</option>\n"
                + "  <option value='two'>Two</option>\n"
                + "  <option selected value='three'>Three</option>\n"
                + "</select>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLSelectElementImpl elem = (HTMLSelectElementImpl) document.getElementById("s");
        HTMLOptionsCollection options = elem.getOptions();

        HTMLOptionElement first = ((HTMLOptionElement) options.item(0));
        HTMLOptionElement second = ((HTMLOptionElement) options.item(2));
        assertTrue(first.isSelected());
        first.click();
        assertFalse(first.isSelected());
        assertTrue(second.isSelected());

    }

    @Test
    public void optionClick() {
        final String html
                = "<html><body>\n"
                + "<select id='s' multiple>\n"
                + "  <option selected value='one'>One</option>\n"
                + "  <option value='two'>Two</option>\n"
                + "  <option selected value='three'>Three</option>\n"
                + "</select>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLSelectElementImpl elem = (HTMLSelectElementImpl) document.getElementById("s");
        HTMLOptionsCollection options = elem.getOptions();

        HTMLOptionElement first = ((HTMLOptionElement) options.item(0));
        HTMLOptionElement second = ((HTMLOptionElement) options.item(2));
        assertTrue(first.isSelected());
        first.click();
        assertFalse(first.isSelected());
        assertTrue(second.isSelected());
    }

    @Test
    public void optionClickActions() {
        final String html
                = "<html><body>\n"
                + "<select id='s' multiple>\n"
                + "  <option selected value='one'>One</option>\n"
                + "  <option value='two'>Two</option>\n"
                + "  <option selected value='three'>Three</option>\n"
                + "</select>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLSelectElementImpl elem = (HTMLSelectElementImpl) document.getElementById("s");
        HTMLOptionsCollection options = elem.getOptions();

        assertTrue(((HTMLOptionElement) options.item(0)).isSelected());
        assertFalse(((HTMLOptionElement) options.item(1)).isSelected());
        assertTrue(((HTMLOptionElement) options.item(2)).isSelected());

        ((HTMLElementImpl) options.item(0)).getOnclick();

        assertTrue(((HTMLOptionElement) options.item(0)).isSelected());
        assertFalse(((HTMLOptionElement) options.item(1)).isSelected());
        assertFalse(((HTMLOptionElement) options.item(2)).isSelected());
    }

    @Test
    @Alerts({"[object HTMLOptionElement]", "[object HTMLOptionElement]", "[object HTMLOptionElement]"})
    public void optionsForOf() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                		+ "  function test() {\n"
                        + "    var s = document.getElementById('s');\n"
                        + "    for (var opt of s) {\n"
                        + "      alert(opt);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<select id='s' multiple>\n"
                        + "  <option selected value='one'>One</option>\n"
                        + "  <option value='two'>Two</option>\n"
                        + "  <option selected value='three'>Three</option>\n"
                        + "</select>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
