
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

package org.loboevolution.domts.level2;

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.assertTrue;


/**
 * The createDocument method should raise a NAMESPACE_ERR if the qualifiedName is malformed
 * <p>
 * Invoke the createDocument method on this DOMImplementation object with null values
 * for namespaceURI and docType and a malformed qualifiedName.
 * The NAMESPACE_ERR should be raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocument</a>
 */
public class domimplementationcreatedocument07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        String namespaceURI = "http://www.w3.org/DOMTest/level2";
        DocumentType docType = null;

        doc = sampleXmlFile("staffNS.xml");
        
        domImpl = doc.getImplementation();

        {
            boolean success = false;
            try {
                domImpl.createDocument(namespaceURI, ":", docType);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
            }
            assertTrue("domimplementationcreatedocument07", success);
        }
    }
}

