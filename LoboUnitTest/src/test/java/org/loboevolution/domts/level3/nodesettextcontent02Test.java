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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertEquals;

/**
 * The method setTextContent has no effect when the node is defined to be null.
 * <p>
 * Using setTextContent on a new Document node, attempt to set the textContent of this
 * new Document node to textContent.  Check if it was not set by checking the nodeName
 * attribute of a new Element of this Document node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent</a>
 */
public class nodesettextcontent02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        Document newDoc;
        String nodeName;
        Element elemChild;
        Element newElem;
        HTMLCollection elemList;
        DocumentType nullDocType = null;

        Node appendedChild;
        Element documentElem;
        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "dom3:elem", nullDocType);
        newElem = newDoc.createElementNS("http://www.w3.org/DOM/Test", "dom3:childElem");
        documentElem = newDoc.getDocumentElement();
        appendedChild = documentElem.appendChild(newElem);
        newDoc.setTextContent("textContent");
        elemList = newDoc.getElementsByTagNameNS("*", "childElem");
        elemChild = (Element) elemList.item(0);
        nodeName = elemChild.getNodeName();
        assertEquals("nodesettextcontent02", "dom3:childElem", nodeName);
    }
}

