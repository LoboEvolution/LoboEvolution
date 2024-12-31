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
import org.loboevolution.traversal.NodeIterator;

/**
 * Tests for {@link NodeIterator}.
 */
@ExtendWith(AlertsExtension.class)
public class NodeIteratorTest extends LoboUnitTest {


    @Test
    @Alerts({"[object HTMLDivElement]", "[object HTMLSpanElement]", "[object HTMLSpanElement]",
            "[object HTMLSpanElement]"})
    public void filterNull() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (document.createNodeIterator) {\n"
                + "        var nodeIterator = document.createNodeIterator(\n"
                + "          document.getElementById('myId'),\n"
                + "          NodeFilter.SHOW_ELEMENT,\n"
                + "          null\n"
                + "        );\n"
                + "        var currentNode;\n"
                + "        while (currentNode = nodeIterator.nextNode()) {\n"
                + "         alert(currentNode);\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='myId'><span>a</span><span>b</span><span>c</span></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLParagraphElement]")
    public void filterFunction() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (document.createNodeIterator) {\n"
                + "        var nodeIterator = document.createNodeIterator(\n"
                + "          document.getElementById('myId'),\n"
                + "          NodeFilter.SHOW_ELEMENT,\n"
                + "          function(node) {\n"
                + "            return node.nodeName.toLowerCase() === 'p' ? NodeFilter.FILTER_ACCEPT"
                + " : NodeFilter.FILTER_REJECT;\n"
                + "          }\n"
                + "        );\n"
                + "        var currentNode;\n"
                + "        while (currentNode = nodeIterator.nextNode()) {\n"
                + "         alert(currentNode);\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='myId'><span>a</span><p>b</p><span>c</span></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("def")
    public void filterObject() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (document.createNodeIterator) {\n"
                + "        var nodeIterator = document.createNodeIterator(\n"
                + "          document.getElementById('myId'),\n"
                + "          NodeFilter.SHOW_TEXT,\n"
                + "          { acceptNode: function(node) {\n"
                + "            if (node.data.indexOf('e') != -1) {\n"
                + "              return NodeFilter.FILTER_ACCEPT;\n"
                + "            }\n"
                + "          } }\n"
                + "        );\n"
                + "        var currentNode;\n"
                + "        while (currentNode = nodeIterator.nextNode()) {\n"
                + "         alert(currentNode.data);\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='myId'><span>abc</span><p>def</p><span>ghi</span></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "11", "12"})
    public void subroot() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (document.createNodeIterator) {\n"
                + "        var nodeIterator = document.createNodeIterator(document.getElementById('1'), NodeFilter.SHOW_ELEMENT);\n"
                + "        var currentNode;\n"
                + "        while (currentNode = nodeIterator.nextNode()) {\n"
                + "         alert(currentNode.id);\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='before'>before</div>\n"
                + "<table>\n"
                + "  <tr id='1'>\n"
                + "    <td id='11'>11</td>\n"
                + "    <td id='12'>12</td>\n"
                + "  </tr>\n"
                + "  <tr id='2'>\n"
                + "    <td id='21'>21</td>\n"
                + "    <td id='22'>22</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "<div id='after'>after</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
