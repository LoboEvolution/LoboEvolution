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
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.fail;


/**
 * Using replaceChild on this Document node attempt to replace this DocumentElement node
 * with  a new element that was created in another document and verify if a
 * WRONG_DOCUMENT_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document doc2;
        Element docElem;
        Element elem;
        String nodeName;
        Node replaced;
        String rootNS;
        String rootName;
        DOMImplementation domImpl;
        DocumentType nullDocType = null;

        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        rootNS = docElem.getNamespaceURI();
        domImpl = new DOMImplementationImpl(new UserAgentContext(true));
        doc2 = domImpl.createDocument(rootNS, rootName, nullDocType);
        elem = doc2.createElementNS(rootNS, rootName);

        try {
            replaced = doc.replaceChild(elem, docElem);
            fail("throw_WRONG_DOCUMENT_OR_NOT_SUPPORTED");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 4:
                    break;
                case 9:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

