
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Using the method importNode with deep=true, import the fourth employee element node of this
 * Document.  Verify if the node has been imported correctly by checking
 * if the default attribute present on this node has not been imported
 * and an explicit attribute has been imported.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=402">http://www.w3.org/Bugs/Public/show_bug.cgi?id=402</a>
 */
public class documentimportnode14Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        DocumentType nullDocType = null;

        HTMLCollection childList;
        Node imported;
        Node employeeElem;
        Attr attrNode;
        String attrValue;
        String nullNS = null;

        doc = sampleXmlFile("staffNS.xml");
        childList = doc.getElementsByTagNameNS("*", "employee");
        employeeElem = childList.item(3);
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(nullNS, "staff", nullDocType);
        imported = newDoc.importNode(employeeElem, true);
        attrNode = ((Element) imported).getAttributeNodeNS(nullNS, "defaultAttr");
        assertNull("defaultAttrNotImported", attrNode);
        attrValue = ((Element) imported).getAttributeNS("http://www.w3.org/2000/xmlns/", "emp");
        assertEquals("explicitAttrImported", "http://www.nist.gov", attrValue);
    }
}

