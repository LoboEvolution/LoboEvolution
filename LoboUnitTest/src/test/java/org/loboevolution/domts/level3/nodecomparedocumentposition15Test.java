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
 * Using compareDocumentPosition check if the Element node precedes and contains its Attr child, and that the Attr child
 * is contained and follows the Element node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class nodecomparedocumentposition15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        Element docElem;
        Attr attr;
        Node docFragChild;
        int attrPosition;
        int docFragChildPosition;
        Node appendedChild;
        Node attrNode;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        docFrag = doc.createDocumentFragment();
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        attrNode = docElem.setAttributeNodeNS(attr);
        appendedChild = docFrag.appendChild(docElem);
        docFragChild = docFrag.getFirstChild();
        docFragChildPosition = docFragChild.compareDocumentPosition(attr);
        assertEquals("nodecomparedocumentpositionIsContainedFollows15", 20, docFragChildPosition);
        attrPosition = attr.compareDocumentPosition(docFragChild);
        assertEquals("nodecomparedocumentpositionPRECEEDINGContains15", 10, attrPosition);
    }
}

