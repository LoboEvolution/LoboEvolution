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
 * Tests for MessageEvent
 */
@ExtendWith(AlertsExtension.class)
public class MessageEventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "    if (event) {\n"
            + "     alert(event);\n"
            + "     alert(event.type);\n"
            + "     alert(event.bubbles);\n"
            + "     alert(event.cancelable);\n"
            + "     alert(event.composed);\n"
            + "     alert(event.data);\n"
            + "     alert(event.origin);\n"
            + "     alert(event.lastEventId);\n"
            + "     alert(event.source);\n"
            + "    } else {\n"
            + "     alert('no event');\n"
            + "    }\n"
            + "  }\n";


    @Test
    @Alerts({"[object MessageEvent]", "type-message", "false", "false", "false",
            "null", "", "", "null"})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new MessageEvent('type-message');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception'); }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MessageEvent]", "type-message", "false", "false", "false",
            "test-data", "test-origin", "42", "[object Window]"})
    public void createCtorWithDetails() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new MessageEvent('type-message', {\n"
                + "        'data': 'test-data',\n"
                + "        'origin': 'test-origin',\n"
                + "        'lastEventId': 42,\n"
                + "        'source': window\n"
                + "      });\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception'); }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"DOM2: exception", "DOM3: [object MessageEvent]"})
    public void createEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert('DOM2: ' + document.createEvent('MessageEvents'));\n"
                + "    } catch(e) {alert('DOM2: exception')}\n"
                + "    try {\n"
                + "     alert('DOM3: ' + document.createEvent('MessageEvent'));\n"
                + "    } catch(e) {alert('DOM3: exception')}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
