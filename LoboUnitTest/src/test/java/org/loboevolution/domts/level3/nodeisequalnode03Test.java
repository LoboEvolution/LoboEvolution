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


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * Using isEqualNode check if 2 Document nodes created by parsing
 * documents only differing in declared encoding return false for isEqualNode on
 * the document and true on the document element.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=528">http://www.w3.org/Bugs/Public/show_bug.cgi?id=528</a>
 */
public class nodeisequalnode03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc1;
        Document doc2;
        Element docElem1;
        Element docElem2;
        boolean isEqual;
        doc1 = sampleXmlFile("barfoo_utf8.xml");
        doc2 = sampleXmlFile("barfoo_utf16.xml");
        isEqual = doc1.isEqualNode(doc2);
        assertTrue("docAreNotEquals", isEqual);
        docElem1 = doc1.getDocumentElement();
        docElem2 = doc2.getDocumentElement();
        isEqual = docElem1.isEqualNode(docElem2);
        assertTrue("docElemsAreEquals", isEqual);
    }
}

