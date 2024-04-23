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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.DOMImplementation;

/**
 * Tests for {@link DOMImplementation}.
 */
@ExtendWith(AlertsExtension.class)
public class DOMImplementationTest extends LoboUnitTest {


    @Test
    @Alerts({"Core 1.0: true", "Core 2.0: true", "Core 3.0: true"})
    public void hasFeatureCore() {
        hasFeature("Core", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"HTML 1.0: true", "HTML 2.0: true", "HTML 3.0: true"})
    public void hasFeatureHTML() {
        hasFeature("HTML", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"XML 1.0: true", "XML 2.0: true", "XML 3.0: true"})
    public void hasFeatureXML() {
        hasFeature("XML", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"XHTML 1.0: true", "XHTML 2.0: true", "XHTML 3.0: true"})
    public void hasFeatureXHTML() {
        hasFeature("XHTML", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"Views 1.0: true", "Views 2.0: true", "Views 3.0: true"})
    public void hasFeatureViews() {
        hasFeature("Views", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"StyleSheets 1.0: true", "StyleSheets 2.0: true", "StyleSheets 3.0: true"})
    public void hasFeatureStyleSheets() {
        hasFeature("StyleSheets", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"CSS 1.0: true", "CSS 2.0: true", "CSS 3.0: true"})
    public void hasFeatureCSS() {
        hasFeature("CSS", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"CSS2 1.0: true", "CSS2 2.0: true", "CSS2 3.0: true"})
    public void hasFeatureCSS2() {
        hasFeature("CSS2", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"CSS3 1.0: true", "CSS3 2.0: true", "CSS3 3.0: true"})
    public void hasFeatureCSS3() {
        hasFeature("CSS3", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"Events 1.0: true", "Events 2.0: true", "Events 3.0: true"})
    public void hasFeatureEvents() {
        hasFeature("Events", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"UIEvents 1.0: true", "UIEvents 2.0: true", "UIEvents 3.0: true"})
    public void hasFeatureUIEvents() {
        hasFeature("UIEvents", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"MouseEvents 1.0: true", "MouseEvents 2.0: true", "MouseEvents 3.0: true"})
    public void hasFeatureMouseEvents() {
        hasFeature("MouseEvents", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"TextEvents 1.0: true", "TextEvents 2.0: true", "TextEvents 3.0: true"})
    public void hasFeatureTextEvents() {
        hasFeature("TextEvents", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"KeyboardEvents 1.0: true", "KeyboardEvents 2.0: true", "KeyboardEvents 3.0: true"})
    public void hasFeatureKeyboardEvents() {
        hasFeature("KeyboardEvents", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"MutationEvents 1.0: true", "MutationEvents 2.0: true", "MutationEvents 3.0: true"})
    public void hasFeatureMutationEvents() {
        hasFeature("MutationEvents", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"MutationNameEvents 1.0: true", "MutationNameEvents 2.0: true", "MutationNameEvents 3.0: true"})
    public void hasFeatureMutationNameEvents() {
        hasFeature("MutationNameEvents", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"HTMLEvents 1.0: true", "HTMLEvents 2.0: true", "HTMLEvents 3.0: true"})
    public void hasFeatureHTMLEvents() {
        hasFeature("HTMLEvents", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"Range 1.0: true", "Range 2.0: true", "Range 3.0: true"})
    public void hasFeatureRange() {
        hasFeature("Range", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"Traversal 1.0: true", "Traversal 2.0: true", "Traversal 3.0: true"})
    public void hasFeatureTraversal() {
        hasFeature("Traversal", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"LS 1.0: true", "LS 2.0: true", "LS 3.0: true"})
    public void hasFeatureLS() {
        hasFeature("LS", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"LS-Async 1.0: true", "LS-Async 2.0: true", "LS-Async 3.0: true"})
    public void hasFeatureLSAsync() {
        hasFeature("LS-Async", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"Validation 1.0: true", "Validation 2.0: true", "Validation 3.0: true"})
    public void hasFeatureValidation() {
        hasFeature("Validation", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"XPath 1.0: true", "XPath 2.0: true", "XPath 3.0: true"})
    public void hasFeatureXPath() {
        hasFeature("XPath", "['1.0', '2.0', '3.0']");
    }

    @Test
    @Alerts({"http://www.w3.org/TR/SVG11/feature#BasicStructure 1.0: true",
            "http://www.w3.org/TR/SVG11/feature#BasicStructure 1.1: true",
            "http://www.w3.org/TR/SVG11/feature#BasicStructure 1.2: true"})
    public void hasFeatureSVG_BasicStructure() {
        hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure", "['1.0', '1.1', '1.2']");
    }

    @Test
    @Alerts({"http://www.w3.org/TR/SVG11/feature#Shape 1.0: true",
            "http://www.w3.org/TR/SVG11/feature#Shape 1.1: true",
            "http://www.w3.org/TR/SVG11/feature#Shape 1.2: true"})
    public void hasFeatureSVG_Shape() {
        hasFeature("http://www.w3.org/TR/SVG11/feature#Shape", "['1.0', '1.1', '1.2']");
    }

