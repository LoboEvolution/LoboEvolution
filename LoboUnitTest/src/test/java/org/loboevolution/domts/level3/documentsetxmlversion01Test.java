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
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.Document;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * Set the value of the version attribute of the XML declaration of this document to
 * various invalid characters and  verify if a NOT_SUPPORTED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-version">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-version</a>
 */
public class documentsetxmlversion01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        String versionValue;
        List<String> illegalVersion = new ArrayList<>();
        illegalVersion.add("{");
        illegalVersion.add("}");
        illegalVersion.add("~");
        illegalVersion.add("'");
        illegalVersion.add("!");
        illegalVersion.add("@");
        illegalVersion.add("#");
        illegalVersion.add("$");
        illegalVersion.add("%");
        illegalVersion.add("^");
        illegalVersion.add("&");
        illegalVersion.add("*");
        illegalVersion.add("(");
        illegalVersion.add(")");
        illegalVersion.add("+");
        illegalVersion.add("=");
        illegalVersion.add("[");
        illegalVersion.add("]");
        illegalVersion.add("\\");
        illegalVersion.add("/");
        illegalVersion.add(";");
        illegalVersion.add("`");
        illegalVersion.add("<");
        illegalVersion.add(">");
        illegalVersion.add(",");
        illegalVersion.add("a ");
        illegalVersion.add("\"");
        illegalVersion.add("---");

        doc = sampleXmlFile("hc_staff.xml");
        for (int indexN10087 = 0; indexN10087 < illegalVersion.size(); indexN10087++) {
            versionValue = illegalVersion.get(indexN10087);

            {
                boolean success = false;
                try {
                    doc.setXmlVersion(versionValue);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue("NOT_SUPPORTED_ERR_documentsetversion01", success);
            }
        }
    }
}

