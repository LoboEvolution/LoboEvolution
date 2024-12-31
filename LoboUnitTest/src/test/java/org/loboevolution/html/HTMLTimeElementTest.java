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
import org.loboevolution.html.dom.HTMLTimeElement;

/**
 * Unit tests for {@link HTMLTimeElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTimeElementTest extends LoboUnitTest {

    @Test
    @Alerts({"undefined", "undefined"})
    public void text() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var time1 = document.getElementById('time1');\n"
                        + "        alert(time1.text);\n"
                        + "        var time2 = document.getElementById('time1');\n"
                        + "        alert(time2.text);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <p>start <time id='time1'>20:00</time></p>\n"
                        + "    <p>start <time id='time2' datetime='2001-05-15 19:00'>15. Mai</time></p>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "20:40", "2001-05-15 19:00", ""})
    public void datetime() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var time1 = document.getElementById('time1');\n"
                        + "        alert(time1.dateTime);\n"
                        + "        time1.dateTime = '20:40';\n"
                        + "        alert(time1.dateTime);\n"

                        + "        var time2 = document.getElementById('time2');\n"
                        + "        alert(time2.dateTime);\n"
                        + "        time2.dateTime = '';\n"
                        + "        alert(time2.dateTime);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <p>start <time id='time1'>20:00</time></p>\n"
                        + "    <p>start <time id='time2' datetime='2001-05-15 19:00'>15. Mai</time></p>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
