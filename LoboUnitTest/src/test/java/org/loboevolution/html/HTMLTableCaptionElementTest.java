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
package org.loboevolution.html;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLTableCaptionElement;

/**
 * Tests for {@link HTMLTableCaptionElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTableCaptionElementTest extends LoboUnitTest {


    @Test
    @Alerts({"left", "right", "bottom", "top", "wrong", "null"})
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <caption id='c1' align='left' ></caption>\n"
                + "    <caption id='c2' align='right' ></caption>\n"
                + "    <caption id='c3' align='bottom' ></caption>\n"
                + "    <caption id='c4' align='top' ></caption>\n"
                + "    <caption id='c5' align='wrong' ></caption>\n"
                + "    <caption id='c6' ></caption>\n"
                + "  </table>\n"
                + "    <script>\n"
                + "  for (var i = 1; i <= 6; i++) {\n"
                + "    alert(document.getElementById('c' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"CenTer", "8", "foo", "left", "right", "bottom", "top"})
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <caption id='c1' align='left' ></caption>\n"
                + "  </table>\n"
                + "    <script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"
                + "  var elem = document.getElementById('c1');\n"
                + "  setAlign(elem, 'CenTer');\n"
                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"
                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'bottom');\n"
                + "  setAlign(elem, 'top');\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined", "undefined", "middle", "8", "BOTtom"})
    public void vAlign() {
        final String html
                = "<html><body><table>\n"
                + "  <caption id='c1' valign='top'>a</caption>\n"
                + "  <caption id='c2' valign='baseline'>b</caption>\n"
                + "  <caption id='c3' valign='3'>c</caption>\n"
                + "  <tr>\n"
                + "    <td>a</td>\n"
                + "    <td>b</td>\n"
                + "    <td>c</td>\n"
                + "  </tr>\n"
                + "</table>\n"
                + "    <script>\n"
                + "  function set(e, value) {\n"
                + "    try {\n"
                + "      e.vAlign = value;\n"
                + "    } catch (e) {\n"
                + "      alert('error');\n"
                + "    }\n"
                + "  }\n"
                + "  var c1 = document.getElementById('c1');\n"
                + "  var c2 = document.getElementById('c2');\n"
                + "  var c3 = document.getElementById('c3');\n"
                + "  alert(c1.vAlign);\n"
                + "  alert(c2.vAlign);\n"
                + "  alert(c3.vAlign);\n"
                + "  set(c1, 'middle');\n"
                + "  set(c2, 8);\n"
                + "  set(c3, 'BOTtom');\n"
                + "  alert(c1.vAlign);\n"
                + "  alert(c2.vAlign);\n"
                + "  alert(c3.vAlign);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<CAPTION id=\"cap\">a</CAPTION>", "new"})
    public void outerHTML() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.getElementById('cap').outerHTML);\n"
                        + "        document.getElementById('cap').outerHTML = '<div id=\"new\">text<div>';\n"
                        + "        alert(document.getElementById('new').id);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <table>\n"
                        + "    <caption id='cap'>a</caption>\n"
                        + "    <tr>\n"
                        + "      <td>cell1</td>\n"
                        + "    </tr>\n"
                        + "  </table>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
