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
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;

/**
 * The method replaceChild replaces the child node oldChild with newChild in the list of
 * children, and returns the oldChild node.
 * Using replaceChild on this DocumentType node attempt to replace an Entity node with
 * a notation node of retieved from the DTD of another document and verify if a
 * NO_MODIFICATION_ALLOWED_ERR is thrown since DocumentType node is read-only.
 * Also try replacing the docType with an entity node and see if the same exception gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild21Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entitiesMap;
        Node ent;
        Document doc1;
        DocumentType docType1;
        NamedNodeMap notationsMap;
        Notation notation;
        Node replacedChild;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entitiesMap = docType.getEntities();
        ent = entitiesMap.getNamedItem("alpha");
        doc1 = sampleXmlFile("hc_staff.xml");
        docType1 = doc1.getDoctype();
        notationsMap = docType1.getNotations();
        notation = (Notation) notationsMap.getNamedItem("notation1");

        {
            boolean success = false;
            try {
                replacedChild = docType.replaceChild(notation, ent);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("NO_MODIFICATION_ALLOWED_ERR1_nodereplacechild21", success);
        }

        {
            boolean success = false;
            try {
                replacedChild = docType.replaceChild(ent, docType);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("NO_MODIFICATION_ALLOWED_ERR2_nodereplacechild21", success);
        }
    }
}

