/*
 * ExtendedMethodResolver.java
 *
 * Created on September 27, 2006, 10:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.lobobrowser.http;

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
public class XPathFunctionResolverImpl implements XPathFunctionResolver, NamespaceContext {
    
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
    
    /* (non-Javadoc)
     * @see javax.xml.xpath.XPathFunctionResolver#resolveFunction(javax.xml.namespace.QName, int)
     */
    public XPathFunction resolveFunction(QName functionName, int arity) {
        return functions.get(new Signature(functionName, arity));
    }
    
    /**
     * Install function.
     *
     * @param function the function
     */
    public static void installFunction(AbstractFunction function) {
        functions.put(new Signature(new QName(NAMESPACE, function.getName()),
                function.getArity()), function);
    }
    
    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
     */
    public String getNamespaceURI(String prefix) {
        if (prefix == null) throw new NullPointerException("Null prefix");
        else if ("fn".equals(prefix)) return NAMESPACE;
        else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
        
        prefix = prefix.intern();
        System.out.println("looking for prefix: " + prefix);
        for(String s : namespaces.keySet()) {
            System.out.println(s);
        }
        
        if (namespaces.containsKey(prefix)) {
            System.out.println("it contains the key: " + prefix);
            return namespaces.get(prefix);
        }
        
        
        return XMLConstants.NULL_NS_URI;
    }
    
    // This method isn't necessary for XPath processing.
    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
     */
    public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }
    
    // This method isn't necessary for XPath processing either.
    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
     */
    public Iterator getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Adds the namespace mapping.
     *
     * @param namespacePrefix the namespace prefix
     * @param namespace the namespace
     */
    public void addNamespaceMapping(String namespacePrefix, String namespace) {
        namespaces.put(namespacePrefix.intern(),namespace.intern());
    }
    
    /**
     * Removes the namespace mapping.
     *
     * @param namespacePrefix the namespace prefix
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
         * @param functionName the function name
         * @param arity the arity
         */
        public Signature(QName functionName, int arity) {
            this.functionName = functionName;
            this.arity = arity;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object o) {
            if (o instanceof Signature) {
                Signature s = (Signature)o;
                return s.arity == arity && s.functionName.equals(functionName);
            }
            return false;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        public int hashCode() {
            return functionName.hashCode() + arity;
        }
    }
    
    //    public static void main(String[] args) {
    //        QName name1 = new QName("http://swinglabs.org/xpath/fs", "ends-with");
    //        QName name2 = new QName("http://swinglabs.org/xpath/fs", "ends-with");
    //        System.out.println("Equals: " + name1.equals(name2));
    //        System.out.println("Hash: " + name1.hashCode() + ", " + name2.hashCode());
    //
    //        Signature s1 = new Signature(name1, 3);
    //        Signature s2 = new Signature(name2, 3);
    //        System.out.println("Equals: " + s1.equals(s2));
    //        System.out.println("Hash: " + s1.hashCode() + ", " + s2.hashCode());
    //
    //    }
}
