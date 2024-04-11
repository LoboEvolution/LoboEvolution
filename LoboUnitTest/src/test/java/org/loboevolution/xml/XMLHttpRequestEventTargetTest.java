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
package org.loboevolution.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for XMLHttpRequestEventTarget.
 */
@ExtendWith(AlertsExtension.class)
public class XMLHttpRequestEventTargetTest extends LoboUnitTest {


    @Test
    @Alerts("true")
    public void inWindow() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "     alert('XMLHttpRequestEventTarget' in window);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void ctor() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var xhr = new XMLHttpRequestEventTarget();\n"
                + "   alert(xhr);\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Object]", "undefined", "undefined",
            "function get onabort() { [native code] }",
            "function set onabort() { [native code] }",
            "true", "true"})
    public void getOwnPropertyDescriptorOnabort() {
        getOwnPropertyDescriptor("onabort");
    }

    @Test
    @Alerts({"[object Object]", "undefined", "undefined",
            "function get onerror() { [native code] }",
            "function set onerror() { [native code] }",
            "true", "true"})
    public void getOwnPropertyDescriptorOnerror() {
        getOwnPropertyDescriptor("onerror");
    }

    @Test
    @Alerts({"[object Object]", "undefined", "undefined",
            "function get onload() { [native code] }",
            "function set onload() { [native code] }",
            "true", "true"})
    public void getOwnPropertyDescriptorOnload() {
        getOwnPropertyDescriptor("onload");
    }

    @Test
    @Alerts({"[object Object]", "undefined", "undefined",
            "function get onloadstart() { [native code] }",
            "function set onloadstart() { [native code] }",
            "true", "true"})
    public void getOwnPropertyDescriptorOnloadstart() {
        getOwnPropertyDescriptor("onloadstart");
    }

    @Test
    @Alerts({"[object Object]", "undefined", "undefined",
            "function get onloadend() { [native code] }",
            "function set onloadend() { [native code] }",
            "true", "true"})
    public void getOwnPropertyDescriptorOnloadend() {
        getOwnPropertyDescriptor("onloadend");
    }

    @Test
    @Alerts({"[object Object]", "undefined", "undefined",
            "function get onprogress() { [native code] }",
            "function set onprogress() { [native code] }",
            "true", "true"})
    public void getOwnPropertyDescriptorOnprogress() {
        getOwnPropertyDescriptor("onprogress");
    }

    @Test
    @Alerts("undefined")
    public void getOwnPropertyDescriptorOnreadystatechange() {
        getOwnPropertyDescriptor("onreadystatechange");
    }

    @Test
    @Alerts({"[object Object]", "undefined", "undefined",
            "function get ontimeout() { [native code] }",
            "function set ontimeout() { [native code] }",
            "true", "true"})
    public void getOwnPropertyDescriptorOntimeout() {
        getOwnPropertyDescriptor("ontimeout");
    }

    private void getOwnPropertyDescriptor(final String event) {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      var request;\n"
                        + "      function test() {\n"
                        + "        var desc = Object.getOwnPropertyDescriptor("
                        + "XMLHttpRequestEventTarget.prototype, '" + event + "');\n"
                        + "       alert(desc);\n"
                        + "        if(!desc) { return; }\n"

                        + "       alert(desc.value);\n"
                        + "       alert(desc.writable);\n"
                        + "       alert(desc.get);\n"
                        + "       alert(desc.set);\n"
                        + "       alert(desc.configurable);\n"
                        + "       alert(desc.enumerable);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
