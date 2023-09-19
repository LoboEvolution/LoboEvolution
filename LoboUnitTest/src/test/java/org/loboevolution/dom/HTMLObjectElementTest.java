/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLObjectElement}.
 */
public class HTMLObjectElementTest extends LoboUnitTest {

    /**
     * <p>form.</p>
     */
    @Test
    public void form() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <object id='o1'></object>\n"
                + "</form>\n"
                + "<object id='o2'></object>\n"
                + "<script>\n"
                + "  alert(document.getElementById('o1').form);\n"
                + "  alert(document.getElementById('o2').form);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"[object HTMLFormElement]", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>responseXML_htmlObject.</p>
     */
    @Test
    public void responseXML_htmlObject() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var xhr = new XMLHttpRequest();\n"
                + "    xhr.open('GET', 'foo.xml', false);\n"
                + "    xhr.send('');\n"
                + "    try {\n"
                + "      alert(xhr.responseXML);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String xml = "<html xmlns='http://www.w3.org/1999/xhtml'>\n"
                + "<object classid='CLSID:test'/>\n"
                + "</html>";
        final String[] messages = {"[object XMLDocument]"};
        checkHtmlAlert(html, messages);
    }
}
