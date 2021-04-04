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
 * Tests for {@link org.loboevolution.html.dom.HTMLBodyElement}.
 */
public class HTMLBodyElementTest extends LoboUnitTest {

    /**
     * <p>defaultPaddingAndMargins.</p>
     */
    @Test
    public void defaultPaddingAndMargins() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        var s = b.currentStyle ? b.currentStyle : getComputedStyle(b, null);\n"
                        + "        alert(s.padding + ',' + s.paddingLeft + ',' + s.paddingRight + ',' + s.paddingTop + ',' + s.paddingBottom);\n"
                        + "        alert(b.style.padding + ',' + b.style.paddingLeft + ',' + b.style.paddingRight + ',' + b.style.paddingTop + ',' + b.style.paddingBottom);\n"
                        + "        alert(s.margin + ',' + s.marginLeft + ',' + s.marginRight + ',' + s.marginTop + ',' + s.marginBottom);\n"
                        + "        alert(b.style.margin + ',' + b.style.marginLeft + ',' + b.style.marginRight + ',' + b.style.marginTop + ',' + b.style.marginBottom);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        final String[] messages = {"0px,0px,0px,0px,0px", ",,,,", "8px,8px,8px,8px,8px", ",,,,"};
        checkHtmlAlert(html, messages);
    }

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
                        + "        if(document.body.doScroll) {\n"
                        + "          alert('yes');\n"
                        + "          document.body.doScroll();\n"
                        + "          document.body.doScroll('down');\n"
                        + "        } else {\n"
                        + "          alert('no');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";
        final String[] messages = {"no"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>aLink.</p>
     */
    @Test
    public void aLink() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(b.aLink);\n"
                        + "        b.aLink = '#0000aa';\n"
                        + "        alert(b.aLink);\n"
                        + "        b.aLink = 'x';\n"
                        + "        alert(b.aLink);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        final String[] messages = {null, "#0000aa", "x"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>background.</p>
     */
    @Test
    public void background() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(b.background);\n"
                        + "        b.background = 'http://www.foo.com/blah.gif';\n"
                        + "        alert(b.background);\n"
                        + "        b.background = 'blah.gif';\n"
                        + "        alert(b.background);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";

        final String[] messages = {"", "http://www.foo.com/blah.gif", "blah.gif"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>bgColor.</p>
     */
    @Test
    public void bgColor() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(b.bgColor);\n"
                        + "        b.bgColor = '#0000aa';\n"
                        + "        alert(b.bgColor);\n"
                        + "        b.bgColor = 'x';\n"
                        + "        alert(b.bgColor);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        final String[] messages = {null, "#0000aa", "x"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>link.</p>
     */
    @Test
    public void link() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(b.link);\n"
                        + "        b.link = '#0000aa';\n"
                        + "        alert(b.link);\n"
                        + "        b.link = 'x';\n"
                        + "        alert(b.link);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        final String[] messages = {null, "#0000aa", "x"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>text.</p>
     */
    @Test
    public void text() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(b.text);\n"
                        + "        b.text = '#0000aa';\n"
                        + "        alert(b.text);\n"
                        + "        b.text = 'x';\n"
                        + "        alert(b.text);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        final String[] messages = {null, "#0000aa", "x"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>vLink.</p>
     */
    @Test
    public void vLink() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(b.vLink);\n"
                        + "        b.vLink = '#0000aa';\n"
                        + "        alert(b.vLink);\n"
                        + "        b.vLink = 'x';\n"
                        + "        alert(b.vLink);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        final String[] messages = {null, "#0000aa", "x"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>enumeratedProperties.</p>
     */
    @Test
    public void enumeratedProperties() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var str = '';\n"
                + "    try {\n"
                + "      alert(HTMLBodyElement);\n"
                + "      var str = '';\n"
                + "      for (var i in HTMLBodyElement)\n"
                + "        str += i + ', ';\n"
                + "      alert(str);\n"
                + "    } catch (e) { alert('exception')}\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"function HTMLBodyElement() { [native code] }", ""
                + "ELEMENT_NODE, ATTRIBUTE_NODE, TEXT_NODE, CDATA_SECTION_NODE, ENTITY_REFERENCE_NODE, "
                + "ENTITY_NODE, PROCESSING_INSTRUCTION_NODE, COMMENT_NODE, DOCUMENT_NODE, DOCUMENT_TYPE_NODE, "
                + "DOCUMENT_FRAGMENT_NODE, NOTATION_NODE, DOCUMENT_POSITION_DISCONNECTED, "
                + "DOCUMENT_POSITION_PRECEDING, "
                + "DOCUMENT_POSITION_FOLLOWING, DOCUMENT_POSITION_CONTAINS, DOCUMENT_POSITION_CONTAINED_BY, "
                + "DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC, "};
        checkHtmlAlert(html, messages);
    }

     /**
      * <p>top.</p>
      */
     @Test
    public void top() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(b.offsetTop);\n"
                        + "        alert(b.getBoundingClientRect().top);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        final String[] messages = {"0", "8"};
        checkHtmlAlert(html, messages);
    }
}
