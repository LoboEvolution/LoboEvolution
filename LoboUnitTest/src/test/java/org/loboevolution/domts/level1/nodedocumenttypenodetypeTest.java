
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


/**
 * The "getNodeType()" method for a DocumentType Node
 * returns the constant value 10.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-111237558">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-111237558</a>
 */
public class nodedocumenttypenodetypeTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType documentTypeNode;
        int nodeType;
        doc = sampleXmlFile("staff.xml");
        documentTypeNode = doc.getDoctype();
        assertNotNull("doctypeNotNull", documentTypeNode);
        nodeType = documentTypeNode.getNodeType();
        assertEquals("nodeType", 10, nodeType);
    }
}

