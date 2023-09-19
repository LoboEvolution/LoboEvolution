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
 * Tests for {@link org.loboevolution.html.dom.HTMLFieldSetElement}.
 */
public class HTMLFieldSetElementTest extends LoboUnitTest {

    /**
     * <p>getAlign.</p>
     */
    @Test
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <form>\n"
                + "    <fieldset id='f1' align='left' ></fieldset>\n"
                + "    <fieldset id='f2' align='right' ></fieldset>\n"
                + "    <fieldset id='f3' align='bottom' ></fieldset>\n"
                + "    <fieldset id='f4' align='middle' ></fieldset>\n"
                + "    <fieldset id='f5' align='top' ></fieldset>\n"
                + "    <fieldset id='f6' align='absbottom' ></fieldset>\n"
                + "    <fieldset id='f7' align='absmiddle' ></fieldset>\n"
                + "    <fieldset id='f8' align='baseline' ></fieldset>\n"
                + "    <fieldset id='f9' align='texttop' ></fieldset>\n"
                + "    <fieldset id='f10' align='wrong' ></fieldset>\n"
                + "    <fieldset id='f11' ></fieldset>\n"
                + "  </form>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 11; i++) {\n"
                + "    alert(document.getElementById('f' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages =  {"undefined", "undefined", "undefined", "undefined", "undefined", "undefined",
                "undefined", "undefined", "undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAlign.</p>
     */
    @Test
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <form>\n"
                + "    <fieldset id='i1' align='left' />\n"
                + "  <form>\n"

                + "<script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"

                + "  var elem = document.getElementById('i1');\n"
                + "  setAlign(elem, 'CenTer');\n"

                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"

                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'bottom');\n"
                + "  setAlign(elem, 'middle');\n"
                + "  setAlign(elem, 'top');\n"
                + "  setAlign(elem, 'absbottom');\n"
                + "  setAlign(elem, 'absmiddle');\n"
                + "  setAlign(elem, 'baseline');\n"
                + "  setAlign(elem, 'texttop');\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"CenTer", "8", "foo", "left", "right",
                "bottom", "middle", "top", "absbottom", "absmiddle", "baseline", "texttop"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>form.</p>
     */
    @Test
    public void form() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <fieldset id='a' />\n"
                + "  </form>"
                + "  <script>\n"
                + "    alert(document.getElementById('a').form);\n"
                + "  </script>"
                + "</body>"
                + "</html>";
        final String[] messages = {"[object HTMLFormElement]"};
        checkHtmlAlert(html, messages);
    }
}
