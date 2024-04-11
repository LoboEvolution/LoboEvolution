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
import org.loboevolution.html.js.xml.XMLDocument;

import static org.loboevolution.xml.XMLDocumentTest.*;

/**
 * Tests for {@link XMLDocument}.
 */
@ExtendWith(AlertsExtension.class)
public class XMLDocument3Test extends LoboUnitTest {


    @Test
    @Alerts({"1610", "1575", "32", "1604", "1610", "1610", "1610", "1610", "1610", "1610", "1604"})
    public void load_Encoding() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = " + callLoadXMLDocumentFromFile("'" + URL_SECOND + "'") + ";\n"
                + "    var value = doc.documentElement.firstChild.nodeValue;\n"
                + "    for (var i = 0; i < value.length; i++) {\n"
                + "     alert(value.charCodeAt(i));\n"
                + "    }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_FILE_FUNCTION
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"230", "230"})
    public void parseIso88591Encoding() {
        final String html = "<html>\n"
                + "  <head>\n"
                + "<script>\n"
                + "  function test(encoding) {\n"
                + "    var text=\"<?xml version='1.0' encoding='\" + encoding + \"'?><body>æ</body>\";\n"
                + "    var doc=" + callLoadXMLDocumentFromString("text") + ";\n"
                + "    var value = doc.documentElement.firstChild.nodeValue;\n"
                + "    for (var i = 0; i < value.length; i++) {\n"
                + "     alert(value.charCodeAt(i));\n"
                + "    }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test(\"ISO-8859-1\");test(\"UTF8\");'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1044", "1044"})
    public void parseUtf8Encoding() {
        final String html = "<html>\n"
                + "  <head>\n"
                + "<script>\n"                + "  function test(encoding) {\n"
                + "    var text=\"<?xml version='1.0' encoding='\" + encoding + \"'?><body>Д</body>\";\n"
                + "    var doc=" + callLoadXMLDocumentFromString("text") + ";\n"
                + "    var value = doc.documentElement.firstChild.nodeValue;\n"
                + "    for (var i = 0; i < value.length; i++) {\n"
                + "     alert(value.charCodeAt(i));\n"
                + "    }\n"
                + "  }\n"
                + LOAD_XML_DOCUMENT_FROM_STRING_FUNCTION
                + "</script></head>\n"
                + "<body onload='test(\"UTF-8\");test(\"ISO-8859-1\");'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
