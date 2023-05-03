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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Using replaceChild on an Entity node attempt to replace its Text child with new Text,
 * Comment, ProcessingInstruction and CDATASection nodes and in each case verify if
 * a NO_MODIFICATION_ALLOWED_ERR is raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild38Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entitiesMap;
        Node ent;
        Text oldChild;
        EntityReference entRef;
        Text txt;
        Element elem;
        Comment comment;
        ProcessingInstruction pi;
        CDATASection cdata;
        Node replaced;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entitiesMap = docType.getEntities();
        ent = entitiesMap.getNamedItem("alpha");
        assertNotNull("alphaEntity", ent);
        oldChild = (Text) ent.getFirstChild();
        assertNotNull("alphaText", oldChild);
        cdata = doc.createCDATASection("CDATASection");

        {
            boolean success = false;
            try {
                replaced = ent.replaceChild(cdata, oldChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR1", success);
        }
        pi = doc.createProcessingInstruction("target", "data");

        {
            boolean success = false;
            try {
                replaced = ent.replaceChild(pi, oldChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR2", success);
        }
        comment = doc.createComment("Comment");

        {
            boolean success = false;
            try {
                replaced = ent.replaceChild(comment, oldChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR3", success);
        }
        txt = doc.createTextNode("Text");

        {
            boolean success = false;
            try {
                replaced = ent.replaceChild(txt, oldChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR4", success);
        }
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:p");

        {
            boolean success = false;
            try {
                replaced = ent.replaceChild(elem, oldChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR5", success);
        }
        entRef = doc.createEntityReference("delta");

        {
            boolean success = false;
            try {
                replaced = ent.replaceChild(entRef, oldChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR6", success);
        }
    }
}

