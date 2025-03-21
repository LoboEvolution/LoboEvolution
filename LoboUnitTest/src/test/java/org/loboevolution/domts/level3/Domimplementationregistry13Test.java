/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level3;


import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.nodeimpl.bootstrap.DOMImplementationRegistry;
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.dom.DOMImplementationList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * DOMImplementationRegistry.getDOMImplementationList("cOrE") should return a
 * list of at least one DOMImplementation
 * where hasFeature("Core", null) returns true.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/java-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/ecma-script-binding</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-getDOMImpls</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementationList-item">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementationList-item</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementationList-length">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMImplementationList-length</a>
 */
public class Domimplementationregistry13Test extends LoboUnitTest {
    @Test
    @SneakyThrows
    public void runTest() {

        boolean hasFeature;
        DOMImplementation domImpl;
        final DOMImplementationList domImplList;
        final int length;
        final DOMImplementationRegistry domImplRegistry = DOMImplementationRegistry.newInstance();
        assertNotNull(domImplRegistry, "Domimplementationregistry13Assert1");
        domImplList = domImplRegistry.getDOMImplementationList("cOrE");
        length = domImplList.getLength();
        /*int */
        domImpl = domImplList.item(length);
        assertNull(domImpl, "Domimplementationregistry13Assert2");
        assertNull(null, "Domimplementationregistry13Assert3");
        assertTrue((length > 0), "Domimplementationregistry13Assert4");
        for (int indexN10067 = 0; indexN10067 < domImplList.getLength(); indexN10067++) {
            domImpl = domImplList.item(indexN10067);
            hasFeature = domImpl.hasFeature("Core", null);
            assertTrue(hasFeature, "Domimplementationregistry13Assert5");
        }
    }
}

