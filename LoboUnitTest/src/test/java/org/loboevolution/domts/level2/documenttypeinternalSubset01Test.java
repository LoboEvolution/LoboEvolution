
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
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.assertNull;


/**
 * The method getInternalSubset() returns the internal subset as a string.
 * <p>
 * Create a new DocumentType node with null values for publicId and systemId.
 * Verify that its internal subset is null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-Core-DocType-internalSubset">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-Core-DocType-internalSubset</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class documenttypeinternalSubset01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        DocumentImpl doc;
        DocumentType docType;
        DOMImplementation domImpl;
        String internal;
        String nullNS = null;

        doc = (DocumentImpl) sampleXmlFile("staffNS.xml");
        doc.setTest(true);
        domImpl = doc.getImplementation();
        docType = domImpl.createDocumentType("l2:root", nullNS, nullNS);
        internal = docType.getInternalSubset();
        assertNull("internalSubsetNull", internal);
    }
}

