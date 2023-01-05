
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertEquals;


/**
 * The "getElementsByTagNameNS(namespaceURI,localName)" method for a
 * Document should return a new NodeList of all Elements that have a namespace
 * when local name is specified as ' '.
 * <p>
 * Invoke method getElementsByTagNameNS(namespaceURI,localName) on this document
 * with namespaceURI and localName as " ".
 * Method should return a new NodeList of 36 elements.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS</a>
 */
public class getElementsByTagNameNS01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "*";
        String localName = "*";
        Document doc;
        HTMLCollection newList;
        doc = sampleXmlFile("staffNS.xml");
        newList = doc.getElementsByTagNameNS(namespaceURI, localName);
        assertEquals( "throw_Size", 36, newList.getLength());
    }
}

