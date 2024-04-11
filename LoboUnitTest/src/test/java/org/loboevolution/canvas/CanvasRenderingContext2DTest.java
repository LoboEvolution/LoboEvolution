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
import org.loboevolution.html.dom.CanvasRenderingContext2D;

/**
 * Unit tests for {@link CanvasRenderingContext2D}.
 */
@ExtendWith(AlertsExtension.class)
public class CanvasRenderingContext2DTest extends LoboUnitTest {


    @Test
    @Alerts("done")
    public void test() {
        final String html =
                "<html><head><script>\n"
                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  try {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.fillStyle = 'rgb(200,0,0)';\n"
                        + "    ctx.fillRect(10, 10, 55, 50);\n"
                        + "    ctx.fillStyle = 'rgba(0, 0, 200, 0.5)';\n"
                        + "    ctx.fillRect(30, 30, 55, 50);\n"
                        + "    ctx.drawImage(canvas, 1, 2);\n"
                        + "    ctx.drawImage(canvas, 1, 2, 3, 4);\n"
                        + "    ctx.drawImage(canvas, 1, 1, 1, 1, 1, 1, 1, 1);\n"
                        + "    ctx.translate(10, 10);\n"
                        + "    ctx.scale(10, 10);\n"
                        + "    ctx.fillRect(30, 30, 55, 50);\n"
                        + "    ctx.beginPath();\n"
                        + "    ctx.moveTo(0, 10);\n"
                        + "    ctx.lineTo(10, 10);\n"
                        + "    ctx.quadraticCurveTo(0, 10, 15, 10);\n"
                        + "    ctx.closePath();\n"
                        + "    ctx.rotate(1.234);\n"
                        + "   alert('done');\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <canvas id='myCanvas'></canvas></body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"addHitRegion", "drawCustomFocusRing", "drawSystemFocusRing", "removeHitRegion",
            "scrollPathIntoView", "36 methods"})
    public void methods() {
        final String[] methods = {"addHitRegion", "arc", "arcTo", "beginPath", "bezierCurveTo", "clearRect", "clip",
                "closePath", "createImageData", "createLinearGradient", "createPattern", "createRadialGradient",
                "drawImage", "drawCustomFocusRing", "drawSystemFocusRing", "ellipse", "fill", "fillRect", "fillText",
                "getImageData", "getLineDash", "isPointInPath", "lineTo", "measureText", "moveTo", "putImageData",
                "quadraticCurveTo", "rect", "removeHitRegion", "restore", "rotate", "save", "scale", "scrollPathIntoView",
                "setLineDash", "setTransform", "stroke", "strokeRect", "strokeText", "transform", "translate"};
        final String html = "<html><body>\n"
                + "<canvas id='myCanvas'></canvas>\n"
                + "<script>\n"                + "  var canvas = document.getElementById('myCanvas');\n"
                + "  var nbMethods = 0;\n"
                + "  var methods = ['" + String.join("', '", methods) + "'];\n"
                + "  try {\n"
                + "    var ctx = canvas.getContext('2d');\n"
                + "    for (var i = 0; i < methods.length; i++) {\n"
                + "      if (typeof ctx[methods[i]] == 'function')\n"
                + "        nbMethods++;\n"
                + "      else\n"
                + "       alert(methods[i]);\n"
                + "    }\n"
                + "   alert(nbMethods + ' methods');\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    private void drawImage(final String png, final String canvasSetup, final String drawJS) {
        final String html = "<html><head>\n"
                + "<script>"
                + "  function test() {\n"
                + "    var img = document.getElementById('myImage');\n"
                + "    var canvas = document.createElement('canvas');\n"
                + canvasSetup
                + "    if (canvas.getContext) {\n"
                + "      var context = canvas.getContext('2d');\n"
                + drawJS
                + "     alert(canvas.toDataURL());\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage' src='" + png + "'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAAutJREFUO"
            + "E99k19sU1Ucxz/3du1du94tLcE1IjIUHlASRcEOtnVccWyIJWRmuhD02cBQlMTnvfsiIUQy9uIDCf+hWYFN3Q0mBAIJSCQy"
            + "yIgORMtGHe1u71273T/m3qYN2wPn5OSc3znn90nO+X2/AsDF8XGp5vGT/QFJahBFEVEE0T14vtkwndfuf9Sp/Pj8tuAGF0Z"
            + "GExveXferaVoIgoAggOD28qI8Ow6iKHD1xs3Dya4P+ioQD3AilVbaNzar86aF6AEqo5xcGQG/D8uyuHzlxnfdyc5v3VwPcC"
            + "Y1rLRu2qDOmyaiIHoJmYJJtlS+EpFgZURC1wsYuo7P53P+fPRwfdeWLbeqgLaW91T3CaYNV/8tUQzIyLVBHBxkDMyiwVuxE"
            + "HUBHwIOw+qVZG/39nQVkHABls21v3UyVpBwnUy0XiImWQTMWXIFg4fPSux8cwn+mhpSl0YXAVrj6jNjjsHbOfyhMCuW1LNU"
            + "riXeKDKllZjRNCayM7yzPMrrS8OkLv6yENDeGlfHJgucvJcnFAoRDtaxIiLR0RQk7LO5l8lx7PcpNq+M0vVGI+cv/LwQsLk"
            + "trv6R0bj56D86VkcY+Qf+0mws2+bjVUG0gs7315/wdUsT29bGOJdeDEg0q5pR4sDQGH3rGjhyd47peQHHceh6VWIiq3F7qs"
            + "jgJ2/zWmMDZxcA0sOK0tKszhaLnLo+jjpR4Lenc95ngYNgWZ46e9ZEOLB1LXK9TOrST8meHdUqpBWlvU21TItsNsvZOxlOj"
            + "+WYnjWxbYew5KN7TZTP17/CspdjSAGJ80MjyZ5KGY+dSCkfdiqqKxvbtjEMg+kZjQdPdWwHVr0kE4vKyLJMwO9HFH0cP51K"
            + "7u7tLuvg0A9H3/9s16ejrgkqUva07jgIokDZYGJVpa4nDh4+suObL/cOeYB4IrF63xd7ByORSF3Vad5J2ZMLnClAbiY/u7/"
            + "vqz2Tk4/veNf6+/tr8vm8f7GDXxTrum4ODAzM/w8TKTogggbXQgAAAABJRU5ErkJggg==")
    public void drawImage() {
        drawImage("html.png",
                "canvas.width = img.width; canvas.height = img.height;\n",
                "context.drawImage(img, 0, 0, canvas.width, canvas.height);\n");
    }

