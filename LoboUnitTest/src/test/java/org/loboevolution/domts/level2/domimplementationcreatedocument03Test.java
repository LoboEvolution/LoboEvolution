
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;


/**
 * The createDocument method with valid arguments, should create a DOM Document of
 * the specified type.
 * <p>
 * Call the createDocument on this DOMImplementation with
 * createDocument ("http://www.w3.org/DOMTest/L2",see the array below for valid QNames,null).
 * Check if the returned Document object is empty with no Document Element.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument</a>
 */
public class domimplementationcreatedocument03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        Document newDoc;
        DocumentType docType = null;

        String namespaceURI = "http://www.w3.org/DOMTest/L2";
        String qualifiedName;
        List<String> qualifiedNames = new ArrayList<>();
        qualifiedNames.add("_:_");
        qualifiedNames.add("_:h0");
        qualifiedNames.add("_:test");
        qualifiedNames.add("l_:_");
        qualifiedNames.add("ns:_0");
        qualifiedNames.add("ns:a0");
        qualifiedNames.add("ns0:test");
        qualifiedNames.add("a.b:c");
        qualifiedNames.add("a-b:c");
        qualifiedNames.add("a-b:c");

        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        for (Object name : qualifiedNames) {
            qualifiedName = (String) name;
            newDoc = domImpl.createDocument(namespaceURI, qualifiedName, docType);
            assertNotNull("domimplementationcreatedocument03", newDoc);
        }
    }
}

