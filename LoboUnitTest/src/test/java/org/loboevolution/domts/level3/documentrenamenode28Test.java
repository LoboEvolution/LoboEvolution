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
 * Invoke the renameNode method to attempt to rename a Entity and Notation nodes of this Document.
 * Check if a NOT_SUPPORTED_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode28Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entityNodeMap;
        NamedNodeMap notationNodeMap;
        Node entity;
        Notation notation;
        Node renamedEntityNode;
        Node renamedNotationNode;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entityNodeMap = docType.getEntities();
        notationNodeMap = docType.getNotations();
        entity = entityNodeMap.getNamedItem("alpha");
        notation = (Notation) notationNodeMap.getNamedItem("notation1");

        boolean success = false;
        try {
            renamedEntityNode = doc.renameNode(entity, "http://www.w3.org/DOM/Test", "beta");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue("documentrenamenode28_ENTITY_NOT_SUPPORTED_ERR", success);


        success = false;
        try {
            renamedNotationNode = doc.renameNode(notation, "http://www.w3.org/DOM/Test", "notation2");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue("documentrenamenode28_NOTATION_NOT_SUPPORTED_ERR", success);
    }
}