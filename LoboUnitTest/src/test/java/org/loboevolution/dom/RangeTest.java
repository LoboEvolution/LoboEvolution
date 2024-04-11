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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Range;

/**
 * Tests for {@link Range}.
 */
@ExtendWith(AlertsExtension.class)
public class RangeTest extends LoboUnitTest {

    private static final String contentStart = "<html><head><title></title>\n"
            + "<script>\n"
            + "function safeTagName(o) {\n"
            + "  return o ? (o.tagName ? o.tagName : o) : undefined;\n"
            + "}\n"
            + "function alertRange(r) {\n"
            + " alert(r.collapsed);\n"
            + " alert(safeTagName(r.commonAncestorContainer));\n"
            + " alert(safeTagName(r.startContainer));\n"
            + " alert(r.startOffset);\n"
            + " alert(safeTagName(r.endContainer));\n"
            + " alert(r.endOffset);\n"
            + "}\n"
            + "function test() {\n"
            + "  var r = document.createRange();\n";

    private static final String contentEnd = "\n}\n</script></head>\n"
            + "<body onload='test()'>\n"
            + "<div id='theDiv'>Hello, <span id='theSpan'>this is a test for"
            + "<a id='theA' href='http://htmlunit.sf.net'>HtmlUnit</a> support"
            + "</div>\n"
            + "<p id='theP'>for Range</p>\n"
            + "</body></html>";


    @Test
    @Alerts({"true", "[object HTMLDocument]", "[object HTMLDocument]", "0", "[object HTMLDocument]", "0"})
    public void emptyRange() {
        checkHtmlAlert(contentStart + "alertRange(r);" + contentEnd);
    }

    @Test
    @Alerts({"false", "BODY", "BODY", "1", "BODY", "2"})
    public void selectNode() {
        final String script = "r.selectNode(document.getElementById('theDiv'));"
                + "alertRange(r);";

        checkHtmlAlert(contentStart + script + contentEnd);
    }

    @Test
    @Alerts({"false", "DIV", "DIV", "0", "DIV", "2"})
    public void selectNodeContents() {
        final String script = "r.selectNodeContents(document.getElementById('theDiv'));"
                + "alertRange(r);";

        checkHtmlAlert(contentStart + script + contentEnd);
    }

