
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.*;


/**
 * The importNode method imports a node from another document to this document.
 * The returned node has no parent; (parentNode is null). The source node is not
 * altered or removed from the original document but a new copy of the source node
 * is created.
 * <p>
 * Using the method importNode with deep=true, import the first address element node of this
 * Document.  Verify if the node has been imported correctly by checking the length of the
 * this elements childNode list before and after the import.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode12Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection childList;
        Node imported;
        Node addressElem;
        NodeList addressElemChildren;
        NodeList importedChildren;
        int addressElemLen;
        int importedLen;
        doc = sampleXmlFile("staffNS.xml");
        childList = doc.getElementsByTagNameNS("*", "address");
        addressElem = childList.item(0);
        imported = doc.importNode(addressElem, true);
        addressElemChildren = addressElem.getChildNodes();
        importedChildren = imported.getChildNodes();
        addressElemLen = addressElemChildren.getLength();
        importedLen = importedChildren.getLength();
        assertEquals("documentimportnode12", importedLen, addressElemLen);
    }
}

