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
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.loboevolution.javax.xml.stream;

import org.loboevolution.javax.xml.transform.Result;

public abstract class XMLOutputFactory {
  /**
   * Property used to set prefix defaulting on the output side
   */
  public static final String IS_REPAIRING_NAMESPACES=
    "org.loboevolution.javax.xml.stream.isRepairingNamespaces";

  static final String DEFAULIMPL = "com.sun.xml.internal.stream.XMLOutputFactoryImpl";

  protected XMLOutputFactory(){}

  /**
   * Creates a new instance of the factory in exactly the same manner as the
   * {@link #newFactory()} method.
   * @throws FactoryConfigurationError if an instance of this factory cannot be loaded
   */
  public static XMLOutputFactory newInstance()
    throws FactoryConfigurationError
  {
    return FactoryFinder.find(XMLOutputFactory.class, DEFAULIMPL);
  }

  /**
   * Create a new instance of the factory.
   * This static method creates a new factory instance. This method uses the
   * following ordered lookup procedure to determine the XMLOutputFactory
   * implementation class to load:
   * <ul>
   * <li>
   *   Use the org.loboevolution.javax.xml.stream.XMLOutputFactory system property.
   * </li>
   * <li>
   *   Use the properties file "lib/stax.properties" in the JRE directory.
   *     This configuration file is in standard java.util.Properties format
   *     and contains the fully qualified name of the implementation class
   *     with the key being the system property defined above.
   * </li>
   * <li>
   *   Use the service-provider loading facilities, defined by the
   *   {@link java.util.ServiceLoader} class, to attempt to locate and load an
   *   implementation of the service using the {@linkplain
   *   java.util.ServiceLoader#load(java.lang.Class) default loading mechanism}:
   *   the service-provider loading facility will use the {@linkplain
   *   java.lang.Thread#getContextClassLoader() current thread's context class loader}
   *   to attempt to load the service. If the context class
   *   loader is null, the {@linkplain
   *   ClassLoader#getSystemClassLoader() system class loader} will be used.
   * </li>
   * <li>
   *   Otherwise, the system-default implementation is returned.
   * </li>
   * </ul>
   * Once an application has obtained a reference to a XMLOutputFactory it
   * can use the factory to configure and obtain stream instances.
   * Note that this is a new method that replaces the deprecated newInstance() method.
   * No changes in behavior are defined by this replacement method relative to the
   * deprecated method.
   * @throws FactoryConfigurationError in case of {@linkplain
   *   java.util.ServiceConfigurationError service configuration error} or if
   *   the implementation is not available or cannot be instantiated.
   */
  public static XMLOutputFactory newFactory()
    throws FactoryConfigurationError
  {
    return FactoryFinder.find(XMLOutputFactory.class, DEFAULIMPL);
  }

  /**
   * Create a new instance of the factory.
   *
   * @param factoryId             Name of the factory to find, same as
   *                              a property name
   * @param classLoader           classLoader to use
   * @return the factory implementation
   * @throws FactoryConfigurationError if an instance of this factory cannot be loaded
   *
   * @deprecated  This method has been deprecated because it returns an
   *              instance of XMLInputFactory, which is of the wrong class.
   *              Use the new method {@link #newFactory(java.lang.String,
   *              java.lang.ClassLoader)} instead.
   */
  public static XMLInputFactory newInstance(String factoryId,
          ClassLoader classLoader)
          throws FactoryConfigurationError {
      //do not fallback if given classloader can't find the class, throw exception
      return FactoryFinder.find(XMLInputFactory.class, factoryId, classLoader, null);
  }