    @Test
    @Alerts({"rendering...", "...done"})
    public void drawImageNoImage() {
        final String html = "<html><body>\n"
                + "<img id='myImage'>\n"
                + "<canvas id='myCanvas'></canvas>\n"
                + "<script>\n"                + "try {\n"
                + "  var img = document.getElementById('myImage');\n"
                + "  var canvas = document.getElementById('myCanvas');\n"
                + "  var context = canvas.getContext('2d');\n"
                + " alert('rendering...');\n"
                + "  context.drawImage(img, 0, 0, 10, 10);\n"
                + " alert('...done');\n"
                + "} catch (e) {alert('exception'); }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAA1JREFUGFdj+M/A8B8ABQAB/6Zcm10A"
            + "AAAASUVORK5CYII=")
    public void drawImage_1x1_32bits() {
        drawImage("1x1red_32_bit_depth.png",
                "canvas.width = img.width; canvas.height = img.height;\n",
                "context.drawImage(img, 0, 0, canvas.width, canvas.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAA1JREFUGFdj+M/A8B8ABQAB/6Zcm10A"
            + "AAAASUVORK5CYII=")
    public void drawImage_1x1_24bits() {
        drawImage("1x1red_24_bit_depth.png",
                "canvas.width = img.width; canvas.height = img.height;\n",
                "context.drawImage(img, 0, 0, canvas.width, canvas.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAAXNSR0IArs4c6QAAABdJREFUGFdj/M/A8J+RgYGRAQrgDOI"
            + "FAIPmAgWeg1g2AAAAAElFTkSuQmCC")
    public void drawImage3Arguments() {
        drawImage("1x1red_32_bit_depth.png",
                "canvas.width = 4; canvas.height = 4;\n",
                "context.drawImage(img, 0, 0);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAAXNSR0IArs4c6QAAABpJREFUGFdjZEADjFgF/jMw/GdkYABL"
            + "YqgAADfmAgXboMAzAAAAAElFTkSuQmCC")
    public void drawImage3ArgumentsPlacement() {
        drawImage("1x1red_32_bit_depth.png",
                "canvas.width = 4; canvas.height = 4;\n",
                "context.drawImage(img, 1, 2);\n");
    }


    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAAXNSR0IArs4c6QAAAC1JREFUGFc1yNENACAQwlAYxUllU0ep4aL9"
            + "ap6RMEv4yJKdhJ2oDXQABtqHt76KMBEFpp4gUgAAAABJRU5ErkJggg==")
    public void drawImage3ArgumentsPlacementNegative() {
        drawImage("4x6.png",
                "canvas.width = 4; canvas.height = 4;\n",
                "context.drawImage(img, -1, -2);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAIAAAAFCAYAAABvsz2cAAAAAXNSR0IArs4c6QAAACpJREFUGFctyLERACAMxDD/KDTMGbbk0jC"
            + "KKUClwlQ7RJANwaE5b1YV4buKVA5Xbm45TAAAAABJRU5ErkJggg==")
    public void drawImage3ArgumentsImageTooLarge() {
        drawImage("4x6.png",
                "canvas.width = 2; canvas.height = 5;\n",
                "context.drawImage(img, 0, 0);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAAXNSR0IArs4c6QAAABdJREFUGFdj/M/A8J+RgYGRAQrgDOI"
            + "FAIPmAgWeg1g2AAAAAElFTkSuQmCC")
    public void drawImage5Arguments() {
        drawImage("1x1red_32_bit_depth.png",
                "canvas.width = 4; canvas.height = 4;\n",
                "context.drawImage(img, 0, 0, img.width, img.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAAXNSR0IArs4c6QAAABpJREFUGFdjZEADjFgF/jMw/GdkYABL"
            + "YqgAADfmAgXboMAzAAAAAElFTkSuQmCC")
    public void drawImage5ArgumentsPlacement() {
        drawImage("1x1red_32_bit_depth.png",
                "canvas.width = 4; canvas.height = 4;\n",
                "context.drawImage(img, 1, 2, img.width, img.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAAXNSR0IArs4c6QAAACVJREFUGFdj/H+D4T+jBgMjAxQw/mdg+M/I"
            + "gFfg////jIyMcC0AnzULBWgjtygAAAAASUVORK5CYII=")
    public void drawImage5ArgumentsPlacementNegative() {
        drawImage("4x6.png",
                "canvas.width = 4; canvas.height = 4;\n",
                "context.drawImage(img, -3, -1, img.width, img.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAYAAACp8Z5+AAAAAXNSR0IArs4c6QAAABZJREFUGFdj/M/A8J8BCTAic0BswgIAg"
            + "2sCAzQ3b+cAAAAASUVORK5CYII=")
    public void drawImageStretch() {
        drawImage("1x1red_32_bit_depth.png",
                "canvas.width = 4; canvas.height = 4;\n",
                "context.drawImage(img, 0, 0, img.width, img.height, 0, 0, canvas.width, img.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAIAAAAFCAYAAABvsz2cAAAAAXNSR0IArs4c6QAAACpJREFUGFctyLERACAMxDD/KDTMGbbk0jC"
            + "KKUClwlQ7RJANwaE5b1YV4buKVA5Xbm45TAAAAABJRU5ErkJggg==")
    public void drawImage5ArgumentsImageTooLarge() {
        drawImage("4x6.png",
                "canvas.width = 2; canvas.height = 5;\n",
                "context.drawImage(img, 0, 0, 4, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAJFJREFUKFONkMENAjEMBNclIBoIH0QF"
            + "9z0XRA+Re6CgpZM0gO538DM4CaeDV1aRrI0mazuCQUnlzu66Evlh1dox1xpen4QAUkGPMwG49vhbr+EnQC4dhJ9c+UK2pSXm"
            + "Q0u0BXH/k1hSwl21AjOJVMo2/Qbu93H3oGPI9urTQUL/S39BkmpmINnYwd+JOcc0DL4B84cwC7JlTrYAAAAASUVORK5CYII=")
    public void drawImage5ArgumentsStretchX() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, 1, 1, 8, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAFFJREFUKFNjZCASMILUNQg3/K+PbGBoXN7"
            + "AAKIZpzCAxZEBRGGDwH/7AwYMBx0uMNQ3fACpwq4QpvP/////GxsbGRoaGvArxOdcDJ24FA8FhQDPFBULZWbihAAAAABJRU5Erk"
            + "Jggg==")
    public void drawImage5ArgumentsShrinkY() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, 1, 1, 4, 3);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAIAAAAFCAYAAABvsz2cAAAAAXNSR0IArs4c6QAAADhJREFUGFcBLQDS/wEAJv//QPbAAAFAHL/"
            + "/MAfQAAG/CUD/ECfwAAG/QAj/EBj+AAFAvxn/MN76AI+DEbp3TIWcAAAAAElFTkSuQmCC")
    public void drawImage5ArgumentsStretchImageTooLarge() {
        drawImage("4x6.png",
                "canvas.width = 2; canvas.height = 5;\n",
                "context.drawImage(img, 0, 0, 8, 12);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAFpJREFUKFO10EEKgDAMRNE/R+mm9JbGW4ob"
            + "jzJiUFetdGM2A+GRwIjJ0aTjD1ht70LVZNL/IoPZgAZXqg0gLrYO5ELm18U1giUiCxjCpx7bTih1m3iXF7xNF57OsB8LLeYw9AAA"
            + "AABJRU5ErkJggg==")
    public void drawImage5ArgumentsNegativeWidth() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, 4, 4, -4, 6);\n");
    }


    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAFlJREFUKFPFzkEKgDAMRNE/R+mm9JbGW4ob"
            + "jzJiUChixZ0hZDaPJOJe1fYqVE0mR5+jtwazAA2OVBtAXGxtyIXMt41zBFNEHhrC6w3bTig9/9jD3qT+Uj/CHbu+Hws0CMhTAAAA"
            + "AElFTkSuQmCC")
    public void drawImage5ArgumentsNegativeHeight() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, 4, 6, 4, -6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAFxJREFUKFOl0EEKgDAMRNE/R+mm9JbGW4ob"
            + "jzJiRSjUSsFsZvMIk4jJ0aSjh9n2LpRNTW7TQYPZgAJXqgwgTrYO5ETNr41rBEtErT+Ez3G2XaH03rGFrfnxnsFjT80oHwvhRbeC"
            + "AAAAAElFTkSuQmCC")
    public void drawImage9Arguments() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, 0, 0, img.width, img.height, 4, 2, img.width, img.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAIpJREFUKFNjPBXA/p+BgYFhWgQfiGJY"
            + "EPGaEcxAA4zrA0zACh8ovGAQ+PCBIXHBF+wKFyQkXGHl4OC2v3ZNVPrtW27Gq1dxKIyP1zaNiDBVkZDIYmNlNWXU0cGuEGTt"
            + "////HYBU/YMHDxwUFRUJKzxw4ICDo6MjboXYfInha2IUgdRgtQZrOBJrIgAUwCcL+6R7MwAAAABJRU5ErkJggg==")
    public void drawImage9ArgumentsCrop() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, 1, 2, 2, 4, 0, 0, img.width, img.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAFxJREFUKFNjZCASMBKpjmHoKlT7/z/h0wK"
            + "G+reNDIq/H4D9gdUz2WJT/ptLnWTwUtrGILLuLW6F+x04/kuo/mVQMPzDwJn1H7dC0Vdy/0PW/GQomPCNQf3WZ7BCAChZHAtgVD"
            + "PiAAAAAElFTkSuQmCC")
    public void drawImage9ArgumentsCropNegativStart() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, -1, -2, 3, 5, 4, 4, img.width, img.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAABdJREFUKFNjZCASMBKpjmFUId6QIjp4"
            + "AAppAAuXjCs4AAAAAElFTkSuQmCC")
    public void drawImage9ArgumentsCropNegativWidth() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, 0, 0, -3, 5, 4, 4, img.width, img.height);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAEhJREFUKFNjnK+Z8J+BgYEh8foCRhCNCzDO"
            + "r+aBKGz9gl/hAQeH/fYMDA6MBw7gVwgybf78+f8TExMJK8TnNpgcXlOQDRgKCgFraBALDri0wwAAAABJRU5ErkJggg==")
    public void drawImage9ArgumentsStretch() {
        drawImage("4x6.png",
                "canvas.width = 10; canvas.height = 10;\n",
                "context.drawImage(img, 0, 0, img.width, img.height, 0, 0, 2, 4);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAAXNSR0IArs4c6QAAAHBJREFUKFOd0LENglAUBdDzSmLNCO5g"
            + "4QTG1jWo7JjCFehchgSdQxcg5BsSKPQHQ3j1efcmN6y8WOlkMLGfnovgMQd9wcQJFUo0wW0JvrDDgGdwyGDiiPuU1qPDOXiP"
            + "+Le6xgUtrjPK4L8Fts+zlPoB1NMSC7MwsngAAAAASUVORK5CYII=")
    public void drawImageDataUrlPng() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function test() {\n"
                + "    var img = document.getElementById('myImage');\n"
                + "    var canvas = document.createElement('canvas');\n"
                + "    canvas.width = img.width;\n"
                + "    canvas.height = img.height;\n"
                + "    if (canvas.getContext) {\n"
                + "      var context = canvas.getContext('2d');\n"
                + "      context.drawImage(img, 0, 0, canvas.width, canvas.height);\n"
                + "     alert(canvas.toDataURL());\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage' src='data:image/png;base64,"
                + "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAABGdBTUEAALGP"
                + "C/xhBQAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB9YGARc5KB0XV+IA"
                + "AAAddEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIFRoZSBHSU1Q72QlbgAAAF1J"
                + "REFUGNO9zL0NglAAxPEfdLTs4BZM4DIO4C7OwQg2JoQ9LE1exdlYvBBeZ7jq"
                + "ch9//q1uH4TLzw4d6+ErXMMcXuHWxId3KOETnnXXV6MJpcq2MLaI97CER3N0"
                + "vr4MkhoXe0rZigAAAABJRU5ErkJggg==' alt='red dot' />\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAL1JREFUOE9jZEAFjgwMDOZQoZMMDAz7"
            + "0eQJchmhKnhFGBi2CDAw6AcwMHCDxDYwMHz9wMBw8Q0Dgw8DA8NngiZBFYANFGFgOJjAwGDVzcDAgqyxlIHhzwIGhmNvGBjs"
            + "STHQUYWBYf1tBgZ+bJpUGRg+3mFgCCTW+yAXVpQwMDSjuw5mOMiVPQwMtQwMDB3EuJImBlLdy1SPFFDQUDfZIAU21RI2MRFI"
            + "lBpYTiFKMTGKRg0kJpTwqxkNQ8rDEABatjIVyjXhJwAAAABJRU5ErkJggg==")
    public void drawImageDataUrlSvg() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function test() {\n"
                + "    var img = document.getElementById('myImage');\n"
                + "    var canvas = document.createElement('canvas');\n"
                + "    canvas.width = 20;\n"
                + "    canvas.height = 20;\n"
                + "    if (canvas.getContext) {\n"
                + "      var context = canvas.getContext('2d');\n"
                + "      context.drawImage(img, 0, 0, img.width, img.height);\n"
                + "     alert(canvas.toDataURL());\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage' src='data:image/svg+xml,"
                + "<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" "
                + "overflow=\"hidden\" width=\"10\" height=\"10\">"
                + "  <circle cx=\"5\" cy=\"5\" r=\"4\" stroke=\"black\" stroke-width=\"1\" fill=\"red\" />"
                + "</svg>' />\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "0", "true", "true"})
    public void measureText() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var canvas = document.getElementById('myCanvas');\n"
                        + "        if (canvas.getContext){\n"
                        + "          ctx = canvas.getContext('2d');\n"
                        + "          try {\n"
                        + "           alert(ctx.measureText());\n"
                        + "          } catch(e) {alert('exception'); }\n"

                        + "          var metrics = ctx.measureText('');\n"
                        + "         alert(metrics.width);\n"

                        + "          metrics = ctx.measureText('a');\n"
                        + "         alert(metrics.width > 5);\n"

                        + "          metrics = ctx.measureText('abc');\n"
                        + "         alert(metrics.width > 10);\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <canvas id='myCanvas'></canvas>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    private void draw(final String canvasSetup, final String drawJS) {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function test() {\n"
                + "    var canvas = document.getElementById('myCanvas');\n"
                + "    if (canvas.getContext) {\n"
                + "      var context = canvas.getContext('2d');\n"
                + drawJS
                + "     alert(canvas.toDataURL());\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + canvasSetup
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAACoAAAAqCAYAAADFw8lbAAAAAXNSR0IArs4c6QAAAVpJREFUWEft1L1KHVEUxfHffQTJC4iN"
            + "dgFbrQSNBNLEwuKa2qCVYCepQjpBSBGwlVhoYSlYWNqm1s4XSG2rW+bCOOj9gK02e6qZc2avs/b/rHN6nj6z+IyD1nC/eT/G"
            + "Mv7hf6eu+/kBW9jHd5zjpvkp5n5h7wWddm34uYva3gRGz7CLP69stG1pZ9DkJEZD4C8OcYpVrDSq2/iBT1jE9ZhEQ+ML1hud"
            + "du0VLnGB/nNGT/CxQ3oDbaJz+IboeL4xGRGJ8aWG+jhbH0Zn8BMLw2onIdo1Oo3I7WCBWGyQ8aMJiN4+ZD/oDa19C6NfW9Ri"
            + "o9qNxS68itG4DYLkb0yNSTTMRd1mE6fHzDUHcpTROLxrkeEu0RG3zvtNl9Fs9kW0iGYTyNarjBbRbALZepXRIppNIFuvMlpE"
            + "swlk61VGi2g2gWy9ymgRzSaQrVcZLaLZBLL1KqNFNJtAtt49X4NjZWyu/AkAAAAASUVORK5CYII=")
    public void fillText() {
        draw("<canvas id='myCanvas' width='42' height='42'>\n",
                "context.fillText('HtmlUnit', 3, 7);\n");
    }

    @Test
    @Alerts("[object CanvasGradient]")
    public void createLinearGradient() {
        final String html =
                "<html><head><script>\n"
                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  var ctx = canvas.getContext('2d');\n"
                        + "  var gradient = ctx.createLinearGradient(0, 0, 200, 0);\n"
                        + " alert(gradient);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <canvas id='myCanvas'></canvas>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object CanvasGradient]")
    public void createRadialGradient() {
        final String html =
                "<html><head><script>\n"
                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  var ctx = canvas.getContext('2d');\n"
                        + "  var gradient = ctx.createRadialGradient(100, 100, 100, 100, 100, 0);\n"
                        + " alert(gradient);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <canvas id='myCanvas'></canvas>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "0.5", "0", "0.699999988079071", "0"})
    public void globalAlpha() {
        final String html =
                "<html><head><script>\n"
                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  try {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "    ctx.globalAlpha = 0.5;\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "    ctx.globalAlpha = 0;\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "    ctx.globalAlpha = 0.7;\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "    ctx.globalAlpha = null;\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <canvas id='myCanvas'></canvas>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0.5", "0.5", "0.5", "0.5"})
    public void globalAlphaInvalid() {
        final String html =
                "<html><head><script>\n"
                        + "function test() {\n"
                        + "  var canvas = document.getElementById('myCanvas');\n"
                        + "  try {\n"
                        + "    var ctx = canvas.getContext('2d');\n"
                        + "    ctx.globalAlpha = 0.5;\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "    ctx.globalAlpha = -1;\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "    ctx.globalAlpha = 'test';\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "    ctx.globalAlpha = undefined;\n"
                        + "   alert(ctx.globalAlpha);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <canvas id='myCanvas'></canvas>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAKCAYAAAC0VX7mAAAAAXNSR0IArs4c6QAAADxJREFUOE9jZKAyYISa10AlcxtoZmA9"
            + "AwMDyHByXQrTB3ch1Q2E20BmWGK4kGYGkulAuDbaxTKlLoPrBwDnlwwLU3fVIAAAAABJRU5ErkJggg==")
    public void strokeRect() {
        draw("<canvas id='myCanvas' width='20', height='10' style='border: 1px solid red;'></canvas>\n",
                "context.strokeRect(2, 2, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAKCAYAAAC0VX7mAAAAAXNSR0IArs4c6QAAAChJREFUOE9jZKAyYKSyeQzIBv6n0HCw"
            + "WaMGUhSKtA9DipwH00z1dAgAPKcGC8XskzoAAAAASUVORK5CYII=")
    public void fillRect() {
        draw("<canvas id='myCanvas' width='20', height='10' style='border: 1px solid red;'></canvas>\n",
                "context.fillRect(2, 2, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAACxJREFUOE9jZICA/1CaUoqRcdTA0TAk"
            + "OQRGkw3JQYahYTQMR8OQjBCgfrIBABkEFBUHz6OrAAAAAElFTkSuQmCC")
    public void fillRectWidthHeight() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.fillRect(1, 0, 18, 20);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAADdJREFUOE9jZKAyYKSyeQzIBv6n0HCw"
            + "WaMGUhSKtA9DipwH00zTdDjqQvJCYDRSyAs3ZF2DPwwBbfkGFYXESWwAAAAASUVORK5CYII=")
    public void fillRectRotate() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.fillRect(2, 2, 16, 6); context.rotate(.5);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAANRJREFUOE/d1CEPgUEYwPH/q/kCvoRA"
            + "kRQbCkUQTJFtNp2MpmmCTZIQRJIkapIoaT4A4bnt3c3dvffuCrfddtvz3O+e5267iMAjCuzxm2ATOKS9Cr3lEnAB9sBU1l62"
            + "Dm6BVkxYCnxPqsbBhqXVmcAvFxwHz0DZsuEp6NyGKrAHrFynS/wGTID1t3wFZoCRzGxC+ARU9Vz9UXKCDhOgNeDoAlU8L3DX"
            + "AG+Ajq1lU0EVgetaQgG4pgHVnjYwBorAAhiYKvD9HPofdAc8QoHOt/Kt8A/AN1+PHBVxaGY3AAAAAElFTkSuQmCC")
    public void rotateFillRect() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.rotate(.5); context.fillRect(6, 2, 12, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAADdJREFUOE9jZKAyYKSyeQzIBv6n0HCw"
            + "WaMGUhSKtA9DipwH00zTdDjqQvJCYDRSyAs3ZF2DPwwBbfkGFYXESWwAAAAASUVORK5CYII=")
    public void fillRectTranslate() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.fillRect(2, 2, 16, 6); context.translate(3, 4);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAADFJREFUOE9jZKAyYKSyeQyjBlIeorjC"
            + "8D+5Ro8aCA+5wR+G5EbyaNYjO+QQGkdg8QUAebwGD4ULeHoAAAAASUVORK5CYII=")
    public void translateFillRect() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.translate(3, 4); context.fillRect(2, 2, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAMxJREFUOE/t1LEJAjEUxvH/gYKVjStY"
            + "WYlOYOMGCnYOYeEKYu8INo4gOICgnaWCYCEIghvIgzx4hODlTBrBQLjj8vJ7+VJcQeZRZPb4g+k3+lt3OAIOwCUluI18AjrA"
            + "w8FH95Qm19gmCjaB14dNd4Nro1uoXsEBsIs9hasT0KbYSzoFZ8CyImjLz0BbPii4BiYJ4AYYW3AF9IA+UPsCngMLC6ohJxZU"
            + "cXmXWTaGwDYEhjbWvQbSrOsVtoBnLBhq0vCSTG3EsjiV1rP/HN50hx8VahodiQAAAABJRU5ErkJggg==")
    public void rotateTranslateFillRect() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.rotate(0.2); context.translate(0, 4); context.fillRect(4, 4, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAALZJREFUOE/tkjEKAjEQRd8ewXoLK23s"
            + "1kZ7j+IRbG3st/M6tlYq2CxYCl7CTgZ2liEMMm5WsDCQIiR5b+YnBQOPYmAef2B+ol/LcAzc8+uje+U1sAfOwMXM66cSbbkE"
            + "Hs7lZwu3orcSm+ERWAYqUol0oqJOYoFbYBcAekdqYCMbFjgHTj2BDTBLgbK+AdOeUAE23j8cAVUyIxJpuY5+bJVILCqbJJ0c"
            + "gFUU6KXgSRY5QDfq3we+AOdFHSeUoHcMAAAAAElFTkSuQmCC")
    public void transformTranslateFillRect() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.setTransform(1, .2, .3, 1, 0, 0); context.translate(-5, 4); context.fillRect(4, 4, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAKCAYAAAC0VX7mAAAAAXNSR0IArs4c6QAAAC1JREFUOE9jZKAyYKSyeQxwA88wMPyn"
            + "xHATBohZowaSH4q0D0Py3Yaqk+rpEAAqPQwLveqeQwAAAABJRU5ErkJggg==")
    public void fillStyleNullFillRect() {
        draw("<canvas id='myCanvas' width='20', height='10' style='border: 1px solid red;'></canvas>\n",
                "context.fillStyle = '#cc0000'; context.fillStyle = null; context.fillRect(2, 2, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAKCAYAAAC0VX7mAAAAAXNSR0IArs4c6QAAAC1JREFUOE9jZKAyYKSyeQxwA88wMPyn"
            + "xHATBohZowaSH4q0D0Py3Yaqk+rpEAAqPQwLveqeQwAAAABJRU5ErkJggg==")
    public void fillStyleUndefinedFillRect() {
        draw("<canvas id='myCanvas' width='20', height='10' style='border: 1px solid red;'></canvas>\n",
                "context.fillStyle = '#cc0000'; context.fillStyle = undefined; context.fillRect(2, 2, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAKCAYAAAC0VX7mAAAAAXNSR0IArs4c6QAAAC1JREFUOE9jZKAyYKSyeQxwA88wMPyn"
            + "xHATBohZowaSH4q0D0Py3Yaqk+rpEAAqPQwLveqeQwAAAABJRU5ErkJggg==")
    public void fillStyleUnknownFillRect() {
        draw("<canvas id='myCanvas' width='20', height='10' style='border: 1px solid red;'></canvas>\n",
                "context.fillStyle = '#cc0000'; context.fillStyle = 'pipi'; context.fillRect(2, 2, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAKCAYAAAC0VX7mAAAAAXNSR0IArs4c6QAAAC1JREFUOE9jZKAyYKSyeQzIBv6n0HCw"
            + "WXQxEFcwEPIBTheOIAMpjGSIdqqnQwA/UgoLxnfNlgAAAABJRU5ErkJggg==")
    public void clearRect() {
        draw("<canvas id='myCanvas' width='20', height='10' style='border: 1px solid red;'></canvas>\n",
                "context.fillRect(2, 2, 16, 6); context.clearRect(4, 4, 6, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAF1JREFUOE9j/O/C8J+BioBx1ECKQ3Mk"
            + "hyHjHgZGWABSkpTgYYhsILaYIdYSog0k1hKUWCbkSnxpCuaDIWYgupdICQKsXiYm3+GzBGQoVbIezBKqGYjsM6q4kKYGAgB9"
            + "kkg955Wz+gAAAABJRU5ErkJggg==")
    public void transformTranslateClearRect() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.fillStyle = '#ff4400'; context.fillRect(0, 0, 20, 20); "
                        + "context.setTransform(1, .2, .3, 1, 0, 0); "
                        + "context.translate(-5, 4); context.clearRect(4, 4, 16, 6);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAKCAYAAAC0VX7mAAAAAXNSR0IArs4c6QAAAHVJREFUOE9jZKAyYEQyL5GBgWEXAwPD"
            + "U0rsgBmozMDA0MLAwODGwMBwF2owyPBDpBqO7EKYXjuowSDDQRaBDIZhgq7HZiCyo6SRDHdnYGC4Q8j1hAxE9zHM9SDDlaCG"
            + "10CDCayWVAOxuX4+siAlBmKNr8FvIAB+fhILDX78/QAAAABJRU5ErkJggg==")
    public void moveToLineToStroke() {
        draw("<canvas id='myCanvas' width='20', height='10' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(2, 2); context.lineTo(16, 6); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAOlJREFUOE+t0z1KRDEQAOBvS+32AHoI"
            + "EbzAoh7ARhAR3N6txUL8W7CzshEbwcbOxhU9idVaWNsoFoL6Igm8Qpbn20kzIWG+TEKmI3h0gj0F3M9wia3PKeAlvrHdWsqJ"
            + "dfAL/SjwAJ84jAKP8IHjKDBB7ziJAq8wg7UocAG3WMcrXnL8t1//2DtYRhdzeMMI9zk2wid1Sqp6FStYwg2uq/ndJLlp681i"
            + "CwM8Vbc4w8NfcFOwnpu6KX2z9ObD6qBxfbMNWPJPsYk9XJTFacBkLOI8P8MunqcFS2GpITYwHwUmuIfHSPC32nDwBzgGJBXR"
            + "dXO7AAAAAElFTkSuQmCC")
    public void moveToBezierCurveToStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(2, 2); context.bezierCurveTo(2, 17, 1, 4, 19, 17); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAOhJREFUOE/N1LErxVEUB/DP+xNke4OS"
            + "LAZlNkmxSIoy2ZRks5n0bDIoI6NNBmWQUW+RgZXF8AazySi/I7+6vXp63juDW3e59f3cM5xzGpJPo/B28YrnnzvQVyV4jElM"
            + "YQT3uMMtnvrVS7DMjGIW81jCO85xio/f8F5gd2YOm1jEIY56of2CdX4a+1WlTezgsRv+K1jnt3CCdVyV6KBgGAu4xgpuanQY"
            + "MIxlnGEGb/EwLBjGAcaxkQWG08EaHjIqDHAPY9jOAieqIWhHO2WBUeULVjPBy2oPXGSCsVw6mWALn5ng97D8f/ALi58fh0da"
            + "GhQAAAAASUVORK5CYII=")
    public void moveToQuadraticCurveToStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(2, 2); context.quadraticCurveTo(19, 4, 19, 17); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAADRJREFUOE9jZKAyYKSyeQyjBlIeokMr"
            + "DP9T6GGwb5G9PAINpDAIIdqHVrIZ9TJ5IUD1WAYAYokEFfnLcNAAAAAASUVORK5CYII=")
    public void lineWidthMoveToLineToStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.lineWidth = 4; context.moveTo(2, 10); context.lineTo(18, 10); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAL9JREFUOE/tkr0NwkAMhb+0SNRQsAM/"
            + "PWILaGGMlClAbAEFDYzBAEAmgBK2QJZO4ohs4qCjw80V9/T5PdsZiStLzOPnwBYwAU7A4xv3msML0AeuAXwOr6uJBlwBueHu"
            + "FsGXmkYDjoGjI+4AKKs6ayl3oFMDlRRrL3ADzGuAkkIW+FaWwymwd8TuVq/BAraBHTACeh/AC2Ab/3sOW4DDAJcGcZMDMGsK"
            + "1AzGTYoUQHMKnsiO3bwkf2Cjcani5DN8AnFEGRVy9qdcAAAAAElFTkSuQmCC")
    public void setTransformFillRect() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.setTransform(1, .2, .3, 1, 0, 0); context.fillRect(3, 3, 10, 7);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAL9JREFUOE/tkr0NwkAMhb+0SNRQsAM/"
            + "PWILaGGMlClAbAEFDYzBAEAmgBK2QJZO4ohs4qCjw80V9/T5PdsZiStLzOPnwBYwAU7A4xv3msML0AeuAXwOr6uJBlwBueHu"
            + "FsGXmkYDjoGjI+4AKKs6ayl3oFMDlRRrL3ADzGuAkkIW+FaWwymwd8TuVq/BAraBHTACeh/AC2Ab/3sOW4DDAJcGcZMDMGsK"
            + "1AzGTYoUQHMKnsiO3bwkf2Cjcani5DN8AnFEGRVy9qdcAAAAAElFTkSuQmCC")
    public void transformFillRect() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.transform(1, .2, .3, 1, 0, 0); context.fillRect(3, 3, 10, 7);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAEFJREFUOE9jZKAyYKSyeQyjBlIeoiM4"
            + "DBUYGBiSSAjBOlxqkcOwiQQD5zEwMDzApn4ERwoJwYdf6WgYUh6UVA9DAJn9AxWRWUx3AAAAAElFTkSuQmCC")
    public void moveToLineToTransformStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(2, 10); context.lineTo(13, 10);"
                        + "context.transform(1, .2, .3, 1, 0, 0); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAGZJREFUOE9jZKAyYKSyeQyjBlIeoqNh"
            + "CA5DFgYGhj/khiauMHzAwMBwm4GB4QISvkqMJfgiRZuBgcEADSNbAGJjWEJqLKNbosrAwKCA7HJSDcTma5Qwp4aBKJaMGkhM"
            + "SsOvhuphCABLbg0VZ88YdgAAAABJRU5ErkJggg==")
    public void transformMoveToLineToStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.transform(1, .2, .3, 1, 0, 0); context.moveTo(2, 10);"
                        + "context.lineTo(13, 10); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAADNJREFUOE9jZKAyYKSyeQyjBlIeoiM8"
            + "DBsoDEGwfuQwpLqBFDoQon2Ex/JoGJIXAlRPNgCWIAIVwMrcFwAAAABJRU5ErkJggg==")
    public void moveToLineToRotateStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(2, 10); context.lineTo(18, 10); context.rotate(90); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAALBJREFUOE/t0rEJAkEUhOH/cjGwAhEs"
            + "wSYMLMEmNBCNNbcJ0RosQrAEMRYzOTCQB/Nkg3PZXQ0M7sU7H8OwFdADbvzoKmAP3IENcPnWNbADLIGF0DXwKIUN9OsDK2Ai"
            + "eFuChqDnR2o8FLzLgZtAz48FPwGb4ZgCx0DPTzXFWY1PMTgF9PxMjQ+Cr01wDmj5rtB58CPqEM4FPTsQbLta4/eVgh9nbMGU"
            + "rxt/0274hxu+AE0JGRUWB25NAAAAAElFTkSuQmCC")
    public void rotateMoveToLineToStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.rotate(.5); context.moveTo(1, 1); context.lineTo(18, 1); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAADdJREFUOE9jZKAyYKSyeQzIBv6n0HCw"
            + "WaMGUhSKtA9DipwH00zTdDjqQvJCYDRSyAs3ZF2DPwwBbfkGFYXESWwAAAAASUVORK5CYII=")
    public void rectFill() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.rect(2, 2, 16, 6); context.fill();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAPxJREFUOE/F08EqRVEUAND1PkL5AEli"
            + "qgxRb2Cg5AswMREDmZqLwctE6T2lZCDlB0wYGBmIJF9ARr6Au3WVbq577uvGGZ7a6+x99t4tDZ9Ww54/Adf4fGgRT7jAGV5T"
            + "qilmGNg0dnJsEksYze/2q9AiuI4bXBUCx9HFIzbxXAYXwVu08VISsIcxTKWCpzjGeVVpqeAKZjHXFDiAa8zjrh/0pzmMLKOz"
            + "E02B4RzhDat10bJNGcRJNpOX2KqD/rZ6QzjAfZ1Mq3Y5Mt3GCJZTGlUFflUbjdrAA3r5JAznjdv9/iWpYMTESC1kazmDwA6z"
            + "/31Hp18wqTd1Mvwf8AMXuSUVr2KpOAAAAABJRU5ErkJggg==")
    public void ellipseStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "if (!context.ellipse) {alert('context.ellipse not supported'); return; }\n"
                        + "context.ellipse(10, 10, 8, 4, Math.PI / 4, 0, 1.5 * Math.PI); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAOlJREFUOE/NlL0OAVEQRs+UCrVai8Qb"
            + "aK0HUCslOk+iU6hovQC1JyBCq9cqlMPdrGTt/dtNNmLqmXPnm/nmCjWH1MzjN0BVEqAJXEW4VFFhdajKHhjmICdgIcKmDPgL"
            + "mHW28xSah2Yi3ELgInAMbAMFTyAR4eDLKQJ7wDki7S5CqxTQJKlyBPoR6EgknbUVrqVMgHUEuBJhWgqYdWkWY6zjC69sp7FV"
            + "aUPqv0YA6pTtvRRVBpDOyQd1yg6eXgTqlB295Uz+0jNTS3YU+JmhKmb784KlLNmlgTlw931NHeDh8mJlYOyD+H/gC77COxVH"
            + "Ilo+AAAAAElFTkSuQmCC")
    public void ellipseFill() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "if (!context.ellipse) {alert('context.ellipse not supported'); return; }\n"
                        + "context.fillStyle = 'yellow';"
                        + "context.ellipse(10, 10, 8, 4, Math.PI / 4, 0, 1.5 * Math.PI); context.fill();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAMZJREFUOE/t1CFuAkEYxfHfolAIAjcg"
            + "wXEGHAh0UdXQNEgcqqoXaBoSHKo9QWXPUFfFASCIKkQFu8mSkGV3s5uMZJIRk3nf/5vv5WUigVcUmOcOzHW0jWNVr8s87GGJ"
            + "HV5DADf4x7wqLNEVvXCEBSZ1YGXAFRrxfgkFfMYAswLgGj94y94XjTzFE4YFwG+846MqMNF94Q8PmaJPtDDOa1YWmy62cWSa"
            + "+E2L+zjhEfu6wIs+Gb+THg55Y16D759D3Rjf6oN7eAYSbhYVZnnVXgAAAABJRU5ErkJggg==")
    public void arcStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.arc(10, 10, 4, 0, 4.3); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAALNJREFUOE/l0jEOAVEUheFPIVGpLECn"
            + "U1mEVkNlLUarF7EEGluwAIlEJxo9tUgUeC+qiUnMZBTiJq95uefPvfecCpLnK1J3VNLC8BGARaFpXvKnwNHzuOMirrzR/OsN"
            + "fyeHAzRezp2xKOB8dHmCNmrYvyAtXDHEKQc4Ag/Yop8SLlFHNy/wiGaGaI1ZjvXjhBt0MoBz7DD9cMrvAEtfuXRTQmx6qCLk"
            + "L1TI4w0rXD68X2x7AH8dMhXIajp1AAAAAElFTkSuQmCC")
    public void arcCircleStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.arc(4, 16, 4, 0, 2 * Math.PI); context.stroke(); context.strokeRect(0, 0, 20, 20);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAHlJREFUOE/t1LENwjAQBdDnkiZtGno6"
            + "qgzBEmnZCCFGgCmYIWuEAVKGtJGDLMcFha/30/c/6YLCEwp7KhhttMMZB3zw/NV7SodXtDjihAk9xhicAq7fvdDgUgpcnPc3"
            + "9T32/ZyEC/jAgNs6ZS64uZcK7j8VtcM/7HAGnYcKFQzb6fQAAAAASUVORK5CYII=")
    public void arcAnticlockwiseStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.arc(10, 10, 4, 0, 4.3, true); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAJpJREFUOE/t1KENAkEQRuHvHAoFNeCo"
            + "AUkFoNCEXD2EoFFQAZIacNQACoUkmxyCy+4mXDaoWzuZl33/TKZS+FWFeXpgNNElRk3lgWMu91yGYxwwwK2BTPDCCvcYOAc8"
            + "44lFq/GEIea/AIPmBrOE3gW7mH7qhzWmWCeAe1yxbdf/BiyuHEyKDiUAi6/NJ+9ii93pEPXXplNsX01vh5geFQJIQ80AAAAA"
            + "SUVORK5CYII=")
    public void arcCircleAnticlockwiseStroke() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.arc(10, 10, 4, 0, 2 * Math.PI, true); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAHpJREFUOE/tlMENgCAMRR8upFN50CW6"
            + "hB6cSlwIQ/SgScsBG0/0COmDPn4IOFdw5tGA342WHQoTMAI9kIAD2BBW62gbeMEWo3G2oCXgft9MY0aEQdsoAfOIViWEzhMI"
            + "omf415GdHyULescmr8T62FRmvH0OleIebe4OT5UrGhUWjbLDAAAAAElFTkSuQmCC")
    public void arcFillPath() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.fillStyle = 'green'; context.beginPath();"
                        + "context.arc(10, 10, 4, 0, 2 * Math.PI); context.fill();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAQlJREFUOE/N1EtKA0EUheGvxYUoug4F"
            + "0W0IKuguzEXcg61oEEfOXICCj4kLcKRCsgdHghIf3Qlp207SCUG8oyo49XPq1j2VmHIlU+YZDNyzrGMdS5jDLNq4+dqfCndV"
            + "ZqqB4RhbI9wfCTtlzW9guMRqzVZcCWtF7U9gOMR2TVhP1hT92/SBkfXqdkxYLk+saLjOl70KTWxMBMwfKTtbBLYwPyGwJSyU"
            + "gR8TwvJjkZsrOnzrztq43HMzUrt5/4vAZyzWpL0i9S6177F6bMIJNocCE20dqUQqvAxPSh6171hV1X3mKJyNukF5sMuRu5A4"
            + "0MjSU6sGRe+p2+iHWpSC6A+/r3GtdfX/3+EnUB03FTaS9zwAAAAASUVORK5CYII=")
    public void arcFillPathAngle() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.fillStyle = 'green'; context.beginPath();"
                        + "context.arc(10, 10, 8, 2.3, 2 * Math.PI); context.fill();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAALhJREFUOE/Fk7ENAjEMRd/NQsEOLMAC"
            + "tFQ3AQwCE1DRsgALsMMV7AKW7lAUYn8XEaSKlO/n7+RnoPMaOvP4CfAKPJPOV2/tvtTWDnfAGbgkgSNwAG6LvjXyAziVIgdu"
            + "zY/AJnJoZ01hA9ps7D2Kcuk29YDKpdswio1XFDaLgF5heB0q2HWxugr5U2qAeiwJtLQsENt/5a6Okxq5zKXtZeAzQANNs5O1"
            + "+pJZ4HYG3XsBFedznnX4P+ALwKAjFWklBKcAAAAASUVORK5CYII=")
    public void closePath() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(4,4); context.lineTo(10,16);"
                        + "context.lineTo(16,4); context.closePath(); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAChJREFUOE9jZKAyYKSyeQyjBlIeoqNh"
            + "OBqGZITAaLIhI9DQtIzAMAQASMYAFTvklLAAAAAASUVORK5CYII=")
    public void closePathNoSubpath() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.closePath();context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAChJREFUOE9jZKAyYKSyeQyjBlIeoqNh"
            + "OBqGZITAaLIhI9DQtIzAMAQASMYAFTvklLAAAAAASUVORK5CYII=")
    public void closePathPointOnly() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(4,4); context.closePath(); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAChJREFUOE9jZKAyYKSyeQyjBlIeoqNh"
            + "OBqGZITAaLIhI9DQtIzAMAQASMYAFTvklLAAAAAASUVORK5CYII=")
    public void closePathTwice() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.closePath(); context.closePath(); context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAK9JREFUOE/tk7ERgkAQRR+1ENiDDdgA"
            + "qREVYCFSgZGpDdiAPRDYCy5z66zLyeGsIRcx/89/t3yWij+fyvAOwN3xrXYFns6vX9rRagpsgBOwd4EHcE5aD1yc3wIdcFPd"
            + "TqhhNf0lJX9iWuCvAH/BDCiCfcVSBTn/Y0IB6pTyLN29u0kdlfwZUHJDCu++bNSibzvUvKyKHL9Cq/wcMLTqGzBUX3axw8Tt"
            + "o4QrzP7LIeoI3pwhFeJpA+wAAAAASUVORK5CYII=")
    public void closePathClosesOnlyLastSubpath() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(2,2); context.lineTo(5,8); context.lineTo(8,2);"
                        + "context.moveTo(10,2); context.lineTo(13,8); context.lineTo(16,2); context.closePath();"
                        + "context.stroke();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAGZJREFUOE9jZNjH/J8BGTj9ZUThE+Kg"
            + "6WekuoH7GFBd6MRAmgvR9TNS3UB4EFEallCDEBFAbQMp9TrMp3AXUt1AQsmNWHnSEjERpo4aSEQgEVAyEsIQozykNNiobSBG"
            + "eUixA9EKaAA0fyYV+TYpjQAAAABJRU5ErkJggg==")
    public void putImageDataInside() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "if (typeof ImageData != 'function') {alert('no ctor'); return; }\n"
                        + "      var arr = new Uint8ClampedArray(64);\n"
                        + "      for (var i = 0; i < 32; i += 4) {\n"
                        + "        arr[i + 0] = 0; arr[i + 1] = 190; arr[i + 2] = 3; arr[i + 3] = 255;\n"
                        + "      }\n"
                        + "      for (var i = 32; i < 64; i += 4) {\n"
                        + "        arr[i + 0] = 190; arr[i + 1] = 0; arr[i + 2] = 3; arr[i + 3] = 255;\n"
                        + "      }\n"
                        + "      var imageData = new ImageData(arr, 4, 4);\n"
                        + "      context.putImageData(imageData, 0, 0);\n"
                        + "      context.putImageData(imageData, 2, 4);\n"
                        + "      context.putImageData(imageData, 16, 0);\n"
                        + "      context.putImageData(imageData, 16, 16);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAGtJREFUOE9jZNjH/J+BgYFhnxOIRAAn"
            + "hr+MqCLE8RipbuA+BogLyXURursZqW4g3AZoWML5TuSGIcwEahsI8zrMfHLDFJ40qG4gcamMsCqyEi8+Y0cNJBzohFSMhiGh"
            + "ECIsT70whBYuVDcQAEALHZVK+V18AAAAAElFTkSuQmCC")
    public void putImageDataOutside() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "if (typeof ImageData != 'function') {alert('no ctor'); return; }\n"
                        + "      var arr = new Uint8ClampedArray(64);\n"
                        + "      for (var i = 0; i < 32; i += 4) {\n"
                        + "        arr[i + 0] = 0; arr[i + 1] = 190; arr[i + 2] = 3; arr[i + 3] = 255;\n"
                        + "      }\n"
                        + "      for (var i = 32; i < 64; i += 4) {\n"
                        + "        arr[i + 0] = 190; arr[i + 1] = 0; arr[i + 2] = 3; arr[i + 3] = 255;\n"
                        + "      }\n"
                        + "      var imageData = new ImageData(arr, 4, 4);\n"
                        + "      context.putImageData(imageData, -2, 0);\n"
                        + "      context.putImageData(imageData, 2, -2);\n"
                        + "      context.putImageData(imageData, 2, 4);\n"
                        + "      context.putImageData(imageData, 18, 18);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAF1JREFUOE/tkskJACAMBBPszMLTmSjR"
            + "jxBhPfap38AwDqtCfkrlWaodaJJqlvIMd84zZP4hHehwquES6Np+uG0aDOnAMCMbxpL3VoAb0oGHy8eGH4gK/IaoEL7TGzaf"
            + "lBRV53guuAAAAABJRU5ErkJggg==")
    public void putImageDataDirty() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "if (typeof ImageData != 'function') {alert('no ctor'); return; }\n"
                        + "      var arr = new Uint8ClampedArray(64);\n"
                        + "      for (var i = 0; i < 32; i += 4) {\n"
                        + "        arr[i + 0] = 0; arr[i + 1] = 190; arr[i + 2] = 3; arr[i + 3] = 255;\n"
                        + "      }\n"
                        + "      for (var i = 32; i < 64; i += 4) {\n"
                        + "        arr[i + 0] = 190; arr[i + 1] = 0; arr[i + 2] = 3; arr[i + 3] = 255;\n"
                        + "      }\n"
                        + "      var imageData = new ImageData(arr, 4, 4);\n"
                        + "      context.putImageData(imageData, 0, 0, 1, 2, 1, 1);\n"
                        + "      context.putImageData(imageData, 4, 4, 0, 2, 2, 2);\n"
                        + "      context.putImageData(imageData, 8, 8, 0, 0, 2, 2);\n"
                        + "      context.putImageData(imageData, 18, 0, 1, 1, 2, 3);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAYxJREFUOE+t1L9LV2EUx/HXl2gI+gPS"
            + "piBqMsFRoiGiEIKWIFAicNEahMIpUKPNxcYMoogIGhwFrSkQFUchiqBJlPYSDYf0OXCvPN2uelOf7d7nPO/z63NOyzGfVsa7"
            + "gQ8Vfvw7iemmfkvgbTxCd+XhV5xFF743geYRLmACU+jEIG4ioOcQTpcPgubAePAUq+jACXzCF/ShHfN4hpm9wDnwCR7jOeZq"
            + "ShD/VnAVLxIw7P85JTAurxew+4VVmX756DwW8QAPU70/1kED2INXuIBf+FYQLtYEEDV9jWuYTRH3V9MPYHh6g3cFIKQSpyqh"
            + "kj+MW5jEvSKzXd8B/JFq0pZFs31QJ7P7jUJqu90P4FvcPSRwE0N4Wb4PYDQk79j/RBi279F7XMDfGM8DOmrKawXsr5SP0pR1"
            + "XM5Hsk42TWu4lTS8hCu5KuqE3RQYQ3CnTtjhoBy9EPXPBjoMWCyJsaptdTkMpAk4sw/wD0J7ARvZbzmUdzHXo7iUNk/U6FT6"
            + "DkhEdDrtxM9FNnuurx0UblcV1Fz/YwAAAABJRU5ErkJggg==")
    public void clip() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "if (typeof ImageData != 'function') {alert('no ctor'); return; }\n"
                        + "context.moveTo(2,2); context.lineTo(5,8); context.lineTo(8,2);"
                        + "context.arc(8, 12, 8, 0, 2 * Math.PI); context.stroke();"
                        + "context.clip(); context.fillRect(4, 9, 19, 14);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAK1JREFUOE9jZKAyYKSyeQw4DfwvMsme"
            + "4T9THOPbnGRSLMVj4OT/IIMY3+SS5ItRAxHB/19k2IchzIswT8OSC7o4rjQJUw9PNv9FJt9lYGBQItPAe4xvcpXB6RZmwH/h"
            + "KXMZGP8nkWXgf8Z5sByF5MJJ9gwMjAfIMpDhvwPjm7yDKC4Ecf6LTJ7FwMCQipzliAjD2YxvctPgDsEVyEMhHVK5+CKlDERW"
            + "S1JZR4wlAO50XRUscFdZAAAAAElFTkSuQmCC")
    public void clipWindingEvenOdd() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "if (typeof ImageData != 'function') {alert('no ctor'); return; }\n"
                        + "context.rect(6, 2, 2, 16); context.rect(2, 10, 16, 5); context.clip('evenodd');"
                        + "context.beginPath(); context.arc(10, 10, 8, 0, 2 * Math.PI);"
                        + "context.fillStyle = 'deeppink';context.fill();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAO5JREFUOE/t1K9KREEUx/HP1C2CCIIg"
            + "GxbBJhiFxSb6Dgb/sAbBIBpkLSLYfIbV4isYhX0Cg8lkMG/VYLg66ywOV1gvF+P9lQPDme/85pwzE3CB9a8YNUyxdghpZ4RG"
            + "TWIDrF6BrIY7i9yMsIEVbBPuclTx3bwzbKb1/cAgzykDP3CMFq5wToiHjJWAB+ihjSOcBt4mOWXgw4+rIoLvCc8lYCe6KpiL"
            + "MFxOA1Zx+CcwDfbWDIfvrHZYmGd3wO1rXp8e7WVmT3hconXN2h7DEdHIWPHKGbD7Qv+pek9/Z06pYT1s8/Sa36bG5Pz72HwC"
            + "6OxElc5l3LEAAAAASUVORK5CYII=")
    public void fillTextAndTransform() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "if (typeof ImageData != 'function') {alert('no ctor'); return; }\n"
                        + "context.moveTo(0, 0);\n"
                        + "      context.lineTo(20, 0);\n"
                        + "      context.moveTo(2, 0);\n"
                        + "      context.lineTo(2, 20);\n"
                        + "      context.moveTo(0, 10);\n"
                        + "      context.lineTo(20, 10);\n"
                        + "      context.stroke();\n"
                        + "      context.fillStyle = 'blue';\n"
                        + "      context.fillText('p', 2, 10);\n"
                        + "      context.fillStyle = 'red';\n"
                        + "      context.setTransform(1.0, 0.0, -0.0, 1.0, 11.0, 10.0);\n"
                        + "      context.fillText('n', 0, 0);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAJBJREFUOE+t1FsOgCAMRNFhc7pmXZ0p"
            + "CQSkj6GhP4YQL+erBYenHO5hDD4AruQDL4Bb/h2DEpNoZiQm0Sko54yy67RgRtl1WnBXOems4I5y0llBVrnovCCjXHReMFKq"
            + "uijoKVVdFLSUpo4JakpTxwT/SlfHBkelq2ODTSnfulG8YfdhW2t1o5wIRp1+zwrp4Ac9kx4V/3bxXQAAAABJRU5ErkJggg==")
    public void pathFill() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "context.moveTo(2, 2);\n"
                        + "      context.lineTo(10, 18);\n"
                        + "      context.lineTo(18, 2);\n"
                        + "      context.closePath();\n"
                        + "      context.fill();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAQxJREFUOE+11M8qRVEUx/HPfRMTychE"
            + "KEJKGchUUqKYmLolioFS8gIGDIgkMwNlYsRAeQhFEV6CXffUdjp/9qlrT3at9eu79vq3W7p8Wl3miYFPGE4McI6lIm0M3MFe"
            + "AvATQ3itAw7iOQG4jqMyXb6GDxirgN5itipoHriJg4RX5iVnWA7GPDA0JTSnyQm1nMRLETDY7jHVgLiGk0xfNIfb2E8EXmIx"
            + "1hYBR/GYAHzHCN7qgHHad781nSmB/0m1KuXg28UGJnCI6Rz0GvN1gx37x9GHY6x27sz/jQF8NAHG2p5sJDrGFZymbkqZ7gZz"
            + "uMJCk00p07axhX58dQMYNqgXF3Xj9K8fbF3wJP8PpH0mFcFq5PIAAAAASUVORK5CYII=")
    public void pathFillTransform() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "      context.moveTo(2, 2);\n"
                        + "      context.lineTo(6, 14);\n"
                        + "      context.lineTo(14, 2);\n"
                        + "      context.closePath();\n"
                        + "      context.setTransform(1.0, 0.0, 0.0, 1.0, 4.0, 4.0);\n"
                        + "      context.moveTo(2, 2);\n"
                        + "      context.lineTo(6, 14);\n"
                        + "      context.lineTo(14, 2);\n"
                        + "      context.closePath();\n"
                        + "      context.fill();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAOZJREFUOE+tlNENwjAMRC/zVBVD8MkM"
            + "LMASMExX4AuGYA+GCLrIjYzrYlc0P0hteL13cVuw8yo786CBLwCH5AMmAGdvrwbeAFwTwDeAAQB/F0sDmY4po8VkTOgu22Gk"
            + "vao60y2wa0dkE28q0qkFdu1sodLlUKRTb2xauGyhPO2iOvWAW7S76lqHvJ7VbuMzq/4C8l7TPgL3J3BamZAv1QhI7QsTVODh"
            + "vEEL1QhI7ZEDXAF74K5qBOyWVXUqF13VNJAbq3TKxPMAZ189d59ot07tqdo/pL6Hoj3qAf4rYfT50fdTCbcAP2uOMhXdBSkP"
            + "AAAAAElFTkSuQmCC")
    public void pathFillTransform2() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "      context.beginPath();\n"
                        + "      context.moveTo(2, 2);\n"
                        + "      context.lineTo(6, 14);\n"
                        + "      context.lineTo(14, 2);\n"
                        + "      context.closePath();\n"
                        + "      context.fill();\n"
                        + "      context.setTransform(1.0, 0.0, 0.0, 1.0, 4.0, 4.0);\n"
                        + "      context.fillStyle = 'red';"
                        + "      context.beginPath();\n"
                        + "      context.moveTo(2, 2);\n"
                        + "      context.lineTo(6, 14);\n"
                        + "      context.lineTo(14, 2);\n"
                        + "      context.closePath();\n"
                        + "      context.fill();\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAHVJREFUOE/Vk8EOwFAEBNef+3M9lDY0"
            + "mhIOfccnmYwFYfjRMA9/BDLExcC1Lp4tbwGF/bgI30xTwzmgignOLClECtDrZqTFceCdoPipdw0XgX7aFoX9xumXT28NSGFP"
            + "oRfVNhwDXonGE9VC2XAPaORg2jdMgAdeyiQRQvsTZgAAAABJRU5ErkJggg==")
    public void saveRestore() {
        draw("<canvas id='myCanvas' width='20', height='20' style='border: 1px solid red;'></canvas>\n",
                "      context.fillStyle = 'green';\n"
                        + "      context.save();\n"
                        + "      context.fillRect(4, 4, 4, 4);\n"

                        + "      context.fillStyle = 'red';\n"
                        + "      context.fillRect(6, 6, 4, 4);\n"
                        + "      context.save();\n"

                        + "      context.fillStyle = 'blue';\n"
                        + "      context.fillRect(8, 8, 4, 4);\n"

                        + "      context.restore();\n"
                        + "      context.fillRect(12, 12, 4, 4);\n"

                        + "      context.restore();\n"
                        + "      context.fillRect(14, 14, 4, 4);\n"

                        + "      context.restore();\n"
                        + "      context.fillRect(16, 16, 4, 4);\n");
    }

    @Test
    @Alerts("data:image/png;base64,"
            + "iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAAXNSR0IArs4c6QAAAL1JREFUOE9jZEAFjgwMDOZQoZMMDAz7"
            + "0eQJchmhKnhFGBi2CDAw6AcwMHCDxDYwMHz9wMBw8Q0Dgw8DA8NngiZBFYANFGFgOJjAwGDVzcDAgqyxlIHhzwIGhmNvGBjs"
            + "STHQUYWBYf1tBgZ+bJpUGRg+3mFgCCTW+yAXVpQwMDSjuw5mOMiVPQwMtQwMDB3EuJImBlLdy1SPFFDQUDfZIAU21RI2MRFI"
            + "lBpYTiFKMTGKRg0kJpTwqxkNQ8rDEABatjIVyjXhJwAAAABJRU5ErkJggg==")
    public void imageOnLoad() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function test() {\n"
                + "    var img = new Image();\n"
                + "    img.onload = function() {\n"
                + "      var canvas = document.createElement('canvas');\n"
                + "      canvas.width = 20;\n"
                + "      canvas.height = 20;\n"
                + "      var context = canvas.getContext('2d');\n"
                + "      context.drawImage(img, 0, 0);\n"

                + "     alert(canvas.toDataURL());\n"
                + "    }\n"

                + "    img.src = 'data:image/svg+xml,"
                + "<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" "
                + "overflow=\"hidden\" width=\"10\" height=\"10\">"
                + "  <circle cx=\"5\" cy=\"5\" r=\"4\" stroke=\"black\" stroke-width=\"1\" fill=\"red\" />"
                + "</svg>';"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
