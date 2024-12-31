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
package org.loboevolution.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for EventHandler
 */
@ExtendWith(AlertsExtension.class)
public class EventHandlerTest extends LoboUnitTest {

    @Test
    @Alerts({"function onload(event) { test() }",
            "function onload(event) { test() }",
            "function onload(event) { test() }"})
    public void testToString() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var e = test.caller;\n"
                + "   alert(e);\n"
                + "   alert('' + e);\n"
                + "   alert(e.toString());\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function onload(event) { test() }",
            "function onload(event) { test() }",
            "function onload(event) { test() }"})
    public void testToStringWhitespace() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var e = test.caller;\n"
                + "   alert(e);\n"
                + "   alert('' + e);\n"
                + "   alert(e.toString());\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='  test() \t \n'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function onload(event) { test() // comment }",
            "function onload(event) { test() // comment }",
            "function onload(event) { test() // comment }"})
    public void testToStringCommentAtEnd() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var e = test.caller;\n"
                + "   alert(e);\n"
                + "   alert('' + e);\n"
                + "   alert(e.toString());\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test() // comment'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
