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
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * Tests that when DOM events such as "onclick" have access
 * to an Event} object with context information.
 */
@ExtendWith(AlertsExtension.class)
public class EventTest extends LoboUnitTest {

    private static final String DUMP_EVENT_FUNCTION = "  function dump(event) {\n"
            + "   alert(event);\n"
            + "   alert(event.type);\n"
            + "   alert(event.bubbles);\n"
            + "   alert(event.cancelable);\n"
            + "   alert(event.composed);\n"
            + "  }\n";


    @Test
    @Alerts({"[object Event]", "event", "false", "false", "false"})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new Event('event');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "event", "true", "false", "false"})
    public void createCtorWithDetails() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new Event('event', {\n"
                + "        'bubbles': true\n"
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
    @Alerts({"[object Event]", "event", "true", "false", "false"})
    public void createCtorWithDetailsBoolAsString() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new Event('event', {\n"
                + "        'bubbles': 'true'\n"
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
    @Alerts({"[object Event]", "event", "true", "false", "false"})
    public void createCtorWithDetailsBoolAsNumber() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new Event('event', {\n"
                + "        'bubbles': 1\n"
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
    @Alerts({"[object Event]", "event", "true", "false", "false"})
    public void createCtorWithDetailsBoolAsObject() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new Event('event', {\n"
                + "        'bubbles': {}\n"
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
    @Alerts({"[object Event]", "event", "false", "false", "false"})
    public void createCtorWithDetailsBoolAsUndefined() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new Event('event', {\n"
                + "        'bubbles': undefined\n"
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
    @Alerts({"[object Event]", "event", "false", "false", "false"})
    public void createCtorWithDetailsBoolAsNull() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new Event('event', {\n"
                + "        'bubbles': null\n"
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
    @Alerts({"[object Event]", "", "false", "false", "false"})
    public void createCreateEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('Event');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"DOM2: [object Event]", "DOM3: [object Event]", "vendor: [object Event]"})
    public void createCreateEventForDifferentTypes() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "     alert('DOM2: ' + document.createEvent('HTMLEvents'));\n"
                + "    } catch(e) {alert('DOM2: exception')}\n"
                + "    try {\n"
                + "     alert('DOM3: ' + document.createEvent('Event'));\n"
                + "    } catch(e) {alert('DOM3: exception')}\n"
                + "    try {\n"
                + "     alert('vendor: ' + document.createEvent('Events'));\n"
                + "    } catch(e) {alert('vendor: exception')}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "event", "true", "false", "false"})
    public void initEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('Event');\n"
                + "      event.initEvent('event', true, false);\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("clickId")
    public void thisDefined() {
        final String content
                = "<html><head></head><body>\n"
                + "<input type='button' id='clickId'/>\n"
                + "<script>\n"
                + "  function handler(event) {alert(this.getAttribute('id')); }\n"
                + "  document.getElementById('clickId').onclick = handler;</script>\n"
                + "</body></html>";
        onClickPageTest(content);
    }

    @Test
    @Alerts("foo")
    public void setPropOnThisDefined() {
        final String content
                = "<html><head></head><body>\n"
                + "<input type='button' id='clickId'/>\n"
                + "<script>\n"
                + "  function handler(event) {alert(this.madeUpProperty); }\n"
                + "  document.getElementById('clickId').onclick = handler;\n"
                + "  document.getElementById('clickId').madeUpProperty = 'foo';\n"
                + "</script>\n"
                + "</body></html>";
        onClickPageTest(content);
    }

    @Test
    @Alerts("defined")
    public void eventArgDefinedByWrapper() {
        final String content
                = "<html><head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head><body>\n"
                + "<input type='button' id='clickId' onclick=\"alert(event ? 'defined' : 'undefined')\"/>\n"
                + "</body></html>";
        onClickPageTest(content);
    }

    @Test
    @Alerts("defined")
    public void eventArgDefined() {
        final String content
                = "<html><head></head>\n"
                + "<body>\n"
                + "<input type='button' id='clickId'/>\n"
                + "<script>\n"
                + "  function handler(event) {alert(event ? 'defined' : 'undefined'); }\n"
                + "  document.getElementById('clickId').onclick = handler;\n"
                + "</script>\n"
                + "</body></html>";
        onClickPageTest(content);
    }

    @Test
    @Alerts("pass")
    public void eventTargetSameAsThis() {
        final String content
                = "<html><head></head>\n"
                + "<body>\n"
                + "<input type='button' id='clickId'/>\n"
                + "<script>\n"
                + "  function handler(event) {\n"
                + "   alert(event.target == this ? 'pass' : event.target + '!=' + this);\n"
                + "  }\n"
                + "  document.getElementById('clickId').onclick = handler;\n"
                + "</script>\n"
                + "</body></html>";
        onClickPageTest(content);
    }

    @Test
    @Alerts({"[object HTMLInputElement]", "true"})
    public void eventSrcElementSameAsThis() {
        final String content
                = "<html><head></head><body>\n"
                + "<input type='button' id='clickId'/>\n"
                + "<script>\n"
                + "  function handler(event) {\n"
                + "    event = event ? event : window.event;\n"
                + "   alert(event.srcElement);\n"
                + "   alert(event.srcElement == this);\n"
                + "  }\n"
                + "  document.getElementById('clickId').onclick = handler;\n"
                + "</script>\n"
                + "</body></html>";
        onClickPageTest(content);
    }

    @Test
    @Alerts("pass")
    public void eventCurrentTargetSameAsThis() {
        final String content
                = "<html><head></head>\n"
                + "<body>\n"
                + "<input type='button' id='clickId'/>\n"
                + "<script>\n"
                + "  function handler(event) {\n"
                + "   alert(event.currentTarget == this ? 'pass' : event.currentTarget + '!=' + this);\n"
                + "  }\n"
                + "  document.getElementById('clickId').onclick = handler;\n"
                + "</script>\n"
                + "</body></html>";
        onClickPageTest(content);
    }

    @Test
    @Alerts({"[object Window]", "[object HTMLDivElement]"})
    public void currentTarget_sameListenerForEltAndWindow() {
        final String content
                = "<html><head></head><body>\n"
                + "<div id='clickId'>click me</div>\n"
                + "<script>\n"
                + "  function handler(event) {\n"
                + "   alert(event.currentTarget);\n"
                + "  }\n"
                + "  document.getElementById('clickId').onmousedown = handler;\n"
                + "  window.addEventListener('mousedown', handler, true);\n"
                + "</script>\n"
                + "</body></html>";
        onClickPageTest(content);
    }

    @Test
    public void addEventListener_HandlerNull() {
        final String content
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "try {\n"
                + "  window.addEventListener('mousedown', null, true);\n"
                + "} catch (err) {\n"
                + " alert('error');\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(content);
    }

    @Test
    @Alerts({"123a4a", "1a2a3ab4ab1ab2ab3abc4abc"})
    public void typing_inputText() {
        testTyping("<input type='text'", "");
    }

    @Test
    @Alerts({"123a4a", "1a2a3ab4ab1ab2ab3abc4abc"})
    public void typing_inputPassword() {
        testTyping("<input type='password'", "");
    }

    @Test
    @Alerts({"123a4a", "1a2a3ab4ab1ab2ab3abc4abc"})
    public void typing_inputTextarea() {
        testTyping("<textarea", "</textarea>");
    }

    @Test
    @Alerts({"123a4a", "1a2a3ab4ab1ab2ab3abc4abc"})
    public void typing_inputTel() {
        testTyping("<input type='tel'", "");
    }

    @Test
    @Alerts({"123a4a", "1a2a3ab4ab1ab2ab3abc4abc"})
    public void typing_input_search() {
        testTyping("<input type='search'", "");
    }

    @Test
    @Alerts({"124", "124124"})
    public void typing_inputNumber() {
        testTyping("<input type='number'", "");
    }

    @Test
    @Alerts({"123a4a", "1a2a3ab4ab1ab2ab3abc4abc"})
    public void typingTextara() {
        testTyping("<textarea", "</textarea>");
    }

    private void testTyping(final String opening, final String closing) {
        final String html =
                "<html><body>\n"
                        + "<script>\n"                        + "var x = '';\n"
                        + "function msg(s) { x += s; }</script>\n"
                        + "<form>\n"
                        + opening + " id='t' onkeydown='msg(1 + this.value)' "
                        + "onkeypress='msg(2 + this.value)' "
                        + "oninput='msg(3 + this.value)'"
                        + "onkeyup='msg(4 + this.value)'>" + closing
                        + "</form>\n"
                        + "<div id='d' onclick='alert(x); x=\"\"'>abc</div>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("t");
        elem.getOnclick();
    }

    private void onClickPageTest(final String html) {
        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickId");
        elem.getOnclick();
    }

    @Test
    @Alerts("frame1")
    public void thisInEventHandler() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <button name='button1' id='button1' onclick='alert(this.name)'>1</button>\n"
                + "  <iframe src='default' name='frame1' id='frame1'></iframe>\n"
                + "  <script>\n"
                + "    var e = document.getElementById('frame1');\n"
                + "    e.onload = document.getElementById('button1').onclick;\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("called")
    public void iframeOnload() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "   alert('called');\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "<iframe src='default' name='frame1' id='frame1'></iframe>\n"
                + "<script>\n"
                + "  var e = document.getElementById('frame1');\n"
                + "  e.onload = test;\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"inline", "null"})
    public void iframeOnload2() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "<iframe src='about:blank' name='frame1' id='frame1'></iframe>\n"
                + "<script>\n"
                + "  var e = document.getElementById('frame1');\n"
                + "  e.onload =alert('inline');\n"
                + " alert(e.onload);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false"})
    public void ieWindowEvent() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + " alert(window.event == null);\n"
                        + "  try {\n"
                        + "   alert(event == null);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head><body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2"})
    public void commentInEventHandlerDeclaration() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='alert(1);\n"
                + "// a comment within the onload declaration\n"
                + "alert(2)'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void nullEventHandler() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('myDiv');\n"
                + "   alert(div.onclick);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<div id='myDiv'/>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "load", "false", "false", "false"})
    public void onload() {
        final String html =
                "<html><body onload='test(event)'>\n"
                        + "<script>\n"
                        + "    function test(e) {\n"
                        + "      dump(e);\n"
                        + "    }\n"
                        + DUMP_EVENT_FUNCTION
                        + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Event]", "number"})
    public void timeStamp() {
        final String html =
                "<html><body onload='test(event)'>\n"
                        + "<script>\n"
                        + "  function test(e) {\n"
                        + "   alert(e);\n"
                        + "   alert(typeof e.timeStamp);\n"
                        + "  }\n"
                        + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"click", "true", "true", "click", "false", "false"})
    public void initEventClick() {
        testInitEvent("click");
    }

    @Test
    @Alerts({"dblclick", "true", "true", "dblclick", "false", "false"})
    public void initEventDblClick() {
        testInitEvent("dblclick");
    }

    @Test
    @Alerts({"unknown", "true", "true", "unknown", "false", "false"})
    public void initEventUnknown() {
        testInitEvent("unknown");
    }

    @Test
    @Alerts({"cASe", "true", "true", "cASe", "false", "false"})
    public void initEventCaseSensitive() {
        testInitEvent("cASe");
    }

    private void testInitEvent(final String eventType) {
        final String html =
                "<html><head><script>\n"

                        + "  function test() {\n"
                        + "    var e = document.createEvent('Event');\n"
                        + "    try {\n"
                        + "      e.initEvent('" + eventType + "', true, true);\n"
                        + "     alert(e.type);\n"
                        + "     alert(e.bubbles);\n"
                        + "     alert(e.cancelable);\n"
                        + "    } catch(e) {alert('e-' + '" + eventType + "'); }\n"

                        + "    var e = document.createEvent('Event');\n"
                        + "    try {\n"
                        + "      e.initEvent('" + eventType + "', false, false);\n"
                        + "     alert(e.type);\n"
                        + "     alert(e.bubbles);\n"
                        + "     alert(e.cancelable);\n"
                        + "    } catch(e) {alert('e2-' + '" + eventType + "'); }\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "I was here"})
    public void firedEventEqualsOriginalEvent() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  var e = document.getElementById('myDiv');\n"
                        + "  \n"
                        + "  var myEvent;\n"
                        + "  var listener = function(x) {\n"
                        + "   alert(x == myEvent);\n"
                        + "    x.foo = 'I was here';\n"
                        + "  }\n"
                        + "  \n"
                        + "  e.addEventListener('click', listener, false);\n"
                        + "  myEvent = document.createEvent('HTMLEvents');\n"
                        + "  myEvent.initEvent('click', true, true);\n"
                        + "  e.dispatchEvent(myEvent);\n"
                        + " alert(myEvent.foo);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head><body onload='test()'>\n"
                        + "<div id='myDiv'>toti</div>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"e-0", "e-1", "e-2", "e-3", "e-4", "e-5",
            "e-6", "e-7", "e-8", "e-9", "e-10", "e-11",
            "e-12", "e-13", "e-14", "e-15", "e-16", "e-17", "e-18",
            "e-19", "e-20", "e-21", "e-22", "e-23", "e-24",
            "e-25", "e-26", "e-27", "e-28", "e-29", "e-30", "e-31", "e-32",
            "e-33"})
    public void constants() {
        final String html =
                "<html><body>\n"
                        + "<script>\n"
                        + "  var constants = [Event.ABORT, Event.ALT_MASK, Event.BACK, Event.BLUR, Event.CHANGE, Event.CLICK, "
                        + "Event.CONTROL_MASK, Event.DBLCLICK, Event.DRAGDROP, Event.ERROR, Event.FOCUS, Event.FORWARD, "
                        + "Event.HELP, Event.KEYDOWN, Event.KEYPRESS, Event.KEYUP, Event.LOAD, Event.LOCATE, Event.META_MASK, "
                        + "Event.MOUSEDOWN, Event.MOUSEDRAG, Event.MOUSEMOVE, Event.MOUSEOUT, Event.MOUSEOVER, Event.MOUSEUP, "
                        + "Event.MOVE, Event.RESET, Event.RESIZE, Event.SCROLL, Event.SELECT, Event.SHIFT_MASK, Event.SUBMIT, "
                        + "Event.UNLOAD, Event.XFER_DONE];\n"
                        + "  for (var x in constants) {\n"
                        + "    try {\n"
                        + "     alert(constants[x].toString(16));\n"
                        + "    } catch(e) {alert('e-' + x); }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void text() {
        final String html =
                "<html><body onload='test(event)'><script>\n"

                        + "  function test(e) {\n"
                        + "    try {\n"
                        + "     alert(e.TEXT.toString(16));\n"// But Event.TEXT is undefined!!!
                        + "    } catch(e) {alert('exception'); }\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"form1 -> custom", "form2 -> [object HTMLFormElement]",
            "form1: [object HTMLFormElement]", "form2: [object HTMLFormElement]",
            "form1 -> custom", "form2 -> [object HTMLFormElement]"})
    public void nameResolution() {
        final String html = "<html><head><script>\n"
                + "var form1 = 'custom';\n"
                + "function testFunc() {\n"
                + " alert('form1 -> ' + form1);\n"
                + " alert('form2 -> ' + form2);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='testFunc()'>\n"
                + "<form name='form1'></form>\n"
                + "<form name='form2'></form>\n"
                + "<button id='button' onclick=\"alert('form1: ' + form1);alert('form2: ' + form2); testFunc()\">click me</button>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("button");
        elem.getOnclick();
    }

    @Test
    @Alerts("activeElement BODY")
    public void document_focus() {
        final String html = "<html>\n"
                + "<head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    handle(document);\n"
                + "   alert('activeElement ' + document.activeElement.nodeName);\n"
                + "  }\n"
                + "  function handle(obj) {\n"
                + "    obj.addEventListener('focus', handler, true);\n"
                + "  }\n"
                + "  function handler(e) {\n"
                + "    var src = e.srcElement;\n"
                + "    if (!src)\n"
                + "      src = e.target;\n"
                + "   alert(e.type + ' ' + src.nodeName);\n"
                + "   alert('handler: activeElement ' + document.activeElement.nodeName);\n"
                + "  }\n"
                + "  function alert(x) {\n"
                + "    document.getElementById('log').value += x + '\\n';\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"focus INPUT", "focus INPUT"})
    public void document_input_focus() {
        document_input("focus");
    }

    @Test
    @Alerts("blur INPUT")
    public void document_input_blur() {
        document_input("blur");
    }

    private void document_input(final String event) {
        final String html = "<html>\n"
                + "<head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    handle(document);\n"
                + "  }\n"
                + "  function handle(obj) {\n"
                + "    obj.addEventListener('" + event + "', handler, true);\n"
                + "  }\n"
                + "  function handler(e) {\n"
                + "    var src = e.srcElement;\n"
                + "    if (!src)\n"
                + "      src = e.target;\n"
                + "   alert(e.type + ' ' + src.nodeName);\n"
                + "  }\n"
                + "  function alert(x) {\n"
                + "    document.getElementById('log').value += x + '\\n';\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id=\"div\">\n"
                + "    <input id=\"input1\" type=\"text\">\n"
                + "    <input id=\"input2\" type=\"text\">\n"
                + "  </div>\n"
                + "<textarea id='log' cols='80' rows='40'></textarea>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("input1");
        elem.getOnclick();

        elem = (HTMLElementImpl) document.getElementById("input2");
        elem.getOnclick();
    }

    @Test
    @Alerts({"2from window", "1from document"})
    public void eventHandlersParentScope() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "<button name='button1' id='button1' onclick='alert(1 + foo)'>click me</button>\n"
                + "<script>\n"
                + "  window.addEventListener('click', function() {alert(2 + foo); }, true);\n"
                + "  document.foo = 'from document';\n"
                + "  var foo = 'from window';\n"
                + "</script>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("button1");
        elem.getOnclick();
    }

    @Test
    @Alerts({"from theField", "from theForm", "from document", "from window"})
    public void eventHandlersParentScopeChain_formFields() {
        eventHandlersParentScopeChain("<button", "</button>");
        eventHandlersParentScopeChain("<select", "</select>");
        eventHandlersParentScopeChain("<textarea", "</textarea>");

        eventHandlersParentScopeChain("<input type='text'", "");
        eventHandlersParentScopeChain("<input type='password'", "");

        eventHandlersParentScopeChain("<input type='checkbox'", "");
        eventHandlersParentScopeChain("<input type='radio'", "");

        // eventHandlersParentScopeChain("<input type='file'", "");
        eventHandlersParentScopeChain("<input type='image'", "");

        eventHandlersParentScopeChain("<input type='button'", "");

        eventHandlersParentScopeChain("<input type='submit' value='xxx'", "");
        // case without value attribute was failing first with IE due to the way the value attribute was added
        eventHandlersParentScopeChain("<input type='submit'", "");

        eventHandlersParentScopeChain("<input type='reset' value='xxx'", "");
        // case without value attribute was failing first with IE due to the way the value attribute was added
        eventHandlersParentScopeChain("<input type='reset'", "");
    }

    @Test
    @Alerts({"from theField", "from document", "from document", "from window"})
    public void eventHandlersParentScopeChain_span() {
        eventHandlersParentScopeChain("<span", "</span>");
    }

    private void eventHandlersParentScopeChain(final String startTag, final String endTag) {
        final String html = "<html><html>\n"
                + "<head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body id='body'>\n"
                + "<form id='theForm'>\n"
                + "  <div id='theDiv'>\n"
                + "    " + startTag + " id='theField' onclick='alert(foo); return false;'>click me" + endTag + "\n"
                + "  </div>\n"
                + "</form>\n"
                + "<script>\n"
                + "  var foo = 'from window';\n"
                + "  document.foo = 'from document';\n"
                + "  document.body.foo = 'from body';\n"
                + "  document.getElementById('theForm').foo = 'from theForm';\n"
                + "  document.getElementById('theDiv').foo = 'from theDiv';\n"
                + "  document.getElementById('theField').foo = 'from theField';\n"
                + "</script>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("theField");
        elem.getOnclick();
    }

    @Test
    @Alerts("from document")
    public void eventHandlers_functionOpen() {
        final String html = "<html><body>\n"
                + "<button id='button1' onclick='identify(open)'>click me</button>\n"
                + "<script>\n"
                + "function identify(fnOpen) {\n"
                + "  var origin = 'unknown';\n"
                + "  if (fnOpen === window.open) {\n"
                + "    origin = 'from window';\n"
                + "  }\n"
                + "  else if (fnOpen === document.open) {\n"
                + "    origin = 'from document';\n"
                + "  }\n"
                + " alert(origin);\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("button1");
        elem.getOnclick();
    }

    @Test
    @Alerts({"false", "boolean"})
    public void defaultPrevented() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('Event');\n"
                + "     alert(event.defaultPrevented);\n"
                + "     alert(typeof event.defaultPrevented);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "boolean"})
    public void returnValue() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('Event');\n"
                + "     alert(event.returnValue);\n"
                + "     alert(typeof event.returnValue);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "boolean", "false - false",
            "true", "true - false",
            "false", "boolean",
            "true", "boolean", "false - false",
            "true", "boolean"})
    public void returnValueSetter() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('Event');\n"
                + "     alert(event.returnValue);\n"
                + "     alert(typeof event.returnValue);\n"
                + "     alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "      event.initEvent('click', 'true', 'true');\n"
                + "     alert(event.returnValue);\n"
                + "     alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "      event.preventDefault();\n"
                + "     alert(event.returnValue);\n"
                + "     alert(typeof event.returnValue);\n"
                + "      event = document.createEvent('Event');\n"
                + "     alert(event.returnValue);\n"
                + "     alert(typeof event.returnValue);\n"
                + "     alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "      event.preventDefault();\n"
                + "     alert(event.returnValue);\n"
                + "     alert(typeof event.returnValue);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "boolean", "false - false",
            "true", "true - false",
            "false", "boolean", "false",
            "true", "boolean", "false - false",
            "true", "boolean", "true",
            "true", "boolean", "true - false",
            "false", "boolean", "false"})
    public void returnValueSetterFalse() {
        returnValueSetterUndefined("false");
    }

    @Test
    @Alerts({"true", "boolean", "false - false",
            "true", "true - false",
            "true", "boolean", "false",
            "true", "boolean", "false - false",
            "true", "boolean", "true",
            "true", "boolean", "true - false",
            "true", "boolean", "false"})
    public void returnValueSetterTrue() {
        returnValueSetterUndefined("true");
    }

    @Test
    @Alerts({"true", "boolean", "false - false",
            "true", "true - false",
            "true", "boolean", "false",
            "true", "boolean", "false - false",
            "true", "boolean", "true",
            "true", "boolean", "true - false",
            "true", "boolean", "false"})
    public void returnValueSetterString() {
        returnValueSetterUndefined("'test'");
    }

    @Test
    @Alerts({"true", "boolean", "false - false",
            "true", "true - false",
            "false", "boolean", "false",
            "true", "boolean", "false - false",
            "true", "boolean", "true",
            "true", "boolean", "true - false",
            "false", "boolean", "false"})
    public void returnValueSetterZero() {
        returnValueSetterUndefined("0");
    }

    @Test
    @Alerts({"true", "boolean", "false - false",
            "true", "true - false",
            "true", "boolean", "false",
            "true", "boolean", "false - false",
            "true", "boolean", "true",
            "true", "boolean", "true - false",
            "true", "boolean", "false"})
    public void returnValueSetterOne() {
        returnValueSetterUndefined("1");
    }

    @Test
    @Alerts({"true", "boolean", "false - false",
            "true", "true - false",
            "true", "boolean", "false",
            "true", "boolean", "false - false",
            "true", "boolean", "true",
            "true", "boolean", "true - false",
            "true", "boolean", "false"})
    public void returnValueSetterMinusOne() {
        returnValueSetterUndefined("-1");
    }

    @Test
    @Alerts({"true", "boolean", "false - false",
            "true", "true - false",
            "false", "boolean", "false",
            "true", "boolean", "false - false",
            "true", "boolean", "true",
            "true", "boolean", "true - false",
            "false", "boolean", "false"})
    public void returnValueSetterUndefined() {
        returnValueSetterUndefined("undefined");
    }

    private void returnValueSetterUndefined(final String value) {
        final String html = "<html>\n"
                + "  <head></head>\n"
                + "  <body onload='test()'>\n"
                + "    <div><a id='triggerClick' href='#'>click event</a></div>\n"
                + "    <script>\n"


                + "      function test() {\n"
                + "        try {\n"
                + "          var event = document.createEvent('Event');\n"
                + "         alert(event.returnValue);\n"
                + "         alert(typeof event.returnValue);\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.initEvent('click', 'true', 'true');\n"
                + "         alert(event.returnValue);\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.returnValue = " + value + ";\n"
                + "         alert(event.returnValue);\n"
                + "         alert(typeof event.returnValue);\n"
                + "          event.returnValue = !event.returnValue;\n"
                + "         alert(event.returnValue);\n"
                + "          event = document.createEvent('Event');\n"
                + "         alert(event.returnValue);\n"
                + "         alert(typeof event.returnValue);\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.returnValue = " + value + ";\n"
                + "         alert(event.returnValue);\n"
                + "         alert(typeof event.returnValue);\n"
                + "          event.returnValue = !event.returnValue;\n"
                + "         alert(event.returnValue);\n"
                + "        } catch (e) {alert('exception') }\n"
                + "      }\n"
                + "      triggerClick.addEventListener('click', function (event) {\n"
                + "         alert(event.returnValue);\n"
                + "         alert(typeof event.returnValue);\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.returnValue = " + value + ";\n"
                + "         alert(event.returnValue);\n"
                + "         alert(typeof event.returnValue);\n"
                + "          event.returnValue = !event.returnValue;\n"
                + "         alert(event.returnValue);\n"
                + "        });\n"
                + "    </script>\n"
                + "  </body>\n"
                + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("triggerClick");
        elem.getOnclick();
    }

    @Test
    @Alerts({"false - false", "true - false", "true - true",
            "false - false", "false - false", "false - false",
            "false - false", "true - false"})
    public void preventDefault() {
        final String html = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"


                + "      function test() {\n"
                + "        try {\n"
                + "          var event = document.createEvent('Event');\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.initEvent('click', 'true', 'true');\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.preventDefault();\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event = document.createEvent('Event');\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.preventDefault();\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event = document.createEvent('Event');\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.preventDefault();\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "          event.initEvent('click', 'true', 'true');\n"
                + "         alert(event.cancelable + ' - ' + event.defaultPrevented);\n"
                + "        } catch (e) {alert('exception') }\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='test()'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("OK")
    public void domEventNameUsedAsFunctionName() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function onclick() {\n"
                + " alert('OK');\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='onclick()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
