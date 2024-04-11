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
 * Tests that the events triggered in the right order.
 */
@ExtendWith(AlertsExtension.class)
public class Event2Test extends LoboUnitTest {
    @Test
    @Alerts("[object Event] change b:true c:false [select] [-]"
            + " [object MouseEvent] click b:true c:true [clickMe] [1]")
    public void optionClick() {
        final String firstSnippet = "       <select name='select' id='select' size='2'\n";
        final String secondSnippet = ">\n"
                + "               <option id='o_id1' value='o_value1'>option1</option>\n"
                + "               <option id='clickMe' value='o_value2'>option2</option>\n"
                + "               <option id='o_id3' value='o_value3'>option3</option>\n"
                + "       </select>\n";

        testClickEvents(firstSnippet, secondSnippet);
    }

     @Test
    @Alerts("[object MouseEvent] click b:true c:true [clickMe] [1]")
    public void optionClick2() {
        final String firstSnippet = "       <select name='select' id='select' size='2'>\n"
                + "               <option id='o_id1' value='o_value1'>option1</option>\n"
                + "               <option id='clickMe' value='o_value2'\n";
        final String secondSnippet = ">option2</option>\n"
                + "               <option id='o_id3' value='o_value3'>option3</option>\n"
                + "       </select>\n";

        testClickEvents(firstSnippet, secondSnippet);
    }


    @Test
    @Alerts("[object MouseEvent] click b:true c:true [radio] [1]"
            + " [object Event] change b:true c:false [radio] [-]")
    public void radioClick() {
        final String firstSnippet = "       <input type='radio' name='radio' id='clickMe' value='2'\n";
        final String secondSnippet = ">Radio\n";

        testClickEvents(firstSnippet, secondSnippet);
    }

    @Test
    @Alerts("[object MouseEvent] click b:true c:true [checkbox] [1]"
            + " [object Event] change b:true c:false [checkbox] [-]")
    public void checkboxClick() {
        final String firstSnippet = "       <input type='checkbox' name='checkbox' id='clickMe' value='2'\n";
        final String secondSnippet = ">Checkbox\n";

        testClickEvents(firstSnippet, secondSnippet);
    }


    @Test
    @Alerts("[object MouseEvent] click b:true c:true [clickMe] [1]")
    public void inputTextClick() {
        final String firstSnippet = "       <input type='text' name='clickMe' id='clickMe' size='2'\n";
        final String secondSnippet = ">\n";

        testClickEvents(firstSnippet, secondSnippet);
    }


    @Test
    @Alerts("[object MouseEvent] click b:true c:true [clickMe] [1]")
    public void inputPasswordClick() {
        final String firstSnippet = "       <input type='password' name='clickMe' id='clickMe' size='2'\n";
        final String secondSnippet = ">\n";

        testClickEvents(firstSnippet, secondSnippet);
    }


    @Test
    @Alerts("[object MouseEvent] click b:true c:true [clickMe] [1]")
    public void textareaClick() {
        final String firstSnippet = "       <textarea name='clickMe' id='clickMe' size='2'\n";
        final String secondSnippet = "></textarea>\n";

        testClickEvents(firstSnippet, secondSnippet);
    }


    @Test
    @Alerts("")
    public void submitClick() {
        final String firstSnippet = "       <input type='submit' name='clickMe' id='clickMe'\n";
        final String secondSnippet = ">\n";

        testClickEvents(firstSnippet, secondSnippet);
    }

    @Test
    @Alerts("[object MouseEvent] click b:true c:true [clickMe] [1]")
    public void resetClick() {
        final String firstSnippet = "       <input type='reset' name='clickMe' id='clickMe'\n";
        final String secondSnippet = ">\n";

        testClickEvents(firstSnippet, secondSnippet);
    }


    @Test
    @Alerts("[object MouseEvent] click b:true c:true [clickMe] [1]")
    public void buttonClick() {
        final String firstSnippet = "       <input type='button' name='clickMe' id='clickMe'\n";
        final String secondSnippet = ">\n";

        testClickEvents(firstSnippet, secondSnippet);
    }


    @Test
    @Alerts("[object MouseEvent] click b:true c:true [clickMe] [1]")
    public void anchorClick() {
        final String firstSnippet = "       <a href='#' name='clickMe' id='clickMe'\n";
        final String secondSnippet = ">anchor</a>\n";

        testClickEvents(firstSnippet, secondSnippet);
    }

