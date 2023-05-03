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

import static org.junit.Assert.assertTrue;


/**
 * The method renameNode renames an existing node and raises a NAMESPACE_ERR
 * if the qualifiedName is malformed per the Namespaces in XML specification.
 * <p>
 * Invoke the renameNode method on a new document node to rename a node to nodes
 * with malformed qualifiedNames.
 * Check if a NAMESPACE_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode19Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        Element element;
        Node renamedNode;
        String qualifiedName;
        DocumentType nullDocType = null;

        java.util.List qualifiedNames = new java.util.ArrayList();
        qualifiedNames.add("a_:");
        qualifiedNames.add("_:");
        qualifiedNames.add(":");
        qualifiedNames.add("::0;");
        qualifiedNames.add("a:-:c");

        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "newD", nullDocType);
        element = doc.createElementNS("http://www.w3.org/DOM/Test", "test");
        for (int indexN1006C = 0; indexN1006C < qualifiedNames.size(); indexN1006C++) {
            qualifiedName = (String) qualifiedNames.get(indexN1006C);

            {
                boolean success = false;
                try {
                    renamedNode = doc.renameNode(element, "http://www.w3.org/2000/XMLNS", qualifiedName);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.NAMESPACE_ERR);
                }
                assertTrue("documentrenamenode19_NAMESPACE_ERR", success);
            }
        }
    }
}

