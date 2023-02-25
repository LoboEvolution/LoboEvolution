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

import static org.junit.Assert.assertTrue;


/**
 * Retreive the documentURI of a document for which standalone was specified as "yes", this
 * should return true.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-standalone">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-standalone</a>
 */
public class documentgetxmlstandalone04Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        boolean standalone;
        doc = sampleXmlFile("barfoo_standalone_yes.xml");
        standalone = doc.getXmlStandalone();
        assertTrue("documentgetxmlstandalone04", standalone);
    }
}

