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
package org.loboevolution.css.property;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for ComputedHeight.
 */
@ExtendWith(AlertsExtension.class)
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
                + "alert(document.getElementById('myTextarea').value);\n"
                + "</script></body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("myTextarea");
        assertNull(elem.getNodeValue());
    }

    @Test
    @Alerts("12, 27, 44, 60, 80, 108, 126, 161, 208, 216, 270, 288, 340, 407")
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
                + "alert(document.getElementById('myTextarea').value);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true, true, true, true, true, true, true, true, true, true, true, true, true, true")
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
                + "alert(document.getElementById('myTextarea').value);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("true")
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
                + "alert(document.getElementById('myTextarea').value);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"549", "273"})
    public void issue124() {
        final String html
                = "<html>\n"
                + "<html>\n"
                + "  <head>\n"
                + "    <style>\n"
                + "      .title-box {width: 960px; font-size: 60px;}\n"
                + "      .title-sizer {height: 300px;}\n"
                + "    </style>\n"
                + "  </head>\n"
                + "  <body>\n"
                + "    <div class='title-box'>\n"
                + "      <span class='title-sizer'>\n"
                + "        <span class='title'>\n"
                + "          8oz steak from Good and Gather. 8oz steak from Good and Gather. 8oz"
                + "          steak from Good and Gather. 8oz steak from Good and Gather. 8oz steak"
                + "          from Good and Gather. 8oz steak from Good and Gather. 8oz steak from"
                + "          Good and Gather. 8oz steak from Good and Gather."
                + "        </span>\n"
                + "      </span>\n"
                + "    </div>\n"
                + "  </body>\n"
                + "  <script>\n"
                + "    function getAttributeValue(element, attribute) {\n"
                + "      if (element) {\n"
                + "        return window.getComputedStyle(element)[attribute].split('px')[0];\n"
                + "      }\n"
                + "      return 0;\n"
                + "    }\n"
                + "    var titleSizer = document.querySelector('.title-sizer');\n"
                + "    var title = document.querySelector('.title');\n"
                + "    var titleHeight = titleSizer.offsetHeight;\n"
                + "    var titleFontSize = getAttributeValue(titleSizer, 'fontSize');\n"
                + "    var titleHeightGoal = getAttributeValue(titleSizer, 'height');\n"
                + "   alert(titleHeight);\r\n"
                + "    while (titleHeight > titleHeightGoal) {\n"
                + "      titleFontSize -= 1;\n"
                + "      title.style.fontSize = titleFontSize + 'px';\n"
                + "      titleHeight = titleSizer.offsetHeight;\n"
                + "    }\n"
                + "   alert(titleHeight);\n"
                + "  </script>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
