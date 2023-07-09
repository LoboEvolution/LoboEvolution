/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
import org.loboevolution.html.js.css.MediaQueryListImpl;

/**
 * Tests for {@link MediaQueryListImpl}.
 */
public class MediaQueryListTest extends LoboUnitTest {

    @Test
    public void matches() {
        final String html
                = "<html><head><script>\n"
                + "  function test() {\n"
                + "    if (window.matchMedia) {\n"
                + "      alert(window.matchMedia('(min-width: 400px)').matches);\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void listener() {
        final String html
                = "<html><head><script>\n"
                + "  function listener(mql) {\n"
                + "    alert(mql);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    if (window.matchMedia) {\n"
                + "      var mql = window.matchMedia('(min-width: 400px)');\n"
                + "      mql.addListener(listener);\n"
                + "      alert('added');\n"
                + "      mql.removeListener(listener);\n"
                + "      alert('removed');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"added", "removed"};
        checkHtmlAlert(html, messages);
    }
}
