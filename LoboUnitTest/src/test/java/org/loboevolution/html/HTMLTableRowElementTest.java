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
import org.loboevolution.html.dom.HTMLTableRowElement;

/**
 * Tests for {@link HTMLTableRowElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTableRowElementTest extends LoboUnitTest {

    @Test
    @Alerts("[object HTMLTableRowElement]")
    public void simpleScriptable() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <table>\n"
                + "    <tr id='myId'/>\n"
                + "  </table>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "td1", "3", "td2", "td4", "2", "td3", "exception", "exception"})
    public void deleteCell() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var tr = document.getElementById('myId');\n"
                + "    alert(tr.cells.length);\n"
                + "    alert(tr.cells[0].id);\n"
                + "    tr.deleteCell(0);\n"
                + "    alert(tr.cells.length);\n"
                + "    alert(tr.cells[0].id);\n"
                + "    alert(tr.cells[tr.cells.length-1].id);\n"
                + "    tr.deleteCell(-1);\n"
                + "    alert(tr.cells.length);\n"
                + "    alert(tr.cells[tr.cells.length-1].id);\n"
                + "    try { tr.deleteCell(25); } catch(e) { alert('exception'); }\n"
                + "    try { tr.deleteCell(-2); } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <table>\n"
                + "    <tr id='myId'/>\n"
                + "      <td id='td1'>1</td>\n"
                + "      <td id='td2'>2</td>\n"
                + "      <td id='td3'>3</td>\n"
                + "      <td id='td4'>4</td>\n"
                + "    </tr>\n"
                + "  </table>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "exception", "4"})
    public void deleteCellNoArg() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var tr = document.getElementById('myId');\n"
                + "    alert(tr.cells.length);\n"
                + "    try { tr.deleteCell(); } catch(e) { alert('exception'); }\n"
                + "    alert(tr.cells.length);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <table>\n"
                + "    <tr id='myId'/>\n"
                + "      <td id='td1'>1</td>\n"
                + "      <td id='td2'>2</td>\n"
                + "      <td id='td3'>3</td>\n"
                + "      <td id='td4'>4</td>\n"
                + "    </tr>\n"
                + "  </table>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"left", "right", "3", "center", "8", "foo"})
    public void align() {
        final String html
                = "<html><body><table>\n"
                + "  <tr id='tr1' align='left'><td>a</td></tr>\n"
                + "  <tr id='tr2' align='right'><td>b</td></tr>\n"
                + "  <tr id='tr3' align='3'><td>c</td></tr>\n"
                + "</table>\n"
                + "    <script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.align = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var tr1 = document.getElementById('tr1');\n"
                + "  var tr2 = document.getElementById('tr2');\n"
                + "  var tr3 = document.getElementById('tr3');\n"
                + "  alert(tr1.align);\n"
                + "  alert(tr2.align);\n"
                + "  alert(tr3.align);\n"
                + "  set(tr1, 'center');\n"
                + "  set(tr2, '8');\n"
                + "  set(tr3, 'foo');\n"
                + "  alert(tr1.align);\n"
                + "  alert(tr2.align);\n"
                + "  alert(tr3.align);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"p", "po", "", "u", "8", "U8"})
    public void ch() {
        final String html
                = "<html><body><table>\n"
                + "  <tr id='tr1' char='p'><td>a</td></tr>\n"
                + "  <tr id='tr2' char='po'><td>b</td></tr>\n"
                + "  <tr id='tr3'><td>c</td></tr>\n"
                + "</table>\n"
                + "    <script>\n"
                + "  var tr1 = document.getElementById('tr1');\n"
                + "  var tr2 = document.getElementById('tr2');\n"
                + "  var tr3 = document.getElementById('tr3');\n"
                + "  alert(tr1.ch);\n"
                + "  alert(tr2.ch);\n"
                + "  alert(tr3.ch);\n"
                + "  tr1.ch = 'u';\n"
                + "  tr2.ch = '8';\n"
                + "  tr3.ch = 'U8';\n"
                + "  alert(tr1.ch);\n"
                + "  alert(tr2.ch);\n"
                + "  alert(tr3.ch);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "4", "", "5.2", "-3", "abc"})
    public void chOff() {
        final String html
                = "<html><body><table>\n"
                + "  <tr id='tr1' charoff='0'><td>a</td></tr>\n"
                + "  <tr id='tr2' charoff='4'><td>b</td></tr>\n"
                + "  <tr id='tr3'><td>c</td></tr>\n"
                + "</table>\n"
                + "    <script>\n"
                + "  var tr1 = document.getElementById('tr1');\n"
                + "  var tr2 = document.getElementById('tr2');\n"
                + "  var tr3 = document.getElementById('tr3');\n"
                + "  alert(tr1.chOff);\n"
                + "  alert(tr2.chOff);\n"
                + "  alert(tr3.chOff);\n"
                + "  tr1.chOff = '5.2';\n"
                + "  tr2.chOff = '-3';\n"
                + "  tr3.chOff = 'abc';\n"
                + "  alert(tr1.chOff);\n"
                + "  alert(tr2.chOff);\n"
                + "  alert(tr3.chOff);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"top", "baseline", "3", "middle", "8", "BOTtom"})
    public void vAlign() {
        final String html
                = "<html><body><table>\n"
                + "  <tr id='tr1' valign='top'><td>a</td></tr>\n"
                + "  <tr id='tr2' valign='baseline'><td>b</td></tr>\n"
                + "  <tr id='tr3' valign='3'><td>c</td></tr>\n"
                + "</table>\n"
                + "    <script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.vAlign = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var tr1 = document.getElementById('tr1');\n"
                + "  var tr2 = document.getElementById('tr2');\n"
                + "  var tr3 = document.getElementById('tr3');\n"
                + "  alert(tr1.vAlign);\n"
                + "  alert(tr2.vAlign);\n"
                + "  alert(tr3.vAlign);\n"
                + "  set(tr1, 'middle');\n"
                + "  set(tr2, 8);\n"
                + "  set(tr3, 'BOTtom');\n"
                + "  alert(tr1.vAlign);\n"
                + "  alert(tr2.vAlign);\n"
                + "  alert(tr3.vAlign);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "#0000aa", "x"})
    public void bgColor() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var tr = document.getElementById('tr');\n"
                        + "        alert(tr.bgColor);\n"
                        + "        tr.bgColor = '#0000aa';\n"
                        + "        alert(tr.bgColor);\n"
                        + "        tr.bgColor = 'x';\n"
                        + "        alert(tr.bgColor);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><tr id='tr'><td>a</td></tr></table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "3", "1", "-1", "true", "false"})
    public void rowIndexsectionRowIndex() {
        final String html
                = "<html><body><table>\n"
                + "  <tr id='tr1'><td>a</td></tr>\n"
                + "  <tr id='tr2'><td>b</td></tr>\n"
                + "  <tfoot>\n"
                + "    <tr id='trf1'><td>a</td></tr>\n"
                + "    <tr id='trf2'><td>a</td></tr>\n"
                + "  </tfoot>\n"
                + "</table>\n"
                + "    <script>\n"
                + "  var tr1 = document.getElementById('tr1');\n"
                + "  var trf2 = document.getElementById('trf2');\n"
                + "  alert(tr1.rowIndex);\n"
                + "  alert(tr1.sectionRowIndex);\n"
                + "  alert(trf2.rowIndex);\n"
                + "  alert(trf2.sectionRowIndex);\n"
                + "  var tr3 = document.createElement('tr');\n"
                + "  alert(tr3.rowIndex);\n"
                + "  alert(tr3.sectionRowIndex == -1);\n"
                + "  alert(tr3.sectionRowIndex > 1000);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Test for 3180939; same left offset for both
     * rows is expected.
     *
     * @throws Exception if an error occurs
     */
    @Test
    @Alerts("true")
    public void offsetLeftDifferentRows() {
        final String html
                = "<html><body><table>\n"
                + "  <tr>\n"
                + "    <td id='td_1_1'>1_1</td>\n"
                + "    <td id='td_1_2'>1_2</td>\n"
                + "  </tr>\n"
                + "  <tr>\n"
                + "    <td id='td_2_1'>2_1</td>\n"
                + "    <td id='td_2_2'>2_2</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "    <script>\n"
                + "  var o1 = document.getElementById('td_1_1').offsetLeft;\n"
                + "  var o2 = document.getElementById('td_2_1').offsetLeft;\n"
                + "  alert(o1 == o2 ? 'true' : o1 + ' != ' + o2);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"cell1", "[object HTMLTableCellElement]", "abc", "[object Text]", ""})
    public void innerText() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tr id='tab_row'><td>cell1</td></tr>\n"
                + "  </table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tab_row');\n"
                + "  alert(node.innerText);\n"
                + "  alert(node.firstChild);\n"
                + "  try { node.innerText = 'abc'; } catch(e) {alert('ex');}\n"
                + "  alert(node.innerText);\n"
                + "  alert(node.firstChild);\n"
                + "  try { node.innerText = ''; } catch(e) {alert('ex');}\n"
                + "  alert(node.innerText);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"cell1", "[object HTMLTableCellElement]", "abc", "[object Text]", ""})
    public void textContent() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tr id='tab_row'><td>cell1</td></tr>\n"
                + "  </table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tab_row');\n"
                + "  alert(node.textContent);\n"
                + "  alert(node.firstChild);\n"
                + "  try { node.textContent = 'abc'; } catch(e) {alert('ex');}\n"
                + "  alert(node.textContent);\n"
                + "  alert(node.firstChild);\n"
                + "  try { node.textContent = ''; } catch(e) {alert('ex');}\n"
                + "  alert(node.textContent);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    private void insertCell(final String cellIndex) {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "  <table>\n"
                + "    <tr id='myRow'>\n"
                + "      <td>first</td>\n"
                + "      <td>second</td>\n"
                + "    </tr>\n"
                + "  </table>\n"
                + "  <script>\n"
                + "    var row = document.getElementById('myRow');\n"
                + "    alert(row.cells.length);\n"
                + "    try {\n"
                + "      var newCell = row.insertCell(" + cellIndex + ");\n"
                + "      alert(row.cells.length);\n"
                + "      alert(newCell.cellIndex);\n"
                + "    } catch (e) { alert('exception'); }\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "3", "2"})
    public void insertCellEmpty() {
        insertCell("");
    }

    @Test
    @Alerts({"2", "exception"})
    public void insertCellMinusTwo() {
        insertCell("-2");
    }

    @Test
    @Alerts({"2", "3", "2"})
    public void insertCellMinusOne() {
        insertCell("-1");
    }

    @Test
    @Alerts({"2", "3", "0"})
    public void insertCellZero() {
        insertCell("0");
    }

    @Test
    @Alerts({"2", "3", "1"})
    public void insertCellOne() {
        insertCell("1");
    }

    @Test
    @Alerts({"2", "3", "2"})
    public void insertCellTwo() {
        insertCell("2");
    }

    @Test
    @Alerts({"2", "exception"})
    public void insertCellThree() {
        insertCell("3");
    }

    @Test
    @Alerts({"undefined", "#667788", "unknown", "undefined", "undefined", "undefined"})
    public void borderColor() {
        final String html
                = "<html><body>\n"
                + "  <table><tr id='tabr1'></tr></table>\n"
                + "  <table><tr id='tabr2' borderColor='red'></tr></table>\n"
                + "  <table><tr id='tabr3' borderColor='#123456'></tr></table>\n"
                + "  <table><tr id='tabr4' borderColor='unknown'></tr></table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tabr1');\n"
                + "  alert(node.borderColor);\n"
                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColor);\n"
                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColor);\n"
                + "  var node = document.getElementById('tabr2');\n"
                + "  alert(node.borderColor);\n"
                + "  var node = document.getElementById('tabr3');\n"
                + "  alert(node.borderColor);\n"
                + "  var node = document.getElementById('tabr4');\n"
                + "  alert(node.borderColor);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined", "undefined", "undefined"})
    public void borderColorDark() {
        final String html
                = "<html><body>\n"
                + "  <table><tr id='tabr1'></tr></table>\n"
                + "  <table><tr id='tabr2' borderColorDark='red'></tr></table>\n"
                + "  <table><tr id='tabr3' borderColorDark='#123456'></tr></table>\n"
                + "  <table><tr id='tabr4' borderColorDark='unknown'></tr></table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tabr1');\n"
                + "  alert(node.borderColorDark);\n"
                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColorDark);\n"
                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColorDark);\n"
                + "  var node = document.getElementById('tabr2');\n"
                + "  alert(node.borderColorDark);\n"
                + "  var node = document.getElementById('tabr3');\n"
                + "  alert(node.borderColorDark);\n"
                + "  var node = document.getElementById('tabr4');\n"
                + "  alert(node.borderColorDark);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined", "undefined", "undefined"})
    public void borderColorLight() {
        final String html
                = "<html><body>\n"
                + "  <table><tr id='tabr1'></tr></table>\n"
                + "  <table><tr id='tabr2' borderColorLight='red'></tr></table>\n"
                + "  <table><tr id='tabr3' borderColorLight='#123456'></tr></table>\n"
                + "  <table><tr id='tabr4' borderColorLight='unknown'></tr></table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tabr1');\n"
                + "  alert(node.borderColorLight);\n"
                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColorLight);\n"
                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColorLight);\n"
                + "  var node = document.getElementById('tabr2');\n"
                + "  alert(node.borderColorLight);\n"
                + "  var node = document.getElementById('tabr3');\n"
                + "  alert(node.borderColorLight);\n"
                + "  var node = document.getElementById('tabr4');\n"
                + "  alert(node.borderColorLight);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void heightIsMaxOfCells() {
        final String html
                = "<html><body>\n"
                + "<table>\n"
                + "  <tr id='tr1'>\n"
                + "    <td style='height: 30px'>a</td>\n"
                + "    <td style='height: 10px'>b</td>\n"
                + "    <td style='height: 40px'>d</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "    <script>\n"
                + "  var tr1 = document.getElementById('tr1');\n"
                + "  alert(tr1.offsetHeight > 35);\n"
                + "  alert(tr1.offsetHeight < 45);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
