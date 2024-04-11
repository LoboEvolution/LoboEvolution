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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for DOMStringMap.
 */
@ExtendWith(AlertsExtension.class)
public class DOMStringMapTest extends LoboUnitTest {

    @Test
    @Alerts({"undefined", "there"})
    public void get() {
        final String html
                = "<html><head>\n"
                + "<script>\n"                + "function test() {\n"
                + "  if (document.body.dataset) {\n"
                + "   alert(document.body.dataset.hi);\n"
                + "   alert(document.body.dataset.hello);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='test()' data-hello='there'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"old", "old", "null", "null"})
    public void put() {
        final String html
                = "<html><head>\n"
                + "<script>\n"                + "function test() {\n"
                + "  if (document.body.dataset) {\n"
                + "    document.body.dataset.dateOfBirth = 'old';\n"
                + "   alert(document.body.dataset.dateOfBirth);\n"
                + "   alert(document.body.getAttribute('data-date-of-birth'));\n"
                + "    document.body.dataset.dateOfBirth = null;\n"
                + "   alert(document.body.dataset.dateOfBirth);\n"
                + "   alert(document.body.getAttribute('data-date-of-birth'));\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
