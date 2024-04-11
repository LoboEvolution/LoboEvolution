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
package org.loboevolution.canvas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Unit tests for {@link IntersectionObserver}.
 */
@ExtendWith(AlertsExtension.class)
public class IntersectionObserverTest extends LoboUnitTest {


    @Test
    @Alerts("true")
    public void inWindow() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "     alert('IntersectionObserver' in window);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function", "function", "function", "function"})
    public void functions() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  if (window.IntersectionObserver) {\n"
                        + "    var callback = function (entries, observer) {};\n"
                        + "    var observer = new IntersectionObserver(callback);\n"
                        + "   alert(typeof observer.observe);\n"
                        + "   alert(typeof observer.unobserve);\n"
                        + "   alert(typeof observer.disconnect);\n"
                        + "   alert(typeof observer.takeRecords);\n"
                        + "  } else {alert('no IntersectionObserver'); }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
