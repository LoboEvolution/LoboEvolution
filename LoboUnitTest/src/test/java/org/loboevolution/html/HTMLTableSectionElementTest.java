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
import org.loboevolution.html.dom.HTMLTableSectionElement;

/**
 * Tests for {@link HTMLTableSectionElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTableSectionElementTest extends LoboUnitTest {

    @Test
    @Alerts({"", "hello", "left", "hi", "right"})
    public void alignThead() {
        align("th");
    }

    @Test
    @Alerts({"", "hello", "left", "hi", "right"})
    public void alignTbody() {
        align("tb");
    }

    @Test
    @Alerts({"", "hello", "left", "hi", "right"})
    public void alignTfoot() {
        align("tf");
    }

    private void align(final String id) {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var t = document.getElementById('" + id + "');\n"
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
    @Alerts({"top", "baseline", "3", "middle", "8", "BOTtom"})
    public void vAlignThead() {
        vAlign("th");
    }

    @Test
    @Alerts({"top", "baseline", "3", "middle", "8", "BOTtom"})
    public void vAlignTbody() {
        vAlign("tb");
    }

    @Test
    @Alerts({"top", "baseline", "3", "middle", "8", "BOTtom"})
    public void vAlignTfoot() {
        vAlign("tf");
    }

    private void vAlign(final String id) {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var t1 = document.getElementById('" + id + "1');\n"
                        + "        var t2 = document.getElementById('" + id + "2');\n"
                        + "        var t3 = document.getElementById('" + id + "3');\n"
                        + "        alert(t1.vAlign);\n"
                        + "        alert(t2.vAlign);\n"
                        + "        alert(t3.vAlign);\n"
                        + "        set(t1, 'middle');\n"
                        + "        set(t2, 8);\n"
                        + "        set(t3, 'BOTtom');\n"
                        + "        alert(t1.vAlign);\n"
                        + "        alert(t2.vAlign);\n"
                        + "        alert(t3.vAlign);\n"
                        + "      }\n"
                        + "      function set(e, value) {\n"
                        + "        try {\n"
                        + "          e.vAlign = value;\n"
                        + "        } catch (e) {\n"
                        + "          alert('error');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <table id='t1'>\n"
                        + "      <thead id='th1' valign='top'/>\n"
                        + "      <tbody id='tb1' valign='top'/>\n"
                        + "      <tfoot id='tf1' valign='top'/>\n"
                        + "    </table>\n"
                        + "    <table id='t2'>\n"
                        + "      <thead id='th2' valign='baseline'/>\n"
                        + "      <tbody id='tb2' valign='baseline'/>\n"
                        + "      <tfoot id='tf2' valign='baseline'/>\n"
                        + "    </table>\n"
                        + "    <table id='t3'>\n"
                        + "      <thead id='th3' valign='3'/>\n"
                        + "      <tbody id='tb3' valign='3'/>\n"
                        + "      <tfoot id='tf3' valign='3'/>\n"
                        + "    </table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"p", "po", "", "u", "8", "U8"})
    public void chThead() {
        ch("th");
    }

    @Test
    @Alerts({"p", "po", "", "u", "8", "U8"})
    public void chTbody() {
        ch("tb");
    }

    @Test
    @Alerts({"p", "po", "", "u", "8", "U8"})
    public void chTfoot() {
        ch("tf");
    }

    private void ch(final String id) {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var t1 = document.getElementById('" + id + "1');\n"
                        + "        var t2 = document.getElementById('" + id + "2');\n"
                        + "        var t3 = document.getElementById('" + id + "3');\n"
                        + "        alert(t1.ch);\n"
                        + "        alert(t2.ch);\n"
                        + "        alert(t3.ch);\n"
                        + "        set(t1, 'u');\n"
                        + "        set(t2, 8);\n"
                        + "        set(t3, 'U8');\n"
                        + "        alert(t1.ch);\n"
                        + "        alert(t2.ch);\n"
                        + "        alert(t3.ch);\n"
                        + "      }\n"
                        + "      function set(e, value) {\n"
                        + "        try {\n"
                        + "          e.ch = value;\n"
                        + "        } catch (e) {\n"
                        + "          alert('error');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <table id='t1'>\n"
                        + "      <thead id='th1' char='p'/>\n"
                        + "      <tbody id='tb1' char='p'/>\n"
                        + "      <tfoot id='tf1' char='p'/>\n"
                        + "    </table>\n"
                        + "    <table id='t2'>\n"
                        + "      <thead id='th2' char='po'/>\n"
                        + "      <tbody id='tb2' char='po'/>\n"
                        + "      <tfoot id='tf2' char='po'/>\n"
                        + "    </table>\n"
                        + "    <table id='t3'>\n"
                        + "      <thead id='th3'/>\n"
                        + "      <tbody id='tb3'/>\n"
                        + "      <tfoot id='tf3'/>\n"
                        + "    </table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "4", "", "5.2", "-3", "abc"})
    public void chOffThead() {
        chOff("th");
    }

    @Test
    @Alerts({"0", "4", "", "5.2", "-3", "abc"})
    public void chOffTbody() {
        chOff("tb");
    }

    @Test
    @Alerts({"0", "4", "", "5.2", "-3", "abc"})
    public void chOffTfoot() {
        chOff("tf");
    }

    private void chOff(final String id) {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var t1 = document.getElementById('" + id + "1');\n"
                        + "        var t2 = document.getElementById('" + id + "2');\n"
                        + "        var t3 = document.getElementById('" + id + "3');\n"
                        + "        alert(t1.chOff);\n"
                        + "        alert(t2.chOff);\n"
                        + "        alert(t3.chOff);\n"
                        + "        set(t1, '5.2');\n"
                        + "        set(t2, -3);\n"
                        + "        set(t3, 'abc');\n"
                        + "        alert(t1.chOff);\n"
                        + "        alert(t2.chOff);\n"
                        + "        alert(t3.chOff);\n"
                        + "      }\n"
                        + "      function set(e, value) {\n"
                        + "        try {\n"
                        + "          e.chOff = value;\n"
                        + "        } catch (e) {\n"
                        + "          alert('error');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <table id='t1'>\n"
                        + "      <thead id='th1' charoff='0'/>\n"
                        + "      <tbody id='tb1' charoff='0'/>\n"
                        + "      <tfoot id='tf1' charoff='0'/>\n"
                        + "    </table>\n"
                        + "    <table id='t2'>\n"
                        + "      <thead id='th2' charoff='4'/>\n"
                        + "      <tbody id='tb2' charoff='4'/>\n"
                        + "      <tfoot id='tf2' charoff='4'/>\n"
                        + "    </table>\n"
                        + "    <table id='t3'>\n"
                        + "      <thead id='th3'/>\n"
                        + "      <tbody id='tb3'/>\n"
                        + "      <tfoot id='tf3'/>\n"
                        + "    </table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<tr><td>world</td></tr>")
    public void TBODY_innerHTML() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var t = document.getElementById('myId');\n"
                + "    try {\n"
                + "      t.innerHTML = '<tr><td>world</td></tr>';\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "    alert(t.innerHTML.toLowerCase());\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <table>\n"
                + "    <tbody id='myId'><tr><td>hello</td></tr></tbody>\n"
                + "  </table>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""})
    public void innerTextBody() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tbody id='tab_row'><tr><td>cell1</td></tr></tbody>\n"
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
    @Alerts({"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""})
    public void innerTextHeader() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <thead id='tab_row'><tr><td>cell1</td></tr></thead>\n"
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
    @Alerts({"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""})
    public void innerText_footer() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tfoot id='tab_row'><tr><td>cell1</td></tr></tfoot>\n"
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
    @Alerts({"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""})
    public void textContentBody() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tbody id='tab_row'><tr><td>cell1</td></tr></tbody>\n"
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

    @Test
    @Alerts({"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""})
    public void textContentHeader() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <thead id='tab_row'><tr><td>cell1</td></tr></thead>\n"
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

    @Test
    @Alerts({"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""})
    public void textContentFooter() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tfoot id='tab_row'><tr><td>cell1</td></tr></tfoot>\n"
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

    @Test
    @Alerts({"undefined", "#0000aa", "x"})
    public void bgColorFooter() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var tfoot = document.getElementById('tfoot');\n"
                        + "        alert(tfoot.bgColor);\n"
                        + "        tfoot.bgColor = '#0000aa';\n"
                        + "        alert(tfoot.bgColor);\n"
                        + "        tfoot.bgColor = 'x';\n"
                        + "        alert(tfoot.bgColor);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><tfoot id='tfoot'><tr><td>cell1</td></tr></tfoot></table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "#0000aa", "x"})
    public void bgColorHeader() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var thead = document.getElementById('thead');\n"
                        + "        alert(thead.bgColor);\n"
                        + "        thead.bgColor = '#0000aa';\n"
                        + "        alert(thead.bgColor);\n"
                        + "        thead.bgColor = 'x';\n"
                        + "        alert(thead.bgColor);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><thead id='thead'><tr><td>cell1</td></tr></thead></table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<thead id=\"thead\"><tr><td>cell1</td></tr></thead>", "new"})
    public void outerHTML() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.getElementById('thead').outerHTML);\n"
                        + "        document.getElementById('thead').outerHTML = '<div id=\"new\">text<div>';\n"
                        + "        alert(document.getElementById('new').id);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table><thead id='thead'><tr><td>cell1</td></tr></thead></table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
