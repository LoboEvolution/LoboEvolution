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
 * Tests for css pseudo selectors :placeholder-shown and :-ms-input-placeholder.
 */
@ExtendWith(AlertsExtension.class)
public class CSSSelector2Test extends LoboUnitTest {

    private static final String PLACEHOLDER_SHOWN_HTML_HEAD = "<html><head>\n"
            + "<style>:placeholder-shown {border: 10px solid;}</style>\n"
            + "<script>\n"            + "  function test() {\n"

            + "    try {\n"
            + "     alert(document.querySelector(':placeholder-shown'));\n"
            + "    } catch (exception) {\n"
            + "     alert(exception.name);\n"
            + "    }\n"
            + "  }\n"
            + "</script>\n"
            + "</head>\n";

    @Test
    @Alerts("[object HTMLInputElement]")
    public void placeholderShown() {
        final String html = PLACEHOLDER_SHOWN_HTML_HEAD
                + "<body onload='test();'>\n"
                + "<form>\n"
                + "  <input placeholder='htmlUnit supports placeholder-shown'>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLInputElement]")
    public void placeholderShownNumber() {
        final String html = PLACEHOLDER_SHOWN_HTML_HEAD
                + "<body onload='test();'>\n"
                + "<form>\n"
                + "  <input type='number' placeholder='200'>\n"
                + "</form></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLInputElement]")
    public void placeholderShownDisplayNone() {
        final String html = PLACEHOLDER_SHOWN_HTML_HEAD
                + "<body onload='test();'>\n"
                + "<form style='display:none;'>\n"
                + "  <input placeholder='htmlUnit supports placeholder-shown'>\n"
                + "</form></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void placeholderShownHasValue() {
        final String html = PLACEHOLDER_SHOWN_HTML_HEAD
                + "<body onload='test();'>\n"
                + "<form style='display:none;'>\n"
                + "  <input placeholder='htmlUnit supports placeholder-shown' value='dont show placeholder'>\n"
                + "</form></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void placeholderShownNoInput() {
        final String html = PLACEHOLDER_SHOWN_HTML_HEAD
                + "<body onload='test();'>\n"
                + "<form style='display:none;'>\n"
                + "  <div placeholder='htmlUnit supports placeholder-shown' value='dont show placeholder'>\n"
                + "</form></body></html>";
        checkHtmlAlert(html);
    }
}
