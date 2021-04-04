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
 * Tests for {@link org.loboevolution.html.dom.HTMLParagraphElement}.
 */
public class HTMLPhraseElementTest extends LoboUnitTest {

    /**
     * <p>cite.</p>
     */
    @Test
    public void cite() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        debug(document.createElement('abbr'));\n"
                        + "        debug(document.createElement('big'));\n"
                        + "      }\n"
                        + "      function debug(e) {\n"
                        + "        alert(e + ' ' + e.cite);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'></body>\n"
                        + "</html>";

        final String[] messages = {"[object HTMLElement] undefined", "[object HTMLElement] undefined"};
        checkHtmlAlert(html, messages);
    }
}
