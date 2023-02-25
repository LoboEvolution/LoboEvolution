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


import lombok.SneakyThrows;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.nodeimpl.bootstrap.DOMImplementationRegistry;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DOMImplementationList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * DOMImplementationRegistry.getDOMImplementationList("cOrE 3.0") should return
 * a list of DOMImplementation
 * where hasFeature("Core", "3.0") returns true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls</a>
 */
public class domimplementationregistry14Test extends LoboUnitTest {
    @Test
    @SneakyThrows
    public void runTest() {
       
        DOMImplementation domImpl;
        boolean hasFeature;
        DOMImplementationList domImplList;
        int length;
         DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull("domImplRegistryNotNull", domImplRegistry);
        domImplList = domImplRegistry.getDOMImplementationList("cOrE 3.0");
        length = (int) domImplList.getLength();
        assertTrue("atLeastOne", (length > 0));
        for (int indexN10052 = 0; indexN10052 < domImplList.getLength(); indexN10052++) {
            domImpl = (DOMImplementation) domImplList.item(indexN10052);
            hasFeature = domImpl.hasFeature("Core", "3.0");
            assertTrue("hasCore", hasFeature);
        }
    }
}

