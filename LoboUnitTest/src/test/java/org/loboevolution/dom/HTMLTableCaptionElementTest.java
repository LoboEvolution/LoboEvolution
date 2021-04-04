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
 * Tests for {@link org.loboevolution.html.dom.HTMLTableCaptionElement}.
 */
public class HTMLTableCaptionElementTest extends LoboUnitTest {

    /**
     * <p>getAlign.</p>
     */
    @Test
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <caption id='c1' align='left' ></caption>\n"
                + "    <caption id='c2' align='right' ></caption>\n"
                + "    <caption id='c3' align='bottom' ></caption>\n"
                + "    <caption id='c4' align='top' ></caption>\n"
                + "    <caption id='c5' align='wrong' ></caption>\n"
                + "    <caption id='c6' ></caption>\n"
                + "  </table>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 6; i++) {\n"
                + "    alert(document.getElementById('c' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"left", "right", "bottom", "top", "wrong", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAlign.</p>
     */
    @Test
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <caption id='c1' align='left' ></caption>\n"
                + "  </table>\n"

                + "<script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"

                + "  var elem = document.getElementById('c1');\n"
                + "  setAlign(elem, 'CenTer');\n"

                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"

                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'bottom');\n"
                + "  setAlign(elem, 'top');\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"CenTer", "8", "foo", "left", "right", "bottom", "top"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>vAlign.</p>
     */
    @Test
    public void vAlign() {
        final String html
                = "<html><body><table>\n"
                + "  <caption id='c1' valign='top'>a</caption>\n"
                + "  <caption id='c2' valign='baseline'>b</caption>\n"
                + "  <caption id='c3' valign='3'>c</caption>\n"
                + "  <tr>\n"
                + "    <td>a</td>\n"
                + "    <td>b</td>\n"
                + "    <td>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.vAlign = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var c1 = document.getElementById('c1');\n"
                + "  var c2 = document.getElementById('c2');\n"
                + "  var c3 = document.getElementById('c3');\n"
                + "  alert(c1.vAlign);\n"
                + "  alert(c2.vAlign);\n"
                + "  alert(c3.vAlign);\n"
                + "  set(c1, 'middle');\n"
                + "  set(c2, 8);\n"
                + "  set(c3, 'BOTtom');\n"
                + "  alert(c1.vAlign);\n"
                + "  alert(c2.vAlign);\n"
                + "  alert(c3.vAlign);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"undefined", "undefined", "undefined", "middle", "8", "BOTtom"};
        checkHtmlAlert(html, messages);
    }
}
