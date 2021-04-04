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
 * Tests for {@link org.loboevolution.html.dom.HTMLTitleElement}.
 */
public class HTMLTitleElementTest extends LoboUnitTest {
	
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
                        + "        var title = document.getElementsByTagName('title')[0];\n"
                        + "        alert(title.text);\n"
                        + "        title.text = 'New Title';\n"
                        + "        alert(title.text);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"Page Title", "New Title"};
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
                        + "        var title = document.createElement('title');\n"
                        + "        alert(title.text);\n"
                        + "        title.text = 'New Title';\n"
                        + "        alert(title.text);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"", "New Title"};
        checkHtmlAlert(html, messages);
    }
}