    private void testClickEvents(final String firstSnippet, final String secondSnippet) {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <title></title>\n"
                        + "  <script type='text/javascript'>\n"
                        + "  <!--\n"
                        + "    function dumpEvent(event) {\n"
                        + "      var msg = event;\n"
                        + "      msg = msg + ' ' + event.type;\n"
                        + "      msg = msg + ' b:' + event.bubbles;\n"
                        + "      msg = msg + ' c:' + event.cancelable;\n"
                        + "\n"
                        + "      // target\n"
                        + "      var eTarget;\n"
                        + "      if (event.target) {\n"
                        + "        eTarget = event.target;\n"
                        + "      } else if (event.srcElement) {\n"
                        + "        eTarget = event.srcElement;\n"
                        + "      }\n"
                        + "      // defeat Safari bug\n"
                        + "      if (eTarget.nodeType == 3) {\n"
                        + "        eTarget = eTarget.parentNode;\n"
                        + "      }\n"
                        + "\n"
                        + "      if (eTarget.name) {\n"
                        + "        msg = msg + ' [' + eTarget.name + ']';\n"
                        + "      } else {\n"
                        + "        msg = msg + ' [' + eTarget.id + ']';\n"
                        + "      }\n"
                        + "\n"
                        + "      // key code\n"
                        + "      var eCode;\n"
                        + "      if (event.keyCode) {\n"
                        + "        eCode = event.keyCode;\n"
                        + "      } else if (event.which) {\n"
                        + "        eCode = event.which;\n"
                        + "      } else if (event.button) {\n"
                        + "        eCode = event.button;\n"
                        + "      }\n"
                        + "      if (eCode) {\n"
                        + "        var char = String.fromCharCode(eCode);\n"
                        + "        msg = msg + ' [' + eCode + ']';\n"
                        + "      } else {\n"
                        + "        msg = msg + ' [-]';\n"
                        + "      }\n"
                        + "\n"
                        + "      document.title += ' ' + msg;\n"
                        + "    }\n"
                        + "  //-->\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "  <form id='form' name='form' action='#'>\n"
                        + "    <input type='text' id='start' name='startText'/>\n"
                        + "\n"
                        + firstSnippet
                        + "      onclick='dumpEvent(event);'\n"
                        + "      onchange = 'dumpEvent(event);'"
                        + secondSnippet
                        + "   </form>\n"
                        + "</body>\n"
                        + "</html>\n";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();
    }


    @Test
    @Alerts("[object KeyboardEvent] keydown b:true c:true [typeHere] [65] "
            + "[object KeyboardEvent] keypress b:true c:true [typeHere] [97] "
            + "[object KeyboardEvent] keyup b:true c:true [typeHere] [65]")
    public void inputTextType() {
        final String firstSnippet = "       <input type='text' id='typeHere'\n";
        final String secondSnippet = "/>\n";

        testTypeEvents(firstSnippet, secondSnippet);
    }


    @Test
    @Alerts("[object KeyboardEvent] keydown b:true c:true [typeHere] [65] "
            + "[object KeyboardEvent] keypress b:true c:true [typeHere] [97] "
            + "[object KeyboardEvent] keyup b:true c:true [typeHere] [65]")
    public void inputPasswordType() {
        final String firstSnippet = "       <input type='password' id='typeHere'\n";
        final String secondSnippet = "/>\n";

        testTypeEvents(firstSnippet, secondSnippet);
    }

    @Test
    @Alerts("[object KeyboardEvent] keydown b:true c:true [typeHere] [65] "
            + "[object KeyboardEvent] keypress b:true c:true [typeHere] [97] "
            + "[object KeyboardEvent] keyup b:true c:true [typeHere] [65]")
    public void textAreaType() {
        final String firstSnippet = "       <textarea id='typeHere' rows='4' cols='2'\n";
        final String secondSnippet = "></textarea >\n";

        testTypeEvents(firstSnippet, secondSnippet);
    }

