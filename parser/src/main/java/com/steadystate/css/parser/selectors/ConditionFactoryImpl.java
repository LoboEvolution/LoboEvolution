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

	@Override
	public CombinatorCondition createAndCondition(final Condition first, final Condition second) throws CSSException {
		return new AndConditionImpl(first, second);
	}

	@Override
	public CombinatorCondition createOrCondition(final Condition first, final Condition second) throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	@Override
	public NegativeCondition createNegativeCondition(final Condition condition) throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	@Override
	public PositionalCondition createPositionalCondition(final int position, final boolean typeNode, final boolean type)
			throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	@Override
	public AttributeCondition createAttributeCondition(final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new AttributeConditionImpl(localName, value, specified);
	}

	@Override
	public AttributeCondition createIdCondition(final String value) throws CSSException {
		return new IdConditionImpl(value);
	}

	@Override
	public LangCondition createLangCondition(final String lang) throws CSSException {
		return new LangConditionImpl(lang);
	}

	@Override
	public AttributeCondition createOneOfAttributeCondition(final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new OneOfAttributeConditionImpl(localName, value, specified);
	}

	@Override
	public AttributeCondition createBeginHyphenAttributeCondition(final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new BeginHyphenAttributeConditionImpl(localName, value, specified);
	}

	public AttributeCondition createPrefixAttributeCondition(final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new PrefixAttributeConditionImpl(localName, value, specified);
	}

	public AttributeCondition createSuffixAttributeCondition(final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new SuffixAttributeConditionImpl(localName, value, specified);
	}

	public AttributeCondition createSubstringAttributeCondition(final String localName, final String namespaceURI,
			final boolean specified, final String value) throws CSSException {
		return new SubstringAttributeConditionImpl(localName, value, specified);
	}

	@Override
	public AttributeCondition createClassCondition(final String namespaceURI, final String value) throws CSSException {
		return new ClassConditionImpl(value);
	}

	@Override
	public AttributeCondition createPseudoClassCondition(final String namespaceURI, final String value)
			throws CSSException {
		return new PseudoClassConditionImpl(value);
	}

	@Override
	public Condition createOnlyChildCondition() throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	@Override
	public Condition createOnlyTypeCondition() throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}

	@Override
	public ContentCondition createContentCondition(final String data) throws CSSException {
		throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
	}
}
