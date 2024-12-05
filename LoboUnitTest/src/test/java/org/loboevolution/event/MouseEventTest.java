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
 * Tests for mouse events support.
 */
@ExtendWith(AlertsExtension.class)
public class MouseEventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "   alert(event);\n"
            + "   alert(event.type);\n"
            + "   alert(event.bubbles);\n"
            + "   alert(event.cancelable);\n"
            + "   alert(event.composed);\n"
            + "   alert(event.view == window);\n"
            + "   alert(event.screenX);\n"
            + "   alert(event.screenY);\n"
            + "   alert(event.clientX);\n"
            + "   alert(event.clientY);\n"
            + "   alert(event.ctrlKey);\n"
            + "   alert(event.altKey);\n"
            + "   alert(event.shiftKey);\n"
            + "   alert(event.metaKey);\n"
            + "   alert(event.button);\n"
            + "   alert(event.buttons);\n"
            + "   alert(event.which);\n"
            + "  }\n";


    @Test
    @Alerts({"[object MouseEvent]", "click", "false", "false", "false", "false",
            "0", "0", "0", "0", "false", "false", "false", "false", "0", "0", "1"})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new MouseEvent('click');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MouseEvent]", "undefined", "false", "false", "false", "false",
            "0", "0", "0", "0", "false", "false", "false", "false", "0", "0", "1"})
    public void createCtorWithoutType() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new MouseEvent();\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MouseEvent]", "42", "false", "false", "false", "false",
            "0", "0", "0", "0", "false", "false", "false", "false", "0", "0", "1"})
    public void createCtorNumericType() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new MouseEvent(42);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MouseEvent]", "null", "false", "false", "false", "false",
            "0", "0", "0", "0", "false", "false", "false", "false", "0", "0", "1"})
    public void createCtorNullType() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new MouseEvent(null);\n"
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
                + "      var event = new MouseEvent(unknown);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MouseEvent]", "HtmlUnitEvent", "false", "false", "false", "false",
            "0", "0", "0", "0", "false", "false", "false", "false", "0", "0", "1"})
    public void createCtorArbitraryType() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new MouseEvent('HtmlUnitEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MouseEvent]", "click", "false", "false", "false", "false",
            "7", "0", "13", "-15", "true", "true", "true", "true", "2", "4", "3"})
    public void createCtorAllDetails() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new MouseEvent('click', {\n"
                + "        'screenX': 7,\n"
                + "        'screenY': 0.4,\n"
                + "        'clientX': 13.007,\n"
                + "        'clientY': -15,\n"
                + "        'ctrlKey': true,\n"
                + "        'shiftKey': 'true',\n"
                + "        'altKey': 1,\n"
                + "        'metaKey': {},\n"
                + "        'button': 2,\n"
                + "        'buttons': 4\n"
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
    @Alerts({"DOM2: [object MouseEvent]", "DOM3: [object MouseEvent]"})
    public void createEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert('DOM2: ' + document.createEvent('MouseEvent'));\n"
                + "    } catch(e) {alert('DOM2: exception')}\n"
                + "    try {\n"
                + "     alert('DOM3: ' + document.createEvent('MouseEvent'));\n"
                + "    } catch(e) {alert('DOM3: exception')}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"click", "true", "true", "true", "1", "2", "3", "4", "true", "true", "true", "true"})
    public void initMouseEvent() {
        final String html = "<html><body><script>\n"
                + "  var e = document.createEvent('MouseEvent');\n"
                + "  e.initMouseEvent('click', true, true, window, 0, 1, 2, 3, 4, true, true, true, true, 0, null);\n"
                + " alert(e.type);\n"
                + " alert(e.bubbles);\n"
                + " alert(e.cancelable);\n"
                + " alert(e.view == window);\n"
                + " alert(e.screenX);\n"
                + " alert(e.screenY);\n"
                + " alert(e.clientX);\n"
                + " alert(e.clientY);\n"
                + " alert(e.ctrlKey);\n"
                + " alert(e.altKey);\n"
                + " alert(e.shiftKey);\n"
                + " alert(e.metaKey);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "1"})
    public void dispatchEvent() {
        final String html = "<html><head><script>\n"
                + "  var clickCount = 0;\n"
                + "  var dblClickCount = 0;\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('myDiv');\n"
                + "    div.onclick = clickHandler;\n"
                + "    div.ondblclick = dblClickHandler;\n"
                + "    if (div.fireEvent) {\n"
                + "      var clickEvent = (evt = document.createEventObject(), evt.type = 'click', evt);\n"
                + "      div.fireEvent('onclick', clickEvent);\n"
                + "      var dblclickEvent = (evt_0 = document.createEventObject(), evt_0.type = 'dblclick', evt_0);\n"
                + "      div.fireEvent('ondblclick', dblclickEvent);\n"
                + "    }\n"
                + "    else {\n"
                + "      var clickEvent = $createMouseEvent(document, 'click', true, true, 0, 0, 0, 0, 0,"
                + " false, false, false, false, 1, null);\n"
                + "      div.dispatchEvent(clickEvent);\n"
                + "      var dblclickEvent = $createMouseEvent(document, 'dblclick', true, true, 0, 0, 0, 0, 0,"
                + " false, false, false, false, 1, null);\n"
                + "      div.dispatchEvent(dblclickEvent);\n"
                + "    }\n"
                + "   alert(clickCount);\n"
                + "   alert(dblClickCount);\n"
                + "  }\n"
                + "  function clickHandler() {\n"
                + "    clickCount++;\n"
                + "  }\n"
                + "  function dblClickHandler() {\n"
                + "    dblClickCount++;\n"
                + "  }\n"
                + "  function $createMouseEvent(doc, type, canBubble, cancelable, detail, screenX, screenY, clientX,"
                + " clientY, ctrlKey, altKey, shiftKey, metaKey, button, relatedTarget) {\n"
                + "    button == 1?(button = 0):button == 4?(button = 1):(button = 2);\n"
                + "    var evt = doc.createEvent('MouseEvent');\n"
                + "    evt.initMouseEvent(type, canBubble, cancelable, null, detail, screenX, screenY, clientX,"
                + " clientY, ctrlKey, altKey, shiftKey, metaKey, button, relatedTarget);\n"
                + "    return evt;\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void buttonOnclick() {
        final String html = "<html><body>\n"
                + "<p id='clicker'>Click me!</p>\n"
                + "<script>\n"
                + "  function handler(event) {\n"
                + "   alert(event.button);\n"
                + "  }\n"
                + "  var p = document.getElementById('clicker');\n"
                + "  if (p.addEventListener ) {\n"
                + "    p.addEventListener('click', handler, false);\n"
                + "  } else if (p.attachEvent) {\n"
                + "    p.attachEvent('onclick', handler);\n"
                + "  }\n"
                + "  document.getElementById('clicker').click();"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void buttonOnmousedown() {
        final String html = "<html><body>\n"
                + "<p id='clicker'>Click me!</p>\n"
                + "<script>\n"
                + "  function handler(event) {\n"
                + "   alert(event.button);\n"
                + "  }\n"
                + "  var p = document.getElementById('clicker');\n"
                + "  if (p.addEventListener ) {\n"
                + "    p.addEventListener('mousedown', handler, false);\n"
                + "  } else if (p.attachEvent) {\n"
                + "    p.attachEvent('onmousedown', handler);\n"
                + "  }\n"
                + "  document.getElementById('clicker').click();"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0"})
    public void pageX() {
        final String html = "<html><body><script>\n"
                + "  var e = document.createEvent('MouseEvent');\n"
                + " alert(e.pageX);\n"
                + " alert(e.pageY);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }
}