  /**
   * Create a new instance of the factory.
   * If the classLoader argument is null, then the ContextClassLoader is used.
   * <p>
   * This method uses the following ordered lookup procedure to determine
   * the XMLOutputFactory implementation class to load:
   * </p>
   * <ul>
   * <li>
   *   Use the value of the system property identified by {@code factoryId}.
   * </li>
   * <li>
   *   Use the properties file "lib/stax.properties" in the JRE directory.
   *     This configuration file is in standard java.util.Properties format
   *     and contains the fully qualified name of the implementation class
   *     with the key being the given {@code factoryId}.
   * </li>
   * <li>
   *   If {@code factoryId} is "org.loboevolution.javax.xml.stream.XMLOutputFactory",
   *   use the service-provider loading facilities, defined by the
   *   {@link java.util.ServiceLoader} class, to attempt to {@linkplain
   *   java.util.ServiceLoader#load(java.lang.Class, java.lang.ClassLoader) locate and load}
   *   an implementation of the service using the specified {@code ClassLoader}.
   *   If {@code classLoader} is null, the {@linkplain
   *   java.util.ServiceLoader#load(java.lang.Class) default loading mechanism} will apply:
   *   That is, the service-provider loading facility will use the {@linkplain
   *   java.lang.Thread#getContextClassLoader() current thread's context class loader}
   *   to attempt to load the service. If the context class
   *   loader is null, the {@linkplain
   *   ClassLoader#getSystemClassLoader() system class loader} will be used.
   * </li>
   * <li>
   *   Otherwise, throws a {@link FactoryConfigurationError}.
   * </li>
   * </ul>
   *
   *  The parameter factoryId defined here is inconsistent with that
   * of other JAXP factories where the first parameter is fully qualified
   * factory class name that provides implementation of the factory.
   *
   * <p>
   *   Note that this is a new method that replaces the deprecated
   *   {@link #newInstance(java.lang.String, java.lang.ClassLoader)
   *   newInstance(String factoryId, ClassLoader classLoader)} method.
   *   The original method was incorrectly defined to return XMLInputFactory.
   * </p>
   *
   * @param factoryId             Name of the factory to find, same as
   *                              a property name
   * @param classLoader           classLoader to use
   * @return the factory implementation
   * @throws FactoryConfigurationError in case of {@linkplain
   *   java.util.ServiceConfigurationError service configuration error} or if
   *   the implementation is not available or cannot be instantiated.
   */
  public static XMLOutputFactory newFactory(String factoryId,
          ClassLoader classLoader)
          throws FactoryConfigurationError {
      //do not fallback if given classloader can't find the class, throw exception
      return FactoryFinder.find(XMLOutputFactory.class, factoryId, classLoader, null);
  }

  /**
   * Create a new XMLStreamWriter that writes to a writer
   * @param stream the writer to write to
   * @throws XMLStreamException
   */
  public abstract XMLStreamWriter createXMLStreamWriter(java.io.Writer stream) throws XMLStreamException;

  /**
   * Create a new XMLStreamWriter that writes to a stream
   * @param stream the stream to write to
   * @throws XMLStreamException
   */
  public abstract XMLStreamWriter createXMLStreamWriter(java.io.OutputStream stream) throws XMLStreamException;

  /**
   * Create a new XMLStreamWriter that writes to a stream
   * @param stream the stream to write to
   * @param encoding the encoding to use
   * @throws XMLStreamException
   */
  public abstract XMLStreamWriter createXMLStreamWriter(java.io.OutputStream stream,
                                         String encoding) throws XMLStreamException;

  /**
   * Create a new XMLStreamWriter that writes to a JAXP result.  This method is optional.
   * @param result the result to write to
   * @throws UnsupportedOperationException if this method is not
   * supported by this XMLOutputFactory
   * @throws XMLStreamException
   */
  public abstract XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException;


  /**
   * Create a new XMLEventWriter that writes to a JAXP result.  This method is optional.
   * @param result the result to write to
   * @throws UnsupportedOperationException if this method is not
   * supported by this XMLOutputFactory
   * @throws XMLStreamException
   */
  public abstract XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException;

  /**
   * Create a new XMLEventWriter that writes to a stream
   * @param stream the stream to write to
   * @throws XMLStreamException
   */
  public abstract XMLEventWriter createXMLEventWriter(java.io.OutputStream stream) throws XMLStreamException;



  /**
   * Create a new XMLEventWriter that writes to a stream
   * @param stream the stream to write to
   * @param encoding the encoding to use
   * @throws XMLStreamException
   */
  public abstract XMLEventWriter createXMLEventWriter(java.io.OutputStream stream,
                                                     String encoding) throws XMLStreamException;

  /**
   * Create a new XMLEventWriter that writes to a writer
   * @param stream the stream to write to
   * @throws XMLStreamException
   */
  public abstract XMLEventWriter createXMLEventWriter(java.io.Writer stream) throws XMLStreamException;

  /**
   * Allows the user to set specific features/properties on the underlying implementation.
   * @param name The name of the property
   * @param value The value of the property
   * @throws java.lang.IllegalArgumentException if the property is not supported
   */
  public abstract void setProperty(java.lang.String name,
                                    Object value)
    throws IllegalArgumentException;

  /**
   * Get a feature/property on the underlying implementation
   * @param name The name of the property
   * @return The value of the property
   * @throws java.lang.IllegalArgumentException if the property is not supported
   */
  public abstract Object getProperty(java.lang.String name)
    throws IllegalArgumentException;

  /**
   * Query the set of properties that this factory supports.
   *
   * @param name The name of the property (may not be null)
   * @return true if the property is supported and false otherwise
   */
  public abstract boolean isPropertySupported(String name);
}
