
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

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import static org.junit.Assert.assertTrue;


/**
 * The "replaceChild(newChild,oldChild)" method raises a
 * HIERARCHY_REQUEST_ERR DOMException if the node to put
 * in is one of this node's ancestors.
 * <p>
 * Retrieve the second employee and attempt to replace
 * one of its children with an ancestor node(root node).
 * An attempt to make such a replacement should raise the
 * desired exception.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='HIERARCHY_REQUEST_ERR'])">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='HIERARCHY_REQUEST_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-785887307')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='HIERARCHY_REQUEST_ERR'])">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-785887307')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='HIERARCHY_REQUEST_ERR'])</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-785887307</a>
 */
public class hc_nodereplacechildnodeancestorTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Node newChild;
        HTMLCollection elementList;
        Node employeeNode;
        NodeList childList;
        Node oldChild;
        doc = sampleXmlFile("hc_staff.xml");
        newChild = doc.getDocumentElement();
        elementList = doc.getElementsByTagName("p");
        employeeNode = elementList.item(1);
        childList = employeeNode.getChildNodes();
        oldChild = childList.item(0);
        boolean success = false;
        try {
            employeeNode.replaceChild(newChild, oldChild);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
        }
        assertTrue("throw_HIERARCHY_REQUEST_ERR", success);

    }
}

