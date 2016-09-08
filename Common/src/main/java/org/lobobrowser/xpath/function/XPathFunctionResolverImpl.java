/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.xpath.function;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionResolver;

/**
 * The Class XPathFunctionResolverImpl.
 *
 * @author richardallenbair
 */
public class XPathFunctionResolverImpl
        implements XPathFunctionResolver, NamespaceContext
{
    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "http://swinglabs.org/xpath/fn";
    /** The functions. */
    private static Map<Signature, XPathFunction> functions = new HashMap<Signature, XPathFunction>();
    /** The namespaces. */
    private static Map<String, String> namespaces = new HashMap<String, String>();
    
    static {
        installFunction(new Abs());
        installFunction(new EndsWith());
        installFunction(new EscapeUri());
        installFunction(new LowerCase());
        installFunction(new Matches());
        installFunction(new Replace());
        installFunction(new UpperCase());
        installFunction(new EqualsIgnoreCase());
    }
    
    /*
     * (non-Javadoc)
     * @see
     * javax.xml.xpath.XPathFunctionResolver#resolveFunction(javax.xml.namespace
     * .QName, int)
     */
    @Override
    public XPathFunction resolveFunction(QName functionName, int arity) {
        return functions.get(new Signature(functionName, arity));
    }
    
    /**
     * Install function.
     *
     * @param function
     *            the function
     */
    public static void installFunction(AbstractFunction function) {
        functions.put(new Signature(new QName(NAMESPACE, function.getName()),
                function.getArity()), function);
    }
    
    /*
     * (non-Javadoc)
     * @see
     * javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
     */
    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("Null prefix");
        } else if ("fn".equals(prefix)) {
            return NAMESPACE;
        } else if ("xml".equals(prefix)) {
            return XMLConstants.XML_NS_URI;
        }
        prefix = prefix.intern();

        if (namespaces.containsKey(prefix)) {
            return namespaces.get(prefix);
        }
        return XMLConstants.NULL_NS_URI;
    }
    
    // This method isn't necessary for XPath processing.
    /*
     * (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
     */
    @Override
    public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }
    
    // This method isn't necessary for XPath processing either.
    /*
     * (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
     */
    @Override
    public Iterator getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Adds the namespace mapping.
     *
     * @param namespacePrefix
     *            the namespace prefix
     * @param namespace
     *            the namespace
     */
    public void addNamespaceMapping(String namespacePrefix, String namespace) {
        namespaces.put(namespacePrefix.intern(), namespace.intern());
    }
    
    /**
     * Removes the namespace mapping.
     *
     * @param namespacePrefix
     *            the namespace prefix
     */
    public void removeNamespaceMapping(String namespacePrefix) {
        namespaces.remove(namespacePrefix.intern());
    }
    
    /**
     * The Class Signature.
     */
    private static final class Signature {
        /** The function name. */
        private QName functionName;
        /** The arity. */
        private int arity;
        
        /**
         * Instantiates a new signature.
         *
         * @param functionName
         *            the function name
         * @param arity
         *            the arity
         */
        public Signature(QName functionName, int arity) {
            this.functionName = functionName;
            this.arity = arity;
        }
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object o) {
            if (o instanceof Signature) {
                Signature s = (Signature) o;
                return (s.arity == arity)
                        && s.functionName.equals(functionName);
            }
            return false;
        }
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return functionName.hashCode() + arity;
        }
    }
}
