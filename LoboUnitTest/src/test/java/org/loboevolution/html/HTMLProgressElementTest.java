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
import org.loboevolution.html.dom.HTMLProgressElement;

/**
 * Tests for {@link HTMLProgressElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLProgressElementTest extends LoboUnitTest {

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
                        + "  <progress id='e1'>e 1</progress><br>\n"
                        + "  <label>something <label> click here <progress id='e2'>e 2</progress></label></label><br>\n"
                        + "  <label for='e3'> and here</label>\n"
                        + "  <progress id='e3'>e 3</progress><br>\n"
                        + "  <label id='l4' for='e4'> what about</label>\n"
                        + "  <label> this<progress id='e4'>e 4</progress></label><br>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }
}
