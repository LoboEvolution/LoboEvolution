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
 * Tests for {@link org.loboevolution.html.dom.HTMLBaseFontElement}.
 */
public class HTMLBaseFontElementTest extends LoboUnitTest {

    /**
     * <p>defaults.</p>
     */
    @Test
    public void defaults() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base);\n"
                        + "        alert(base.face);\n"
                        + "        alert(base.size);\n"
                        + "        alert(base.color);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"[object HTMLElement]", "undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>size.</p>
     */
    @Test
    public void size() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='4' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.size);\n"
                        + "        try {\n"
                        + "          base.size = 42;\n"
                        + "          alert(base.size);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "42"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>face.</p>
     */
    @Test
    public void face() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='5' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.face);\n"
                        + "        try {\n"
                        + "          base.face = 'helvetica';\n"
                        + "          alert(base.face);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "helvetica"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>color.</p>
     */
    @Test
    public void color() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='4' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.color);\n"
                        + "        try {\n"
                        + "          base.color = 'blue';\n"
                        + "          alert(base.color);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "blue"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>type.</p>
     */
    @Test
    public void type() {
        final String html =
                "<html><head><title>foo</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "  var elem = document.getElementById('b1');\n"
                + "    try {\n"
                + "      alert(elem);\n"
                + "      alert(HTMLBaseFontElement);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <basefont id='b1' color='red' face='swiss' size='4' />\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLElement]", "exception"};
        checkHtmlAlert(html, messages);
    }
}
