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
package org.loboevolution.css;

import org.htmlunit.cssparser.parser.CSSErrorHandler;
import org.htmlunit.cssparser.parser.CSSException;
import org.htmlunit.cssparser.parser.CSSOMParser;
import org.htmlunit.cssparser.parser.CSSParseException;
import org.htmlunit.cssparser.parser.javacc.CSS3Parser;
import org.htmlunit.cssparser.parser.selector.Selector;
import org.htmlunit.cssparser.parser.selector.SelectorList;
import org.htmlunit.cssparser.parser.selector.SelectorListImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.StyleSheetAggregator;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CSSStyleSheet}.
 */
@ExtendWith(AlertsExtension.class)
public class CSSStyleSheet2Test extends LoboUnitTest {

    @Test
    public void selectsMiscSelectors() {
        final String html = "<html><head><title>test</title>\n"
                + "</head><body><style></style>\n"
                + "<form name='f1' action='foo' class='yui-log'>\n"
                + "<div><div><input name='i1' id='m1'></div></div>\n"
                + "<input name='i2' class='yui-log'>\n"
                + "<button name='b1' class='yui-log'>\n"
                + "<button name='b2'>\n"
                + "</form>\n"
                + "</body></html>";

        final HTMLDocument document = loadHtml(html);
        HTMLElementImpl form = (HTMLElementImpl) document.getElementById("f1");
        HTMLElementImpl body = (HTMLElementImpl) document.getBody();
        HTMLElementImpl input1 = (HTMLElementImpl) document.getElementById("i1");
        HTMLElementImpl input2 = (HTMLElementImpl) document.getElementById("i2");
        HTMLElementImpl button1 = (HTMLElementImpl) document.getElementById("b1");
        HTMLElementImpl button2 = (HTMLElementImpl) document.getElementById("b2");
        Selector selector = parseSelector("*.yui-log input");

        assertFalse(StyleSheetAggregator.selects(selector, body, null));
        assertFalse(StyleSheetAggregator.selects(selector, form, null));
        assertTrue(StyleSheetAggregator.selects(selector, input1, null));
        assertTrue(StyleSheetAggregator.selects(selector, input2, null));
        assertFalse(StyleSheetAggregator.selects(selector, button1, null));
        assertFalse(StyleSheetAggregator.selects(selector, button2, null));

        selector = parseSelector("#m1");
        assertTrue(StyleSheetAggregator.selects(selector, input1, null));
        assertFalse(StyleSheetAggregator.selects(selector, input2, null));
    }

    @Test
    public void selectsAnyNodeSelector() {
        testSelects("*", true, true, true);
    }

    @Test
    public void selectsChildSelector() {
        testSelects("body > div", false, false, true);
    }

    @Test
    public void selectsDescendantSelector() {
        testSelects("body span", false, false, true);
    }

    @Test
    public void selectsElementSelector() {
        testSelects("div", false, false, true);
    }

    @Test
    public void selectsDirectAdjacentSelector() {
        testSelects("span + span", false, false, true);
    }

    @Test
    public void selectsConditionalSelectorIdCondition() {
        testSelects("span#s", false, false, true);
        testSelects("#s", false, false, true);
        testSelects("span[id=s]", false, false, true);
    }

    @Test
    public void selectsIdConditionWithSpecialChars() {
        final String html =
                "<html><body><style></style>\n"
                        + "<div id='d:e'></div>\n"
                        + "<div id='d-e'></div>\n"
                        + "</body></html>";
        final HTMLDocument document = loadHtml(html);

        Selector selector = parseSelectors("#d\\:e").get(0);
        assertTrue(StyleSheetAggregator.selects(selector, document.getElementById("d:e"), null));

        selector = parseSelectors("#d-e").get(0);
        assertTrue(StyleSheetAggregator.selects(selector, document.getElementById("d:e"), null));
    }

    @Test
    public void selectsConditionalSelectorClassCondition() {
        testSelects("div.bar", false, false, true);
        testSelects(".bar", false, false, true);
        testSelects("div[class~=bar]", false, false, true);
    }

    @Test
    public void selectsPseudoClassRoot() {
        testSelects(":root", false, false, false);
    }

    @Test
    public void selectsPseudoClassLang() {
        testSelects(":lang(en)", true, false, true);
        testSelects(":lang(de)", false, false, false);
    }

    @Test
    public void selectsPseudoClassNgation() {
        testSelects(":not(div)", true, false, true);
    }

    private void testSelects(final String css, final boolean selectBody, final boolean selectDivD,
                             final boolean selectSpanS) {
        final String html =
                "<html>\n"
                        + "  <body id='b'>\n"
                        + "    <style></style>\n"
                        + "    <div id='d' class='foo bar' lang='en-GB'>\n"
                        + "      <span>x</span>\n"
                        + "      <span id='s'>a</span>b\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        final HTMLDocument document = loadHtml(html);

        final Selector selector = parseSelectors(css).get(0);
        assertEquals(selectBody, StyleSheetAggregator.selects(selector, document.getElementById("b"), null));
        assertEquals(selectDivD, StyleSheetAggregator.selects(selector, document.getElementById("d"), null));
        assertEquals(selectSpanS, StyleSheetAggregator.selects(selector, document.getElementById("s"), null));
    }

    private static Selector parseSelector(final String rule) {
        return parseSelectors(rule).get(0);
    }

    private static SelectorList parseSelectors(final String source) {
        SelectorList selectors = null;
        final CSSErrorHandler errorHandler = new CSSErrorHandler() {

            @Override
            public void warning(final CSSParseException exception) throws CSSException {
                throw exception;
            }

            @Override
            public void fatalError(final CSSParseException exception) throws CSSException {
                throw exception;
            }

            @Override
            public void error(final CSSParseException exception) throws CSSException {
                throw exception;
            }
        };
        
        final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
        parser.setErrorHandler(errorHandler);
        try {
            selectors = parser.parseSelectors(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // in case of error parseSelectors returns null
        if (selectors == null ) {
            selectors = new SelectorListImpl();
        }
        return selectors;
    }
}
