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
package org.loboevolution.canvas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.CanvasGradient;

/**
 * Unit tests for {@link CanvasGradient}.
 */
@ExtendWith(AlertsExtension.class)
public class CanvasGradientTest extends LoboUnitTest {


    @Test
    @Alerts("function")
    public void functions() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  var ctx = canvas.getContext('2d');\n"
                        + "  var gradient = ctx.createRadialGradient(100, 100, 100, 100, 100, 0);\n"
                        + " alert(typeof gradient.addColorStop);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object CanvasGradient]")
    public void addColorStop() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  var ctx = canvas.getContext('2d');\n"
                        + "  var gradient = ctx.createRadialGradient(100, 100, 100, 100, 100, 0);\n"
                        + "  gradient.addColorStop(0, 'green');\n"
                        + "  gradient.addColorStop(1, 'white');\n"
                        + " alert(gradient);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