    private void testTypeEvents(final String firstSnippet, final String secondSnippet) {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script type='text/javascript'>\n"
                        + "  <!--\n"
                        + "    function dumpEvent(event) {\n"
                        + "      var msg = event;\n"
                        + "      msg = msg + ' ' + event.type;\n"
                        + "      msg = msg + ' b:' + event.bubbles;\n"
                        + "      msg = msg + ' c:' + event.cancelable;\n"
                        + "\n"
                        + "      // target\n"
                        + "      var eTarget;\n"
                        + "      if (event.target) {\n"
                        + "        eTarget = event.target;\n"
                        + "      } else if (event.srcElement) {\n"
                        + "        eTarget = event.srcElement;\n"
                        + "      }\n"
                        + "      // defeat Safari bug\n"
                        + "      if (eTarget.nodeType == 3) {\n"
                        + "        eTarget = eTarget.parentNode;\n"
                        + "      }\n"
                        + "\n"
                        + "      if (eTarget.name) {\n"
                        + "        msg = msg + ' [' + eTarget.name + ']';\n"
                        + "      } else {\n"
                        + "        msg = msg + ' [' + eTarget.id + ']';\n"
                        + "      }\n"
                        + "\n"
                        + "      // key code\n"
                        + "      var eCode;\n"
                        + "      if (event.keyCode) {\n"
                        + "        eCode = event.keyCode;\n"
                        + "      } else if (event.which) {\n"
                        + "        eCode = event.which;\n"
                        + "      } else if (event.button) {\n"
                        + "        eCode = event.button;\n"
                        + "      }\n"
                        + "      if (eCode) {\n"
                        + "        var char = String.fromCharCode(eCode);\n"
                        + "        msg = msg + ' [' + eCode + ']';\n"
                        + "      } else {\n"
                        + "        msg = msg + ' [-]';\n"
                        + "      }\n"
                        + "\n"
                        + "      document.title += ' ' + msg;\n"
                        + "    }\n"
                        + "  //-->\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "  <form id='form' name='form' action='#'>\n"
                        + "    <input type='text' id='start' name='startText'/>\n"
                        + "\n"
                        + firstSnippet
                        + "    onclick='dumpEvent(event);'\n"
                        + "    onkeydown = 'dumpEvent(event);'\n"
                        + "    onkeyup = 'dumpEvent(event);'\n"
                        + "    onkeypress = 'dumpEvent(event);'\n"
                        + "    onchange = 'dumpEvent(event);'"
                        + secondSnippet
                        + "   </form>\n"
                        + "</body>\n"
                        + "</html>\n";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("typeHere");
        elem.getOnclick();
    }

