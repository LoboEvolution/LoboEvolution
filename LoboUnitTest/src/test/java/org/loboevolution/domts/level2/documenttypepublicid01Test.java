
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.*;


/**
 * The method getInternalSubset() returns the public identifier of the external subset.
 * <p>
 * Create a new DocumentType node with the value "PUB" for its publicId.
 * Check the value of the publicId attribute using getPublicId().
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-Core-DocType-publicId">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-Core-DocType-publicId</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class documenttypepublicid01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        DOMImplementation domImpl;
        String publicId;
        String nullNS = null;

        doc = sampleXmlFile("staffNS.xml");
        
        domImpl = doc.getImplementation();
        docType = domImpl.createDocumentType("l2:root", "PUB", nullNS);
        publicId = docType.getPublicId();
        assertEquals("documenttypepublicid01", "PUB", publicId);
    }
}

