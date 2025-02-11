
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
import org.loboevolution.html.node.EntityReference;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The "createEntityReference(name)" method creates an
 * EntityReferrence node.
 * <p>
 * Retrieve the entire DOM document and invoke its
 * "createEntityReference(name)" method.  It should create
 * a new EntityReference node for the Entity with the
 * given name.  The name, value and type are retrieved and
 * output.

 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-392B75AE">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-392B75AE</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68D080</a>
 */
public class DocumentcreateentityreferenceTest extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final Document doc;
        final EntityReference newEntRefNode;
        final String entRefValue;
        final String entRefName;
        final int entRefType;
        doc = sampleXmlFile("staff.xml");
        newEntRefNode = doc.createEntityReference("ent1");
        assertNotNull(newEntRefNode, "DocumentcreateentityreferenceAssert1");
        entRefValue = newEntRefNode.getNodeValue();
        assertNull(entRefValue, "DocumentcreateentityreferenceAssert2");
        entRefName = newEntRefNode.getNodeName();
        assertEquals("ent1", entRefName, "DocumentcreateentityreferenceAssert3");
        entRefType = newEntRefNode.getNodeType();
        assertEquals(5, entRefType, "DocumentcreateentityreferenceAssert4");
    }
}

