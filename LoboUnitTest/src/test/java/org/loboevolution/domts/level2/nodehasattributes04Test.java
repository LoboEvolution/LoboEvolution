
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

import static org.junit.Assert.assertTrue;


/**
 * The method hasAttributes returns whether this node (if it is an element) has any attributes.
 * Create a new Document, Element and Attr node.  Add the Attr to the Element and append the
 * Element to the Document.  Retreive the newly created element node from the document and check
 * if it has attributes using hasAttributes.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeHasAttrs">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeHasAttrs</a>
 */
public class nodehasattributes04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DocumentType docType = null;

        DOMImplementation domImpl;
        Element element;
        Element elementTest;
        Element elementDoc;
        Attr attribute;
        Node setNode;
        Node appendedChild;
        HTMLCollection elementList;
        boolean hasAttributes;
        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "test", docType);
        element = newDoc.createElementNS("http://www.w3.org/DOM/Test", "dom:elem");
        attribute = newDoc.createAttribute("attr");
        setNode = element.setAttributeNode(attribute);
        elementDoc = newDoc.getDocumentElement();
        appendedChild = elementDoc.appendChild(element);
        elementList = newDoc.getElementsByTagNameNS("http://www.w3.org/DOM/Test", "elem");
        elementTest = (Element) elementList.item(0);
        hasAttributes = elementTest.hasAttributes();
        assertTrue("nodehasattributes04", hasAttributes);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/nodehasattributes04";
    }
}

