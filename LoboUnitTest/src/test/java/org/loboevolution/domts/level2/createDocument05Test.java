
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
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * The "createDocument(namespaceURI,qualifiedName,doctype)" method for a
 * DOMImplementation should raise INVALID_CHARACTER_ERR DOMException
 * if parameter qualifiedName contains an illegal character.
 * <p>
 * Invoke method createDocument(namespaceURI,qualifiedName,doctype) on
 * this domimplementation with namespaceURI equals "http://www.ecommerce.org/schema",
 * doctype is null and qualifiedName contains an illegal character from
 * illegalChars[].  Method should raise INVALID_CHARACTER_ERR DOMException
 * for all characters in illegalChars[].
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#">http://www.w3.org/TR/DOM-Level-2-Core/core#</a>
 */
public class createDocument05Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.ecommerce.org/schema";
        String qualifiedName;
        DocumentImpl doc;
        DocumentType docType = null;

        DOMImplementation domImpl;
        List<String> illegalQNames = new ArrayList<>();
        illegalQNames.add("namespaceURI:{");
        illegalQNames.add("namespaceURI:}");
        illegalQNames.add("namespaceURI:~");
        illegalQNames.add("namespaceURI:'");
        illegalQNames.add("namespaceURI:!");
        illegalQNames.add("namespaceURI:@");
        illegalQNames.add("namespaceURI:#");
        illegalQNames.add("namespaceURI:$");
        illegalQNames.add("namespaceURI:%");
        illegalQNames.add("namespaceURI:^");
        illegalQNames.add("namespaceURI:&");
        illegalQNames.add("namespaceURI:*");
        illegalQNames.add("namespaceURI:(");
        illegalQNames.add("namespaceURI:)");
        illegalQNames.add("namespaceURI:+");
        illegalQNames.add("namespaceURI:=");
        illegalQNames.add("namespaceURI:[");
        illegalQNames.add("namespaceURI:]");
        illegalQNames.add("namespaceURI:\\");
        illegalQNames.add("namespaceURI:/");
        illegalQNames.add("namespaceURI:;");
        illegalQNames.add("namespaceURI:`");
        illegalQNames.add("namespaceURI:<");
        illegalQNames.add("namespaceURI:>");
        illegalQNames.add("namespaceURI:,");
        illegalQNames.add("namespaceURI:a ");
        illegalQNames.add("namespaceURI:\"");

        doc = (DocumentImpl) sampleXmlFile("staffNS.xml");
        doc.setTest(true);
        for (int indexN1009A = 0; indexN1009A < illegalQNames.size(); indexN1009A++) {
            qualifiedName = illegalQNames.get(indexN1009A);
            domImpl = doc.getImplementation();

            {
                boolean success = false;
                try {
                    domImpl.createDocument(namespaceURI, qualifiedName, docType);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
                }
                assertTrue("throw_INVALID_CHARACTER_ERR", success);
            }
        }
    }
}

