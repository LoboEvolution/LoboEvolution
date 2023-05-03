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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Using insertBefore on a child Element of an EntityReference node attempt to insert
 * a new Element node, before a Text node child of an Entity Node's replacement
 * text and verify if a NO_MODIFICATION_ALLOWED_ERR is raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore25Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element element;
        EntityReference eRef;
        Element span;
        Text spanText;
        Element newNode;
        HTMLCollection childList;
        Node inserted;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("var");
        element = (Element) childList.item(2);
        eRef = (EntityReference) element.getFirstChild();
        span = (Element) eRef.getFirstChild();
        assertNotNull("spanNotNull", span);
        spanText = (Text) span.getFirstChild();
        assertNotNull("spanTextNotNull", spanText);
        newNode = doc.createElementNS("http://www.w3.org/1999/xhtml", "span");

        {
            boolean success = false;
            try {
                inserted = span.insertBefore(newNode, spanText);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

