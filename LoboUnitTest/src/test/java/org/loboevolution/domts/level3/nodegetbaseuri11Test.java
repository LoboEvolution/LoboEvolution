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
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertEquals;


/**
 * Import a new Processing Instruction of a new Document after the document element.  Using getBaseURI
 * check if the baseURI attribute of the new Processing Instruction node is the same as Document.documentURI.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=419">http://www.w3.org/Bugs/Public/show_bug.cgi?id=419</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2ProcessingInstruction">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2ProcessingInstruction</a>
 */
public class nodegetbaseuri11Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Document newDoc;
        DOMImplementation domImpl;
        ProcessingInstruction newPI;
        ProcessingInstruction imported;
        String baseURI;
        String docURI;
        DocumentType nullDocType = null;

        doc = sampleXmlFile("barfoo_base.xml");
        domImpl = doc.getImplementation();
        newDoc = domImpl.createDocument("http://www.w3.org/1999/xhtml", "html", nullDocType);
        newPI = newDoc.createProcessingInstruction("TARGET", "DATA");
        imported = (ProcessingInstruction) doc.importNode(newPI, true);
        doc.appendChild(imported);
        baseURI = imported.getBaseURI();
        assertURIEquals("equalsBarfooBase", null, null, null, null, "barfoo_base", null, null, Boolean.TRUE, baseURI);
        docURI = doc.getDocumentURI();
        assertEquals("equalsDocURI", docURI, baseURI);
    }
}