/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.parser.selectors;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CharacterDataSelector;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.DescendantSelector;
import org.w3c.css.sac.ElementSelector;
import org.w3c.css.sac.NegativeSelector;
import org.w3c.css.sac.ProcessingInstructionSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorFactory;
import org.w3c.css.sac.SiblingSelector;
import org.w3c.css.sac.SimpleSelector;


/**
 * The Class SelectorFactoryImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 */
public class SelectorFactoryImpl implements SelectorFactory {

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createConditionalSelector(org.w3c.css.sac.SimpleSelector, org.w3c.css.sac.Condition)
     */
    public ConditionalSelector createConditionalSelector(
        final SimpleSelector selector,
        final Condition condition) throws CSSException {
        return new ConditionalSelectorImpl(selector, condition);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createAnyNodeSelector()
     */
    public SimpleSelector createAnyNodeSelector() throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createRootNodeSelector()
     */
    public SimpleSelector createRootNodeSelector() throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createNegativeSelector(org.w3c.css.sac.SimpleSelector)
     */
    public NegativeSelector createNegativeSelector(final SimpleSelector selector) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createElementSelector(java.lang.String, java.lang.String)
     */
    public ElementSelector createElementSelector(final String namespaceURI, final String localName)
        throws CSSException {
        if (namespaceURI != null) {
            throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
        }
        return new ElementSelectorImpl(localName);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createTextNodeSelector(java.lang.String)
     */
    public CharacterDataSelector createTextNodeSelector(final String data) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createCDataSectionSelector(java.lang.String)
     */
    public CharacterDataSelector createCDataSectionSelector(final String data)
        throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createProcessingInstructionSelector(java.lang.String, java.lang.String)
     */
    public ProcessingInstructionSelector createProcessingInstructionSelector(
        final String target,
        final String data) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createCommentSelector(java.lang.String)
     */
    public CharacterDataSelector createCommentSelector(final String data) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createPseudoElementSelector(java.lang.String, java.lang.String)
     */
    public ElementSelector createPseudoElementSelector(
        final String namespaceURI,
        final String pseudoName) throws CSSException {
        if (namespaceURI != null) {
            throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
        }
        return new PseudoElementSelectorImpl(pseudoName);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createDescendantSelector(org.w3c.css.sac.Selector, org.w3c.css.sac.SimpleSelector)
     */
    public DescendantSelector createDescendantSelector(
        final Selector parent,
        final SimpleSelector descendant) throws CSSException {
        return new DescendantSelectorImpl(parent, descendant);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createChildSelector(org.w3c.css.sac.Selector, org.w3c.css.sac.SimpleSelector)
     */
    public DescendantSelector createChildSelector(
        final Selector parent,
        final SimpleSelector child) throws CSSException {
        return new ChildSelectorImpl(parent, child);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.SelectorFactory#createDirectAdjacentSelector(short, org.w3c.css.sac.Selector, org.w3c.css.sac.SimpleSelector)
     */
    public SiblingSelector createDirectAdjacentSelector(
        final short nodeType,
        final Selector child,
        final SimpleSelector directAdjacent) throws CSSException {
        return new DirectAdjacentSelectorImpl(nodeType, child, directAdjacent);
    }

    /**
     * Creates the general adjacent selector.
     *
     * @param nodeType the node type
     * @param child the child
     * @param directAdjacent the direct adjacent
     * @return the sibling selector
     * @throws CSSException the CSS exception
     */
    public SiblingSelector createGeneralAdjacentSelector(
            final short nodeType,
            final Selector child,
            final SimpleSelector directAdjacent) throws CSSException {
        return new GeneralAdjacentSelectorImpl(nodeType, child, directAdjacent);
    }
}
