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
import org.loboevolution.html.dom.HTMLDetailsElement;

/**
 * Unit tests for {@link HTMLDetailsElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLDetailsElementTest extends LoboUnitTest {

    @Test
    @Alerts({"false", "null", "true", "", "false", "null", "true", "",
            "true", "", "true", "TrUE", "false", "null"})
    public void open() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var det = document.getElementById('detail');\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.open = true;\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.open = false;\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.open = 'true';\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.open = 'faLSE';\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.setAttribute('open', 'TrUE');\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.removeAttribute('open');\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <details id='detail'>\n"
                        + "      <summary>Automated Status: Operational</summary>\n"
                        + "      <p>Velocity: 12m/s</p>\n"
                        + "      <p>Direction: North</p>\n"
                        + "    </details>\n"
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
                        + "      function test() {\n"
                        + "        var det = document.getElementById('detail');\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.open = '';\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.open = 'abc';\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.setAttribute('open', 'blah');\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"

                        + "        det.removeAttribute('open');\n"
                        + "        alert(det.open);\n"
                        + "        alert(det.getAttribute('open'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <details id='detail'>\n"
                        + "      <summary>Automated Status: Operational</summary>\n"
                        + "      <p>Velocity: 12m/s</p>\n"
                        + "      <p>Direction: North</p>\n"
                        + "    </details>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "null", "", "null", "abc", "null", "abc", "blah", "abc", "null"})
    public void name() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var det = document.getElementById('detail');\n"
                        + "        alert(det.name);\n"
                        + "        alert(det.getAttribute('name'));\n"

                        + "        det.name = '';\n"
                        + "        alert(det.name);\n"
                        + "        alert(det.getAttribute('name'));\n"

                        + "        det.name = 'abc';\n"
                        + "        alert(det.name);\n"
                        + "        alert(det.getAttribute('name'));\n"

                        + "        det.setAttribute('name', 'blah');\n"
                        + "        alert(det.name);\n"
                        + "        alert(det.getAttribute('name'));\n"

                        + "        det.removeAttribute('name');\n"
                        + "        alert(det.name);\n"
                        + "        alert(det.getAttribute('name'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <details id='detail'>\n"
                        + "      <summary>Automated Status: Operational</summary>\n"
                        + "      <p>Velocity: 12m/s</p>\n"
                        + "      <p>Direction: North</p>\n"
                        + "    </details>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
