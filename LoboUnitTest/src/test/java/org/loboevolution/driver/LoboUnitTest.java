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

package org.loboevolution.driver;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.css.ComputedCSSStyleDeclaration;
import org.loboevolution.html.node.js.Window;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

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
        final String url = LoboWebDriver.class.getResource("/org/lobo/html/htmlsample.html").toString();
        DocumentImpl doc = loadHtml(LoboUnitTest.class.getResourceAsStream("/org/lobo/html/htmlsample.html"), url);
        doc.setTest(true);
        return doc;
    }

    /**
     * <p>sampleHtmlFile.</p>.
     */
    public static Document sampleXmlFile(String fileName) {
        final String url = LoboWebDriver.class.getResource("/org/lobo/xml/" + fileName).toString();
        DocumentImpl doc = loadHtml(LoboUnitTest.class.getResourceAsStream("/org/lobo/xml/" + fileName), url);
        doc.setTest(true);
        doc.setXml(true);
        return doc;
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
            alerts = messages != null ? Arrays.asList(messages) : null;
            assertEquals(alerts, window.getMsg());
        } catch (AssertionError e) {
            throw new AssertionError("Result expected: " +  alerts + " Result: " + window.getMsg());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.severe(ex.getMessage());
        }
    }

    public void assertURIEquals(
            String assertID, String scheme, String path, String host,
            String file, String name, String query, String fragment, Boolean isAbsolute, String actual) {

        assertNotNull(assertID, actual);

        String uri = actual;

        int lastPound = actual.lastIndexOf("#");
        String actualFragment = "";
        if (lastPound != -1) {
            uri = actual.substring(0, lastPound);
            actualFragment = actual.substring(lastPound + 1);
        }
        if (fragment != null) {
            assertEquals(assertID, fragment, actualFragment);

        }
        int lastQuestion = uri.lastIndexOf("?");
        String actualQuery = "";
        if (lastQuestion != -1) {
            uri = actual.substring(0, lastQuestion);
            actualQuery = actual.substring(lastQuestion + 1);
        }
        if (query != null) {
            assertEquals(assertID, query, actualQuery);

        }
        int firstColon = uri.indexOf(":");
        int firstSlash = uri.indexOf("/");
        String actualPath = uri;
        String actualScheme = "";
        if (firstColon != -1 && firstColon < firstSlash) {
            actualScheme = uri.substring(0, firstColon);
            actualPath = uri.substring(firstColon + 1);
        }

        if (scheme != null) {
            assertEquals(assertID, scheme, actualScheme);
        }

        if (path != null) {
            assertEquals(assertID, path, actualPath);
        }

        if (host != null) {
            String actualHost = "";
            if (actualPath.startsWith("//")) {
                int termSlash = actualPath.indexOf("/", 2);
                actualHost = actualPath.substring(0, termSlash);
            }
            assertEquals(assertID, host, actualHost);
        }

        String actualFile = actualPath;
        if (file != null || name != null) {
            int finalSlash = actualPath.lastIndexOf("/");
            if (finalSlash != -1) {
                actualFile = actualPath.substring(finalSlash + 1);
            }
            if (file != null) {
                assertEquals(assertID, file, actualFile);
            }
        }

        if (name != null) {
            String actualName = actualFile;
            int finalPeriod = actualFile.lastIndexOf(".");
            if (finalPeriod != -1) {
                actualName = actualFile.substring(0, finalPeriod);
            }
            assertEquals(assertID, name, actualName);
        }

        if (isAbsolute != null) {
            assertEquals(
                    assertID,
                    isAbsolute.booleanValue(),
                    actualPath.startsWith("/") || actualPath.startsWith("file:/"));
        }
    }
}
