/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.apache.xpath;

import org.loboevolution.javax.xml.transform.SourceLocator;
import org.loboevolution.javax.xml.transform.TransformerException;

/**
 * This class implements an exception object that all XPath classes will throw in case of an error.
 * This class extends TransformerException, and may hold other exceptions. In the case of nested
 * exceptions, printStackTrace will dump all the traces of the nested exceptions, not just the trace
 * of this object.
 */
public class XPathException extends TransformerException {

  /**
   * Create an XPathException object that holds an error message.
   *
   * @param message The error message.
   */
  public XPathException(String message, SourceLocator ex) {
    super(message);
    this.setLocator(ex);
  }

  /**
   * Create an XPathException object that holds an error message.
   *
   * @param message The error message.
   */
  public XPathException(String message) {
    super(message);
  }

  /** {@inheritDoc} */
  @Override
  public void printStackTrace(java.io.PrintStream s) {

    if (s == null) s = System.err;

    try {
      super.printStackTrace(s);
    } catch (Exception e) {
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getMessage() {
    String lastMessage = super.getMessage();
    return (null != lastMessage) ? lastMessage : "";
  }

  /** {@inheritDoc} */
  @Override
  public void printStackTrace(java.io.PrintWriter s) {

    if (s == null) s = new java.io.PrintWriter(System.err);

    try {
      super.printStackTrace(s);
    } catch (Exception e) {
    }
  }

  /** {@inheritDoc} */
  @Override
  public Throwable getException() {
    return null;
  }
}