    private void hasFeature(final String feature, final String versions) {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var feature = '" + feature + "';\n"
                + "    var versions = " + versions + ";\n"
                + "    for (var j = 0; j < versions.length; j++) {\n"
                + "      var version = versions[j];\n"
                + "     alert(feature + ' ' + version + ': ' + document.implementation.hasFeature(feature, version));\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("[object XMLDocument]")
    public void createDocument() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "   alert(document.implementation.createDocument('', '', null));\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"mydoc", "null", "mydoc", "null"})
    public void createDocument_qualifiedName() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('', 'mydoc', null);\n"
                + "   alert(doc.documentElement.tagName);\n"
                + "   alert(doc.documentElement.prefix);\n"
                + "   alert(doc.documentElement.localName);\n"
                + "   alert(doc.documentElement.namespaceURI);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"mydoc", "null", "mydoc", "http://mynamespace"})
    public void createDocumentNamespaceAndQualifiedName() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('http://mynamespace', 'mydoc', null);\n"
                + "   alert(doc.documentElement.tagName);\n"
                + "   alert(doc.documentElement.prefix);\n"
                + "   alert(doc.documentElement.localName);\n"
                + "   alert(doc.documentElement.namespaceURI);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"m:mydoc", "m", "mydoc", "http://mynamespace"})
    public void createDocumentNamespaceAndQualifiedNameWithPrefix() {
        final String html = "<html><head>"
                + "<script>\n"
                + "  function test() {\n"
                + "    var doc = document.implementation.createDocument('http://mynamespace', 'm:mydoc', null);\n"
                + "   alert(doc.documentElement.tagName);\n"
                + "   alert(doc.documentElement.prefix);\n"
                + "   alert(doc.documentElement.localName);\n"
                + "   alert(doc.documentElement.namespaceURI);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDocument]", "undefined"})
    public void createHTMLDocument() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument();\n"
                + "     alert(doc);\n"
                + "     alert(doc.window);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDocument]", "newdoctitle"})
    public void createHTMLDocumentTitle() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('newdoctitle');\n"
                + "     alert(doc);\n"
                + "     alert(doc.title);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"[object HTMLDocument]", ""})
    public void createHTMLDocumentTitleEmpty() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('');\n"
                + "     alert(doc);\n"
                + "     alert(doc.title);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("2")
    public void createHTMLDocument_jQuery() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('');\n"
                + "      doc.body.innerHTML = '<form></form><form></form>';\n"
                + "     alert(doc.body.childNodes.length);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("createdElement")
    public void createHTMLDocumentCreateElement() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('');\n"
                + "      var p = doc.createElement('p');\n"
                + "      p.innertHTML = 'createdElement';\n"
                + "     alert(p.innertHTML);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"<html><head></head><body></body></html>",
            "<html><head><title></title></head><body></body></html>",
            "<html><head><title>abc</title></head><body></body></html>"})
    public void createHTMLDocumentHtmlCode() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument();\n"
                + "     alert(doc.documentElement.outerHTML);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('');\n"
                + "     alert(doc.documentElement.outerHTML);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('abc');\n"
                + "     alert(doc.documentElement.outerHTML);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<html><head><title>test</title></head>"
            + "<body><p>This is a new paragraph.</p></body></html>")
    public void createHTMLDocumentAddParagraph() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('test');\n"
                + "      var p = doc.createElement('p');\n"
                + "      p.innerHTML = 'This is a new paragraph.';\n"
                + "      doc.body.appendChild(p);"
                + "     alert(doc.documentElement.outerHTML);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<html><head><title>test</title></head><body><p>Hello</p></body></html>")
    public void createHTMLDocumentInnerAddParagraph() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('test');\n"
                + "      doc.body.innerHTML = '<p>Hello</p>';\n"
                + "     alert(doc.documentElement.outerHTML);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("<html><head><title>test</title></head><body><img src=\"x\" onerror=\"alert(1)\"></body></html>")
    public void createHTMLDocumentInnerAddImg() {
        final String html = "<html>\n"
                + "<head>\n"  
                + "<script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var doc = document.implementation.createHTMLDocument('test');\n"
                + "      doc.body.innerHTML = '<img src=\"x\" onerror=\"alert(1)\">';\n"
                + "     alert(doc.documentElement.outerHTML);\n"
                + "    } catch(e) {alert('exception'); }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("before1")
    public void createHTMLDocumentInnerAddImgAddDocToIframe() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var frame = document.getElementById('theFrame');\n"
                + "      var doc = document.implementation.createHTMLDocument('test');\n"
                + "      doc.body.innerHTML = '<img src=\"x\" onerror=\"window.parent.document.title += 1\">';\n"
                + "      var destDocument = frame.contentDocument;\n"
                + "      var srcNode = doc.documentElement;\n"
                + "      var newNode = destDocument.importNode(srcNode, true);\n"
                + "      destDocument.replaceChild(newNode, destDocument.documentElement);\n"
                + "      window.parent.document.title += 'before';"
                + "    } catch(e) { window.parent.document.title += 'exception'; }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <iframe id='theFrame' src='about:blank' />"
                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts("before import;after import;1")
    public void createHTMLDocumentInnerAddImgAddDocToIframe1() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "  function test() {\n"
                + "    try {\n"
                + "      var frame = document.getElementById('theFrame');\n"
                + "      var doc = document.implementation.createHTMLDocument('test');\n"
                + "      doc.body.innerHTML = '<img src=\"x\" onerror=\"window.parent.document.title += 1\">';\n"
                + "      var destDocument = frame.contentDocument;\n"
                + "      var srcNode = doc.documentElement;\n"
                + "      document.title += 'before import;';\n"
                + "      var newNode = destDocument.importNode(srcNode, true);\n"
                + "      document.title += 'after import;';\n"
                + "      destDocument.replaceChild(newNode, destDocument.documentElement);\n"
                + "    } catch(e) { document.title += 'exception'; }\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <iframe id='theFrame' src='about:blank' />"
                + "</body></html>";
        checkHtmlAlert(html);
    }
}
