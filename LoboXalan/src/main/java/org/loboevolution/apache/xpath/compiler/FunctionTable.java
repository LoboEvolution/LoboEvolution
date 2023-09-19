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
package org.loboevolution.apache.xpath.compiler;

import java.util.HashMap;
import org.loboevolution.javax.xml.transform.TransformerException;
import org.loboevolution.apache.xpath.functions.Function;

/** The function table for XPath. */
public class FunctionTable {

  /** The 'current()' id. */
  public static final int FUNC_CURRENT = 0;

  /** The 'last()' id. */
  public static final int FUNC_LAST = 1;

  /** The 'position()' id. */
  public static final int FUNC_POSITION = 2;

  /** The 'count()' id. */
  public static final int FUNC_COUNT = 3;

  /** The 'id()' id. */
  public static final int FUNC_ID = 4;

  /** The 'local-name()' id. */
  public static final int FUNC_LOCAL_PART = 7;

  /** The 'namespace-uri()' id. */
  public static final int FUNC_NAMESPACE = 8;

  /** The 'name()' id. */
  public static final int FUNC_QNAME = 9;

  /** The 'not()' id. */
  public static final int FUNC_NOT = 11;

  /** The 'true()' id. */
  public static final int FUNC_TRUE = 12;

  /** The 'false()' id. */
  public static final int FUNC_FALSE = 13;

  /** The 'boolean()' id. */
  public static final int FUNC_BOOLEAN = 14;

  /** The 'number()' id. */
  public static final int FUNC_NUMBER = 15;

  /** The 'floor()' id. */
  public static final int FUNC_FLOOR = 16;

  /** The 'ceiling()' id. */
  public static final int FUNC_CEILING = 17;

  /** The 'round()' id. */
  public static final int FUNC_ROUND = 18;

  /** The 'sum()' id. */
  public static final int FUNC_SUM = 19;

  /** The 'string()' id. */
  public static final int FUNC_STRING = 20;

  /** The 'starts-with()' id. */
  public static final int FUNC_STARTS_WITH = 21;

  /** The 'contains()' id. */
  public static final int FUNC_CONTAINS = 22;

  /** The 'substring-before()' id. */
  public static final int FUNC_SUBSTRING_BEFORE = 23;

  /** The 'substring-after()' id. */
  public static final int FUNC_SUBSTRING_AFTER = 24;

  /** The 'normalize-space()' id. */
  public static final int FUNC_NORMALIZE_SPACE = 25;

  /** The 'translate()' id. */
  public static final int FUNC_TRANSLATE = 26;

  /** The 'concat()' id. */
  public static final int FUNC_CONCAT = 27;

  /** The 'substring()' id. */
  public static final int FUNC_SUBSTRING = 29;

  /** The 'string-length()' id. */
  public static final int FUNC_STRING_LENGTH = 30;

  /** The 'lang()' id. */
  public static final int FUNC_LANG = 32;

  /** The function table. */
  private static final Class<?>[] m_functions;

  /** Table of function name to function ID associations. */
  private static final HashMap<String, Integer> m_functionID = new HashMap<>();

  /** The function table contains customized functions */
  private final Class<?>[] m_functions_customer = new Class[NUM_ALLOWABLE_ADDINS];

  /** Table of function name to function ID associations for customized functions */
  private final HashMap<String, Integer> m_functionID_customer = new HashMap<>();

  /** Number of built in functions. Be sure to update this as built-in functions are added. */
  private static final int NUM_BUILT_IN_FUNCS = 37;

  /** Number of built-in functions that may be added. */
  private static final int NUM_ALLOWABLE_ADDINS = 30;

  /** The index to the next free function index. */
  private int m_funcNextFreeIndex = NUM_BUILT_IN_FUNCS;

