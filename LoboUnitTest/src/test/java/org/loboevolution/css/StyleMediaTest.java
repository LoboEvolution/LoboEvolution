/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for StyleMedia.
 */
@ExtendWith(AlertsExtension.class)
public class StyleMediaTest extends LoboUnitTest {


    @Test
    @Alerts({"[object StyleMedia]", "screen"})
    public void type() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "   alert(window.styleMedia);\n"
                + "    if (window.styleMedia) {\n"
                + "     alert(window.styleMedia.type);\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true", "false"})
    public void matchMedium() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    if (window.styleMedia) {\n"
                + "     alert(window.styleMedia.matchMedium('screen'));\n"
                + "     alert(window.styleMedia.matchMedium('SCREEN'));\n"
                + "     alert(window.styleMedia.matchMedium('screen, handheld'));\n"
                + "     alert(window.styleMedia.matchMedium('handheld'));\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
