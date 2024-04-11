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
import org.loboevolution.html.dom.HTMLDataElement;

/**
 * Tests for {@link HTMLDataElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLDataElementTest extends LoboUnitTest {

    @Test
    @Alerts("[object HTMLDataElement]")
    public void tag() {
        final String html = "<html><body>\n"
                + "  <data id='it' value='1234'>onetwothreefour</data>\n"
                + "    <script>\n"
                + "  alert(document.getElementById('it'));\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1234", "#12o", "", "#12o"})
    public void value() {
        final String html = "<html><body>\n"
                + "  <data id='d1' value='1234'>onetwothreefour</data>\n"
                + "  <data id='d2' >onetwothreefour</data>\n"
                + "    <script>\n"
                + "  var dat = document.getElementById('d1');\n"
                + "  alert(dat.value);\n"
                + "  dat.value = '#12o';\n"
                + "  alert(dat.value);\n"

                + "  dat = document.getElementById('d2');\n"
                + "  alert(dat.value);\n"
                + "  dat.value = '#12o';\n"
                + "  alert(dat.value);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }
}
