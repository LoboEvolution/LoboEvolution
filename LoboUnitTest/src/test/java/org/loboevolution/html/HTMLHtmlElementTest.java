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
package org.loboevolution.html;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLHtmlElement;

/**
 * Unit tests for {@link HTMLHtmlElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLHtmlElementTest extends LoboUnitTest {

    @Test
    @Alerts("[object HTMLHtmlElement]")
    public void simpleScriptable() {
        final String html = "<html id='myId'><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId'));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLHtmlElement]", "function HTMLHtmlElement() { [native code] }"})
    public void HTMLHtmlElementToString() {
        final String html = "<html id='myId'><head><script>\n"
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

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"2", "HEAD", "BODY", "null", "null"})
    public void childNodes1() {
        final String html = "<html> \n <body> \n <script>\n"
                + "var nodes = document.documentElement.childNodes;\n"
                + "alert(nodes.length);\n"
                + "alert(nodes[0].nodeName);\n"
                + "alert(nodes[1].nodeName);\n"
                + "alert(nodes[0].previousSibling);\n"
                + "alert(nodes[1].nextSibling);\n"
                + "</script> \n </body> \n </html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "HEAD"})
    public void childNodes2() {
        final String html = "<html> \n <head> \n <script>\n"
                + "var nodes = document.documentElement.childNodes;\n"
                + "alert(nodes.length);\n"
                + "alert(nodes[0].nodeName);\n"
                + "</script> \n </head> \n </html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true", "true"})
    public void clientWidth() {
        final String html = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"
                + " 'http://www.w3.org/TR/html4/loose.dtd'>"
                + "<html><head>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  var elt = document.body.parentNode;\n"
                + "  alert(elt.clientWidth > 0);\n"
                + "  alert(elt.clientWidth == window.innerWidth);\n"
                + "  alert(elt.clientHeight > 0);\n"
                + "  alert(elt.clientHeight == window.innerHeight);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Hello World")
    public void innerText() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    document.documentElement.innerText = 'Hello World';\n"
                + "    alert( document.documentElement.childNodes.item(0).data)\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"8", "16", "13", "0"})
    public void offsetsHtmlAbsoluteLeft() {
        offsetsHtml("position: absolute; left: 13px;");
    }


    @Test
    @Alerts({"8", "16", "1227", "0"})
    public void offsetsHtmlAbsoluteRight() {
        offsetsHtml("position: absolute; right: 13px;");
    }


    @Test
    @Alerts({"8", "16", "0", "0"})
    public void offsetsHtmlFixed() {
        offsetsHtml("position: fixed;");
    }


    @Test
    @Alerts({"8", "16", "1227", "0"})
    public void offsetsHtmlFixedRight() {
        offsetsHtml("position: fixed; right: 13px;");
    }

    private void offsetsHtml(final String style) {
        final String html = "<html id='my' style='" + style + "'>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "</div></body>\n"
                + "    <script>\n"
                + "function alertOffsets(elt) {\n"
                + "  alert(elt.offsetHeight);\n"
                + "  alert(elt.offsetWidth);\n"
                + "  alert(elt.offsetLeft);\n"
                + "  alert(elt.offsetTop);\n"
                + "}\n"
                + "alertOffsets(document.getElementById('my'));\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }
}
