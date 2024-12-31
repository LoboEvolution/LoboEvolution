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
import org.loboevolution.html.node.DocumentFragment;

/**
 * Tests for {@link DocumentFragment}.
 */
@ExtendWith(AlertsExtension.class)
public class DocumentFragmentTest extends LoboUnitTest {

    @Test
    @Alerts("[object CSSStyleDeclaration]")
    public void getComputedStyleOnChild() {
        final String html = "<html><head><style>\n"
                + "  body > div { background-color: green#FF0000; }\n"
                + "</style></head>\n"
                + "<body>\n"
                + "<script>\n"
                + "  try {\n"
                + "    var frag = document.createDocumentFragment();\n"
                + "    var d = document.createElement('div');\n"
                + "    frag.appendChild(d);\n"
                + "   alert(window.getComputedStyle(d, null));\n"
                + "  } catch (e) {alert('exception'); }\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    public void createElement() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function test() {\n"
                + "        var frag = document.createDocumentFragment();\n"
                + "        if (frag.createElement) {\n"
                + "          var d = frag.createElement('div');\n"
                + "         alert(d.tagName);\n"
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
    @Alerts({"1", "DIV", "DIV"})
    public void querySelector() {
        final String html = "<html><head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var frag = document.createDocumentFragment();\n"
                + "  var d = document.createElement('div');\n"
                + "  frag.appendChild(d);\n"
                + " alert(frag.querySelectorAll('div').length);\n"
                + " alert(frag.querySelectorAll('div')[0].tagName);\n"
                + " alert(frag.querySelector('div').tagName);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<div id='root'>\n"
                + "</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("0")
    public void children() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var fragment = document.createDocumentFragment();\n"
                + "  fragment.textContent = '';\n"
                + " alert(fragment.childNodes.length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"[object DocumentFragment]", "undefined"})
    public void url() {
        final String html = "<!DOCTYPE><html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var fragment = document.createDocumentFragment();\n"
                + " alert(fragment);\n"
                + " alert(fragment.URL);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "null", "null", "1", "myDiv", "myDiv"})
    public void firstElementChild() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var fragment = document.createDocumentFragment();\n"
                + "   alert(fragment.childElementCount);\n"
                + "   alert(fragment.firstElementChild);\n"
                + "   alert(fragment.lastElementChild);\n"
                + "    if (fragment.childElementCount === undefined) { return; };\n"
                + "    var d = document.createElement('div');\n"
                + "    d.id = 'myDiv';\n"
                + "    fragment.appendChild(d);\n"
                + "    var e = document.createElement('input');\n"
                + "    e.id = 'first';\n"
                + "    d.appendChild(e);\n"
                + "   alert(fragment.childElementCount);\n"
                + "   alert(fragment.firstElementChild.id);\n"
                + "   alert(fragment.lastElementChild.id);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "null", "null", "0", "null", "null", "1", "myDiv", "myDiv"})
    public void firstElementChildTextNode() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var fragment = document.createDocumentFragment();\n"
                + "   alert(fragment.childElementCount);\n"
                + "   alert(fragment.firstElementChild);\n"
                + "   alert(fragment.lastElementChild);\n"
                + "    if (fragment.childElementCount === undefined) { return; };\n"
                + "    var txt = document.createTextNode('HtmlUnit');\n"
                + "    fragment.appendChild(txt);\n"
                + "   alert(fragment.childElementCount);\n"
                + "   alert(fragment.firstElementChild);\n"
                + "   alert(fragment.lastElementChild);\n"
                + "    var d = document.createElement('div');\n"
                + "    d.id = 'myDiv';\n"
                + "    fragment.appendChild(d);\n"
                + "   alert(fragment.childElementCount);\n"
                + "   alert(fragment.firstElementChild.id);\n"
                + "   alert(fragment.lastElementChild.id);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "null", "null", "[object HTMLDivElement]", "null", "null"})
    public void getElementById() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    var fragment = document.createDocumentFragment();\n"
                + "    var d = document.createElement('div');\n"
                + "    d.id = 'myDiv';\n"
                + "    fragment.appendChild(d);\n"
                + "    var e = document.createElement('input');\n"
                + "    e.id = 'first';\n"
                + "    d.appendChild(e);\n"
                + "   alert(document.getElementById(''));\n"
                + "   alert(document.getElementById(undefined));\n"
                + "   alert(document.getElementById(null));\n"
                + "   alert(document.getElementById('unknown'));\n"
                + "   alert(document.getElementById('myDiv'));\n"
                + "   alert(document.getElementById('mydiv'));\n"
                + "   alert(document.getElementById('first'));\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='myDiv'>\n"
                + "  <div></div>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void ownerDocument() {
        final String content = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var fragment = document.createDocumentFragment();\n"
                + "     alert(document === fragment.ownerDocument);\n"
                + "      var div = document.createElement('div');\n"
                + "      fragment.appendChild(div);\n"
                + "     alert(div.ownerDocument === document);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>bla\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(content);
    }

    @Test
    @Alerts({"false", "true", "false", "true", "false", "true", "true", "false"})
    public void getRootNode() {
        final String content = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      if (!document.body.getRootNode) {\n"
                + "       alert('-'); return;\n"
                + "      }\n"
                + "      var fragment = document.createDocumentFragment();\n"
                + "     alert(document === fragment.getRootNode());\n"
                + "     alert(fragment === fragment.getRootNode());\n"
                + "      var div = document.createElement('div');\n"
                + "      fragment.appendChild(div);\n"
                + "     alert(document === div.getRootNode());\n"
                + "     alert(fragment === div.getRootNode());\n"
                + "      document.body.appendChild(fragment);\n"
                + "     alert(document === fragment.getRootNode());\n"
                + "     alert(fragment === fragment.getRootNode());\n"
                + "     alert(document === div.getRootNode());\n"
                + "     alert(fragment === div.getRootNode());\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>bla\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(content);
    }
}
