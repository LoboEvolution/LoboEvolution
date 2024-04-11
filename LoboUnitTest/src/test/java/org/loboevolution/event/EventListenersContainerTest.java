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
 * Tests for EventListenersContainer
 */
@ExtendWith(AlertsExtension.class)
public class EventListenersContainerTest extends LoboUnitTest {

    @Test
    @Alerts({"someName", "myevent", "[object Window]"})
    public void addEventListener() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function MyEventListener(name) {\n"
                + "    this.name = name;\n"
                + "  }\n"
                + "\n"
                + "  MyEventListener.prototype = {\n"
                + "    handleEvent: function(evt) {\n"
                + "     alert(this.name);\n"
                + "     alert(evt.type);\n"
                + "     alert(evt.target);\n"
                + "    }\n"
                + "  }\n"
                + "\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var listener = new MyEventListener('someName');\n"
                + "      window.addEventListener('myevent', listener, false);\n"
                + "      window.dispatchEvent(new Event('myevent'));\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"someName", "myevent", "[object HTMLBodyElement]"})
    public void addEventListenerNode() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function MyEventListener(name) {\n"
                + "    this.name = name;\n"
                + "  }\n"
                + "\n"
                + "  MyEventListener.prototype = {\n"
                + "    handleEvent: function(evt) {\n"
                + "     alert(this.name);\n"
                + "     alert(evt.type);\n"
                + "     alert(evt.target);\n"
                + "    }\n"
                + "  }\n"
                + "\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var listener = new MyEventListener('someName');\n"
                + "      document.body.addEventListener('myevent', listener, false);\n"
                + "      document.body.dispatchEvent(new Event('myevent'));\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({})
    public void addEventListenerNoHandleEvent() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function MyEventListener(name) {\n"
                + "    this.name = name;\n"
                + "  }\n"
                + "\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var listener = new MyEventListener('someName');\n"
                + "      window.addEventListener('myevent', listener, false);\n"
                + "      window.dispatchEvent(new Event('myevent'));\n"
                + "    } catch (e) {\n"
                + "     alert('exception');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
