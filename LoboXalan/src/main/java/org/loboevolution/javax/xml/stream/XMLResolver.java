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

public interface XMLResolver {

  /**
   * Retrieves a resource.  This resource can be of the following three return types:
   * (1) java.io.InputStream (2) org.loboevolution.javax.xml.stream.XMLStreamReader (3) java.xml.stream.XMLEventReader.
   * If this method returns null the processor will attempt to resolve the entity using its
   * default mechanism.
   *
   * @param publicID The public identifier of the external entity being referenced, or null if none was supplied.
   * @param systemID The system identifier of the external entity being referenced.
   * @param baseURI  Absolute base URI associated with systemId.
   * @param namespace The namespace of the entity to resolve.
   * @return The resource requested or null.
   * @throws XMLStreamException if there was a failure attempting to resolve the resource.
   */
  public Object resolveEntity(String publicID,
                              String systemID,
                              String baseURI,
                              String namespace)
    throws XMLStreamException;
}
