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
package org.loboevolution.apache.xml.dtm;

import org.loboevolution.apache.xml.dtm.ref.DTMManagerDefault;
import org.loboevolution.apache.xml.utils.PrefixResolver;
import org.loboevolution.html.node.Node;

/**
 * A DTMManager instance can be used to create DTM and DTMIterator objects, and manage the DTM
 * objects in the system.
 *
 * <p>The system property that determines which Factory implementation to create is named
 * "org.apache.xml.utils.DTMFactory". This property names a concrete subclass of the DTMFactory
 * abstract class. If the property is not defined, a platform default is be used.
 *
 * <p>An instance of this class must be safe to use across thread instances. It is expected that a
 * client will create a single instance of a DTMManager to use across multiple threads. This will
 * allow sharing of DTMs across multiple processes.
 *
 * <p>Note: this class is incomplete right now. It will be pretty much modeled after
 * org.loboevolution.javax.xml.transform.TransformerFactory in terms of its factory support.
 *
 * <p>State: In progress!!
 */
public abstract class DTMManager {
  /** Default constructor is protected on purpose. */
  protected DTMManager() {}

  /**
   * Obtain a new instance of a <code>DTMManager</code>. This static method creates a new factory
   * instance This method uses the following ordered lookup procedure to determine the <code>
   * DTMManager</code> implementation class to load:
   *
   * <ul>
   *   <li>Use the <code>org.loboevolution.apache.xml.dtm.DTMManager</code> system property.
   *   <li>Use the JAVA_HOME(the parent directory where jdk is installed)/lib/xalan.properties for a
   *       property file that contains the name of the implementation class keyed on the same value
   *       as the system property defined above.
   *   <li>Use the Services API (as detailed in the JAR specification), if available, to determine
   *       the classname. The Services API will look for a classname in the file <code>
   *       META-INF/services/org.loboevolution.apache.xml.dtm.DTMManager</code> in jars
   *       available to the runtime.
   *   <li>Use the default <code>DTMManager</code> classname, which is <code>
   *       org.loboevolution.apache.xml.ref.dtm.DTMManagerDefault</code>.
   * </ul>
   *
   * Once an application has obtained a reference to a <code>
   * DTMManager</code> it can use the factory to configure and obtain parser instances.
   *
   * @return new DTMManager instance, never null.
   */
  public static DTMManager newInstance() {
    return new DTMManagerDefault();
  }

  /**
   * Get an instance of a DTM, loaded with the content from the specified source. If the unique flag
   * is true, a new instance will always be returned. Otherwise it is up to the DTMManager to return
   * a new instance or an instance that it already created and may be being used by someone else.
   *
   * <p>(More parameters may eventually need to be added for error handling and entity resolution,
   * and to better control selection of implementations.)
   *
   * @param source the specification of the source object, which may be null, in which case it is
   *     assumed that node construction will take by some other means.
   * @param unique true if the returned DTM must be unique, probably because it is going to be
   *     mutated.
   * @param incremental true if the DTM should be built incrementally, if possible.
   * @param doIndexing true if the caller considers it worth it to use indexing schemes.
   * @return a non-null DTM reference.
   */
  public abstract DTM getDTM(
      org.loboevolution.javax.xml.transform.Source source, boolean unique, boolean incremental, boolean doIndexing);

  /**
   * Get the instance of DTM that "owns" a node handle.
   *
   * @param nodeHandle the nodeHandle.
   * @return a non-null DTM reference.
   */
  public abstract DTM getDTM(int nodeHandle);

  /**
   * Given a W3C DOM node, try and return a DTM handle. Note: calling this may be non-optimal.
   *
   * @param node Non-null reference to a DOM node.
   * @return a valid DTM handle.
   */
  public abstract int getDTMHandleFromNode(Node node);

  /**
   * Create a new <code>DTMIterator</code> based on an XPath <a
   * href="http://www.w3.org/TR/xpath#NT-LocationPath">LocationPath</a> or a <a
   * href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.
   *
   * @param xpathCompiler ??? Somehow we need to pass in a subpart of the expression. I hate to do
   *     this with strings, since the larger expression has already been parsed.
   * @param pos The position in the expression.
   * @return The newly created <code>DTMIterator</code>.
   */
  public abstract DTMIterator createDTMIterator(Object xpathCompiler, int pos);

  /**
   * Create a new <code>DTMIterator</code> based on an XPath <a
   * href="http://www.w3.org/TR/xpath#NT-LocationPath">LocationPath</a> or a <a
   * href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.
   *
   * @param xpathString Must be a valid string expressing a <a
   *     href="http://www.w3.org/TR/xpath#NT-LocationPath">LocationPath</a> or a <a
   *     href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.
   * @param presolver An object that can resolve prefixes to namespace URLs.
   * @return The newly created <code>DTMIterator</code>.
   */
  public abstract DTMIterator createDTMIterator(String xpathString, PrefixResolver presolver);

  /**
   * Create a new <code>DTMIterator</code> based only on a whatToShow and a DTMFilter. The traversal
   * semantics are defined as the descendant access.
   *
   * <p>Note that DTMIterators may not be an exact match to DOM NodeIterators. They are initialized
   * and used in much the same way as a NodeIterator, but their response to document mutation is not
   * currently defined.
   *
   * @param whatToShow This flag specifies which node types may appear in the logical view of the
   *     tree presented by the iterator. See the description of <code>NodeFilter</code> for the set
   *     of possible <code>SHOW_</code> values.These flags can be combined using <code>OR
   *     </code> .
   * @param filter The <code>NodeFilter</code> to be used with this <code>DTMFilter</code>, or
   *     <code>null</code> to indicate no filter.
   * @param entityReferenceExpansion The value of this flag determines whether entity reference
   *     nodes are expanded.
   * @return The newly created <code>DTMIterator</code>.
   */
  public abstract DTMIterator createDTMIterator(
      int whatToShow, DTMFilter filter, boolean entityReferenceExpansion);

  // -------------------- private methods --------------------

  static {
    try {
      /* Temp debug code - this will be removed after we test everything */
      boolean debug = System.getProperty("dtm.debug") != null;
    } catch (SecurityException ex) {
    }
  }

  /**
   * This value, set at compile time, controls how many bits of the DTM node identifier numbers are
   * used to identify a node within a document, and thus sets the maximum number of nodes per
   * document. The remaining bits are used to identify the DTM document which contains this node.
   *
   * <p>If you change IDENT_DTM_NODE_BITS, be sure to rebuild _ALL_ the files which use it...
   * including the IDKey testcases.
   *
   * <p>(FuncGenerateKey currently uses the node identifier directly and thus is affected when this
   * changes. The IDKEY results will still be _correct_ (presuming no other breakage), but simple
   * equality comparison against the previous "golden" files will probably complain.)
   */
  public static final int IDENT_DTM_NODE_BITS = 16;

  /**
   * When this bitmask is ANDed with a DTM node handle number, the result is the low bits of the
   * node's index number within that DTM. To obtain the high bits, add the DTM ID portion's offset
   * as assigned in the DTM Manager.
   */
  public static final int IDENT_NODE_DEFAULT = (1 << IDENT_DTM_NODE_BITS) - 1;

  /**
   * When this bitmask is ANDed with a DTM node handle number, the result is the DTM's document
   * identity number.
   */
  public static final int IDENT_DTM_DEFAULT = ~IDENT_NODE_DEFAULT;

  /** This is the maximum number of DTMs available. The highest DTM is one less than this. */
  public static final int IDENT_MAX_DTMS = (IDENT_DTM_DEFAULT >>> IDENT_DTM_NODE_BITS) + 1;
}
