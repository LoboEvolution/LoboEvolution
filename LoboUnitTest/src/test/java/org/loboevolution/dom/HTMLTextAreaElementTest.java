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

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLTextAreaElement}.
 */
public class HTMLTextAreaElementTest extends LoboUnitTest {
	
    /**
     * <p>getValue.</p>
     */
    @Test
    public void getValue() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title>\n"
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

        final String[] messages = {"1234", "PoohBear"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setValue.</p>
     */
    @Test
    public void setValue() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title></head>\n"
                + "<body>\n"
                + "  <form name='form1'>\n"
                + "    <textarea name='question'></textarea>\n"
                + "    <input type='button' name='btn_submit' value='Next'>\n"
                + "  </form>\n"
                + "  <script>\n"
                + "    document.form1.question.value = 'some text';\n"
                + "    alert(document.form1.elements[0].tagName);\n"
                + "    alert(document.form1.elements[1].tagName);\n"
                + "  </script>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"TEXTAREA", "INPUT"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>textLength.</p>
     */
    @Test
    public void textLength() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title></head>\n"
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

        final String[] messages = {"11", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>selection.</p>
     */
    @Test
    public void selection() {
		final String[] messages = {"0,0", "11,11", "3,11", "3,10", "7,7"};
        selection(3, 10, messages);
    }

    /**
     * <p>selection_outOfBounds.</p>
     */
    @Test
    public void selection_outOfBounds() {
		final String[] messages = {"0,0", "11,11", "11,11", "11,11", "7,7"};
        selection(-3, 15, messages);
    }

    /**
     * <p>selection_reverseOrder.</p>
     */
    @Test
    public void selection_reverseOrder() {
		final String[] messages = {"0,0", "11,11", "10,11", "5,5", "7,7"};
        selection(10, 5, messages);
    }

    private void selection(final int selectionStart, final int selectionEnd, String[] messages) {
        final String html
                = "<html>\n"
                + "<head><title>foo</title></head>\n"
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
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>doScroll.</p>
     */
    @Test
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

        final String[] messages = {"no"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>readOnly.</p>
     */
    @Test
    public void readOnly() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title>\n"
                + "  <script>\n"
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

        final String[] messages = {"true", "false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>accessKey.</p>
     */
    @Test
    public void accessKey() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title></head>\n"
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
        final String[] messages = {null, "A", "a", "A", "a8", "8Afoo", "8", "@"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>cols.</p>
     */
    @Test
    public void cols() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
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
        final String[] messages = {"20", "5", "8", "4", "20", "20", "20", "3"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>rows.</p>
     */
    @Test
    public void rows() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
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
        final String[] messages = {"2", "5", "8", "4", "2", "2", "2", "3"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>selectionRange.</p>
     */
    @Test
    public void selectionRange() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title>\n"
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

        final String[] messages =  {"9", "9", "2", "7"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttributeAndSetValue.</p>
     */
    @Test
    public void getAttributeAndSetValue() {
        final String html =
                "<html>\n"
                        + "  <head><title>foo</title>\n"
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

        final String[] messages = {"test", "4", "42", "2", "[object HTMLTextAreaElement]", "28"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttributeAndSetValueNull.</p>
     */
    @Test
    public void getAttributeAndSetValueNull() {
        final String html =
                "<html>\n"
                        + "  <head><title>foo</title>\n"
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

        final String[] messages = {"null", "4", "", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttributeAndSetValueOther.</p>
     */
    @Test
    public void getAttributeAndSetValueOther() {
        final String html =
                "<html>\n"
                        + "  <head><title>foo</title>\n"
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

        final String[] messages = {"12", "2", "[object HTMLTextAreaElement]", "28"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>maxLength.</p>
     */
    @Test
    public void maxLength() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title>\n"
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

        final String[] messages = {"2147483647", null, "32", "32", "2147483647", "ms"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>minLength.</p>
     */
    @Test
    public void minLength() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title>\n"
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

        final String[] messages = {"-1", null, "32", "32", "-1", "ms"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setMaxLength.</p>
     */
    @Test
    public void setMaxLength() {
        final String html
                = "<html>\n"
                + "<head><title>foo</title>\n"
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

        final String[] messages = {"10", "10", "error", "10", "10", "0", "0"};
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
                + "    <textarea id='a'></textarea>\n"
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
