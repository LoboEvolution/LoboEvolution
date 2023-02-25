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

import static org.junit.Assert.assertTrue;


/**
 * Appends an entity reference containing text and an element to an existing
 * text node, then calls Text.replaceWholeText on the existing text node.
 * A NO_MODIFICATION_ALLOWED_ERR should be thrown.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=425">http://www.w3.org/Bugs/Public/show_bug.cgi?id=425</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=540">http://www.w3.org/Bugs/Public/show_bug.cgi?id=540</a>
 */
public class textreplacewholetext08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection itemList;
        Element p;
        EntityReference entRef;
        Node node;
        doc = sampleXmlFile("barfoo.xml");
        itemList = doc.getElementsByTagName("p");
        p = (Element) itemList.item(0);
        entRef = doc.createEntityReference("ent2");
        node = p.appendChild(entRef);
        node = p.getFirstChild();

        {
            boolean success = false;
            try {
                ((Text) node).replaceWholeText("yo");
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

