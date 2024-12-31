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
import org.loboevolution.html.dom.HTMLCanvasElement;

/**
 * Unit tests for {@link HTMLCanvasElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLCanvasElementTest extends LoboUnitTest {

    @Test
    @Alerts({"300", "number", "150", "number", "[object CanvasRenderingContext2D]"})
    public void test() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var canvas = document.getElementById('myCanvas');\n"
                        + "        alert(canvas.width);\n"
                        + "        alert(typeof canvas.width);\n"
                        + "        alert(canvas.height);\n"
                        + "        alert(typeof canvas.height);\n"
                        + "        if (canvas.getContext){\n"
                        + "          var ctx = canvas.getContext('2d');\n"
                        + "          alert(ctx);\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAAAXNSR0IArs4c6QAABGJJREFUeF7t1AEJAAAMAsHZv/RyPN"
            + "wSyDncOQIECEQEFskpJgECBM5geQICBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGD"
            + "lalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBD"
            + "ICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfID"
            + "BAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAY"
            + "PlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUo"
            + "AQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDla"
            + "lKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDIC"
            + "BitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBA"
            + "hkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPl"
            + "BwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQ"
            + "IGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalK"
            + "UAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBi"
            + "tTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhk"
            + "BAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBw"
            + "gQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIG"
            + "yw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAgQdWMQCX4yW9owAAAABJRU5ErkJggg==")
    public void toDataUrl() {
        final String html =
                "<html>\n"
                        + "<body><canvas id='myCanvas'></canvas>\n"
                        + "    <script>\n" 
                + "try {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  alert(canvas.toDataURL());\n"
                        + "}\n"
                        + "catch (e) { alert('exception'); }\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAAAXNSR0IArs4c6QAABGJJREFUeF7t1AEJAAAMAsHZv/RyPN"
            + "wSyDncOQIECEQEFskpJgECBM5geQICBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGD"
            + "lalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBD"
            + "ICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfID"
            + "BAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAY"
            + "PlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUo"
            + "AQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDla"
            + "lKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDIC"
            + "BitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBA"
            + "hkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPl"
            + "BwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQ"
            + "IGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalK"
            + "UAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBi"
            + "tTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhk"
            + "BAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBw"
            + "gQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIG"
            + "yw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAgQdWMQCX4yW9owAAAABJRU5ErkJggg==")

    public void toDataUrlPng() {
        final String html =
                "<html>\n"
                        + "<body><canvas id='myCanvas'></canvas>\n"
                        + "    <script>\n" 
                + "try {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  alert(canvas.toDataURL('image/png'));\n"
                        + "}\n"
                        + "catch (e) { alert('exception'); }\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object CanvasRenderingContext2D]", "[object WebGLRenderingContext]",
            "[object WebGLRenderingContext]", "[object WebGL2RenderingContext]", "null", "null"})
    public void getContext() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var canvas = document.createElement('canvas');\n"
                        + "        if (canvas.getContext){\n"
                        + "          alert(document.createElement('canvas').getContext('2d'));\n"
                        + "          alert(document.createElement('canvas').getContext('webgl'));\n"
                        + "          alert(document.createElement('canvas').getContext('experimental-webgl'));\n"
                        + "          alert(document.createElement('canvas').getContext('webgl2'));\n"
                        + "          alert(document.createElement('canvas').getContext('experimental-webgl2'));\n"
                        + "          alert(document.createElement('canvas').getContext('abcdefg'));\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'></body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    /**
     * Was throwing exception as of HU 2.18.
     */
    @Test
    @Alerts("[object CanvasRenderingContext2D]")
    public void getContextShouldNotThrowForSize0() {
        final String html = "<html><body>\n"
                + "<canvas id='it' width=0 height=0></canvas>\n"
                + "    <script>\n"
                + "var canvas = document.getElementById('it');\n"
                + "if (canvas.getContext){\n"
                + "  alert(canvas.getContext('2d'));\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "3"})
    public void getWidthDot() {
        getWidth("3.1");
    }

    @Test
    @Alerts({"3", "3"})
    public void getWidthDigitAlpha() {
        getWidth("3a1");
    }

    @Test
    @Alerts({"300", "150"})
    public void getWidthAlphaDigit() {
        getWidth("a7");
    }

    @Test
    @Alerts({"300", "150"})
    public void getWidthAlpha() {
        getWidth("abb");
    }

    private void getWidth(final String value) {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var canvas = document.getElementById('myId');\n"
                        + "        alert(canvas.width);\n"
                        + "        alert(canvas.height);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <canvas id='myId' width='" + value + "' height='" + value + "'></canvas>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
