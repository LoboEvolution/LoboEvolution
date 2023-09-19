/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLLinkElement}.
 */
public class HTMLLinkElementTest extends LoboUnitTest {

    /**
     * <p>basicLinkAttributes.</p>
     */
    @Test
    public void basicLinkAttributes() {
        final String html =
                "<html>\n"
                        + "  <body onload='test()'>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var s = document.createElement('link');\n"
                        + "        alert(s.href);\n"
                        + "        alert(s.type);\n"
                        + "        alert(s.rel);\n"
                        + "        alert(s.rev);\n"
                        + "        s.href = 'test.css';\n"
                        + "        s.type = 'text/css';\n"
                        + "        s.rel  = 'stylesheet';\n"
                        + "        s.rev  = 'stylesheet1';\n"
                        + "        alert(s.href);\n"
                        + "        alert(s.type);\n"
                        + "        alert(s.rel);\n"
                        + "        alert(s.rev);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {"", null, null, null, "test.css", "text/css", "stylesheet", "stylesheet1"};;
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>readWriteRel.</p>
     */
    @Test
    public void readWriteRel() {
        final String html
                = "<html><body><link id='l1'><link id='l2' rel='alternate help'><script>\n"
                + "var l1 = document.getElementById('l1'), l2 = document.getElementById('l2');\n"

                + "alert(l1.rel);\n"
                + "alert(l2.rel);\n"

                + "l1.rel = 'prefetch';\n"
                + "l2.rel = 'prefetch';\n"
                + "alert(l1.rel);\n"
                + "alert(l2.rel);\n"

                + "l1.rel = 'not supported';\n"
                + "l2.rel = 'notsupported';\n"
                + "alert(l1.rel);\n"
                + "alert(l2.rel);\n"

                + "</script></body></html>";
        final String[] messages = {null, "alternate help", "prefetch", "prefetch", "not supported", "notsupported"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>relList.</p>
     */
    @Test
    public void relList() {
        final String html
                = "<html><body><link id='l1'><link id='l2' rel='alternate help'><script>\n"
                + "var l1 = document.getElementById('l1'), l2 = document.getElementById('l2');\n"

                + "try {\n"
                + "  alert(l1.relList.length);\n"
                + "  alert(l2.relList.length);\n"

                + "  for (var i = 0; i < l2.relList.length; i++) {\n"
                + "    alert(l2.relList[i]);\n"
                + "  }\n"
                + "} catch(e) { alert('exception'); }\n"

                + "</script></body></html>";
        final String[] messages = {"0", "2", "alternate", "help"};
        checkHtmlAlert(html, messages);
    }
}
