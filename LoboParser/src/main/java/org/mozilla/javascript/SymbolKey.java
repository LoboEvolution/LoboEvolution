package org.mozilla.javascript;

import java.io.Serializable;

/**
 * A SymbolKey is one of the implementations of Symbol. It is really there
 * so that we can easily use pre-defined symbols as keys in native code.
 * A SymbolKey has the special property that two NativeSymbol objects
 * with the same key are equal.
 *
 * @author utente
 * @version $Id: $Id
 */
public class SymbolKey
    implements Symbol, Serializable
{
    private static final long serialVersionUID = -6019782713330994754L;

    // These are common SymbolKeys that are equivalent to well-known symbols
    // defined in ECMAScript.
    /** Constant ITERATOR */
    public static final SymbolKey ITERATOR = new SymbolKey("Symbol.iterator");
    /** Constant TO_STRING_TAG */
    public static final SymbolKey TO_STRING_TAG = new SymbolKey("Symbol.toStringTag");
    /** Constant SPECIES */
    public static final SymbolKey SPECIES = new SymbolKey("Symbol.species");
    /** Constant HAS_INSTANCE */
    public static final SymbolKey HAS_INSTANCE = new SymbolKey("Symbol.hasInstance");
    /** Constant IS_CONCAT_SPREADABLE */
    public static final SymbolKey IS_CONCAT_SPREADABLE = new SymbolKey("Symbol.isConcatSpreadable");
    /** Constant IS_REGEXP */
    public static final SymbolKey IS_REGEXP = new SymbolKey("Symbol.isRegExp");
    /** Constant TO_PRIMITIVE */
    public static final SymbolKey TO_PRIMITIVE = new SymbolKey("Symbol.toPrimitive");
    /** Constant MATCH */
    public static final SymbolKey MATCH = new SymbolKey("Symbol.match");
    /** Constant REPLACE */
    public static final SymbolKey REPLACE = new SymbolKey("Symbol.replace");
    /** Constant SEARCH */
    public static final SymbolKey SEARCH = new SymbolKey("Symbol.search");
    /** Constant SPLIT */
    public static final SymbolKey SPLIT = new SymbolKey("Symbol.split");
    /** Constant UNSCOPABLES */
    public static final SymbolKey UNSCOPABLES = new SymbolKey("Symbol.unscopables");

    private String name;

    /**
     * <p>Constructor for SymbolKey.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public SymbolKey(String name)
    {
        this.name = name;
    }

    /**
     * <p>Getter for the field name.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode()
    {
        return System.identityHashCode(this);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof SymbolKey) {
            return o == this;
        }
        if (o instanceof NativeSymbol) {
            return ((NativeSymbol) o).getKey() == this;
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String toString()
    {
        if (name == null) {
            return "Symbol()";
        }
        return "Symbol(" + name + ')';
    }
}
