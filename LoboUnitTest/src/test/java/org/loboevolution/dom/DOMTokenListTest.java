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
import org.loboevolution.html.dom.DOMTokenList;

/**
 * Tests for {@link DOMTokenList}.
 */
@ExtendWith(AlertsExtension.class)
public class DOMTokenListTest extends LoboUnitTest {

    @Test
    @Alerts({"3", "b", "b", "true", "false", "c d"})
    public void various() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.body.classList;\n"
                + " alert(list.length);\n"
                + " alert(list.item(1));\n"
                + " alert(list[1]);\n"
                + " alert(list.contains('c'));\n"
                + "  list.add('d');\n"
                + "  list.remove('a');\n"
                + " alert(list.toggle('b'));\n"
                + " alert(list);\n"
                + "}\n"
                + "</script></head><body onload='test()' class='a b c'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "null", "false", "# removed", ""})
    public void noAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.body.classList;\n"
                + " alert(list.length);\n"
                + " alert(list.item(0));\n"
                + " alert(list.contains('#'));\n"
                + "  list.remove('#');alert('# removed');\n"
                + " alert(document.body.className);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "undefined", "1", "#"})
    public void noAttributeAdd() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.body.classList;\n"
                + " alert(list.length);\n"
                + " alert(list.add('#'));\n"
                + " alert(list.length);\n"
                + " alert(document.body.className);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"0", "true", "1", "#"})
    public void noAttributeToggle() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.body.classList;\n"
                + " alert(list.length);\n"
                + " alert(list.toggle('#'));\n"
                + " alert(list.length);\n"
                + " alert(document.body.className);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"3", "0", "2", "8"})
    public void length() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.getElementById('d1').classList;\n"
                + " alert(list.length);\n"
                + "  list = document.getElementById('d2').classList;\n"
                + " alert(list.length);\n"
                + "  list = document.getElementById('d3').classList;\n"
                + " alert(list.length);\n"
                + "  list = document.getElementById('d4').classList;\n"
                + " alert(list.length);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class=' a b c '></div>\n"
                + "  <div id='d2' class=''></div>\n"
                + "  <div id='d3' class=' a b a'></div>\n"
                + "  <div id='d4' class=' a b \t c \n d \u000B e \u000C f \r g'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"a", "b", "c", "d", "\u000B", "e", "f", "g", "null", "null", "null"})
    public void item() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.getElementById('d1').classList;\n"
                + "  for (var i = 0; i < list.length; i++) {\n"
                + "   alert(list.item(i));\n"
                + "  }\n"
                + " alert(list.item(-1));\n"
                + " alert(list.item(list.length));\n"
                + " alert(list.item(100));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class=' a b \t c \n d \u000B e \u000C f \r g'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"a b", "2", "null", "undefined"})
    public void itemNegative() {
        item(-1);
    }

    @Test
    @Alerts({"a b", "2", "null", "undefined"})
    public void itemNegative2() {
        item(-123);
    }

    @Test
    @Alerts({"a b", "2", "a", "a"})
    public void itemFirst() {
        item(0);
    }

    @Test
    @Alerts({"a b", "2", "b", "b"})
    public void itemLast() {
        item(1);
    }

    @Test
    @Alerts({"a b", "2", "null", "undefined"})
    public void itemOutside() {
        item(13);
    }

