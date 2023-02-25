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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The method renameNode renames an existing node and raises a  NOT_SUPPORTED_ERR
 * if the type of the specified node is neither ELEMENT_NODE nor ATTRIBUTE_NODE.
 * <p>
 * Invoke the renameNode method on this document node to attempt to rename itself.
 * The namespaceURI specified here is null and the name has a prefix.
 * Check if a NOT_SUPPORTED_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-renameNode</a>
 */
public class documentrenamenode24Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Node renamedNode;
        String nullNSURI = null;

        Document docowner;
        doc = sampleXmlFile("hc_staff.xml");


        boolean success = false;
        try {
            renamedNode = doc.renameNode(doc, nullNSURI, "doc:root");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue("documentrenamenode24_NOT_SUPPORTED_ERR", success);

    }
}

