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
import org.loboevolution.html.node.*;

import static org.junit.Assert.fail;


/**
 * Attempt to add a second document element by a comment.  The attempt should result
 * in a HIERARCHY_REQUEST_ERR or NOT_SUPPORTED_ERR.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild40Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        String rootName;
        String publicId = null;

        String systemId = null;

        Comment newComment;
        DocumentType newDocType;
        DOMImplementation domImpl;
        Node retNode;
        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(rootName, publicId, systemId);
        newComment = doc.createComment("second element goes here");
        retNode = doc.insertBefore(newComment, docElem);

        try {
            retNode = doc.replaceChild(newDocType, newComment);
            fail("throw_HIERARCHY_REQUEST_OR_NOT_SUPPORTED");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 3:
                    break;
                case 9:
                    break;
                default:
                    throw ex;
            }
        }
    }
}

