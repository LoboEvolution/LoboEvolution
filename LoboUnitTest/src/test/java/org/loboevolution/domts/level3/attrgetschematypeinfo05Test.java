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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.TypeInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Call getSchemaTypeInfo on class attribute for the third acronym element.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-schemaTypeInfo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-schemaTypeInfo</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-typeName">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-typeName</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-typeNamespace">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-typeNamespace</a>
 */
public class attrgetschematypeinfo05Test extends LoboUnitTest {


    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element acronymElem;
        Attr attr;
        TypeInfo typeInfo;
        String typeNS;
        String typeName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        acronymElem = (Element) elemList.item(2);
        attr = acronymElem.getAttributeNode("class");
        typeInfo = attr.getSchemaTypeInfo();
        assertNotNull("typeInfoNotNull", typeInfo);
        typeName = typeInfo.getTypeName();
        assertEquals("nameIsClassType", "classType", typeName);
        typeNS = typeInfo.getTypeNamespace();
        assertEquals("nsIsXHTML", "http://www.w3.org/1999/xhtml", typeNS);
    }
}

