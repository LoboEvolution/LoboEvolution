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
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Using removeChild on a new EntityReference node attempt to remove its last ProcessingInstruction
 * child node and verify if a NO_MODIFICATION_ALLOWED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class noderemovechild14Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Node removed;
        EntityReference eRef;
        ProcessingInstruction pi;
        String entName;
        doc = sampleXmlFile("hc_staff.xml");
        eRef = doc.createEntityReference("ent4");
        pi = (ProcessingInstruction) eRef.getLastChild();
        assertNotNull("piNotNull", pi);

        {
            boolean success = false;
            try {
                removed = eRef.removeChild(pi);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

