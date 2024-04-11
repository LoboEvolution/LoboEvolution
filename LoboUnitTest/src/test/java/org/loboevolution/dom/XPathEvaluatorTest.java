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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.xpath.XPathEvaluator;

/**
 * Tests for {@link XPathEvaluator}.
 */
@ExtendWith(AlertsExtension.class)
public class XPathEvaluatorTest extends LoboUnitTest {


    @Test
    @Alerts({"function", "[object XPathEvaluator]", "[object HTMLHtmlElement]", "first", "second", ""})
    public void simple() {
        final String html = "<html><body>\n"
                + "<span id='first'>hello</span>\n"
                + "<div><span id='second'>world</span></div>\n"
                + "<script>\n"
                + "var res = '';\n"
                + "try {\n"
                + "  res += typeof window.XPathEvaluator + '§';\n"
                + "  var xpe = new XPathEvaluator();\n"
                + "  res += xpe + '§';\n"
                + "  var nsResolver = xpe.createNSResolver(document.documentElement);\n"
                + "  res += nsResolver + '§';\n"
                + "  var result = xpe.evaluate('//span', document.body, nsResolver, 0, null);\n"
                + "  var found = [];\n"
                + "  var next;\n"
                + "  while (next = result.iterateNext()) {\n"
                + "    res += next.id + '§';\n"
                + "  }\n"
                + "} catch(e) { res += 'exception' + '§'; }\n"
                + "alert(res);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void namespacesWithNodeInArray() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  var xml = "
                + "  '<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "    <soap:books>"
                + "      <soap:book>"
                + "        <title>Immortality</title>"
                + "        <author>John Smith</author>"
                + "      </soap:book>"
                + "    </soap:books>"
                + "  </soap:Envelope>';\n"

                + "  function test() {\n"
                + "    if (window.XPathEvaluator) {\n"
                + "      var doc = (new DOMParser).parseFromString(xml, 'text/xml');\n"
                + "      var xpe = new XPathEvaluator();\n"
                + "      var nsResolver = xpe.createNSResolver(doc.documentElement);\n"
                + "      try {\n"
                + "        var result = xpe.evaluate('/soap:Envelope/soap:books/soap:book/title/text()', "
                + "[doc.documentElement], nsResolver, XPathResult.STRING_TYPE, null);\n"
                + "       alert(result.stringValue);\n"
                + "      } catch(e) {alert('exception'); }\n"
                + "    } else {\n"
                + "     alert('window.XPathEvaluator undefined');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Immortality")
    public void namespacesWithCustomNSResolver() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function nsResolver(prefix) {\n"
                + "    return {s : 'http://schemas.xmlsoap.org/soap/envelope/'}[prefix] || null;\n"
                + "  }\n"

                + "  var xml = "
                + "  '<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "    <soap:books>"
                + "      <soap:book>"
                + "        <title>Immortality</title>"
                + "        <author>John Smith</author>"
                + "      </soap:book>"
                + "    </soap:books>"
                + "  </soap:Envelope>';\n"

                + "  function test() {\n"
                + "    if (window.XPathEvaluator) {\n"
                + "      var doc = (new DOMParser).parseFromString(xml, 'text/xml');\n"
                + "      var xpe = new XPathEvaluator();\n"
                + "      var result = xpe.evaluate('/s:Envelope/s:books/s:book/title/text()', "
                + "doc.documentElement, nsResolver, XPathResult.STRING_TYPE, null);\n"
                + "     alert(result.stringValue);\n"
                + "    } else {\n"
                + "     alert('window.XPathEvaluator undefined');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
