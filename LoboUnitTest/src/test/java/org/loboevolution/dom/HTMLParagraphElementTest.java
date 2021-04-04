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
public class HTMLParagraphElementTest extends LoboUnitTest {


    /**
     * <p>align.</p>
     */
    @Test
    public void align() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var p = document.getElementById('p');\n"
                        + "        alert(p.align);\n"
                        + "        set(p, 'hello');\n"
                        + "        alert(p.align);\n"
                        + "        set(p, 'left');\n"
                        + "        alert(p.align);\n"
                        + "        set(p, 'hi');\n"
                        + "        alert(p.align);\n"
                        + "        set(p, 'right');\n"
                        + "        alert(p.align);\n"
                        + "      }\n"
                        + "      function set(e, value) {\n"
                        + "        try {\n"
                        + "          e.align = value;\n"
                        + "        } catch (e) {\n"
                        + "          alert('error');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'><p id='p'>foo</p></body>\n"
                        + "</html>";
        final String[] messages =  {"", "hello", "left", "hi", "right"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clear.</p>
     */
    @Test
    public void clear() {
        final String html
                = "<html><body>\n"
                + "<p id='p1'>p1</p>\n"
                + "<p id='p2' clear='left'>p2</p>\n"
                + "<p id='p3' clear='all'>p3</p>\n"
                + "<p id='p4' clear='right'>p4</p>\n"
                + "<p id='p5' clear='none'>p5</p>\n"
                + "<p id='p6' clear='2'>p6</p>\n"
                + "<p id='p7' clear='foo'>p7</p>\n"
                + "<script>\n"
                + "function set(p, value) {\n"
                + "  try {\n"
                + "    p.clear = value;\n"
                + "  } catch(e) {\n"
                + "    alert('!');\n"
                + "  }\n"
                + "}\n"
                + "var p1 = document.getElementById('p1');\n"
                + "var p2 = document.getElementById('p2');\n"
                + "var p3 = document.getElementById('p3');\n"
                + "var p4 = document.getElementById('p4');\n"
                + "var p5 = document.getElementById('p5');\n"
                + "var p6 = document.getElementById('p6');\n"
                + "var p7 = document.getElementById('p7');\n"
                + "alert(p1.clear);\n"
                + "alert(p2.clear);\n"
                + "alert(p3.clear);\n"
                + "alert(p4.clear);\n"
                + "alert(p5.clear);\n"
                + "alert(p6.clear);\n"
                + "alert(p7.clear);\n"
                + "set(p1, 'left');\n"
                + "set(p2, 'none');\n"
                + "set(p3, 'right');\n"
                + "set(p4, 'all');\n"
                + "set(p5, 2);\n"
                + "set(p6, 'abc');\n"
                + "set(p7, '8');\n"
                + "alert(p1.clear);\n"
                + "alert(p2.clear);\n"
                + "alert(p3.clear);\n"
                + "alert(p4.clear);\n"
                + "alert(p5.clear);\n"
                + "alert(p6.clear);\n"
                + "alert(p7.clear);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"undefined", "undefined", "undefined", "undefined", "undefined", "undefined",
                "undefined", "left", "none", "right", "all", "2", "abc", "8"};
        checkHtmlAlert(html, messages);
    }
}