  static {
    m_functions = new Class[NUM_BUILT_IN_FUNCS];
    // m_functions[FUNC_CURRENT] =
    // org.loboevolution.apache.xpath.functions.FuncCurrent.class;
    m_functions[FUNC_LAST] = org.loboevolution.apache.xpath.functions.FuncLast.class;
    m_functions[FUNC_POSITION] = org.loboevolution.apache.xpath.functions.FuncPosition.class;
    m_functions[FUNC_COUNT] = org.loboevolution.apache.xpath.functions.FuncCount.class;
    m_functions[FUNC_ID] = org.loboevolution.apache.xpath.functions.FuncId.class;
    // m_functions[FUNC_KEY] =
    // org.apache.xalan.templates.FuncKey.class;
    m_functions[FUNC_LOCAL_PART] = org.loboevolution.apache.xpath.functions.FuncLocalPart.class;
    m_functions[FUNC_NAMESPACE] = org.loboevolution.apache.xpath.functions.FuncNamespace.class;
    m_functions[FUNC_QNAME] = org.loboevolution.apache.xpath.functions.FuncQname.class;
    m_functions[FUNC_NOT] = org.loboevolution.apache.xpath.functions.FuncNot.class;
    m_functions[FUNC_TRUE] = org.loboevolution.apache.xpath.functions.FuncTrue.class;
    m_functions[FUNC_FALSE] = org.loboevolution.apache.xpath.functions.FuncFalse.class;
    m_functions[FUNC_BOOLEAN] = org.loboevolution.apache.xpath.functions.FuncBoolean.class;
    m_functions[FUNC_LANG] = org.loboevolution.apache.xpath.functions.FuncLang.class;
    m_functions[FUNC_NUMBER] = org.loboevolution.apache.xpath.functions.FuncNumber.class;
    m_functions[FUNC_FLOOR] = org.loboevolution.apache.xpath.functions.FuncFloor.class;
    m_functions[FUNC_CEILING] = org.loboevolution.apache.xpath.functions.FuncCeiling.class;
    m_functions[FUNC_ROUND] = org.loboevolution.apache.xpath.functions.FuncRound.class;
    m_functions[FUNC_SUM] = org.loboevolution.apache.xpath.functions.FuncSum.class;
    m_functions[FUNC_STRING] = org.loboevolution.apache.xpath.functions.FuncString.class;
    m_functions[FUNC_STARTS_WITH] = org.loboevolution.apache.xpath.functions.FuncStartsWith.class;
    m_functions[FUNC_CONTAINS] = org.loboevolution.apache.xpath.functions.FuncContains.class;
    m_functions[FUNC_SUBSTRING_BEFORE] =
        org.loboevolution.apache.xpath.functions.FuncSubstringBefore.class;
    m_functions[FUNC_SUBSTRING_AFTER] =
        org.loboevolution.apache.xpath.functions.FuncSubstringAfter.class;
    m_functions[FUNC_NORMALIZE_SPACE] =
        org.loboevolution.apache.xpath.functions.FuncNormalizeSpace.class;
    m_functions[FUNC_TRANSLATE] = org.loboevolution.apache.xpath.functions.FuncTranslate.class;
    m_functions[FUNC_CONCAT] = org.loboevolution.apache.xpath.functions.FuncConcat.class;
    m_functions[FUNC_SUBSTRING] = org.loboevolution.apache.xpath.functions.FuncSubstring.class;
    m_functions[FUNC_STRING_LENGTH] =
        org.loboevolution.apache.xpath.functions.FuncStringLength.class;
  }

  static {
    m_functionID.put(Keywords.FUNC_CURRENT_STRING, new Integer(FunctionTable.FUNC_CURRENT));
    m_functionID.put(Keywords.FUNC_LAST_STRING, new Integer(FunctionTable.FUNC_LAST));
    m_functionID.put(Keywords.FUNC_POSITION_STRING, new Integer(FunctionTable.FUNC_POSITION));
    m_functionID.put(Keywords.FUNC_COUNT_STRING, new Integer(FunctionTable.FUNC_COUNT));
    m_functionID.put(Keywords.FUNC_ID_STRING, new Integer(FunctionTable.FUNC_ID));
    m_functionID.put(Keywords.FUNC_LOCAL_PART_STRING, new Integer(FunctionTable.FUNC_LOCAL_PART));
    m_functionID.put(Keywords.FUNC_NAMESPACE_STRING, new Integer(FunctionTable.FUNC_NAMESPACE));
    m_functionID.put(Keywords.FUNC_NAME_STRING, new Integer(FunctionTable.FUNC_QNAME));
    m_functionID.put(Keywords.FUNC_NOT_STRING, new Integer(FunctionTable.FUNC_NOT));
    m_functionID.put(Keywords.FUNC_TRUE_STRING, new Integer(FunctionTable.FUNC_TRUE));
    m_functionID.put(Keywords.FUNC_FALSE_STRING, new Integer(FunctionTable.FUNC_FALSE));
    m_functionID.put(Keywords.FUNC_BOOLEAN_STRING, new Integer(FunctionTable.FUNC_BOOLEAN));
    m_functionID.put(Keywords.FUNC_LANG_STRING, new Integer(FunctionTable.FUNC_LANG));
    m_functionID.put(Keywords.FUNC_NUMBER_STRING, new Integer(FunctionTable.FUNC_NUMBER));
    m_functionID.put(Keywords.FUNC_FLOOR_STRING, new Integer(FunctionTable.FUNC_FLOOR));
    m_functionID.put(Keywords.FUNC_CEILING_STRING, new Integer(FunctionTable.FUNC_CEILING));
    m_functionID.put(Keywords.FUNC_ROUND_STRING, new Integer(FunctionTable.FUNC_ROUND));
    m_functionID.put(Keywords.FUNC_SUM_STRING, new Integer(FunctionTable.FUNC_SUM));
    m_functionID.put(Keywords.FUNC_STRING_STRING, new Integer(FunctionTable.FUNC_STRING));
    m_functionID.put(Keywords.FUNC_STARTS_WITH_STRING, new Integer(FunctionTable.FUNC_STARTS_WITH));
    m_functionID.put(Keywords.FUNC_CONTAINS_STRING, new Integer(FunctionTable.FUNC_CONTAINS));
    m_functionID.put(
        Keywords.FUNC_SUBSTRING_BEFORE_STRING, new Integer(FunctionTable.FUNC_SUBSTRING_BEFORE));
    m_functionID.put(
        Keywords.FUNC_SUBSTRING_AFTER_STRING, new Integer(FunctionTable.FUNC_SUBSTRING_AFTER));
    m_functionID.put(
        Keywords.FUNC_NORMALIZE_SPACE_STRING, new Integer(FunctionTable.FUNC_NORMALIZE_SPACE));
    m_functionID.put(Keywords.FUNC_TRANSLATE_STRING, new Integer(FunctionTable.FUNC_TRANSLATE));
    m_functionID.put(Keywords.FUNC_CONCAT_STRING, new Integer(FunctionTable.FUNC_CONCAT));
    m_functionID.put(Keywords.FUNC_SUBSTRING_STRING, new Integer(FunctionTable.FUNC_SUBSTRING));
    m_functionID.put(
        Keywords.FUNC_STRING_LENGTH_STRING, new Integer(FunctionTable.FUNC_STRING_LENGTH));
  }

