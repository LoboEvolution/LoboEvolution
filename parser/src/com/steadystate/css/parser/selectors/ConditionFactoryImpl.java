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

import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionFactory;
import org.w3c.css.sac.ContentCondition;
import org.w3c.css.sac.LangCondition;
import org.w3c.css.sac.NegativeCondition;
import org.w3c.css.sac.PositionalCondition;


/**
 * The Class ConditionFactoryImpl.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 */
public class ConditionFactoryImpl implements ConditionFactory {

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createAndCondition(org.w3c.css.sac.Condition, org.w3c.css.sac.Condition)
     */
    public CombinatorCondition createAndCondition(final Condition first, final Condition second) throws CSSException {
        return new AndConditionImpl(first, second);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createOrCondition(org.w3c.css.sac.Condition, org.w3c.css.sac.Condition)
     */
    public CombinatorCondition createOrCondition(final Condition first, final Condition second) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createNegativeCondition(org.w3c.css.sac.Condition)
     */
    public NegativeCondition createNegativeCondition(final Condition condition) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createPositionalCondition(int, boolean, boolean)
     */
    public PositionalCondition createPositionalCondition(
            final int position,
            final boolean typeNode,
            final boolean type) throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createAttributeCondition(java.lang.String, java.lang.String, boolean, java.lang.String)
     */
    public AttributeCondition createAttributeCondition(
            final String localName,
            final String namespaceURI,
            final boolean specified,
            final String value) throws CSSException {
        return new AttributeConditionImpl(localName, value, specified);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createIdCondition(java.lang.String)
     */
    public AttributeCondition createIdCondition(final String value) throws CSSException {
        return new IdConditionImpl(value);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createLangCondition(java.lang.String)
     */
    public LangCondition createLangCondition(final String lang) throws CSSException {
        return new LangConditionImpl(lang);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createOneOfAttributeCondition(java.lang.String, java.lang.String, boolean, java.lang.String)
     */
    public AttributeCondition createOneOfAttributeCondition(
            final String localName,
            final String namespaceURI,
            final boolean specified,
            final String value) throws CSSException {
        return new OneOfAttributeConditionImpl(localName, value, specified);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createBeginHyphenAttributeCondition(java.lang.String, java.lang.String, boolean, java.lang.String)
     */
    public AttributeCondition createBeginHyphenAttributeCondition(
            final String localName,
            final String namespaceURI,
            final boolean specified,
            final String value) throws CSSException {
        return new BeginHyphenAttributeConditionImpl(localName, value, specified);
    }

    /**
     * Creates the prefix attribute condition.
     *
     * @param localName the local name
     * @param namespaceURI the namespace uri
     * @param specified the specified
     * @param value the value
     * @return the attribute condition
     * @throws CSSException the CSS exception
     */
    public AttributeCondition createPrefixAttributeCondition(
            final String localName,
            final String namespaceURI,
            final boolean specified,
            final String value) throws CSSException {
        return new PrefixAttributeConditionImpl(localName, value, specified);
    }

    /**
     * Creates the suffix attribute condition.
     *
     * @param localName the local name
     * @param namespaceURI the namespace uri
     * @param specified the specified
     * @param value the value
     * @return the attribute condition
     * @throws CSSException the CSS exception
     */
    public AttributeCondition createSuffixAttributeCondition(
            final String localName,
            final String namespaceURI,
            final boolean specified,
            final String value) throws CSSException {
        return new SuffixAttributeConditionImpl(localName, value, specified);
    }

    /**
     * Creates the substring attribute condition.
     *
     * @param localName the local name
     * @param namespaceURI the namespace uri
     * @param specified the specified
     * @param value the value
     * @return the attribute condition
     * @throws CSSException the CSS exception
     */
    public AttributeCondition createSubstringAttributeCondition(
            final String localName,
            final String namespaceURI,
            final boolean specified,
            final String value) throws CSSException {
        return new SubstringAttributeConditionImpl(localName, value, specified);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createClassCondition(java.lang.String, java.lang.String)
     */
    public AttributeCondition createClassCondition(
            final String namespaceURI,
            final String value) throws CSSException {
        return new ClassConditionImpl(value);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createPseudoClassCondition(java.lang.String, java.lang.String)
     */
    public AttributeCondition createPseudoClassCondition(
            final String namespaceURI,
            final String value) throws CSSException {
        return new PseudoClassConditionImpl(value);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createOnlyChildCondition()
     */
    public Condition createOnlyChildCondition() throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createOnlyTypeCondition()
     */
    public Condition createOnlyTypeCondition() throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.ConditionFactory#createContentCondition(java.lang.String)
     */
    public ContentCondition createContentCondition(final String data)
        throws CSSException {
        throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
    }
}
