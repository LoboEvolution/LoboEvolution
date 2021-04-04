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
 * Tests for {@link org.loboevolution.html.dom.HTMLFieldSetElement}.
 */
public class HTMLFieldSetElementTest extends LoboUnitTest {

    /**
     * <p>getAlign.</p>
     */
    @Test
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <form>\n"
                + "    <fieldset id='f1' align='left' ></fieldset>\n"
                + "    <fieldset id='f2' align='right' ></fieldset>\n"
                + "    <fieldset id='f3' align='bottom' ></fieldset>\n"
                + "    <fieldset id='f4' align='middle' ></fieldset>\n"
                + "    <fieldset id='f5' align='top' ></fieldset>\n"
                + "    <fieldset id='f6' align='absbottom' ></fieldset>\n"
                + "    <fieldset id='f7' align='absmiddle' ></fieldset>\n"
                + "    <fieldset id='f8' align='baseline' ></fieldset>\n"
                + "    <fieldset id='f9' align='texttop' ></fieldset>\n"
                + "    <fieldset id='f10' align='wrong' ></fieldset>\n"
                + "    <fieldset id='f11' ></fieldset>\n"
                + "  </form>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 11; i++) {\n"
                + "    alert(document.getElementById('f' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages =  {"undefined", "undefined", "undefined", "undefined", "undefined", "undefined",
                "undefined", "undefined", "undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAlign.</p>
     */
    @Test
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <form>\n"
                + "    <fieldset id='i1' align='left' />\n"
                + "  <form>\n"

                + "<script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"

                + "  var elem = document.getElementById('i1');\n"
                + "  setAlign(elem, 'CenTer');\n"

                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"

                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'bottom');\n"
                + "  setAlign(elem, 'middle');\n"
                + "  setAlign(elem, 'top');\n"
                + "  setAlign(elem, 'absbottom');\n"
                + "  setAlign(elem, 'absmiddle');\n"
                + "  setAlign(elem, 'baseline');\n"
                + "  setAlign(elem, 'texttop');\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"CenTer", "8", "foo", "left", "right",
                "bottom", "middle", "top", "absbottom", "absmiddle", "baseline", "texttop"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>form.</p>
     */
    @Test
    public void form() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "  <form>\n"
                + "    <fieldset id='a' />\n"
                + "  </form>"
                + "  <script>\n"
                + "    alert(document.getElementById('a').form);\n"
                + "  </script>"
                + "</body>"
                + "</html>";
        final String[] messages = {"[object HTMLFormElement]"};
        checkHtmlAlert(html, messages);
    }
}
