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
import org.loboevolution.html.node.Selection;

/**
 * Unit tests for {@link Selection}.
 */
@ExtendWith(AlertsExtension.class)
public class SelectionTest extends LoboUnitTest {


    @Test
    @Alerts("true")
    public void equality_selection() {
        final String html = "<html>\n"
                + "<body>\n"
                + "<script>"

                + "alert(document.selection==document.selection);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void equalityGetSelection() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "try {\n"
                + " alert(window.getSelection()==window.getSelection());\n"
                + "} catch (e) {alert('exception')}\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0", "0", "cdefg"})
    public void inputSelectionsAreIndependent() {
        final String html = "<html><body onload='test()'>\n"
                + "<input id='i' value='abcdefghi'/>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var i = document.getElementById('i');\n"
                + "    var s = window.getSelection();\n"
                + "   alert(s.rangeCount);\n"
                + "    i.selectionStart = 2;\n"
                + "   alert(s.rangeCount);\n"
                + "    i.selectionEnd = 7;\n"
                + "   alert(s.rangeCount);\n"
                + "   alert(i.value.substring(i.selectionStart, i.selectionEnd));\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1:null/0/null/0/true/None/0/",
            "2:s2/0/s2/1/false/Range/1/xyz[xyz"})
    public void selectAllChildren() {
        final String jsSnippet = "    alertSelection(selection);\n"
                + "    selection.selectAllChildren(s2);\n"
                + "    alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:s2/0/s2/1/false/Range/1/xyz[xyz",
            "2:s2/0/s3/1/false/Range/1/xyzfoo[xyzfoo",
            "3:s2/0/s3/2/false/Range/1/xyzfoo---[xyzfoo---",
            "4:s2/0/s3/3/false/Range/1/xyzfoo---foo[xyzfoo---foo"})
    public void extend() {
        final String jsSnippet = "    selection.selectAllChildren(s2);\n"
                + "    alertSelection(selection);\n"
                + "    if (selection.extend) {\n"
                + "      selection.extend(s3, 1);\n"
                + "      alertSelection(selection);\n"
                + "      selection.extend(s3, 2);\n"
                + "      alertSelection(selection);\n"
                + "      selection.extend(s3, 3);\n"
                + "      alertSelection(selection);\n"
                + "    } else {alert('selection.extend not available'); }\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:s2/0/s2/1/false/Range/1/xyz[xyz",
            "2:s2/0/s2/0/true/Caret/1/["})
    public void collapseToStart() {
        final String jsSnippet = "    selection.selectAllChildren(s2);\n"
                + "    alertSelection(selection);\n"
                + "    selection.collapseToStart();\n"
                + "    alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:s2/0/s2/1/false/Range/1/xyz[xyz",
            "2:s2/1/s2/1/true/Caret/1/["})
    public void collapseToEnd() {
        final String jsSnippet = "    selection.selectAllChildren(s2);\n"
                + "    alertSelection(selection);\n"
                + "    selection.collapseToEnd();\n"
                + "    alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:s2/0/s2/1/false/Range/1/xyz[xyz",
            "2:null/0/null/0/true/None/0/"})
    public void empty() {
        final String jsSnippet = "    selection.selectAllChildren(s2);\n"
                + "    alertSelection(selection);\n"
                + "    selection.empty();\n"
                + "    alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:null/0/null/0/true/None/0/",
            "2:null/0/null/0/true/None/0/",
            "3:s2/1/s3/1/false/Range/1/foo[foo"})
    public void addRange() {
        final String jsSnippet = "      alertSelection(selection);\n"
                + "      var range = document.createRange();\n"
                + "      range.setStart(s2, 1);\n"
                + "      range.setEnd(s3, 1);\n"
                + "      alertSelection(selection);\n"
                + "      selection.addRange(range);\n"
                + "      alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:null/0/null/0/true/None/0/",
            "2:s1/1/s3/1/false/Range/1/xyzfoo[xyzfoo",
            "3:null/0/null/0/true/None/0/"})
    public void removeAllRanges() {
        final String jsSnippet = "      alertSelection(selection);\n"
                + "      var range = document.createRange();\n"
                + "      range.setStart(s1, 1);\n"
                + "      range.setEnd(s3, 1);\n"
                + "      selection.addRange(range);\n"
                + "      alertSelection(selection);\n"
                + "      selection.removeAllRanges();\n"
                + "      alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:s1/1/s3/1/false/Range/1/xyzfoo[xyzfoo",
            "2:null/0/null/0/true/None/0/"})
    public void removeAllRanges2() {
        final String jsSnippet = "      var range = document.createRange();\n"
                + "      range.setStart(s1, 1);\n"
                + "      range.setEnd(s3, 1);\n"
                + "      selection.addRange(range);\n"
                + "      alertSelection(selection);\n"
                + "      selection.removeAllRanges();\n"
                + "      alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:null/0/null/0/true/None/0/",
            "2:s1/0/s1/1/false/Range/1/abc[abc",
            "3:null/0/null/0/true/None/0/"})
    public void selectAllChildrenAddRange() {
        final String jsSnippet = "      alertSelection(selection);\n"
                + "      selection.selectAllChildren(s1);\n"
                + "      var range = document.createRange();\n"
                + "      range.setStart(s1, 1);\n"
                + "      range.setEnd(s3, 1);\n"
                + "      selection.addRange(range);\n"
                + "      alertSelection(selection);\n"
                + "      selection.removeAllRanges();\n"
                + "      alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:null/0/null/0/true/None/0/",
            "2:s1/0/s1/1/false/Range/1/abc[abc",
            "3:null/0/null/0/true/None/0/"})
    public void addRangeSelectAllChildren() {
        final String jsSnippet = "      alertSelection(selection);\n"
                + "      var range = document.createRange();\n"
                + "      range.setStart(s1, 1);\n"
                + "      range.setEnd(s3, 1);\n"
                + "      selection.addRange(range);\n"
                + "      selection.selectAllChildren(s1);\n"
                + "      alertSelection(selection);\n"
                + "      selection.removeAllRanges();\n"
                + "      alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:null/0/null/0/true/None/0/",
            "2:s1/0/s1/1/false/Range/1/abc[abc",
            "3:s1/0/s1/1/false/Range/1/abc[abc",
            "4:null/0/null/0/true/None/0/"})
    public void addRangeAddRange() {
        final String jsSnippet = "alertSelection(selection);\n"
                + "      selection.selectAllChildren(s1);\n"
                + "      var range = document.createRange();\n"
                + "      range.setStart(s1, 1);\n"
                + "      range.setEnd(s2, 1);\n"
                + "      selection.addRange(range);\n"
                + "      alertSelection(selection);\n"
                + "      var range = document.createRange();\n"
                + "      range.setStart(s2, 1);\n"
                + "      range.setEnd(s3, 3);\n"
                + "      selection.addRange(range);\n"
                + "      alertSelection(selection);\n"
                + "      selection.removeAllRanges();\n"
                + "      alertSelection(selection);\n";

        tester(jsSnippet);
    }

    @Test
    @Alerts({"1:[object Text]/1/[object Text]/2/false/Range/1/yzfo[yzfo",
            "2:null/0/null/0/true/None/0/",
            "false", "true"})
    public void aLittleBitOfEverythingRemoveRange() {
        final String jsSnippet = "    var range = document.createRange();\n"
                + "    range.setStart(s2.firstChild, 1);\n"
                + "    range.setEnd(s3.firstChild, 2);\n"
                + "    selection.addRange(range);\n"
                + "    alertSelection(selection);\n"
                + "    selection.removeRange(range);\n"
                + "    alertSelection(selection);\n"
                + "   alert(range.collapsed);\n"
                + "    selection.addRange(range);\n"
                + "   alert(selection.getRangeAt(0) == selection.getRangeAt(0));\n";

        tester(jsSnippet);
    }

    private void tester(final String jsSnippet) {
        final String html = "<html>\n"
                + "<body onload='test()'>\n"
                + "  <span id='s1'>abc</span><span id='s2'>xyz</span><span id='s3'>foo<span>---</span>foo</span>\n"
                + "<script>\n"
                + "  var x = 1;\n"
                + "  function test() {\n"
                + "    var selection = window.getSelection();\n"
                + "    var s1 = document.getElementById('s1');\n"
                + "    var s2 = document.getElementById('s2');\n"
                + "    var s3 = document.getElementById('s3');\n"
                + "    try {\n"
                + jsSnippet
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "  function alertSelection(s) {\n"
                + "    var anchorNode = (s.anchorNode && s.anchorNode.id ? s.anchorNode.id : s.anchorNode);\n"
                + "    var focusNode = (s.focusNode && s.focusNode.id ? s.focusNode.id : s.focusNode);\n"
                + "    var msg = (x++) + ':' + anchorNode + '/' + s.anchorOffset + '/' + focusNode + '/' +\n"
                + "       s.focusOffset + '/' + s.isCollapsed + '/' + s.type + '/' + s.rangeCount + '/' + s;\n"
                + "    for(var i = 0; i < s.rangeCount; i++) {\n"
                + "      msg += '[' + s.getRangeAt(i);\n"
                + "    }\n"
                + "   alert(msg);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "null-0", "", "null-0", "", "null-0", "", "null-0"})
    public void getSelectionDisplay() {
        final String html = "<html>\n"
                + "<body onload='test()'>\n"
                + "  <iframe id='frame1' src='about:blank'></iframe>\n"
                + "  <iframe id='frame2' src='about:blank' style='display: none'></iframe>\n"
                + "  <div style='display: none'>\n"
                + "    <iframe id='frame3' src='about:blank'></iframe>\n"
                + "  </div>\n"
                + "  <script>\n"
                + "    function sel(win) {\n"
                + "      if (win.getSelection) {\n"
                + "        var range = win.getSelection();\n"
                + "       alert(range);\n"
                + "        if (range) {\n"
                + "         alert(range.anchorNode + '-' + range.rangeCount);\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "    function test() {\n"
                + "      sel(window);\n"
                + "      sel(document.getElementById('frame1').contentWindow);\n"
                + "      sel(document.getElementById('frame2').contentWindow);\n"
                + "      sel(document.getElementById('frame3').contentWindow);\n"
                + "    }\n"
                + "  </script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", ""})
    public void testToString() {
        final String html = "<html>\n"
                + "<body onload='test()'>\n"
                + "<input id='i' value='abcdefghi'/>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var i = document.getElementById('i');\n"
                + "    var s = window.getSelection();\n"
                + "   alert(s);\n"
                + "   alert('' + s);\n"
                + "   alert(s.toString());\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }
}
