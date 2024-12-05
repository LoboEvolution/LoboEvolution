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
import org.loboevolution.html.dom.HTMLTextAreaElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * Tests for {@link HTMLTextAreaElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTextAreaElementTest extends LoboUnitTest {

    @Test
    @Alerts({"1234", "PoohBear"})
    public void getValue() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      alert(document.form1.textarea1.value);\n"
                + "      document.form1.textarea1.value = 'PoohBear';\n"
                + "      alert(document.form1.textarea1.value);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <p>hello world</p>\n"
                + "  <form name='form1' method='post' >\n"
                + "    <textarea name='textarea1' cols='45' rows='4'>1234</textarea>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("foo")
    public void onChange() {
        final String html
                = "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "  <p>hello world</p>\n"
                + "  <form name='form1'>\n"
                + "    <textarea name='textarea1' onchange='alert(this.value)'></textarea>\n"
                + "    <input id='tester' name='myButton' type='button' onclick='document.form1.textarea1.value=\"from button\"'>\n"
                + "  </form>\n"
                + "</body>"
                + " <script>"
                + "  document.getElementById('tester\"').click();"
                + "  </script>"
                + "</html>";
        checkHtmlAlert(html);
    }

    /**
     * Tests that setValue doesn't has side effect. Test for bug 1155063.
     */
    @Test
    @Alerts({"TEXTAREA", "INPUT"})
    public void setValue() {
        final String html
                = "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "  <form name='form1'>\n"
                + "    <textarea name='question'></textarea>\n"
                + "    <input type='button' name='btnsubmit' value='Next'>\n"
                + "  </form>\n"
                + "  <script>\n"
                + "    document.form1.question.value = 'some text';\n"
                + "    alert(document.form1.elements[0].tagName);\n"
                + "    alert(document.form1.elements[1].tagName);\n"
                + "  </script>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"11", "0"})
    public void textLength() {
        final String html
                = "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "  <textarea id='myTextArea'></textarea>\n"
                + "  <script>\n"
                + "    var textarea = document.getElementById('myTextArea');\n"
                + "    textarea.value = 'hello there';\n"
                + "    alert(textarea.textLength);\n"
                + "    textarea.value = '';\n"
                + "    alert(textarea.textLength);\n"
                + "  </script>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0,0", "11,11", "3,11", "3,10", "7,7"})
    public void selection() {
        selection(3, 10);
    }

    @Test
    @Alerts({"0,0", "11,11", "11,11", "11,11", "7,7"})
    public void selectionOutOfBounds() {
        selection(-3, 15);
    }

    @Test
    @Alerts({"0,0", "11,11", "10,11", "5,5", "7,7"})
    public void selectionReverseOrder() {
        selection(10, 5);
    }

    private void selection(final int selectionStart, final int selectionEnd) {
        final String html
                = "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "  <textarea id='myTextArea'></textarea>\n"
                + "  <script>\n"
                + "    var textarea = document.getElementById('myTextArea');\n"
                + "    alert(textarea.selectionStart + ',' + textarea.selectionEnd);\n"
                + "    textarea.value = 'Hello there';\n"
                + "    alert(textarea.selectionStart + ',' + textarea.selectionEnd);\n"
                + "    textarea.selectionStart = " + selectionStart + ";\n"
                + "    alert(textarea.selectionStart + ',' + textarea.selectionEnd);\n"
                + "    textarea.selectionEnd = " + selectionEnd + ";\n"
                + "    alert(textarea.selectionStart + ',' + textarea.selectionEnd);\n"
                + "    textarea.value = 'nothing';\n"
                + "    alert(textarea.selectionStart + ',' + textarea.selectionEnd);\n"
                + "  </script>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("no")
    public void doScroll() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var t = document.getElementById('t');\n"
                        + "        if(t.doScroll) {\n"
                        + "          alert('yes');\n"
                        + "          t.doScroll();\n"
                        + "          t.doScroll('down');\n"
                        + "        } else {\n"
                        + "          alert('no');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'><textarea id='t'>abc</textarea></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    /**
     * Test that the new line immediately following opening tag is ignored.
     */
    @Test
    @Alerts("Hello\\nworld\\n")
    public void valueIgnoreFirstNewLine() {
        value("\nHello\nworld\n");
    }

    @Test
    @Alerts(" \\nHello\\nworld\\n")
    public void valueSpaceBeforeFirstNewLine() {
        value(" \nHello\nworld\n");
    }

    private void value(final String textAreaBody) {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      alert(document.form1.textarea1.value);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <form name='form1' method='post' >\n"
                + "    <textarea name='textarea1'>" + textAreaBody + "</textarea>\n"
                + "    </textarea>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({" foo \\n bar ", " foo \\n bar "})
    public void defaultValue() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var t = document.getElementById('textArea');\n"
                + "      alert(t.defaultValue);\n"
                + "      alert(t.value);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form id='form1'>\n"
                + "    <textarea id='textArea'>\n foo \n bar </textarea>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "false"})
    public void readOnly() {
        final String html
                = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    var t = document.getElementById('textArea');\n"
                + "    alert(t.readOnly);\n"
                + "    t.readOnly = false;\n"
                + "    alert(t.readOnly);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form id='form1'>\n"
                + "    <textarea id='textArea' readonly>\n foo \n bar </textarea>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "A", "a", "A", "a8", "8Afoo", "8", "@"})
    public void accessKey() {
        final String html
                = "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "  <textarea id='a1'>a1</textarea>\n"
                + "  <textarea id='a2' accesskey='A'>a2</textarea>\n"
                + "  <script>\n"
                + "    var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
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
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"20", "5", "8", "4", "20", "20", "20", "3"})
    public void cols() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function setCols(e, value) {\n"
                + "    try {\n"
                + "      e.cols = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <textarea id='a1'>a1</textarea>\n"
                + "  <textarea id='a2' cols='5'>a2</textarea>\n"
                + "  <script>\n"
                + "    var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "    alert(a1.cols);\n"
                + "    alert(a2.cols);\n"
                + "    setCols(a1, '8');\n"
                + "    alert(a1.cols);\n"
                + "    setCols(a1, 4);\n"
                + "    alert(a1.cols);\n"
                + "    setCols(a1, 'a');\n"
                + "    alert(a1.cols);\n"
                + "    setCols(a1, '');\n"
                + "    alert(a1.cols);\n"
                + "    setCols(a1, -1);\n"
                + "    alert(a1.cols);\n"
                + "    setCols(a1, 3.4);\n"
                + "    alert(a1.cols);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "5", "8", "4", "2", "2", "2", "3"})
    public void rows() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function setRows(e, value) {\n"
                + "    try {\n"
                + "      e.rows = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <textarea id='a1'>a1</textarea>\n"
                + "  <textarea id='a2' rows='5'>a2</textarea>\n"
                + "  <script>\n"
                + "    var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "    alert(a1.rows);\n"
                + "    alert(a2.rows);\n"
                + "    setRows(a1, '8');\n"
                + "    alert(a1.rows);\n"
                + "    setRows(a1, 4);\n"
                + "    alert(a1.rows);\n"
                + "    setRows(a1, 'a');\n"
                + "    alert(a1.rows);\n"
                + "    setRows(a1, '');\n"
                + "    alert(a1.rows);\n"
                + "    setRows(a1, -1);\n"
                + "    alert(a1.rows);\n"
                + "    setRows(a1, 3.4);\n"
                + "    alert(a1.rows);\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"9", "9", "2", "7"})
    public void selectionRange() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var ta = document.getElementById('myInput');\n"
                + "      ta.setSelectionRange(15, 15);\n"
                + "      alert(ta.selectionStart);\n"
                + "      alert(ta.selectionEnd);\n"
                + "      ta.setSelectionRange(2, 7);\n"
                + "      alert(ta.selectionStart);\n"
                + "      alert(ta.selectionEnd);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <textarea id='myInput'>some test</textarea>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"test", "4", "42", "2", "[object HTMLTextAreaElement]", "28"})
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
                        + "    <textarea id='t'>abc</textarea>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "4", "", "0"})
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
                        + "    <textarea id='t'>abc</textarea>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"12", "2", "[object HTMLTextAreaElement]", "28"})
    public void getAttributeAndSetValueOther() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var t = document.getElementById('t');\n"
                        + "        t.value = 12;\n"
                        + "        alert(t.value);\n"
                        + "        if (t.value != null)\n"
                        + "          alert(t.value.length);\n"

                        + "        t.value = t;\n"
                        + "        alert(t.value);\n"
                        + "        if (t.value != null)\n"
                        + "          alert(t.value.length);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <textarea id='t'>abc</textarea>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"-1", "null", "32", "32", "-1", "ms"})
    public void maxLength() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.form1.textarea1.maxLength);\n"
                + "      alert(document.form1.textarea1.getAttribute('maxLength'));\n"
                + "      alert(document.form1.textarea2.maxLength);\n"
                + "      alert(document.form1.textarea2.getAttribute('maxLength'));\n"
                + "      alert(document.form1.textarea3.maxLength);\n"
                + "      alert(document.form1.textarea3.getAttribute('maxLength'));\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form name='form1' method='post'>\n"
                + "    <textarea name='textarea1'></textarea>\n"
                + "    <textarea name='textarea2' maxLength='32'></textarea>\n"
                + "    <textarea name='textarea3' maxLength='ms'></textarea>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"-1", "null", "32", "32", "-1", "ms"})
    public void minLength() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.form1.textarea1.minLength);\n"
                + "      alert(document.form1.textarea1.getAttribute('minLength'));\n"
                + "      alert(document.form1.textarea2.minLength);\n"
                + "      alert(document.form1.textarea2.getAttribute('minLength'));\n"
                + "      alert(document.form1.textarea3.minLength);\n"
                + "      alert(document.form1.textarea3.getAttribute('minLength'));\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <form name='form1' method='post'>\n"
                + "    <textarea name='textarea1'></textarea>\n"
                + "    <textarea name='textarea2' minLength='32'></textarea>\n"
                + "    <textarea name='textarea3' minLength='ms'></textarea>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"10", "10", "error", "10", "10", "0", "0"})
    public void setMaxLength() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function setMaxLength(length){\n"
                + "      try {\n"
                + "        document.form1.textarea1.maxLength = length;\n"
                + "      } catch(e) { alert('error'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <form name='form1' method='post' >\n"
                + "    <textarea id='textarea1'></textarea>\n"
                + "    <script>\n"
                + "      var a = document.getElementById('textarea1');\n"
                + "      setMaxLength(10);\n"
                + "      alert(a.maxLength);\n"
                + "      alert(a.getAttribute('maxLength'));\n"
                + "      setMaxLength(-1);\n"
                + "      alert(a.maxLength);\n"
                + "      alert(a.getAttribute('maxLength'));\n"
                + "      setMaxLength('abc');\n"
                + "      alert(a.maxLength);\n"
                + "      alert(a.getAttribute('maxLength'));\n"
                + "    </script>\n"
                + "  </form>\n"
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
                + "    <textarea id='a'></textarea>\n"
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
    public void mouseOverTextarea() {
        mouseOver("<textarea id='tester' onmouseover='dumpEvent(event);'></textarea>");
    }

    @Test
    @Alerts("mouse over [tester]")
    public void mouseOverTextareaDisabled() {
        mouseOver("<textarea id='tester' onmouseover='dumpEvent(event);' disabled ></textarea>");
    }

    private void mouseOver(final String element) {
        final String html = "<html>\n"
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
                + "      document.title += msg;\n"
                + "    }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "<body>\n"
                + "  <form id='form1'>\n"
                + "    " + element + "\n"
                + "  </form>\n"
                + "</body>"
                + " <script>"
                + "  document.getElementById('tester').click();"
                + "  </script>"
                + "</html>";
        checkHtmlAlert(html);
    }
}
