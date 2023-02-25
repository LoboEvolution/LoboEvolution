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


import com.gargoylesoftware.css.dom.DOMException;
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

