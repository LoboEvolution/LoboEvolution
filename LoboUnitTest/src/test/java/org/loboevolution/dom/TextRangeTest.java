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

/**
 * Tests for TextRange.
 */
@ExtendWith(AlertsExtension.class)
public class TextRangeTest extends LoboUnitTest {


    @Test
    @Alerts("exception")
    public void text() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        var f = document.getElementById('foo');\n"
                + "        f.focus();\n"
                + "        var r = document.selection.createRange();\n"
                + "       alert(f.value);\n"
                + "        r.text = 'bla bla';\n"
                + "       alert(f.value);\n"
                + "        r.duplicate().text = 'bli bli';\n"
                + "       alert(f.value);\n"
                + "      } catch(e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<textarea id='foo'></textarea>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void parentElement() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "       alert(document.body.createTextRange().parentElement().tagName);\n"
                + "      } catch(e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void collapse() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        var f = document.getElementById('foo');\n"
                + "        f.focus();\n"
                + "        f.select();\n"
                + "        var r = document.selection.createRange();\n"
                + "       alert(r.text);\n"
                + "        r.collapse();\n"
                + "       alert(r.text);\n"
                + "      } catch(e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<textarea id='foo'>hello</textarea>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void select() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        var r = document.selection.createRange();\n"
                + "        r.select();\n"
                + "      } catch(e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<textarea id='foo'>hello</textarea>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void moveEnd() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        var f = document.getElementById('foo');\n"
                + "        f.focus();\n"
                + "        f.select();\n"
                + "        var r = document.selection.createRange();\n"
                + "       alert(r.text);\n"
                + "        r.moveEnd('character', -1);\n"
                + "       alert(r.text);\n"
                + "        r.moveStart('character');\n"
                + "       alert(r.text);\n"
                + "      } catch(e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<textarea id='foo'>hello</textarea>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void moveOutOfBounds_input() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        var f = document.getElementById('foo');\n"
                + "        f.focus();\n"
                + "        f.select();\n"
                + "        var r = document.selection.createRange();\n"
                + "       alert(r.text);\n"
                + "        r.moveEnd('character', -1);\n"
                + "       alert(r.text);\n"
                + "        r.moveStart('character');\n"
                + "       alert(r.text);\n"
                + "       alert(r.moveEnd('character', 100));\n"
                + "       alert(r.moveStart('character', -100));\n"
                + "       alert(r.text);\n"
                + "      } catch(e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<input id='foo' value='hello'/>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void inRange() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      try {\n"
                + "        var r1 = document.body.createTextRange();\n"
                + "        var r2 = r1.duplicate();\n"
                + "       alert(r1.inRange(r2));\n"
                + "       alert(r2.inRange(r1));\n"
                + "        r1.collapse();\n"
                + "       alert(r1.inRange(r2));\n"
                + "       alert(r2.inRange(r1));\n"
                + "      } catch(e) {alert('exception'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<textarea id='foo'>hello</textarea>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void inRange2() {
        final String html = "<html><body>\n"
                + "<form name='f'><input name='q' value=''></form>\n"
                + "<script>\n"
                + "  try {\n"
                + "    var range = document.f.q.createTextRange();\n"
                + "    var selectionRange = document.selection.createRange();\n"
                + "   alert(range.inRange(selectionRange));\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void moveToElementText() {
        final String html = "<html><body onload='test()'>\n"
                + "<span id='s1'>abc</span><span id='s2'>xyz</span><span id='s3'>foo</span>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var r = document.selection.createRange();\n"
                + "      r.moveToElementText(document.getElementById('s3'));\n"
                + "     alert(r.parentElement().id + ' ' + r.text + ' ' + r.htmlText);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void setEndRange() {
        final String html = "<html><body>\n"
                + "<form name='f'><input name='q' value='hello world'></form>\n"
                + "<script>\n"
                + "  try {\n"
                + "    var range1 = document.f.q.createTextRange();\n"
                + "    var range2 = range1.duplicate();\n"
                + "    range1.moveEnd('character', -6);\n"
                + "   alert(range1.text);\n"
                + "    range2.moveStart('character', 6);\n"
                + "   alert(range2.text);\n"
                + "    var r3 = range1.duplicate();\n"
                + "    r3.setEndPoint('EndToEnd', range2);\n"
                + "   alert(r3.text);\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createRangeParentElement() {
        final String html =
                "<html><body>\n"
                        + "<script>\n"
                        + "  try {\n"
                        + "    s = document.selection.createRange();\n"
                        + "    p = s.parentElement();\n"
                        + "   alert(p.tagName);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createRangeHtmlText() {
        final String html =
                "<html><body>\n"
                        + "<script>\n"
                        + "  try {\n"
                        + "    s = document.selection.createRange();\n"
                        + "    t = s.htmlText;\n"
                        + "   alert(t);\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void moveToBookmark() {
        final String html =
                "<html><body>\n"
                        + "<script>\n"
                        + "  try {\n"
                        + "    var rng = document.body.createTextRange();\n"
                        + "    rng.moveToBookmark(rng.getBookmark());\n"
                        + "   alert('ok');\n"
                        + "  } catch(e) {alert('exception'); }\n"
                        + "</script>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void compareEndPoints() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (document.selection) {\n"
                + "        var r1 = document.selection.createRange();\n"
                + "        var r2 = document.selection.createRange();\n"
                + "       alert(r1.compareEndPoints('StartToStart', r2));\n"
                + "      }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

}
