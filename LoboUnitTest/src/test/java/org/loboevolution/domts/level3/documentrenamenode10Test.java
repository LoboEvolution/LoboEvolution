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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertTrue;


/**
 * The method renameNode renames an existing node and raises a  NAMESPACE_ERR
 * if the qualifiedName has a prefix and the namespaceURI is null but a
 * NOT_SUPPORTED_ERR should be raised since the type of the specified node is
 * neither ELEMENT_NODE nor ATTRIBUTE_NODE.
 * <p>
 * Invoke the renameNode method on a new document node to rename a node to nodes
 * with malformed qualifiedNames.
 * Check if a NOT_SUPPORTED_ERR gets thrown instead of a NAMESPACE_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode10Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        String textEntry = "hello";
        Text textNode;
        Node renamedNode;
        String qualifiedName;
        DocumentType nullDocType = null;

        java.util.List qualifiedNames = new java.util.ArrayList();
        qualifiedNames.add("_:");
        qualifiedNames.add(":0");
        qualifiedNames.add(":");
        qualifiedNames.add("a0:0");
        qualifiedNames.add("_:0;");
        qualifiedNames.add("a:::::c");

        doc = sampleXmlFile("hc_staff.xml");
        textNode = doc.createTextNode(textEntry);
        for (Object name : qualifiedNames) {
            qualifiedName = (String) name;

            {
                boolean success = false;
                try {
                    renamedNode = doc.renameNode(textNode, "http://www.w3.org/XML/1998/namespace", qualifiedName);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue("documentrenamenode10_NOT_SUPPORTED_ERR", success);
            }
        }
    }
}

