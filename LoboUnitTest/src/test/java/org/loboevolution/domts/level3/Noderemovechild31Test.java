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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Using removeChild on a default Attribute node attempt to remove its EntityReference child node and
 * and verify the name of the returned node that was removed.  Now attempt the reverse
 * and verify if a NO_MODIFICATION_ALLOWED_ERR or NOT_FOUND_ERR is thrown.
 * Then remove an child of the entity reference and expect a NO_MODIFICATION_ALLOWED_ERR.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class Noderemovechild31Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection parentList;
        final NamedNodeMap attrsMap;
        final Attr parent;
        final EntityReference child;
        final EntityReference entRef;
        final Element elem;
        final EntityReference removed;
        final String removedName;
        final Node entRefChild;
        doc = sampleXmlFile("hc_staff.xml");
        parentList = doc.getElementsByTagName("acronym");
        elem = (Element) parentList.item(3);
        attrsMap = elem.getAttributes();
        parent = (Attr) attrsMap.getNamedItem("class");
        entRef = doc.createEntityReference("delta");
        parent.appendChild(entRef);
        child = (EntityReference) parent.getLastChild();
        removed = (EntityReference) parent.removeChild(child);
        removedName = removed.getNodeName();
        assertEquals("delta", removedName, "Noderemovechild31Assert1");

        try {
            child.removeChild(parent);
            fail("throw_DOMException");

        } catch (final DOMException ex) {
            switch (ex.getCode()) {
                case 7, 8:
                    break;
                default:
                    throw ex;
            }
        }
        entRefChild = child.getFirstChild();

        if ((entRefChild != null)) {

            {
                boolean success = false;
                try {
                    child.removeChild(entRefChild);
                } catch (final DOMException ex) {
                    success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
                }
                assertTrue(success, "Noderemovechild31Assert2");
            }
        }
    }
}

