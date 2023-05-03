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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;

/**
 * Using removeChild on a default Attribute node attempt to remove its EntityReference child node and
 * and verify the name of the returned node that was removed.  Now attempt the reverse
 * and verify if a NO_MODIFICATION_ALLOWED_ERR or NOT_FOUND_ERR is thrown.
 * Then remove an child of the entity reference and expect a NO_MODIFICATION_ALLOWED_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class noderemovechild31Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection parentList;
        NamedNodeMap attrsMap;
        Attr parent;
        EntityReference child;
        EntityReference entRef;
        Element elem;
        EntityReference removed;
        Node removedNode;
        String removedName;
        Node appendedChild;
        Node entRefChild;
        doc = sampleXmlFile("hc_staff.xml");
        parentList = doc.getElementsByTagName("acronym");
        elem = (Element) parentList.item(3);
        attrsMap = elem.getAttributes();
        parent = (Attr) attrsMap.getNamedItem("class");
        entRef = doc.createEntityReference("delta");
        appendedChild = parent.appendChild(entRef);
        child = (EntityReference) parent.getLastChild();
        removed = (EntityReference) parent.removeChild(child);
        removedName = removed.getNodeName();
        assertEquals("noderemovechild31", "delta", removedName);

        try {
            removedNode = child.removeChild(parent);
            fail("throw_DOMException");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 7:
                    break;
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

