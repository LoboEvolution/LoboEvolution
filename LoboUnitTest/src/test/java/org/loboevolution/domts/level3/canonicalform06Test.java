/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.domts.level3;


import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.DOMLocator;
import org.loboevolution.html.dom.nodeimpl.DOMErrorMonitor;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.http.UserAgentContext;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Create a document with an XML 1.1 valid but XML 1.0 invalid element and
 * normalize document with canonical-form set to true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-canonical-form</a>
 */
public class canonicalform06Test extends LoboUnitTest {

    @Test
    public void runTest() {
        DOMImplementation domImpl;
        String nullString = null;

        DocumentType nullDoctype = null;

        Document doc;
        Element elem;
        DOMConfiguration domConfig;
        DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        List<DOMError> errors;

        DOMError error;
        int severity;
        String type;
        DOMLocator locator;
        Node relatedNode;
        boolean canSet;
        domImpl = new DOMImplementationImpl(new UserAgentContext(true));
        doc = domImpl.createDocument(nullString, nullString, nullDoctype);

        {
            boolean success = false;
            try {
                elem = doc.createElementNS("http://www.example.org/domts/wellformed01", "LegalNameࢎ");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
            }
            assertTrue("xml10InvalidName", success);
        }

        try {
            doc.setXmlVersion("1.1");

        } catch (DOMException ex) {
            if (ex.getCode() == 9) {
                return;
            }
            throw ex;
        }
        elem = doc.createElementNS("http://www.example.org/domts/wellformed01", "LegalNameࢎ");
        doc.appendChild(elem);
        doc.setXmlVersion("1.0");
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter("canonical-form", Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter("canonical-form", Boolean.TRUE);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            doc.normalizeDocument();
            errors = errorMonitor.getErrors();
            for (Object o : errors) {
                error = (DOMError) o;
                severity = error.getSeverity();
                assertEquals("severity", 2, severity);
                type = error.getType();
                assertEquals("type", "wf-invalid-character-in-node-name", type);
                locator = error.getLocation();
                relatedNode = locator.getRelatedNode();
                assertSame("relatedNode", elem, relatedNode);
            }
            assertSame("oneError", 1, errors.size());
        }
    }
}