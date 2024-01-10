
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The "getPublicId()" method of an Entity node contains
 * the public identifier associated with the entity, if
 * one was specified.
 * <p>
 * Retrieve the entity named "ent5" and access its
 * public identifier.  The string "entityURI" should be
 * returned.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7303025">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7303025</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6ABAEB38">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-6ABAEB38</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7C29F3E">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D7C29F3E</a>
 */
public class EntitygetpublicidTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final DocumentType docType;
        final NamedNodeMap entityList;
        final EntityReference entityNode;
        final String publicId;
        final String notation;
        doc = sampleXmlFile("staff.xml");
        docType = doc.getDoctype();
        assertNotNull(docType, "EntitygetpublicidAssert1");
        entityList = docType.getEntities();
        assertNotNull(entityList, "EntitygetpublicidAssert2");
        entityNode = (EntityReference) entityList.getNamedItem("ent5");
        publicId = entityNode.getPublicId();
        assertEquals("entityURI", publicId, "EntitygetpublicidAssert3");
        entityNode.getSystemId();
        notation = entityNode.getNotationName();
        assertEquals("notation1", notation, "EntitygetpublicidAssert4");
    }
}

