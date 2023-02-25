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
 * Invoke the adoptNode method on this document using a new PI node created in a new doc
 * as the source.  Verify if the node has been adopted correctly by checking the nodeValue
 * of the adopted node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class documentadoptnode36Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        Document newDoc;
        ProcessingInstruction newPI1;
        ProcessingInstruction newPI2;
        ProcessingInstruction adoptedPI1;
        ProcessingInstruction adoptedPI2;
        String piTarget;
        String piData;
        DocumentType nullDocType = null;

        Element docElem;
        String rootNS;
        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument(rootNS, rootName, nullDocType);
        newPI1 = newDoc.createProcessingInstruction("PITarget", "PIData");
        newPI2 = doc.createProcessingInstruction("PITarget", "PIData");
        adoptedPI1 = (ProcessingInstruction) newDoc.adoptNode(newPI1);

        if ((adoptedPI1 != null)) {
            adoptedPI2 = (ProcessingInstruction) newDoc.adoptNode(newPI2);

            if ((adoptedPI2 != null)) {
                piTarget = adoptedPI1.getTarget();
                piData = adoptedPI1.getData();
                assertEquals("documentadoptnode36_Target1", "PITarget", piTarget);
                assertEquals("documentadoptnode36_Data1", "PIData", piData);
                piTarget = adoptedPI2.getTarget();
                piData = adoptedPI2.getData();
                assertEquals("documentadoptnode36_Target2", "PITarget", piTarget);
                assertEquals("documentadoptnode36_Data2", "PIData", piData);
            }
        }
    }
}