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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.EntityReference;
import org.loboevolution.html.node.Node;

import static org.junit.Assert.assertTrue;


/**
 * Using replaceChild on a new EntityReference node attempt to replace an EntityReference node with
 * its Element parent, with itself and vice versa verify if a NO_MODIFICATION_ALLOWED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild22Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        EntityReference entRefMain;
        EntityReference entRef;
        Element elem;
        Node appendedChild;
        Node replacedChild;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:p");
        entRefMain = doc.createEntityReference("delta");
        entRef = doc.createEntityReference("beta");
        appendedChild = elem.appendChild(entRef);

        {
            boolean success = false;
            try {
                replacedChild = entRefMain.replaceChild(elem, entRef);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_1", success);
        }

        {
            boolean success = false;
            try {
                replacedChild = entRefMain.replaceChild(entRef, elem);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_2", success);
        }

        {
            boolean success = false;
            try {
                replacedChild = entRefMain.replaceChild(entRefMain, entRef);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_3", success);
        }
    }
}

