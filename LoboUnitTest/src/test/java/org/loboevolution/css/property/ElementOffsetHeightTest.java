/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package org.loboevolution.css.property;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Unit tests for {@code offsetHeight} of an element.
 */
public class ElementOffsetHeightTest extends LoboUnitTest {


    @Test
    public void offsetHeight() {
        final String html
                = "<html><head><body>\n"
                + "  <div id='myDiv'>a</div>\n"
                + "  <textarea id='myTextarea' cols='120' rows='20'></textarea>\n"
                + "<script>\n"
                + "var e = document.getElementById('myDiv');\n"
                + "var array = [];\n"
                + "for (var i = 0; i <= 128; i++) {\n"
                + "  e.style.fontSize = i + 'px';\n"
                + "  array.push(e.offsetHeight);\n"
                + "}\n"
                + "document.getElementById('myTextarea').value = array.join(', ');\n"
                + "  var area = document.getElementById('myTextarea');\n"
                + "  alert(area.value);\n"
                + "</script></body></html>";

        final String[] messages = {"0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 16, 17, 18, 19, 20, 21, 22, 24, 25, " +
                "26, 27, 28, 29, 31, 32, 33, 34, 35, 36, 37, 39, 40, 41, 42, 43, 44, 45, 47, 48, 49, 50, 51, 52, 54, 55, " +
                "56, 57, 58, 59, 60, 62, 63, 64, 65, 66, 67, 68, 70, 71, 72, 73, 74, 75, 77, 78, 79, 80, 81, 82, 83, 85, " +
                "86, 87, 88, 89, 90, 91, 93, 94, 95, 96, 97, 98, 100, 101, 102, 103, 104, 105, 106, 108, 109, 110, 111, " +
                "112, 113, 114, 116, 117, 118, 119, 120, 121, 123, 124, 125, 126, 127, 128, 129, 131, 132, 133, 134, 135, " +
                "136, 137, 139, 140, 141, 142, 143, 144, 146, 147"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightLineBreaks() {
        final String html
                = "<html><head><body>\n"
                + "  <div id='myDiv' style='width: 400px'>"
                + "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt "
                + "ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo "
                + "dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor "
                + "sit amet.</div>\n"
                + "  <textarea id='myTextarea' cols='120' rows='20'></textarea>\n"
                + "<script>\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var array = [];\n"
                + "  for (var i = 6; i <= 32; i+=2) {\n"
                + "    div.style.fontSize = i + 'px';\n"
                + "    array.push(div.offsetHeight);\n"
                + "  }\n"
                + "  document.getElementById('myTextarea').value = array.join(', ');\n"
                + "  var area = document.getElementById('myTextarea');\n"
                + "  alert(area.value);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"12, 27, 44, 60, 80, 108, 126, 161, 208, 216, 270, 288, 340, 407"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightLineBreaks2() {
        final String html
                = "<html><head><body>\n"
                + "  <div id='myLine'>Lorem ipsum</div>\n"
                + "  <div id='myDiv' style='width: 400px'>"
                + "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt "
                + "ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo "
                + "dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor "
                + "sit amet.</div>\n"
                + "  <textarea id='myTextarea' cols='120' rows='20'></textarea>\n"
                + "<script>\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var array = [];\n"
                + "  var lastHeight = 0;\n"

                + "  for (var i = 6; i <= 32; i+=2) {\n"
                + "    div.style.fontSize = i + 'px';\n"
                + "    var height = div.offsetHeight;"
                + "    array.push(height >= lastHeight);\n"
                + "    lastHeight = height;\n"
                + "  }\n"
                + "  document.getElementById('myTextarea').value = array.join(', ');\n"
                + "  var area = document.getElementById('myTextarea');\n"
                + "  alert(area.value);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"true, true, true, true, true, true, true, true, true, true, true, true, true, true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightManualLineBreaks() {
        final String html
                = "<html><head><body>\n"
                + "  <div id='myDiv' style='width: 400px;font-size: 12px;'>"
                + "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
                + "sed diam nonumy eirmod tempor invidunt "
                + "ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo "
                + "dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor "
                + "sit amet.</div>\n"
                + "  <div id='myDivBr' style='width: 400px;font-size: 12px;'>"
                + "Lorem<br>ipsum<br>dolor<br>sit<br>amet, consetetur sadipscing elitr, "
                + "sed diam nonumy eirmod tempor invidunt "
                + "ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo "
                + "dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor "
                + "sit amet.</div>\n"
                + "  <textarea id='myTextarea' cols='120' rows='20'></textarea>\n"
                + "<script>\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var divBr = document.getElementById('myDivBr');\n"
                + "  document.getElementById('myTextarea').value = div.offsetHeight < divBr.offsetHeight;\n"
                + "  var area = document.getElementById('myTextarea');\n"
                + "  alert(area.value);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }
}
