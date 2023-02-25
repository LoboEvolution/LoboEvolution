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
import org.loboevolution.html.node.ProcessingInstruction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Using isEqualNode check if 2 new ProcessingInstruction nodes having the same data are equal and two others
 * having different data are not equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-isEqualNode</a>
 */
public class nodeisequalnode32Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        ProcessingInstruction pi1;
        ProcessingInstruction pi2;
        ProcessingInstruction pi3;
        boolean isEqual;
        doc = sampleXmlFile("hc_staff.xml");
        pi1 = doc.createProcessingInstruction("Target1", "pi");
        pi2 = doc.createProcessingInstruction("Target1", "pi");
        pi3 = doc.createProcessingInstruction("Target1", "#ProcessingInstruction");
        isEqual = pi1.isEqualNode(pi2);
        assertTrue("nodeisequalnodeTrue29", isEqual);
        isEqual = pi1.isEqualNode(pi3);
        assertFalse("nodeisequalnodeFalse29", isEqual);
    }
}

