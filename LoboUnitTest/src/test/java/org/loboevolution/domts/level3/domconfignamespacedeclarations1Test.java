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
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Checks behavior of "namespace-declarations" configuration parameter.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespace-declarations">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespace-declarations</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter</a>
 */
public class domconfignamespacedeclarations1Test extends LoboUnitTest {
    @Test
    public void runTest() {
        DOMImplementation domImpl;
        Document doc;
        DOMConfiguration domConfig;
        DocumentType nullDocType = null;

        boolean canSet;
        boolean state;
        String parameter = "nAmEspace-declarations";
        domImpl = new DOMImplementationImpl(new UserAgentContext(true));
        doc = domImpl.createDocument("http://www.w3.org/1999/xhtml", "html", nullDocType);
        domConfig = doc.getDomConfig();
        state = (Boolean) domConfig.getParameter(parameter);
        assertTrue("defaultFalse", state);
        canSet = domConfig.canSetParameter(parameter, Boolean.FALSE);
        assertTrue("canSetFalse", canSet);
        canSet = domConfig.canSetParameter(parameter, Boolean.TRUE);
        assertTrue("canSetTrue", canSet);
        domConfig.setParameter(parameter, Boolean.FALSE);
        state = (Boolean) domConfig.getParameter(parameter);
        assertFalse("setFalseEffective", state);
        domConfig.setParameter(parameter, Boolean.TRUE);
        state = (Boolean) domConfig.getParameter(parameter);
        assertTrue("setTrueEffective", state);
    }
}

