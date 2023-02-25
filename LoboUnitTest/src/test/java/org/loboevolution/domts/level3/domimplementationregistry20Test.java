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
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.dom.nodeimpl.bootstrap.DOMImplementationRegistry;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DOMImplementationList;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;


/**
 * DOMImplementationRegistry.getDOMImplementationList("HTML") should return
 * an empty list or a list of DOMImplementation
 * where hasFeature("HTML", null) returns true.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls</a>
 */
public class domimplementationregistry20Test extends LoboUnitTest {
    @Test
    @SneakyThrows
    public void runTest() {
       
        DOMImplementation domImpl;
        boolean hasFeature;
        DOMImplementation baseImpl;
        String nullVersion = null;

        DOMImplementationList domImplList;
        int length;
         DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull("domImplRegistryNotNull", domImplRegistry);
        domImplList = domImplRegistry.getDOMImplementationList("HTML");
        length = (int) domImplList.getLength();

        if (length == 0) {
            baseImpl = new DOMImplementationImpl(new UserAgentContext(true));
            hasFeature = baseImpl.hasFeature("HTML", nullVersion);
            assertFalse("baseImplSupportsHTML", hasFeature);
        } else {
            for (int indexN10068 = 0; indexN10068 < domImplList.getLength(); indexN10068++) {
                domImpl = (DOMImplementation) domImplList.item(indexN10068);
                hasFeature = domImpl.hasFeature("HTML", nullVersion);
                assertTrue("hasCore", hasFeature);
            }
        }

    }
}

