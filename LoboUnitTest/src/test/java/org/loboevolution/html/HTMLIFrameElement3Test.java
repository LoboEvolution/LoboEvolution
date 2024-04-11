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
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLIFrameElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * Tests for {@link HTMLIFrameElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLIFrameElement3Test extends LoboUnitTest {

    @Test
    @Alerts("false")
    public void style() {
        final String html
                = "<html>\n"
                + "<html><head>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('myIFrame').style == undefined);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<iframe id='myIFrame' src='about:blank'></iframe>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "myIFrame"})
    public void referenceFromJavaScript() {
        final String html
                = "<html>\n"
                + "<html><head>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  alert(window.frames.length);\n"
                + "  alert(window.frames['myIFrame'].name);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<iframe name='myIFrame' src='about:blank'></iframe></body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Regression test for bug 1562872.
     *
     * @throws Exception if the test fails
     */
    @Test
    @Alerts({"about:blank", "about:blank"})
    public void directAccessPerName() {
        final String html
                = "<html>\n"
                + "<html><head>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  alert(myIFrame.location);\n"
                + "  alert(Frame.location);\n"
                + "}\n</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "<iframe name='myIFrame' src='about:blank'></iframe>\n"
                + "<iframe name='Frame' src='about:blank'></iframe>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDocument]", "true"})
    public void contentDocument() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.getElementById('myFrame').contentDocument);\n"
                + "      alert(document.getElementById('myFrame').contentDocument == frames.foo.document);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <iframe name='foo' id='myFrame' src='about:blank'></iframe>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void frameElement() {
        final String html
                = "<html>\n"
                + "<html><head>\n"
                + "<script>\n"                + "function test() {\n"
                + "  alert(document.getElementById('myFrame') == frames.foo.frameElement);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<iframe name='foo' id='myFrame' src='about:blank'></iframe>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false", "true", "true", "true", "object", "object"})
    public void writeToIFrame() {
        final String html
                = "<html>\n"
                + "<html><body onload='test()'><script>\n"
                + "  function test() {\n"
                + "    var frame = document.createElement('iframe');\n"
                + "    document.body.appendChild(frame);\n"
                + "    var win = frame.contentWindow;\n"
                + "    var doc = frame.contentWindow.document;\n"
                + "    alert(win == window);\n"
                + "    alert(doc == document);\n"
                + "    \n"
                + "    doc.open();\n"
                + "    doc.write(\"<html><body><input type='text'/></body></html>\");\n"
                + "    doc.close();\n"
                + "    var win2 = frame.contentWindow;\n"
                + "    var doc2 = frame.contentWindow.document;\n"
                + "    alert(win == win2);\n"
                + "    alert(doc == doc2);\n"
                + "    \n"
                + "    var input = doc.getElementsByTagName('input')[0];\n"
                + "    var input2 = doc2.getElementsByTagName('input')[0];\n"
                + "    alert(input == input2);\n"
                + "    alert(typeof input);\n"
                + "    alert(typeof input2);\n"
                + "  }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("about:blank")
    public void setSrc_JavascriptUrl() {
        final String html
                = "<html>\n"
                + "<html><head>\n"
                + "<script>\n"                + "  function test() {\n"
                + "    document.getElementById('iframe1').src = 'javascript:void(0)';\n"
                + "    alert(window.frames[0].location);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "<iframe id='iframe1'></iframe>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "100", "foo", "20%", "-5", "30.2", "400", "abc", "-5", "100.2", "10%", "-12.56"})
    public void width() {
        final String html
                = "<html>\n"
                + "<html><body>\n"
                + "<iframe id='i1'></iframe>\n"
                + "<iframe id='i2' width='100'></iframe>\n"
                + "<iframe id='i3' width='foo'></iframe>\n"
                + "<iframe id='i4' width='20%'></iframe>\n"
                + "<iframe id='i5' width='-5'></iframe>\n"
                + "<iframe id='i6' width='30.2'></iframe>\n"
                + "    <script>\n"
                + "function set(e, value) {\n"
                + "  try {\n"
                + "    e.width = value;\n"
                + "  } catch (e) {\n"
                + "    alert('error');\n"
                + "  }\n"
                + "}\n"
                + "var i1 = document.getElementById('i1');\n"
                + "var i2 = document.getElementById('i2');\n"
                + "var i3 = document.getElementById('i3');\n"
                + "var i4 = document.getElementById('i4');\n"
                + "var i5 = document.getElementById('i5');\n"
                + "var i6 = document.getElementById('i6');\n"
                + "alert(i1.width);\n"
                + "alert(i2.width);\n"
                + "alert(i3.width);\n"
                + "alert(i4.width);\n"
                + "alert(i5.width);\n"
                + "alert(i6.width);\n"
                + "set(i1, '400');\n"
                + "set(i2, 'abc');\n"
                + "set(i3, -5);\n"
                + "set(i4, 100.2);\n"
                + "set(i5, '10%');\n"
                + "set(i6, -12.56);\n"
                + "alert(i1.width);\n"
                + "alert(i2.width);\n"
                + "alert(i3.width);\n"
                + "alert(i4.width);\n"
                + "alert(i5.width);\n"
                + "alert(i6.width);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "100", "foo", "20%", "-5", "30.2", "400", "abc", "-5", "100.2", "10%", "-12.56"})
    public void height() {
        final String html
                = "<html>\n"
                + "<html><body>\n"
                + "<iframe id='i1'></iframe>\n"
                + "<iframe id='i2' height='100'></iframe>\n"
                + "<iframe id='i3' height='foo'></iframe>\n"
                + "<iframe id='i4' height='20%'></iframe>\n"
                + "<iframe id='i5' height='-5'></iframe>\n"
                + "<iframe id='i6' height='30.2'></iframe>\n"
                + "    <script>\n"
                + "function set(e, value) {\n"
                + "  try {\n"
                + "    e.height = value;\n"
                + "  } catch (e) {\n"
                + "    alert('error');\n"
                + "  }\n"
                + "}\n"
                + "var i1 = document.getElementById('i1');\n"
                + "var i2 = document.getElementById('i2');\n"
                + "var i3 = document.getElementById('i3');\n"
                + "var i4 = document.getElementById('i4');\n"
                + "var i5 = document.getElementById('i5');\n"
                + "var i6 = document.getElementById('i6');\n"
                + "alert(i1.height);\n"
                + "alert(i2.height);\n"
                + "alert(i3.height);\n"
                + "alert(i4.height);\n"
                + "alert(i5.height);\n"
                + "alert(i6.height);\n"
                + "set(i1, '400');\n"
                + "set(i2, 'abc');\n"
                + "set(i3, -5);\n"
                + "set(i4, 100.2);\n"
                + "set(i5, '10%');\n"
                + "set(i6, -12.56);\n"
                + "alert(i1.height);\n"
                + "alert(i2.height);\n"
                + "alert(i3.height);\n"
                + "alert(i4.height);\n"
                + "alert(i5.height);\n"
                + "alert(i6.height);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"uninitialized", "complete"})
    public void readyStateIFrame() {
        final String html
                = "<html>\n"
                + "<html><head></head>\n"
                + "  <body>\n"
                + "    <iframe id='i'></iframe>\n"
                + "    <script>\n"

                + "      alert(document.getElementById('i').contentWindow.document.readyState);\n"
                + "      window.onload = function() {\n"
                + "        alert(document.getElementById('i').contentWindow.document.readyState);\n"
                + "      };\n"
                + "    </script>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("128px")
    public void widthPx() {
        final String html
                = "<html>\n"
                + "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var iframe = document.getElementById('myFrame');\n"
                + "    iframe.width = '128px';\n"
                + "    alert(iframe.width);\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "  <iframe id='myFrame'></iframe>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLIFrameElement]", "[object HTMLIFrameElement]", "", ""})
    public void idByName() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(myFrame);\n"
                + "    alert(document.getElementById('myFrame'));\n"
                + "    alert(myFrame.width);\n"
                + "    alert(document.getElementById('myFrame').width);\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "  <iframe id='myFrame'></iframe>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("iframe onload")
    public void writeTriggersOnload() {
        final String html
                = "<html>\n"
                + "<html><head><title>First</title><script>\n"
                + "function test() {\n"
                + "  var iframe = document.createElement('iframe');\n"
                + "  var content = 'something';\n"
                + "  document.body.appendChild(iframe);\n"

                + "  iframe.onload = function() {alert('iframe onload')};\n"
                + "  iframe.contentWindow.document.open('text/html', 'replace');\n"
                + "  iframe.contentWindow.document.write(content);\n"
                + "  iframe.contentWindow.document.close();\n"
                + "}\n</script></head>\n"
                + "<body>\n"
                + "  <button type='button' id='clickme' onClick='test();'>Click me</a>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickme");
        elem.getOnclick();
    }

    @Test
    @Alerts({"localhost", "localhost", "localhost", "localhost",
            "true", "true", "true"})
    public void domain() {
        final String html
                = "<html>\n"
                + "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      var docDomain = document.domain;\n"
                + "      var frame1Domain = document.getElementById('frame1').contentWindow.document.domain;\n"
                + "      var frame2Domain = document.getElementById('frame2').contentWindow.document.domain;\n"
                + "      var frame3Domain = document.getElementById('frame3').contentWindow.document.domain;\n"
                + "      alert(docDomain);\n"
                + "      alert(frame1Domain);\n"
                + "      alert(frame2Domain);\n"
                + "      alert(frame3Domain);\n"
                + "      alert(docDomain === frame1Domain);\n"
                + "      alert(docDomain === frame2Domain);\n"
                + "      alert(docDomain === frame3Domain);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <iframe id='frame1' ></iframe>\n"
                + "  <iframe id='frame2' src='about:blank'></iframe>\n"
                + "  <iframe id='frame3' src='content.html'></iframe>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"localhost", "localhost", "true"})
    public void domainDynamic() {
        final String html
                = "<html>\n"
                + "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function doTest() {\n"
                + "      var myFrame = document.createElement('iframe');\n"
                + "      myFrame.id = 'idMyFrame';\n"
                + "      myFrame.src = 'about:blank';\n"
                + "      document.body.appendChild(myFrame);\n"

                + "      var docDomain = document.domain;\n"
                + "      var myFrameDomain = myFrame.contentDocument.domain;\n"

                + "      alert(docDomain);\n"
                + "      alert(myFrameDomain);\n"
                + "      alert(docDomain === myFrameDomain);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Window]", "topbody", "framebody"})
    public void contentWindowAndActiveElement() {
        final String html
                = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function check() {\n"
                + "      alert(document.getElementById('frame').contentWindow);\n"
                + "      alert(document.activeElement.id);\n"
                + "      alert(window.frame.window.document.activeElement.id);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body id='topbody'>\n"
                + "  <iframe id='frame' name='frame' src='" + URL_SECOND + "'></iframe>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"loaded", "[object HTMLDocument]", "2"})
    public void recursive() {
        final String html
                = "<html>\n"
                + "<html>\n"
                + "<head>\n"
                + "  <title>Deny</title>\n"
                + "  <script>\n"
                + "    function check() {\n"
                + "      try {\n"
                + "        alert(document.getElementById(\"frame1\").contentDocument);\n"
                + "      } catch (e) { alert('error'); }\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <iframe id='frame1' src='https://www.google.com/' "
                + "onLoad='alert(\"loaded\")'></iframe>\n"
                + "  <button type='button' id='clickme' onClick='check()'>Click me</a>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
