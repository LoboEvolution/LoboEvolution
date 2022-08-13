
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertEquals;


/**
 * The method getLocalName returns the local part of the qualified name of this node.
 * <p>
 * Ceate two new element nodes and atribute nodes, with and without namespace prefixes.
 * Retreive the local part of their qualified names using getLocalName and verrify
 * if it is correct.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSLocalN">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSLocalN</a>
 */
public class nodegetlocalname03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element qelement;
        Attr attr;
        Attr qattr;
        String localElemName;
        String localQElemName;
        String localAttrName;
        String localQAttrName;
        doc = sampleXmlFile("staff.xml");
        element = doc.createElementNS("http://www.w3.org/DOM/Test/elem", "elem");
        qelement = doc.createElementNS("http://www.w3.org/DOM/Test/elem", "qual:qelem");
        attr = doc.createAttributeNS("http://www.w3.org/DOM/Test/attr", "attr");
        qattr = doc.createAttributeNS("http://www.w3.org/DOM/Test/attr", "qual:qattr");
        localElemName = element.getLocalName();
        localQElemName = qelement.getLocalName();
        localAttrName = attr.getLocalName();
        localQAttrName = qattr.getLocalName();
        assertEquals("nodegetlocalname03_localElemName", "elem", localElemName);
        assertEquals("nodegetlocalname03_localQElemName", "qelem", localQElemName);
        assertEquals("nodegetlocalname03_localAttrName", "attr", localAttrName);
        assertEquals("nodegetlocalname03_localQAttrName", "qattr", localQAttrName);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/nodegetlocalname03";
    }
}

