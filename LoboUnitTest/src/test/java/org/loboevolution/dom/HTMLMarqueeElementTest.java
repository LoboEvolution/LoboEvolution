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
 * Tests for {@link org.loboevolution.html.dom.HTMLDivElement}.
 */
public class HTMLMarqueeElementTest extends LoboUnitTest {

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
                        + "        var marquee = document.getElementById('test');\n"
                        + "        alert(marquee.noWrap);\n"
                        + "        alert(marquee.getAttribute('noWrap'));\n"
                        + "        marquee.noWrap = 'nowrap';\n"
                        + "        alert(marquee.noWrap);\n"
                        + "        alert(marquee.getAttribute('noWrap'));\n"
                        + "        marquee.noWrap = 'x';\n"
                        + "        alert(marquee.noWrap);\n"
                        + "        alert(marquee.getAttribute('noWrap'));\n"
                        + "        marquee.setAttribute('noWrap', 'blah');\n"
                        + "        alert(marquee.noWrap);\n"
                        + "        alert(marquee.getAttribute('noWrap'));\n"
                        + "        marquee.noWrap = '';\n"
                        + "        alert(marquee.noWrap);\n"
                        + "        alert(marquee.getAttribute('noWrap'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <marquee id='test'>marquee</marquee>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"undefined", null, "nowrap", null, "x", null, "x", "blah", "", "blah"};
        checkHtmlAlert(html, messages);
    }
}
