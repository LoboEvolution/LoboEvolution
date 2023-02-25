
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

package org.loboevolution.domts.level2;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.EntityReference;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


/**
 * The method "isSupported(feature,version)" Tests whether the DOM implementation
 * implements a specific feature and that feature is supported by this node.
 * <p>
 * Call the isSupported method specifying empty strings for feature and version on a
 * new EntityReference node.  Check if the value returned value was false.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports">http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports</a>
 */
public class nodeissupported04Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        EntityReference entRef;
        boolean success;
        doc = sampleXmlFile("staffNS.xml");
        entRef = doc.createEntityReference("ent1");
        assertNotNull("createdEntRefNotNull", entRef);
        success = entRef.isSupported("XML CORE", "");
        assertFalse("nodeissupported04", success);
    }
}

