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
 * Tests for PopStateEvent
 */
@ExtendWith(AlertsExtension.class)
public class PopStateEventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION =
            "  function dump(event) {\n"
                    + "    if (event) {\n"
                    + "     alert(event);\n"
                    + "     alert(event.target);\n"
                    + "     alert(event.type);\n"
                    + "     alert(event.bubbles);\n"
                    + "     alert(event.cancelable);\n"
                    + "     alert(event.composed);\n"
                    + "     alert(event.state);\n"
                    + "    } else {\n"
                    + "     alert('no event');\n"
                    + "    }\n"
                    + "  }\n";


    @Test
    @Alerts({"[object PopStateEvent]", "null", "popstate", "false", "false", "false", "null"})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new PopStateEvent('popstate');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object PopStateEvent]", "null", "popstate", "true", "false", "false", "2"})
    public void createCtorWithDetails() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new PopStateEvent('popstate', {\n"
                + "        'bubbles': true,\n"
                + "        'state': 2,\n"
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
    @Alerts({"[object PopStateEvent]", "null", "null", "false", "false", "false", "null"})
    public void createCreateEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('PopStateEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object PopStateEvent]", "null", "null", "false", "false", "false", "null"})
    public void setState() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('PopStateEvent');\n"
                + "      event.state = 'test';\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("dispatched")
    public void dispatchEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('PopStateEvent');\n"
                + "      event.initEvent('', true, true);\n"
                + "    } catch (e) {alert('exception ctor'); return; }\n"
                + "    try {\n"
                + "      dispatchEvent(event);\n"
                + "     alert('dispatched');\n"
                + "    } catch (e) {alert('exception' + e) }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "  try {\n"
                + "    window.addEventListener('popstate',dump);\n"
                + "  } catch (e) { }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void dispatchEventWithoutInit() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('PopStateEvent');\n"
                + "    } catch (e) {alert('exception'); return; }\n"
                + "    try {\n"
                + "      dispatchEvent(event);\n"
                + "     alert('dispatched');\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "  try {\n"
                + "    window.addEventListener('popstate',dump);\n"
                + "  } catch (e) { }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("no initPopStateEvent")
    public void initPopStateEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('PopStateEvent');\n"
                + "    } catch (e) {alert('exception ctor'); return }\n"
                + "    if (event.initPopStateEvent) {\n"
                + "      event.initPopStateEvent('PopState', true, false, 'html');\n"
                + "      dump(event);\n"
                + "    } else {alert('no initPopStateEvent'); }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
