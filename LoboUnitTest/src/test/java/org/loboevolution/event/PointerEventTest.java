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
 * Tests for PointerEvent
 */
@ExtendWith(AlertsExtension.class)
public class PointerEventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "    if (event) {\n"
            + "     alert(event);\n"
            + "     alert(event.type);\n"
            + "     alert(event.bubbles);\n"
            + "     alert(event.cancelable);\n"
            + "     alert(event.composed);\n"

            + "     alert(event.pointerId);\n"
            + "     alert(event.width);\n"
            + "     alert(event.height);\n"
            + "     alert(event.pressure);\n"
            + "     alert(event.tiltX);\n"
            + "     alert(event.tiltY);\n"
            + "     alert(event.pointerType);\n"
            + "     alert(event.isPrimary);\n"
            + "     alert(event.altitudeAngle);\n"
            + "     alert(event.azimuthAngle);\n"
            + "    } else {\n"
            + "     alert('no event');\n"
            + "    }\n"
            + "  }\n";


    @Test
    @Alerts({"[object PointerEvent]", "click", "false", "false", "false",
            "0", "1", "1", "0", "0", "0", "", "false", "1.5707963267948966", "0"})
    public void createCtor() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new PointerEvent('click');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object PointerEvent]", "click", "true", "false", "false",
            "2", "1", "1", "0", "0", "0", "mouse", "false", "1.5707963267948966", "0"})
    public void createCtorWithDetails() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new PointerEvent('click', {\n"
                + "        'bubbles': true,\n"
                + "        'pointerId': 2,\n"
                + "        'pointerType': 'mouse'\n"
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
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('PointerEvent');\n"
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
    public void initPointerEvent() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('PointerEvent');\n"
                + "      event.initPointerEvent('click', true, false, window, 3, 10, 11, 12, 13, true, true, true, false, "
                + "0, null, 14, 15, 4, 5, 6, 16, 17, 18, 123, 'mouse', 987, false);\n"
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
    public void dispatchEvent() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('PointerEvent');\n"
                + "      event.initPointerEvent('click', true, false, window, 3, 10, 11, 12, 13, true, true, true, false, "
                + "0, null, 14, 15, 4, 5, 6, 16, 17, 18, 123, 'mouse', 987, false);\n"
                + "      dispatchEvent(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "  try {\n"
                + "    window.addEventListener('click',dump);\n"
                + "  } catch (e) { }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
