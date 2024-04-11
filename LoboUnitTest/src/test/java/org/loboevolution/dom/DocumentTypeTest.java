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
import org.loboevolution.html.node.DocumentType;

/**
 * Tests for {@link DocumentType}.
 */
@ExtendWith(AlertsExtension.class)
public class DocumentTypeTest extends LoboUnitTest {


    @Test
    @Alerts({"[object DocumentType]", "true", "html,10,null,undefined,undefined,undefined",
            "html,-//W3C//DTD XHTML 1.0 Strict//EN,http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd,"
                    + "undefined,undefined,undefined"})
    public void doctype() {
        final String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
                + "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var t = document.doctype;\n"
                + "     alert(t);\n"
                + "      if (t != null) {\n"
                + "       alert(t == document.firstChild);\n"
                + "       alert(t.nodeName + ',' + t.nodeType + ',' + t.nodeValue + ',' + t.prefix "
                + "+ ',' + t.localName + ',' + t.namespaceURI);\n"
                + "       alert(t.name + ',' + t.publicId + ',' + t.systemId + ',' + t.internalSubset"
                + " + ',' + t.entities + ',' + t.notations);\n"
                + "      }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object DocumentType]", "greeting,10,null,undefined,undefined,undefined",
            "greeting,MyIdentifier,hello.dtd,undefined,undefined,undefined"})
    public void doctypeXml() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"

                        + "        var request = new XMLHttpRequest();\n"
                        + "        request.open('GET', 'foo.xml', false);\n"
                        + "        request.send('');\n"
                        + "        var doc = request.responseXML;\n"
                        + "        var t = doc.doctype;\n"
                        + "       alert(t);\n"
                        + "        if (t != null) {\n"
                        + "         alert(t.nodeName + ',' + t.nodeType + ',' + t.nodeValue + ',' + t.prefix "
                        + "+ ',' + t.localName + ',' + t.namespaceURI);\n"
                        + "         alert(t.name + ',' + t.publicId + ',' + t.systemId + ',' + t.internalSubset"
                        + " + ',' + t.entities + ',' + t.notations);\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("undefined")
    public void htmlPreviousSibling() {
        final String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
                + "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (document.body.parentElement) {\n"
                + "        //.text is defined for Comment in IE\n"
                + "       alert(typeof document.body.parentElement.previousSibling.text);\n"
                + "        }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object DocumentType]", "[object HTMLHtmlElement]"})
    public void documentChildren() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      for (var elem = document.firstChild; elem; elem = elem.nextSibling) {\n"
                + "       alert(elem);\n"
                + "      }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }
}
