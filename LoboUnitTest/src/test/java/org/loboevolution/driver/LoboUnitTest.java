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

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.css.ComputedCSSStyleDeclaration;
import org.loboevolution.html.node.js.Window;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * <p>LoboUnitTest class.</p>
 */
public class LoboUnitTest extends LoboWebDriver {


    /** The Constant logger. */
     private final Logger logger = Logger.getLogger(LoboWebDriver.class.getName());

    /**
     * <p>checkSelectorsTest.</p>
     *
     * @param html a {@link java.lang.String} object.
     * @param result1 a {@link java.lang.String} object.
     * @param result2 a {@link java.lang.String} object.
     */
    public void checkSelectorsTest(final String html, final String result1, final String result2) {
        final HTMLDocumentImpl doc = loadHtml(html);
        final HTMLElementImpl div = (HTMLElementImpl) doc.getElementById("myId");
        final HTMLElementImpl div2 = (HTMLElementImpl) doc.getElementById("myId2");
        final ComputedCSSStyleDeclaration computedStyle = div.getComputedStyle();
        final ComputedCSSStyleDeclaration computedStyle2 = div2.getComputedStyle();
        assertEquals(result1, computedStyle.getColor());
        assertEquals(result2, computedStyle2.getColor());
    }

    /**
     * <p>sampleHtmlFile.</p>.
     * @return a {@link org.loboevolution.html.node.Document} object.
     */
    public static Document sampleHtmlFile() {
        final String url = LoboWebDriver.class.getResource("/org/lobo/html/htmlsample.html").toString();
        final DocumentImpl doc = loadHtml(LoboUnitTest.class.getResourceAsStream("/org/lobo/html/htmlsample.html"), url);
        doc.setTest(true);
        return doc;
    }

    /**
     * <p>sampleHtmlFile.</p>.
     * @param fileName a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.Document} object.
     */
    public static Document sampleXmlFile(final String fileName) {
        final String url = Objects.requireNonNull(LoboWebDriver.class.getResource("/org/lobo/xml/" + fileName)).toString();
        final DocumentImpl doc = LoboWebDriver.loadHtml(LoboUnitTest.class.getResourceAsStream("/org/lobo/xml/" + fileName), url);
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
            final HTMLDocumentImpl doc = loadHtml(html);
            window = doc.getDefaultView();
            alerts = messages != null ? Arrays.asList(messages) : null;
            assertEquals(alerts, window.getMsg());
        } catch (final AssertionError e) {
            throw new AssertionError("Result expected: " +  alerts + " Result: " + window.getMsg());
        } catch (final Exception ex) {
            ex.printStackTrace();
            logger.severe(ex.getMessage());
        }
    }

    /**
     * <p>assertURIEquals.</p>
     * @param uriEquals a {@link org.loboevolution.driver.LoboUnitTest.URIEquals} object.
     */
    public void assertURIEquals(final URIEquals uriEquals) {

        assertNotNull(uriEquals.getAssertID(), uriEquals.getActual());

        String uri = uriEquals.getActual();
        final String actual = uriEquals.getActual();
        final String fragment = uriEquals.getFragment();
        final String assertID = uriEquals.getAssertID();
        final String query = uriEquals.getQuery();
        final String scheme = uriEquals.getScheme();
        final String path = uriEquals.getPath();
        final String host = uriEquals.getHost();
        final String file = uriEquals.getFile();
        final String name = uriEquals.getName();
        final Boolean isAbsolute = uriEquals.getAbsolute();

        final int lastPound = actual.lastIndexOf('#');
        String actualFragment = "";
        if (lastPound != -1) {
            uri = actual.substring(0, lastPound);
            actualFragment = actual.substring(lastPound + 1);
        }
        if (Strings.isNotBlank(fragment)) {
            assertEquals(assertID, fragment, actualFragment);

        }
        final int lastQuestion = uri.lastIndexOf('?');
        String actualQuery = "";
        if (lastQuestion != -1) {
            uri = actual.substring(0, lastQuestion);
            actualQuery = actual.substring(lastQuestion + 1);
        }
        if (query != null) {
            assertEquals(assertID, query, actualQuery);

        }
        final int firstColon = uri.indexOf(':');
        final int firstSlash = uri.indexOf('/');
        String actualPath = uri;
        String actualScheme = "";
        if (firstColon != -1 && firstColon < firstSlash) {
            actualScheme = uri.substring(0, firstColon);
            actualPath = uri.substring(firstColon + 1);
        }

        if (Strings.isNotBlank(scheme)) {
            assertEquals(assertID, scheme, actualScheme);
        }

        if (Strings.isNotBlank(path)) {
            assertEquals(assertID, path, actualPath);
        }

        if (Strings.isNotBlank(host)) {
            String actualHost = "";
            if (actualPath.startsWith("//")) {
                final int termSlash = actualPath.indexOf("/", 2);
                actualHost = actualPath.substring(0, termSlash);
            }
            assertEquals(assertID, host, actualHost);
        }

        String actualFile = actualPath;
        if (file != null || name != null) {
            final int finalSlash = actualPath.lastIndexOf("/");
            if (finalSlash != -1) {
                actualFile = actualPath.substring(finalSlash + 1);
            }
            if (file != null) {
                assertEquals(assertID, file, actualFile);
            }
        }

        if (name != null) {
            String actualName = actualFile;
            final int finalPeriod = actualFile.lastIndexOf(".");
            if (finalPeriod != -1) {
                actualName = actualFile.substring(0, finalPeriod);
            }
            assertEquals(assertID, name, actualName);
        }

        if (isAbsolute != null) {
            assertEquals(
                    assertID,
                    isAbsolute,
                    actualPath.startsWith("/") || actualPath.startsWith("file:/"));
        }
    }

    @Getter
    @AllArgsConstructor
    public class URIEquals {
        /**
         * <p>assertID.</p>
         */
        private String assertID;
        /**
         * <p>scheme.</p>
         */
        private String scheme;
        /**
         * <p>path.</p>
         */
        private String path;
        /**
         * <p>host.</p>
         */
        private String host;
        /**
         * <p>file.</p>
         */
        private String file;
        /**
         * <p>name.</p>
         */
        private String name;
        /**
         * <p>query.</p>
         */
        private String query;
        /**
         * <p>fragment.</p>
         */
        private String fragment;
        /**
         * <p>isAbsolute.</p>
         */
        private Boolean absolute;
        /**
         * <p>actual.</p>
         */
        private String actual;
    }
}
