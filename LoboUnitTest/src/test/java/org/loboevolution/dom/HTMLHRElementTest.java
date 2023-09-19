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
 * Tests for {@link org.loboevolution.html.dom.HTMLHRElement}.
 */
public class HTMLHRElementTest extends LoboUnitTest {

    /**
     * <p>getAlign.</p>
     */
    @Test
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <hr id='h1' align='left' />\n"
                + "  <hr id='h2' align='right' />\n"
                + "  <hr id='h3' align='center' />\n"
                + "  <hr id='h4' align='wrong' />\n"
                + "  <hr id='h5' />\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 5; i++) {\n"
                + "    alert(document.getElementById('h' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"left", "right", "center", "wrong", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAlign.</p>
     */
    @Test
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <hr id='h1' align='left' />\n"

                + "<script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"

                + "  var elem = document.getElementById('h1');\n"
                + "  setAlign(elem, 'CenTer');\n"

                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"

                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'center');\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"CenTer", "8", "foo", "left", "right", "center"};
        checkHtmlAlert(html, messages);
    }
}
