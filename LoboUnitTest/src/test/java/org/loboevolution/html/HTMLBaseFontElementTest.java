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
import org.loboevolution.html.dom.HTMLBaseFontElement;

/**
 * Tests for {@link HTMLBaseFontElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLBaseFontElementTest extends LoboUnitTest {

    @Test
    @Alerts({"[object HTMLElement]", "undefined", "undefined", "undefined"})
    public void defaults() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base);\n"
                        + "        alert(base.face);\n"
                        + "        alert(base.size);\n"
                        + "        alert(base.color);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "42"})
    public void size() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='4' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.size);\n"
                        + "        try {\n"
                        + "          base.size = 42;\n"
                        + "          alert(base.size);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "helvetica"})
    public void face() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='5' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.face);\n"
                        + "        try {\n"
                        + "          base.face = 'helvetica';\n"
                        + "          alert(base.face);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "blue"})
    public void color() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='4' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.color);\n"
                        + "        try {\n"
                        + "          base.color = 'blue';\n"
                        + "          alert(base.color);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLElement]", "exception"})
    public void type() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "  var elem = document.getElementById('b1');\n"
                + "    try {\n"
                + "      alert(elem);\n"
                + "      alert(HTMLBaseFontElement);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <basefont id='b1' color='red' face='swiss' size='4' />\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
