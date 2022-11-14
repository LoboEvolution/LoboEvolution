
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

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * The method setAttributeNS adds a new attribute and raises a NAMESPACE_ERR if the
 * qualifiedName has a prefix and the namespaceURI is null.
 * Invoke the setAttributeNS method on a new Element object with null namespaceURI and a
 * qualifiedName that has a namespace prefix.  Check if the NAMESPACE_ERR was thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElSetAttrNS</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=259">http://www.w3.org/Bugs/Public/show_bug.cgi?id=259</a>
 */
public class elementsetattributens05Test extends LoboUnitTest {


    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc = sampleXmlFile("staffNS.xml");
        Element element = doc.createElementNS("http://www.w3.org/DOM/Test/L2", "dom:elem");
        boolean success = false;
        try {
            element.setAttributeNS(null, "dom:root", "test");
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NAMESPACE_ERR);
        }
        assertTrue("elementsetattributens05", success);
    }
}

