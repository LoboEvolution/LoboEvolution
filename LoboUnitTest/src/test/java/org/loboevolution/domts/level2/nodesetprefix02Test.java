
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertEquals;


/**
 * The method setPrefix sets the namespace prefix of this node.  Note that setting this attribute,
 * when permitted, changes the nodeName attribute, which holds the qualified name, as well as the
 * tagName and name attributes of the Element and Attr interfaces, when applicable.
 * Create a new attribute node and add it to an element node with an existing attribute having
 * the same localName as this attribute but different namespaceURI.  Change the prefix of the
 * newly created attribute using setPrefix.  Check if the new attribute nodeName has changed
 * and the existing attribute is the same.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix</a>
 */
public class nodesetprefix02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Attr attribute;
        Attr newAttribute;
        Node setNode;
        HTMLCollection elementList;
        String attrName;
        String newAttrName;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagName("address");
        element = (Element) elementList.item(1);
        newAttribute = doc.createAttributeNS("http://www.w3.org/DOM/Test", "test:address");
        setNode = element.setAttributeNodeNS(newAttribute);
        newAttribute.setPrefix("dom");
        attribute = element.getAttributeNodeNS("http://www.usa.com", "domestic");
        attrName = attribute.getNodeName();
        newAttrName = newAttribute.getNodeName();
        assertEquals("nodesetprefix02_attrName", "dmstc:domestic", attrName);
        assertEquals("nodesetprefix02_newAttrName", "dom:address", newAttrName);
    }

    /**
     * Gets URI that identifies the test.
     *
     * @return uri identifier of test
     */
    public String getTargetURI() {
        return "http://www.w3.org/2001/DOM-Test-Suite/level2/core/nodesetprefix02";
    }
}

