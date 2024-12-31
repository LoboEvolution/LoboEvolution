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
package org.loboevolution.dom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.traversal.TreeWalker;

/**
 * Tests for {@link TreeWalker}.
 */
@ExtendWith(AlertsExtension.class)
public class TreeWalkerTest extends LoboUnitTest {
    private static final String contentStart = "<html><head><title></title>\n"
            + "<script>\n"
            + "function safeTagName(o) {\n"
            + "  return o ? o.tagName : undefined\n"
            + "}\n"
            + "function alertTreeWalker(tw) {\n"
            + " alert(safeTagName(tw.root));\n"
            + " alert(safeTagName(tw.currentNode));\n"
            + " alert(tw.whatToShow);\n"
            + " alert(tw.expandEntityReferences);\n"
            + "}\n"
            + "function test() {\n"
            + "  try {\n";

    private static final String contentEnd = "\n  } catch(e) {alert('exception') }\n"
            + "\n}\n</script></head>\n"
            + "<body onload='test()'>\n"
            + "<div id='theDiv'>Hello, <span id='theSpan'>this is a test for"
            + "<a id='theA' href='http://htmlunit.sf.net'>HtmlUnit</a> support"
            + "</div>\n"
            + "<p id='theP'>for TreeWalker's</p>\n"
            + "</body></html>";
    private static final String contentStart2 = "<html><head><title></title>\n"
            + "<script>\n"
            + "function safeTagName(o) {\n"
            + "  return o ? o.tagName : undefined\n"
            + "}\n"
            + "function test() {\n"
            + "  try {\n";
    private static final String contentEnd2 = "\n  } catch(e) {alert('exception') }\n"
            + "\n}\n</script></head>\n"
            + "<body onload='test()'>\n"
            + "<div id='theDiv'>Hello, <span id='theSpan'>this is a test for"
            + "<a id='theA' href='http://htmlunit.sf.net'>HtmlUnit</a> support"
            + "</div>\n"
            + "<p id='theP'>for <br/>TreeWalkers<span>something</span>that is <a>important to me</a></p>\n"
            + "<span>something <code>codey</code>goes <pre>  here</pre></span>\n"
            + "</body></html>";

    private void test(final String script) {
        final String html = contentStart + script + contentEnd;

        checkHtmlAlert(html);
    }

