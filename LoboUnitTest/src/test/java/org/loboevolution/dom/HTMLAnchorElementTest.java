/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */


package org.loboevolution.dom;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * * Tests for {@link org.loboevolution.html.dom.HTMLAnchorElement}.
 */
public class HTMLAnchorElementTest extends LoboUnitTest {

    /**
     * <p>attributes.</p>
     */
    @Test
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

        final String[] messages = {"", null, null, "test.css", "stylesheet", "stylesheet1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>defaultConversionToString.</p>
     */
    @Test
    public void defaultConversionToString() {
        final String html
                = "<html><head><title>AnchorTest</title><script>\n"
                + "function test() {\n"
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

        final String[] messages = {"", "foo.html", "javascript:void(0)", "#", "mailto:"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getAttribute_and_href.</p>
     */
    @Test
    public void getAttribute_and_href() {
        final String html
                = "<html><head><title>AnchorTest</title>\n"
                + "<script>\n"
                + "  function doTest() {\n"
                + "    var anchorElement = document.getElementById('link1');"
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
                + "<body onload='doTest()'>\n"
                + "  <a href='testsite1.html' id='link1' name='testanchor'>bla</a>\n"
                + "  <a href='mailto:' id='link2'>mail</a>\n"
                + "</body></html>";

        final String[] messages = {"testsite1.html", "testsite1.html", "testsite2.html", "testsite2.html", "link1", "testanchor", "mailto:"};
        checkHtmlAlert(html, messages);

    }

    /**
     * <p>getDefaultValue.</p>
     */
    @Test
    public void getDefaultValue() {
        final String html
                = "<html><head><title>AnchorTest</title>\n"
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

        final String[] messages = {"http://htmlunit.sourceforge.net/", "test", "#test", "#", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getDefaultValueWithHash.</p>
     */
    @Test
    public void getDefaultValueWithHash() {
        final String html
                = "<html><head><title>AnchorTest</title>\n"
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

        final String[] messages = {"http://htmlunit.sourceforge.net/", "test", "#test", "#", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getDefaultValueWithHashAndFileName.</p>
     */
    @Test
    public void getDefaultValueWithHashAndFileName() {
        final String html
                = "<html><head><title>AnchorTest</title>\n"
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

        final String[] messages = {"http://htmlunit.sourceforge.net/", "test", "#test", "#", ""};
        checkHtmlAlert(html, messages);

    }

    /**
     * <p>onclickToString.</p>
     */
    @Test
    public void onclickToString() {
        final String html
                = "<html><head><title>AnchorTest</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    for (var i = 0; i < document.links.length; i++) {\n"
                + "      var onclick = document.links[i].onclick;\n"
                + "      alert(onclick ? (onclick.toString().indexOf('alert(') != -1) : 'not defined');\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <a href='foo.html' onClick='alert(\"on click\")'>a1</a>\n"
                + "  <a href='foo2.html'>a2</a>\n"
                + "</body></html>";

        final String[] messages = {"true", "not defined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>readWriteAccessKey.</p>
     */
    @Test
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

        final String[] messages = {"undefined", "undefined", "a", "A", "a8", "8Afoo", "8", "@"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>hrefTrimmed.</p>
     */
    @Test
    public void hrefTrimmed() {
        final String html = "<html><head><title>AnchorTest</title>\n"
                + "<script>\n"
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

        final String[] messages = {"9", "9", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>typeof.</p>
     */
    @Test
    public void typeof() {
        final String html =
                "<html><head><title>foo</title>\n"
                + "<script>\n"
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
                + "  <a id='link' href='" + URL_SECOND + "'>link</a>\n"
                + "</body></html>";

        final String[] messages = {URL_SECOND, "object", "exception",};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getType.</p>
     */
    @Test
    public void getType() {
        final String html = "<html><head><title>foo</title>\n"
                + "<script>\n"
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
                + "  <a id='idWithout' href='" + URL_SECOND + "'>link</a>\n"
                + "  <a id='idEmpty' href='" + URL_SECOND + "' type=''>link</a>\n"
                + "  <a id='idText' href='" + URL_SECOND + "' type='text/html'>link</a>\n"
                + "  <a id='idCase' href='" + URL_SECOND + "' type='TExT/hTMl'>link</a>\n"
                + "  <a id='idWhitespace' href='" + URL_SECOND + "' type=' text/html '>link</a>\n"
                + "  <a id='idPdf' href='" + URL_SECOND + "' type='application/pdf'>link</a>\n"
                + "  <a id='idUnknown' href='" + URL_SECOND + "' type='unknown'>link</a>\n"
                + "</body></html>";

        final String[] messages = {null, "", "text/html", "TExT/hTMl", " text/html ", "application/pdf", "unknown"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>setType.</p>
     */
    @Test
    public void setType() {
        final String html = "<html><head><title>foo</title>\n"
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
                + "  <a id='id' href='" + URL_SECOND + "' type='text/html'>link</a>\n"
                + "</body></html>";

        final String[] messages = {"text/html", "", " TExT/hTMl  ", "unknown", "application/pdf"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>charset.</p>
     */
    @Test
    public void charset() {
        final String[] messages = {null, "hi"};
        attribute("charset", "hi", messages);
    }

    /**
     * <p>hreflang.</p>
     */
    @Test
    public void hreflang() {
        final String[] messages = {null, "en"};
        attribute("hreflang", "en", messages);
    }

    /**
     * <p>focus.</p>
     */
    @Test
    public void focus() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <title>Test</title>\n"
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

        final String[] messages = {"[object HTMLButtonElement]", "[object HTMLButtonElement]", "", "http://srv/htmlunit.org"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>readWriteRel.</p>
     */
    @Test
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
        final String[] messages = {null, "alternate help", "prefetch", "prefetch", "not supported", "notsupported"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>relList.</p>
     */
    @Test
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
        final String[] messages = {"0", "2", "alternate", "help"};
        checkHtmlAlert(html, messages);
    }

    private void attribute(final String attribute, final String value, final String[] messages) {
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

        checkHtmlAlert(html, messages);
    }
}
