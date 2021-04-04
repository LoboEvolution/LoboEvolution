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
 * Tests for {@link org.loboevolution.html.dom.HTMLHtmlElement}.
 */
public class HTMLHtmlElementTest extends LoboUnitTest {

   @Test
    public void HTMLHtmlElement_toString() {
        final String html = "<html id='myId'><head><title>foo</title><script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.getElementById('myId'));\n"
                + "      alert(HTMLHtmlElement);\n"
                + "    } catch (e) {\n"
                + "      alert('exception');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object HTMLHtmlElement]", "function HTMLHtmlElement() {\n    [native code]\n}"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>childNodes_1.</p>
     */
    @Test
    public void childNodes_1() {
        // The whitespace in this HTML is very important, because we're verifying
        // that it doesn't get included in the childNodes collection.
        final String html = "<html> \n <body> \n <script>\n"
                + "var nodes = document.documentElement.childNodes;\n"
                + "alert(nodes.length);\n"
                + "alert(nodes[0].nodeName);\n"
                + "alert(nodes[1].nodeName);\n"
                + "alert(nodes[0].previousSibling);\n"
                + "alert(nodes[1].nextSibling);\n"
                + "</script> \n </body> \n </html>";
        final String[] messages =  {"2", "HEAD", "BODY", null, null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>childNodes_2.</p>
     */
    @Test
    public void childNodes_2() {
        // The whitespace in this HTML is very important, because we're verifying
        // that it doesn't get included in the childNodes collection.
        final String html = "<html> \n <head> \n <script>\n"
                + "var nodes = document.documentElement.childNodes;\n"
                + "alert(nodes.length);\n"
                + "alert(nodes[0].nodeName);\n"
                + "</script> \n </head> \n </html>";
        final String[] messages = {"1", "HEAD"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientWidth.</p>
     */
    @Test
    public void clientWidth() {
        final String html = "html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var elt = document.body.parentNode;\n"
                + "  alert(elt.clientWidth > 0);\n"
                + "  alert(elt.clientWidth == window.innerWidth);\n"
                + "  alert(elt.clientHeight > 0);\n"
                + "  alert(elt.clientHeight == window.innerHeight);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }
}
