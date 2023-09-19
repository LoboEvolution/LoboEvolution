/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package org.loboevolution.node;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.node.DOMTokenList}.
 */

public class DOMTokenListUnitTest extends LoboUnitTest {

    @Test
    public void various() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.body.classList;\n"
                + "  alert(list.length);\n"
                + "  alert(list.item(1));\n"
                + "  alert(list[1]);\n"
                + "  alert(list.contains('c'));\n"
                + "  list.add('d');\n"
                + "  list.remove('a');\n"
                + "  alert(list.toggle('b'));\n"
                + "  alert(list);\n"
                + "}\n"
                + "</script></head><body onload='test()' class='a b c'>\n"
                + "</body></html>";

        final String[] messages = {"3", "b", "b", "true", "false", "c d"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void noAttribute() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.body.classList;\n"
                + "  alert(list.length);\n"
                + "  alert(list.item(0));\n"
                + "  alert(list.contains('#'));\n"
                + "  list.remove('#'); alert('# removed');\n"
                + "  alert(document.body.className);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"0", null, "false", "# removed", ""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void noAttributeAdd() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.body.classList;\n"
                + "  alert(list.length);\n"
                + "  list.add('#');\n"
                + "  alert(list.length);\n"
                + "  alert(document.body.className);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"0", "1", "#"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void noAttributeToggle() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.body.classList;\n"
                + "  alert(list.length);\n"
                + "  alert(list.toggle('#'));\n"
                + "  alert(list.length);\n"
                + "  alert(document.body.className);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";

        final String[] messages = {"0", "true", "1", "#"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void length() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.getElementById('d1').classList;\n"
                + "  alert(list.length);\n"
                + "  list = document.getElementById('d2').classList;\n"
                + "  alert(list.length);\n"
                + "  list = document.getElementById('d3').classList;\n"
                + "  alert(list.length);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class=' a b c '></div>\n"
                + "  <div id='d2' class=''></div>\n"
                + "  <div id='d3' class=' a b a'></div>\n"
                + "</body></html>";

        final String[] messages = {"3", "0", "2"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void item() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.getElementById('d1').classList;\n"
                + "  for (var i = 0; i < list.length; i++) {\n"
                + "    alert(list.item(i));\n"
                + "  }\n"
                + "  alert(list.item(-1));\n"
                + "  alert(list.item(list.length));\n"
                + "  alert(list.item(100));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class=' a b \t c \n d e \u000C f \r g'></div>\n"
                + "</body></html>";

        final String[] messages = {"a", "b", "c", "d", "e", "f", "g", null, null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void itemNegative() {
        final String[] messages = {"a b", "2", null, "undefined"};
        item("a b", -1, messages);
    }

    @Test
    public void itemNegative2() {
        final String[] messages = {"a b", "2", null, "undefined"};
        item("a b", -123, messages);
    }

    @Test
    public void itemFirst() {
        final String[] messages = {"a b", "2", "a", "a"};
        item("a b", 0, messages);
    }

    @Test
    public void itemLast() {
        final String[] messages = {"a b", "2", "b", "b"};
        item("a b", 1, messages);
    }

    @Test
    public void itemOutside() {
        final String[] messages = {"a b", "2", null, "undefined"};
        item("a b", 13, messages);
    }

    private void item(final String in, final int pos, final String[] messages) {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('d1');\n"
                + "    var list = elem.classList;\n"
                + "    if (!list) { alert('no list'); return; }\n"
                + "    alert(elem.className);\n"
                + "    alert(list.length);\n"
                + "    try {\n"
                + "      alert(list.item(" + pos + "));\n"
                + "      alert(list[" + pos + "]);\n"
                + "    } catch(e) { alert('exception');}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='" + in + "'></div>\n"
                + "</body></html>";

        checkHtmlAlert(html, messages);
    }

    @Test
    public void containsEmpty() {
        final String[] messages = {"a b", "2", "false"};
        contains("a b", "", messages);
    }

    @Test
    public void containsBlank() {
        final String[] messages = {"a b", "2", "false"};
        contains("a b", " ", messages);
    }

    @Test
    public void containsTab() {
        final String[] messages = {"a b", "2", "false"};
        contains("a b", "\t", messages);
    }

    @Test
    public void containsCr() {
        final String[] messages = {"a b", "2", "false"};
        contains("a b", "\\r", messages);
    }

    @Test
    public void containsNl() {
        final String[] messages = {"a b", "2", "false"};
        contains("a b", "\\n", messages);
    }

    @Test
    public void containsVt() {
        final String[] messages = {"a b", "2", "false"};
        contains("a b", "\u000B", messages);
    }

    @Test
    public void containsInsideEmpty() {
        final String[] messages = {"", "0", "false"};
        contains("", "a", messages);
    }

    @Test
    public void containsInsideWhitespace() {
        final String[] messages = {" \t \n  ", "0", "false"};
        contains(" \t \r  ", "a", messages);
    }

    /**
     *
     */
    @Test
    public void containsInsideAtStart() {
        final String[] messages = {"a b", "2", "true"};
        contains("a b", "a", messages);
    }

    @Test
    public void containsInsideAtEnd() {
        final String[] messages = {"a b", "2", "true"};
        contains("a b", "b", messages);
    }

    @Test
    public void containsInsideSubstringAtStart() {
        final String[] messages = {"abc def", "2", "false"};
        contains("abc def", "ab", messages);
    }

    @Test
    public void containsInsideSubstringAtEnd() {
        final String[] messages = {"abc def", "2", "false"};
        contains("abc def", "bc", messages);
    }

    @Test
    public void containsInsideSubstringInside() {
        final String[] messages = {"abcd ef", "2", "false"};
        contains("abcd ef", "bc", messages);
    }

    @Test
    public void containsInsideWhitespaceAtEnd() {
        final String[] messages = {"a  ", "1", "true"};
        contains("a  ", "a", messages);
    }

    @Test
    public void containsInsideWhitespaceInFront() {
        final String[] messages = {"  a", "1", "true"};
        contains("  a", "a", messages);
    }

    @Test
    public void containsWhitespaceExisting() {
        final String[] messages = {"a \t c \n d  e", "4", "true"};
        contains("a \t c \n d  e", "c", messages);
    }

    private void contains(final String in, final String toAdd, final String[] messages) {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function alert(msg) {\n"
                + "    var ta = document.getElementById('myTextArea');\n"
                + "    ta.value += msg + '; ';\n"
                + "  }\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('d1');\n"
                + "    var list = elem.classList;\n"
                + "    if (!list) { alert('no list'); return; }\n"
                + "    alert(elem.className);\n"
                + "    alert(list.length);\n"
                + "    try {\n"
                + "      alert(list.contains('" + toAdd + "'));\n"
                + "    } catch(e) { alert('exception');}\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='" + in + "'></div>\n"
                + "  <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
                + "</body></html>";

        checkHtmlAlert(html, messages);
    }

    @Test
    public void addEmpty() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        add("a b", "", messages);
    }

    @Test
    public void addBlank() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        add("a b", " ", messages);
    }

    @Test
    public void addTab() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        add("a b", "\t", messages);
    }

    @Test
    public void addCr() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        add("a b", "\\r", messages);
    }

    @Test
    public void addNl() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        add("a b", "\\n", messages);
    }

