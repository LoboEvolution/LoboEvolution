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
 * Tests for AudioProcessingEvent.
 */
@ExtendWith(AlertsExtension.class)
public class AudioProcessingEventTest extends LoboUnitTest {


    @Test
    @Alerts("exception")
    public void createCtor() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new AudioProcessingEvent('audioprocessing');\n"
                + "    } catch (e) {alert('exception') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object AudioProcessingEvent]", "audioprocessing", "false", "false", "false"})
    public void createCtorAllDetails() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var audioCtx = new AudioContext();\n"
                + "      var inputBuffer = audioCtx.createBuffer(2, audioCtx.sampleRate * 3, audioCtx.sampleRate);\n"
                + "      var outputBuffer = audioCtx.createBuffer(2, audioCtx.sampleRate * 3, audioCtx.sampleRate);\n"
                + "      var event = new AudioProcessingEvent('audioprocessing', {"
                + "        'inputBuffer': inputBuffer,\n"
                + "        'outputBuffer': outputBuffer,\n"
                + "        'playbackTime': 4,\n"
                + "      });\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + DUMP_EVENT_FUNCTION
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createCtorMissingDetails() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = new AudioProcessingEvent('audioprocessing');\n"
                + "      dump(event);\n"
                + "    } catch (e) {alert('exception') }\n"
                + DUMP_EVENT_FUNCTION
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createCreateEvent() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    try {\n"
                + "      var event = document.createEvent('AudioProcessingEvent');\n"
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
                + "     alert('AudioProcessingEvent' in window);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
