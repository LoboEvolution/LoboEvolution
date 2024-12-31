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
import org.loboevolution.html.dom.HTMLStyleElement;

/**
 * Tests for {@link HTMLStyleElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLStyleElementTest extends LoboUnitTest {

    @Test
    @Alerts({"[object HTMLStyleElement]", "[object CSSStyleSheet]", "undefined"})
    public void stylesheet() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var f = document.getElementById('myStyle');\n"
                + "  alert(f);\n"
                + "  alert(f.sheet);\n"
                + "  alert(f.styleSheet);\n"
                + "}</script>\n"
                + "<style id='myStyle'>p: vertical-align:top</style>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("2")
    public void styleChildren() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var doc = document;\n"
                + "  var style = doc.createElement('style');\n"
                + "  doc.documentElement.firstChild.appendChild(style);\n"
                + "  style.appendChild(doc.createTextNode('* { z-index: 0; }\\\n'));\n"
                + "  style.appendChild(doc.createTextNode('DIV { z-index: 10; position: absolute; }\\\n'));\n"
                + "  if (doc.styleSheets[0].cssRules)\n"
                + "    rules = doc.styleSheets[0].cssRules;\n"
                + "  else\n"
                + "    rules = doc.styleSheets[0].rules;\n"
                + "  alert(rules.length);\n"
                + "}</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({".a > .t { }", ".b > .t { }", ".c > .t { }"})
    public void innerHtml() {
        final String html
                = "<html><head>\n"
                + "<style id='style_none'>.a > .t { }</style>\n"
                + "<style type='text/test' id='style_text'>.b > .t { }</style>\n"
                + "<style type='text/html' id='style_html'>.c > .t { }</style>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('style_none');\n"
                + "  alert(style.innerHTML);\n"
                + "  style = document.getElementById('style_text');\n"
                + "  alert(style.innerHTML);\n"
                + "  style = document.getElementById('style_html');\n"
                + "  alert(style.innerHTML);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "text/test", "text/css"})
    public void type() {
        final String html
                = "<html><head>\n"
                + "<style id='style_none'>my { }</style>\n"
                + "<style type='text/test' id='style_text'>my { }</style>\n"
                + "<style type='text/css' id='style_css'>my { }</style>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('style_none');\n"
                + "  alert(style.type);\n"
                + "  style = document.getElementById('style_text');\n"
                + "  alert(style.type);\n"
                + "  style = document.getElementById('style_css');\n"
                + "  alert(style.type);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "all", "screen, print,test"})
    public void media() {
        final String html
                = "<html><head>\n"
                + "<style id='style_none'>my { }</style>\n"
                + "<style media='all' id='style_all'>my { }</style>\n"
                + "<style media='screen, print,test' id='stylesome'>my { }</style>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('style_none');\n"
                + "  alert(style.media);\n"
                + "  style = document.getElementById('style_all');\n"
                + "  alert(style.media);\n"
                + "  style = document.getElementById('stylesome');\n"
                + "  alert(style.media);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"all", "", "screen:screen", "priNT", "screen, print"})
    public void mediasetter() {
        final String html
                = "<html><head>\n"
                + "<style id='myStyle' media='all'>my { }</style>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('myStyle');\n"
                + "  alert(style.media);\n"
                + "  style.media = '';\n"
                + "  alert(style.media);\n"
                + "  style.media = 'screen';\n"
                + "  alert(style.media + ':' + style.attributes['media'].value);\n"
                + "  style.media = 'priNT';\n"
                + "  alert(style.media);\n"
                + "  style.media = 'screen, print';\n"
                + "  alert(style.media);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "undefined"})
    public void scoped() {
        final String html
                = "<html><head>\n"
                + "<style id='style_none'>my { }</style>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('style_none');\n"
                + "  alert(style.scoped);\n"
                + "  style = document.getElementById('stylescoped');\n"
                + "  alert(style.scoped);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <style id='stylescoped' scoped>my { }</style>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "true", "false"})
    public void scopedsetter() {
        final String html
                = "<html><head>\n"
                + "<style id='myStyle' media='all'>my { }</style>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('myStyle');\n"
                + "  alert(style.scoped);\n"
                + "  style.scoped = true;\n"
                + "  alert(style.scoped);\n"
                + "  style.media = false;\n"
                + "  alert(style.media);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "text/css"})
    public void typesetter() {
        final String html
                = "<html><head>\n"
                + "<style id='style_none'></style>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('style_none');\n"
                + "  alert(style.type);\n"
                + "  style.type = 'text/css';\n"
                + "  alert(style.type);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"rgb(0, 128, 0)", "false", "rgb(0, 0, 0)"})
    public void disabled() {
        final String html
                = "<html><head>\n"
                + "<style id='myStyle'> .abc { color: green; }</style>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var style = document.getElementById('myStyle');\n"
                + "  alert(window.getComputedStyle(div, '').color);\n"
                + "  alert(style.disabled);\n"
                + "  style.disabled = true;\n"
                + "  alert(window.getComputedStyle(div, '').color);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "  <div id='myDiv' class='abc'>abcd</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"rgb(0, 0, 0)", "rgb(0, 128, 0)", "rgb(0, 0, 255)"})
    public void styleInCdataXHtml() {
        final String html =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<!DOCTYPE html PUBLIC \n"
                        + "  \"-//W3C//DTD XHTML 1.0 Strict//EN\" \n"
                        + "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                        + "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:xhtml='http://www.w3.org/1999/xhtml'>\n"
                        + "<head>\n"
                        + "  <style>\n"
                        + "    //<![CDATA[\n"
                        + "      #one {color: red;}\n"
                        + "    //]]>\n"
                        + "  </style>\n"
                        + "  <style>/*<![CDATA[*/ #two {color: green;} /*]]>*/</style>\n"
                        + "  <style>\n"
                        + "    <![CDATA[\n"
                        + "      #three {color: blue;}\n"
                        + "    ]]>\n"
                        + "  </style>\n"
                        + "  <script>\n"

                        + "    function doTest() {\n"
                        + "      var div = document.getElementById('one');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "      div = document.getElementById('two');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "      div = document.getElementById('three');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <div id='one'>one</div>\n"
                        + "  <div id='two'>two</div>\n"
                        + "  <div id='three'>three</div>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"rgb(0, 0, 0)", "rgb(0, 128, 0)", "rgb(0, 0, 255)"})
    public void scriptInCdataXml() {
        final String html =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<!DOCTYPE html PUBLIC \n"
                        + "  \"-//W3C//DTD XHTML 1.0 Strict//EN\" \n"
                        + "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                        + "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:xhtml='http://www.w3.org/1999/xhtml'>\n"
                        + "<head>\n"
                        + "  <style>\n"
                        + "    //<![CDATA[\n"
                        + "      #one {color: red;}\n"
                        + "    //]]>\n"
                        + "  </style>\n"
                        + "  <style>/*<![CDATA[*/ #two {color: green;} /*]]>*/</style>\n"
                        + "  <style>\n"
                        + "    <![CDATA[\n"
                        + "      #three {color: blue;}\n"
                        + "    ]]>\n"
                        + "  </style>\n"
                        + "  <script>\n"

                        + "    function doTest() {\n"
                        + "      var div = document.getElementById('one');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "      div = document.getElementById('two');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "      div = document.getElementById('three');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <div id='one'>one</div>\n"
                        + "  <div id='two'>two</div>\n"
                        + "  <div id='three'>three</div>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"rgb(0, 0, 0)", "rgb(0, 128, 0)", "rgb(0, 0, 0)"})
    public void scriptInCdataHtml() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <style>\n"
                        + "    //<![CDATA[\n"
                        + "      #one {color: red;}\n"
                        + "    //]]>\n"
                        + "  </style>\n"
                        + "  <style>/*<![CDATA[*/ #two {color: green;} /*]]>*/</style>\n"
                        + "  <style>\n"
                        + "    <![CDATA[\n"
                        + "      #three {color: blue;}\n"
                        + "    ]]>\n"
                        + "  </style>\n"
                        + "  <script>\n"

                        + "    function doTest() {\n"
                        + "      var div = document.getElementById('one');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "      div = document.getElementById('two');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "      div = document.getElementById('three');\n"
                        + "      alert(window.getComputedStyle(div, null).color);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='doTest()'>\n"
                        + "  <div id='one'>one</div>\n"
                        + "  <div id='two'>two</div>\n"
                        + "  <div id='three'>three</div>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }
}
