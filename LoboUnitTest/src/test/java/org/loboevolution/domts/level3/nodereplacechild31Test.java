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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Using replaceChild on an Element node that is the replacement Text of an EntityReference
 * node, attempt to replace its Text child node with a new Element node and verify if
 * a NO_MODIFICATION_ALLOWED_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild31Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection childList;
        Element elem;
        Element span;
        EntityReference ent4Ref;
        Text spanText;
        Element newChild;
        Node replaced;
        doc = sampleXmlFile("hc_staff.xml");
        childList = doc.getElementsByTagName("var");
        elem = (Element) childList.item(2);
        ent4Ref = (EntityReference) elem.getFirstChild();
        span = (Element) ent4Ref.getFirstChild();
        assertNotNull("spanNotNull", span);
        spanText = (Text) span.getFirstChild();
        assertNotNull("spanTextNotNull", spanText);
        newChild = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:p");

        {
            boolean success = false;
            try {
                replaced = span.replaceChild(newChild, spanText);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