    @Test
    public void addToEmpty() {
        final String[] messages = {"", "0", "1", "a"};
        add("", "a", messages);
    }

    @Test
    public void addToWhitespace() {
        final String[] messages = {" \t \n  ", "0", "1", "a"};
        add(" \t \r  ", "a", messages);
    }

    @Test
    public void addToWhitespaceAtEnd() {
        final String[] messages = {"a  ", "1", "2", "a b"};
        add("a  ", "b", messages);
    }

    @Test
    public void addNotExisting() {
        final String[] messages = {"a b", "2", "3", "a b c"};
        add("a b", "c", messages);
    }

    @Test
    public void addExisting() {
        final String[] messages = {"a b", "2", "2", "a b"};
        add("a b", "a", messages);
    }

    @Test
    public void addExisting2() {
        final String[] messages = {"b a", "2", "2", "b a"};
        add("b a", "a", messages);
    }

    @Test
    public void addElementWithBlank() {
        final String[] messages = {"a b a", "2", "exception", "2", "a b a"};
        add("a b a", "a b", messages);
    }


    @Test
    public void addElementWithTab() {
        final String[] messages = {"a b a\tb", "2", "exception", "2", "a b a\tb"};
        add("a b a\tb", "a\tb", messages);
    }

    private void add(final String in, final String toAdd, final String[] messages) {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function alert(msg) {\n"
                + "    var ta = document.getElementById('myTextArea');\n"
                + "    ta.value += msg + '; ';\n"
                + "  }\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('d1');\n"
                + "    var list = elem.classList;\n"
                + "    if (!list) { alert('no list'); return; }\n"
                + "    alert(elem.className);\n"
                + "    alert(list.length);\n"
                + "    try {\n"
                + "      list.add('" + toAdd + "');\n"
                + "    } catch(e) { alert('exception');}\n"
                + "    alert(list.length);\n"
                + "    alert(elem.className);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='" + in + "'></div>\n"
                + "  <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
                + "</body></html>";

        checkHtmlAlert(html, messages);
    }

    @Test
    public void addSvg() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('myId');\n"
                + "    var list = elem.classList;\n"
                + "    if (!list) { alert('no list'); return; }\n"
                + "    alert(list.length);\n"
                + "    try {\n"
                + "      list.add('new');\n"
                + "    } catch(e) { alert('exception');}\n"
                + "    alert(list.length);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <svg xmlns='http://www.w3.org/2000/svg' version='1.1'>\n"
                + "    <text id='myId' class='cls1, cls2'/>\n"
                + "  </svg>\n"
                + "</body></html>";

        final String[] messages = {"2", "3"};
        checkHtmlAlert(html, messages);
    }

