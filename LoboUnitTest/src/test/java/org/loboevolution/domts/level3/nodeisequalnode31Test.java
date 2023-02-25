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
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Document;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Using isEqualNode check if 2 new CDATASection nodes having the same data are equal and two others
 * having different data are not equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class nodeisequalnode31Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        CDATASection cdata1;
        CDATASection cdata2;
        CDATASection cdata3;
        boolean isEqual;
        doc = sampleXmlFile("hc_staff.xml");
        cdata1 = doc.createCDATASection("cdata");
        cdata2 = doc.createCDATASection("cdata");
        cdata3 = doc.createCDATASection("#CDATASection");
        isEqual = cdata1.isEqualNode(cdata2);
        assertTrue("nodeisequalnodeTrue29", isEqual);
        isEqual = cdata1.isEqualNode(cdata3);
        assertFalse("nodeisequalnodeFalse29", isEqual);
    }
}

