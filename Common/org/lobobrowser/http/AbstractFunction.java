/*
 * AbstractFunction.java
 *
 * Created on September 27, 2006, 10:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.lobobrowser.http;

import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author richardallenbair
 */
public abstract class AbstractFunction implements XPathFunction {
    private int arity;
    private String name;
    
    protected AbstractFunction(String name, int arity) {
        if (name == null) {
            throw new NullPointerException();
        }
        if (arity < 0) {
            throw new IllegalArgumentException("Arity must be positive");
        }
        this.arity = arity;
        this.name = name;
    }
    
    public final int getArity() {
        return arity;
    }
    
    public final String getName() {
        return name;
    }
    
    protected String getStringParam(Object o) throws XPathFunctionException {
        // perform conversions
        String output = null;
        if (o instanceof String) output = (String) o;
        else if (o instanceof Boolean) output = o.toString();
        else if (o instanceof Double) output = o.toString();
        else if (o instanceof NodeList) {
            NodeList list = (NodeList) o;
            Node node = list.item(0);
            output= node.getTextContent();
        } else {
            throw new XPathFunctionException("Could not convert argument type");
        }
        return output;
    }
    
    protected Number getNumberParam(Object o) throws XPathFunctionException {
        // perform conversions
        Number output = null;
        try {
            if (o instanceof String) output = Double.parseDouble((String) o);
            else if (o instanceof Double) output = (Double)o;
            else if (o instanceof NodeList) {
                NodeList list = (NodeList) o;
                Node node = list.item(0);
                output = Double.parseDouble(node.getTextContent());
            } else {
                throw new XPathFunctionException("Could not convert argument type");
            }
        } catch (NumberFormatException ex) {
            throw new XPathFunctionException(ex);
        }
        return output;
    }
}
