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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.TypeInfo;

import static org.junit.Assert.*;


/**
 * Check if emType is derived from itself.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-isDerivedFrom">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-isDerivedFrom</a>
 */
public class typeinfoisderivedfrom21Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element elem;
        TypeInfo typeInfo;
        boolean isDerived;
        String typeName;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("em");
        elem = (Element) elemList.item(0);
        typeInfo = elem.getSchemaTypeInfo();
        assertNotNull("typeInfoNotNull", typeInfo);
        typeName = typeInfo.getTypeName();
        assertEquals("typeName", "emType", typeName);
        isDerived = typeInfo.isDerivedFrom("http://www.w3.org/1999/xhtml", "emType", 1);
        assertTrue("derivedFromSelfByRestriction", isDerived);
        isDerived = typeInfo.isDerivedFrom("http://www.w3.org/1999/xhtml", "emType", 14);
        assertFalse("notDerivedFromSelfOtherMethod", isDerived);
        isDerived = typeInfo.isDerivedFrom("http://www.w3.org/1999/xhtml", "emType", 0);
        assertTrue("derivedFromSelfByAny", isDerived);
        isDerived = typeInfo.isDerivedFrom("http://www.w3.org/1999/xhtml", "emType", 15);
        assertTrue("derivedFromSelfByAll", isDerived);
    }
}

