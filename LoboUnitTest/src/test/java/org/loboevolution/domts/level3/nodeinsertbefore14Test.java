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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The method insertBefore inserts the node newChild before the existing child node refChild.
 * If refChild is null, insert newChild at the end of the list of children.
 * A NO_MODIFICATION_ALLOWED_ERR is raised if the node is read-only.
 * Using insertBefore on this Document node attempt to insert a new Attr node before
 * this DocumentType node and verfiy if a NO_MODIFICATION_ALLOWED_ERR is raised.
 * (This can also raise a HIERARCHY_REQUEST_ERR and NOT_FOUND_ERR)
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore14Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        Attr newAttr;
        Node inserted;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        newAttr = doc.createAttributeNS("http://www.w3.org/DOM/Test", "dom3:attr");

        {
            boolean success = false;
            try {
                inserted = docType.insertBefore(newAttr, docType);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("NO_MODIFICATION_ALLOWED_ERR_nodeinsertbefore14", success);
        }
    }
}

