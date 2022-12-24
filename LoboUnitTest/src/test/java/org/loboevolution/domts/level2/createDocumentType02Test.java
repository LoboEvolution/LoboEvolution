
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
 * The "createDocumentType(qualifiedName,publicId,systemId)" method for a
 * DOMImplementation should raise INVALID_CHARACTER_ERR DOMException if
 * qualifiedName contains an illegal character.
 * <p>
 * Invoke method createDocumentType(qualifiedName,publicId,systemId) on
 * this domimplementation with qualifiedName containing an illegal character
 * from illegalChars[]. Method should raise INVALID_CHARACTER_ERR
 * DOMException for all characters in illegalChars[].
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-DOM-createDocType</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('Level-2-Core-DOM-createDocType')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('Level-2-Core-DOM-createDocType')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])</a>
 */
public class createDocumentType02Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String publicId = "http://www.localhost.com/";
        String systemId = "myDoc.dtd";
        DocumentImpl doc;

        DOMImplementation domImpl;
        List<String> illegalQNames = new ArrayList<String>();
        illegalQNames.add("edi:{");
        illegalQNames.add("edi:}");
        illegalQNames.add("edi:~");
        illegalQNames.add("edi:'");
        illegalQNames.add("edi:!");
        illegalQNames.add("edi:@");
        illegalQNames.add("edi:#");
        illegalQNames.add("edi:$");
        illegalQNames.add("edi:%");
        illegalQNames.add("edi:^");
        illegalQNames.add("edi:&");
        illegalQNames.add("edi:*");
        illegalQNames.add("edi:(");
        illegalQNames.add("edi:)");
        illegalQNames.add("edi:+");
        illegalQNames.add("edi:=");
        illegalQNames.add("edi:[");
        illegalQNames.add("edi:]");
        illegalQNames.add("edi:\\");
        illegalQNames.add("edi:/");
        illegalQNames.add("edi:;");
        illegalQNames.add("edi:`");
        illegalQNames.add("edi:<");
        illegalQNames.add("edi:>");
        illegalQNames.add("edi:,");
        illegalQNames.add("edi:a ");
        illegalQNames.add("edi:\"");

        doc = (DocumentImpl) sampleXmlFile("staffNS.xml");
        doc.setTest(true);
        for (String qualifiedName : illegalQNames) {
            domImpl = doc.getImplementation();
            boolean success = false;
            try {
                domImpl.createDocumentType(qualifiedName, publicId, systemId);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
            }
            assertTrue("throw_INVALID_CHARACTER_ERR", success);

        }
    }
}

