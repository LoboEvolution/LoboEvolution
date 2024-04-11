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
import org.loboevolution.html.dom.HTMLPreElement;

/**
 * Unit tests for {@link HTMLPreElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLPreElementTest extends LoboUnitTest {


    @Test
    @Alerts({"0", "number", "100", "77", "number", "123"})
    public void width() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var testPre = document.getElementById('testPre');\n"
                        + "        alert(testPre.width);\n"
                        + "        alert(typeof testPre.width);\n"
                        + "        testPre.width = 100;\n"
                        + "        alert(testPre.width);\n"

                        + "        var testPre = document.getElementById('testPreWidth');\n"
                        + "        alert(testPreWidth.width);\n"
                        + "        alert(typeof testPreWidth.width);\n"
                        + "        testPreWidth.width = 123;\n"
                        + "        alert(testPreWidth.width);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <pre id='testPre'>pre content</pre>\n"
                        + "    <pre id='testPreWidth' width='77'>pre content</pre>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined", "undefined", "undefined",
            "undefined", "left", "none", "right", "all", "2", "abc", "8"})
    public void clear() {
        final String html
                = "<html><body>\n"
                + "<pre id='p1'>pre1</pre>\n"
                + "<pre id='p2' clear='left'>pre2</pre>\n"
                + "<pre id='p3' clear='all'>pre3</pre>\n"
                + "<pre id='p4' clear='right'>pre4</pre>\n"
                + "<pre id='p5' clear='none'>pre5</pre>\n"
                + "<pre id='p6' clear='2'>pre6</pre>\n"
                + "<pre id='p7' clear='foo'>pre7</pre>\n"
                + "    <script>\n"
                + "function set(p, value) {\n"
                + "  try {\n"
                + "    p.clear = value;\n"
                + "  } catch(e) {\n"
                + "    alert('!');\n"
                + "  }\n"
                + "}\n"
                + "var p1 = document.getElementById('p1');\n"
                + "var p2 = document.getElementById('p2');\n"
                + "var p3 = document.getElementById('p3');\n"
                + "var p4 = document.getElementById('p4');\n"
                + "var p5 = document.getElementById('p5');\n"
                + "var p6 = document.getElementById('p6');\n"
                + "var p7 = document.getElementById('p7');\n"
                + "alert(p1.clear);\n"
                + "alert(p2.clear);\n"
                + "alert(p3.clear);\n"
                + "alert(p4.clear);\n"
                + "alert(p5.clear);\n"
                + "alert(p6.clear);\n"
                + "alert(p7.clear);\n"
                + "set(p1, 'left');\n"
                + "set(p2, 'none');\n"
                + "set(p3, 'right');\n"
                + "set(p4, 'all');\n"
                + "set(p5, 2);\n"
                + "set(p6, 'abc');\n"
                + "set(p7, '8');\n"
                + "alert(p1.clear);\n"
                + "alert(p2.clear);\n"
                + "alert(p3.clear);\n"
                + "alert(p4.clear);\n"
                + "alert(p5.clear);\n"
                + "alert(p6.clear);\n"
                + "alert(p7.clear);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
