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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for EventNode.
 */
@ExtendWith(AlertsExtension.class)
public class EventNodeTest extends LoboUnitTest {

    @Test
    @Alerts("fireEvent not available")
    public void fireEvent() {
        final String html
                = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var form = document.getElementById('myForm');\n"
                + "    if (!form.fireEvent) {alert('fireEvent not available'); return }\n"
                + "   alert(form.fireEvent('onsubmit'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <form id='myForm'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("createEventObject not available")
    public void fireEvent_initFromTemplate() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (!document.createEventObject) {alert('createEventObject not available'); return }\n"
                + "      var myEvent = document.createEventObject();\n"
                + "      myEvent.eventType = 'onclick';\n"
                + "      myEvent.foo = 'hello';\n"
                + "      var butt = document.getElementById('theButton');\n"
                + "      butt.fireEvent('onclick', myEvent);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "  <span id='theButton' onclick='alert(event.foo)'>a span</span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("mousedown span,mouseup span,click span,mousedown text,focus text,mouseup text,"
            + "click text,mousedown image,focus image,mouseup image,click image,mousedown textarea,focus textarea,"
            + "mouseup textarea,click textarea,")
    public void clickEvents() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function alert(text) {\n"
                + "      var textarea = document.getElementById('myTextarea');\n"
                + "      textarea.value += text + ',';\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body>\n"
                + "  <span id='testSpan' onfocus=\"alert('will not be triggered')\" onmousedown=\"alert('mousedown span')\""
                + " onclick=\"alert('click span')\" onmouseup=\"alert('mouseup span')\">test span</span>\n"
                + "  <form>\n"
                + "    <input type='text' id='testInput' onmousedown=\"alert('mousedown text')\""
                + " onclick=\"alert('click text')\" onmouseup=\"alert('mouseup text')\" onfocus=\"alert('focus text')\">\n"
                + "    <input type='image' id='testImage' onmousedown=\"alert('mousedown image')\""
                + " onclick=\"alert('click image'); return false;\" onmouseup=\"alert('mouseup image')\""
                + " onfocus=\"alert('focus image')\">\n"
                + "    <textarea id='testTextarea' onfocus=\"alert('focus textarea')\""
                + " onmousedown=\"alert('mousedown textarea')\" onclick=\"alert('click textarea')\""
                + " onmouseup=\"alert('mouseup textarea')\" onfocus=\"alert('focus textarea')\"></textarea>\n"
                + "  </form>\n"
                + "  <textarea id='myTextarea' cols='80' rows='10'></textarea>\n"
                + "</body>"
                + " <script>"
                + "  document.getElementById('testSpan').click();"
                + "  document.getElementById('testInput').click();"
                + "  document.getElementById('testSpan').click();"
                + "  document.getElementById('testImage').click();"
                + "  document.getElementById('testTextarea').click();"
                + "  </script>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("mousedown label,mouseup label,click label,focus text,click text,")
    public void clickEventsLabel() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function alert(text) {\n"
                + "      var textarea = document.getElementById('myTextarea');\n"
                + "      textarea.value += text + ',';\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body>\n"
                + "  <label id='testLabel' for='testInput'"
                + " onfocus=\"alert('will not be triggered')\" onmousedown=\"alert('mousedown label')\""
                + " onclick=\"alert('click label')\" onmouseup=\"alert('mouseup label')\">test label</label>\n"
                + "  <form>\n"
                + "    <input type='text' id='testInput' onmousedown=\"alert('mousedown text')\""
                + " onclick=\"alert('click text')\" onmouseup=\"alert('mouseup text')\" onfocus=\"alert('focus text')\">\n"
                + "  </form>\n"
                + "  <textarea id='myTextarea' cols='80' rows='10'></textarea>\n"
                + "</body>"
                + " <script>"
                + "  document.getElementById('testLabel').click();"
                + "  </script>"
                + "</html>";
        checkHtmlAlert(html);
    }
}