    private void item(final int pos) {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('d1');\n"
                + "    var config = { attributes: true, attributeOldValue: true };\n"
                + "    var observer = new MutationObserver(function(mutations) {\n"
                + "      mutations.forEach(function(mutation) {\n"
                + "       alert(mutation.attributeName + ' changed old: ' + mutation.oldValue);\n"
                + "      });\n"
                + "    });\n"
                + "    observer.observe(elem, config);"

                + "    var list = elem.classList;\n"
                + "    if (!list) {alert('no list'); return; }\n"
                + "   alert(elem.className);\n"
                + "   alert(list.length);\n"
                + "    try {\n"
                + "     alert(list.item(" + pos + "));\n"
                + "     alert(list[" + pos + "]);\n"
                + "    } catch(e) {alert('exception');}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='" + "a b" + "'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"a b", "2", "false"})
    public void containsEmpty() {
        contains("a b", "");
    }

    @Test
    @Alerts({"a b", "2", "false"})
    public void containsBlank() {
        contains("a b", " ");
    }

    @Test
    @Alerts({"a b", "2", "false"})
    public void containsTab() {
        contains("a b", "\t");
    }

    @Test
    @Alerts({"a b", "2", "false"})
    public void containsCr() {
        contains("a b", "\\r");
    }

    @Test
    @Alerts({"a b", "2", "false"})
    public void containsNl() {
        contains("a b", "\\n");
    }

    @Test
    @Alerts({"a b", "2", "false"})
    public void containsVt() {
        contains("a b", "\u000B");
    }

    @Test
    @Alerts({"", "0", "false"})
    public void containsInsideEmpty() {
        contains("", "a");
    }

    @Test
    @Alerts({" \\t \\n  ", "0", "false"})
    public void containsInsideWhitespace() {
        contains(" \t \r  ", "a");
    }

    @Test
    @Alerts({"a b", "2", "true"})
    public void containsInsideAtStart() {
        contains("a b", "a");
    }

    @Test
    @Alerts({"a b", "2", "true"})
    public void containsInsideAtEnd() {
        contains("a b", "b");
    }

    @Test
    @Alerts({"abc def", "2", "false"})
    public void containsInsideSubstringAtStart() {
        contains("abc def", "ab");
    }

    @Test
    @Alerts({"abc def", "2", "false"})
    public void containsInsideSubstringAtEnd() {
        contains("abc def", "bc");
    }

    @Test
    @Alerts({"abcd ef", "2", "false"})
    public void containsInsideSubstringInside() {
        contains("abcd ef", "bc");
    }

    @Test
    @Alerts({"a  ", "1", "true"})
    public void containsInsideWhitespaceAtEnd() {
        contains("a  ", "a");
    }

    @Test
    @Alerts({"  a", "1", "true"})
    public void containsInsideWhitespaceInFront() {
        contains("  a", "a");
    }

    @Test
    @Alerts({"a \\t c \\n d  e", "4", "true"})
    public void containsWhitespaceExisting() {
        contains("a \t c \n d  e", "c");
    }

    private void contains(final String in, final String toAdd) {
        final String html
                = "<html><head>\n"
               + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('d1');\n"
                + "    var config = { attributes: true, attributeOldValue: true };\n"
                + "    var observer = new MutationObserver(function(mutations) {\n"
                + "      mutations.forEach(function(mutation) {\n"
                + "       alert(mutation.attributeName + ' changed old: ' + mutation.oldValue);\n"
                + "      });\n"
                + "    });\n"
                + "    observer.observe(elem, config);"

                + "    var list = elem.classList;\n"
                + "    if (!list) {alert('no list'); return; }\n"
                + "   alert(elem.className);\n"
                + "   alert(list.length);\n"
                + "    try {\n"
                + "     alert(list.contains('" + toAdd + "'));\n"
                + "    } catch(e) {alert('exception');}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='" + in + "'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void addEmpty() {
        add("a b", "");
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void addBlank() {
        add("a b", " ");
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void addTab() {
        add("a b", "\t");
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void addCr() {
        add("a b", "\\r");
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void addNl() {
        add("a b", "\\n");
    }

    @Test
    @Alerts({"a b", "2", "3", "a b \u000B", "class changed old: a b"})
    public void addVt() {
        add("a b", "\u000B");
    }

    @Test
    @Alerts({"", "0", "1", "a", "class changed old: "})
    public void addToEmpty() {
        add("", "a");
    }

    @Test
    @Alerts({" \\t \\n  ", "0", "1", "a", "class changed old:  \\t \\n  "})
    public void addToWhitespace() {
        add(" \t \r  ", "a");
    }

    @Test
    @Alerts({"a  ", "1", "2", "a b", "class changed old: a  "})
    public void addToWhitespaceAtEnd() {
        add("a  ", "b");
    }

    @Test
    @Alerts({"a b", "2", "3", "a b c", "class changed old: a b"})
    public void addNotExisting() {
        add("a b", "c");
    }

    @Test
    @Alerts({"a b", "2", "2", "a b", "class changed old: a b"})
    public void addExisting() {
        add("a b", "a");
    }

    @Test
    @Alerts({"b a", "2", "2", "b a", "class changed old: b a"})
    public void addExisting2() {
        add("b a", "a");
    }

    @Test
    @Alerts({"a b a", "2", "exception", "2", "a b a"})
    public void addElementWithBlank() {
        add("a b a", "a b");
    }

    @Test
    @Alerts({"a b a\\tb", "2", "exception", "2", "a b a\\tb"})
    public void addElementWithTab() {
        add("a b a\tb", "a\tb");
    }

    @Test
    @Alerts({"a \\t c \\n d  e", "4", "4", "a c d e",
            "class changed old: a \\t c \\n d  e"})
    public void addToWhitespaceExisting() {
        add("a \t c \n d  e", "c");
    }

    private void add(final String in, final String toAdd) {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('d1');\n"
                + "    var config = { attributes: true, attributeOldValue: true };\n"
                + "    var observer = new MutationObserver(function(mutations) {\n"
                + "      mutations.forEach(function(mutation) {\n"
                + "       alert(mutation.attributeName + ' changed old: ' + mutation.oldValue);\n"
                + "      });\n"
                + "    });\n"
                + "    observer.observe(elem, config);"

                + "    var list = elem.classList;\n"
                + "    if (!list) {alert('no list'); return; }\n"
                + "   alert(elem.className);\n"
                + "   alert(list.length);\n"
                + "    try {\n"
                + "      list.add('" + toAdd + "');\n"
                + "    } catch(e) {alert('exception');}\n"
                + "   alert(list.length);\n"
                + "   alert(elem.className);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d1' class='" + in + "'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "3"})
    public void addSvg() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('myId');\n"
                + "    var list = elem.classList;\n"
                + "    if (!list) {alert('no list'); return; }\n"
                + "   alert(list.length);\n"
                + "    try {\n"
                + "      list.add('new');\n"
                + "    } catch(e) {alert('exception');}\n"
                + "   alert(list.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <svg xmlns='http://www.w3.org/2000/svg' version='1.1'>\n"
                + "    <text id='myId' class='cls1, cls2'/>\n"
                + "  </svg>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"block", "none"})
    public void addStyleCheck() {
        final String html
                = "<html><head>\n"
                + "<style>\n"
                + "  #d1.hidden { display: none; }\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var div1 = document.getElementById('d1');\n"
                + "  var list = div1.classList;\n"
                + " alert(getComputedStyle(div1, null).display);\n"
                + "  list.add('hidden');\n"
                + " alert(getComputedStyle(div1, null).display);\n"
                + "}\n"
                + "</script>"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d1' class='nice'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void removeEmpty() {
        remove("a b", "");
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void removeBlank() {
        remove("a b", " ");
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void removeTab() {
        remove("a b", "\t");
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void removeCr() {
        remove("a b", "\\r");
    }

    @Test
    @Alerts({"a b", "2", "exception", "2", "a b"})
    public void removeNl() {
        remove("a b", "\\n");
    }

    @Test
    @Alerts({"a b", "2", "2", "a b", "class changed old: a b"})
    public void removeVt() {
        remove("a b", "\u000B");
    }

    @Test
    @Alerts({"", "0", "0", "", "class changed old: "})
    public void removeFromEmpty() {
        remove("", "a");
    }

    @Test
    @Alerts({" \\t \\n  ", "0", "0", "", "class changed old:  \\t \\n  "})
    public void removeFromWhitespace() {
        remove(" \t \r  ", "a");
    }

    @Test
    @Alerts({"a b", "2", "2", "a b", "class changed old: a b"})
    public void removeNotExisting() {
        remove("a b", "c");
    }

    @Test
    @Alerts({"a b a", "2", "1", "b", "class changed old: a b a"})
    public void removeDuplicated() {
        remove("a b a", "a");
    }

    @Test
    @Alerts({"a b a", "2", "exception", "2", "a b a"})
    public void removeElementWithBlank() {
        remove("a b a", "a b");
    }

    @Test
    @Alerts({"a b a\\tb", "2", "exception", "2", "a b a\\tb"})
    public void removeElementWithTab() {
        remove("a b a\tb", "a\tb");
    }

    @Test
    @Alerts({"a", "1", "0", "", "class changed old: a"})
    public void removeLast() {
        remove("a", "a");
    }

    @Test
    @Alerts({"a \\t c \\n d  e", "4", "3", "a d e",
            "class changed old: a \\t c \\n d  e"})
    public void removeWhitespace() {
        remove("a \t c \n d  e", "c");
    }

    private void remove(final String in, final String toRemove) {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('d1');\n"
                + "    var config = { attributes: true, attributeOldValue: true };\n"
                + "    var observer = new MutationObserver(function(mutations) {\n"
                + "      mutations.forEach(function(mutation) {\n"
                + "       alert(mutation.attributeName + ' changed old: ' + mutation.oldValue);\n"
                + "      });\n"
                + "    });\n"
                + "    observer.observe(elem, config);"

                + "    var list = elem.classList;\n"
                + "    if (!list) {alert('no list'); return; }\n"
                + "   alert(elem.className);\n"
                + "   alert(list.length);\n"
                + "    try {\n"
                + "      list.remove('" + toRemove + "');\n"
                + "    } catch(e) {alert('exception');}\n"
                + "   alert(list.length);\n"
                + "   alert(elem.className);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d1' class='" + in + "'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"none", "block"})
    public void removeStyleCheck() {
        final String html
                = "<html><head>\n"
                + "<style>\n"
                + "  #d1.hidden { display: none; }\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var div1 = document.getElementById('d1');\n"
                + "  var list = div1.classList;\n"
                + " alert(getComputedStyle(div1, null).display);\n"
                + "  list.remove('hidden');\n"
                + " alert(getComputedStyle(div1, null).display);\n"
                + "}\n"
                + "</script>"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d1' class='hidden'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"exception", "exception", "2", "true", "false", "1", "false", "true", "2", "true",
            "class changed old: a e", "class changed old: a"})
    public void toggle() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var elem = document.getElementById('d1');\n"
                + "    var config = { attributes: true, attributeOldValue: true };\n"
                + "    var observer = new MutationObserver(function(mutations) {\n"
                + "      mutations.forEach(function(mutation) {\n"
                + "       alert(mutation.attributeName + ' changed old: ' + mutation.oldValue);\n"
                + "      });\n"
                + "    });\n"
                + "    observer.observe(elem, config);"

                + "  var list = elem.classList;\n"
                + "  try {\n"
                + "    list.toggle('ab e');\n"
                + "  } catch(e) {alert('exception');}\n"
                + "  try {\n"
                + "    list.toggle('');\n"
                + "  } catch(e) {alert('exception');}\n"
                + " alert(list.length);\n"
                + " alert(list.contains('e'));\n"
                + " alert(list.toggle('e'));\n"
                + " alert(list.length);\n"
                + " alert(list.contains('e'));\n"
                + " alert(list.toggle('e'));\n"
                + " alert(list.length);\n"
                + " alert(list.contains('e'));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='a e'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "false", "true", "false", "false"})
    public void in() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.getElementById('d1').classList;\n"
                + " alert(list.length);\n"
                + " alert(-1 in list);\n"
                + " alert(0 in list);\n"
                + " alert(2 in list);\n"
                + " alert(42 in list);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='a e'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"none", "block", "none"})
    public void toggleStyleCheck() {
        final String html
                = "<html><head>\n"
                + "<style>\n"
                + "  #d1.hidden { display: none; }\n"
                + "</style>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var div1 = document.getElementById('d1');\n"
                + "  var list = div1.classList;\n"
                + " alert(getComputedStyle(div1, null).display);\n"
                + "  list.toggle('hidden');\n"
                + " alert(getComputedStyle(div1, null).display);\n"
                + "  list.toggle('hidden');\n"
                + " alert(getComputedStyle(div1, null).display);\n"
                + "}\n"
                + "</script>"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d1' class='hidden'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
