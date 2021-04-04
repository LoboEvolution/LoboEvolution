/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.dom.HTMLStyleElement}.
 */
public class HTMLStyleElementTest extends LoboUnitTest {
	
    /**
     * <p>stylesheet.</p>
     */
    @Test
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

        final String[] messages = {"[object HTMLStyleElement]", "[object CSSStyleSheet]", "undefined"};
        checkHtmlAlert(html, messages);
    }
	
    /**
     * <p>styleChildren.</p>
     */
    @Test
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

        final String[] messages = {"2"};
        checkHtmlAlert(html, messages);
    }
	
    /**
     * <p>innerHtml.</p>
     */
    @Test
    public void innerHtml() {
        final String html
                = "<html><head>\n"

                + "<style id='style_none'>.a > .t { }</style>\n"
                + "<style type='text/test' id='style_text'>.b > .t { }</style>\n"
                + "<style type='text/html' id='style_html'>.c > .t { }</style>\n"

                + "<script>\n"
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

        final String[] messages = {".a > .t { }", ".b > .t { }", ".c > .t { }"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>type.</p>
     */
    @Test
    public void type() {
        final String html
                = "<html><head>\n"

                + "<style id='style_none'>my { }</style>\n"
                + "<style type='text/test' id='style_text'>my { }</style>\n"
                + "<style type='text/css' id='style_css'>my { }</style>\n"

                + "<script>\n"
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

        final String[] messages = {"", "text/test", "text/css"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>media.</p>
     */
    @Test
    public void media() {
        final String html
                = "<html><head>\n"

                + "<style id='style_none'>my { }</style>\n"
                + "<style media='all' id='style_all'>my { }</style>\n"
                + "<style media='screen, print,test' id='style_some'>my { }</style>\n"

                + "<script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('style_none');\n"
                + "  alert(style.media);\n"
                + "  style = document.getElementById('style_all');\n"
                + "  alert(style.media);\n"
                + "  style = document.getElementById('style_some');\n"
                + "  alert(style.media);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"", "all", "screen, print,test"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>media_setter.</p>
     */
    @Test
    public void media_setter() {
        final String html
                = "<html><head>\n"

                + "<style id='myStyle' media='all'>my { }</style>\n"

                + "<script>\n"
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

        final String[] messages = {"all", "", "screen:screen", "priNT", "screen, print"};
        checkHtmlAlert(html, messages);
    }
	
    /**
     * <p>scoped.</p>
     */
    @Test
    public void scoped() {
        final String html
                = "<html><head>\n"

                + "<style id='style_none'>my { }</style>\n"

                + "<script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('style_none');\n"
                + "  alert(style.scoped);\n"
                + "  style = document.getElementById('style_scoped');\n"
                + "  alert(style.scoped);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <style id='style_scoped' scoped>my { }</style>\n"
                + "</body></html>";

        final String[] messages = {"undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scoped_setter.</p>
     */
    @Test
    public void scoped_setter() {
        final String html
                = "<html><head>\n"

                + "<style id='myStyle' media='all'>my { }</style>\n"

                + "<script>\n"
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

        final String[] messages = {"undefined", "true", "false"};
        checkHtmlAlert(html, messages);
    }
	
    /**
     * <p>type_setter.</p>
     */
    @Test
    public void type_setter() {
        final String html
                = "<html><head>\n"
                + "<style id='style_none'></style>\n"

                + "<script>\n"
                + "function doTest() {\n"
                + "  style = document.getElementById('style_none');\n"
                + "  alert(style.type);\n"
                + "  style.type = 'text/css';\n"
                + "  alert(style.type);\n"
                + "}\n"
                + "</script>\n"
                + "</head><body onload='doTest()'>\n"
                + "</body></html>";

        final String[] messages = {"", "text/css"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>disabled.</p>
     */
    @Test
    public void disabled() {
        final String html
                = "<html><head>\n"
                + "<style id='myStyle'> .abc { color: green; }</style>\n"

                + "<script>\n"
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

        final String[] messages = {"rgb(0, 128, 0)", "false", "rgb(0, 0, 0)"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>scriptInCdataHtml.</p>
     */
    @Test
    public void scriptInCdataHtml() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <style>\n"
                        + "    //<![CDATA[\n"
                        + "      #one {color: red;}\n"
                        + "    //]]>\n"
                        + "  </style>\n"
                        + "  <style>/*<![CDATA[ #two {color: green;} /*]]></style>\n"
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

        final String[] messages = {"rgb(0, 0, 0)", "rgb(0, 128, 0)", "rgb(0, 0, 0)"};
        checkHtmlAlert(html, messages);
    }
}
