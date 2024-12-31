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

/**
 * Tests for HTMLTemplateElement.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLTemplateElementTest extends LoboUnitTest {

    @Test
    @Alerts("false")
    public void prototype() {
        final String html
                = "<html><body>\n"
                + "    <script>\n"
                + "  try {\n"
                + "    alert(HTMLTemplateElement.prototype == null);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void contentCheck() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        try {\n"
                        + "          var template = document.createElement('template');\n"
                        + "          alert('content' in template);\n"
                        + "        } catch (e) { alert('exception'); }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object DocumentFragment]", "0-0",
            "[object DocumentFragment]", "0-0",
            "[object DocumentFragment]", "0-1",
            "[object DocumentFragment]", "0-2"})
    public void content() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var template = document.createElement('template');\n"
                        + "        if (!('content' in template)) { alert('not available'); return }\n"
                        + "        alert(template.content);\n"
                        + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"

                        + "        template = document.getElementById('tEmpty');\n"
                        + "        alert(template.content);\n"
                        + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"

                        + "        template = document.getElementById('tText');\n"
                        + "        alert(template.content);\n"
                        + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"

                        + "        template = document.getElementById('tDiv');\n"
                        + "        alert(template.content);\n"
                        + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <template id='tEmpty'></template>\n"
                        + "    <template id='tText'>HtmlUnit</template>\n"
                        + "    <template id='tDiv'><div>HtmlUnit</div><div>is great</div></template>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object DocumentFragment]", "0-0", "1-0"})
    public void appendChild() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var template = document.createElement('template');\n"
                        + "        if (!('content' in template)) { alert('not available'); return }\n"

                        + "        template = document.getElementById('tester');\n"
                        + "        alert(template.content);\n"
                        + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"

                        + "        var div = document.createElement('div');\n"
                        + "        template.appendChild(div);\n"
                        + "        alert(template.childNodes.length + '-' + template.content.childNodes.length);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <template id='tester'></template>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", "<p></p>", "", "HtmlUnit", "<div>HtmlUnit</div><div>is great</div>"})
    public void innerHTML() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var template = document.createElement('template');\n"
                        + "        alert(template.innerHTML);\n"

                        + "        var div = document.createElement('div');\n"
                        + "        template.appendChild(div);\n"
                        + "        alert(template.innerHTML);\n"

                        + "        var p = document.createElement('p');\n"
                        + "        if ('content' in template) {\n"
                        + "          template.content.appendChild(p);\n"
                        + "          alert(template.innerHTML);\n"
                        + "        }\n"

                        + "        template = document.getElementById('tEmpty');\n"
                        + "        alert(template.innerHTML);\n"

                        + "        template = document.getElementById('tText');\n"
                        + "        alert(template.innerHTML);\n"

                        + "        template = document.getElementById('tDiv');\n"
                        + "        alert(template.innerHTML);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <template id='tEmpty'></template>\n"
                        + "    <template id='tText'>HtmlUnit</template>\n"
                        + "    <template id='tDiv'><div>HtmlUnit</div><div>is great</div></template>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"<template></template>", "<template>HtmlUnit</template>",
            "<template><div>HtmlUnit</div><div>is great</div></template>"})
    public void innerHTMLIncludingTemplate() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        template = document.getElementById('tEmpty');\n"
                        + "        alert(template.innerHTML);\n"

                        + "        template = document.getElementById('tText');\n"
                        + "        alert(template.innerHTML);\n"

                        + "        template = document.getElementById('tDiv');\n"
                        + "        alert(template.innerHTML);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <div id='tEmpty'><template></template></div>\n"
                        + "    <div id='tText'><template>HtmlUnit</template></div>\n"
                        + "    <div id='tDiv'><template><div>HtmlUnit</div><div>is great</div></template></div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<template></template>", "<template></template>",
            "<template><p></p></template>", "<template id=\"tEmpty\"></template>",
            "<template id=\"tText\">HtmlUnit</template>",
            "<template id=\"tDiv\"><div>HtmlUnit</div><div>is great</div></template>"})
    public void outerHTML() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var template = document.createElement('template');\n"
                        + "        alert(template.outerHTML);\n"

                        + "        var div = document.createElement('div');\n"
                        + "        template.appendChild(div);\n"
                        + "        alert(template.outerHTML);\n"

                        + "        var p = document.createElement('p');\n"
                        + "        if ('content' in template) {\n"
                        + "          template.content.appendChild(p);\n"
                        + "          alert(template.outerHTML);\n"
                        + "        }\n"

                        + "        template = document.getElementById('tEmpty');\n"
                        + "        alert(template.outerHTML);\n"

                        + "        template = document.getElementById('tText');\n"
                        + "        alert(template.outerHTML);\n"

                        + "        template = document.getElementById('tDiv');\n"
                        + "        alert(template.outerHTML);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <template id='tEmpty'></template>\n"
                        + "    <template id='tText'>HtmlUnit</template>\n"
                        + "    <template id='tDiv'><div>HtmlUnit</div><div>is great</div></template>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myTemplate", "null"})
    public void getElementById() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var template = document.getElementById('myTemplate');\n"
                        + "        alert(template.id);\n"

                        + "        outer = document.getElementById('outerDiv');\n"
                        + "        alert(outer);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <template id='myTemplate'>\n"
                        + "      <div id='outerDiv'>HtmlUnit <div id='innerDiv'>is great</div></div>\n"
                        + "    </template>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myTemplate", "0"})
    public void getElementsByTagName() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var template = document.getElementById('myTemplate');\n"
                        + "        alert(template.id);\n"

                        + "        var children = template.getElementsByTagName('div');"
                        + "        alert(children.length);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <template id='myTemplate'>\n"
                        + "      <div id='outerDiv'>HtmlUnit <div id='innerDiv'>is great</div></div>\n"
                        + "    </template>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myTemplate", "0"})
    public void childNodes() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var template = document.getElementById('myTemplate');\n"
                        + "        alert(template.id);\n"

                        + "        alert(template.childNodes.length);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <template id='myTemplate'>\n"
                        + "      <div id='outerDiv'>HtmlUnit <div id='innerDiv'>is great</div></div>\n"
                        + "    </template>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }
}
