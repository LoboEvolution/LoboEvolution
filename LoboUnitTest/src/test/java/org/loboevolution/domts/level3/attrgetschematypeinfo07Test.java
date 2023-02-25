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
 * The getSchemaTypeInfo method retrieves the type information associated with this attribute.
 * Load a valid document with an XML Schema.
 * Invoke getSchemaTypeInfo method on an attribute having [type definition] property.  Expose {name} and {target namespace}
 * properties of the [type definition] property.  Verity that the typeName and typeNamespace of the title attribute's
 * schemaTypeInfo are correct. getSchemaTypeInfo on the 'id' attribute of the fourth 'acronym' element
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-schemaTypeInfo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-schemaTypeInfo</a>
 */
public class attrgetschematypeinfo07Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element acElem;
        Attr attr;
        TypeInfo attrTypeInfo;
        String typeName;
        String typeNamespace;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        acElem = (Element) elemList.item(3);
        attr = acElem.getAttributeNode("id");
        attrTypeInfo = attr.getSchemaTypeInfo();
        assertNotNull("typeInfoNotNull", attrTypeInfo);
        typeName = attrTypeInfo.getTypeName();
        typeNamespace = attrTypeInfo.getTypeNamespace();
        assertEquals("attrgetschematypeinfo07_typeName", "ID", typeName);
        assertEquals("attrgetschematypeinfo07_typeNamespace", "http://www.w3.org/2001/XMLSchema", typeNamespace);
    }
}