    @Test
    @Alerts("<div id=\"myDiv2\"></div><div>harhar</div><div id=\"myDiv3\"></div>")
    public void createContextualFragment() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var element = document.getElementById('myDiv2');\n"
                + "    var range = element.ownerDocument.createRange();\n"
                + "    range.setStartAfter(element);\n"
                + "    var fragment = range.createContextualFragment('<div>harhar</div>');\n"
                + "    element.parentNode.insertBefore(fragment, element.nextSibling);\n"
                + "   alert(element.parentNode.innerHTML);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv'><div id='myDiv2'></div><div id='myDiv3'></div></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Text]", "[object HTMLTableRowElement]"})
    public void createContextualFragment2() {
        final String html = "<html><body>\n"
                + "<div id ='d'></div>\n"
                + "<table><tr id='t'><td>old</td></tr></table>\n"
                + "<script>\n"
                + "function test(id) {\n"
                + "  var element = document.getElementById(id);\n"
                + "  var range = element.ownerDocument.createRange();\n"
                + "  range.selectNode(element);\n"
                + "  var str = '<tr>  <td>new</td></tr>';\n"
                + "  var fragment = range.createContextualFragment(str);\n"
                + " alert(fragment.firstChild);\n"
                + "}\n"
                + "try {\n"
                + "  test('d');\n"
                + "  test('t');\n"
                + "} catch (e) {alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"qwerty", "tyxy", "[object DocumentFragment]", "[object HTMLSpanElement] [object Text]", "qwer",
            "[object HTMLSpanElement]"})
    public void extractContents() {
        final String html =
                "<html><body><div id='d'>abc<span id='s'>qwerty</span>xyz</div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var s = document.getElementById('s');\n"
                        + "  var r = document.createRange();\n"
                        + "  r.setStart(s.firstChild, 4);\n"
                        + "  r.setEnd(d.childNodes[2], 2);\n"
                        + " alert(s.innerHTML);\n"
                        + " alert(r);\n"
                        + "  var fragment = r.extractContents();\n"
                        + " alert(fragment);\n"
                        + " alert(fragment.childNodes[0] + ' ' + fragment.childNodes[1]);\n"
                        + " alert(s.innerHTML);\n"
                        + " alert(document.getElementById('s'));\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1 <p><b id=\"b\">text1<span id=\"s\">inner</span>text2</b></p>",
            "2 text1",
            "3 [object DocumentFragment]",
            "4 1: [object HTMLParagraphElement]: <b id=\"b\">text1</b>",
            "5 <p><b id=\"b\"><span id=\"s\">inner</span>text2</b></p>",
            "6 1: [object HTMLParagraphElement]: <b id=\"b\"><span id=\"s\"></span>text2</b>",
            "7 <p><b id=\"b\"><span id=\"s\">inner</span></b></p>"})
    public void extractContents2() {
        final String html =
                "<html><body><div id='d'><p><b id='b'>text1<span id='s'>inner</span>text2</b></p></div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var b = document.getElementById('b');\n"
                        + "  var s = document.getElementById('s');\n"
                        + "  var r = document.createRange();\n"
                        + "  r.setStart(d, 0);\n"
                        + "  r.setEnd(b, 1);\n"
                        + " alert('1 ' + d.innerHTML);\n"
                        + " alert('2 ' + r);\n"
                        + "  var f = r.extractContents();\n"
                        + " alert('3 ' + f);\n"
                        + " alert('4 ' + f.childNodes.length + ': ' + f.childNodes[0] + ': ' + f.childNodes[0].innerHTML);\n"
                        + " alert('5 ' + d.innerHTML);\n"
                        + "  var r2 = document.createRange();\n"
                        + "  r2.setStart(s, 1);\n"
                        + "  r2.setEnd(d, 1);\n"
                        + "  var f2 = r2.extractContents();\n"
                        + " alert('6 ' + f2.childNodes.length + ': ' + f2.childNodes[0] + ': ' + f2.childNodes[0].innerHTML);\n"
                        + " alert('7 ' + d.innerHTML);\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "1", "2", "3"})
    public void constants() {
        final String html =
                "<html><body>\n"
                        + "<script>\n"
                        + " alert(Range.START_TO_START);\n"
                        + " alert(Range.START_TO_END);\n"
                        + " alert(Range.END_TO_END);\n"
                        + " alert(Range.END_TO_START);\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"-1", "1", "1", "-1", "0"})
    public void compareBoundaryPoints() {
        final String html = "<html><body>\n"
                + "<div id='d1'><div id='d2'></div></div>\n"
                + "<script>\n"
                + "  var range = document.createRange();\n"
                + "  range.selectNode(document.getElementById('d1'));\n"
                + "  var sourceRange = document.createRange();\n"
                + "  sourceRange.selectNode(document.getElementById('d2'));\n"
                + " alert(range.compareBoundaryPoints(Range.START_TO_START, sourceRange));\n"
                + " alert(range.compareBoundaryPoints(Range.START_TO_END, sourceRange));\n"
                + " alert(range.compareBoundaryPoints(Range.END_TO_END, sourceRange));\n"
                + " alert(range.compareBoundaryPoints(Range.END_TO_START, sourceRange));\n"
                + " alert(range.compareBoundaryPoints(Range.START_TO_START, range));\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"abcd", "bc", "null", "null", "ad", "bc"})
    public void extractContents3() {
        final String html =
                "<html><body><div id='d'><span id='a'>a</span><span id='b'>b</span>"
                        + "<span id='c'>c</span><span id='d'>d</span></div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var s = document.getElementById('s');\n"
                        + "  var r = document.createRange();\n"
                        + "  r.setStart(d, 1);\n"
                        + "  r.setEnd(d, 3);\n"
                        + " alert(d.textContent);\n"
                        + " alert(r.toString());\n"
                        + "  var x = r.extractContents();\n"
                        + " alert(document.getElementById('b'));\n"
                        + " alert(document.getElementById('c'));\n"
                        + " alert(d.textContent);\n"
                        + " alert(x.textContent);\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"qwerty", "tyxy", "[object DocumentFragment]", "[object HTMLSpanElement] [object Text]",
            "qwerty", "[object HTMLSpanElement]"})
    public void cloneContents() {
        final String html =
                "<html><body><div id='d'>abc<span id='s'>qwerty</span>xyz</div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var s = document.getElementById('s');\n"
                        + "  var r = document.createRange();\n"
                        + "  r.setStart(s.firstChild, 4);\n"
                        + "  r.setEnd(d.childNodes[2], 2);\n"
                        + " alert(s.innerHTML);\n"
                        + " alert(r);\n"
                        + "  var fragment = r.cloneContents();\n"
                        + " alert(fragment);\n"
                        + " alert(fragment.childNodes[0] + ' ' + fragment.childNodes[1]);\n"
                        + " alert(s.innerHTML);\n"
                        + " alert(document.getElementById('s'));\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"qwerty", "bcqwertyxy", "null", "az"})
    public void deleteContents() {
        final String html =
                "<html><body><div id='d'>abc<span id='s'>qwerty</span>xyz</div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var s = document.getElementById('s');\n"
                        + "  var r = document.createRange();\n"
                        + "  r.setStart(d.firstChild, 1);\n"
                        + "  r.setEnd(d.childNodes[2], 2);\n"
                        + " alert(s.innerHTML);\n"
                        + " alert(r.toString());\n"
                        + "  r.deleteContents();\n"
                        + " alert(document.getElementById('s'));\n"
                        + " alert(d.textContent);\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"abcd", "bc", "null", "null", "ad"})
    public void deleteContents2() {
        final String html =
                "<html><body><div id='d'><span id='a'>a</span><span id='b'>b</span><span id='c'>c</span>"
                        + "<span id='d'>d</span></div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var s = document.getElementById('s');\n"
                        + "  var r = document.createRange();\n"
                        + "  r.setStart(d, 1);\n"
                        + "  r.setEnd(d, 3);\n"
                        + " alert(d.textContent);\n"
                        + " alert(r.toString());\n"
                        + "  r.deleteContents();\n"
                        + " alert(document.getElementById('b'));\n"
                        + " alert(document.getElementById('c'));\n"
                        + " alert(d.textContent);\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void getClientRectsEmpty() {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "  <div id='d'>a</div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var r = document.createRange();\n"
                        + " alert(r.getClientRects().length);\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>\n";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void getClientRectsMany() {
        final String html =
                "<html><body><div id='d'><span id='a'>a</span><span id='b'>b</span><span id='c'>c</span>"
                        + "<span id='d'>d</span></div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var s = document.getElementById('s');\n"
                        + "  var r = document.createRange();\n"
                        + "  r.setStart(d, 1);\n"
                        + "  r.setEnd(d, 3);\n"
                        + " alert(r.getClientRects().length > 1);\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLBodyElement]")
    public void getBoundingClientRectDoesNotChangeTheParent() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var range = document.createRange();\n"

                + "  var elem = document.createElement('boundtest');\n"
                + "  document.body.appendChild(elem);\n"

                + "  range.selectNode(elem);\n"
                + "  range.getBoundingClientRect();\n"

                + " alert(elem.parentNode);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLBodyElement]")
    public void getClientRectsDoesNotChangeTheParent() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function doTest() {\n"
                + "  var range = document.createRange();\n"

                + "  var elem = document.createElement('boundtest');\n"
                + "  document.body.appendChild(elem);\n"

                + "  range.selectNode(elem);\n"
                + "  range.getClientRects();\n"

                + " alert(elem.parentNode);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"tyxy", "tyxy", "tyxy"})
    public void testToString() {
        final String html =
                "<html><body><div id='d'>abc<span id='s'>qwerty</span>xyz</div>\n"
                        + "<script>\n"
                        + "  var d = document.getElementById('d');\n"
                        + "  var s = document.getElementById('s');\n"
                        + "  var r = document.createRange();\n"
                        + "  r.setStart(s.firstChild, 4);\n"
                        + "  r.setEnd(d.childNodes[2], 2);\n"
                        + " alert(r);\n"
                        + " alert('' + r);\n"
                        + " alert(r.toString());\n"
                        + "</script></body></html>";
        checkHtmlAlert(html);
    }
}
