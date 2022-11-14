
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * The "createElementNS(namespaceURI,qualifiedName)" method for a
 * Document should return a new Element object given that all parameters
 * are valid and correctly formed.
 * <p>
 * Invoke method createElementNS(namespaceURI,qualifiedName on this document
 * with namespaceURI as "http://www.nist.gov" and qualifiedName as "gov:faculty".
 * Method should return a new Element object whose name is "gov:faculty".
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-104682815">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-104682815</a>
 */
public class createElementNS05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.nist.gov";
        String qualifiedName = "GOV:FACULTY";
        Document doc;
        Element newElement;
        String elementName;
        doc = sampleXmlFile("staffNS.xml");
        newElement = doc.createElementNS(namespaceURI, qualifiedName);
        elementName = newElement.getTagName();
        assertEquals("throw_Equals", qualifiedName, elementName);
    }
}

