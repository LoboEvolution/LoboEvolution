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


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;


/**
 * The method replaceChild replaces the child node oldChild with newChild in the list of
 * children, and returns the oldChild node.
 * Using replaceChild on the documentElement of a newly created Document node, attempt to replace an
 * element child of this documentElement node with a child that was imported from another document.
 * Verify the nodeName of the replaced element node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild14Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        Element docElem;
        Element elem;
        Element elem2;
        Node imported;
        Element replaced;
        DOMImplementation domImpl;
        String nodeName;
        Node appendedChild;
        DocumentType nullDocType = null;

        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/DOM/Test", "dom3:doc1elem");
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/test", "dom3:doc", nullDocType);
        elem2 = newDoc.createElementNS("http://www.w3.org/DOM/Test", "dom3:doc2elem");
        imported = newDoc.importNode(elem, true);
        docElem = newDoc.getDocumentElement();
        appendedChild = docElem.appendChild(imported);
        appendedChild = docElem.appendChild(elem2);
        replaced = (Element) docElem.replaceChild(imported, elem2);
        nodeName = replaced.getNodeName();
        assertEquals("nodereplacechild14", "dom3:doc2elem", nodeName);
    }
}

