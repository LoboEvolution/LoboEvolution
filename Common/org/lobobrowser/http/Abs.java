/*
 * EndsWith.java
 *
 * Created on September 27, 2006, 10:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.lobobrowser.http;

import java.util.List;

import javax.xml.xpath.XPathFunctionException;


/**
 * The Class Abs.
 *
 * @author richardallenbair
 */
public class Abs extends AbstractFunction {
    
    /**
     *  Creates a new instance of EndsWith.
     */
    public Abs() {
        super("abs", 1);
    }

    /* (non-Javadoc)
     * @see javax.xml.xpath.XPathFunction#evaluate(java.util.List)
     */
    public Object evaluate(List args) throws XPathFunctionException {
        return Math.abs(getNumberParam(args.get(0)).doubleValue());
    }
}
