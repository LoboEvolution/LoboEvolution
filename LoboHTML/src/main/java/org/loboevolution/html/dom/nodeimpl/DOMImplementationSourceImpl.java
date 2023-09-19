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
 * Created on Oct 15, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DOMImplementationList;
import org.loboevolution.html.node.DOMImplementationSource;
import org.loboevolution.http.UserAgentContext;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <p>DOMImplementationSourceImpl class.</p>
 */
public class DOMImplementationSourceImpl implements DOMImplementationSource {

    /**
     * A method to request a DOM implementation.
     *
     * @param features A string that specifies which features are required.
     *                 This is a space separated list in which each feature is specified
     *                 by its name optionally followed by a space and a version number.
     *                 This is something like: "XML 1.0 Traversal Events 2.0"
     * @return An implementation that has the desired features, or
     * <code>null</code> if this source has none.
     */
    public DOMImplementation getDOMImplementation(String features) {
        DOMImplementation impl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), false));
        if (testImpl(impl, features)) {
            return impl;
        }

        return null;
    }

    /**
     * A method to request a list of DOM implementations that support the
     * specified features and versions, as specified in .
     *
     * @param features A string that specifies which features and versions
     *                 are required. This is a space separated list in which each feature
     *                 is specified by its name optionally followed by a space and a
     *                 version number. This is something like: "XML 3.0 Traversal +Events
     *                 2.0"
     * @return A list of DOM implementations that support the desired
     * features.
     */
    public DOMImplementationList getDOMImplementationList(String features) {
        DOMImplementation impl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(),false));
        final List<DOMImplementation> implementations = new ArrayList<>();
        if (testImpl(impl, features)) {
            implementations.add(impl);
        }

        return new DOMImplementationListImpl(implementations);
    }

    boolean testImpl(DOMImplementation impl, String features) {

        StringTokenizer st = new StringTokenizer(features);
        String feature = null;
        String version = null;

        if (st.hasMoreTokens()) {
            feature = st.nextToken();
        }
        while (feature != null) {
            boolean isVersion = false;
            if (st.hasMoreTokens()) {
                char c;
                version = st.nextToken();
                c = version.charAt(0);
                switch (c) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        isVersion = true;
                }
            } else {
                version = null;
            }
            if (isVersion) {
                if (!impl.hasFeature(feature, version)) {
                    return false;
                }
                if (st.hasMoreTokens()) {
                    feature = st.nextToken();
                } else {
                    feature = null;
                }
            } else {
                if (!impl.hasFeature(feature, null)) {
                    return false;
                }
                feature = version;
            }
        }
        return true;
    }
}
