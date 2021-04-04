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
 * Tests for {@link org.loboevolution.html.dom.HTMLButtonElement}.
 */
public class HTMLButtonElementTest extends LoboUnitTest {


    /**
     * <p>readWriteAccessKey.</p>
     */
    @Test
    public void readWriteAccessKey() {
        final String html
                = "<html><body><button id='a1'>a1</button><button id='a2' accesskey='A'>a2</button><script>\n"
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
        final String[] messages = {"", "A", "a", "A", "a8", "8Afoo", "8", "@"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>type.</p>
     */
    @Test
    public void type() {
        final String html = "<html><head><script>\n"
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
        final String[] messages =  {"submit", "button", "submit"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getType.</p>
     */
    @Test
    public void getType() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
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

        final String[] messages =  {"submit", "submit", "submit", "submit", "reset", "button", "submit"};
        checkHtmlAlert(html, messages);
    }

     /**
      * <p>getForm.</p>
      */
     @Test
    public void getForm() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
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

        final String[] messages = {"myFormId", null, null, null, null, "myFormId", null, "myForm2Id", "myForm2Id"};
        checkHtmlAlert(html, messages);
    }

     /**
      * <p>getAttributeAndSetValue.</p>
      */
     @Test
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

        final String[] messages = {"test", "4", "42", "2", "[object HTMLButtonElement]", "26"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttributeAndSetValueNull.</p>
     */
    @Test
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

        final String[] messages =  {null, "4", null, "4"};
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
                        + "  <button id='e1'>e 1</button><br>\n"
                        + "  <label>something <label> click here <button id='e2'>e 2</button></label></label><br>\n"
                        + "  <label for='e3'> and here</label>\n"
                        + "  <button id='e3'>e 3</button><br>\n"
                        + "  <label id='l4' for='e4'> what about</label>\n"
                        + "  <label> this<button id='e4'>e 4</button></label><br>\n"
                        + "</body></html>";

        final String[] messages = {"0", "2", "1", "2", "1", "1"};
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
                + "    <button id='a'>button</button><br>\n"
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
