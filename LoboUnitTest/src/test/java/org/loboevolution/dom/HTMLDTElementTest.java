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
 * Tests for HTMLDTElement.
 */
public class HTMLDTElementTest extends LoboUnitTest {

    /**
     * <p>noWrap.</p>
     */
    @Test
    public void noWrap() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var dt = document.getElementById('test');\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "        dt.noWrap = 'nowrap';\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "        dt.noWrap = 'x';\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "        dt.setAttribute('noWrap', 'blah');\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "        dt.noWrap = '';\n"
                        + "        alert(dt.noWrap);\n"
                        + "        alert(dt.getAttribute('noWrap'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <dl><dt id='test'>dt</dt></dl>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"undefined", null, "nowrap", null, "x", null, "x", "blah", "", "blah"};
        checkHtmlAlert(html, messages);
    }
}
