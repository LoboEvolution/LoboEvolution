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
 * Tests for KeyboardEvent
 */
@ExtendWith(AlertsExtension.class)
public class KeyboardEventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "   alert(event);\n"
            + "   alert(event.type);\n"
            + "   alert(event.bubbles);\n"
            + "   alert(event.cancelable);\n"
            + "   alert(event.composed);\n"

            + "    var details = [event.key, event.code, event.location, event.ctrlKey,\n"
            + "                 event.shiftKey, event.altKey, event.metaKey, event.repeat, \n"
            + "                 event.isComposing, event.charCode, event.which].join(',');\n"
            + "   alert(details);\n"
            + "  }\n";


    @Test
    @Alerts({"[object KeyboardEvent]", "type", "false", "false", "false",
            ",,0,false,false,false,false,false,false,0,0"})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent('type');\n"
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
                + "      var event = new KeyboardEvent();\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object KeyboardEvent]", "42", "false", "false", "false",
            ",,0,false,false,false,false,false,false,0,0"})
    public void createCtorNumericType() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent(42);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object KeyboardEvent]", "null", "false", "false", "false",
            ",,0,false,false,false,false,false,false,0,0"})
    public void createCtorNullType() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent(null);\n"
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
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent(unknown);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object KeyboardEvent]", "HtmlUnitEvent", "false", "false", "false",
            ",,0,false,false,false,false,false,false,0,0"})
    public void createCtorArbitraryType() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent('HtmlUnitEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object KeyboardEvent]", "keyboard", "false", "false", "false",
            "key,code,123,true,true,true,true,true,true,456,789"})
    public void createCtorAllDetails() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent('keyboard', "
                + "{ key: 'key', code: 'code', location: 123, "
                + "ctrlKey: true, shiftKey: true, altKey: true, metaKey: true,"
                + "repeat: true, isComposing: true, charCode: 456, which: 789 });\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object KeyboardEvent]", "keyboard", "false", "false", "false",
            "null,,0,true,false,false,false,false,false,456,0"})
    public void createCtorSomeDetails() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent('keyboard', "
                + "{ key: null, code: undefined, ctrlKey: true, charCode: 456 });\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object KeyboardEvent]", "keyboard", "false", "false", "false",
            ",,0,false,false,false,false,false,false,0,0"})
    public void createCtorMissingData() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent('keyboard', {\n"
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
    @Alerts({"[object KeyboardEvent]", "keyboard", "false", "false", "false",
            ",,0,false,false,false,false,false,false,0,0"})
    public void createCtorNullData() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent('keyboard', null);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object KeyboardEvent]", "keyboard", "false", "false", "false",
            ",,0,false,false,false,false,false,false,0,0"})
    public void createCtorUndefinedData() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent('keyboard', undefined);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object KeyboardEvent]", "keyboard", "false", "false", "false",
            ",,0,false,false,false,false,false,false,0,0"})
    public void createCtorWrongData() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new KeyboardEvent('keyboard', {\n"
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

    @Test
    @Alerts({"DOM3: [object KeyboardEvent]", "vendor: exception"})
    public void createEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert('DOM3: ' + document.createEvent('KeyboardEvent'));\n"
                + "    } catch(e) {alert('DOM3: exception')}\n"
                + "    try {\n"
                + "     alert('vendor: ' + document.createEvent('KeyEvents'));\n"
                + "    } catch(e) {alert('vendor: exception')}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "0-0", "undefined-undefined"})
    public void keyCode() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var keyEvent = document.createEvent('KeyEvents');\n"
                + "     alert(keyEvent.keyCode + '-' + keyEvent.charCode);\n"
                + "    } catch(e) {alert('exception')}\n"
                + "    try {\n"
                + "      var keyEvent = document.createEvent('KeyboardEvent');\n"
                + "     alert(keyEvent.keyCode + '-' + keyEvent.charCode);\n"
                + "    } catch(e) {alert('exception')}\n"
                + "    try {\n"
                + "      var mouseEvent = document.createEvent('MouseEvents');\n"
                + "     alert(mouseEvent.keyCode + '-' + mouseEvent.charCode);\n"
                + "    } catch(e) {alert('exception')}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "exception"})
    public void initKeyEvent() {
        final String html = "<html><head><script>\n"
                + "  var properties = ['type', 'bubbles', 'cancelable', /*'view',*/ 'ctrlKey', 'altKey',\n"
                + "        'shiftKey', 'metaKey', 'keyCode', 'charCode'];\n"
                + "  function dumpEvent(e) {\n"
                + "    var str = '';\n"
                + "    for (var i = 0; i < properties.length; i++) str += ', ' + e[properties[i]];\n"
                + "   alert(str.substring(2));\n"
                + "  }\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var keyEvent = document.createEvent('KeyEvents');\n"
                + "      keyEvent.initKeyEvent('keydown', true, true, null, true, true, true, true, 65, 65);\n"
                + "      dumpEvent(keyEvent);\n"
                + "      keyEvent = document.createEvent('KeyEvents');\n"
                + "      keyEvent.initKeyEvent('keyup', false, false, null, false, false, false, false, 32, 32);\n"
                + "      dumpEvent(keyEvent);\n"
                + "    } catch(e) {alert('exception')}\n"
                + "    try {\n"
                + "      var keyEvent = document.createEvent('KeyboardEvent');\n"
                + "      keyEvent.initKeyEvent('keydown', true, true, null, true, true, true, true, 65, 65);\n"
                + "      dumpEvent(keyEvent);\n"
                + "      keyEvent = document.createEvent('KeyboardEvent');\n"
                + "      keyEvent.initKeyEvent('keyup', false, false, null, false, false, false, false, 32, 32);\n"
                + "      dumpEvent(keyEvent);\n"
                + "    } catch(e) {alert('exception')}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception",
            "keydown, true, true, , 0, true, true, true, true, 0, 0",
            "keyup, false, false, , 7, false, false, false, false, 0, 0"})
    public void initKeyboardEvent() {
        final String html = "<html><head><script>\n"
                + "  var properties = ['type', 'bubbles', 'cancelable', /*'view',*/ 'key', 'location',"
                + "        'ctrlKey', 'altKey', 'shiftKey', 'metaKey', 'keyCode', 'charCode'];\n"
                + "  function dumpEvent(e) {\n"
                + "    var str = '';\n"
                + "    for (var i = 0; i < properties.length; i++) str += ', ' + e[properties[i]];\n"
                + "   alert(str.substring(2));\n"
                + "  }\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var keyEvent = document.createEvent('KeyEvents');\n"
                + "      keyEvent.initKeyboardEvent('keydown', true, true, null, 'Fn', 0, true, true, true, true);\n"
                + "      dumpEvent(keyEvent);\n"
                + "      keyEvent = document.createEvent('KeyEvents');\n"
                + "      keyEvent.initKeyboardEvent('keyup', false, false, null, '', 7, false, false, false, false);\n"
                + "      dumpEvent(keyEvent);\n"
                + "    } catch(e) {alert('exception')}\n"
                + "    try {\n"
                + "      var keyEvent = document.createEvent('KeyboardEvent');\n"
                + "      keyEvent.initKeyboardEvent('keydown', true, true, null, 'Fn', 0, true, true, true, true);\n"
                + "      dumpEvent(keyEvent);\n"
                + "      keyEvent = document.createEvent('KeyboardEvent');\n"
                + "      keyEvent.initKeyboardEvent('keyup', false, false, null, '', 7, false, false, false, false);\n"
                + "      dumpEvent(keyEvent);\n"
                + "    } catch(e) {alert('exception')}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"13", "13", "13"})
    public void keyCodeEnter_keypress() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function handleKey(e) {\n"
                + " alert(e.charCode);\n"
                + " alert(e.keyCode);\n"
                + " alert(e.which);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <textarea id='t' onkeypress='handleKey(event)'></textarea>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
