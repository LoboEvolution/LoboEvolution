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
 * Tests for {@link org.loboevolution.html.dom.HTMLHtmlElement}.
 */
public class HTMLHtmlElementTest extends LoboUnitTest {

   @Test
    public void HTMLHtmlElement_toString() {
        final String html = "<html id='myId'><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.getElementById('myId'));\n"
                + "      alert(HTMLHtmlElement);\n"
                + "    } catch (e) {\n"
                + "      alert('exception');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object HTMLHtmlElement]", "function HTMLHtmlElement() {\n    [native code]\n}"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>childNodes_1.</p>
     */
    @Test
    public void childNodes_1() {
        final String html = "<html> \n <body> \n <script>\n"
                + "var nodes = document.documentElement.childNodes;\n"
                + "alert(nodes.length);\n"
                + "alert(nodes[0].nodeName);\n"
                + "alert(nodes[1].nodeName);\n"
                + "alert(nodes[0].previousSibling);\n"
                + "alert(nodes[1].nextSibling);\n"
                + "</script> \n </body> \n </html>";
        final String[] messages =  {"2", "HEAD", "BODY", null, null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>childNodes_2.</p>
     */
    @Test
    public void childNodes_2() {
        // The whitespace in this HTML is very important, because we're verifying
        // that it doesn't get included in the childNodes collection.
        final String html = "<html> \n <head> \n <script>\n"
                + "var nodes = document.documentElement.childNodes;\n"
                + "alert(nodes.length);\n"
                + "alert(nodes[0].nodeName);\n"
                + "</script> \n </head> \n </html>";
        final String[] messages = {"1", "HEAD"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientWidth.</p>
     */
    @Test
    public void clientWidth() {
        final String html = "html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var elt = document.body.parentNode;\n"
                + "  alert(elt.clientWidth > 0);\n"
                + "  alert(elt.clientWidth == window.innerWidth);\n"
                + "  alert(elt.clientHeight > 0);\n"
                + "  alert(elt.clientHeight == window.innerHeight);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }
}
