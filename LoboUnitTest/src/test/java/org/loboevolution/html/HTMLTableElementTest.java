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
import org.loboevolution.html.dom.HTMLTableElement;

/**
 * Tests for {@link HTMLTableElement}
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTableElementTest extends LoboUnitTest {

    @Test
    @Alerts({"caption1", "caption2", "null", "caption3", "exception",
            "[object HTMLTableCaptionElement]", "caption3", "caption4"})
    public void tableCaptions() {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "  <table id='table_1'><caption>caption1</caption><caption>caption2</caption>\n"
                + "    <tr><td>cell1</td><td>cell2</td><td rowspan='2'>cell4</td></tr>\n"
                + "    <tr><td colspan='2'>cell3</td></tr>\n"
                + "  </table>\n"
                + "  <script type='text/javascript' language='JavaScript'>\n"
                + "    var table = document.getElementById('table_1');\n"
                + "    alert(table.caption.innerHTML);\n"
                + "    table.deleteCaption();\n"
                + "    alert(table.caption.innerHTML);\n"
                + "    table.deleteCaption();\n"
                + "    alert(table.caption);\n"
                + "    var newCaption = table.createCaption();\n"
                + "    newCaption.innerHTML = 'caption3';\n"
                + "    alert(table.caption.innerHTML);\n"
                + "    try { table.caption = 123; } catch(e) { alert('exception') }\n"
                + "    alert(table.caption);\n"
                + "    if (table.caption) { alert(table.caption.innerHTML) }\n"
                + "    var caption4 = document.createElement('caption');\n"
                + "    caption4.innerHTML = 'caption4';\n"
                + "    try { table.caption = caption4; } catch(e) { alert('exception') }\n"
                + "    alert(table.caption.innerHTML);\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"thead1", "thead2", "null", "thead3", "exception",
            "[object HTMLTableSectionElement]", "thead3", "thead4"})
    public void tableHeaders() {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "  <table id='table_1'>\n"
                + "    <thead id='thead1'><tr><td>cell1</td><td>cell2</td><td>cell3</td></tr></thead>\n"
                + "    <thead id='thead2'><tr><td>cell7</td><td>cell8</td><td>cell9</td></tr></thead>\n"
                + "    <tr><td>cell1</td><td>cell2</td><td rowspan='2'>cell4</td></tr>\n"
                + "    <tr><td colspan='2'>cell3</td></tr>\n"
                + "  </table>\n"
                + "  <script type='text/javascript' language='JavaScript'>\n"
                + "    var table = document.getElementById('table_1');\n"
                + "    alert(table.tHead.id);\n"
                + "    table.deleteTHead();\n"
                + "    alert(table.tHead.id);\n"
                + "    table.deleteTHead();\n"
                + "    alert(table.tHead);\n"
                + "    var newTHead = table.createTHead();\n"
                + "    newTHead.id = 'thead3';\n"
                + "    alert(table.tHead.id);\n"
                + "    try { table.tHead = 123; } catch(e) { alert('exception') }\n"
                + "    alert(table.tHead);\n"
                + "    if (table.tHead) { alert(table.tHead.id) }\n"
                + "    var tHead4 = document.createElement('tHead');\n"
                + "    tHead4.id = 'thead4';\n"
                + "    try { table.tHead = tHead4; } catch(e) { alert('exception') }\n"
                + "    alert(table.tHead.id);\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "true", "4 2 2", "6 3 3"})
    public void tableBodies() {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "  <table id='table_1'>\n"
                + "    <tbody id='tbody1'>\n"
                + "      <tr><td>cell1</td><td>cell2</td></tr>\n"
                + "      <tr><td>cell3</td><td>cell4</td></tr>\n"
                + "    </tbody>\n"
                + "    <tbody id='tbody2'>\n"
                + "      <tr><td>cell1</td><td>cell2</td></tr>\n"
                + "      <tr><td>cell3</td><td>cell4</td></tr>\n"
                + "    </tbody>\n"
                + "  </table>\n"
                + "  <script type='text/javascript' language='JavaScript'>\n"
                + "    var table = document.getElementById('table_1');\n"
                + "    var bodies = table.tBodies;\n"
                + "    alert(bodies.length);\n"
                + "    alert(bodies == table.tBodies);\n"
                + "    var body1 = table.tBodies[0];\n"
                + "    var body2 = table.tBodies[1];\n"
                + "    alert(table.rows.length + ' ' + body1.rows.length + ' ' + body2.rows.length);\n"
                + "    table.insertRow(-1); // Should add at end, to body2.\n"
                + "    body1.insertRow(-1); // Add one to body1, as well.\n"
                + "    alert(table.rows.length + ' ' + body1.rows.length + ' ' + body2.rows.length);\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "true", "3", "2", "3", "2"})
    public void tableRows() {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "  <table id='table_1'>\n"
                + "    <tr><td>cell1</td><td>cell2</td><td rowspan='2'>cell4</td></tr>\n"
                + "    <tr><td colspan='2'>cell3</td></tr>\n"
                + "  </table>\n"
                + "  <script type='text/javascript' language='JavaScript'>\n"
                + "    var table = document.getElementById('table_1');\n"
                + "    var rows = table.rows;\n"
                + "    alert(rows.length);\n"
                + "    alert(rows == table.rows);\n"
                + "    table.insertRow(1);\n"
                + "    alert(rows.length);\n"
                + "    table.deleteRow(1);\n"
                + "    alert(rows.length);\n"
                + "    table.insertRow(rows.length);\n"
                + "    alert(rows.length);\n"
                + "    table.deleteRow(-1);\n"
                + "    alert(rows.length);\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "1"})
    public void tableHeadRows() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var t = document.getElementById('myTable');\n"
                + "  alert(t.rows[0].cells.length);\n"
                + "  alert(t.rows[1].cells.length);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<table id='myTable'>\n"
                + "<tr><th>Some Heading</th></tr>\n"
                + "<tr><td>some desc</td></tr>\n"
                + "</table>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "true", "8 2 2 2 2",
            "9 2 2 2 3", "8 2 2 1 3", "9 2 3 1 3", "8 1 3 1 3"})
    public void tableRowsWithManySections() {
        final String html = "<html><head></head>\n"
                + "<body>\n"
                + "  <table id='table_1'>\n"
                + "    <thead>\n"
                + "      <tr><td>cell1</td><td>cell2</td></tr>\n"
                + "      <tr><td>cell3</td><td>cell4</td></tr>\n"
                + "    </thead>\n"
                + "    <tbody id='tbody1'>\n"
                + "      <tr><td>cell1</td><td>cell2</td></tr>\n"
                + "      <tr><td>cell3</td><td>cell4</td></tr>\n"
                + "    </tbody>\n"
                + "    <tbody id='tbody2'>\n"
                + "      <tr><td>cell1</td><td>cell2</td></tr>\n"
                + "      <tr><td>cell3</td><td>cell4</td></tr>\n"
                + "    </tbody>\n"
                + "    <tfoot>\n"
                + "      <tr><td>cell1</td><td>cell2</td></tr>\n"
                + "      <tr><td>cell3</td><td>cell4</td></tr>\n"
                + "    </tfoot>\n"
                + "  </table>\n"
                + "  <script type='text/javascript' language='JavaScript'>\n"
                + "  <!--\n"
                + "    var table = document.getElementById('table_1');\n"
                + "    var bodies = table.tBodies;\n"
                + "    alert(bodies.length);\n"
                + "    alert(bodies == table.tBodies);\n"
                + "    var head = table.tHead;\n"
                + "    var body1 = table.tBodies.item(0);\n"
                + "    var body2 = table.tBodies.item(1);\n"
                + "    var foot = table.tFoot;\n"
                + "    alert(table.rows.length + ' ' + head.rows.length + ' ' + body1.rows.length "
                + "  + ' ' + body2.rows.length + ' ' + foot.rows.length);\n"
                + "    table.insertRow(6); // Insert a row in the footer.\n"
                + "    alert(table.rows.length + ' ' + head.rows.length + ' ' + body1.rows.length "
                + "  + ' ' + body2.rows.length + ' ' + foot.rows.length);\n"
                + "    table.deleteRow(5); // Delete a row from the second body.\n"
                + "    alert(table.rows.length + ' ' + head.rows.length + ' ' + body1.rows.length "
                + "  + ' ' + body2.rows.length + ' ' + foot.rows.length);\n"
                + "    table.insertRow(2); // Insert a row in the first body.\n"
                + "    alert(table.rows.length + ' ' + head.rows.length + ' ' + body1.rows.length "
                + "  + ' ' + body2.rows.length + ' ' + foot.rows.length);\n"
                + "    table.deleteRow(1); // Delete a row from the header.\n"
                + "    alert(table.rows.length + ' ' + head.rows.length + ' ' + body1.rows.length "
                + "  + ' ' + body2.rows.length + ' ' + foot.rows.length);\n"
                + "  // -->\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"tfoot1", "tfoot2", "null", "tfoot3", "exception",
            "[object HTMLTableSectionElement]", "tfoot3", "tfoot4"})
    public void tableFooters() {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "  <table id='table_1'>\n"
                + "    <tr><td>cell1</td><td>cell2</td><td rowspan='2'>cell4</td></tr>\n"
                + "    <tr><td colspan='2'>cell3</td></tr>\n"
                + "    <tfoot id='tfoot1'><tr><td>cell1</td><td>cell2</td><td>cell3</td></tr></tfoot>\n"
                + "    <tfoot id='tfoot2'><tr><td>cell7</td><td>cell8</td><td>cell9</td></tr></tfoot>\n"
                + "  </table>\n"
                + "  <script>\n"
                + "    var table = document.getElementById('table_1');\n"
                + "    alert(table.tFoot.id);\n"
                + "    table.deleteTFoot();\n"
                + "    alert(table.tFoot.id);\n"
                + "    table.deleteTFoot();\n"
                + "    alert(table.tFoot);\n"
                + "    var newTFoot = table.createTFoot();\n"
                + "    newTFoot.id = 'tfoot3';\n"
                + "    alert(table.tFoot.id);\n"
                + "    try { table.tFoot = 123; } catch(e) { alert('exception') }\n"
                + "    alert(table.tFoot);\n"
                + "    if (table.tFoot) { alert(table.tFoot.id) }\n"
                + "    var tFoot4 = document.createElement('tFoot');\n"
                + "    tFoot4.id = 'tfoot4';\n"
                + "    try { table.tFoot = tFoot4; } catch(e) { alert('exception') }\n"
                + "    alert(table.tFoot.id);\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "0", "1", "-1"})
    public void cellIndex() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('th1').cellIndex);\n"
                + "    alert(document.getElementById('th2').cellIndex);\n"
                + "    alert(document.getElementById('td1').cellIndex);\n"
                + "    alert(document.getElementById('td2').cellIndex);\n"
                + "    alert(document.createElement('td').cellIndex);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'><table>\n"
                + "<tr><th id='th1'>a</th><th id='th2'>b</th></tr>\n"
                + "<tr><td id='td1'>c</td><td id='td2'>d</td></tr>\n"
                + "</table></body></html>";

        checkHtmlAlert(html);
    }

    private void insertRow(final String rowIndex) {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "  <table id='table_1'>\n"
                + "    <tr><td>first</td></tr>\n"
                + "    <tr><td>second</td></tr>\n"
                + "  </table>\n"
                + "  <script>\n"
                + "    var table = document.getElementById('table_1');\n"
                + "    alert(table.rows.length);\n"
                + "    try {\n"
                + "      var newRow = table.insertRow(" + rowIndex + ");\n"
                + "      alert(table.rows.length);\n"
                + "      alert(newRow.rowIndex);\n"
                + "    } catch (e) { alert('exception'); }\n"
                + "  </script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "3", "2"})
    public void insertRowEmpty() {
        insertRow("");
    }

    @Test
    @Alerts({"2", "exception"})
    public void insertRow_MinusTwo() {
        insertRow("-2");
    }

    @Test
    @Alerts({"2", "3", "2"})
    public void insertRow_MinusOne() {
        insertRow("-1");
    }

    @Test
    @Alerts({"2", "3", "0"})
    public void insertRow_Zero() {
        insertRow("0");
    }

    @Test
    @Alerts({"2", "3", "1"})
    public void insertRowOne() {
        insertRow("1");
    }

    @Test
    @Alerts({"2", "3", "2"})
    public void insertRowTwo() {
        insertRow("2");
    }

    @Test
    @Alerts({"2", "exception"})
    public void insertRowThree() {
        insertRow("3");
    }

    @Test
    @Alerts({"mytable", "mytable"})
    public void insertRowNested() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "function test() {\n"
                        + "  var container = document.getElementById('mytable');\n"
                        + "  alert(container.id);\n"
                        + "  var tableRow = container.insertRow(1);\n"
                        + "  alert(tableRow.parentNode.parentNode.id);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<table id='mytable'>\n"
                        + "<tr>\n"
                        + "<td>\n"
                        + "  <table id='nested'><tr><td></td></tr></table>\n"
                        + "</td></tr>\n"
                        + "</table>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"TBODY", "TABLE"})
    public void insertRowInEmptyTable() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "function test() {\n"
                        + "  var oTable = document.getElementById('mytable');\n"
                        + "  var tableRow = oTable.insertRow(0);\n"
                        + "  alert(tableRow.parentNode.tagName);\n"
                        + "  alert(tableRow.parentNode.parentNode.tagName);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<table id='mytable'>\n"
                        + "</table>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"TBODY", "TBODY", "TBODY"})
    public void insertRowInTableWithEmptyTbody() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "function test() {\n"
                        + "  var oTable = document.getElementById('mytable');\n"
                        + "  alert(oTable.lastChild.tagName);\n"
                        + "  var tableRow = oTable.insertRow(0);\n"
                        + "  alert(oTable.lastChild.tagName);\n"
                        + "  alert(tableRow.parentNode.tagName);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<table id='mytable'><tbody></tbody></table>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "1"})
    public void nestedTables() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "function test() {\n"
                        + "  var myTable = document.getElementById('mytable');\n"
                        + "  alert(myTable.rows.length);\n"
                        + "  alert(myTable.tBodies.length);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<table id='mytable'>\n"
                        + "<tr>\n"
                        + "<td>\n"
                        + "  <table id='nested'><tr><td></td></tr></table>\n"
                        + "</td></tr>\n"
                        + "</table>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    /**
     * Tests string default values.
     * See <a href="https://sourceforge.net/tracker/?func=detail&atid=448266&aid=1538136&group_id=47038">issue 370</a>.
     * Currently not working for FF as HtmlUnit's object names don't map to FF ones.
     */
    @Test
    @Alerts({"table: [object HTMLTableElement]",
            "row: [object HTMLTableRowElement]",
            "headcell: [object HTMLTableCellElement]",
            "datacell: [object HTMLTableCellElement]"})
    public void stringValues() {
        final String html =
                "<html><head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      alert('table: ' + document.getElementById('myTable'));\n"
                        + "      alert('row: ' + document.getElementById('myRow'));\n"
                        + "      alert('headcell: ' + document.getElementById('myHeadCell'));\n"
                        + "      alert('datacell: ' + document.getElementById('myDataCell'));\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <table id='myTable'>\n"
                        + "      <tr id='myRow'>\n"
                        + "        <th id='myHeadCell'>Foo</th>\n"
                        + "      </tr>\n"
                        + "      <tr>\n"
                        + "        <td id='myDataCell'>Foo</th>\n"
                        + "      </tr>\n"
                        + "    </table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("21")
    public void cellSpacing() {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "<table id='tableID' cellspacing='2'><tr><td></td></tr></table>\n"
                + "<script language='javascript'>\n"
                + "    var table = document.getElementById('tableID');\n"
                + "    table.cellSpacing += 1;\n"
                + "    alert(table.cellSpacing);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("21")
    public void cellPadding() {
        final String html
                = "<html><head></head>\n"
                + "<body>\n"
                + "<table id='tableID' cellpadding='2'><tr><td></td></tr></table>\n"
                + "<script language='javascript'>\n"
                + "    var table = document.getElementById('tableID');\n"
                + "    table.cellPadding += 1;\n"
                + "    alert(table.cellPadding);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("no refresh function")
    public void refresh() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      if (document.getElementById('myTable').refresh) {\n"
                + "        document.getElementById('myTable').refresh();\n"
                + "        alert('refreshed');\n"
                + "      } else {\n"
                + "        alert('no refresh function');\n"
                + "      }\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<table id='myTable'></table>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "hello", "left", "hi", "right"})
    public void align() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var t = document.getElementById('t');\n"
                        + "        alert(t.align);\n"
                        + "        set(t, 'hello');\n"
                        + "        alert(t.align);\n"
                        + "        set(t, 'left');\n"
                        + "        alert(t.align);\n"
                        + "        set(t, 'hi');\n"
                        + "        alert(t.align);\n"
                        + "        set(t, 'right');\n"
                        + "        alert(t.align);\n"
                        + "      }\n"
                        + "      function set(e, value) {\n"
                        + "        try {\n"
                        + "          e.align = value;\n"
                        + "        } catch (e) {\n"
                        + "          alert('error');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <table id='t'>\n"
                        + "      <thead id='th'/>\n"
                        + "      <tbody id='tb'/>\n"
                        + "      <tfoot id='tf'/>\n"
                        + "    </table>\n"
                        + "  </body>\n"
                        + "</html>";

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
                        + "        var table = document.getElementById('table');\n"
                        + "        alert(table.bgColor);\n"
                        + "        table.bgColor = '#0000aa';\n"
                        + "        alert(table.bgColor);\n"
                        + "        table.bgColor = 'x';\n"
                        + "        alert(table.bgColor);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table id='table'><tr><td>a</td></tr></table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"cell1", "[object Text]", "abc", "[object Text]", ""})
    public void innerText() {
        final String html
                = "<html><body>\n"
                + "  <table id='tab'>\n"
                + "    <tr><td>cell1</td></tr>\n"
                + "  </table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tab');\n"
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
    @Alerts({"cell1", "[object Text]", "abc", "[object Text]", ""})
    public void textContent() {
        final String html
                = "<html><body>\n"
                + "  <table id='tab'>\n"
                + "    <tr><td>cell1</td></tr>\n"
                + "  </table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tab');\n"
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

    @Test
    @Alerts({"undefined", "#667788", "unknown", "undefined", "undefined", "undefined"})
    public void borderColor() {
        final String html
                = "<html><body>\n"
                + "  <table id='tab1'></table>\n"
                + "  <table id='tab2' borderColor='red'></table>\n"
                + "  <table id='tab3' borderColor='#123456'></table>\n"
                + "  <table id='tab4' borderColor='unknown'></table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tab1');\n"
                + "  alert(node.borderColor);\n"
                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColor);\n"
                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColor);\n"
                + "  var node = document.getElementById('tab2');\n"
                + "  alert(node.borderColor);\n"
                + "  var node = document.getElementById('tab3');\n"
                + "  alert(node.borderColor);\n"
                + "  var node = document.getElementById('tab4');\n"
                + "  alert(node.borderColor);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined", "undefined", "undefined"})
    public void borderColorDark() {
        final String html
                = "<html><body>\n"
                + "  <table id='tab1'></table>\n"
                + "  <table id='tab2' borderColorDark='red'></table>\n"
                + "  <table id='tab3' borderColorDark='#123456'></table>\n"
                + "  <table id='tab4' borderColorDark='unknown'></table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tab1');\n"
                + "  alert(node.borderColorDark);\n"
                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColorDark);\n"
                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColorDark);\n"
                + "  var node = document.getElementById('tab2');\n"
                + "  alert(node.borderColorDark);\n"
                + "  var node = document.getElementById('tab3');\n"
                + "  alert(node.borderColorDark);\n"
                + "  var node = document.getElementById('tab4');\n"
                + "  alert(node.borderColorDark);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "undefined", "undefined", "undefined"})
    public void borderColorLight() {
        final String html
                = "<html><body>\n"
                + "  <table id='tab1'></table>\n"
                + "  <table id='tab2' borderColorLight='red'></table>\n"
                + "  <table id='tab3' borderColorLight='#123456'></table>\n"
                + "  <table id='tab4' borderColorLight='unknown'></table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tab1');\n"
                + "  alert(node.borderColorLight);\n"
                + "  node.borderColor = '#667788';\n"
                + "  alert(node.borderColorLight);\n"
                + "  node.borderColor = 'unknown';\n"
                + "  alert(node.borderColorLight);\n"
                + "  var node = document.getElementById('tab2');\n"
                + "  alert(node.borderColorLight);\n"
                + "  var node = document.getElementById('tab3');\n"
                + "  alert(node.borderColorLight);\n"
                + "  var node = document.getElementById('tab4');\n"
                + "  alert(node.borderColorLight);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "hello", "unknown", "exception", "", "test"})
    public void summary() {
        final String html
                = "<html><body>\n"
                + "  <table id='tab1'></table>\n"
                + "  <table id='tab2' summary=''></table>\n"
                + "  <table id='tab3' summary='test'></table>\n"
                + "    <script>\n"
                + "  var node = document.getElementById('tab1');\n"
                + "  alert(node.summary);\n"
                + "  node.summary = 'hello';\n"
                + "  alert(node.summary);\n"
                + "  node.summary = 'unknown';\n"
                + "  alert(node.summary);\n"
                + "  try { node.summary = unknown; } catch(e) { alert('exception') }\n"
                + "  var node = document.getElementById('tab2');\n"
                + "  alert(node.summary);\n"
                + "  var node = document.getElementById('tab3');\n"
                + "  alert(node.summary);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"none", "groups", "rows", "cols", "wrong", "null"})
    public void getRules() {
        final String html
                = "<html><body>\n"
                + "  <table id='t1' rules='none'></table>\n"
                + "  <table id='t2' rules='groups'></table>\n"
                + "  <table id='t3' rules='rows'></table>\n"
                + "  <table id='t4' rules='cols'></table>\n"
                + "  <table id='t5' rules='wrong'></table>\n"
                + "  <table id='t6'></table>\n"
                + "    <script>\n"
                + "  for (var i = 1; i <= 6; i++) {\n"
                + "    alert(document.getElementById('t' + i).rules);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"groUPs", "8", "foo", "rows", "cols"})
    public void setRules() {
        final String html
                = "<html><body>\n"
                + "  <table id='t1' rules='groups'></table>\n"
                + "    <script>\n"
                + "  function setRules(elem, value) {\n"
                + "    try {\n"
                + "      elem.rules = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.rules);\n"
                + "  }\n"
                + "  var elem = document.getElementById('t1');\n"
                + "  setRules(elem, 'groUPs');\n"
                + "  setRules(elem, '8');\n"
                + "  setRules(elem, 'foo');\n"
                + "  setRules(elem, 'rows');\n"
                + "  setRules(elem, 'cols');\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
