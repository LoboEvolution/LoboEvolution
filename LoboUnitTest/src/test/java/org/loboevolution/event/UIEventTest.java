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
 * Unit tests for UIEvent
 */
@ExtendWith(AlertsExtension.class)
public class UIEventTest extends LoboUnitTest {

    @Test
    @Alerts({"[object UIEvent]", "event", "false", "false", "false"})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new UIEvent('event');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert(e) }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object UIEvent]", "event", "true", "false", "false"})
    public void createCtorWithDetails() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new UIEvent('event', {\n"
                + "        'bubbles': true,\n"
                + "        'view': window\n"
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
    public void createCtorWithDetailsViewNotWindow() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new UIEvent('event', {\n"
                + "        'view': {}\n"
                + "      });\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"DOM2: [object UIEvent]", "DOM3: [object UIEvent]"})
    public void createEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert('DOM2: ' + document.createEvent('UIEvents'));\n"
                + "    } catch(e) {alert(e)}\n"
                + "    try {\n"
                + "     alert('DOM3: ' + document.createEvent('UIEvent'));\n"
                + "    } catch(e) {alert(e)}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object UIEvent]", "click", "true", "true", "true", "7"})
    public void initUIEvent() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  var e = document.createEvent('UIEvents');\n"
                + " alert(e);\n"
                + "  e.initUIEvent('click', true, true, window, 7);\n"
                + " alert(e.type);\n"
                + " alert(e.bubbles);\n"
                + " alert(e.cancelable);\n"
                + " alert(e.view == window);\n"
                + " alert(e.detail);\n"
                + "} catch(e) {alert('exception') }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "undefined", "[object PointerEvent]", "1"})
    public void detail() {
        final String html =
                "<html><head><script>\n"
                        + "  function alertDetail(e) {\n"
                        + "   alert(e);\n"
                        + "   alert(e.detail);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='alertDetail(event)'>\n"
                        + "  <div id='a' onclick='alertDetail(event)'>abc</div>\n"
                        + "  <div id='b' ondblclick='alertDetail(event)'>xyz</div>\n"
                        + "  <div id='c' oncontextmenu='alertDetail(event)'>xyz</div>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "undefined", "[object PointerEvent]", "1"})
    public void detailInputText() {
        final String html =
                "<html><head><script>\n"
                        + "  function alertDetail(e) {\n"
                        + "   alert(e);\n"
                        + "   alert(e.detail);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='alertDetail(event)'>\n"
                        + "  <input type='text' id='a' onclick='alertDetail(event)'>\n"
                        + "  <input type='text' id='b' ondblclick='alertDetail(event)'>\n"
                        + "  <input type='text' id='c' oncontextmenu='alertDetail(event)'>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "undefined", "[object PointerEvent]", "1"})
    public void detailInputRadio() {
        final String html =
                "<html><head><script>\n"

                        + "  function alertDetail(e) {\n"
                        + "   alert(e);\n"
                        + "   alert(e.detail);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='alertDetail(event)'>\n"
                        + "  <input type='radio' id='a' onclick='alertDetail(event)'>\n"
                        + "  <input type='radio' id='b' ondblclick='alertDetail(event)'>\n"
                        + "  <input type='radio' id='c' oncontextmenu='alertDetail(event)'>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "undefined", "[object MouseEvent]", "[object Window]"})
    public void view() {
        final String html =
                "<html><body onload='alertView(event)'><script>\n"
                        + "  function alertView(e) {\n"
                        + "   alert(e);\n"
                        + "   alert(e.view);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "<form><input type='button' id='b' onclick='alertView(event)'></form>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }
}
