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
import org.loboevolution.html.dom.HTMLButtonElement;

/**
 * Tests for {@link HTMLButtonElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLButtonElementTest extends LoboUnitTest {

    @Test
    @Alerts({"null", "A", "a", "A", "a8", "8Afoo", "8", "@"})
    public void readWriteAccessKey() {
        final String html
                = "<html><body>\n"
                + "<button id='a1'>a1</button><button id='a2' accesskey='A'>a2</button>\n"
                + "    <script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = 'a';\n"
                + "a2.accessKey = 'A';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = 'a8';\n"
                + "a2.accessKey = '8Afoo';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = '8';\n"
                + "a2.accessKey = '@';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Tests setting the <tt>type</tt> property.
     */
    @Test
    @Alerts({"submit", "button", "submit"})
    public void type() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var b = document.createElement('button');\n"
                + "    alert(b.type);\n"
                + "    try {\n"
                + "      b.type = 'button';\n"
                + "    } catch(e) {alert('exception')}\n"
                + "    alert(b.type);\n"
                + "    b.removeAttribute('type');\n"
                + "    alert(b.type);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"submit", "submit", "submit", "submit", "reset", "button", "submit"})
    public void getType() {
        final String html = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myNone').type);\n"
                + "    alert(document.getElementById('myEmpty').type);\n"
                + "    alert(document.getElementById('mySubmit').type);\n"
                + "    alert(document.getElementById('mySubmitTrim').type);\n"
                + "    alert(document.getElementById('myReset').type);\n"
                + "    alert(document.getElementById('myButton').type);\n"
                + "    alert(document.getElementById('myUnknown').type);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <p>hello world</p>\n"
                + "  <form id='myForm' action='" + URL_SECOND + "'>\n"
                + "    <button id='myNone'></button>\n"
                + "    <button type='' id='myEmpty'></button>\n"
                + "    <button type='submit' id='mySubmit'></button>\n"
                + "    <button type=' Submit\t' id='mySubmitTrim'></button>\n"
                + "    <button type='reSet' id='myReset'></button>\n"
                + "    <button type='button' id='myButton'></button>\n"
                + "    <button type='unknown' id='myUnknown'></button>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myFormId", "null", "null", "null", "null", "myFormId", "null", "myForm2Id", "myForm2Id"})
    public void getForm() {
        final String html = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "  function show(id) {\n"
                + "    elem = document.getElementById(id);\n"
                + "    if (elem.form) {\n"
                + "      alert(elem.form.id);\n"
                + "    } else {\n"
                + "      alert(elem.form);\n"
                + "    }\n"
                + "  }\n"
                + "  function test() {\n"
                + "    show('myNone');\n"
                + "    show('myEmptyInside');\n"
                + "    show('myEmptyOutside');\n"
                + "    show('myFormTrim');\n"
                + "    show('myFormCase');\n"
                + "    show('myOutside');\n"
                + "    show('myUnknown');\n"
                + "    show('myFormOther');\n"
                + "    show('myFormOtherOutside');\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <p>hello world</p>\n"
                + "  <form id='myFormId' action='" + URL_SECOND + "'>\n"
                + "    <button id='myNone'></button>\n"
                + "    <button form='' id='myEmptyInside'></button>\n"
                + "    <button form='myFormId' id='myForm'></button>\n"
                + "    <button form=' myFormId\t' id='myFormTrim'></button>\n"
                + "    <button form='myformid' id='myFormCase'></button>\n"
                + "    <button form='unknown' id='myUnknown'></button>\n"
                + "    <button form='myForm2Id' id='myFormOther'></button>\n"
                + "  </form>\n"
                + "  <form id='myForm2Id' action='" + URL_SECOND + "'>\n"
                + "  </form>\n"
                + "  <button form='myFormId' id='myOutside'></button>\n"
                + "  <button form='' id='myEmptyOutside'></button>\n"
                + "  <button form='myForm2Id' id='myFormOtherOutside'></button>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"test", "4", "42", "2", "[object HTMLButtonElement]", "26"})
    public void getAttributeAndSetValue() {
        final String html =
                "<html>\n"
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
                        + "    <button id='t'>abc</button>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "4", "null", "4"})
    public void getAttributeAndSetValueNull() {
        final String html =
                "<html>\n"
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
                        + "    <button id='t'>abc</button>\n"
                        + "  </body>\n"
                        + "</html>";

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
                        + "  <button id='e1'>e 1</button><br>\n"
                        + "  <label>something <label> click here <button id='e2'>e 2</button></label></label><br>\n"
                        + "  <label for='e3'> and here</label>\n"
                        + "  <button id='e3'>e 3</button><br>\n"
                        + "  <label id='l4' for='e4'> what about</label>\n"
                        + "  <label> this<button id='e4'>e 4</button></label><br>\n"
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
                + "    <button id='a'>button</button><br>\n"
                + "  </form>"
                + "  <script>\n"
                + "    alert(document.getElementById('a').form);\n"
                + "  </script>"
                + "</body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("mouse over [btn]")
    public void mouseOver() {
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
                        + "      alert(msg);\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <form id='form1'>\n"
                        + "    <button id='btn' onmouseover='dumpEvent(event);'>button</button><br>\n"
                        + "  </form>\n"
                        + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Test:mouse over [disabledBtn]")
    public void mouseOverDiabled() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <title>Test:</title>\n"
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
                        + "      document.title += msg;\n"
                        + "    }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "<body>\n"
                        + "  <form id='form1'>\n"
                        + "    <button id='disabledBtn' onmouseover='dumpEvent(event);' disabled>disabled button</button><br>\n"
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
                + "    var b = document.createElement('button');\n"
                + "    alert(b.formNoValidate);\n"
                + "    b.formNoValidate = '';\n"
                + "    alert(b.formNoValidate);\n"
                + "    b.formNoValidate = 'yes';\n"
                + "    alert(b.formNoValidate);\n"
                + "    b.removeAttribute('formNoValidate');\n"
                + "    alert(b.formNoValidate);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
