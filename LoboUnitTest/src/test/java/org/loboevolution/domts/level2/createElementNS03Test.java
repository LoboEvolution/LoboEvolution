
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

