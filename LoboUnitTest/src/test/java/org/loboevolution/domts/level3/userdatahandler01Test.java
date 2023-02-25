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
import org.loboevolution.html.dom.UserDataHandler;
import org.loboevolution.html.dom.nodeimpl.UserDataHandlerImpl;
import org.loboevolution.html.dom.nodeimpl.UserDataNotification;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


/**
 * Call setUserData on a node providing a UserDataHandler and rename the node.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-handleUserDataEvent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-handleUserDataEvent</a>
 */
public class userdatahandler01Test extends LoboUnitTest {
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
        doc = sampleXmlFile("barfoo.xml");
        pList = doc.getElementsByTagName("p");
        node = pList.item(0);
        oldUserData = node.setUserData("greeting", ((Object) /*DOMString */hello), ((UserDataHandler) /*UserDataHandlerImpl */userDataHandlerImpl));
        oldUserData = node.setUserData("salutation", ((Object) /*DOMString */mister), ((UserDataHandler) /*UserDataHandlerImpl */userDataHandlerImpl));
        elementNS = node.getNamespaceURI();
        newNode = doc.renameNode(node, elementNS, "div");
        notifications = userDataHandlerImpl.getAllNotifications();
        assertEquals("twoNotifications", 2, notifications.size());
        for (UserDataNotification userDataNotification : notifications) {
            operation = userDataNotification.getOperation();
            assertEquals("operationIsRename", 4, operation);
            key = userDataNotification.getKey();
            data = (String) userDataNotification.getData();

            if ("greeting".equals(key)) {
                assertEquals("greetingDataHello", hello, data);
                greetingCount += 1;
            } else {
                assertEquals("saluationKey", "salutation", key);
                assertEquals("salutationDataMr", mister, data);
                salutationCount += 1;
            }

            src = userDataNotification.getSrc();
            assertSame("srcIsNode", node, src);
            dst = userDataNotification.getDst();

            if ((dst == null)) {
                assertSame("ifDstNullRenameMustReuseNode", node, newNode);
            } else {
                assertSame("dstIsNewNode", newNode, dst);
            }

        }
        assertEquals("greetingCountIs1", 1, greetingCount);
        assertEquals("salutationCountIs1", 1, salutationCount);
    }
}

