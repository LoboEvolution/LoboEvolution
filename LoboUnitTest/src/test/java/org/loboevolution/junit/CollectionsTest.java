/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.junit;

import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.filter.ElementNameFilter;
import org.loboevolution.html.dom.nodeimpl.traversal.NodeIteratorImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.traversal.TreeWalker;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.Assert.*;

/**
 * Compare <code>NodeList</code> and <code>NodeIterator</code> implementations
 * with a reference (JAXP).
 * <p>
 * Iterators cannot be fully compared due to differences in how attributes are
 * handled (see comments in source code).
 */
public class CollectionsTest extends LoboUnitTest {

    @Test
    public void testDocumentElement() throws DOMException {
        Document xmlDoc = sampleXmlFile("entities.xhtml");
        Element docelm = xmlDoc.getDocumentElement();
        NodeList list = docelm.getChildNodes();
        assertEquals(2, list.getLength());
        Node fcNode = docelm.getFirstChild();
        Node lcNode = docelm.getLastChild();
        Element fcElement = docelm.getFirstElementChild();
        Element lcElement = docelm.getLastElementChild();
        assertEquals("HEAD", fcNode.getLocalName());
        assertEquals("HEAD", fcElement.getTagName());
        assertEquals("BODY", lcNode.getLocalName());
        assertEquals("BODY", lcElement.getTagName());
        assertSame(fcNode, fcElement);
        assertSame(lcNode, lcElement);
        assertSame(fcNode, lcElement.getPreviousSibling());
        assertSame(fcNode, lcElement.getPreviousElementSibling());
        assertSame(lcNode, fcElement.getNextSibling());
        assertSame(lcNode, fcElement.getNextElementSibling());
        assertSame(fcNode, list.item(0));
        assertSame(lcNode, list.item(1));
        Element element = fcElement;
        list = element.getChildNodes();
        assertEquals(4, list.getLength());
        fcNode = element.getFirstChild();
        lcNode = element.getLastChild();
        fcElement = element.getFirstElementChild();
        lcElement = element.getLastElementChild();
        assertEquals("TITLE", fcNode.getLocalName());
        assertEquals("TITLE", fcElement.getTagName());
        assertEquals("BASE", lcNode.getLocalName());
        assertEquals("BASE", lcElement.getTagName());
        assertSame(fcNode, fcElement);
        assertSame(lcNode, lcElement);
        assertSame(fcNode, list.item(0));
        assertSame(lcNode, list.item(3));
    }

    @Test
    public void testReference() {
        compareToReferenceDocumentBuilder(true);
    }

    @Test
    public void testReferenceECW() {
        compareToReferenceDocumentBuilder(false);
    }

    private void compareToReferenceDocumentBuilder(boolean ignoreElementContentWhitespace) {
        Document xmlDoc = sampleXmlFile("entities.xhtml");
        DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
        dbFac.setIgnoringElementContentWhitespace(ignoreElementContentWhitespace);
        dbFac.setNamespaceAware(true);
        Document refdoc = sampleXmlFile("entities.xhtml");
        refdoc.setDocumentURI("http://www.example.com/xml/entities.xhtml");
        DocumentType refdoctype = refdoc.getDoctype();
        DocumentType doctype = xmlDoc.getDoctype();
        if (refdoctype == null) {
            assertNull(doctype);
        } else {
            assertEquals(refdoctype.getName(), doctype.getName());
            assertEquals(refdoctype.getSystemId(), doctype.getSystemId());
            assertEquals(refdoctype.getPublicId(), doctype.getPublicId());
        }
        Element refde = refdoc.getDocumentElement();
        Element de = xmlDoc.getDocumentElement();
        Node head = refdoc.getDocumentElement().getElementsByTagName("head").item(0);
        Element first = xmlDoc.getDocumentElement().getFirstElementChild();
        compareNodes(refde, de);
        compareNodeIterators(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ALL, null);
        compareNodeIterators(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ELEMENT, null);
        compareNodeIterators(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ELEMENT | NodeFilter.SHOW_TEXT, null);
        compareNodeIterators(refdoc, head, xmlDoc, first, NodeFilter.SHOW_ALL, null);
        compareNodeIterators(refdoc, head, xmlDoc, first, NodeFilter.SHOW_ELEMENT, null);
        compareNodeIterators(refdoc, head, xmlDoc, first, NodeFilter.SHOW_ELEMENT | NodeFilter.SHOW_TEXT, null);
        compareNodeIterators(refdoc, head, xmlDoc, first, NodeFilter.SHOW_COMMENT, null);
        NodeFilter filter = new ElementNameFilter("ul");
        compareNodeIterators(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_COMMENT, filter);
        compareTreeWalkers(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ALL, null);
        compareTreeWalkers(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ELEMENT, null);
        compareTreeWalkers(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ELEMENT | NodeFilter.SHOW_TEXT, null);
        compareTreeWalkers(refdoc, head, xmlDoc, first, NodeFilter.SHOW_ELEMENT, null);
        compareTreeWalkers(refdoc, head, xmlDoc, first, NodeFilter.SHOW_COMMENT, null);
        compareTreeWalkers(refdoc, refdoc.getElementById("ul1li1"), xmlDoc, xmlDoc.getElementById("ul1li1"), NodeFilter.SHOW_ATTRIBUTE, null);
        compareTreeWalkers(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_COMMENT, filter);
        compareTreeWalkers2(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ALL, null);
        compareTreeWalkers2(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ELEMENT, null);
        compareTreeWalkers2(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ELEMENT | NodeFilter.SHOW_TEXT, null);
        compareTreeWalkers2(refdoc, head, xmlDoc, first, NodeFilter.SHOW_ELEMENT, null);
        compareTreeWalkers2(refdoc, head, xmlDoc, first, NodeFilter.SHOW_COMMENT, null);
        compareTreeWalkers2(refdoc, refdoc.getElementById("ul1li1"), xmlDoc, xmlDoc.getElementById("ul1li1"), NodeFilter.SHOW_ATTRIBUTE, null);
        compareTreeWalkers2(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_COMMENT, filter);
        compareTreeWalkersChild(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ALL);
        compareTreeWalkersChild(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ELEMENT);
        compareTreeWalkersChild(refdoc, refdoc, xmlDoc, xmlDoc, NodeFilter.SHOW_ELEMENT | NodeFilter.SHOW_TEXT);
        compareTreeWalkersChild(refdoc, head, xmlDoc, first, NodeFilter.SHOW_ELEMENT);
    }

