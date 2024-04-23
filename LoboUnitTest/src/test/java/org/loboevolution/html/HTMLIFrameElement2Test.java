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
public class HTMLIFrameElement2Test extends LoboUnitTest {


    @Test
    @Alerts("loaded")
    public void onLoad() {
        final String html =
                "<html>\n"
                        + "  <body>\n"
                        + "    <script>\n"
                        + "    </script>\n"
                        + "    <iframe id='i' onload='alert(\"loaded\");' src='" + URL_SECOND + "'></iframe>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("loaded")
    public void onLoadNoSrc() {
        final String html =
                "<html>\n"
                        + "  <body>\n"
                        + "    <script>\n"

                        + "    </script>\n"
                        + "    <iframe id='i' onload='alert(\"loaded\");'></iframe>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("loaded")
    public void documentWriteOnLoad() {
        final String html =
                "<html>\n"
                        + "<body>\n"
                        + "    <script>\n" 
                + "document.write(\"<iframe id='i' onload='alert(\\\"loaded\\\");' src='" + URL_SECOND + "'></iframe>\");\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("loaded")
    public void documentWriteOnLoadNoSrc() {
        final String html =
                "<html>\n"
                        + "  <body>\n"
                        + "    <script>\n"
                        + "      document.write(\"<iframe id='i' onload='alert(\\\"loaded\\\");'></iframe>\");\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"loaded", "foo"})
    public void documentCreateElementOnLoad() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "    var myFrame = document.getElementById('myFrame');\n"
                        + "    alert(myFrame.contentWindow.document.body.innerHTML);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "  <body>\n"
                        + "    <script>\n"
                        + "      var myFrame = document.createElement('iframe');\n"
                        + "      myFrame.id = 'myFrame';\n"
                        + "      myFrame.src = '" + URL_SECOND + "';\n"
                        + "      myFrame.onload = handleFrameLoad;\n"
                        + "      var body = document.getElementsByTagName('body')[0];\n"
                        + "      body.appendChild(myFrame);\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"loaded", ""})
    public void documentCreateElementOnLoadNoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "    var myFrame = document.getElementById('myFrame');\n"
                        + "    alert(myFrame.contentWindow.document.body.innerHTML);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "  <body>\n"
                        + "    <script>\n"
                        + "      var myFrame = document.createElement('iframe');\n"
                        + "      myFrame.id = 'myFrame';\n"
                        + "      myFrame.onload = handleFrameLoad;\n"
                        + "      var body = document.getElementsByTagName('body')[0];\n"
                        + "      body.appendChild(myFrame);\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"createIFrame", "loaded", "foo"})
    public void documentCreateElementOnLoad2() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"
                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.src = '" + URL_SECOND + "';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var body = document.getElementsByTagName('body')[0];\n"
                        + "    body.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "    var myFrame = document.getElementById('myFrame');\n"
                        + "    alert(myFrame.contentWindow.document.body.innerHTML);\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"createIFrame", "loaded", ""})
    public void documentCreateElementOnLoad2NoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"
                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var body = document.getElementsByTagName('body')[0];\n"
                        + "    body.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "    var myFrame = document.getElementById('myFrame');\n"
                        + "    alert(myFrame.contentWindow.document.body.innerHTML);\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("created")
    public void documentCreateElementNoAppendNoLoad() {
        final String html = "<html><body><script>\n"
                + "var myFrame = document.createElement('iframe');\n"
                + "myFrame.src = 'notExisting.html';\n"
                + "alert('created');\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"createIFrame", "loaded"})
    public void documentCreateElement_iFrameInsideDiv() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"
                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var content = document.getElementById('content');\n"
                        + "    var newContent = document.createElement('div');\n"
                        + "    newContent.innerHTML = '<iframe name=\"iFrame\" src=\"" + URL_SECOND + "\"></iframe>';\n"
                        + "    content.appendChild(newContent);\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body>\n"
                        + "    <div id='content'>content</div>\n"
                        + "    <a id='test' onclick='createIFrame();'>insert frame</a>\n"
                        + "  </body>\n"
                        + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("test");
        elem.getOnclick();
    }

    @Test
    @Alerts({"createIFrame", "loaded", "foo"})
    public void documentCreateElementOnLoad3() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    myFrame.src = '" + URL_SECOND + "';\n"
                        + "    var body = document.getElementsByTagName('body')[0];\n"
                        + "    body.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "    var myFrame = document.getElementById('myFrame');\n"
                        + "    alert(myFrame.contentWindow.document.body.innerHTML);\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"createIFrame", "loaded", ""})
    public void documentCreateElementOnLoad3NoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var body = document.getElementsByTagName('body')[0];\n"
                        + "    body.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "    var myFrame = document.getElementById('myFrame');\n"
                        + "    alert(myFrame.contentWindow.document.body.innerHTML);\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    public void documentFragmentCreateElementOnLoad() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body>\n"
                        + "    <script>\n"
                        + "      var myFrame = document.createElement('iframe');\n"
                        + "      myFrame.id = 'myFrame';\n"
                        + "      myFrame.src = '" + URL_SECOND + "';\n"
                        + "      myFrame.onload = handleFrameLoad;\n"
                        + "      var fragment = document.createDocumentFragment();\n"
                        + "      fragment.appendChild(myFrame);\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    public void documentFragmentCreateElementOnLoadNoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body>\n"
                        + "    <script>\n"
                        + "      var myFrame = document.createElement('iframe');\n"
                        + "      myFrame.id = 'myFrame';\n"
                        + "      myFrame.onload = handleFrameLoad;\n"
                        + "      var fragment = document.createDocumentFragment();\n"
                        + "      fragment.appendChild(myFrame);\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("createIFrame")
    public void documentFragmentCreateElementOnLoad2() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.src = '" + URL_SECOND + "';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var fragment = document.createDocumentFragment();\n"
                        + "    fragment.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("createIFrame")
    public void documentFragmentCreateElementOnLoad2NoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var fragment = document.createDocumentFragment();\n"
                        + "    fragment.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts("createIFrame")
    public void documentFragmentCreateElementOnLoad3() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    myFrame.src = '" + URL_SECOND + "';\n"
                        + "    var fragment = document.createDocumentFragment();\n"
                        + "    fragment.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"createIFrame", "loaded"})
    public void documentCreateElementOnLoadsrcAboutBlank() {
        documentCreateElementOnLoadsrcX("about:blank");
    }


    @Test
    @Alerts({"createIFrame", "loaded"})
    public void documentCreateElementOnLoadsrcSomePage() {
        documentCreateElementOnLoadsrcX("foo.html");
    }

    private void documentCreateElementOnLoadsrcX(final String iframeSrc) {
        final String html = "<html><body><script>\n"
                + "function createIFrame() {\n"
                + "  alert('createIFrame');\n"
                + "  var myFrame = document.createElement('iframe');\n"
                + "  myFrame.onload = handleFrameLoad;\n"
                + "  myFrame.src = '" + iframeSrc + "';\n"
                + "  document.body.appendChild(myFrame);\n"
                + "}\n"
                + "function handleFrameLoad() {\n"
                + "  alert('loaded');\n"
                + "}\n"
                + "</script>\n"
                + "<button id='it' onclick='createIFrame()'>click me</button>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("it");
        elem.getOnclick();
    }


    @Test
    @Alerts("createIFrame")
    public void documentFragmentCreateElementOnLoad3NoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var fragment = document.createDocumentFragment();\n"
                        + "    fragment.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"fragment append done", "loaded"})
    public void documentDocumentFragmentCreateElementOnLoad() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "  <body>\n"
                        + "    <script>\n"
                        + "      var myFrame = document.createElement('iframe');\n"
                        + "      myFrame.id = 'myFrame';\n"
                        + "      myFrame.src = '" + URL_SECOND + "';\n"
                        + "      myFrame.onload = handleFrameLoad;\n"
                        + "      var fragment = document.createDocumentFragment();\n"
                        + "      fragment.appendChild(myFrame);\n"
                        + "      alert('fragment append done');\n"
                        + "      var body = document.getElementsByTagName('body')[0];\n"
                        + "      body.appendChild(myFrame);\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"fragment append done", "loaded"})
    public void documentDocumentFragmentCreateElementOnLoadNoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body>\n"
                        + "    <script>\n"
                        + "      var myFrame = document.createElement('iframe');\n"
                        + "      myFrame.id = 'myFrame';\n"
                        + "      myFrame.onload = handleFrameLoad;\n"
                        + "      var fragment = document.createDocumentFragment();\n"
                        + "      fragment.appendChild(myFrame);\n"
                        + "      alert('fragment append done');\n"
                        + "      var body = document.getElementsByTagName('body')[0];\n"
                        + "      body.appendChild(myFrame);\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"createIFrame", "fragment append done", "loaded"})
    public void documentDocumentFragmentCreateElementOnLoad2() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.src = '" + URL_SECOND + "';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var fragment = document.createDocumentFragment();\n"
                        + "    fragment.appendChild(myFrame);\n"
                        + "    alert('fragment append done');\n"
                        + "    var body = document.getElementsByTagName('body')[0];\n"
                        + "    body.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"createIFrame", "fragment append done", "loaded"})
    public void documentDocumentFragmentCreateElementOnLoad2NoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"
                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var fragment = document.createDocumentFragment();\n"
                        + "    fragment.appendChild(myFrame);\n"
                        + "    alert('fragment append done');\n"
                        + "    var body = document.getElementsByTagName('body')[0];\n"
                        + "    body.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"createIFrame", "fragment append done", "loaded"})
    public void documentDocumentFragmentCreateElementOnLoad3() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    myFrame.src = '" + URL_SECOND + "';\n"
                        + "    var fragment = document.createDocumentFragment();\n"
                        + "    fragment.appendChild(myFrame);\n"
                        + "    alert('fragment append done');\n"
                        + "    var body = document.getElementsByTagName('body')[0];\n"
                        + "    body.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);

    }


    @Test
    @Alerts({"createIFrame", "fragment append done", "loaded"})
    public void documentDocumentFragmentCreateElementOnLoad3NoSrc() {
        final String html =
                "<html>\n"
                        + "<head><script type='text/javascript'>\n"

                        + "  function createIFrame() {\n"
                        + "    alert('createIFrame');\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    myFrame.id = 'myFrame';\n"
                        + "    myFrame.onload = handleFrameLoad;\n"
                        + "    var fragment = document.createDocumentFragment();\n"
                        + "    fragment.appendChild(myFrame);\n"
                        + "    alert('fragment append done');\n"
                        + "    var body = document.getElementsByTagName('body')[0];\n"
                        + "    body.appendChild(myFrame);\n"
                        + "  }\n"
                        + "  function handleFrameLoad() {\n"
                        + "    alert('loaded');\n"
                        + "  }\n"
                        + "</script></head>\n"

                        + "  <body onload='createIFrame();' >\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);

    }


    @Test
    @Alerts({"left", "right", "bottom", "middle", "top", "wrong", ""})
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <iframe id='i1' align='left' ></iframe>\n"
                + "  <iframe id='i2' align='right' ></iframe>\n"
                + "  <iframe id='i3' align='bottom' ></iframe>\n"
                + "  <iframe id='i4' align='middle' ></iframe>\n"
                + "  <iframe id='i5' align='top' ></iframe>\n"
                + "  <iframe id='i6' align='wrong' ></iframe>\n"
                + "  <iframe id='i7' ></iframe>\n"
                + "<script>\n"
                + "  for (var i = 1; i <= 7; i++) {\n"
                + "    alert(document.getElementById('i' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"CenTer", "8", "foo", "left", "right", "bottom", "middle", "top"})
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <iframe id='i1' align='left' ></iframe>\n"
                + "<script>\n"
                + "  function setAlign(elem, value) {\n"
                + "    try {\n"
                + "      elem.align = value;\n"
                + "    } catch (e) { alert('error'); }\n"
                + "    alert(elem.align);\n"
                + "  }\n"
                + "  var elem = document.getElementById('i1');\n"
                + "  setAlign(elem, 'CenTer');\n"
                + "  setAlign(elem, '8');\n"
                + "  setAlign(elem, 'foo');\n"
                + "  setAlign(elem, 'left');\n"
                + "  setAlign(elem, 'right');\n"
                + "  setAlign(elem, 'bottom');\n"
                + "  setAlign(elem, 'middle');\n"
                + "  setAlign(elem, 'top');\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"loaded", "loaded", "loaded"})
    public void onLoadCalledEachTimeFrameContentChanges() {
        final String html =
                "<html>\n"
                        + "  <body>\n"
                        + "    <script>\n"
                        + "    </script>\n"
                        + "    <iframe id='testFrame' onload='alert(\"loaded\");'></iframe>\n"
                        + "    <div id='d1' onclick='i.contentWindow.location.replace(\"blah.html\")'>1</div>\n"
                        + "    <div id='d2' onclick='i.contentWindow.location.href=\"blah.html\"'>2</div>\n"
                        + "    <script>var i = document.getElementById('testFrame')</script>\n"
                        + "  </body>\n"
                        + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("d1");
        elem.getOnclick();

        elem = (HTMLElementImpl) document.getElementById("d2");
        elem.getOnclick();
    }


    @Test
    @Alerts({"about:blank", "", ""})
    public void location() {
        final String html =
                "<html>\n"
                        + "<head><script>\n"
                        + "  function test() {\n"
                        + "    var myFrame = document.createElement('iframe');\n"
                        + "    document.body.appendChild(myFrame);\n"
                        + "    var win = myFrame.contentWindow;\n"
                        + "    var doc = win.document;\n"
                        + "    alert(win.location);\n"
                        + "    doc.open();\n"
                        + "    alert(win.location);\n"
                        + "    doc.write('');\n"
                        + "    doc.close();\n"
                        + "    alert(win.location);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "  <body onload='test()'>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);

    }


    @Test
    @Alerts({"loaded", "", "", "loaded", "about:blank"})
    public void removeSourceAttribute() {
        final String html =
                "<html>\n"
                        + "<head><script>\n"
                        + "  function alert(msg) {\n"
                        + "    var ta = document.getElementById('myTextArea');\n"
                        + "    ta.value += msg + '; ';\n"
                        + "  }\n"

                        + "  function test() {\n"
                        + "    var myFrame = document.getElementById('i');\n"
                        + "    var win = myFrame.contentWindow;\n"
                        + "    alert(win.location);\n"

                        + "    myFrame.removeAttribute('src');\n"
                        + "    alert(win.location);\n"
                        + "  }\n"

                        + "  function test2() {\n"
                        + "    var myFrame = document.getElementById('i');\n"
                        + "    var win = myFrame.contentWindow;\n"
                        + "    alert(win.location);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "  <body>\n"
                        + "    <iframe id='i' onload='alert(\"loaded\");' src='" + URL_SECOND + "'></iframe>\n"

                        + "    <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
                        + "    <button id='clickMe' onclick='test()'>Click Me</button>\n"
                        + "    <button id='clickMe2' onclick='test2()'>Click Me</button>\n"
                        + "  </body>\n"
                        + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();

        elem = (HTMLElementImpl) document.getElementById("clickMe2");
        elem.getOnclick();
    }


    @Test
    @Alerts({"loaded", "", "[object HTMLDocument]", "http://localhost:22222/second/",
            "1", "[object Window]", "[object HTMLDocument]", "http://localhost:22222/second/",
            "0", "#[object Window]", "#[object HTMLDocument]", "http://localhost:22222/second/"})
    public void detach() {
        final String html =
                "<html>\n"
                        + "<head><script>\n"
                        + "  function alert(msg) {\n"
                        + "    var ta = document.getElementById('myTextArea');\n"
                        + "    ta.value += msg + '; ';\n"
                        + "  }\n"

                        + "  function test() {\n"
                        + "    var myFrame = document.getElementById('i');\n"
                        + "    var win = myFrame.contentWindow;\n"
                        + "    alert(win.location);\n"
                        + "    alert(win.document);\n"
                        + "    alert(win.document.URL);\n"
                        + "    alert(window.frames.length);\n"

                        + "    myFrame.parentNode.removeChild(myFrame);\n"
                        + "    alert(win);\n"
                        + "    alert(win.document);\n"
                        + "    alert(win.document.URL);\n"
                        + "    alert(window.frames.length);\n"

                        + "    window.setTimeout(function () "
                        + "{ alert('#' + win); alert('#' + win.document); alert(win.document.URL); }, 42);\n"
                        + "  }\n"

                        + "</script></head>\n"
                        + "  <body>\n"
                        + "    <iframe id='i' onload='alert(\"loaded\");' src='" + URL_SECOND + "'></iframe>\n"

                        + "    <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
                        + "    <button id='clickMe' onclick='test()'>Click Me</button>\n"
                        + "  </body>\n"
                        + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("d2");
        elem.getOnclick();

        elem = (HTMLElementImpl) document.getElementById("");
        elem.getOnclick();
    }


    @Test
    @Alerts({"iframe script", "loaded", "null", "[object Window]",
            "about:blank", "iframe script", "loaded"})
    public void detachAppend() {
        final String html =
                "<html>\n"
                        + "<head><script>\n"
                        + "  function alert(msg) {\n"
                        + "    var ta = document.getElementById('myTextArea');\n"
                        + "    ta.value += msg + '; ';\n"
                        + "  }\n"

                        + "  function test() {\n"
                        + "    var myFrame = document.getElementById('i');\n"

                        + "    var parent = myFrame.parentNode;\n"
                        + "    parent.removeChild(myFrame);\n"
                        + "    alert(myFrame.contentWindow);\n"

                        + "    parent.appendChild(myFrame);\n"
                        + "    alert(myFrame.contentWindow);\n"
                        + "    alert(myFrame.contentWindow.location);\n"
                        + "  }\n"

                        + "</script></head>\n"
                        + "  <body>\n"
                        + "    <iframe id='i' onload='alert(\"loaded\");' src='" + URL_SECOND + "'></iframe>\n"

                        + "    <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
                        + "    <button id='clickMe' onclick='test()'>Click Me</button>\n"
                        + "  </body>\n"
                        + "</html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("clickMe");
        elem.getOnclick();
    }
}
