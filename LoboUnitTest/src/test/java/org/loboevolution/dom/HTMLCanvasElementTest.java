/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLCanvasElement}.
 */
public class HTMLCanvasElementTest extends LoboUnitTest {

     /**
      * <p>test.</p>
      */
     @Test
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
        final String[] messages = {"300", "number", "150", "number", "[object CanvasRenderingContext2D]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>toDataUrl.</p>
     */
    @Test
    public void toDataUrl() {
        final String html =
                "<html>\n"
                        + "<body><canvas id='myCanvas'></canvas>\n"
                        + "<script>\n"
                        + "try {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  alert(canvas.toDataURL());\n"
                        + "  alert(canvas.toDataURL('image/png'));\n"
                        + "}\n"
                        + "catch (e) { alert('exception'); }\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAAxUlEQVR4nO3BMQEAAADCoPVPbQhf"
                + "oAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAOA1v9QAATX68/0AAAAASUVORK5CYII=",
                "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAAxUlEQVR4nO3BMQEAAADCoPVPbQhf"
                        + "oAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAOA1v9QAATX68/0AAAAASUVORK5CYII="};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getContext.</p>
     */
    @Test
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
        final String[] messages = {"[object CanvasRenderingContext2D]", "[object WebGLRenderingContext]",
                "[object WebGLRenderingContext]", "[object WebGL2RenderingContext]", null, null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getContextShouldNotThrowForSize0.</p>
     */
    @Test
    public void getContextShouldNotThrowForSize0() {
        final String html = "<html><body>\n"
                + "<canvas id='it' width=0 height=0></canvas>\n"
                + "<script>\n"
                + "var canvas = document.getElementById('it');\n"
                + "if (canvas.getContext){\n"
                + "  alert(canvas.getContext('2d'));\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>\n";
        final String[] messages = {"[object CanvasRenderingContext2D]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getWidthDot.</p>
     */
    @Test
    public void getWidthDot() {
        final String[] messages = {"3", "3"};
        getWidth("3.1", messages);
    }

    /**
     * <p>getWidthDigitAlpha.</p>
     */
    @Test
    public void getWidthDigitAlpha() {
        final String[] messages =  {"3", "3"};
        getWidth("3a1", messages);
    }

      /**
       * <p>getWidthAlphaDigit.</p>
       */
      @Test
    public void getWidthAlphaDigit() {
        final String[] messages =  {"300", "150"};
        getWidth("a7", messages);
    }

    /**
     * <p>getWidthAlpha.</p>
     */
    @Test
    public void getWidthAlpha() {
        final String[] messages = {"300", "150"};
        getWidth("abb", messages);
    }

    private void getWidth(final String value, String[] messages) {
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
        checkHtmlAlert(html, messages);
    }
}
