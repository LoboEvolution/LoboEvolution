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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.EntityReference;

import static org.junit.Assert.assertNull;

/**
 * The method removeChild removes the child node indicated by oldChild from the list
 * of children, and returns it.
 * Using removeChild on a new DocumentFragment node attempt to remove a new EntityReference node.
 * Also attempt to remove the document fragment node from the EntityReference.  Verify that a
 * NO_MODIFICATION_ALLOWED_ERR (EntityReference node is read-only) or a NOT_FOUND_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class noderemovechild12Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        EntityReference eRef;
        EntityReference removedERef;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        eRef = doc.createEntityReference("ent1");
        docFrag.appendChild(eRef);
        docFrag.removeChild(eRef);
        removedERef = (EntityReference) docFrag.getFirstChild();
        assertNull("noderemovechild12", removedERef);

        try {
            eRef.removeChild(docFrag);
        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 8:
                case 7:
                    break;
                default:
                    throw ex;
            }
        }
    }

}

