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
 * Tests for {@link org.loboevolution.html.dom.HTMLMapElement}.
 */
public class HTMLMapElementTest extends LoboUnitTest {

    /**
     * <p>areas.</p>
     */
    @Test
    public void areas() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var map = document.createElement('map');\n"
                + "      var area0 = document.createElement('area');\n"
                + "      var area1 = document.createElement('area');\n"
                + "      var area2 = document.createElement('area');\n"
                + "      map.appendChild(area0);\n"
                + "      map.appendChild(area1);\n"
                + "      map.appendChild(area2);\n"
                + "      var areaElems = map.areas;\n"
                + "      alert(areaElems.length);\n"
                + "      alert(area0 === areaElems[0]);\n"
                + "      alert(area1 === areaElems[1]);\n"
                + "      alert(area2 === areaElems[2]);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"3", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }
}
