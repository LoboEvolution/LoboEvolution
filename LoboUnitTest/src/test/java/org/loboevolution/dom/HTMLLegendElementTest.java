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
 * Tests for {@link org.loboevolution.html.dom.HTMLLegendElement}.
 */
public class HTMLLegendElementTest extends LoboUnitTest {

    /**
     * <p>accessKey.</p>
     */
    @Test
    public void accessKey() {
        final String html
                = "<html><body><legend id='a1'>a1</legend><legend id='a2' accesskey='A'>a2</legend><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = 'a';\n"
                + "a2.accessKey = 'A';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = 'a8';\n"
                + "a2.accessKey = '8Afoo';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "a1.accessKey = '8';\n"
                + "a2.accessKey = '@';\n"
                + "alert(a1.accessKey);\n"
                + "alert(a2.accessKey);\n"
                + "</script></body></html>";
        final String[] messages = {"", "A", "a", "A", "a8", "8Afoo", "8", "@"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>form.</p>
     */
    @Test
    public void form() {
        final String html
                = "<html><body><form><fieldset><legend id='a'>a</legend></fieldset></form><script>\n"
                + "alert(document.getElementById('a').form);\n"
                + "</script></body></html>";
        final String[] messages = {"[object HTMLFormElement]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAlign.</p>
     */
    @Test
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <form><fieldset>\n"
                + "    <legend id='i1' align='left' ></legend>\n"
                + "    <legend id='i2' align='right' ></legend>\n"
                + "    <legend id='i3' align='bottom' ></legend>\n"
                + "    <legend id='i4' align='top' ></legend>\n"
                + "    <legend id='i5' align='wrong' ></legend>\n"
                + "    <legend id='i6' ></legend>\n"
                + "  </fieldset></form>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 6; i++) {\n"
                + "    alert(document.getElementById('i' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"left", "right", "bottom", "top", "wrong", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAlign.</p>
     */
    @Test
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <form><fieldset>\n"
                + "    <legend id='i1' align='left' ></legend>\n"
                + "  </fieldset></form>\n"

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
                + "  setAlign(elem, 'top');\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"CenTer", "8", "foo", "left", "right", "bottom", "top"};
        checkHtmlAlert(html, messages);
    }
}
