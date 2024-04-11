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
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLSpanElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link HTMLSpanElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLSpanElementTest extends LoboUnitTest {


    @Test
    @Alerts("no")
    public void doScroll() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var span = document.getElementById('s');\n"
                        + "        if(span.doScroll) {\n"
                        + "          alert('yes');\n"
                        + "          span.doScroll();\n"
                        + "          span.doScroll('down');\n"
                        + "        } else {\n"
                        + "          alert('no');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'><span id='s'>abc</span></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLSpanElement] undefined")
    public void cite() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        debug(document.createElement('span'));\n"
                        + "      }\n"
                        + "      function debug(e) {\n"
                        + "        alert(e + ' ' + e.cite);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    public void getText() {
        final String html = "<html><head></head><body>\n"
                + "<div id='foo'><span>beforeSpace</span><span> </span><span>afterSpace</span></div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("foo");
        assertEquals("beforeSpace afterSpace", elem.getTextContent());
    }
}
