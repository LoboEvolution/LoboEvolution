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
package org.loboevolution.html.dom.nodeimpl.bootstrap;

import org.loboevolution.html.dom.nodeimpl.DOMImplementationSourceImpl;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DOMImplementationList;
import org.loboevolution.html.node.DOMImplementationSource;

import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.StringTokenizer;
import java.util.Vector;

public final class DOMImplementationRegistry {

    /**
     * The list of DOMImplementationSources.
     */
    private Vector sources;

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
    private DOMImplementationRegistry(final Vector srcs) {
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
        Vector sources = new Vector();

        ClassLoader classLoader = getClassLoader();
        StringTokenizer st = new StringTokenizer(FALLBACK_CLASS);
        while (st.hasMoreTokens()) {
            String sourceName = st.nextToken();
            // make sure we have access to restricted packages
            boolean internal = false;
            if (System.getSecurityManager() != null) {
                if (sourceName != null && sourceName.startsWith(DEFAULT_PACKAGE)) {
                    internal = true;
                }
            }
            Class sourceClass = null;
            if (classLoader != null && !internal) {
                sourceClass = classLoader.loadClass(sourceName);
            } else {
                sourceClass = Class.forName(sourceName);
            }
            try {
                DOMImplementationSourceImpl source =
                        (DOMImplementationSourceImpl) sourceClass.getConstructor().newInstance();
                sources.addElement(source);
            } catch (NoSuchMethodException | InvocationTargetException e) {
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
        int size = sources.size();
        String name = null;
        for (int i = 0; i < size; i++) {
            DOMImplementationSourceImpl source =
                    (DOMImplementationSourceImpl) sources.elementAt(i);
            DOMImplementation impl = source.getDOMImplementation(features);
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
        final Vector implementations = new Vector();
        int size = sources.size();
        for (int i = 0; i < size; i++) {
            DOMImplementationSource source = (DOMImplementationSource) sources.elementAt(i);
            DOMImplementationList impls =
                    source.getDOMImplementationList(features);
            for (int j = 0; j < impls.getLength(); j++) {
                DOMImplementation impl = impls.item(j);
                implementations.addElement(impl);
            }
        }
        return new DOMImplementationList() {
            public DOMImplementation item(final int index) {
                if (index >= 0 && index < implementations.size()) {
                    try {
                        return (DOMImplementation)
                                implementations.elementAt(index);
                    } catch (ArrayIndexOutOfBoundsException e) {
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
            sources.addElement(s);
        }
    }

    /**
     * Gets a class loader.
     *
     * @return A class loader, possibly <code>null</code>
     */
    private static ClassLoader getClassLoader() {
        try {
            ClassLoader contextClassLoader = getContextClassLoader();

            if (contextClassLoader != null) {
                return contextClassLoader;
            }
        } catch (Exception e) {
            // Assume that the DOM application is in a JRE 1.1, use the
            // current ClassLoader
            return DOMImplementationRegistry.class.getClassLoader();
        }
        return DOMImplementationRegistry.class.getClassLoader();
    }

    /**
     * A simple JRE (Java Runtime Environment) 1.1 test
     *
     * @return <code>true</code> if JRE 1.1
     */
    private static boolean isJRE11() {
        try {
            Class c = Class.forName("java.security.AccessController");
            // java.security.AccessController existed since 1.2 so, if no
            // exception was thrown, the DOM application is running in a JRE
            // 1.2 or higher
            return false;
        } catch (Exception ex) {
            // ignore
        }
        return true;
    }

    /**
     * This method returns the ContextClassLoader or <code>null</code> if
     * running in a JRE 1.1
     *
     * @return The Context Classloader
     */
    private static ClassLoader getContextClassLoader() {
        return isJRE11()
                ? null
                : (ClassLoader)
                AccessController.doPrivileged((PrivilegedAction) () -> {
                    ClassLoader classLoader = null;
                    try {
                        classLoader =
                                Thread.currentThread().getContextClassLoader();
                    } catch (SecurityException ex) {
                    }
                    return classLoader;
                });
    }
}
