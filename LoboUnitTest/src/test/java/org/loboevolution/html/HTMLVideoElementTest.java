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
package org.loboevolution.html;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLVideoElement;

/**
 * Tests for {@link HTMLVideoElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLVideoElementTest extends LoboUnitTest {

    @Test
    @Alerts("false")
    public void prototype() {
        final String html
                = "<html><body>\n"
                + "    <script>\n"
                + "try {\n"
                + "alert(HTMLVideoElement.prototype == null);\n"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLVideoElement]", "function HTMLVideoElement() { [native code] }"})
    public void type() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('v1');\n"
                + "    try {\n"
                + "      alert(elem);\n"
                + "      alert(HTMLVideoElement);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <video id='v1'/>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "VIDEO"})
    public void nodeTypeName() {
        final String html
                = "<html><body>\n"
                + "<video id='v' src='flower.mp4'></video>"
                + "    <script>\n"
                + "try {\n"
                + "  var video = document.getElementById('v');\n"
                + "  alert(video.nodeType);"
                + "  alert(video.nodeName);"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"string", "flower.mp4", "tree.mp4",
            "<video id=\"v\" src=\"tree.mp4\"></video>"})
    public void src() {
        final String html
                = "<html><body>\n"
                + "<video id='v' src='flower.mp4'></video>"
                + "    <script>\n"
                + "try {\n"
                + "  var video = document.getElementById('v');\n"
                + "  var src = video.src;\n"
                + "  alert(typeof src);"
                + "  alert(src);"
                + "  video.src = 'tree.mp4';\n"
                + "  alert(video.src);"
                + "  alert(video.outerHTML);"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"string", "", "tree.mp4",
            "<video id=\"v\" src=\"tree.mp4\"><source src=\"flower.mp4\" type=\"video/mp4\"></video>"})
    public void srcChild() {
        final String html
                = "<html><body>\n"
                + "<video id='v'><source src='flower.mp4' type='video/mp4'></video>"
                + "    <script>\n"
                + "try {\n"
                + "  var video = document.getElementById('v');\n"
                + "  var src = video.src;\n"
                + "  alert(typeof src);"
                + "  alert(src);"
                + "  video.src = 'tree.mp4';\n"
                + "  alert(video.src);"
                + "  alert(video.outerHTML);"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"string", ""})
    public void srcNotDefined() {
        final String html
                = "<html><body>\n"
                + "<video id='v'></video>"
                + "    <script>\n"
                + "try {\n"
                + "  var src = document.getElementById('v').src;\n"
                + "  alert(typeof src);"
                + "  alert(src);"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"string", ""})
    public void currentSrc() {
        final String html
                = "<html><body>\n"
                + "<video id='v' src='flower.mp4'></video>"
                + "    <script>\n"
                + "try {\n"
                + "  var currentSrc = document.getElementById('v').currentSrc;\n"
                + "  alert(typeof currentSrc);"
                + "  alert(currentSrc);"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"string", ""})
    public void currentSrcChild() {
        final String html
                = "<html><body>\n"
                + "<video id='v'><source src='flower.mp4' type='video/mp4'></video>"
                + "    <script>\n"
                + "try {\n"
                + "  var currentSrc = document.getElementById('v').currentSrc;\n"
                + "  alert(typeof currentSrc);"
                + "  alert(currentSrc);"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"string", ""})
    public void currentSrcNotDefined() {
        final String html
                = "<html><body>\n"
                + "<video id='v'></video>"
                + "    <script>\n"
                + "try {\n"
                + "  var currentSrc = document.getElementById('v').currentSrc;\n"
                + "  alert(typeof currentSrc);"
                + "  alert(currentSrc);"
                + "} catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }
}
