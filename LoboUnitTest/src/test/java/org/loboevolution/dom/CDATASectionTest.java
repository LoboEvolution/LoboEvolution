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
import org.loboevolution.html.node.CDATASection;

/**
 * Tests for {@link CDATASection}.
 */
@ExtendWith(AlertsExtension.class)
public class CDATASectionTest extends LoboUnitTest {


    @Test
    @Alerts("[object Comment]")
    public void simpleScriptable() {
        final String html
                = "<html><head>\n"
                + "<script>\n" 
                + "function test() {\n"
                + " alert(document.body.firstChild);\n"
                + "}\n"
                + "</script></head><body onload='test()'><![CDATA[Jeep]]></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("2")
    public void splitText() {
        final String html = "<html><head>\n"
                + "<script>"
                +  " function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var root = doc.appendChild(doc.createElement('root'));\n"
                + "    var cdata = root.appendChild(doc.createCDATASection('abcdef'));\n"
                + "    cdata.splitText(2);\n"
                + "   alert(root.childNodes.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

}
