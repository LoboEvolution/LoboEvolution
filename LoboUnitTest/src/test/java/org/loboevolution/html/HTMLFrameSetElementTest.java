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

/**
 * Unit tests for HTMLFrameSetElement.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLFrameSetElementTest extends LoboUnitTest {

    @Test
    @Alerts({"20%,*", "*,*"})
    public void cols() {
        final String html =
                "<html><head>\n"
                        + "<script>"
                        + "function test() {\n"
                        + "  alert(document.getElementById('fs').cols);\n"
                        + "  document.getElementById('fs').cols = '*,*';\n"
                        + "  alert(document.getElementById('fs').cols);\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<frameset id='fs' cols='20%,*' onload='test()'>\n"
                        + "  <frame name='left' src='about:blank' />\n"
                        + "  <frame name='right' src='about:blank' />\n"
                        + "</frameset>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<frameset id=\"fs\" onload=\"test()\"> </frameset>", "new"})
    public void outerHTML() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "function test() {\n"
                        + "  alert(document.getElementById('fs').outerHTML);\n"
                        + "  document.getElementById('fs').outerHTML = '<div id=\"new\">text<div>';\n"
                        + "  alert(document.getElementById('new').id);\n"
                        + "}\n"
                        + "</script></head>\n"
                        + "<frameset id='fs' onload='test()'>\n"
                        + "</frameset>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
