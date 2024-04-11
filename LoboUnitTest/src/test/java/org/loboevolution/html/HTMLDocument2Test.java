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

/**
 * Tests for {@link HTMLDocument}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLDocument2Test extends LoboUnitTest {

    @Test
    @Alerts({"www.gargoylesoftware.com", "gargoylesoftware.com"})
    public void domain() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.domain);\n"
                + "  document.domain = 'gargoylesoftware.com';\n"
                + "  alert(document.domain);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"localhost", "gargoylesoftware.com"})
    public void domainFromLocalhost() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.domain);\n"
                + "  document.domain = 'gargoylesoftware.com';\n"
                + "  alert(document.domain);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"www.gargoylesoftware.com", "gargoylesoftware.com"})
    public void domainMixedCaseNetscape() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.domain);\n"
                + "  document.domain = 'GaRgOyLeSoFtWaRe.CoM';\n"
                + "  alert(document.domain);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"www.gargoylesoftware.com", "gargoylesoftware.com"})
    public void domainMixedCase() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.domain);\n"
                + "  document.domain = 'GaRgOyLeSoFtWaRe.CoM';\n"
                + "  alert(document.domain);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"d4.d3.d2.d1.gargoylesoftware.com", "d4.d3.d2.d1.gargoylesoftware.com", "d1.gargoylesoftware.com"})
    public void domainLong() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.domain);\n"
                + "  document.domain = 'd4.d3.d2.d1.gargoylesoftware.com';\n"
                + "  alert(document.domain);\n"
                + "  document.domain = 'd1.gargoylesoftware.com';\n"
                + "  alert(document.domain);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"localhost", "localhost"})
    public void domainSetSelf() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.domain);\n"
                + "  document.domain = 'localhost';\n"
                + "  alert(document.domain);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"www.gargoylesoftware.com", "www.gargoylesoftware.com"})
    public void domainset_forAboutBlank() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  var domain = document.domain;\n"
                + "  alert(domain);\n"
                + "  var frameDoc = frames[0].document;\n"
                + "  alert(frameDoc.domain);\n"
                + "  try {\n"
                + "    frameDoc.domain = domain;\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<iframe src='about:blank'></iframe>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"one", "two", "three", "four"})
    public void cookieRead() {

        final String html
                = "<html><head><title>First</title><script>\n"
                + "function doTest() {\n"
                + "  var cookieSet = document.cookie.split('; ');\n"
                + "  var setSize = cookieSet.length;\n"
                + "  var crumbs;\n"
                + "  for (var x = 0; x < setSize; x++) {\n"
                + "    crumbs = cookieSet[x].split('=');\n"
                + "    alert(crumbs[0]);\n"
                + "    alert(crumbs[1]);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "", "", ""})
    public void cookieWriteCookiesDisabled() {
        final String html =
                "<html><head><script>\n"
                        + "  alert(navigator.cookieEnabled);\n"
                        + "  alert(document.cookie);\n"
                        + "  document.cookie = 'foo=bar';\n"
                        + "  alert(document.cookie);\n"
                        + "  document.cookie = 'foo=hello world';\n"
                        + "  alert(document.cookie);\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body>abc</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
