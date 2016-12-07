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

import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class AbstractFunction.
 *
 * @author richardallenbair
 */
public abstract class AbstractFunction implements XPathFunction {
    /** The arity. */
    private int arity;
    /** The name. */
    private String name;
    
    /**
     * Instantiates a new abstract function.
     *
     * @param name
     *            the name
     * @param arity
     *            the arity
     */
    protected AbstractFunction(final String name, final int arity) {
        if (name == null) {
            throw new NullPointerException();
        }
        if (arity < 0) {
            throw new IllegalArgumentException("Arity must be positive");
        }
        this.arity = arity;
        this.name = name;
    }
    
     /** Gets the arity.
	 *
	 * @return the arity
	 */
    public final int getArity() {
        return arity;
    }
    
     /** Gets the name.
	 *
	 * @return the name
	 */
    public final String getName() {
        return name;
    }
    
    /**
     * Gets the string param.
     *
     * @param o
     *            the o
     * @return the string param
     * @throws XPathFunctionException
     *             the x path function exception
     */
    protected String getStringParam(final Object o) throws XPathFunctionException {
        // perform conversions
        String output = null;
        if (o instanceof String) {
            output = (String) o;
        } else if (o instanceof Boolean) {
            output = o.toString();
        } else if (o instanceof Double) {
            output = o.toString();
        } else if (o instanceof NodeList) {
            NodeList list = (NodeList) o;
            Node node = list.item(0);
            output = node.getTextContent();
        } else {
            throw new XPathFunctionException("Could not convert argument type");
        }
        return output;
    }
    
    /**
     * Gets the number param.
     *
     * @param o
     *            the o
     * @return the number param
     * @throws XPathFunctionException
     *             the x path function exception
     */
    protected Number getNumberParam(final Object o) throws XPathFunctionException {
        // perform conversions
        Number output = null;
        try {
            if (o instanceof String) {
                output = Double.parseDouble((String) o);
            } else if (o instanceof Double) {
                output = (Double) o;
            } else if (o instanceof NodeList) {
                NodeList list = (NodeList) o;
                Node node = list.item(0);
                output = Double.parseDouble(node.getTextContent());
            } else {
                throw new XPathFunctionException(
                        "Could not convert argument type");
            }
        } catch (NumberFormatException ex) {
            throw new XPathFunctionException(ex);
        }
        return output;
    }
}
