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
package org.loboevolution.node;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.node.CDATASection}.
 */

public class CDATASectionUnitTest extends LoboUnitTest {

    @Test
    public void simpleScriptable() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  alert(document.body.firstChild);\n"
                + "}\n"
                + "</script></head><body onload='test()'><![CDATA[Jeep]]></body></html>";
        final String[] messages = {"[object Comment]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void splitText() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', '', null);\n"
                + "    var root = doc.appendChild(doc.createElement('root'));\n"
                + "    var cdata = root.appendChild(doc.createCDATASection('abcdef'));\n"
                + "    cdata.splitText(2);\n"
                + "    alert(root.childNodes.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"2"};
        checkHtmlAlert(html, messages);
    }

}
