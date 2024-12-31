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
import org.loboevolution.html.dom.HTMLMetaElement;

/**
 * Unit tests for {@link HTMLMetaElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLMetaElementTest extends LoboUnitTest {


    @Test
    @Alerts({"undefined", "text/html; charset=utf-8", "", "", "", "undefined", ""})
    public void name() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var meta = document.getElementsByTagName('meta')[0];\n"
                        + "        alert(meta.charset);\n"
                        + "        alert(meta.content);\n"
                        + "        alert(meta.httpEquiv);\n"
                        + "        alert(meta.name);\n"
                        + "        alert(meta.scheme);\n"
                        + "        alert(meta.url);\n"
                        + "        alert(meta.media);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("only screen and (max-width: 600px)")
    public void media() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <meta http-equiv='Content-Type' media='only screen and (max-width: 600px)'>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var meta = document.getElementsByTagName('meta')[0];\n"
                        + "        alert(meta.media);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
