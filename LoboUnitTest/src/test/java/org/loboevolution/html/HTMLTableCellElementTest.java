/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLTableCellElement;

/**
 * Tests for {@link HTMLTableCellElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTableCellElementTest extends LoboUnitTest {


    @Test
    @Alerts({"left", "right", "3", "center", "8", "foo"})
    public void align() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' align='left'>a</td>\n"
                + "    <td id='td2' align='right'>b</td>\n"
                + "    <td id='td3' align='3'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"p", "po", "", "u", "8", "U8"})
    public void ch() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' char='p'>a</td>\n"
                + "    <td id='td2' char='po'>b</td>\n"
                + "    <td id='td3'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "4", "", "5.2", "-3", "abc"})
    public void chOff() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' charoff='0'>a</td>\n"
                + "    <td id='td2' charoff='4'>b</td>\n"
                + "    <td id='td3'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"top", "baseline", "3", "middle", "8", "BOTtom"})
    public void vAlign() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' valign='top'>a</td>\n"
                + "    <td id='td2' valign='baseline'>b</td>\n"
                + "    <td id='td3' valign='3'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "#0000aa", "x"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "null", "true", "", "true", "", "true", "blah", "false", "null"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "blah", "3", ""})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "3", "1", "2", "1", "5", "1", "2", "1"})
    public void colSpan() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1'>a</td>\n"
                + "    <td id='td2' colspan='3'>b</td>\n"
                + "    <td id='td3' colspan='foo'>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("3")
    public void colSpanLineBreaks() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' colspan='\r3\t\n  '>b</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  alert(td1.colSpan);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "1", "3", "3", "3"})
    public void colSpanInvalid() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' colspan='-1'>b</td>\n"
                + "    <td id='td2' colspan='0'>b</td>\n"
                + "    <td id='td3' colspan='3.14'>b</td>\n"
                + "    <td id='td4' colspan='3.5'>b</td>\n"
                + "    <td id='td5' colspan='3.7'>b</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  var td4 = document.getElementById('td4');\n"
                + "  var td5 = document.getElementById('td5');\n"
                + "  alert(td1.colSpan);\n"
                + "  alert(td2.colSpan);\n"
                + "  alert(td3.colSpan);\n"
                + "  alert(td4.colSpan);\n"
                + "  alert(td5.colSpan);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"999", "1000", "1000"})
    public void colSpanLarge() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' colspan='999'>b</td>\n"
                + "    <td id='td2' colspan='1000'>b</td>\n"
                + "    <td id='td3' colspan='1001'>b</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  alert(td1.colSpan);\n"
                + "  alert(td2.colSpan);\n"
                + "  alert(td3.colSpan);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "3", "1", "2", "1", "5", "1", "2", "1"})
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
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("3")
    public void rowSpanLineBreaks() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' rowspan='\r3\t\n  '>a</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  alert(td1.rowSpan);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "0", "3", "3", "3"})
    public void rowSpanInvalid() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' rowspan='-1'>b</td>\n"
                + "    <td id='td2' rowspan='0'>b</td>\n"
                + "    <td id='td3' rowspan='3.14'>b</td>\n"
                + "    <td id='td4' rowspan='3.5'>b</td>\n"
                + "    <td id='td5' rowspan='3.7'>b</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  var td4 = document.getElementById('td4');\n"
                + "  var td5 = document.getElementById('td5');\n"
                + "  alert(td1.rowSpan);\n"
                + "  alert(td2.rowSpan);\n"
                + "  alert(td3.rowSpan);\n"
                + "  alert(td4.rowSpan);\n"
                + "  alert(td5.rowSpan);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"999", "1001", "65534", "65534"})
    public void rowSpanLarge() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td1' rowspan='999'>b</td>\n"
                + "    <td id='td2' rowspan='1001'>b</td>\n"
                + "    <td id='td3' rowspan='65534'>b</td>\n"
                + "    <td id='td4' rowspan='65535'>b</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  var td2 = document.getElementById('td2');\n"
                + "  var td3 = document.getElementById('td3');\n"
                + "  var td4 = document.getElementById('td4');\n"
                + "  alert(td1.rowSpan);\n"
                + "  alert(td2.rowSpan);\n"
                + "  alert(td3.rowSpan);\n"
                + "  alert(td4.rowSpan);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "blah", "abc , xyz", "3", ""})
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

        checkHtmlAlert(html);
    }

    /**
     * Tests some obscure table cell CSS calculations required by the MochiKit tests.
     *
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"100,42", "90,36"})
    public void cellWidthHeightWithBorderCollapse() {
        final String html
                = "<html><body><table id='t'><tr>\n"
                + "<td id='td1' style='width: 80px; height: 30px; "
                + "border: 2px solid blue; border-width: 2px 7px 10px 13px; padding: 0px;'>a</td>\n"
                + "</tr></table>\n"
                + "    <script>\n"
                + "  var t = document.getElementById('t');\n"
                + "  var td1 = document.getElementById('td1');\n"
                + "  alert(td1.offsetWidth + ',' + td1.offsetHeight);\n"
                + "  t.style.borderCollapse = 'collapse';\n"
                + "  alert(td1.offsetWidth + ',' + td1.offsetHeight);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"84,42", "84,42", "100,42", "82,36", "88,36", "90,36"})
    public void cellWidthHeightWithBorderCollapseCellsInRow() {
        final String html
                = "<html><body><table id='t'><tr>\n"
                + "<td id='td1' style='width: 80px; height: 30px; border: 2px solid blue; padding: 0px;'>a</td>\n"
                + "<td id='td2' style='width: 80px; height: 30px; "
                + "border: solid blue; border-width: 2px; padding: 0px;'>a</td>\n"
                + "<td id='td3' style='width: 80px; height: 30px; "
                + "border: 2px solid blue; border-width: 2px 7px 10px 13px; padding: 0px;'>a</td>\n"
                + "</tr></table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    /**
     * Tests some obscure table cell CSS calculations required by the MochiKit tests.
     *
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts({"84,34", "84,34", "84,34", "82,32", "82,32", "82,32"})
    public void cellWidthHeightWithBorderCollapseSameCellLayout() {
        final String html
                = "<html><body><table id='t'><tr>\n"
                + "<td id='td1' style='width: 80px; height: 30px; border: 2px solid blue; padding: 0px;'>a</td>\n"
                + "<td id='td2' style='width: 80px; height: 30px; border: 2px solid blue; padding: 0px;'>a</td>\n"
                + "<td id='td3' style='width: 80px; height: 30px; border: 2px solid blue; padding: 0px;'>a</td>\n"
                + "</tr></table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"100px", "200px", "400", "abc", "-5", "100.2", "10%"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void offsetHeight() {
        final String html =
                "<html><body>\n"
                        + "<table><tr>\n"
                        + "<td style='padding:0' id='it'></td>\n"
                        + "<td style='display: none'>t</td>\n"
                        + "</tr></table>\n"
                        + "    <script>\n"
                        + "var it = document.getElementById('it');\n"
                        + "alert(it.offsetHeight);\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "#667788", "unknown", "undefined", "undefined", "undefined"})
    public void borderColor() {
        final String html
                = "<html><body>\n"
                + "  <table><tr><td id='tabd1'></td></tr></table>\n"
                + "  <table><tr><td id='tabd2' borderColor='red'></td></tr></table>\n"
                + "  <table><tr><td id='tabd3' borderColor='#123456'></td></tr></table>\n"
                + "  <table><tr><td id='tabd4' borderColor='unknown'></td></tr></table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined", "undefined", "undefined"})
    public void borderColorDark() {
        final String html
                = "<html><body>\n"
                + "  <table><tr><td id='tabd1'></td></tr></table>\n"
                + "  <table><tr><td id='tabd2' borderColor='red'></td></tr></table>\n"
                + "  <table><tr><td id='tabd3' borderColor='#123456'></td></tr></table>\n"
                + "  <table><tr><td id='tabd4' borderColor='unknown'></td></tr></table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined", "undefined", "undefined"})
    public void borderColorLight() {
        final String html
                = "<html><body>\n"
                + "  <table><tr><td id='tabd1'></td></tr></table>\n"
                + "  <table><tr><td id='tabd2' borderColor='red'></td></tr></table>\n"
                + "  <table><tr><td id='tabd3' borderColor='#123456'></td></tr></table>\n"
                + "  <table><tr><td id='tabd4' borderColor='unknown'></td></tr></table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "false", "false"})
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

        checkHtmlAlert(html);
    }

}
