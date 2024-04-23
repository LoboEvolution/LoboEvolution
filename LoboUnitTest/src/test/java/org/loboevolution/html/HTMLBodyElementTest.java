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
import org.loboevolution.html.dom.HTMLBodyElement;

/**
 * Unit tests for {@link HTMLBodyElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLBodyElementTest extends LoboUnitTest {

    /**
     * Tests the default body padding and margins.
     */
    @Test
    @Alerts({"0px,0px,0px,0px,0px", ",,,,", "8px,8px,8px,8px,8px", ",,,,"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void attachEvent() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function handler() {\n"
                        + "        alert(event);\n"
                        + "      }\n"
                        + "      function test() {\n"
                        + "        try {\n"
                        + "          document.body.attachEvent('onclick', handler);\n"
                        + "        } catch(e) { alert('exception'); }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <input type='button' id='myInput' value='Test me'>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("no")
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "#0000aa", "x"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "http://www.foo.com/blah.gif", "blah.gif"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "#0000aa", "x"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "#0000aa", "x"})
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
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "#0000aa", "x"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "#0000aa", "x"})
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function HTMLBodyElement() { [native code] }", "ELEMENT_NODE, ATTRIBUTE_NODE, TEXT_NODE, CDATA_SECTION_NODE, ENTITY_REFERENCE_NODE, "
            + "ENTITY_NODE, PROCESSINGINSTRUCTION_NODE, COMMENT_NODE, DOCUMENT_NODE, DOCUMENT_TYPE_NODE, "
            + "DOCUMENT_FRAGMENT_NODE, NOTATION_NODE, DOCUMENT_POSITION_DISCONNECTED, "
            + "DOCUMENT_POSITION_PRECEDING, "
            + "DOCUMENT_POSITION_FOLLOWING, DOCUMENT_POSITION_CONTAINS, DOCUMENT_POSITION_CONTAINED_BY, "
            + "DOCUMENT_POSITIONIMPLEMENTATION_SPECIFIC, "})
    public void enumeratedProperties() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"keydown (target)", "keydown (body)"})
    public void eventHandler() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var target = document.getElementById('target');\n"
                        + "        target.onkeydown = function() {\n"
                        + "          alert('keydown (target)');\n"
                        + "        };\n"
                        + "        document.body.onkeydown = function() {\n"
                        + "          alert('keydown (body)');\n"
                        + "        };\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <input id='target' type='checkbox'>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "8"})
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

        checkHtmlAlert(html);
    }

}
