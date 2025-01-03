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
/*
 * $Id: XPathResultImpl.java 1225426 2011-12-29 04:13:08Z mrglavas $
 */

package org.loboevolution.html.dom.xpath;

import lombok.Getter;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.apache.xpath.XPath;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.events.EventListener;
import org.loboevolution.html.node.Node;
import org.loboevolution.traversal.NodeIterator;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.events.Event;
import org.loboevolution.events.EventTarget;
import org.loboevolution.html.xpath.XPathException;
import org.loboevolution.html.xpath.XPathResult;

import javax.xml.transform.TransformerException;

/**
 * The class provides an implementation XPathResult according to the DOM L3
 * XPath Specification, Working Group Note 26 February 2004.
 *
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document
 * Object Model (DOM) Level 3 XPath Specification</a>.
 * </p>
 *
 * <p>
 * The XPathResult interface represents the result of the
 * evaluation of an XPath expression within the context of a particular node.
 * Since evaluation of an XPath expression can result in various result types,
 * this object makes it possible to discover and manipulate the type and value
 * of the result.
 * </p>
 *
 * <p>
 * This implementation wraps an XObject.
 *
 * @see org.loboevolution.apache.xpath.objects.XObject
 * @see org.loboevolution.html.xpath.XPathResult
 */
public class XPathResultImpl implements XPathResult {

    /**
     * The Constant ANY_TYPE.
     */
    @Getter
    public static final short ANY_TYPE = 0;

    /**
     * The Constant NUMBER_TYPE.
     */
    @Getter
    public static final short NUMBER_TYPE = 1;

    /**
     * The Constant STRING_TYPE.
     */
    @Getter
    public static final short STRING_TYPE = 2;

    /**
     * The Constant BOOLEAN_TYPE.
     */
    @Getter
    public static final short BOOLEAN_TYPE = 3;

    /**
     * The Constant UNORDERED_NODE_ITERATOR_TYPE.
     */
    @Getter
    public static final short UNORDERED_NODE_ITERATOR_TYPE = 4;

    /**
     * The Constant ORDERED_NODE_ITERATOR_TYPE.
     */
    @Getter
    public static final short ORDERED_NODE_ITERATOR_TYPE = 5;

    /**
     * The Constant UNORDERED_NODE_SNAPSHOT_TYPE.
     */
    @Getter
    public static final short UNORDERED_NODE_SNAPSHOT_TYPE = 6;

    /**
     * The Constant ORDERED_NODE_SNAPSHOT_TYPE.
     */
    @Getter
    public static final short ORDERED_NODE_SNAPSHOT_TYPE = 7;

    /**
     * The Constant ANY_UNORDERED_NODE_TYPE.
     */
    @Getter
    public static final short ANY_UNORDERED_NODE_TYPE = 8;

    /**
     * The Constant FIRST_ORDERED_NODE_TYPE.
     */
    @Getter
    public static final short FIRST_ORDERED_NODE_TYPE = 9;

    /**
     * The wrapped XObject.
     */
    private final XObject m_resultObj;

    /**
     * The xpath object that wraps the expression used for this result.
     */
    private final XPath m_xpath;

    /**
     * This the type specified by the user during construction. Typically the
     * constructor will be called by org.loboevolution.apache.xpath.XPath.evaluate().
     */
    private final short m_resultType;
    /**
     * Only used to attach a mutation event handler when specified type is an
     * iterator type.
     */
    private final Node m_contextNode;
    /**
     * The iterator, if this is an iterator type.
     */
    private final NodeIterator m_iterator = null;
    /**
     * The list, if this is a snapshot type.
     */
    private final NodeList m_list = null;
    /**
     * The m_is invalid iterator state.
     */
    private boolean m_isInvalidIteratorState = false;

    /**
     * Instantiates a new x path result impl.
     */
    public XPathResultImpl() {
        m_xpath = null;
        m_contextNode = null;
        m_resultType = 0;
        m_resultObj = null;

    }

