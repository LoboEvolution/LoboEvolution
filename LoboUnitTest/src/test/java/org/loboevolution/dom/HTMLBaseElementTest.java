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
 * * Tests for {@link org.loboevolution.html.dom.HTMLBaseElement}.
 */
public class HTMLBaseElementTest extends LoboUnitTest {

    /**
     * <p>hrefAndTarget.</p>
     */
    @Test
    public void hrefAndTarget() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <base id='b1' href='http://www.foo.com/images/' />\n"
                        + "    <base id='b2' target='_blank' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.getElementById('b1').href);\n"
                        + "        alert(document.getElementById('b2').href);\n"
                        + "        alert(document.getElementById('b1').target);\n"
                        + "        alert(document.getElementById('b2').target);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"http://www.foo.com/images/", "§§URL§§", "", "_blank"};
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
                + "      alert(HTMLBaseElement);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <base id='b1' href='http://somehost/images/' />\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLBaseElement]", "function HTMLBaseElement() { [native code] }"};
        checkHtmlAlert(html, messages);
    }
}
