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
 * Tests for CustomEvent
 */
@ExtendWith(AlertsExtension.class)
public class CustomEventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "   alert(event);\n"
            + "   alert(event.type);\n"
            + "   alert(event.bubbles);\n"
            + "   alert(event.cancelable);\n"
            + "   alert(event.composed);\n"
            + "   alert(event.detail);\n"
            + "  }\n";


    @Test
    @Alerts({"[object CustomEvent]", "my", "false", "false", "false", "null"})
    public void createCtor() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new CustomEvent('my');\n"
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
    public void createCtorWithoutType() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new CustomEvent();\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CustomEvent]", "42", "false", "false", "false", "null"})
    public void createCtorNumericType() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new CustomEvent(42);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CustomEvent]", "null", "false", "false", "false", "null"})
    public void createCtorNullType() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new CustomEvent(null);\n"
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
    public void createCtorUnknownType() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new CustomEvent(unknown);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CustomEvent]", "HtmlUnitEvent", "false", "false", "false", "null"})
    public void createCtorArbitraryType() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new CustomEvent('HtmlUnitEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CustomEvent]", "click", "false", "false", "false", "abcd"})
    public void createCtorAllDetails() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new CustomEvent('click', {\n"
                + "        'detail': 'abcd'"
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
    @Alerts("function")
    public void initCustomEvent() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var e = document.createEvent('CustomEvent');\n"
                + "     alert(typeof e.initCustomEvent);\n"
                + "    } catch(e) {alert('exception')}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "details", "I was here"})
    public void dispatchEvent() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  var listener = function(x) {\n"
                        + "   alert(x == myEvent);\n"
                        + "   alert(x.detail);\n"
                        + "    x.foo = 'I was here';\n"
                        + "  }\n"
                        + "  document.addEventListener('HTMLImportsLoaded', listener);\n"

                        + "  var myEvent = document.createEvent('CustomEvent');\n"
                        + "  myEvent.initCustomEvent('HTMLImportsLoaded', true, true, 'details');\n"
                        + "  document.dispatchEvent(myEvent);\n"
                        + " alert(myEvent.foo);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head><body onload='test()'>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "details", "I was here"})
    public void dispatchEventOnDomText() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  var listener = function(x) {\n"
                        + "   alert(x == myEvent);\n"
                        + "   alert(x.detail);\n"
                        + "    x.foo = 'I was here';\n"
                        + "  }\n"
                        + "  var txt = document.getElementById('myDiv').firstChild;\n"
                        + "  txt.addEventListener('MyEvent', listener);\n"

                        + "  var myEvent = document.createEvent('CustomEvent');\n"
                        + "  myEvent.initCustomEvent('MyEvent', true, true, 'details');\n"
                        + "  txt.dispatchEvent(myEvent);\n"
                        + " alert(myEvent.foo);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='myDiv'>Hallo HtmlUnit</div>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }
}
