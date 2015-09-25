/*
 * EndsWith.java Created on September 27, 2006, 10:09 PM To change this
 * template, choose Tools | Template Manager and open the template in the
 * editor.
 */
package org.lobobrowser.xpath.function;

import java.util.List;

import javax.xml.xpath.XPathFunctionException;

/**
 * The Class EndsWith.
 *
 * @author richardallenbair
 */
public class EndsWith extends AbstractFunction {
    /**
     * Creates a new instance of EndsWith.
     */
    public EndsWith() {
        super("ends-with", 2);
    }
    
    /*
     * (non-Javadoc)
     * @see javax.xml.xpath.XPathFunction#evaluate(java.util.List)
     */
    @Override
    public Object evaluate(List args) throws XPathFunctionException {
        String s1 = getStringParam(args.get(0));
        String s2 = getStringParam(args.get(1));
        return s1.endsWith(s2);
    }
}
