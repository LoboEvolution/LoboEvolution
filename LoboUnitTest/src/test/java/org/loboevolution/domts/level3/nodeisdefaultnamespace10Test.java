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
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Using isDefaultNamespace on a new Child of a new Element node with a namespace URI
 * and prefix and using the childs namespace URI as an argument, verify if the
 * value returned is true.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isDefaultNamespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isDefaultNamespace</a>
 */
public class nodeisdefaultnamespace10Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element parent;
        Element child;
        boolean isDefault;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        parent = doc.createElementNS("http://www.w3.org/1999/xhtml", "xhtml:body");
        child = doc.createElementNS("http://www.w3.org/1999/xhtml", "p");
        appendedChild = parent.appendChild(child);
        isDefault = child.isDefaultNamespace("http://www.w3.org/1999/xhtml");
        assertTrue("nodeisdefaultnamespace10_1", isDefault);
        isDefault = parent.isDefaultNamespace("http://www.w3.org/1999/xhtml");
        assertFalse("nodeisdefaultnamespace10_2", isDefault);
    }
}

