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
 * Tests for {@link org.loboevolution.html.dom.HTMLTableCellElement}.
 */
public class HTMLTableCellElementTest extends LoboUnitTest {

    /**
     * <p>align.</p>
     */
    @Test
    public void align() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' align='left'>a</td>\n"
                + "    <td id='td2' align='right'>b</td>\n"
                + "    <td id='td3' align='3'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.align = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  alert(td1.align);\n"
                + "  alert(td2.align);\n"
                + "  alert(td3.align);\n"
                + "  set(td1, 'center');\n"
                + "  set(td2, '8');\n"
                + "  set(td3, 'foo');\n"
                + "  alert(td1.align);\n"
                + "  alert(td2.align);\n"
                + "  alert(td3.align);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"left", "right", "3", "center", "8", "foo"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>ch.</p>
     */
    @Test
    public void ch() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' char='p'>a</td>\n"
                + "    <td id='td2' char='po'>b</td>\n"
                + "    <td id='td3'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  alert(td1.ch);\n"
                + "  alert(td2.ch);\n"
                + "  alert(td3.ch);\n"
                + "  td1.ch = 'u';\n"
                + "  td2.ch = '8';\n"
                + "  td3.ch = 'U8';\n"
                + "  alert(td1.ch);\n"
                + "  alert(td2.ch);\n"
                + "  alert(td3.ch);\n"
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
                + "  <tr>\n"
                + "    <td id='td1' charoff='0'>a</td>\n"
                + "    <td id='td2' charoff='4'>b</td>\n"
                + "    <td id='td3'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  alert(td1.chOff);\n"
                + "  alert(td2.chOff);\n"
                + "  alert(td3.chOff);\n"
                + "  td1.chOff = '5.2';\n"
                + "  td2.chOff = '-3';\n"
                + "  td3.chOff = 'abc';\n"
                + "  alert(td1.chOff);\n"
                + "  alert(td2.chOff);\n"
                + "  alert(td3.chOff);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"0", "4", "", "5.2", "-3", "abc"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>vAlign.</p>
     */
    @Test
    public void vAlign() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' valign='top'>a</td>\n"
                + "    <td id='td2' valign='baseline'>b</td>\n"
                + "    <td id='td3' valign='3'>c</td>\n"
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
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  alert(td1.vAlign);\n"
                + "  alert(td2.vAlign);\n"
                + "  alert(td3.vAlign);\n"
                + "  set(td1, 'middle');\n"
                + "  set(td2, 8);\n"
                + "  set(td3, 'BOTtom');\n"
                + "  alert(td1.vAlign);\n"
                + "  alert(td2.vAlign);\n"
                + "  alert(td3.vAlign);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"top", "baseline", "3", "middle", "8", "BOTtom"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>bgColor.</p>
     */
    @Test
    public void bgColor() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var td = document.getElementById('td');\n"
                        + "        alert(td.bgColor);\n"
                        + "        td.bgColor = '#0000aa';\n"
                        + "        alert(td.bgColor);\n"
                        + "        td.bgColor = 'x';\n"
                        + "        alert(td.bgColor);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><tr><td id='td'>a</td></tr></table>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {null, "#0000aa", "x"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>noWrap.</p>
     */
    @Test
    public void noWrap() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var td = document.getElementById('td');\n"
                        + "        alert(td.noWrap);\n"
                        + "        alert(td.getAttribute('noWrap'));\n"
                        + "        td.noWrap = 'nowrap';\n"
                        + "        alert(td.noWrap);\n"
                        + "        alert(td.getAttribute('noWrap'));\n"
                        + "        td.noWrap = 'x';\n"
                        + "        alert(td.noWrap);\n"
                        + "        alert(td.getAttribute('noWrap'));\n"
                        + "        td.setAttribute('noWrap', 'blah');\n"
                        + "        alert(td.noWrap);\n"
                        + "        alert(td.getAttribute('noWrap'));\n"
                        + "        td.noWrap = '';\n"
                        + "        alert(td.noWrap);\n"
                        + "        alert(td.getAttribute('noWrap'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><tr><td id='td'>a</td></tr></table>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {"false", null, "true", "", "true", "", "true", "blah", "false", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>abbr.</p>
     */
    @Test
    public void abbr() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var td = document.getElementById('td');\n"
                        + "        alert(td.abbr);\n"
                        + "        td.abbr = 'blah';\n"
                        + "        alert(td.abbr);\n"
                        + "        td.abbr = 3;\n"
                        + "        alert(td.abbr);\n"
                        + "        td.abbr = '';\n"
                        + "        alert(td.abbr);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><tr><td id='td'>a</td></tr></table>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {null, "blah", "3", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>colSpan.</p>
     */
    @Test
    public void colSpan() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1'>a</td>\n"
                + "    <td id='td2' colspan='3'>b</td>\n"
                + "    <td id='td3' colspan='foo'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.colSpan = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  alert(td1.colSpan);\n"
                + "  alert(td2.colSpan);\n"
                + "  alert(td3.colSpan);\n"
                + "  set(td1, '2');\n"
                + "  set(td2, 'blah');\n"
                + "  set(td3, 5);\n"
                + "  alert(td1.colSpan);\n"
                + "  alert(td2.colSpan);\n"
                + "  alert(td3.colSpan);\n"
                + "  set(td1, -1);\n"
                + "  set(td2, 2.2);\n"
                + "  set(td3, 0);\n"
                + "  alert(td1.colSpan);\n"
                + "  alert(td2.colSpan);\n"
                + "  alert(td3.colSpan);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"1", "3", "1", "2", "1", "5", "1", "2", "1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>rowSpan.</p>
     */
    @Test
    public void rowSpan() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1'>a</td>\n"
                + "    <td id='td2' rowspan='3'>b</td>\n"
                + "    <td id='td3' rowspan='foo'>c</td>\n"
                + "  </tr>\n"
                + "  <tr><td>a</td><td>b</td><td>c</td></tr>\n"
                + "  <tr><td>a</td><td>b</td><td>c</td></tr>\n"
                + "  <tr><td>a</td><td>b</td><td>c</td></tr>\n"
                + "  <tr><td>a</td><td>b</td><td>c</td></tr>\n"
                + "  <tr><td>a</td><td>b</td><td>c</td></tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.rowSpan = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  alert(td1.rowSpan);\n"
                + "  alert(td2.rowSpan);\n"
                + "  alert(td3.rowSpan);\n"
                + "  set(td1, '2');\n"
                + "  set(td2, 'blah');\n"
                + "  set(td3, 5);\n"
                + "  alert(td1.rowSpan);\n"
                + "  alert(td2.rowSpan);\n"
                + "  alert(td3.rowSpan);\n"
                + "  set(td1, -1);\n"
                + "  set(td2, 2.2);\n"
                + "  set(td3, 0);\n"
                + "  alert(td1.rowSpan);\n"
                + "  alert(td2.rowSpan);\n"
                + "  alert(td3.rowSpan);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"1", "3", "1", "2", "1", "5", "1", "2", "1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>axis.</p>
     */
    @Test
    public void axis() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var td = document.getElementById('td');\n"
                        + "        alert(td.axis);\n"
                        + "        td.axis = 'blah';\n"
                        + "        alert(td.axis);\n"
                        + "        td.axis = 'abc , xyz';\n"
                        + "        alert(td.axis);\n"
                        + "        td.axis = 3;\n"
                        + "        alert(td.axis);\n"
                        + "        td.axis = '';\n"
                        + "        alert(td.axis);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><tr><td id='td'>a</td></tr></table>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {null, "blah", "abc , xyz", "3", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>cellWidthHeightWithBorderCollapse.</p>
     */
    @Test
    public void cellWidthHeightWithBorderCollapse() {
        final String html = "<html><body><table id='t'><tr>\n"
                + "<td id='td1' style='width: 80px; height: 30px; "
                + "border: 2px solid blue; border-width: 2px 7px 10px 13px; padding: 0px;'>a</td>\n"
                + "</tr></table>\n"
                + "<script>\n"
                + "  var t = document.getElementById('t');\n"
                + "  var td1 = document.getElementById('td1');\n"

                + "  alert(td1.offsetWidth + ',' + td1.offsetHeight);\n"

                + "  t.style.borderCollapse = 'collapse';\n"
                + "  alert(td1.offsetWidth + ',' + td1.offsetHeight);\n"

                + "</script></body></html>";

        final String[] messages = {"100,42", "90,36"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>cellWidthHeightWithBorderCollapseCellsInRow.</p>
     */
    @Test
    public void cellWidthHeightWithBorderCollapseCellsInRow() {
        final String html = "<html><body><table id='t'><tr>\n"
                + "<td id='td1' style='width: 80px; height: 30px; border: 2px solid blue; padding: 0px;'>a</td>\n"
                + "<td id='td2' style='width: 80px; height: 30px; "
                + "border: solid blue; border-width: 2px; padding: 0px;'>a</td>\n"
                + "<td id='td3' style='width: 80px; height: 30px; "
                + "border: 2px solid blue; border-width: 2px 7px 10px 13px; padding: 0px;'>a</td>\n"
                + "</tr></table>\n"
                + "<script>\n"
                + "  var t = document.getElementById('t');\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"

                + "  alert(td1.offsetWidth + ',' + td1.offsetHeight);\n"
                + "  alert(td2.offsetWidth + ',' + td2.offsetHeight);\n"
                + "  alert(td3.offsetWidth + ',' + td3.offsetHeight);\n"

                + "  t.style.borderCollapse = 'collapse';\n"
                + "  alert(td1.offsetWidth + ',' + td1.offsetHeight);\n"
                + "  alert(td2.offsetWidth + ',' + td2.offsetHeight);\n"
                + "  alert(td3.offsetWidth + ',' + td3.offsetHeight);\n"

                + "</script></body></html>";

        final String[] messages = {"84,42", "84,42", "100,42", "82,36", "88,36", "90,36"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>cellWidthHeightWithBorderCollapseSameCellLayout.</p>
     */
    @Test
    public void cellWidthHeightWithBorderCollapseSameCellLayout() {
        final String html = "<html><body><table id='t'><tr>\n"
                + "<td id='td1' style='width: 80px; height: 30px; border: 2px solid blue; padding: 0px;'>a</td>\n"
                + "<td id='td2' style='width: 80px; height: 30px; border: 2px solid blue; padding: 0px;'>a</td>\n"
                + "<td id='td3' style='width: 80px; height: 30px; border: 2px solid blue; padding: 0px;'>a</td>\n"
                + "</tr></table>\n"
                + "<script>\n"
                + "  var t = document.getElementById('t');\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"

                + "  alert(td1.offsetWidth + ',' + td1.offsetHeight);\n"
                + "  alert(td2.offsetWidth + ',' + td2.offsetHeight);\n"
                + "  alert(td3.offsetWidth + ',' + td3.offsetHeight);\n"

                + "  t.style.borderCollapse = 'collapse';\n"
                + "  alert(td1.offsetWidth + ',' + td1.offsetHeight);\n"
                + "  alert(td2.offsetWidth + ',' + td2.offsetHeight);\n"
                + "  alert(td3.offsetWidth + ',' + td3.offsetHeight);\n"
                + "</script></body></html>";

        final String[] messages = {"84,34", "84,34", "84,34", "82,32", "82,32", "82,32"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>width.</p>
     */
    @Test
    public void width() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function set(e, value) {\n"
                        + "        try {\n"
                        + "          e.width = value;\n"
                        + "        } catch (e) {\n"
                        + "          alert('error');\n"
                        + "        }\n"
                        + "      }\n"
                        + "      function test() {\n"
                        + "        var td = document.getElementById('td');\n"
                        + "        set(td, '100px');\n"
                        + "        alert(td.width);\n"
                        + "        td.height = '200px';\n"
                        + "        alert(td.height);\n"
                        + "        set(td, '400');\n"
                        + "        alert(td.width);\n"
                        + "        set(td, 'abc');\n"
                        + "        alert(td.width);\n"
                        + "        set(td, -5);\n"
                        + "        alert(td.width);\n"
                        + "        set(td, 100.2);\n"
                        + "        alert(td.width);\n"
                        + "        set(td, '10%');\n"
                        + "        alert(td.width);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><tr><td id='td'>a</td></tr></table>\n"
                        + "  </body>\n"
                        + "</html>";

        final String[] messages = {"100px", "200px", "400", "abc", "-5", "100.2", "10%"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>offsetHeight.</p>
     */
    @Test
    public void offsetHeight() {
        final String html =
                "<html><body>\n"
                        + "<table><tr>\n"
                        + "<td style='padding:0' id='it'></td>\n"
                        + "<td style='display: none'>t</td>\n"
                        + "</tr></table>\n"
                        + "<script>\n"
                        + "var it = document.getElementById('it');\n"
                        + "alert(it.offsetHeight);\n"
                        + "</script>\n"
                        + "</body></html>";

        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>borderColor.</p>
     */
    @Test
    public void borderColor() {
        final String html
                = "<html><body>\n"
                + "  <table><tr><td id='tabd1'></td></tr></table>\n"
                + "  <table><tr><td id='tabd2' borderColor='red'></td></tr></table>\n"
                + "  <table><tr><td id='tabd3' borderColor='#123456'></td></tr></table>\n"
                + "  <table><tr><td id='tabd4' borderColor='unknown'></td></tr></table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tabd1');\n"
                + "  alert(node.borderColor);\n"

                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColor);\n"

                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColor);\n"

                + "  var node = document.getElementById('tabd2');\n"
                + "  alert(node.borderColor);\n"
                + "  var node = document.getElementById('tabd3');\n"
                + "  alert(node.borderColor);\n"
                + "  var node = document.getElementById('tabd4');\n"
                + "  alert(node.borderColor);\n"

                + "</script></body></html>";

        final String[] messages = {"undefined", "#667788", "unknown", "undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>borderColorDark.</p>
     */
    @Test
    public void borderColorDark() {
        final String html
                = "<html><body>\n"
                + "  <table><tr><td id='tabd1'></td></tr></table>\n"
                + "  <table><tr><td id='tabd2' borderColor='red'></td></tr></table>\n"
                + "  <table><tr><td id='tabd3' borderColor='#123456'></td></tr></table>\n"
                + "  <table><tr><td id='tabd4' borderColor='unknown'></td></tr></table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tabd1');\n"
                + "  alert(node.borderColorDark);\n"

                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColorDark);\n"

                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColorDark);\n"

                + "  var node = document.getElementById('tabd2');\n"
                + "  alert(node.borderColorDark);\n"
                + "  var node = document.getElementById('tabd3');\n"
                + "  alert(node.borderColorDark);\n"
                + "  var node = document.getElementById('tabd4');\n"
                + "  alert(node.borderColorDark);\n"

                + "</script></body></html>";

        final String[] messages = {"undefined", "undefined", "undefined", "undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>borderColorLight.</p>
     */
    @Test
    public void borderColorLight() {
        final String html
                = "<html><body>\n"
                + "  <table><tr><td id='tabd1'></td></tr></table>\n"
                + "  <table><tr><td id='tabd2' borderColor='red'></td></tr></table>\n"
                + "  <table><tr><td id='tabd3' borderColor='#123456'></td></tr></table>\n"
                + "  <table><tr><td id='tabd4' borderColor='unknown'></td></tr></table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tabd1');\n"
                + "  alert(node.borderColorLight);\n"

                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColorLight);\n"

                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColorLight);\n"

                + "  var node = document.getElementById('tabd2');\n"
                + "  alert(node.borderColorLight);\n"
                + "  var node = document.getElementById('tabd3');\n"
                + "  alert(node.borderColorLight);\n"
                + "  var node = document.getElementById('tabd4');\n"
                + "  alert(node.borderColorLight);\n"

                + "</script></body></html>";

        final String[] messages = {"undefined", "undefined", "undefined", "undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>offsetHeightParentHidden.</p>
     */
    @Test
    public void offsetHeightParentHidden() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var table = document.getElementById('table1');\n"
                + "      var td = document.getElementById('td1');\n"
                + "      alert(td.offsetWidth != 0);\n"
                + "      alert(td.offsetHeight != 0);\n"
                + "      td.style.display = 'none';\n"
                + "      alert(td.offsetWidth != 0);\n"
                + "      alert(td.offsetHeight != 0);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <table id='table1'>\n"
                + "    <tr><td id='td1'>One</td></tr>\n"
                + "  </table>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"true", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }
}
