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

import static org.junit.Assert.assertNotNull;


/**
 * Invoke getFeature method on this DOMImplementation with the value of the feature parameter
 * as Core and version as "".  This should return a DOMImplementation object that's not null.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementation3-getFeature">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementation3-getFeature</a>
 */
public class domimplementationgetfeature02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DOMImplementation domImpl;
        DOMImplementation domImplReturned;
        doc = sampleXmlFile("hc_staff.xml");
        domImpl = doc.getImplementation();
        domImplReturned = (DOMImplementation) domImpl.getFeature("Core", "");
        assertNotNull("domimplementationgetfeature02", domImplReturned);
    }
}

