
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The "getOwnerDocument()" method returns the Document
 * object associated with this node.
 * <p>
 * Retrieve the second employee and examine Document
 * returned by the "getOwnerDocument()" method.   Invoke
 * the "getDocumentElement()" on the Document which will
 * return an Element that is equal to "staff".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#node-ownerDoc">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#node-ownerDoc</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=251">http://www.w3.org/Bugs/Public/show_bug.cgi?id=251</a>
 */
public class nodegetownerdocumentTest extends LoboUnitTest {
    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elementList;
        Node docNode;
        Document ownerDocument;
        Element docElement;
        String elementName;
        doc = sampleXmlFile("staff.xml");
        elementList = doc.getElementsByTagName("employee");
        docNode = elementList.item(1);
        ownerDocument = docNode.getOwnerDocument();
        docElement = ownerDocument.getDocumentElement();
        elementName = docElement.getNodeName();
        assertEquals("nodeGetOwnerDocumentAssert1", "STAFF", elementName);

    }
}

