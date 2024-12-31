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
 * Tests for BeforeUnloadEvent.
 */
@ExtendWith(AlertsExtension.class)
public class BeforeUnloadEvent2Test extends LoboUnitTest {


    @Test
    @Alerts("Second")
    public void nothing() {
        onbeforeunload("");
    }

    @Test
    @Alerts("First")
    public void setString() {
        onbeforeunload("e.returnValue = 'Hello'");
    }

    @Test
    @Alerts("First")
    public void setNull() {
        onbeforeunload("e.returnValue = null");
    }

    @Test
    @Alerts("First")
    public void returnString() {
        onbeforeunload("return 'Hello'");
    }

    @Test
    @Alerts("Second")
    public void returnNull() {
        onbeforeunload("return null");
    }

    private void onbeforeunload(final String functionBody) {
        final String html = "<html><head><title>First</title><script>\n"
                + "  window.onbeforeunload = function (e) {\n"
                + "    " + functionBody + ";\n"
                + "  }\n"
                + "</script></head><body>\n"
                + "  <a href='" + URL_SECOND + "'>Click Here</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("First")
    public void setNullReturnString() {
        onbeforeunload("e.returnValue = null;\n"
                + "return 'Hello'");
    }

    @Test
    @Alerts("First")
    public void setStringReturnNull() {
        onbeforeunload("e.returnValue = 'Hello';\n"
                + "return null");
    }

    @Test
    @Alerts("First")
    public void setNullReturnNull() {
        onbeforeunload("e.returnValue = null;\n"
                + "return null");
    }

    @Test
    @Alerts("First")
    public void setStringReturnString() {
        onbeforeunload("e.returnValue = 'Hello';\n"
                + "return 'Hello'");
    }
}
