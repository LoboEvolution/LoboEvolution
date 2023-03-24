/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
