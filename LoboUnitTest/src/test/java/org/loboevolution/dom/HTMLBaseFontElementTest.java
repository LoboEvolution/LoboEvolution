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
 * Tests for {@link org.loboevolution.html.dom.HTMLBaseFontElement}.
 */
public class HTMLBaseFontElementTest extends LoboUnitTest {

    /**
     * <p>defaults.</p>
     */
    @Test
    public void defaults() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base);\n"
                        + "        alert(base.face);\n"
                        + "        alert(base.size);\n"
                        + "        alert(base.color);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"[object HTMLElement]", "undefined", "undefined", "undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>size.</p>
     */
    @Test
    public void size() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='4' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.size);\n"
                        + "        try {\n"
                        + "          base.size = 42;\n"
                        + "          alert(base.size);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "42"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>face.</p>
     */
    @Test
    public void face() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='5' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.face);\n"
                        + "        try {\n"
                        + "          base.face = 'helvetica';\n"
                        + "          alert(base.face);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "helvetica"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>color.</p>
     */
    @Test
    public void color() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <basefont id='base' color='red' face='swiss' size='4' />\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var base = document.getElementById('base');\n"
                        + "        alert(base.color);\n"
                        + "        try {\n"
                        + "          base.color = 'blue';\n"
                        + "          alert(base.color);\n"
                        + "        } catch(e) {\n"
                        + "          alert('exception');\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>foo</body>\n"
                        + "</html>";
        final String[] messages = {"undefined", "blue"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>type.</p>
     */
    @Test
    public void type() {
        final String html =
                "<html><head><title>foo</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "  var elem = document.getElementById('b1');\n"
                + "    try {\n"
                + "      alert(elem);\n"
                + "      alert(HTMLBaseFontElement);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <basefont id='b1' color='red' face='swiss' size='4' />\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLElement]", "exception"};
        checkHtmlAlert(html, messages);
    }
}
