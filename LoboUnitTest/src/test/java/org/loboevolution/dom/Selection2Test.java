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
public class Selection2Test extends LoboUnitTest {


    @Test
    @Alerts({"", "cx"})
    public void stringValue() {
        test("", "selection", "x");
    }

    @Test
    @Alerts({"null", "s1"})
    public void anchorNode() {
        test("", "selection.anchorNode", "x ? x.parentNode.id : x");
    }

    @Test
    @Alerts({"0", "2"})
    public void anchorOffset() {
        test("", "selection.anchorOffset", "x");
    }

    @Test
    @Alerts({"null", "s2"})
    public void focusNode() {
        test("", "selection.focusNode", "x ? x.parentNode.id : x");
    }

    @Test
    @Alerts({"0", "1"})
    public void focusOffset() {
        test("", "selection.focusOffset", "x");
    }

    @Test
    @Alerts({"true", "false"})
    public void isCollapsed() {
        test("", "selection.isCollapsed", "x");
    }

    @Test
    @Alerts({"0", "1"})
    public void rangeCount() {
        test("", "selection.rangeCount", "x");
    }

    @Test
    @Alerts({"0", "1"})
    public void rangeCount2() {
        test("try{selection.collapseToEnd()}catch(e){alert('exception')}", "selection.rangeCount", "x");
    }

    @Test
    @Alerts({"null", "null"})
    public void removeAllRangesAnchorNode() {
        test("try{selection.removeAllRanges()}catch(e){alert('exception')}",
                "selection.anchorNode", "x ? x.parentNode.id : x");
    }

    @Test
    @Alerts({"0", "0"})
    public void removeAllRangesAnchorOffset() {
        test("try{selection.removeAllRanges()}catch(e){alert('exception')}",
                "selection.anchorOffset", "x ? x.parentNode.id : x");
    }

    @Test
    @Alerts({"null", "null"})
    public void removeAllRanges_focusNode() {
        test("try{selection.removeAllRanges()}catch(e){alert('exception')}",
                "selection.focusNode", "x ? x.parentNode.id : x");
    }

    @Test
    @Alerts({"0", "0"})
    public void removeAllRanges_focusOffset() {
        test("try{selection.removeAllRanges()}catch(e){alert('exception')}",
                "selection.focusOffset", "x ? x.parentNode.id : x");
    }

    @Test
    @Alerts({"null", "s1"})
    public void collapse() {
        test("try{selection.collapse(s1, 1)}catch(e){alert('exception')}",
                "selection.focusNode", "x ? x.id : x");
    }

    @Test
    @Alerts({"null", "s2"})
    public void collapseToEnd() {
        test("try{selection.collapseToEnd()}catch(e){alert('exception')}",
                "selection.anchorNode", "x ? x.parentNode.id : x");
    }

    @Test
    @Alerts({"null", "s1"})
    public void collapseToStart() {
        test("try{selection.collapseToStart()}catch(e){alert('exception')}",
                "selection.focusNode", "x ? x.parentNode.id : x");
    }

    @Test
    @Alerts({"0", "1"})
    public void extend() {
        test("try{selection.extend(s2, 1)}catch(e){alert('exception')}", "selection.focusOffset", "x");
    }

    @Test
    @Alerts({"0", "0"})
    public void selectAllChildren() {
        test("try{selection.selectAllChildren(document.body)}catch(e){alert('exception')}",
                "selection.anchorOffset", "x");
    }

    @Test
    @Alerts({"none", "cx"})
    public void getRangeAt() {
        test("", "selection.rangeCount > 0 ? selection.getRangeAt(0) : 'none'", "x");
    }

    @Test
    @Alerts({"", "true"})
    public void getRangeAtPrototype() {
        test("", "selection.rangeCount > 0 ? (selection.getRangeAt(0) instanceof Range) : ''", "x");
    }

    @Test
    @Alerts({"None", "None"})
    public void empty() {
        test("try{selection.empty()}catch(e){alert('exception')}", "selection.type", "x ? x : 'undefined'");
    }

    @Test
    @Alerts({"unsupported action", "unsupported action"})
    public void createRange() {
        test("", "selection.createRange()", "x");
    }

    private void test(final String action, final String x, final String alert) {

        final String html = "<html>\n"
                + "<body onload='test()'>\n"
                + "  <span id='s1'>abc</span><span id='s2'>xyz</span><span id='s3'>foo</span>\n"
                + "  <input type='button' id='b' onclick=\"" + action + ";test();\" value='click'></input>\n"
                + "<script>\n"
                + "  var selection = document.selection; // IE\n"
                + "  if(!selection) selection = window.getSelection(); // FF\n"
                + "  var s1 = document.getElementById('s1');\n"
                + "  var s2 = document.getElementById('s2');\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var x = " + x + ";\n"
                + "      alert(" + alert + ");\n"
                + "    } catch (e) {\n"
                + "      alert('unsupported action');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

}