    private void test2(final String script) {
        final String html = contentStart2 + script + contentEnd2;

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"BODY", "BODY", "1", "undefined"})
    public void getters1() {
        final String script = "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false);"
                + "alertTreeWalker(tw);";

        test(script);
    }

    @Test
    @Alerts({"A", "A", "4294967295", "undefined"})
    // The spec states it is an unsigned long.
    public void getters2() {
        final String script = "var theA = document.getElementById('theA');\n"
                + "var tw = document.createTreeWalker(theA, NodeFilter.SHOW_ALL, null, true);\n"
                + "alertTreeWalker(tw);\n";

        test(script);
    }

    @Test
    @Alerts({"BODY", "DIV", "1", "undefined"})
    public void firstChild() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.firstChild();\n"
                        + "alertTreeWalker(tw);\n";

        test(script);
    }

    @Test
    @Alerts({"BODY", "SPAN", "1", "undefined"})
    public void firstChild2() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.currentNode = document.getElementById('theDiv');\n"
                        + "tw.firstChild();\n"
                        + "alertTreeWalker(tw);\n";

        test(script);
    }

    @Test
    @Alerts({"BODY", "P", "1", "undefined"})
    public void lastChild() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.lastChild();\n"
                        + "alertTreeWalker(tw);\n";

        test(script);
    }

    @Test
    @Alerts({"BODY", "SPAN", "1", "undefined"})
    public void lastChild2() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.currentNode = document.getElementById('theDiv');\n"
                        + "tw.lastChild();\n"
                        + "alertTreeWalker(tw);\n";

        test(script);
    }

    @Test
    @Alerts({"BODY", "BODY", "1", "undefined", "null"})
    public void parentNode() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.currentNode = document.getElementById('theDiv');\n"
                        + "tw.parentNode();\n"
                        + "alertTreeWalker(tw);\n"
                        + "alert(tw.parentNode());";

        test(script);
    }

    @Test
    @Alerts({"BODY", "DIV", "1", "undefined"})
    public void parentNode2() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.currentNode = document.getElementById('theSpan');\n"
                        + "tw.parentNode();\n"
                        + "alertTreeWalker(tw);";

        test(script);
    }

    @Test
    @Alerts({"BODY", "P", "1", "undefined", "null"})
    public void siblings() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.currentNode = document.getElementById('theDiv');\n"
                        + "tw.nextSibling();\n"
                        + "alertTreeWalker(tw);\n"
                        + "alert(tw.nextSibling());\n";

        test(script);
    }

    @Test
    @Alerts({"BODY", "DIV", "1", "undefined", "null"})
    public void siblings2() {
        final String script1 =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.currentNode = document.getElementById('theP');\n"
                        + "tw.previousSibling();\n"
                        + "alertTreeWalker(tw);\n"
                        + "alert(tw.previousSibling());\n";

        test(script1);
    }

    @Test
    @Alerts({"BODY", "DIV", "SPAN", "A", "P", "undefined", "P"})
    public void next() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "alert(safeTagName(tw.currentNode));\n"
                        + "alert(safeTagName(tw.nextNode()));\n"
                        + "alert(safeTagName(tw.nextNode()));\n"
                        + "alert(safeTagName(tw.nextNode()));\n"
                        + "alert(safeTagName(tw.nextNode()));\n"
                        + "alert(safeTagName(tw.nextNode()));\n"
                        + "alert(safeTagName(tw.currentNode));\n";

        test(script);
    }

    @Test
    @Alerts({"P", "A", "SPAN", "DIV", "BODY", "undefined", "BODY"})
    public void previous() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.currentNode = document.getElementById('theP');\n"
                        + "alert(safeTagName(tw.currentNode));\n"
                        + "alert(safeTagName(tw.previousNode()));\n"
                        + "alert(safeTagName(tw.previousNode()));\n"
                        + "alert(safeTagName(tw.previousNode()));\n"
                        + "alert(safeTagName(tw.previousNode()));\n"
                        + "alert(safeTagName(tw.previousNode()));\n"
                        + "alert(safeTagName(tw.currentNode));\n";

        test(script);
    }

    @Test
    @Alerts({"DIV", "SPAN", "A", "undefined", "P", "BODY", "undefined", "SPAN", "undefined",
            "P", "SPAN", "CODE", "PRE", "undefined"})
    public void walking() {
        final String script = "var tw = document.createTreeWalker(document.body, 1, null, true);\n"
                + "alert(safeTagName(tw.firstChild()));\n"
                + "alert(safeTagName(tw.firstChild()));\n"
                + "alert(safeTagName(tw.lastChild()));\n"
                + "alert(safeTagName(tw.lastChild()));\n"
                + "alert(safeTagName(tw.nextNode()));\n"
                + "alert(safeTagName(tw.parentNode()));\n"
                + "alert(safeTagName(tw.parentNode()));\n"
                + "alert(safeTagName(tw.lastChild()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.previousSibling()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.nextNode()));\n"
                + "alert(safeTagName(tw.nextNode()));\n"
                + "alert(safeTagName(tw.nextNode()));\n";

        test2(script);
    }

    @Test
    @Alerts({"TITLE", "SCRIPT", "HEAD", "HTML", "HEAD", "BODY", "undefined"})
    public void walkingOutsideTheRoot() {
        final String script =
                "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, true);\n"
                        + "tw.currentNode = document.firstChild.firstChild;\n"
                        + "alert(safeTagName(tw.firstChild()));\n"
                        + "alert(safeTagName(tw.nextNode()));\n"
                        + "alert(safeTagName(tw.parentNode()));\n"
                        + "alert(safeTagName(tw.previousNode()));\n"
                        + "alert(safeTagName(tw.firstChild()));\n"
                        + "alert(safeTagName(tw.nextSibling()));\n"
                        + "alert(safeTagName(tw.previousSibling()));\n";

        test2(script);
    }

    @Test
    @Alerts("exception")
    public void nullRoot() {
        final String script = "try {\n"
                + "var tw = document.createTreeWalker(null, NodeFilter.SHOW_ELEMENT, null, true);\n"
                + "} catch(e) {alert('exception'); }\n";

        test2(script);
    }

    @Test
    @Alerts({"TITLE", "undefined", "HEAD", "HTML", "HEAD", "BODY", "undefined"})
    public void simpleFilter() {
        final String script = "var noScripts = {\n"
                + "  acceptNode: function(node) {\n"
                + "    if (node.tagName == 'SCRIPT')\n"
                + "      return NodeFilter.FILTER_REJECT;\n"
                // using number rather that object field causes Rhino to pass a Double
                + "    return 1; // NodeFilter.FILTER_ACCEPT \n"
                + "  }\n"
                + "}\n"
                + "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, noScripts, true);\n"
                + "tw.currentNode = document.firstChild.firstChild;\n"
                + "alert(safeTagName(tw.firstChild()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.parentNode()));\n"
                + "alert(safeTagName(tw.previousNode()));\n"
                + "alert(safeTagName(tw.firstChild()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.previousSibling()));\n";

        test2(script);
    }

    @Test
    @Alerts({"TITLE", "undefined", "HEAD", "HTML", "HEAD", "BODY", "undefined"})
    public void simpleFilterAsAFunction() {
        final String script = "var noScripts = function(node) {\n"
                + "  if (node.tagName == 'SCRIPT')\n"
                + "    return NodeFilter.FILTER_REJECT;\n"
                + "  return 1;\n"
                + "}\n"
                + "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, noScripts, true);\n"
                + "tw.currentNode = document.firstChild.firstChild;\n"
                + "alert(safeTagName(tw.firstChild()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.parentNode()));\n"
                + "alert(safeTagName(tw.previousNode()));\n"
                + "alert(safeTagName(tw.firstChild()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.previousSibling()));\n";

        test2(script);
    }

    @Test
    @Alerts("exception")
    public void emptyFilter() {
        final String script = "try {\n"
                + "var tw = document.createTreeWalker(null, NodeFilter.SHOW_ELEMENT, {}, true);\n"
                + "} catch(e) {alert('exception'); }\n";

        test2(script);
    }

    @Test
    @Alerts({"P", "undefined"})
    public void secondFilterReject() {
        final String script = "var noScripts = {\n"
                + "  acceptNode: function(node) {\n"
                + "    if (node.tagName == 'SPAN' || node.tagName == 'DIV') {\n"
                + "      return NodeFilter.FILTER_REJECT;\n"
                + "    }\n"
                + "    return NodeFilter.FILTER_ACCEPT;\n"
                + "  }\n"
                + "}\n"
                + "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, noScripts, true);\n"
                + "alert(safeTagName(tw.firstChild()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n";

        test2(script);
    }

    @Test
    @Alerts({"A", "P", "CODE", "PRE", "undefined"})
    public void secondFilterSkip() {
        final String script = "var noScripts = {acceptNode: function(node) {if (node.tagName == 'SPAN' ||"
                + "node.tagName == 'DIV') return NodeFilter.FILTER_SKIP;"
                + "return NodeFilter.FILTER_ACCEPT}};\n"
                + "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, noScripts, true);\n"
                + "alert(safeTagName(tw.firstChild()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n"
                + "alert(safeTagName(tw.nextSibling()));\n";

        test2(script);
    }

    @Test
    @Alerts({"P", "undefined"})
    public void secondFilterRejectReverse() {
        final String script = "var noScripts = {acceptNode: function(node) {if (node.tagName == 'SPAN' ||"
                + "node.tagName == 'DIV') return NodeFilter.FILTER_REJECT;"
                + "return NodeFilter.FILTER_ACCEPT}};\n"
                + "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, noScripts, true);\n"
                + "alert(safeTagName(tw.lastChild()));\n"
                + "alert(safeTagName(tw.previousSibling()));\n";

        test2(script);
    }

    @Test
    @Alerts({"PRE", "CODE", "P", "A", "undefined"})
    public void secondFilterSkipReverse() {
        final String script = "var noScripts = {acceptNode: function(node) {if (node.tagName == 'SPAN' ||"
                + "node.tagName == 'DIV') return NodeFilter.FILTER_SKIP; return NodeFilter.FILTER_ACCEPT}};\n"
                + "var tw = document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, noScripts, true);\n"
                + "alert(safeTagName(tw.lastChild()));\n"
                + "alert(safeTagName(tw.previousSibling()));\n"
                + "alert(safeTagName(tw.previousSibling()));\n"
                + "alert(safeTagName(tw.previousSibling()));\n"
                + "alert(safeTagName(tw.previousSibling()));";

        test2(script);
    }
}