    @Test
    @Alerts({"pass", "fail:66", "fail:undefined"})
    public void eventOnKeyDown() {
        final String html
                = "<html><head>"
                + "<script>\n"
                + "</script>"
                + "</head>\n"
                + "<body>\n"
                + "  <button type='button' id='clickId'>Click Me</button>\n"
                + "  <script>\n"
                + "    function handler(_e) {\n"
                + "      var e = _e ? _e : window.event;\n"
                + "      if (e.keyCode == 65)\n"
                + "       alert('pass');\n"
                + "      else\n"
                + "       alert('fail:' + e.keyCode);\n"
                + "    }\n"
                + "    document.getElementById('clickId').onkeydown = handler;\n"
                + "    document.getElementById('clickId').onclick = handler;\n"
                + "  </script>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickId");
        elem.getOnclick();
    }

    @Test
    @Alerts({"object", "undefined", "undefined", "undefined", "undefined",
            "object", "false", "false", "false", "false"})
    public void keys() {
        final String html =
                "<html><body onload='test(event)'><script>\n"

                        + "  function test(e) {\n"
                        + "   alert(typeof e);\n"
                        + "   alert(e.shiftKey);\n"
                        + "   alert(e.ctrlKey);\n"
                        + "   alert(e.altKey);\n"
                        + "   alert(e.metaKey);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "<div id='div' onclick='test(event)'>abc</div>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("div");
        elem.getOnclick();
    }

    @Test
    @Alerts({"DOMContentLoaded type=DOMContentLoaded", "onLoad"})
    public void dOMContentLoaded() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  document.addEventListener('DOMContentLoaded', onDCL, false);\n"
                + "  function onDCL(e) {\n"
                + "   alert('DOMContentLoaded type=' + e.type);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='alert(\"onLoad\")'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "not canceled", "true", "canceled", "true"})
    public void testPreventDefault() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function preventDef(event) {\n"
                + "    event.preventDefault();\n"
                + "  }\n"

                + "  function addHandler() {\n"
                + "    document.getElementById('checkbox').addEventListener('click', preventDef, false);\n"
                + "  }\n"

                + "  function simulateClick() {\n"
                + "    var evt = document.createEvent('MouseEvents');\n"
                + "    evt.initMouseEvent('click', true, true, window, 0, 0, 0, 0, 0,"
                + " false, false, false, false, 0, null);\n"
                + "    var cb = document.getElementById('checkbox');\n"
                + "    var canceled = !cb.dispatchEvent(evt);\n"
                + "    if(canceled) {\n"
                + "      // A handler called preventDefault\n"
                + "     alert('canceled');\n"
                + "    } else {\n"
                + "      // None of the handlers called preventDefault\n"
                + "     alert('not canceled');\n"
                + "    }\n"
                + "  }\n"

                + "  function test() {\n"
                + "   alert(document.getElementById('checkbox').checked);\n"
                + "    simulateClick();\n"
                + "   alert(document.getElementById('checkbox').checked);\n"
                + "    addHandler();\n"
                + "    simulateClick();\n"
                + "   alert(document.getElementById('checkbox').checked);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <input type='checkbox' id='checkbox'/><label for='checkbox'>Checkbox</label>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false", "SPAN"})
    public void eventTransmission() {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "  <span id='clickMe'>foo</span>\n"
                        + "  <script>\n"

                        + "    function handler(e) {\n"
                        + "     alert(e == null);\n"
                        + "     alert(window.event == null);\n"
                        + "      var theEvent = (e != null) ? e : window.event;\n"
                        + "      var target = theEvent.target ? theEvent.target : theEvent.srcElement;\n"
                        + "     alert(target.tagName);\n"
                        + "    }\n"
                        + "    document.getElementById('clickMe').onclick = handler;\n"
                        + "</script>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();
    }

    @Test
    @Alerts({"capturing", "at target", "bubbling"})
    public void eventPhase() {
        final String html =
                "<html>\n"
                        + "<head><script>\n"

                        + "  function init() {\n"
                        + "    var form = document.forms[0];\n"
                        + "    form.addEventListener('click', alertPhase, true);\n"
                        + "    form.addEventListener('click', alertPhase, false);\n"
                        + "  }\n"

                        + "  function alertPhase(e) {\n"
                        + "    switch (e.eventPhase) {\n"
                        + "      case 1:alert('capturing'); break;\n"
                        + "      case 2:alert('at target'); break;\n"
                        + "      case 3:alert('bubbling'); break;\n"
                        + "      default:alert('unknown');\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='init()'>\n"
                        + "  <form>\n"
                        + "    <input type='button' onclick='alertPhase(event)' id='b'>\n"
                        + "  </form>\n"
                        + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("b");
        elem.getOnclick();
    }


    @Test
    @Alerts({"window capturing", "div capturing", "span capturing",
            "span bubbling", "div", "div bubbling", "window bubbling"})
    public void eventCapturingAndBubbling() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function t(_s) {\n"
                + "    return function() {alert(_s) };\n"
                + "  }\n"

                + "  function init() {\n"
                + "    window.addEventListener('click', t('window capturing'), true);\n"
                + "    window.addEventListener('click', t('window bubbling'), false);\n"
                + "    var oDiv = document.getElementById('theDiv');\n"
                + "    oDiv.addEventListener('click', t('div capturing'), true);\n"
                + "    oDiv.addEventListener('click', t('div bubbling'), false);\n"
                + "    var oSpan = document.getElementById('theSpan');\n"
                + "    oSpan.addEventListener('click', t('span capturing'), true);\n"
                + "    oSpan.addEventListener('click', t('span bubbling'), false);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='init()'>\n"
                + "  <div onclick=\"alert('div')\" id='theDiv'>\n"
                + "    <span id='theSpan'>blabla</span>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("theSpan");
        elem.getOnclick();
    }


    @Test
    @Alerts({"window capturing", "div capturing", "span capturing", "div", "window capturing", "false",
            "true"})
    public void stopPropagation() {
        stopPropagation("stopPropagation()");
    }


    @Test
    @Alerts({"window capturing", "div capturing", "span capturing", "div", "window capturing", "false", "true"})
    public void stopPropagationCancelBubble() {
        stopPropagation("cancelBubble=true");
    }

    private void stopPropagation(final String cancelMethod) {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  var counter = 0;\n"
                + "  function t(_s) {\n"
                + "    return function(e) {\n"
                + "     alert(_s); counter++;\n"
                + "      if (counter >= 4) {\n"
                + "       alert(e.cancelBubble);\n"
                + "        e." + cancelMethod + ";\n"
                + "       alert(e.cancelBubble);\n"
                + "      }\n"
                + "    };\n"
                + "  }\n"
                + "  function init() {\n"
                + "    window.addEventListener('click', t('window capturing'), true);\n"
                + "    var oDiv = document.getElementById('theDiv');\n"
                + "    oDiv.addEventListener('click', t('div capturing'), true);\n"
                + "    var oSpan = document.getElementById('theSpan');\n"
                + "    oSpan.addEventListener('click', t('span capturing'), true);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='init()'>\n"
                + "  <div onclick=\"alert('div')\" id='theDiv'>\n"
                + "    <span id='theSpan'>blabla</span>\n"
                + "  </div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("theSpan");
        elem.getOnclick();
    }

    @Test
    @Alerts({"w", "w 2", "d", "d 2", "s", "s 2", "w", "w 2"})
    public void stopPropagation_WithMultipleEventHandlers() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  var counter = 0;\n"
                + "  function t(_s) {\n"
                + "    return function(e) {alert(_s); counter++; if (counter >= 5) e.stopPropagation(); };\n"
                + "  }\n"
                + "  function init() {\n"
                + "    window.addEventListener('click', t('w'), true);\n"
                + "    window.addEventListener('click', t('w 2'), true);\n"
                + "    var oDiv = document.getElementById('theDiv');\n"
                + "    oDiv.addEventListener('click', t('d'), true);\n"
                + "    oDiv.addEventListener('click', t('d 2'), true);\n"
                + "    var oSpan = document.getElementById('theSpan');\n"
                + "    oSpan.addEventListener('click', t('s'), true);\n"
                + "    oSpan.addEventListener('click', t('s 2'), true);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='init()'>\n"
                + "<div id='theDiv'>\n"
                + "<span id='theSpan'>blabla</span>\n"
                + "</div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("theSpan");
        elem.getOnclick();
    }


    @Test
    @Alerts({"window capturing", "div capturing", "span capturing", "div", "window capturing", "false",
            "true"})
    public void stopImmediatePropagation() {
        stopPropagation("stopImmediatePropagation()");
    }

    @Test
    @Alerts({"w", "w 2", "d", "d 2", "s", "w"})
    public void stopImmediatePropagation_WithMultipleEventHandlers() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  var counter = 0;\n"
                + "  function t(_s) {\n"
                + "    return function(e) {alert(_s); counter++; if (counter >= 5) e.stopImmediatePropagation(); };\n"
                + "  }\n"
                + "  function init() {\n"
                + "    window.addEventListener('click', t('w'), true);\n"
                + "    window.addEventListener('click', t('w 2'), true);\n"
                + "    var oDiv = document.getElementById('theDiv');\n"
                + "    oDiv.addEventListener('click', t('d'), true);\n"
                + "    oDiv.addEventListener('click', t('d 2'), true);\n"
                + "    var oSpan = document.getElementById('theSpan');\n"
                + "    oSpan.addEventListener('click', t('s'), true);\n"
                + "    oSpan.addEventListener('click', t('s 2'), true);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='init()'>\n"
                + "<div id='theDiv'>\n"
                + "<span id='theSpan'>blabla</span>\n"
                + "</div>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("theSpan");
        elem.getOnclick();
    }

    @Test
    @Alerts("[object Event]")
    public void windowEvent() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "   alert(window.event);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"anchor onclick prevented=false",
            "document onclick prevented=false",
            "window onclick prevented=true"})
    public void returnPriority() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  functionalert(msg) {\n"
                + "    window.document.title += msg + ';';\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "<a id='tester' href='javascript:alert(\"FIRED\")'>test: onclick return value</a>\n"
                + "<script>\n"                + "  tester.onclick = function (event) { "
                + "alert('anchor onclick prevented=' + event.defaultPrevented); return true }\n"
                + "  document.onclick = function (event) { "
                + "alert('document onclick prevented=' + event.defaultPrevented); return false }\n"
                + "  window.onclick = function (event) { "
                + "alert('window onclick prevented=' + event.defaultPrevented); return true; }\n"
                + "</script>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("tester");
        elem.getOnclick();
    }
}
