
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import static org.junit.Assert.assertTrue;


/**
 * The method createElementNS raises a NAMESPACE_ERR if the qualifiedName
 * has a prefix that is "xml" and the namespaceURI is different
 * from http://www.w3.org/XML/1998/namespace
 * <p>
 * Invoke the createElementNS method on this DOMImplementation object with
 * the qualifiedName as xml:root and namespaceURI as http://www.w3.org/xml/1998/namespace
 * Check if the NAMESPACE_ERR exception is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS</a>
 */
public class documentcreateelementNS06Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DocumentType docType = null;

        DOMImplementation domImpl;
        String namespaceURI = "http://www.w3.org/xml/1998/namespace ";
        String qualifiedName = "xml:root";
        doc = sampleXmlFile("staffNS.xml");
        
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/DOM/Test", "dom:doc", docType);

        boolean success = false;
        try {
            newDoc.createElementNS(namespaceURI, qualifiedName);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NAMESPACE_ERR);
        }
        assertTrue("documentcreateelementNS06", success);

    }
}

