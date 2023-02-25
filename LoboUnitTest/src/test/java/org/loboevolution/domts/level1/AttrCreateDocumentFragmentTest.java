
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.*;

/**
 * Attr nodes may be associated with Element nodes contained within a DocumentFragment.
 * Create a new DocumentFragment and add a newly created Element node(with one attribute).
 * Once the element is added, its attribute should be available as an attribute associated
 * with an Element within a DocumentFragment.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-35CB04B5">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-35CB04B5</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68F082">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-F68F082</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-B63ED1A3">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-B63ED1A3</a>
 */
public class AttrCreateDocumentFragmentTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFragment;
        Element newOne;
        Element domesticNode;
        NamedNodeMap domesticAttr;
        Attr attrs;
        String attrName;
        doc = sampleXmlFile("staff.xml");
        docFragment = doc.createDocumentFragment();
        newOne = doc.createElement("newElement");
        newOne.setAttribute("newdomestic", "Yes");
        docFragment.appendChild(newOne);
        domesticNode = (Element) docFragment.getFirstChild();
        domesticAttr = domesticNode.getAttributes();
        attrs = (Attr) domesticAttr.item(0);
        attrName = attrs.getName();
        assertEquals("attrCreateDocumentFragmentAssert", "newdomestic", attrName);
    }
}

