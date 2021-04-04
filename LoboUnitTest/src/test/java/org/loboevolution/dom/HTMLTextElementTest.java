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
 * Unit tests for text property.
 */
public class HTMLTextElementTest extends LoboUnitTest {

    /**
     * <p>text.</p>
     */
    @Test
    public void text() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <title>Page Title</title>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var wbr = document.getElementsByTagName('wbr')[0];\n"
                        + "        alert(wbr.text);\n"
                        + "        wbr.text = 'New Text';\n"
                        + "        alert(wbr.text);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <p>wbr test <wbr>wbr<wbr></p>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages =  {"undefined", "New Text"};
        checkHtmlAlert(html, messages);
    }
	
    /**
     * <p>textCreateElement.</p>
     */
    @Test
    public void textCreateElement() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var wbr = document.createElement('wbr');\n"
                        + "        alert(wbr.text);\n"
                        + "        wbr.text = 'New Text';\n"
                        + "        alert(wbr.text);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "New Text"};
        checkHtmlAlert(html, messages);
    }
}
