/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;

/**
 * Using removeChild on the first 'p' Element node attempt to remove a EntityReference
 * node child and verify the nodeName of the returned node that was removed.  Attempt
 * to remove a non-child from an entity reference and expect either a NOT_FOUND_ERR or
 * a NO_MODIFICATION_ALLOWED_ERR.  Renove a child from an entity reference and expect
 * a NO_MODIFICATION_ALLOWED_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class noderemovechild19Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection parentList;
        Element parent;
        EntityReference child;
        EntityReference removed;
        String removedName;
        Node removedNode;
        Node entRefChild;
        doc = sampleXmlFile("hc_staff.xml");
        parentList = doc.getElementsByTagName("acronym");
        parent = (Element) parentList.item(1);
        child = (EntityReference) parent.getFirstChild();
        removed = (EntityReference) parent.removeChild(child);
        removedName = removed.getNodeName();
        assertEquals("noderemovechild19", "beta", removedName);

        try {
            removedNode = child.removeChild(parent);
            fail("throw_DOMException");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 7:
                case 8:
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
                    removedNode = child.removeChild(entRefChild);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
                }
                assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
            }
        }
    }
}

