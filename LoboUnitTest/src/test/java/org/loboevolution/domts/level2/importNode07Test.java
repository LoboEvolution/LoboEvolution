
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

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import static org.junit.Assert.*;


/**
 * The "importNode(importedNode,deep)" method for a
 * Document should import the given importedNode into that Document.
 * The importedNode is of type Element.
 * If this document defines default attributes for this element name (importedNode),
 * those default attributes are assigned.
 * <p>
 * Create an element whose name is "employee" in a different document.
 * Invoke method importNode(importedNode,deep) on this document which
 * defines default attribute for the element name "employee".
 * Method should return an the imported element with an assigned default attribute.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode">http://www.w3.org/TR/DOM-Level-2-Core/core#Core-Document-importNode</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=238">http://www.w3.org/Bugs/Public/show_bug.cgi?id=238</a>
 */
public class importNode07Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Document aNewDoc;
        Element element;
        Element aNode;
        NamedNodeMap attributes;
        String name;
        Attr attr;
        String lname;
        String namespaceURI = "http://www.nist.gov";
        String qualifiedName = "employee";
        doc = sampleXmlFile("staffNS.xml");
        aNewDoc = sampleXmlFile("staff.xml");
        element = aNewDoc.createElementNS(namespaceURI, qualifiedName);
        aNode = (Element) doc.importNode(element, false);
        attributes = aNode.getAttributes();
        assertEquals("throw_Size", 1, attributes.getLength());
        name = aNode.getNodeName();
        assertEquals("nodeName", "employee", name);
        attr = (Attr) attributes.item(0);
        lname = attr.getLocalName();
        assertEquals("lname", "defaultAttr", lname);
    }
}

