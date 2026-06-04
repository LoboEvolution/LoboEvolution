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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Using compareDocumentPosition check the document position of the EntityReference node ent4's
 * first child and last child.  Invoke compareDocumentPositon on first child with last child as a parameter
 * should return FOLLOWING, and should return PRECEDING vice versa.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-compareDocumentPosition</a>
 */
public class Nodecomparedocumentposition28Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection varList;
        final Element varElem;
        final EntityReference entity;
        final Element entityChild1;
        final ProcessingInstruction entityChild2;
        final int entityChild1Position;
        final int entityChild2Position;
        doc = sampleXmlFile("hc_staff.xml");

        varList = doc.getElementsByTagName("var");
        varElem = (Element) varList.item(2);
        assertNotNull(varElem, "Nodecomparedocumentposition28Assert3");
        entity = (EntityReference) varElem.getFirstChild();
        assertNotNull(entity, "Nodecomparedocumentposition28Assert4");

        entityChild1 = (Element) entity.getFirstChild();
        assertNotNull(entityChild1, "Nodecomparedocumentposition28Assert5");
        entityChild2 = (ProcessingInstruction) entity.getLastChild();
        assertNotNull(entityChild2, "Nodecomparedocumentposition28Assert6");
        entityChild1Position = entityChild1.compareDocumentPosition(entityChild2);
        assertEquals(4, entityChild1Position, "Nodecomparedocumentposition28Assert7");
        entityChild2Position = entityChild2.compareDocumentPosition(entityChild1);
        assertEquals(2, entityChild2Position, "Nodecomparedocumentposition28Assert8");
    }
}

