/*
 * Copyright (c) 2002-2025 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.css;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for css pseudo selectors :not(), :is(), :where() and :has().
 */
@ExtendWith(AlertsExtension.class)
public class CSSSelector3Test extends LoboUnitTest {

    @Test
    @Alerts({"6", "[object HTMLBodyElement]", "[object HTMLLIElement]", "[object HTMLScriptElement]",
             "5", "[object HTMLBodyElement]", "[object HTMLScriptElement]",
             "5", "[object HTMLBodyElement]", "[object HTMLScriptElement]"})
    public void notElement() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head><title></title></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li>ul - item 0</li>\n"
                + "</ul>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(':not(ul)');"
                + "    alert(items.length);\n"
                + "    alert(items[3]);\n"
                + "    alert(items[4]);\n"
                + "    alert(items[5]);\n"
                + "    items = document.querySelectorAll(':not(ul, li)');"
                + "    alert(items.length);\n"
                + "    alert(items[3]);\n"
                + "    alert(items[4]);\n"
                + "    items = document.querySelectorAll(':not(ul):not(li)');"
                + "    alert(items.length);\n"
                + "    alert(items[3]);\n"
                + "    alert(items[4]);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void notStar() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head><title></title></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li>ul - item 0</li>\n"
                + "</ul>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(':not(*)');"
                + "    alert(items.length);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLUListElement]"})
    public void notId() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head><title></title></head>\n"
                + "<body>\n"
                + "<ul id='foo'>\n"
                + "  <li>ul - item 0</li>\n"
                + "</ul>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll('#foo:not(#bar)');"
                + "    alert(items.length);\n"
                + "    alert(items[0]);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "<A>my</A>", "0"})
    public void notTable() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head><title></title></head>\n"
                + "<body>\n"
                + "<table>\n"
                + "  <tr><td><a>my</a></td></tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll('body :not(table) a');"
                + "    alert(items.length);\n"
                + "    alert(items[0].outerHTML);\n"
                + "    items = document.querySelectorAll('body a:not(table a)');"
                + "    alert(items.length);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void notDoubleColon() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head><title></title></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li>ul - item 0</li>\n"
                + "</ul>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll('::not(ul)');"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"

                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLUListElement]",
             "2", "[object HTMLUListElement]", "[object HTMLOListElement]"})
    public void isElement() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li>ul - item 0</li>\n"
                + "  <li>ul - item 1</li>\n"
                + "</ul>\n"
                + "<ol>\n"
                + "  <li>ol - item 0</li>\n"
                + "  <li>ol - item 1</li>\n"
                + "</ol>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(':is(ul)');"
                + "    alert(items.length);\n"
                + "    alert(items[0]);\n"
                + "    items = document.querySelectorAll(':is(ul, ol)');"
                + "    alert(items.length);\n"
                + "    alert(items[0]);\n"
                + "    alert(items[1]);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "ul - item 1", "ol - item 1"})
    public void isAttribute() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li name='i0'>ul - item 0</li>\n"
                + "  <li name='i1'>ul - item 1</li>\n"
                + "</ul>\n"
                + "<ol>\n"
                + "  <li name='i7'>ol - item 0</li>\n"
                + "  <li name='i2'>ol - item 1</li>\n"
                + "</ol>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\":is([name='i1'], [name='i2'])\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].innerText);\n"
                + "    alert(items[1].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "ul - item 0 ul - item 1", "ul - item 1",
             "ol - item 0 ol - item 1", "ol - item 1"})
    public void isDuplicates() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li name='i0'>ul - item 0</li>\n"
                + "  <li name='i1'>ul - item 1</li>\n"
                + "</ul>\n"
                + "<ol name='i1'>\n"
                + "  <li name='i7'>ol - item 0</li>\n"
                + "  <li name='i2'>ol - item 1</li>\n"
                + "</ol>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\":is(ul, [name='i1'], [name='i2'])\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].innerText);\n"
                + "    alert(items[1].innerText);\n"
                + "    alert(items[2].innerText);\n"
                + "    alert(items[3].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void isDoubleColon() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head><title></title></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li>ul - item 0</li>\n"
                + "</ul>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll('::is(ul)');"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "[object HTMLUListElement]",
             "2", "[object HTMLUListElement]", "[object HTMLOListElement]"})
    public void whereElement() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li>ul - item 0</li>\n"
                + "  <li>ul - item 1</li>\n"
                + "</ul>\n"
                + "<ol>\n"
                + "  <li>ol - item 0</li>\n"
                + "  <li>ol - item 1</li>\n"
                + "</ol>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(':where(ul)');"
                + "    alert(items.length);\n"
                + "    alert(items[0]);\n"
                + "    items = document.querySelectorAll(':where(ul, ol)');"
                + "    alert(items.length);\n"
                + "    alert(items[0]);\n"
                + "    alert(items[1]);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "ul - item 1", "ol - item 1"})
    public void whereAttribute() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li name='i0'>ul - item 0</li>\n"
                + "  <li name='i1'>ul - item 1</li>\n"
                + "</ul>\n"
                + "<ol>\n"
                + "  <li name='i7'>ol - item 0</li>\n"
                + "  <li name='i2'>ol - item 1</li>\n"
                + "</ol>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\":where([name='i1'], [name='i2'])\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].innerText);\n"
                + "    alert(items[1].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"4", "ul - item 0 ul - item 1", "ul - item 1",
             "ol - item 0 ol - item 1", "ol - item 1"})
    public void whereDuplicates() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li name='i0'>ul - item 0</li>\n"
                + "  <li name='i1'>ul - item 1</li>\n"
                + "</ul>\n"
                + "<ol name='i1'>\n"
                + "  <li name='i7'>ol - item 0</li>\n"
                + "  <li name='i2'>ol - item 1</li>\n"
                + "</ol>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\":where(ul, [name='i1'], [name='i2'])\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].innerText);\n"
                + "    alert(items[1].innerText);\n"
                + "    alert(items[2].innerText);\n"
                + "    alert(items[3].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"

                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void whereDoubleColon() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head><title></title></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li>ul - item 0</li>\n"
                + "</ul>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll('::where(ul)');"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "SECTION / a0 a1"})
    public void hasDescandant() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <article class='featured'>a0</article>\n"
                + "  <article>a1</article>\n"
                + "</section>\n"
                + "<section>\n"
                + "  <article>a2</article>\r\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\"section:has(.featured)\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "SECTION / a0 a1"})
    public void hasDescandantDeep() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <div>\n"
                + "    <article class='featured'>a0</article>\n"
                + "    <article>a1</article>\n"
                + "  </div>\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\"section:has(.featured)\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "SECTION / a0 a1"})
    public void hasChild() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <article class='featured'>a0</article>\n"
                + "  <article>a1</article>\n"
                + "</section>\n"
                + "<section>\n"
                + "  <article>a2</article>\r\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\"section:has(> .featured)\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("0")
    public void hasChildDeep() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <div>\n"
                + "    <article class='featured'>a0</article>\n"
                + "    <article>a1</article>\n"
                + "  </div>\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\"section:has(> .featured)\");"
                + "    alert(items.length);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "H1 / h1 1"})
    public void hasNextSiblingCombinator() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <article>\n"
                + "    <h1>h1 0</h1>\n"
                + "    <p>p0</p>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <h1>h1 1</h1>\n"
                + "    <h2>h2 0</h2>\n"
                + "    <p>p1</p>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <h1>h1 2</h1>\n"
                + "    <p>p2</p>\n"
                + "    <h2>h2 1</h2>\n"
                + "  </article>\n"
                + "</section>\n"
                + "<section>\n"
                + "  <article>a2</article>\r\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\"h1:has(+ h2)\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "H1 / h1 1", "H1 / h1 2"})
    public void hasSubsequentSiblingCombinator() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <article>\n"
                + "    <h1>h1 0</h1>\n"
                + "    <p>p0</p>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <h1>h1 1</h1>\n"
                + "    <h2>h2 0</h2>\n"
                + "    <p>p1</p>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <h1>h1 2</h1>\n"
                + "    <p>p2</p>\n"
                + "    <h2>h2 1</h2>\n"
                + "  </article>\n"
                + "</section>\n"
                + "<section>\n"
                + "  <article>a2</article>\r\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\"h1:has(~ h2)\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "    alert(items[1].tagName + ' / ' + items[1].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"

                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "H1 / h1 1", "H2 / h2 0",
             "2", "H1 / h1 1", "H2 / h2 0"})
    public void hasIs() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <article>\n"
                + "    <h1>h1 0</h1>\n"
                + "    <p>p0</p>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <h1>h1 1</h1>\n"
                + "    <h2>h2 0</h2>\n"
                + "    <h3>h3 0</h3>\n"
                + "    <p>p1</p>\n"
                + "  </article>\n"
                + "</section>\n"
                + "<section>\n"
                + "  <article>a2</article>\r\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\":is(h1, h2, h3):has(+ :is(h2, h3, h4))\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "    alert(items[1].tagName + ' / ' + items[1].innerText);\n"

                + "    items = document.querySelectorAll(\":is(h1, h2, h3):has(+ h2, + h3, + h4)\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "    alert(items[1].tagName + ' / ' + items[1].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "ARTICLE / h1 0", "ARTICLE / p0"})
    public void hasOr() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <article>\n"
                + "    <h1>h1 0</h1>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <p>p0</p>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <div>div0</div>\n"
                + "  </article>\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\"article:has(h1, p)\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "    alert(items[1].tagName + ' / ' + items[1].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"1", "ARTICLE / h1 1 p1"})
    public void hasAnd() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<section>\n"
                + "  <article>\n"
                + "    <h1>h1 0</h1>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <p>p0</p>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <h1>h1 1</h1>\n"
                + "    <p>p1</p>\n"
                + "  </article>\n"
                + "  <article>\n"
                + "    <div>div0</div>\n"
                + "  </article>\n"
                + "</section>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll(\"article:has(h1):has(p)\");"
                + "    alert(items.length);\n"
                + "    alert(items[0].tagName + ' / ' + items[0].innerText);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"

                + "</body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"0", "exception"})
    public void hasSizzleJQuery182InvalidContains() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<script>\n"
                + "  try {\n"
                + "    items = document.querySelectorAll(\"#form select:has(option:first-child)\");"
                + "    alert(items.length);\n"
                + "    items = document.querySelectorAll(\"#form select:has(option:first-child:contains('o'))\");"
                + "    alert(items.length);\n"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("exception")
    public void hasDoubleColon() {
        final String html = "<!DOCTYPE html>"
                + "<html>\n"
                + "<head><title></title></head>\n"
                + "<body>\n"
                + "<ul>\n"
                + "  <li>ul - item 0</li>\n"
                + "</ul>\n"
                + "<script>\n"
                + "  try {\n"
                + "    let items = document.querySelectorAll('::has(ul)');"
                + "  } catch (e) { alert('exception'); }\n"
                + "</script>\n"
                + "</body></html>";

        checkHtmlAlert(html);
    }
}
