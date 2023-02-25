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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * Invoke the adoptNode method on another document using a new CDataSection node created in this
 * Document as the source.  Verify if the node has been adopted correctly by checking the nodeValue
 * of the adopted node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode32Test extends LoboUnitTest {


    /**
     * Runs the test case.
     *
     * @throws Throwable Any uncaught exception causes test to fail
     */
    @Test
    public void runTest() {
        Document doc;
        Document docAdopter;
        Node newCDATA;
        Node adoptedCDATA;
        String nodeValue;
        doc = sampleXmlFile("hc_staff.xml");
        docAdopter = sampleXmlFile("hc_staff.xml");
        newCDATA = doc.createCDATASection("Document.adoptNode test for a CDATASECTION_NODE");
        adoptedCDATA = docAdopter.adoptNode(newCDATA);

        if ((adoptedCDATA != null)) {
            nodeValue = adoptedCDATA.getNodeValue();
            assertEquals("documentadoptnode32", "Document.adoptNode test for a CDATASECTION_NODE", nodeValue);
        }
    }
}

