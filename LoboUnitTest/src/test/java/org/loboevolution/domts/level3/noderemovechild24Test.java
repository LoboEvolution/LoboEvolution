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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Using removeChild on an Entity node attempt to remove a Text child
 * and verify if a NO_MODIFICATION_ALLOWED_ERR gets thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-1734834066</a>
 */
public class noderemovechild24Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        DocumentType docType;
        NamedNodeMap entitiesMap;
        Node alphaEntity;
        Text alphaText;
        Text removed;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();
        entitiesMap = docType.getEntities();
        alphaEntity = entitiesMap.getNamedItem("alpha");
        assertNotNull("alphaEntityNotNull", alphaEntity);
        alphaText = (Text) alphaEntity.getFirstChild();
        assertNotNull("alphaTextNotNull", alphaText);

        {
            boolean success = false;
            try {
                removed = (Text) alphaEntity.removeChild(alphaText);
            } catch (DOMException ex) {
                success = (ex.getCode() == DOMException.NO_MODIFICATION_ALLOWED_ERR);
            }
            assertTrue("throw_NO_MODIFICATION_ALLOWED_ERR", success);
        }
    }
}

