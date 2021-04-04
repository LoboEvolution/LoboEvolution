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
 * Tests for {@link org.loboevolution.html.dom.HTMLMapElement}.
 */
public class HTMLMapElementTest extends LoboUnitTest {

    /**
     * <p>areas.</p>
     */
    @Test
    public void areas() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var map = document.createElement('map');\n"
                + "      var area0 = document.createElement('area');\n"
                + "      var area1 = document.createElement('area');\n"
                + "      var area2 = document.createElement('area');\n"
                + "      map.appendChild(area0);\n"
                + "      map.appendChild(area1);\n"
                + "      map.appendChild(area2);\n"
                + "      var areaElems = map.areas;\n"
                + "      alert(areaElems.length);\n"
                + "      alert(area0 === areaElems[0]);\n"
                + "      alert(area1 === areaElems[1]);\n"
                + "      alert(area2 === areaElems[2]);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"3", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }
}
