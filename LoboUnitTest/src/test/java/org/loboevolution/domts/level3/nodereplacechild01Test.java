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

package org.loboevolution.domts.level3;


import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

/**
 * The method replaceChild replaces the child node oldChild with newChild in the list of
 * children, and returns the oldChild node.
 * <p>
 * Using replaceChild on this Document node attempt to replace this Document node with itself
 * and verify if a HIERARCHY_REQUEST_ERR error or a NOT_FOUND_ERR (since oldChild
 * is not a child of this node) is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Node replaced;
        doc = sampleXmlFile("hc_staff.xml");

        try {
            replaced = doc.replaceChild(doc, doc);

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 8:
                    break;
                case 3:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

