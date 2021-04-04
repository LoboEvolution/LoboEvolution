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
 * Tests for {@link org.loboevolution.html.dom.HTMLObjectElement}.
 */
public class HTMLObjectElementTest extends LoboUnitTest {

    /**
     * <p>form.</p>
     */
    @Test
    public void form() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <object id='o1'></object>\n"
                + "</form>\n"
                + "<object id='o2'></object>\n"
                + "<script>\n"
                + "  alert(document.getElementById('o1').form);\n"
                + "  alert(document.getElementById('o2').form);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"[object HTMLFormElement]", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>responseXML_htmlObject.</p>
     */
    @Test
    public void responseXML_htmlObject() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var xhr = new XMLHttpRequest();\n"
                + "    xhr.open('GET', 'foo.xml', false);\n"
                + "    xhr.send('');\n"
                + "    try {\n"
                + "      alert(xhr.responseXML);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String xml = "<html xmlns='http://www.w3.org/1999/xhtml'>\n"
                + "<object classid='CLSID:test'/>\n"
                + "</html>";
        final String[] messages = {"[object XMLDocument]"};
        checkHtmlAlert(html, messages);
    }
}
