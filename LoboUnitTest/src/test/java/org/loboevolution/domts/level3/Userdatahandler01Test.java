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


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.UserDataHandlerImpl;
import org.loboevolution.html.dom.nodeimpl.UserDataNotification;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


/**
 * Call setUserData on a node providing a UserDataHandler and rename the node.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-handleUserDataEvent">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-handleUserDataEvent</a>
 */
public class Userdatahandler01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Node node;
        final HTMLCollection pList;
        final UserDataHandlerImpl userDataHandlerImpl = new UserDataHandlerImpl();

        final String elementNS;
        final Node newNode;
        final List<UserDataNotification> notifications;

        short operation;
        String key;
        String data;
        Node src;
        Node dst;
        int greetingCount = 0;
        int salutationCount = 0;
        final String hello = "Hello";
        final String mister = "Mr.";
        doc = sampleXmlFile("barfoo.xml");
        pList = doc.getElementsByTagName("p");
        node = pList.item(0);
        /*UserDataHandlerImpl */
        /*DOMString */
        node.setUserData("greeting", hello, userDataHandlerImpl);
        /*UserDataHandlerImpl */
        /*DOMString */
        node.setUserData("salutation", mister, userDataHandlerImpl);
        elementNS = node.getNamespaceURI();
        newNode = doc.renameNode(node, elementNS, "div");
        notifications = userDataHandlerImpl.getAllNotifications();
        assertEquals(2, notifications.size(), "Userdatahandler01Assert3");
        for (final UserDataNotification userDataNotification : notifications) {
            operation = userDataNotification.getOperation();
            assertEquals(4, operation, "Userdatahandler01Assert4");
            key = userDataNotification.getKey();
            data = (String) userDataNotification.getData();

            if ("greeting".equals(key)) {
                assertEquals(hello, data, "Userdatahandler01Assert5");
                greetingCount += 1;
            } else {
                assertEquals("salutation", key, "Userdatahandler01Assert6");
                assertEquals(mister, data, "Userdatahandler01Assert7");
                salutationCount += 1;
            }

            src = userDataNotification.getSrc();
            assertSame(node, src, "Userdatahandler01Assert8");
            dst = userDataNotification.getDst();

            if ((dst == null)) {
                assertSame(node, newNode, "Userdatahandler01Assert9");
            } else {
                assertSame(newNode, dst, "Userdatahandler01Assert10");
            }

        }
        assertEquals(1, greetingCount, "Userdatahandler01Assert11");
        assertEquals(1, salutationCount, "Userdatahandler01Assert12");
    }
}

