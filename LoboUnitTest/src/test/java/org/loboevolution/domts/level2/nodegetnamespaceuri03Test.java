
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
import static org.junit.Assert.assertNull;


/**
 * The method getNamespaceURI returns the namespace URI of this node, or null if it is unspecified
 * For nodes of any type other than ELEMENT_NODE and ATTRIBUTE_NODE and nodes created with
 * a DOM Level 1 method, such as createElement from the Document interface, this is always null.
 * <p>
 * Ceate two new element nodes and atribute nodes, with and without namespace prefixes.
 * Retreive their namespaceURI's using getNamespaceURI and verrify if it is correct.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSname">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSname</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class nodegetnamespaceuri03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element elementNS;
        Attr attr;
        Attr attrNS;
        String elemNSURI;
        String elemNSURINull;
        String attrNSURI;
        String attrNSURINull;
        String nullNS = null;

        doc = sampleXmlFile("staff.xml");
        element = doc.createElementNS(nullNS, "elem");
        elementNS = doc.createElementNS("http://www.w3.org/DOM/Test/elem", "qual:qelem");
        attr = doc.createAttributeNS(nullNS, "attr");
        attrNS = doc.createAttributeNS("http://www.w3.org/DOM/Test/attr", "qual:qattr");
        elemNSURI = elementNS.getNamespaceURI();
        elemNSURINull = element.getNamespaceURI();
        attrNSURI = attrNS.getNamespaceURI();
        attrNSURINull = attr.getNamespaceURI();
        assertEquals("nodegetnamespaceuri03_elemNSURI", "http://www.w3.org/DOM/Test/elem", elemNSURI);
        assertNull("nodegetnamespaceuri03_1", elemNSURINull);
        assertEquals("nodegetnamespaceuri03_attrNSURI", "http://www.w3.org/DOM/Test/attr", attrNSURI);
        assertNull("nodegetnamespaceuri03_2", attrNSURINull);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/nodegetnamespaceuri03";
    }
}

