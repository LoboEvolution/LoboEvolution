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
package org.loboevolution.css;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Unit tests for {@link StyleSheetList}.
 */
@ExtendWith(AlertsExtension.class)
public class StyleSheetListTest extends LoboUnitTest {


    @Test
    @Alerts("4")
    public void length() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link href='style1.css'></link>\n"
                        + "    <link href='style2.css' rel='stylesheet'></link>\n"
                        + "    <link href='style3.css' type='text/css'></link>\n"
                        + "    <link href='style4.css' rel='stylesheet' type='text/css'></link>\n"
                        + "    <style>div.x { color: red; }</style>\n"
                        + "    <script>\n"

                        + "    </script></head>\n"
                        + "  </head>\n"
                        + "  <body onload='alert(document.styleSheets.length)'>\n"
                        + "    <style>div.y { color: green; }</style>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"rgb(255, 0, 0)", "rgb(255, 0, 0)"})
    public void getComputedStyleLink() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='" + URL_SECOND + "'/>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var div = document.getElementById('myDiv');\n"
                        + "        try {\n"
                        + "         alert(window.getComputedStyle(div, null).color);\n"
                        + "          var div2 = document.getElementById('myDiv2');\n"
                        + "         alert(window.getComputedStyle(div2, null).color);\n"
                        + "        } catch(e) {alert('exception'); }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <div id='myDiv'></div>\n"
                        + "    <div id='myDiv2'></div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "undefined", "undefined", "undefined"})
    public void arrayIndexOutOfBoundAccess() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "       alert(document.styleSheets.length);\n"

                        + "        try {\n"
                        + "         alert(document.styleSheets[0]);\n"
                        + "        }\n"
                        + "        catch (e) {\n"
                        + "         alert('exception for 0');\n"
                        + "        }\n"

                        + "        try {\n"
                        + "         alert(document.styleSheets[46]);\n"
                        + "        }\n"
                        + "        catch (e) {\n"
                        + "         alert('exception for 46');\n"
                        + "        }\n"

                        + "        try {\n"
                        + "         alert(document.styleSheets[-2]);\n"
                        + "        }\n"
                        + "        catch (e) {\n"
                        + "         alert('exception for -2');\n"
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
    @Alerts({"1", "[object CSSStyleSheet]", "[object CSSStyleSheet]"})
    public void nonExistentStylesheet() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='foo.css'/>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "       alert(document.styleSheets.length);\n"
                        + "       alert(document.styleSheets.item(0));\n"
                        + "       alert(document.styleSheets[0]);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>abc</body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object CSSStyleSheet]", "[object CSSStyleSheet]"})
    public void emptyGZipEncodedStylesheet() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='foo.css'/>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "       alert(document.styleSheets.length);\n"
                        + "       alert(document.styleSheets.item(0));\n"
                        + "       alert(document.styleSheets[0]);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>abc</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object CSSStyleSheet]", "[object CSSStyleSheet]"})
    public void brokenGZipEncodedStylesheet() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='foo.css'/>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "       alert(document.styleSheets.length);\n"
                        + "       alert(document.styleSheets.item(0));\n"
                        + "       alert(document.styleSheets[0]);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>abc</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "1"})
    public void dynamicAddedStyleSheet() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='" + URL_SECOND + "'/>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "       alert(document.styleSheets.length);\n"

                        + "        var linkTag = document.createElement ('link');\n"
                        + "        linkTag.href = 'new.css';\n"
                        + "        linkTag.rel = 'stylesheet';\n"
                        + "        var head = document.getElementsByTagName ('head')[0];\n"
                        + "        head.appendChild (linkTag);\n"

                        + "       alert(document.styleSheets.length);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <div id='myDiv'></div>\n"
                        + "    <div id='myDiv2'></div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "false", "true", "false", "false"})
    public void in() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='foo.css'/>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var sheets = document.styleSheets;\n"
                        + "       alert(sheets.length);\n"
                        + "       alert(-1 in sheets);\n"
                        + "       alert(0 in sheets);\n"
                        + "       alert(1 in sheets);\n"
                        + "       alert(42 in sheets);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>abc</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "false", "false"})
    public void equivalentValues() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='foo.css'/>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var sheets = document.styleSheets;\n"
                        + "       alert(sheets == document.styleSheets);\n"
                        + "       alert(sheets == null);\n"
                        + "       alert(null == sheets);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>abc</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
