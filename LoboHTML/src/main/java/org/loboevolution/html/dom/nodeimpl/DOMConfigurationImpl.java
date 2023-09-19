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
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.DOMErrorHandler;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.DOMStringList;

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
		parameters.put("namespaces", true);
		parameters.put("namespace-declarations", true);
		parameters.put("element-content-whitespace", true);
		parameters.put("comments", true);
		parameters.put("entities", true);
		parameters.put("ignore-unknown-character-denormalizations", true);
		parameters.put("cdata-sections", true);
		parameters.put("well-formed", true);
		parameters.put("split-cdata-sections", true);
		parameters.put("normalize-characters", false);
		parameters.put("canonical-form", false);
		parameters.put("check-character-normalization", false);
		parameters.put("validate", false);
		parameters.put("validate-if-schema", false);
		parameters.put("datatype-normalization", false);
		parameters.put("supported-media-types-only", false);
		parameters.put("charset-overrides-xml-encoding", false);
		parameters.put("infoset", false);
		parameters.put("error-handler", new DOMErrorMonitor());
		parameters.put("schema-type", null);
		parameters.put("schema-location", null);

	}

	/** {@inheritDoc} */
	@Override
	public boolean canSetParameter(String name, Object value) {
		if (value == null) {
			return true;
		}

		if (name.equalsIgnoreCase("supported-media-types-only")
				|| name.equalsIgnoreCase("normalize-characters")
				|| name.equalsIgnoreCase("check-character-normalization")
				|| name.equalsIgnoreCase("canonical-form")
				|| name.equalsIgnoreCase("namespace-declarations")
				|| name.equalsIgnoreCase("infoset")
				|| name.equalsIgnoreCase("cdata-sections")
				|| name.equalsIgnoreCase("charset-overrides-xml-encoding")
				|| name.equalsIgnoreCase("comments")
				|| name.equalsIgnoreCase("datatype-normalization")
				|| name.equalsIgnoreCase("entities")
				|| name.equalsIgnoreCase("validate")
				|| name.equalsIgnoreCase("validate-if-schema")
				|| name.equalsIgnoreCase("element-content-whitespace")
				|| name.equalsIgnoreCase("split-cdata-sections")){
			return true;
		} else if (name.equalsIgnoreCase("well-formed")
				|| name.equalsIgnoreCase("namespaces")
				|| name.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
			return (Boolean) value;
		} else if (name.equalsIgnoreCase("error-handler")) {
			return value instanceof DOMErrorHandler;
		} else if (name.equalsIgnoreCase("schema-type")
				|| name.equalsIgnoreCase("schema-location")) {
			return value instanceof String;
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Object getParameter(String name) {
		synchronized (this) {
			if (Strings.isBlank(name)) return null;
			String nl = name.toLowerCase();
			Object param = parameters.get(nl);
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
	public void setParameter(String name, Object value) throws DOMException {
		synchronized (this) {

			String nl = name.toLowerCase();

			if ("datatype-normalization".equals(nl) && (Boolean) value) {
				parameters.put("validate", true);
			}

			if ("infoset".equals(nl) && (Boolean) value) {
				parameters.put("entities", false);
				parameters.put("cdata-sections", false);
				parameters.put("namespace-declarations", true);
				parameters.put("element-content-whitespace", true);
				parameters.put("comments", true);
			}

			if (("namespaces".equals(nl) || "well-formed".equals(nl)) && !(Boolean) value) {
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
			}

			parameters.put(name.toLowerCase(), value);
		}
	}
}
