
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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.*;


/**
 * The method getElementsByTagNameNS returns a NodeList of all the Elements with
 * a given local name and namespace URI in the order in which they are encountered
 * in a preorder traversal of the Document tree.
 * <p>
 * Invoke the getElementsByTagNameNS method on a new Document object with the values of
 * namespaceURI=** and localName=**.  This should return a nodeList of 0 items.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-getElBTNNS</a>
 */
public class documentgetelementsbytagnameNS03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection childList;
        doc = sampleXmlFile("staffNS.xml");
        childList = doc.getElementsByTagNameNS("**", "*");
        assertEquals( "documentgetelementsbytagnameNS03", 0, childList.getLength());
    }
}

