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
package org.loboevolution.canvas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.ImageData;

/**
 * Tests for {@link ImageData}.
 */
@ExtendWith(AlertsExtension.class)
public class ImageDataTest extends LoboUnitTest {


    @Test
    @Alerts({"8", "1", "2",
            "0", "190", "3", "255", "0", "190", "3", "255",
            "8", "2", "1",
            "0", "190", "3", "255", "0", "190", "3", "255"})
    public void ctorArray() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  if (typeof ImageData != 'function') {alert('no ctor'); return; }"

                        + "  var arr = new Uint8ClampedArray(8);\n"
                        + "  for (var i = 0; i < arr.length; i += 4) {\n"
                        + "    arr[i + 0] = 0;\n"
                        + "    arr[i + 1] = 190;\n"
                        + "    arr[i + 2] = 3;\n"
                        + "    arr[i + 3] = 255;\n"
                        + "  }\n"

                        + "  var imageData = new ImageData(arr, 1);\n"
                        + " alert(imageData.data.length);\n"
                        + " alert(imageData.width);\n"
                        + " alert(imageData.height);\n"

                        + "  var data = imageData.data;\n"
                        + "  for (var i = 0; i < data.length; i++) {\n"
                        + "   alert(data[i]);\n"
                        + "  }\n"

                        + "  var imageData = new ImageData(arr, 2);\n"
                        + " alert(imageData.data.length);\n"
                        + " alert(imageData.width);\n"
                        + " alert(imageData.height);\n"