  public FunctionTable() {}

  /**
   * Return the name of the a function in the static table. Needed to avoid making the table
   * publicly available.
   */
  String getFunctionName(int funcID) {
    if (funcID < NUM_BUILT_IN_FUNCS) {
      return m_functions[funcID].getSimpleName();
    }
    return m_functions_customer[funcID - NUM_BUILT_IN_FUNCS].getName();
  }

  /**
   * Obtain a new Function object from a function ID.
   *
   * @param which The function ID, which may correspond to one of the FUNC_XXX values found in
   *     {@link org.loboevolution.apache.xpath.compiler.FunctionTable}, but may be a value installed
   *     by an external module.
   * @return a a new Function instance.
   * @throws TransformerException if ClassNotFoundException,
   *     IllegalAccessException, or InstantiationException is thrown.
   */
  Function getFunction(int which) throws TransformerException {
    try {
      if (which < NUM_BUILT_IN_FUNCS) return (Function) m_functions[which].newInstance();
      else return (Function) m_functions_customer[which - NUM_BUILT_IN_FUNCS].newInstance();
    } catch (IllegalAccessException | InstantiationException ex) {
      throw new TransformerException(ex.getMessage());
    }
  }

  /**
   * Obtain a function ID from a given function name
   *
   * @param key the function name in a java.lang.String format.
   * @return a function ID, which may correspond to one of the FUNC_XXX values found in {@link
   *     org.loboevolution.apache.xpath.compiler.FunctionTable}, but may be a value installed by an
   *     external module.
   */
  Object getFunctionID(String key) {
    Object id = m_functionID_customer.get(key);
    if (null == id) id = m_functionID.get(key);
    return id;
  }

  /**
   * Install a built-in function.
   *
   * @param name The unqualified name of the function, must not be null
   * @param func A Implementation of an XPath Function object.
   * @return the position of the function in the internal index.
   */
  public int installFunction(String name, Class<?> func) {

    int funcIndex;
    Object funcIndexObj = getFunctionID(name);

    if (null != funcIndexObj) {
      funcIndex = ((Integer) funcIndexObj).intValue();

      if (funcIndex < NUM_BUILT_IN_FUNCS) {
        funcIndex = m_funcNextFreeIndex++;
        m_functionID_customer.put(name, new Integer(funcIndex));
      }
      m_functions_customer[funcIndex - NUM_BUILT_IN_FUNCS] = func;
    } else {
      funcIndex = m_funcNextFreeIndex++;

      m_functions_customer[funcIndex - NUM_BUILT_IN_FUNCS] = func;

      m_functionID_customer.put(name, new Integer(funcIndex));
    }
    return funcIndex;
  }

  /**
   * Tell if a built-in, non-namespaced function is available.
   *
   * @param methName The local name of the function.
   * @return True if the function can be executed.
   */
  public boolean functionAvailable(String methName) {
    Object tblEntry = m_functionID.get(methName);
    if (null != tblEntry) return true;
    else {
      tblEntry = m_functionID_customer.get(methName);
      return null != tblEntry;
    }
  }
}
