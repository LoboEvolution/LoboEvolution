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
import org.loboevolution.html.dom.HTMLBaseElement;

/**
 * Tests for {@link HTMLBaseElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLBaseElementTest extends LoboUnitTest {

    @Test
    @Alerts({"http://www.foo.com/images/", "", "", "_blank"})
    public void hrefAndTarget() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <base id='b1' href='http://www.foo.com/images/' />\n"
                        + "    <base id='b2' target='_blank' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.getElementById('b1').href);\n"
                        + "        alert(document.getElementById('b2').href);\n"
                        + "        alert(document.getElementById('b1').target);\n"
                        + "        alert(document.getElementById('b2').target);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";


        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLBaseElement]", "function HTMLBaseElement() { [native code] }"})
    public void type() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "  var elem = document.getElementById('b1');\n"
                + "    try {\n"
                + "      alert(elem);\n"
                + "      alert(HTMLBaseElement);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <base id='b1' href='http://somehost/images/' />\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
