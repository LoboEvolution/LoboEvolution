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
 * Tests for {@link org.loboevolution.html.dom.HTMLTableSectionElement}.
 */
public class HTMLTableSectionElementTest extends LoboUnitTest {

    /**
     * <p>align_thead.</p>
     */
    @Test
    public void align_thead() {
        String[] messages = {null, "hello", "left", "hi", "right"};
        align("th", messages);
    }

    /**
     * <p>align_tbody.</p>
     */
    @Test
    public void align_tbody() {
        String[] messages = {null, "hello", "left", "hi", "right"};
        align("tb", messages);
    }

    /**
     * <p>align_tfoot.</p>
     */
    @Test
    public void align_tfoot() {
        String[] messages = {null, "hello", "left", "hi", "right"};
        align("tf", messages);
    }

    private void align(final String id, final String[] messages) {
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

        checkHtmlAlert(html, messages);
    }

    /**
     * <p>vAlign_thead.</p>
     */
    @Test
    public void vAlign_thead() {
        String[] messages = {"top", "baseline", "3", "middle", "8", "BOTtom"};
        vAlign("th", messages);
    }

    /**
     * <p>vAlign_tbody.</p>
     */
    @Test
    public void vAlign_tbody() {
        String[] messages = {"top", "baseline", "3", "middle", "8", "BOTtom"};
        vAlign("tb", messages);
    }

    /**
     * <p>vAlign_tfoot.</p>
     */
    @Test
    public void vAlign_tfoot() {
        String[] messages = {"top", "baseline", "3", "middle", "8", "BOTtom"};
        vAlign("tf", messages);
    }

    private void vAlign(final String id, final String[] messages) {
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

        checkHtmlAlert(html, messages);
    }

    /**
     * <p>ch_thead.</p>
     */
    @Test
    public void ch_thead() {
        String[] messages = {"p", "po", "", "u", "8", "U8"};
        ch("th", messages);
    }

    /**
     * <p>ch_tbody.</p>
     */
    @Test
    public void ch_tbody() {
        String[] messages = {"p", "po", "", "u", "8", "U8"};
        ch("tb", messages);
    }

    /**
     * <p>ch_tfoot.</p>
     */
    @Test
    public void ch_tfoot() {
        String[] messages = {"p", "po", "", "u", "8", "U8"};
        ch("tf", messages);
    }

    private void ch(final String id, final String[] messages) {
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

        checkHtmlAlert(html, messages);
    }

    /**
     * <p>chOff_thead.</p>
     */
    @Test
    public void chOff_thead() {
        String[] messages = {"0", "4", "", "5.2", "-3", "abc"};
        chOff("th", messages);
    }

    /**
     * <p>chOff_tbody.</p>
     */
    @Test
    public void chOff_tbody() {
        String[] messages = {"0", "4", "", "5.2", "-3", "abc"};
        chOff("tb", messages);
    }

    /**
     * <p>chOff_tfoot.</p>
     */
    @Test
    public void chOff_tfoot() {
        String[] messages = {"0", "4", "", "5.2", "-3", "abc"};
        chOff("tf", messages);
    }

    private void chOff(final String id, final String[] messages) {
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

        checkHtmlAlert(html, messages);
    }

    /**
     * <p>TBODY_innerHTML.</p>
     */
    @Test
    public void TBODY_innerHTML() {
        final String html = "<html><head>\n"
                + "<script>\n"
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

        final String[] messages = {"<tr><td>world</td></tr>"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>innerText_body.</p>
     */
    @Test
    public void innerText_body() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tbody id='tab_row'><tr><td>cell1</td></tr></tbody>\n"
                + "  </table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tab_row');\n"
                + "  alert(node.innerText);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.innerText = 'abc'; } catch(e) {alert('ex');}\n"
                + "  alert(node.innerText);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.innerText = ''; } catch(e) {alert('ex');}\n"
                + "  alert(node.innerText);\n"
                + "</script></body></html>";

        final String[] messages = {"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>innerText_header.</p>
     */
    @Test
    public void innerText_header() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <thead id='tab_row'><tr><td>cell1</td></tr></thead>\n"
                + "  </table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tab_row');\n"
                + "  alert(node.innerText);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.innerText = 'abc'; } catch(e) {alert('ex');}\n"
                + "  alert(node.innerText);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.innerText = ''; } catch(e) {alert('ex');}\n"
                + "  alert(node.innerText);\n"
                + "</script></body></html>";

        final String[] messages = {"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>innerText_footer.</p>
     */
    @Test
    public void innerText_footer() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tfoot id='tab_row'><tr><td>cell1</td></tr></tfoot>\n"
                + "  </table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tab_row');\n"
                + "  alert(node.innerText);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.innerText = 'abc'; } catch(e) {alert('ex');}\n"
                + "  alert(node.innerText);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.innerText = ''; } catch(e) {alert('ex');}\n"
                + "  alert(node.innerText);\n"
                + "</script></body></html>";

        final String[] messages = {"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>textContent_body.</p>
     */
    @Test
    public void textContent_body() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tbody id='tab_row'><tr><td>cell1</td></tr></tbody>\n"
                + "  </table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tab_row');\n"
                + "  alert(node.textContent);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.textContent = 'abc'; } catch(e) {alert('ex');}\n"
                + "  alert(node.textContent);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.textContent = ''; } catch(e) {alert('ex');}\n"
                + "  alert(node.textContent);\n"
                + "</script></body></html>";

        final String[] messages = {"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>textContent_header.</p>
     */
    @Test
    public void textContent_header() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <thead id='tab_row'><tr><td>cell1</td></tr></thead>\n"
                + "  </table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tab_row');\n"
                + "  alert(node.textContent);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.textContent = 'abc'; } catch(e) {alert('ex');}\n"
                + "  alert(node.textContent);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.textContent = ''; } catch(e) {alert('ex');}\n"
                + "  alert(node.textContent);\n"
                + "</script></body></html>";

        final String[] messages = {"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>textContent_footer.</p>
     */
    @Test
    public void textContent_footer() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <tfoot id='tab_row'><tr><td>cell1</td></tr></tfoot>\n"
                + "  </table>\n"
                + "<script>\n"
                + "  var node = document.getElementById('tab_row');\n"
                + "  alert(node.textContent);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.textContent = 'abc'; } catch(e) {alert('ex');}\n"
                + "  alert(node.textContent);\n"
                + "  alert(node.firstChild);\n"

                + "  try { node.textContent = ''; } catch(e) {alert('ex');}\n"
                + "  alert(node.textContent);\n"
                + "</script></body></html>";

        final String[] messages = {"cell1", "[object HTMLTableRowElement]", "abc", "[object Text]", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>bgColorFooter.</p>
     */
    @Test
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

        final String[] messages = {"undefined", "#0000aa", "x"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>bgColorHeader.</p>
     */
    @Test
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

        final String[] messages = {"undefined", "#0000aa", "x"};
        checkHtmlAlert(html, messages);
    }
}
