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

/**
 * Tests for ComputedCursorTest
 */
public class ComputedCursorTest  extends LoboUnitTest {

    @Test
    public void cursor() {
        final String html = "<html><head>\n" +
                " <script>\n" +
                " function test_computed_value(property, specified) {\n" +
                "   const target = document.getElementById('target');\n" +
                "   target.style.cursor = '';\n" +
                "   target.style.cursor = specified;\n" +
                "   let readValue = getComputedStyle(target).cursor;\n" +
                "   alert(readValue == specified);\n" +
                " } \n" +
                "  function test() {\n" +
                "   test_computed_value('cursor', 'auto');\n" +
                "   test_computed_value('cursor', 'pointer');\n" +
                "   test_computed_value('cursor', 'progress');\n" +
                "   test_computed_value('cursor', 'crosshair');\n" +
                "   test_computed_value('cursor', 'text');\n" +
                "   test_computed_value('cursor', 'move');\n" +
                "   test_computed_value('cursor', 'e-resize');\n" +
                "   test_computed_value('cursor', 'n-resize');\n" +
                "   test_computed_value('cursor', 'ne-resize');\n" +
                "   test_computed_value('cursor', 'nw-resize');\n" +
                "   test_computed_value('cursor', 's-resize');\n" +
                "   test_computed_value('cursor', 'se-resize');\n" +
                "   test_computed_value('cursor', 'sw-resize');\n" +
                "   test_computed_value('cursor', 'w-resize');\n" +
                "   test_computed_value('cursor', 'zoom-in');\n" +
                "   test_computed_value('cursor', 'zoom-out');\n" +
                "  }\n" +
                " </script>\n" +
                " </head>\n" +
                " <body onload='test()'> <div id=target></div>  \n" +
                " </body></hml>";
        final String[] messages = {"true", "true", "true", "true", "true", "true", "true", "true",
                                   "true", "true", "true", "true", "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }
}
