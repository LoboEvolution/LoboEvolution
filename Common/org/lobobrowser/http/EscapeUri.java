/*
 * EndsWith.java
 *
 * Created on September 27, 2006, 10:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.lobobrowser.http;

import java.net.URLEncoder;
import java.util.List;

import javax.xml.xpath.XPathFunctionException;

/**
 *
 * @author richardallenbair
 */
public class EscapeUri extends AbstractFunction {
    /** Creates a new instance of EndsWith */
    public EscapeUri() {
        super("escape-uri", 2);
    }

    public Object evaluate(List args) throws XPathFunctionException {
        try {
            return URLEncoder.encode(getStringParam(args.get(0)), "UTF-8");
        } catch (Exception e) {
            throw new XPathFunctionException(e);
        }
    }
}
