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
import org.loboevolution.html.dom.HTMLOptGroupElement;

/**
 * Unit tests for {@link HTMLOptGroupElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLOptGroupElementTest extends LoboUnitTest {

    @Test
    @Alerts({"false", "true", "true", "false", "true"})
    public void disabledAttribute() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var test1 = document.getElementById('test1');\n"
                        + "        alert(test1.disabled);\n"
                        + "        test1.disabled = true;\n"
                        + "        alert(test1.disabled);\n"
                        + "        test1.disabled = true;\n"
                        + "        alert(test1.disabled);\n"
                        + "        test1.disabled = false;\n"
                        + "        alert(test1.disabled);\n"

                        + "        var test2 = document.getElementById('test2');\n"
                        + "        alert(test2.disabled);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <form name='form1'>\n"
                        + "      <select>\n"
                        + "        <optgroup id='test1'>\n"
                        + "          <option value='group1'>Group1</option>\n"
                        + "        </optgroup>\n"
                        + "        <optgroup id='test2' disabled>\n"
                        + "          <option value='group2'>Group2</option>\n"
                        + "        </optgroup>\n"
                        + "      </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "newLabel", "", "label"})
    public void labelAttribute() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var test1 = document.getElementById('test1');\n"
                        + "        alert(test1.label);\n"
                        + "        test1.label = 'newLabel';\n"
                        + "        alert(test1.label);\n"
                        + "        test1.label = '';\n"
                        + "        alert(test1.label);\n"

                        + "        var test2 = document.getElementById('test2');\n"
                        + "        alert(test2.label);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <form name='form1'>\n"
                        + "      <select>\n"
                        + "        <optgroup id='test1'>\n"
                        + "          <option value='group1'>Group1</option>\n"
                        + "        </optgroup>\n"
                        + "        <optgroup id='test2' label='label'>\n"
                        + "          <option value='group2'>Group2</option>\n"
                        + "        </optgroup>\n"
                        + "      </select>\n"
                        + "  </form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }
}
