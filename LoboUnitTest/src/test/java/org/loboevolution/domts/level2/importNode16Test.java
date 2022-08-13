
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should raise NOT_SUPPORTED_ERR DOMException if
 * the type of node being imported is DocumentType.
 * <p>
 * Retrieve document staff.xml and get its type.
 * Invoke method importNode(importedNode,deep) where importedNode
 * contains the document type of the staff.xml.
 * Method should raise NOT_SUPPORT_ERR DOMException.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NOT_SUPPORTED_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-258A00AF')/constant[@name='NOT_SUPPORTED_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('Core-Document-importNode')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NOT_SUPPORTED_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('Core-Document-importNode')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='NOT_SUPPORTED_ERR'])</a>
 */
public class importNode16Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document anotherDoc;
        DocumentType docType;
        Node node;
        doc = sampleXmlFile("staffNS.xml");
        anotherDoc = sampleXmlFile("staffNS.xml");
        docType = anotherDoc.getDoctype();

        {
            boolean success = false;
            try {
                node = doc.importNode(docType, false);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue("throw_NOT_SUPPORTED_ERR", success);
        }
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/importNode16";
    }
}

