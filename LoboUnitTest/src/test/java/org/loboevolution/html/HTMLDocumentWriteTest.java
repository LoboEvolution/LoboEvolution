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
import org.loboevolution.html.dom.HTMLDocument;

/**
 * Tests for {@link HTMLDocument}'s write(ln) function.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLDocumentWriteTest extends LoboUnitTest {

    @Test
    @Alerts("[object HTMLDocument]")
    public void openResult() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var res = document.open();\n"
                + "  alert(res);\n"
                + "  document.close();\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Hello There")
    public void write() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<title>Test</title>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  document.write('<html><body><scr'+'ipt>alert(\"Hello There\")</scr'+'ipt></body></html>');\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void writeNested() {
        final String html =
                "<html><body><script>\n"

                        + "var s = '\"<script>alert(1);<\\/scr\" + \"ipt>\"';\n"
                        + "document.write('<script><!--\\ndocument.write(' + s + ');\\n--><\\/script>');\n"
                        + "</script></body></html>";

        checkHtmlAlert(html);

    }

    @Test
    public void write2HtmlEndhtml_inHead() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "document.write('<HTML></HTML>');\n"
                + "</script>\n"
                + "</head><body>\n"
                + "</body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void writeScript() {
        final String html =
                "<html><body><script>\n"

                        + "  document.write('<scr'+'ipt>alert(1<2)</sc'+'ript>');\n"
                        + "</script></body></html>";

        checkHtmlAlert(html);

    }

    @Test
    @Alerts("<form></form>#[object HTMLFormElement]")
    public void writeOnOpenedWindow_DocumentIsProxied() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var w = window.open('','blah','width=460,height=420');\n"
                + "  var d = w.document;\n"
                + "  d.write('<html><body><form></form></body></html>');\n"
                + "  d.close();\n"
                + "  document.title = d.body.innerHTML;\n"
                + "  document.title += '#' + d.forms[0];\n"
                + "}\n"
                + "</script></head><body onload='test()'>foo</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void writeInFrameWithOnload() {
        final String html = "<html><head></head>\n"
                + "<body>\n"
                + "<iframe id='theIframe' src='about:blank'></iframe>\n"
                + "<script>\n"
                + "var doc = document.getElementById('theIframe').contentWindow.document;\n"
                + "doc.open();\n"
                + "doc.write('<html>');\n"
                + "doc.write('<body onload=\"document.getElementById(\\'foo\\')\">');\n"
                + "doc.write('</body></html>');\n"
                + "doc.close();\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "[object HTMLBodyElement]", "s1 s2 s3 s4 s5"})
    public void writeDestination() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>" + "</script>\n"
                        + "    <script>alert(document.body);</script>\n"
                        + "    <script>document.write('<span id=\"s1\">1</span>');</script>\n"
                        + "    <script>alert(document.body);</script>\n"
                        + "    <title>test</title>\n"
                        + "    <script>document.write('<span id=\"s2\">2</span>');</script>\n"
                        + "  </head>\n"
                        + "  <body id='foo'>\n"
                        + "    <script>document.write('<span id=\"s3\">3</span>');</script>\n"
                        + "    <span id='s4'>4</span>\n"
                        + "    <script>document.write('<span id=\"s5\">5</span>');</script>\n"
                        + "    <script>\n"
                        + "      var s = '';\n"
                        + "      for(var n = document.body.firstChild; n; n = n.nextSibling) {\n"
                        + "        if(n.id) {\n"
                        + "          if(s.length > 0) s+= ' ';\n"
                        + "            s += n.id;\n"
                        + "        }\n"
                        + "      }\n"
                        + "      alert(s);\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);

    }

    @Test
    @Alerts({"null", "[object HTMLBodyElement]", "", "foo"})
    public void writeBodyAttributesKept() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>" + "</script>\n"
                        + "    <script>alert(document.body);</script>\n"
                        + "    <script>document.write('<span id=\"s1\">1</span>');</script>\n"
                        + "    <script>alert(document.body);</script>\n"
                        + "    <script>alert(document.body.id);</script>\n"
                        + "    <title>test</title>\n"
                        + "  </head>\n"
                        + "  <body id='foo'>\n"
                        + "    <script>alert(document.body.id);</script>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);

    }

    @Test
    @Alerts({"1", "2", "3"})
    public void writeScriptExecutionOrder() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <title>test</title>\n"
                        + "    <script>" + "</script>\n"
                        + "    <script>alert('1');</script>\n"
                        + "    <script>document.write('<scrip'+'t>alert(\"2\")</s'+'cript>');</script>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <script>document.write('<scrip'+'t>alert(\"3\")</s'+'cript>');</script>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);

    }

    @Test
    @Alerts("outer")
    public void writeInManyTimes() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('inner').parentNode.id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<script>\n"
                + "document.write('<div id=\"outer\">');\n"
                + "document.write('<div id=\"inner\"/>');\n"
                + "document.write('</div>');\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);

    }

    @Test
    @Alerts({"theBody", "theBody", "theBody"})
    public void writeAddNodesToCorrectParent() {
        final String html = "<html><head><title>foo</title></head>\n"
                + "<body id=\"theBody\">\n"
                + "<script>\n"
                + "document.write('<p id=\"para1\">Paragraph #1</p>');\n"
                + "document.write('<p id=\"para2\">Paragraph #2</p>');\n"
                + "document.write('<p id=\"para3\">Paragraph #3</p>');\n"
                + "alert(document.getElementById('para1').parentNode.id);\n"
                + "alert(document.getElementById('para2').parentNode.id);\n"
                + "alert(document.getElementById('para3').parentNode.id);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);

    }

    @Test
    @Alerts({"outer", "inner1"})
    public void writeAddNodesToCorrectParentBug1678826() {
        final String html = "<html><head><title>foo</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('inner1').parentNode.id);\n"
                + "  alert(document.getElementById('inner2').parentNode.id);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<script>\n"
                + "document.write('<div id=\"outer\">');\n"
                + "document.write('<br id=\"br1\">');\n"
                + "document.write('<div id=\"inner1\"/>');\n"
                + "document.write('<hr id=\"hr1\"/>');\n"
                + "document.write('<div id=\"inner2\"/>');\n"
                + "document.write('</div>');\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"STYLE", "SCRIPT"})
    public void writeStyle() {
        final String html = "<html><head><title>foo</title></head><body>\n"
                + "<script>\n"
                + "  document.write('<style type=\"text/css\" id=\"myStyle\">');\n"
                + "  document.write('  .nwr {white-space: nowrap;}');\n"
                + "  document.write('</style>');\n"
                + "  document.write('<div id=\"myDiv\">');\n"
                + "  document.write('</div>');\n"
                + "  alert(document.getElementById('myDiv').previousSibling.nodeName);\n"
                + "  alert(document.getElementById('myStyle').previousSibling.nodeName);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);

    }
}
