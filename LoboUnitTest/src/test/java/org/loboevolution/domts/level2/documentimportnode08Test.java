
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
 * The importNode method imports a node from another document to this document.
 * A NOT_SUPPORTED_ERR is raised if the type of node being imported is
 * not supported
 * <p>
 * Using the method importNode with deep=true, try to import a newly created DOcumentType
 * node. Since DocumentType nodes cannot be imported, a NOT_SUPPORTED_ERR should be raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class documentimportnode08Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        DOMImplementation domImpl;
        String nullNS = null;
        doc = sampleXmlFile("staffNS.xml");
        domImpl = doc.getImplementation();
        docType = domImpl.createDocumentType("test:root", nullNS, nullNS);

        boolean success = false;
        try {
            doc.importNode(docType, true);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
        }
        assertTrue("throw_NOT_SUPPORTED_ERR", success);
    }
}