    /**
     * Constructor for XPathResultImpl.
     * <p>
     * For internal use only.
     *
     * @param type        the type
     * @param result      the result
     * @param contextNode the context node
     * @param xpath       the xpath
     */
    XPathResultImpl(final short type, final XObject result, final Node contextNode, final XPath xpath) {
        // Check that the type is valid
        if (!isValidType(type)) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INVALID_XPATH_TYPE,
                    new Object[]{(int) type});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
        }

        // Result object should never be null!
        if (null == result) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_EMPTY_XPATH_RESULT, null);
            throw new XPathException(XPathException.INVALID_EXPRESSION_ERR, fmsg);
        }

        this.m_resultObj = result;
        this.m_contextNode = contextNode;
        this.m_xpath = xpath;

        // If specified result was ANY_TYPE, determine XObject type
        if (type == ANY_TYPE) {
            this.m_resultType = getTypeFromXObject(result);
        } else {
            this.m_resultType = type;
        }

        if (m_resultType == ORDERED_NODE_ITERATOR_TYPE || m_resultType == UNORDERED_NODE_ITERATOR_TYPE) {
            addEventListener();

        }

		 /*if (m_resultType == ORDERED_NODE_ITERATOR_TYPE || m_resultType == UNORDERED_NODE_ITERATOR_TYPE
				|| m_resultType == ANY_UNORDERED_NODE_TYPE || m_resultType == FIRST_ORDERED_NODE_TYPE) {

			try {
				m_iterator = m_resultObj.nodeset();
			} catch (TransformerException te) {
				// probably not a node type
				String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INCOMPATIBLE_TYPES,
						new Object[] { m_xpath.getPatternString(), getTypeString(getTypeFromXObject(m_resultObj)),
								getTypeString(m_resultType) });
				throw new XPathException(XPathException.TYPE_ERR, fmsg); 
			}
			// If it's a snapshot type, get the nodelist
		} else if (m_resultType == UNORDERED_NODE_SNAPSHOT_TYPE || m_resultType == ORDERED_NODE_SNAPSHOT_TYPE) {
			try {
				m_list = m_resultObj.nodelist();
			} catch (TransformerException te) {
				// probably not a node type
				String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INCOMPATIBLE_TYPES,
						new Object[] { m_xpath.getPatternString(), getTypeString(getTypeFromXObject(m_resultObj)),
								getTypeString(m_resultType) });
				throw new XPathException(XPathException.TYPE_ERR, fmsg);
			}
		}*/
    }

    /**
     * Check if the specified type is one of the supported types.
     *
     * @param type The specified type
     * @return true If the specified type is supported; otherwise, returns false.
     */
    public static boolean isValidType(final short type) {
        return switch (type) {
            case ANY_TYPE, NUMBER_TYPE, STRING_TYPE, BOOLEAN_TYPE, UNORDERED_NODE_ITERATOR_TYPE,
                 ORDERED_NODE_ITERATOR_TYPE, UNORDERED_NODE_SNAPSHOT_TYPE, ORDERED_NODE_SNAPSHOT_TYPE,
                 ANY_UNORDERED_NODE_TYPE, FIRST_ORDERED_NODE_TYPE -> true;
            default -> false;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getResultType() {
        return m_resultType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNumberValue() throws XPathException {
        if (getResultType() != NUMBER_TYPE) {
            final String fmsg = XPATHMessages.createXPATHMessage(
                    XPATHErrorResources.ER_CANT_CONVERT_XPATHRESULTTYPE_TO_NUMBER,
                    new Object[]{m_xpath.getPatternString(), getTypeString(m_resultType)});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
            // "The XPathResult of XPath expression {0} has an XPathResultType
            // of {1} which cannot be converted to a number"
        } else {
            try {
                return m_resultObj.num();
            } catch (final Exception e) {
                // Type check above should prevent this exception from
                // occurring.
                throw new XPathException(XPathException.TYPE_ERR, e.getMessage());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringValue() throws XPathException {
        if (getResultType() != STRING_TYPE) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_CONVERT_TO_STRING,
                    new Object[]{m_xpath.getPatternString(), m_resultObj.getTypeString()});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
            // "The XPathResult of XPath expression {0} has an XPathResultType
            // of {1} which cannot be converted to a string."
        } else {
            try {
                return m_resultObj.str();
            } catch (final Exception e) {
                // Type check above should prevent this exception from
                // occurring.
                throw new XPathException(XPathException.TYPE_ERR, e.getMessage());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getBooleanValue() throws XPathException {
        if (getResultType() != BOOLEAN_TYPE) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_CONVERT_TO_BOOLEAN,
                    new Object[]{m_xpath.getPatternString(), getTypeString(m_resultType)});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
            // "The XPathResult of XPath expression {0} has an XPathResultType
            // of {1} which cannot be converted to a boolean."
        } else {
            try {
                return m_resultObj.bool();
            } catch (final TransformerException e) {
                // Type check above should prevent this exception from
                // occurring.
                new XPathException(XPathException.TYPE_ERR, e.getMessage());
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getSingleNodeValue() throws XPathException {

        if (m_resultType != ANY_UNORDERED_NODE_TYPE && m_resultType != FIRST_ORDERED_NODE_TYPE) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_CONVERT_TO_SINGLENODE,
                    new Object[]{m_xpath.getPatternString(), getTypeString(m_resultType)});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
            // "The XPathResult of XPath expression {0} has an XPathResultType
            // of {1} which cannot be converted to a single node.
            // This method applies only to types ANY_UNORDERED_NODE_TYPE and
            // FIRST_ORDERED_NODE_TYPE."
        }

        final NodeIterator result = null;
		/*try {
			result = m_resultObj.nodeset();
		} catch (TransformerException te) {
			throw new XPathException(XPathException.TYPE_ERR, te.getMessage());
		}*/

        if (null == result) {
            return null;
        }

        final Node node = result.nextNode();

        if (isNamespaceNode(node)) {
            return new XPathNamespaceImpl(node);
        } else {
            return node;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getInvalidIteratorState() {
        return m_isInvalidIteratorState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSnapshotLength() throws XPathException {

        if (m_resultType != UNORDERED_NODE_SNAPSHOT_TYPE && m_resultType != ORDERED_NODE_SNAPSHOT_TYPE) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_GET_SNAPSHOT_LENGTH,
                    new Object[]{m_xpath.getPatternString(), getTypeString(m_resultType)});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
            // "The method getSnapshotLength cannot be called on the XPathResult
            // of XPath expression {0} because its XPathResultType is {1}.
        }

        return m_list.getLength();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node iterateNext() throws XPathException, DOMException {
        if (m_resultType != UNORDERED_NODE_ITERATOR_TYPE && m_resultType != ORDERED_NODE_ITERATOR_TYPE) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_NON_ITERATOR_TYPE,
                    new Object[]{m_xpath.getPatternString(), getTypeString(m_resultType)});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
            // "The method iterateNext cannot be called on the XPathResult of
            // XPath expression {0} because its XPathResultType is {1}.
            // This method applies only to types UNORDERED_NODE_ITERATOR_TYPE
            // and ORDERED_NODE_ITERATOR_TYPE."},
        }

        if (getInvalidIteratorState()) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_DOC_MUTATED, null);
            throw new DOMException(DOMException.INVALID_STATE_ERR, fmsg);
        }

        final Node node = m_iterator.nextNode();
        if (null == node) {
            removeEventListener(); // JIRA 1673
        }
        // Wrap "namespace node" in an XPathNamespace
        if (isNamespaceNode(node)) {
            return new XPathNamespaceImpl(node);
        } else {
            return node;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node snapshotItem(final int index) throws XPathException {

        if (m_resultType != UNORDERED_NODE_SNAPSHOT_TYPE && m_resultType != ORDERED_NODE_SNAPSHOT_TYPE) {
            final String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_NON_SNAPSHOT_TYPE,
                    new Object[]{m_xpath.getPatternString(), getTypeString(m_resultType)});
            throw new XPathException(XPathException.TYPE_ERR, fmsg);
            // "The method snapshotItem cannot be called on the XPathResult of
            // XPath expression {0} because its XPathResultType is {1}.
            // This method applies only to types UNORDERED_NODE_SNAPSHOT_TYPE
            // and ORDERED_NODE_SNAPSHOT_TYPE."},
        }

        final Node node = m_list.item(index);

        // Wrap "namespace node" in an XPathNamespace
        if (isNamespaceNode(node)) {
            return new XPathNamespaceImpl(node);
        } else {
            return node;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handle event.
     *
     * @param event a {@link Event} object.
     * @see EventListener#handleEvent(Event)
     */
    public void handleEvent(final Event event) {
        if (event.getType().equals("DOMSubtreeModified")) {
            m_isInvalidIteratorState = true;
            removeEventListener();
        }
    }

    /**
     * Given a request type, return the equivalent string. For diagnostic purposes.
     *
     * @param type the type
     * @return type string
     */
    private String getTypeString(final int type) {
        return switch (type) {
            case ANY_TYPE -> "ANY_TYPE";
            case ANY_UNORDERED_NODE_TYPE -> "ANY_UNORDERED_NODE_TYPE";
            case BOOLEAN_TYPE -> "BOOLEAN";
            case FIRST_ORDERED_NODE_TYPE -> "FIRST_ORDERED_NODE_TYPE";
            case NUMBER_TYPE -> "NUMBER_TYPE";
            case ORDERED_NODE_ITERATOR_TYPE -> "ORDERED_NODE_ITERATOR_TYPE";
            case ORDERED_NODE_SNAPSHOT_TYPE -> "ORDERED_NODE_SNAPSHOT_TYPE";
            case STRING_TYPE -> "STRING_TYPE";
            case UNORDERED_NODE_ITERATOR_TYPE -> "UNORDERED_NODE_ITERATOR_TYPE";
            case UNORDERED_NODE_SNAPSHOT_TYPE -> "UNORDERED_NODE_SNAPSHOT_TYPE";
            default -> "#UNKNOWN";
        };
    }

    /**
     * Given an XObject, determine the corresponding DOM XPath type.
     *
     * @param object the object
     * @return type string
     */
    private short getTypeFromXObject(final XObject object) {
        return switch (object.getType()) {
            case XObject.CLASS_BOOLEAN -> BOOLEAN_TYPE;
            case XObject.CLASS_NODESET, XObject.CLASS_RTREEFRAG -> UNORDERED_NODE_ITERATOR_TYPE;
            case XObject.CLASS_NUMBER -> NUMBER_TYPE;
            case XObject.CLASS_STRING -> STRING_TYPE;
            default -> ANY_TYPE; // throw exception ?
        };

    }

    /**
     * Given a node, determine if it is a namespace node.
     *
     * @param node the node
     * @return boolean Returns true if this is a namespace node; otherwise, returns
     * false.
     */
    private boolean isNamespaceNode(final Node node) {
        return (null != node && node.getNodeType() == Node.ATTRIBUTE_NODE
                && (node.getNodeName().startsWith("xmlns:") || node.getNodeName().equals("xmlns")));
    }

    /**
     * Add m_contextNode to Event Listner to listen for Mutations Events.
     */
    private void addEventListener() {
        if (m_contextNode instanceof EventTarget) {
            //((EventTarget) m_contextNode).addEventListener("DOMSubtreeModified", this, true);
        }

    }

    /**
     * Remove m_contextNode to Event Listner to listen for Mutations Events.
     */
    private void removeEventListener() {
        if (m_contextNode instanceof EventTarget) {
            //((EventTarget) m_contextNode).removeEventListener("DOMSubtreeModified", this, true);
        }
    }

}
