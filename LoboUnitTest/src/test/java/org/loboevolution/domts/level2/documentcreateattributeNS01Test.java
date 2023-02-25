
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.*;


/**
 * The method createAttributeNS creates an attribute of the given qualified name and namespace URI
 * <p>
 * Invoke the createAttributeNS method on this Document object with a null
 * namespaceURI, and a qualifiedName without a prefix.  This should return a valid Attr
 * node object.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS</a>
 */
public class documentcreateattributeNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Attr attribute;
        String namespaceURI = "http://www.w3.org/XML/1998/namespace";
        String qualifiedName = "test";
        doc = sampleXmlFile("staffNS.xml");
        attribute = doc.createAttributeNS(namespaceURI, qualifiedName);
        String nodeName = attribute.getNodeName();
        assertEquals("documentcreateattributeNS01", "test", nodeName);
    }
}

