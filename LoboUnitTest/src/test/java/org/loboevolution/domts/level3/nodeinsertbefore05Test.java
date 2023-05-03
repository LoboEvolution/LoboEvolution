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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.fail;


/**
 * Attempt to insert a second DocumentType node in a document using Node.insertBefore,
 * should raise either DOMException with either a HIERARCHY_REQUEST_ERR
 * or NOT_SUPPORTED_ERR code.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        DOMImplementation domImpl;
        DocumentType newDocType;
        Node inserted;
        String nullPubId = null;

        String nullSysId = null;

        String rootName;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        rootName = docType.getName();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(rootName, nullPubId, nullSysId);

        try {
            inserted = doc.insertBefore(newDocType, docType);
            fail("throw_DOMException");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 3:
                case 9:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

