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
package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLDivElement}.
 */
public class HTMLDivElementTest extends LoboUnitTest {

    /**
     * <p>doScroll.</p>
     */
    @Test
    public void doScroll() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var div = document.getElementById('d');\n"
                        + "        if(div.doScroll) {\n"
                        + "          alert('yes');\n"
                        + "          div.doScroll();\n"
                        + "          div.doScroll('down');\n"
                        + "        } else {\n"
                        + "          alert('no');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'><div id='d'>abc</div></body>\n"
                        + "</html>";
        final String[] messages = {"no"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAlign.</p>
     */
    @Test
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <div id='d1' align='left' ></div>\n"
                + "    <div id='d2' align='right' ></div>\n"
                + "    <div id='d3' align='justify' ></div>\n"
                + "    <div id='d4' align='center' ></div>\n"
                + "    <div id='d5' align='wrong' ></div>\n"
                + "    <div id='d6' ></div>\n"
                + "  </table>\n"

                + "<script>\n"
                + "  for (var i = 1; i <= 6; i++) {\n"
                + "    alert(document.getElementById('d' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"left", "right", "justify", "center", "wrong", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setAlign.</p>
     */
    @Test
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <table>\n"
                + "    <div id='d1' align='left' ></div>\n"
                + "  </table>\n"

                + "<script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"

                + "  var elem = document.getElementById('d1');\n"
                + "  setAlign(elem, 'CenTer');\n"

                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"

                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'justify');\n"
                + "  setAlign(elem, 'center');\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"CenTer", "8", "foo", "left", "right", "justify", "center"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>handlers.</p>
     */
    @Test
    public void handlers() {
        final String html
                = "<html><body>\n"
                + "<div id='d1'></div>\n"
                + "<script>\n"
                + "  var d = document.getElementById('d1');\n"
                + "  alert(d.onchange);\n"
                + "  alert('onchange' in d);\n"
                + "  alert(d.onsubmit);\n"
                + "  alert('onsubmit' in d);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {null, "true", null, "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientHeight.</p>
     */
    @Test
    public void clientHeight() {
        final String html = "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var elt = document.getElementById('emptyDiv');\n"
                        + "      alert(elt.clientHeight == 0);\n"

                        + "      elt = document.getElementById('textDiv');\n"
                        + "      alert(elt.clientHeight > 13);\n"

                        + "      elt = document.getElementById('styleDiv0');\n"
                        + "      alert(elt.clientHeight == 0);\n"

                        + "      elt = document.getElementById('styleDiv10');\n"
                        + "      alert(elt.clientHeight > 5);\n"

                        + "      elt = document.getElementById('styleDivAuto');\n"
                        + "      alert(elt.clientHeight > 13);\n"

                        + "      elt = document.getElementById('styleDivAutoEmpty');\n"
                        + "      alert(elt.clientHeight == 0);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='emptyDiv'></div>\n"
                        + "  <div id='textDiv'>HtmlUnit</div>\n"
                        + "  <div id='styleDiv0' style='height: 0px'>HtmlUnit</div>\n"
                        + "  <div id='styleDiv10' style='height: 10px'>HtmlUnit</div>\n"
                        + "  <div id='styleDivAuto' style='height: auto'>HtmlUnit</div>\n"
                        + "  <div id='styleDivAutoEmpty' style='height: auto'></div>\n"
                        + "</body></html>";

        final String[] messages = {"true", "true", "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientWidth.</p>
     */
    @Test
    public void clientWidth() {
        final String html = "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var elt = document.getElementById('emptyDiv');\n"
                        + "      alert(elt.clientWidth > 500);\n"

                        + "      elt = document.getElementById('textDiv');\n"
                        + "      alert(elt.clientWidth > 500);\n"

                        + "      elt = document.getElementById('styleDiv0');\n"
                        + "      alert(elt.clientWidth == 0);\n"

                        + "      elt = document.getElementById('styleDiv10');\n"
                        + "      alert(elt.clientWidth > 8);\n"

                        + "      elt = document.getElementById('styleDivAuto');\n"
                        + "      alert(elt.clientWidth > 500);\n"

                        + "      elt = document.getElementById('styleDivAutoEmpty');\n"
                        + "      alert(elt.clientWidth > 500);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='emptyDiv'></div>\n"
                        + "  <div id='textDiv'>HtmlUnit</div>\n"
                        + "  <div id='styleDiv0' style='width: 0px'>HtmlUnit</div>\n"
                        + "  <div id='styleDiv10' style='width: 10px'>HtmlUnit</div>\n"
                        + "  <div id='styleDivAuto' style='width: auto'>HtmlUnit</div>\n"
                        + "  <div id='styleDivAutoEmpty' style='width: auto'></div>\n"
                        + "</body></html>";

        final String[] messages =  {"true", "true", "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>clientWidthNested.</p>
     */
    @Test
    public void clientWidthNested() {
        final String html = "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var elt = document.getElementById('emptyDiv');\n"
                        + "      alert(elt.clientWidth > 500);\n"

                        + "      elt = document.getElementById('textDiv');\n"
                        + "      alert(elt.clientWidth > 500);\n"

                        + "      elt = document.getElementById('styleDiv0');\n"
                        + "      alert(elt.clientWidth == 0);\n"

                        + "      elt = document.getElementById('styleDiv10');\n"
                        + "      alert(elt.clientWidth > 8);\n"

                        + "      elt = document.getElementById('styleDivAuto');\n"
                        + "      alert(elt.clientWidth > 500);\n"

                        + "      elt = document.getElementById('styleDivAutoEmpty');\n"
                        + "      alert(elt.clientWidth > 500);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='emptyDiv'><div></div></div>\n"
                        + "  <div id='textDiv'><div>HtmlUnit</div></div>\n"
                        + "  <div id='styleDiv0' style='width: 0px'><div>HtmlUnit</div></div>\n"
                        + "  <div id='styleDiv10' style='width: 10px'><div>HtmlUnit</div></div>\n"
                        + "  <div id='styleDivAuto' style='width: auto'><div>HtmlUnit</div></div>\n"
                        + "  <div id='styleDivAutoEmpty' style='width: auto'><div></div></div>\n"
                        + "</body></html>";

        final String[] messages = {"true", "true", "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>noWrap.</p>
     */
    @Test
    public void noWrap() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var div = document.getElementById('test');\n"
                        + "        alert(div.noWrap);\n"
                        + "        alert(div.getAttribute('noWrap'));\n"
                        + "        div.noWrap = 'nowrap';\n"
                        + "        alert(div.noWrap);\n"
                        + "        alert(div.getAttribute('noWrap'));\n"
                        + "        div.noWrap = 'x';\n"
                        + "        alert(div.noWrap);\n"
                        + "        alert(div.getAttribute('noWrap'));\n"
                        + "        div.setAttribute('noWrap', 'blah');\n"
                        + "        alert(div.noWrap);\n"
                        + "        alert(div.getAttribute('noWrap'));\n"
                        + "        div.noWrap = '';\n"
                        + "        alert(div.getAttribute('noWrap'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  <div id='test'>div</div>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"undefined", null, "nowrap", null, "x", null, "x", "blah", "blah"};
        checkHtmlAlert(html, messages);
    }
}
