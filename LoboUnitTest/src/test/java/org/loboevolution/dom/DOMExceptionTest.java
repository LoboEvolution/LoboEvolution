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

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link DOMException}.
 */
@ExtendWith(AlertsExtension.class)
public class DOMExceptionTest extends LoboUnitTest {


    @Test
    @Alerts({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"})
    public void constants() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  var properties = ['INDEX_SIZE_ERR', 'DOMSTRING_SIZE_ERR', 'HIERARCHY_REQUEST_ERR',"
                + " 'WRONG_DOCUMENT_ERR', 'INVALID_CHARACTER_ERR', 'NO_DATA_ALLOWED_ERR', 'NO_MODIFICATION_ALLOWED_ERR',"
                + " 'NOT_FOUND_ERR', 'NOT_SUPPORTED_ERR', 'INUSE_ATTRIBUTE_ERR', 'INVALID_STATE_ERR', 'SYNTAX_ERR',"
                + " 'INVALID_MODIFICATION_ERR', 'NAMESPACE_ERR', 'INVALID_ACCESS_ERR'];\n"
                + "  try {\n"
                + "    for (var i = 0; i < properties.length; i++) {\n"
                + "     alert(DOMException[properties[i]]);\n"
                + "    }\n"
                + "  } catch(e) {alert('exception');}\n"
                + "</script></head>\n"
                + "<body></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined"})
    public void properties() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  try {\n"
                + "   alert(DOMException.code);\n"
                + "   alert(DOMException.filename);\n"
                + "   alert(DOMException.lineNumber);\n"
                + "   alert(DOMException.message);\n"
                + "  } catch(e) {alert('exception');}\n"
                + "</script></head>\n"
                + "<body></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "true", "undefined", "undefined", "HIERARCHY_REQUEST_ERR: 3", "1"})
    public void appendChildIllegalNode() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var htmlNode = document.documentElement;\n"
                + "  var body = document.body;\n"
                + "  try {\n"
                + "    body.appendChild(htmlNode);\n"
                + "  } catch(e) {\n"
                + "   alert(e.code);\n"
                + "   alert(e.message != null);\n"
                + "   alert(e.lineNumber);\n"
                + "   alert(e.filename);\n"
                + "   alert('HIERARCHY_REQUEST_ERR: ' + e.HIERARCHY_REQUEST_ERR);\n"
                + "  }\n"
                + " alert(body.childNodes.length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'><span>hi</span></body></html>";
        checkHtmlAlert(html);
    }
}
