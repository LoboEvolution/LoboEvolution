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
package org.loboevolution.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for ProgressEvent
 */
@ExtendWith(AlertsExtension.class)
public class ProgressEventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "    if (event) {\n"
            + "     alert(event);\n"
            + "     alert(event.type);\n"
            + "     alert(event.bubbles);\n"
            + "     alert(event.cancelable);\n"
            + "     alert(event.composed);\n"
            + "     alert(event.lengthComputable);\n"
            + "     alert(event.loaded);\n"
            + "     alert(event.total);\n"
            + "    } else {\n"
            + "     alert('no event');\n"
            + "    }\n"
            + "  }\n";


    @Test
    @Alerts({"[object ProgressEvent]", "progress", "false", "false", "false", "false", "0", "0"})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new ProgressEvent('progress');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object ProgressEvent]", "test", "true", "false", "false", "true", "234", "666"})
    public void createCtorWithDetails() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new ProgressEvent('test', {\n"
                + "        'bubbles': true,\n"
                + "        'lengthComputable': true,\n"
                + "        'loaded': 234,\n"
                + "        'total': 666\n"
                + "      });\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createCreateEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('ProgressEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
