
/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.domts.level2;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DOMImplementation;

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
        Document doc;

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

        doc = sampleXmlFile("staffNS.xml");
        
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

