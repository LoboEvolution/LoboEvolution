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

/**
 * Unit tests for HTMLDTElement.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLDTElementTest extends LoboUnitTest {

    @Test
    @Alerts({"undefined", "null", "nowrap", "null", "x", "null", "x", "blah", "", "blah"})
    public void noWrap() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var dt = document.getElementById('test');\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "        dt.noWrap = 'nowrap';\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "        dt.noWrap = 'x';\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "        dt.setAttribute('noWrap', 'blah');\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "        dt.noWrap = '';\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <dl><dt id='test'>dt</dt></dl>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
