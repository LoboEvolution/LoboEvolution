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

import org.loboevolution.javax.xml.namespace.NamespaceContext;

public interface XMLStreamWriter {

  /**
   * Writes a start tag to the output.  All writeStartElement methods
   * open a new scope in the internal namespace context.  Writing the
   * corresponding EndElement causes the scope to be closed.
   * @param localName local name of the tag, may not be null
   * @throws XMLStreamException
   */
  public void writeStartElement(String localName)
    throws XMLStreamException;

  /**
   * Writes a start tag to the output
   * @param namespaceURI the namespaceURI of the prefix to use, may not be null
   * @param localName local name of the tag, may not be null
   * @throws XMLStreamException if the namespace URI has not been bound to a prefix and
   * org.loboevolution.javax.xml.stream.isRepairingNamespaces has not been set to true
   */
  public void writeStartElement(String namespaceURI, String localName)
    throws XMLStreamException;

  /**
   * Writes a start tag to the output
   * @param localName local name of the tag, may not be null
   * @param prefix the prefix of the tag, may not be null
   * @param namespaceURI the uri to bind the prefix to, may not be null
   * @throws XMLStreamException
   */
  public void writeStartElement(String prefix,
                                String localName,
                                String namespaceURI)
    throws XMLStreamException;

  /**
   * Writes an empty element tag to the output
   * @param namespaceURI the uri to bind the tag to, may not be null
   * @param localName local name of the tag, may not be null
   * @throws XMLStreamException if the namespace URI has not been bound to a prefix and
   * org.loboevolution.javax.xml.stream.isRepairingNamespaces has not been set to true
   */
  public void writeEmptyElement(String namespaceURI, String localName)
    throws XMLStreamException;

  /**
   * Writes an empty element tag to the output
   * @param prefix the prefix of the tag, may not be null
   * @param localName local name of the tag, may not be null
   * @param namespaceURI the uri to bind the tag to, may not be null
   * @throws XMLStreamException
   */
  public void writeEmptyElement(String prefix, String localName, String namespaceURI)
    throws XMLStreamException;

  /**
   * Writes an empty element tag to the output
   * @param localName local name of the tag, may not be null
   * @throws XMLStreamException
   */
  public void writeEmptyElement(String localName)
    throws XMLStreamException;

  /**
   * Writes string data to the output without checking for well formedness.
   * The data is opaque to the XMLStreamWriter, i.e. the characters are written
   * blindly to the underlying output.  If the method cannot be supported
   * in the currrent writing context the implementation may throw a
   * UnsupportedOperationException.  For example note that any
   * namespace declarations, end tags, etc. will be ignored and could
   * interfere with proper maintanence of the writers internal state.
   *
   * @param data the data to write
   */
  //  public void writeRaw(String data) throws XMLStreamException;

  /**
   * Writes an end tag to the output relying on the internal
   * state of the writer to determine the prefix and local name
   * of the event.
   * @throws XMLStreamException
   */
  public void writeEndElement()
    throws XMLStreamException;

  /**
   * Closes any start tags and writes corresponding end tags.
   * @throws XMLStreamException
   */
  public void writeEndDocument()
    throws XMLStreamException;

  /**
   * Close this writer and free any resources associated with the
   * writer.  This must not close the underlying output stream.
   * @throws XMLStreamException
   */
  public void close()
    throws XMLStreamException;

  /**
   * Write any cached data to the underlying output mechanism.
   * @throws XMLStreamException
   */
  public void flush()
    throws XMLStreamException;

  /**
   * Writes an attribute to the output stream without
   * a prefix.
   * @param localName the local name of the attribute
   * @param value the value of the attribute
   * @throws IllegalStateException if the current state does not allow Attribute writing
   * @throws XMLStreamException
   */
  public void writeAttribute(String localName, String value)
    throws XMLStreamException;

  /**
   * Writes an attribute to the output stream
   * @param prefix the prefix for this attribute
   * @param namespaceURI the uri of the prefix for this attribute
   * @param localName the local name of the attribute
   * @param value the value of the attribute
   * @throws IllegalStateException if the current state does not allow Attribute writing
   * @throws XMLStreamException if the namespace URI has not been bound to a prefix and
   * org.loboevolution.javax.xml.stream.isRepairingNamespaces has not been set to true
   */

  public void writeAttribute(String prefix,
                             String namespaceURI,
                             String localName,
                             String value)
    throws XMLStreamException;

  /**
   * Writes an attribute to the output stream
   * @param namespaceURI the uri of the prefix for this attribute
   * @param localName the local name of the attribute
   * @param value the value of the attribute
   * @throws IllegalStateException if the current state does not allow Attribute writing
   * @throws XMLStreamException if the namespace URI has not been bound to a prefix and
   * org.loboevolution.javax.xml.stream.isRepairingNamespaces has not been set to true
   */
  public void writeAttribute(String namespaceURI,
                             String localName,
                             String value)
    throws XMLStreamException;

  /**
   * Writes a namespace to the output stream
   * If the prefix argument to this method is the empty string,
   * "xmlns", or null this method will delegate to writeDefaultNamespace
   *
   * @param prefix the prefix to bind this namespace to
   * @param namespaceURI the uri to bind the prefix to
   * @throws IllegalStateException if the current state does not allow Namespace writing
   * @throws XMLStreamException
   */
  public void writeNamespace(String prefix, String namespaceURI)
    throws XMLStreamException;

