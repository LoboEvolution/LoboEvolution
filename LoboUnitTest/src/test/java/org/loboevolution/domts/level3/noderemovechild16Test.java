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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Using removeChild on the first 'p' Element node attempt to remove its 'em'
 * Element child and verify the name of the returned node that was removed.  Now attempt
 * the reverse and verify if a NOT_FOUND_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class noderemovechild16Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection parentList;
        Element parent;
        Element child;
        Element removed;
        String removedName;
        doc = sampleXmlFile("hc_staff.xml");
        parentList = doc.getElementsByTagName("em");
        child = (Element) parentList.item(0);
        parent = (Element) child.getParentNode();
        removed = (Element) parent.removeChild(child);
        removedName = removed.getNodeName();
        assertEquals("noderemovechild16", "em", removedName);

        boolean success = false;
        try {
            child.removeChild(parent);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.NOT_FOUND_ERR);
        }
        assertTrue("NOT_FOUND_ERR_noderemovechild16", success);

    }
}

