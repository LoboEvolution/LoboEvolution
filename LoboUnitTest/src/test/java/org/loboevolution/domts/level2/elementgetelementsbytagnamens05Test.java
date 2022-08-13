
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
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * Returns a NodeList of all the Elements with a given local name and namespace URI in the
 * order in which they are encountered in a preorder traversal of the Document tree.
 * Invoke getElementsByTagNameNS on the documentElement with the following values:
 * namespaceURI: 'http://www.altavista.com'
 * localName: '*'.
 * Verify if this returns a nodeList of 1 elements.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS</a>
 */
public class elementgetelementsbytagnamens05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        HTMLCollection elementList;
        doc = sampleXmlFile("staffNS.xml");
        element = doc.getDocumentElement();
        elementList = element.getElementsByTagNameNS("http://www.altavista.com", "*");
        assertEquals( "elementgetelementsbytagnamens05", 1, elementList.getLength());
    }
}

