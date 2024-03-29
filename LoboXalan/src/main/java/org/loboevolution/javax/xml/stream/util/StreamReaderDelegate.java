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

package org.loboevolution.javax.xml.stream.util;

import org.loboevolution.javax.xml.namespace.QName;
import org.loboevolution.javax.xml.namespace.NamespaceContext;
import org.loboevolution.javax.xml.stream.XMLStreamReader;
import org.loboevolution.javax.xml.stream.Location;
import org.loboevolution.javax.xml.stream.XMLStreamException;

public class StreamReaderDelegate implements XMLStreamReader {
  private XMLStreamReader reader;

  /**
   * Construct an empty filter with no parent.
   */
  public StreamReaderDelegate(){}

  /**
   * Construct an filter with the specified parent.
   * @param reader the parent
   */
  public StreamReaderDelegate(final XMLStreamReader reader) {
    this.reader = reader;
  }

  /**
   * Set the parent of this instance.
   * @param reader the new parent
   */
  public void setParent(final XMLStreamReader reader) {
    this.reader = reader;
  }

  /**
   * Get the parent of this instance.
   * @return the parent or null if none is set
   */
  public XMLStreamReader getParent() {
    return reader;
  }

  public int next()
    throws XMLStreamException
  {
    return reader.next();
  }

  public int nextTag()
    throws XMLStreamException
  {
    return reader.nextTag();
  }

  public String getElementText()
    throws XMLStreamException
  {
    return reader.getElementText();
  }

  public void require(final int type, final String namespaceURI, final String localName)
    throws XMLStreamException
  {
    reader.require(type,namespaceURI,localName);
  }

  public boolean hasNext()
    throws XMLStreamException
  {
    return reader.hasNext();
  }

  public void close()
    throws XMLStreamException
  {
    reader.close();
  }

  public String getNamespaceURI(final String prefix)
  {
    return reader.getNamespaceURI(prefix);
  }

  public NamespaceContext getNamespaceContext() {
    return reader.getNamespaceContext();
  }

  public boolean isStartElement() {
    return reader.isStartElement();
  }

  public boolean isEndElement() {
    return reader.isEndElement();
  }

  public boolean isCharacters() {
    return reader.isCharacters();
  }

  public boolean isWhiteSpace() {
    return reader.isWhiteSpace();
  }

  public String getAttributeValue(final String namespaceUri,
                                  final String localName)
  {
    return reader.getAttributeValue(namespaceUri,localName);
  }

  public int getAttributeCount() {
    return reader.getAttributeCount();
  }

  public QName getAttributeName(final int index) {
    return reader.getAttributeName(index);
  }

  public String getAttributePrefix(final int index) {
    return reader.getAttributePrefix(index);
  }

  public String getAttributeNamespace(final int index) {
    return reader.getAttributeNamespace(index);
  }

  public String getAttributeLocalName(final int index) {
    return reader.getAttributeLocalName(index);
  }

  public String getAttributeType(final int index) {
    return reader.getAttributeType(index);
  }

  public String getAttributeValue(final int index) {
    return reader.getAttributeValue(index);
  }

  public boolean isAttributeSpecified(final int index) {
    return reader.isAttributeSpecified(index);
  }

  public int getNamespaceCount() {
    return reader.getNamespaceCount();
  }

  public String getNamespacePrefix(final int index) {
    return reader.getNamespacePrefix(index);
  }

  public String getNamespaceURI(final int index) {
    return reader.getNamespaceURI(index);
  }

  public int getEventType() {
    return reader.getEventType();
  }

  public String getText() {
    return reader.getText();
  }

  public int getTextCharacters(final int sourceStart,
                               final char[] target,
                               final int targetStart,
                               final int length)
    throws XMLStreamException {
    return reader.getTextCharacters(sourceStart,
                                    target,
                                    targetStart,
                                    length);
  }


  public char[] getTextCharacters() {
    return reader.getTextCharacters();
  }

  public int getTextStart() {
    return reader.getTextStart();
  }

  public int getTextLength() {
    return reader.getTextLength();
  }

  public String getEncoding() {
    return reader.getEncoding();
  }

  public boolean hasText() {
    return reader.hasText();
  }

  public Location getLocation() {
    return reader.getLocation();
  }

  public QName getName() {
    return reader.getName();
  }

  public String getLocalName() {
    return reader.getLocalName();
  }

  public boolean hasName() {
    return reader.hasName();
  }

  public String getNamespaceURI() {
    return reader.getNamespaceURI();
  }

  public String getPrefix() {
    return reader.getPrefix();
  }

  public String getVersion() {
    return reader.getVersion();
  }

  public boolean isStandalone() {
    return reader.isStandalone();
  }

  public boolean standaloneSet() {
    return reader.standaloneSet();
  }

  public String getCharacterEncodingScheme() {
    return reader.getCharacterEncodingScheme();
  }

  public String getPITarget() {
    return reader.getPITarget();
  }

  public String getPIData() {
    return reader.getPIData();
  }

  public Object getProperty(final String name) {
    return reader.getProperty(name);
  }
}
