
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

package org.loboevolution.domts.level1;

import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Replaces a text node of an attribute with a document fragment and checks if the value of
 * the attribute is changed.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307</a>
 */
public class hc_attrreplacechild2Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection acronymList;
        final Element testNode;
        final NamedNodeMap attributes;
        final Attr titleAttr;
        String value;
        final Text terNode;
        final Text dayNode;
        final DocumentFragment docFrag;
        Node retval;
        Node firstChild;
        doc = sampleXmlFile("hc_staff.xml");
        acronymList = doc.getElementsByTagName("acronym");
        testNode = (Element) acronymList.item(3);
        attributes = testNode.getAttributes();
        titleAttr = (Attr) attributes.getNamedItem("title");
        terNode = doc.createTextNode("ter");
        dayNode = doc.createTextNode("day");
        docFrag = doc.createDocumentFragment();
        retval = docFrag.appendChild(terNode);
        retval = docFrag.appendChild(dayNode);
        firstChild = titleAttr.getFirstChild();
        assertNotNull(firstChild);
        retval = titleAttr.replaceChild(docFrag, firstChild);
        value = titleAttr.getValue();
        assertEquals("terday", value);
        value = titleAttr.getNodeValue();
        assertEquals("attrNodeValue", "terday", value);
        value = retval.getNodeValue();
        assertEquals("retvalValue", "Yes", value);
        firstChild = titleAttr.getFirstChild();
        value = firstChild.getNodeValue();
        assertEquals( "ter", value);
    }
}

