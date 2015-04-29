/*
 * EndsWith.java Created on September 27, 2006, 10:09 PM To change this
 * template, choose Tools | Template Manager and open the template in the
 * editor.
 */
package org.lobobrowser.http;

import java.util.List;

import javax.xml.xpath.XPathFunctionException;

/**
 * The Class Matches.
 *
 * @author richardallenbair
 */
public class Matches extends AbstractFunction {
    /**
     * Creates a new instance of EndsWith.
     */
    public Matches() {
        super("matches", 2);
    }

    /*
     * (non-Javadoc)
     * @see javax.xml.xpath.XPathFunction#evaluate(java.util.List)
     */
    @Override
    public Object evaluate(List args) throws XPathFunctionException {
        String s = getStringParam(args.get(0));
        String pattern = getStringParam(args.get(1));
        return s.matches(pattern);
    }
}
