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
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.UserDataHandlerImpl;
import org.loboevolution.html.dom.nodeimpl.UserDataNotification;
import org.loboevolution.html.node.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


/**
 * Call setUserData on a node providing a UserDataHandler and import the node.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-handleUserDataEvent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-handleUserDataEvent</a>
 */
public class userdatahandler03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Node node;
        HTMLCollection pList;
        UserDataHandlerImpl userDataHandlerImpl = new UserDataHandlerImpl();

        Object oldUserData;
        String elementNS;
        Node newNode;
        List<UserDataNotification> notifications;

        UserDataNotification notification;
        short operation;
        String key;
        String data;
        Node src;
        Node dst;
        int greetingCount = 0;
        int salutationCount = 0;
        String hello = "Hello";
        String mister = "Mr.";
        Document newDoc;
        String rootName;
        String rootNS;
        DOMImplementation domImpl;
        DocumentType docType = null;

        Element docElem;
        doc = sampleXmlFile("barfoo.xml");
        domImpl = doc.getImplementation();
        docElem = doc.getDocumentElement();
        rootNS = docElem.getNamespaceURI();
        rootName = docElem.getTagName();
        newDoc = domImpl.createDocument(rootNS, rootName, docType);
        pList = doc.getElementsByTagName("p");
        node = pList.item(0);
        oldUserData = node.setUserData("greeting", hello, userDataHandlerImpl);
        oldUserData = node.setUserData("salutation", mister, userDataHandlerImpl);
        elementNS = node.getNamespaceURI();
        newNode = doc.importNode(node, true);
        notifications = userDataHandlerImpl.getAllNotifications();
        assertEquals("twoNotifications", 2, notifications.size());
        for (UserDataNotification notification1 : notifications) {
            operation = notification1.getOperation();
            assertEquals("operationIsImport", 2, operation);
            key = notification1.getKey();
            data = (String) notification1.getData();

            if ("greeting".equals(key)) {
                assertEquals("greetingDataHello", hello, data);
                greetingCount += 1;
            } else {
                assertEquals("saluationKey", "salutation", key);
                assertEquals("salutationDataMr", mister, data);
                salutationCount += 1;
            }

            src = notification1.getSrc();
            assertSame("srcIsNode", node, src);
            dst = notification1.getDst();
            assertSame("dstIsNewNode", newNode, dst);
        }
        assertEquals("greetingCountIs1", 1, greetingCount);
        assertEquals("salutationCountIs1", 1, salutationCount);
    }
}