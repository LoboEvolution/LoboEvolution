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

/**
 * Tests for MutationObserver.
 */
@ExtendWith(AlertsExtension.class)
public class MutationObserverTest extends LoboUnitTest {


    @Test
    @Alerts("exception")
    public void observeNullNode() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var observer = new MutationObserver(function(mutations) {});\n"
                + "\n"
                + "  try {\n"
                + "    observer.observe(div, {});\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void observeNullInit() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var observer = new MutationObserver(function(mutations) {});\n"
                + "\n"
                + "  try {\n"
                + "    observer.observe(div);\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void observeEmptyInit() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var observer = new MutationObserver(function(mutations) {});\n"
                + "\n"
                + "  try {\n"
                + "    observer.observe(div, {});\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "childList", "attributes", "characterData"})
    public void observeRequiredMissingInit() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var observer = new MutationObserver(function(mutations) {});\n"
                + "\n"
                + "  try {\n"
                + "    observer.observe(div, {subtree: true});\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "  try {\n"
                + "    observer.observe(div, {childList: true});\n"
                + "   alert('childList');\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "  try {\n"
                + "    observer.observe(div, {attributes: true});\n"
                + "   alert('attributes');\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "  try {\n"
                + "    observer.observe(div, {characterData: true});\n"
                + "   alert('characterData');\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"old", "new"})
    public void characterData() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var observer = new MutationObserver(function(mutations) {\n"
                + "    mutations.forEach(function(mutation) {\n"
                + "     alert(mutation.oldValue);\n"
                + "     alert(mutation.target.textContent);\n"
                + "    });\n"
                + "  });\n"
                + "\n"
                + "  observer.observe(div, {\n"
                + "    characterData: true,\n"
                + "    characterDataOldValue: true,\n"
                + "    subtree: true\n"
                + "  });\n"
                + "\n"
                + "  div.firstChild.textContent = 'new';\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "new"})
    public void characterDataNoOldValue() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var observer = new MutationObserver(function(mutations) {\n"
                + "    mutations.forEach(function(mutation) {\n"
                + "     alert(mutation.oldValue);\n"
                + "     alert(mutation.target.textContent);\n"
                + "    });\n"
                + "  });\n"
                + "\n"
                + "  observer.observe(div, {\n"
                + "    characterData: true,\n"
                + "    subtree: true\n"
                + "  });\n"
                + "\n"
                + "  div.firstChild.textContent = 'new';\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void characterDataNoSubtree() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var observer = new MutationObserver(function(mutations) {\n"
                + "    mutations.forEach(function(mutation) {\n"
                + "     alert(mutation.oldValue);\n"
                + "     alert(mutation.target.textContent);\n"
                + "    });\n"
                + "  });\n"
                + "\n"
                + "  observer.observe(div, {\n"
                + "    characterData: true,\n"
                + "    characterDataOldValue: true\n"
                + "  });\n"
                + "\n"
                + "  div.firstChild.textContent = 'new';\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>old</div>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"attributes", "ltr"})
    public void attributes() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var observer = new MutationObserver(function(mutations) {\n"
                + "    mutations.forEach(function(mutation) {\n"
                + "     alert(mutation.type);\n"
                + "     alert(mutation.oldValue);\n"
                + "    });\n"
                + "  });\n"
                + "\n"
                + "  observer.observe(div, {\n"
                + "    attributes: true,\n"
                + "    attributeFilter: ['dir'],\n"
                + "    attributeOldValue: true\n"
                + "  });\n"
                + "\n"
                + "  div.dir = 'rtl';\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv' dir='ltr'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"heho", "attributes", "value", "null", "x", "abc",
            "heho", "attributes", "value", "null", "y", "abc"})
    public void attributeValue() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var config = { attributes: true, childList: true, characterData: true, subtree: true };\n"
                + "    var observer = new MutationObserver(function(mutations) {\n"
                + "      mutations.forEach(function(mutation) {\n"
                + "       alert(mutation.type);\n"
                + "       alert(mutation.attributeName);\n"
                + "       alert(mutation.oldValue);\n"
                + "       alert(mutation.target.getAttribute(\"value\"));\n"
                + "       alert(mutation.target.value);\n"
                + "      });\n"
                + "    });\n"
                + "    observer.observe(document.getElementById('tester'), config);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <input id='tester' value=''>\n"
                + "  <button id='doAlert' onclick='alert(\"heho\");'>DoAlert</button>\n"
                + "  <button id='doIt' "
                + "onclick='document.getElementById(\"tester\").setAttribute(\"value\", \"x\")'>"
                + "DoIt</button>\n"
                + "  <button id='doItAgain' "
                + " onclick='document.getElementById(\"tester\").setAttribute(\"value\", \"y\")'>"
                + "DoItAgain</button>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLHeadingElement]-attributes")
    public void attributeValue2() {
        final String html = "<html><head><script>\n"
                + "  function makeRed() {\n"
                + "    document.getElementById('headline').setAttribute('style', 'color: red');\n"
                + "  }\n"
                + "  function print(mutation) {\n"
                + "   alert(mutation.target + '-' + mutation.type);\n"
                + "  }\n"
                + "  function alert(x) {\n"
                + "    document.getElementById('log').value += x + '\\n';\n"
                + "  }\n"
                + "  function test() {\n"
                + "    var mobs = new MutationObserver(function(mutations) {\n"
                + "      mutations.forEach(print)\n"
                + "    });\n"
                + "    mobs.observe(document.getElementById('container'), {\n"
                + "      attributes: true,\n"
                + "      childList: true,\n"
                + "      characterData: true,\n"
                + "      subtree: true\n"
                + "    });\n"
                + "    document.addEventListener('beforeunload', function() {\n"
                + "      mobs.disconnect();\n"
                + "    });\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='container'>\n"
                + "    <h1 id='headline' style='font-style: italic'>Some headline</h1>\n"
                + "    <input id='id1' type='button' onclick='makeRed()' value='Make Red'>\n"
                + "  </div>\n"
                + "  <textarea id='log' cols='80' rows='40'></textarea>\n"
                + "</body></html>\n";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"before", "after div", "after text", "div observed", "text observed"})
    public void callbackOrder() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var div = document.getElementById('myDiv');\n"
                + "  var divObserver = new MutationObserver(function() {\n"
                + "     alert('div observed');\n"
                + "    });\n"
                + "\n"
                + "  divObserver.observe(div, { attributes: true });\n"
                + "\n"
                + "  var text = document.createTextNode('')\n"
                + "  var txtObserver = new MutationObserver(function() {\n"
                + "       alert('text observed');\n"
                + "    });\n"
                + "  txtObserver.observe(text, { characterData: true });"
                + "\n"
                + " alert('before');\n"
                + "  div.style = 'background-color: red';\n"
                + " alert('after div');\n"
                + "  text.data = 42;\n"
                + " alert('after text');\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv' style='color: green'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Content")
    public void callbackRequiresStackSetup() {
       final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "\n"
                + "  var text = document.createTextNode('')\n"
                + "  var txtObserver = new MutationObserver(function() {\n"
                + "        window.location.href = 'content.html'"
                + "    });\n"
                + "  txtObserver.observe(text, { characterData: true });"
                + "\n"
                + "  text.data = 42\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv' dir='ltr'>old</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object MutationObserver]", "", "false"})
    public void webKitMutationObserver() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var observer = new MutationObserver(function() {});\n"
                + "  var wkObserver = '';\n"
                + "  if (typeof(WebKitMutationObserver) == 'function') {\n"
                + "    wkObserver = new WebKitMutationObserver(function() {});\n"
                + "  }\n"
                + " alert(observer);\n"
                + " alert(wkObserver);\n"
                + " alert(Object.getPrototypeOf(observer) == Object.getPrototypeOf(wkObserver));\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
