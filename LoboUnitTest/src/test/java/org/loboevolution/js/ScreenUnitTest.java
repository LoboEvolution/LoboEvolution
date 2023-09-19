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

package org.loboevolution.js;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

public class ScreenUnitTest extends LoboUnitTest {

    @Test
    public void availHeight() {
        final String[] messages = {"870", "870"};
        testNumericProperty("availHeight", messages);
    }

    @Test
    public void availWidth() {
        final String[] messages = {"1600", "1600"};
        testNumericProperty("availWidth", messages);
    }

    @Test
    public void bufferDepth() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("bufferDepth", messages);
    }

    @Test
    public void colorDepth() {
        final String[] messages = {"24", "24"};
        testNumericProperty("colorDepth", messages);
    }

    @Test
    public void deviceXDPI() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("deviceXDPI", messages);
    }

    @Test
    public void deviceYDPI() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("deviceYDPI", messages);
    }

    @Test
    public void fontSmoothingEnabled() {
        final String[] messages = {"undefined", "false"};
        testBooleanProperty("fontSmoothingEnabled", messages);
    }


    @Test
    public void height() {
        final String[] messages = {"900", "900"};
        testNumericProperty("height", messages);
    }

    @Test
    public void left() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("left", messages);
    }


    @Test
    public void top() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("top", messages);
    }


    @Test
    public void pixelDepth() {
        final String[] messages = {"24", "24"};
        testNumericProperty("pixelDepth", messages);
    }

    @Test
    public void width() {
        final String[] messages = {"1600", "1600"};
        testNumericProperty("width", messages);
    }

    private void testBooleanProperty(final String prop, String[] messages) {
        final String html = "<html><head><title>test</title>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        alert(window.screen." + prop + ");\n"
                + "      } catch(e) { alert('get exception') }\n"

                + "      try {\n"
                + "        window.screen." + prop + " = false;\n"
                + "        alert(window.screen." + prop + ");\n"
                + "      } catch(e) { alert('set exception') }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html, messages);
    }

    private void testNumericProperty(final String prop, String[] messages) {
        final String html = "<html><head><title>test</title>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      try {\n"
                + "        alert(window.screen." + prop + ");\n"
                + "      } catch(e) { alert('get exception') }\n"

                + "      try {\n"
                + "        window.screen." + prop + " = 1234;\n"
                + "        alert(window.screen." + prop + ");\n"
                + "      } catch(e) { alert(e) }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html, messages);
    }
}
