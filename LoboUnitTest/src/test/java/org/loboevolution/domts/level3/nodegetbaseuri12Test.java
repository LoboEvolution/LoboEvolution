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
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;


/**
 * Using getBaseURI verify if the entity epsilon is absolute
 * and matches the URL of the document entity.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-baseURI</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=419">http://www.w3.org/Bugs/Public/show_bug.cgi?id=419</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2Entity">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/infoset-mapping#Infoset2Entity</a>
 */
public class nodegetbaseuri12Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entitiesMap;
        Node entity;
        String baseURI;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entitiesMap = docType.getEntities();
        entity = entitiesMap.getNamedItem("epsilon");
        baseURI = entity.getBaseURI();
        assertURIEquals("entityBase", null, null, null, null, "hc_staff", null, null, Boolean.TRUE, baseURI);
    }
}

