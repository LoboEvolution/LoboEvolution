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
 * Tests for {@link org.loboevolution.html.dom.HTMLDocument}.
 */
public class HTMLDocumentTest extends LoboUnitTest {/* jQuery selectors that aren't CSS selectors.*/

    private String[] JQUERY_CUSTOM_SELECTORS = {"div.submenu-last:last",
            "*#__sizzle__ div.submenu-last:last", "div:animated", "div:animated", "*:button", "*:checkbox", "div:even",
            "*:file", "div:first", "td:gt(4)", "div:has(p)", ":header", ":hidden", ":image", ":input", "td:lt(4)",
            ":odd", ":password", ":radio", ":reset", ":selected", ":submit", ":text", ":visible"
    };

    /**
     * <p>scriptableToString.</p>
     */
    @Test
    public void scriptableToString() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    alert(document);\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "</body></html>";

        final String[] messages = {"#document"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByTagName.</p>
     */
    @Test
    public void getElementsByTagName() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
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

        final String[] messages = {"2", "DIV", "2"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByClassName.</p>
     */
    @Test
    public void getElementsByClassName() {
        final String html = "<html><head><title>First</title><script>\n"
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
                + "  }\n"
                + "  catch (e) { alert(e) }\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "<div class='foo' id='div1'><span class='c2'>hello</span>\n"
                + "  <span class='foo' id='span2'>World!</span></div>\n"
                + "<span class='foo red' id='span3'>again</span>\n"
                + "<span class='red' id='span4'>bye</span>\n"
                + "</body></html>";

        final String[] messages = {"object", "div1", "span2", "span3", "2", "1", "1", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>uniqueID.</p>
     */
    @Test
    public void uniqueID() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <title>Test</title>\n"
                + "  <script>\n"
                + "  function test() {\n"
                + "    alert(document.uniqueID != undefined);\n"
                + "  }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>createDocumentNS.</p>
     */
    @Test
    public void createDocumentNS() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<title>Test</title>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var elt = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');\n"
                + "  alert(elt);\n"
                + "  var elt = document.createElementNS('http://www.w3.org/1999/xhtml', 'foo');\n"
                + "  alert(elt);\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>";

        final String[] messages = {"[object HTMLDivElement]", "[object HTMLElement]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>createDocumentNS_svg.</p>
     */
    @Test
    public void createDocumentNS_svg() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "try {\n"
                + "  var elt = document.createElementNS('http://www.w3.org/2000/svg', 'svg');\n"
                + "  alert(elt);\n"
                + "} catch (e) { alert('exception'); }\n"
                + "</script></body></html>";

        final String[] messages = {"[object SVGSVGElement]"};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>createElementNS.</p>
     */
    @Test
    public void createElementNS() {
        final String html =
                "<html><head>\n"
                        + "<script>\n"
                        + "  function test() {\n"
                        + "    alert(document.createElementNS('http://www.w3.org/2000/svg', 'rect'));\n"
                        + "  }\n"
                        + "</script>\n"
                        + "</head><body onload='test()'>\n"
                        + "</body></html>";
        final String[] messages = {"[object SVGRectElement]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>createDocumentNS_xul.</p>
     */
    @Test
    public void createDocumentNS_xul() {
        final String html = "<html><body>\n"
                + "<script>\n"
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

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>namespaces.</p>
     */
    @Test
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

        final String[] messages = {"undefined", "exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>documentMethodsWithoutDocument.</p>
     */
    @Test
    public void documentMethodsWithoutDocument() {
        final String html
                = "<div id='d' name='d'>d</div>\n"
                + "<script>\n"
                + "try {\n"
                + "  var i = document.getElementById; alert(i('d').id);\n"
                + "  var n = document.getElementsByName; alert(n('d').length);\n"
                + "} catch(e) { alert('exception') }\n"
                + "</script>";

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>prefix.</p>
     */
    @Test
    public void prefix() {
        final String html = "<html><head><title>foo</title><script>\n"
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

        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>all_scriptableToString.</p>
     */
    @Test
    public void all_scriptableToString() {
        final String html =
                "<html><head><title>foo</title><script>\n"
                        + "  function test() {\n"
                        + "    alert(document.all);\n"
                        + "  }\n"
                        + "</script></head><body onload='test()'>\n"
                        + "</body></html>";   final String[] messages = {"[object HTMLCollection]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>frames.</p>
     */
    @Test
    public void frames() {
        final String html = "<html><head><script>\n"
                + "function test() {\n"
                + "  if (document.frames)\n"
                + "  {\n"
                + "    alert(document.frames == window.frames);\n"
                + "    alert(document.frames.length);\n"
                + "    alert(document.frames(0).location);\n"
                + "    alert(document.frames('foo').location);\n"
                + "  }\n"
                + "  else\n"
                + "    alert('not defined');\n"
                + "}\n"
                + "</script></head><body onload='test();'>\n"
                + "<iframe src='about:blank' name='foo'></iframe>\n"
                + "</body></html>";

        final String[] messages = {"not defined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByName_notFound.</p>
     */
    @Test
    public void getElementsByName_notFound() {
        final String html
                = "<html><head><title>Test</title><script>\n"
                + "function doTest() {\n"
                + "  alert(document.getElementsByName(null).length);\n"
                + "  alert(document.getElementsByName('foo').length);\n"
                + "}\n"
                + "</script></head><body onload='doTest()'>\n"
                + "  <div name='test'></div>\n"
                + "</body></html>";

        final String[] messages = {"0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByName_emptyName.</p>
     */
    @Test
    public void getElementsByName_emptyName() {
        final String html =
                "<html><head><title>foo</title><script>\n"
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

        final String[] messages = {"2", "0", "0"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementsByName_elements.</p>
     */
    @Test
    public void getElementsByName_elements() {
        final String html =
                "<html><head><title>foo</title><script>\n"
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

        final String[] messages = {"1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getBoxObjectFor.</p>
     */
    @Test
    public void getBoxObjectFor() {
        final String html = "<html><head><title>Test</title><script>\n"
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

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>querySelectorAll.</p>
     */
    @Test
    public void querySelectorAll() {
        final String html = "<html><head><title>Test</title>\n"
                + "<style>\n"
                + "  .red   {color:#FF0000;}\n"
                + "  .green {color:#00FF00;}\n"
                + "  .blue  {color:#0000FF;}\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  alert('ciao');\n"
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

        final String[] messages = {"3", "div1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>querySelectorAllType.</p>
     */
    @Test
    public void querySelectorAllType() {
        final String html = "<html><head><title>Test</title>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  alert(document.querySelectorAll('html'));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"[object NodeList]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>querySelectorAll_badSelector.</p>
     */
    @Test
    public void querySelectorAll_badSelector() {
        for (final String selector : JQUERY_CUSTOM_SELECTORS) {
            doTestQuerySelectorAll_badSelector(selector, "working");
        }
    }

    private void doTestQuerySelectorAll_badSelector(final String selector, String msg) {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  document.querySelectorAll('" + selector + "');\n"
                + "  alert('working');\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        final String[] messages = {msg};
        checkHtmlAlert(html, messages);
    }


    /**
     * <p>querySelector_badSelector.</p>
     */
    @Test
    public void querySelector_badSelector() {
        for (final String selector : JQUERY_CUSTOM_SELECTORS) {
            doTestQuerySelector_badSelector(selector, "working: " + selector);
        }
    }

    private void doTestQuerySelector_badSelector(final String selector, String msg) {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  document.querySelector('" + selector + "');\n"
                + "  alert('working: " + selector + "');\n"
                + "} catch(e) { alert('exception'); }\n"
                + "</script></body></html>";

        final String[] messages = {msg};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>querySelectorAll_quirks.</p>
     */
    @Test
    public void querySelectorAll_quirks() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<title>Test</title>\n"
                + "<meta http-equiv='X-UA-Compatible' content='IE=7' />\n"
                + "<style>\n"
                + "  .red   {color:#FF0000;}\n"
                + "  .green {color:#00FF00;}\n"
                + "  .blue  {color:#0000FF;}\n"
                + "</style>\n"
                + "<script>\n"
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

        final String[] messages = {"3", "div1"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>querySelectorAll_implicitAttribute.</p>
     */
    @Test
    public void querySelectorAll_implicitAttribute() {
        final String html = "<html><head><title>Test</title>\n"
                + "<script>\n"
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

        final String[] messages = {"3"};
        checkHtmlAlert(html, messages);
    }




    /**
     * <p>querySelector.</p>
     */
    @Test
    public void querySelector() {
        final String html = "<html><head><title>Test</title>\n"
                + "<style>\n"
                + "  .red   {color:#FF0000;}\n"
                + "  .green {color:#00FF00;}\n"
                + "  .blue  {color:#0000FF;}\n"
                + "</style>\n"
                + "<script>\n"
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

        final String[] messages = {"div1", null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>createAttributeNameValue.</p>
     */
    @Test
    public void createAttributeNameValue() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var node = document.createAttribute('myAttr');\n"
                + "    alert(node.name);\n"
                + "    alert(node.value);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='tester'></div>\n"
                + "</body></html>";

        final String[] messages = {"myAttr", ""};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementById_strict.</p>
     */
    @Test
    public void getElementById_strict() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('myId'));\n"
                + "  }\n"
                + "</script>\n"
                + "</head><body onload=test()>\n"
                + "  <a name='myId'/>\n"
                + "</body></html>";

        final String[] messages = {null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementById_caseSensitivity.</p>
     */
    @Test
    public void getElementById_caseSensitivity() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
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

        final String[] messages = {null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>getElementById_emptyParams.</p>
     */
    @Test
    public void getElementById_emptyParams() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
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

        final String[] messages = {null, null, null};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>head.</p>
     */
    @Test
    public void head() {
        final String html = "<html><body>\n"
                + "<head>\n"
                + "<script>\n"
                + "  alert(document.head);\n"
                + "</script>\n"
                + "</head>\n"
                + "</body></html>";

        final String[] messages = {"[object HTMLHeadElement]"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>document_undefined.</p>
     */
    @Test
    public void document_undefined() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.blah);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "  <div id='foo'>the div 1</div>\n"
                + "  <form name='foo' id='blah'>\n"
                + "    <input name='foo'>\n"
                + "  </form>\n"
                + "</body></html>";

        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>encoding.</p>
     */
    @Test
    public void encoding() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      alert(document.inputEncoding);\n"
                + "      alert(document.characterSet);\n"
                + "      alert(document.charset);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"UTF-8", "UTF-8", "UTF-8"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>equalsString.</p>
     */
    @Test
    public void equalsString() {
        final String html = "<html><body>\n"
                + "<script>\n"
                + "  alert('foo' == document);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"false"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>type.</p>
     */
    @Test
    public void type() {
        final String html = "<html><head><title>foo</title>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      alert(document);\n"
                + "      alert(HTMLDocument);\n"
                + "    } catch(e) { alert(e); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";

        final String[] messages = {"#document", "function HTMLDocument"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>baseURI_withBaseTag.</p>
     */
    @Test
    public void baseURI_withBaseTag() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <base href='http://myotherwebsite.com/foo'>\n"
                + "</head>\n"
                + "<body>\n"
                + "<script>\n"
                + "  alert(document.baseURI);\n"
                + "</script></body></html>";

        final String[] messages = {"http://myotherwebsite.com/foo"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>baseURI_withBaseTagInBody.</p>
     */
    @Test
    public void baseURI_withBaseTagInBody() {
        final String html = "<html>\n"
                + "<body>\n"
                + "<base href='http://myotherwebsite.com/foo'>\n"
                + "<script>\n"
                + "  alert(document.baseURI);\n"
                + "</script>\n"
                + "</body></html>";

        final String[] messages = {"http://myotherwebsite.com/foo"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>hasFocus.</p>
     */
    @Test
    public void hasFocus() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.hasFocus());\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>embeds.</p>
     */
    @Test
    public void embeds() {
        final String html = "<html><head>\n"
                + "<script>\n"
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

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>plugins.</p>
     */
    @Test
    public void plugins() {
        final String html = "<html><head>\n"
                + "<script>\n"
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

        final String[] messages = {"1", "exception"};
        checkHtmlAlert(html, messages);
    }

    /**
     * <p>images.</p>
     */
    @Test
    public void images() {
        final String html = "<html><head>\n"
                + "<script>\n"
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

        final String[] messages = {"exception"};
        checkHtmlAlert(html, messages);
    }
}
