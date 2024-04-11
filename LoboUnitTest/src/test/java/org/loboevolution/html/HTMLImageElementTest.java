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
import org.loboevolution.html.dom.HTMLImageElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link HTMLImageElement}
 */
@ExtendWith(AlertsExtension.class)
public class HTMLImageElementTest extends LoboUnitTest {

    @Test
    @Alerts({"[object HTMLImageElement]", "[object HTMLImageElement]"})
    public void simpleScriptable() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId1'));\n"
                + "    alert(document.getElementById('myId2'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myId1'>\n"
                + "  <image id='myId2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"IMG", "IMG"})
    public void nodeName() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId1').nodeName);\n"
                + "    alert(document.getElementById('myId2').nodeName);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myId1'>\n"
                + "  <image id='myId2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"IMG", "IMG"})
    public void tagName() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId1').tagName);\n"
                + "    alert(document.getElementById('myId2').tagName);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myId1'>\n"
                + "  <image id='myId2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLImageElement]", "[object HTMLUnknownElement]", "IMG", "IMAGE",
            "[object HTMLImageElement]", "[object HTMLImageElement]", "IMG", "IMG"})
    public void image() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.createElement('img'));\n"
                + "    alert(document.createElement('image'));\n"
                + "    alert(document.createElement('img').nodeName);\n"
                + "    alert(document.createElement('image').nodeName);\n"
                + "    alert(document.getElementById('myId1'));\n"
                + "    alert(document.getElementById('myId2'));\n"
                + "    alert(document.getElementById('myId1').nodeName);\n"
                + "    alert(document.getElementById('myId2').nodeName);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myId1'>\n"
                + "  <image id='myId2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "undefined", "", ""})
    public void src() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.createElement('img').src);\n"
                + "    alert(document.createElement('image').src);\n"
                + "    alert(document.getElementById('myId1').src);\n"
                + "    alert(document.getElementById('myId2').src);\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myId1'>\n"
                + "  <image id='myId2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("foo.gif")
    public void getSrc() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementById('anImage').src);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<img src='foo.gif' id='anImage'/>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("")
    public void getSrcNewImagesrcNotSet() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  var oImg = new Image();\n"
                + "  alert(oImg.src);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    public void setSrc() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  document.getElementById('anImage').src = 'bar.gif';\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<img src='foo.gif' id='anImage'/>\n"
                + "</body></html>";
        final HTMLDocument document = loadHtml(html);
        HTMLImageElement elem = (HTMLImageElement) document.getElementById("anImage");
        assertEquals("bar.gif", elem.getAttribute("src"));
    }

    @Test
    @Alerts({"onLoad", "bar.gif"})
    public void setSrcNewImage() {
        final String html
                = "<html><head>\n"
                + "<script>\n"                + "function doTest() {\n"
                + "  var preloadImage = new Image();\n"
                + "  preloadImage.onload = alert('onLoad');\n"
                + "  preloadImage.src = 'bar.gif';\n"
                + "  alert(preloadImage.src);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("foo")
    public void attributeName() {
        final String html
                = "<html><head>\n"
                + "<script>\n"                + "function test() {\n"
                + "  var oImg = document.getElementById('myImage');\n"
                + "  oImg.name = 'foo';\n"
                + "  alert(oImg.name);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "<img src='foo.png' id='myImage'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "relative", "", ""})
    public void newImage() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  var i = new Image();\n"
                + "  alert(i.style != null);\n"
                + "  i.style.position = 'relative';\n"
                + "  alert(i.style.position);\n"
                + "  alert(i.border);\n"
                + "  alert(i.alt);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"left", "right", "center", "justify", "bottom", "middle",
            "top", "absbottom", "absmiddle", "baseline", "texttop", "wrong", ""})
    public void getAlign() {
        final String html
                = "<html><body>\n"
                + "  <img id='i1' align='left' />\n"
                + "  <img id='i2' align='right' />\n"
                + "  <img id='i3' align='center' />\n"
                + "  <img id='i4' align='justify' />\n"
                + "  <img id='i5' align='bottom' />\n"
                + "  <img id='i6' align='middle' />\n"
                + "  <img id='i7' align='top' />\n"
                + "  <img id='i8' align='absbottom' />\n"
                + "  <img id='i9' align='absmiddle' />\n"
                + "  <img id='i10' align='baseline' />\n"
                + "  <img id='i11' align='texttop' />\n"
                + "  <img id='i12' align='wrong' />\n"
                + "  <img id='i13' />\n"
                + "<script>\n"                + "  for (var i = 1; i <= 13; i++) {\n"
                + "    alert(document.getElementById('i' + i).align);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"CenTer", "8", "foo", "left", "right", "center", "justify",
            "bottom", "middle", "top", "absbottom", "absmiddle", "baseline", "texttop"})
    public void setAlign() {
        final String html
                = "<html><body>\n"
                + "  <img id='i1' align='left' />\n"

                + "    <script>\n"
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
                + "  setAlign(elem, 'center');\n"
                + "  setAlign(elem, 'justify');\n"
                + "  setAlign(elem, 'bottom');\n"
                + "  setAlign(elem, 'middle');\n"
                + "  setAlign(elem, 'top');\n"
                + "  setAlign(elem, 'absbottom');\n"
                + "  setAlign(elem, 'absmiddle');\n"
                + "  setAlign(elem, 'baseline');\n"
                + "  setAlign(elem, 'texttop');\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"number: 300", "number: 200", "number: 0", "number: 0", "number: 0", "number: 0"})
    public void widthHeightWithoutSource() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function showInfo(imageId) {\n"
                + "    var img = document.getElementById(imageId);\n"
                + "    alert(typeof(img.width) + ': ' + img.width);\n"
                + "    alert(typeof(img.height) + ': ' + img.height);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    showInfo('myImage1');\n"
                + "    showInfo('myImage2');\n"
                + "    showInfo('myImage3');\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage1' width='300' height='200'>\n"
                + "  <img id='myImage2'>\n"
                + "  <img id='myImage3' width='hello' height='hello'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"number: 300", "number: 200", "number: 1", "number: 1", "number: 1", "number: 1"})
    public void widthHeightWithSource() {

        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function showInfo(imageId) {\n"
                + "    var img = document.getElementById(imageId);\n"
                + "    alert(typeof(img.width) + ': ' + img.width);\n"
                + "    alert(typeof(img.height) + ': ' + img.height);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    showInfo('myImage1');\n"
                + "    showInfo('myImage2');\n"
                + "    showInfo('myImage3');\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage1' src='" + URL_SECOND + "' width='300' height='200'>\n"
                + "  <img id='myImage2' src='" + URL_SECOND + "' >\n"
                + "  <img id='myImage3' src='" + URL_SECOND + "' width='hello' height='hello'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"number: 300", "number: 200", "number: 0", "number: 0", "number: 0", "number: 0"})
    public void widthHeightEmptySource() {

        final String html = "<html><head>\n"
                + "<script>\n"                + "  function showInfo(imageId) {\n"
                + "    var img = document.getElementById(imageId);\n"
                + "    alert(typeof(img.width) + ': ' + img.width);\n"
                + "    alert(typeof(img.height) + ': ' + img.height);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    showInfo('myImage1');\n"
                + "    showInfo('myImage2');\n"
                + "    showInfo('myImage3');\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage1' src='' width='300' height='200'>\n"
                + "  <img id='myImage2' src='' >\n"
                + "  <img id='myImage3' src='' width='hello' height='hello'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"number: 300", "number: 200", "number: 24", "number: 24", "number: 24", "number: 24"})
    public void widthHeightBlankSource() {

        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function showInfo(imageId) {\n"
                + "    var img = document.getElementById(imageId);\n"
                + "    alert(typeof(img.width) + ': ' + img.width);\n"
                + "    alert(typeof(img.height) + ': ' + img.height);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    showInfo('myImage1');\n"
                + "    showInfo('myImage2');\n"
                + "    showInfo('myImage3');\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage1' src=' ' width='300' height='200'>\n"
                + "  <img id='myImage2' src=' ' >\n"
                + "  <img id='myImage3' src=' ' width='hello' height='hello'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"number: 300", "number: 200", "number: 24", "number: 24", "number: 24", "number: 24"})
    public void widthHeightInvalidSource() {

        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function showInfo(imageId) {\n"
                + "    var img = document.getElementById(imageId);\n"
                + "    alert(typeof(img.width) + ': ' + img.width);\n"
                + "    alert(typeof(img.height) + ': ' + img.height);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    showInfo('myImage1');\n"
                + "    showInfo('myImage2');\n"
                + "    showInfo('myImage3');\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage1' src='" + URL_SECOND + "' width='300' height='200'>\n"
                + "  <img id='myImage2' src='" + URL_SECOND + "' >\n"
                + "  <img id='myImage3' src='" + URL_SECOND + "' width='hello' height='hello'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true", "true"})
    public void complete() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function showInfo(imageId) {\n"
                + "    var img = document.getElementById(imageId);\n"
                + "    alert(img.complete);\n"
                + "  }\n"
                + "  function test() {\n"
                + "    showInfo('myImage1');\n"
                + "    showInfo('myImage2');\n"
                + "    showInfo('myImage3');\n"
                + "    showInfo('myImage4');\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage1' >\n"
                + "  <img id='myImage2' src=''>\n"
                + "  <img id='myImage3' src='" + URL_SECOND + "'>\n"
                + "  <img id='myImage4' src='" + URL_SECOND + "img.jpg'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"error2;error3;error4;load5;", "3"})
    public void onload() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function showInfo(text) {\n"
                + "    document.title += text + ';';\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body>\n"
                + "  <img id='myImage1' onload='showInfo(\"load1\")' onerror='showInfo(\"error1\")'>\n"
                + "  <img id='myImage2' src='' onload='showInfo(\"load2\")' onerror='showInfo(\"error2\")'>\n"
                + "  <img id='myImage3' src='  ' onload='showInfo(\"load3\")' onerror='showInfo(\"error3\")'>\n"
                + "  <img id='myImage4' src='" + URL_SECOND + "' onload='showInfo(\"load4\")' "
                + "onerror='showInfo(\"error4\")'>\n"
                + "  <img id='myImage5' src='" + URL_SECOND + "img.jpg' onload='showInfo(\"load5\")' "
                + "onerror='showInfo(\"error5\")'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"load;", "2"})
    public void emptyMimeType() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function showInfo(text) {\n"
                + "    document.title += text + ';';\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body>\n"
                + "  <img id='myImage5' src='" + URL_SECOND + "img.jpg' onload='showInfo(\"load\")' "
                + "onerror='showInfo(\"error\")'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"load;", "2"})
    public void wrongMimeType() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function showInfo(text) {\n"
                + "    document.title += text + ';';\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body>\n"
                + "  <img id='myImage5' src='" + URL_SECOND + "img.jpg' onload='showInfo(\"load\")' "
                + "onerror='showInfo(\"error\")'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"test", "string", "hui", "", "null", "false", "true", ""})
    public void alt() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var testImg = document.getElementById('myImage');\n"
                        + "        alert(testImg.alt);\n"
                        + "        alert(typeof testImg.alt);\n"

                        + "        testImg.alt = 'hui';\n"
                        + "        alert(testImg.alt);\n"

                        + "        testImg.alt = '';\n"
                        + "        alert(testImg.alt);\n"

                        + "        testImg.alt = null;\n"
                        + "        alert(testImg.alt);\n"
                        + "        alert(testImg.alt === null);\n"
                        + "        alert(testImg.alt === 'null');\n"

                        + "        var testImg = document.getElementById('myImageWithoutAlt');\n"
                        + "        alert(testImg.alt);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <img id='myImage' src='" + URL_SECOND + "' alt='test'>\n"
                        + "    <img id='myImageWithoutAlt' src='" + URL_SECOND + "'>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myImage clicked", "myImageNone clicked"})
    public void click() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    document.getElementById('myImage').click();\n"
                + "    document.getElementById('myImageNone').click();\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImage' src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAA"
                + "HElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=='"
                + " onclick='alert(\"myImage clicked\");'>\n"
                + "  <img id='myImageNone' src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAA"
                + "HElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=='"
                + " style='display: none' onclick='alert(\"myImageNone clicked\");'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("myImageWithMap clicked")
    public void clickWithMap() {
        final String html = "<html><head>\n"
                + "<script>\n"                + "  function test() {\n"
                + "    document.getElementById('myImageWithMap').click();\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "  <img id='myImageWithMap' usemap='#dot'"
                + " src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAA"
                + "HElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=='"
                + " onclick='alert(\"myImageWithMap clicked\");'>\n"
                + "  <map name='dot'>\n"
                + "    <area id='a0' shape='rect' coords='0 0 7 7' onclick='alert(\"a0 clicked\");'/>\n"
                + "    <area id='a1' shape='rect' coords='0,0,1,1' onclick='alert(\"a1 clicked\");'/>\n"
                + "  <map>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("[object HTMLImageElement]")
    public void ctorImage() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function doTest() {\n"
                + "        var htmlImageElement = new Image(1, 1);"
                + "        alert('' + htmlImageElement);\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("error")
    public void ctorHTMLImageElement() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"

                + "      function doTest() {\n"
                + "        try {\n"
                + "          var htmlImageElement = new HTMLImageElement(1, 1);"
                + "          alert('' + htmlImageElement);\n"
                + "        } catch (e) { alert('error'); }\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("{\"enumerable\":true,\"configurable\":true}")
    public void imagePrototype() {
        final String html
                = "<html>\n"
                + "  <head>\n"
                + "    <script>\n"
                + "      function doTest() {\n"
                + "        var desc = Object.getOwnPropertyDescriptor(Image.prototype, 'src');"
                + "        alert(JSON.stringify(desc));\n"
                + "      }\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body onload='doTest()'>\n"
                + "  </body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
