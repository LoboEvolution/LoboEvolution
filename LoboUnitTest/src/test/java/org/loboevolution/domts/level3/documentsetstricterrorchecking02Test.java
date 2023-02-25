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

import static org.junit.Assert.assertTrue;


/**
 * Set the strictErrorChecking attribute value on a new Document to true.
 * Call the createAttributeNS method on this document with a a null namespaceURI and a qualified name
 * with a prefix and check if the NAMESPACE_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-strictErrorChecking">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-strictErrorChecking</a>
 */
public class documentsetstricterrorchecking02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Attr newAttr;
        String nullValue = null;

        doc = sampleXmlFile("hc_staff.xml");
        doc.setStrictErrorChecking(true);

        {
            boolean success = false;
            try {
                newAttr = doc.createAttributeNS(nullValue, "dom:test");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NAMESPACE_ERR);
            }
            assertTrue("NAMESPACE_ERR_documentsetstricterrorchecking02", success);
        }
    }
}

