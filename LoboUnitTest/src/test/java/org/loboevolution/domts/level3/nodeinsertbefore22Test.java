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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * Using insertBefore on an Element node attempt to insert the ancestor of an Element node
 * before its child and verify if a HIERARCHY_REQUEST_ERR is raised.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore22Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element element;
        Element refNode;
        Element ancestor;
        HTMLCollection childList;
        Node appendedChild;
        Node inserted;
        doc = sampleXmlFile("barfoo.xml");
        element = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:body");
        refNode = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:a");
        ancestor = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:p");
        appendedChild = element.appendChild(refNode);
        appendedChild = ancestor.appendChild(element);

        {
            boolean success = false;
            try {
                inserted = element.insertBefore(ancestor, refNode);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
            }
            assertTrue("throw_HIERARCHY_REQUEST_ERR", success);
        }
    }
}

