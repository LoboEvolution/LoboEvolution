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

package org.loboevolution.junit;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;

public class DOMImplementationTest extends LoboUnitTest {

    private static DOMImplementationImpl domImpl;

    @BeforeClass
    public static void setUpBeforeClass() {
        UserAgentContext context = new UserAgentContext(new LocalHtmlRendererConfig(), true);
        context.setUserAgentEnabled(false);
        domImpl = new DOMImplementationImpl(context);
    }

    @Test
    public void testCreateDocument2() {
        DocumentType doctype = domImpl.createDocumentType("html", null, null);
        assertNull(doctype.getNextSibling());
        assertNull(doctype.getPreviousSibling());
        assertNull(doctype.getParentNode());
        assertNull(doctype.getOwnerDocument());
        Document document = domImpl.createDocument(null, null, doctype);
        assertNull(doctype.getNextSibling());
        assertNull(doctype.getPreviousSibling());
		assertSame(document, doctype.getOwnerDocument());
        try {
            domImpl.createDocument(null, null, doctype);
            fail("Must throw an exception");
        } catch (DOMException e) {
            assertEquals(DOMException.WRONG_DOCUMENT_ERR, e.getCode());
        }
    }

    @Test
    public void testCreateDocument3() {
        DocumentType doctype = domImpl.createDocumentType("html", null, null);
        assertNull(doctype.getNextSibling());
        assertNull(doctype.getPreviousSibling());
        Document document = domImpl.createDocument(null, "html", doctype);
        assertEquals("CSS1Compat", document.getCompatMode());
        Element docelm = document.getDocumentElement();
        assertNotNull(docelm);
		assertSame(docelm, doctype.getNextSibling());
        assertNull(doctype.getPreviousSibling());
		assertSame(doctype, docelm.getPreviousSibling());
        assertNull(docelm.getNextSibling());
		assertSame(document, docelm.getParentNode());
		assertSame(document, docelm.getOwnerDocument());
		assertSame(document, doctype.getOwnerDocument());
    }

    @Test
    public void testCreateDocumentType() {
        DocumentType doctype = domImpl.createDocumentType("html", null, null);
        assertEquals("<!DOCTYPE html>", doctype.toString());
        //
        doctype = domImpl.createDocumentType("html", "-//W3C//DTD XHTML 1.0 Strict//EN", null);
        assertEquals("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\">", doctype.toString());
        //
        doctype = domImpl.createDocumentType("html", null, "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd");
        assertEquals("<!DOCTYPE html SYSTEM \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">",
                doctype.toString());
        //
        doctype = domImpl.createDocumentType("html", "-//W3C//DTD XHTML 1.0 Strict//EN",
                "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd");
        assertEquals(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">",
                doctype.toString());
    }

}
