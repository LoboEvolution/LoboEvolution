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
import org.loboevolution.html.dom.HTMLDirectoryElement;

/**
 * Unit tests for {@link HTMLDirectoryElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLDirectoryElementTest extends LoboUnitTest {

    @Test
    @Alerts("[object HTMLDirectoryElement]")
    public void simpleScriptable() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <dir id='myId'/>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "true", "true", "true", "null", "", "blah", "2",
            "true", "false", "true", "false", "", "null", "", "null"})
    public void compact() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.getElementById('dir1').compact);\n"
                        + "        alert(document.getElementById('dir2').compact);\n"
                        + "        alert(document.getElementById('dir3').compact);\n"
                        + "        alert(document.getElementById('dir4').compact);\n"
                        + "        alert(document.getElementById('dir1').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dir2').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dir3').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dir4').getAttribute('compact'));\n"

                        + "        document.getElementById('dir1').compact = true;\n"
                        + "        document.getElementById('dir2').compact = false;\n"
                        + "        document.getElementById('dir3').compact = 'xyz';\n"
                        + "        document.getElementById('dir4').compact = null;\n"
                        + "        alert(document.getElementById('dir1').compact);\n"
                        + "        alert(document.getElementById('dir2').compact);\n"
                        + "        alert(document.getElementById('dir3').compact);\n"
                        + "        alert(document.getElementById('dir4').compact);\n"
                        + "        alert(document.getElementById('dir1').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dir2').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dir3').getAttribute('compact'));\n"
                        + "        alert(document.getElementById('dir4').getAttribute('compact'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dir id='dir1'><li>a</li><li>b</li></dir>\n"
                        + "    <dir compact='' id='dir2'><li>a</li><li>b</li></dir>\n"
                        + "    <dir compact='blah' id='dir3'><li>a</li><li>b</li></dir>\n"
                        + "    <dir compact='2' id='dir4'><li>a</li><li>b</li></dir>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined",
            "null", "", "blah", "A", "1", "a", "A", "i", "I", "u"})
    public void type() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.getElementById('dir1').type);\n"
                        + "        alert(document.getElementById('dir2').type);\n"
                        + "        alert(document.getElementById('dir3').type);\n"
                        + "        alert(document.getElementById('dir4').type);\n"
                        + "        alert(document.getElementById('dir1').getAttribute('type'));\n"
                        + "        alert(document.getElementById('dir2').getAttribute('type'));\n"
                        + "        alert(document.getElementById('dir3').getAttribute('type'));\n"
                        + "        alert(document.getElementById('dir4').getAttribute('type'));\n"

                        + "        document.getElementById('dir1').type = '1';\n"
                        + "        alert(document.getElementById('dir1').type);\n"

                        + "        document.getElementById('dir1').type = 'a';\n"
                        + "        alert(document.getElementById('dir1').type);\n"

                        + "        document.getElementById('dir1').type = 'A';\n"
                        + "        alert(document.getElementById('dir1').type);\n"

                        + "        document.getElementById('dir1').type = 'i';\n"
                        + "        alert(document.getElementById('dir1').type);\n"

                        + "        document.getElementById('dir1').type = 'I';\n"
                        + "        alert(document.getElementById('dir1').type);\n"

                        + "        try { document.getElementById('dir1').type = 'u' } catch(e) {alert('exception');}\n"
                        + "        alert(document.getElementById('dir1').type);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <dir id='dir1'><li>a</li><li>b</li></dir>\n"
                        + "    <dir type='' id='dir2'><li>a</li><li>b</li></dir>\n"
                        + "    <dir type='blah' id='dir3'><li>a</li><li>b</li></dir>\n"
                        + "    <dir type='A' id='dir4'><li>a</li><li>b</li></dir>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
