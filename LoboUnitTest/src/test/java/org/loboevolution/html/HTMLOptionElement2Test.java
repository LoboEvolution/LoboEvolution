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
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * Tests for {@link HTMLOptionElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLOptionElement2Test extends LoboUnitTest {

    @Test
    @Alerts("SELECT;")
    public void clickSelect() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function alert(x) {\n"
                        + "    document.getElementById('log_').value += x + '; ';\n"
                        + "  }\n"

                        + "  function init() {\n"
                        + "    var s = document.getElementById('s');\n"
                        + "    s.addEventListener('click', handle, false);\n"
                        + "  }\n"

                        + "  function handle(event) {\n"
                        + "    if (event.target) {\n"
                        + "      alert(event.target.nodeName);\n"
                        + "    } else {\n"
                        + "      alert(event.srcElement.nodeName);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "<body onload='init()'>\n"
                        + "<form>\n"
                        + "  <textarea id='log_' rows='4' cols='50'></textarea>\n"
                        + "  <select id='s' size='7'>\n"
                        + "    <option value='opt-a'>A</option>\n"
                        + "    <option id='opt-b' value='b'>B</option>\n"
                        + "    <option value='opt-c'>C</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body>"
                        + " <script>"
                        + "  document.getElementById('s').click();"
                        + "  </script>"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("opt-a; opt-b")
    public void click2() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function alert(x) {\n"
                        + "    document.getElementById('log_').value += x + '; ';\n"
                        + "  }\n"

                        + "  function init() {\n"
                        + "    s = document.getElementById('s');\n"
                        + "    s.addEventListener('click', handle, false);\n"
                        + "  }\n"

                        + "  function handle(event) {\n"
                        + "    alert(s.options[s.selectedIndex].value);\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "<body onload='init()'>\n"
                        + "<form>\n"
                        + "  <textarea id='log_' rows='4' cols='50'></textarea>\n"
                        + "  <select id='s'>\n"
                        + "    <option value='opt-a'>A</option>\n"
                        + "    <option id='opt-b' value='b'>B</option>\n"
                        + "    <option value='opt-c'>C</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body>"
                        + " <script>"
                        + "  document.getElementById('s').click();"
                        + "  document.getElementById('opt-b').click();"
                        + "  </script>"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("onchange-select; onclick-option; onclick-select;")
    public void clickOptionEventSequence1() {
        final String html = "<html>"
                + "<head>\n"
                + "<script>\n"
                + "  function alert(x) {\n"
                + "    document.getElementById('log_').value += x + '; ';\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body>\n"
                + "<form>\n"
                + "  <textarea id='log_' rows='4' cols='50'></textarea>\n"
                + "  <select id='s' size='2' onclick=\"alert('onclick-select')\""
                + " onchange=\"alert('onchange-select')\">\n"
                + "    <option id='clickId' value='a' onclick=\"alert('onclick-option')\""
                + " onchange=\"alert('onchange-option')\">A</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body>"
                + " <script>"
                + "  document.getElementById('s').click();"
                + "  </script>"
                + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("change-SELECT; click-OPTION; click-OPTION;")
    public void clickOptionEventSequence2() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"                        + "  function alert(x) {\n"
                        + "    document.getElementById('log_').value += x + '; ';\n"
                        + "  }\n"

                        + "  function init() {\n"
                        + "    var s = document.getElementById('s');\n"
                        + "    var o = document.getElementById('clickId');\n"
                        + "    s.addEventListener('click', handle, false);\n"
                        + "    s.addEventListener('change', handle, false);\n"
                        + "    o.addEventListener('click', handle, false);\n"
                        + "    o.addEventListener('change', handle, false);\n"
                        + "  }\n"

                        + "  function handle(event) {\n"
                        + "    if (event.target) {\n"
                        + "      alert(event.type + '-' + event.target.nodeName);\n"
                        + "    } else {\n"
                        + "      alert(event.type + '-' + event.srcElement.nodeName);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "<body onload='init()'>\n"
                        + "<form>\n"
                        + "  <textarea id='log_' rows='4' cols='50'></textarea>\n"
                        + "  <select id='s' size='2' >\n"
                        + "    <option id='clickId' value='a' >A</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body>"
                        + " <script>"
                        + "  document.getElementById('clickId').click();"
                        + "  </script>"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("onchange-select; change-SELECT; onclick-option; click-OPTION; onclick-select; click-OPTION;")
    public void clickOptionEventSequence3() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "  function alert(x) {\n"
                        + "    document.getElementById('log_').value += x + '; ';\n"
                        + "  }\n"
                        + "  function init() {\n"
                        + "    var s = document.getElementById('s');\n"
                        + "    var o = document.getElementById('clickId');\n"
                        + "    s.addEventListener('click', handle, false);\n"
                        + "    s.addEventListener('change', handle, false);\n"
                        + "    o.addEventListener('click', handle, false);\n"
                        + "    o.addEventListener('change', handle, false);\n"
                        + "  }\n"
                        + "  function handle(event) {\n"
                        + "    if (event.target) {\n"
                        + "      alert(event.type + '-' + event.target.nodeName);\n"
                        + "    } else {\n"
                        + "      alert(event.type + '-' + event.srcElement.nodeName);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='init()'>\n"
                        + "<form>\n"
                        + "  <textarea id='log_' rows='4' cols='50'></textarea>\n"
                        + "  <select id='s' size='2' onclick=\"alert('onclick-select')\""
                        + " onchange=\"alert('onchange-select')\">\n"
                        + "    <option id='clickId' value='a' onclick=\"alert('onclick-option')\""
                        + " onchange=\"alert('onchange-option')\">A</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body>"
                        + " <script>"
                        + "  document.getElementById('clickId').click();"
                        + "  </script>"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "option1", "0"})
    public void unselectResetToFirstOption() {
        final String html =
                "<html><head><script>\n"
                        + "function doTest() {\n"
                        + "  var sel = document.form1.select1;\n"
                        + "  alert(sel.selectedIndex);\n"
                        + "  sel.options[1].selected = false;\n"
                        + "  alert(sel.value);\n"
                        + "  alert(sel.selectedIndex);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1'>\n"
                        + "    <option value='option1' name='option1'>One</option>\n"
                        + "    <option value='option2' name='option2' selected>Two</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "null", "-1"})
    public void unselectResetToFirstOption1() {
        final String html =
                "<html><head>"
                        +"<script>\n"
                        + "function doTest() {\n"
                        + "  var sel = document.form1.select1;\n"
                        + "  alert(sel.selectedIndex);\n"
                        + "  sel.options[1].selected = false;\n"
                        + "  alert(sel.value);\n"
                        + "  alert(sel.selectedIndex);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1' size='4'>\n"
                        + "    <option value='option1' name='option1'>One</option>\n"
                        + "    <option value='option2' name='option2' selected>Two</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void selectFromJSTriggersNoFocusEvent() {
        final String html =
                "<html><head><script>\n"

                        + "function doTest() {\n"
                        + "  var sel = document.form1.select1;\n"
                        + "  sel.options[1].selected = true;\n"
                        + "  alert(sel.selectedIndex);\n"
                        + "}\n"
                        + "</script></head><body onload='doTest()'>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1' onfocus='alert(\"focus\")'>\n"
                        + "    <option value='option1' name='option1'>One</option>\n"
                        + "    <option value='option2' name='option2'>Two</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true", "true", "false", "true"})
    public void disabledAttribute() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var test1 = document.getElementById('test1');\n"
                        + "        alert(test1.disabled);\n"
                        + "        test1.disabled = true;\n"
                        + "        alert(test1.disabled);\n"
                        + "        test1.disabled = true;\n"
                        + "        alert(test1.disabled);\n"
                        + "        test1.disabled = false;\n"
                        + "        alert(test1.disabled);\n"

                        + "        var test2 = document.getElementById('test2');\n"
                        + "        alert(test2.disabled);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <form name='form1'>\n"
                        + "      <select>\n"
                        + "        <option id='test1' value='option1'>Option1</option>\n"
                        + "        <option id='test2' value='option2' disabled>Option2</option>\n"
                        + "      </select>\n"
                        + "  </form>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"some text", "some value", "false", "some other text", "some other value", "true"})
    public void readPropsBeforeAdding() {
        final String html =
                "<html><head><script>\n"

                        + "function doTest() {\n"
                        + "  var oOption = new Option('some text', 'some value');\n"
                        + "  alert(oOption.text);\n"
                        + "  alert(oOption.value);\n"
                        + "  alert(oOption.selected);\n"
                        + "  oOption.text = 'some other text';\n"
                        + "  oOption.value = 'some other value';\n"
                        + "  oOption.selected = true;\n"
                        + "  alert(oOption.text);\n"
                        + "  alert(oOption.value);\n"
                        + "  alert(oOption.selected);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<p>hello world</p>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void selectingOrphanedOptionCreatedByDocument() {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "<form name='myform'/>\n"
                        + "<script>\n"                        + "var select = document.createElement('select');\n"
                        + "var opt = document.createElement('option');\n"
                        + "opt.value = 'x';\n"
                        + "opt.selected = true;\n"
                        + "select.appendChild(opt);\n"
                        + "document.myform.appendChild(select);\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "2"})
    public void setSelected() {
        final String html =
                "<html><head><script>\n"

                        + "function doTest() {\n"
                        + "  var sel = document.form1.select1;\n"
                        + "  alert(sel.selectedIndex);\n"
                        + "  sel.options[0].selected = false;\n"
                        + "  alert(sel.selectedIndex);\n"
                        + "}</script></head><body onload='doTest()'>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1' onchange='this.form.submit()'>\n"
                        + "    <option value='option1' name='option1'>One</option>\n"
                        + "    <option value='option2' name='option2'>Two</option>\n"
                        + "    <option value='option3' name='option3' selected>Three</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    public void setAttribute() {
        final String html =
                "<html>\n"
                        + "<head><title>foo</title>\n"
                        + "<script>\n"                        + "  function doTest() {\n"
                        + "    document.getElementById('option1').setAttribute('class', 'bla bla');\n"
                        + "    var o = new Option('some text', 'some value');\n"
                        + "    o.setAttribute('class', 'myClass');\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1'>\n"
                        + "      <option value='option1' id='option1' name='option1'>One</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined"})
    public void optionIndexOutOfBound() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"                        + "function doTest() {\n"
                        + "  var options = document.getElementById('testSelect').options;\n"
                        + "  alert(options[55]);\n"
                        + "  try {\n"
                        + "    alert(options[-55]);\n"
                        + "  } catch (e) { alert('exception'); }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <form name='form1'>\n"
                        + "    <select name='select1' id='testSelect'>\n"
                        + "      <option value='option1' name='option1'>One</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"o2: text: Option 2, label: Option 2, value: 2, defaultSelected: false, selected: false",
            "o3: text: Option 3, label: Option 3, value: 3, defaultSelected: true, selected: false",
            "0", "1"})
    public void constructor() {
        final String html =
                "<html><head><script>\n"

                        + "function dumpOption(_o) {\n"
                        + "  return 'text: ' + _o.text\n"
                        + " + ', label: ' + _o.label\n"
                        + " + ', value: ' + _o.value\n"
                        + " + ', defaultSelected: ' + _o.defaultSelected\n"
                        + " + ', selected: ' + _o.selected;\n"
                        + "}\n"
                        + "function doTest() {\n"
                        + "  var o2 = new Option('Option 2', '2');\n"
                        + "  alert('o2: ' + dumpOption(o2));\n"
                        + "  var o3 = new Option('Option 3', '3', true, false);\n"
                        + "  alert('o3: ' + dumpOption(o3));\n"
                        + "  document.form1.select1.appendChild(o3);\n"
                        + "  alert(document.form1.select1.options.selectedIndex);\n"
                        + "  document.form1.reset();\n"
                        + "  alert(document.form1.select1.options.selectedIndex);\n"
                        + "}\n"
                        + "</script></head><body onload='doTest()'>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1' id='testSelect'>\n"
                        + "    <option value='option1' name='option1'>One</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void insideBold() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var sel = document.form1.select1;\n"
                        + "  sel.options[0] = null;\n"
                        + "  alert(sel.options.length);\n"
                        + "}</script></head><body onload='test()'>\n"
                        + "<form name='form1'>\n"
                        + "  <b>\n"
                        + "    <select name='select1'>\n"
                        + "      <option>One</option>\n"
                        + "    </select>\n"
                        + "  </b>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "[object Attr]", "null", "null", "null",
            "null", "null", "null", "null", "null"})
    public void getAttributeNode() {
        final String html =
                "<html><head><script>\n"

                        + "function doTest() {\n"
                        + "  var s = document.getElementById('testSelect');\n"
                        + "  var o1 = s.options[0];\n"
                        + "  alert(o1.getAttributeNode('id'));\n"
                        + "  alert(o1.getAttributeNode('name'));\n"
                        + "  alert(o1.getAttributeNode('value'));\n"
                        + "  alert(o1.getAttributeNode('selected'));\n"
                        + "  alert(o1.getAttributeNode('foo'));\n"
                        + "  var o2 = s.options[1];\n"
                        + "  alert(o2.getAttributeNode('id'));\n"
                        + "  alert(o2.getAttributeNode('name'));\n"
                        + "  alert(o2.getAttributeNode('value'));\n"
                        + "  alert(o2.getAttributeNode('selected'));\n"
                        + "  alert(o2.getAttributeNode('foo'));\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='doTest()'>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1' id='testSelect'>\n"
                        + "    <option name='option1'>One</option>\n"
                        + "    <option>Two</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false null null", "false null null", "true *selected selected",
            "true null null", "false null null", "false *selected selected",
            "false null null", "true null null", "false *selected selected",
            "true null null", "false null null", "false *selected selected"})
    public void selectedAttribute() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "  function info(opt) {\n"
                        + "    var attrNode = opt.getAttributeNode('selected');\n"
                        + "    if (attrNode) { attrNode = '*' + attrNode.value; }\n"
                        + "    alert(opt.selected + ' ' + attrNode + ' ' + opt.getAttribute('selected'));\n"
                        + "  }\n"

                        + "  function doTest() {\n"
                        + "    var s = document.getElementById('testSelect');\n"

                        + "    var o1 = s.options[0];\n"
                        + "    var o2 = s.options[1];\n"
                        + "    var o3 = s.options[2];\n"

                        + "    info(o1);info(o2);info(o3);\n"

                        + "    o1.selected = true;\n"
                        + "    info(o1);info(o2);info(o3);\n"

                        + "    o2.selected = true;\n"
                        + "    info(o1);info(o2);info(o3);\n"

                        + "    o2.selected = false;\n"
                        + "    info(o1);info(o2);info(o3);\n"

                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='doTest()'>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1' id='testSelect'>\n"
                        + "    <option>One</option>\n"
                        + "    <option>Two</option>\n"
                        + "    <option selected='selected'>Three</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false null null", "false null null", "true *selected selected",
            "true null null", "false null null", "true *selected selected",
            "true null null", "true null null", "true *selected selected",
            "true null null", "false null null", "true *selected selected"})
    public void selectedAttributeMultiple() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "  function info(opt) {\n"
                        + "    var attrNode = opt.getAttributeNode('selected');\n"
                        + "    if (attrNode) { attrNode = '*' + attrNode.value; }\n"
                        + "    alert(opt.selected + ' ' + attrNode + ' ' + opt.getAttribute('selected'));\n"
                        + "  }\n"

                        + "  function doTest() {\n"
                        + "    var s = document.getElementById('testSelect');\n"

                        + "    var o1 = s.options[0];\n"
                        + "    var o2 = s.options[1];\n"
                        + "    var o3 = s.options[2];\n"

                        + "    info(o1);info(o2);info(o3);\n"

                        + "    o1.selected = true;\n"
                        + "    info(o1);info(o2);info(o3);\n"

                        + "    o2.selected = true;\n"
                        + "    info(o1);info(o2);info(o3);\n"

                        + "    o2.selected = false;\n"
                        + "    info(o1);info(o2);info(o3);\n"

                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='doTest()'>\n"
                        + "<form name='form1'>\n"
                        + "  <select name='select1' id='testSelect' multiple>\n"
                        + "    <option>One</option>\n"
                        + "    <option>Two</option>\n"
                        + "    <option selected='selected'>Three</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLOptionsCollection]", "0", "1"})
    public void withNew() {
        final String html =
                "<html><head><script>\n"

                        + "function doTest() {\n"
                        + "  var s = document.getElementById('testSelect');\n"
                        + "  alert(s.options);\n"
                        + "  alert(s.length);\n"
                        + "  try {\n"
                        + "    s.options[0] = new Option('one', 'two');\n"
                        + "  } catch (e) { alert(e) }\n"
                        + "  alert(s.length);\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <select id='testSelect'>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLOptionsCollection]", "0", "exception", "0"})
    public void withoutNew() {
        final String html =
                "<html><head><script>\n"
                        + "function doTest() {\n"
                        + "  var s = document.getElementById('testSelect');\n"
                        + "  alert(s.options);\n"
                        + "  alert(s.length);\n"
                        + "  try {\n"
                        + "    s.options[0] = Option('one', 'two');\n"
                        + "  } catch (e) { alert('exception') }\n"
                        + "  alert(s.length);\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <select id='testSelect'>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text1", "New Text1", "", "New Text2", "text3", "New Text3", "text4", "New Text4"})
    public void text() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var option = document.getElementsByTagName('option')[0];\n"
                        + "        alert(option.text);\n"
                        + "        option.text = 'New Text1';\n"
                        + "        alert(option.text);\n"

                        + "        option = document.getElementsByTagName('option')[1];\n"
                        + "        alert(option.text);\n"
                        + "        option.text = 'New Text2';\n"
                        + "        alert(option.text);\n"

                        + "        option = document.getElementsByTagName('option')[2];\n"
                        + "        alert(option.text);\n"
                        + "        option.text = 'New Text3';\n"
                        + "        alert(option.text);\n"

                        + "        option = document.getElementsByTagName('option')[3];\n"
                        + "        alert(option.text);\n"
                        + "        option.text = 'New Text4';\n"
                        + "        alert(option.text);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <select>\n"
                        + "      <option value='value1' label='label1'>text1</option>\n"
                        + "      <option value='value2' label='label2'></option>\n"
                        + "      <option value='value3' label=''>text3</option>\n"
                        + "      <option value='value4' >text4</option>\n"
                        + "    </select>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "[object Text]", "null"})
    public void setText() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var s = document.getElementById('testSelect');\n"
                        + "  var lastIndex = s.length;\n"
                        + "  s.length += 1;\n"
                        + "  alert(s[lastIndex].firstChild);\n"
                        + "  s[lastIndex].text  = 'text2';\n"
                        + "  alert(s[lastIndex].firstChild);\n"
                        + "  s[lastIndex].text  = '';\n"
                        + "  alert(s[lastIndex].firstChild);\n"
                        + "}\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <select id='testSelect'>\n"
                        + "    <option value='value1' label='label1'>text1</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text1", "text1b", "text2"})
    public void textWhenNotDisplayed() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var s = document.getElementById('testSelect1');\n"
                        + "  alert(s.options[0].text);\n"
                        + "  alert(s.options[1].text);\n"
                        + "  var s2 = document.getElementById('testSelect2');\n"
                        + "  alert(s2.options[0].text);\n"
                        + "}\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <div style='display: none'>\n"
                        + "  <select id='testSelect1'>\n"
                        + "    <option>text1</option>\n"
                        + "    <option><strong>text1b</strong></option>\n"
                        + "  </select>\n"
                        + "  </div>\n"
                        + "  <div style='visibility: hidden'>\n"
                        + "  <select id='testSelect2'>\n"
                        + "    <option>text2</option>\n"
                        + "  </select>\n"
                        + "  </div>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text0", "text1", "text1b", "text2"})
    public void defaultValueFromNestedNodes() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var s0 = document.getElementById('testSelect0');\n"
                        + "  alert(s0.options[0].value);\n"
                        + "  var s = document.getElementById('testSelect1');\n"
                        + "  alert(s.options[0].value);\n"
                        + "  alert(s.options[1].value);\n"
                        + "  var s2 = document.getElementById('testSelect2');\n"
                        + "  alert(s2.options[0].value);\n"
                        + "}\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <select id='testSelect0'>\n"
                        + "    <option>text0</option>\n"
                        + "  </select>\n"
                        + "  <div style='display: none'>\n"
                        + "  <select id='testSelect1'>\n"
                        + "    <option>text1</option>\n"
                        + "    <option><strong>text1b</strong></option>\n"
                        + "  </select>\n"
                        + "  </div>\n"
                        + "  <div style='visibility: hidden'>\n"
                        + "  <select id='testSelect2'>\n"
                        + "    <option>text2</option>\n"
                        + "  </select>\n"
                        + "  </div>\n"
                        + "</form>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLFormElement]")
    public void form() {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "  <form>\n"
                        + "    <select id='s'>\n"
                        + "      <option>a</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "  <script>\n"

                        + "    alert(document.getElementById('s').options[0].form);\n"
                        + "  </script>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"o2",
            "InvalidStateError: Failed to execute 'iterateNext' on 'XPathResult': "
                    + "The document has mutated since the result was returned.",
            "1", "0", "o2",
            "InvalidStateError: Failed to execute 'iterateNext' on 'XPathResult': "
                    + "The document has mutated since the result was returned."})
    public void xpathSelected() {
        final String selectionChangeCode = "    sel.options[1].selected = false;\n";

        xpathSelected(selectionChangeCode, false);
    }

    @Test
    @Alerts({"o2",
            "InvalidStateError: Failed to execute 'iterateNext' on 'XPathResult': "
                    + "The document has mutated since the result was returned.",
            "1", "1", "o2",
            "InvalidStateError: Failed to execute 'iterateNext' on 'XPathResult': "
                    + "The document has mutated since the result was returned."})
    public void xpathSelectedSetAttribute() {
        final String selectionChangeCode = "    sel.options[1].setAttribute('selected', false);\n";

        xpathSelected(selectionChangeCode, false);
    }

    @Test
    @Alerts({"o2",
            "InvalidStateError: Failed to execute 'iterateNext' on 'XPathResult': "
                    + "The document has mutated since the result was returned.",
            "1", "-1", "o2",
            "InvalidStateError: Failed to execute 'iterateNext' on 'XPathResult': "
                    + "The document has mutated since the result was returned."})
    public void xpathSelectedMultiple() {
        final String selectionChangeCode = "    sel.options[1].selected = false;\n";

        xpathSelected(selectionChangeCode, true);
    }

    @Test
    @Alerts({"o2",
            "InvalidStateError: Failed to execute 'iterateNext' on 'XPathResult': "
                    + "The document has mutated since the result was returned.",
            "1", "1", "o2",
            "InvalidStateError: Failed to execute 'iterateNext' on 'XPathResult': "
                    + "The document has mutated since the result was returned."})
    public void xpathSelectedSetAttributeMultiple() {
        final String selectionChangeCode = "    sel.options[1].setAttribute('selected', false);\n";

        xpathSelected(selectionChangeCode, false);
    }

    private void xpathSelected(final String selectionChangeCode, final boolean multiple) {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "    <script>\n"
                        + "  function test() {\n"
                        + "    xpath();\n"
                        + "    var sel = document.getElementById('s1');\n"
                        + "    alert(sel.selectedIndex);\n"
                        + selectionChangeCode
                        + "    alert(sel.selectedIndex);\n"
                        + "    xpath();\n"
                        + "  }\n"

                        + "  function xpath() {\n"
                        + "    if (document.evaluate && XPathResult) {\n"
                        + "      try {\n"
                        + "        var result = document.evaluate('" + "//option[@selected]" + "', document.documentElement, "
                        + "null, XPathResult.ANY_TYPE, null);\n"

                        + "        var thisNode = result.iterateNext();\n"
                        + "        while (thisNode) {\n"
                        + "          alert(thisNode.getAttribute('id'));\n"
                        + "          thisNode = result.iterateNext();\n"
                        + "        }\n"
                        + "      } catch (e) { alert(e); }\n"
                        + "    } else {\n"
                        + "      alert('evaluate not supported');\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form id='form1'>\n"
                        + "    <select id='s1' name='select1' " + (multiple ? "multiple='multiple'" : "") + ">\n"
                        + "      <option id='o1' value='option1'>Option1</option>\n"
                        + "      <option id='o2' value='option2' selected='selected'>Option2</option>\n"
                        + "      <option id='o3'value='option3'>Option3</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"value1", "text1", "label1", "value2", "text2", "text2"})
    public void label() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var s = document.getElementById('testSelect');\n"
                + "  var lastIndex = s.length;\n"
                + "  s.length += 1;\n"
                + "  s[lastIndex].value = 'value2';\n"
                + "  s[lastIndex].text  = 'text2';\n"
                + "  alert(s[0].value);\n"
                + "  alert(s[0].text);\n"
                + "  alert(s[0].label);\n"
                + "  alert(s[1].value);\n"
                + "  alert(s[1].text);\n"
                + "  alert(s[1].label);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <select id='testSelect'>\n"
                + "    <option value='value1' label='label1'>text1</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", "", "", "text2", "text2", "text2", "label2"})
    public void setLabel() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var s = document.getElementById('testSelect');\n"
                        + "  var lastIndex = s.length;\n"
                        + "  s.length += 1;\n"
                        + "  alert(s[1].text);\n"
                        + "  alert(s[1].label);\n"

                        + "  s[lastIndex].value = 'value2';\n"
                        + "  alert(s[1].text);\n"
                        + "  alert(s[1].label);\n"

                        + "  s[lastIndex].text  = 'text2';\n"
                        + "  alert(s[1].text);\n"
                        + "  alert(s[1].label);\n"

                        + "  s[lastIndex].label = 'label2';\n"
                        + "  alert(s[1].text);\n"
                        + "  alert(s[1].label);\n"
                        + "}\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <select id='testSelect'>\n"
                        + "    <option value='value1' label='label1'>text1</option>\n"
                        + "  </select>\n"
                        + "</form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "2", "0"})
    public void index() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "    <script>\n"
                        + "  function test() {\n"
                        + "    var opt = document.getElementById('o1');\n"
                        + "    alert(opt.index);\n"

                        + "    opt = document.getElementById('o2');\n"
                        + "    alert(opt.index);\n"

                        + "    opt = document.getElementById('o3');\n"
                        + "    alert(opt.index);\n"

                        + "    opt = document.createElement('option');\n"
                        + "    alert(opt.index);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form id='form1'>\n"
                        + "    <select id='s1'>\n"
                        + "      <option id='o1' value='option1'>Option1</option>\n"
                        + "      <option id='o2' value='option2' selected='selected'>Option2</option>\n"
                        + "      <option id='o3'value='option3'>Option3</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false-null", "true-selected", "false-null",
            "true-null", "false-selected", "false-null",
            "false-null", "false-selected", "false-null"})
    public void selectAndAttribute() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "    <script>\n"
                        + "  function test() {\n"
                        + "    var s1 = document.getElementById('select1');\n"
                        + "    var o1 = document.getElementById('option1');\n"
                        + "    var o2 = document.getElementById('option2');\n"
                        + "    var o3 = document.getElementById('option3');\n"

                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"
                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"
                        + "    alert(o3.selected + '-' + o3.getAttribute('selected'));\n"

                        + "    o1.selected = true;\n"
                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"
                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"
                        + "    alert(o3.selected + '-' + o3.getAttribute('selected'));\n"

                        + "    s1.selectedIndex = 3;\n"
                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"
                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"
                        + "    alert(o3.selected + '-' + o3.getAttribute('selected'));\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form id='form1'>\n"
                        + "    <select name='select1' id='select1'>\n"
                        + "      <option value='option1' id='option1'>Option1</option>\n"
                        + "      <option value='option2' id='option2' selected='selected'>Option2</option>\n"
                        + "      <option value='option3' id='option3'>Option3</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false-null", "true-true", "true-null",
            "false-selected", "false-null", "true-true"})
    public void setSelectedAttribute() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "<script>\n"
                		+ "  function test() {\n"
                        + "    var o1 = document.getElementById('option1');\n"
                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"

                        + "    o1.setAttribute('selected', true);\n"
                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"

                        + "    o1.removeAttribute('selected');\n"
                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"

                        + "    var o2 = document.getElementById('option2');\n"
                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"

                        + "    o2.removeAttribute('selected');\n"
                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"

                        + "    o2.setAttribute('selected', true);\n"
                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form id='form1'>\n"
                        + "    <select name='select1' id='select1'>\n"
                        + "      <option value='option1' id='option1'>Option1</option>\n"
                        + "      <option value='option2' id='option2' selected='selected'>Option2</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false-null", "true-true", "false-null",
            "false-null", "true-true", "false-null"})
    public void createOption() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "    <script>\n"
                        + "  function test() {\n"
                        + "    var o1 = document.createElement('option');\n"

                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"

                        + "    o1.setAttribute('selected', true);\n"
                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"

                        + "    o1.removeAttribute('selected');\n"
                        + "    alert(o1.selected + '-' + o1.getAttribute('selected'));\n"

                        + "    var s1 = document.getElementById('select1');\n"
                        + "    var o2 = document.createElement('option');\n"
                        + "    s1.appendChild(o2);\n"

                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"

                        + "    o2.setAttribute('selected', true);\n"
                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"

                        + "    o2.removeAttribute('selected');\n"
                        + "    alert(o2.selected + '-' + o2.getAttribute('selected'));\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <form id='form1'>\n"
                        + "    <select name='select1' id='select1'>\n"
                        + "      <option value='option1' id='option1'>Option1</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("o-mouse over [option1]s-mouse over [option1]")
    public void mouseOver() {

        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <title></title>\n"
                        + "    <script>\n"
                        + "    function dumpEvent(event, pre) {\n"
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
                        + "      var msg = pre + '-mouse over';\n"
                        + "      if (eTarget.name) {\n"
                        + "        msg = msg + ' [' + eTarget.name + ']';\n"
                        + "      } else {\n"
                        + "        msg = msg + ' [' + eTarget.id + ']';\n"
                        + "      }\n"
                        + "      document.title += msg;\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <form id='form1'>\n"
                        + "    <select name='select1' id='select1' size='2' onmouseover='dumpEvent(event, \"s\");' >\n"
                        + "      <option value='option1' id='option1' onmouseover='dumpEvent(event, \"o\");' >Option1</option>\n"
                        + "      <option value='option2' id='option2'>Option2</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("option1");
        elem.getOnmouseover();
    }


    @Test
    @Alerts("o-mouse over [option1] s-mouse over [option1]")
    public void mouseOverDisabledSelect() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "    function dumpEvent(event, pre) {\n"
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
                        + "      var msg = pre + '-mouse over';\n"
                        + "      if (eTarget.name) {\n"
                        + "        msg = msg + ' [' + eTarget.name + ']';\n"
                        + "      } else {\n"
                        + "        msg = msg + ' [' + eTarget.id + ']';\n"
                        + "      }\n"
                        + "      document.title += ' ' + msg;\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <form id='form1'>\n"
                        + "    <select name='select1' id='select1' size='2' disabled='disabled' "
                        + "onmouseover='dumpEvent(event, \"s\");' >\n"
                        + "      <option value='option1' id='option1' onmouseover='dumpEvent(event, \"o\");'>Option1</option>\n"
                        + "      <option value='option2' id='option2'>Option2</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("option1");
        elem.getOnmouseover();
    }

    @Test
    @Alerts("o-mouse over [option1] s-mouse over [option1]")
    public void mouseOverDisabledOption() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <title></title>\n"
                        + "    <script>\n"
                        + "    function dumpEvent(event, pre) {\n"
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
                        + "      var msg = pre + '-mouse over';\n"
                        + "      if (eTarget.name) {\n"
                        + "        msg = msg + ' [' + eTarget.name + ']';\n"
                        + "      } else {\n"
                        + "        msg = msg + ' [' + eTarget.id + ']';\n"
                        + "      }\n"
                        + "      if (msg.length == 0) { msg = '-' };\n"
                        + "      document.title += ' ' + msg;\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <form id='form1'>\n"
                        + "    <select name='select1' id='select1' size='2' onmouseover='dumpEvent(event, \"s\");' >\n"
                        + "      <option value='option1' id='option1' onmouseover='dumpEvent(event, \"o\");' "
                        + "disabled='disabled'>Option1</option>\n"
                        + "      <option value='option2' id='option2'>Option2</option>\n"
                        + "    </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("option1");
        elem.getOnmouseover();
    }
}
