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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Element;

import static org.junit.Assert.assertTrue;


/**
 * Using replaceChild on a DocumentFragment node attempt to replace an Element node with
 * an Attr Node and verify if a HIERARCHY_REQUEST_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild20Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentFragment docFrag;
        Attr attr;
        Element elem;
        doc = sampleXmlFile("hc_staff.xml");
        docFrag = doc.createDocumentFragment();
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:p");
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang");
        docFrag.appendChild(elem);

        boolean success = false;
        try {
            docFrag.replaceChild(attr, elem);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
        }
        assertTrue("throw_HIERARCHY_REQUEST_ERR", success);
    }
}