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
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.*;


/**
 * Using removeChild on this Document node attempt to remove a new DocumentType node and
 * verify if the DocumentType node is null.  Attempting to remove the DocumentType
 * a second type should result in a NOT_FOUND_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=417">http://www.w3.org/Bugs/Public/show_bug.cgi?id=417</a>
 */
public class noderemovechild05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        DocumentType removedDocType;
        Node removedChild;
        doc = sampleXmlFile("barfoo.xml");
        docType = doc.getDoctype();

        try {
            removedChild = doc.removeChild(docType);

        } catch (DOMException ex) {
            if (ex.getCode() == 9) {
                ex.printStackTrace();
                return;
            }
            throw ex;
        }
        assertNotNull("removedChildNotNull", removedChild);
        removedDocType = doc.getDoctype();
        assertNull("noderemovechild05", removedDocType);

        {
            boolean success = false;
            try {
                docType.removeChild(doc);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
            }
            assertTrue("NOT_FOUND_ERR_noderemovechild05", success);
        }
    }
}

