/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.html.dom.nodeimpl.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationSourceImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DOMImplementationList;
import org.loboevolution.html.node.DOMImplementationSource;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.List;

@Slf4j
public final class DOMImplementationRegistry {

    /**
     * The list of DOMImplementationSources.
     */
    private final List<DOMImplementationSource> sources;

    /**
     * Default class name.
     */
    private static final String FALLBACK_CLASS =
            "org.loboevolution.html.dom.nodeimpl.DOMImplementationSourceImpl";
    private static final String DEFAULT_PACKAGE =
            "org.loboevolution.html.dom.nodeimpl";

    /**
     * Private constructor.
     *
     * @param srcs Vector List of DOMImplementationSources
     */
    private DOMImplementationRegistry(final List<DOMImplementationSource> srcs) {
        sources = srcs;
    }

    /**
     * Obtain a new instance of a <code>DOMImplementationRegistry</code>.
     * <p>
     * The <code>DOMImplementationRegistry</code> is initialized by the
     * application or the implementation, depending on the context, by
     * first checking the value of the Java system property
     * <code>org.w3c.dom.DOMImplementationSourceList</code> and
     * the service provider whose contents are at
     * "<code>META_INF/services/org.w3c.dom.DOMImplementationSourceList</code>".
     * The value of this property is a white-space separated list of
     * names of availables classes implementing the
     * <code>DOMImplementationSource</code> interface. Each class listed
     * in the class name list is instantiated and any exceptions
     * encountered are thrown to the application.
     *
     * @return an initialized instance of DOMImplementationRegistry
     * @throws ClassNotFoundException If any specified class can not be found
     * @throws InstantiationException If any specified class is an interface or abstract class
     * @throws IllegalAccessException If the default constructor of a specified class is not accessible
     * @throws ClassCastException     If any specified class does not implement
     *                                <code>DOMImplementationSource</code>
     */
    public static DOMImplementationRegistry newInstance()
            throws
            ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            ClassCastException {
        final List<DOMImplementationSource> sources = new ArrayList<>();

        final ClassLoader classLoader = getClassLoader();
        final StringTokenizer st = new StringTokenizer(FALLBACK_CLASS);
        while (st.hasMoreTokens()) {
            final String sourceName = st.nextToken();
            boolean internal = sourceName != null && sourceName.startsWith(DEFAULT_PACKAGE);
            Class sourceClass;
            if (classLoader != null && !internal) {
                sourceClass = classLoader.loadClass(sourceName);
            } else {
                sourceClass = Class.forName(sourceName);
            }
            try {
                final DOMImplementationSourceImpl source =
                        (DOMImplementationSourceImpl) sourceClass.getConstructor().newInstance();
                sources.add(source);
            } catch (final NoSuchMethodException | InvocationTargetException e) {
                throw new InstantiationException(e.getMessage());
            }
        }
        return new DOMImplementationRegistry(sources);
    }

    /**
     * Return the first implementation that has the desired
     * features, or <code>null</code> if none is found.
     *
     * @param features A string that specifies which features are required. This is
     *                 a space separated list in which each feature is specified by
     *                 its name optionally followed by a space and a version number.
     *                 This is something like: "XML 1.0 Traversal +Events 2.0"
     * @return An implementation that has the desired features,
     * or <code>null</code> if none found.
     */
    public DOMImplementation getDOMImplementation(final String features) {
        for (final DOMImplementationSource source : sources) {
            final DOMImplementation impl = source.getDOMImplementation(features);
            if (impl != null) {
                return impl;
            }
        }
        return null;
    }

    /**
     * Return a list of implementations that support the
     * desired features.
     *
     * @param features A string that specifies which features are required. This is
     *                 a space separated list in which each feature is specified by
     *                 its name optionally followed by a space and a version number.
     *                 This is something like: "XML 1.0 Traversal +Events 2.0"
     * @return A list of DOMImplementations that support the desired features.
     */
    public DOMImplementationList getDOMImplementationList(final String features) {
        final List<DOMImplementation> implementations = new ArrayList<>();
        for (final DOMImplementationSource source : sources) {
            final DOMImplementationList impls = source.getDOMImplementationList(features);
            for (int j = 0; j < impls.getLength(); j++) {
                final DOMImplementation impl = impls.item(j);
                implementations.add(impl);
            }
        }
        return new DOMImplementationList() {
            public DOMImplementation item(final int index) {
                if (index >= 0 && index < implementations.size()) {
                    try {
                        return implementations.get(index);
                    } catch (final ArrayIndexOutOfBoundsException e) {
                        return null;
                    }
                }
                return null;
            }

            public int getLength() {
                return implementations.size();
            }
        };
    }

    /**
     * Register an implementation.
     *
     * @param s The source to be registered, may not be <code>null</code>
     */
    public void addSource(final DOMImplementationSource s) {
        if (s == null) {
            throw new NullPointerException();
        }
        if (!sources.contains(s)) {
            sources.add(s);
        }
    }

    /**
     * Gets a class loader.
     *
     * @return A class loader, possibly <code>null</code>
     */
    private static ClassLoader getClassLoader() {
        return DOMImplementationRegistry.class.getClassLoader();
    }
}
