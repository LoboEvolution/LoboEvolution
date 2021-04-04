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
 * Tests for {@link org.loboevolution.html.dom.HTMLTableColElement}.
 */
public class HTMLTableColElementTest extends LoboUnitTest {

    /**
     * <p>getAlign.</p>
     */
    @Test
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <col id='c1' align='left' ></col>\n"
                + "    <col id='c2' align='right' ></col>\n"
                + "    <col id='c3' align='justify' ></col>\n"
                + "    <col id='c4' align='char' ></col>\n"
                + "    <col id='c5' align='center' ></col>\n"
                + "    <col id='c6' align='wrong' ></col>\n"
                + "    <col id='c7' ></col>\n"
                + "  </table>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 7; i++) {\n"
                + "    alert(document.getElementById('c' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"left", "right", "justify", "char", "center", "wrong", null};
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
                + "    <col id='c1' align='left' ></col>\n"
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
                + "  setAlign(elem, 'justify');\n"
                + "  setAlign(elem, 'char');\n"
                + "  setAlign(elem, 'center');\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"CenTer", "8", "foo", "left", "right", "justify", "char", "center"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>ch.</p>
     */
    @Test
    public void ch() {
        final String html
                = "<html><body><table>\n"
                + "  <col id='c1' char='p'></col>\n"
                + "  <col id='c2' char='po'></col>\n"
                + "  <col id='c3'></col>\n"
                + "  <tr>\n"
                + "    <td>a</td>\n"
                + "    <td>b</td>\n"
                + "    <td>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var c1 = document.getElementById('c1');\n"
                + "  var c2 = document.getElementById('c2');\n"
                + "  var c3 = document.getElementById('c3');\n"
                + "  alert(c1.ch);\n"
                + "  alert(c2.ch);\n"
                + "  alert(c3.ch);\n"
                + "  c1.ch = 'u';\n"
                + "  c2.ch = '8';\n"
                + "  c3.ch = 'U8';\n"
                + "  alert(c1.ch);\n"
                + "  alert(c2.ch);\n"
                + "  alert(c3.ch);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"p", "po", "", "u", "8", "U8"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>chOff.</p>
     */
    @Test
    public void chOff() {
        final String html
                = "<html><body><table>\n"
                + "  <col id='c1' charoff='0'></col>\n"
                + "  <col id='c2' charoff='4'></col>\n"
                + "  <col id='c3'></col>\n"
                + "  <tr>\n"
                + "    <td>a</td>\n"
                + "    <td>b</td>\n"
                + "    <td>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var c1 = document.getElementById('c1');\n"
                + "  var c2 = document.getElementById('c2');\n"
                + "  var c3 = document.getElementById('c3');\n"
                + "  alert(c1.chOff);\n"
                + "  alert(c2.chOff);\n"
                + "  alert(c3.chOff);\n"
                + "  c1.chOff = '5.2';\n"
                + "  c2.chOff = '-3';\n"
                + "  c3.chOff = 'abc';\n"
                + "  alert(c1.chOff);\n"
                + "  alert(c2.chOff);\n"
                + "  alert(c3.chOff);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"0", "4", "", "5.2", "-3", "abc"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>span.</p>
     */
    @Test
    public void span() {
        final String html
                = "<html><body><table>\n"
                + "  <col id='c1' span='0'></col>\n"
                + "  <col id='c2' span='2'></col>\n"
                + "  <col id='c3'></col>\n"
                + "  <tr>\n"
                + "    <td>a</td>\n"
                + "    <td>b</td>\n"
                + "    <td>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.span = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var c1 = document.getElementById('c1');\n"
                + "  var c2 = document.getElementById('c2');\n"
                + "  var c3 = document.getElementById('c3');\n"
                + "  alert(c1.span);\n"
                + "  alert(c2.span);\n"
                + "  alert(c3.span);\n"
                + "  set(c1, '5.2');\n"
                + "  set(c2, '-3');\n"
                + "  set(c3, 'abc');\n"
                + "  alert(c1.span);\n"
                + "  alert(c2.span);\n"
                + "  alert(c3.span);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"1", "2", "1", "5", "1", "1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>vAlign.</p>
     */
    @Test
    public void vAlign() {
        final String html
                = "<html><body><table>\n"
                + "  <col id='c1' valign='top'></col>\n"
                + "  <col id='c2' valign='baseline'></col>\n"
                + "  <col id='c3' valign='3'></col>\n"
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

        final String[] messages = {"top", "baseline", "3", "middle", "8", "BOTtom"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>width.</p>
     */
    @Test
    public void width() {
        final String html
                = "<html><body><table>\n"
                + "  <col id='c1' width='50'></col>\n"
                + "  <col id='c2' width='75%'></col>\n"
                + "  <col id='c3' width='foo'></col>\n"
                + "  <col id='c4' width='-7'></col>\n"
                + "  <col id='c5' width='20.2'></col>\n"
                + "  <col id='c6'></col>\n"
                + "  <tr>\n"
                + "    <td>a</td>\n"
                + "    <td>b</td>\n"
                + "    <td>c</td>\n"
                + "    <td>d</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.width = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var c1 = document.getElementById('c1');\n"
                + "  var c2 = document.getElementById('c2');\n"
                + "  var c3 = document.getElementById('c3');\n"
                + "  var c4 = document.getElementById('c4');\n"
                + "  var c5 = document.getElementById('c5');\n"
                + "  var c6 = document.getElementById('c6');\n"
                + "  alert(c1.width);\n"
                + "  alert(c2.width);\n"
                + "  alert(c3.width);\n"
                + "  alert(c4.width);\n"
                + "  alert(c5.width);\n"
                + "  alert(c6.width);\n"
                + "  set(c1, '80');\n"
                + "  set(c2, 40);\n"
                + "  set(c3, 'abc');\n"
                + "  set(c4, -10);\n"
                + "  set(c5, '30%');\n"
                + "  set(c6, 33.3);\n"
                + "  alert(c1.width);\n"
                + "  alert(c2.width);\n"
                + "  alert(c3.width);\n"
                + "  alert(c4.width);\n"
                + "  alert(c5.width);\n"
                + "  alert(c6.width);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"50", "75%", "foo", "-7", "20.2", "", "80", "40", "abc", "-10", "30%", "33.3"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>width_px.</p>
     */
    @Test
    public void width_px() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    myCol.width = '128px';\n"
                + "    alert(myCol.width);\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "<table>\n"
                + "  <col id='myCol'></col>\n"
                + "</table>\n"
                + "</body></html>";

        final String[] messages = {"128px"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>width_null.</p>
     */
    @Test
    public void width_null() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    myCol.width = null;\n"
                + "    alert(myCol.width);\n"
                + "    alert(typeof myCol.width);\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "<table>\n"
                + "  <col id='myCol'></col>\n"
                + "</table>\n"
                + "</body></html>";

        final String[] messages = {null, "object"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>parsing.</p>
     */
    @Test
    public void parsing() {
        final String html
                = "<html><body><div><table><colgroup><col></colgroup></table></div>\n"
                + "<script>\n"
                + "  alert(document.body.firstChild.innerHTML);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"<table><colgroup><col></colgroup></table>"};
        checkHtmlAlert(html, messages);
    }
}
