
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;


/**
 * Create a document fragment with two adjacent text nodes, normalize and see if the text nodes
 * were combined.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-F68D095</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-B63ED1A3">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-B63ED1A3</a>
 */
public class hc_nodedocumentfragmentnormalize1Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFragment;
        String nodeValue;
        Text txtNode;
        Node retval;
        doc = sampleXmlFile("hc_staff.xml");
        docFragment = doc.createDocumentFragment();
        txtNode = doc.createTextNode("foo");
        retval = docFragment.appendChild(txtNode);
        txtNode = doc.createTextNode("bar");
        retval = docFragment.appendChild(txtNode);
        docFragment.normalize();
        txtNode = (Text) docFragment.getFirstChild();
        nodeValue = txtNode.getNodeValue();
        assertEquals("normalizedNodeValue", "foobar", nodeValue);
        retval = txtNode.getNextSibling();
        assertNull("singleChild", retval);
    }
}

