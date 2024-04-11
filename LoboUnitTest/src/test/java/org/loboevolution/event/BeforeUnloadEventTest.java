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
 * Tests for BeforeUnloadEvent
 */
@ExtendWith(AlertsExtension.class)
public class BeforeUnloadEventTest extends LoboUnitTest {


    @Test
    @Alerts("exception")
    public void createCtor() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new BeforeUnloadEvent('beforeunload');\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object BeforeUnloadEvent]", "", "false", "false", "false", ""})
    public void createCreateEvent() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('BeforeUnloadEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception'); }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object BeforeUnloadEvent]", "beforeunload", "true", "false", "false", ""})
    public void initEvent() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('BeforeUnloadEvent');\n"
                + "      event.initEvent('beforeunload', true, false);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object BeforeUnloadEvent]", "beforeunload", "true", "false", "false", ""})
    public void dispatchEvent() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('BeforeUnloadEvent');\n"
                + "      event.initEvent('beforeunload', true, false);\n"
                + "      dispatchEvent(event);\n"
                + "    } catch (e) {alert('exception'); }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "  window.onbeforeunload  = dump;\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "beforeunload", "true", "false", "false", "true"})
    public void dispatchEventEvent() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('Event');\n"
                + "      event.initEvent('beforeunload', true, false);\n"
                + "      dispatchEvent(event);\n"
                + "    } catch (e) {alert('exception'); }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "  window.onbeforeunload = dump;\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("supported")
    public void onBeforeUnload_supported() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    if ('onbeforeunload' in window) {alert('supported') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
