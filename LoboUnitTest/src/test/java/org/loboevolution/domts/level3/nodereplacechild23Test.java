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
 * Using replaceChild on a new EntityReference node attempt to replace an Element, Text,
 * Comment, ProcessingInstruction and CDATASection nodes with each other and in each case
 * verify if a NO_MODIFICATION_ALLOWED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild23Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        EntityReference entRef;
        Text txt;
        Element elem;
        Comment comment;
        ProcessingInstruction pi;
        CDATASection cdata;
        Node replaced;
        Node appendedChild;
        doc = sampleXmlFile("hc_staff.xml");
        elem = doc.createElementNS("http://www.w3.org/1999/xhtml", "dom3:p");
        entRef = doc.createEntityReference("delta");
        txt = doc.createTextNode("Text");
        comment = doc.createComment("Comment");
        cdata = doc.createCDATASection("CDATASection");
        pi = doc.createProcessingInstruction("target", "data");
        appendedChild = elem.appendChild(entRef);
        appendedChild = elem.appendChild(txt);
        appendedChild = elem.appendChild(comment);
        appendedChild = elem.appendChild(pi);
        appendedChild = elem.appendChild(cdata);

        {
            boolean success = false;
            try {
                replaced = entRef.replaceChild(cdata, elem);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_1", success);
        }

        {
            boolean success = false;
            try {
                replaced = entRef.replaceChild(pi, cdata);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_2", success);
        }

        {
            boolean success = false;
            try {
                replaced = entRef.replaceChild(comment, pi);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_3", success);
        }

        {
            boolean success = false;
            try {
                replaced = entRef.replaceChild(txt, comment);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_4", success);
        }

        {
            boolean success = false;
            try {
                replaced = entRef.replaceChild(elem, txt);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR_5", success);
        }
    }
}

