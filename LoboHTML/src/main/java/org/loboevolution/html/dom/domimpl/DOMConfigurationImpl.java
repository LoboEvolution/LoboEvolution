/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.DOMErrorHandler;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.dom.DOMStringList;

import java.util.Map;
import java.util.TreeMap;

/**
 * <p>DOMConfigurationImpl class.</p>
 */
public class DOMConfigurationImpl implements DOMConfiguration {

	private static final Map<String, Object> parameters = new TreeMap<>();

	/**
	 * <p>Constructor for DOMConfigurationImpl.</p>
	 */
	public DOMConfigurationImpl() {

		parameters.put("canonical-form", Boolean.FALSE);
		parameters.put("cdata-sections", Boolean.TRUE);
		parameters.put("check-character-normalization", Boolean.FALSE);
		parameters.put("comments", Boolean.TRUE);
		parameters.put("datatype-normalization", Boolean.FALSE);
		parameters.put("element-content-whitespace", Boolean.TRUE);
		parameters.put("entities", Boolean.TRUE);
		parameters.put("error-handler", new DOMErrorMonitor());
		parameters.put("infoset", Boolean.FALSE);
		parameters.put("namespaces", Boolean.TRUE);
		parameters.put("namespace-declarations", Boolean.TRUE);
		parameters.put("normalize-characters", Boolean.FALSE);
		parameters.put("schema-location", null);
		parameters.put("schema-type", null);
		parameters.put("split-cdata-sections", Boolean.TRUE);
		parameters.put("validate", Boolean.FALSE);
		parameters.put("validate-if-schema", Boolean.FALSE);
		parameters.put("well-formed", Boolean.TRUE);
	}

	/** {@inheritDoc} */
	@Override
	public boolean canSetParameter(String name, final Object value) {
		if (value == null) {
			return true;
		}

		if (name == null) {
			return false;
		}

		return switch (name.toLowerCase()) {
			case "supported-media-types-only", "normalize-characters", "check-character-normalization",
				 "canonical-form", "namespace-declarations", "infoset", "cdata-sections",
				 "charset-overrides-xml-encoding", "comments", "datatype-normalization", "entities", "validate",
				 "validate-if-schema", "element-content-whitespace", "split-cdata-sections" -> true;
			case "well-formed", "namespaces", "ignore-unknown-character-denormalizations" -> (Boolean) value;
			case "error-handler" -> value instanceof DOMErrorHandler;
			case "schema-type", "schema-location" -> value instanceof String;
			default -> false;
		};
	}

	/** {@inheritDoc} */
	@Override
	public Object getParameter(final String name) {
		synchronized (this) {
			if (Strings.isBlank(name)) return null;
			final String nl = name.toLowerCase();
			final Object param = parameters.get(nl);
			if (param != null) {
				return param;
			} else if ("error-handler".equals(nl) || nl.startsWith("schema")) {
				return null;
			}
			throw new DOMException(DOMException.NOT_FOUND_ERR, "Record not found");
		}
	}


	/** {@inheritDoc} */
	@Override
	public DOMStringList getParameterNames() {
		synchronized (this) {
			return new DOMStringListImpl(parameters.keySet());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setParameter(final String name, final Object value) throws DOMException {

		final String nl = name.toLowerCase();

		if ("datatype-normalization".equals(nl) && (Boolean) value) {
			parameters.put("validate", true);
		}

		if ("infoset".equals(nl) && (Boolean) value) {
			parameters.put("validate-if-schema", Boolean.FALSE);
			parameters.put("entities", Boolean.FALSE);
			parameters.put("datatype-normalization", Boolean.FALSE);
			parameters.put("cdata-sections", Boolean.FALSE);

			parameters.put("namespace-declarations", Boolean.TRUE);
			parameters.put("well-formed", Boolean.TRUE);
			parameters.put("element-content-whitespace", Boolean.TRUE);
			parameters.put("comments", Boolean.TRUE);
			parameters.put("namespaces", Boolean.TRUE);
		}

		if (("namespaces".equals(nl) || "well-formed".equals(nl)) && !(Boolean) value) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
		}

		parameters.put(name.toLowerCase(), value);
	}

	public boolean getBoolean(String name) {
		Object v = parameters.get(name);
		return Boolean.TRUE.equals(v);
	}
}
