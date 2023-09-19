
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level2;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.assertTrue;


/**
 * The method setNamedItemNS adds a node using its namespaceURI and localName and
 * raises a INUSE_ATTRIBUTE_ERR Raised if arg is an Attr that is already an
 * attribute of another Element object.
 * <p>
 * Retreieve the attributes of first element whose localName is address into a named node map.
 * Retreive the attribute whose namespaceURI=http://www.usa.com and localName=domestic
 * from the NamedNodeMap.  Retreieve the attributes of second element whose localName is address
 * into a named node map.  Call the setNamedItemNS method on the second nodemap with the domestic
 * attribute that was retreived and removed from the first nodeMap as an argument.
 * Assuming that when an attribute is removed from a nodemap, it still remains in the domtree
 * his should raise an INUSE_ATTRIBIUTE_ERR.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-setNamedItemNS</a>
 */
public class namednodemapsetnameditemns07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap attributes;
        HTMLCollection elementList;
        Element element;
        Attr attr;
        doc = sampleXmlFile("staffNS.xml");
        elementList = doc.getElementsByTagNameNS("*", "address");
        element = (Element) elementList.item(0);
        attributes = element.getAttributes();
        attr = (Attr) attributes.getNamedItemNS("http://www.usa.com", "domestic");
        element = (Element) elementList.item(1);
        attributes = element.getAttributes();
        boolean success = false;
        try {
            attributes.setNamedItemNS(attr);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.INUSE_ATTRIBUTE_ERR);
        }
        assertTrue("namednodemapsetnameditemns07", success);
    }
}

