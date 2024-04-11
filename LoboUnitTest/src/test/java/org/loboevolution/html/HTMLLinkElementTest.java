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
import org.loboevolution.html.dom.HTMLLinkElement;

/**
 * Unit tests for {@link HTMLLinkElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLLinkElementTest extends LoboUnitTest {


    @Test
    @Alerts({"", "", "", "", "test.css", "text/css", "stylesheet", "stylesheet1"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "alternate help", "prefetch", "prefetch", "not supported", "notsupported"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "alternate", "help"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "2", "1", "alternate", "help", "abc", "alternate help", "abc"})
    public void setRelListString() {
        final String html
                = "<html><body><link id='l1'><link id='l2' rel='alternate help'><script>\n"

                + "var a1 = document.getElementById('l1'), a2 = document.getElementById('l2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  a1.relList = 'alternate help';\n"
                + "  a2.relList = 'abc';\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"
                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "0", "0", "", "\\s\\s\\t"})
    public void setRelListStringBlank() {
        final String html
                = "<html><body><link id='l1'><link id='l2' rel='alternate help'><script>\n"
                + "var a1 = document.getElementById('l1'), a2 = document.getElementById('l2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  a1.relList = '';\n"
                + "  a2.relList = '  \t';\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "1", "1", "null", "null", "null", "null"})
    public void setRelListNull() {
        final String html
                = "<html><body><link id='l1'><link id='l2' rel='alternate help'><script>\n"
                + "var a1 = document.getElementById('l1'), a2 = document.getElementById('l2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  a1.relList = null;\n"
                + "  a2.relList = null;\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"
                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "1", "1", "undefined", "undefined", "undefined", "undefined"})
    public void setRelListUndefined() {
        final String html
                = "<html><body><link id='l1'><link id='l2' rel='alternate help'><script>\n"
                + "var a1 = document.getElementById('l1'), a2 = document.getElementById('l2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  a1.relList = undefined;\n"
                + "  a2.relList = undefined;\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"
                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }
}