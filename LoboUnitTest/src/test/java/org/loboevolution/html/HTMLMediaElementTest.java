/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.html;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for HTMLMediaElement.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLMediaElementTest extends LoboUnitTest {

    @Test
    @Alerts("")
    public void canPlayTypeBlank() {
        canPlayType("");
    }

    @Test
    @Alerts("maybe")
    public void canPlayTypeVideoOgg() {
        canPlayType("video/ogg");
    }

    @Test
    @Alerts("maybe")
    public void canPlayTypeVideoMp4() {
        canPlayType("video/mp4");
    }

    @Test
    @Alerts("maybe")
    public void canPlayTypeVideoWebm() {
        canPlayType("video/webm");
    }

    @Test
    @Alerts("maybe")
    public void canPlayTypeAudioMpeg() {
        canPlayType("audio/mpeg");
    }

    @Test
    @Alerts("maybe")
    public void canPlayTypeAudioMp4() {
        canPlayType("audio/mp4");
    }

    @Test
    @Alerts("probably")
    public void canPlayTypeVideoOggCodecs() {
        canPlayType("video/ogg; codecs=\"theora, vorbis\"");
    }

    @Test
    @Alerts("probably")
    public void canPlayTypeVideoMp4Codecs() {
        canPlayType("video/mp4; codecs=\"avc1.4D401E, mp4a.40.2\"");
    }

    @Test
    @Alerts("probably")
    public void canPlayTypeAudioWebmCodecs() {
        canPlayType("video/webm; codecs=\"vp8.0, vorbis\"");
    }

    @Test
    @Alerts("probably")
    public void canPlayTypeAudioOggCodecs() {
        canPlayType("audio/ogg; codecs=\"vorbis\"");
    }

    @Test
    @Alerts("probably")
    public void canPlayTypeAudioMp4Codecs() {
        canPlayType("audio/mp4; codecs=\"mp4a.40.5\"");
    }

    private void canPlayType(final String type) {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body>\n"
                + "<script>\n"
                + "try {\n"
                + "  var video = document.createElement('video');"
                + "  alert(video.canPlayType('" + type + "'));\n"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLAudioElement]", "done"})
    public void pause() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var a = new Audio('1.mp3');\n"
                + "    alert(a);\n"
                + "    a.pause();\n"
                + "    alert('done');\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
