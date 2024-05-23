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
package org.loboevolution.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.js.xml.XMLHttpRequest;

/**
 * Tests for {@link XMLHttpRequest}.
 */
@ExtendWith(AlertsExtension.class)
public class XMLHttpRequestTest extends LoboUnitTest {

    @Test
    public void syncUse() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function testSync() {\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "       alert(request.readyState);\n"
                +  "       alert(request.responseType);\n"
                +  "        request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "       alert(request.readyState);\n"
                +  "        request.send('');\n"
                +  "       alert(request.readyState);\n"
                +  "       alert(request.responseText);\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='testSync()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1: 0-", "2: ", "3: 200-OK"})
    public void statusSync() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "<script>\n" 
                + "  var xhr = new XMLHttpRequest();\n"
                +  "  alertStatus('1: ');\n"
                +  "  xhr.open('GET', '" + URL_XML + "foo.xml" + "', false);\n"
                +  " alert('2: ');\n"
                +  "  xhr.send();\n"
                +  "  alertStatus('3: ');\n"
                +  "  function alertStatus(prefix) {\n"
                +  "    var msg = prefix;\n"
                +  "    try {\n"
                +  "      msg = msg + xhr.status + '-';\n"
                +  "    } catch(e) { msg = msg + 'ex: status' + '-' }\n"
                +  "    try {\n"
                +  "      msg = msg + xhr.statusText;;\n"
                +  "    } catch(e) { msg = msg + 'ex: statusText' }\n"
                +  "   alert(msg);\n"
                +  "  }\n"
                +  "</script>\n"
                +  "  </head>\n"
                +  "  <body></body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1: 0-", "2: 0-", "#1: 0-", "3: 0-", "4: 0-", "#2: 200-OK", "#3: 200-OK", "#4: 200-OK"})
    public void statusAsync() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <title>XMLHttpRequest Test</title>\n"
                +  "<script>\n" 
                + "  var xhr = new XMLHttpRequest();\n"
                +  "  function test() {\n"
                +  "    try {\n"
                +  "      logStatus('1: ');\n"
                +  "      xhr.onreadystatechange = onReadyStateChange;\n"
                +  "      logStatus('2: ');\n"
                +  "      xhr.open('GET', '" + URL_XML + "foo.xml" + "', true);\n"
                +  "      logStatus('3: ');\n"
                +  "      xhr.send();\n"
                +  "      logStatus('4: ');\n"
                +  "    } catch(e) {\n"
                +  "      document.getElementById('log').value += e + '\\n';\n"
                +  "    }\n"
                +  "  }\n"
                +  "  function onReadyStateChange() {\n"
                +  "    logStatus('#' + xhr.readyState + ': ');\n"
                +  "  }\n"
                +  "  function logStatus(prefix) {\n"
                +  "    var msg = prefix;\n"
                +  "    try {\n"
                +  "      msg = msg + xhr.status + '-';\n"
                +  "    } catch(e) { msg = msg + 'ex: status' + '-' }\n"
                +  "    try {\n"
                +  "      msg = msg + xhr.statusText;\n"
                +  "    } catch(e) { msg = msg + 'ex: statusText' }\n"
                +  "    document.getElementById('log').value += msg;\n"
                +  "    alert(document.getElementById('log').value);\n"
                +  "  }\n"
                +  "</script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "    <textarea id='log' cols='80' rows='40'></textarea>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"#1", "#2", "#3", "#4"})
    public void asyncIsDefault() {
        final String html = "<html>\n"
        +  "<body>\n"
        +  "  <textarea id='log' cols='80' rows='40'></textarea>\n"
        +  "<script>\n" 
        + "    function alert(x) {\n"
        +  "      document.getElementById('log').value += x + '\\n';\n"
        +  "    }\n"
        +  "var xhr = new XMLHttpRequest();\n"
        +  "function onReadyStateChange() {\n"
        +  "  if(xhr.readyState == 4) {\n"
        +  "   alert('#4');\n"
        +  "  }\n"
        +  "}\n"
        +  "try {\n"
        +  "  alert('#1');\n"
        +  "  xhr.onreadystatechange = onReadyStateChange;\n"
        +  "  xhr.open('GET', '" + URL_XML + "foo.xml" + "');\n"
        +  "  alert('#2');\n"
        +  "  xhr.send();\n"
        +  "  alert('#3');\n"
        +  "} catch(e) {alert(e); }\n"
        +  "</script>\n"
        +  "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"orsc1", "open-done", "send-done",
            "orsc2", "orsc3", "orsc4", "4", "<a>b</a>", "[object XMLHttpRequest]"})
    public void onload() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        var xhr = new XMLHttpRequest();\n"
                +  "        xhr.onreadystatechange = function() {alert('orsc' + xhr.readyState); };\n"
                +  "        xhr.onload = function() {alert(xhr.readyState);alert(xhr.responseText);alert(this); }\n"
                +  "        xhr.open('GET', '" + URL_XML + "foo.xml" + "', true);\n"
                +  "        alert('open-done');\n"
                +  "        xhr.send('');\n"
                +  "        alert('send-done');\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "    <textarea id='log' cols='80' rows='40'></textarea>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null"})
    public void responseHeaderBeforeSend() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "        alert(request.getResponseHeader('content-length'));\n"
                +  "        request.open('GET', '" + URL_XML + "foo.xml" + "', false);\n"
                +  "       alert(request.getResponseHeader('content-length'));\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body></body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("bla bla")
    public void responseTextNotXml() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "  request.open('GET', 'foo.txt', false);\n"
        +  "  request.send('');\n"
        +  " alert(request.responseText);\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void responseXMLTextHtml() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "  function test() {\n"
        +  "    var xhr = new XMLHttpRequest();\n"
        +  "    xhr.open('GET', '" + URL_XML + "foo.xml" + "', false);\n"
        +  "    xhr.send('');\n"
        +  "    try {\n"
        +  "     alert(xhr.responseXML);\n"
        +  "    } catch(e) {alert('exception'); }\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void responseXMLTextXml() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "  function test() {\n"
        +  "    var xhr = new XMLHttpRequest();\n"
        +  "    xhr.open('GET', '" + URL_XML + "foo.xml" + "', false);\n"
        +  "    xhr.send('');\n"
        +  "    try {\n"
        +  "     alert(xhr.responseXML);\n"
        +  "    } catch(e) {alert('exception'); }\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void responseXMLApplicationXml() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "  function test() {\n"
        +  "    var xhr = new XMLHttpRequest();\n"
        +  "    xhr.open('GET', '" + URL_XML + "foo.xml" + "', false);\n"
        +  "    xhr.send('');\n"
        +  "    try {\n"
        +  "     alert(xhr.responseXML);\n"
        +  "    } catch(e) {alert('exception'); }\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void responseXMLApplicationXhtmlXml() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "  function test() {\n"
        +  "    var xhr = new XMLHttpRequest();\n"
        +  "    xhr.open('GET', '" + URL_XML + "foo.xml" + "', false);\n"
        +  "    xhr.send('');\n"
        +  "    try {\n"
        +  "     alert(xhr.responseXML);\n"
        +  "    } catch(e) {alert('exception'); }\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void responseXMLApplicationSvgXml() {
        final String html = "<html><head>\n"
        +  "<script>"
                +  " function test() {\n"
        +  "    var xhr = new XMLHttpRequest();\n"
        +  "    xhr.open('GET', '" + URL_XML + "foo.xml" + "', false);\n"
        +  "    xhr.send('');\n"
        +  "    try {\n"
        +  "     alert(xhr.responseXML);\n"
        +  "    } catch(e) {alert('exception'); }\n"
        +  "  }\n"
        +  "</script></head><body onload='test()'>\n"
        +  "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "someAttr", "undefined", "undefined"})
    public void responseXML2() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "  request.open('GET', '" + URL_XML + "foo.xml" + "', false);\n"
        +  "  request.send('');\n"
        +  "  var childNodes = request.responseXML.childNodes;\n"
        +  " alert(childNodes.length);\n"
        +  "  var rootNode = childNodes[0];\n"
        +  " alert(rootNode.attributes[0].nodeName);\n"
        +  " alert(rootNode.attributes[0].text);\n"
        +  " alert(rootNode.attributes[0].xml);\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("received: null")
    public void responseXMLSiteNotExisting() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "try {\n"
        +  "  request.open('GET', 'http://this.doesnt.exist/foo.xml', false);\n"
        +  "  request.send('');\n"
        +  "} catch(e) {\n"
        +  " alert('received: ' + request.responseXML);\n"
        +  "}\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    public void sendNull() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "  request.open('GET', 'foo.txt', false);\n"
        +  "  request.send(null);\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    public void sendGETWithContent() {
        send("'foo'");
    }


    @Test
    public void sendNoArg() {
        send("");
    }


    private void send(final String sendArg) {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "  request.open('GET', 'foo.txt', false);\n"
        +  "  request.send(" + sendArg + ");\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "false"})
    public void overrideMimeType() {
        final String html = "<html><head>\n"
            +  "<script>\n"
            +  "function test() {\n"
            +  "try {\n"
            +  "  var request = new XMLHttpRequest();\n"
            +  "  request.open('GET','" + URL_XML + "foo.xml" + "', false);\n"
            +  "  request.send('');\n"
            +  "  alert(request.responseXML == null);\n"
            +  "  request.open('GET','" + URL_XML + "foo.xml" + "', false);\n"
            +  "  request.overrideMimeType('text/xml');\n"
            +  "  request.send('');\n"
            +  "  alert(request.responseXML == null);\n"
            +  "} catch (e) {alert('exception'); }\n"
            +  "}\n"
            +  "</script>\n"
            +  "</head>\n"
            +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "exception"})
    public void overrideMimeTypeAfterSend() {
        final String html = "<html><head>\n"
            +  "<script>\n"
            +  "function test() {\n"
            +  "  var request = new XMLHttpRequest();\n"
            +  "  request.open('GET','" + URL_XML + "foo.xml" + "', false);\n"
            +  "  request.send('');\n"
            +  " alert(request.responseXML == null);\n"
            +  "  try {\n"
            +  "    request.overrideMimeType('text/xml');\n"
            +  "   alert('overwritten');\n"
            +  "  } catch (e) {alert('exception'); }\n"
            +  "}\n"
            +  "</script>\n"
            +  "</head>\n"
            +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("27035")
    public void overrideMimeTypeCharset() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "try {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "  request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
        +  "  request.overrideMimeType('text/plain; charset=GBK');\n"
        +  "  request.send('');\n"
        +  " alert(request.responseText.charCodeAt(0));\n"
        +  "} catch (e) {alert('exception'); }\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("27035")
    public void overrideMimeTypeCharsetUpperCase() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "try {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "  request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
        +  "  request.overrideMimeType('text/plain; chaRSet=GBK');\n"
        +  "  request.send('');\n"
        +  " alert(request.responseText.charCodeAt(0));\n"
        +  "} catch (e) {alert('exception'); }\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("40644")
    public void overrideMimeTypeCharsetEmpty() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "try {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "  request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
        +  "  request.overrideMimeType('text/plain; charset=');\n"
        +  "  request.send('');\n"
        +  " alert(request.responseText.charCodeAt(0));\n"
        +  "} catch (e) {alert('exception'); }\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("40644")
    public void overrideMimeTypeCharsetWrong() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "  try {\n"
        +  "    var request = new XMLHttpRequest();\n"
        +  "    request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
        +  "    request.overrideMimeType('text/plain; charset=abcdefg');\n"
        +  "    request.send('');\n"
        +  "    var text = request.responseText;\n"
        +  "    for (var i = 0; i < text.length; i++) {\n"
        +  "     alert(text.charCodeAt(i));\n"
        +  "    }\n"
        +  "  } catch (e) {alert('exception'); }\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"ibcdefg", "xxxxxfg"})
    public void replaceOnTextData() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var request;\n"
                +  "      function testReplace() {\n"
                +  "        request = new XMLHttpRequest();\n"
                +  "        request.onreadystatechange = onReadyStateChange;\n"
                +  "        request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "        request.send('');\n"
                +  "      }\n"
                +  "      function onReadyStateChange() {\n"
                +  "        if (request.readyState == 4){\n"
                +  "          var theXML = request.responseXML;\n"
                +  "          var theDoc = theXML.documentElement;\n"
                +  "          var theElements = theDoc.getElementsByTagName('update');\n"
                +  "          var theUpdate = theElements[0];\n"
                +  "          var theData = theUpdate.firstChild.data;\n"
                +  "          theResult = theData.replace('a','i');\n"
                +  "         alert(theResult);\n"
                +  "          theResult = theData.replace('abcde', 'xxxxx');\n"
                +  "         alert(theResult);\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='testReplace()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("selectNodes not available")
    public void responseXMLSelectNodesIE() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "        request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "        request.send('');\n"
                +  "        if (!request.responseXML.selectNodes) {alert('selectNodes not available'); return }\n"
                +  "       alert(request.responseXML.selectNodes('//content').length);\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Element]", "myID", "blah", "span", "[object XMLDocument]", "[object XMLDocument]"})
    public void responseXMLGetElementById2() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "        request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "        request.send('');\n"
                +  "        if (request.responseXML.getElementById) {\n"
                +  "         alert(request.responseXML.getElementById('id1'));\n"
                +  "         alert(request.responseXML.getElementById('myID').id);\n"
                +  "         alert(request.responseXML.getElementById('myID').innerHTML);\n"
                +  "         alert(request.responseXML.getElementById('myID').tagName);\n"
                +  "         alert(request.responseXML.getElementById('myID').ownerDocument);\n"
                +  "          if (request.responseXML.getElementById('myID').getRootNode) {\n"
                +  "           alert(request.responseXML.getElementById('myID').getRootNode());\n"
                +  "          } elsealert('-');\n"
                +  "        } else  {\n"
                +  "         alert('responseXML.getElementById not available');\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Element]", "[object Element]", "[object HTMLBodyElement]",
            "[object HTMLSpanElement]", "[object XMLDocument]", "[object XMLDocument]", "undefined"})
    public void responseXMLGetElementById() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "        request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "        request.send('');\n"
                +  "        var doc = request.responseXML;\n"
                +  "       alert(doc.documentElement);\n"
                +  "       alert(doc.documentElement.childNodes[0]);\n"
                +  "       alert(doc.documentElement.childNodes[1]);\n"
                +  "        if (doc.getElementById) {\n"
                +  "         alert(doc.getElementById('out'));\n"
                +  "         alert(doc.getElementById('out').ownerDocument);\n"
                +  "          if (doc.getElementById('out').getRootNode) {\n"
                +  "           alert(doc.getElementById('out').getRootNode());\n"
                +  "          } elsealert('-');\n"
                +  "        }\n"
                +  "       alert(doc.documentElement.childNodes[1].xml);\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("olé")
    public void defaultEncodingIsUTF8() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "        request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "        request.send('');\n"
                +  "       alert(request.responseText);\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("myID")
    public void responseXMLHtmlSelect() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var request = new XMLHttpRequest();\n"
                +  "          request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "          request.send('');\n"
                +  "         alert(request.responseXML.getElementById('myID').id);\n"
                +  "        } catch (e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("myInput")
    public void responseXMLHtmlForm() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var request = new XMLHttpRequest();\n"
                +  "          request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "          request.send('');\n"
                +  "         alert(request.responseXML.getElementById('myID').myInput.name);\n"
                +  "        } catch (e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "undefined"})
    public void caseSensitivityXMLHttpRequest() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "  try {\n"
        +  "    var req = new XMLHttpRequest();\n"
        +  "   alert(req.readyState);\n"
        +  "   alert(req.reAdYsTaTe);\n"
        +  "  } catch (e) {alert('exception'); }\n"
        +  "}\n"
        +  "</script></head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"39", "27035", "65533", "39"})
    public void overrideMimeTypeCharsetAll() {
        final String html = "<html><head>\n"
        +  "<script>\n"
        +  "function test() {\n"
        +  "try {\n"
        +  "  var request = new XMLHttpRequest();\n"
        +  "  request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
        +  "  request.overrideMimeType('text/plain; charset=GBK');\n"
        +  "  request.send('');\n"
        +  "  for (var i = 0; i < request.responseText.length; i++) {\n"
        +  "   alert(request.responseText.charCodeAt(i));\n"
        +  "  }\n"
        +  "} catch (e) {alert('exception'); }\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object ProgressEvent]")
    public void loadParameter() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function someLoad(e) {\n"
                +  "       alert(e);\n"
                +  "      }\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var request = new XMLHttpRequest();\n"
                +  "          request.onload = someLoad;\n"
                +  "          request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "          request.send('');\n"
                +  "        } catch (e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"someLoad [object ProgressEvent]", "load", "false", "11", "0"})
    public void addEventListener() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function someLoad(event) {\n"
                +  "       alert('someLoad ' + event);\n"
                +  "       alert(event.type);\n"
                +  "       alert(event.lengthComputable);\n"
                +  "       alert(event.loaded);\n"
                +  "       alert(event.total);\n"
                +  "      }\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var request = new XMLHttpRequest();\n"
                +  "          request.addEventListener('load', someLoad, false);\n"
                +  "          request.open('GET', '" + URL_HTML + "foo.html" + "', false);\n"
                +  "          request.send('');\n"
                +  "        } catch (e) {alert(e); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"someLoad [object ProgressEvent]", "load", "false", "11", "0"})
    public void addEventListenerDetails() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function someLoad(event) {\n"
                +  "       alert('someLoad ' + event);\n"
                +  "       alert(event.type);\n"
                +  "       alert(event.lengthComputable);\n"
                +  "       alert(event.loaded);\n"
                +  "       alert(event.total);\n"
                +  "      }\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var request = new XMLHttpRequest();\n"
                +  "          request.addEventListener('load', someLoad, false);\n"
                +  "          request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "          request.send('');\n"
                +  "        } catch (e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("function")
    public void addEventListenerCaller() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function someLoad(event) {\n"
                +  "        var caller = arguments.callee.caller;\n"
                +  "        alert(typeof caller == 'function' ? 'function' : caller);\n"
                +  "      }\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var request = new XMLHttpRequest();\n"
                +  "          request.addEventListener('load', someLoad, false);\n"
                +  "          request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"
                +  "          request.send('');\n"
                +  "        } catch (e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object XMLHttpRequestUpload]")
    public void upload() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function test() {\n"
                +  "        try {\n"
                +  "          var request = new XMLHttpRequest();\n"
                +  "         alert(request.upload);\n"
                +  "        } catch (e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Object]", "undefined", "undefined",
            "function() { return !0 }",
            "function set onreadystatechange() { [native code] }",
            "true", "true"})
    public void defineProperty() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var request;\n"
                +  "      function test() {\n"
                +  "        Object.defineProperty(XMLHttpRequest.prototype, 'onreadystatechange', {\n"
                +  "                                 enumerable: !0,\n"
                +  "                                 configurable: !0,\n"
                +  "                                 get: function() { return !0 }\n"
                +  "                             });\n"
                +  "       var desc = Object.getOwnPropertyDescriptor(XMLHttpRequest.prototype, 'onreadystatechange');\n"
                +  "       alert(desc);\n"
                +  "       alert(desc.value);\n"
                +  "       alert(desc.writable);\n"
                +  "       alert(desc.get);\n"
                +  "       alert(desc.set);\n"
                +  "       alert(desc.configurable);\n"
                +  "       alert(desc.enumerable);\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object XMLHttpRequest]")
    public void defineProperty2() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var request;\n"
                +  "      function test() {\n"
                +  "        var t = Object.getOwnPropertyDescriptor(XMLHttpRequest.prototype, 'onreadystatechange');\n"
                +  "        var res = Object.defineProperty(XMLHttpRequest.prototype, 'onreadystatechange', t);\n"
                +  "       alert(res);\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("error")
    public void setRequestHeaderNotOpend() {
        final String html
                = "<html>\n"
        +  "<head>\n"
        +  "<script>\n"
        +  "function doTest() {\n"
        +  "  try {\n"
        +  "    var xhr = new XMLHttpRequest();\n"
        +  "    xhr.setRequestHeader('Content-Type', 'text/jpeg');\n"
        +  "   alert('done');\n"
        +  "  } catch (e) {\n"
        +  "   alert('error');\n"
        +  "  }\n"
        +  "}\n"
        +  "</script>\n"
        +  "</head>\n"
        +  "<body onload='doTest()'>\n"
        +  "</body>\n"
        +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "arraybuffer", "blob", "json", "text", "text", "text", "text", "text", ""})
    public void responseTypeSetBeforeOpen() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function testSync() {\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "       alert(request.responseType);\n"
                +  "      try {\n"
                +  "        request.responseType = 'arraybuffer';\n"
                +  "       alert(request.responseType);\n"
                +  "        request.responseType = 'blob';\n"
                +  "       alert(request.responseType);\n"
                +  "        request.responseType = 'json';\n"
                +  "       alert(request.responseType);\n"
                +  "        request.responseType = 'text';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'JsON';\n"
                +  "       alert(request.responseType);\n"

                +  "        request.responseType = 'unknown';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = null;\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = undefined;\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = '';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='testSync()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "exception", "exception", "exception", "exception", "exception",
            "", "", "", "", "exception"})
    public void responseTypeSetAfterOpenSync() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function testSync() {\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "        request.open('GET','" + URL_HTML + "foo.html" + "', false);\n"

                +  "       alert(request.responseType);\n"

                +  "      try {\n"
                +  "        request.responseType = 'arraybuffer';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'blob';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'json';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'text';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'document';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'JsON';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'unknown';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = null;\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = undefined;\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = '';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='testSync()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "arraybuffer", "blob", "json", "text", "document",
            "document", "document", "document", "document", ""})
    public void responseTypeSetAfterOpenAsync() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      function testSync() {\n"
                +  "        var request = new XMLHttpRequest();\n"
                +  "        request.open('GET','" + URL_HTML + "foo.html" + "', true);\n"

                +  "       alert(request.responseType);\n"
                +  "      try {\n"
                +  "        request.responseType = 'arraybuffer';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'blob';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'json';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'text';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'document';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'JsON';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = 'unknown';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = null;\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = undefined;\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"

                +  "      try {\n"
                +  "        request.responseType = '';\n"
                +  "       alert(request.responseType);\n"
                +  "      } catch(e) {alert('exception'); }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='testSync()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", "exception", "exception"})
    public void responseTextInvalidResponseType() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.responseType = 'arraybuffer';\n"
                +  "        try {\n"
                +  "         alert(xhr.responseText);\n"
                +  "        } catch(ex) {alert('exception'); }\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.responseText);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "", "<xml>\\n<content>blah</content>\\n</xml>"})
    public void responseResponseTypeDefault() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "       alert(xhr.responseType);\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "text", "<xml>\\n<content>blah</content>\\n</xml>"})
    public void responseResponseTypeText() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"
                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'text';\n"
                +  "       alert(xhr.responseType);\n"
                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"
                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "arraybuffer", "[object ArrayBuffer]", "36"})
    public void responseResponseTypeArrayBuffer() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'arraybuffer';\n"
                +  "       alert(xhr.responseType);\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "           alert(xhr.response.byteLength);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "arraybuffer", "[object ArrayBuffer]", "36"})
    public void responseResponseTypeArrayBufferGzipIncrease() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'arraybuffer';\n"
                +  "       alert(xhr.responseType);\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "           alert(xhr.response.byteLength);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "arraybuffer", "[object ArrayBuffer]", "72"})
    public void responseResponseTypeArrayBufferGzipDecrease() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'arraybuffer';\n"
                +  "       alert(xhr.responseType);\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "           alert(xhr.response.byteLength);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "arraybuffer", "[object ArrayBuffer]", "0"})
    public void responseResponseTypeArrayBufferEmpty() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"
                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'arraybuffer';\n"
                +  "       alert(xhr.responseType);\n"
                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"
                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "           alert(xhr.response.byteLength);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "blob", "[object Blob]", "36", "text/xml"})
    public void responseResponseTypeBlob() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'blob';\n"
                +  "       alert(xhr.responseType);\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "           alert(xhr.response.size);\n"
                +  "           alert(xhr.response.type);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "blob", "[object Blob]", "0"})
    public void responseResponseTypeBlobEmpty() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"
                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'blob';\n"
                +  "       alert(xhr.responseType);\n"
                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"
                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "           alert(xhr.response.size);\n"
                +  "          } catch(ex) {alert('exception'); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "json", "[object Object]", "Unit", "{\"Html\":\"Unit\"}"})
    public void responseResponseTypeJson() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'json';\n"
                +  "       alert(xhr.responseType);\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "           alert(xhr.response.Html);\n"
                +  "           alert(JSON.stringify(xhr.response));\n"
                +  "          } catch(ex) {alert('exception' + ex); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "json", "null"})
    public void responseResponseTypeJsonEmpty() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'json';\n"
                +  "       alert(xhr.responseType);\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "          } catch(ex) {alert('exception' + ex); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "document", "[object XMLDocument]"})
    public void responseResponseTypeDocumentXml() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "        alert(xhr.responseText);\n"
                +  "        xhr.open('GET','" + URL_XML + "foo.xml" + "', true);\n"
                +  "        xhr.responseType = 'document';\n"
                +  "        alert(xhr.responseType);\n"
                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"
                +  "      function onStateChange(e) {\n"
                        +  "           alert(xhr);\n"
                        +  "           alert(xhr.readyState);\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "          } catch(ex) {alert('exception' + ex); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "document", "[object HTMLDocument]"})
    public void responseResponseTypeDocumentHtml() {
        final String html =
                "<html>\n"
                +  "  <head>\n"
                +  "    <script>\n"
                +  "      var xhr;\n"
                +  "      function test() {\n"
                +  "        xhr = new XMLHttpRequest();\n"
                +  "       alert(xhr.responseText);\n"

                +  "        xhr.open('GET','" + URL_HTML + "foo.html" + "', true);\n"
                +  "        xhr.responseType = 'document';\n"
                +  "       alert(xhr.responseType);\n"

                +  "        xhr.onreadystatechange = onStateChange;\n"
                +  "        xhr.send('');\n"
                +  "      }\n"

                +  "      function onStateChange(e) {\n"
                +  "        if (xhr.readyState == 4) {\n"
                +  "          try {\n"
                +  "           alert(xhr.response);\n"
                +  "          } catch(ex) {alert('exception' + ex); }\n"
                +  "        }\n"
                +  "      }\n"
                +  "    </script>\n"
                +  "  </head>\n"
                +  "  <body onload='test()'>\n"
                +  "  </body>\n"
                +  "</html>";

        checkHtmlAlert(html);
    }
}