    @Test
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
                + "  alert(getComputedStyle(div1, null).display);\n"
                + "  list.add('hidden');\n"
                + "  alert(getComputedStyle(div1, null).display);\n"
                + "}\n"
                + "</script>"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d1' class='nice'></div>\n"
                + "</body></html>";

        final String[] messages = {"block", "none"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void removeEmpty() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        remove("a b", "", messages);
    }

    @Test
    public void removeBlank() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        remove("a b", " ", messages);
    }

    @Test
    public void removeTab() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        remove("a b", "\t", messages);
    }

    @Test
    public void removeCr() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        remove("a b", "\\r", messages);
    }


    @Test
    public void removeNl() {
        final String[] messages = {"a b", "2", "exception", "2", "a b"};
        remove("a b", "\\n", messages);
    }

    @Test
    public void removeFromEmpty() {
        final String[] messages = {"", "0", "0", ""};
        remove("", "a", messages);
    }

    @Test
    public void removeFromWhitespace() {
        final String[] messages = {" \t \n  ", "0", "0", ""};
        remove(" \t \r  ", "a", messages);
    }

    @Test
    public void removeNotExisting() {
        final String[] messages = {"a b", "2", "2", "a b"};
        remove("a b", "c", messages);
    }

    @Test
    public void removeDuplicated() {
        final String[] messages = {"a b a", "2", "1", "b"};
        remove("a b a", "a", messages);
    }


    @Test
    public void removeElementWithBlank() {
        final String[] messages = {"a b a", "2", "exception", "2", "a b a"};
        remove("a b a", "a b", messages);
    }

    @Test
    public void removeElementWithTab() {
        final String[] messages = {"a b a\tb", "2", "exception", "2", "a b a\tb"};
        remove("a b a\tb", "a\tb", messages);
    }

    @Test
    public void removeLast() {
        final String[] messages = {"a", "1", "0", ""};
        remove("a", "a", messages);
    }

    @Test
    public void removeWhitespace() {
        final String[] messages = {"a \t c \n d  e", "4", "3", "a d e"};
        remove("a \t c \n d  e", "c", messages);
    }

    private void remove(final String in, final String toRemove, final String[] messages) {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "  function alert(msg) {\n"
                + "    var ta = document.getElementById('myTextArea');\n"
                + "    ta.value += msg + '; ';\n"
                + "  }\n"
                + "  function test() {\n"
                + "    var elem = document.getElementById('d1');\n"
                + "    var list = elem.classList;\n"
                + "    if (!list) { alert('no list'); return; }\n"
                + "    alert(elem.className);\n"
                + "    alert(list.length);\n"
                + "    try {\n"
                + "      list.remove('" + toRemove + "');\n"
                + "    } catch(e) { alert('exception');}\n"
                + "    alert(list.length);\n"
                + "    alert(elem.className);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='" + in + "'></div>\n"
                + "  <textarea id='myTextArea' cols='80' rows='30'></textarea>\n"
                + "</body></html>";
        checkHtmlAlert(html, messages);
    }

    @Test
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

                + "  alert(getComputedStyle(div1, null).display);\n"
                + "  list.remove('hidden');\n"
                + "  alert(getComputedStyle(div1, null).display);\n"
                + "}\n"
                + "</script>"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d1' class='hidden'></div>\n"
                + "</body></html>";

        final String[] messages = {"none", "block"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void toggle() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.getElementById('d1').classList;\n"
                + "  try {\n"
                + "    list.toggle('ab e');\n"
                + "  } catch(e) { alert('exception');}\n"
                + "  try {\n"
                + "    list.toggle('');\n"
                + "  } catch(e) { alert('exception');}\n"
                + "  alert(list.length);\n"
                + "  alert(list.contains('e'));\n"
                + "  alert(list.toggle('e'));\n"
                + "  alert(list.length);\n"
                + "  alert(list.contains('e'));\n"
                + "  alert(list.toggle('e'));\n"
                + "  alert(list.length);\n"
                + "  alert(list.contains('e'));\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='a e'></div>\n"
                + "</body></html>";

        final String[] messages = {"exception", "exception", "2", "true", "false", "1", "false", "true", "2", "true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void in() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var list = document.getElementById('d1').classList;\n"
                + "  alert(list.length);\n"
                + "  alert(-1 in list);\n"
                + "  alert(0 in list);\n"
                + "  alert(2 in list);\n"
                + "  alert(42 in list);\n"
                + "}\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='d1' class='a e'></div>\n"
                + "</body></html>";

        final String[] messages = {"2", "false", "true", "false", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
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
                + "  alert(getComputedStyle(div1, null).display);\n"
                + "  list.toggle('hidden');\n"
                + "  alert(getComputedStyle(div1, null).display);\n"
                + "  list.toggle('hidden');\n"
                + "  alert(getComputedStyle(div1, null).display);\n"
                + "}\n"
                + "</script>"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='d1' class='hidden'></div>\n"
                + "</body></html>";

        final String[] messages = {"none", "block", "none"};
        checkHtmlAlert(html, messages);
    }
}
