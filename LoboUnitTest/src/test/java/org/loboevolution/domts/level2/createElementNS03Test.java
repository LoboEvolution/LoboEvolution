
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
import org.loboevolution.html.node.Element;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * The "createElementNS(namespaceURI,qualifiedName)" method for a
 * Document should raise INVALID_CHARACTER_ERR DOMException if
 * qualifiedName contains an illegal character.
 * <p>
 * Invoke method createElementNS(namespaceURI,qualifiedName) on this document
 * with qualifiedName containing an illegal character from illegalChars[].
 * Method should raise INVALID_CHARACTER_ERR DOMException for all characters
 * in illegalChars[].
 *
 * @author NIST
 * @author Mary Brady
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-DocCrElNS</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-DocCrElNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])">http://www.w3.org/TR/DOM-Level-2-Core/core#xpointer(id('ID-DocCrElNS')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])</a>
 */
public class createElementNS03Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        String namespaceURI = "http://www.wedding.com/";
        String qualifiedName;
        Document doc;
        boolean done;
        Element newElement;
        String charact;
        List<String> illegalQNames = new ArrayList<>();
        illegalQNames.add("person:{");
        illegalQNames.add("person:}");
        illegalQNames.add("person:~");
        illegalQNames.add("person:'");
        illegalQNames.add("person:!");
        illegalQNames.add("person:@");
        illegalQNames.add("person:#");
        illegalQNames.add("person:$");
        illegalQNames.add("person:%");
        illegalQNames.add("person:^");
        illegalQNames.add("person:&");
        illegalQNames.add("person:*");
        illegalQNames.add("person:(");
        illegalQNames.add("person:)");
        illegalQNames.add("person:+");
        illegalQNames.add("person:=");
        illegalQNames.add("person:[");
        illegalQNames.add("person:]");
        illegalQNames.add("person:\\");
        illegalQNames.add("person:/");
        illegalQNames.add("person:;");
        illegalQNames.add("person:`");
        illegalQNames.add("person:<");
        illegalQNames.add("person:>");
        illegalQNames.add("person:,");
        illegalQNames.add("person:a ");
        illegalQNames.add("person:\"");

        doc = sampleXmlFile("staffNS.xml");
        for (int indexN10098 = 0; indexN10098 < illegalQNames.size(); indexN10098++) {
            qualifiedName = illegalQNames.get(indexN10098);

            {
                boolean success = false;
                try {
                    newElement = doc.createElementNS(namespaceURI, qualifiedName);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.INVALID_CHARACTER_ERR);
                }
                assertTrue("throw_INVALID_CHARACTER_ERR", success);
            }
        }
    }
}

