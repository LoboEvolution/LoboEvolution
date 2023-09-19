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
 * Tests for {@link org.loboevolution.html.dom.HTMLOptionElement}.
 */
public class HTMLOptionElementTest extends LoboUnitTest {

    /**
     * <p>label.</p>
     */
    @Test
    public void label() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  var s = document.getElementById('testSelect');\n"
                + "  var lastIndex = s.length;\n"
                + "  s.length += 1;\n"
                + "  s[lastIndex].value = 'value2';\n"
                + "  s[lastIndex].text  = 'text2';\n"
                + "  alert(s[0].value);\n"
                + "  alert(s[0].text);\n"
                + "  alert(s[0].label);\n"
                + "  alert(s[1].value);\n"
                + "  alert(s[1].text);\n"
                + "  alert(s[1].label);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <select id='testSelect'>\n"
                + "    <option value='value1' label='label1'>text1</option>\n"
                + "  </select>\n"
                + "</form>\n"
                + "</body></html>";

        final String[] messages = {"value1", "text1", "label1", "value2", "text2", "text2"};
        checkHtmlAlert(html, messages);
    }
}
