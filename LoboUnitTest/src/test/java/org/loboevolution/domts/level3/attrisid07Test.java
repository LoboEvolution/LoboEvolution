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

package org.loboevolution.domts.level3;


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * The method isId returns whether this attribute is known to be of type ID or not.
 * <p>
 * Add a new attribute of type ID to the third acronym element node of this document. Verify that the method
 * isId returns true. The use of Element.setIdAttributeNS() makes 'isId' a user-determined ID attribute.
 * Import the newly created attribute node into this document.
 * Since user data assocated to the imported node is not carried over, verify that the method isId
 * returns false on the imported attribute node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-isId">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-isId</a>
 */
public class attrisid07Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element acronymElem;
        Attr attr;
        Attr attrImported;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagNameNS("*", "acronym");
        acronymElem = (Element) elemList.item(2);
        acronymElem.setAttributeNS("http://www.w3.org/DOM", "dom3:newAttr", "null");
        acronymElem.setIdAttributeNS("http://www.w3.org/DOM", "newAttr", true);
        attr = acronymElem.getAttributeNodeNS("http://www.w3.org/DOM", "newAttr");
        assertTrue("AttrIsIDTrue07_1", attr.isId());
        attrImported = (Attr) doc.importNode(attr, false);
        assertFalse("AttrIsID07_isFalseforImportedNode", attrImported.isId());
    }
}