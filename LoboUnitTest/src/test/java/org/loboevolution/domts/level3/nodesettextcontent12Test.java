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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * Using setTextContent on a new EntityReference node, attempt to set its value.
 * Since EntityReference nodes are ReadOnly, check if a NO_MODIFICATION_ALLOWED_ERR
 * is raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-textContent</a>
 */
public class nodesettextcontent12Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        EntityReference entRef;
        String textContent;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.getDocumentElement();
        entRef = doc.createEntityReference("beta");
        appendedChild = elem.appendChild(entRef);

        {
            boolean success = false;
            try {
                entRef.setTextContent("NA");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("nodesettextcontent12", success);
        }
    }
}

