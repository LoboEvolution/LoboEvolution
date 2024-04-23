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
import org.loboevolution.html.dom.HTMLImageElement;

/**
 * Tests for {@link HTMLImageElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLImageElement2Test extends LoboUnitTest {

    @Test
    @Alerts({"0", "1"})
    public void onLoadCalledWhenImageDownloadedstatic() {
        final String html = "<html>\n"
                + "<body>\n"
                + "  <img src='foo.png' onload='test()'>\n"
                + "  <script>\n"
                + "    alert(0);\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(1);\n"
                + "    }\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void onLoadCalledWhenImageDownloadedDynamic() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  var i = document.createElement('img');\n"
                + "  i.src = '" + URL_SECOND + "';\n"
                + "  i.src = '" + URL_THIRD + "';\n"
                + "  i.onload = function() { alert(1); };\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void onLoadCalledWhenImageDownloadedDynamicOnLoadAlreadyset() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  var i = document.createElement('img');\n"
                + "  i.onload = function() { alert(1); };\n"
                + "  i.src = '" + URL_SECOND + "';\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2"})
    public void onLoadCalledWhenImageDownloadedDynamicTwoSteps() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  var i = document.createElement('img');\n"
                + "  i.src = '" + URL_SECOND + "';\n"
                + "  i.onload = function() {\n"
                + "    alert(1);\n"
                + "    var i2 = document.createElement('img');\n"
                + "    i2.src = '" + URL_THIRD + "';\n"
                + "    i2.onload = function() {\n"
                + "      alert(2);\n"
                + "    };\n"
                + "  };\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"image one", "image two"})
    public void onLoadCalledWhenImageDownloadedMixed() {
        final String html
                = "<html><body><img id='img' name='img'/><script>\n"
                + "  var i = document.getElementById('img');\n"
                + "  i.onload = function() {\n"
                + "    alert('image one');\n"
                + "    i.onload = function() {\n"
                + "      alert('image two');\n"
                + "    };\n"
                + "    i.src = '" + URL_THIRD + "';\n"
                + "  };\n"
                + "  i.setAttribute('src','" + URL_SECOND + "');\n"
                + "  var t = setTimeout(function() {clearTimeout(t);}, 500);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }
}
