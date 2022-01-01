/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.js;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

public class ScreenUnitTest extends LoboUnitTest {

    @Test
    public void availHeight() {
        final String[] messages = {"1040", "1040"};
        testNumericProperty("availHeight", messages);
    }

    @Test
    public void availLeft() {
        final String[] messages = {"0", "0"};
        testNumericProperty("availLeft", messages);
    }

    @Test
    public void availTop() {
        final String[] messages = {"0", "0"};
        testNumericProperty("availTop", messages);
    }

    @Test
    public void availWidth() {
        final String[] messages = {"1920", "1920"};
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
        final String[] messages = {"1080", "1080"};
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
    public void logicalXDPI() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("logicalXDPI", messages);
    }

    @Test
    public void logicalYDPI() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("logicalYDPI", messages);
    }

    @Test
    public void pixelDepth() {
        final String[] messages = {"24", "24"};
        testNumericProperty("pixelDepth", messages);
    }

    @Test
    public void systemXDPI() {
        final String[] messages = {"24", "24"};
        testNumericProperty("systemXDPI", messages);
    }


    @Test
    public void systemYDPI() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("systemYDPI", messages);
    }

    @Test
    public void updateInterval() {
        final String[] messages = {"undefined", "1234"};
        testNumericProperty("updateInterval", messages);
    }

    @Test
    public void width() {
        final String[] messages = {"1920", "1920"};
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
                + "      } catch(e) { alert('set exception') }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html, messages);
    }
}
