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
package org.loboevolution.node;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.node.DocumentFragment}.
 */

public class DocumentFragmentUnitTest extends LoboUnitTest {

    @Test
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
                + "    alert(window.getComputedStyle(d, null));\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"[object CSSStyleDeclaration]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void querySelector() {
        final String html = "<html><head><title>First</title>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=edge'>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var frag = document.createDocumentFragment();\n"
                + "  var d = document.createElement('div');\n"
                + "  frag.appendChild(d);\n"
                + "  alert(frag.querySelectorAll('div').length);\n"
                + "  alert(frag.querySelectorAll('div')[0].tagName);\n"
                + "  alert(frag.querySelector('div').tagName);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<div id='root'>\n"
                + "</div>\n"
                + "</body></html>";
        final String[] messages = {"1", "DIV", "DIV"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void children() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var fragment = document.createDocumentFragment();\n"
                + "  fragment.textContent = '';\n"
                + "  alert(fragment.childNodes.length);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void url() throws Exception {
        final String html = "<!DOCTYPE><html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var fragment = document.createDocumentFragment();\n"
                + "  alert(fragment);\n"
                + "  alert(fragment.URL);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object DocumentFragment]", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void firstElementChild() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var fragment = document.createDocumentFragment();\n"
                + "    alert(fragment.childElementCount);\n"
                + "    alert(fragment.firstElementChild);\n"
                + "    alert(fragment.lastElementChild);\n"
                + "    if (fragment.childElementCount === undefined) { return; };\n"
                + "    var d = document.createElement('div');\n"
                + "    d.id = 'myDiv';\n"
                + "    fragment.appendChild(d);\n"
                + "    var e = document.createElement('input');\n"
                + "    e.id = 'first';\n"
                + "    d.appendChild(e);\n"
                + "    alert(fragment.childElementCount);\n"
                + "    alert(fragment.firstElementChild.id);\n"
                + "    alert(fragment.lastElementChild.id);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"0", null, null, "1", "myDiv", "myDiv"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getElementById() throws Exception {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "  function test() {\n"
                + "    var fragment = document.createDocumentFragment();\n"
                + "    var d = document.createElement('div');\n"
                + "    d.id = 'myDiv';\n"
                + "    fragment.appendChild(d);\n"
                + "    var e = document.createElement('input');\n"
                + "    e.id = 'first';\n"
                + "    d.appendChild(e);\n"
                + "    alert(document.getElementById(''));\n"
                + "    alert(document.getElementById(undefined));\n"
                + "    alert(document.getElementById(null));\n"
                + "    alert(document.getElementById('unknown'));\n"
                + "    alert(document.getElementById('myDiv'));\n"
                + "    alert(document.getElementById('mydiv'));\n"
                + "    alert(document.getElementById('first'));\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div id='myDiv'>\n"
                + "  <div></div>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {null, null, null, null, "[object HTMLDivElement]", null, null};
        checkHtmlAlert(html, messages);
    }
}
