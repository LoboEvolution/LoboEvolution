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
import org.loboevolution.html.dom.HTMLAnchorElement;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * Tests for {@link HTMLAnchorElement}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLAnchorElementTest extends LoboUnitTest {

    @Test
    @Alerts({"", "null", "null", "test.css", "stylesheet", "stylesheet1"})
    public void attributes() {
        final String html =
                "<html>\n"
                        + "  <body onload='test()'>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var a = document.createElement('a');\n"
                        + "        alert(a.href);\n"
                        + "        alert(a.rel);\n"
                        + "        alert(a.rev);\n"
                        + "        a.href = 'test.css';\n"
                        + "        a.rel  = 'stylesheet';\n"
                        + "        a.rev  = 'stylesheet1';\n"
                        + "        alert(a.href);\n"
                        + "        alert(a.rel);\n"
                        + "        alert(a.rev);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"attachEvent not available", "href"})
    public void javaScriptPreventDefaultIE() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var a = document.getElementById('link');\n"
                + "    if (!a.attachEvent) { alert('attachEvent not available'); return }\n"
                + "    a.attachEvent('onclick', handler);\n"
                + "  }\n"
                + "  function handler() {\n"
                + "    event.returnValue = false;\n"
                + "    alert('onclick');\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "  <a id='link' href='javascript: alert(\"href\");'>link</a>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("onclick")
    public void javaScriptPreventDefault() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var a = document.getElementById('link');\n"
                + "    a.addEventListener('click', handler);\n"
                + "  }\n"
                + "  function handler(event) {\n"
                + "    event.preventDefault();\n"
                + "    alert('onclick');\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "<a id='link' href='javascript: alert(\"href\");'>link</a>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "foo.html", "javascript:void(0)", "#", "mailto:"})
    public void defaultConversionToString() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  alert(document.getElementById('myAnchor'));\n"
                + "  for (var i = 0; i < document.links.length; i++)\n"
                + "  {\n"
                + "    alert(document.links[i]);\n"
                + "  }\n"
                + "}</script></head>\n"
                + "<body onload='test()'>\n"
                + "<a name='start' id='myAnchor'/>\n"
                + "<a href='foo.html'>foo</a>\n"
                + "<a href='javascript:void(0)'>void</a>\n"
                + "<a href='#'>#</a>\n"
                + "<a href='mailto:'>mail</a>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"testsite1.html", "testsite1.html", "testsite2.html",
            "testsite2.html", "13", "testanchor", "mailto:"})
    public void getAttributeAndHref() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function doTest(anchorElement) {\n"
                + "    alert(anchorElement.href);\n"
                + "    alert(anchorElement.getAttribute('href'));\n"
                + "    anchorElement.href = 'testsite2.html';\n"
                + "    alert(anchorElement.href);\n"
                + "    alert(anchorElement.getAttribute('href'));\n"
                + "    alert(anchorElement.getAttribute('id'));\n"
                + "    alert(anchorElement.getAttribute('name'));\n"
                + "    var link2 = document.getElementById('link2');\n"
                + "    alert(link2.href);\n"
                + "  }\n</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <a href='testsite1.html' id='13' name='testanchor' onClick='doTest(this);return false'>bla</a>\n"
                + "  <a href='mailto:' id='link2'>mail</a>\n"
                + "</body></html>";
        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl elem = (HTMLElementImpl) document.getElementById("13");
        elem.getOnclick();
    }

    @Test
    @Alerts({"http://htmlunit.sourceforge.net/", "test", "#test",
            "#", ""})
    public void getDefaultValue() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('absolute'));\n"
                + "    alert(document.getElementById('relative'));\n"
                + "    alert(document.getElementById('hash'));\n"
                + "    alert(document.getElementById('hashOnly'));\n"
                + "    alert(document.getElementById('empty'));\n"
                + "  }\n</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='http://htmlunit.sourceforge.net/' id='absolute'>bla</a>\n"
                + "  <a href='test' id='relative'>bla</a>\n"
                + "  <a href='#test' id='hash'>bla</a>\n"
                + "  <a href='#' id='hashOnly'>bla</a>\n"
                + "  <a href='' id='empty'>bla</a>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"http://htmlunit.sourceforge.net/", "test", "#test",
            "#", ""})
    public void getDefaultValueWithHash() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('absolute'));\n"
                + "    alert(document.getElementById('relative'));\n"
                + "    alert(document.getElementById('hash'));\n"
                + "    alert(document.getElementById('hashOnly'));\n"
                + "    alert(document.getElementById('empty'));\n"
                + "  }\n</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='http://htmlunit.sourceforge.net/' id='absolute'>bla</a>\n"
                + "  <a href='test' id='relative'>bla</a>\n"
                + "  <a href='#test' id='hash'>bla</a>\n"
                + "  <a href='#' id='hashOnly'>bla</a>\n"
                + "  <a href='' id='empty'>bla</a>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"http://htmlunit.sourceforge.net/", "test", "index.html#test",
            "index.html#", "index.html"})
    public void getDefaultValueWithHashAndFileName() {
        final String html
                = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('absolute'));\n"
                + "    alert(document.getElementById('relative'));\n"
                + "    alert(document.getElementById('hash'));\n"
                + "    alert(document.getElementById('hashOnly'));\n"
                + "    alert(document.getElementById('empty'));\n"
                + "  }\n</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='http://htmlunit.sourceforge.net/' id='absolute'>bla</a>\n"
                + "  <a href='test' id='relative'>bla</a>\n"
                + "  <a href='#test' id='hash'>bla</a>\n"
                + "  <a href='#' id='hashOnly'>bla</a>\n"
                + "  <a href='' id='empty'>bla</a>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("function onclick(event) { alert(\"on click\") }§not defined")
    public void onclickToString() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    for (var i = 0; i < document.links.length; i++) {\n"
                + "      var onclick = document.links[i].onclick;\n"
                + "      alert(onclick ? onclick.toString() : 'not defined');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='foo.html' onClick='alert(\"on click\")'>a1</a>\n"
                + "  <a href='foo2.html'>a2</a>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"null", "A", "a", "A", "a8", "8Afoo", "8", "@"})
    public void readWriteAccessKey() {
        final String html
                = "<html>\n"
                + "<body>\n"
                + "  <a id='a1' href='#'></a><a id='a2' href='#' accesskey='A'></a>\n"
                + "<script>\n"
                + "  var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "  alert(a1.accessKey);\n"
                + "  alert(a2.accessKey);\n"
                + "  a1.accessKey = 'a';\n"
                + "  a2.accessKey = 'A';\n"
                + "  alert(a1.accessKey);\n"
                + "  alert(a2.accessKey);\n"
                + "  a1.accessKey = 'a8';\n"
                + "  a2.accessKey = '8Afoo';\n"
                + "  alert(a1.accessKey);\n"
                + "  alert(a2.accessKey);\n"
                + "  a1.accessKey = '8';\n"
                + "  a2.accessKey = '@';\n"
                + "  alert(a1.accessKey);\n"
                + "  alert(a2.accessKey);\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"9", "9", "true", "false"})
    public void hrefTrimmed() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('a').href.length);\n"
                + "    alert(document.getElementById('b').href.length);\n"
                + "    alert(document.getElementById('c').href === '');\n"
                + "    alert(document.getElementById('d').href === '');\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a href=' http://a/ ' id='a'>a</a> "
                + "  <a href='  http://b/    ' id='b'>b</a>\n"
                + "  <a name='myAnchor' id='c'>c</a>\n"
                + "  <a href='' id='d'>d</a>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void thisInJavascriptHref() {
        final String html
                = "<html><head>\n"
                + "    <script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <a id='tester' href='javascript:alert(this === window)'>link 1</a>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLAnchorElement elem = (HTMLAnchorElement) document.getElementById("tester");
        elem.getHref();
    }

    @Test
    @Alerts({"second/", "object", "function HTMLAnchorElement() { [native code] }"})
    public void typeof() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.links[0]);\n"
                + "      alert(typeof document.links[0]);\n"
                + "      alert(HTMLAnchorElement);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a id='link'  href='" + URL_CSS + "style1.css'>link</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "", "text/html", "TExT/hTMl", " text/html ", "application/pdf", "unknown"})
    public void getType() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alertType('idWithout');\n"
                + "    alertType('idEmpty');\n"
                + "    alertType('idText');\n"
                + "    alertType('idCase');\n"
                + "    alertType('idWhitespace');\n"
                + "    alertType('idPdf');\n"
                + "    alertType('idUnknown');\n"
                + "  }\n"
                + "  function alertType(id) {\n"
                + "    var anchor = document.getElementById(id);\n"
                + "    alert(anchor.type);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a id='idWithout' href='" + URL_CSS + "style1.css'>link</a>\n"
                + "  <a id='idEmpty' href='" + URL_CSS + "style1.css' type=''>link</a>\n"
                + "  <a id='idText' href='" + URL_CSS + "style1.css' type='text/html'>link</a>\n"
                + "  <a id='idCase' href='" + URL_CSS + "style1.css' type='TExT/hTMl'>link</a>\n"
                + "  <a id='idWhitespace' href='" + URL_CSS + "style1.css' type=' text/html '>link</a>\n"
                + "  <a id='idPdf' href='" + URL_CSS + "style1.css' type='application/pdf'>link</a>\n"
                + "  <a id='idUnknown' href='" + URL_CSS + "style1.css' type='unknown'>link</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"text/html", "", " TExT/hTMl  ", "unknown", "application/pdf"})
    public void setType() {
        final String html = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var anchor = document.getElementById('id');\n"
                + "    alert(anchor.type);\n"
                + "    anchor.type = '';\n"
                + "    alert(anchor.type);\n"
                + "    anchor.type = ' TExT/hTMl  ';\n"
                + "    alert(anchor.type);\n"
                + "    anchor.type = 'unknown';\n"
                + "    alert(anchor.type);\n"
                + "    anchor.type = 'application/pdf';\n"
                + "    alert(anchor.type);\n"
                + "  }\n"
                + "  function alertType(id) {\n"
                + "    var anchor = document.getElementById(id);\n"
                + "    alert(anchor.type);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a id='id'  href='" + URL_CSS + "style1.css' type='text/html'>link</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({":||||||", ":||||||", "mailto:||||||foo@foo.com", "tel:||||||123456",
            "foo:||||||blabla", "file:||||||/P://", "file:||||||/P:/", "file:||||||/P:/TeMp"})
    public void propertiesNonStandardHref() {
        final String html = "<html>\n"
                + "<body>\n"
                + "  <a href='http://'>http://</a>\n"
                + "  <a href='https://'>https://</a>\n"
                + "  <a href='mailto:foo@foo.com'>foo@foo.com</a>\n"
                + "  <a href='tel:123456'>tel:123456</a>\n"
                + "  <a href='foo:blabla'>foo:blabla</a>\n"
                + "  <a href='p://'>p://</a>\n"
                + "  <a href='p:/'>p:/</a>\n"
                + "  <a href='p:/TeMp'>p:/TeMp</a>\n"
                + "  <script>\n"
                + "  var links = document.getElementsByTagName('a');\n"
                + "  for (var i = 0; i < links.length; i++) {\n"
                + "    var link = links[i];\n"
                + "    var props = [link.protocol, link.host, link.hostname, \n"
                + "           link.search, link.hash, link.port, link.pathname];\n"
                + "    alert(props.join('|'));\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"null", "hi"})
    public void charset() {
        attribute("charset", "hi");
    }

    private void attribute(final String attribute, final String value) {
        final String html =
                "<html>\n"
                        + "  <body onload='test()'>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var a = document.createElement('a');\n"
                        + "        alert(a." + attribute + ");\n"
                        + "        a." + attribute + " = '" + value + "';\n"
                        + "        alert(a." + attribute + ");\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"null", "0,0"})
    public void coords() {
        attribute("coords", "0,0");
    }


    @Test
    @Alerts({"null", "en"})
    public void hreflang() {
        attribute("hreflang", "en");
    }

    @Test
    @Alerts({"null", "null", "null", "null", "http://www.htmlunit.org",
            "http://www.htmlunit.org:1234", "https://www.htmlunit.org:1234"})
    public void originAttrib() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        for(i=0; i<7; i++) {\n"
                        + "          var a = document.getElementById('a'+i);\n"
                        + "          alert(a.origin);\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='a0'>a0</a>\n"
                        + "    <a id='a1' href=''>a1</a>\n"
                        + "    <a id='a2' href='  \t '>a2</a>\n"
                        + "    <a id='a3' href='relative.html'>a3</a>\n"
                        + "    <a id='a4' href='http://www.htmlunit.org/index.html'>a4</a>\n"
                        + "    <a id='a5' href='http://www.htmlunit.org:1234/index.html'>a5</a>\n"
                        + "    <a id='a6' href='https://www.htmlunit.org:1234/index.html'>a6</a>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"-null", "-", "-  \t ", "no-referrer-no-referrer",
            "origin-origin", "unsafe-url-unsafe-url", "-unknown"})
    public void referrerPolicy() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        for(i=0; i<7; i++) {\n"
                        + "          var a = document.getElementById('a'+i);\n"
                        + "          alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='a0'>a0</a>\n"
                        + "    <a id='a1' referrerPolicy=''>a1</a>\n"
                        + "    <a id='a2' referrerPolicy='  \t '>a2</a>\n"
                        + "    <a id='a3' referrerPolicy='no-referrer'>a3</a>\n"
                        + "    <a id='a4' referrerPolicy='origin'>a4</a>\n"
                        + "    <a id='a5' referrerPolicy='unsafe-url'>a5</a>\n"
                        + "    <a id='a6' referrerPolicy='unknown'>a6</a>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"origin-origin", "-unknown", "no-referrer-no-referrer",
            "-", "no-referrer-NO-reFerrer", "origin-origin", "- ", "-unknown"})
    public void setReferrerPolicy() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var a = document.getElementById('tester');\n"
                        + "        alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"

                        + "        a.referrerPolicy = 'unknown';\n"
                        + "        alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"

                        + "        a.referrerPolicy = 'no-referrer';\n"
                        + "        alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"

                        + "        a.referrerPolicy = '';\n"
                        + "        alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"

                        + "        a.referrerPolicy = 'NO-reFerrer';\n"
                        + "        alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"

                        + "        a.setAttribute('referrerPolicy', 'origin');\n"
                        + "        alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"

                        + "        a.setAttribute('referrerPolicy', ' ');\n"
                        + "        alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"

                        + "        a.setAttribute('referrerPolicy', 'unknown');\n"
                        + "        alert(a.referrerPolicy + '-' + a.getAttribute('referrerPolicy'));\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='tester' referrerPolicy='origin'>a4</a>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLButtonElement]", "[object HTMLButtonElement]",
            "", "http://srv/htmlunit.org"})
    public void focus() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var testNode = document.getElementById('myButton');\n"
                        + "      testNode.focus();\n"
                        + "      alert(document.activeElement);\n"
                        + "      testNode = document.getElementById('myA');\n"
                        + "      testNode.focus();\n"
                        + "      alert(document.activeElement);\n"
                        + "      testNode = document.getElementById('myHrefEmpty');\n"
                        + "      testNode.focus();\n"
                        + "      alert(document.activeElement);\n"
                        + "      testNode = document.getElementById('myHref');\n"
                        + "      testNode.focus();\n"
                        + "      alert(document.activeElement);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "  <button id='myButton'>Press</button>\n"
                        + "  <a id='myA'>anchor</a>\n"
                        + "  <a id='myHrefEmpty' href=''>anchor</a>\n"
                        + "  <a id='myHref' href='http://srv/htmlunit.org'>anchor</a>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "alternate help", "prefetch", "prefetch", "not supported", "notsupported"})
    public void readWriteRel() {
        final String html
                = "<html><body><a id='a1'>a1</a><a id='a2' rel='alternate help'>a2</a><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"
                + "a1.rel = 'prefetch';\n"
                + "a2.rel = 'prefetch';\n"
                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"
                + "a1.rel = 'not supported';\n"
                + "a2.rel = 'notsupported';\n"
                + "alert(a1.rel);\n"
                + "alert(a2.rel);\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "2", "alternate", "help"})
    public void relList() {
        final String html
                = "<html><body><a id='a1'>a1</a><a id='a2' rel='alternate help'>a2</a><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "2", "2", "1", "alternate", "help", "abc", "alternate help", "abc"})
    public void setRelListString() {
        final String html
                = "<html><body><a id='a1'>a1</a><a id='a2' rel='alternate help'>a2</a><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  a1.relList = 'alternate help';\n"
                + "  a2.relList = 'abc';\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"
                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "2", "0", "0", "", "  \\t"})
    public void setRelListStringBlank() {
        final String html
                = "<html><body><a id='a1'>a1</a><a id='a2' rel='alternate help'>a2</a><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  a1.relList = '';\n"
                + "  a2.relList = '  \t';\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "2", "1", "1", "null", "null", "null", "null"})
    public void setRelListNull() {
        final String html
                = "<html><body><a id='a1'>a1</a><a id='a2' rel='alternate help'>a2</a><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  a1.relList = null;\n"
                + "  a2.relList = null;\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"
                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "2", "1", "1", "undefined", "undefined", "undefined", "undefined"})
    public void setRelListUndefined() {
        final String html
                = "<html><body><a id='a1'>a1</a><a id='a2' rel='alternate help'>a2</a><script>\n"
                + "var a1 = document.getElementById('a1'), a2 = document.getElementById('a2');\n"
                + "try {\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  a1.relList = undefined;\n"
                + "  a2.relList = undefined;\n"
                + "  alert(a1.relList.length);\n"
                + "  alert(a2.relList.length);\n"
                + "  for (var i = 0; i < a1.relList.length; i++) {\n"
                + "    alert(a1.relList[i]);\n"
                + "  }\n"
                + "  for (var i = 0; i < a2.relList.length; i++) {\n"
                + "    alert(a2.relList[i]);\n"
                + "  }\n"
                + "  alert(a1.rel);\n"
                + "  alert(a2.rel);\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"", "user", "user", "",
            "", "",
            "Tester", "https://Tester:password@developer.mozilla.org/",
            "Tester", "https://Tester@developer.mozilla.org/",
            "Tester", "https://Tester@developer.mozilla.org/"})
    public void readWriteUsername() {
        final String html
                = "<html><body><a id='a1'>a1</a>"
                + "<a id='a2' href='https://user:password@developer.mozilla.org'>a2</a>"
                + "<a id='a3' href='https://user@developer.mozilla.org'>a3</a>"
                + "<a id='a4' href='https://developer.mozilla.org'>a3</a>"
                + "    <script>\n"
                + "var a1 = document.getElementById('a1'),"
                + "a2 = document.getElementById('a2'),"
                + "a3 = document.getElementById('a3'),"
                + "a4 = document.getElementById('a4');\n"
                + "alert(a1.username);\n"
                + "alert(a2.username);\n"
                + "alert(a3.username);\n"
                + "alert(a4.username);\n"
                + "if (a1.username != undefined) {\n"
                + "a1.username = 'Tester';\n"
                + "a2.username = 'Tester';\n"
                + "a3.username = 'Tester';\n"
                + "a4.username = 'Tester';\n"
                + "alert(a1.username);\n"
                + "alert(a1.href);\n"
                + "alert(a2.username);\n"
                + "alert(a2.href);\n"
                + "alert(a3.username);\n"
                + "alert(a3.href);\n"
                + "alert(a4.username);\n"
                + "alert(a4.href);\n"
                + "}\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"", "password", "password", "",
            "", "",
            "Tester", "https://user:Tester@developer.mozilla.org/",
            "Tester", "https://:Tester@developer.mozilla.org/",
            "Tester", "https://:Tester@developer.mozilla.org/"})
    public void readWritePassword() {
        final String html
                = "<html><body><a id='a1'>a1</a>"
                + "<a id='a2' href='https://user:password@developer.mozilla.org'>a2</a>"
                + "<a id='a3' href='https://:password@developer.mozilla.org'>a3</a>"
                + "<a id='a4' href='https://developer.mozilla.org'>a3</a>"
                + "    <script>\n"
                + "var a1 = document.getElementById('a1'),"
                + "a2 = document.getElementById('a2'),"
                + "a3 = document.getElementById('a3'),"
                + "a4 = document.getElementById('a4');\n"
                + "alert(a1.password);\n"
                + "alert(a2.password);\n"
                + "alert(a3.password);\n"
                + "alert(a4.password);\n"
                + "if (a1.password != undefined) {\n"
                + "a1.password = 'Tester';\n"
                + "a2.password = 'Tester';\n"
                + "a3.password = 'Tester';\n"
                + "a4.password = 'Tester';\n"
                + "alert(a1.password);\n"
                + "alert(a1.href);\n"
                + "alert(a2.password);\n"
                + "alert(a2.href);\n"
                + "alert(a3.password);\n"
                + "alert(a3.href);\n"
                + "alert(a4.password);\n"
                + "alert(a4.href);\n"
                + "}\n"
                + "</script></body></html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"http:", "https:", "https:///foo.html#O"})
    public void readWriteProtocol() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var tester = document.getElementById('tester');\n"
                        + "        alert(tester.protocol);\n"

                        + "        tester.protocol = 'httPS';\n"
                        + "        alert(tester.protocol);\n"
                        + "        alert(tester.href);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  <head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='tester' href='foo.html#O'>link 1</a>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"http:", "axdeg:", "axdeg:///foo.html#O"})
    public void readWriteProtocolUnknown() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var tester = document.getElementById('tester');\n"
                        + "        alert(tester.protocol);\n"

                        + "        tester.protocol = 'axdeg';\n"
                        + "        alert(tester.protocol);\n"
                        + "        alert(tester.href);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  <head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='tester' href='foo.html#O'>link 1</a>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"http:", "https:", "https:///foo.html#O"})
    public void readWriteProtocolIncludingColon() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var tester = document.getElementById('tester');\n"
                        + "        alert(tester.protocol);\n"
                        + "        tester.protocol = 'https:';\n"
                        + "        alert(tester.protocol);\n"
                        + "        alert(tester.href);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  <head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='tester' href='foo.html#O'>link 1</a>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"http:", "https:", "https:///foo.html#O"})
    public void readWriteProtocolWithUrl() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var tester = document.getElementById('tester');\n"
                        + "        alert(tester.protocol);\n"
                        + "        tester.protocol = 'https://www.htmlunit.org';\n"
                        + "        alert(tester.protocol);\n"
                        + "        alert(tester.href);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  <head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='tester' href='foo.html#O'>link 1</a>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"http:", "http:", "http:///foo.html#O",
            "http:", "http:///abc_xyz://localhost/foo.html"})
    public void readWriteProtocolBroken() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var tester = document.getElementById('tester');\n"
                        + "        alert(tester.protocol);\n"

                        + "        try {\n"
                        + "          tester.protocol = ' axdeg ';\n"
                        + "          alert(tester.protocol);\n"
                        + "          alert(tester.href);\n"
                        + "        } catch(e) { alert('invalid argument') }\n"

                        + "        tester = document.getElementById('invalidHref');\n"
                        + "        alert(tester.protocol);\n"
                        + "        alert(tester.href);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  <head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='tester' href='foo.html#O'>link 1</a>\n"
                        + "    <a id='invalidHref' href='abc_xyz://localhost/foo.html'>link 1</a>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"localhost", "motion", "http:///foo.html#O"})
    public void readWriteAnchorHostname() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var tester = document.getElementById('tester');\n"
                        + "        alert(tester.hostname);\n"

                        + "        tester.hostname = 'motion';\n"
                        + "        alert(tester.hostname);\n"
                        + "        alert(tester.href);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  <head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='tester' href='foo.html#O'>link 1</a>\n"
                        + "  </body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"localhost", "localhost", "http://localhost:/foo.html#O",
            "localhost", "http://localhost:/foo.html#O"})
    public void readWriteAnchorHostnameEmpty() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"

                        + "      function test() {\n"
                        + "        var tester = document.getElementById('tester');\n"
                        + "        alert(tester.hostname);\n"
                        + "        tester.hostname = '';\n"
                        + "        alert(tester.hostname);\n"
                        + "        alert(tester.href);\n"
                        + "        tester.hostname = '    ';\n"
                        + "        alert(tester.hostname);\n"
                        + "        alert(tester.href);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  <head>\n"
                        + "  <body onload='test()'>\n"
                        + "    <a id='tester' href='foo.html#O'>link 1</a>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }
}
