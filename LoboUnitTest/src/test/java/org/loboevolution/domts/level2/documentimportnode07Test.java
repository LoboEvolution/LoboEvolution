
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
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * The importNode method imports a node from another document to this document.
 * A NOT_SUPPORTED_ERR is raised if the type of node being imported is
 * not supported
 * <p>
 * Using the method importNode with deep=true, try to import this Document's
 * DocumentType object. Since DocumentType nodes cannot be imported, a
 * NOT_SUPPORTED_ERR should be raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 */
public class documentimportnode07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Node imported;
        DocumentType docType;
        doc = sampleXmlFile("staffNS.xml");
        docType = doc.getDoctype();

        {
            boolean success = false;
            try {
                imported = doc.importNode(docType, true);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
            }
            assertTrue("throw_NOT_SUPPORTED_ERR", success);
        }
    }
}

