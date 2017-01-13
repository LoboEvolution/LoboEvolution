/*
 * Copyright (C) 1999-2017 David Schweinsberg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 */
public class SelectorFactoryImpl implements SelectorFactory {

    public ConditionalSelector createConditionalSelector(
        final SimpleSelector selector,
        final Condition condition) throws CSSException {
        return new ConditionalSelectorImpl(selector, condition);
    }

    public SimpleSelector createAnyNodeSelector() throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    public SimpleSelector createRootNodeSelector() throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    public NegativeSelector createNegativeSelector(final SimpleSelector selector) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    public ElementSelector createElementSelector(final String namespaceURI, final String localName)
        throws CSSException {
        if (namespaceURI != null) {
            throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
        }
        return new ElementSelectorImpl(localName);
    }

    public ElementSelector createSyntheticElementSelector() throws CSSException {
        return new SyntheticElementSelectorImpl();
    }

    public CharacterDataSelector createTextNodeSelector(final String data) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    public CharacterDataSelector createCDataSectionSelector(final String data)
        throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    public ProcessingInstructionSelector createProcessingInstructionSelector(
        final String target,
        final String data) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    public CharacterDataSelector createCommentSelector(final String data) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    public ElementSelector createPseudoElementSelector(
        final String namespaceURI,
        final String pseudoName) throws CSSException {
        if (namespaceURI != null) {
            throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
        }
        return new PseudoElementSelectorImpl(pseudoName);
    }

    public DescendantSelector createDescendantSelector(
        final Selector parent,
        final SimpleSelector descendant) throws CSSException {
        return new DescendantSelectorImpl(parent, descendant);
    }

    public DescendantSelector createChildSelector(
        final Selector parent,
        final SimpleSelector child) throws CSSException {
        return new ChildSelectorImpl(parent, child);
    }

    public SiblingSelector createDirectAdjacentSelector(
        final short nodeType,
        final Selector child,
        final SimpleSelector directAdjacent) throws CSSException {
        return new DirectAdjacentSelectorImpl(nodeType, child, directAdjacent);
    }

    public SiblingSelector createGeneralAdjacentSelector(
            final short nodeType,
            final Selector child,
            final SimpleSelector directAdjacent) throws CSSException {
        return new GeneralAdjacentSelectorImpl(nodeType, child, directAdjacent);
    }
}
