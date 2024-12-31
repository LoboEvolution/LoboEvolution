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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.traversal.NodeFilter;

/**
 * Tests for {@link NodeFilter}.
 */
@ExtendWith(AlertsExtension.class)
public class NodeFilterTest extends LoboUnitTest {


    @Test
    @Alerts({"1", "2", "3", "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048"})
    public void constants() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  var properties = ['FILTER_ACCEPT', 'FILTER_REJECT', 'FILTER_SKIP',\n"
                + "'SHOW_ELEMENT', 'SHOW_ATTRIBUTE', 'SHOW_TEXT', 'SHOW_CDATA_SECTION',\n"
                + "'SHOW_ENTITY_REFERENCE', 'SHOW_ENTITY', 'SHOW_PROCESSING_INSTRUCTION', 'SHOW_COMMENT',\n"
                + "'SHOW_DOCUMENT', 'SHOW_DOCUMENT_TYPE', 'SHOW_DOCUMENT_FRAGMENT', 'SHOW_NOTATION'];\n"
                + "  try {\n"
                + "    for (var i = 0; i < properties.length; i++) {\n"
                + "     alert(NodeFilter[properties[i]]);\n"
                + "    }\n"
                + "  } catch(e) {alert('exception');}\n"
                + "</script></head>\n"
                + "<body></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("4294967295")
    public void constants_SHOW_ALL() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  try {\n"
                + "   alert(NodeFilter.SHOW_ALL);\n"
                + "  } catch(e) {alert('exception');}\n"
                + "</script></head>\n"
                + "<body></body></html>";

        checkHtmlAlert(html);
    }
}
