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
 * Tests for HashChangeEvent
 */
@ExtendWith(AlertsExtension.class)
public class HashChangeEventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "    if (event) {\n"
            + "     alert(event);\n"
            + "     alert(event.type);\n"
            + "     alert(event.bubbles);\n"
            + "     alert(event.cancelable);\n"
            + "     alert(event.composed);\n"

            + "     alert(event.oldURL);\n"
            + "     alert(event.newURL);\n"
            + "    } else {\n"
            + "     alert('no event');\n"
            + "    }\n"
            + "  }\n";


    @Test
    @Alerts({"[object HashChangeEvent]", "hashchange", "false", "false", "false", "", ""})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new HashChangeEvent('hashchange');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HashChangeEvent]", "hashchange", "true", "false", "false", "null", "#1"})
    public void createCtorWithDetails() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new HashChangeEvent('hashchange', {\n"
                + "        'bubbles': true,\n"
                + "        'oldURL': null,\n"
                + "        'newURL': '" + URL_CSS + "#1'\n"
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
    @Alerts({"[object HashChangeEvent]", "null", "false", "false", "false", "", ""})
    public void createCreateEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('HashChangeEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HashChangeEvent]", "[object HashChangeEvent]",
            "hashchange", "true", "false", "false", "", "#1"})
    public void initHashChangeEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('HashChangeEvent');\n"
                + "     alert(event);\n"
                + "    } catch (e) {alert('exception createEvent'); return; }\n"
                + "    if (!event.initHashChangeEvent) {alert('missing initHashChangeEvent'); return;}\n"
                + "    try {\n"
                + "      event.initHashChangeEvent('hashchange', true, false, '" + URL_CSS + "', '"
                + URL_CSS + "#1');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception initHashChangeEvent') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HashChangeEvent]", "hashchange", "true", "false", "false", "", "#1"})
    public void dispatchEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('HashChangeEvent');\n"
                + "      event.initHashChangeEvent('hashchange', true, false, '" + URL_CSS + "', '"
                + URL_CSS + "#1');\n"
                + "      dispatchEvent(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "  window.onhashchange = dump;\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "hashchange", "true", "false", "false", "undefined", "undefined"})
    public void dispatchEventEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('Event');\n"
                + "      event.initEvent('hashchange', true, false);\n"
                + "      dispatchEvent(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "  window.onhashchange = dump;\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("supported")
    public void onHashChangeSupported() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    if ('onhashchange' in window) {alert('supported') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
