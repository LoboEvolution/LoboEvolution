/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.domts.level3;


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Create a document fragment with an entity reference, adopt the node and check
 * that the entity reference value comes from the adopting documents DTD.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-adoptNode</a>
 */
public class Documentadoptnode16Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DocumentFragment docFragment;
        HTMLCollection childList;
        final Element parent;
        final Element child;
        final Attr childsAttr;
        final EntityReference entRef;
        final Text textNode;
        final Node adopted;
        final Element parentImp;
        final Element childImp;
        final NamedNodeMap attributes;
        final Attr childAttrImp;
        String nodeValue;
        Node appendedChild;
        final Attr attrNode;
        final Node firstChild;
        final int firstChildType;
        final String firstChildName;
        final String firstChildValue;
        doc = sampleXmlFile("hc_staff.xml");
        docFragment = doc.createDocumentFragment();
        parent = doc.createElement("parent");
        child = doc.createElement("child");
        childsAttr = doc.createAttribute("state");
        entRef = doc.createEntityReference("gamma");
        textNode = doc.createTextNode("Test");
        appendedChild = childsAttr.appendChild(entRef);
        attrNode = child.setAttributeNode(childsAttr);
        appendedChild = child.appendChild(textNode);
        appendedChild = parent.appendChild(child);
        appendedChild = docFragment.appendChild(parent);
        adopted = doc.adoptNode(docFragment);

        if ((adopted != null)) {
            parentImp = (Element) adopted.getFirstChild();
            childImp = (Element) parentImp.getFirstChild();
            attributes = childImp.getAttributes();
            childAttrImp = (Attr) attributes.getNamedItem("state");
            firstChild = childAttrImp.getFirstChild();
            assertNotNull(firstChild, "Documentadoptnode16Assert3");
            firstChildName = firstChild.getNodeName();
            firstChildValue = firstChild.getNodeValue();
            firstChildType = firstChild.getNodeType();

            if (firstChildType == 5) {
                assertEquals("gamma", firstChildName, "Documentadoptnode16Assert4");
            } else {
                assertEquals("Texas", firstChildValue, "Documentadoptnode16Assert5");
            }

        }
    }
}

