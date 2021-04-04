/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLOutputElement}.
 */
public class HTMLOutputElementTest extends LoboUnitTest {

    /**
     * <p>labels.</p>
     */
    @Test
    public void labels() {
        final String html =
                "<html><head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      debug(document.getElementById('e1'));\n"
                        + "      debug(document.getElementById('e2'));\n"
                        + "      debug(document.getElementById('e3'));\n"
                        + "      debug(document.getElementById('e4'));\n"
                        + "      var labels = document.getElementById('e4').labels;\n"
                        + "      document.body.removeChild(document.getElementById('l4'));\n"
                        + "      debug(document.getElementById('e4'));\n"
                        + "      alert(labels ? labels.length : labels);\n"
                        + "    }\n"
                        + "    function debug(e) {\n"
                        + "      alert(e.labels ? e.labels.length : e.labels);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <output id='e1'>e 1</output><br>\n"
                        + "  <label>something <label> click here <output id='e2'>e 2</output></label></label><br>\n"
                        + "  <label for='e3'> and here</label>\n"
                        + "  <output id='e3'>e 3</output><br>\n"
                        + "  <label id='l4' for='e4'> what about</label>\n"
                        + "  <label> this<output id='e4'>e 4</output></label><br>\n"
                        + "</body></html>";

        final String[] messages = {"0", "2", "1", "2", "1", "1"};
        checkHtmlAlert(html, messages);
    }
}
