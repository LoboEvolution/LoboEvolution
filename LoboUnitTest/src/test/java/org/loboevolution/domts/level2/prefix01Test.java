
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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertNull;


/**
 * The "getPrefix()" method for a Node
 * returns the namespace prefix of the node,
 * and for nodes of any type other than ELEMENT_NODE and ATTRIBUTE_NODE
 * and nodes created with a DOM Level 1 method, this is null.
 * <p>
 * Create an new Element with the createElement() method.
 * Invoke the "getPrefix()" method on the newly created element
 * node will cause "null" to be returned.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-NodeNSPrefix</a>
 */
public class prefix01Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Node createdNode;
        String prefix;
        doc = sampleXmlFile("staffNS.xml");
        createdNode = doc.createElement("test:employee");
        prefix = createdNode.getPrefix();
        assertNull("throw_Null", prefix);
    }
}

