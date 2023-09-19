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

package org.loboevolution.js;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.node.js.Window}.
 */
public class WindowUnitTest extends LoboUnitTest {

    @Test
    public void thisIsWindow() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  alert(this);\n"
                + "  try {\n"
                + "    alert(abc);\n"
                + "  } catch(e) {alert('exception')}\n"
                + "  alert(this.abc);\n"
                + "  alert(this.def);\n"
                + "  this.abc = 'hello';\n"
                + "  def = 'world';\n"
                + "  alert(abc);\n"
                + "  alert(this.abc);\n"
                + "  alert(def);\n"
                + "  alert(this.def);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"[object Window]", "exception", "undefined", "undefined", "hello", "hello", "world", "world"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void thisIsWindow2() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  function hello() {\n"
                + "    var x = 1;\n"
                + "  } \n"
                + "  alert(typeof hello);\n"
                + "  alert(typeof window.hello);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"function", "function"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onloadPrototype() {
        final String html
                = "<html><body onload='alert(1)'>\n"
                + "<script>Function.prototype.x='a'; alert(window.onload.x);</script>\n"
                + "</body></html>";
        final String[] messages = {"a", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void atob() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  var data = window.btoa('Hello World!');\n"
                + "  alert(data);\n"
                + "  alert(atob(data));\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"SGVsbG8gV29ybGQh", "Hello World!"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void atobUnicode() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  try {\n"
                + "    window.btoa('I \\u2661 Unicode!');\n"
                + "  } catch(e) {alert('exception')}\n"
                + "  try {\n"
                + "    window.atob('I \\u2661 Unicode!');\n"
                + "  } catch(e) {alert('exception')}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void atobUnicodeOutput() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  var data = window.btoa('3\u00C3\u00AE\u00C2\u00A6');\n"
                + "  alert(data);\n"
                + "  alert(atob(data));\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"M8OuwqY=", "3\u00C3\u00AE\u00C2\u00A6"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void atobControlChar() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  var data = window.btoa('\\t \\u001e');\n"
                + "  alert(data);\n"
                + "  alert(atob(data));\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"CSAe", "\t \u001e"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void atobNull() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  var data = window.btoa(null);\n"
                + "  alert(data);\n"
                + "  alert(atob(data));\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"bnVsbA==", "null"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void atobUndefined() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  var data = window.btoa(undefined);\n"
                + "  alert(data);\n"
                + "  alert(atob(data));\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"dW5kZWZpbmVk", "undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void topLevelProperties() {
        final String[] properties = {
                "getClass", "java", "javax", "javafx", "org", "com", "edu", "net", "JavaAdapter",
                "JavaImporter", "Continuation", "Packages", "XML", "XMLList", "Namespace", "QName", "arguments", "load",
                "loadWithNewGlobal", "exit", "quit", "__FILE__", "__DIR__", "__LINE__", "context", "engine",
                "__noSuchProperty__", "Java", "JSAdapter",
                "NaN", "Infinity", "eval", "print", "parseInt", "parseFloat", "isNaN", "isFinite", "encodeURI",
                "encodeURIComponent", "decodeURI", "decodeURIComponent", "escape", "unescape"};

        final String html = "<html><head></head><body>\n"
                + "<script>\n"
                + "  var props = ['" + String.join("', '", properties) + "'];\n"
                + "  for (var i = 0; i < props.length; i++)\n"
                + "    alert(props[i] + ': ' + typeof(window[props[i]]) + ',' + typeof(eval('this.' + props[i])));\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"getClass: undefined,undefined", "java: undefined,undefined", "javax: undefined,undefined",
                "javafx: undefined,undefined", "org: undefined,undefined", "com: undefined,undefined",
                "edu: undefined,undefined", "net: undefined,undefined", "JavaAdapter: undefined,undefined",
                "JavaImporter: undefined,undefined", "Continuation: undefined,undefined", "Packages: undefined,undefined",
                "XML: undefined,undefined", "XMLList: undefined,undefined", "Namespace: undefined,undefined",
                "QName: undefined,undefined", "arguments: undefined,undefined", "load: undefined,undefined",
                "loadWithNewGlobal: undefined,undefined", "exit: undefined,undefined", "quit: undefined,undefined",
                "__FILE__: undefined,undefined", "__DIR__: undefined,undefined", "__LINE__: undefined,undefined",
                "context: undefined,undefined", "engine: undefined,undefined", "__noSuchProperty__: undefined,undefined",
                "Java: undefined,undefined", "JSAdapter: undefined,undefined",
                "NaN: number,number", "Infinity: number,number", "eval: function,function", "print: function,function",
                "parseInt: function,function", "parseFloat: function,function",
                "isNaN: function,function", "isFinite: function,function", "encodeURI: function,function",
                "encodeURIComponent: function,function", "decodeURI: function,function",
                "decodeURIComponent: function,function", "escape: function,function", "unescape: function,function"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void collectGarbage() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    alert(typeof CollectGarbage);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void eval_localVariable() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var f = document.getElementById('testForm1');\n"
                + "    alert(f.text1.value);\n"
                + "    eval('f.text_' + 1).value = 'changed';\n"
                + "    alert(f.text1.value);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <form id='testForm1'>\n"
                + "    <input id='text1' type='text' name='text_1' value='original'>\n"
                + "  </form>\n"
                + "</body></html>";
        final String[] messages = {"original", "changed"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void windowProperties() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    alert(window.Node);\n"
                + "    alert(window.Element);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "<form name='myForm'></form>\n"
                + "</body></html>";
        final String[] messages = {"function Node() { [native code] }", "function Element() { [native code] }"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void framesLengthZero() {
        final String html
                = "<html><head><script>\n"
                + "alert(window.length);\n"
                + "alert(window.frames.length);\n"
                + "</script></head><body>\n"
                + "</body></html>";
        final String[] messages = {"0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void overwriteFunctions_navigator() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    function navigator() {\n"
                + "      alert('hello');\n"
                + "    }\n"
                + "    navigator();\n"
                + "  }\n"
                + "</script></head><body onload='test()'></body></html>";
        final String[] messages = {"hello"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void framesAreWindows() {
        final String html = "<html><body><iframe name='f'></iframe><script>\n"
                + "alert(window.frames);\n"
                + "alert(window.f);\n"
                + "alert(window.frames.f);\n"
                + "alert(window.length);\n"
                + "alert(window.length == window.frames.length);\n"
                + "alert(window.length == window.frames.frames.length);\n"
                + "alert(window[0]);\n"
                + "alert(window[0] == window.frames[0]);\n"
                + "alert(window[0] == window.frames.frames[0]);\n"
                + "alert(window[1]);\n"
                + "alert(window[1] == window.frames[1]);\n"
                + "alert(window[1] == window.frames.frames[1]);\n"
                + "alert(window.history);\n"
                + "alert(window.history == window.frames.history);\n"
                + "alert(window.history == window.frames.frames.history);\n"
                + "alert(window.self);\n"
                + "alert(window.self == window.frames.self);\n"
                + "alert(window.self == window.frames.frames.self);\n"
                + "</script></body></html>";
        final String[] messages = {"[object Window]", "[object Window]", "[object Window]", "1", "true", "true",
                "[object Window]", "true", "true", "undefined", "true", "true",
                "[object History]", "true", "true", "[object Window]", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void heightsAndWidths() {
        final String html
                = "<html><body onload='test()'><script>\n"
                + "function test() {\n"
                + "  alert(window.innerHeight > 0);\n"
                + "  alert(window.innerHeight == document.body.clientHeight);\n"
                + "  alert(window.outerHeight );\n"
                + "  alert(window.innerHeight);\n"
                + "  alert(window.outerHeight - window.innerHeight);\n"
                + "  alert(window.innerWidth > 0);\n"
                + "  alert(window.innerWidth == document.body.clientWidth);\n"
                + "  alert(window.outerWidth - window.innerWidth);\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true", "true", "132", "true", "true", "16"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setInnerWidth() {
        final String html
                = "<html><body onload='test()'><script>\n"
                + "function test() {\n"
                + "  alert(window.innerWidth > 0);\n"
                + "  window.innerWidth = 1234;\n"
                + "  alert(window.innerWidth);\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true", "1234"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setInnerHeight() {
        final String html
                = "<html><body onload='test()'><script>\n"
                + "function test() {\n"
                + "  alert(window.innerHeight > 0);\n"
                + "  window.innerHeight = 1234;\n"
                + "  alert(window.innerHeight);\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true", "1234"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setOuterWidth() {
        final String html
                = "<html><body onload='test()'><script>\n"
                + "function test() {\n"
                + "  alert(window.outerWidth > 0);\n"
                + "  window.outerWidth = 1234;\n"
                + "  alert(window.outerWidth);\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true", "1234"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void setOuterHeight() {
        final String html
                = "<html><body onload='test()'><script>\n"
                + "function test() {\n"
                + "  alert(window.outerHeight > 0);\n"
                + "  window.outerHeight = 1234;\n"
                + "  alert(window.outerHeight);\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true", "1234"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void changeHeightsAndWidths() {
        final String html
                = "<html><head>\n"
                + "<script language='javascript'>\n"
                + "  function test() {\n"
                + "    var oldHeight = document.body.clientHeight;\n"
                + "    var oldWidth = document.body.clientWidth;\n"
                + "    alert(oldHeight);\n"
                + "    alert(oldWidth);\n"
                + "    newDiv = document.createElement('div');\n"
                + "    document.body.appendChild(newDiv);\n"
                + "    newDiv.style.height = oldHeight + 100 + 'px';\n"
                + "    newDiv.style.width = oldWidth + 100 + 'px';\n"
                + "    alert(document.body.clientHeight);\n"
                + "    alert(document.body.clientWidth);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";
        final String[] messages = {"636", "1600", "619", "1239"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void scrolling() {
        final String html
                = "<html><body onload='test()'>\n"
                + "<div id='d' style='width:10000px;height:10000px;background-color:blue;'></div>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var b = document.body;\n"
                + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
                + "  window.scrollTo(100, 200);\n"
                + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
                + "  window.scrollBy(10, 30);\n"
                + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
                + "  window.scrollTo(-5, -20);\n"
                + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
                + "  if(window.scrollByLines) {\n"
                + "    window.scrollByLines(5);\n"
                + "    alert(b.scrollLeft + ',' + b.scrollTop);\n"
                + "  } else {\n"
                + "    alert('no scrollByLines()');\n"
                + "  }\n"
                + "  window.scroll(0, 0);\n"
                + "  alert(b.scrollLeft + ',' + b.scrollTop);\n"
                + "  if(window.scrollByPages) {\n"
                + "    window.scrollByPages(2);\n"
                + "    alert(b.scrollLeft + ',' + b.scrollTop);\n"
                + "  } else {\n"
                + "    alert('no scrollByPages()');\n"
                + "  }\n"
                + "}\n"
                + "</script></body></html>";
        final String[] messages = {"0,0", "0,0", "0,0", "0,0", "no scrollByLines()", "0,0", "no scrollByPages()"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void pageXOffset() {
        final String html
                = "<html><body onload='test()'><script>\n"
                + "function test() {\n"
                + "  window.scrollBy(5, 10);\n"
                + "  alert(window.pageXOffset);\n"
                + "  alert(window.pageYOffset);\n"
                + "  alert(window.scrollX);\n"
                + "  alert(window.scrollY);\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"0", "0", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void typeof() {
        final String html
                = "<html><body><script>\n"
                + "  alert(typeof window);\n"
                + "</script></body></html>";
        final String[] messages = {"object"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void eval() {
        final String html
                = "<html><body onload='test()'><script>\n"
                + "function test() {\n"
                + "  var x = new Object();\n"
                + "  x.a = 'Success';\n"
                + "  try {\n"
                + "    alert(window['eval']('x.a'));\n"
                + "  } catch(e) {alert('exception')}\n"
                + "  try {\n"
                + "    alert(window.eval('x.a'));\n"
                + "  } catch(e) {alert('exception')}\n"
                + "  try {\n"
                + "    alert(eval('x.a'));\n"
                + "  } catch(e) {alert('exception')}\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"exception", "exception", "Success"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void firedEventEqualsEriginalEvent() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  var myEvent;\n"
                        + "  var listener = function(x) {\n"
                        + "    alert(x == myEvent);\n"
                        + "    x.foo = 'I was here';\n"
                        + "  }\n"
                        + "  window.addEventListener('click', listener, false);\n"
                        + "  myEvent = document.createEvent('HTMLEvents');\n"
                        + "  myEvent.initEvent('click', true, true);\n"
                        + "  window.dispatchEvent(myEvent);\n"
                        + "  alert(myEvent.foo);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head><body onload='test()'>\n"
                        + "</body></html>";
        final String[] messages = {"true", "I was here"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void thisEquals() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  alert(this === window);\n"
                        + "  alert(window === this);\n"
                        + "  alert(this == window);\n"
                        + "  alert(window == this);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</head><body onload='test()'>\n"
                        + "</body></html>";
        final String[] messages = {"true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onbeforeunload() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  alert(window.onbeforeunload);\n"
                        + "  var handle = function() {};\n"
                        + "  window.onbeforeunload = handle;\n"
                        + "  alert(typeof window.onbeforeunload);\n"
                        + "  window.onbeforeunload = null;\n"
                        + "  alert(window.onbeforeunload);\n"
                        + "  window.onbeforeunload = undefined;\n"
                        + "  alert(window.onbeforeunload);\n"
                        + "  \n"
                        + "}\n"
                        + "</script>\n"
                        + "</head><body onload='test()'>\n"
                        + "</body></html>";
        final String[] messages = {null, "function", null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void functionPrototypeArguments() {
        final String html =
                "<html>\n"
                        + "<body onload='test()'>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + " \n"
                        + "    Function.prototype.doAlerts = function() {\n"
                        + "      alert(this == o.f);\n"
                        + "      alert(arguments ? arguments.length : 'null');\n"
                        + "      alert(this.arguments ? this.arguments.length : 'null');\n"
                        + "    }\n"
                        + " \n"
                        + "    var o = function() {};\n"
                        + "    o.f = function(x, y, z) {\n"
                        + "      this.f.doAlerts();\n"
                        + "      alert(arguments ? arguments.length : 'null');\n"
                        + "      alert(this.arguments ? this.arguments.length : 'null');\n"
                        + "    }\n"
                        + "    o.f('a', 'b');\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"true", "0", "2", "2", "null"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void functionPrototypeArguments2() {
        final String html =
                "<html>\n"
                        + "<body onload='test()'>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + " \n"
                        + "    Function.prototype.doAlerts = function() {\n"
                        + "      alert(this == o.f);\n"
                        + "      alert(arguments);\n"
                        + "      alert(this.arguments);\n"
                        + "    }\n"
                        + " \n"
                        + "    var o = function() {};\n"
                        + "    o.f = function(x, y, z) {\n"
                        + "      alert(this == o);\n"
                        + "      alert(arguments);\n"
                        + "      alert(this.arguments);\n"
                        + "      this.f.doAlerts();\n"
                        + "    }\n"
                        + "    o.f('a', 'b');\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"true", "[object Arguments]", null, "true", "[object Arguments]", "[object Arguments]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void functionPrototypeArguments3() {
        final String html =
                "<html>\n"
                        + "<body onload='test()'>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var o = function() {};\n"
                        + "    o.x = function() {\n"
                        + "      alert(this == o);\n"
                        + "      alert(arguments);\n"
                        + "      alert(this.arguments);\n"
                        + "    }\n"
                        + "    o.f = function(x, y, z) {\n"
                        + "      alert(this == o);\n"
                        + "      alert(arguments);\n"
                        + "      alert(this.arguments);\n"
                        + "      this.x();\n"
                        + "    }\n"
                        + "    o.f('a', 'b');\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"true", "[object Arguments]", null, "true", "[object Arguments]", null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onError() {
        final String html
                = "<script>\n"
                + "  alert(window.onerror);\n"
                + "  window.onerror = function() { alert(arguments.length); };\n"
                + "  alert(typeof window.onerror);\n"
                + "  try { alert(undef); } catch(e) { /* caught, so won't trigger onerror */ }\n"
                + "</script>";

        final String[] messages = {null, "function"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onErrorExceptionInstance() {
        final String html
                = "<html>\n"
                + "<script>\n"
                + "  window.onerror = function(messageOrEvent, source, lineno, colno, error) {\n"
                + "    alert(typeof messageOrEvent + ' ' + typeof source + ' '"
                + " + lineno + ' ' + typeof colno + ' ' + typeof error);\n"
                + "  };\n"
                + "</script>\n"
                + "<script>throw 'string';</script>\n"
                + "<script>throw {'object':'property'};</script>\n"
                + "</html>";

        final String[] messages = {"string string 8 number string", "string string 9 number object"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onErrorExceptionInstance2() {
        final String html
                = "<html>\n"
                + "<script>\n"
                + "  window.onerror = function(messageOrEvent, source, lineno, colno, error) {\n"
                + "    alert(typeof messageOrEvent + ' ' + typeof source + ' '"
                + " + lineno + ' ' + typeof colno + ' ' + typeof error);\n"
                + "  };\n"
                + "</script>\n"
                + "<script>does.not.exist();</script>\n"
                + "<script>eval('syntax[error');</script>\n"
                + "</html>";
        final String[] messages = {"string string 8 number object", "string string 1 number object"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onErrorModifyObject() {
        final String html
                = "<html>\n"
                + "<script>\n"
                + "  window.onerror = function(messageOrEvent, source, lineno, colno, error) {\n"
                + "    error.property = 'success'\n"
                + "    alert(error.property);\n"
                + "  };\n"
                + "</script>\n"
                + "<script>throw {};</script>\n"
                + "</html>";
        final String[] messages = {"success"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getComputedStyle() {
        final String html = "<html><body>\n"
                + "<div id='myDiv'></div>\n"
                + "<script>\n"
                + "  var e = document.getElementById('myDiv');\n"
                + "  alert(window.getComputedStyle(e, null).color);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"rgb(0, 0, 0)"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getComputedStyleWithComputedColor() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <style>div.x { color: red; }</style>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    var e = document.getElementById('d');\n"
                        + "    alert(window.getComputedStyle(e, '').color);\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <div id='d' class='x'>foo bar</div>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"rgb(255, 0, 0)"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getComputedStyleSvg() {
        final String html = "<html><body>\n"
                + "  <svg xmlns='http://www.w3.org/2000/svg' id='myId' version='1.1'></svg>\n"
                + "<script>\n"
                + "  var e = document.getElementById('myId');\n"
                + "  alert(window.getComputedStyle(e, null).color);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"rgb(0, 0, 0)"};
        checkHtmlAlert(html, messages);

    }

    @Test
    public void hangingObjectCallOnWindowProxy() {
        final String html = "<html><body>\n"
                + "<iframe id='it'></iframe>;\n"
                + "<script>\n"
                + "  Object(top);\n"
                + "  alert(window.foo);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void equalsString() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  alert('foo' == window);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void equalsInt() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  var i = 0;\n"
                + "  alert(i == window);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onchangenoHandler() {
        final String html
                = "<html><body><script>\n"
                + "  alert('onchange' in window);\n"
                + "  alert(window.onchange);\n"
                + "</script></body></html>";
        final String[] messages = {"true", null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void location() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  alert(location === window.location);\n"
                + "  alert(location === document.location);\n"
                + "  alert(window.location === document.location);\n"
                + "</script></head>\n"
                + "<body></body></html>";
        final String[] messages = {"true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void enumeratedProperties() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var str = '';\n"
                + "    alert(window);\n"
                + "    alert(Window);\n"
                + "    var str = '';\n"
                + "    for (var i in Window) {\n"
                + "      str += i + ', ';\n"
                + "    }\n"
                + "    alert(str);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object Window]", "function Window() { [native code] }", "TEMPORARY, PERSISTENT, "};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void dump() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "<script>\n"
                + "  alert(typeof window.dump);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void showModalDiaalert() {
        final String html
                = "<html><body><script>\n"
                + "  alert(typeof window.showModalDialog);\n"
                + "</script></body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void showModelessDiaalert() {
        final String html
                = "<html><body><script>\n"
                + "  alert(typeof window.showModelessDialog);\n"
                + "</script></body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void find() {
        final String html
                = "<html><body><script>\n"
                + "  alert(typeof window.find);\n"
                + "</script></body></html>";
        final String[] messages = {"function"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getComputedStyleElementDocument() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  tt { display: none; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var iframe = document.createElement('iframe');\n"
                + "    document.body.appendChild(iframe);\n"
                + "\n"
                + "    var doc = iframe.contentWindow.document;\n"
                + "    var tt = doc.createElement('tt');\n"
                + "    doc.body.appendChild(tt);\n"
                + "    alert(window.getComputedStyle(tt, null).display);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>\n";
        final String[] messages = {"inline"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void calledTwice() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "<script>\n"
                + "  alert('a');\n"
                + "  alert('b');\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"a", "b"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void constructor() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(new Window());\n"
                + "    } catch(e) {alert('exception')}\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void clientInformation() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(window.clientInformation);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"[object Navigator]"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void xmlNotInWindow() {
        final String html
                = "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:me='http://mysite'>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert('XML' in window);\n"
                + "    alert('XMLList' in window);\n"
                + "    alert('Namespace' in window);\n"
                + "    alert('QName' in window);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <app:dIv xmlns='http://anotherURL'></app:dIv>\n"
                + "</body></html>";
        final String[] messages = {"false", "false", "false", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void formByName() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    alert(window.form1.name);\n"
                        + "    alert(form1.name);\n"
                        + "    alert(window.form2.length);\n"
                        + "    alert(form2.length);\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <form name='form1'></form>\n"
                        + "  <form name='form2'></form>\n"
                        + "  <form name='form2'></form>\n"
                        + "</body></html>";
        final String[] messages = {"form1", "form1", "2", "2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void elementsByName() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    dump(window.element1);\n"
                        + "    dump(element1);\n"
                        + "  }\n"
                        + "  function dump(c) {\n"
                        + "    alert(c.length);\n"
                        + "    for (i = 0; i < c.length; i++) {\n"
                        + "      alert(c.item(i).nodeName);\n"
                        + "    }\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <abbr name='element1'></abbr>\n"
                        + "  <acronym name='element1'></acronym>\n"
                        + "  <a name='element1'></a>\n"
                        + "  <address name='element1'></address>\n"
                        + "  <article name='element1'></article>\n"
                        + "  <audio name='element1'></audio>\n"
                        + "  <bgsound name='element1'>\n"
                        + "  <base name='element1'>\n"
                        + "  <basefont name='element1'>\n"
                        + "  <bdo name='element1'></bdo>\n"
                        + "  <big name='element1'></big>\n"
                        + "  <blink name='element1'></blink>\n"
                        + "  <blockquote name='element1'></blockquote>\n"
                        + "  <b name='element1'></b>\n"
                        + "  <br name='element1'>\n"
                        + "  <button name='element1'></button>\n"
                        + "  <canvas name='element1'></canvas>\n"
                        + "  <caption name='element1'></caption>\n"
                        + "  <center name='element1'></center>\n"
                        + "  <cite name='element1'></cite>\n"
                        + "  <code name='element1'></code>\n"
                        + "  <datalist name='element1'></datalist>\n"
                        + "  <dfn name='element1'></dfn>\n"
                        + "  <del name='element1'></del>\n"
                        + "  <dir name='element1'></dir>\n"
                        + "  <div name='element1'></div>\n"
                        + "  <dl name='element1'>\n"
                        + "    <dt name='element1'></dt>\n"
                        + "    <dd name='element1'></dd>\n"
                        + "  </dl>\n"
                        + "  <embed name='element1'>\n"
                        + "  <em name='element1'></em>\n"
                        + "  <fieldset name='element1'></fieldset>\n"
                        + "  <figcaption name='element1'></figcaption>\n"
                        + "  <figure name='element1'></figure>\n"
                        + "  <font name='element1'></font>\n"
                        + "  <form name='element1'></form>\n"
                        + "  <footer name='element1'></footer>\n"
                        + "  <h1 name='element1'></h1>\n"
                        + "  <h2 name='element1'></h2>\n"
                        + "  <h3 name='element1'></h3>\n"
                        + "  <h4 name='element1'></h4>\n"
                        + "  <h5 name='element1'></h5>\n"
                        + "  <h6 name='element1'></h6>\n"
                        + "  <header name='element1'></header>\n"
                        + "  <hr name='element1'>\n"
                        + "  <q name='element1'></q>\n"
                        + "  <ruby name='element1'>\n"
                        + "    <rt name='element1'></rt>\n"
                        + "    <rp name='element1'></rp>\n"
                        + "  </ruby>\n"
                        + "  <image name='element1'></image>\n"
                        + "  <img name='element1'>\n"
                        + "  <input name='element1'>\n"
                        + "  <ins name='element1'></ins>\n"
                        + "  <i name='element1'></i>\n"
                        + "  <kbd name='element1'></kbd>\n"
                        + "  <keygen name='element1'>\n"
                        + "  <label name='element1'></label>\n"
                        + "  <legend name='element1'></legend>\n"
                        + "  <listing name='element1'></listing>\n"
                        + "  <link name='element1'>\n"
                        + "  <map name='element1'>\n"
                        + "    <area name='element1'>\n"
                        + "  </map>\n"
                        + "  <marquee name='element1'></marquee>\n"
                        + "  <mark name='element1'></mark>\n"
                        + "  <menu name='element1'></menu>\n"
                        + "  <meter name='element1'></meter>\n"
                        + "  <multicol name='element1'></multicol>\n"
                        + "  <nav name='element1'></nav>\n"
                        + "  <nextid name='element1'></nextid>\n"
                        + "  <nobr name='element1'></nobr>\n"
                        + "  <noembed name='element1'></noembed>\n"
                        + "  <noframes name='element1'></noframes>\n"
                        + "  <noscript name='element1'></noscript>\n"
                        + "  <object name='element1'>\n"
                        + "    <param name='element1'>\n"
                        + "  </object>\n"
                        + "  <ol name='element1'>\n"
                        + "    <li name='element1'></li>\n"
                        + "  </ol>\n"
                        + "  <output name='element1'></output>\n"
                        + "  <p name='element1'></p>\n"
                        + "  <pre name='element1'></pre>\n"
                        + "  <progress name='element1'></progress>\n"
                        + "  <s name='element1'></s>\n"
                        + "  <samp name='element1'></samp>\n"
                        + "  <script name='element1'></script>\n"
                        + "  <section name='element1'></section>\n"
                        + "  <select name='element1'>\n"
                        + "    <optgroup name='element1'>\n"
                        + "      <option name='element1'></option>\n"
                        + "    </optgroup>\n"
                        + "  </select>\n"
                        + "  <small name='element1'></small>\n"
                        + "  <source name='element1'>\n"
                        + "  <spacer name='element1'></spacer>\n"
                        + "  <span name='element1'></span>\n"
                        + "  <strike name='element1'></strike>\n"
                        + "  <strong name='element1'></strong>\n"
                        + "  <style name='element1'></style>\n"
                        + "  <sub name='element1'></sub>\n"
                        + "  <sup name='element1'></sup>\n"
                        + "  <table name='element1'>\n"
                        + "    <colgroup name='element1'>\n"
                        + "      <col name='element1'></col>\n"
                        + "    </colgroup>\n"
                        + "    <thead name='element1'>\n"
                        + "      <tr name='element1'>\n"
                        + "        <th name='element1'></th>\n"
                        + "      </tr>\n"
                        + "    </thead>\n"
                        + "    <tbody name='element1'>\n"
                        + "      <tr name='element1'>\n"
                        + "        <td name='element1'></td>\n"
                        + "      </tr>\n"
                        + "    </tbody>\n"
                        + "    <tfoot name='element1'></tfoot>\n"
                        + "  </table>\n"
                        + "  <textarea name='element1'></textarea>\n"
                        + "  <tt name='element1'></tt>\n"
                        + "  <time name='element1'></time>\n"
                        + "  <u name='element1'></u>\n"
                        + "  <ul name='element1'></ul>\n"
                        + "  <var name='element1'></var>\n"
                        + "  <video name='element1'></video>\n"
                        + "  <wbr name='element1'>\n"
                        + "  <xmp name='element1'></xmp>\n"
                        + "</body></html>";
        final String[] messages = {"5", "EMBED", "FORM", "IMG", "IMG", "OBJECT", "5", "EMBED", "FORM", "IMG", "IMG", "OBJECT"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void elementsByName_changedAfterGet() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var collection1 = window.image1;\n"
                        + "    var collection2 = image1;\n"
                        + "    if (!collection1) {\n"
                        + "      collection1 = [];\n"
                        + "    }\n"
                        + "    if (!collection2) {\n"
                        + "      collection2 = [];\n"
                        + "    }\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    var newImage1 = document.createElement('img');\n"
                        + "    newImage1.name = 'image1';\n"
                        + "    document.getElementById('outer1').appendChild(newImage1);\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"

                        + "    var newImage2 = document.createElement('img');\n"
                        + "    newImage2.name = 'image1';\n"
                        + "    document.getElementById('outer2').insertBefore(newImage2, null);\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    var newImage3 = document.createElement('img');\n"
                        + "    newImage3.name = 'image1';\n"
                        + "    document.getElementById('outer3').replaceChild(newImage3, document.getElementById('inner3'));\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    document.getElementById('outer4').outerHTML = '<img name=\"image1\">';\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    document.getElementById('outer5').innerHTML = '<img name=\"image1\">';\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    document.getElementById('outer6').insertAdjacentHTML('beforeend', '<img name=\"image1\">');\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    document.getElementById('image3').setAttribute('name', 'image1');\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    var newAttr = document.createAttribute('name');\n"
                        + "    newAttr.nodeValue = 'image1';\n"
                        + "    document.getElementById('image4').setAttributeNode(newAttr);\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    document.getElementById('image5').setAttributeNS(null, 'name', 'image1');\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    document.getElementById('outer1').removeChild(newImage1);\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <img name='image1'>\n"
                        + "  <img name='image1'>\n"
                        + "  <div id='outer1'></div>\n"
                        + "  <div id='outer2'></div>\n"
                        + "  <div id='outer3'><div id='inner3'></div></div>\n"
                        + "  <div id='outer4'></div>\n"
                        + "  <div id='outer5'></div>\n"
                        + "  <div id='outer6'></div>\n"
                        + "  <img id='image2'>\n"
                        + "  <img id='image3'>\n"
                        + "  <img id='image4'>\n"
                        + "  <img id='image5'>\n"
                        + "</body></html>";
        final String[] messages = {"2-2", "3-3", "4-4", "5-5", "6-6", "7-7", "8-8", "9-9", "10-10", "11-11", "10-10"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void elementsByName_changedAfterGet2() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    var collection1 = window.image1;\n"
                        + "    var collection2 = image1;\n"
                        + "    if (!collection1) {\n"
                        + "      collection1 = [];\n"
                        + "    }\n"
                        + "    if (!collection2) {\n"
                        + "      collection2 = [];\n"
                        + "    }\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "    document.getElementById('image2').name = 'image1';\n"
                        + "    alert(collection1.length + '-' + collection2.length);\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <img name='image1'>\n"
                        + "  <img name='image1'>\n"
                        + "  <img id='image2'>\n"
                        + "</body></html>";
        final String[] messages = {"2-2", "3-3"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void elementsById() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    alert(window.form1.id);\n"
                        + "    alert(form1.id);\n"
                        + "    alert(window.frame1.name);\n"
                        + "    alert(frame1.name);\n"
                        + "    alert(window.input1.id);\n"
                        + "    alert(input1.id);\n"
                        + "    alert(window.anchor1.id);\n"
                        + "    alert(anchor1.id);\n"
                        + "    alert(window.image1.id);\n"
                        + "    alert(image1.id);\n"
                        + "    alert(window.element1.id);\n"
                        + "    alert(element1.id);\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "  <form id='form1'></form>\n"
                        + "  <iframe id='frame1' name='f1'></iframe>\n"
                        + "  <input type='text' id='input1' value='1'/>\n"
                        + "  <a id='anchor1'></a>\n"
                        + "  <img id='image1'>\n"
                        + "  <div id='element1'></table>\n"
                        + "</body></html>";
        final String[] messages = {"form1", "form1", "f1", "f1", "input1", "input1", "anchor1", "anchor1", "image1", "image1", "element1", "element1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onLoadFunction() {
        final String html =
                "<html>\n"
                        + "<head><title>test</title>\n"
                        + "<script>\n"
                        + "  function test()\n"
                        + "  {\n"
                        + "    alert('test');\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "<script>\n"
                        + "  var oldOnLoad = window.onload;\n"
                        + "  window.onload = test2;\n"
                        + "  function test2()\n"
                        + "  {\n"
                        + "    alert('test2');\n"
                        + "    oldOnLoad();\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"test2", "test"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onloadNotAFunction() {
        final String html =
                "<html><body><script>\n"
                        + "window.onload = new function() {alert('a')};\n"
                        + "window.onload = undefined;\n"
                        + "alert(window.onload);\n"
                        + "</script></body></html>";
        final String[] messages = {"a", null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void addOnLoadEventListener() {
        final String html =
                "<html>\n"
                        + "<head><title>test</title>\n"
                        + "<script>\n"
                        + "  function test1() {alert('test1');}\n"
                        + "  function test2() {alert('test2');}\n"
                        + "  function test3() {alert('test3');}\n"
                        + "  alert(window.addEventListener == null);\n"
                        + "  alert(window.removeEventListener == null);\n"
                        + "  window.addEventListener('load', test1, true);\n"
                        + "  window.addEventListener('load', test1, true);\n"
                        + "  window.addEventListener('load', test2, true);\n"
                        + "  window.addEventListener('load', test3, true);\n"
                        + "  window.removeEventListener('load', test3, true);\n"
                        + "</script></head>\n"
                        + "<body onload='alert(\"onload\")'></body></html>";
        final String[] messages = {"false", "false", "test1", "test2", "onload"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void attachOnLoadEvent() {
        final String html =
                "<html>\n"
                        + "<head><title>test</title>\n"
                        + "<script>\n"
                        + "  function test1(_e) {alert('test1, param null: ' + (_e == null));}\n"
                        + "  function test2() {alert('test2');}\n"
                        + "  function test3() {alert('test3');}\n"
                        + "  alert(window.attachEvent == null);\n"
                        + "  alert(window.detachEvent == null);\n"
                        + "  try {\n"
                        + "    window.attachEvent('onload', test1);\n"
                        + "    window.attachEvent('onload', test1);\n"
                        + "    window.attachEvent('onload', test2);\n"
                        + "    window.attachEvent('onload', test3);\n"
                        + "    window.detachEvent('onload', test3);\n"
                        + "  } catch (e) { alert('exception'); }\n"
                        + "</script></head>\n"
                        + "<body onload='alert(\"onload\")'></body></html>";
        final String[] messages = {"true", "true", "exception", "onload"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void detachEventInAttachEvent() {
        final String html =
                "<html>\n"
                        + "<head><title>test</title>\n"
                        + "<script>\n"
                        + "function test() {\n"
                        + "  window.detachEvent('onload', test);\n"
                        + "  alert('detached');\n"
                        + "}\n"
                        + "try {\n"
                        + "  window.attachEvent('onload', test);\n"
                        + "} catch (e) { alert('exception'); }\n"
                        + "</script></head>\n"
                        + "<body></body></html>";
        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void windowName() {
        final String html =
                "<html>\n"
                        + "<head><title>window.name test</title></head>\n"
                        + "<body>\n"
                        + "<script>\n"
                        + "  alert('window.name before: ' + window.name);\n"
                        + "  window.name = 'main';\n"
                        + "  alert('window.name after: ' + window.name);\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"window.name before: ", "window.name after: main"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void viewport() {
        final String html =
                "<html>\n"
                        + "<head></head>\n"
                        + "<body>\n"
                        + "<script>\n"
                        + "  alert(typeof window.innerWidth);\n"
                        + "  alert(typeof window.innerHeight);\n"
                        + "  alert(typeof window.outerWidth);\n"
                        + "  alert(typeof window.outerHeight);\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        final String[] messages = {"number", "number", "number", "number"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void evalScopeLocal() {
        final String html =
                "<html><body><form id='formtest'><input id='element' value='elementValue'/></form>\n"
                        + "<script>\n"
                        + "var docPatate = 'patate';\n"
                        + "function test() {\n"
                        + "  var f = document.forms['formtest'];\n"
                        + "  alert(eval(\"document.forms['formtest'].element.value\"));\n"
                        + "  alert(f.element.value);\n"
                        + "  alert(eval('f.element.value'));\n"
                        + "}\n"
                        + "test();\n"
                        + "</script>\n"
                        + "</body></html>";
        final String[] messages = {"elementValue", "elementValue", "elementValue"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void evalScopeEvent() {
        final String html =
                "<html><body onload='test()'><script>\n"
                        + "   function test() {\n"
                        + "     var s = 'string';\n"
                        + "     var f = 'initial';\n"
                        + "     eval('f = function() {alert(s);}');\n"
                        + "     invoke(f);\n"
                        + "   }\n"
                        + "   function invoke(fn) {\n"
                        + "     fn();\n"
                        + "   }\n"
                        + "</script></body></html>";
        final String[] messages = {"string"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void functionEquality() {
        final String html =
                "<html><body>\n"
                        + "<script>\n"
                        + "  alert(window.focus == window.focus);\n"
                        + "</script>\n"
                        + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void onLoadContext() {
        final String html =
                "<html><body><script>\n"
                        + "var x = function() { alert(this==window) };\n"
                        + "window.onload = x;\n"
                        + "</script></body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void undefinedProperty() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    alert(window['something']);\n"
                        + "    alert(typeof window['something']);\n"
                        + "    alert(typeof window['something']=='undefined');\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "</body></html>";
        final String[] messages = {"undefined", "undefined", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void stop() {
        final String html =
                "<html><head><script>\n"
                        + "function test() {\n"
                        + "  try {\n"
                        + "    window.stop();\n"
                        + "    alert(true);\n"
                        + "  } catch (e) {\n"
                        + "    alert('error');\n"
                        + "  }\n"
                        + "}\n"
                        + "</script></head><body onload='test()'>\n"
                        + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void devicePixelRatio() {
        final String html
                = "<html><head><script>\n"
                + "  function test() {\n"
                + "    alert(window.devicePixelRatio);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"1"};
        checkHtmlAlert(html, messages);
    }
}