  /**
   * Writes the default namespace to the stream
   * @param namespaceURI the uri to bind the default namespace to
   * @throws IllegalStateException if the current state does not allow Namespace writing
   * @throws XMLStreamException
   */
  public void writeDefaultNamespace(String namespaceURI)
    throws XMLStreamException;

  /**
   * Writes an xml comment with the data enclosed
   * @param data the data contained in the comment, may be null
   * @throws XMLStreamException
   */
  public void writeComment(String data)
    throws XMLStreamException;

  /**
   * Writes a processing instruction
   * @param target the target of the processing instruction, may not be null
   * @throws XMLStreamException
   */
  public void writeProcessingInstruction(String target)
    throws XMLStreamException;

  /**
   * Writes a processing instruction
   * @param target the target of the processing instruction, may not be null
   * @param data the data contained in the processing instruction, may not be null
   * @throws XMLStreamException
   */
  public void writeProcessingInstruction(String target,
                                         String data)
    throws XMLStreamException;

  /**
   * Writes a CData section
   * @param data the data contained in the CData Section, may not be null
   * @throws XMLStreamException
   */
  public void writeCData(String data)
    throws XMLStreamException;

  /**
   * Write a DTD section.  This string represents the entire doctypedecl production
   * from the XML 1.0 specification.
   *
   * @param dtd the DTD to be written
   * @throws XMLStreamException
   */
  public void writeDTD(String dtd)
    throws XMLStreamException;

  /**
   * Writes an entity reference
   * @param name the name of the entity
   * @throws XMLStreamException
   */
  public void writeEntityRef(String name)
    throws XMLStreamException;

  /**
   * Write the XML Declaration. Defaults the XML version to 1.0, and the encoding to utf-8
   * @throws XMLStreamException
   */
  public void writeStartDocument()
    throws XMLStreamException;

  /**
   * Write the XML Declaration. Defaults the XML version to 1.0
   * @param version version of the xml document
   * @throws XMLStreamException
   */
  public void writeStartDocument(String version)
    throws XMLStreamException;

  /**
   * Write the XML Declaration.  Note that the encoding parameter does
   * not set the actual encoding of the underlying output.  That must
   * be set when the instance of the XMLStreamWriter is created using the
   * XMLOutputFactory
   * @param encoding encoding of the xml declaration
   * @param version version of the xml document
   * @throws XMLStreamException If given encoding does not match encoding
   * of the underlying stream
   */
  public void writeStartDocument(String encoding,
                                 String version)
    throws XMLStreamException;

  /**
   * Write text to the output
   * @param text the value to write
   * @throws XMLStreamException
   */
  public void writeCharacters(String text)
    throws XMLStreamException;

  /**
   * Write text to the output
   * @param text the value to write
   * @param start the starting position in the array
   * @param len the number of characters to write
   * @throws XMLStreamException
   */
  public void writeCharacters(char[] text, int start, int len)
    throws XMLStreamException;

  /**
   * Gets the prefix the uri is bound to
   * @return the prefix or null
   * @throws XMLStreamException
   */
  public String getPrefix(String uri)
    throws XMLStreamException;

  /**
   * Sets the prefix the uri is bound to.  This prefix is bound
   * in the scope of the current START_ELEMENT / END_ELEMENT pair.
   * If this method is called before a START_ELEMENT has been written
   * the prefix is bound in the root scope.
   * @param prefix the prefix to bind to the uri, may not be null
   * @param uri the uri to bind to the prefix, may be null
   * @throws XMLStreamException
   */
  public void setPrefix(String prefix, String uri)
    throws XMLStreamException;


  /**
   * Binds a URI to the default namespace
   * This URI is bound
   * in the scope of the current START_ELEMENT / END_ELEMENT pair.
   * If this method is called before a START_ELEMENT has been written
   * the uri is bound in the root scope.
   * @param uri the uri to bind to the default namespace, may be null
   * @throws XMLStreamException
   */
  public void setDefaultNamespace(String uri)
    throws XMLStreamException;

  /**
   * Sets the current namespace context for prefix and uri bindings.
   * This context becomes the root namespace context for writing and
   * will replace the current root namespace context.  Subsequent calls
   * to setPrefix and setDefaultNamespace will bind namespaces using
   * the context passed to the method as the root context for resolving
   * namespaces.  This method may only be called once at the start of
   * the document.  It does not cause the namespaces to be declared.
   * If a namespace URI to prefix mapping is found in the namespace
   * context it is treated as declared and the prefix may be used
   * by the StreamWriter.
   * @param context the namespace context to use for this writer, may not be null
   * @throws XMLStreamException
   */
  public void setNamespaceContext(NamespaceContext context)
    throws XMLStreamException;

  /**
   * Returns the current namespace context.
   * @return the current NamespaceContext
   */
  public NamespaceContext getNamespaceContext();

  /**
   * Get the value of a feature/property from the underlying implementation
   * @param name The name of the property, may not be null
   * @return The value of the property
   * @throws IllegalArgumentException if the property is not supported
   * @throws NullPointerException if the name is null
   */
  public Object getProperty(java.lang.String name) throws IllegalArgumentException;

}
