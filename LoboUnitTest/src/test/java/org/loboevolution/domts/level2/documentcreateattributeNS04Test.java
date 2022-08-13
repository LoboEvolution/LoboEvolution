
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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.fail;


/**
 * The method createAttributeNS raises a NAMESPACE_ERR if the specified qualified name
 * is malformed.
 * <p>
 * Invoke the createAttributeNS method on this Document object with a valid value for
 * namespaceURI, and malformed qualifiedNames.  Check if the a NAMESPACE_ERR was thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core">http://www.w3.org/TR/DOM-Level-2-Core/core</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrAttrNS</a>
 */
public class documentcreateattributeNS04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        Attr attribute;
        String namespaceURI = "http://www.w3.org/DOM/Test/Level2";
        String qualifiedName;
        java.util.List qualifiedNames = new java.util.ArrayList();
        qualifiedNames.add("_:");
        qualifiedNames.add(":0a");
        qualifiedNames.add(":");
        qualifiedNames.add("a:b:c");
        qualifiedNames.add("_::a");

        doc = sampleXmlFile("staffNS.xml");
        for (int indexN1004E = 0; indexN1004E < qualifiedNames.size(); indexN1004E++) {
            qualifiedName = (String) qualifiedNames.get(indexN1004E);

            {
                // BEGIN Android-changed
                //     Our exception priorities differ from the spec
                try {
                    attribute = doc.createAttributeNS(namespaceURI, qualifiedName);
                    fail("documentcreateattributeNS04");
                } catch (DOMException expected) {
                }
                // END Android-changed
            }
        }
    }
}

