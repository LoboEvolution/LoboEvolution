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
 * Tests for {@link org.loboevolution.html.dom.HTMLBRElement}.
 */
public class HTMLBRElementTest extends LoboUnitTest {

    /**
     * <p>clear.</p>
     */
    @Test
    public void clear() {
        final String html
                = "<html><body>\n"
                + "<br id='br1'/>\n"
                + "<br id='br2' clear='left'/>\n"
                + "<br id='br3' clear='all'/>\n"
                + "<br id='br4' clear='right'/>\n"
                + "<br id='br5' clear='none'/>\n"
                + "<br id='br6' clear='2'/>\n"
                + "<br id='br7' clear='foo'/>\n"
                + "<script>\n"
                + "function set(br, value) {\n"
                + "  try {\n"
                + "    br.clear = value;\n"
                + "  } catch(e) {\n"
                + "    alert('!');\n"
                + "  }\n"
                + "}\n"
                + "var br1 = document.getElementById('br1');\n"
                + "var br2 = document.getElementById('br2');\n"
                + "var br3 = document.getElementById('br3');\n"
                + "var br4 = document.getElementById('br4');\n"
                + "var br5 = document.getElementById('br5');\n"
                + "var br6 = document.getElementById('br6');\n"
                + "var br7 = document.getElementById('br7');\n"
                + "alert(br1.clear);\n"
                + "alert(br2.clear);\n"
                + "alert(br3.clear);\n"
                + "alert(br4.clear);\n"
                + "alert(br5.clear);\n"
                + "alert(br6.clear);\n"
                + "alert(br7.clear);\n"
                + "set(br1, 'left');\n"
                + "set(br2, 'none');\n"
                + "set(br3, 'right');\n"
                + "set(br4, 'all');\n"
                + "set(br5, 2);\n"
                + "set(br6, 'abc');\n"
                + "set(br7, '8');\n"
                + "alert(br1.clear);\n"
                + "alert(br2.clear);\n"
                + "alert(br3.clear);\n"
                + "alert(br4.clear);\n"
                + "alert(br5.clear);\n"
                + "alert(br6.clear);\n"
                + "alert(br7.clear);\n"
                + "</script></body></html>";
        final String[] messages = {"", "left", "all", "right", "none", "2", "foo", "left",
                "none", "right", "all", "2", "abc", "8"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>outerHTML.</p>
     */
    @Test
    public void outerHTML() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('myId').outerHTML);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "  <br id='myId'>\n"
                + "</body></html>";

        final String[] messages = {"<br id=\"myId\">"};
        checkHtmlAlert(html, messages);
    }
}
