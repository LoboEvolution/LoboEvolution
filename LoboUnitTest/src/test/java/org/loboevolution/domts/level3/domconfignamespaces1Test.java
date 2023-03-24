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


import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.gui.LocalHtmlRendererConfig;
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
 * Checks behavior of "namespaces" configuration parameter.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-namespaces</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration</a>
 */
public class domconfignamespaces1Test extends LoboUnitTest {
    @Test
    public void runTest() {
        DOMImplementation domImpl;
        Document doc;
        DOMConfiguration domConfig;
        DocumentType nullDocType = null;

        boolean canSet;
        boolean state;
        String parameter = "nAmEspaces";
        domImpl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), true));
        doc = domImpl.createDocument("http://www.w3.org/1999/xhtml", "html", nullDocType);
        domConfig = doc.getDomConfig();
        state = ((Boolean) domConfig.getParameter(parameter)).booleanValue();
        assertTrue("defaultFalse", state);
        canSet = domConfig.canSetParameter(parameter, Boolean.TRUE);
        assertTrue("canSetTrue", canSet);
        canSet = domConfig.canSetParameter(parameter, Boolean.FALSE);

        if (canSet) {
            domConfig.setParameter(parameter, Boolean.FALSE);
            state = ((Boolean) domConfig.getParameter(parameter)).booleanValue();
            assertFalse("setFalseEffective", state);
        } else {

            {
                boolean success = false;
                try {
                    domConfig.setParameter(parameter, Boolean.FALSE);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue("throw_NOT_SUPPORTED_ERR", success);
            }
            state = ((Boolean) domConfig.getParameter(parameter)).booleanValue();
            assertTrue("setFalseNotEffective", state);
        }

        domConfig.setParameter(parameter, Boolean.TRUE);
    }
}

