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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * An attempt to add a second doctype node should result in a HIERARCHY_REQUEST_ERR
 * or a NOT_SUPPORTED_ERR.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-184E7107">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-184E7107</a>
 */
public class nodeappendchild01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        DocumentType docType;
        String nullPubId = null;
        String nullSysId = null;
        String tagName;
        Element docElem;
        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        tagName = docElem.getTagName();
        domImpl = doc.getImplementation();
        docType = domImpl.createDocumentType(tagName, nullPubId, nullSysId);

        boolean success = false;
        try {
            doc.appendChild(docType);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
        }
        assertTrue("throw_HIERARCHY_REQUEST_ERR", success);
    }
}

