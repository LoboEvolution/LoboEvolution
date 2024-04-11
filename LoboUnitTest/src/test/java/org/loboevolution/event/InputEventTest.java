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
 * Tests for InputEvent
 */
@ExtendWith(AlertsExtension.class)
public class InputEventTest extends LoboUnitTest {

    @Test
    @Alerts({"[object InputEvent]", "type", "false", "false", "false", ",,false"})
    public void createCtor() {
        final String html =
                "<html><head><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent('type');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "undefined", "false", "false", "false", ",,false"})
    public void createCtorWithoutType() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent();\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "42", "false", "false", "false", ",,false"})
    public void createCtorNumericType() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent(42);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "null", "false", "false", "false", ",,false"})
    public void createCtorNullType() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent(null);\n"
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
                + "      var event = new InputEvent(unknown);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "HtmlUnitEvent", "false", "false", "false", ",,false"})
    public void createCtorArbitraryType() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent('HtmlUnitEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "input", "false", "false", "false",
            "data,inputType,true"})
    public void createCtorAllDetails() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent('input', "
                + "{ inputType: 'inputType', data: 'data', isComposing: true });\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "input", "false", "false", "false", ",,true"})
    public void createCtorSomeDetails() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent('input', "
                + "{ isComposing: true });\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "input", "false", "false", "false", ",,false"})
    public void createCtorMissingData() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent('input', {\n"
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
    @Alerts({"[object InputEvent]", "input", "false", "false", "false", ",,false"})
    public void createCtorNullData() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent('input', null);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "input", "false", "false", "false", ",,false"})
    public void createCtorUndefinedData() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent('input', undefined);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object InputEvent]", "input", "false", "false", "false", "Html,Unit,,false"})
    public void createCtorWrongData() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new InputEvent('input', {\n"
                + "        'data': ['Html', 'Unit']\n"
                + "      });\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