    private void compareNodes(Node refde, Node de) {
        compareNodesBasic(refde, de);
        // Attributes
        NamedNodeMap refnnm = refde.getAttributes();
        if (refnnm != null) {
            compareAttributes(refnnm, de.getAttributes());
        }
        // Child nodes
        Node reffcNode = refde.getFirstChild();
        Node reflcNode = refde.getLastChild();
        Node fcNode = de.getFirstChild();
        Node lcNode = de.getLastChild();
        if (reffcNode != null) {
            assertNotNull(fcNode);
            compareNodesBasic(reffcNode, fcNode);
            compareNodesBasic(reflcNode, lcNode);
            NodeList list = de.getChildNodes();
            NodeList reflist = refde.getChildNodes();
            int len = list.getLength();
            assertEquals(reflist.getLength(), len);
            assertSame(fcNode, list.item(0));
            assertSame(lcNode, list.item(len - 1));
            if (de.getNodeType() == Node.ELEMENT_NODE) {
                Element fcElement = ((Element) de).getFirstElementChild();
                Element lcElement = ((Element) de).getLastElementChild();
                if (fcNode.getNodeType() == Node.ELEMENT_NODE) {
                    assertSame(fcNode, fcElement);
                }
                if (lcNode.getNodeType() == Node.ELEMENT_NODE) {
                    assertSame(lcNode, lcElement);
                }
            }
            // List items
            for (int i = 0; i < len; i++) {
                compareNodes(reflist.item(i), list.item(i));
            }
        }
    }

    private void compareNodesBasic(Node refde, Node de) {
        assertEquals(refde.getNodeType(), de.getNodeType());
        assertEquals(refde.getNodeName(), de.getNodeName());
    }

    private void compareAttributes(NamedNodeMap refnnm, NamedNodeMap nnm) {
        assertNotNull(nnm);
        int nnmlen = nnm.getLength();
        for (int i = 0; i < nnmlen; i++) {
            Attr refitem = (Attr) refnnm.item(i);
            Node named = nnm.getNamedItemNS(refitem.getNamespaceURI(), refitem.getLocalName());
            if (refitem.isSpecified() || named != null) {
                assertNotNull(named);
                Node named2 = nnm.getNamedItem(named.getNodeName());
                assertSame(named, named2);
                assertSame(refitem, refnnm.getNamedItem(named.getNodeName()));
                compareNodesBasic(refitem, named);
                if (!"style".equalsIgnoreCase(refitem.getNodeName())) {
                    assertEquals(refitem.getNodeValue(), named.getNodeValue());
                }
            }
        }
    }

    private void compareNodeIterators(Document refDoc, Node refroot, Document doc, Node root, int whatToShow, NodeFilter filter) {


        NodeIteratorImpl refit = (NodeIteratorImpl) refDoc.createNodeIterator(refroot, whatToShow, filter);
        NodeIteratorImpl nodeIterator = (NodeIteratorImpl) doc.createNodeIterator(root, whatToShow, filter);
        Iterator<Node> it = nodeIterator.iterator();
        ListIterator<Node> listIterator = nodeIterator.listIterator();

        while (it.hasNext()) {
            Node node = it.next();
            Node refnode = refit.nextNode();
            assertNotNull(refnode);
            compareNodesBasic(refnode, node);
        }

        while (listIterator.hasPrevious()) {
            Node node = listIterator.previous();
            Node refnode = refit.previousNode();
            assertNotNull(refnode);
            compareNodesBasic(refnode, node);
        }
        assertNull(refit.previousNode());
    }

