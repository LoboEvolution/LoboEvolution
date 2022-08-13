
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * An notation is accessed, setNodeValue is called with a non-null argument, but getNodeValue
 * should still return null.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-5431D1B9">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-5431D1B9</a>
 */
public class nodevalue08Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        Node newNode;
        String newValue;
        NamedNodeMap nodeMap;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull("docTypeNotNull", docType);
        nodeMap = docType.getNotations();
        assertNotNull("notationsNotNull", nodeMap);
        newNode = nodeMap.getNamedItem("notation1");
        assertNotNull("notationNotNull", newNode);
        newValue = newNode.getNodeValue();
        assertNull("initiallyNull", newValue);
        newNode.setNodeValue("This should have no effect");
        newValue = newNode.getNodeValue();
        assertNull("nullAfterAttemptedChange", newValue);
    }
}

