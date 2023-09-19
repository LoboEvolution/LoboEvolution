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
package org.loboevolution.css;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.js.css.MediaQueryListImpl;

/**
 * Tests for {@link MediaQueryListImpl}.
 */
public class MediaQueryListTest extends LoboUnitTest {

    @Test
    public void matches() {
        final String html
                = "<html><head><script>\n"
                + "  function test() {\n"
                + "    if (window.matchMedia) {\n"
                + "      alert(window.matchMedia('(min-width: 400px)').matches);\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void listener() {
        final String html
                = "<html><head><script>\n"
                + "  function listener(mql) {\n"
                + "    alert(mql);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    if (window.matchMedia) {\n"
                + "      var mql = window.matchMedia('(min-width: 400px)');\n"
                + "      mql.addListener(listener);\n"
                + "      alert('added');\n"
                + "      mql.removeListener(listener);\n"
                + "      alert('removed');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"added", "removed"};
        checkHtmlAlert(html, messages);
    }
}
