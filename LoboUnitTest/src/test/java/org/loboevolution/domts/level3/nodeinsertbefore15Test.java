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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;


/**
 * A NO_MODIFICATION_ALLOWED_ERR is raised if the node is read-only.
 * <p>
 * Using insertBefore on a new EntityReference node attempt to insert Element, Text,
 * Comment, ProcessingInstruction and CDATASection nodes before an element child
 * and verify if a NO_MODIFICATION_ALLOWED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-952280727</a>
 */
public class nodeinsertbefore15Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        EntityReference entRef;
        Node elemChild;
        Text txt;
        Element elem;
        Comment comment;
        ProcessingInstruction pi;
        CDATASection cdata;
        Node inserted;
        doc = sampleXmlFile("hc_staff.xml");
        entRef = doc.createEntityReference("delta");
        elemChild = entRef.getFirstChild();
        cdata = doc.createCDATASection("CDATASection");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(cdata, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_1", success);
        }
        pi = doc.createProcessingInstruction("target", "data");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(pi, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_2", success);
        }
        comment = doc.createComment("Comment");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(comment, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_3", success);
        }
        txt = doc.createTextNode("Text");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(txt, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_4", success);
        }
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "body");

        {
            boolean success = false;
            try {
                inserted = entRef.insertBefore(elem, elemChild);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_5", success);
        }
    }
}

