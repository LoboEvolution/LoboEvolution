/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import org.loboevolution.html.dom.HTMLTableColElement;

/**
 * Tests for {@link HTMLTableColElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTableColElementTest extends LoboUnitTest {

    @Test
    @Alerts({"left", "right", "justify", "char", "center", "wrong", ""})
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
                + "    <script>\n"
                + "  for (var i = 1; i <= 7; i++) {\n"
                + "    alert(document.getElementById('c' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"CenTer", "8", "foo", "left", "right", "justify", "char", "center"})
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <col id='c1' align='left' ></col>\n"
                + "  </table>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"p", "po", "", "u", "8", "U8"})
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
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "4", "", "5.2", "-3", "abc"})
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
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "1", "5", "1", "1"})
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
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"top", "baseline", "3", "middle", "8", "BOTtom"})
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
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"50", "75%", "foo", "-7", "20.2", "", "80", "40", "abc", "-10", "30%", "33.3"})
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
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("128px")
    public void widthPx() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    /**
     * Regression test for bug 2948498.
     */
    @Test
    @Alerts({"null", "string"})
    public void widthNull() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<table><colgroup><col></colgroup></table>")
    public void parsing() {
        final String html
                = "<html><body><div><table><colgroup><col></colgroup></table></div>\n"
                + "    <script>\n"
                + "  alert(document.body.firstChild.innerHTML);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
