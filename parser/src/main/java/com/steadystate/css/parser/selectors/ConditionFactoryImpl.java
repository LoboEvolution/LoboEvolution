/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2011 David Schweinsberg.  All rights reserved.
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
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 */
public class ConditionFactoryImpl implements ConditionFactory {

	public CombinatorCondition createAndCondition(final Condition first,
			final Condition second) throws CSSException {
		return new AndConditionImpl(first, second);
	}

	public CombinatorCondition createOrCondition(final Condition first,
			final Condition second) throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	public NegativeCondition createNegativeCondition(final Condition condition)
			throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	public PositionalCondition createPositionalCondition(final int position,
			final boolean typeNode, final boolean type) throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	public AttributeCondition createAttributeCondition(final String localName,
			final String namespaceURI, final boolean specified,
			final String value) throws CSSException {
		return new AttributeConditionImpl(localName, value, specified);
	}

	public AttributeCondition createIdCondition(final String value)
			throws CSSException {
		return new IdConditionImpl(value);
	}

	public LangCondition createLangCondition(final String lang)
			throws CSSException {
		return new LangConditionImpl(lang);
	}

	public AttributeCondition createOneOfAttributeCondition(
			final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new OneOfAttributeConditionImpl(localName, value, specified);
	}

	public AttributeCondition createBeginHyphenAttributeCondition(
			final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new BeginHyphenAttributeConditionImpl(localName, value,
				specified);
	}

	public AttributeCondition createPrefixAttributeCondition(
			final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new PrefixAttributeConditionImpl(localName, value, specified);
	}

	public AttributeCondition createSuffixAttributeCondition(
			final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new SuffixAttributeConditionImpl(localName, value, specified);
	}

	public AttributeCondition createSubstringAttributeCondition(
			final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new SubstringAttributeConditionImpl(localName, value, specified);
	}

	public AttributeCondition createClassCondition(final String namespaceURI,
			final String value) throws CSSException {
		return new ClassConditionImpl(value);
	}

	public AttributeCondition createPseudoClassCondition(
			final String namespaceURI, final String value) throws CSSException {
		return new PseudoClassConditionImpl(value);
	}

	public Condition createOnlyChildCondition() throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	public Condition createOnlyTypeCondition() throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	public ContentCondition createContentCondition(final String data)
			throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}
}
