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
 * Tests for AnimationEvent.
 */
@ExtendWith(AlertsExtension.class)
public class AnimationEventTest extends LoboUnitTest {


    @Test
    @Alerts({"[object AnimationEvent]", "animationstart", "false", "false", "false"})
    public void createCtor() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new AnimationEvent('animationstart');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object AnimationEvent]", "null", "false", "false", "false"})
    public void createCreateEvent() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('AnimationEvent');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception'); }\n"
                + "  }\n"
                + DUMP_EVENT_FUNCTION
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void inWindow() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "     alert('AnimationEvent' in window);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"animationstart", "animationend"})
    public void animate() {
        final String html
                = "<html><head>\n"
                + "<style>\n"
                + "  .animate {  animation: identifier .1s ; }\n"
                + "  @keyframes identifier {\n"
                + "    0% { width: 0px; }\n"
                + "    100% { width: 30px; }\n"
                + "  }\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var el = document.getElementById('div1');\n"
                + "  el.addEventListener('animationstart', function(e) {\n"
                + "   alert(e.type);\n"
                + "  });\n"
                + "  el.addEventListener('animationend', function(e) {\n"
                + "   alert(e.type);\n"
                + "  });\n"
                + "  el.className = 'animate';\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "<div id='div1'>TXT</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