    private void compareTreeWalkers(Document refDoc, Node refroot, Document doc, Node root,
                                    int whatToShow, NodeFilter filter) {
        TreeWalker reftw = refDoc.createTreeWalker(refroot, whatToShow, filter);
        TreeWalker tw = doc.createTreeWalker(root, whatToShow, filter);
        Node node;
        while ((node = tw.nextNode()) != null) {
            Node refnode = reftw.nextNode();
            assertNotNull(refnode);
            compareNodesBasic(refnode, node);
        }
        assertNull(reftw.nextNode());
        while ((node = tw.previousNode()) != null) {
            Node refnode = reftw.previousNode();
            assertNotNull(refnode);
            compareNodesBasic(refnode, node);
        }
        assertNull(reftw.previousNode());
    }

    private void compareTreeWalkers2(Document refDoc, Node refroot, Document doc, Node root,
                                     int whatToShow, NodeFilter filter) {
        TreeWalker reftw = refDoc.createTreeWalker(refroot, whatToShow, filter);
        TreeWalker tw = doc.createTreeWalker(root, whatToShow, filter);
        Node node;
        while ((node = tw.nextNode()) != null) {
            Node refnode = reftw.nextNode();
            assertNotNull(refnode);
            compareNodesBasic(refnode, node);
            compareSiblings(reftw, tw);
            tw.setCurrentNode(node);
            reftw.setCurrentNode(refnode);
        }
        assertNull(reftw.nextNode());
        while ((node = tw.previousNode()) != null) {
            Node refnode = reftw.previousNode();
            assertNotNull(refnode);
            compareNodesBasic(refnode, node);
            compareSiblings(reftw, tw);
            tw.setCurrentNode(node);
            reftw.setCurrentNode(refnode);
        }
        assertNull(reftw.previousNode());
    }

    private void compareSiblings(TreeWalker reftw, TreeWalker tw) {
        Node sibling, refsibling;
        while ((sibling = tw.nextSibling()) != null) {
            refsibling = reftw.nextSibling();
            assertNotNull(refsibling);
            compareNodesBasic(refsibling, sibling);
        }
        while ((sibling = tw.previousSibling()) != null) {
            refsibling = reftw.previousSibling();
            assertNotNull(refsibling);
            compareNodesBasic(refsibling, sibling);
        }
    }

    private void compareTreeWalkersChild(Document refDoc, Node refroot, Document doc, Node root,
                                         int whatToShow) {
        TreeWalker reftw = refDoc.createTreeWalker(refroot, whatToShow, null);
        TreeWalker tw = doc.createTreeWalker(root, whatToShow, null);
        Node node;
        while ((node = tw.nextNode()) != null) {
            Node refnode = reftw.nextNode();
            assertNotNull(refnode);
            compareNodesBasic(refnode, node);
            compareChild(reftw, tw);
            tw.setCurrentNode(node);
            reftw.setCurrentNode(refnode);
        }
        assertNull(reftw.nextNode());
        while ((node = tw.previousNode()) != null) {
            Node refnode = reftw.previousNode();
            assertNotNull(refnode);
            compareNodesBasic(refnode, node);
            compareChild(reftw, tw);
            tw.setCurrentNode(node);
            reftw.setCurrentNode(refnode);
        }
        assertNull(reftw.previousNode());
    }

    private void compareChild(TreeWalker reftw, TreeWalker tw) {
        Node child, refchild;
        while ((child = tw.firstChild()) != null) {
            refchild = reftw.firstChild();
            assertNotNull(refchild);
            compareNodesBasic(refchild, child);
            compareSiblings(reftw, tw);
            tw.setCurrentNode(child);
            reftw.setCurrentNode(refchild);
        }
        assertNull(reftw.firstChild());
        while ((child = tw.parentNode()) != null) {
            refchild = reftw.parentNode();
            assertNotNull(refchild);
            compareNodesBasic(refchild, child);
            compareSiblings(reftw, tw);
            tw.setCurrentNode(child);
            reftw.setCurrentNode(refchild);
        }
        assertNull(reftw.parentNode());
        while ((child = tw.lastChild()) != null) {
            refchild = reftw.lastChild();
            assertNotNull(refchild);
            compareNodesBasic(refchild, child);
        }
        assertNull(reftw.lastChild());
        while ((child = tw.parentNode()) != null) {
            refchild = reftw.parentNode();
            assertNotNull(refchild);
            compareNodesBasic(refchild, child);
            compareSiblings(reftw, tw);
            tw.setCurrentNode(child);
            reftw.setCurrentNode(refchild);
        }
        assertNull(reftw.parentNode());
    }
}
