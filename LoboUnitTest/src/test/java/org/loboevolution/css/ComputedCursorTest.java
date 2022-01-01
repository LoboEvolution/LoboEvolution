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

package org.loboevolution.css;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for ComputedCursorTest
 */
public class ComputedCursorTest  extends LoboUnitTest {

    @Test
    public void cursor() {
        final String html = "<html><head>\n" +
                " <script>\n" +
                " function test_computed_value(property, specified) {\n" +
                "   const target = document.getElementById('target');\n" +
                "   target.style[property] = '';\n" +
                "   target.style[property] = specified;\n" +
                "   let readValue = getComputedStyle(target)[property];\n" +
                "   alert(readValue == specified);\n" +
                " } \n" +
                "  function test() {\n" +
                "   test_computed_value('cursor', 'auto');\n" +
                "   test_computed_value('cursor', 'pointer');\n" +
                "   test_computed_value('cursor', 'progress');\n" +
                "   test_computed_value('cursor', 'crosshair');\n" +
                "   test_computed_value('cursor', 'text');\n" +
                "   test_computed_value('cursor', 'move');\n" +
                "   test_computed_value('cursor', 'e-resize');\n" +
                "   test_computed_value('cursor', 'n-resize');\n" +
                "   test_computed_value('cursor', 'ne-resize');\n" +
                "   test_computed_value('cursor', 'nw-resize');\n" +
                "   test_computed_value('cursor', 's-resize');\n" +
                "   test_computed_value('cursor', 'se-resize');\n" +
                "   test_computed_value('cursor', 'sw-resize');\n" +
                "   test_computed_value('cursor', 'w-resize');\n" +
                "   test_computed_value('cursor', 'zoom-in');\n" +
                "   test_computed_value('cursor', 'zoom-out');\n" +
                "  }\n" +
                " </script>\n" +
                " </head>\n" +
                " <body onload='test()'> <div id=target></div>  \n" +
                " </body></hml>";
        final String[] messages = {"true", "true", "true", "true", "true", "true", "true", "true",
                                   "true", "true", "true", "true", "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }
}
