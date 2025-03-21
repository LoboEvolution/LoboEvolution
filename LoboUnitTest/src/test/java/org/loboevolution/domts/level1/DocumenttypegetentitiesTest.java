
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level1;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * The "getEntities()" method is a NamedNodeMap that contains
 * the general entities for this document.
 * <p>
 * Retrieve the Document Type for this document and create
 * a NamedNodeMap of all its entities.  The entire map is
 * traversed and the names of the entities are retrieved.
 * There should be 5 entities.  Duplicates should be ignored.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1788794630">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1788794630</a>
 */
public class DocumenttypegetentitiesTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final DocumentType docType;
        final NamedNodeMap entityList;
        String name;
        final List<String> expectedResult = new ArrayList<>();
        expectedResult.add("ent1");
        expectedResult.add("ent2");
        expectedResult.add("ent3");
        expectedResult.add("ent4");
        expectedResult.add("ent5");

        final List<String> nameList = new ArrayList<>();

        Node entity;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull(docType, "DocumenttypegetentitiesAssert1");
        entityList = docType.getEntities();
        assertNotNull(entityList, "DocumenttypegetentitiesAssert2");
        for (int indexN1007B = 0; indexN1007B < entityList.getLength(); indexN1007B++) {
            entity = entityList.item(indexN1007B);
            name = entity.getNodeName();
            nameList.add(name);
        }

        assertEquals(expectedResult, nameList, "DocumenttypegetentitiesAssert3");

    }
}

