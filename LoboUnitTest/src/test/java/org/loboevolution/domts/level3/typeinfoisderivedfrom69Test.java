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

import static org.junit.Assert.assertFalse;

/**
 * Checks that isDerivedFrom(...,DERIVATION_UNION|DERIVATION_LIST) returns false when there
 * is both a union and list derivation steps between the target and
 * ancestor type.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-isDerivedFrom">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-isDerivedFrom</a>
 */
public class typeinfoisderivedfrom69Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element elem;
        TypeInfo elemTypeInfo;
        String typeName;
        HTMLCollection elemList;
        boolean retValue;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("sup");
        elem = (Element) elemList.item(0);
        elemTypeInfo = elem.getSchemaTypeInfo();
        retValue = elemTypeInfo.isDerivedFrom("http://www.w3.org/2001/XMLSchema", "integer", 12);
        assertFalse("isDerived", retValue);
    }
}

