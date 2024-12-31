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
import org.loboevolution.html.dom.HTMLAreaElement;

/**
 * Tests for {@link HTMLAreaElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLAreaElementTest extends LoboUnitTest {

    @Test
    @Alerts({"null", "A", "a", "A", "a8", "8Afoo", "8", "@"})
    public void readWriteAccessKey() {
        final String html
                = "<html><body><map><area id='a1'/><area id='a2' accesskey='A'/></map>"
                + "<script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = 'a';\n"
                + "a2.accessKey = 'A';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = 'a8';\n"
                + "a2.accessKey = '8Afoo';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = '8';\n"
                + "a2.accessKey = '@';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "function HTMLAreaElement() { [native code] }"})
    public void type() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "  var elem = document.getElementById('a1');\n"
                + "    try {\n"
                + "      alert(elem);\n"
                + "      alert(HTMLAreaElement);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <map><area id='a1'/><area id='a2' accesskey='A'/></map>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLButtonElement]", "[object HTMLButtonElement]",
            "", "http://srv/htmlunit.org"})
    public void focus() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var testNode = document.getElementById('myButton');\n"
                        + "      testNode.focus();\n"
                        + "      alert(document.activeElement);\n"

                        + "      testNode = document.getElementById('a1');\n"
                        + "      testNode.focus();\n"
                        + "      alert(document.activeElement);\n"

                        + "      testNode = document.getElementById('a2');\n"
                        + "      testNode.focus();\n"
                        + "      alert(document.activeElement);\n"

                        + "      testNode = document.getElementById('a3');\n"
                        + "      testNode.focus();\n"
                        + "      alert(document.activeElement);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <button id='myButton'>Press</button>\n"
                        + "  <img usemap='#dot'"
                        + " src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAA"
                        + "HElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=='>\n"
                        + "  <map name='dot'>\n"
                        + "    <area id='a1' shape='rect' coords='0,0,1,1'/>\n"
                        + "    <area id='a2' href='' shape='rect' coords='0,0,1,1'/>\n"
                        + "    <area id='a3' href='http://srv/htmlunit.org' shape='rect' coords='0,0,1,1'/>\n"
                        + "  <map>\n"
                        + "</body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"a1 clicked", "a2 clicked"})
    public void click() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    document.getElementById('a1').click();\n"
                + "    document.getElementById('a2').click();\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <img usemap='#dot'"
                + " src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAA"
                + "HElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=='>\n"
                + "  <map name='dot'>\n"
                + "    <area id='a1' shape='rect' coords='0,0,1,1' onclick='alert(\"a1 clicked\");'/>\n"
                + "    <area id='a2' shape='rect' coords='0 0 2 2' onclick='alert(\"a2 clicked\");'/>\n"
                + "  <map>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "alternate help", "prefetch", "prefetch", "not supported", "notsupported"})
    public void readWriteRel() {
        final String html
                = "<html><body><map><area id='a1'/><area id='a2' rel='alternate help'/></map><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"
                + "a1.rel = 'prefetch';\n"
                + "a2.rel = 'prefetch';\n"
                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"
                + "a1.rel = 'not supported';\n"
                + "a2.rel = 'notsupported';\n"
                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "2", "alternate", "help"})
    public void relList() {
        final String html
                = "<html><body><map><area id='a1'/><area id='a2' rel='alternate help'/></map><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }
}
