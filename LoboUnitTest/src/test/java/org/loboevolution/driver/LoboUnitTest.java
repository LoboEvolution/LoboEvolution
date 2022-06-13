/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

package org.loboevolution.driver;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.css.ComputedCSSStyleDeclaration;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.junit.HTMLDocumentTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * <p>LoboUnitTest class.</p>
 */
public class LoboUnitTest extends LoboWebDriver {

    public final String URL_SECOND = "http://www.example.com/";

    /**
     * <p>checkSelectorsTest.</p>
     *
     * @param html a {@link java.lang.String} object.
     * @param result1 a {@link java.lang.String} object.
     * @param result2 a {@link java.lang.String} object.
     */
    public void checkSelectorsTest(final String html, final String result1, final String result2) {
        HTMLDocumentImpl doc = loadHtml(html);
        HTMLElementImpl div = (HTMLElementImpl)doc.getElementById("myId");
        HTMLElementImpl div2 = (HTMLElementImpl)doc.getElementById("myId2");
        ComputedCSSStyleDeclaration computedStyle = div.getComputedStyle();
        ComputedCSSStyleDeclaration computedStyle2 = div2.getComputedStyle();
        assertEquals(result1, computedStyle.getColor());
        assertEquals(result2, computedStyle2.getColor());
    }

    /**
     * <p>sampleHtmlFile.</p>.
     */
    public static Document sampleHtmlFile() {
        return loadHtml(HTMLDocumentTest.class.getResourceAsStream("/org/lobo/html/htmlsample.html"));
    }

    /**
     * <p>checkHtmlAlert.</p>
     *
     * @param html a {@link java.lang.String} object.
     * @param messages an array of {@link java.lang.String} objects.
     */
    public void checkHtmlAlert(final String html, final String[] messages) {
    	Window window = null;
        List<String> alerts = null;

        try {
            HTMLDocumentImpl doc = loadHtml(html);
            window = doc.getDefaultView();
            alerts = Arrays.asList(messages);
            assertEquals(alerts, window.getMsg());
        } catch (AssertionError e) {
            throw new AssertionError("Result expected: " +  alerts + " Result: " + window.getMsg());
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }
}
