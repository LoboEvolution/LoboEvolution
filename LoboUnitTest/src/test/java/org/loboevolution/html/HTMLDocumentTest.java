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
package org.loboevolution.html;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLDocument;

/**
 * Tests for {@link HTMLDocument}.
 */
@ExtendWith(AlertsExtension.class)
public class HTMLDocumentTest extends LoboUnitTest {


    static final String[] JQUERY_CUSTOM_SELECTORS = {"div.submenu-last:last",
            "*#_sizzle__ div.submenu-last:last", "div:animated", "div:animated", "*:button", "*:checkbox", "div:even",
            "*:file", "div:first", "td:gt(4)", ":header", ":hidden", ":image", ":input", "td:lt(4)",
            ":odd", ":password", ":radio", ":reset", ":selected", ":submit", ":text", ":visible"
    };

    @Test
    @Alerts("[object HTMLDocument]")
    public void scriptableToString() {
        final String html =
                "<html><head>\n"
                        + "    <script>\n"
                        + "  function test() {\n"
                        + "    alert(document);\n"
                        + "  }\n"
                        + "</script></head>\n"
                        + "<body onload='test()'>\n"
                        + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "DIV", "2"})
    public void getElementsByTagName() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementsByTagName('div').length);\n"
                + "    document.getElementById('myDiv').innerHTML = \"<P><DIV id='secondDiv'></DIV></P>\";\n"
                + "    alert(document.getElementById('secondDiv').nodeName);\n"
                + "    alert(document.getElementsByTagName('div').length);\n"
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
    @Alerts({"object", "div1", "span2", "span3", "2", "1", "1", "0", "0", "0"})
    public void getElementsByClassName() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  alert(typeof document.getElementsByClassName);\n"
                + "  try {\n"
                + "    var elements = document.getElementsByClassName('foo');\n"
                + "    for (var i = 0; i < elements.length; i++) {\n"
                + "      alert(elements[i].id);\n"
                + "    }\n"
                + "    alert(document.getElementsByClassName('red').length);\n"
                + "    alert(document.getElementsByClassName('foo red').length);\n"
                + "    alert(document.getElementsByClassName('red foo').length);\n"
                + "    alert(document.getElementsByClassName('blue foo').length);\n"
                + "    alert(document.getElementsByClassName('*').length);\n"
                + "    alert(document.getElementsByClassName(null).length);\n"
                + "  }\n"
                + "  catch (e) { alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div class='foo' id='div1'><span class='c2'>hello</span>\n"
                + "  <span class='foo' id='span2'>World!</span></div>\n"
                + "<span class='foo red' id='span3'>again</span>\n"
                + "<span class='red' id='span4'>bye</span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("BackCompat")
    public void compatMode() {
        compatMode("");
    }

    @Test
    @Alerts("BackCompat")
    public void compatModeDoctype() {
        compatMode("<!DOCTYPE>");
    }

    @Test
    @Alerts("CSS1Compat")
    public void compatModeHtml() {
        compatMode("<html>");
    }

    @Test
    @Alerts("BackCompat")
    public void compatModeQuestion() {
        compatMode("<?DOCTYPE html>");
    }

    @Test
    @Alerts("BackCompat")
    public void compatModeHtmlTransitional40NoUrl() {
        compatMode("<!DOCTYPE html PUBLIC \"-//W3C//Dtd html 4.0 transitional//EN\">");
    }

    @Test
    @Alerts("BackCompat")
    public void compatModeHtmlTransitionalNoUrl() {
        compatMode("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
    }

    @Test
    @Alerts("BackCompat")
    public void compatModeHtmlTransitional40() {
        compatMode("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\" "
                + "\"http://www.w3.org/TR/html4/loose.dtd\">");
    }

    @Test
    @Alerts("CSS1Compat")
    public void compatModeHtmlTransitional() {
        compatMode("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" "
                + "\"http://www.w3.org/TR/html4/loose.dtd\">");
    }

    @Test
    @Alerts("CSS1Compat")
    public void compatModeHtmlstrict40() {
        compatMode("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
    }

    @Test
    @Alerts("CSS1Compat")
    public void compatModeHtmlstrict() {
        compatMode("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
    }

    @Test
    @Alerts("CSS1Compat")
    public void compatModeXhtmlTransitional() {
        compatMode("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
    }

    @Test
    @Alerts("CSS1Compat")
    public void compatModeXhtmlstrict() {
        compatMode("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
    }

    private void compatMode(final String doctype) {
        final String html = doctype + "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.compatMode);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("false")
    public void uniqueID() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.uniqueID != undefined);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDivElement]", "[object HTMLUnknownElement]", "[object Element]"})
    public void createDocumentNS() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"                
                + "function test() {\n"
                + "  var elt = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');\n"
                + "  alert(elt);\n"
                + "  var elt = document.createElementNS('http://www.w3.org/1999/xhtml', 'foo');\n"
                + "  alert(elt);\n"
                + "  elt = document.createElementNS('blabla', 'div');\n"
                + "  alert(elt);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object SVGSVGElement]")
    public void createDocumentNSsvg() {
        final String html = "<html><body>\n"
                + "    <script>\n"
                + "try {\n"
                + "  var elt = document.createElementNS('http://www.w3.org/2000/svg', 'svg');\n"
                + "  alert(elt);\n"
                + "} catch (e) { alert('exception'); }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object SVGRectElement]")
    public void createElementNS() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.createElementNS('http://www.w3.org/2000/svg', 'rect'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createDocumentNSXul() {
        final String html = "<html><body>\n"
                + "    <script>\n"
                + "try {\n"
                + "  var inner = document.createElementNS('http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul',"
                + "'label');\n"
                + "  inner.setAttribute('value', 'Hello');\n"
                + "  inner.style['fontFamily'] = 'inherit';\n"
                + "  document.body.appendChild(inner);\n"
                + "  alert(document.body.lastChild.value);\n"
                + "}\n"
                + "catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void hasXmlNamespaceSupport() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(typeof(document.createElementNS) != \"undefined\");\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLCollection]", "0"})
    public void applets() {
        final String html = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  alert(document.applets);\n"
                + "  alert(document.applets.length);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"imported: [object HTMLScriptElement]", "replaced"})
    public void importNodescript() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var d = document.implementation.createDocument(null, null, null);\n"
                + "    var xhtml = \"<html xmlns='http://www.w3.org/1999/xhtml'><sc\" "
                + "     + \"ript>alert('o'); scriptEvaluated=true;</scr\" + \"ipt></html>\";\n"
                + "    var newDoc = (new DOMParser()).parseFromString(xhtml, 'text/xml');\n"
                + "    var theScript = newDoc.getElementsByTagName('script')[0];\n"
                + "    var importedScript = window.document.importNode(theScript, true);\n"
                + "    alert('imported: ' + importedScript);\n"
                + "    var theSpan = document.getElementById('s1');\n"
                + "    document.body.replaceChild(importedScript, theSpan);\n"
                + "    alert('replaced');\n"
                + "  } catch (e) { alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <span id='s1'></span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"imported: [object HTMLDivElement]", "replaced"})
    public void importNodescriptChild() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var d = document.implementation.createDocument(null, null, null);\n"
                + "    var xhtml = \"<html xmlns='http://www.w3.org/1999/xhtml'><div id='myDiv'><sc\" "
                + "     + \"ript>alert('o'); scriptEvaluated=true;</scr\" + \"ipt></div></html>\";\n"
                + "    var newDoc = (new DOMParser()).parseFromString(xhtml, 'text/xml');\n"
                + "    var theDiv = newDoc.getElementById('myDiv');\n"
                + "    var importedDiv = window.document.importNode(theDiv, true);\n"
                + "    alert('imported: ' + importedDiv);\n"
                + "    var theSpan = document.getElementById('s1');\n"
                + "    document.body.replaceChild(importedDiv, theSpan);\n"
                + "    alert('replaced');\n"
                + "  } catch (e) { alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <span id='s1'></span>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("clicked")
    public void dispatchEvent() {
        final String html =
                "<html>\n"
                        + "    <script>\n"
                        + "  function doTest() {\n"
                        + "    var e = document.createEvent('MouseEvent');\n"
                        + "    e.initMouseEvent('click', true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);\n"
                        + "    document.dispatchEvent(e);\n"
                        + "  }\n"
                        + "  function clickListener() {\n"
                        + "    alert('clicked');\n"
                        + "  }\n"
                        + "  document.addEventListener('click', clickListener, true);\n"
                        + "</script>\n"
                        + "<body onload='doTest()'>foo</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "exception"})
    public void namespaces() {
        final String html =
                "<body><script>\n"
                        + "var ns = document.namespaces;\n"
                        + "alert(ns);\n"
                        + "try {\n"
                        + "  alert(ns.length);\n"
                        + "  ns.add('f', 'urn:f');\n"
                        + "  alert(ns.length);\n"
                        + "  alert(ns.item(0).name);\n"
                        + "  alert(ns[0].name);\n"
                        + "  alert(ns(0).name);\n"
                        + "  alert(ns('f').name);\n"
                        + "  alert(ns.item('f').urn);\n"
                        + "  alert(ns['f'].urn);\n"
                        + "  alert(ns == document.namespaces);\n"
                        + "}\n"
                        + "catch(e) { alert('exception') }\n"
                        + "</script></body>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void documentMethodsWithoutDocument() {
        final String html
                = "<div id='d' name='d'>d</div>\n"
                + "    <script>\n"
                + "try {\n"
                + "  var i = document.getElementById; alert(i('d').id);\n"
                + "  var n = document.getElementsByName; alert(n('d').length);\n"
                + "} catch(e) { alert('exception') }\n"
                + "</script>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "#0000aa", "#0000aa", "x", "x"})
    public void bgColor() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(document.bgColor);\n"
                        + "        alert(b.bgColor);\n"
                        + "        document.bgColor = '#0000aa';\n"
                        + "        alert(document.bgColor);\n"
                        + "        alert(b.bgColor);\n"
                        + "        document.bgColor = 'x';\n"
                        + "        alert(document.bgColor);\n"
                        + "        alert(b.bgColor);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLCollection]", "4", "red"})
    public void identicalIDs() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        alert(document.all['Item']);\n"
                        + "        alert(document.all.Item.length);\n"
                        + "        if (document.all.Item.length) {\n"
                        + "          alert(document.all.Item[1].style.color);\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>\n"
                        + "    <span id='Item' style='color:black'></span>\n"
                        + "    <span id='Item' style='color:red'></span>\n"
                        + "    <span id='Item' style='color:green'></span>\n"
                        + "    <span id='Item' style='color:blue'></span>\n"
                        + "  </body>\n"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("undefined")
    public void prefix() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  alert(document.forms.fmLogin);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "  <s:form name='fmLogin' action='doLogin' method='POST'>\n"
                + "    <s:hidden name='hdUserID'/>\n"
                + "    <s:hidden name='hdPassword'/>\n"
                + "  </s:form>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    /**
     * Property lastModified returns the last modification date of the document.
     */
    @Test
    @Alerts({"string", ""})
    public void lastModifiedOnlyDate() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  alert(typeof document.lastModified);\n"
                + "  var d = new Date(document.lastModified);\n"
                + "  alert(d.toGMTString().substr(0, 17));\n" // to have results not depending on the user's time zone
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void lastModifiedNoDateHeader() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var justBeforeLoading = " + System.currentTimeMillis() + ";\n"
                + "  var d = new Date(document.lastModified);\n"
                + "  alert(d.valueOf() >= justBeforeLoading - 1000);\n" // date string format has no ms, take 1s marge
                + "  alert(d.valueOf() <= new Date().valueOf());\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Warning: this test works fine in real FF8 when started manually but fails through WebDriver.
     * Warning: opens a modal panel when run through IEDriver which needs to be closed MANUALLY.
     * If not all following test will fail.
     */
    @Test
    @Alerts({"0", "exception"})
    public void designModeselectionRangeEmpty() {
        designModeselectionRange("");
    }

    /**
     * Warning: opens a modal panel when run through IEDriver which needs to be closed MANUALLY.
     * If not all following test will fail.
     */
    @Test
    @Alerts({"1", "[object Text]"})
    public void designModeselectionRangeText() {
        designModeselectionRange("hello");
    }

    private void designModeselectionRange(final String bodyContent) {
        final String html = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "function doTest() {\n"
                + "  try {\n"
                + "    document.designMode = 'on';\n"
                + "    var s = window.getSelection();\n"
                + "    alert(s.rangeCount);\n"
                + "    alert(s.getRangeAt(0).startContainer);\n"
                + "  } catch(e) {alert('exception'); }\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='doTest()'>" // no \n here!
                + bodyContent
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("false")
    public void allDetection() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(!(!document.all));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object HTMLAllCollection]")
    public void allscriptableToString() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.all);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("not defined")
    public void frames() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  if (document.frames) {\n"
                + "    alert(document.frames == window.frames);\n"
                + "    alert(document.frames.length);\n"
                + "  } else\n"
                + "    alert('not defined');\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test();'>\n"
                + "  <iframe src='about:blank' name='foo'></iframe>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object Window]", "true"})
    public void frameAccessByName() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  alert(document.foo);\n"
                + "  alert(window.frames[0] == document.foo);\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <iframe src='about:blank' name='foo'></iframe>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "0"})
    public void getElementsByNameNotFound() {
        final String html
                = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementsByName(null).length);\n"
                + "  alert(document.getElementsByName('foo').length);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "  <div name='test'></div>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "0", "0"})
    public void getElementsByNameEmptyName() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementsByName('').length);\n"
                + "    alert(document.getElementsByName(' ').length);\n"
                + "    alert(document.getElementsByName(null).length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div name=''></div>\n"
                + "  <div name=''></div>\n"
                + "  <div></div>\n"
                + "  <div></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2"})
    public void getElementsByNameElements() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('form1').length);\n"
                + "    } catch (e) { alert('exception:f1') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('form2').length);\n"
                + "    } catch (e) { alert('exception:f2') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('frame1').length);\n"
                + "    } catch (e) { alert('exception:f1') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('frame2').length);\n"
                + "    } catch (e) { alert('exception:f2') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('input1').length);\n"
                + "    } catch (e) { alert('exception:i1') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('input2').length);\n"
                + "    } catch (e) { alert('exception:i2') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('anchor1').length);\n"
                + "    } catch (e) { alert('exception:a1') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('anchor2').length);\n"
                + "    } catch (e) { alert('exception:a2') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('image1').length);\n"
                + "    } catch (e) { alert('exception:i1') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('image2').length);\n"
                + "    } catch (e) { alert('exception:i2') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('element1').length);\n"
                + "    } catch (e) { alert('exception:e1') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('element2').length);\n"
                + "    } catch (e) { alert('exception:e2') }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <form name='form1'></form>\n"
                + "  <form name='form2'></form>\n"
                + "  <form name='form2'></form>\n"
                + "  <iframe name='frame1'></iframe>\n"
                + "  <iframe name='frame2'></iframe>\n"
                + "  <iframe name='frame2'></iframe>\n"
                + "  <input type='text' name='input1' value='1'/>\n"
                + "  <input type='text' name='input2' value='2'/>\n"
                + "  <input type='text' name='input2' value='3'/>\n"
                + "  <a name='anchor1'></a>\n"
                + "  <a name='anchor2'></a>\n"
                + "  <a name='anchor2'></a>\n"
                + "  <img name='image1'>\n"
                + "  <img name='image2'>\n"
                + "  <img name='image2'>\n"
                + "  <div name='element1'></table>\n"
                + "  <div name='element2'></div>\n"
                + "  <div name='element2'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2"})
    public void getElementsByNameFrame() {
        final String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\""
                + "\"http://www.w3.org/TR/html4/frameset.dtd\">\n"
                + "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('frame1').length);\n"
                + "    } catch (e) { alert('exception:f1') }\n"
                + "    try {\n"
                + "      alert(document.getElementsByName('frame2').length);\n"
                + "    } catch (e) { alert('exception:f2') }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<frameset onload='test()'>\n"
                + "  <frame src='" + URL_SECOND + "' name='frame1'>\n"
                + "  <frame src='" + URL_SECOND + "' name='frame2'>\n"
                + "  <frame src='" + URL_SECOND + "' name='frame2'>\n"
                + "</frameset>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "9"})
    public void getElementsByNameChangedAfterGet() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var collection = document.getElementsByName('image1');\n"
                + "    alert(collection.length);\n"
                + "    var newImage1 = document.createElement('img');\n"
                + "    newImage1.name = 'image1';\n"
                + "    document.getElementById('outer1').appendChild(newImage1);\n"
                + "    alert(collection.length);\n"
                + "    var newImage2 = document.createElement('img');\n"
                + "    newImage2.name = 'image1';\n"
                + "    document.getElementById('outer2').insertBefore(newImage2, null);\n"
                + "    alert(collection.length);\n"
                + "    var newImage3 = document.createElement('img');\n"
                + "    newImage3.name = 'image1';\n"
                + "    document.getElementById('outer3').replaceChild(newImage3, document.getElementById('inner3'));\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('outer4').outerHTML = '<img name=\"image1\">';\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('outer5').innerHTML = '<img name=\"image1\">';\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('outer6').insertAdjacentHTML('beforeend', '<img name=\"image1\">');\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('image3').setAttribute('name', 'image1');\n"
                + "    alert(collection.length);\n"
                + "    var newAttr = document.createAttribute('name');\n"
                + "    newAttr.nodeValue = 'image1';\n"
                + "    document.getElementById('image4').setAttributeNode(newAttr);\n"
                + "    alert(collection.length);\n"
                + "    try {\n"
                + "      document.getElementById('image5').setAttributeNS(null, 'name', 'image1');\n"
                + "      alert(collection.length);\n"
                + "    } catch (e) { alert('exception:setAttributeNS') }\n"
                + "    document.getElementById('outer1').removeChild(newImage1);\n"
                + "    alert(collection.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
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

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2"})
    public void getElementsByNameChangedAfterGet2() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var collection = document.getElementsByName('image1');\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('image2').name = 'image1';\n"
                + "    alert(collection.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <img name='image1'>\n"
                + "  <img id='image2'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "9"})
    public void getElementsByNameChangedAfterGetNested() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var collection = document.getElementsByName('image1');\n"
                + "    alert(collection.length);\n"
                + "    var newImage1 = document.createElement('img');\n"
                + "    newImage1.name = 'image1';\n"
                + "    document.getElementById('outer1').appendChild(newImage1);\n"
                + "    alert(collection.length);\n"
                + "    var newImage2 = document.createElement('img');\n"
                + "    newImage2.name = 'image1';\n"
                + "    document.getElementById('outer2').insertBefore(newImage2, null);\n"
                + "    alert(collection.length);\n"
                + "    var newImage3 = document.createElement('img');\n"
                + "    newImage3.name = 'image1';\n"
                + "    document.getElementById('outer3').replaceChild(newImage3, document.getElementById('inner3'));\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('outer4').outerHTML = '<img name=\"image1\">';\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('outer5').innerHTML = '<img name=\"image1\">';\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('outer6').insertAdjacentHTML('beforeend', '<img name=\"image1\">');\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('image3').setAttribute('name', 'image1');\n"
                + "    alert(collection.length);\n"
                + "    var newAttr = document.createAttribute('name');\n"
                + "    newAttr.nodeValue = 'image1';\n"
                + "    document.getElementById('image4').setAttributeNode(newAttr);\n"
                + "    alert(collection.length);\n"
                + "    try {\n"
                + "      document.getElementById('image5').setAttributeNS(null, 'name', 'image1');\n"
                + "      alert(collection.length);\n"
                + "    } catch (e) { alert('exception:setAttributeNS') }\n"
                + "    document.getElementById('outer1').removeChild(newImage1);\n"
                + "    alert(collection.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div>\n"
                + "    <img name='image1'>\n"
                + "    <div id='outer1'></div>\n"
                + "    <div id='outer2'></div>\n"
                + "    <div id='outer3'><div id='inner3'></div></div>\n"
                + "    <div id='outer4'></div>\n"
                + "    <div id='outer5'></div>\n"
                + "    <div id='outer6'></div>\n"
                + "    <img id='image2'>\n"
                + "    <img id='image3'>\n"
                + "    <img id='image4'>\n"
                + "    <img id='image5'>\n"
                + "  </div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "2"})
    public void getElementsByNameChangedAfterGetNested2() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var collection = document.getElementsByName('image1');\n"
                + "    alert(collection.length);\n"
                + "    document.getElementById('image2').name = 'image1';\n"
                + "    alert(collection.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div>\n"
                + "    <img name='image1'>\n"
                + "    <img id='image2'>\n"
                + "  </div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    /**
     * Regression test for a bug introduced by the document proxy and detected by the Dojo JavaScript library tests.
     */
    @Test
    @Alerts("true")
    public void equalityViaDifferentPaths() {
        final String html
                = "<html><body>\n"
                + "<script>"
                + "alert(document.body.parentNode.parentNode === document)\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void getBoxObjectFor() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var e = document.getElementById('log');\n"
                + "  try {\n"
                + "    var a = document.getBoxObjectFor(e);\n"
                + "    alert(a);\n"
                + "    alert(a === document.getBoxObjectFor(e));\n"
                + "    alert(a.screenX > 0);\n"
                + "    alert(a.screenY > 0);\n"
                + "  } catch (e) { alert('exception') }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='log'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"32 commands supported", "not supported: foo, 123"})
    public void queryCommandSupportedCommon() {
        final String[] commands = {"BackColor", "Bold",
                "Copy", "CreateLink", "Cut", "Delete",
                "FontName", "FontSize", "ForeColor", "FormatBlock",
                "Indent", "InsertHorizontalRule", "InsertImage", "InsertOrderedList",
                "InsertParagraph", "InsertUnorderedList", "Italic",
                "JustifyCenter", "JustifyFull", "JustifyLeft", "JustifyRight",
                "Outdent", "Paste", "Redo", "RemoveFormat",
                "SelectAll", "StrikeThrough", "Subscript", "Superscript",
                "Underline", "Undo", "Unlink",
                "foo", "123"};
        queryCommandSupported(commands);
    }

    @Test
    @Alerts({"3 commands supported", "not supported: 2D-Position, AbsolutePosition, "
            + "BlockDirLTR, BlockDirRTL, BrowseMode, ClearAuthenticationCache, CreateBookmark, "
            + "DirLTR, DirRTL, EditMode, InlineDirLTR, InlineDirRTL, InsertButton, InsertFieldset, "
            + "InsertIFrame, InsertInputButton, InsertInputCheckbox, InsertInputFileUpload, "
            + "InsertInputHidden, InsertInputImage, InsertInputPassword, InsertInputRadio, "
            + "InsertInputReset, InsertInputSubmit, InsertInputText, InsertMarquee, InsertSelectDropdown, "
            + "InsertSelectListbox, InsertTextArea, LiveResize, MultipleSelection, "
            + "Open, OverWrite, PlayImage, Refresh, RemoveParaFormat, SaveAs, SizeToControl, "
            + "SizeToControlHeight, SizeToControlWidth, Stop, StopImage, UnBookmark"})
    public void queryCommandSupportedDisctinct() {
        final String[] commands = {"2D-Position", "AbsolutePosition",
                "BlockDirLTR", "BlockDirRTL", "BrowseMode",
                "ClearAuthenticationCache", "CreateBookmark",
                "DirLTR", "DirRTL", "EditMode",
                "InlineDirLTR", "InlineDirRTL", "InsertButton", "InsertFieldset",
                "InsertIFrame", "InsertInputButton", "InsertInputCheckbox", "InsertInputFileUpload",
                "InsertInputHidden", "InsertInputImage", "InsertInputPassword", "InsertInputRadio",
                "InsertInputReset", "InsertInputSubmit", "InsertInputText", "InsertMarquee",
                "InsertSelectDropdown", "InsertSelectListbox", "InsertTextArea",
                "JustifyNone",
                "LiveResize", "MultipleSelection", "Open", "OverWrite",
                "PlayImage", "Print", "Refresh", "RemoveParaFormat",
                "SaveAs", "SizeToControl", "SizeToControlHeight", "SizeToControlWidth", "Stop", "StopImage",
                "UnBookmark", "Unselect"};

        queryCommandSupported(commands);
    }

    private void queryCommandSupported(final String... commands) {
        final String jsCommandArray = "['" + String.join("', '", commands) + "']";
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  var cmds = " + jsCommandArray + ";\n"
                + "  var nbSupported = 0;\n"
                + "  var cmdsNotSupported = [];\n"
                + "  try {\n"
                + "    for (var i = 0; i < cmds.length; i++) {\n"
                + "      var cmd = cmds[i];\n"
                + "      var b = document.queryCommandSupported(cmd);\n"
                + "      if (b)\n"
                + "        nbSupported++;\n"
                + "      else\n"
                + "        cmdsNotSupported[cmdsNotSupported.length] = cmd;\n"
                + "    }\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "  alert(nbSupported + ' commands supported');\n"
                + "  if (nbSupported != 0 && cmdsNotSupported.length > 0)\n"
                + "    alert('not supported: ' + cmdsNotSupported.join(', '));\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div id='log'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "div1"})
    public void querySelectorAll() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  .red   {color:#FF0000;}\n"
                + "  .green {color:#00FF00;}\n"
                + "  .blue  {color:#0000FF;}\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var redTags = document.querySelectorAll('.green,.red');\n"
                + "  alert(redTags.length);\n"
                + "  alert(redTags.item(0).id);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1' class='red'>First</div>\n"
                + "  <div id='div2' class='red'>Second</div>\n"
                + "  <div id='div3' class='green'>Third</div>\n"
                + "  <div id='div4' class='blue'>Fourth</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object NodeList]")
    public void querySelectorAllType() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  alert(document.querySelectorAll('html'));\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void querySelectorAllBadSelector() {
        for (final String selector : JQUERY_CUSTOM_SELECTORS) {
            doTestQuerySelectorAllBadSelector(selector);
        }
    }

    private void doTestQuerySelectorAllBadSelector(final String selector) {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  document.querySelectorAll('" + selector + "');\n"
                + "  alert('working');\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void querySelectorBadSelector() {
        for (final String selector : JQUERY_CUSTOM_SELECTORS) {
            doTestQuerySelectorBadSelector(selector);
        }
    }

    private void doTestQuerySelectorBadSelector(final String selector) {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  document.querySelector('" + selector + "');\n"
                + "  alert('working: " + selector + "');\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "div1"})
    public void querySelectorAll_quirks() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=7' />\n"
                + "<style>\n"
                + "  .red   {color:#FF0000;}\n"
                + "  .green {color:#00FF00;}\n"
                + "  .blue  {color:#0000FF;}\n"
                + "</style>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  if(document.querySelectorAll) {\n"
                + "    var redTags = document.querySelectorAll('.green,.red');\n"
                + "    alert(redTags.length);\n"
                + "    alert(redTags.item(0).id);\n"
                + "  }\n"
                + "  else\n"
                + "    alert('undefined');\n"
                + "}\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1' class='red'>First</div>\n"
                + "  <div id='div2' class='red'>Second</div>\n"
                + "  <div id='div3' class='green'>Third</div>\n"
                + "  <div id='div4' class='blue'>Fourth</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("3")
    public void querySelectorAll_implicitAttribute() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  var result = document.querySelectorAll('[disabled]');\n"
                + "  alert(result.length);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <select name='select4' id='select4' multiple='multiple'>\n"
                + "    <optgroup disabled='disabled'>\n"
                + "      <option id='option4a' class='emptyopt' value=''>Nothing</option>\n"
                + "      <option id='option4b' disabled='disabled' selected='selected' value='1'>1</option>\n"
                + "      <option id='option4c' selected='selected' value='2'>2</option>\n"
                + "    </optgroup>\n"
                + "    <option selected='selected' disabled='disabled' id='option4d' value='3'>3</option>\n"
                + "    <option id='option4e'>no value</option>\n"
                + "    </select>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"div1", "null"})
    public void querySelector() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  .red   {color:#FF0000;}\n"
                + "  .green {color:#00FF00;}\n"
                + "  .blue  {color:#0000FF;}\n"
                + "</style>\n"
                + "    <script>\n"
                + "function test() {\n"
                + "  alert(document.querySelector('.green,.red').id);\n"
                + "  alert(document.querySelector('.orange'));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='div1' class='red'>First</div>\n"
                + "  <div id='div2' class='red'>Second</div>\n"
                + "  <div id='div3' class='green'>Third</div>\n"
                + "  <div id='div4' class='blue'>Fourth</div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "0"})
    public void getElementsByTagName2() {
        final String html = "<html xmlns:ns1='http://example.com'>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.getElementsByTagName('ns1:ele').length);\n"
                + "      alert(document.getElementsByTagName('ele').length);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <ns1:ele>&nbsp;</ns1:ele>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "0"})
    public void getElementsByTagName3() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.getElementsByTagName('ns1:ele').length);\n"
                + "      alert(document.getElementsByTagName('ele').length);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <ns1:ele>&nbsp;</ns1:ele>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    public void clear() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "document.clear();\n"
                + "</script>\n"
                + "</head><body>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "", "foo=bar", "foo=hello world"})
    public void cookieWriteCookiesEnabled() {
        final String html =
                "<html><head><script>\n"

                        + "  alert(navigator.cookieEnabled);\n"
                        + "  alert(document.cookie);\n"
                        + "  document.cookie = 'foo=bar';\n"
                        + "  alert(document.cookie);\n"
                        + "  document.cookie = 'foo=hello world';\n"
                        + "  alert(document.cookie);\n"
                        + "</script>\n"
                        + "</head>\n"
                        + "<body>abc</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "a", "", "b", ""})
    public void cookieWrite2() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      alert(document.cookie);\n"
                        + "      document.cookie = 'a';\n"
                        + "      alert(document.cookie);\n"
                        + "      document.cookie = '';\n"
                        + "      alert(document.cookie);\n"
                        + "      document.cookie = 'b';\n"
                        + "      alert(document.cookie);\n"
                        + "      document.cookie = ' ';\n"
                        + "      alert(document.cookie);\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body>abc</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "a", "b"})
    public void cookieWriteValueOnly() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      alert(document.cookie);\n"
                        + "      document.cookie = 'a';\n"
                        + "      alert(document.cookie);\n"
                        + "      document.cookie = '=b';\n"
                        + "      alert(document.cookie);\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body>abc</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "test2=1", ""})
    public void writeCookieExpired() {
        final String html = "<html><body>\n"
                + "    <script>\n"
                + "alert(document.cookie);\n"
                + "document.cookie = 'test2=1';\n"
                + "alert(document.cookie);\n"
                + "document.cookie = 'test2=;expires=Fri, 02-Jan-1970 00:00:00 GMT';\n"
                + "alert(document.cookie);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void createElementNotOnlyTagName() {
        final String html = "<html><body>\n"
                + "    <script>\n"
                + "try {\n"
                + "  var t = document.createElement('<input name=x>');\n"
                + "  alert(t.tagName);\n"
                + "} catch(e) {\n"
                + "  alert('exception');\n"
                + "}\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myAttr", "null"})
    public void createAttributeNameValue() {
        final String html = "<html>\n"
                + "<head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    var node = document.createAttribute('myAttr');\n"
                + "    alert(node.name);\n"
                + "    alert(node.value);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='tester'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void getElementByIdstrict() {
        getElementByIdstrict(true);
    }

    @Test
    @Alerts("null")
    public void getElementById_quirks() {
        getElementByIdstrict(false);
    }

    private void getElementByIdstrict(final boolean xhtml) {
        final String header = xhtml ? "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" : "";
        final String html = header + "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload=test()>\n"
                + "  <a name='myId'/>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("null")
    public void getElementByIdCaseSensitivity() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('MYDIV'));\n"
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
    @Alerts({"null", "null", "null"})
    public void getElementByIdEmptyParams() {
        final String html = "<html>\n"
                + "<head>\n" 
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById(''));\n"
                + "    alert(document.getElementById(undefined));\n"
                + "    alert(document.getElementById(null));\n"
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
    @Alerts("[object HTMLHeadElement]")
    public void head() {
        final String html = "<html><body>\n"
                + "    <script>\n"
                + "  alert(document.head);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"null", "null", "#0000aa", "#0000aa", "x", "x"})
    public void fgColor() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        var b = document.getElementById('body');\n"
                        + "        alert(document.fgColor);\n"
                        + "        alert(b.text);\n"
                        + "        document.fgColor = '#0000aa';\n"
                        + "        alert(document.fgColor);\n"
                        + "        alert(b.text);\n"
                        + "        document.fgColor = 'x';\n"
                        + "        alert(document.fgColor);\n"
                        + "        alert(b.text);\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"", "true"})
    public void getSelection() {
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <script>\n"
                        + "      function test() {\n"
                        + "        if (document.getSelection) {\n"
                        + "          alert(document.getSelection());\n"
                        + "          alert(document.getSelection() === window.getSelection());\n"
                        + "        }\n"
                        + "      }\n"
                        + "    </script>\n"
                        + "  </head>\n"
                        + "  <body id='body' onload='test()'>blah</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "undefined", "false"})
    public void documentXxxFormAccess() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.foo == document.forms.foo);\n"
                + "      alert(document.blah);\n"
                + "      alert(document.blah == document.forms.foo);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div id='foo'>the div 1</div>\n"
                + "  <form name='foo' id='blah'>\n"
                + "    <input name='foo'>\n"
                + "  </form>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"UTF-8", "UTF-8", "UTF-8", "undefined"})
    public void encoding() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.inputEncoding);\n"
                + "      alert(document.characterSet);\n"
                + "      alert(document.charset);\n"
                + "      alert(document.defaultCharset);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"UTF-8", "UTF-8", "UTF-8", "undefined"})
    public void encoding2() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.inputEncoding);\n"
                + "      alert(document.characterSet);\n"
                + "      alert(document.charset);\n"
                + "      alert(document.defaultCharset);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"UTF-8", "UTF-8", "UTF-8", "undefined"})
    public void encoding3() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.inputEncoding);\n"
                + "      alert(document.characterSet);\n"
                + "      alert(document.charset);\n"
                + "      alert(document.defaultCharset);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"UTF-8", "UTF-8", "UTF-8", "undefined"})
    public void encoding4() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.inputEncoding);\n"
                + "      alert(document.characterSet);\n"
                + "      alert(document.charset);\n"
                + "      alert(document.defaultCharset);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"UTF-8", "UTF-8", "UTF-8", "undefined"})
    public void encoding5() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.inputEncoding);\n"
                + "      alert(document.characterSet);\n"
                + "      alert(document.charset);\n"
                + "      alert(document.defaultCharset);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"UTF-8", "UTF-8", "UTF-8", "undefined"})
    public void encoding6() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <meta charset='UTF-8'>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.inputEncoding);\n"
                + "      alert(document.characterSet);\n"
                + "      alert(document.charset);\n"
                + "      alert(document.defaultCharset);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "  <a id='myId' href='test?è=è'>test</a>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "BackCompat", "object", "object"})
    public void documentMode() {
        documentMode("", "");
    }

    @Test
    @Alerts({"undefined", "CSS1Compat", "object", "object"})
    public void documentModeDoctypeStrict() {
        documentMode(STANDARDS_MODE_PREFIX_, "");
    }

    @Test
    @Alerts({"undefined", "BackCompat", "object", "object"})
    public void documentModeDoctypeTransitional() {
        documentMode("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\""
                + " \"http://www.w3.org/TR/html4/loose.dtd\">\n", "");
    }

    @Test
    @Alerts({"undefined", "CSS1Compat", "object", "object"})
    public void documentModeDoctypeHTML5() {
        documentMode("<html>\n", "");
    }

    @Test
    @Alerts({"undefined", "BackCompat", "object", "object"})
    public void documentModeMetaIE5() {
        documentMode("", "  <meta http-equiv='X-UA-Compatible' content='IE=5'>\n");
    }

    @Test
    @Alerts({"undefined", "BackCompat", "object", "object"})
    public void documentModeMetaIE8() {
        documentMode("", "  <meta http-equiv='X-UA-Compatible' content='IE=8'>\n");
    }

    @Test
    @Alerts({"undefined", "CSS1Compat", "object", "object"})
    public void documentModeMetaIE8DoctypeStrict() {
        documentMode(STANDARDS_MODE_PREFIX_, "  <meta http-equiv='X-UA-Compatible' content='IE=8'>\n");
    }

    @Test
    @Alerts({"undefined", "BackCompat", "object", "object"})
    public void documentModeMetaEmulateIE8() {
        documentMode("", "  <meta http-equiv='X-UA-Compatible' content='IE=Emulate8'>\n");
    }

    @Test
    @Alerts({"undefined", "CSS1Compat", "object", "object"})
    public void documentModeMetaEmulateIE8DoctypeStrict() {
        documentMode(STANDARDS_MODE_PREFIX_,
                "  <meta http-equiv='X-UA-Compatible' content='IE=Emulate8'>\n");
    }

    @Test
    @Alerts({"undefined", "BackCompat", "object", "object"})
    public void documentModeMetaIE9() {
        documentMode("", "  <meta http-equiv='X-UA-Compatible' content='IE=9'>\n");
    }

    @Test
    @Alerts({"undefined", "CSS1Compat", "object", "object"})
    public void documentModeMetaIE9DoctypeStrict() {
        documentMode(STANDARDS_MODE_PREFIX_,
                "  <meta http-equiv='X-UA-Compatible' content='IE=9'>\n");
    }

    @Test
    @Alerts({"undefined", "BackCompat", "object", "object"})
    public void documentModeMetaIEEdge() {
        documentMode("", "  <meta http-equiv='X-UA-Compatible' content='IE=edge'>\n");
    }

    @Test
    @Alerts({"undefined", "CSS1Compat", "object", "object"})
    public void documentModeMetaIEEdgeDoctypeStrict() {
        documentMode(STANDARDS_MODE_PREFIX_,
                "  <meta http-equiv='X-UA-Compatible' content='IE=edge'>\n");
    }

    private void documentMode(final String doctype, final String meta) {
        final String html = doctype
                + "<html>\n"
                + "<head>\n"
                + meta
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.documentMode);\n"
                + "      alert(document.compatMode);\n"
                + "      alert(typeof document.querySelectorAll);\n"
                + "      alert(typeof document.getElementById('myDiv').querySelector);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("false")
    public void equalsString() {
        final String html = "<html><body>\n"
                + "    <script>\n"
                + "  alert('foo' == document);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    /**
     * Simple test that calls setCapture.
     */
    @Test
    @Alerts("undefined")
    public void setCapture() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.setCapture);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"undefined", "releaseCapture available"})
    public void releaseCapture() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.releaseCapture());\n"
                + "      alert('releaseCapture available');\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDocument]", "function HTMLDocument() { [native code] }"})
    public void type() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document);\n"
                + "      alert(HTMLDocument);\n"
                + "    } catch(e) { alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("file://C:/Users/utente/workspace/LoboEvolution/LoboUnitTest/")
    public void baseURINoBaseTag() {
        final String html = "<html>\n"
                + "<body>\n"
                + "    <script>\n"
                + "  alert(document.baseURI);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("http://myotherwebsite.com/foo")
    public void baseURIWithBaseTag() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <base href='http://myotherwebsite.com/foo'>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <script>\n"
                + "  alert(document.baseURI);\n"
                + "</script></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("http://myotherwebsite.com/foo")
    public void baseURIWithBaseTagInBody() {
        final String html = "<html>\n"
                + "<body>\n"
                + "<base href='http://myotherwebsite.com/foo'>\n"
                + "    <script>\n"
                + "  alert(document.baseURI);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("/img/")
    public void baseURIWithBaseTagAbsolutePath() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <base href='/img/'>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <script>\n"
                + "  alert(document.baseURI);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("path/to/img")
    public void baseURIWithBaseTagRelativePath() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <base href='img'>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <script>\n"
                + "  alert(document.baseURI);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("path/to/img/")
    public void baseURIWithBaseTagRelativePathslash() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <base href='img/'>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <script>\n"
                + "  alert(document.baseURI);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("path/img")
    public void baseURIWithBaseTagRelativePathParent() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <base href='../img'>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <script>\n"
                + "  alert(document.baseURI);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("img")
    public void baseURIWithBaseTagRelativePathstrange() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <base href='../../../../img'>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <script>\n"
                + "  alert(document.baseURI);\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void hasFocus() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.hasFocus());\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("complete,[object HTMLBodyElement]-complete,[object HTMLBodyElement]-")
    public void readyState() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "  var doc;\n"
                + "  function test() {\n"
                + "    var iframe = document.createElement('iframe');\n"
                + "    var textarea = document.getElementById('myTextarea');\n"
                + "    textarea.parentNode.appendChild(iframe);\n"
                + "    doc = iframe.contentWindow.document;\n"
                + "    check();\n"
                + "    setTimeout(check, 100);\n"
                + "  }\n"
                + "  function check() {\n"
                + "    var textarea = document.getElementById('myTextarea');\n"
                + "    textarea.value += doc.readyState + ',' + doc.body + '-';\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "<div>\n"
                + "  <textarea id='myTextarea' cols='80'></textarea>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("1")
    public void childElementCount() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    alert(document.childElementCount);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div/>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void embeds() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.embeds(0));\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <embed>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "exception"})
    public void plugins() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.plugins.length);\n"
                + "      alert(document.plugins(0));\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <embed>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void images() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.images(0));\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <img>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("myBody")
    public void body() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<body id='myBody' onload='alert(document.body.id)'>\n"
                + "</body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("myFrameset")
    public void bodyFrameset() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "</script>\n"
                + "</head>\n"
                + "<frameset id='myFrameset' onload='alert(document.body.id)'>\n"
                + "  <frame />\n"
                + "</frameset>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myBody", "newBody"})
    public void setBody() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.body.id);\n"
                + "      var newBody = document.createElement('body');\n"
                + "      newBody.id = 'newBody';\n"
                + "      document.body = newBody;\n"
                + "      alert(document.body.id);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body id='myBody' onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myBody", "exception"})
    public void setBodyDiv() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.body.id);\n"
                + "      var newDiv = document.createElement('div');\n"
                + "      newDiv.id = 'newDiv';\n"
                + "      document.body = newDiv;\n"
                + "      alert(document.body.id);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body id='myBody' onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myBody", "exception"})
    public void setBodyString() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.body.id);\n"
                + "      var newBody = '<body id=\"newBody\" onload=\"test()\"></body>';\n"
                + "      document.body = newBody;\n"
                + "      alert(document.body.id);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body id='myBody' onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myBody", "newFrameset"})
    public void setBodyFrameset() {
        final String html = "<html><head>\n"
                + "    <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document.body.id);\n"
                + "      var newBody = document.createElement('frameset');\n"
                + "      newBody.id = 'newFrameset';\n"
                + "      document.body = newBody;\n"
                + "      alert(document.body.id);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body id='myBody' onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