                        + "  var data = imageData.data;\n"
                        + "  for (var i = 0; i < data.length; i++) {\n"
                        + "   alert(data[i]);\n"
                        + "  }\n"

                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"8", "1", "2",
            "0", "190", "3", "255", "0", "190", "3", "255"})
    public void ctorArrayWidthHeight() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  if (typeof ImageData != 'function') {alert('no ctor'); return; }"

                        + "  var arr = new Uint8ClampedArray(8);\n"
                        + "  for (var i = 0; i < arr.length; i += 4) {\n"
                        + "    arr[i + 0] = 0;\n"
                        + "    arr[i + 1] = 190;\n"
                        + "    arr[i + 2] = 3;\n"
                        + "    arr[i + 3] = 255;\n"
                        + "  }\n"

                        + "  var imageData = new ImageData(arr, 1, 2);\n"
                        + " alert(imageData.data.length);\n"
                        + " alert(imageData.width);\n"
                        + " alert(imageData.height);\n"

                        + "  var data = imageData.data;\n"
                        + "  for (var i = 0; i < data.length; i++) {\n"
                        + "   alert(data[i]);\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "exception", "exception", "exception", "exception", "exception", "exception"})
    public void ctorArrayInvalid() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"

                        + "  try {\n"
                        + "    var imageData = new ImageData();\n"
                        + "  } catch (e) {alert('exception');}\n"

                        + "  try {\n"
                        + "    var imageData = new ImageData(-2, 1);\n"
                        + "  } catch (e) {alert('exception');}\n"

                        + "  try {\n"
                        + "    var imageData = new ImageData(2, -1);\n"
                        + "  } catch (e) {alert('exception');}\n"

                        + "  try {\n"
                        + "    var imageData = new ImageData(-2, -1);\n"
                        + "  } catch (e) {alert('exception');}\n"

                        + "  var arr = new Uint8ClampedArray(8);\n"
                        + "  try {\n"
                        + "    var imageData = new ImageData(arr, 3);\n"
                        + "  } catch (e) {alert('exception');}\n"

                        + "  arr = new Uint8ClampedArray(11);\n"
                        + "  try {\n"
                        + "    var imageData = new ImageData(arr, 2);\n"
                        + "  } catch (e) {alert('exception');}\n"

                        + "  arr = new Uint8ClampedArray(8);\n"
                        + "  try {\n"
                        + "    var imageData = new ImageData(arr, 2, 2);\n"
                        + "  } catch (e) {alert('exception');}\n"

                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"8", "2", "1", "0", "0", "0", "0", "0", "0", "0", "0"})
    public void ctorWidthHeight() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  if (typeof ImageData != 'function') {alert('no ctor'); return; }"

                        + "  var imageData = new ImageData(2, 1);\n"
                        + " alert(imageData.data.length);\n"
                        + " alert(imageData.width);\n"
                        + " alert(imageData.height);\n"

                        + "  var data = imageData.data;\n"
                        + "  for (var i = 0; i < data.length; i++) {\n"
                        + "   alert(data[i]);\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"200", "100", "50", "255", "100", "50", "125", "255", "123", "111", "222", "255"})
    public void getImageData() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillStyle = 'rgb(200,100,50)';\n"
                        + "    ctx.fillRect(0, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgba(0, 0, 200, 0.5)';\n"
                        + "    ctx.fillRect(1, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgb(123,111,222)';\n"
                        + "    ctx.fillRect(2, 0, 2, 2);\n"
                        + "    var imageData = ctx.getImageData(0, 0, 3, 1);\n"
                        + "    var data = imageData.data;\n"
                        + "    for (var i = 0; i < data.length; i++) {\n"
                        + "     alert(data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"})
    public void getImageDataOutside() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillStyle = 'rgb(200,100,50)';\n"
                        + "    ctx.fillRect(0, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgba(0, 0, 200, 0.5)';\n"
                        + "    ctx.fillRect(1, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgb(123,111,222)';\n"
                        + "    ctx.fillRect(2, 0, 2, 2);\n"
                        + "    var imageData = ctx.getImageData(-10, -10, 3, 1);\n"
                        + "    var data = imageData.data;\n"
                        + "    for (var i = 0; i < data.length; i++) {\n"
                        + "     alert(data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"})
    public void getImageDataOutside2() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillStyle = 'rgb(200,100,50)';\n"
                        + "    ctx.fillRect(0, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgba(0, 0, 200, 0.5)';\n"
                        + "    ctx.fillRect(1, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgb(123,111,222)';\n"
                        + "    ctx.fillRect(2, 0, 2, 2);\n"
                        + "    var imageData = ctx.getImageData(500, 500, 3, 1);\n"
                        + "    var data = imageData.data;\n"
                        + "    for (var i = 0; i < data.length; i++) {\n"
                        + "     alert(data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "0", "200", "100", "50", "255", "100", "50", "125", "255"})
    public void getImageDataPartlyOutside() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillStyle = 'rgb(200,100,50)';\n"
                        + "    ctx.fillRect(0, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgba(0, 0, 200, 0.5)';\n"
                        + "    ctx.fillRect(1, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgb(123,111,222)';\n"
                        + "    ctx.fillRect(2, 0, 2, 2);\n"
                        + "    var imageData = ctx.getImageData(-1, 0, 3, 1);\n"
                        + "    var data = imageData.data;\n"
                        + "    for (var i = 0; i < data.length; i++) {\n"
                        + "     alert(data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"})
    public void getImageDataPartlyOutside2() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillStyle = 'rgb(200,100,50)';\n"
                        + "    ctx.fillRect(0, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgba(0, 0, 200, 0.5)';\n"
                        + "    ctx.fillRect(1, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgb(123,111,222)';\n"
                        + "    ctx.fillRect(2, 0, 2, 2);\n"
                        + "    var imageData = ctx.getImageData(298, 149, 3, 1);\n"
                        + "    var data = imageData.data;\n"
                        + "    for (var i = 0; i < data.length; i++) {\n"
                        + "     alert(data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"200", "100", "50", "255", "200", "100", "50", "255", "0", "0", "0", "0"})
    public void getImageDataDrawAfter() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillStyle = 'rgb(200,100,50)';\n"
                        + "    ctx.fillRect(0, 0, 2, 2);\n"
                        + "    var imageData = ctx.getImageData(0, 0, 3, 1);\n"

                        + "    ctx.fillStyle = 'rgba(0, 0, 200, 0.5)';\n"
                        + "    ctx.fillRect(1, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgb(123,111,222)';\n"
                        + "    ctx.fillRect(2, 0, 2, 2);\n"

                        + "    var data = imageData.data;\n"
                        + "    for (var i = 0; i < data.length; i++) {\n"
                        + "     alert(data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"})
    public void data() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillStyle = 'rgb(200,100,50)';\n"
                        + "    ctx.fillRect(0, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgba(0, 0, 200, 0.5)';\n"
                        + "    ctx.fillRect(1, 0, 2, 2);\n"
                        + "    ctx.fillStyle = 'rgb(123,111,222)';\n"
                        + "    ctx.fillRect(2, 0, 2, 2);\n"
                        + "    var imageData = ctx.getImageData(0, 0, 3, 1);\n"
                        + "    for (var i = 0; i < imageData.data.length; i++) {\n"
                        + "      imageData.data[i] = i;\n"
                        + "     alert(imageData.data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"8", "1", "2",
            "13", "0", "17", "0", "0", "0", "0", "42"})
    public void setValues() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"

                        + "    var imageData = ctx.createImageData(1, 2);\n"
                        + "   alert(imageData.data.length);\n"
                        + "   alert(imageData.width);\n"
                        + "   alert(imageData.height);\n"

                        + "    imageData.data[0] = 13;\n"
                        + "    imageData.data[2] = 17;\n"
                        + "    imageData.data[7] = 42;\n"
                        + "    imageData.data[8] = 43;\n"
                        + "    imageData.data[-5] = 7;\n"
                        + "    imageData.data[100] = 11;\n"
                        + "    for (var i = 0; i < imageData.data.length; i++) {\n"
                        + "     alert(imageData.data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"24", "2", "3",
            "0", "0", "17", "0", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"})
    public void createImageData() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillRect(1, 1, 13, 11);\n"

                        + "    var imageData = ctx.createImageData(2, 3);\n"
                        + "   alert(imageData.data.length);\n"
                        + "   alert(imageData.width);\n"
                        + "   alert(imageData.height);\n"

                        + "    imageData.data[2] = 17;\n"
                        + "    for (var i = 0; i < imageData.data.length; i++) {\n"
                        + "     alert(imageData.data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"24", "2", "3",
            "0", "0", "17", "0", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"})
    public void createImageDataFlipped() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillRect(1, 1, 13, 11);\n"

                        + "    var imageData = ctx.createImageData(-2, -3);\n"
                        + "   alert(imageData.data.length);\n"
                        + "   alert(imageData.width);\n"
                        + "   alert(imageData.height);\n"

                        + "    imageData.data[2] = 17;\n"
                        + "    for (var i = 0; i < imageData.data.length; i++) {\n"
                        + "     alert(imageData.data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"8",
            "0", "0", "17", "0", "0", "0", "0", "0",
            "8", "1", "2",
            "0", "0", "0", "0", "0", "0", "0", "0"})
    public void createImageDataFromImageData() {
        final String html =
                "<html><head><script>\n"

                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  if (canvas.getContext) {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    var imageData = ctx.createImageData(1, 2);\n"
                        + "   alert(imageData.data.length);\n"
                        + "    imageData.data[2] = 17;\n"
                        + "    for (var i = 0; i < imageData.data.length; i++) {\n"
                        + "     alert(imageData.data[i]);\n"
                        + "    }\n"

                        + "    var imageDataCopy = ctx.createImageData(imageData);\n"
                        + "   alert(imageDataCopy.data.length);\n"
                        + "   alert(imageDataCopy.width);\n"
                        + "   alert(imageDataCopy.height);\n"
                        + "    for (var i = 0; i < imageDataCopy.data.length; i++) {\n"
                        + "     alert(imageDataCopy.data[i]);\n"
                        + "    }\n"
                        + "  }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'><canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }
}
