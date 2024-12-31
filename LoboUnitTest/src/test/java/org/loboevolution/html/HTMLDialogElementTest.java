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
import org.loboevolution.html.dom.HTMLDialogElement;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * Unit tests for {@link HTMLDialogElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLDialogElementTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "    alert(event);\n"
            + "    alert(event.type);\n"
            + "    alert(event.bubbles);\n"
            + "    alert(event.cancelable);\n"
            + "    alert(event.composed);\n"
            + "    alert(event.target);\n"
            + "  }\n";

    @Test
    @Alerts({"false", "null", "true", "", "false", "null", "true", "",
            "true", "", "true", "TrUE", "false", "null"})
    public void open() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.open = true;\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.open = false;\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.open = 'true';\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.open = 'faLSE';\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.setAttribute('open', 'TrUE');\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.removeAttribute('open');\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "false", "null", "true", "", "true", "blah", "false", "null"})
    public void openString() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.open = '';\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.open = 'abc';\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.setAttribute('open', 'blah');\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.removeAttribute('open');\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "", "true", ""})
    public void show() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "", "true", ""})
    public void showAlreadyOpend() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester' open>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "", "true", ""})
    public void showModal() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.showModal();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        try {\n"
                        + "          dia.showModal();\n"
                        + "        } catch(e) { alert('InvalidStateError'); }"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "", "InvalidStateError", "true", ""})
    public void showModalAlreadyOpend() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        try {\n"
                        + "          dia.showModal();\n"
                        + "        } catch(e) { alert('InvalidStateError'); }"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester' open>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "", "true", ""})
    public void showAfterShow() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        try {\n"
                        + "          dia.show();\n"
                        + "        } catch(e) { alert('InvalidStateError'); }"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "", "InvalidStateError", "true", ""})
    public void showAfterShowModal() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.showModal();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        try {\n"
                        + "          dia.show();\n"
                        + "        } catch(e) { alert('InvalidStateError'); }"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "", "InvalidStateError", "true", ""})
    public void showModalAfterShow() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        try {\n"
                        + "          dia.showModal();\n"
                        + "        } catch(e) { alert('InvalidStateError'); }"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "", "true", ""})
    public void showModalAfterShowModal() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        dia.showModal();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        try {\n"
                        + "          dia.showModal();\n"
                        + "        } catch(e) { alert('InvalidStateError'); }"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "null", "true", "", "null",
            "false", "null", "null", "false", "null", "null",
            "[object Event]", "close", "false", "false", "false", "[object HTMLDialogElement]"})
    public void close() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.close();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.close();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "null", "true", "", "null",
            "false", "null", "", "false", "null", "", "closed"})
    public void closeOnclose() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.close();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.close();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester' onclose='alert(\"closed\")'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "", "true", "", "",
            "false", "null", "Html", "false", "null", "Html",
            "[object Event]", "close", "false", "false", "false", "[object HTMLDialogElement]"})
    public void closeReturnValue() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.close('Html');\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.close('unit');\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.getAttribute('open'));\n"
                        + "        alert(dia.returnValue);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "", "1", "true", "1", "2", "false", "3", "4",
            "[object Event]", "close", "false", "false", "false", "[object HTMLDialogElement]"})
    public void returnValue() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.returnValue = '1';\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.returnValue = '2';\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.close('3');\n"
                        + "        alert(dia.open);\n"
                        + "        alert(dia.returnValue);\n"
                        + "        dia.returnValue = '4';\n"
                        + "        alert(dia.returnValue);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "string ", "string null", "string undefined", "string 4", "string [object Object]"})
    public void returnValueSpecial() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        alert(typeof dia.returnValue + ' ' + dia.returnValue);\n"
                        + "        dia.returnValue = null;\n"
                        + "        alert(typeof dia.returnValue + ' ' + dia.returnValue);\n"
                        + "        dia.returnValue = undefined;\n"
                        + "        alert(typeof dia.returnValue + ' ' + dia.returnValue);\n"
                        + "        dia.returnValue = 4;\n"
                        + "        alert(typeof dia.returnValue + ' ' + dia.returnValue);\n"
                        + "        dia.returnValue = { a: '#' };\n"
                        + "        alert(typeof dia.returnValue + ' ' + dia.returnValue);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true", "false",
            "[object Event]", "close", "false", "false", "false", "[object HTMLDialogElement]"})
    public void formClosesDiaalert() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        document.getElementById('close').click();\n"
                        + "        alert(dia.open);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "      <form method='dialog'>\n"
                        + "        <button id='close'>OK</button>\n"
                        + "      </form>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true", "false",
            "[object Event]", "close", "false", "false", "false", "[object HTMLDialogElement]"})
    public void formClosesDialogWithoutJs() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        document.getElementById('close').click();\n"
                        + "        alert(dia.open);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "      <form method='dialog'>\n"
                        + "        <button id='close'>OK</button>\n"
                        + "      </form>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true", "true"})
    public void formGet() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        document.getElementById('close').click();\n"
                        + "        alert(dia.open);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "      <form method='get' action='" + URL_SECOND + "'>\n"
                        + "        <button id='close'>OK</button>\n"
                        + "      </form>\n"
                        + "    </dialog>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true", "true"})
    public void formOutsideDiaalert() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + DUMP_EVENT_FUNCTION
                        + "      function test() {\n"
                        + "        var dia = document.getElementById('tester');\n"
                        + "        if (typeof HTMLDialogElement !== 'object') { alert('No'); return; }\n"
                        + "        dia.addEventListener('close', (event) => {\n"
                        + "          dump(event);\n"
                        + "        });\n"
                        + "        alert(dia.open);\n"
                        + "        dia.show();\n"
                        + "        alert(dia.open);\n"
                        + "        document.getElementById('close').click();\n"
                        + "        alert(dia.open);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dialog id='tester'>\n"
                        + "      <p>HtmlUNit dialog</p>\n"
                        + "    </dialog>\n"
                        + "    <form method='dialog'>\n"
                        + "      <button id='close'>OK</button>\n"
                        + "    </form>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"Show dialog", "false",
            "Show dialog\nHello World\nDismiss", "true",
            "Show dialog", "false"})
    public void useCaseIssue598() {
        final String html = "<html>\n"
                + "  <body>\n"
                + "    <button id='showMyDialog'>Show dialog</button><br/>\n"
                + "    <dialog id='mydialog'>\n"
                + "      Hello World<br/>\n"
                + "      <button id='dismiss'>Dismiss</button>\n"
                + "    </dialog>\n"
                + "    <script>\n"
                + "      showButton = document.getElementById('showMyDialog');\n"
                + "      showButton.addEventListener('click', showMyDialog);\n"
                + "      dismissButton = document.getElementById('dismiss');\n"
                + "      dismissButton.addEventListener('click', closeMyDialog);\r\n"
                + "      function showMyDiaalert() {\n"
                + "        mydialog = document.getElementById('mydialog');\n"
                + "        mydialog.showModal();\n"
                + "      }\n"
                + "      function closeMyDiaalert() {\n"
                + "        mydialog = document.getElementById('mydialog');\n"
                + "        mydialog.close();\n"
                + "      }\n"
                + "    </script>\n"
                + "</body>"
                + " <script>"
                + "  document.getElementById('showMyDialog').click();"
                + "  document.getElementById('dismiss').click();"
                + "  </script>"
                + "</html>";
        checkHtmlAlert(html);
    }
}
