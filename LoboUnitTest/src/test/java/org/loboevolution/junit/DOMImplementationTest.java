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

package org.loboevolution.junit;

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.domimpl.DOMImplementationImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Element;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;

public class DOMImplementationTest extends LoboUnitTest {

    private static DOMImplementationImpl domImpl;

    @BeforeClass
    public static void setUpBeforeClass() {
        UserAgentContext context = new UserAgentContext();
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
        doctype.setNodeValue("foo"); // No effect
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
        //
        try {
            domImpl.createDocumentType("html><html><injection/", null, null);
            fail("Must throw exception.");
        } catch (DOMException e) {
            assertEquals(DOMException.INVALID_CHARACTER_ERR, e.getCode());
        }
        //
        doctype = domImpl.createDocumentType("html", "\"><injection foo=\"", null);
        assertEquals("<!DOCTYPE html PUBLIC \"&quot;&gt;&lt;injection foo=&quot;\">", doctype.toString());
        //
        doctype = domImpl.createDocumentType("html", null, "\"><injection foo=\"");
        assertEquals("<!DOCTYPE html SYSTEM \"&quot;&gt;&lt;injection foo=&quot;\">", doctype.toString());
    }

}
